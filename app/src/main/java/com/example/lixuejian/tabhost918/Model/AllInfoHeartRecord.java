package com.example.lixuejian.tabhost918.Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lixuejian on 2017/9/27.
 */

public class AllInfoHeartRecord {

    private AHeartrate mStartPoint;
    private AHeartrate mEndPoint;
    List<AHeartrate> mHeartratePath=new ArrayList<AHeartrate>();
    private int mAvarage;
    private int mMaxHeartrate;
    private int mMinHeartrate;
    private String mMDuration;//持续时间
    private String mDate;

    public AllInfoHeartRecord() {
    }

    public AHeartrate getmStartPoint() {
        return mStartPoint;
    }

    public void setmStartPoint(AHeartrate mStartPoint) {
        this.mStartPoint = mStartPoint;
    }

    public AHeartrate getmEndPoint() {
        return mEndPoint;
    }

    public void setmEndPoint(AHeartrate mEndPoint) {
        this.mEndPoint = mEndPoint;
    }

    public List<AHeartrate> getmHeartratePath() {
        return mHeartratePath;
    }

    public void setmHeartratePath(List<AHeartrate> mHeartratePath) {
        this.mHeartratePath = mHeartratePath;
    }

    public int getmAvarage() {
        return mAvarage;
    }

    public void setmAvarage(int mAvarage) {
        this.mAvarage = mAvarage;
    }

    public int getmMaxHeartrate() {
        return mMaxHeartrate;
    }

    public void setmMaxHeartrate(int mMaxHeartrate) {
        this.mMaxHeartrate = mMaxHeartrate;
    }

    public int getmMinHeartrate() {
        return mMinHeartrate;
    }

    public void setmMinHeartrate(int mMinHeartrate) {
        this.mMinHeartrate = mMinHeartrate;
    }

    public String getmMDuration() {
        return mMDuration;
    }

    public void setmMDuration(String mMDuration) {
        this.mMDuration = mMDuration;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }


    public void addpoint(AHeartrate mheartrate){
        mHeartratePath.add(mheartrate);
    }

    @Override
    public String toString() {
        StringBuilder record = new StringBuilder();
        record.append("recordSize:" + getmHeartratePath().size() + ", ");
        record.append("avarage:" + getmAvarage() + "bpm, ");
        record.append("duration:" + getmMDuration() + "s");
        return record.toString();
    }
}
