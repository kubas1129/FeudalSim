package com.tobi;

import com.sun.org.apache.regexp.internal.RE;
import com.tobi.enums.Amount;
import com.tobi.enums.Resource;
import com.tobi.enums.SocialStatus;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Woronko on 2016-07-20.
 */
public class Town {
    String name;
    Jobs jobs;
    int population;
    LinkedList<House> houses=new LinkedList<>();
    LinkedList<Building> infrastructure=new LinkedList<>();
    LinkedList<Resource> resources=new LinkedList<>();
    House homeless=new House();

    public void genHouses(){
        Random random=new Random();
        int peopleLeft=population,householdSize=0;
        while(peopleLeft>0){
            householdSize=random.nextInt(6)+3;
            while(householdSize>peopleLeft){
                householdSize=random.nextInt(8)+1;
            }
            houses.add(new House(householdSize));
            for(int i=0;i<householdSize;i++){
                houses.getLast().addHabitant(new Citizen(SocialStatus.PEASANT));
                assignJobs(houses.getLast().household.getFirst());
            }
            peopleLeft-=householdSize;
            if(peopleLeft==0)break;
        }
    }

    public void assignJobs(Citizen citizen){
        if(citizen.age>10) {
            for (Building building : infrastructure) {
                if (building.workerNumber < building.workerNumberMax && building.workerType.strength < citizen.strength && building.workerType.iq < citizen.iq) {
                    building.addWorker(citizen);
                    citizen.assignJob(building.workerType);
                    break;
                }
            }
        }
        if (citizen.job == null) {
            if (citizen.age <= 10) {
                citizen.assignJob(new Jobs().getJob("Child"));
            } else if (citizen.sex == "female") {
                citizen.assignJob(new Jobs().getJob("Lady"));
            } else if (citizen.wealth < 0.2) {
                citizen.assignJob(new Jobs().getJob("Beggar"));
            } else {
                citizen.assignJob(new Jobs().getJob("Unemployed"));
            }
        }
    }

    public void genInfrastructure(){
        Random random=new Random();
        infrastructure.add(new Building(new Jobs().getJob("Innkeep"),random.nextInt(1)+1,random.nextInt(100),Resource.FOOD,Resource.MONEY));
        if(population>10){
            if(resources.contains(Resource.LAND)) {
                infrastructure.add(new Building(new Jobs().getJob("Farmer"), random.nextInt(12) + 1, random.nextInt(100), Resource.LAND, Resource.FOOD));
            }
            if(resources.contains(Resource.WATER)){
                infrastructure.add(new Building(new Jobs().getJob("Fisherman"),random.nextInt(5)+1,random.nextInt(100),Resource.WATER,Resource.FOOD));
            }
            if(resources.contains(Resource.TREES)){
                infrastructure.add(new Building(new Jobs().getJob("Woodcutter"),random.nextInt(3)+1,random.nextInt(100),Resource.TREES,Resource.WOOD));
            }
        }
        if(population>20){
            infrastructure.add(new Building(new Jobs().getJob("Militia"),random.nextInt(2)+1));
            if(resources.contains(Resource.LAND)) {
                infrastructure.add(new Building(new Jobs().getJob("Farmer"), random.nextInt(12) + 6, random.nextInt(100), Resource.LAND, Resource.FOOD));
            }
        }
    }

    public void writeTownMembers(){
        for(House house:houses){
            for(Citizen citizen:house.household){
                citizen.write();
            }
        }
    }

    public void writeTownJobs(){
        System.out.println("Population: "+population);
        System.out.println("Houses: "+houses.size());
        for(Building building:infrastructure){
            System.out.print(building.workerType.name+"s: "+building.workerNumber+"/"+building.workerNumberMax);
            if(building.owner!=null){
                System.out.println(", Owner - "+building.owner.name+" "+building.owner.surname);
            }
        }
    }

    public Town(int population){
        this.population=population;
        resources.add(Resource.LAND);
        resources.add(Resource.TREES);
        resources.add(Resource.WATER);
        genInfrastructure();
        genHouses();
    }

}
