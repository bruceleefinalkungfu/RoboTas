package code.constant;

public class RobotConst {

	public static final String OVERWEIGHT_MESSAGE 				= "Overweight";
	public static final String INVALID_BAR_CODE_MESSAGE 		= "Scan Failure";
	
	public static final int MAX_WEIGHT_CARRY_LIMIT_IN_KG 		= 10;
	
	public static final int BATTERY_LOW_PERCENTAGE				= 15;

	public static final int ROBOT_TOTAL_BATTERY_LIFE_IN__MILISEC;
	public static final int ROBOT_WALK_DISTANCE_IN_METER_BEFORE_BATTERY_DRAINED		= 5000;
	public static final int ROBOT_WALKING_SPEED_IN_METER_PER_SEC					= 5000;
	
	public static final int CAPABLITY_TO_RUN_TOTAL_TASKS_IN_PARALLEL_DEFAULT_VAL	= 4;
	
	static {
		ROBOT_TOTAL_BATTERY_LIFE_IN__MILISEC = (ROBOT_WALK_DISTANCE_IN_METER_BEFORE_BATTERY_DRAINED / ROBOT_WALKING_SPEED_IN_METER_PER_SEC) * 1000;
	}
	
}
