package com.tobi;

public class Main {

    public static void main(String[] args) {
        Citizen citizenOne=new Citizen(new Job("Unemployed",0,0,0));
        citizenOne.write();
    }
}
