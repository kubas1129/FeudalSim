package com.tobi;

import java.util.*;

/**
 * Created by Woronko on 2016-07-20.
 */
public class Jobs {

    public LinkedList<Job> jobLinkedList=new LinkedList<>();

    public void createJobs(){
        jobLinkedList.push(new Job("Alchemist",40,5,0));
        jobLinkedList.push(new Job("Brewer",20,15,5));
        jobLinkedList.push(new Job("Blacksmith",30,40,5));
        jobLinkedList.push(new Job("Builder",10,25,0));
        jobLinkedList.push(new Job("Farmer",10,25,0));
        jobLinkedList.push(new Job("Forester",15,10,0));
        jobLinkedList.push(new Job("Fisherman",5,0,0));
        jobLinkedList.push(new Job("Hunter",20,20,0));
        jobLinkedList.push(new Job("Innkeep",20,0,0));
        jobLinkedList.push(new Job("Mayor",30,0,50));
        jobLinkedList.push(new Job("Militia",10,30,0));
        jobLinkedList.push(new Job("Miner",0,25,0));
        jobLinkedList.push(new Job("Smelter",20,5,0));
        jobLinkedList.push(new Job("Unemployed",0,0,0));
        jobLinkedList.push(new Job("Woodcutter",0,30,0));
    }

    public Job getAmbition(){
        Random random=new Random();
        return jobLinkedList.get(random.nextInt(jobLinkedList.size()));
    }

    public Job getJob(String name){
        for (Job job:jobLinkedList){
            if(job.name==name){
                return job;
            }
        }
        return null;
    }

    public Jobs(){
        createJobs();
    }
}
