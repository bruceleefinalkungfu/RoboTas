package code.callback;

import java.util.Optional;

import code.bo.BarCode;
import code.tasks.impl.RobotBarCodeScanTask;

/**
 * Register the callback to {@link RobotBarCodeScanTask} to get the price
 * @author Anurag.Awasthi
 *
 */
public interface ScanBarCodeCallBack {

	/**
	 * @param barCode
	 * @param price : price.get() will throw NoSuchElementException if the bar code was invalid
	 */
	public void onPriceCalculated(BarCode barCode, Optional<Double> price);
	
}
