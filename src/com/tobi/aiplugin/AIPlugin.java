package com.tobi.aiplugin;
import com.tobi.Citizen;
import com.tobi.enums.JobName;

/**
 * Created by tobiasz on 29.11.16.
 */
public interface AIPlugin {
    public void action();
    public boolean canDoJob();
    public void endMonth();
    public JobName getPluginJob();
}