package com.owl.api.example.service;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class OntologyManager {
    public OWLOntology getOntology() {
        return ontology;
    }
    private OWLOntology ontology;

    public OntologyManager() throws OWLOntologyCreationException {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        OWLOntology ontology = man.loadOntologyFromOntologyDocument(new File("data/knowledge_ontology.ttl"));

        OWLOntology hardwareOntology = man.loadOntologyFromOntologyDocument(new File("data/hardware_new_ontology.ttl"));
        OWLOntology hardwareIndividuals = man.loadOntologyFromOntologyDocument(new File("data/hardware_individuals.ttl"));
        OWLOntology devicesOntology = man.loadOntologyFromOntologyDocument(new File("data/devices_new_ontology.ttl"));
        OWLOntology devicesIndividuals = man.loadOntologyFromOntologyDocument(new File("data/devices_individuals.ttl"));
        /*OWLOntology softwareOntology = man.loadOntologyFromOntologyDocument(new File("data/software_ontology.ttl"));

        OWLOntology desktopComputerOntology = man.loadOntologyFromOntologyDocument(new File("data/desktop_computer_ontology.ttl"));
        OWLOntology individualsOntology = man.loadOntologyFromOntologyDocument(new File("data/individuals_ontology.ttl"));
  */
        OWLOntology combinedOntology = man.createOntology();
        man.addAxioms(combinedOntology, hardwareOntology.getAxioms());
        man.addAxioms(combinedOntology, hardwareIndividuals.getAxioms());
        man.addAxioms(combinedOntology, devicesOntology.getAxioms());
        man.addAxioms(combinedOntology, devicesIndividuals.getAxioms());
//        man.addAxioms(combinedOntology, individualsOntology.getAxioms());

        this.ontology = combinedOntology;
        System.out.println(combinedOntology.getAxioms());
    }

}
