package com.MohaddisMedia.mohaddis.DataModels;

public class Huakm {
    String name;
    String hukam;

    public Huakm() {
    }

    public Huakm(String name, String hukam) {
        this.name = name;
        this.hukam = hukam;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHukam() {
        return hukam;
    }

    public void setHukam(String hukam) {
        this.hukam = hukam;
    }
}
