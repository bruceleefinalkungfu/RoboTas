package code.service;

import code.bo.BarCode;
import code.bo.Robot;
import code.callback.ScanBarCodeCallBack;
import code.tasks.RobotTask;
import code.tasks.impl.RobotBarCodeScanTask;
import code.tasks.impl.RobotCarryWeightTask;
import code.tasks.impl.RobotLiftWeightTask;
import code.tasks.impl.RobotWalkTask;

/**
 * Returned {@link RobotTask} can be used to stop the task if possible
 * 
 * All the services are non blocking
 * @author Anurag.Awasthi
 *
 */
public interface RobotService {

	public RobotWalkTask walk(Robot robot, double km);

	public RobotCarryWeightTask carry(Robot robot, double km, int weightInKG);

	public RobotLiftWeightTask liftWeight(Robot robot, int weightInKG);

	/**
	 * @param robot
	 * @param barCode
	 * @return : call {@link RobotBarCodeScanTask#getPrice()} to get the price, although that call is blocking.
	 * 				Use {@link R} 
	 */
	public RobotBarCodeScanTask scanBarCode(Robot robot, BarCode barCode);

	/**
	 * @param robot 
	 * @param barCode
	 * @param callBack : register a callback to get the price
	 * @return : call {@link RobotBarCodeScanTask#getPrice()} to get the price, although that call is blocking
	 */
	public RobotBarCodeScanTask scanBarCode(Robot robot, BarCode barCode, ScanBarCodeCallBack callBack);

}
