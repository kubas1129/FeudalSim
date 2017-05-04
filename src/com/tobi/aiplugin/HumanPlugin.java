package com.tobi.aiplugin;

import com.tobi.Citizen;
import com.tobi.GUI.GuiSettings;
import com.tobi.WorldVariables;
import com.tobi.enums.JobName;

/**
 * Created by tobiasz on 29.11.16.
 */
public class HumanPlugin implements AIPlugin {

    public Citizen host;

    @Override
    public void action() {
        //szukaj helarea
        HealerPlugin healerPlugin;
        if(host.sickness>60){
            if(GuiSettings.debug) {
                System.out.println("Szukam Healera");
            }
            for(Citizen citizen : host.inhabitedTown.citizens){
                if(citizen.job==WorldVariables.jobs.getJob(JobName.Healer)) {
                    healerPlugin = (HealerPlugin) citizen.getPlugin(JobName.Healer);
                    if(host.wealth>healerPlugin.price&&healerPlugin.canDoJob()){
                        healerPlugin.doJob(host);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public boolean canDoJob(){
        return true;
    }

    @Override
    public void endMonth(){
        //szukaj pracy
        if(host.wealth<3&&host.age>12){
            for(Citizen citizen : host.inhabitedTown.citizens){
                if(citizen.job!=host.job) {
                    if (citizen.monthlyGain > host.monthlyGain) {
                        if (citizen.job.canHeDoIt(host)) {
                            if(GuiSettings.debug) {
                                System.out.println(citizen.monthlyGain + " " + host.monthlyGain);
                            }
                            host.assignJob(citizen.job);
                            host.changedJob=true;
                            break;
                        }
                    }
                }
            }
        }
    }

    public HumanPlugin(Citizen host){
        this.host=host;
    }

    @Override
    public JobName getPluginJob(){
        return JobName.None;
    }
}
