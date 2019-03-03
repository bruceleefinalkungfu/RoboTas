package code.test;

import code.bo.Robot;
import code.constant.CommonConst;
import code.util.MathUtil;

/**
 * Not using JUnit. Don't wanna add a jar and upload it on Github 
 * since I didn't start the project as a maven or gradle proj
 * 
 * Robot carries 12 KG weight for 1 KM (or at least it tries to) and then its status is displayed
 * @author Anurag.Awasthi
 *
 */
public class TestScenario3 {

	public static void main(String[] args) throws Exception {
		/**
		 * It takes 1 second to travel 1 meter. 
		 * Since one second equals to 1 millisecond now, it will take 5 second to travel 5000 meter
		 */
		CommonConst.MILLISECONDS_IN_ONE_SECOND = 1;
		Robot robot = RobotTestCLI.getRobot("Bot-Scenario-3");
		/**
		 * Walk 1 KM
		 */
		double walkKM = 1;
		
		int carryWeightKG = 12;
		
		RobotTestCLI.handleCommand(robot, "4" +CommonConst.NEW_LINE
				+ carryWeightKG + CommonConst.NEW_LINE + walkKM, true);
		System.out.println("Wait.....");
		Thread.sleep((MathUtil.round(walkKM) + 1) * 1000);
		RobotTestCLI.printMenu(robot, false);
		System.exit(0);
	}
	
}
