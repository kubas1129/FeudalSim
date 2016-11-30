package com.tobi;

import com.tobi.enums.Resource;
import com.tobi.enums.Weather;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by tobiasz on 22.07.16.
 */
public class Month {
    Town town;
    Weather weather;
    Random random;
    int monthNumber;

    public Month(Town town){
        monthNumber=0;
        random=new Random();
        this.town=town;
        setWeather();
        produce();
        relationsAndAge();
        end();
    }

    public void nextMonth(){
        monthNumber++;
        setWeather();
        produce();
        relationsAndAge();
        end();
        System.out.println("Month "+monthNumber);
    }

    public void setWeather(){
        weather=Weather.values()[random.nextInt(Weather.values().length)];
        if(random.nextInt(2)%2==0){
            weather=Weather.Cloudy;
        }
        System.out.println(weather.toString());
    }

    public void end(){
        LinkedList<Citizen> diers=new LinkedList<>();
        for(Citizen citizen:town.citizens){
            if((!citizen.fed)||citizen.sickness>=100){
                citizen.die();
                town.citizenDeath(citizen);
                diers.add(citizen);
            }
        }
        for(Citizen citizen:diers) {
            town.citizens.remove(citizen);
        }
    }

    public void produce(){
        for (Citizen citizen: town.citizens) {
            citizen.job.produce(weather, citizen);
        }
        town.genDemand(weather);
        town.genPrices();
        town.purchaseResources(weather);
        System.out.println(town.prices.get(Resource.FOOD).doubleValue());
        town.executeAiPlugins();
    }

    public void relationsAndAge(){
        for(Citizen citizen: town.citizens){
            citizen.lookForRelationship(town.citizens);
            citizen.age+=1./12.;
            citizen.fallSick();
        }
    }

}
