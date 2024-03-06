package holdemhandhistory;

/*-  ******************************************************************************
 *  @author PEAK_

 ****************************************************************************** */

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
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

/*-******************************************************************************
 * Our end objective is to be able to convert Hand Histories from many different
 * Websites to a common universal format. For a start, we only support PokerStars
 * format and some sites that use the PokerStars Hand History format.
 * 
* This class exist mostly because of the poor quality of the Hand Histories
 * that can be purchased. They contain games of the wrong radio, wrong level, __
* 
* The class will be executed many times in testing and debug. 
* By filtering out the bad hands a lot of time is saved.
* Input is always a directory that contains 1 or more files.
* We read and process one file at a time.
* 	
* A Hand History file may contain 1 to thousands of hands. 
* We process one hand at a time until all hands in a file have been processed.
* Then we do the next file until all files have been processed.
* 
* When we process a hand:
* 		We check for hands that we do not want and skip over them.
* 	    First we check for invalid hands such as hands that are not in PokerStars format,
*    	Then we checks for hands that may cause integrity issues such as not having 6 players.
* 	    Then we check for problems that would cause more complexity in processing a hand, 
* 	    	such as players leaving table, being disconnected, etc.	
* 		We require that each player in game have played a minimum number of hands so that
*         the player can be categorized by type.  ( Fish, Lag, Tag, Average, Shark, Winner, Looser ).
*         
* In a future release we may make various types of checking user selectable.       
* 		
* If a hand meets all of our requirements:
* 		We write as a .txt file the filtered hands.  No additional processing. 
* 	    THis is both as inputb to GUIConvertToUniversal and for users that may
* 	    want to develop their own applications.
* 	   
 * Application outputs:  ( Optional )
*	 .txt file that contains the filtered but not converted hands. 
*   The filtered .txt files are the required input to GUIConvertToUniversal. 
 *******************************************************************************/

public class GUIFilter implements Constants {

	private static String pathIn = "C:\\HH\\";
	private static String pathOut = "C:\\HHFiltered\\";
	private static String selectedPathIn = "";
	private static String selectedPathOut = "";

	/*-******************************************************************************
	 * HashMap and HashSet hmapPlayerGames - 
	 * Key is player name, data is number of  hands played hashSetHandNumbers 
	 *	 Hand numbers, used to check for duplicates* hashSetFileNames 
	 * File names, used to check for duplicates.
	 *******************************************************************************/
	private static final HashSet<Long> hashSetHandNumbers = new HashSet<>(10000000); // duplicate check
	static int nextPLayerID = 1000; // playerID numbers to assign next - incremented
	static Long nextHandID = 1000L; // handID numbers to assign next - incremented
	private static final HashMap<String, String> hashMapNameToName2 = new HashMap<>(2000000);
	private static final HandPS[] handArrayPS = new HandPS[100000];

	private static int handIndex = 0; // hands in handArrayPS

	private static int handsThisFile = 0;
	private static int handsMax = 0;

	private static final int MAX_FILES = 600000;
	private static final String[] filesInFolder = new String[MAX_FILES];
	private static int filesInFolderCount = 0;
	private static String fileInFolder = ""; // Current file
	private static String fileNameFiltered = "";
	private static int filesFiltered = 0;
	private static int filesRead = 0;
	private static int handsRead = 0;
	private static int games = 0;
	private static int handsRejected = 0;
	private static int handsAccepted = 0;
	private static int hashSetHandNumbersSize = 0;
	private static int duplicateHands = 0;
	private static int filteredFilesWritten = 0;

	/*-************************************************************************************************
	 * Display definitions
	************************************************************************************************ */
	private static JFrame frame;
	private static final JButton buttonRun = new JButton("Run             ");

	private static final JButton buttonHelp = new JButton("Help      ");

	private static final JButton buttonFolder = new JButton("Select Folder");

	private static final JTextField entryText = new JTextField(
			"                                                                             ");
	private static final JTextField statusTextRead1 = new JTextField(
			"                                                                            ");
	private static final JTextField statusTextAcceptReject = new JTextField(
			"                                                                            ");

	private static final JTextField statusDirectories = new JTextField(
			"                                                                            ");

	private static JLabel blank = new JLabel("   ");

	/*-************************************************************************************************
	 * Check boxes  
	************************************************************************************************ */
	private static final JRadioButton ps = new JRadioButton("PokerStars");

	private static final JRadioButton GGPoker = new JRadioButton("GGPoker");

	private static final JRadioButton ph = new JRadioButton("Peak Holdem");

	private static final JRadioButton usd = new JRadioButton("US Dollars");

	private static final JRadioButton canada = new JRadioButton("Canadian Dollars");

	private static final JRadioButton euro = new JRadioButton("Euro");

	private static final JCheckBox players6 = new JCheckBox("Must be 6 at table");

	private static final JCheckBox allowDuplicate = new JCheckBox("Allow duplicate hands");

	private static final JCheckBox removeNames = new JCheckBox("Remove Names");

	private static final JCheckBox sixMax = new JCheckBox("six max only");

	private static final JCheckBox writeNames = new JCheckBox("Write name file");

	private static final JCheckBox crash = new JCheckBox("Crash severe error");

	private static final JPanel panelRadio = new JPanel();

	private static final JPanel panelRadioSite = new JPanel();

	private static final JPanel panelRadioCurrency = new JPanel();

	private static final JRadioButton stakes050_010 = new JRadioButton("$0.05/$0.10    ");

	private static final JRadioButton stakes025_050 = new JRadioButton("$0.25/$0.50    ");

	private static final JRadioButton stakes05_1 = new JRadioButton("$0.5/$1    ");

	private static final JRadioButton stakes1_2 = new JRadioButton("$1/$2    ");

	private static final JRadioButton stakes250_5 = new JRadioButton("$2.50/$5.00    ");

	private static final JRadioButton stakes5_10 = new JRadioButton("$5/$10    ");

	private static final JRadioButton stakes10_20 = new JRadioButton("$10/$20    ");

	private static final JRadioButton stakes25_50 = new JRadioButton("$25/$50    ");

