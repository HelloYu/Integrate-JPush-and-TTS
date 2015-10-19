package cn.jpush.phonegap;

import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class AlarmRecordService {

    private DBOpenHelper dbOpenHelper;
	
	public AlarmRecordService(Context context){
		this.dbOpenHelper = new DBOpenHelper(context);
	}
	
	public void save(AlarmRecord Alarm){
		/**
		 * 第一次调用getWritableDatabase()或getReadableDatabase()方法后，
		 * SQLiteOpenHelper会缓存当前的SQLiteDatabase实例，SQLiteDatabase
		 * 实例正常情况下会维持数据库的打开状�?，所以在你不再需要SQLiteDatabase
		 * 实例时，请及时调用close()方法释放资源。一旦SQLiteDatabase实例被缓存，
		 * 多次调用getWritableDatabase()或getReadableDatabase()方法得到的都是同�?��例�?
		 */
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("insert into AlarmRecords(title,description,pushurl,alarmtime,username,alarmguid,messagecode) values(?,?,?,?,?,?,?)",
				new Object[]{Alarm.getTitle(), Alarm.getDescription(),Alarm.getPushurl(),Alarm.getAlarmtime(),Alarm.getUserName(),Alarm.getAlarmguid(),Alarm.getMessagecode()});
		db.close();//当应用中只有�?��地方使用数据库的时�?可以不关闭它,提高性能
	}
	
	/**
	 * 删除操作
	 * sql语句�?��加上delete from table 加上from关键字才�?
	 * @param id
	 */
	public void delete(Integer alarmid){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from AlarmRecords where alarmid=?", new Object[]{alarmid});
		db.close();
	}
	
	public void deleteall(){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		db.execSQL("delete from AlarmRecords");
		db.close();
	}
	
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
	public AlarmRecord find(Integer id){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select alarmid,title,description,datetime(alarmtime,'localtime') as alarmtime,pushurl,isread from AlarmRecords where alarmid=?", 
				new String[]{String.valueOf(id)});
		AlarmRecord alarm = new AlarmRecord();
		if(cursor.moveToFirst()){
			Integer alarmid = cursor.getInt(cursor.getColumnIndex("alarmid"));
			String title = cursor.getString(cursor.getColumnIndex("title"));
			String description = cursor.getString(cursor.getColumnIndex("description"));
			String strDate =cursor.getString(cursor.getColumnIndex("alarmtime"));		
						
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date alarmtime=new Date();
//			try {
//				alarmtime = format.parse(strDate);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			String pushurl=cursor.getString(cursor.getColumnIndex("pushurl"));
			String isread=cursor.getString(cursor.getColumnIndex("isread"));
			
			String username =cursor.getString(cursor.getColumnIndex("username"));
			String alarmguid =cursor.getString(cursor.getColumnIndex("alarmguid"));
			
			Integer messagecode = cursor.getInt(cursor.getColumnIndex("messagecode"));
			
//			alarm = new AlarmRecord();
			alarm.setAlarmid(alarmid);
			alarm.setTitle(title);
			alarm.setDescription(description);
			alarm.setAlarmtime(strDate);
			alarm.setPushurl(pushurl);
			alarm.setIsread(Boolean.parseBoolean(isread));
			
			alarm.setUserName(username);
			alarm.setAlarmguid(alarmguid);
			
			alarm.setMessagecode(messagecode);
			
		}else{
			alarm=null;
		}
		cursor.close();
		db.close();
		return alarm;
	}
	
	/**
	 * 分页获取记录
	 * @param offset 跳过前面多少条记�?
	 * @param maxResult 每页获取多少条记�?
	 * @return
	 */
	public List<AlarmRecord> getScrollData(int offset, int maxResult){
		List<AlarmRecord> alarms = new ArrayList<AlarmRecord>();

		return alarms;
	}

	
	/**
	 * 获取记录总数
	 * 经sql查询,就算表中没有�?��记录,count也会�?
	 * @return
	 */
	public long getCount(){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.rawQuery("select count(*) from AlarmRecords", null);
		cursor.moveToFirst();
		long count = cursor.getLong(0);//�?个下标参�?
		cursor.close();
		db.close();
		return count;
	}
	
