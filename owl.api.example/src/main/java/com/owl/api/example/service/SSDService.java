package com.owl.api.example.service;

import com.owl.api.example.dto.SSDRequestDTO;
import com.owl.api.example.dto.SSDResponseDTO;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.vocab.OWLFacet;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class SSDService {
    private OntologyManager ontologyManager;
    private OWLOntologyManager manager;
    private OWLDataFactory dataFactory;
    private OWLReasonerFactory reasonerFactory;
    private OWLReasoner reasoner;
    private static String baseIRI = "http://www.semanticweb.org/sara/ontologies/2023/3//";
    private OWLClassExpression classSSD;
    private OWLDataProperty ssdName;
    private OWLDataProperty ssdCapacity;
    private OWLDataProperty ssdReadSpeed;
    private OWLDataProperty ssdWriteSpeed;
    private OWLDataProperty ssdInterface;
    private OWLDataProperty ssdFormat;


    public SSDService() throws OWLOntologyCreationException {
        ontologyManager = new OntologyManager();
        manager = OWLManager.createOWLOntologyManager();
        dataFactory = manager.getOWLDataFactory();
        reasonerFactory = new ReasonerFactory();
        reasoner = reasonerFactory.createReasoner(this.ontologyManager.getOntology());

        classSSD = dataFactory.getOWLClass(IRI.create(baseIRI + "SSD"));
        ssdName = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ssd_has_name"));
        ssdCapacity = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ssd_has_capacity_in_gb"));
        ssdReadSpeed = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ssd_has_read_speed_in_mbs"));
        ssdWriteSpeed = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ssd_has_write_speed_in_mbs"));
        ssdInterface = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ssd_has_interface"));
        ssdFormat = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "ssd_has_format"));

    }
    public List<SSDResponseDTO> getAllSSDs() {
        return getAllSSDsDTOs(classSSD);
    }

    private List<SSDResponseDTO> getAllSSDsDTOs(OWLClassExpression classSSD) {
        Set<OWLNamedIndividual> individuals = reasoner.getInstances(classSSD, false).getFlattened();
        List<SSDResponseDTO> SSDs = new ArrayList<>();
        for (OWLNamedIndividual individual : individuals) {
            SSDs.add(setDataProperties(individual));
        }
        return SSDs;
    }
    private SSDResponseDTO setDataProperties(OWLNamedIndividual individual) {
        SSDResponseDTO ssdResponseDTO = new SSDResponseDTO();
        for (OWLDataPropertyAssertionAxiom assertion : ontologyManager.getOntology().getDataPropertyAssertionAxioms(individual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (ssdName.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ssdResponseDTO.setName(value.getLiteral());
            } else if (ssdCapacity.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ssdResponseDTO.setCapacityInGB(Integer.parseInt(value.getLiteral()));
            } else if (ssdReadSpeed.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ssdResponseDTO.setReadSpeedInMBs(Integer.parseInt(value.getLiteral()));
            } else if (ssdWriteSpeed.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ssdResponseDTO.setWriteSpeedInMBs(Integer.parseInt(value.getLiteral()));
            }
            else if (ssdInterface.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ssdResponseDTO.setInterfaceSSD((value.getLiteral()));
            }
            else if (ssdFormat.equals(property)) {
                OWLLiteral value = assertion.getObject();
                ssdResponseDTO.setFormat((value.getLiteral()));
            }
        }
        return ssdResponseDTO;
    }
    public List<SSDResponseDTO> getSSDsByProperties(SSDRequestDTO requestDTO) {

        OWLDataRange capacityRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMinCapacity())),
                dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMaxCapacity())));

        OWLDataRange readSpeedRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMinReadSpeed())),
                dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMaxReadSpeed())));

        OWLDataRange writeSpeedRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMinWriteSpeed())),
                dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMaxWriteSpeed())));

        OWLClassExpression queryExpression = dataFactory.getOWLObjectIntersectionOf(
                classSSD,
                dataFactory.getOWLDataSomeValuesFrom(ssdCapacity, capacityRange),
                dataFactory.getOWLDataSomeValuesFrom(ssdReadSpeed, readSpeedRange),
                dataFactory.getOWLDataSomeValuesFrom(ssdWriteSpeed, writeSpeedRange)
        );
        return getAllSSDsDTOs(queryExpression);
    }
    public List<SSDResponseDTO> getSSDsUpgrades(String SSD){

        OWLNamedIndividual SSDIndividual = dataFactory.getOWLNamedIndividual(IRI.create(baseIRI + SSD.replace(" ", "_")));

        Set<OWLLiteral> capacityLiterals = reasoner.getDataPropertyValues(SSDIndividual, ssdCapacity);
        OWLLiteral capacityLiteral = capacityLiterals.stream().findFirst().orElse(null);

        Set<OWLLiteral> readSpeedLiterals = reasoner.getDataPropertyValues(SSDIndividual, ssdReadSpeed);
        OWLLiteral readSpeedLiteral = readSpeedLiterals.stream().findFirst().orElse(null);

        Set<OWLLiteral> writeSpeedLiterals = reasoner.getDataPropertyValues(SSDIndividual, ssdWriteSpeed);
        OWLLiteral writeSpeedLiteral = writeSpeedLiterals.stream().findFirst().orElse(null);

        OWLDataRange capacityRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(Integer.parseInt(capacityLiteral.getLiteral()))));

        OWLDataRange readSpeedRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(Integer.parseInt(readSpeedLiteral.getLiteral()))));

        OWLDataRange writeSpeedRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(Integer.parseInt(writeSpeedLiteral.getLiteral()))));

        OWLClassExpression queryExpression = dataFactory.getOWLObjectIntersectionOf(
                classSSD,
                dataFactory.getOWLDataSomeValuesFrom(ssdCapacity, capacityRange),
                dataFactory.getOWLDataSomeValuesFrom(ssdReadSpeed, readSpeedRange),
                dataFactory.getOWLDataSomeValuesFrom(ssdWriteSpeed, writeSpeedRange)
        );
        return getAllSSDsDTOs(queryExpression);
    }

}
