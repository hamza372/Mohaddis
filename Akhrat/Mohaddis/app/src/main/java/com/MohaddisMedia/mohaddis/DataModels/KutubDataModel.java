package com.MohaddisMedia.mohaddis.DataModels;

import java.io.Serializable;

public class KutubDataModel extends MasadirDataModel implements Serializable {
    int id;
    int masadirId;
    String kutubNameUrdu;
    String kutubNameArabic;
    String kutubTamheedUrdu;
    String kutubTamheedArabic;
    float kitab_hiddenId;

    public float getKitab_hiddenId() {
        return kitab_hiddenId;
    }

    public void setKitab_hiddenId(float kitab_hiddenId) {
        this.kitab_hiddenId = kitab_hiddenId;
    }

    public String getKutubTamheedUrdu() {
        return kutubTamheedUrdu;
    }

    public String getKutubTamheedArabic() {
        return kutubTamheedArabic;
    }

    public void setKutubTamheedArabic(String kutubTamheedArabic) {
        this.kutubTamheedArabic = kutubTamheedArabic;
    }

    public void setKutubTamheedUrdu(String kutubTamheedUrdu) {
        this.kutubTamheedUrdu = kutubTamheedUrdu;
    }

    public KutubDataModel() {
    }

    public KutubDataModel(int id, String masadirNameArabic, String masadirNameUrdu) {
        super(id, masadirNameArabic, masadirNameUrdu);
    }

    public KutubDataModel(int id, String masadirNameArabic, String masadirNameUrdu, int id1, int masadirId, String kutubNameUrdu, String kutubNameArabic) {
        super(id, masadirNameArabic, masadirNameUrdu);
        this.id = id1;
        this.masadirId = masadirId;
        this.kutubNameUrdu = kutubNameUrdu;
        this.kutubNameArabic = kutubNameArabic;
    }

    public KutubDataModel(int id, int masadirId, String kutubNameUrdu, String kutubNameArabic) {
        this.id = id;
        this.masadirId = masadirId;
        this.kutubNameUrdu = kutubNameUrdu;
        this.kutubNameArabic = kutubNameArabic;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public int getMasadirId() {
        return masadirId;
    }

    public void setMasadirId(int masadirId) {
        this.masadirId = masadirId;
    }

    public String getKutubNameUrdu() {
        return kutubNameUrdu;
    }

    public void setKutubNameUrdu(String kutubNameUrdu) {
        if(kutubNameUrdu != null && kutubNameUrdu.length()>7 && kutubNameUrdu.startsWith("<p>")) {
            this.kutubNameUrdu =kutubNameUrdu.substring(3, kutubNameUrdu.length() - 4);
        }else{
            this.kutubNameUrdu = kutubNameUrdu;
        }
    }

    public String getKutubNameArabic() {
        return kutubNameArabic;
    }

    public void setKutubNameArabic(String kutubNameArabic) {
        if(kutubNameArabic != null && kutubNameArabic.length()>7 && kutubNameArabic.startsWith("<p>")) {
            this.kutubNameArabic =kutubNameArabic.substring(3, kutubNameArabic.length() - 4);
        }else{
            this.kutubNameArabic = kutubNameArabic;
        }
    }
}