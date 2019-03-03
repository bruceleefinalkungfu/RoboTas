package code.test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

import code.bo.BarCode;
import code.bo.Robot;
import code.callback.ScanBarCodeCallBack;
import code.constant.CommonConst;
import code.service.RobotService;
import code.service.impl.RobotServiceImpl;
import code.tasks.RobotTask;
import code.util.BarCodeUtil;

/**
 * Use CLI to test it.
 * <pre>
 * 	When prompted to choose command
 *  Choose 3. Walk
 *  Specify how many KM to walk
 *  Then press 0 and enter again and again to see Robot's latest stats.
 * </pre>
 * @author Anurag.Awasthi
 *
 */
public class RobotTestCLI {

	public static Scanner sc = new Scanner(System.in);
	public static List<RobotTask> allTheTasks = new ArrayList<>();
	
	public static RobotService robotService = new RobotServiceImpl();
	
	public static final int ROBOT_BOX_LENGTH		= 55;
	
	private static Map<String, Optional<Double>> barCodeWithValue = new LinkedHashMap<>();
	
	public static ScanBarCodeCallBack callBack = (a, b) -> {
		barCodeWithValue.put(a.getBarCodeId(), b);
	};
	
	public static Robot getRobot(String roboName) {
		return new Robot.Builder().name(roboName).handleNumberOfParallelTasks(2).build();
	}
	
	public static void main(String[] args) {
		Robot robot = getRobot("Test-Bot");
		printMenu(robot);
		for(;;) {
			String command = sc.nextLine();
			handleCommand(robot, command, false);
		}
	}
	
	/**
	 * @param robot
	 * @param command : 0. print Robot. 1. Lift weight. 2. Scan BarCode. 3. Walk. 4. Carry weight. 5. stop task. 6. Get price of bar code. 7. Get all the price
	 * @param batchProcess : set it true and send all the commands in command separated by New line
	 */
	public static void handleCommand(Robot robot, String command, boolean batchProcess) {
		String[] multiCommands = command.split(CommonConst.NEW_LINE);
		try {
			int option = batchProcess ? Integer.parseInt(multiCommands[0]) : Integer.parseInt(command);
			if(option < 0 || option > 7)
				throw new NumberFormatException();
			switch (option) {
				case 0:
					break;
				case 1: {
					int weighKG = batchProcess ? Integer.parseInt(multiCommands[1]) : getInt("How much weight to pick up?");
					robotService.liftWeight(robot, weighKG);
					break;
				}
				case 2 : {
					int howMuchDataInBarCode = batchProcess ? Integer.parseInt(multiCommands[1]) : getInt("How much data is in barCode? 0 or less means invalid. More data takes more time to process");
					Object[] data = new Object[] {null};
					if(howMuchDataInBarCode > 0) {
						data = new Object[howMuchDataInBarCode];
						data[0] = "DUMMY_DATA";
					}
					BarCode barCode = BarCodeUtil.getBarCode(data);
					robotService.scanBarCode(robot, barCode, callBack);
					break;
				}
				case 3: {
					double walkKm = batchProcess ? Double.parseDouble(multiCommands[1]) : getDouble("How many KM to walk?");
					robotService.walk(robot, walkKm);
					break;
				}
				case 4: {
					int weighKG = batchProcess ? Integer.parseInt(multiCommands[1]) : getInt("How much weight to pick up?");
					double walkKm = batchProcess ? Double.parseDouble(multiCommands[2]) : getDouble("How many KM to walk with the weight?");
					robotService.carry(robot, walkKm, weighKG);
					break;
				}
				case 5: {
					String taskId = batchProcess ? multiCommands[1] : getString("Enter taskId");
					robot.stopTask(taskId);
					break;
				}
				case 6: {
					String barCodeId = batchProcess ? multiCommands[1] : getString("Enter barCodeId");
					Optional<Double> val = barCodeWithValue.get(barCodeId);
					if(val == null)
						System.out.println("No such bar code is done scanned yet");
					else {
						if(val.isPresent())
							System.out.println("Bar code "+barCodeId+" price = "+val.get());
						else
							System.out.println("Bar code "+barCodeId+" is invalid");
					}
					break;
				}
				case 7: {
					for(Map.Entry<String, Optional<Double>> entry : barCodeWithValue.entrySet()) {
						String barCodeId = entry.getKey();
						Optional<Double> val = entry.getValue();
						if(val.isPresent())
							System.out.println("Bar code "+barCodeId+" price = "+val.get());
						else
							System.out.println("Bar code "+barCodeId+" is invalid");
					}
					break;
				}
				default:
					break;
			}
			if(! batchProcess) {
				try { Thread.sleep(CommonConst.MILLISECONDS_IN_ONE_SECOND); } catch(Exception e) {}
				if(command.equals("0"))
					printMenu(robot, false);
				else
					printMenu(robot);
			}
		} catch(NumberFormatException | NullPointerException e) {
			printMenu(robot);
			System.out.println("Please choose a valid option");
		}	
	}

