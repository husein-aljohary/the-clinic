package com.example.clinic.Admin;

public class parkingspace   {
    private String id;
    private boolean status=true;

    public parkingspace() {
    }

    public String getId() {
        return id;
    }

    public String setId(String id) {
        this.id = id;
        return id;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
