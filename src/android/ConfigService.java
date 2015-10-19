package cn.jpush.phonegap;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class ConfigService {

    private DBOpenHelper dbOpenHelper;
	
	public ConfigService(Context context){
		this.dbOpenHelper = new DBOpenHelper(context);
	}
	
//	public void save(Config config){
//		/**
//		 * 第一次调用getWritableDatabase()或getReadableDatabase()方法后，
//		 * SQLiteOpenHelper会缓存当前的SQLiteDatabase实例，SQLiteDatabase
//		 * 实例正常情况下会维持数据库的打开状�?，所以在你不再需要SQLiteDatabase
//		 * 实例时，请及时调用close()方法释放资源。一旦SQLiteDatabase实例被缓存，
//		 * 多次调用getWritableDatabase()或getReadableDatabase()方法得到的都是同�?��例�?
//		 */
//		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
//		db.execSQL("insert into AlarmRecords(title,description,pushurl,alarmtime,username,alarmguid,messagecode) values(?,?,?,?,?,?,?)",
//				new Object[]{Alarm.getTitle(), Alarm.getDescription(),Alarm.getPushurl(),Alarm.getAlarmtime(),Alarm.getUserName(),Alarm.getAlarmguid(),Alarm.getMessagecode()});
//		db.close();//当应用中只有�?��地方使用数据库的时�?可以不关闭它,提高性能
//	}
	
	/**
	 * 删除操作
	 * sql语句�?��加上delete from table 加上from关键字才�?
	 * @param id
	 */
//	public void delete(Integer alarmid){
//		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
//		db.execSQL("delete from AlarmRecords where alarmid=?", new Object[]{alarmid});
//		db.close();
//	}
//	
//	public void deleteall(){
//		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
//		db.execSQL("delete from AlarmRecords");
//		db.close();
//	}
	
	/**
	 * 更新操作
	 * @param person
	 */
//	public void update(AlarmRecord Alarm){
//		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
//		db.execSQL("update AlarmRecords set title=?,description=?,pushurl=?,isread=? where alarmid=?", 
//			new Object[]{Alarm.getTitle(),Alarm.getDescription(),Alarm.getAlarmid(),Alarm.getPushurl(),Alarm.isIsread()});
//		db.close();
//	}
	
	/**
	 * 查询记录
	 * Cursor是结果集游标，用于对结果集进行随机访问，如果大家熟悉jdbc�?
	 * 其实Cursor与JDBC中的ResultSet作用很相似�?使用moveToNext()方法
	 * 可以将游标从当前行移动到下一行，如果已经移过了结果集的最后一行，
	 * 返回结果为false，否则为true。另外Cursor 还有常用的moveToPrevious()
	 * 方法(用于将游标从当前行移动到上一行，如果已经移过了结果集的第�?���?
	 * 返回值为false，否则为true )、moveToFirst()方法（用于将游标移动�?
	 * 结果集的第一行，如果结果集为空，返回值为false，否则为true)和moveToLast()
	 * 方法(用于将游标移动到结果集的�?���?��，如果结果集为空，返回�?为false，否则为true)
	 * @param id
	 * @return
	 */
	public Config find(String confignameQuery){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select configid,configname,configvalue from AlarmConfigs where configname=?", 
				new String[]{confignameQuery});
		Config config = new Config();
		if(cursor.moveToFirst()){
			Integer configid = cursor.getInt(cursor.getColumnIndex("configid"));
			String configname = cursor.getString(cursor.getColumnIndex("configname"));
			String configvalue = cursor.getString(cursor.getColumnIndex("configvalue"));
			
			config.setConfigid(configid);
			config.setConfigname(configname);
			config.setConfigvalue(configvalue);
			
		}else{
			config=null;
		}
		cursor.close();
		db.close();
		return config;
	}
}
