package com.owl.api.example.service;

import com.owl.api.example.dto.*;
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
public class RAMService {
    private OntologyManager ontologyManager;
    private OWLOntologyManager manager;
    private OWLDataFactory dataFactory;
    private OWLReasonerFactory reasonerFactory;
    private OWLReasoner reasoner;
    private static String baseIRI = "http://www.semanticweb.org/sara/ontologies/2023/4/untitled-ontology-44/";
    private OWLClassExpression classRAM;
    private OWLDataProperty ramName;
    private OWLDataProperty ramCapacity;
    private OWLDataProperty ramVoltage;
    private OWLDataProperty ramType;
    private OWLDataProperty ramRgb;
    private OWLDataProperty ramLatency;
    private OWLDataProperty ramMaxFrequency;

    public RAMService() throws OWLOntologyCreationException {
        ontologyManager = new OntologyManager();
        manager = OWLManager.createOWLOntologyManager();
        dataFactory = manager.getOWLDataFactory();
        reasonerFactory = new ReasonerFactory();
        reasoner = reasonerFactory.createReasoner(this.ontologyManager.getOntology());

        classRAM = dataFactory.getOWLClass(IRI.create(baseIRI + "RAM"));
        ramName = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ram_has_name"));
        ramCapacity = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ram_has_capacity_in_gb"));
        ramVoltage = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ram_has_voltage"));
        ramType = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ram_has_type"));
        ramRgb = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ram_has_rgb"));
        ramLatency = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ram_has_latency"));
        ramMaxFrequency = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ram_has_max_frequency_in_mhz"));

    }

    public List<RAMResponseDTO> getAllRAMs() {
        return getAllRAMsDTOs(classRAM);
    }

    private List<RAMResponseDTO> getAllRAMsDTOs(OWLClassExpression classRAM) {
        Set<OWLNamedIndividual> individuals = reasoner.getInstances(classRAM, false).getFlattened();
        List<RAMResponseDTO> RAMs = new ArrayList<>();
        for (OWLNamedIndividual individual : individuals) {
            RAMs.add(setDataProperties(individual));
        }
        return RAMs;
    }

    private RAMResponseDTO setDataProperties(OWLNamedIndividual individual) {
        RAMResponseDTO ramResponseDTO = new RAMResponseDTO();
        for (OWLDataPropertyAssertionAxiom assertion : ontologyManager.getOntology().getDataPropertyAssertionAxioms(individual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (ramName.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ramResponseDTO.setName(value.getLiteral());
            } else if (ramCapacity.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ramResponseDTO.setCapacityInGB(Integer.parseInt(value.getLiteral()));
            } else if (ramMaxFrequency.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ramResponseDTO.setMaxFrequencyInMHz(Integer.parseInt(value.getLiteral()));
            } else if (ramLatency.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ramResponseDTO.setLatency(value.getLiteral());
            } else if (ramVoltage.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ramResponseDTO.setVoltage(Double.parseDouble(value.getLiteral()));
            } else if (ramRgb.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ramResponseDTO.setHasRGB(Boolean.parseBoolean((value.getLiteral())));
            }
            else if (ramType.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ramResponseDTO.setType((value.getLiteral()));
            }
        }
        return ramResponseDTO;
    }

    public List<RAMResponseDTO> getRAMsByProperties(RAMRequestDTO requestDTO) {

        OWLDataRange capacityRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMinCapacity())),
                dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMaxCapacity())));

        OWLDataRange voltageRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(String.valueOf(requestDTO.getMinVoltage()), dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()))),
                dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, dataFactory.getOWLLiteral(String.valueOf(requestDTO.getMaxVoltage()), dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()))));

        OWLDataRange frequencyRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMinFrequency())),
                dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMaxFrequency())));

        OWLClassExpression queryExpression = dataFactory.getOWLObjectIntersectionOf(
                classRAM,
                dataFactory.getOWLDataSomeValuesFrom(ramCapacity, capacityRange),
                dataFactory.getOWLDataSomeValuesFrom(ramVoltage, voltageRange),
                dataFactory.getOWLDataSomeValuesFrom(ramMaxFrequency, frequencyRange)
        );
        return getAllRAMsDTOs(queryExpression);
    }

    public List<RAMResponseDTO> getRAMsUpgrades(String ram){

        OWLNamedIndividual ramIndividual = dataFactory.getOWLNamedIndividual(IRI.create(baseIRI + ram.replace(" ", "_")));

        Set<OWLLiteral> capacityLiterals = reasoner.getDataPropertyValues(ramIndividual, ramCapacity);
        OWLLiteral capacityLiteral = capacityLiterals.stream().findFirst().orElse(null);

        Set<OWLLiteral> voltageLiterals = reasoner.getDataPropertyValues(ramIndividual, ramVoltage);
        OWLLiteral voltageLiteral = voltageLiterals.stream().findFirst().orElse(null);

        Set<OWLLiteral> frequencyLiterals = reasoner.getDataPropertyValues(ramIndividual, ramMaxFrequency);
        OWLLiteral frequencyLiteral = frequencyLiterals.stream().findFirst().orElse(null);

        OWLDataRange voltageRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(String.valueOf(voltageLiteral.getLiteral()), dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()))));

        OWLDataRange capacityRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(Integer.parseInt(capacityLiteral.getLiteral()))));

        OWLDataRange frequencyRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(Integer.parseInt(frequencyLiteral.getLiteral()))));

        OWLClassExpression queryExpression = dataFactory.getOWLObjectIntersectionOf(
                classRAM,
                dataFactory.getOWLDataSomeValuesFrom(ramCapacity, capacityRange),
                dataFactory.getOWLDataSomeValuesFrom(ramMaxFrequency, frequencyRange),
                dataFactory.getOWLDataSomeValuesFrom(ramVoltage, voltageRange)
        );
        return getAllRAMsDTOs(queryExpression);
    }


}
