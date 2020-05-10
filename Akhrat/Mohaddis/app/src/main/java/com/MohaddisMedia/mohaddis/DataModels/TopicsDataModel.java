package com.MohaddisMedia.mohaddis.DataModels;

import android.os.Parcel;
import android.os.Parcelable;

public class TopicsDataModel implements Parcelable {
    int Id;
    int parentId;
    int seqId;
    String topicUrdu;
    String topicArabic;
    int level;
    String parentTitle;

    public TopicsDataModel(int id, int parentId, int seqId, String topicUrdu, String topicArabic, int level) {
        Id = id;
        this.parentId = parentId;
        this.seqId = seqId;
        this.topicUrdu = topicUrdu;
        this.topicArabic = topicArabic;
        this.level = level;
    }

    public TopicsDataModel() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public int getSeqId() {
        return seqId;
    }

    public void setSeqId(int seqId) {
        this.seqId = seqId;
    }

    public String getTopicUrdu() {
        return topicUrdu;
    }

    public void setTopicUrdu(String topicUrdu) {
        this.topicUrdu = topicUrdu;
    }

    public String getTopicArabic() {
        return topicArabic;
    }

    public void setTopicArabic(String topicArabic) {
        this.topicArabic = topicArabic;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

    }

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }
}
