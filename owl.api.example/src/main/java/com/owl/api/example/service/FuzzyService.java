package com.owl.api.example.service;

import com.owl.api.example.dto.MembershipDTO;
import com.owl.api.example.dto.PurposeDTO;
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
    public PurposeDTO getPurposeType(int cpu_cores, int cpu_threads, int cpu_tdp, int ram_capacity_in_gb,
                                 int ram_speed, double ram_voltage, int gpu_memory_in_gb, int gpu_clock_in_mhz,
                                 int psu_output_power, int hdd_capacity_in_gb) {
        PurposeDTO purpose = new PurposeDTO();
        MembershipDTO appDevelopmentMemberships = new MembershipDTO();
        MembershipDTO gamingMemberships = new MembershipDTO();
        MembershipDTO cryptoMiningMemberships = new MembershipDTO();
        MembershipDTO homeMemberships = new MembershipDTO();

        fis.setVariable("cpu_cores", cpu_cores);
        fis.setVariable("cpu_threads", cpu_threads);
        fis.setVariable("cpu_tdp", cpu_tdp);
        fis.setVariable("ram_capacity_in_gb", ram_capacity_in_gb);
        fis.setVariable("ram_speed", ram_speed);
        fis.setVariable("ram_voltage", ram_voltage);
        fis.setVariable("gpu_memory_in_gb", gpu_memory_in_gb);
        fis.setVariable("gpu_clock_in_mhz", gpu_clock_in_mhz);
        fis.setVariable("psu_output_power", psu_output_power);
        fis.setVariable("hdd_capacity_in_gb", hdd_capacity_in_gb);

        fis.evaluate();
       // System.out.println(fis);

        appDevelopmentMemberships.setPoor(fis.getVariable("app_development").getMembership("poor"));
        appDevelopmentMemberships.setGood(fis.getVariable("app_development").getMembership("good"));
        appDevelopmentMemberships.setExcellent(fis.getVariable("app_development").getMembership("excellent"));
        purpose.setAppDevelopment(appDevelopmentMemberships);

        gamingMemberships.setPoor(fis.getVariable("gaming").getMembership("poor"));
        gamingMemberships.setGood(fis.getVariable("gaming").getMembership("good"));
        gamingMemberships.setExcellent(fis.getVariable("gaming").getMembership("excellent"));
        purpose.setGaming(gamingMemberships);

        cryptoMiningMemberships.setPoor(fis.getVariable("crypto_mining").getMembership("poor"));
        cryptoMiningMemberships.setGood(fis.getVariable("crypto_mining").getMembership("good"));
        cryptoMiningMemberships.setExcellent(fis.getVariable("crypto_mining").getMembership("excellent"));
        purpose.setCryptoMining(cryptoMiningMemberships);

        homeMemberships.setPoor(fis.getVariable("home").getMembership("poor"));
        homeMemberships.setGood(fis.getVariable("home").getMembership("good"));
        homeMemberships.setExcellent(fis.getVariable("home").getMembership("excellent"));
        purpose.setHome(homeMemberships);

        System.out.println("Cpu cores poor: " + fis.getVariable("cpu_cores").getMembership("poor"));
        System.out.println("Cpu cores average: " + fis.getVariable("cpu_cores").getMembership("average"));
        System.out.println("Cpu cores excellent: " + fis.getVariable("cpu_cores").getMembership("excellent"));

        System.out.println("App dev purpose poor: " + fis.getVariable("app_development").getMembership("poor"));
        System.out.println("App dev purpose good: " + fis.getVariable("app_development").getMembership("good"));
        System.out.println("App dev purpose excellent: " + fis.getVariable("app_development").getMembership("excellent"));

        return purpose;
    }
}
