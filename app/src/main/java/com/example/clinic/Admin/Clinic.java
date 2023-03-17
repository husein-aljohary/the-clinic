package com.example.clinic.Admin;

import android.net.Uri;

public class Clinic {
    String name;
    Uri ImageUrl;

    Clinic(String name, Uri imageUrl){
        this.name = name;
        this.ImageUrl = imageUrl;

    }

    public Uri getImageUrl() {
        return ImageUrl;
    }

    private void setImageUrl(Uri imageUrl) {
        ImageUrl = imageUrl;
    }

    public Clinic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }



}