	public static void printMenu(Robot robot) {
		printMenu(robot, true);
	}
	
	public static void printMenu(Robot robot, boolean seeCommands) {
		String menu = "";
		menu += printRobotBox("", '_', "");
		menu += printRobotBox("| Name | "+robot.getRobotName(), ' ', "|");
		menu += printRobotBox("|", '-', "|");
		menu += printRobotBox("| Battery:" + robot.getRemainingBatteryInPercentage() +"%  | Red light:" + (robot.getStats().isBatteryLow() ? "ON" : "OFF"), ' ', "|");
		menu += printRobotBox("| Weight:"+ robot.getCarryingWeightInKiloGram()+" KG   | Overweight:" + (robot.getStats().isOverweight() ? "YES" : "NO"), ' ', "|");
		menu += printRobotBox("| Remaining distance:" + robot.getDistanceToWalkInKM()+" KM", ' ', "|");
		menu += printRobotBox("| Distance walked:" + robot.getTotalDistanceWalkedInKM()+" KM", ' ', "|");
		List<String> runningTasks = robot.getRunningTasks();
		if(! runningTasks.isEmpty())
			menu += printRobotBox("|", '-', "|");
		for(String task : runningTasks)
			menu += printRobotBox("|"+task.replaceAll("Robot", "").replaceAll("Task", ""), ' ' , "|");
		if(seeCommands) {
			menu += printRobotBox("|", '-', "|");
			menu += printRobotBox("| Choose command", ' ', "|");
			menu += printRobotBox("| 0. Refresh Robot state   | 3. walk", ' ', "|");
			menu += printRobotBox("| 1. Pick up weight        | 4. Carry weight", ' ', "|");
			menu += printRobotBox("| 2. Scan a bar code       | 5. Stop a task", ' ', "|");
			menu += printRobotBox("| 6. Get price of bar code | 7. Get all barcode price", ' ', "|");
		}
		menu += printRobotBox("|", '_', "|");
		System.out.println(menu);
	}

	private static double getDouble(String msg) {
		System.out.println(msg);
		return Double.parseDouble( sc.nextLine() );
	}
	
	private static int getInt(String msg) {
		System.out.println(msg);
		return Integer.parseInt( sc.nextLine() );
	}
	
	public static String getString(String msg) {
		System.out.println(msg);
		return sc.nextLine();
	}
	
	public static String printRobotBox(String prefix, char charToRepeat, String suffix) {
		StringBuilder sb = new StringBuilder();
		int length = ROBOT_BOX_LENGTH - prefix.length() - suffix.length(); 
		for(int i=0 ; i<length ; i++) {
			sb.append(charToRepeat);
		}
		//System.out.println(prefix + sb.toString() + suffix);
		return prefix + sb.toString() + suffix + CommonConst.NEW_LINE;
	}
}
