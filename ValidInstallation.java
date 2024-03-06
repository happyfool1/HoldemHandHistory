package holdemhandhistory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.Scanner;

import javax.swing.JOptionPane;

public class ValidInstallation implements Constants {
	private ValidInstallation() {
		throw new IllegalStateException("Utility class");
	}

	/*- **************************************************************************** 
	* Check for valid installation.
	* Is Options file in application directory.
	* Are required files in directory.
	* is past expiration date?
	* get machine ID.
	* 
	* @author PEAK_
	*******************************************************************************/

	private static String sn;

	/*- **************************************************************************** 
	* Check for valid installation.
	**************************************************************************** */
	static boolean checkValid() {
		// Options.initialize();
//		if (!new File(Options.optionsFile).exists()) {
//			JOptionPane.showMessageDialog(null, new StringBuilder().append("Unable to start application ")
//					.append(Options.optionsFile).append(" missing. ").toString());
//			Logger.logError(new StringBuilder().append("Unable to start application ").append(Options.optionsFile)
		// .append(" missing").toString());
		// return false;
		// }
		if (!checkValidFiles()) {
			return false;
		}
		final var id = getMachineID();
		Options.machineID = id;
		final var uuid = getSystemUUID();
		Options.uuid = uuid;
		// System.out.println("//XXX " + id + " " + uuid);
		checkExpiration();
		return true;
	}

	public static boolean checkValidFiles() {
		final var dir0 = APPLICATION_DIRECTORY;
		final var dir1 = APPLICATION_DIRECTORY + "\\Options.ser";

		final var msg1 = "SEVERE ERROR   Unable to start Applicvation. \n\rDirectory required to run the program is missing,\r\n ";
		final var msg2 = "Please check installation and create this File.\r\n ";
		final var msg3 = "This File contains the Ranges necessary to run the application.\r\n ";

		if (!exists(dir0)) {
			Popup.popup(new StringBuilder().append(msg1).append(dir0).append(msg2).toString());
			return false;
		}
		if (!exists(dir1)) {
			// Popup.popup(new
			// StringBuilder().append(msg1).append(dir1).append(msg2).toString());
			// return false;
		}
		return true;
	}

	/*-************************************************************************************
	 * Check for expiration If expired return true If close to expiration display
	 * warning message Returns true if valid Displays popup dialog if near
	 * expiration or expired.
	 *************************************************************************************/
	private static boolean checkExpiration() {
		Options.machineID = getMachineID();

		final var expirationDate = LocalDate.of(Options.expYear, Options.expMonth, Options.expDay);
		final var today = LocalDate.now();
		if (today.isBefore(expirationDate)) {
			return true;
		}
		JOptionPane.showMessageDialog(null,
				new StringBuilder().append("This application is past it's expiration date. Expired on ")
						.append(Options.expDate).append(" please uodate from website to avoid problems").toString());
		Logger.log("Expired. Your version is out of date. Expired on " + Options.expDate);
		return false;
	}

	/*-************************************************************************************
	 * Get the machine ID for this computer.
	 * Uses powershell.exe Get-CimInstance -ClassName Win32_ComputerSystemProduct  
	 *************************************************************************************/
	static String getSystemUUID() {
		// Create a ProcessBuilder object with the PowerShell command
		final var pb = new ProcessBuilder("powershell.exe",
				"Get-CimInstance -ClassName Win32_ComputerSystemProduct | Select-Object -ExpandProperty UUID");
		try {
			// Start the process and get the input stream
			final var p = pb.start();
			final var is = p.getInputStream();
			// Create a BufferedReader to read the output
			final var br = new BufferedReader(new InputStreamReader(is));
			// Read the first line of output, which should be the UUID
			final var uuid = br.readLine();
			// Close the BufferedReader and the InputStream
			br.close();
			is.close();
			// Return the UUID
			return uuid;
		} catch (IOException e) {
			// Handle the exception
			e.printStackTrace();
			return null;
		}
	}

	/*- ************************************************************************************
	 *  Get the machine ID for this computer.
	 ************************************************************************************  */
	private static String getMachineID() {

		final OutputStream os;
		final InputStream is;
		final var runtime = Runtime.getRuntime();
		Process process = null;
		try {
			process = runtime.exec(new String[] { "wmic", "bios", "get", "serialnumber" });
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		os = process.getOutputStream();
		is = process.getInputStream();
		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		final var sc = new Scanner(is);
		try {
			while (sc.hasNext()) {
				final var next = sc.next();
				if ("SerialNumber".equals(next)) {
					sn = sc.next().trim();
					break;
				}
			}
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		if (sn == null) {
			throw new RuntimeException("Cannot find computer SN");
		}
		return sn;
	}

	/*- ************************************************************************************
	 * Does file or directory exist Returns true is yes. 
	 /*- ************************************************************************************ */
	private static boolean exists(String file) {
		return (new File(file)).exists();
	}

}
