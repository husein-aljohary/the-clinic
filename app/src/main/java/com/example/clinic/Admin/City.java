package com.example.clinic.Admin;

import android.net.Uri;

public class City {

    String name;
    Uri ImageUrl;

    City(String name, Uri imageUrl){
        this.name = name;
        this.ImageUrl = imageUrl;

    }

    public Uri getImageUrl() {
        return ImageUrl;
    }

    private void setImageUrl(Uri imageUrl) {
        ImageUrl = imageUrl;
    }

    public City(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    }

