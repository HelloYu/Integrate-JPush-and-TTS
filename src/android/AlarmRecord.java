package cn.jpush.phonegap;

public class AlarmRecord {
	
	private Integer alarmid;
	private String title="";
	
	private String description="";
	private String alarmtime="2000-01-01 00:00:00";
	private int messagecode=0;//报警指令代码1为报警记�?
	private String pushurl="";
	private boolean isread;
	private String UserName="";
	private String alarmguid="";
	
	public Integer getAlarmid() {
		return alarmid;
	}
	public void setAlarmid(Integer alarmid) {
		this.alarmid = alarmid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAlarmtime() {
		return alarmtime;
	}
	public void setAlarmtime(String alarmtime) {
		this.alarmtime = alarmtime;
	}
	public int getMessagecode() {
		return messagecode;
	}
	public void setMessagecode(int messagecode) {
		this.messagecode = messagecode;
	}
	public String getPushurl() {
		return pushurl;
	}
	public void setPushurl(String pushurl) {
		this.pushurl = pushurl;
	}
	public boolean isIsread() {
		return isread;
	}
	public void setIsread(boolean isread) {
		this.isread = isread;
	} 	
	
	public String getUserName() {
		return UserName;
	}
	public void setUserName(String userName) {
		UserName = userName;
	}
	public String getAlarmguid() {
		return alarmguid;
	}
	public void setAlarmguid(String alarmguid) {
		this.alarmguid = alarmguid;
	}
	
	

}
