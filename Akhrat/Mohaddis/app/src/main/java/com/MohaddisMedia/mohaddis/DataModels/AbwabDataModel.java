package com.MohaddisMedia.mohaddis.DataModels;

import java.io.Serializable;

public class AbwabDataModel extends KutubDataModel implements Serializable {
    int kitaabId;
    int id;
    String babNameUrdu;
    String babNameArabic;
    String babTamheedUrdu;
    String babTamheedArabic;
    float bab_hidden_id;

    public float getBab_hidden_id() {
        return bab_hidden_id;
    }

    public void setBab_hidden_id(float bab_hidden_id) {
        this.bab_hidden_id = bab_hidden_id;
    }

    public AbwabDataModel() {
    }

    public AbwabDataModel(int id, String masadirNameArabic, String masadirNameUrdu) {
        super(id, masadirNameArabic, masadirNameUrdu);
    }

    public AbwabDataModel(int id, String masadirNameArabic, String masadirNameUrdu, int id1, int masadirId, String kutubNameUrdu, String kutubNameArabic) {
        super(id, masadirNameArabic, masadirNameUrdu, id1, masadirId, kutubNameUrdu, kutubNameArabic);
    }

    public String getBabTamheedUrdu() {
        return babTamheedUrdu;
    }

    public void setBabTamheedUrdu(String babTamheedUrdu) {
        this.babTamheedUrdu = babTamheedUrdu;
    }

    public String getBabTamheedArabic() {
        return babTamheedArabic;
    }

    public void setBabTamheedArabic(String babTamheedArabic) {
        this.babTamheedArabic = babTamheedArabic;
    }

    public AbwabDataModel(int id, String masadirNameArabic, String masadirNameUrdu, int id1, int masadirId, String kutubNameUrdu, String kutubNameArabic, int kitaabId, int id2, String babNameUrdu, String babNameArabic) {
        super(id, masadirNameArabic, masadirNameUrdu, id1, masadirId, kutubNameUrdu, kutubNameArabic);
        this.kitaabId = kitaabId;
        this.id = id2;
        this.babNameUrdu = babNameUrdu;
        this.babNameArabic = babNameArabic;
    }

    public AbwabDataModel(int id, int masadirId, String kutubNameUrdu, String kutubNameArabic, int kitaabId, int id1, String babNameUrdu, String babNameArabic) {
        super(id, masadirId, kutubNameUrdu, kutubNameArabic);
        this.kitaabId = kitaabId;
        this.id = id1;
        this.babNameUrdu = babNameUrdu;
        this.babNameArabic = babNameArabic;
    }

    public AbwabDataModel(int id, int masadirId, String kutubNameUrdu, String kutubNameArabic) {
        super(id, masadirId, kutubNameUrdu, kutubNameArabic);
    }

    public int getKitaabId() {
        return kitaabId;
    }

    public void setKitaabId(int kitaabId) {
        this.kitaabId = kitaabId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getBabNameUrdu() {
        return babNameUrdu;
    }

    public void setBabNameUrdu(String babNameUrdu) {
        this.babNameUrdu = babNameUrdu;
    }

    public String getBabNameArabic() {
        return babNameArabic;
    }

    public void setBabNameArabic(String babNameArabic) {
        this.babNameArabic = babNameArabic;
    }
}
