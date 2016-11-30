package com.tobi;

import com.tobi.enums.Resource;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by tobiasz on 21.07.16.
 */
public class House{
    boolean hasWood;
    public LinkedList<Citizen> household = new LinkedList<>();
    public int householdSize;
    public House(int houseHoldSize){
        this.householdSize=houseHoldSize;
    }
    public House(){
        this.householdSize=0;
    }
    public void addHabitant(Citizen member){
        household.push(member);
    }
    public Citizen getRichestCitizen(){
        Citizen richest=household.getFirst();
        for(Citizen citizen:household){
            if(citizen.wealth>richest.wealth){
                richest=citizen;
            }
        }
        return richest;
    }
}
