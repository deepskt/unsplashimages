package com.deepak.mobikwikimage.model;

import java.util.HashMap;

public class Photo {
    private String id;
    private String alt_description;
    private byte[] imageData;
    private HashMap<String,String> urls;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getAlt_description() {
        return alt_description;
    }

    public void setAlt_description(String alt_description) {
        this.alt_description = alt_description;
    }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public HashMap<String, String> getUrls() {
        return urls;
    }

    public void setUrls(HashMap<String, String> urls) {
        this.urls = urls;
    }
}
