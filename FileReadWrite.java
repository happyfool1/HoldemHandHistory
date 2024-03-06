package holdemhandhistory;

/*- *********************************************************************************************
 * Methods to read and write a file
 * 
 * @author PEAK_
 * 
 **********************************************************************************************/
import java.io.BufferedWriter;

/* - General utility to read and write text files */

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

class FileReadWrite {

	private FileReadWrite() {
		throw new IllegalStateException("Utility class");
	}

	/*- - Read strings from file. */
	static String readFile(String fileN) {
		final char[] $ = new char[60000];
		try (var fr = new FileReader(fileN)) {
			fr.read($);
		} catch (SecurityException | IOException i) {
			i.printStackTrace();
		}
		return String.valueOf($);
	}

	/*-
	 * - Write to a file - Arg0 - Name of file to write Arg1 - String to write to
	 * file Returns true if OK.
	 */
	static boolean writeFile(String fileN, String s) {
		boolean result = true;
		deleteFile(fileN);

		PrintWriter pwFile1 = null;

		// write in a file in a newline - append to existing
		try {
			final var fl = new FileWriter(fileN, true);
			final var br = new BufferedWriter(fl);
			pwFile1 = new PrintWriter(br);
			pwFile1.write(s);
		} catch (SecurityException | IOException i) {
			// for FileWriter
			result = false;
			i.printStackTrace();
		} finally {
			// no matter what happen, close the output stream
			if (pwFile1 != null) {
				pwFile1.close();
			}
		}
		pwFile1.flush();
		pwFile1.close();
		return result;
	}

	/*-
	 * - Write to a file appending to existing file- Arg0 - Name of file to write
	 * Arg1 - String to write to file Returns true if OK.
	 */
	static boolean writeFileAppend(String fileN, String s) {
		boolean result = true;

		PrintWriter pwFile1 = null;

		// write in a file in a newline - append to existing
		try {
			final var fl = new FileWriter(fileN, true);
			final var br = new BufferedWriter(fl);
			pwFile1 = new PrintWriter(br);
			pwFile1.write(s);
		} catch (SecurityException | IOException i) {
			// for FileWriter
			result = false;
			i.printStackTrace();
		} finally {
			// no matter what happen, close the output stream
			if (pwFile1 != null) {
				pwFile1.close();
			}
		}
		if (pwFile1 != null) {
			pwFile1.flush();
			pwFile1.close();
		}
		return result;
	}

	/*-
	 * - Append to file Arg0 - Name of file to write Arg1 - String to write to file
	 * Returns true if OK.
	 */
	static void appendFile(String fileIn, String fileOut) {
		var st = "";
		st = readFile(fileIn);
		// Logger.log(st);
		writeFileAppend(fileOut, st);

	}

	/*- - Delete file. */
	static boolean deleteFile(String fileN) {
		final boolean result = true;
		final var f = new File(fileN);
		if (f.exists()) {
			f.delete();
		}
		return result;
	}

}
