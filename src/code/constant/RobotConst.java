package code.constant;

public class RobotConst {

	public static final String OVERWEIGHT_MESSAGE 				= "Overweight";
	public static final String INVALID_BAR_CODE_MESSAGE 		= "Scan Failure";
	
	public static final int MAX_WEIGHT_CARRY_LIMIT_IN_KG 		= 10;

	public static final int ROBOT_TOTAL_BATTERY_DURATION_IN_SEC;
	public static final int ROBOT_WALK_DISTANCE_IN_METER_BEFORE_BATTERY_DRAINED		= 5000;
	public static final int ROBOT_WALKING_SPEED_IN_METER_PER_SEC					= 5000;
	
	static {
		ROBOT_TOTAL_BATTERY_DURATION_IN_SEC = ROBOT_WALK_DISTANCE_IN_METER_BEFORE_BATTERY_DRAINED / ROBOT_WALKING_SPEED_IN_METER_PER_SEC;
	}
	
}
