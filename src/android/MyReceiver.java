package cn.jpush.phonegap;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.jpush.android.api.JPushInterface;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver {
	private static String TAG = "Client Receiver";
	
	private static NotificationCompat.Builder mBuilder;
	private static NotificationManager mNotificationManager;
	/** Notification的ID */
	private static int notifyId = 100;
	
	
	
	@Override
	public void onReceive(Context context, Intent intent) {

		JPushPlugin.inittts();	
		
		JPushPlugin.getConfig();
	
		
        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
        	
        }else if (JPushInterface.ACTION_UNREGISTER.equals(intent.getAction())){
        	
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
        	handlingReceivedMessage(context,intent);
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
        	handlingNotificationReceive(context,intent);
        	
        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
        	handlingNotificationOpen(context,intent);
        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
        
        } else {
        	Log.d(TAG, "Unhandled intent - " + intent.getAction());
        }
	
	}
	private void handlingReceivedMessage(Context context, Intent intent) {
		String msg = intent.getStringExtra(JPushInterface.EXTRA_MESSAGE);
		Map<String,Object> extras = getNotificationExtras(intent);

		
        AlarmRecord ar=new AlarmRecord();
        ar=Utils.getAlarmRecord(msg,extras);
        
        Log.d(TAG, "from:"+ar.getUserName()+">>to:"+Utils.UserName);
        //如果用户不同则丢弃
//        if(Utils.UserName.equals(ar.getUserName()) || Utils.UserName.length()==0){
        	Log.d(TAG, "保存报警信息！");
        	AlarmRecordService ars=new AlarmRecordService(context);
    		ars.save(ar);
    		
//    		Log.d(TAG, "url:" + Utils.url);
//            Log.d(TAG, "ar.getPushurl:"+ar.getPushurl());   
            
//            if((ar.getPushurl().length()>0 && ar.getPushurl().contains(Utils.url)) || Utils.url.length()==0 ){		
//            	Log.d(TAG, "url相同或者为空");     	       	
    			if( (!Utils.isMute || ( CompareTime() )) &&  Utils.isAlarm){
    				Log.d(TAG, "需要报警提示！");  
    		        Utils.alarmlist.add(ar);  
    		        try{
    		        	Log.d(TAG, "本地通知报警！"); 
    		        	//可能会有异常，未调试很清楚
    		            JPushPlugin.jkaddnotify(ar);
    		            if( Utils.isTTS){
    		               Log.d(TAG, "语音报警！"); 
    			           JPushPlugin.playtts();
    		            }
    			        
    		        }catch(Exception e){
    		        	Log.d(TAG, "报警异常:"+e.getMessage());   
    		        }
    			}	
    			
//            }
            Log.d(TAG, "语音消息处理结束！"); 
            
//        }else{
//        	Log.d(TAG, "用户名不同跳过！");
//        }
        
        
		
		JPushPlugin.transmitPush(msg, extras);
	}
	public boolean CompareTime(){
    	SimpleDateFormat sdf =   new SimpleDateFormat("HH:mm");
		String strDt = sdf.format(new Date());
		int i=strDt.compareTo(Utils.muteStTime);
		int j=strDt.compareTo(Utils.muteEndTime);
		//如果开始时间大于结束时间
		if(Utils.muteStTime.compareTo(Utils.muteEndTime)>0){
			if(i<0 && j>0){
				return true;
			}else
			{
				return false;
			}
		}else{
			if(i>0 || j<0){
				return true;
			}else
			{
				return false;
			}
		}
		
    }

