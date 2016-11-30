package com.tobi;

import com.tobi.enums.Infrastructure;
import com.tobi.enums.JobName;
import com.tobi.enums.Resource;
import com.tobi.enums.Weather;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Woronko on 2016-07-20.
 */
public class Job {
    String name;
    JobName jobName;
    int iq;
    int strength;
    int charisma;
    Resource requiredResource;
    Resource outputResource;
    boolean weatherAffected;

    public Job(String name, int iq, int strength, int charisma){
        this.name=name;
        this.iq=iq;
        this.strength=strength;
        this.charisma=charisma;
    }
    public Job(JobName name, int iq, int strength, int charisma, Resource requiredResource, Resource outputResource, boolean weatherAffected){
        this.jobName=name;
        this.name=jobName.toString();
        this.iq=iq;
        this.strength=strength;
        this.charisma=charisma;
        this.requiredResource=requiredResource;
        this.outputResource=outputResource;
        this.weatherAffected=weatherAffected;
    }

    public boolean canHeDoIt(Citizen citizen){
        if(citizen.strength>strength&&citizen.iq>iq){
            return true;
        }else{
            return false;
        }
    }

    public boolean areThereResources(LinkedList<Resource> resources){
        for(Resource resource:resources) {
            if(resource==requiredResource){
                return true;
            }
        }
        return false;
    }

    public void produce(Weather weather, Citizen citizen) {
        Random random = new Random();
        double baseProduction;
        baseProduction = random.nextInt(50) + 100;
        if(this.weatherAffected) {
            switch (weather) {
                case Hot:
                    baseProduction *= 0.8;
                    break;
                case Draught:
                    baseProduction *= 0.2;
                    break;
                case Blizzard:
                    baseProduction *= 0.2;
                    break;
                case Lightsnow:
                    baseProduction *= 0.5;
                    break;
                case Deepsnow:
                    baseProduction *= 0.3;
                    break;
                case Frost:
                    baseProduction *= 0.8;
                    break;
                case Rainy:
                    baseProduction *= 1.2;
                    break;
                case Sunny:
                    baseProduction *= 1.4;
                    break;
            }
        }

        if(strength>0) {
            baseProduction += citizen.strength;
        }
        if(iq>0) {
            baseProduction += citizen.iq;
        }
        Double wealth;
        if(citizen.resources.containsKey(outputResource)) {
            wealth=citizen.resources.get(outputResource).doubleValue();
            citizen.resources.replace(outputResource,baseProduction+wealth);
        }else{
            citizen.resources.put(outputResource,baseProduction);
        }
    }
/*
    public boolean isThereInfrastructure(LinkedList<Infrastructure> infrastructures){
        for(Infrastructure infrastructure:infrastructures) {
            if(infrastructure==requiredResource){
                return true;
            }
        }
        return false;
    }
    */
}
