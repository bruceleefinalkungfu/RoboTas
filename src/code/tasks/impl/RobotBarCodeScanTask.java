package code.tasks.impl;

import code.bo.BarCode;
import code.callback.ScanBarCodeCallBack;
import code.exception.BarCodeException;
import code.tasks.StoppableRobotTask;
import code.util.BarCodeUtil;
import code.util.LoggerUtil;

import static code.constant.RobotConst.INVALID_BAR_CODE_MESSAGE;

import java.util.Optional;

/**
 * Register a {@link ScanBarCodeCallBack} callback to receive the value of a particular bar code
 * @author Anurag.Awasthi
 *
 */
public class RobotBarCodeScanTask extends StoppableRobotTask {

	private BarCode barCode;
	volatile boolean taskFinished = false;
	private volatile double price = -1;
	private volatile boolean isValidBarCode;
	private ScanBarCodeCallBack callBack;
	private Object priceCalculatedLock;
	
	public RobotBarCodeScanTask(BarCode barCode) {
		this(barCode, (e, f) -> {} );
	}
	
	public RobotBarCodeScanTask(BarCode barCode, ScanBarCodeCallBack callBack) {
		super();
		this.barCode = barCode;
		this.isValidBarCode = true;
		this.callBack = callBack;
		this.priceCalculatedLock = new Object();
	}

	@Override
	public boolean isTaskFinished() {
		return super.isTaskFinished() || taskFinished;
	}

	@Override
	public void beforeTask() {
		LoggerUtil.log("Scanning the BarCode "+barCode.getBarCodeId());
	}

	@Override
	public void processTask() throws InterruptedException {
		try {
			this.price = BarCodeUtil.getPrice(barCode);
		} catch (BarCodeException e) {
			isValidBarCode = false;
		} finally {
			taskFinished = true;
			synchronized (priceCalculatedLock) {
				priceCalculatedLock.notifyAll();
			}
		}
	}

	@Override
	protected void afterTaskIsFinished() {
		if(isValidBarCode) {
			LoggerUtil.log("Barcode "+barCode.getBarCodeId()+"'s price is "+price);
			callBack.onPriceCalculated(barCode, Optional.of(price));
		}
		else {
			LoggerUtil.log(INVALID_BAR_CODE_MESSAGE+ "\nbecause "+barCode.getBarCodeId()+" is invalid");
			callBack.onPriceCalculated(barCode, Optional.ofNullable(null));
		}
	}

	@Override
	protected void onStopTask() { }

	/**
	 * It's a blocking call. For asynchronous processing, register a {@link ScanBarCodeCallBack} callback 
	 * @return
	 * @throws BarCodeException : It throws an exception if the bar code is invalid
	 */
	public double getPrice() throws BarCodeException {
		synchronized (priceCalculatedLock) {
			while( ! isTaskFinished() ) {
				try {
					priceCalculatedLock.wait(1000L);
				} catch (InterruptedException e) {
					throw new BarCodeException("Getting price of "+barCode.getBarCodeId()+" process got interrupted");
				}
			}
		}
		// while(price == -1 && isValidBarCode && !super.isTaskFinished());
		if(isValidBarCode)
			return price;
		else
			throw new BarCodeException("Invalid bar code "+barCode.getBarCodeId());
	}

}