package holdemhandhistory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

class GUIConvertToUniversal implements Constants {
	/*- ************************************************************************************************
	*@author PEAK
	 *************************************************************************************************/

	/*- ************************************************************************************************
	 * This Class is used to convert a PokerStars format hand to a universal format.
	 * We originally planned to write a .ser file with a collection of Hand classes BUT
	 * the performance was so bad that we decided to go with a String format.
	 * We plan to investigate Protocol Buffers (protobuf) or Apache Avro. 
	 * These formats are designed for efficiency in terms of both serialization size and speed.  
	 * 
	 * We parse each hand with Class ParsePS. 
	* 		There are many validity checks, too many to even mention here.
	*       In the process we convert each hand into a universal format and convert player names from
	*       String to a single Integer value. Names are kept in a separate dictionary file.
	*      
	*  This is done in 2 passes. Pass 1  parses PokerStars hands into Hand objects. 
	*   A HashMap of PlayerInfo objects is created with an instance for each player.
	*   PlayerInfo contains the playerID and number of hands played ( calculated in pass 1 )
	*  Pass 2 iterates over the Hand  objects checking the number of hands played and for
	*  players with too few hands sets their playerId in IDArray in Hand to -1 so t
	*  that the player will be ignored in the HandHistory application.
	*  
	*  In the display screen there are radio buttons to choose how many hands are required.
	*  
	*  Note: If the number of hands reaches the maximum limit and multiple output files
	*  are created some players may slip through but will be caught by the HandHistory application
	*  so the impact is small.
	 ************************************************************************************************ */

	private static String pathIn = "C:\\HHFiltered\\";
	private static String pathOut = "C:\\HHUniversal\\";
	private static String selectedPathIn = "";
	private static String selectedPathOut = "";

	private static int MAX_HANDS = 100000;

	/*- ************************************************************************************************
	 * Site and currency for all hands in a file. Set for each file in Folder
	 * See Constants Class for definitions
	  *************************************************************************************************/
	private static int source = POKER_STARS; // Source of this action array
	private static int currency = USD;
	/*- ************************************************************************************************
	 * Input and output arrays
	  *************************************************************************************************/
	private static HandPS[] handArrayPS = new HandPS[MAX_HANDS + 300000];
	private static Hand[] handArray = new Hand[MAX_HANDS + 300000];
	private static PlayerInfo playerArray;
	private static final HashMap<Integer, PlayerInfo> playersInfoMap = new HashMap<>(200000);
	private static int handsHandFile = 0;
	private static int minimumPlayed = 0;
	/*- ************************************************************************************************
	 * For reporting results
	  *************************************************************************************************/
	private static int handIndexPS = 0;
	private static int handIndex = 0;
	private static int handsPSFile = 0;
	private static int handsAccepted = 0;
	private static int handsAcceptedThisFile = 0;
	private static int handsRejected = 0;
	private static int filesInFolderCount = 0;
	private static int playersLessThanMinimum = 0;
	private static int players = 0;

	/*- ************************************************************************************************
	 * Timers
	  *************************************************************************************************/
	private static final Timer timerListOfFiles = new Timer();
	private static final Timer timerRead = new Timer();
	private static final Timer timerConvert = new Timer();
	private static final Timer removePlayersFromHand = new Timer();
	private static final Timer timerWrite = new Timer();
	private static final Timer timerTotal = new Timer();

	// Array of all file names in input folder
	private static final String[] filesInFolder = new String[10000];
	private static String fileInFolder = ""; // Current file

	private static File file;

	/*-************************************************************************************************
	 * Check boxes for reports 
	************************************************************************************************ */

	private static final JTextField statusText = new JTextField(
			"                                                                            ");
	private static final JTextField handsText = new JTextField(6);

	private static final JButton buttonRun = new JButton("Run");

	private static final JButton buttonHelp = new JButton("Help");

	private static final JButton buttonExit = new JButton("Exit");

	private static boolean exit = false;

	private static boolean run = false;

	private static final JPanel panelStatus = new JPanel();

	private static final JPanel panel0 = new JPanel();

	private static final JPanel panelFolder = new JPanel();