	/*-************************************************************************************************
	 *Check boxes
	************************************************************************************************ */
	static boolean psOption = true;
	static boolean GGPokerOption = true;
	static boolean phOption = true;
	static boolean usdOption = true;
	static boolean canadaOption = true;
	static boolean euroOption = true;

	static boolean player6MaxOption = true;
	static boolean sixPlayersOption = true;
	static boolean allowDuplicatesOption = false;
	static boolean removeNamesOption = true;
	static boolean writeNamesOption = false;
	static boolean sixMaxOption = true;

	static boolean stakes050_010Option = false;
	static boolean stakes025_050Option = false;
	static boolean stakes05_1Option = false;
	static boolean stakes1_2Option = false;
	static boolean stakes250_5Option = false;
	static boolean stakes5_10Option = false;
	static boolean stakes10_20Option = false;
	static boolean stakes25_50Option = false;

	/*-************************************************************************************************
	 *Panels 
	************************************************************************************************ */

	private static final JPanel panel0 = new JPanel();

	private static final JPanel panelBlank = new JPanel();

	private static final JPanel panelControl = new JPanel();

	private static final JPanel panelCheck = new JPanel();

	private static final JPanel panelStakes = new JPanel();

	/*- 
	 * Data used when writing to filtered directory. 
	 */
	private static String hhDate = "";

	private static final int files = 0;

	private static final String[] playerNames = new String[6];
	private static int skip;

	private static int status = 0; // Why a hand was rejected or accepted

	/*-******************************************************************************
	 * Timers used by HandFromPokerStars
	 *******************************************************************************/
	private static final Timer timer = new Timer();
	private static final Timer timerListOfFiles = new Timer();
	private static final Timer timerReadPS = new Timer();
	private static final Timer timerSelect = new Timer();
	private static final Timer timerWriteFiltered = new Timer();
	private static final Timer timerReplaceNames = new Timer();
	private static final Timer timerAddHand = new Timer();
	private static final Timer timerTotal = new Timer();
	private static Timer timerAll = new Timer();

	private static int p2 = 0;

	public static void main(String[] args) {
		if (!ValidInstallation.checkValid()) {
			return;
		}
		startupWindow();
	}

	/*- *****************************************************************************
	* Display main window and options to select folder ansd to run program.
	****************************************************************************** */
	private static void startupWindow() {
		timer.resetTimer();
		timerListOfFiles.resetTimer();
		timerReadPS.resetTimer();
		timerSelect.resetTimer();
		timerWriteFiltered.resetTimer();
		timerReplaceNames.resetTimer();
		timerAddHand.resetTimer();
		timerTotal.resetTimer();

		final var f = new Font(Font.SERIF, Font.BOLD, 18);
		frame = new JFrame("Hold'em  Hand History Filter");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(100, 50);
		frame.setPreferredSize(new Dimension(1600, 600));

		panelBlank.add(blank);
		blank.setFont(f);

		panel0.setLayout(new GridLayout(14, 1));
		panel0.setSize(1600, 600);

		final var options1 = Box.createHorizontalBox();
		options1.setFont(f);

		Box.createHorizontalBox();
		entryText.setBorder(BorderFactory.createTitledBorder("Input and Output folders     "));
		entryText.setBackground(Color.WHITE);
		entryText.setFont(f);
		panel0.add(entryText);
		panel0.add(panelBlank);

		panelCheck.setLayout(new GridLayout(1, 11));
		final var optionsCheck = Box.createHorizontalBox();
		optionsCheck.setFont(f);
		panelCheck.setBorder(BorderFactory.createTitledBorder("Filter criterion - Check if must have"));
		panelCheck.add(removeNames);
		panelCheck.add(ps);
		panelCheck.add(ph);
		panelCheck.add(GGPoker);
		panelCheck.add(usd);
		panelCheck.add(canada);
		panelCheck.add(players6);
		panelCheck.add(sixMax);
		panelCheck.add(allowDuplicate);
		panelCheck.add(writeNames);
		panelCheck.add(crash);
		panel0.add(panelCheck);
		panel0.add(panelBlank);
		removeNames.setSelected(true);
		ps.setSelected(true);
		usd.setSelected(true);
		players6.setSelected(true);
		allowDuplicate.setSelected(false);
		sixMax.setSelected(true);
		crash.setSelected(true);
		ps.addActionListener(new Listener());
		GGPoker.addActionListener(new Listener());
		ph.addActionListener(new Listener());
		usd.addActionListener(new Listener());
		canada.addActionListener(new Listener());
		players6.addActionListener(new Listener());
		allowDuplicate.addActionListener(new Listener());
		sixMax.addActionListener(new Listener());
		writeNames.addActionListener(new Listener());
		crash.addActionListener(new Listener());
		panelCheck.setFont(f);
		panel0.add(panelBlank);
		panel0.add(panelBlank);

		panelRadioSite.setLayout(new GridLayout(1, 3));
		final var radioSite = Box.createHorizontalBox();
		radioSite.setFont(f);
		final var radioGroupSite = new ButtonGroup();
		panelRadioSite.setFont(f);
		panelRadioSite.setBorder(BorderFactory.createTitledBorder("Poker Site"));
		panelRadioSite.add(ps);
		panelRadioSite.add(ph);
		panelRadioSite.add(GGPoker);
		radioGroupSite.add(ps);
		radioGroupSite.add(ph);
		radioGroupSite.add(GGPoker);
		ps.setSelected(true);
		ps.addActionListener(new Listener());
		ph.addActionListener(new Listener());
		GGPoker.addActionListener(new Listener());
		panel0.add(panelRadioSite);
		panel0.add(panelBlank);
		panel0.add(panelBlank);

		panelRadioCurrency.setLayout(new GridLayout(1, 3));
		final var radioCurrency = Box.createHorizontalBox();
		radioCurrency.setFont(f);
		final var radioGroupCurrency = new ButtonGroup();
		panelRadioCurrency.setFont(f);
		panelRadioCurrency.setBorder(BorderFactory.createTitledBorder("Currency"));
		panelRadioCurrency.add(usd);
		panelRadioCurrency.add(canada);
		panelRadioCurrency.add(euro);
		radioGroupCurrency.add(usd);
		radioGroupCurrency.add(canada);
		radioGroupCurrency.add(euro);
		usd.setSelected(true);
		usd.addActionListener(new Listener());
		canada.addActionListener(new Listener());
		euro.addActionListener(new Listener());
		panelRadio.setFont(f);
		panel0.add(panelRadioCurrency);
		panel0.add(panelBlank);
		panel0.add(panelBlank);

		panelRadio.setLayout(new GridLayout(1, 8));
		final var radio = Box.createHorizontalBox();
		radio.setFont(f);
		final var radioGroup = new ButtonGroup();
		panelRadio.setFont(f);
		panelRadio.setBorder(BorderFactory.createTitledBorder("Stakes"));
		panelRadio.add(stakes050_010);
		panelRadio.add(stakes025_050);
		panelRadio.add(stakes05_1);
		panelRadio.add(stakes1_2);
		panelRadio.add(stakes250_5);
		panelRadio.add(stakes5_10);
		panelRadio.add(stakes10_20);
		panelRadio.add(stakes25_50);
		radioGroup.add(stakes050_010);
		radioGroup.add(stakes025_050);
		radioGroup.add(stakes05_1);
		radioGroup.add(stakes1_2);
		radioGroup.add(stakes250_5);
		radioGroup.add(stakes5_10);
		radioGroup.add(stakes10_20);
		radioGroup.add(stakes25_50);
		panel0.add(panelRadio);
		stakes1_2.setSelected(true);
		stakes050_010.addActionListener(new Listener());
		stakes025_050.addActionListener(new Listener());
		stakes05_1.addActionListener(new Listener());
		stakes1_2.addActionListener(new Listener());
		stakes250_5.addActionListener(new Listener());
		stakes5_10.addActionListener(new Listener());
		stakes10_20.addActionListener(new Listener());
		stakes25_50.addActionListener(new Listener());
		panelRadio.setFont(f);
		panel0.add(panelStakes);
		panel0.add(panelBlank);
		panel0.add(panelBlank);

		final var control = Box.createHorizontalBox();
		control.setFont(f);
		control.add(buttonFolder);
		control.add(buttonRun);
		control.add(buttonHelp);
		panelControl.add(control);
		panel0.add(panelControl);
		panel0.add(panelBlank);
		buttonRun.setToolTipText("Start Analysis");
		buttonFolder.setToolTipText("Select Input and Ouput folders");
		buttonRun.setBackground(Color.GREEN);
		buttonHelp.setBackground(Color.GREEN);
		buttonFolder.setBackground(Color.GREEN);
		buttonRun.addActionListener(new Listener());
		buttonHelp.addActionListener(new Listener());
		buttonFolder.addActionListener(new Listener());
		buttonRun.setEnabled(false);

		Box.createHorizontalBox();
		statusTextRead1.setBorder(BorderFactory.createTitledBorder("Reading input folder      "));
		statusTextRead1.setBackground(Color.WHITE);
		statusTextRead1.setFont(f);
		panel0.add(statusTextRead1);
		panel0.add(panelBlank);

		statusTextAcceptReject.setBorder(BorderFactory.createTitledBorder("Accept and Reject games and hands       "));
		statusTextAcceptReject.setBackground(Color.WHITE);
		statusTextAcceptReject.setFont(f);
		panel0.add(statusTextAcceptReject);

		frame.add(panel0);
		frame.pack();
		frame.setVisible(true);
	}

