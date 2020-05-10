package com.MohaddisMedia.mohaddis.DataModels;

import android.content.SharedPreferences;

import com.MohaddisMedia.mohaddis.Constants;
import com.MohaddisMedia.mohaddis.Utilites;

import java.io.Serializable;

public class HadeesDataModel extends AbwabDataModel implements Serializable {
    int babId;
    int id;
    float hadeesNo;
    String tarjumBabUrdu;
    String textHadeesUrdu;
    String textHadeesArabic;
    String hadessHasiaText;

    //TODO qoli feli taqreri
    String HadithTypeAtraaf;
    String HadithTypeRawaat;
    String HadithTypeQFT;

    //TODO main hukam
    String HadithHukamAjmali = "HadithHukamAjmali";
    String HadithHukamTafseeli = "HadithHukamTafseeli";

    //TODO mozu
    String HadithMouzuhArabic;
    String HadithMouzuhUrdu;
    String HadithHukamTafseeliArabic;

    //TODO taraqeem
    float HadeesNumberTOne;
    float HadeesNumberTTwo;
    float HadeesNumberTThree;
    float HadeesNumberTFour;
    float HadeesNumberTFive;
    float HadeesNumberTSix;
    float HadeesNumberTSeven;
    float HadeesNumberTEight;
    float HadeesNumberTNine;
    float HadeesNumberTTen;

    //TODO Hukam
    String HadithHukamAjmaliOne;
    String HadithHukamAjmaliTwo;
    String HadithHukamAjmaliThree;
    String HadithHukamAjmaliFour;
    String HadithHukamAjmaliFive;
    String HadithHukamAjmaliSix;
    String HadithHukamAjmaliSeven;
    String HadithHukamAjmaliEight;
    String HadithHukamAjmaliNine;
    String HadithHukamAjmaliTen;


    //TODO Hasia
    String HadithHashiaTextOne = "HadithHashiaTextOne";
    String HadithHashiaTextTwo= "HadithHashiaTextTwo";
    String HadithHashiaTextThree = "HadithHashiaTextThree";
    String HadithHashiaTextFour = "HadithHashiaTextFour";
    String HadithHashiaTextFive = "HadithHashiaTextFive";
    String HadithHashiaTextSix = "HadithHashiaTextSix";
    String HadithHashiaTextSeven = "HadithHashiaTextSeven";
    String HadithHashiaTextEight = "HadithHashiaTextEight";
    String HadithHashiaTextNine = "HadithHashiaTextNine";
    String HadithHashiaTextTen = "HadithHashiaTextTen";

    //TODO Tarjama
    String HadithUrduText = "HadithUrduText";
    String HadithUrduTextOne = "HadithUrduTextOne";
    String HadithUrduTextTwo = "HadithUrduTextTwo";
    String HadithUrduTextThree = "HadithUrduTextThreew";
    String HadithUrduTextFour = "HadithUrduTextFour";
    String HadithUrduTextFive = "HadithUrduTextFive";
    String HadithUrduTextSix = "HadithUrduTextSix";
    String HadithUrduTextSeven = "HadithUrduTextSeven";
    String HadithUrduTextEight = "HadithUrduTextEight";
    String HadithUrduTextNine = "HadithUrduTextNine";
    String HadithUrduTextTen = "HadithUrduTextTen";

    public String getTarjumBabUrdu() {
        return tarjumBabUrdu;
    }

    public void setTarjumBabUrdu(String tarjumBabUrdu) {
        this.tarjumBabUrdu = tarjumBabUrdu;
    }

    public String getHadithUrduText() {
        return HadithUrduText;
    }

    public void setHadithUrduText(String hadithUrduText) {
        HadithUrduText = hadithUrduText;
    }

    public HadeesDataModel() {
    }

    public int getBabId() {
        return babId;
    }

