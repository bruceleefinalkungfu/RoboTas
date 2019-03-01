package code.bo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import code.constant.RobotConst;
import code.util.MathUtil;

public class Robot {

	private AtomicInteger remainingBatteryInMiliSecond;
	private AtomicInteger carryingWeight;
	private AtomicInteger distanceToWalkInMeter;
	private AtomicInteger totalDistanceWalked;
	
	private ExecutorService pool;
	
	private HeadUpDisplayStats stats;
	
	private Robot(int noOfThreads) {
		remainingBatteryInMiliSecond = new AtomicInteger( RobotConst.ROBOT_TOTAL_BATTERY_LIFE_IN__MILISEC );
		carryingWeight = new AtomicInteger(0);
		totalDistanceWalked = new AtomicInteger(0);
		distanceToWalkInMeter = new AtomicInteger(0);
		pool = Executors.newFixedThreadPool(noOfThreads);
		stats = new HeadUpDisplayStats();
	}
	
	public static class Builder {
		private int handleNumberOfParallelTasks = RobotConst.CAPABLITY_TO_RUN_TOTAL_TASKS_IN_PARALLEL_DEFAULT_VAL;
		public Builder handleNumberOfParallelTasks(int handleNumberOfParallelTasks) {
			this.handleNumberOfParallelTasks = handleNumberOfParallelTasks;
			return this;
		}
		public Robot build() { return new Robot(this.handleNumberOfParallelTasks); }
	}
	

	public int getTotalDistanceWalked() {
		return totalDistanceWalked.get();
	}

	public int getRemainingBatteryInMiliSecond() {
		return remainingBatteryInMiliSecond.get();
	}
	
	public int getRemainingBatteryInPercentage() {
		return stats.getBatteryPercentage();
	}

	public int getCarryingWeight() {
		return carryingWeight.get();
	}

	public int getDistanceToWalkInMeter() {
		return distanceToWalkInMeter.get();
	}

	@Deprecated
	public void chargeBatteryInMilliSecond(int milliSecond) {
		drainBatteryInMilliSecond(milliSecond * -1);
	}
	
	public void drainBatteryInMilliSecond(int milliSecond) {
		this.remainingBatteryInMiliSecond.getAndAdd(milliSecond * -1);
		stats.setBatteryPercentage(
				MathUtil.round( (remainingBatteryInMiliSecond.get() * 100.0) / RobotConst.ROBOT_TOTAL_BATTERY_LIFE_IN__MILISEC )
			);
	}

	public void addWeight(int weight) {
		int wt = this.carryingWeight.getAndAdd(weight);
		stats.setOverweight( wt > RobotConst.MAX_WEIGHT_CARRY_LIMIT_IN_KG ? true : false);
	}

	public void dropWeight(int weight) {
		addWeight(weight * -1);
	}

	public void addDistanceToWalkInMeter(int distanceInMeter) {
		this.distanceToWalkInMeter.getAndAdd(distanceInMeter);
	}

	public void subtractDistanceToWalkInMeter(int distanceInMeter) {
		addDistanceToWalkInMeter(distanceInMeter * -1);
	}

	public HeadUpDisplayStats getStats() {
		return stats;
	}
	
}