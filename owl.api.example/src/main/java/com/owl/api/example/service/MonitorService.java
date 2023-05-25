package com.owl.api.example.service;

import com.owl.api.example.dto.MonitorRequestDTO;
import com.owl.api.example.dto.MonitorResponseDTO;
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
public class MonitorService {
    private OntologyManager ontologyManager;
    private OWLOntologyManager manager;
    private OWLDataFactory dataFactory;
    private OWLReasonerFactory reasonerFactory;
    private OWLReasoner reasoner;
    private static String baseIRI = "http://www.semanticweb.org/sara/ontologies/2023/4/untitled-ontology-46/";
    private OWLClassExpression classMonitor;
    private OWLDataProperty monitorName;
    private OWLDataProperty monitorRefreshment;
    private OWLDataProperty monitorDiagonal;
    private OWLDataProperty monitorResolution;
    private OWLDataProperty monitorIsCurved;


    public MonitorService() throws OWLOntologyCreationException {
        ontologyManager = new OntologyManager();
        manager = OWLManager.createOWLOntologyManager();
        dataFactory = manager.getOWLDataFactory();
        reasonerFactory = new ReasonerFactory();
        reasoner = reasonerFactory.createReasoner(this.ontologyManager.getOntology());

        classMonitor = dataFactory.getOWLClass(IRI.create(baseIRI + "Monitor"));
        monitorName = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "monitor_has_name"));
        monitorRefreshment = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "monitor_has_refreshment_in_hz"));
        monitorResolution = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "monitor_has_resolution"));
        monitorIsCurved = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "monitor_is_curved"));
        monitorDiagonal = dataFactory.getOWLDataProperty(IRI.create(baseIRI + "monitor_has_diagonal"));
    }
    public List<MonitorResponseDTO> getAllMonitors() {
        return getAllMonitorsDTOs(classMonitor);
    }
    private List<MonitorResponseDTO> getAllMonitorsDTOs(OWLClassExpression classMonitor) {
        Set<OWLNamedIndividual> individuals = reasoner.getInstances(classMonitor, false).getFlattened();
        List<MonitorResponseDTO> monitors = new ArrayList<>();
        for (OWLNamedIndividual individual : individuals) {
            monitors.add(setDataProperties(individual));
        }
        return monitors;
    }
    private MonitorResponseDTO setDataProperties(OWLNamedIndividual individual) {
        MonitorResponseDTO monitorResponseDTO = new MonitorResponseDTO();
        for (OWLDataPropertyAssertionAxiom assertion : ontologyManager.getOntology().getDataPropertyAssertionAxioms(individual)) {
            OWLDataProperty property = assertion.getProperty().asOWLDataProperty();
            if (monitorName.equals(property)) {
                OWLLiteral value = assertion.getObject();
                monitorResponseDTO.setName(value.getLiteral());
            } else if (monitorDiagonal.equals(property)) {
                OWLLiteral value = assertion.getObject();
                monitorResponseDTO.setDiagonal(Double.parseDouble(value.getLiteral()));
            } else if (monitorResolution.equals(property)) {
                OWLLiteral value = assertion.getObject();
                monitorResponseDTO.setResolution(value.getLiteral());
            } else if (monitorRefreshment.equals(property)) {
                OWLLiteral value = assertion.getObject();
                monitorResponseDTO.setRefreshmentInHz(Integer.parseInt(value.getLiteral()));
            }
            else if (monitorIsCurved.equals(property)) {
                OWLLiteral value = assertion.getObject();
                monitorResponseDTO.setCurved(Boolean.parseBoolean((value.getLiteral())));
            }
        }
        return monitorResponseDTO;
    }
    public List<MonitorResponseDTO> getMonitorsByProperties(MonitorRequestDTO requestDTO) {

        OWLDataRange diagonalRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(String.valueOf(requestDTO.getMinDiagonal()), dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()))),
                dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, dataFactory.getOWLLiteral(String.valueOf(requestDTO.getMaxDiagonal()), dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()))));

        OWLDataRange refreshmentRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMinRefreshment())),
                dataFactory.getOWLFacetRestriction(OWLFacet.MAX_INCLUSIVE, dataFactory.getOWLLiteral(requestDTO.getMaxRefreshment())));

        OWLClassExpression queryExpression = dataFactory.getOWLObjectIntersectionOf(
                classMonitor,
                dataFactory.getOWLDataSomeValuesFrom(monitorDiagonal, diagonalRange),
                dataFactory.getOWLDataSomeValuesFrom(monitorRefreshment, refreshmentRange),
                dataFactory.getOWLDataHasValue(monitorIsCurved, dataFactory.getOWLLiteral(requestDTO.isCurved()))
        );
        return getAllMonitorsDTOs(queryExpression);
    }
    public List<MonitorResponseDTO> getMonitorsUpgrades(String monitor){

        OWLNamedIndividual monitorIndividual = dataFactory.getOWLNamedIndividual(IRI.create(baseIRI + monitor.replace(" ", "_")));

        Set<OWLLiteral> diagonalLiterals = reasoner.getDataPropertyValues(monitorIndividual, monitorDiagonal);
        OWLLiteral diagonalLiteral = diagonalLiterals.stream().findFirst().orElse(null);

        Set<OWLLiteral> refreshmentLiterals = reasoner.getDataPropertyValues(monitorIndividual, monitorRefreshment);
        OWLLiteral refreshmentLiteral = refreshmentLiterals.stream().findFirst().orElse(null);

        OWLDataRange diagonalRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_EXCLUSIVE, dataFactory.getOWLLiteral(String.valueOf(diagonalLiteral.getLiteral()), dataFactory.getOWLDatatype(XSDVocabulary.DECIMAL.getIRI()))));

        OWLDataRange refreshmentRange = dataFactory.getOWLDatatypeRestriction(dataFactory.getIntegerOWLDatatype(),
                dataFactory.getOWLFacetRestriction(OWLFacet.MIN_INCLUSIVE, dataFactory.getOWLLiteral(Integer.parseInt(refreshmentLiteral.getLiteral()))));

        OWLClassExpression queryExpression = dataFactory.getOWLObjectIntersectionOf(
                classMonitor,
                dataFactory.getOWLDataSomeValuesFrom(monitorDiagonal, diagonalRange),
                dataFactory.getOWLDataSomeValuesFrom(monitorRefreshment, refreshmentRange)
        );
        return getAllMonitorsDTOs(queryExpression);
    }
}
