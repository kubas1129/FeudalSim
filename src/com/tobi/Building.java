package com.tobi;

import com.tobi.enums.Amount;
import com.tobi.enums.Resource;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Woronko on 2016-07-20.
 */
public class Building {
    Job workerType;
    int workerNumberMax;
    int workerNumber;
    Citizen owner;
    LinkedList<Citizen>workers=new LinkedList<>();
    LinkedList<Resource>requirements=new LinkedList<>();
    LinkedList<Resource>production=new LinkedList<>();
    int resourcesStored;

    public Building(Job workerType, int workerNumberMax, int resourcesStored, Resource resource, Resource production){
        this.workerType=workerType;
        this.workerNumberMax=workerNumberMax;
        this.resourcesStored=resourcesStored;
        requirements.push(resource);
        this.production.push(production);
    }
    public Building(Job workerType, int workerNumberMax){
        this.workerType=workerType;
        this.workerNumberMax=workerNumberMax;
    }
    public void addWorker(Citizen worker){
        workers.add(worker);
        if(workerNumber==0){
            owner=worker;
        }
        workerNumber++;
    }
    public void setOwner(Citizen owner){
        this.owner=owner;
    }
}
