package holdemhandhistory;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Crash extends RuntimeException implements Constants {

	/*-  ******************************************************************************
	 * This Class is used to assist in testing and debug. 
	 * When a terminal error ( program bug ) is detected then this method can be 
	 * called to write a message to the log file.
	 * 		A message supplied by the calling method.
	 * 		A stack trace caused by a forced runtime exception.
	 * 
	 * The stack trace will show the path of calls that got us here.
	 * 
	 * The crash boolian can be set by the applications methods to bypass the crash and
	 * continue with the error. Use with caution!
	 * 	 
	 * Arg0 - Message to add
	 * 
	 * @author PEAK_
	 ****************************************************************************** */
	private static final int[] k = { 0 };

	static boolean crash = true; // If false no crash just error messages
	private static int count = 0; // Number of times crashed

	private Crash() {
		throw new IllegalStateException("Utility class");
	}

	public static void log(String message) {
		try {
			// Throw a RuntimeException to force an exception
			throw new RuntimeException("Forced exception");
		} catch (Exception e) {
			// Save stack trace to a file
			final var sw = new StringWriter();
			final var pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			final var stackTrace = sw.toString();
			final var st = new StringBuilder().append("\r\n//")
					.append("SEVERE ERROR OR PROGRAM BUG. The purpose of this is to provide a stack trace.")
					.append("\r\n//Contact peakpokersoftware@gmail.com with copy  of trace data.")
					.append("\r\n//A log file and cumulativeLog file can be found in c:\\applicationData. PLease copy that also")
					.append("\r\n//Your application window may have an option that can be checked to disable crash and run with Severe Errors.")
					.append("\r\n//Use with great caution!").append(message).append("\r\nStack trace\r\n")
					.append(stackTrace).toString();
			System.out.println(st);
			Logger.log(st);
			if (crash || ++count > 100) {
				k[99] = 0;
				System.exit(0);
			}
		}
		final var st2 = new StringBuilder().append("\r\n//")
				.append("Continuing without crash. Use results with cauion. Hard crash afterv 10 soft crashes.")
				.toString();
		System.out.println(st2);
		Logger.log(st2);
	}
}
