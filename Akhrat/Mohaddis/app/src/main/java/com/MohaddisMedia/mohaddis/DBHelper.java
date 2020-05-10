package com.MohaddisMedia.mohaddis;

import android.provider.BaseColumns;

public class DBHelper {
    public static class MasadaEntry implements BaseColumns {
        public static final String MA_ID = "HadithBookID";
        public static final String HIDDEN_ID = "HiddenID";
        public static final String MASADIR_NAME_ARABIC = "HadithBookName";
        public static final String MASADIR_NAME_URDU = "HadithBookNameUrdu";
        public static final String TABLE_NAME = "HadithBooksName";

    }

    public static class KutubEntry implements BaseColumns {
        public static final String ID = "IDPK";
        public static final String HIDDEN_ID = "HiddenID";
        public static final String TABLE_NAME = "Kutab";
        public static final String KITAAB_NAME_URDU = "kitaabNameUrdu";
        public static final String MASADIR_ID = "BookID";
        public static final String KITAAB_NAME_ARABIC = "kitaabNameArabic";
        public static final String KITAB_TAMHEED_ARABIC = "KitaabTamheedArabic";
        public static final String KITAB_TAMHEED_URDU = "KitaabTamheedUrdu";
    }

    public static class AbwabEntry implements BaseColumns {
        public static final String ID = "IDPK";
        public static final String HIDDEN_ID = "HiddenID";
        public static final String TABLE_NAME = "Abwaab";
        public static final String BAB_NAME_URDU = "BaabNameUrdu";
        public static final String KUTUB_ID = "KitaabID";
        public static final String BAB_NAME_ARABIC = "BaabNameArabic";
        public static final String BAB_TAMHEED_ARABIC = "AbwaabTamheedArabic";
        public static final String BAB_TAMHEED_URDU = "AbwaabTamheedUrdu";
        public static final String TARJUMA_BAB_URDU = "TarjumatulBaabUrdu";
    }

    public static class HadeesEntry implements BaseColumns {
        public static final String ID = "Id";
        public static final String HADEES_NO= "HadeesNumber";
        public static final String TABLE_NAME = "Ahadith";
        public static final String HADEES_TEXT_URDU = "HadithUrduText";
        public static final String HADESS_HASHIA_TEXT = "HadithHashiaText";
        public static final String BAB_ID = "BaabID";
        public static final String HADEES_TEXT_ARABIC = "HadithArabicText";
        public static final String HIDDEN_ID = "HiddenID";

        //TODO qoli feli taqreri
        public static final String HadithTypeAtraaf = "HadithTypeAtraaf";
        public static final String HadithTypeRawaat = "HadithTypeRawaat";
        public static final String HadithTypeQFT = "HadithTypeQFT";

        //TODO main hukam
        public static final String HadithHukamAjmali = "HadithHukamAjmali";
        public static final String HadithHukamTafseeli = "HadithHukamTafseeli";

        //TODO mozu
        public static final String HadithMouzuhArabic = "HadithMouzuhArabic";
        public static final String HadithMouzuhUrdu = "HadithMouzuhUrdu";
        public static final String HadithHukamTafseeliArabic = "HadithHukamTafseeliArabic";

        //TODO taraqeem
        public static final String HadeesNumberTOne = "HadeesNumberTOne";
        public static final String HadeesNumberTTwo = "HadeesNumberTTwo";
        public static final String HadeesNumberTThree = "HadeesNumberTThree";
        public static final String HadeesNumberTFour = "HadeesNumberTFour";
        public static final String HadeesNumberTFive = "HadeesNumberTFive";
        public static final String HadeesNumberTSix = "HadeesNumberTSix";
        public static final String HadeesNumberTSeven = "HadeesNumberTSeven";
        public static final String HadeesNumberTEight = "HadeesNumberTEight";
        public static final String HadeesNumberTNine = "HadeesNumberTNine";
        public static final String HadeesNumberTTen = "HadeesNumberTTen";

