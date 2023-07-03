package com.owl.api.example.csvconn;

import com.owl.api.example.dto.SimilarityDTO;
import ucm.gaia.jcolibri.cbrcore.CBRCase;
import ucm.gaia.jcolibri.cbrcore.CaseBaseFilter;
import ucm.gaia.jcolibri.cbrcore.Connector;
import ucm.gaia.jcolibri.exception.InitializingException;
import ucm.gaia.jcolibri.util.FileIO;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collection;
import java.util.LinkedList;

public class CsvConnector implements Connector {
    @Override
    public void initFromXMLfile(URL url) throws InitializingException {

    }

    @Override
    public void close() {

    }

    @Override
    public void storeCases(Collection<CBRCase> collection) {

    }

    @Override
    public void deleteCases(Collection<CBRCase> collection) {

    }

    @Override
    public Collection<CBRCase> retrieveAllCases() {
        LinkedList<CBRCase> cases = new LinkedList<CBRCase>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(FileIO.openFile("data/similarity.csv")));

            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.startsWith("#") || (line.length() == 0))
                    continue;
                String[] values = line.split(";");

                CBRCase cbrCase = new CBRCase();

                SimilarityDTO similarityDTO = new SimilarityDTO();

                similarityDTO.setCpuName(values[0]);
                similarityDTO.setCpuCores(Integer.parseInt(values[1]));
                similarityDTO.setMotherboardName((values[2]));
                similarityDTO.setRamCapacity(Integer.parseInt(values[3]));
                similarityDTO.setRamType(values[4]);
                similarityDTO.setGraphicCardMemory(Integer.parseInt(values[5]));
                similarityDTO.setHddCapacity(Integer.parseInt(values[6]));
                similarityDTO.setPsuOutputPower(Integer.parseInt(values[7]));

                cbrCase.setDescription(similarityDTO);
                cases.add(cbrCase);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cases;
    }


    @Override
    public Collection<CBRCase> retrieveSomeCases(CaseBaseFilter caseBaseFilter) {
        return null;
    }
}
