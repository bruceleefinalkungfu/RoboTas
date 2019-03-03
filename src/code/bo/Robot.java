package code.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import code.constant.RobotConst;
import code.util.LoggerUtil;
import code.util.MathUtil;

/**
 * @author Anurag.Awasthi
 *
 */
public class Robot {

	private AtomicInteger remainingBatteryInMiliSecond;
	private AtomicInteger carryingWeightInGram;
	private AtomicInteger distanceToWalkInMeter;
	private AtomicInteger totalDistanceWalked;
	
	private ExecutorService pool;
	
	private HeadUpDisplayStats stats;
	
	private Robot(String roboName, int noOfThreads) {
		remainingBatteryInMiliSecond = new AtomicInteger( RobotConst.ROBOT_TOTAL_WALKING_BATTERY_LIFE_IN__MILISEC );
		stats = new HeadUpDisplayStats(roboName);
		carryingWeightInGram = new AtomicInteger(0);
		totalDistanceWalked = new AtomicInteger(0);
		distanceToWalkInMeter = new AtomicInteger(0);
		pool = Executors.newFixedThreadPool(noOfThreads + 1);
	}
	
	public static class Builder {
		private int handleNumberOfParallelTasks = RobotConst.CAPABLITY_TO_RUN_TOTAL_TASKS_IN_PARALLEL_DEFAULT_VAL;
		private String name = "";
		public Builder handleNumberOfParallelTasks(int handleNumberOfParallelTasks) {
			this.handleNumberOfParallelTasks = handleNumberOfParallelTasks;
			return this;
		}
		public Builder name(String name) { this.name = name; return this; }
		private String getName() {
			if(name == null || name.trim().isEmpty()) 
				return "R2D2_"+new Date().getTime();
			return name;
		}
		public Robot build() { return new Robot(getName(), this.handleNumberOfParallelTasks); }
	}
	

	public double getTotalDistanceWalkedInKM() {
		return totalDistanceWalked.get() / 1000.0;
	}

	public int getRemainingBatteryInMiliSecond() {
		return remainingBatteryInMiliSecond.get();
	}
	
	public int getRemainingBatteryInPercentage() {
		return stats.getBatteryPercentage();
	}

	public int getCarryingWeightInGram() {
		return carryingWeightInGram.get();
	}

	public double getCarryingWeightInKiloGram() {
		return carryingWeightInGram.get() / 1000.0;
	}

	public int getDistanceToWalkInMeter() {
		return distanceToWalkInMeter.get();
	}

	public double getDistanceToWalkInKM() {
		return distanceToWalkInMeter.get() / 1000.0;
	}

	@Deprecated
	private void chargeBatteryInMilliSecond(int milliSecond) {
		drainBatteryInMilliSecond(milliSecond * -1);
	}
	
	public void drainBatteryInMilliSecond(int milliSecond) {
		this.remainingBatteryInMiliSecond.addAndGet(milliSecond * -1);
		stats.setBatteryPercentage(
				MathUtil.round( (remainingBatteryInMiliSecond.get() * 100.0) / RobotConst.ROBOT_TOTAL_WALKING_BATTERY_LIFE_IN__MILISEC )
			);
	}


	public void addWeightInKiloGram(double weight) {
		addWeightInGram(MathUtil.round(weight * 1000));
	}
	
	/**
	 * @param weight : weight has to be in gram
	 */
	private void addWeightInGram(int weight) {
		int wt = this.carryingWeightInGram.addAndGet(weight);
		stats.setOverweight( wt > RobotConst.MAX_WEIGHT_CARRY_LIMIT_IN_GRAM ? true : false);
		if(stats.isOverweight())
			LoggerUtil.log("Robot "+getRobotName()+" will not be able to walk because it has " 
					+this.getCarryingWeightInKiloGram() +"kg weight and It can't carry more than "+RobotConst.MAX_WEIGHT_CARRY_LIMIT_IN_KG);
	}

	public void dropWeightInKiloGram(double weight) {
		addWeightInKiloGram(weight * -1);
	}

	public void addDistanceToWalkInMeter(int distanceInMeter) {
		this.distanceToWalkInMeter.addAndGet(distanceInMeter);
	}

	public void subtractDistanceToWalkInMeter(int distanceInMeter) {
		addDistanceToWalkInMeter(distanceInMeter * -1);
		this.totalDistanceWalked.addAndGet(distanceInMeter);
	}

	public HeadUpDisplayStats getStats() {
		return stats;
	}
	
	public String getRobotName() {
		return stats.getRobotName();
	}
	
}