package code.util;

public class MathUtil {

	public static int round(double val) {
		if(val > 0)
			return (int) (val + 0.5);
		else
			return (int) (val - 0.5);
	}
	
}
