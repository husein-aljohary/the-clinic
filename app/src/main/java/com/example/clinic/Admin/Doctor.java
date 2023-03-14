package com.example.clinic.Admin;

import android.net.Uri;

public class Doctor {
    String name;
    String department;
    String starttime;
    String endtime;
    Uri ImageUrl;
    String appointments;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Uri getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(Uri imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getAppointments() {
        return appointments;
    }

    public void setAppointments(String appointments) {
        this.appointments = appointments;
    }

    Doctor(String name, Uri imageUrl){
        this.name = name;
        this.ImageUrl = imageUrl;

    }

    Doctor(String name,String department,String starttime,String endtime) {
        this.name = name;
        this.department=department;
        this.starttime=starttime;
        this.endtime=endtime;
    }
    Doctor(String appointments){
        this.appointments=appointments;
    }



}
