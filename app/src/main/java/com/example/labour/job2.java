package com.example.labour;

public class job2 {

    private String jobName,jobDiscription,jobLocation,jobPay,JobRequirements;

    public job2(String jobName, String jobDiscription, String jobLocation, String jobPay, String JobRequirements) {


        this.jobName= jobName;
        this.jobDiscription= jobDiscription;
        this.jobLocation= jobLocation;
        this.jobPay= jobPay;
        this.JobRequirements=JobRequirements;
    }

    public String getJobName(){return  jobName;}
    public void setJobName(){this.jobName=jobName;}

    public String getJobDiscription(){return  jobDiscription;}
    public void setJobDiscription(){this.jobDiscription=jobDiscription;}

    public String getJobLocation(){return  jobLocation;}
    public void setJobLocation(){this.jobLocation=jobLocation;}

    public String getJobPay(){return  jobPay;}
    public void setJobPay(){this.jobName=jobPay;}

    public String getJobRequirements(){return JobRequirements;}
    public void setJobRequirements(){this.JobRequirements=JobRequirements;}

    public job2(String jobName,String jobDiscription,String jobLocation,String jobRequirements){

        this.jobName=jobName;
        this.jobDiscription=jobDiscription;
        this.jobLocation=jobLocation;
        this.JobRequirements=jobRequirements;
    }
    public job2(){

    }
    public String toString(){
        String str= "\nSubject: "+jobName+
                "\nDiscription: "+jobDiscription+
                "\nLocation: "+jobLocation+
                "\nRequirements: "+JobRequirements;
        return str;
    }
}