//    /** 初始化通知栏 */
//	private void initNotify(Context context){
//		mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
//		mBuilder = new NotificationCompat.Builder(context.getApplicationContext());
//		mBuilder.setContentTitle("测试标题")
//				.setContentText("测试内容")
//				.setContentIntent(getDefalutIntent(context.getApplicationContext(),Notification.FLAG_AUTO_CANCEL))
////				.setNumber(number)//显示数量
//				.setTicker("测试通知来啦")//通知首次出现在通知栏，带上升动画效果的
//				.setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
//				.setPriority(Notification.PRIORITY_DEFAULT)//设置该通知优先级
////				.setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消  
//				.setOngoing(false);//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
////				.setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合：
//				//Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission
////				.setSmallIcon(R.drawable.ic_launcher);
////		mBuilder.setDefaults(Notification.DEFAULT_VIBRATE | Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS);
//		mBuilder.setDefaults(Notification.DEFAULT_VIBRATE  | Notification.DEFAULT_LIGHTS);
////		mBuilder.setDefaults(Notification.DEFAULT_SOUND);
////		mBuilder.setDefaults(Notification.DEFAULT_LIGHTS);
//	}
//	
//	/** 显示通知栏 */
//	public void showNotify(Context context,String title,String Content){
////		mBuilder.setContentTitle(title)
////				.setContentText(Content)
//////				.setNumber(number)//显示数量
////				.setTicker(Content);//通知首次出现在通知栏，带上升动画效果的
////		mNotificationManager.notify(notifyId, mBuilder.build());
//////		mNotification.notify(getResources().getString(R.string.app_name), notiId, mBuilder.build());
//		
//		initNotify(context);
//		
//		
//		mBuilder.setAutoCancel(true)//点击后让通知将消失  
//		.setContentTitle(title)
//		.setContentText(Content)
//		.setTicker(Content);
//		//点击的意图ACTION是跳转到Intent
////		Intent resultIntent = new Intent(context,com.ionicframework.mytodolist522381.MainActivity.class);
//		Intent resultIntent = new Intent(context,context.getClass());
//		resultIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
//		PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//		mBuilder.setContentIntent(pendingIntent);
//		mNotificationManager.notify(notifyId, mBuilder.build());
//	}
//	
//	/** 
//	 * 清除当前创建的通知栏 
//	 */
//	public void clearNotify(int notifyId){
//		mNotificationManager.cancel(notifyId);//删除一个特定的通知ID对应的通知
////		mNotification.cancel(getResources().getString(R.string.app_name));
//	}
//	
//	/**
//	 * 清除所有通知栏
//	 * */
//	public void clearAllNotify() {
//		mNotificationManager.cancelAll();// 删除你发的所有通知
//	}
//	
//	/**
//	 * @获取默认的pendingIntent,为了防止2.3及以下版本报错
//	 * @flags属性:  
//	 * 在顶部常驻:Notification.FLAG_ONGOING_EVENT  
//	 * 点击去除： Notification.FLAG_AUTO_CANCEL 
//	 */
//	public PendingIntent getDefalutIntent(Context context,int flags){
//		PendingIntent pendingIntent= PendingIntent.getActivity(context, 1, new Intent(), flags);
//		return pendingIntent;
//	}
	
	
	
	
	
	
	 private void handlingNotificationOpen(Context context,Intent intent){
		 String alert = intent.getStringExtra(JPushInterface.EXTRA_ALERT);
		 Map<String,Object> extras = getNotificationExtras(intent);
		 
		 Intent launch = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
		 launch.addCategory(Intent.CATEGORY_LAUNCHER);
		 launch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		 
		 JPushPlugin.notificationAlert = alert;
		 JPushPlugin.notificationExtras = extras;
		 
		 JPushPlugin.transmitOpen(alert, extras);

		 context.startActivity(launch);
	 }
	 private void handlingNotificationReceive(Context context,Intent intent){
		 String alert = intent.getStringExtra(JPushInterface.EXTRA_ALERT);
		 Map<String,Object> extras = getNotificationExtras(intent);
		 
		 Intent launch = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
		 launch.addCategory(Intent.CATEGORY_LAUNCHER);
		 launch.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);
		 
		 JPushPlugin.notificationAlert = alert;
		 JPushPlugin.notificationExtras = extras;
		 
		 JPushPlugin.transmitReceive(alert, extras);
	 }
	 private Map<String, Object> getNotificationExtras(Intent intent) {
		 Map<String, Object> extrasMap = new HashMap<String, Object>();
		 
		 for (String key : intent.getExtras().keySet()) {
			 if (!IGNORED_EXTRAS_KEYS.contains(key)) {
			    Log.e("key","key:"+key);
		     	if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)){
		     		extrasMap.put(key, intent.getIntExtra(key,0));
		     	}else{
		     		extrasMap.put(key, intent.getStringExtra(key));
		        }
			 }
		 }
		 return extrasMap;
	 }
	 private static final List<String> IGNORED_EXTRAS_KEYS = 
			 Arrays.asList("cn.jpush.android.TITLE","cn.jpush.android.MESSAGE","cn.jpush.android.APPKEY","cn.jpush.android.NOTIFICATION_CONTENT_TITLE");
}
