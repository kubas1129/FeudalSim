package com.tobi;

/**
 * Created by Woronko on 2016-07-20.
 */
public class Relationship {
    public int value;
    public Citizen target;
    public String type;

    public Relationship(){

    }
    public Relationship(int value, Citizen target, String type){
        this.target=target;
        this.value=value;
        this.type=type;
    }
}
