package holdemhandhistory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HandReader implements Constants {

	/*- ********************************************************************************************** 
	 * There are multiple applications in this Hold'em analysis project. 
	 * This application is Hold'em Hand History. It analyzes Universal Format Hand History files.
	 * The current set of applications are:
	 * 		Holde'm Evaluator.
	 *     Hold'em Hand History Converter.
	 *     Hold'em Hand History Analysis.
	 *     Hold'em Library
	 *  Planned future applications that are in progress:   
	 *     Hold'em Simulator. Simulates play and optimizes strategy. Uses Hand History Analysis data.
	 *  	Hold'em Interactive.  Play against the simulator with prompts.  
	 *  
	 *  We hope that by making all of this open source the set of applications will be expanded.
	 *     
	*@author PEAK
	*************************************************************************************************/

	/*- ********************************************************************************************** 
	* This class reads all of the Universal Hand History files in a Folder. 
	* The hands are returned to the calling application one at a time until all hands in every file 
	* have been read.
	* The last hand is indicated null
	* What is returned is a Hand instance.
	* 
	* There is no processing of hand data and no selection of hands. Just read and return.
	* 
	* There are x public methods:
	* 	int	    folderPath(String path)		Let this class know the path to the folder.
	* 	Hand    getNextHand()					Get the next hand, last hand is null
	*  String  getPathStatus()			        Get the status of the path if there is an error, other info like files 
	*	String 	 getReadStatus()		            Get the status files being processed, check and display every 
	* 	                                    					few thousand hands for large files
	*************************************************************************************************/

	private static Hand hand = new Hand();
	private static String[] handStringArray = new String[MAX_HANDS_IN_FILE + 1000];
	private static int fileIndex = 0;
	private static int handStringArrayIndex = 0;

	private static final int MAX_FILES = 100000;
	// Array of all file names in input folder
	private static final String[] filesInFolder = new String[MAX_FILES];
	private static String fileInFolder = ""; // Current file
	private static int filesInFolderCount = 0;
	private static int handIndex = 0;
	private static int filesRead = 0;
	private static int hands = 0;
	private static int handsThisFile = 0;
	private static int players = 0;
	private static int skip = 0;

	private static String folderStatus = "";
	private static String readStatus = "";

	private static String path = "";

	private static final Timer getHandsTimer = new Timer();

	/*- ********************************************************************************************** 
	* Receives the path of the input folder. 
	* Checks that the folder exist and that there are files in it. 
	* Arg0 - Full path to folder
	* Returns 0 if the folder exists and there are files in it. 
	*************************************************************************************************/
	static int folderPath(String p) {
		path = p;
		handStringArrayIndex = 0;
		handsThisFile = 0;
		filesInFolderCount = 0;
		filesRead = 0;
		filesInFolderCount = 0;
		hands = 0;
		handsThisFile = 0;
		players = 0;
		fileIndex = 0;
		fileIndex = -1;
		return checkPath(path);
	}

	/*- ********************************************************************************************** 
	* Get the next hand
	* If there is a problem returns null;
	* handStringArrayIndex of -1 indicates that no files have been read
	*************************************************************************************************/
	static Hand getNextHand() {
		if (fileIndex == -1) {
			getHandsTimer.startTimer();
			getHandsTimer.resetTimer();
			fileIndex = 0;
			handStringArrayIndex = 0;
			fileInFolder = filesInFolder[0];
			String filePath = path + fileInFolder;
			readOneFile(filePath);
		}
		if (handStringArrayIndex >= handsThisFile) {
			++fileIndex;
			if (fileIndex >= filesInFolderCount) {
				System.out.println("//null ");
				return null;
			}
			handStringArrayIndex = 0;
			fileInFolder = filesInFolder[fileIndex];
			String filePath = path + fileInFolder;
			readOneFile(filePath);
		}
		// Convert and return one Hand
		hand = new Hand();
		++hands;
		hand = hand.fromStringEquivalent(handStringArray[handStringArrayIndex]);
		handStringArray[handStringArrayIndex++] = null;
		return hand;
	}

	/*- ********************************************************************************************** 
	* Get the status of folder path - Number of files etc..
	*************************************************************************************************/
	static String getPathStatus() {
		return folderStatus;
	}

	/*- ********************************************************************************************** 
	* Get the status hand reading - Number of players, hands, time, etc..
	*************************************************************************************************/
	static String getReadStatus() {
		getHandsTimer.stopTimer();
		String time = getHandsTimer.getTimerSecondsString();
		readStatus = "Files read " + Format.format(filesRead) + " Hands returned " + Format.format(hands) + " Time "
				+ time;
		return readStatus;
	}

	/*- ********************************************************************************************** 
	* Get the status reading files. Timer, files read, ertc..
	* Call this method every several thousand hands to update progress display
	* Arg0 - Full path to directory
	* Returns 0 if OK, -1 no such folder, -2 folder empty
	*************************************************************************************************/
	private static int checkPath(String path) {
		final var file = new File(path);
		if (!file.exists()) {
			folderStatus = "ERROR Folder does not exist   " + path;
			Logger.logError("ERROR Folder does not exist   " + path);
			return -1;
		}
		listOfFiles(file); // Fill the filesInFolder array with the names of each file
		Logger.log(new StringBuilder().append("//listOfFiles ").append(filesInFolderCount).append("").append(path)
				.toString());
		if (filesInFolderCount == 0) {
			folderStatus = "ERROR no files in input folder ";
			Logger.log(new StringBuilder().append("ERROR no files in input folder ").append(filesInFolderCount)
					.append("").append(path).toString());
			return -2;
		}
		folderStatus = "Files in Folder " + Integer.valueOf(filesInFolderCount);
		return 0;
	}

	/*-***********************************************************************************************
	 * Get names of all files in a directory and subdirectories. Recvursive. 
	 * Arg0 - a File The array filesInFolder is filled with file names filesInFolderCount
	 * is the number of file names in filesInFolder[]
	 * 
	 * Example: 
	 * File file = new File(selectedPathIn);
	 * if (!file.exists()) { // handle }else
	 * { listOfFiles(file);
	 ************************************************************************************************/
	public static boolean listOfFiles(File dirPath) {
		Logger.log("//listOfFiles " + dirPath);
		final var filesList = dirPath.listFiles();
		if (filesList == null) {
			Logger.log("ERROR ");
			return false;
		}
		for (var file : filesList) {
			if (file.isFile()) {
				final var st = file.getName();
				if (st.endsWith(".txt") && !st.startsWith("playerinfo")) {
					filesInFolder[filesInFolderCount++] = st;
					if (filesInFolderCount > MAX_FILES) {
						Logger.logError("MAX_FILES  fileCount " + filesInFolderCount);
						Crash.log("$$$");
						return false;
					}
				}
			} else {
				// listOfFiles(file);
			}
		}
		return true;
	}

	private static int z = 0;

	/*-***********************************************************************************************
	 * Read and process file in Universal format. The Hand and Action classes.
	 * The actual input is a text file because using the built in Java serializarion has VERY poor performance.
	 * We will evaluate alternative methods in the future but for now a simple .txt file works well.
	  * One file may contain thousands of hands
	 * The handsArray array is constructed, one array element for each hand.
	  * Arg0 - Path to file
	 * Returns true if all is OK. False if  any error
	 ************************************************************************************************/
	private static boolean readOneFile(String path) {
		handsThisFile = 0;
		handIndex = 0;
		int length = 0;
		boolean empty = false;
		String oneString = ""; // A String with the equivalent of one Hand instance.
		BufferedReader objReader = null;
		try {
			objReader = new BufferedReader(new FileReader(path));
			++filesRead;
			while ((oneString = objReader.readLine()) != null) {
				length = oneString.length();
				empty = length < 3 || oneString == null;
				if (!empty) {
					handStringArray[handsThisFile] = oneString;
					++handsThisFile;
				}
			}
			objReader.close();
		} catch (

		IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (objReader != null) {
					objReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
				return false;
			}
		}
		// OOO C:\HHUniversal\HHNew10000\02231851101.txt 1191
		System.out.println("//OOO  " + path + " " + handsThisFile);
		return true;
	}
}
