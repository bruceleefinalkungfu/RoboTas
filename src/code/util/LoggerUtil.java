package code.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public class LoggerUtil {

	static StringWriter sw = new StringWriter();
	static PrintWriter pw = new PrintWriter(sw);
	
	public static void log(String msg) {
		System.out.println(msg);
	}

	public static void log(Exception e) {
		e.printStackTrace(pw);
		String stackTrace = sw.toString(); // stack trace as a string
		log(e.getMessage());
		log(stackTrace);
	}
	
}
