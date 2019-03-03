package code.service.impl;

import code.bo.BarCode;
import code.bo.Robot;
import code.callback.ScanBarCodeCallBack;
import code.service.RobotService;
import code.tasks.RobotTask;
import code.tasks.impl.RobotBarCodeScanTask;
import code.tasks.impl.RobotCarryWeightTask;
import code.tasks.impl.RobotLiftWeightTask;
import code.tasks.impl.RobotWalkTask;
import code.util.MathUtil;

/**
 * Returned {@link RobotTask} can be used to stop the task if possible
 * 
 * All the services are non blocking. You can register a call back on RobotTask to get the response when ready 
 * @author Anurag.Awasthi
 *
 */
public class RobotServiceImpl implements RobotService {

	@Override
	public RobotWalkTask walk(Robot robot, double km) {
		RobotWalkTask walkTask = new RobotWalkTask(MathUtil.round(km * 1000), robot);
		robot.executeTask(walkTask);
		return walkTask;
	}

	@Override
	public RobotCarryWeightTask carry(Robot robot, double km, int weightInKG) {
		RobotCarryWeightTask carryWeightTask = new RobotCarryWeightTask(MathUtil.round(km * 1000), weightInKG , robot);
		robot.executeTask(carryWeightTask);
		return carryWeightTask;
	}

	@Override
	public RobotLiftWeightTask liftWeight(Robot robot, int weightInKG) {
		RobotLiftWeightTask liftWeightTask = new RobotLiftWeightTask(robot, weightInKG);
		robot.executeTask(liftWeightTask);
		return liftWeightTask;
	}

	/**
	 * @param robot
	 * @param barCode
	 * @return : call {@link RobotBarCodeScanTask#getPrice()} to get the price, although that call is blocking.
	 * 				For async processing, use {@link RobotService#scanBarCode(Robot, BarCode, ScanBarCodeCallBack)} instead 
	 */
	@Override
	public RobotBarCodeScanTask scanBarCode(Robot robot, BarCode barCode) {
		RobotBarCodeScanTask barCodeScanTask = new RobotBarCodeScanTask(barCode);
		robot.executeTask(barCodeScanTask);
		return barCodeScanTask;
	}

	/**
	 * @param robot 
	 * @param barCode
	 * @param callBack : register a callback to get the price
	 * @return : call {@link RobotBarCodeScanTask#getPrice()} to get the price, although that call is blocking
	 */
	@Override
	public RobotBarCodeScanTask scanBarCode(Robot robot, BarCode barCode, ScanBarCodeCallBack callBack) {
		RobotBarCodeScanTask barCodeScanTask = new RobotBarCodeScanTask(barCode, callBack);
		robot.executeTask(barCodeScanTask);
		return barCodeScanTask;
	}

}
