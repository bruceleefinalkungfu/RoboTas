package code.bo;

import code.constant.RobotConst;

public class HeadUpDisplayStats {

	private String robotName;
	
	private volatile boolean isBatteryLow;
	private volatile boolean isOverweight;
	private volatile int batteryPercentage;

	public HeadUpDisplayStats(String robotName) {
		this.robotName = robotName;
	}

	public String getRobotName() {
		return this.robotName;
	}
	
	public boolean isBatteryLow() {
		return isBatteryLow;
	}

	public void setBatteryLow(boolean isBatteryLow) {
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
	}

}