package com.tobi;

import com.tobi.enums.JobName;
import com.tobi.enums.Resource;

import java.util.*;

/**
 * Created by Woronko on 2016-07-20.
 */
public class Jobs {

    public LinkedList<Job> jobLinkedList=new LinkedList<>();

    public void createJobs(){
        //jobLinkedList.push(new Job("Alchemist",40,5,0));
        jobLinkedList.push(new Job(JobName.Beggar,0,0,0, Resource.NONE,Resource.MONEY,false));
        jobLinkedList.push(new Job(JobName.Healer,50,0,0, Resource.NONE,Resource.MONEY,false));
        //jobLinkedList.push(new Job("Brewer",20,15,5));
        //jobLinkedList.push(new Job("Blacksmith",30,40,0));
        //jobLinkedList.push(new Job("Builder",10,25,0));
        jobLinkedList.push(new Job(JobName.Child,0,0,0,Resource.NONE,Resource.NONE,false));
        jobLinkedList.push(new Job(JobName.Farmer,0,15,0,Resource.LAND,Resource.FOOD,true));
        //jobLinkedList.push(new Job("Forester",15,10,0));
        jobLinkedList.push(new Job(JobName.Fisherman,15,0,0,Resource.WATER,Resource.FOOD,false));
        jobLinkedList.push(new Job(JobName.Hunter,20,20,0,Resource.TREES,Resource.FOOD,false));
        jobLinkedList.push(new Job(JobName.Innkeep,20,0,0,Resource.NONE,Resource.MONEY,false));
        jobLinkedList.push(new Job(JobName.Lady,0,0,0,Resource.NONE,Resource.NONE,false));
        jobLinkedList.push(new Job(JobName.Lord,0,0,0,Resource.NONE,Resource.NONE,false));
        //jobLinkedList.push(new Job("Mayor",30,0,50));
        //jobLinkedList.push(new Job("Militia",10,30,0));
        //jobLinkedList.push(new Job("Miner",0,25,0));
        jobLinkedList.push(new Job(JobName.Prostitute,0,0,0,Resource.NONE,Resource.MONEY,false));
        //jobLinkedList.push(new Job("Smelter",20,5,0));
        jobLinkedList.push(new Job(JobName.Unemployed,0,0,0,Resource.NONE,Resource.NONE,false));
        //jobLinkedList.push(new Job("Widow",0,0,0));
        //jobLinkedList.push(new Job("Wife",0,0,0));
        jobLinkedList.push(new Job(JobName.Woodcutter,0,30,0,Resource.TREES,Resource.WOOD,true));
    }

    public Job getAmbition(){
        Random random=new Random();
        return jobLinkedList.get(random.nextInt(jobLinkedList.size()));
    }

    public Job getJob(JobName name){
        for (Job job:jobLinkedList){
            if(job.jobName==name){
                return job;
            }
        }
        return null;
    }

    public Jobs(){
        createJobs();
    }
}
