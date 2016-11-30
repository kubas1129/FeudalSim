package com.tobi.aiplugin;

import com.tobi.Citizen;
import com.tobi.enums.JobName;

/**
 * Created by tobiasz on 29.11.16.
 */
public class HealerPlugin implements AIPlugin{
    double price;
    public JobName jobName=JobName.Healer;
    Citizen host;
    int maxClients;
    int servedClients;

    @Override
    public void action(){
        host.sickness-=10;
        if(host.sickness<0){
            host.sickness=0;
        }
    }

    public void doJob(Citizen citizen){
        citizen.sickness-=host.iq;
        if(citizen.sickness<0)citizen.sickness=0;
        citizen.wealth-=price;
        host.wealth+=price;
        servedClients++;
        System.out.println("Heal"+price+" "+servedClients);
    }

    @Override
    public boolean canDoJob(){
        if(servedClients<maxClients){
            return true;
        }else{
            return false;
        }
    }

    public HealerPlugin(Citizen host){
        this.host=host;
        maxClients=10;
        servedClients=0;
        price=0.01*host.iq;
    }

    @Override
    public void endMonth(){
        price=0.01*host.iq;
        price*=(double)(servedClients+1)/maxClients;
        servedClients=0;
    }

    @Override
    public JobName getPluginJob(){
        return JobName.Healer;
    }
}