	private static final JButton buttonFolder = new JButton("Select Folders");

	private static final JPanel panelRadio = new JPanel();

	private static final JPanel panelBlank = new JPanel();

	private static final JRadioButton played0 = new JRadioButton("None    ");

	private static final JRadioButton played36 = new JRadioButton("36    ");

	private static final JRadioButton played48 = new JRadioButton("48   ");

	private static final JRadioButton played60 = new JRadioButton("60   ");

	private static final JRadioButton played120 = new JRadioButton("120   ");

	private static final JRadioButton played240 = new JRadioButton("240   ");

	private static final JRadioButton played480 = new JRadioButton("480   ");

	private static final JRadioButton played960 = new JRadioButton("960   ");

	private static final JTextField statusDirectories = new JTextField(
			"                                                                            ");

	private static final JCheckBox crash = new JCheckBox("Crash on severe error");
	private static final JPanel panelCheck = new JPanel();

	private static JLabel blank = new JLabel("   ");

	private static int maxStLen = 0;
	private static int maxActionCount = 0;

	/*-************************************************************************************************
	 * Main - . 
	 *************************************************************************************************/
	public static void main(String[] s0) {
		if (!ValidInstallation.checkValid()) {
			return;
		}
		run = false;
		exit = false;
		startupWindow();
		if (exit) {
			return;
		}

		// Wait for run or exit
		while (!run) {
			sleep(1000);
			// if (exit)
			// return;
			if (run) {
				pass1();
				System.out.println("//maxStLen " + maxStLen + " maxActionCount " + maxActionCount);
			}
		}

		run = false;
		while (!run || !exit) {
			if (exit) {
				return;
			}
			if (run) {
				Logger.logError(
						new StringBuilder().append("startReports").append(run).append(" ").append(exit).toString());
				run = false;
			}
			sleep(1000);
		}
	}

	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {

		}
	}

	/*-************************************************************************************************
	 * Display main window and options to select folder ansd to run program
	 *************************************************************************************************/
	private static void startupWindow() {
		final var frame = new JFrame("Hold'em  Hand History Analysis");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(50, 50);
		frame.setPreferredSize(new Dimension(1600, 400));
		panel0.setLayout(new GridLayout(7, 1));
		panel0.setSize(1600, 400);
		final var f = new Font(Font.SERIF, Font.BOLD, 18);

		Box.createHorizontalBox();
		statusDirectories.setBorder(BorderFactory.createTitledBorder("Input and Output Folders      "));
		statusDirectories.setBackground(Color.WHITE);
		statusDirectories.setFont(f);
		panel0.add(statusDirectories);
		panel0.add(panelBlank);
		panel0.add(panelBlank);
		panelBlank.setFont(f);

		final var options1 = Box.createHorizontalBox();
		options1.setFont(f);

		buttonFolder.setToolTipText("Input Folder for Hand History files");
		buttonFolder.setBackground(Color.GREEN);
		buttonFolder.addActionListener(new Listener());
		buttonRun.setToolTipText("Start Analysis");
		buttonRun.setBackground(Color.GREEN);
		buttonHelp.setToolTipText("Help");
		buttonHelp.setBackground(Color.GREEN);
		buttonRun.addActionListener(new Listener());
		buttonExit.setToolTipText("Exit");
		buttonExit.setBackground(Color.RED);
		buttonExit.addActionListener(new Listener());
		buttonHelp.addActionListener(new Listener());

		final var control1 = Box.createHorizontalBox();
		control1.setFont(f);
		control1.add(buttonFolder);
		control1.add(buttonRun);
		control1.add(buttonHelp);
		panelFolder.add(control1);
		panel0.add(panelFolder);

		panelRadio.setLayout(new GridLayout(1, 8));
		final var radio = Box.createHorizontalBox();
		radio.setFont(f);
		final var radioGroup = new ButtonGroup();
		panelRadio.setFont(f);
		panelRadio.setBorder(BorderFactory.createTitledBorder("Miminimum number of hands played to accept"));
		panelRadio.add(played0);
		panelRadio.add(played36);
		panelRadio.add(played48);
		panelRadio.add(played60);
		panelRadio.add(played120);
		panelRadio.add(played240);
		panelRadio.add(played480);
		panelRadio.add(played960);
		radioGroup.add(played0);
		radioGroup.add(played36);
		radioGroup.add(played48);
		radioGroup.add(played60);
		radioGroup.add(played120);
		radioGroup.add(played240);
		radioGroup.add(played480);
		radioGroup.add(played960);
		panel0.add(panelRadio);

		played240.setSelected(true);

		played0.addActionListener(new Listener());
		played36.addActionListener(new Listener());
		played48.addActionListener(new Listener());
		played60.addActionListener(new Listener());
		played120.addActionListener(new Listener());
		played240.addActionListener(new Listener());
		played480.addActionListener(new Listener());
		played960.addActionListener(new Listener());

		Box.createHorizontalBox();
		statusText.setBorder(BorderFactory.createTitledBorder("Pass 1      "));
		statusText.setBackground(Color.WHITE);
		statusText.setFont(f);
		panel0.add(statusText);

		panelCheck.setLayout(new GridLayout(1, 1));
		final var optionsCheck = Box.createHorizontalBox();
		optionsCheck.setFont(f);
		panelCheck.setBorder(BorderFactory.createTitledBorder("Options"));
		panelCheck.add(crash);
		panel0.add(panelCheck);
		crash.setSelected(true);
		crash.addActionListener(new Listener());
		panelCheck.setFont(f);
		panel0.add(panelBlank);
		panel0.add(panelBlank);
		crash.setSelected(true);

		statusDirectories.setText("Input Folder    " + selectedPathIn + "         Output folder    " + pathOut);
		buttonRun.setEnabled(false);
		frame.add(panel0);
		frame.pack();
		frame.setVisible(true);
	}

	static int skip = 0;

	/*-************************************************************************************************
	 * Read filtered Hand History files in the input directory and create a .ser file in the universal format. 
	 * See classes: Hand, Action, and NameToID.
	 *
	 * Input is a directory containing filtered Hand History files with no player names.
	 * GUIFilter has replaced player named with a 6 character ID.
	 * The ID is actually an Integer value. 
	 * It is represented as a 6 characters in the filtered Hand History file but in the Hand and 
	 * Action class instances it is an Integer. 
	 * The NameToID class makes it possible to mapIOD to original player name.
	 * 
	 * Each filtered Hand History file is a test file that contains thousands of hands. 
	 * That file is converted to an equivalent .ser containing the same hands but is an array
	 * of Hand instances containing Action instances. One to one correspondence.
	 * 
	 * An array of file names in the input directory is first constructed as an array.
	 * We then step through each input file creating one output file in the output directory.
	 * 
	 *************************************************************************************************/
	private static boolean pass1() {
		statusText.setBackground(WHITE);
		ParseCodes.initialize();
		timerTotal.startTimer();
		handIndexPS = 0;
		handIndex = 0;
		handsPSFile = 0;
		handsHandFile = 0;
		handsAccepted = 0;
		handsAcceptedThisFile = 0;
		handsRejected = 0;
		filesInFolderCount = 0;
		playersLessThanMinimum = 0;
		players = 0;
		String st = "";
		int fileNow = 0;

		final var file = new File(selectedPathIn);
		if (!file.exists()) {
			statusText.setText("ERROR Folder does not exist   " + selectedPathIn);
			Logger.logError("ERROR Folder does not exist   " + selectedPathIn);
			statusText.update(statusText.getGraphics());
			return false;
		}

		final var fileD = new File(selectedPathOut);
		if (fileD.exists()) {
			FileUtils.deleteDir(selectedPathOut);
		}
		if (!fileD.exists()) {
			fileD.mkdir();
		}
		skip = 400;
		// Create an array of file names in the input directory
		timerListOfFiles.startTimer();
		statusText.setText("Obtaining l.ist of files");
		listOfFiles(file); // Fill the filesInFolder array with the names of each file
		timerListOfFiles.startTimer();

		Logger.log(new StringBuilder().append("//listOfFiles ").append(filesInFolderCount).append(" ")
				.append(selectedPathIn).toString());
		for (int filiIndex = 0; filiIndex < filesInFolderCount; ++filiIndex) {
			fileInFolder = filesInFolder[filiIndex];
			checkSite();

			timerRead.startTimer();
			// Read one file
			if (!readOneFile(selectedPathIn + fileInFolder)) {
				statusText.setText(new StringBuilder().append("ERROR reading file ").append(selectedPathIn).append(" ")
						.append(fileInFolder).toString());
				Logger.logError(new StringBuilder().append("ERROR reading file ").append(selectedPathIn).append(" ")
						.append(fileInFolder).toString());
				statusText.update(statusText.getGraphics());
				statusText.setBackground(RED);
				return false;
			}
			timerRead.sumTimer();

			final var time = timerTotal.getElapsedTimeString();
			StringBuilder sb = new StringBuilder();
			sb.append("Files ").append(Format.format(filesInFolderCount))
					.append("    Converting file  " + Integer.valueOf(fileNow) + " of "
							+ Integer.valueOf(filesInFolderCount))
					.append("   hands in file ").append(Format.format(handsPSFile)).append("   Accepted ")
					.append(Format.format(handsAccepted)).append("   Rejected ").append(Format.format(handsRejected))
					.append("   Players  ").append(Format.format(players)).append("   Players < min ")
					.append(Format.format(playersLessThanMinimum)).append("   Elapsed  ").append(Format.format(time));
			statusText.setText((sb).toString());
			statusText.update(statusText.getGraphics());

			// Convert one file
			handIndex = 0;
			handIndexPS = 0;
			++fileNow;
			convertOneFile();

			timerTotal.sumTimer();
			// Write the file to output directory
			st = selectedPathOut + fileInFolder.substring(0, fileInFolder.length() - 4) + ".txt";

			checkMinimumPlayed();

			if (writeOneFile(st)) {
			} else {
				Logger.log("ERROR writing file " + selectedPathOut + fileInFolder);
				statusText.setBackground(RED);
				return false;
			}

			for (int i = 0; i < handIndex; ++i) {
				handArrayPS[i] = null;
				handArray[i] = null;
			}
			handIndex = 0;
		}

		final var time = timerTotal.getElapsedTimeString();
		StringBuilder sb = new StringBuilder();
		sb.append("Files ").append(Format.format(filesInFolderCount))
				.append("    Converting file  " + Integer.valueOf(fileNow) + " of "
						+ Integer.valueOf(filesInFolderCount))
				.append("   hands in file ").append(Format.format(handsPSFile)).append("   Accepted ")
				.append(Format.format(handsAccepted)).append("   Rejected ").append(Format.format(handsRejected))
				.append("   Players  ").append(Format.format(players)).append("   Players < min ")
				.append(Format.format(playersLessThanMinimum)).append("   Elapsed  ").append(Format.format(time));
		sb = sb.append("   Complete");
		statusText.setText((sb).toString());
		statusText.setBackground(GREEN);
		statusText.update(statusText.getGraphics());

		final long MegaBytes = (long) 1024 * (long) 1024;
		final var runtime = Runtime.getRuntime();
		final long totalMemory = runtime.totalMemory() / MegaBytes;
		final long maxMemory = runtime.maxMemory() / MegaBytes;
		final long freeMemory = runtime.freeMemory() / MegaBytes;
		final long memoryInUse = (totalMemory - freeMemory) / MegaBytes;

		Logger.log(new StringBuilder().append("//     Memory ").append("\t Memory in use ").append(memoryInUse)
				.append(" \tTotalMemory ").append(totalMemory).append(" \tmaxMemory ").append(maxMemory)
				.append(" \tfreeMemory ").append(freeMemory).toString());
		displayTimers();
		return true;
	}

	/*-************************************************************************************************
	 * Find out what site this Hand History is from.
	 * PokerStars format alone does not tell the story as other sites have switched to the 
	 * PokerStars format.
	 * Sometimes GGPoker has mixed files, some GGPoker and some not. For now check multiple.
	 * TODO Investigate and fix in GUIFilter.
	************************************************************************************************ */
	private static void checkSite() {
		int n = -1;
		int n1 = -1;
		n = filesInFolder[0].indexOf("_GG_");
		if (filesInFolder[1] != null) {
			n1 = filesInFolder[1].indexOf("_GG_");
		}
		if (n != -1 || n1 != -1) {
			source = GGPOKER;
			currency = USD;
		}
		n = filesInFolder[0].indexOf("PokerStarsON");
		if (n != -1) {
			source = CANADA;
			currency = USD;
		}
		n = filesInFolder[0].indexOf("PokerStarsMI");
		if (n != -1) {
			source = POKER888;
			currency = USD;
		}
		n = filesInFolder[0].indexOf("PeakHoldem");
		if (n != -1) {
			source = PEAK_HOLDEM;
			currency = USD;
		}
	}

	/*-************************************************************************************************
	 * Step through each entry in handArray and check each player in Hand.IDArray.
	 * We check the played value in the HashMap playersInfoMap.
	 * If a player does not have enough hands played set his ID to -ID. Convert to negative value.
	 * Then we step through the action array and convert each instance of the players ID to a negative.
	 * 	To convert back id * (-1).
	 *************************************************************************************************/
	private static boolean checkMinimumPlayed() {
		PlayerInfo onePlayer;
		int id = 0;
		for (Iterator iterator = playersInfoMap.entrySet().iterator(); iterator.hasNext();) {
			// java.util.ConcurrentModificationException
			final var mentry = (Map.Entry) iterator.next();
			onePlayer = (PlayerInfo) mentry.getValue();
			id = onePlayer.playerID;
			if (onePlayer.played < minimumPlayed) {
				onePlayer.status = 1;
				++playersLessThanMinimum;
			}
		}
		players += playersInfoMap.size();
		removePlayersFromHand.startTimer();
		for (int i = 0; i < handsAcceptedThisFile; ++i) {
			for (int j = 0; j < PLAYERS; ++j) {
				onePlayer = playersInfoMap.get(handArray[i].IDArray[j]);
				if (onePlayer == null) {
					Logger.logError("ERROR removePlayersFromHand() ");
					Crash.log("ERROR removePlayersFromHand()");
				} else {
					if (onePlayer.played < minimumPlayed) {

						id = handArray[i].IDArray[j];
						int negID = -id; // Invert ID to a negative number
						handArray[i].IDArray[j] = negID;
						for (int k = 0; k < handArray[i].actionCount; ++k) {
							if (handArray[i].actionArray[k].ID == id) {
								handArray[i].actionArray[k].ID = negID;
							}
						}
					}
				}
			}
		}
		removePlayersFromHand.sumTimer();
		return true;
	}

	/*-************************************************************************************************
	 * Display results of HandFromPokerStars timers
	 *************************************************************************************************/
	private static void displayTimers() {
		Logger.log("");
		Logger.log("//List Of Files          \t" + Format.format(timerListOfFiles.getTimer("")));
		Logger.log("//Read  file              \t" + Format.format(timerRead.getTimer("")));
		Logger.log("//Convert               \t" + Format.format(timerConvert.getTimer("")));
		Logger.log("//Remove             \t" + Format.format(removePlayersFromHand.getTimer("")));
		Logger.log("//Write file              \t" + Format.format(timerWrite.getTimer("")));
		Logger.log("//Total              \t" + Format.format(timerRead.getTimer("")));
		Logger.log("");
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
		for (var file : filesList) {
			if (file.isFile()) {
				final var st = file.getName();
				if (st.endsWith(".txt") && !st.startsWith("handID")) {
					filesInFolder[filesInFolderCount++] = st;
				}
			} else {
			}
		}
		return true;
	}

	/*-***********************************************************************************************
	 * This method is poker site independent.
	 * It will perform some basic error checking then call a site specific method
	 * to read and process the file.
	 * Read Hand History as string - PokerStars
	 * Arg0 - File to read
	 * Returns true if file read OK
	 ************************************************************************************************/
	private static boolean checkOneFile(String path) {
		file = new File(path);
		if (file.length() == 0) {
			Logger.logError(new StringBuilder().append("read() file length 0 ").append(file.length()).append(" ")
					.append(path).toString());
			return false;
		}
		if (file.exists()) {
			return true;
		}
		Logger.logError("read() ERROR file not found  " + path);
		return false;
	}

	/*-***********************************************************************************************
	 * Read and process one filtered Hand History file. PokerStars specific.
	  * One file may contain thousands of hands
	 * The handsArray array is constructed, one array element for each hand.
	  * Arg0 - Path to file
	 * Returns true if all is OK. False if  any error
	 ************************************************************************************************/
	private static boolean readOneFile(String path) {
		handsPSFile = 0;
		int ndx = 0;
		int length = 0;
		boolean empty = false;
		handIndexPS = 0;
		ndx = 0;
		handArrayPS[handIndexPS] = new HandPS();

		BufferedReader objReader = null;
		try {
			objReader = new BufferedReader(new FileReader(path));
			while ((handArrayPS[handIndexPS].lines[ndx] = objReader.readLine()) != null) {
				length = handArrayPS[handIndexPS].lines[ndx].length();
				empty = "\r\n".equals(handArrayPS[handIndexPS].lines[ndx]) || length < 3
						|| handArrayPS[handIndexPS].lines[ndx] == null;
				if (empty && ndx != 0) {
					ndx = 0;
					++handIndexPS;
					++handsPSFile;
					handArrayPS[handIndexPS] = new HandPS();
				}
				// All but first line of hand
				if (!empty) {
					++handArrayPS[handIndexPS].numLines;
					++ndx;
				}
			}
			objReader.close();
		} catch (

		IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (objReader != null) {
					objReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return true;
	}

	/*-***********************************************************************************************
	 * Convert PokerStars format filtered Hand History to Hand object. 
	 * Conversion is done by the ParsePS class.
	 * Create instances of PlayerInfo for each unique player ID.
	 * Update count of hands played.
	*********************************************************************************************** */
	private static boolean convertOneFile() {
		handIndexPS = 0;
		handIndex = 0;
		handsAcceptedThisFile = 0;
		PlayerInfo onePlayer;
		for (int i = 0; i < handsPSFile; ++i) {
			if (convertOneHand()) {
				++handIndexPS;
				++handsAccepted;
				++handsAcceptedThisFile;
				for (int j = 0; j < PLAYERS; ++j) {
					onePlayer = playersInfoMap.get(handArray[handIndex].IDArray[j]);
					if (onePlayer == null) {
						onePlayer = new PlayerInfo();
					}
					++onePlayer.played;
					playersInfoMap.put(handArray[handIndex].IDArray[j], onePlayer);
				}
				++handIndex;
			} else {
				++handIndexPS;
				++handsRejected;
			}
		}
		return true;
	}

	/*-***********************************************************************************************
	 * Convert PokerStars format filtered Hand History to Hand object. 
	 * Conversion is done by the ParsePS class.
	*********************************************************************************************** */
	private static boolean convertOneHand() {
		if (handArrayPS[handIndexPS] == null) {
			Logger.logError("//convertOneHand()handArrayPS[handIndexPS] == null " + handIndexPS);
			Crash.log("xxx " + "");
			return false;
		}
		handArray[handIndex] = new Hand();
		handArray[handIndex].source = source;
		handArray[handIndex].currency = currency;
		final int status = ParsePS.convertToHand(handArrayPS[handIndexPS], handArray[handIndex]);
		ParseCodes.updateCodes(status);
		if (status == ParseCodes.OK) {
			return true;
		} else {
			return false;
		}

	}

	/*-	******************************************************************************
	 * Write a single file with many hands appended.
	 * This file is in the Hand String format, a.txt file of strings.
	 * See Hand String toEquivalentString()
	 * Arg0 - Path name
	 ****************************************************************************** */
	private static boolean writeOneFile(String path) {
		String st = "";
		int count = 0;
		PrintWriter file = null;
		try {
			file = new PrintWriter(new BufferedWriter(new FileWriter(path, true), 32768 + 32768));
		} catch (SecurityException | IOException i) {
			i.printStackTrace();
			return false;
		}
		for (int i = 0; i < handsAcceptedThisFile; i++) {
			if (st.length() > maxStLen) {
				maxStLen = st.length();
			}
			count = 0;
			if (handArray[i] != null) {
				for (int j = 0; j < PLAYERS; ++j) {
					if (handArray[i].IDArray[j] < 0) {
						++count;
					}
				}
			}
			if (handArray[i] != null && count < 6) {
				if (maxActionCount < handArray[i].actionCount) {
					maxActionCount = handArray[i].actionCount;
				}
				st = handArray[i].toEquivalentString() + "\r\n";
				file.append(st);
			}
		}
		file.close();
		return true;
	}

	/*-******************************************************************************
	 * Check selected
	 *******************************************************************************/
	private static void checkSelected() {
		if (played0.isSelected()) {
			minimumPlayed = 0;
		}
		if (played36.isSelected()) {
			minimumPlayed = 36;
		}
		if (played48.isSelected()) {
			minimumPlayed = 48;
		}
		if (played60.isSelected()) {
			minimumPlayed = 60;
		}
		if (played120.isSelected()) {
			minimumPlayed = 120;
		}
		if (played240.isSelected()) {
			minimumPlayed = 240;
		}
		if (played480.isSelected()) {
			minimumPlayed = 480;
		}
		if (played960.isSelected()) {
			minimumPlayed = 960;
		}
		if (crash.isSelected()) {
			Crash.crash = true;
		} else {
			Crash.crash = false;
		}
	}

	/*-***********************************************************************************************
	 *  Convert String to double with error checking 
	 *  Return double if valid String
	 * Return -1 if String not valid.
	 ************************************************************************************************/
	private static double stringToDouble(String s0) {
		if (s0.matches("[+-]?\\d+\\.?(\\d+)?")) {
			return Double.parseDouble(s0);
		}
		return -1;
	}

	/*- ***********************************************************************************************-
	 *  Responds to button clicks. 
	***********************************************************************************************  */
	static class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a0) {
			checkSelected();
			switch (a0.getActionCommand()) {
			case "Select Folders":
				selectedPathIn = chooseInputDirectory();
				if (selectedPathIn.equals(pathIn)) {
					JOptionPane.showMessageDialog(null, "You can not choose the root Folder as your input Folder .");
					selectedPathIn = "";
					selectedPathOut = "";
				}

				String st1 = selectedPathIn.substring(4);
				int n = st1.indexOf("\\");
				selectedPathOut = pathOut + st1.substring(n + 1);
				statusDirectories.setText("Input Folder " + selectedPathIn + " Output Folder " + selectedPathOut);
				statusDirectories.update(statusDirectories.getGraphics());
				buttonRun.setEnabled(true);
				break;
			case "Help":
				JOptionPane.showMessageDialog(null, helpString);
			case "Exit":
				exit = true;
				break;
			case "Run":
				run = true;
			}
		}
	}

	/*-******************************************************************************
	 * Choose input directory
	 *******************************************************************************/
	public static String chooseInputDirectory() {
		final var j = new JFileChooser(pathIn);
		j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (j.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			return "";
		}
		final var $ = j.getSelectedFile().getAbsolutePath();
		return $ + "\\";
	}

	// @formatter:off  
			private static final String helpString = new StringBuilder().append("Welcome to the Holdem Hand History project.\r\n")
					.append("THis application will convert filtered Hand History files to a universal format. \r\n")
					.append("The files MUST have been processed by the HHFilter application before being processed by this program/\r\n")
					.append("\r\n")
					.append("There are many different poker websites with different formats of hands. But the rules of Hold'em are very\r\n")
					.append("consistent, so all Hand History files follow the same set of rules reguarding content.  \r\n")
					.append("This is not a final version, but a starting point. The basic idea is that a single set of applicationbs be able to\r\n")
					.append("analyze Hand History from any website. So, write one converter for each format of Hand Histoty and \r\n")
					.append("and use only one set of applications that analyze Hand History data from any site. Obvious.\r\n")
					.append("\r\n")
					.append("It is our hope that some programmers will help to expand on this by writing additional converters. \r\n")
					.append("We meanwhile are focused on completing the analysis applications.\r\n")
					.append("\r\n")
										.append("Try it. Send me your suggestions and comments. Feel free to use any of the source code.\r\n")  
					.toString();
			// @formatter:on

}
