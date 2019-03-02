package code.util;

import java.util.Random;
import java.util.UUID;

import code.bo.BarCode;
import code.exception.BarCodeException;

public class BarCodeUtil {
	
	public static BarCode getBarCode(Object obj) {
		return new BarCode(UUID.randomUUID().toString(), new Object[] {obj});
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
		double price = 0;
		for(Object obj : data) {
			try { Thread.sleep(2000); } catch(Exception e) { }
			if(obj != null)
				price += obj.hashCode();
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
