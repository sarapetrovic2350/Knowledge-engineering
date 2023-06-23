package com.owl.api.example.service;

import com.owl.api.example.dto.CPUResponseDTO;
import com.owl.api.example.dto.MotherboardResponseDTO;
import org.semanticweb.HermiT.ReasonerFactory;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
@Service
public class MotherboardService {
    private OntologyManager ontologyManager;
    private OWLOntologyManager manager;
    private OWLDataFactory dataFactory;
    private OWLReasonerFactory reasonerFactory;
    private OWLReasoner reasoner;
    private static String baseIRI = "http://www.semanticweb.org/sara/ontologies/2023/3//";
    private OWLClassExpression classMotherboard;
    private OWLDataProperty motherboardName;

    public MotherboardService() throws OWLOntologyCreationException {
        ontologyManager = new OntologyManager();
        manager = OWLManager.createOWLOntologyManager();
        dataFactory = manager.getOWLDataFactory();
        reasonerFactory = new ReasonerFactory();
        reasoner = reasonerFactory.createReasoner(this.ontologyManager.getOntology());

        classMotherboard = dataFactory.getOWLClass(IRI.create(baseIRI + "Motherboard"));
        motherboardName = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "motherboard_has_name"));

    }
    public List<MotherboardResponseDTO> getAllMotherboards() {
        return getAllMotherboardsDTOs(classMotherboard);
    }

    private List<MotherboardResponseDTO> getAllMotherboardsDTOs(OWLClassExpression classCPU) {
        Set<OWLNamedIndividual> individuals = reasoner.getInstances(classMotherboard, false).getFlattened();
        List<MotherboardResponseDTO> motherboards = new ArrayList<>();
        for (OWLNamedIndividual individual : individuals) {
            motherboards.add(setDataProperties(individual));
        }
        return motherboards;
    }
    private MotherboardResponseDTO setDataProperties(OWLNamedIndividual individual) {
        MotherboardResponseDTO motherboardResponseDTO = new MotherboardResponseDTO();
        for (OWLDataPropertyAssertionAxiom assertion : ontologyManager.getOntology().getDataPropertyAssertionAxioms(individual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (motherboardName.equals(property)) {
                OWLLiteral value = assertion.getObject();
                motherboardResponseDTO.setName(value.getLiteral());
            }
        }
        return motherboardResponseDTO;
    }



}
