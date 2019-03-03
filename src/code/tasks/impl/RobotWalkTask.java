package code.tasks.impl;

import java.util.concurrent.atomic.AtomicInteger;

import code.bo.Robot;
import code.constant.CommonConst;
import code.tasks.StoppableRobotTask;
import code.util.LoggerUtil;
import code.util.MathUtil;

import static code.constant.CommonConst.EXTRA_BATTERY_PERCENTAGE_DRAINED_PER_KG;
import static code.constant.RobotConst.MILLISECONDS_OF_BATTERY_DRAINED_IN_ONE_METER;

/**
 * Provide the distance to walk in meters
 * @author Anurag.Awasthi
 *
 */
public class RobotWalkTask extends StoppableRobotTask {

	protected int walkInMeters;
	protected int remainingDistance;
	protected Robot robot;
	
	private static final int UPDATE_BATTERY_DRAIN_STATS_IN_METERS			= 100;
	
	public RobotWalkTask(int walkInMeters, Robot robot) {
		super();
		this.walkInMeters = walkInMeters;
		this.remainingDistance = walkInMeters;
		this.robot = robot;
	}

	@Override
	public boolean isTaskFinished() {
		return super.isTaskFinished() || remainingDistance <= 0;
	}

	@Override
	protected void onStopTask() {
		LoggerUtil.log("Walking task of "+(walkInMeters/1000.0)+" KM is stopped. "
				+robot.getRobotName() +" still had "+(remainingDistance/1000.0)+" KM to go");
	}

	@Override
	public void stopTask() {
		super.stopTask();
		robot.subtractDistanceToWalkInMeter(remainingDistance);
		remainingDistance = 0;
	}
	
	@Override
	public void beforeTask() {
		robot.addDistanceToWalkInMeter(walkInMeters);
	}

	@Override
	public void processTask() {
		if(! robot.getStats().isOverweight()) {
			int totalWalkedInMeter = 0;
			for(int i=0 ; i<UPDATE_BATTERY_DRAIN_STATS_IN_METERS ; i++) {
				if(walkOneMeter())
					totalWalkedInMeter++;
				else
					break;
			}
			int walkingBatteryConsumption = totalWalkedInMeter * MILLISECONDS_OF_BATTERY_DRAINED_IN_ONE_METER;
			// Take weight into account
			walkingBatteryConsumption += MathUtil.round(
				(walkingBatteryConsumption * robot.getCarryingWeightInKiloGram() * EXTRA_BATTERY_PERCENTAGE_DRAINED_PER_KG) / 100.0
			);
			robot.drainBatteryInMilliSecond(walkingBatteryConsumption);
			try { Thread.sleep(UPDATE_BATTERY_DRAIN_STATS_IN_METERS * CommonConst.MILLISECONDS_IN_ONE_SECOND); } catch(InterruptedException e) {Thread.currentThread().interrupt();}
		}
	}

	/**
	 * @return : returns false if it failled to walk or true if it successfully walked 1 meter
	 */
	private boolean walkOneMeter() {
		if(remainingDistance != 0) {
			remainingDistance--;
			robot.subtractDistanceToWalkInMeter(1);
			return true;
		}
		return false;
	}
	
	@Override
	protected void afterTaskIsFinished() {
		LoggerUtil.log(robot.getRobotName()+" finished walking "+(walkInMeters/1000.0)+" KM");
	}
	
}