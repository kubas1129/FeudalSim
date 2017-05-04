package com.tobi.GUI;

/**
 * Created by tobiw on 04.05.2017.
 */
public class Logger {

    public static String log;

    public static void addToLog(Object logToAdd){
        if(log!=null){
            log+=logToAdd+"\n";
        }else{
            log="\n";
            log+=logToAdd+"\n";
        }
        System.out.println(logToAdd);
    }
}
