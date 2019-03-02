package code.bo;

import java.util.LinkedList;
import java.util.Queue;

import code.constant.RobotConst;

public class HeadUpDisplayStats {

	private volatile boolean isBatteryLow;
	private volatile boolean isOverweight;
	private volatile int batteryPercentage;
	private Queue<String> barCodeIdQueue;
	private Queue<Integer> barCodeProductPrice;

	public HeadUpDisplayStats() {
		barCodeIdQueue = new LinkedList<>();
		barCodeProductPrice = new LinkedList<>();
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

	public Queue<String> getBarCodeIdQueue() {
		return barCodeIdQueue;
	}

	public void setBarCodeIdQueue(Queue<String> barCodeIdQueue) {
		this.barCodeIdQueue = barCodeIdQueue;
	}

	public Queue<Integer> getBarCodeProductPrice() {
		return barCodeProductPrice;
	}

	public void setBarCodeProductPrice(Queue<Integer> barCodeProductPrice) {
		this.barCodeProductPrice = barCodeProductPrice;
	}
}
