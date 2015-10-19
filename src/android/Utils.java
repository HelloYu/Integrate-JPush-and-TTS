package cn.jpush.phonegap;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.json.JSONException;
import org.json.JSONObject;


import android.content.Context;
import android.util.Log;

public class Utils {
    public static final String TAG = "JKUtils";


    public static List<AlarmRecord> alarmlist=new ArrayList<AlarmRecord>();

    
    public static String UserName="";    
    public static String url="";
    
    public static boolean isMute=true;//是否勿扰
    public static String muteStTime="22:00";
    public static String muteEndTime="08:00";
    
    public static boolean isTTS=true;//是否语音    
    public static boolean isAlarm=true;//是否报警 
    
    public static String action="";

    public static Context appcontext=null;
    
    public static String imei="000000000000";
    public static String phone="13100000000";
    
    
    
    public static boolean isRegister=false;
	
    public static String channelId="0000";
    

    public static AlarmRecord getAlarmRecord(String message, Map<String, Object> extras){
    	AlarmRecord ar=new AlarmRecord(); 
    	ar.setDescription(message);
    	    	
    	try{    		
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			ar.setAlarmtime(df.format(new Date()));// new Date()为获取当前系统时间
    		for(Entry<String,Object> entry:extras.entrySet()){
    			if(entry.getKey().equals("cn.jpush.android.EXTRA")){
    				JSONObject jo = new JSONObject((String)entry.getValue());
                    //jExtras.put("cn.jpush.android.EXTRA", jo);
    				ar.setTitle(jo.getString("title"));
    				//ar.setMessagecode(Integer.parseInt(jo.getString("messagecode")));
    				//ar.setPushurl(jo.getString("pushurl"));
    				
//    				try{
//    				   ar.setUserName(jo.getString("UserName"));
//    				   ar.setAlarmguid(jo.getString("alarmguid"));
//    				}catch(Exception ee){
//    					
//    				}
    				
                } 
//    			if(entry.getKey().equals("cn.jpush.android.MSG_ID")){
//    				ar.setTitle(entry.getValue().toString());
//    			}
    		}		
			
			Log.d(TAG, ar.getAlarmtime() + ar.getTitle()+ar.getDescription()+ar.getMessagecode()+ar.getAlarmguid()+ar.getUserName());
		}
		catch(JSONException e)
		{

			
		}
    	
    	return ar;
    }
}
