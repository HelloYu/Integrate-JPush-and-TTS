package cn.jpush.phonegap;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{

	public DBOpenHelper(Context context) {
		//第二个参数为数据库名�?.db不一定要�?
		//第三个参数CursorFactory指定在执行查询时获得�?��游标实例的工厂类,
		//设置为null,代表使用系统默认的工厂类，用于迭代数�?
		//第四个参数版本号建议�?��始为1，不能小�?	
		super(context, "jkalarm.db", null, 3);//保存�?�?/database/文件夹下,4为当前版本号
		// TODO Auto-generated constructor stub
	}

	/**
	 * 是在数据库第�?��被创建的时�?调用�?
	 * 适合生成数据库表,可以使用SQLiteDatabase
	 * 对数据库的增删改查操�?
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS AlarmRecords(alarmid integer primary key autoincrement, title varchar(100), description text, alarmtime varchar(150),pushurl varchar(150),isread integer,username varchar(150),alarmguid varchar(50),messagecode integer)");
		db.execSQL("CREATE TABLE IF NOT EXISTS AlarmConfigs(configid integer primary key autoincrement, configname varchar(100),configvalue varchar(300))");
	}

	/**
	 * 下面onUpgrade()方法中的注释是在数据库版本每次发生变化时都会把用户手机上的数据库表删除，然后再重新创建�?
	 * �?��在实际项目中是不能这样做的，正确的做法是在更新数据库表结构时，还要�?虑用户存放于数据库中的数据不会丢失�?
	 * 该方法是在数据库的版本号变更的时候被调用�?比如之前�?现在改成�?版本
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS AlarmRecords");
		db.execSQL("DROP TABLE IF EXISTS AlarmConfigs");
        onCreate(db);
//		db.execSQL("ALTER TABLE person ADD phone VARCHAR(12) NULL");//允许phone字段为null
	}

}