//	public Pages<AlarmRecord> getRecordPage(Pages<AlarmRecord> page, String starttime,String endtime,String url) {
//		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
//		int pageSize = page.getPageSize();
//		int pageNo = page.getPageNo();
//		Cursor cursor;
//		String sql;
//		if(starttime.length()>0 && endtime.length()>0){
//			//datetime(alarmtime,'localtime') as 
//			sql= "select alarmid,title,description,datetime(alarmtime,'localtime') as alarmtime,pushurl,isread  from AlarmRecords "
//					+ " where datetime(alarmtime,'localtime')>=? and datetime(alarmtime,'localtime')<=? and pushurl like ? "
//					+ " order by alarmtime desc Limit " + String.valueOf(pageSize) + " Offset "
//					+ String.valueOf(pageNo * pageSize);
//			String[] selectionArgs = { starttime, endtime, "%" + url + "%"};
//			cursor = db.rawQuery(sql, selectionArgs);
//		}else{
//			sql= "select alarmid,title,description,datetime(alarmtime,'localtime') as alarmtime,pushurl,isread from AlarmRecords "
//					+ " where pushurl like ? order by alarmtime desc Limit " + String.valueOf(pageSize) + " Offset "
//					+ String.valueOf(pageNo * pageSize);
//			String[] selectionArgs = { "%" + url + "%"};
//			cursor = db.rawQuery(sql, selectionArgs);
//		}	
//		List<AlarmRecord> result = new ArrayList<AlarmRecord>();
//		for(cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()){
//			AlarmRecord alarm = new AlarmRecord();
//			Integer alarmid = cursor.getInt(cursor.getColumnIndex("alarmid"));
//			String title = cursor.getString(cursor.getColumnIndex("title"));
//			String description = cursor.getString(cursor.getColumnIndex("description"));
//			//String strDate =cursor.getString(cursor.getColumnIndex("datetime(alarmtime,'localtime')"));
//			String strDate =cursor.getString(cursor.getColumnIndex("alarmtime"));
//			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//			Date alarmtime=new Date();
//			try {
//				alarmtime = format.parse(strDate);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			String pushurl=cursor.getString(cursor.getColumnIndex("pushurl"));
//			String isread=cursor.getString(cursor.getColumnIndex("isread"));
//			alarm = new AlarmRecord();
//			alarm.setAlarmid(alarmid);
//			alarm.setTitle(title);
//			alarm.setDescription(description);
//			alarm.setAlarmtime(alarmtime);
//			alarm.setPushurl(pushurl);
//			alarm.setIsread(Boolean.parseBoolean(isread));
//			result.add(alarm);
//		}
//		page.setTotalCount(getRowCount(starttime, endtime,url));
//		page.setResult(result);
//		cursor.close();
//		db.close();
//		return page;
//	}
	
	/**
	 * 总记录数
	 */
	public int getRowCount(String starttime, String endtime,String url){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = null;
		String sql;
		if(starttime.length()>0 && endtime.length()>0){
			sql= "select * from AlarmRecords "
					+ " where datetime(alarmtime,'localtime')>=? and datetime(alarmtime,'localtime')<=? and pushurl like ? ";
			String[] selectionArgs = { starttime, endtime ,"%" + url + "%"};
			cursor = db.rawQuery(sql, selectionArgs);
		}else{
			sql= "select * from AlarmRecords where pushurl like ? ";
			String[] selectionArgs = { "%" + url + "%"};
			cursor = db.rawQuery(sql, selectionArgs);
		}
		int count = cursor.getCount();
		cursor.close();
		db.close();
		return count;
	}
	

}
