package com.owl.api.example.service;

import com.owl.api.example.dto.CauseDTO;
import org.springframework.stereotype.Service;
import unbbayes.io.BaseIO;
import unbbayes.io.NetIO;
import unbbayes.prs.Node;
import unbbayes.prs.bn.JunctionTreeAlgorithm;
import unbbayes.prs.bn.ProbabilisticNetwork;
import unbbayes.prs.bn.ProbabilisticNode;
import unbbayes.util.extension.bn.inference.IInferenceAlgorithm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
@Service
public class BayesService {

    private ProbabilisticNetwork probabilisticNetwork;
    private IInferenceAlgorithm algorithm;

    public BayesService() throws IOException {
        BaseIO io = new NetIO();
        probabilisticNetwork = (ProbabilisticNetwork) io.load(new File("data/Bayes.net"));
    }
    public List<CauseDTO> getAllCausesWithProbabilities(List<String> nodes) throws Exception {
        algorithm = new JunctionTreeAlgorithm();
        algorithm.setNetwork(probabilisticNetwork);
        algorithm.run();

        for(String node : nodes){
            ProbabilisticNode factNode = (ProbabilisticNode)probabilisticNetwork.getNode(node);
            int stateIndex = 0;
            if(factNode == null)
                throw new Exception("Unknown symptom of computer failure!");
            factNode.addFinding(stateIndex);
        }

        try {
            probabilisticNetwork.updateEvidences();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        List<CauseDTO> CauseDTOs = new ArrayList<>();

        for(String node : nodes){
            for (Node parent : probabilisticNetwork.getNode(node).getParents()){
                CauseDTO parentCauseDTO = new CauseDTO();
                parentCauseDTO.setCause(parent.getName());
                parentCauseDTO.setProbability(Math.round(((ProbabilisticNode) parent).getMarginalAt(0) * 100));
                CauseDTOs.add(parentCauseDTO);
                for (Node grandparent : probabilisticNetwork.getNode(parent.getName()).getParents()){
                    CauseDTO grandparentCauseDTO = new CauseDTO();
                    grandparentCauseDTO.setCause(grandparent.getName());
                    grandparentCauseDTO.setProbability(Math.round(((ProbabilisticNode) grandparent).getMarginalAt(0) * 100));
                    CauseDTOs.add(grandparentCauseDTO);

                }
            }
        }

        if(CauseDTOs.isEmpty())
            throw new Exception("Unknown cause of computer failure!");

        CauseDTOs = CauseDTOs.stream().distinct().collect(Collectors.toList());
        
        Collections.sort(CauseDTOs);
        return CauseDTOs.subList(0, Math.min(CauseDTOs.size(), 7));
    }
}