        //TODO Hukam
        public static final String HadithHukamAjmaliOne = "HadithHukamAjmaliOne";
        public static final String HadithHukamAjmaliTwo = "HadithHukamAjmaliTwo";
        public static final String HadithHukamAjmaliThree = "HadithHukamAjmaliThree";
        public static final String HadithHukamAjmaliFour = "HadithHukamAjmaliFour";
        public static final String HadithHukamAjmaliFive = "HadithHukamAjmaliFive";
        public static final String HadithHukamAjmaliSix = "HadithHukamAjmaliSix";
        public static final String HadithHukamAjmaliSeven = "HadithHukamAjmaliSeven";
        public static final String HadithHukamAjmaliEight = "HadithHukamAjmaliEight";
        public static final String HadithHukamAjmaliNine = "HadithHukamAjmaliNine";
        public static final String HadithHukamAjmaliTen = "HadithHukamAjmaliTen";


        //TODO Hasia
        public static final String HadithHashiaTextOne = "HadithHashiaTextOne";
        public static final String HadithHashiaTextTwo= "HadithHashiaTextTwo";
        public static final String HadithHashiaTextThree = "HadithHashiaTextThree";
        public static final String HadithHashiaTextFour = "HadithHashiaTextFour";
        public static final String HadithHashiaTextFive = "HadithHashiaTextFive";
        public static final String HadithHashiaTextSix = "HadithHashiaTextSix";
        public static final String HadithHashiaTextSeven = "HadithHashiaTextSeven";
        public static final String HadithHashiaTextEight = "HadithHashiaTextEight";
        public static final String HadithHashiaTextNine = "HadithHashiaTextNine";
        public static final String HadithHashiaTextTen = "HadithHashiaTextTen";

        //TODO Tarjama
        public static final String HadithUrduText= "HadithUrduTextOne";
        public static final String HadithUrduTextOne = "HadithUrduTextOne";
        public static final String HadithUrduTextTwo = "HadithUrduTextTwo";
        public static final String HadithUrduTextThree = "HadithUrduTextThree";
        public static final String HadithUrduTextFour = "HadithUrduTextFour";
        public static final String HadithUrduTextFive = "HadithUrduTextFive";
        public static final String HadithUrduTextSix = "HadithUrduTextSix";
        public static final String HadithUrduTextSeven = "HadithUrduTextSeven";
        public static final String HadithUrduTextEight = "HadithUrduTextEight";
        public static final String HadithUrduTextNine = "HadithUrduTextNine";
        public static final String HadithUrduTextTen = "HadithUrduTextTen";
        public static final String HadithUrduHasia= "HadithHasiaTextTen";


    }

    public static class TakhreejEntry implements BaseColumns {
        public static final String ID = "ID";
        public static final String TABLE_NAME = "Takhreej";
        public static final String HADEESTAKHREEJID = "HadithIDTakhreej";
        public static final String HADEESPRESENTID = "HadithIDPresent";
        public static final String BOOKNAMEPRESENT = "BookNamePresent";
        public static final String BOOKNAMETAKHREEJ = "BookNameTakhreej";
        public static final String TAKHREEJBOOKID = "HadithBookIdTakhreej";
        public static final String PRESENTBOOKID = "HadithBookIdPresent";
        public static final String AVAILABILITY = "availability";


    }

    public static class TopicEntry implements BaseColumns {
        public static final String ID = "IDPK";
        public static final String TABLE_NAME = "HadithTopics";
        public static final String PARENT_ID = "ParentID";
        public static final String SEQID = "SeqID";
        public static final String TOPICURDU = "TopicUrdu";
        public static final String TOPICARABIC = "TopicArabic";
        public static final String LEVEL = "Level";
        public static final String HIDDEN_ID = "HiddenID";

    }


    public static class HadithTopicsTOAhadithEntry implements BaseColumns {
        public static final String ID = "ID";
        public static final String TABLE_NAME = "HadithTopicsToAhadith";
        public static final String MOZUID = "MozuID";
        public static final String HADEESNUMBER = "HadithNumber";
        public static final String HADITHBOOKID = "HadithBookID";
        public static final String HIDDEN_ID = "HiddenID";

    }
}
