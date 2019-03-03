package code.tasks;

import code.constant.CommonConst;
import code.util.LoggerUtil;

/**
 * <pre>
 * Robot Task life cycle
 * {@link RobotTask#beforeTask()} => is called once before the task is processed
 * {@link RobotTask#processTask()} => is called in an infinite loop
 * {@link RobotTask#isTaskFinished()} => if this returns true, control breaks out of the loop
 * {@link RobotTask#stopTask()} => it should change the state of this object in a way,
 * 	 		that {@link RobotTask#isTaskFinished()} shall return true
 * {@link RobotTask#afterTask()} => Called after {@link RobotTask#isTaskFinished()} returns true
 * </pre>
 * @author Anurag.Awasthi
 *
 */
public interface RobotTask {
	
	/**
	 * Some condition which tells if this task is finished. 
	 * @return
	 */
	boolean isTaskFinished();

	/**
	 * Before robot starts the task
	 */
	public void beforeTask();
	
	/**
	 * Task is processed until {@link RobotTask#isTaskFinished()} returns true
	 * 
	 */
	public void processTask() throws Exception;
	
	/**
	 * After {@link RobotTask#isTaskFinished()} returns true and task is finished
	 */
	public void afterTask();
	
	/**
	 * Change the state of this object in a way that {@link RobotTask#isTaskFinished()} returns true
	 * Do not call the default method, override it.
	 */
	default void stopTask() {
		LoggerUtil.log("The task can not be stopped.");
	}
	
}
