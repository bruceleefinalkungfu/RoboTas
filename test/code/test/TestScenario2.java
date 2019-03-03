package code.test;

import code.bo.Robot;
import code.constant.CommonConst;
import code.util.BarCodeUtil;
import code.util.MathUtil;

/**
 * Not using JUnit. Don't wanna add a jar and upload it on Github 
 * since I didn't start the project as a maven or gradle proj
 * 
 * Robot carries 3 KG weight for 2 KM and then its status is displayed
 * @author Anurag.Awasthi
 *
 */
public class TestScenario2 {

	public static void main(String[] args) throws Exception {
		/**
		 * It takes 1 second to travel 1 meter. 
		 * Since one second equals to 1 millisecond now, it will take 5 second to travel 5000 meter
		 */
		CommonConst.MILLISECONDS_IN_ONE_SECOND = 1;
		Robot robot = RobotTestCLI.getRobot("Bot-Scenario-2");
		/**
		 * Walk 2 KM
		 */
		double walkKM = 2;
		
		int carryWeightKG = 3;
		
		RobotTestCLI.handleCommand(robot, "4" +CommonConst.NEW_LINE
				+ carryWeightKG + CommonConst.NEW_LINE + walkKM, true);
		System.out.println("Wait.....");
		Thread.sleep((MathUtil.round(walkKM) + 3) * 1000);
		RobotTestCLI.printMenu(robot, false);
		System.exit(0);
	}
	
}