	/*- ******************************************************************************
	 *  Initialize all variables Required if run again. 
	****************************************************************************** */
	private static void initialize() {
		statusTextRead1.setBackground(WHITE);
		ParseCodes.initialize();
		for (int k = 0; k < 6; ++k) {
			playerNames[k] = "????";
		}
		games = 0;
		handsRejected = 0;
		handsAccepted = 0;
		hashSetHandNumbersSize = 0;
		hhDate = "";
		filesFiltered = 0;
		fileInFolder = "";
		filesRead = 0;
		handsRead = 0;
		handIndex = 0;
		filteredFilesWritten = 0;
		duplicateHands = 0;
	}

	/*-******************************************************************************
	 * One file in the input directory is read. 
	 *  Each hand is checked for criteria that will cause the hand to be  rejected. 
	 *  The hand number of all hands not rejected are saved in an instance  of Hands. 
	 *  For each player an instance of the HHPlayer class is created and  the number of hands 
	 *  that the player is counted.
	****************************************************************************** */
	static boolean processAll() {
		initialize();
		timer.startTimer();
		timerTotal.startTimer();
		buttonFolder.setEnabled(false);

		final var file = new File(selectedPathIn);
		if (!file.exists()) {
			statusTextRead1.setText("ERROR Folder does not exist   " + selectedPathIn);
			statusTextRead1.setBackground(RED);
			statusTextRead1.update(statusTextRead1.getGraphics());
			return false;
		}
		statusTextRead1
				.setText("Create Filtered Folder for PokerStars output and delete existing files " + selectedPathOut);
		statusTextRead1.update(statusTextRead1.getGraphics());
		final var fileD = new File(selectedPathOut);
		if (!pathOut.equals(selectedPathOut)) {
			if (fileD.exists()) {
				FileUtils.deleteDir(fileD);
			}
			if (!fileD.exists()) {
				fileD.mkdir();
			}
		}
		statusTextRead1.setText("Obtaining a list of files from Folder and Sub Folders  " + selectedPathIn);
		statusTextRead1.update(statusTextRead1.getGraphics());
		timerListOfFiles.startTimer();
		listOfFiles(file); // Fill the filesInFolder array with the names of each file
		checkIfMatchOptions();

		timerListOfFiles.sumTimer();
		for (int filiIndex = 0; filiIndex < filesInFolderCount; ++filiIndex) {
			fileInFolder = filesInFolder[filiIndex];
			String path = selectedPathIn + fileInFolder;

			if (!readOneHHFile(path)) {
				statusTextRead1.setText(new StringBuilder().append("ERROR reading file ").append(selectedPathIn)
						.append(" ").append(fileInFolder).toString());
				Logger.logError(new StringBuilder().append("ERROR reading file ").append(selectedPathIn).append(" ")
						.append(fileInFolder).toString());
				statusTextRead1.update(statusTextRead1.getGraphics());
			} else {
				// File read OK so now we can start processing the file.
				// Select hands that meet our requirements, ignore others
				// Selected hands are placed in handArrayPS array
				if (readHHPSFile(path)) {
					if (handIndex > handsMax) {
						handsMax = handIndex;
					}
				}
				if (handIndex >= MAX_HANDS_IN_FILE) {
					if (handIndex != 0 && handsAccepted != 0) {
						writeGoodHands();
					}
					for (int i = 0; i < handIndex; ++i) {
						handArrayPS[i] = null;
					}
					handIndex = 0;
				}
				if (skip++ >= 4) {
					skip = 0;
					updateDisplay();
				}
			}
		}
		if (handIndex != 0 && handsAccepted != 0) {
			writeGoodHands();
		}
		System.out.println("//handsMax " + handsMax);
		updateDisplay();
		if (writeNamesOption && removeNamesOption) {
			writePlayerInfoFile(selectedPathOut + "playerNames");
		}
		if (handsAccepted < 300 || filteredFilesWritten <= 0) {
			statusTextAcceptReject.setBackground(RED);
		}
		timerTotal.sumTimer();
		displayTimers();
		SelectCodes.report();
		statusTextRead1.setBackground(GREEN);
		return true;
	}

