package code.bo;

import code.constant.RobotConst;

public class HeadUpDisplayStats {

	private String robotName;
	
	private volatile boolean isBatteryLow;
	private volatile boolean isOverweight;
	private volatile int batteryPercentage;
	
	private Object batteryShutdownLock;
	
	private volatile Boolean isBatteryShutdown;
	
	public HeadUpDisplayStats(String robotName) {
		this.robotName = robotName;
		this.batteryShutdownLock = new Object();
		this.isBatteryLow = false;
		this.isBatteryShutdown = false;
		this.isOverweight = false;
		this.batteryPercentage = 100;
	}

	public Object getBatteryShutdownLock() {
		return batteryShutdownLock;
	}

	public Boolean getIsBatteryShutdown() {
		return isBatteryShutdown;
	}

	private void setIsBatteryShutdown(Boolean isBatteryShutdown) {
		this.isBatteryShutdown = isBatteryShutdown;
		if(isBatteryShutdown) {
			synchronized (batteryShutdownLock) {
				this.batteryShutdownLock.notify();
			}
		}
	}

	public String getRobotName() {
		return this.robotName;
	}
	
	public boolean isBatteryLow() {
		return isBatteryLow;
	}

	private void setBatteryLow(boolean isBatteryLow) {
		this.isBatteryLow = isBatteryLow;
	}

	public boolean isOverweight() {
		return isOverweight;
	}

	void setOverweight(boolean isOverweight) {
		this.isOverweight = isOverweight;
	}

	public int getBatteryPercentage() {
		return batteryPercentage;
	}

	void setBatteryPercentage(int batteryPercentage) {
		this.batteryPercentage = batteryPercentage;
		setBatteryLow(batteryPercentage < RobotConst.BATTERY_LOW_PERCENTAGE? true : false);
		setIsBatteryShutdown(batteryPercentage <= RobotConst.ROBOT_SHUTDOWN_BATTERY_PERCENTAGE? true : false);
	}

}