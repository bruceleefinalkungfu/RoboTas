package code.test;

import code.bo.Robot;
import code.constant.CommonConst;
import code.util.BarCodeUtil;
import code.util.MathUtil;

/**
 * Not using JUnit. Don't wanna add a jar and upload it on Github 
 * since I didn't start the project as a maven or gradle proj
 * 
 * Robot is asked to scan 3 different bar codes one after another and then print all of their price 
 * @author Anurag.Awasthi
 *
 */
public class TestScenario5 {

	public static void main(String[] args) throws Exception {
		/**
		 * It takes 1 second to travel 1 meter. 
		 * Since one second equals to 1 millisecond now, it will take 5 second to travel 5000 meter
		 */
		CommonConst.MILLISECONDS_IN_ONE_SECOND = 1;
		Robot robot = RobotTestCLI.getRobot("Bot-Scenario-5");

		int barCodeSize1 = 2;
		int barCodeSize2 = 1;
		int barCodeSize3 = 0;

		RobotTestCLI.handleCommand(robot, "2" +CommonConst.NEW_LINE + barCodeSize1, true);
		RobotTestCLI.handleCommand(robot, "2" +CommonConst.NEW_LINE + barCodeSize2, true);
		RobotTestCLI.handleCommand(robot, "2" +CommonConst.NEW_LINE + barCodeSize3, true);
		System.out.println("Wait.....");
		Thread.sleep((MathUtil.round(barCodeSize1+barCodeSize2+barCodeSize3)) * BarCodeUtil.TIME_TO_PROCESS_BAR_CODE);
		RobotTestCLI.printMenu(robot, false);
		RobotTestCLI.handleCommand(robot, "7", true);
		System.exit(0);
	}
	
}
