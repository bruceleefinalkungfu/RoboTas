package code.tasks;

/**
 * This class should be extended by those tasks 
 * 	which can be stopped before they're finished
 * <pre>
 * It modifies the {@link RobotTask} life cycle
 * 		by removing 3 methods from cycle,
 * {@link RobotTask#afterTask()}
 * {@link RobotTask#stopTask()}
 * {@link RobotTask#isTaskFinished()}
 * 
 * and introducing 2 new abstract methods into the cycle.
 * {@link RobotTask#onStopTask()}
 * {@link RobotTask#afterTaskIsFinished()}
 * </pre>
 * @author Anurag.Awasthi
 *
 */
public abstract class StoppableRobotTask implements RobotTask {

	protected boolean isTaskStopped;

	@Override
	public void stopTask() {
		isTaskStopped = true;
		onStopTask();
	}
	
	@Override
	public void afterTask() {
		if( ! isTaskStopped)
			afterTaskIsFinished();
	}

	@Override
	public boolean isTaskFinished() {
		return isTaskStopped;
	}
	
	/**
	 * If this task is stopped and you need to clear some resource or change the state of some field
	 * you should do that in this method
	 */
	protected abstract void onStopTask();

	/**
	 * {@link RobotTask#afterTask()} is called regardless of whether a task was stopped or finished,
	 * hence the need of this method which is called only after a task is finished and not stopped
	 */
	protected abstract void afterTaskIsFinished();
	
}
