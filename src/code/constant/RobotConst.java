package code.constant;

public class RobotConst {

	public static final String OVERWEIGHT_MESSAGE 				= "Overweight";
	public static final String INVALID_BAR_CODE_MESSAGE 		= "Scan Failure";

	public static final int MAX_WEIGHT_CARRY_LIMIT_IN_KG 		= 10;
	public static final int MAX_WEIGHT_CARRY_LIMIT_IN_GRAM 		= MAX_WEIGHT_CARRY_LIMIT_IN_KG * 1000;

	public static final int BATTERY_LOW_PERCENTAGE				= 15;
	public static final int ROBOT_SHUTDOWN_BATTERY_PERCENTAGE	= 1;

	public static final int ROBOT_TOTAL_WALKING_BATTERY_LIFE_IN__MILISEC			= 5000;
	public static final int ROBOT_WALK_DISTANCE_IN_METER_BEFORE_BATTERY_DRAINED		= 5000;
	public static final int MILLISECONDS_OF_BATTERY_DRAINED_IN_ONE_METER;
	
	public static final int CAPABLITY_TO_RUN_TOTAL_TASKS_IN_PARALLEL_DEFAULT_VAL	= 4;
	
	static {
		MILLISECONDS_OF_BATTERY_DRAINED_IN_ONE_METER = ROBOT_TOTAL_WALKING_BATTERY_LIFE_IN__MILISEC / ROBOT_WALK_DISTANCE_IN_METER_BEFORE_BATTERY_DRAINED;
	}
	
}
