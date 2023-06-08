package com.owl.api.example.service;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.Gpr;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import org.springframework.stereotype.Service;

@Service
public class FuzzyService {
    private FIS fis;
    public FuzzyService() {
        fis = FIS.load("data/purposes.fcl",true);
        if( fis == null ) {
            System.err.println("Can't load file: data/purposes.fcl");
        }
    }
    public double getPurposeType(double service, double food){

        //JFuzzyChart.get().chart(fis);

        fis.setVariable("service", service);
        fis.setVariable("food", food);

        fis.evaluate();

        Variable tip = fis.getVariable("tip");
        //JFuzzyChart.get().chart(tip, tip.getDefuzzifier(), true);
        // Print ruleSet
        System.out.println(fis);
        System.out.println("TIP: " + fis.getVariable("service").getMembership("excellent"));
        System.out.println("TIP: " + fis.getVariable("service").getMembership("good"));
        System.out.println("TIP: " + fis.getVariable("service").getMembership("poor"));

        System.out.println("TIP: " + fis.getVariable("tip").getMembership("generous"));
        System.out.println("TIP: " + fis.getVariable("tip").getMembership("cheap"));
        System.out.println("TIP: " + fis.getVariable("tip").getMembership("average"));
        System.out.println("TIP: " + fis.getVariable("food").getMembership("delicious"));
        System.out.println("TIP: " + fis.getVariable("food").getMembership("rancid"));

        return fis.getVariable("tip").getMembership("generous");
    }
}
