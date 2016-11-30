package com.tobi;

import static java.lang.Thread.sleep;

public class Main {


    public static void main(String[] args) {
        WorldVariables worldVariables=new WorldVariables();
        Town town=new Town(60);
        town.writeTownJobs();
        Month month=new Month(town);
        while(true){
            try {
                sleep(100);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            month.nextMonth();
            System.out.println(town.citizens.size());
        }
    }
}
