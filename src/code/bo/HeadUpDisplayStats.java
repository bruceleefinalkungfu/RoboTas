package code.bo;

import code.constant.RobotConst;

public class HeadUpDisplayStats {

	private String robotName;
	
	private volatile boolean isBatteryLow;
	private volatile boolean isOverweight;
	private volatile int batteryPercentage;
	
	private volatile Boolean isBatteryShutdown;
	
	public HeadUpDisplayStats(String robotName) {
		this.robotName = robotName;
		this.isBatteryLow = false;
		this.isBatteryShutdown = false;
		this.isOverweight = false;
		this.batteryPercentage = 100;
	}

	public Boolean getIsBatteryShutdown() {
		return isBatteryShutdown;
	}

	private void setIsBatteryShutdown(Boolean isBatteryShutdown) {
		this.isBatteryShutdown = isBatteryShutdown;
		if(isBatteryShutdown)
			isBatteryShutdown.notifyAll();
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

	public void setOverweight(boolean isOverweight) {
		this.isOverweight = isOverweight;
	}

	public int getBatteryPercentage() {
		return batteryPercentage;
	}

	public void setBatteryPercentage(int batteryPercentage) {
		this.batteryPercentage = batteryPercentage;
		setBatteryLow(batteryPercentage < RobotConst.BATTERY_LOW_PERCENTAGE? true : false);
		setIsBatteryShutdown(batteryPercentage <= RobotConst.ROBOT_SHUTDOWN_BATTERY_PERCENTAGE? true : false);
	}

}