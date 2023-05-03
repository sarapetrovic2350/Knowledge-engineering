package com.owl.api.example.service;

import com.owl.api.example.dto.CPURequestDTO;
import com.owl.api.example.dto.CPUResponseDTO;
import com.owl.api.example.dto.SSDRequestDTO;
import com.owl.api.example.dto.SSDResponseDTO;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.semanticweb.owlapi.vocab.XSDVocabulary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CPUService {

    private OntologyManager ontologyManager;
    private OWLOntologyManager manager;
    private OWLDataFactory dataFactory;
    private OWLReasonerFactory reasonerFactory;
    private OWLReasoner reasoner;
    private static String baseIRI = "http://www.semanticweb.org/sara/ontologies/2023/3//";
    private OWLClassExpression classCPU;
    private OWLDataProperty cpuName;
    private OWLDataProperty cpuCores;
    private OWLDataProperty cpuClockRate;
    private OWLDataProperty cpuGaming;
    private OWLDataProperty cpuTDP;
    private OWLDataProperty cpuThreads;
    private OWLDataProperty cpuType;
    private OWLDataProperty cpuIntegratedGPU;

    public CPUService() throws OWLOntologyCreationException {
        ontologyManager = new OntologyManager();
        manager = OWLManager.createOWLOntologyManager();
        dataFactory = manager.getOWLDataFactory();
        reasonerFactory = new ReasonerFactory();
        reasoner = reasonerFactory.createReasoner(this.ontologyManager.getOntology());

        classCPU = dataFactory.getOWLClass(IRI.create(baseIRI + "CPU"));
        cpuName = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "cpu_has_name"));
        cpuCores = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "cpu_has_cores"));
        cpuClockRate = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "cpu_has_clock_rate"));
        cpuGaming = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "cpu_for_gaming"));
        cpuTDP = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "cpu_has_tdp"));
        cpuThreads = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "cpu_has_threads"));
        cpuType = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "cpu_has_type"));
        cpuIntegratedGPU = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "cpu_with_integrated_gpu"));

    }

    public List<CPUResponseDTO> getAllCPUs() {
        return getAllCPUsDTOs(classCPU);
    }

    private List<CPUResponseDTO> getAllCPUsDTOs(OWLClassExpression classCPU) {
        Set<OWLNamedIndividual> individuals = reasoner.getInstances(classCPU, false).getFlattened();
        List<CPUResponseDTO> CPUs = new ArrayList<>();
        for (OWLNamedIndividual individual : individuals) {
            CPUs.add(setDataProperties(individual));
        }
        return CPUs;
    }

    private CPUResponseDTO setDataProperties(OWLNamedIndividual individual) {
        CPUResponseDTO cpuResponseDTO = new CPUResponseDTO();
        for (OWLDataPropertyAssertionAxiom assertion : ontologyManager.getOntology().getDataPropertyAssertionAxioms(individual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (cpuName.equals(property)) {
                OWLLiteral value = assertion.getObject();
                cpuResponseDTO.setCpuName(value.getLiteral());
            } else if (cpuCores.equals(property)) {
                OWLLiteral value = assertion.getObject();
                cpuResponseDTO.setCpuCores(Integer.parseInt(value.getLiteral()));
            } else if (cpuClockRate.equals(property)) {
                OWLLiteral value = assertion.getObject();
                cpuResponseDTO.setCpuClockRate(Double.parseDouble(value.getLiteral()));
            } else if (cpuGaming.equals(property)) {
                OWLLiteral value = assertion.getObject();
                cpuResponseDTO.setCpuGaming(Boolean.parseBoolean(value.getLiteral()));
            }
            else if (cpuTDP.equals(property)) {
                OWLLiteral value = assertion.getObject();
                cpuResponseDTO.setCpuTDP(Integer.parseInt(value.getLiteral()));
            }
            else if (cpuThreads.equals(property)) {
                OWLLiteral value = assertion.getObject();
                cpuResponseDTO.setCpuThreads(Integer.parseInt(value.getLiteral()));
            }
            else if (cpuType.equals(property)) {
                OWLLiteral value = assertion.getObject();
                cpuResponseDTO.setCpuType((value.getLiteral()));
            }
            else if (cpuIntegratedGPU.equals(property)) {
                OWLLiteral value = assertion.getObject();
                cpuResponseDTO.setCpuIntegratedGPU(Boolean.parseBoolean(value.getLiteral()));
            }
        }
        return cpuResponseDTO;
    }

    public List<CPUResponseDTO> getCPUSByProperties(CPURequestDTO requestDTO) {

        OWLDataRange coreRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMinCores())),
                dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMaxCores())));

        OWLDataRange threadsRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMinThreads())),
                dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMaxThreads())));

        OWLDataRange clockRateRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(String.valueOf(requestDTO.getMinClockRate()), dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()))),
                dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, dataFactory.getOWLLiteral(String.valueOf(requestDTO.getMaxClockRate()), dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()))));

        OWLClassExpression queryExpression = dataFactory.getOWLObjectIntersectionOf(
                classCPU,
                dataFactory.getOWLDataSomeValuesFrom(cpuCores, coreRange),
                dataFactory.getOWLDataSomeValuesFrom(cpuThreads, threadsRange),
                dataFactory.getOWLDataSomeValuesFrom(cpuClockRate, clockRateRange)
        );
        return getAllCPUsDTOs(queryExpression);
    }

    public List<CPUResponseDTO> getCPUsUpgrades(String CPU){

        OWLNamedIndividual CPUIndividual = dataFactory.getOWLNamedIndividual(IRI.create(baseIRI +CPU.replace(" ", "_")));

        Set<OWLLiteral> coresLiterals = reasoner.getDataPropertyValues(CPUIndividual, cpuCores);
        OWLLiteral coresLiteral = coresLiterals.stream().findFirst().orElse(null);

        Set<OWLLiteral> threadsLiterals = reasoner.getDataPropertyValues(CPUIndividual, cpuThreads);
        OWLLiteral threadsLiteral = threadsLiterals.stream().findFirst().orElse(null);

        Set<OWLLiteral> clockRateLiterals = reasoner.getDataPropertyValues(CPUIndividual, cpuClockRate);
        OWLLiteral clockRateLiteral = clockRateLiterals.stream().findFirst().orElse(null);

        OWLDataRange coreRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(Integer.parseInt(coresLiteral.getLiteral()))));

        OWLDataRange threadsRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(Integer.parseInt(threadsLiteral.getLiteral()))));

        OWLDataRange clockRateRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(String.valueOf(clockRateLiteral.getLiteral()), dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()))));

        OWLClassExpression queryExpression = dataFactory.getOWLObjectIntersectionOf(
                classCPU,
                dataFactory.getOWLDataSomeValuesFrom(cpuCores, coreRange),
                dataFactory.getOWLDataSomeValuesFrom(cpuThreads, threadsRange),
                dataFactory.getOWLDataSomeValuesFrom(cpuClockRate, clockRateRange)
        );
        return getAllCPUsDTOs(queryExpression);
    }
}
