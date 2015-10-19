package cn.jpush.phonegap;
import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
public class TTSService extends Service {
	
	private static String TAG = "TTSService";
	
	// 语音合成对象
	private static SpeechSynthesizer mTts;
	private static Handler refreshHandler;
	private	static Runnable runnable;
	private static boolean isover=true;
//	public TTSService() {
//	      // 设置子线程名称
//	      super("work thread");
//	   }
//	 
//	   @Override
//	   protected void onHandleIntent(Intent intent) {
////	      Log.d("SingleService", Thread.currentThread().getName());
//		  initTTS();
//	      try {
//	         Thread.sleep(3 * 1000);
//	      } catch (InterruptedException e) {
//	         e.printStackTrace();
//	      }
//	   }
	
	@Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub	
        return null;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "initTTS");
        initTTS();
        refreshHandler=new Handler();
 		runnable=new Runnable() {
 		    @Override
 		    public void run() {
 		        // TODO Auto-generated method stub 
// 		    	toastInfo("TTS定时开始");
 		    	try{
 		    		//要做的事情
 	 		        if(Utils.alarmlist.size()>0){
 	 		        	Log.d(TAG, "Utils.alarmlist.size()>0");
 	 		        	if(isover){
 	 		        		
 	 		        	  if(Utils.isTTS){
 	 		        		  Log.d(TAG, "Utils.isTTS");
 	 		        		  isover=false;
 	 	 		        	  int code = mTts.startSpeaking(Utils.alarmlist.get(0).getDescription(), mTtsListener);
 	 	 		        	  if(code != ErrorCode.SUCCESS) {
 	 	 		        		 Log.d(TAG, "ErrorCode.SUCCESS");
 	 	 		        		 isover=true;
 	 	 		        	  }
 	 		        	  }	 		        	  
 	 		        	  
 	 		        	  
 	 		        	  Utils.alarmlist.remove(0);
 	 		        	}
// 	 		        	toastInfo("TTS定时postDelayed开始");
 	 		        	refreshHandler.postDelayed(this, 10000);
 	 		        }else{
// 	 		        	toastInfo("TTS定时removeCallbacks");
 	 		        	refreshHandler.removeCallbacks(runnable);
 	 		        }
 		    		
 		    	}catch(Exception e){
 		    		Log.d(TAG, "TTS定时Exception开始");
 		    		isover=true;
// 		    		toastInfo("TTS定时Exception开始");
 		    		refreshHandler.postDelayed(this, 10000);
 		    	} 		        
	 		    
 		    }
 		};
//        mTts.startSpeaking("", mTtsListener);
//        try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
// 
    }
    @Override  
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.d(TAG, "onStart");
        if(isover){
        	Log.d(TAG, "onStart isover");
        	refreshHandler.post(runnable);
        }
        
//        initTTS();
//      int code = mTts.startSpeaking(Utils.alarmlist.get(0).getDescription(), mTtsListener);
//        if(Utils.alarmlist.size()>0){
//        	int code = mTts.startSpeaking(Utils.alarmlist.get(0).getDescription(), mTtsListener);
//    	     while(code != ErrorCode.SUCCESS) {
//    			if(code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
//    				//未安装则跳转到提示安装页面
////    				mInstaller.install();
//    			}else {
//    				toastInfo("语音合成失败,错误码: " + code);	
////    				code = mTts.startSpeaking(Utils.alarmlist.get(0).getDescription(), mTtsListener);
//    			}
//    			Utils.alarmlist.remove(0);
//    			if(Utils.alarmlist.size()>0){
//    			   code = mTts.startSpeaking(Utils.alarmlist.get(0).getDescription(), mTtsListener);
//    			}else
//    			{
////    				stopSelf();
//    				break;
//    			}
//    		}
    	   //Utils.alarmlist.remove(0);
//        }
    } 
    @Override
    public void onDestroy() {
        super.onDestroy();
        refreshHandler.removeCallbacks(runnable); 
    }
	private void initTTS(){
    	// 初始化合成对象
//		if(Utils.appcontext!=null){
//			mTts = SpeechSynthesizer.createSynthesizer(Utils.appcontext, mTtsInitListener);	
//		}else{
//			mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);	
//		}
		
//		SpeechUtility.createUtility(this, "appid=54b87d34");
		
		Log.d(TAG, "initTTS");
		
		mTts = SpeechSynthesizer.createSynthesizer(this, mTtsInitListener);		
		// 云端发音人名称列表
//		cloudVoicersEntries = getResources().getStringArray(R.array.voicer_cloud_entries);
//		cloudVoicersValue = getResources().getStringArray(R.array.voicer_cloud_values);
//		mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, Activity.MODE_PRIVATE);
		
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
		//设置发音人 voicer为空默认通过语音+界面指定发音人。
		mTts.setParameter(SpeechConstant.VOICE_NAME,"");
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		mTts.setParameter(SpeechConstant.VOLUME,"50");
	}
	/**
	 * 初期化监听。
	 */
	private InitListener mTtsInitListener = new InitListener() {
		@Override
		public void onInit(int code) {
//			Log.d(TAG, "InitListener init() code = " + code);
			if (code != ErrorCode.SUCCESS) {
//        		showTip("初始化失败,错误码："+code);
				toastInfo("TTS语音合成初始化失败,错误码："+code);
				Log.d(TAG, "TTS语音合成初始化失败,错误码："+code);
        	}		
		}
	};
	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener() {
		@Override
		public void onSpeakBegin() {
//			toastInfo("开始播放");
		}

		@Override
		public void onSpeakPaused() {
//			toastInfo("暂停播放");
		}

		@Override
		public void onSpeakResumed() {
//			toastInfo("继续播放");
		}

		@Override
		public void onBufferProgress(int percent, int beginPos, int endPos,
				String info) {
//			mPercentForBuffering = percent;
//			showTip(String.format(getString(R.string.tts_toast_format),
//					mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onSpeakProgress(int percent, int beginPos, int endPos) {
//			mPercentForPlaying = percent;
//			showTip(String.format(getString(R.string.tts_toast_format),
//					mPercentForBuffering, mPercentForPlaying));
		}

		@Override
		public void onCompleted(SpeechError error) {
//			if(error == null)
//			{
////				toastInfo("播放完成");
//				Utils.alarmlist.remove(0);
//				if(Utils.alarmlist.size()>0){
//		        	int code = mTts.startSpeaking(Utils.alarmlist.get(0).getDescription(), mTtsListener);
//		    	    while (code != ErrorCode.SUCCESS) {
//		    			if(code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
//		    				//未安装则跳转到提示安装页面
////		    				mInstaller.install();
//		    			}else {
//		    				toastInfo("语音合成失败,错误码: " + code);	
//		    			}
//		    			Utils.alarmlist.remove(0);
//		    			if(Utils.alarmlist.size()>0){
//		    			   code = mTts.startSpeaking(Utils.alarmlist.get(0).getDescription(), mTtsListener);
//		    			}else
//		    			{
////		    				stopSelf();
//		    				break;
//		    			}
//		    		}		    	   
//		        }else{
////		        	stopSelf();
//		        }				
//			}
//			else if(error != null)
//			{
////				toastInfo(error.getPlainDescription(true));
//			}
			isover=true;
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
			
		}
	};
	private void toastInfo(String string){
		Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
	}
}