	/*-******************************************************************************
	 * Display results of HandFromPokerStars timers
	 * private static final Timer timer = new Timer();
	****************************************************************************** */
	private static void checkIfMatchOptions() {
		int n = 0;
		int n1 = 0;
		String st = filesInFolder[0];
		String st1 = filesInFolder[1];
		n = st.indexOf("0.05");
		if (n != -1) {
			stakes050_010.setSelected(true);
		} else {
			n = st.indexOf("0.50");
			if (n != -1) {
				stakes05_1.setSelected(true);
			} else {
				n = st.indexOf("0.25");
				if (n != -1) {
					stakes025_050Option = true;
				} else {
					n = st.indexOf("2.50");
					if (n != -1) {
						stakes250_5.setSelected(true);
					} else {
						n = st.indexOf("5-10-");
						if (n != -1) {
							stakes5_10.setSelected(true);
						} else {
							n = st.indexOf("1-2-");
							if (n != -1) {
								stakes1_2.setSelected(true);
							} else {
								n = st.indexOf("25-50-");
								if (n != -1) {
									stakes25_50.setSelected(true);
								} else {
									n = st.indexOf("10-20-");
									if (n != -1) {
										stakes10_20.setSelected(true);
									}
								}
							}
						}
					}
				}
			}
		}

		n = st.indexOf("_GG_");
		n1 = st1.indexOf("_GG_");
		if ((n != -1 || n1 != -1) && !GGPokerOption) {
			GGPoker.setSelected(true);
		}
		n = st.indexOf("PokerStarsON");
		if (n != -1 && !canadaOption) {
			canada.setSelected(true);
		}

		checkSelected();
	}

	/*-******************************************************************************
	 * Display results of HandFromPokerStars timers
	 * private static final Timer timer = new Timer();
	****************************************************************************** */
	private static void displayTimers() {
		Logger.log("");
		Logger.log("//Total time \t" + Format.format(timerTotal.getTimer("")));
		Logger.log("//List of files      \t" + Format.format(timerListOfFiles.getTimer("")));
		Logger.log("//ReadPS            \t" + Format.format(timerReadPS.getTimer("")));
		Logger.log("//Select           \t" + Format.format(timerSelect.getTimer("")));
		Logger.log("//Write Filtered            \t" + Format.format(timerWriteFiltered.getTimer("")));
		Logger.log("//Replace names          \t" + Format.format(timerReplaceNames.getTimer("")));
		Logger.log("//Add Hand           \t" + Format.format(timerAddHand.getTimer("")));
		Logger.log("//Total          \t" + Format.format(timerTotal.getTimer("")));
		Logger.log("");
	}

	/*- ******************************************************************************
	 * Update display  
	*******************************************************************************/
	private static void updateDisplay() {
		int p = hashMapNameToName2.size();
		final var time = timer.getElapsedTimeString();
		hashSetHandNumbersSize = hashSetHandNumbers.size();
		statusTextRead1.setText(new StringBuilder().append("Files in Folder ").append(Format.format(filesInFolderCount))
				.append("     Files read ").append(Format.format(filesRead)).append("   Accepted ")
				.append(Format.format(handsAccepted)).append("   Rejected ").append(Format.format(handsRejected))
				.append("   Duplicate ").append(Format.format(duplicateHands)).append("   Files written ")
				.append(Format.format(filteredFilesWritten)).append("  Hands ")
				.append(Format.format(hashSetHandNumbersSize)).append("  Players ").append(Format.format(p))
				.toString());
		statusTextAcceptReject.setText(new StringBuilder().append(" Hands accepted ")
				.append(Format.format(handsAccepted)).append("     Hands rejected  ")
				.append(Format.format(handsRejected)).append("      Elapsed time ").append(time).toString());
		statusTextRead1.update(statusTextRead1.getGraphics());
		statusTextAcceptReject.update(statusTextAcceptReject.getGraphics());
	}

	/*-******************************************************************************
	 * Check for duplicate files.  if copied by windows with same name  
	 * nnnn - Copy of nnnn - Copy (n)
	 * Arg0 - File name
	 * Returns false if not a copy
	****************************************************************************** */
	private static boolean duplicateFile(String st) {
		if (!(st.contains("copy") || st.contains("Copy"))) {
			return false;
		}
		return true;
	}

	/*- 
	*	Print current hand.  
	*	Test and debug.
	*/
	static void printHand() {
		for (int i = 0; i < handArrayPS[handIndex].numLines; ++i) {
			Logger.log("//" + handArrayPS[handIndex].lines[i]);
		}
	}

	private static int fileNameNum = 100;

	/*-******************************************************************************
	 * Create new file name The file name is hh with a unique string based on
	 * the current date and time. Returns a new file name as a String.
	*******************************************************************************/
	private static String newHHFileName() {
		++fileNameNum;
		final var dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		hhDate = dateFormat.format(new Date());
		final var num = filesFiltered > 9 ? Integer.toString(filesFiltered) : "0" + Integer.toString(files);

		return new StringBuilder().append(hhDate.substring(5, 7)).append(hhDate.substring(8, 10))
				.append(hhDate.substring(11, 13)).append(hhDate.substring(14, 16)).append(fileNameNum).toString();
	}

