package com.MohaddisMedia.mohaddis.DataModels;


import java.io.Serializable;

public class MasadirDataModel  implements Serializable {

    int id;
    String masadirNameArabic;
    String masadirNameUrdu;

    public MasadirDataModel(int id, String masadirNameArabic, String masadirNameUrdu) {
        this.id = id;
        this.masadirNameArabic = masadirNameArabic;
        this.masadirNameUrdu = masadirNameUrdu;
    }

    public MasadirDataModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMasadirNameArabic() {
        return masadirNameArabic;
    }

    public void setMasadirNameArabic(String masadirNameArabic) {
        this.masadirNameArabic = masadirNameArabic;
    }

    public String getMasadirNameUrdu() {
        return masadirNameUrdu;
    }

    public void setMasadirNameUrdu(String masadirNameUrdu) {
        this.masadirNameUrdu = masadirNameUrdu;
    }
}

