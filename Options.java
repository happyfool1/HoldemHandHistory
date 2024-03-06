package holdemhandhistory;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;

class Options implements Constants {

	Options() {
		initialize();
	}

	/*- ****************************************************************************
	* These options indicate which reports have been selected for this run.
	 * Not saved to file, just a convenient place to have them.
	 * Set by the GUI class
	*
	* @author PEAK_
	*******************************************************************************/

	/*-****************************************************************************
	* Release date and expiration date. YYY-MM-DD.
	 *****************************************************************************/
	static String machineID = "";
	static String uuid = "";
	static String version = "01.00.00";
	static boolean freeVersion = true;
	static String releaseDate = "2023-12-01";
	// new additions
	static String expDate = "2024-12-31";
	static int expYear = 2024;
	static int expMonth = 12;
	static int expDay = 31;

	/*-****************************************************************************
	 * HashMap of options.
	 *****************************************************************************/
	private static HashMap<String, String> optionsMap = new HashMap<>();

	/*-****************************************************************************
	* Location of directory
	*****************************************************************************/
	static String optionsFile = APPLICATION_DIRECTORY + "\\Options.ser";

	private static final String falseSt = "false";

	/*- ****************************************************************************
	* Initialize Return false if options file not found. 
	*****************************************************************************/
	static boolean initialize() {
		if (!new File(optionsFile).exists()) {
			JOptionPane.showMessageDialog(null, "ERROR  Options() Initialize() can not find " + optionsFile
					+ " program can not execute - terminated");
			return false;
		}

		writeOptionsToFile(); // Used here when new options are added
		readOptionsFromFile();

		// printHashMap();
		return true;
	}

	/*- ****************************************************************************
	 *  Write Options HashMap to file. 
	****************************************************************************  */
	static void writeOptionsToFile() {
		optionsToMap();
		writeOptions();
		// readOptionsFromFile();
		// printHashMap();
	}

	/*-  ****************************************************************************
	 * Read Options HashMap from file. 
	 **************************************************************************** */
	static void readOptionsFromFile() {
		readOptions();
		mapToOptions();
		// printHashMap();
	}

	/*-  ****************************************************************************
	 * Print Options HashMap. 
	**************************************************************************** */
	static void printHashMap() {
		for (Iterator iterator = optionsMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			Logger.log(mentry.getKey() + " " + mentry.getValue());
		}
		Logger.log("\n\n\n");
	}

	/*- *******************************************************************************************
	 *  Copy all options variables to HashMap The HashMap key and value are both
	 * strings.
	 ********************************************************************************************/
	private static void optionsToMap() {
		optionsMap.put("releaseDate", releaseDate);
		optionsMap.put("version", version);
		optionsMap.put("machineID", machineID);
		optionsMap.put("uuid", uuid);
		optionsMap.put("freeVersion", freeVersion ? "true" : falseSt);
		optionsMap.put("expirationDate", expDate);
		optionsMap.put("expirationYear", String.valueOf(expYear));
		optionsMap.put("expirationMonth", String.valueOf(expMonth));
		optionsMap.put("expirationDay", String.valueOf(expDay));
	}

	/*- ****************************************************************************
	 * Copy all HashMap to option variables. The HashMap key and value are both
	 * strings.
	 *****************************************************************************/
	private static void mapToOptions() {
		releaseDate = optionsMap.get("releaseDate");
		version = optionsMap.get("version");
		machineID = optionsMap.get("machineID");
		uuid = optionsMap.get("uuid");
		freeVersion = "true".equals(optionsMap.get("freeVersion"));
		expDate = optionsMap.get("expirationDate");
		expYear = Integer.valueOf(optionsMap.get("expirationYear"));
		expMonth = Integer.valueOf(optionsMap.get("expirationMonth"));
		expDay = Integer.valueOf(optionsMap.get("expirationDay"));
	}

	/*-****************************************************************************
	 * - Return the hex name of a specified color. Arg0 - color Color to get hex
	 * name of. return - Hex name of color: "rrggbb".
	**************************************************************************** */
	static String getHexName(Color c) {
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();

		String $ = Integer.toString(r, 16), gHex = Integer.toString(g, 16);
		String bHex = Integer.toString(b, 16);

		return "#" + ($.length() == 2 ? $ : "0" + $) + (gHex.length() == 2 ? gHex : "0" + gHex)
				+ (bHex.length() == 2 ? bHex : "0" + bHex);
	}

	/*- ****************************************************************************
	 * Write HashMap a disk file.
	 *****************************************************************************/
	private static void writeOptions() {
		deleteFile();
		try {
			FileOutputStream fileOut = new FileOutputStream(optionsFile);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(optionsMap);
			out.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/*-  ****************************************************************************
	 * Read HashMap from a disk file. 
	 *****************************************************************************/
	private static void readOptions() {
		try {
			FileInputStream fileIn = new FileInputStream(optionsFile);
			ObjectInputStream ois = new ObjectInputStream(fileIn);
			optionsMap = (HashMap) ois.readObject();
			ois.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
		}
	}

	/*- ****************************************************************************
	 *  Delete file. 
	 ****************************************************************************  */
	private static boolean deleteFile() {
		boolean result = true;
		File f = new File(optionsFile);
		if (f.exists())
			f.delete();
		return result;
	}

}