	/*-******************************************************************************
	 * Get names of all files in a directory and subdirectories. Recvursive. 
	 * Arg0 - a File The array filesInFolder is filled with file names filesInFolderCount
	 * is the number of file names in filesInFolder[]
	 * 
	 * Example: 
	 * File file = new File(path) 
		*******************************************************************************/
	public static boolean listOfFiles(File dirPath) {
		var st = "";
		final var filesList = dirPath.listFiles();
		for (var file : filesList) {
			if (file.isFile()) {
				st = file.getName();
				if (st.endsWith(".txt")) {
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

	/*-	******************************************************************************
	 * This method is poker site independent.
	 * It will perform some basic error checking then call a site specific method
	 * to read and process the file.
	 * Read Hand History as string - PokerStars
	 * Arg0 - File to read
	 * Returns true if file read OK
	 	*******************************************************************************/
	private static boolean readOneHHFile(String path) {
		handsThisFile = 0;
		// If a file has been copied with the same name then then the copy file name
		// will have "copy" in the name. We ignore copied files and conditionally delete
		// them.
		if (duplicateFile(path)) {
			return false;
		}

		final var file = new File(path);
		if (file.length() == 0) {
			Logger.logError(new StringBuilder().append("read() file length 0 ").append(file.length()).append(" ")
					.append(path).toString());
			return false;
		}
		if (!file.exists()) {
			Logger.logError("read() ERROR file not found  " + path);
			return false;
		}
		return true;
	}

	/*-	******************************************************************************
	 * Read and process one PokerStars Hand History file. Poker site specific.
	 * One file may contain thousands of hands
	 * The handsArray array is updated. 
	 * 
	 * As each hand is read it is used to construct:
	 *  	An array of hands that have been accepted PokerStars format.
	 *  	An array in a PokerStars format that is used as input to the Class.
	 *  
	 * Only hands accepted by HandSelectHandPS() are added to the array.
	  * Arg0 - Path to file
	 * Returns true if all is OK. False if  any error 
	****************************************************************************** */
	private static boolean readHHPSFile(String path) {
		int ndx = 0;
		int length = 0;
		boolean handDone = false;
		boolean badHand = false;
		boolean empty = false;

		if (handIndex == 0) {
			handArrayPS[handIndex] = new HandPS();
		}

		BufferedReader objReader = null;
		try {
			objReader = new BufferedReader(new FileReader(path));
			++filesRead;
			timerReadPS.startTimer();
			// read every line in the file. Isolate hands into handArrayPS array of HandPS
			// objects
			// Check each hand for errors and skip over bad hands
			while ((handArrayPS[handIndex].lines[ndx] = objReader.readLine()) != null) {
				length = handArrayPS[handIndex].lines[ndx].length();
				empty = "\r\n".equals(handArrayPS[handIndex].lines[ndx]) || length < 3
						|| handArrayPS[handIndex].lines[ndx] == null;
				// After one hand has been read
				if (!handDone) {
					if (ndx >= Hand.MAX) {
						ndx = 0;
						badHand = true;
						SelectCodes.updateCodes(SelectCodes.LARGER_THAN_MAX);
						return false;
					} else {
						if (empty && ndx != 0) {
							handDone = true;
						} else if (!empty) {
							++handArrayPS[handIndex].numLines;
							++ndx;
						}
					}
				} else {
					// One hand has been read so check it for errors
					if (handDone && !badHand) {
						// Select hand and check for errors
						if (!selectAndCheckPS(handIndex)) {
							++handsRejected;
							// Some codes disqualify entire file
							if (status == SelectCodes.NOT_6MAX || status == SelectCodes.NOT_USD
									|| status == SelectCodes.NOT_CANADIAN || status == SelectCodes.INVALID_STAKES
									|| status == SelectCodes.NOT_GGPOKER) {
								return false;
							}
						} else {
							handArrayPS[handIndex].lines[++ndx] = "\r\n";
							++handIndex;
							++handsAccepted;
							++handsThisFile;
						}
						handArrayPS[handIndex] = new HandPS();
						ndx = 0;
						handDone = false;
					}
				}
			}
			// objReader.close();
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
		timerReadPS.sumTimer();
		return true;
	}

	/*-	******************************************************************************
	 * Select PokerStars hand and check for errors 
	 * Arg0 = path to Hand History file
	 * Arg1 - Index into handArrayPSFile
	 * Returns true if all is ok. false if hand is not accepted
	 * 
	 * If a file has been copied with the same name then then the copy file name 
	 * will have "copy" in the name. We ignore copied files and conditionally delete them.
	 * 
	 * Each hand is assigned a unique hand Id that is constructed using information is line 0 of
	 * the Hand History. A hand number and date time information.
	 * We use all of that to construct a unique ID number.
	 * Duplicates are ignored and optionally the file containing them is deleted.
	 * 
		****************************************************************************** */
	private static boolean selectAndCheckPS(int index) {
		// hash set is used only to detect duplicate hands
		timerAddHand.startTimer();
		if (handArrayPS[index].lines[0].length() < 35) {
			SelectCodes.updateCodes(SelectCodes.INVALID_LINE_0);
			return false;
		}
		if (!handArrayPS[index].lines[0].startsWith("PokerStars")) {
			int n = handArrayPS[index].lines[0].indexOf("PokerStars");
			if (n == -1 && n < 4) {
				SelectCodes.updateCodes(SelectCodes.NOT_POKERSTARS);
				return false;
			} else {
				handArrayPS[index].lines[0] = handArrayPS[index].lines[0].substring(n);
			}
			return false;
		}

		long handID = ParsePS.getHandID(handArrayPS[index].lines[0]);

		if (!allowDuplicatesOption) {
			if (!hashSetHandNumbers.contains(handID)) {
				hashSetHandNumbers.add(handID);
			} else {
				++duplicateHands;
				++handsRejected;
				SelectCodes.updateCodes(SelectCodes.DUPLICATE_HAND_ID);
				timerAddHand.sumTimer();
				return false;
			}
		}
		timerAddHand.sumTimer();

		timerSelect.startTimer();
		status = SelectPS.selectHand(handArrayPS[handIndex]);
		SelectCodes.updateCodes(status);
		if (status != SelectCodes.OK) {
			timerSelect.sumTimer();
			return false;
		}
		timerSelect.sumTimer();
		return true;
	}

	/*-******************************************************************************
	 * After MAX_HANDS  have been processed or when the the final hand
	 * is processed this method is called to write the HandArray class to a file.
	 * The result is a folder with one or more files but none of the files are larer than MAX_HANDS.
	 * 
	 * All of the hands in handArrayFile have been processed and their status is OK.
	 * All of them will be written to a   .txt file  
	 * 
	 * The HandArray Class is used for performance.
	 * Instead of appending each instance of a Hand to a file they are instead
	 * added to an array of Hand in HandArray and written with one write,
	 * not many.
	 * The file is flushed and closed after being written.
	****************************************************************************** */
	private static void writeGoodHands() {
		final var st = newHHFileName();
		fileNameFiltered = new StringBuilder().append(selectedPathOut).append(st).append(".txt").toString();
		writeHHFilteredFiles(fileNameFiltered);
		++filteredFilesWritten;
	}

	/*-	******************************************************************************
	 * Write a single file with many hands appended.
	 * This file is in the PokerStars format, a .txt file of strings.
	 * Names have been replaced with Integer ID value as string 
	 * Hands consist of many Strings  separated by "\r\n".
	 * All of the hands in the handArrayPS are written into a single file.
	 * Arg0 - Path name
	 ****************************************************************************** */
	private static boolean writeHHFilteredFiles(String path) {
		timerWriteFiltered.startTimer();
		if (removeNamesOption) {
			timerReplaceNames.startTimer();
			replaceNamesAllHands();
			if (euroOption) {
				replaceCurrencyAllHands();
			}
			timerReplaceNames.sumTimer();
		}
		PrintWriter file = null;
		try {
			file = new PrintWriter(new BufferedWriter(new FileWriter(path, true), 32768));
		} catch (SecurityException | IOException i) {
			i.printStackTrace();
		}
		for (int i = 0; i <= handIndex; ++i) {
			if (handArrayPS[i].numLines > 26) {
				for (int j = 0; j < handArrayPS[i].numLines; ++j) {
					if (handArrayPS[i].lines[j] != null) {
						file.append(handArrayPS[i].lines[j] + "\r\n");
					}
				}
				file.append("\r\n");
			}
		}
		file.flush();
		file.close();
		timerWriteFiltered.sumTimer();
		return true;
	}

	/*-	******************************************************************************
	 * Write a single file with HandID data as text strings.
	 * See HandID String toEquivalentString()
	 * Arg0 - Path name
	 ****************************************************************************** */
	private static boolean writePlayerInfoFile(String path) {
		StringBuilder sb = new StringBuilder(10000);
		PrintWriter file = null;
		try {
			file = new PrintWriter(new BufferedWriter(new FileWriter(path, true), 32768));
		} catch (SecurityException | IOException i) {
			i.printStackTrace();
			return false;
		}

		for (Iterator iterator = hashMapNameToName2.entrySet().iterator(); iterator.hasNext();) {
			var mentry = (Map.Entry) iterator.next();
			String name = (String) mentry.getKey();
			String name2 = (String) mentry.getValue();
			sb = sb.append(name).append(",").append(name2).append(",");
		}
		String st = sb.toString();
		file.append(st);
		file.append("\r\n");

		return true;
	}

	/*-******************************************************************************
	* Replace currency in handArrayPS with $
	*This was a quick and dirty solution. We just replace € with $.
	*This assumes that GUIEvaluate will do the currency adjustment.
	*We have the same problem with Canadian dollars.
	*TODO
	*******************************************************************************/
	private static void replaceCurrencyAllHands() {
		for (int i = 0; i <= handIndex; ++i) {
			if (!replaceCurrencyOneHand(i)) {
				handArrayPS[i].numLines = 0; // Flag to not write to file
			}
		}
	}

	private static boolean replaceCurrencyOneHand(int ndx) {
		if (handArrayPS[ndx] == null || handArrayPS[ndx].lines[0] == null)
			return false;
		handArrayPS[ndx].lines[0] = handArrayPS[ndx].lines[0].replace("EUR", "USD");
		for (int i = 0; i < handArrayPS[ndx].numLines; i++) {
			handArrayPS[ndx].lines[i] = handArrayPS[ndx].lines[i].replace("€", "$");
			handArrayPS[ndx].lines[i] = handArrayPS[ndx].lines[i].replace("â‚¬", "$");
			// handArrayPS[ndx].lines[i] =
			// convertEuroToUSDInLine(handArrayPS[ndx].lines[i]);
		}
		return true;
	}

	private static final double EURO_TO_USD_RATE = 1.12;

	/*-
	 * Convert Euro amounts to US Dollars in a given line of PokerStars hand history.
	 *
	 * @param line The line of PokerStars hand history to be scanned and modified.
	 * @return The line with Euro amounts converted to US Dollars.
	 */
	private static String convertEuroToUSDInLine(String line) {
		// Regular expression to match Euro amounts (e.g., €100.00)
		String euroPattern = "€(\\d+(\\.\\d{1,2})?)";
		Pattern pattern = Pattern.compile(euroPattern);
		Matcher matcher = pattern.matcher(line);

		StringBuffer modifiedLine = new StringBuffer();
		while (matcher.find()) {
			double euroAmount = Double.parseDouble(matcher.group(1));
			double usdAmount = euroAmount * EURO_TO_USD_RATE;
			matcher.appendReplacement(modifiedLine, "USD " + String.format("%.2f", usdAmount));
		}
		matcher.appendTail(modifiedLine);
		return modifiedLine.toString();
	}

	/*-******************************************************************************
	* Replace names in handArrayPS with 
	* Names are replaced with Integer ID value as string.
	* The name and ID assigned are in the nameToID array
	*******************************************************************************/
	private static void replaceNamesAllHands() {
		for (int i = 0; i < PLAYERS; i++) {
			name[i] = "";
		}
		for (int i = 0; i <= handIndex; ++i) {
			if (!replaceNamesOneHand(i)) {
				handArrayPS[i].numLines = 0; // Flag to not write to file
			}
		}
	}

	private static String[] name = { "", "", "", "", "", "" };
	private static String[] name2 = { "", "", "", "", "", "" };
	private static String pat = "000000";
	private static DecimalFormat df = new DecimalFormat(pat);
	private static int c = 0;

	/*-******************************************************************************
	* Replace names in handArrayPS with 
	* Names are replaced with Integer ID value as string.
	*  The name and ID assigned are in the nameToID array
	*  
	*  In order to avoid problems caused by players using names like "fold" 
	*  that would result in errors with the simple String replace method we go to 
	*  great pains and a performance hit by not replacing names until we are very
	*  sure that the replace is correct. 
	*  Something to be re-evaluated in the future. 
	*  Arg0 - Index into handArrayPS
	*******************************************************************************/
	private static boolean replaceNamesOneHand(int ndx) {
		if (handArrayPS[ndx] == null || handArrayPS[ndx].lines[0] == null)
			return false;
		String st, st2;
		int n, n2 = 0;

		for (int i = 0; i < PLAYERS; i++) {
			st = handArrayPS[ndx].lines[i + 2];
			n = st.indexOf(": ") + 2;
			n2 = st.indexOf(" ($");

			if (n2 == -1) {
				n2 = st.indexOf(" (€");
			}
			if (n2 == -1) {
				n2 = st.indexOf(" (â");
			}
			if (n2 == -1) {
				Logger.logError("ERROR in replaceNamesOneHand() invalid currency  " + st);
				return false;
			}
			st = st.substring(n, n2);
			if (!name[i].equals(st)) {
				name[i] = st;
				name2[i] = hashMapNameToName2.get(name[i]);
				if (name2[i] == null || name2[i].isEmpty()) {
					name2[i] = df.format(nextHandID++);
					hashMapNameToName2.put(name[i], name2[i]);
				}
			}
			StringBuilder sb = new StringBuilder(handArrayPS[ndx].lines[i + 2]);
			sb.replace(n, n2, name2[i]);
			handArrayPS[ndx].lines[i + 2] = sb.toString();
		}

		// RetqJg: posts small blind $1
		for (int i = 0; i < PLAYERS; i++) {
			n = handArrayPS[ndx].lines[8].indexOf(": ");
			st = handArrayPS[ndx].lines[8].substring(0, n);
			if (st.equals(name[i])) {
				StringBuilder sb = new StringBuilder(handArrayPS[ndx].lines[8]);
				sb.replace(0, n, name2[i]);
				handArrayPS[ndx].lines[8] = sb.toString();
				break;
			}
		}
		// RetqJgX: posts big blind $2
		for (int i = 0; i < PLAYERS; i++) {
			n = handArrayPS[ndx].lines[9].indexOf(": ");
			st = handArrayPS[ndx].lines[9].substring(0, n);
			if (st.equals(name[i])) {
				StringBuilder sb = new StringBuilder(handArrayPS[ndx].lines[9]);
				sb.replace(0, n, name2[i]);
				handArrayPS[ndx].lines[9] = sb.toString();
				break;
			}
		}
		for (int i = 11; i < handArrayPS[ndx].numLines; ++i) {
			if (handArrayPS[ndx].lines[i] == null) {
				return false;
			}
			if (i > 17 && (handArrayPS[ndx].lines[i].startsWith("*** FLOP***")
					|| handArrayPS[ndx].lines[i].startsWith("*** TURN ***")
					|| handArrayPS[ndx].lines[i].startsWith("*** RIVER ***")
					|| handArrayPS[ndx].lines[i].startsWith("*** SHOW ***")
					|| handArrayPS[ndx].lines[i].startsWith("*** SUMMARY ***")
					|| handArrayPS[ndx].lines[i].startsWith("	Total pot "))) {
				// do nothing
			} else {
				for (int j = 0; j < PLAYERS; j++) {
					// RetqJg: folds
					// RetqJgX: calls $2
					String nam = name[j];
					st = handArrayPS[ndx].lines[i];
					n = st.indexOf(nam);
					if (n != -1) {
						n2 = st.indexOf(": ");
						if (n2 != -1) {
							st2 = st.substring(0, n2);
							if (st2.equals(nam)) {
								StringBuilder sb = new StringBuilder(handArrayPS[ndx].lines[i]);
								sb.replace(0, n2, name2[j]);
								handArrayPS[ndx].lines[i] = sb.toString();
								break;
							}
							// Seat 2: RetqJg folded before Flop
							if (st.startsWith("Seat ")) {
								st2 = st.substring(n2 + 2, n2 + 2 + nam.length() + 1);
								st2 = st2.trim();
								if (st2.equals(nam)) {
									StringBuilder sb = new StringBuilder(handArrayPS[ndx].lines[i]);
									sb.replace(n2 + 2, n2 + 2 + nam.length(), name2[j]);
									handArrayPS[ndx].lines[i] = sb.toString();
									break;
								}
							}
						}
						// Redmaster91 collected $23.05 from pot
						// Red and r91 are possible to cause problems
						st2 = st.substring(0, nam.length() + 1);
						st2 = st2.trim();
						if (st2.equals(nam)) {
							StringBuilder sb = new StringBuilder(handArrayPS[ndx].lines[i]);
							sb.replace(0, nam.length(), name2[j]);
							handArrayPS[ndx].lines[i] = sb.toString();
							break;
						}
						st2 = st.substring(n);
						if (st2.equals(nam)) {
							StringBuilder sb = new StringBuilder(handArrayPS[ndx].lines[i]);
							sb.replace(n, n + nam.length(), name2[j]);
							handArrayPS[ndx].lines[i] = sb.toString();
							break;
						}
					}
				}
			}
		}

		if (handArrayPS[ndx].lines[0].contains("#01398473577:")) {
			handArrayPS[ndx].printHand();
		}
		return true;
	}

	/*-	****************************************************************************
	 *  Is this name a partial duplicate of another name?
	 *  The problem happens when the start or end of a name matches exactly.
	 *  One name is shorter than the other. Blanks or : are not good delimiters because 
	 *  name can be at end of a line.
	 *  
	 * 		First 6 characters are an exact match.
	 *  	Seat 2: RetqJg ($37 in chips)
	 * 		Seat 3: RetqJgX ($69.68 in chips)
	 * 
	  * 	First 1 characters are an exact match.
	 *  	Seat 2: A ($37 in chips)
	 * 		Seat 3: AGuy ($69.68 in chips)
	 * 
	 * 
	 * 		OK both contain taco but taco is not a full name.
	 *  	Seat 2: tacoBill ($37 in chips)
	 * 		Seat 3: Samtaco ($69.68 in chips)
	 * This is vary rare but a big problem when it happens.
	 * We do the check only when there is a new player at the table.
	 * The simple solution, which is what we use, is to delete any hands where that happens.  
	 * TODO It there another practical way?
	 * Arg0 - New players name
	 * Arg1 - Where new player will be added in names if not partial duplicate
	 * Returns false if no partial duplication
	 ****************************************************************************** */
	private static boolean isDuplicateName(String nam, int index) {
		for (int i = 0; i < PLAYERS; i++) {
			if (i != index && (nam.startsWith(name[i]) || nam.endsWith(name[i]) || name[i].startsWith(nam)
					|| name[i].endsWith(nam))) {
				return true;
			}
		}
		return false;
	}

	/*-	******************************************************************************
	 *  Responds to button clicks.
		****************************************************************************** */
	private static class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			checkSelected();
			switch (e.getActionCommand()) {
			case "Select Folder" -> {
				selectedPathIn = chooseInputDirectory();
				if (selectedPathIn.equals(pathIn)) {
					JOptionPane.showMessageDialog(null, "You can not choose the root Folder as your input Folder .");
					selectedPathIn = "";
					selectedPathOut = "";
				}
				String st1 = selectedPathIn.substring(4);
				int n = st1.indexOf("\\");
				selectedPathOut = pathOut + st1.substring(n + 1);
				entryText.setText("Input Folder " + selectedPathIn + " Output Folder " + selectedPathOut);
				buttonRun.setEnabled(true);
			}
			case "Help      " -> JOptionPane.showMessageDialog(null, helpString);
			case "Run             " -> {
				processAll();
				buttonFolder.setEnabled(true);
			}
			default -> {
			}
			}
		}
	}

	/*-******************************************************************************
	 * Check selected
	 *******************************************************************************/
	private static void checkSelected() {
		if (ps.isSelected()) {
			psOption = true;
		} else {
			psOption = false;
		}
		if (ph.isSelected()) {
			phOption = true;
		} else {
			phOption = false;
		}
		if (GGPoker.isSelected()) {
			GGPokerOption = true;
		} else {
			GGPokerOption = false;
		}
		if (usd.isSelected()) {
			usdOption = true;
		} else {
			usdOption = false;
		}
		if (canada.isSelected()) {
			canadaOption = true;
		} else {
			canadaOption = false;
		}
		if (euro.isSelected()) {
			euroOption = true;
		} else {
			euroOption = false;
		}

		if (allowDuplicate.isSelected()) {
			allowDuplicatesOption = true;
		} else {
			allowDuplicatesOption = false;
		}
		if (players6.isSelected()) {
			player6MaxOption = true;
		} else {
			player6MaxOption = false;
		}
		if (removeNames.isSelected()) {
			removeNamesOption = true;
		} else {
			removeNamesOption = false;
		}
		if (sixMax.isSelected()) {
			sixMaxOption = true;
		} else {
			sixMaxOption = false;
		}
		if (writeNames.isSelected()) {
			writeNamesOption = true;
		} else {
			writeNamesOption = false;
		}
		if (crash.isSelected()) {
			Crash.crash = true;
		} else {
			Crash.crash = false;
		}

		if (stakes050_010.isSelected()) {
			stakes050_010Option = true;
		} else {
			stakes050_010Option = false;
		}
		if (stakes025_050.isSelected()) {
			stakes025_050Option = true;
		} else {
			stakes025_050Option = false;
		}
		if (stakes05_1.isSelected()) {
			stakes05_1Option = true;
		} else {
			stakes05_1Option = false;
		}
		if (stakes1_2.isSelected()) {
			stakes1_2Option = true;
		} else {
			stakes1_2Option = false;
		}
		if (stakes250_5.isSelected()) {
			stakes250_5Option = true;
		} else {
			stakes250_5Option = false;
		}
		if (stakes5_10.isSelected()) {
			stakes5_10Option = true;
		} else {
			stakes5_10Option = false;
		}
		if (stakes10_20.isSelected()) {
			stakes10_20Option = true;
		} else {
			stakes10_20Option = false;
		}
		if (stakes25_50.isSelected()) {
			stakes25_50Option = true;
		} else {
			stakes25_50Option = false;
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
			.append("This application will read a directory of Hand History files and reject hands that are nog best for\r\n")
			.append("doing analysis. When you obtain hands from a bulk supplier they will almost always have a high")
			.append("percengtage of hands that you do not want. The files may have totally in valid formats, truncated hands,\r\n")
			.append("hands with different currancies, hands with less than 6 players, hands that players suddenly disconnected.\r\n")
			.append("Many different resons for rejecting or selecting.\r\n")
			.append("\r\n")
			.append("Hand History files are reblocked so that a file will contain a large number of hands.\r\n")
			.append("There are many options for selecting the type(s) of hands to include in your output files.\r\n")
			.append("\r\n")
			.append("One option is remove names. For privacy reasons playe names are converted to a string that is   \r\n")
			.append("the equivalent of an Integer. So meBigGuy is converted to 004322. Takes the stink away from data mining and \r\n")
			.append("the purchase of bulk Hand History files.\r\n")
			.append("\r\n")
			.append("This application is intended for use with the GUIConvertToUniversal application, to convert Hand History files\r\n")
			.append("into a universal format for usw with the Hand History analysis programs that are a part of this project but\r\n")
			.append("may be useful for use with applications such as Holdem Manager.\r\n")
			.append("\r\n")
			.append("Try it. Send me your suggestions and comments. Feel free to use any of the source code.\r\n").toString();
	// @formatter:on

}
