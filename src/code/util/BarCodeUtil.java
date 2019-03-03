package code.util;

import java.util.Random;
import java.util.UUID;

import code.bo.BarCode;
import code.exception.BarCodeException;

public class BarCodeUtil {
	
	static Random rd = new Random();
	
	public static final int TIME_TO_PROCESS_BAR_CODE = 2000;
	
	public static BarCode getBarCode(Object obj) {
		return new BarCode(UUID.randomUUID().toString(), new Object[] {obj});
	}
	
	public static BarCode getBarCode(Object[] obj) {
		return new BarCode(UUID.randomUUID().toString(), obj);
	}
	
	public static double getPrice(BarCode barCode) throws BarCodeException {
		if(isBarCodeValid(barCode))
			return calculatePrice(barCode.getData());
		throw new BarCodeException("BarCode "+barCode.getBarCodeId()+" is invalid");
	}
	
	/**
	 * @param data : more data, more time it takes to calculate the price
	 * @return
	 */
	private static double calculatePrice(Object[] data) {
		int price = 0;
		for(Object obj : data) {
			try { Thread.sleep(TIME_TO_PROCESS_BAR_CODE); } catch(Exception e) { }
			if(obj != null)
				price += rd.nextInt(10);
		}
		return price;
	}
	
	/**
	 * If barCode doesn't have any data it is considered invalid barcode
	 * @param barCode
	 * @return
	 */
	private static boolean isBarCodeValid(BarCode barCode) {
		Object[] data = barCode.getData();
		return data != null && data.length != 0 && data[0] != null;
	}
	
}
