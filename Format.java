package holdemhandhistory;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Format implements Constants {
	/*- **************************************************************************** 
	* @author PEAK_
	*******************************************************************************/

	static int pd;
	/*-
	 * Overloaded methods to format data for reports 
	 */

	private Format() {
		throw new IllegalStateException("Utility class");
	}

	/*-   Convert boolean to string. */
	static String format(boolean b) {
		return b ? "true" : "false";
	}

	/*- String to String. */
	static String format(String s) {
		return s;
	}

	/*-   Convert int to string. */
	static String format(int number) {
		return new DecimalFormat("###,###,###,###,###").format(number);
	}

	/*-   Convert long to string. */
	static String format(long number) {
		return new DecimalFormat("###,###,###,###,###,###").format(number);
	}

	/*-   Convert double to string. */
	static String format(double number) {
		return "   " + (number == 0 ? "0  " : new DecimalFormat("###,###,###,###,###.###").format(number) + "");
	}

	/*- 
	 *  Convert double to string.
	 *  Arg0 - Time in seconds
	 *  Returns String mm:ss
	 */
	static String formatTime(int number) {
		final var pattern = "##";
		var st = "";
		final var df = new DecimalFormat(pattern);
		final int min = (number / 60);
		final int sec = (number % 60);
		st = df.format(sec);
		if (sec < 10) {
			st = "0" + st;
		}
		return new StringBuilder().append(df.format(min)).append(":").append(st).toString();
	}

	/*- 
	 *  Time in mili seconds
	 *  Returns String mm:ss
	 */
	static String formatTimeMili(long number) {
		return getElapsedTime(number);
	}

	/*- 
	 *  Convert double to string.
	 *  Arg0 - Time in seconds
	 *  Returns String mm:ss
	 */
	static String formatTime(double num) {
		final int number = (int) num;
		final var pattern = "##";
		final var df = new DecimalFormat(pattern);
		return new StringBuilder().append(df.format(number / 60)).append(":").append(df.format(number % 60)).toString();
	}

	/*- 
	 *  Convert double to string.
	 *  Arg0 - Time in seconds
	 *  Returns String mm:ss
	 */
	static String formatTime(long num) {
		final int number = (int) num;
		final var pattern = "##";
		final var df = new DecimalFormat(pattern);
		return new StringBuilder().append(df.format(number / 60)).append(":").append(df.format(number % 60)).toString();
	}

	/*- Convert BigDecimal to string. */
	static String format(BigDecimal number) {
		final var df = new DecimalFormat();
		df.setMaximumFractionDigits(4);
		df.setMinimumFractionDigits(0);
		df.setGroupingUsed(false);
		return df.format(number);
	}

	/*- Convert BigDecimal to string. */
	static String format$(BigDecimal number) {
		final var df = new DecimalFormat();
		df.setMaximumFractionDigits(4);
		df.setMinimumFractionDigits(0);
		df.setGroupingUsed(false);
		String st = " $" + df.format(number);
		return st;
	}

	/*- Convert double to string. */
	static String format$(double number) {
		final var st = "###,###,###.#####";
		final var df = new DecimalFormat(st);
		return number < 0 ? "-$" + df.format(Math.abs(number)) : number > 0 ? "$" + df.format(number) : "0";
	}

	/*- Convert double to percentage to String. */
	static String formatPer(double number) {
		final var pattern = "###.##";
		final var $ = new DecimalFormat(pattern);
		if (number == 0) {
			return "  0%  ";
		}
		if (number <= 100 && number >= 0 || pd >= 10) {
			return new StringBuilder().append("   ").append($.format(number)).append("%").toString();
		}
		++pd;
		Logger.log("//ERROR Format formatPer()  > 100% or < 0% " + number);
		return new StringBuilder().append("EEE").append($.format(number)).append("%").toString();
	}

	/**
	 * elapsed time in hours/minutes/seconds
	 * 
	 * @return String
	 */
	static String getElapsedTime(long milliseconds) {
		final var format = "%%0%dd".formatted(2);
		return new StringBuilder().append(format.formatted((milliseconds / 1000) / 3600)).append(":")
				.append(format.formatted(((milliseconds / 1000) % 3600) / 60)).append(":")
				.append(format.formatted((milliseconds / 1000) % 60)).toString();
	}

	/*- 
	 * Convert to decimal format.
	 */
	static String decFormatRatio(double d0) {
		return (new DecimalFormat("###.#")).format(d0) + ":1";
	}

	/*-
	 * Convert odds percentage to ratio as String
	 * Arg0 - Odds as a percentage
	 * Returns String
	 */
	static String formatToRatio(double odds) {
		return Calculate.percentageToOdds(odds);
	}

	/*-
	 * Convert odds percentage to ratio as String
	 * Arg0 - Odds as a percentage
	 * Returns String
	 */
	static String formatToRatioAndPer(double odds) {
		return new StringBuilder().append(Calculate.percentageToOdds(odds)).append(" ")
				.append((new DecimalFormat("###.#")).format(odds)).append("%").toString();
	}

}
