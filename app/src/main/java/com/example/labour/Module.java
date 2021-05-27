package com.example.labour;

import android.app.Application;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class Module extends Application {

    public ArrayList<String> garrList =new ArrayList<>();
    public ArrayAdapter<String> garrAdp;
    public String gvalue_assign;
    public String gvalue_jobName;

    public String getGvalue_assign(){return gvalue_assign;}
    public void setGvalue_assign(String gvalue_assign){this.gvalue_assign=gvalue_assign;}

    public String getGvalue_Name(){return gvalue_jobName;}
    public void setGvalue_Name(String gvalue_jobName){this.gvalue_jobName = gvalue_jobName;}
}
