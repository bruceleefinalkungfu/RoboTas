package code.tasks.impl;

import code.bo.Robot;
import code.util.LoggerUtil;

/**
 * Robot doesn't drop the weight after it finishes carrying the weight to its destination
 * But once its {@link RobotTask#stopTask()} is called, it drops the weight too
 * @author Anurag.Awasthi
 *
 */
public class RobotCarryWeightTask extends RobotWalkTask {

	private double weight;
	
	public RobotCarryWeightTask(int walkInMeters, int weightInKGToCarry, Robot robot) {
		super(walkInMeters, robot);
		this.weight = weightInKGToCarry;
	}

	@Override
	public void beforeTask() {
		super.beforeTask();
		robot.addWeightInKiloGram(weight);
	}

	@Override
	public boolean isTaskFinished() {
		return isTaskStopped || (weight == 0 && super.isTaskFinished());
	}

	/*
	 * Robot drops the weight too
	 */
	@Override
	protected void onStopTask() {
		robot.dropWeightInKiloGram(weight);
		LoggerUtil.log(robot.getRobotName()+" dropped "+weight+" KG weight");
	}

	
	@Override
	protected void afterTaskIsFinished() {
		LoggerUtil.log(robot.getRobotName()+" carried "+weight+" KG for "+(walkInMeters/1000.0)+" KM."
				+ "But it still is holding the weight");
	}
	
}