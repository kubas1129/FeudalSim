package com.tobi.aiplugin;

import com.tobi.Citizen;
import com.tobi.WorldVariables;
import com.tobi.enums.JobName;

/**
 * Created by tobiasz on 29.11.16.
 */
public class HumanPlugin implements AIPlugin {

    Citizen host;

    @Override
    public void action() {
        //szukaj helarea
        HealerPlugin healerPlugin;
        if(host.sickness>60){
            System.out.println("szukam healera");
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

    }

    public HumanPlugin(Citizen host){
        this.host=host;
    }

    @Override
    public JobName getPluginJob(){
        return JobName.None;
    }
}
