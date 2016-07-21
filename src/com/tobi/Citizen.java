package com.tobi;

import com.tobi.enums.SocialStatus;

import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Woronko on 2016-07-20.
 */
public class Citizen {
    static int number=0;
    int id;
    int age;
    Job ambition,job;
    int iq, strength, charisma;
    double wealth;
    String name, surname;
    Citizen mother,father;
    String sex;
    SocialStatus status;
    LinkedList<Relationship> relationships=new LinkedList<>();

    public void genStats(){
        Random random=new Random();
        NameGenerator nameGenerator=new NameGenerator();
        iq=random.nextInt(101);
        strength=random.nextInt(101);
        charisma=random.nextInt(101);
        age=random.nextInt(40);
        if(random.nextInt(2)%2==0){
            sex="male";
        }else{
            sex="female";
        }
        wealth=((1/(random.nextInt(10000)+1.))*10000.)-1;
        name=nameGenerator.generateName();
        surname=nameGenerator.generateName();
    }

    public void assignJob(Job job){
        this.job=job;
    }


    public Citizen(Job job, String sex, int age){
        ambition=new Jobs().getAmbition();
        this.job=job;
        this.sex=sex;
        this.age=age;
        genStats();
        number++;
        id=number;
    }
    public Citizen(SocialStatus status){
        ambition=new Jobs().getAmbition();
        this.status=status;
        genStats();
        number++;
        id=number;
    }
    public Citizen(Job job, Citizen mother, Citizen father,SocialStatus status){
        ambition=new Jobs().getAmbition();
        this.job=job;
        this.status=status;
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
        System.out.println(id+" "+name+" "+surname+" "+age+" "+sex+"\n"+iq+" "+strength+" "+charisma+" "+wealth+"\nAmbition: "+ambition.name);
        if(job!=null){
            System.out.println("Job: "+job.name+"\n");
        }
    }

}
