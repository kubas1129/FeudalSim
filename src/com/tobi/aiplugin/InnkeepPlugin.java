package com.tobi.aiplugin;

import com.tobi.enums.JobName;

/**
 * Created by tobiw on 01-Dec-16.
 */
public class InnkeepPlugin implements AIPlugin {
    @Override
    public void action() {

    }

    @Override
    public boolean canDoJob() {
        return false;
    }

    @Override
    public void endMonth() {

    }

    @Override
    public JobName getPluginJob() {
        return null;
    }
}
