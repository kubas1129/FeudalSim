package com.tobi;

import java.util.LinkedList;

/**
 * Created by tobiasz on 21.07.16.
 */
public class House{
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
}