    public void setBabId(int babId) {
        this.babId = babId;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public float getHadeesNo() {
        return hadeesNo;
    }

    public void setHadeesNo(float hadeesNo) {
        this.hadeesNo =  hadeesNo;
    }

    public String getTextHadeesUrdu() {
        return textHadeesUrdu;
    }

    public void setTextHadeesUrdu(String textHadeesUrdu) {
        this.textHadeesUrdu = textHadeesUrdu;
    }

    public String getTextHadeesArabic() {
        return textHadeesArabic;
    }

    public void setTextHadeesArabic(String textHadeesArabic) {
        this.textHadeesArabic = textHadeesArabic;
    }

    public String getHadessHasiaText() {
        return hadessHasiaText;
    }

    public void setHadessHasiaText(String hadessHasiaText) {
        this.hadessHasiaText = hadessHasiaText;
    }

    public String getHadithTypeAtraaf() {
        return HadithTypeAtraaf;
    }

    public void setHadithTypeAtraaf(String hadithTypeAtraaf) {
        HadithTypeAtraaf = hadithTypeAtraaf;
    }

    public String getHadithTypeRawaat() {
        return HadithTypeRawaat;
    }

    public void setHadithTypeRawaat(String hadithTypeRawaat) {
        HadithTypeRawaat = hadithTypeRawaat;
    }

    public String getHadithTypeQFT() {
        return HadithTypeQFT;
    }

    public void setHadithTypeQFT(String hadithTypeQFT) {
        HadithTypeQFT = hadithTypeQFT;
    }

    public String getHadithHukamAjmali() {
        return HadithHukamAjmali;
    }

    public void setHadithHukamAjmali(String hadithHukamAjmali) {
        HadithHukamAjmali = hadithHukamAjmali;
    }

    public String getHadithHukamTafseeli() {
        return HadithHukamTafseeli;
    }

    public void setHadithHukamTafseeli(String hadithHukamTafseeli) {
        HadithHukamTafseeli = hadithHukamTafseeli;
    }

    public String getHadithMouzuhArabic() {
        return HadithMouzuhArabic;
    }

    public void setHadithMouzuhArabic(String hadithMouzuhArabic) {
        HadithMouzuhArabic = hadithMouzuhArabic;
    }

    public String getHadithMouzuhUrdu() {
        return HadithMouzuhUrdu;
    }

    public void setHadithMouzuhUrdu(String hadithMouzuhUrdu) {
        HadithMouzuhUrdu = hadithMouzuhUrdu;
    }

    public String getHadithHukamTafseeliArabic() {
        return HadithHukamTafseeliArabic;
    }

    public void setHadithHukamTafseeliArabic(String hadithHukamTafseeliArabic) {
        HadithHukamTafseeliArabic = hadithHukamTafseeliArabic;
    }

    public float getHadeesNumberTOne() {
        return HadeesNumberTOne;
    }

    public void setHadeesNumberTOne(float hadeesNumberTOne) {
        HadeesNumberTOne = hadeesNumberTOne;
    }

    public float getHadeesNumberTTwo() {
        return HadeesNumberTTwo;
    }

    public void setHadeesNumberTTwo(float hadeesNumberTTwo) {
        HadeesNumberTTwo = hadeesNumberTTwo;
    }

    public float getHadeesNumberTThree() {
        return HadeesNumberTThree;
    }

    public void setHadeesNumberTThree(float hadeesNumberTThree) {
        HadeesNumberTThree = hadeesNumberTThree;
    }

    public float getHadeesNumberTFour() {
        return HadeesNumberTFour;
    }

    public void setHadeesNumberTFour(float hadeesNumberTFour) {
        HadeesNumberTFour = hadeesNumberTFour;
    }

    public float getHadeesNumberTFive() {
        return HadeesNumberTFive;
    }

    public void setHadeesNumberTFive(float hadeesNumberTFive) {
        HadeesNumberTFive = hadeesNumberTFive;
    }

    public float getHadeesNumberTSix() {
        return HadeesNumberTSix;
    }

    public void setHadeesNumberTSix(float hadeesNumberTSix) {
        HadeesNumberTSix = hadeesNumberTSix;
    }

    public float getHadeesNumberTSeven() {
        return HadeesNumberTSeven;
    }

    public void setHadeesNumberTSeven(float hadeesNumberTSeven) {
        HadeesNumberTSeven = hadeesNumberTSeven;
    }

    public float getHadeesNumberTEight() {
        return HadeesNumberTEight;
    }

    public void setHadeesNumberTEight(float hadeesNumberTEight) {
        HadeesNumberTEight = hadeesNumberTEight;
    }

    public float getHadeesNumberTNine() {
        return HadeesNumberTNine;
    }

    public void setHadeesNumberTNine(float hadeesNumberTNine) {
        HadeesNumberTNine = hadeesNumberTNine;
    }

    public float getHadeesNumberTTen() {
        return HadeesNumberTTen;
    }

    public void setHadeesNumberTTen(float hadeesNumberTTen) {
        HadeesNumberTTen = hadeesNumberTTen;
    }



    public String getHadithHukamAjmaliOne() {
        return HadithHukamAjmaliOne;
    }

    public void setHadithHukamAjmaliOne(String hadithHukamAjmaliOne) {
        HadithHukamAjmaliOne = hadithHukamAjmaliOne;
    }

    public String getHadithHukamAjmaliTwo() {
        return HadithHukamAjmaliTwo;
    }

    public void setHadithHukamAjmaliTwo(String hadithHukamAjmaliTwo) {
        HadithHukamAjmaliTwo = hadithHukamAjmaliTwo;
    }

    public String getHadithHukamAjmaliThree() {
        return HadithHukamAjmaliThree;
    }

    public void setHadithHukamAjmaliThree(String hadithHukamAjmaliThree) {
        HadithHukamAjmaliThree = hadithHukamAjmaliThree;
    }

    public String getHadithHukamAjmaliFour() {
        return HadithHukamAjmaliFour;
    }

    public void setHadithHukamAjmaliFour(String hadithHukamAjmaliFour) {
        HadithHukamAjmaliFour = hadithHukamAjmaliFour;
    }

    public String getHadithHukamAjmaliFive() {
        return HadithHukamAjmaliFive;
    }

    public void setHadithHukamAjmaliFive(String hadithHukamAjmaliFive) {
        HadithHukamAjmaliFive = hadithHukamAjmaliFive;
    }

    public String getHadithHukamAjmaliSix() {
        return HadithHukamAjmaliSix;
    }

    public void setHadithHukamAjmaliSix(String hadithHukamAjmaliSix) {
        HadithHukamAjmaliSix = hadithHukamAjmaliSix;
    }

    public String getHadithHukamAjmaliSeven() {
        return HadithHukamAjmaliSeven;
    }

    public void setHadithHukamAjmaliSeven(String hadithHukamAjmaliSeven) {
        HadithHukamAjmaliSeven = hadithHukamAjmaliSeven;
    }

    public String getHadithHukamAjmaliEight() {
        return HadithHukamAjmaliEight;
    }

    public void setHadithHukamAjmaliEight(String hadithHukamAjmaliEight) {
        HadithHukamAjmaliEight = hadithHukamAjmaliEight;
    }

    public String getHadithHukamAjmaliNine() {
        return HadithHukamAjmaliNine;
    }

    public void setHadithHukamAjmaliNine(String hadithHukamAjmaliNine) {
        HadithHukamAjmaliNine = hadithHukamAjmaliNine;
    }

    public String getHadithHukamAjmaliTen() {
        return HadithHukamAjmaliTen;
    }

    public void setHadithHukamAjmaliTen(String hadithHukamAjmaliTen) {
        HadithHukamAjmaliTen = hadithHukamAjmaliTen;
    }

    public String getHadithHashiaTextOne() {
        return HadithHashiaTextOne;
    }

    public void setHadithHashiaTextOne(String hadithHashiaTextOne) {
        HadithHashiaTextOne = hadithHashiaTextOne;
    }

    public String getHadithHashiaTextTwo() {
        return HadithHashiaTextTwo;
    }

    public void setHadithHashiaTextTwo(String hadithHashiaTextTwo) {
        HadithHashiaTextTwo = hadithHashiaTextTwo;
    }

    public String getHadithHashiaTextThree() {
        return HadithHashiaTextThree;
    }

    public void setHadithHashiaTextThree(String hadithHashiaTextThree) {
        HadithHashiaTextThree = hadithHashiaTextThree;
    }

    public String getHadithHashiaTextFour() {
        return HadithHashiaTextFour;
    }

    public void setHadithHashiaTextFour(String hadithHashiaTextFour) {
        HadithHashiaTextFour = hadithHashiaTextFour;
    }

    public String getHadithHashiaTextFive() {
        return HadithHashiaTextFive;
    }

    public void setHadithHashiaTextFive(String hadithHashiaTextFive) {
        HadithHashiaTextFive = hadithHashiaTextFive;
    }

    public String getHadithHashiaTextSix() {
        return HadithHashiaTextSix;
    }

    public void setHadithHashiaTextSix(String hadithHashiaTextSix) {
        HadithHashiaTextSix = hadithHashiaTextSix;
    }

    public String getHadithHashiaTextSeven() {
        return HadithHashiaTextSeven;
    }

    public void setHadithHashiaTextSeven(String hadithHashiaTextSeven) {
        HadithHashiaTextSeven = hadithHashiaTextSeven;
    }

    public String getHadithHashiaTextEight() {
        return HadithHashiaTextEight;
    }

    public void setHadithHashiaTextEight(String hadithHashiaTextEight) {
        HadithHashiaTextEight = hadithHashiaTextEight;
    }

    public String getHadithHashiaTextNine() {
        return HadithHashiaTextNine;
    }

    public void setHadithHashiaTextNine(String hadithHashiaTextNine) {
        HadithHashiaTextNine = hadithHashiaTextNine;
    }

    public String getHadithHashiaTextTen() {
        return HadithHashiaTextTen;
    }

    public void setHadithHashiaTextTen(String hadithHashiaTextTen) {
        HadithHashiaTextTen = hadithHashiaTextTen;
    }

    public String getHadithUrduTextOne() {
        return HadithUrduTextOne;
    }

    public void setHadithUrduTextOne(String hadithUrduTextOne) {
        HadithUrduTextOne = hadithUrduTextOne;
    }

    public String getHadithUrduTextTwo() {
        return HadithUrduTextTwo;
    }

    public void setHadithUrduTextTwo(String hadithUrduTextTwo) {
        HadithUrduTextTwo = hadithUrduTextTwo;
    }

    public String getHadithUrduTextThree() {
        return HadithUrduTextThree;
    }

    public void setHadithUrduTextThree(String hadithUrduTextThree) {
        HadithUrduTextThree = hadithUrduTextThree;
    }

    public String getHadithUrduTextFour() {
        return HadithUrduTextFour;
    }

    public void setHadithUrduTextFour(String hadithUrduTextFour) {
        HadithUrduTextFour = hadithUrduTextFour;
    }

    public String getHadithUrduTextFive() {
        return HadithUrduTextFive;
    }

    public void setHadithUrduTextFive(String hadithUrduTextFive) {
        HadithUrduTextFive = hadithUrduTextFive;
    }

    public String getHadithUrduTextSix() {
        return HadithUrduTextSix;
    }

    public void setHadithUrduTextSix(String hadithUrduTextSix) {
        HadithUrduTextSix = hadithUrduTextSix;
    }

    public String getHadithUrduTextSeven() {
        return HadithUrduTextSeven;
    }

    public void setHadithUrduTextSeven(String hadithUrduTextSeven) {
        HadithUrduTextSeven = hadithUrduTextSeven;
    }

    public String getHadithUrduTextEight() {
        return HadithUrduTextEight;
    }

    public void setHadithUrduTextEight(String hadithUrduTextEight) {
        HadithUrduTextEight = hadithUrduTextEight;
    }

    public String getHadithUrduTextNine() {
        return HadithUrduTextNine;
    }

    public void setHadithUrduTextNine(String hadithUrduTextNine) {
        HadithUrduTextNine = hadithUrduTextNine;
    }

    public String getHadithUrduTextTen() {
        return HadithUrduTextTen;
    }

    public void setHadithUrduTextTen(String hadithUrduTextTen) {
        HadithUrduTextTen = hadithUrduTextTen;
    }

    public String toString(SharedPreferences pref)
    {

        return getMasadirNameUrdu()+"/n"+getKutubNameUrdu()+"\n"+getBabNameUrdu()+"\n"+getHadeesNo()+":"+getTextHadeesArabic()+"\n"+getTextHadeesUrdu()
                +"\n\n\n"+" http://mohaddis.com/View/"+ Constants.booksURl[getMasadirId()-1]+"/"+ Utilites.getTaraqimForURL(pref,getMasadirId())+"/"+Utilites.getTaraqimBasedHNo(pref,getMasadirId(),this);
    }
}
