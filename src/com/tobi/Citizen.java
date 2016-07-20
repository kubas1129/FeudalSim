package com.tobi;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Woronko on 2016-07-20.
 */
public class Citizen {
    static int number=0;
    int id;
    Job ambition,job;
    int iq, strength, charisma;
    double wealth;
    String name, surname;
    Citizen mother,father;
    LinkedList<Relationship> relationships=new LinkedList<>();

    public void genStats(){
        Random random=new Random();
        NameGenerator nameGenerator=new NameGenerator();
        iq=random.nextInt(101);
        strength=random.nextInt(101);
        charisma=random.nextInt(101);
        wealth=((1/(random.nextInt(10000)+1.))*10000.)-1;
        name=nameGenerator.generateName();
        surname=nameGenerator.generateName();
    }


    public Citizen(Job job){
        ambition=new Jobs().getAmbition();
        this.job=job;
        genStats();
        number++;
        id=number;
    }
    public Citizen(Job job, Citizen mother, Citizen father){
        ambition=new Jobs().getAmbition();
        this.job=job;
        this.mother=mother;
        this.father=father;
        relationships.push(new Relationship(100,mother,"family"));
        relationships.push(new Relationship(100,father,"family"));
        genStats();
        number++;
        id=number;
    }

    public Citizen(){
    }

    public void write(){
        System.out.println(id+" "+name+" "+surname+"\n"+iq+" "+strength+" "+charisma+" "+wealth+"\nAmbition: "+ambition.name+"\nJob: "+job.name);
    }

}
