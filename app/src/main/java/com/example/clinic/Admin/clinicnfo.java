package com.example.clinic.Admin;

public class clinicnfo {
    private String city;
    private  String clinicname;
    private String departmentsnumber;
    ////
    private parkingspace space;



    public clinicnfo( ) {

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getclinicname() {
        return clinicname;
    }

    public void setclinicname(String mall) {
        this.clinicname = clinicname;
    }

    public String getdepartmentsnumber() {
        return departmentsnumber;
    }

    public void setdepartmentsnumber(String parksnumber) {
        this.departmentsnumber = parksnumber;
    }

    /////////////////////////////
    public parkingspace getSpace() {
        return space;
    }
    public void setSpace(parkingspace space) {
        this.space = space;
    }


}
