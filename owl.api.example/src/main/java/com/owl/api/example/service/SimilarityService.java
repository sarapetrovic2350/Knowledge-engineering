package com.owl.api.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import com.owl.api.example.csvconn.CsvConnector;
import com.owl.api.example.dto.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ucm.gaia.jcolibri.casebase.LinealCaseBase;
import ucm.gaia.jcolibri.cbraplications.StandardCBRApplication;
import ucm.gaia.jcolibri.cbrcore.*;
import ucm.gaia.jcolibri.exception.ExecutionException;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNConfig;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.NNScoringMethod;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.global.Average;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Equal;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Interval;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.MaxString;
import ucm.gaia.jcolibri.method.retrieve.NNretrieval.similarity.local.Threshold;
import ucm.gaia.jcolibri.method.retrieve.RetrievalResult;
import ucm.gaia.jcolibri.method.retrieve.selection.SelectCases;

import java.io.FileWriter;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
public class SimilarityService implements StandardCBRApplication {
    private Connector connector;
    private CBRCaseBase caseBase;
    private NNConfig simConfig;
    private SimilarityDTO similarityDTO = new SimilarityDTO();
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    public SimilarityService(ModelMapper modelMapper, ObjectMapper objectMapper) {
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
    }

    @Override
    public void configure() throws ExecutionException {
        connector =  new CsvConnector();

        caseBase = new LinealCaseBase();

        simConfig = new NNConfig();
        simConfig.setDescriptionSimFunction(new Average());
        simConfig.addMapping(new Attribute("cpuName", SimilarityDTO.class), new MaxString());
        simConfig.addMapping(new Attribute("cpuCores", SimilarityDTO.class), new Threshold(2));
        simConfig.addMapping(new Attribute("motherboardName", SimilarityDTO.class), new MaxString());
        simConfig.addMapping(new Attribute("ramCapacity", SimilarityDTO.class), new Equal());
        simConfig.addMapping(new Attribute("ramType", SimilarityDTO.class), new MaxString());
        simConfig.addMapping(new Attribute("graphicCardMemory", SimilarityDTO.class), new Interval(10));
        simConfig.addMapping(new Attribute("hddCapacity", SimilarityDTO.class), new Threshold(1000));
        simConfig.addMapping(new Attribute("psuOutputPower", SimilarityDTO.class), new Interval(500));
    }

    @Override
    public CBRCaseBase preCycle() throws ExecutionException {
        caseBase.init(connector);
        java.util.Collection<CBRCase> cases = caseBase.getCases();
        return caseBase;
    }

    @Override
    public void cycle(CBRQuery cbrQuery) throws ExecutionException {
        Collection<RetrievalResult> eval = NNScoringMethod.evaluateSimilarity(caseBase.getCases(), cbrQuery, simConfig);
        eval = SelectCases.selectTopKRR(eval, 5);
        List<SimilarityEvaluationDTO> similarityEvaluationDTOS = new ArrayList<>();
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.HALF_UP);
        for(RetrievalResult res : eval){
            SimilarityEvaluationDTO similarityEvaluationDTO = new SimilarityEvaluationDTO();
            similarityEvaluationDTO.setObject(modelMapper.map(res.get_case().getDescription(), SimilarityDTO.class));
            similarityEvaluationDTO.setEvaluation(Double.parseDouble(df.format(res.getEval()*100))+"%");
            similarityEvaluationDTOS.add(similarityEvaluationDTO);
        }
        saveResultsToTXTFile(similarityEvaluationDTOS);
    }

    private void saveResultsToTXTFile(List<SimilarityEvaluationDTO> similarityEvaluationDTOS) {
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(similarityEvaluationDTOS);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        try (FileWriter fileWriter = new FileWriter("data/results.txt")) {
            fileWriter.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void postCycle() throws ExecutionException {

    }
    public void getSimilarComputers(String cpuName, int cpuCores, String motherboardName,int ramCapacity,
                                    String ramType, int graphicCardMemory, int hddCapacity, int psuOutputPower) {

        StandardCBRApplication recommender = new SimilarityService(modelMapper, objectMapper);
        try {
            recommender.configure();

            recommender.preCycle();

            CBRQuery query = new CBRQuery();

            similarityDTO.setCpuName(cpuName);
            similarityDTO.setCpuCores(cpuCores);
            similarityDTO.setMotherboardName(motherboardName);
            similarityDTO.setRamCapacity(ramCapacity);
            similarityDTO.setRamType(ramType);
            similarityDTO.setGraphicCardMemory(graphicCardMemory);
            similarityDTO.setHddCapacity(hddCapacity);
            similarityDTO.setPsuOutputPower(psuOutputPower);

            query.setDescription(similarityDTO);

            recommender.cycle(query);

            saveInputToCSVFile();

            recommender.postCycle();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void saveInputToCSVFile() {
        try (CSVWriter writer = (CSVWriter) new CSVWriterBuilder(new FileWriter("data/similarity.csv", true))
                .withSeparator(';')
                .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                .build()) {
            String[] data = {
                    similarityDTO.getCpuName(),
                    String.valueOf(similarityDTO.getCpuCores()),
                    similarityDTO.getMotherboardName(),
                    String.valueOf(similarityDTO.getRamCapacity()),
                    similarityDTO.getRamType(),
                    String.valueOf(similarityDTO.getGraphicCardMemory()),
                    String.valueOf(similarityDTO.getHddCapacity()),
                    String.valueOf(similarityDTO.getPsuOutputPower()),
            };
            writer.writeNext(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
