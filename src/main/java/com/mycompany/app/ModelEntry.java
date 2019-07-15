package com.mycompany.app;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ModelEntry {

    private String nameCompany;
    private Calendar startTime;
    private Calendar finishTime;

    public ModelEntry(String nameCompany, Calendar startTime, Calendar finishTime) {
        this.nameCompany = nameCompany;
        this.startTime = startTime;
        this.finishTime = finishTime;
    }

    public String getNameCompany() {
        return nameCompany;
    }

    public void setNameCompany(String nameCompany) {
        this.nameCompany = nameCompany;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public void setStartTime(Calendar startTime) {
        this.startTime = startTime;
    }

    public Calendar getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Calendar finishTime) {
        this.finishTime = finishTime;
    }

    @Override
    public String toString() {
        String entryS =  new SimpleDateFormat("HH:mm").format(startTime.getTime());
        String entryF =  new SimpleDateFormat("HH:mm").format(finishTime.getTime());
        return nameCompany  + " " + entryS + " " + entryF;
    }


}
