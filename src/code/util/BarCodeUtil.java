package code.util;

import java.util.Random;
import java.util.UUID;

import code.bo.BarCode;

public class BarCodeUtil {
	
	static Random rd = new Random();

	public static BarCode getBarCode() {
		return new BarCode(UUID.randomUUID().toString(), null);
	}
	
	public static double getPrice(BarCode barCode) {
		try { Thread.sleep(2000); } catch(Exception e) { }
		return rd.nextInt(100);
	}
	
}
