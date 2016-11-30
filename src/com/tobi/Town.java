package com.tobi;

import com.sun.org.apache.regexp.internal.RE;
import com.tobi.aiplugin.AIPlugin;
import com.tobi.aiplugin.HealerPlugin;
import com.tobi.enums.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Woronko on 2016-07-20.
 */
public class Town {
    String name;
    int population;
    LinkedList<House> houses=new LinkedList<>();
    LinkedList<Infrastructure> infrastructures=new LinkedList<>();
    LinkedList<Resource> resources=new LinkedList<>();
    public LinkedList<Citizen> citizens=new LinkedList<>();
    HashMap<Resource,Integer> demand;
    HashMap<Resource,Double> prices;
    House homeless=new House();

    public void executeAiPlugins(){
        for(Citizen citizen:citizens){
            for(AIPlugin aiPlugin:citizen.aiPlugins){
                aiPlugin.action();
            }
        }
        for(Citizen citizen:citizens){
            for(AIPlugin aiPlugin:citizen.aiPlugins){
                aiPlugin.endMonth();
            }
        }
    }

    public void citizenDeath(Citizen citizen){
        population--;
        for(House house:houses){
            if (house.household.contains(citizen)){
                house.household.remove(citizen);
                break;
            }
        }
    }

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
                houses.getLast().addHabitant(new Citizen(SocialStatus.PEASANT, this));
                assignJobs(houses.getLast().household.getFirst());
                citizens.add(houses.getLast().household.getFirst());
            }
            peopleLeft-=householdSize;
            if(peopleLeft==0)break;
        }
    }

    public boolean isThereJob(JobName jobname){
        for(Citizen citizen:citizens){
            if(citizen.job.jobName==jobname){
                return true;
            }
        }
        return false;
    }

    public void assignJobs(Citizen citizen){
        if(citizen.wealth<1.5) {
            if (citizen.age > 10) {
                if (WorldVariables.jobs.getJob(JobName.Farmer).areThereResources(resources)) {
                    if (WorldVariables.jobs.getJob(JobName.Farmer).canHeDoIt(citizen)) {
                        citizen.assignJob(WorldVariables.jobs.getJob(JobName.Farmer));
                    }
                }
                if(!isThereJob(JobName.Healer)){
                    if (WorldVariables.jobs.getJob(JobName.Healer).canHeDoIt(citizen)) {
                        citizen.assignJob(WorldVariables.jobs.getJob(JobName.Healer));
                        citizen.aiPlugins.add(new HealerPlugin(citizen));
                    }
                }
            }

            //dla noskilli
            if (citizen.job == null) {
                if (citizen.age <= 10) {
                    citizen.assignJob(WorldVariables.jobs.getJob(JobName.Child));
                } else if (citizen.sex == "female") {
                    if (citizen.wealth < 0.2) {
                        citizen.assignJob(WorldVariables.jobs.getJob(JobName.Prostitute));
                    } else {
                        citizen.assignJob(WorldVariables.jobs.getJob(JobName.Lady));
                    }
                } else if (citizen.wealth < 0.2) {
                    citizen.assignJob(WorldVariables.jobs.getJob(JobName.Beggar));
                } else {
                    citizen.assignJob(WorldVariables.jobs.getJob(JobName.Unemployed));
                }
            }
        }else{
            citizen.assignJob(WorldVariables.jobs.getJob(JobName.Lord));
            citizen.status=SocialStatus.KNIGHT;
        }
    }

    public HashMap<Resource,Integer> genDemand(Weather weather){
        demand=new HashMap<>();
        int resourceDemand=0;
        //jedzenie
        for(Citizen citizen:citizens){
            if(citizen.age>15&&citizen.sex=="male"){
                resourceDemand+=100;
            }else{
                resourceDemand+=50;
            }
        }
        demand.put(Resource.FOOD,resourceDemand);
        resourceDemand=0;
        //drewno

        switch (weather) {
            case Deepsnow:
                resourceDemand = 35 * houses.size();
                break;
            case Draught:
                resourceDemand = 5 * houses.size();
                break;
            case Lightsnow:
                resourceDemand = 25 * houses.size();
                break;
            case Blizzard:
                resourceDemand = 40 * houses.size();
                break;
            default:
                resourceDemand = 10 * houses.size();
                break;
        }
        demand.put(Resource.WOOD,resourceDemand);
        return demand;
    }

    public HashMap<Resource,Double> genPrices(){
        double price;
        double supply;
        double demand;
        double ratio;
        this.prices=new HashMap<>();
        this.prices.put(Resource.FOOD,0.0);
        this.prices.put(Resource.WOOD,0.0);

        for(Resource resource: this.demand.keySet()) {
            supply=0.;
            price=0.;
            demand = (double)(this.demand.get(resource).intValue());
            for (Citizen citizen : citizens) {
                if(citizen.resources.containsKey(resource)){
                    supply+=citizen.resources.get(resource).doubleValue();
                }
            }

            ratio=supply/demand;
            if(ratio<1)ratio*=ratio;
            if(ratio!=0) {
                price = 0.2 / ratio;
            }else{
                price =99999;
            }
            this.prices.replace(resource,price);
        }

        return prices;
    }

    public void purchaseResources(Weather weather){
        //jedzenie
        for(Citizen citizen:citizens){
            if(citizen.sex=="male"&&citizen.age>15) {
                if (citizen.wealth > prices.get(Resource.FOOD).doubleValue()) {
                    for (Citizen seller : citizens) {
                        if (seller.resources.containsKey(Resource.FOOD) && seller.resources.get(Resource.FOOD).doubleValue() > 100) {
                            citizen.fed = true;
                            seller.resources.replace(Resource.FOOD, seller.resources.get(Resource.FOOD).doubleValue() - 100);
                            seller.wealth += prices.get(Resource.FOOD).doubleValue();
                            citizen.wealth -= prices.get(Resource.FOOD).doubleValue();
                            break;
                        }
                    }
                } else {
                    citizen.fed = false;
                }
            }else {
                if (citizen.wealth > prices.get(Resource.FOOD).doubleValue() / 2) {
                    for (Citizen seller : citizens) {
                        if (seller.resources.containsKey(Resource.FOOD) && seller.resources.get(Resource.FOOD).doubleValue() > 50) {
                            citizen.fed = true;
                            seller.resources.replace(Resource.FOOD, seller.resources.get(Resource.FOOD).doubleValue() - 50);
                            seller.wealth += prices.get(Resource.FOOD).doubleValue() / 2.;
                            citizen.wealth -= prices.get(Resource.FOOD).doubleValue() / 2.;
                            break;
                        }
                    }
                } else {
                    citizen.fed = false;
                }
            }
        }
        //drewno
        for(House house:houses){
            for(Citizen seller:citizens) {
                if (seller.resources.containsKey(Resource.WOOD) && seller.resources.get(Resource.WOOD).doubleValue() > demand.get(Resource.WOOD) / houses.size()) {
                    if(house.getRichestCitizen().wealth>prices.get(Resource.WOOD).doubleValue()){
                        house.hasWood=true;
                        house.getRichestCitizen().wealth-=prices.get(Resource.WOOD).doubleValue();
                        seller.wealth += prices.get(Resource.WOOD).doubleValue();
                        seller.resources.replace(Resource.FOOD, seller.resources.get(Resource.FOOD).doubleValue() - demand.get(Resource.WOOD) / houses.size());
                    }else{
                        house.hasWood=false;
                    }
                }
            }
        }
    }

    public void writeTownMembers(){
        double moneyPool=0;
        for(Citizen citizen:citizens){
                citizen.write();
                moneyPool+=citizen.wealth;
        }
    }

    public void writeTownJobs(){
        System.out.println("Population: "+population);
        System.out.println("Houses: "+houses.size());
        for(Citizen citizen:citizens){
            System.out.print(citizen.job.name);
        }
    }

    public Town(int population){
        this.population=population;
        resources.add(Resource.LAND);
        resources.add(Resource.TREES);
        resources.add(Resource.WATER);
        infrastructures.add(Infrastructure.INN);
        genHouses();
        for(Citizen citizen:citizens){
            citizen.genRelationships(citizens);
        }
    }

}
