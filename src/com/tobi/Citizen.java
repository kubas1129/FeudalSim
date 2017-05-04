package com.tobi;

import com.tobi.GUI.GuiSettings;
import com.tobi.GUI.Logger;
import com.tobi.aiplugin.AIPlugin;
import com.tobi.aiplugin.HealerPlugin;
import com.tobi.aiplugin.HumanPlugin;
import com.tobi.enums.JobName;
import com.tobi.enums.Resource;
import com.tobi.enums.SocialStatus;
import com.tobi.enums.Traits;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;

/**
 * Created by Woronko on 2016-07-20.
 */
public class Citizen {
    static int number=0;
    public int id;
    public int monthsLeft;
    public double age;
    public Job ambition,job;
    public int iq, strength, charisma;
    public double wealth;
    public double monthlyGain;
    public  boolean fed;
    public  boolean alive;
    public boolean changedJob;
    public double sickness;
    public String name, surname;
    public Citizen mother,father, partner, expartner;
    public String sex;
    public SocialStatus status;
    public Town inhabitedTown;
    public LinkedList<Relationship> relationships=new LinkedList<>();
    public LinkedList<Citizen> children=new LinkedList<>();
    public HashMap<Resource,Double> resources=new HashMap<>();
    public LinkedList<AIPlugin> aiPlugins=new LinkedList<>();

    public void die(){
        double wealthPart;
        int aliveChildren=0;
        alive=false;
        if(partner!=null&&partner.alive){
            partner.wealth+=wealth;
            wealth=0;
            partner.partner=null;
            partner.expartner=this;
        }else if(!children.isEmpty()){
            for(Citizen citizen:children){
                if(citizen.alive)aliveChildren++;
            }
            wealthPart=wealth/aliveChildren;
            for(Citizen citizen:children){
                if(citizen.alive)citizen.wealth+=wealthPart;
            }
        }
        deathLog();
    }

    private void deathLog() {
        if(GuiSettings.deaths) {
            Logger.addToLog(name + " " + surname + " " + wealth + " aged " + (int) age + " passed away this month.");
            if(wealth==0.0) {
                String log = "He left all that he had to: ";
                if(partner!=null&&partner.alive){
                    log+=partner.name;
                }else{
                    for(Citizen citizen:children){
                        if(citizen.alive)log+=citizen.name+" ";
                    }
                }
                Logger.addToLog(log);
            }else{
                Logger.addToLog("He took whatever he possessed to his grave.");
            }
        }
    }

    public void fallSick(){
        Random random=new Random();
        sickness+=((double)random.nextInt(10)-5.)+(age/10.);
        if(sickness>80)monthsLeft--;
    }

    public void recalibratePlugins(){
        if(changedJob) {
            aiPlugins=new LinkedList<>();
            aiPlugins.add(new HumanPlugin(this));
            switch (job.jobName) {
                case Healer:
                    aiPlugins.add(new HealerPlugin(this));
                    break;
            }
            changedJob=false;
        }
    }

    public void genRelationships(LinkedList<Citizen> citizens){
        for(Citizen citizen:citizens){
            relationships.add(new Relationship(new Random().nextInt(100),citizen));

        }
    }

    public void lookForRelationship(LinkedList<Citizen> citizens){
        for(Citizen citizen:citizens){
            if(citizen!=this&&citizen.age>12&&this.age>12&&this.partner==null&&citizen.partner==null&&(citizen.age-this.age<11&&citizen.age-this.age>-11)){
                if((this.sex=="male"&&citizen.sex=="female")){
                    citizen.askToMarry(this);
                }
            }
        }
    }

    public void askToMarry(Citizen citizen){
        for(Relationship relationship:relationships){
            if(relationship.target==citizen){
                if(relationship.value>60 && citizen.wealth>0.2 && (citizen.job!=new Jobs().getJob(JobName.Unemployed) || citizen.job!=new Jobs().getJob(JobName.Beggar))){
                    marry(citizen);
                    citizen.marry(this);
                }
            }
        }
    }

    public void marry(Citizen citizen){
        if(sex!="male"){
            this.surname=citizen.surname;
        }
        partner=citizen;
        if(GuiSettings.marraiges) {
            Logger.addToLog(citizen.name + " and " + this.name + " are both now " + surname);
        }
    }

    public void genStats(){
        Random random=new Random();
        NameGenerator nameGenerator=new NameGenerator(WorldVariables.alphabet);
        iq=random.nextInt(101);
        sickness=(double)random.nextInt(101);
        strength=random.nextInt(101);
        charisma=random.nextInt(101);
        age=(double)random.nextInt(28)+12.;
        if(random.nextInt(2)%2==0){
            sex="male";
        }else{
            sex="female";
        }
        wealth=((1./(random.nextInt(10000)+1.))*10000.)-1.;
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
        alive=true;
        genStats();
        number++;
        id=number;
    }
    public Citizen(SocialStatus status, Town town){
        ambition=new Jobs().getAmbition();
        this.status=status;
        alive=true;
        genStats();
        number++;
        id=number;
        this.inhabitedTown=town;
        aiPlugins.add(new HumanPlugin(this));
        monthsLeft=new Random().nextInt(360)+480;
        changedJob=false;
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

    public AIPlugin getPlugin(JobName jobName){
        for(AIPlugin aiPlugin:aiPlugins){
            if(aiPlugin.getPluginJob()==jobName){
                return aiPlugin;
            }
        }
        return null;
    }

}
