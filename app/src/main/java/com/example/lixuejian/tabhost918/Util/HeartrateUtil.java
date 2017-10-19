package com.example.lixuejian.tabhost918.Util;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.example.lixuejian.tabhost918.uploadHeartrate.AHeartrate;
import com.example.lixuejian.tabhost918.uploadHeartrate.AllInfoHeartRecord;

import java.util.ArrayList;

/**
 * Created by lixuejian on 2017/9/27.
 */

public class HeartrateUtil {


    public static AMapLocation parseLocation(String latLonStr) {
        if (latLonStr == null || latLonStr.equals("") || latLonStr.equals("[]")) {
            return null;
        }
        String[] loc = latLonStr.split(",");
        AMapLocation location = null;
        if (loc.length == 6) {
            location = new AMapLocation(loc[2]);
            location.setProvider(loc[2]);
            location.setLatitude(Double.parseDouble(loc[0]));
            location.setLongitude(Double.parseDouble(loc[1]));
            location.setTime(Long.parseLong(loc[3]));
            location.setSpeed(Float.parseFloat(loc[4]));
            location.setBearing(Float.parseFloat(loc[5]));
        }else if(loc.length == 2){
            location = new AMapLocation("gps");
            location.setLatitude(Double.parseDouble(loc[0]));
            location.setLongitude(Double.parseDouble(loc[1]));
        }

        return location;
    }


    /**
     * 把一条心跳数据的string形式转换成AHeartrate形式
     * @param Str_ONEHeartrateInfoRecord 一条心跳数据的string形式
     * @return AHeartrate
     */
    public static AHeartrate parseOneHeartrateSTR2OneaHeartrate(String Str_ONEHeartrateInfoRecord){
        if (Str_ONEHeartrateInfoRecord == null || Str_ONEHeartrateInfoRecord.equals("") || Str_ONEHeartrateInfoRecord.equals("[]")){
            return null;
        }
        String[] words = Str_ONEHeartrateInfoRecord.split(",");
        AHeartrate maheartrate=null;
        maheartrate=new AHeartrate();

        for (int www=0;www< words.length;www++){
            Log.i("第三层:",words[www]);
        }
        maheartrate.setHeartrateNumner(Integer.parseInt(words[0]));
        maheartrate.setHeaterateTime(words[1]);
        return maheartrate;
    }

    /**
     * 将一个AllInfoHeartRecord转换为ArrayList<AHeartrate>
     * @param Str_AllInfoHeartRecord AllInfoHeartRecord的字符串形式
     * @return ArrayList<AHeartrate>
     */
    public static ArrayList<AHeartrate> parseAllInfoHeartRecords2List(String Str_AllInfoHeartRecord) {
        ArrayList<AHeartrate> heartrates = new ArrayList<AHeartrate>();
        String[] HeartrateSTR = Str_AllInfoHeartRecord.split(";");
        for (int i = 0; i < HeartrateSTR.length; i++) {
            AHeartrate oneHeartrate = HeartrateUtil.parseOneHeartrateSTR2OneaHeartrate(HeartrateSTR[i]);
            if (oneHeartrate != null) {
                heartrates.add(oneHeartrate);
            }
        }
        return heartrates;
    }


    /**
     * 将多个AllInfoHeartRecord转换为ArrayList<AllInfoHeartRecord>
     * @param Str_AllInfoHeartRecord AllInfoHeartRecord的字符串形式
     * @return ArrayList<AHeartrate>
     */
    public static ArrayList<AllInfoHeartRecord> parseSomeAllInfoHeartRecords2List(String Str_AllInfoHeartRecord) {
        ArrayList<AllInfoHeartRecord> heartrates = new ArrayList<AllInfoHeartRecord>();

        String[] HeartrateSTR = Str_AllInfoHeartRecord.split(".");
        for (int i = 0; i < HeartrateSTR.length; i++) {
            ArrayList<AHeartrate> oneAllInfoHeartRecord = HeartrateUtil.parseAllInfoHeartRecords2List(HeartrateSTR[i]);
            if (oneAllInfoHeartRecord != null) {
                AllInfoHeartRecord a=new AllInfoHeartRecord();
                a.setmHeartratePath(oneAllInfoHeartRecord);
                Log.i("第一层",a.toString());
                heartrates.add(a);
            }
        }
        return heartrates;
    }








}
