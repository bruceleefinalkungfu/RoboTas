package code.test;

import code.bo.Robot;
import code.constant.CommonConst;
import code.util.MathUtil;

/**
 * Not using JUnit. Don't wanna add a jar and upload it on Github 
 * since I didn't start the project as a maven or gradle proj
 * 
 * Robot is asked to walk 4000 Meter and one second later, it is to pick up 6 KG weight
 *   another 1 second later and it has to carry a 6 KG weight for 5 KM  
 * @author Anurag.Awasthi
 *
 */
public class TestScenario4 {

	public static void main(String[] args) throws Exception {
		/**
		 * It takes 1 second to travel 1 meter. 
		 * Since one second equals to 1 millisecond now, it will take 5 second to travel 5000 meter
		 */
		CommonConst.MILLISECONDS_IN_ONE_SECOND = 1;
		Robot robot = RobotTestCLI.getRobot("Bot-Scenario-4");
		/**
		 * Walk 4 KM
		 */
		double walkKM1 = 4;
		double walkKM2 = 5;

		int pickUpWeightKG1 = 6;
		int pickUpWeightKG2 = 6;
		RobotTestCLI.handleCommand(robot, "3" +CommonConst.NEW_LINE + walkKM1, true);
		Thread.sleep(1000);
		RobotTestCLI.handleCommand(robot, "1" +CommonConst.NEW_LINE + pickUpWeightKG1, true);
		Thread.sleep(1000);
		RobotTestCLI.handleCommand(robot, "4" +CommonConst.NEW_LINE + pickUpWeightKG2
				+ CommonConst.NEW_LINE + walkKM2, true);
		System.out.println("Wait.....");
		Thread.sleep((MathUtil.round(walkKM1)) * 1000);
		RobotTestCLI.printMenu(robot, false);
		System.exit(0);
	}
	
}