package code.tasks.impl;

import code.bo.Robot;
import code.tasks.StoppableRobotTask;
import code.util.LoggerUtil;

/**
 * Robot is asked to pick up or hold a weight only. Not to walk with it
 * but technically if robot is walking already, then it will be walking with the weight too
 * @author Anurag.Awasthi
 *
 */
public class RobotLiftWeightTask extends StoppableRobotTask {

	private int weight;
	private Robot robot;
	
	public RobotLiftWeightTask(Robot robot, int weightInKG) {
		this.weight = weightInKG;
		this.robot = robot;
	}

	@Override
	public void onStopTask() {
		robot.dropWeightInKiloGram(weight);
		LoggerUtil.log(robot.getRobotName()+" finally dropped the "+weight+" KG weight");
		weight = 0;
	}

	@Override
	public void beforeTask() {
		robot.addWeightInKiloGram(weight);
		LoggerUtil.log(robot.getRobotName()+" picked up a "+weight+" KG weight");
	}

	@Override
	public void processTask() {
		
	}

	@Override
	public void afterTaskIsFinished() { }
}
