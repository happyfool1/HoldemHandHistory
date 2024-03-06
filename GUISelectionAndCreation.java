package holdemhandhistory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;

class GUISelectionAndCreation implements Constants {

	/*- ********************************************************************************************** 
	 * There are multiple applications in this Hold'em analysis project. 
	 * This application is Hold'em Hand History. It analyzes Universal Format Hand History files.
	 * The current set of applications are:
	 *     Hold'em Hand History Analysis.
	 * 		Holde'm Evaluator.     
	 *  Planned future applications that are in progress:   
	 *     Hold'em Simulator. Simulates play and optimizes strategy. Uses Hand History Analysis data.
	 *  	Hold'em Interactive.  Play against the simulator with prompts.  
	 *  
	 *  We hope that by making all of this open source the set of applications will be expanded.
	 *     
	*@author PEAK
	*************************************************************************************************/

	/*- ************************************************************************************************
	* This Class reads universal Hand History files creates a list of player IDs that will be the input to 
	*analysis programs such as  GUIAnalyze. Other GUI applications will use this output in future versions.
	* 
	* What this class does it to read Universal Hand History files, a single Folder of files, analyzes the data, 
	* has the operator select player characteristics, then writes a list of playerIDs to a .ser file.
	* 
	* Helper Classes include:
	* 		PlayerCharacteristics.
	* 		HandHistoryObjects.
	* 		PlayersSelected.
	* 		HandReader.
	* 		 
	* The input is a single folder.
	* We read all of the Hand   files in  the input folder.
	* The files are in a universal format, not poker site dependent.
	* They were created by GUIConvertToUNiversal in the HoldemHandHistoryConverter project.
	* See that project for details.
	* 
	* The output is a list of playerIDs in a folder with the name:
	* Example: 
	* 		Input folder is c:\HHUniversal\new1000 
	* 		output folder is c:\HHSelected\new1000\abc.ser 
	* 		a.ser contains the list of playerIDs.
	* 	    b.ser contains the list of playerIDs.  A second group from same input folder
	* 
	* After each file is read we step through each hand and create one instance of PlayerCharacteristics
	* for each player and update data for that player.
	* Basically we are useing the Action class within the Hand class to update a variety of counts in
	* PlayerCharacteristics.
	* When all files have been read and processed we iteraste through the HashMap for  
	* PlayerCharacteristics and classify each player. 
	* We update the PlayerCharacteristics class instance for each player.
	* We then write a single file with data for all players.
	* 
	* In this class we do very little error checking, that was done by GUIConvertToUNiversal.
	* There are many reports.
	* 
	* The reason for doing all of this is for performance and memory requirements.
	* By classifying players in it's own application we can do it only one time for a new batch
	* of hands. 
	* In the GUIAnalysis application the operator starts by selecting what kind of
	* players this analysis will be for. The PlayerCharacteristics file is then used to either accept or 
	* reject players for analysis. A simple example would be that we are analyzing Lag players
	* so there is no need to create Player instances for any other type, greatly reducing storage 
	* requirements and improving performance.
	* 
	* This class will create HashMaps:
	* 		private static final HashMap<String, PlayerCharacteristics> playersMap = new HashMap<>(200000);
	* 		private static final HashMap<String, private static final
	*		 HashMap<Integer, Players> playersMap = new HashMap<>(200000);
	*   	HashMap playersCharacteristicsMap = new HashMap<>(200000);
	* 	    PlayerCharacteristics will be used to create the output file.
	* 
	 ************************************************************************************************ */

	/*- ************************************************************************************************
	* This Class reads  universal Hand files and analyzes the data. 
	* The operator selects the player characteristics wanted and the list of playerIDs that meet this criteria
	* are written to a file. 
	* The input is a single folder. The output is a single file.
	* The output is in c:\HHPLayerNames\inputFolder\fileName.ser
	* 		inputFolder is the same name as the input folder to this program.
	* 		fileName.ser is the list of playerIDs. Note: There may be more than one list for different criteria.
	* 
	* This Class creates the list of playerIDs. NOTE: There may be more than one list for folder.
	* We reads the all of the Universal Format Hand History files in the input folder and create 
	* several class instances in the Class ProcessUniversal.
	* The operator select the Folder and a list of players to use.
	* 
	* The files are in a universal format, not poker site dependent.
	* They were created by GUIConvertToUNiversal.
	* The list of players is created by GUIPlayerSelection to match some set user selected criterion.
	* 			Winner, Looser, Fish, Nit, Lag, Tag, Shark, etc..	
	*			By high low range, such as win  rate, VPIP, Preflop raise, Aggression Factor, 3 Bet, WTSD
	* Some characteristics are saved for report selection, such as position, relative position, etc..HHPlayerNames/
	* 
	* In this class we do very little error checking, that was done by GUIConvertToUNiversal.
	* There are many reports.
	* 
	* The output of this Class is a group of Classes. One instance of:
	*     Players, PlayerCharacteristics, PLayerClean, PlayerMDF, PlayerMoney, PlayerRanges,
	*     PlayerBetSize, and PlayerIndexes
	* The instance is a composite of the characteristics of each player in the players list.
	* We will almost certainly be adding more classes to the group as development progresses.  
	* 
	* The users of these groups of Classes are two applications:
	*         GUIDisplayData - A large number of data reporting classes.
	*         GUIAnalyzeData - Applications that performs advanced analysis
	* 
	* The purpose of this application is to analyze the data in a hand history file ( Universal format )
	* in ways that are entirely unique.
	* We do not use a relational database like many other applications such as Holdem Manager.
	* We do not intend to compete with them but in some instances do produce about the
	* same output in reports.
	* 
	* One key difference is that we look in detail at opportunities to fold, check, call, bet, raise, etc..
	* 
	* This class will create HashMaps:
	* 		private static final HashMap<String, Players> playersMap = new HashMap<>(200000);
	* 		private static final HashMap<String, PlayerClassification> playersClassificationMap = new HashMap<>(200000);
	* 
	* This class will create instances of:
	* 	One instance for each player.
	* 		private static final Players[] playerThisHand = new Players[PLAYERS];
	* 	One instance for each seat
	* 		private static final Players[] playerOneHand = new Players[PLAYERS]; 	
	* 	One instance for each position and relative position
	*		private static final Player [] playerPos = new Players[PLAYERS];
	*		private static final Player [] playerRp = new Players[NUM_RP]; 		
	*		private static final PlayerBetSize [] playerBetSizePos = new PlayerBetSize[PLAYERS];
	*		private static final PlayerBetSize [] playerBetSizeRp = new PlayerBetSize[MAX_RP]; 		
	*		private static final PlayerMoney [] playerPos = new Players[PLAYERS];
	*		private static final PlayerMoney [] playerRp = new Players[MAX_RP]; 		
	*		private static final PlayerMDF [] playerMDFPos = new PlayerMDF[PLAYERS];
	*		private static final PlayerMDF [] playerMDFRp = new PlayerMDF[MAX_RP]; 		
	*		private static final PlayerBoardIndexes [] playerInderxPos = new PlayerBoardIndexes[PLAYERS];
	*		private static final PlayerBoardIndexes [] playerBoardIndexesFRp = new PlayerBoardIndexes[MAX_RP]; 		
	*		private static final PlayerMDF [] playerMDFPos = new PlayerMDF[PLAYERS];
	*		private static final PlayerMDF [] playerMDFPos = new PlayerMDF[PLAYERS];
	*
	*	One composite instance for each group
	* 		private static final Player  playerAll = new Players();
	* 		private static final Players  playerOneHand = new Players(); 	
	*		private static final Player   playerPos = new Players();
	*		private static final Player   playerRp = new Players
	* 		private static final Players  playerThisHand = new Players();
	* 		private static final Players  playerOneHand = new Players[();
	*		private static final Player   playerPos = new Players[();
	*		private static final Player  playerRp = new Players[(); 		
	*		private static final PlayerBetSize  playerBetSizePos = new PlayerBetSize();
	*		private static final PlayerBetSize  playerBetSizeRp = new PlayerBetSize[();	
	*		private static final PlayerMoney   playerPos = new Players();
	*		private static final PlayerMoney   playerRp = new Players[();	
	*		private static final PlayerMDF   playerMDFPos = new PlayerMDF();
	*		private static final PlayerMDF   playerMDFRp = new PlayerMDF();
	*		private static final PlayerBoardIndexes   playerInderxPos = new PlayerBoardIndexes();
	*		private static final PlayerBoardIndexes [] playerBoardIndexesFRp = new PlayerBoardIndexes();	
	*		private static final PlayerClean   playerCleanPos = new PlayerMDF();	
	*		private static final PlayerClean playerCleanPos = new PlayerMDF[();	
	*
	*		private static final PlayerBetSize  playerBetSizePosAll = new PlayerBetSize[();	
	*		private static final PlayerBetSize   playerBetSizeRpAll = new PlayerBetSize();		
	*		private static final PlayerMoney  playerPosAll = new Players();	
	*		private static final PlayerMoney   playerRpAll = new Players();	
	*		private static final PlayerMDF   playerMDFPosAll = new PlayerMDF();	
	*		private static final PlayerMDF  playerMDFRpAll = new PlayerMDF();	
	*		private static final PlayerBoardIndexes   playerInderxPosAll = new PlayerBoardIndexes();	
	*		private static final PlayerBoardIndexes [  playerBoardIndexesFRpAll = new PlayerBoardIndexes[();	
	*		private static final PlayerClean   playerCleanPosAll = new PlayerClean[();	
	*		private static final PlayerClean [] playerCleanPos = new PlayerClean();	
	*
	*		Ine instance that is a composite for all
	* 		private static final Players[] playerAll = new Players[PLAYERS]; 	
	*
	 * There are several instances ofPlayerClassification  
	 * Instances ofPlayerClassification are written to disk as files for use by
	 * other application Classes.
	 *PlayerClassification contains the results of calculations using the raw data in instances of
	 * PLayers.
	 * An instance of Players may be created by summing together many instances of Players
	 * that are for one individual player selected by classification. ( FISH, NIT, ... )
	 * That instance represents all players in that classification.
	 * That composite instance is included in the PLayerData Class along with
	 * an instance of AllPlayers Class.
	 * 
	* Calculated data only - Written to a disk file for use at runtime
	*		private staticPlayerClassification allPlayerData; // All players all types
	*		private staticPlayerClassification selectedPlayerData;
	* PLayerData by player type - Written to a disk file for use at runtime
	* For testing purposes we have one player that we will use to compare or
	* results with using Holdem Manager 3
	*		private static final Players selectedPlayers = new Players();
	* 
	* This Class instance is an array of Hand Class elements.
	* This is the only input to this program.
	* It is poker site independent and hands from a site have been parsed and converted to a
	* summarized arrayOfHands[handIndex].
	* 
	* The array is read as a single file and contains up to HandArray.HANDS_IN_FILE
	* instances of Hand.
	* 
	* There may be 1 or many Handfiles.
	* Read one at a time because of storage limitations.
	* The input folder pathin may contain many megabytes of data.
	* To access a Hand instance in arrayOfHandsFile 
	* 		arrayOfHandsFile[index].BBSeat - Example is to get Big Blind seat number.
	* Hand contains instances of the Action Class that is a players action.
	* 		arrayOfHandsFile[index].actionArray[inx].street  - Example is to get street number.
	* 		arrayOfHandsFile[index].actionArray[inx].action  - Example is to get players action.
	 ************************************************************************************************ */

	/*- ************************************************************************************************
	* 
	* NEW IDEAS
	*  In the last year or so AI has become a very important part of hold'em strategy. 
	*  I will be looking into how to apply this. 
	*  For example, ChatGPT will discuss poker strategy. I was very surprised to see how well
	*  ChatGPT understands the hand, things like MDF, GTO, etc.. 
	*  
	*  Purpose
	* 
	*  This class examines Hand History files in order to determine how actual players play.
	*  The Hand History files that are the input to this program have have been created by
	*  the HoldemHandHistoryConverter application 
	*  
	*  Hand History files have been read and filtered to remove any Hand History that
	*  do not meet our criterion:
	*  		6 players at the table.
	*      No formatting errors.
	*      No duplicate hands.
	*      US Dollars only. ( Future updates may make optional )
	*      $1/$2 only. ( Future updates may make optional )
	*      No interruptions like player disconnected or leaves table. 
	*      
	*  The filtered files are then analyzed and equivalent universal format objects are
	*  created.   The Hand objects are not site dependent.  
	*  This applications only input is the Hand oblects.
	*  The current version uses a text string created from a Hand object.
	*  The built in Java serialization has very bad performance so the Hand History Converter 
	*  application writes text files which this application reads and converts back into Hand objects.
	*    
	*  This information is used to construct Ranges and other data that will be used in
	*  better simulation how an "average" player plays. 
	*  It is also used for player types such as NIT, LAG, TAG, REG, FISH and HERO.
	*  If we can simulate real players accurately we can also devise and test strategies 
	*  to exploit these players.
	*  
	*	Players contains data about individual player and so there are amny instances of
	*	Players, one for each player.
	* There are also Player instances that are for a collection of players.
	* 	PlayerData contains data that is about all players or all players of a type. 
	*	The key difference is thatPlayerClassification instances are save to a disk file
	*	but PlayerDate is not saved. Used then discarded.
	*  
	* There is one instance of the Players class for every player.
	* Players is just a class to hold player date in a HashMap.
	*  
	*  How this Class operates:
	*  		Operator input. 
	*  		The operator selects a folder where the input files are located.
	*  
	* 		Pass 1:
	* 			Hand History files in Universal Format are read and several data structures and 
	* 	       HashMaps are created. 
	* 
	* 		Pass 2:
	* 		In pass 2, data in this class is analyzed and players are classified by type. 
	* 			Hand History files are not used, only this class.	
	* 			The Players class is used to save the average information for all players.
	* 			Creates Players for the average of all players, no player types.
	*  			Many calculations are made  updating the 	information about each player.
	* 			There are several other things analyzed.
	* 			Data about each player is updated.
	* 
	* 		Pass3:
	* 		In pass 3, data in this class is analyzed again and Players is created for  
	* 			player types. ( NIT, LAG, TAG, ... )
	* 
	* 	    Pass4:
	* 	   ??
	* 
	* 		Display results and do additional analysis.
	* 	    There are many reports, over 80 in the current version with more planned.
	*       This is the area where the most new development is planned.
	*       The other passes were all leading up to this.
	* 			
	*  Several Classes are involved: 
	*  The list of classes are all new or significantly modified, all as part of a strategy to
	*  vastly improve Hold'em. Make it much more accurate in simulation and
	*  do a far better job of developing strategies that can actually be used at the table.
	*  This description is an outline of the developing new architecture.
	*  
	*  		Hand
	*  				A data holding class that is independent of the Hand History website format.
	*  				Hand History files have been parsed and many calculations performed.
	* 			Action
	* 					A class nested inside of Hand. One row for the actions of players preflop,
	* 					flop, turn, and river.
	* 	   		NameToID
	* 	                Player names were changed to IDs, an Integer value, by Hold'em Converter
	* 	                for performance reasons and to protect player privacy. 
	*                  This Class contains the original player name and the ID assigned to it.
	*                  It is a file created by Hold'em Converter.
	*  
	*  		CreateRanges - This class  constructs ranges, both preflop and postflop
	*  				PlayerDate is used to determine the percentages in HandRange files that are
	*  				in Hold'emDatabase.
	*   				These ranges represent the actions of a class of opponents such as AVERAGE, FISH,
	*   				NIT, LAG, TAG. .....
	*   
	*			OpponentAverage - This class contains data about the average opponent. 
	*  				It will be used to supply information to other Classes such as:
	*					tendancies ( percentages ) of opponent that we can take advantage of.  
	*
	*  		OpponentEvaluation - This class will calculate the performance level of each player at the table.
	* 					This data will be used in determining the best exploits for each player.
	* 
	* 			PlayABC - Ths Class is used to play ABC poker.
	* 					ABC Poker is simple rules that apply primarily when you have no information on 
	* 					your opponents.
	* 					It will help greatly in simulation of acual opponents but is not a strategy used by Hero.
	*  				This class works in conjunction with the PlayAnalysis class.
	* 					It is a helper class for the Play class.
	* 					Also refer to the PlayGTO Class for similar ideas. 
	* 
	* 			PlayAnalysis -  This class is a helper to the Play class. It will analyze the current hand
	* 					play, not a long term history of an aoopnent of our dear Hero.
	* 					Based on Game Theory principals, it looks to determine things such as whether
	* 					or not a player is the aggressor in this hand, has a Polarized Range, a
	* 					Condensed Range, etc.. This information is used to help determing the best
	* 					Game Theory alternatives.
	* 
	 * 		PlayerBehavior -   This class is the start of a new idea. Just a start.
	* 					What I plan to do is create a class and methods that describe how a player plays. 
	* 					Things like VPIP and agression factor.
	* 
	 * 		PlayerDataSaveRestore -  This class is used with the PlayerBehavior class and with the 
	 * 				PlayerExploits  class. 
	 * 				It will save the data associated with each class to a file and will
	* 					read the data back in at startup.
	* 
	* 			PlayerExploits -  This is the start of a new class that will allow the implementation 
	* 					of exploitation play.
	* 					From simple things such as reacting to a player that raises too much, calls to much,
	* 					raises too much.
	* 					The idea is simply to identify mistakes that a simulated player makes and to take full
	* 					advantage them.
	* 
	* 			PlayGTO -  This class is used to implement GTO based decisions.
	* 					This class works in conjunction with the PlayAnalysis class.
	* 					It is a helper class for the Play class.
	* 					Also refer to the PlayABC Class for similar ideas.
	* 
	* 			Calculate - A class with methods to do Holdem calculations such as Pot Odds
	* 
	*			TableImage - An interactive application that allows you to play against the simulator 
	*					with helpful prompts on correct play.
	*
	*			GTORules - Just a thought now
	*			PlayerData - This is a data container class created by the  Class.
	* 						It is Serializable it can write itself to a file and read itself back in.
	* 						A huge amount of data on the "average" player. 
	* 
	* 			AnalyzeHML -  I believe that the update of a Flop as some combination of HML is an
	* 						opportunuty to finf a simple way to evaluate betting opportunities.
	* 						What this class does is conduct an experiment to see what value HML has. We
	* 						will collect data and analyze to seewWhich of the 10 HML combinations has:
	* 						Draws - any draws EV - How often will we have the best hand with each of the
	* 						10 HML cominations.
	* 
	* 			EditGTORules -  This Class allows for the editing of GTO rules.
	* 						Just a thought at this point but a start.
	* 
	* 			 Evaluate -  This Class is evaluates hands, boards, hand strength, made hands, 
	* 						possible draws, Showdown and many other things.
	* 						It is currently being modified to better support both GTO, ABC play, and exploitation.
	* 						Also new ideas such as HML analysis.
	* 
	* 			Players -  This class is used by the  Class to classify player types. 
	* 						There is one  instance of this class for each player. 
	* 						It is strictly a data container and used only bythe  Class
	* 
	* 			FlopCombinations - This class is used to generate flops that represent all possible flops. 
	* 						It has a method to generate flops and a method to look find the index into the
	* 						flop array, Speed is important when doing a simulation.
	* 						It will be used to evaluate all possible Flops for EV.
	* 
	* 			Logger -  This class is called when there is an error and logs and analyzes a lot of data.
	* 						Invaluable in debugging.
	* 						It also is a great help in debug and testing.
	* 
	* 			BuildCombinedRange - This class will allow the user to display and edit combined ranges.
	* 						A work in progress.....
	* 
	* 			EditOptionsMDF -  This class will ????
	* 
	* 			ShowRangeSteps  -  This class will ???
	* 
	* 			Strategy -  This class is the strategy for one player.
	*  					There is one folder for each strategy.
	* 						The folder contains several individual text files organized by street 
	* 						and bet type.
	* 						This class will be modified for the new range type constructed by OpponentAverage.
	* 						The new combined ranges will improve performance.
	* 
	* 			HandSummary - Site independent Hand History
	*  					This class is a work in progress.
	* 						It's purpose is to define a site independent format, not the Hand History format.
	* 						It is created by parsing Hand History files. 
	* 						The  Class will create the data in this class.
	 *************************************************************************************************/

	/*- ************************************************************************************************
	 * Arrays of Classes 
	 * Two HashMap are used to save instances of each player
	 * 		playersMap							- Detailed data about each player
	 * 		playersClassificationMap 	- Only data used for player classification
	 * 		playerOneHand 	                - One hand player data
	 * Two arrays for the current hand only are used to keep a temporary local copy of
	 * one instance for each player of their entry in a HashMap.
	 * The instances are copied from a HashMap into the arrays.
	 * Both arrays are indexed by a players seat number.
	 * At the end of the hand the instances are returned to HashMap.
	 * 
	 * PlayerOneHand is a class that contains data for one hand player.
	 * Some analysis can not be done until the hand is complete, such as C-Bet.
	 * 
	 * AllPlayers is a class that contains data for all players combined.
	 * allPlayers is one instance of AllPlayers.
	 * It is used because it was impossible to have all data in the Players class and HashMap due
	 * to performance and storage limitations.
	*************************************************************************************************/

	/*- ************************************************************************************************
	 * Arrays of Classes 
	 * Two HashMap are used to save instances of each player
	 * 		playersMap							- Detailed data about each player
	 * 		playerCharacteristicsMap 	- Only data used for player classification
	 * 		playerOneHand 	                - One hand player data
	 * Two arrays for the current hand only are used to keep a temporary local copy of
	 * one instance for each player of their entry in a HashMap.
	 * The instances are copied from a HashMap into the arrays.
	 * Both arrays are indexed by a players seat number.
	 * At the end of the hand the instances are returned to HashMap.
	 * 
	 * PlayerOneHand is a class that contains data for one hand player.
	 * Some analysis can not be done until the hand is complete, such as C-Bet.
	 * 
	 * AllPlayers is a class that contains data for all players combined.
	 * allPlayers is one instance of AllPlayers.
	 * It is used because it was impossible to have all data in the Players class and HashMap due
	 * to performance and storage limitations.
	*************************************************************************************************/
	private static final ClassificationData rules = new ClassificationData();
	private static Hand[] arrayOfHands = new Hand[100000000]; // When one input file is read

	private static HandHistoryObjects handHistoryObjects = new HandHistoryObjects();

	private static final HashMap<Integer, PlayerCharacteristics> playerCharacteristicsMap = new HashMap<>(50000);
	private static PlayerCharacteristics onePlayerCharacteristics;

	private static HashSet<Integer> selectedPlayerIDSet = new HashSet(100000);

	// Input and output directories
	private static String pathIn = "C:\\HHUniversal\\";
	private static String pathOut = "C:\\HHSelected\\";
	private static String selectedPathIn = "";
	private static String selectedPathOut = "";
	private static String fileName = "";
	private static String fullPath = "";

	private static int handIndex = 0;

	static Timer timerAll = new Timer();
	static Timer timerBeforeAction = new Timer();
	static Timer timerPreflop = new Timer();
	static Timer timerFlop = new Timer();
	static Timer timerTurn = new Timer();
	static Timer timerRiver = new Timer();
	static Timer timerShowdown = new Timer();
	static Timer timerSummary = new Timer();

	private static final Timer timerReadFile = new Timer();
	private static final Timer timerListOfFiles = new Timer();
	private static final Timer timerPass1 = new Timer();
	private static final Timer timerDelete = new Timer();
	private static final Timer timerPass2 = new Timer();
	private static final Timer timerFindPlayers = new Timer(); // get player names
	private static final Timer timerRead = new Timer(); // read hh files
	private static final Timer timerCategorize = new Timer();
	private static final Timer timerCreatePlayerCharacteristics = new Timer();

	private static final int MAX_FILES = 100000;
	// Array of all file names in input folder
	private static final String[] filesInFolder = new String[MAX_FILES];
	private static String fileInFolder = ""; // Current file

	private static int filesInFolderCount = 0;
	private static int fileCount = 0;
	private static int fileCountRej = 0;

	/*- ************************************************************************************************
	 * These are very large arrays.
	 * 		hands is all of the hands played in this Hand History file. One long string for each and.
	 * 		lines is one oneHand. Typically about 40 lines in one hand 
	  *************************************************************************************************/
	private static int passNumber = -1; // Pass number 1-4

	// Pass 1 counts
	private static int filesReadPass1 = 0;
	private static int handsPass1 = 0;
	private static final int handsPass2 = 0;
	private static int handsThisFile = 0;
	private static int playersPass1 = 0;

	private static final int callPreflopCount = 0;
	private static int bet2PreflopCount = 0;

	private static final int pb = 0;

	private static int players = 0;

	private static boolean exit = false;

	/*-************************************************************************************************
	 * Panels
	************************************************************************************************ */
	private static final JPanel panel0 = new JPanel();
	private static final JPanel panel12 = new JPanel();
	private static final JPanel panelBlank = new JPanel();
	private static final JButton buttonAnalyze = new JButton("Analyze");
	private static final JButton buttonSelect = new JButton("Select");
	private static final JButton buttonAnalyzeSelected = new JButton("Analyze Selected");
	private static final JButton buttonShowSelected = new JButton("Show Selected");
	private static final JButton buttonWriteSelected = new JButton("Write Selected");
	private static final JButton buttonFolder = new JButton("Select Folders");
	private static final JButton buttonType = new JButton("Edit Type Data");
	private static final JButton buttonCharacteristic = new JButton("Edit Characteristic Data");
	private static final JButton buttonHelp = new JButton("Help");
	private static final JButton buttonExit = new JButton("Exit");
	private static final JTextField statusTextPass1 = new JTextField(
			"                                                                            ");
	private static final JTextField statusTextPass2 = new JTextField(
			"                                                                            ");
	private static final JTextField statusTextFinal = new JTextField(
			"                                                                            ");
	/*-************************************************************************************************
	 * Panels
	************************************************************************************************ */
	private static final JTextField handsText = new JTextField(6);
	private static final JPanel panelControl = new JPanel();
	private static final JPanel panelControl2 = new JPanel();

	private static final JTextField statusDirectories = new JTextField(
			"                                                                            ");

	private static final JLabel blank = new JLabel("   ");

	private static final JCheckBox crash = new JCheckBox("Crash on severe error");
	private static final JPanel panelCheck = new JPanel();

	private static int skip = 0;

	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {

		}
	}

	private static SelectionValues sv = new SelectionValues();

	/*-************************************************************************************************
	 * Main - . 
	 * Display startup window.
	 * Wait for operator to select folder.
	 * Step through each pass.
	 * Select reports.
	 *************************************************************************************************/
	public static void main(String[] s0) {
		sv.show();
		if (!ValidInstallation.checkValid()) {
			return;
		}
		exit = false;
		startupWindow();
		if (exit) {
			return;
		}
	}

	/*-************************************************************************************************
	 * Display main window and options to select folder ansd to run program
	 *************************************************************************************************/
	private static void startupWindow() {
		final var frame = new JFrame("Hold'em  Hand History Analysis");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(50, 50);
		frame.setPreferredSize(new Dimension(1400, 900));
		panel0.setLayout(new GridLayout(15, 1));
		panel0.setSize(1300, 600);

		final var f = new Font(Font.SERIF, Font.BOLD, 18);

		final var options1 = Box.createHorizontalBox();
		options1.setFont(f);

		Box.createHorizontalBox();
		statusDirectories.setBorder(BorderFactory.createTitledBorder("Input Folder      "));
		statusDirectories.setBackground(Color.WHITE);
		statusDirectories.setFont(f);
		panel0.add(statusDirectories);
		panel0.add(panelBlank);
		panel0.add(panelBlank);
		panelBlank.setFont(f);

		final var control = Box.createHorizontalBox();
		panelControl.setLayout(new GridLayout(1, 8));
		panelControl.setBounds(10, 770, 1000, 40);
		control.setFont(f);
		buttonFolder.setToolTipText("Input Folder for Hand History files");
		buttonFolder.setBackground(Color.GREEN);
		buttonAnalyze.setToolTipText("Start Analysis");
		buttonAnalyze.setBackground(Color.GREEN);
		buttonSelect.setToolTipText("Select Characteristics");
		buttonSelect.setBackground(Color.GREEN);
		buttonAnalyzeSelected.setToolTipText("Analyze Selected");
		buttonAnalyzeSelected.setBackground(Color.GREEN);
		buttonWriteSelected.setToolTipText("Write Final Results");
		buttonWriteSelected.setBackground(Color.GREEN);
		buttonHelp.setBackground(Color.GREEN);
		control.add(buttonFolder);
		control.add(buttonAnalyze);
		control.add(buttonSelect);
		control.add(buttonAnalyzeSelected);
		control.add(buttonWriteSelected);
		panelControl.add(control);
		panel0.add(panelControl);

		final var control2 = Box.createHorizontalBox();
		panelControl2.setLayout(new GridLayout(1, 5));
		panelControl2.setBounds(10, 770, 1000, 40);
		control2.setFont(f);
		buttonFolder.setToolTipText("Input Folder for Hand History files");
		buttonCharacteristic.setToolTipText("Edit Characteristic Data");
		buttonCharacteristic.setBackground(Color.GREEN);
		buttonType.setToolTipText("Edit Type Data");
		buttonType.setBackground(Color.GREEN);
		buttonHelp.setToolTipText("Help");
		buttonHelp.setBackground(Color.GREEN);
		buttonExit.setBackground(Color.GREEN);
		buttonExit.setToolTipText("Exit");
		control2.add(buttonType);
		control2.add(buttonCharacteristic);
		control2.add(buttonHelp);
		control2.add(buttonExit);
		panelControl2.add(control2);
		panel0.add(panelControl2);

		buttonType.addActionListener(new Listener());
		buttonFolder.addActionListener(new Listener());
		buttonAnalyze.addActionListener(new Listener());
		buttonSelect.addActionListener(new Listener());
		buttonAnalyzeSelected.addActionListener(new Listener());
		buttonShowSelected.addActionListener(new Listener());
		buttonWriteSelected.addActionListener(new Listener());
		buttonCharacteristic.addActionListener(new Listener());
		buttonHelp.addActionListener(new Listener());
		buttonExit.addActionListener(new Listener());
		buttonAnalyze.setEnabled(false);
		buttonSelect.setEnabled(false);
		buttonAnalyzeSelected.setEnabled(false);
		buttonWriteSelected.setEnabled(false);

		panel12.setSize(300, 40);
		panel12.setBackground(Color.white);
		panel12.setLayout(new BoxLayout(panel12, BoxLayout.X_AXIS));
		final var dim3 = new Dimension(300, 30);
		panel12.setMaximumSize(dim3);
		panel12.setMinimumSize(dim3);

		Box.createHorizontalBox();
		statusTextPass1.setBorder(BorderFactory.createTitledBorder("Pass 1      "));
		statusTextPass1.setBackground(Color.WHITE);
		statusTextPass1.setFont(f);
		panel0.add(statusTextPass1);

		Box.createHorizontalBox();
		statusTextPass2.setBorder(BorderFactory.createTitledBorder("Pass 2      "));
		statusTextPass2.setBackground(Color.WHITE);
		statusTextPass2.setFont(f);
		panel0.add(statusTextPass2);

		Box.createHorizontalBox();
		statusTextFinal.setBorder(BorderFactory.createTitledBorder("Selection file written     "));
		statusTextFinal.setBackground(Color.WHITE);
		statusTextFinal.setFont(f);
		panel0.add(statusTextFinal);

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

		panel0.add(panel12);

		frame.add(panel0);
		frame.pack();
		frame.setVisible(true);
	}

	/*- **************************************************************************************************
	 * Start analysis. 
	 * Get all hands in folder and concert them to Hand instances.
	 * Hand instances are parsed and analyzed.
	 * Data is saved in several Class Instances and as a Player class for each player.
	*****************************************************************************************************/
	private static boolean startAnalysis() {
		handsPass1 = 0;
		statusTextPass1.setBackground(WHITE);
		boolean done = false;
		skip = 5001;
		while (!done) {
			Hand h = HandReader.getNextHand();
			if (h == null) {
				done = true;
			} else {
				++handIndex;
				++handsPass1;
				if (skip++ >= 5000)
					updateStatus();
				if (handHistoryObjects.doOneHand(h)) {

				}
				cleanup();
			}
		}
		finishAnalysis();
		updateStatus();
		statusTextPass1.setBackground(GREEN);
		buttonSelect.setEnabled(true);
		return true;
	}

	/*-************************************************************************************************
	 *Update status
	 *************************************************************************************************/
	private static void updateStatus() {
		statusTextPass1.setText(HandReader.getReadStatus());
		statusTextPass1.update(statusTextPass1.getGraphics());
		skip = 0;
	}

	/*-************************************************************************************************
	 * Update printed messages
	 *************************************************************************************************/
	private static boolean finishAnalysis() {
		var time = timerPass1.getTimer("");

		final long MegaBytes = (long) 1024 * (long) 1024;
		final var runtime = Runtime.getRuntime();
		final long totalMemory = runtime.totalMemory() / MegaBytes;
		final long maxMemory = runtime.maxMemory() / MegaBytes;
		final long freeMemory = runtime.freeMemory() / MegaBytes;
		final long memoryInUse = (totalMemory - freeMemory) / MegaBytes;

		Logger.log(new StringBuilder().append("//Pass0     Memory ").append("\t Memory in use ").append(memoryInUse)
				.append(" \tTotalMemory ").append(totalMemory).append(" \tmaxMemory ").append(maxMemory)
				.append(" \tfreeMemory ").append(freeMemory).toString());

		displayTimers();

		time = timerPass1.getTimer("");
		Logger.log("GUIPlayerCharacteristics timer " + time);
		return true;
	}

	/*-********************************************************************************************** 
	 * After processing each hand we check all of the players in the hand to see if they are 
	 * players that we will be keeping.
	 * If the playerID is less than zero then it is a reject and was kept in the hand only so that the 
	 * evaluation of the hand is accurate.
	 * At the end of the hand all rejected players Player instances are deleted from the HashMap.
	 * It may be created and added again, a small performance hit, but our more important limitation
	 * is memory. We can always take longer but can not expand beyond 64 GB.
	 * We choose to take the performance hit.
	 * We delete the current hand and then check the playerIDs in the next hand to see if we have
	 * any of the same players and only delete from the HashMap in not, reduces the performance hit
	************************************************************************************************ */
	private static void cleanup() {
		int id = -1;
		if (handIndex < handsThisFile && arrayOfHands[handIndex + 1] != null) {
			for (int i = 0; i < PLAYERS; ++i) {
				id = arrayOfHands[handIndex + 1].IDArray[i];
				if (id < 0 && handHistoryObjects.playerIDs[i] != id) {
					handHistoryObjects.playersMap.remove(id);
				}
			}
		} else if (handIndex + 1 == handsThisFile) {
			for (int i = 0; i < PLAYERS; ++i) {
				if (handHistoryObjects.playerIDs[i] < 0) {
					handHistoryObjects.playersMap.remove(id);
				}
			}
		}
		arrayOfHands[handIndex] = null;
	}

	/*-********************************************************************************************** 
	 * Data for selection
	************************************************************************************************ */
	private static void dataForSelection() {
		Players tempPlayer = null;
		for (Iterator iterator = handHistoryObjects.playersMap.entrySet().iterator(); iterator.hasNext();) {
			final var mentry = (Map.Entry) iterator.next();
			tempPlayer = (Players) mentry.getValue();
			onePlayerCharacteristics = new PlayerCharacteristics();
			onePlayerCharacteristics.doCalculations(tempPlayer);
			onePlayerCharacteristics.doPlayerClassification(tempPlayer);
			for (int i = 0; i < 6; i++) {
				if (tempPlayer.handsPlayed > PlayerSelectionReport.played[i] && tempPlayer.playerID > 0) {
					double p = tempPlayer.handsPlayed;
					PlayerSelectionReport.played[i] = tempPlayer.handsPlayed;
					PlayerSelectionReport.playedID[i] = tempPlayer.playerID;
					PlayerSelectionReport.wonShowdownPer[i] = (tempPlayer.wonShowdownCount / p) * 100.;
					PlayerSelectionReport.wonPer[i] = (tempPlayer.wonCount / p) * 100.;
					PlayerSelectionReport.wtsdPreflopPer[i] = (tempPlayer.wtsdPreflopCount / p) * 100.;
					PlayerSelectionReport.wtsdFlopPer[i] = (tempPlayer.wtsdFlopCount / p) * 100.;
					PlayerSelectionReport.wtsdTurnPer[i] = (tempPlayer.wtsdTurnCount / p) * 100.;
					PlayerSelectionReport.wtsdRiverPer[i] = (tempPlayer.wtsdRiverCount / p) * 100.;
					PlayerSelectionReport.wwspPer[i] = (tempPlayer.wwspCount / p) * 100.;
					PlayerSelectionReport.wwsfPer[i] = (tempPlayer.wwsfCount / p) * 100.;
					PlayerSelectionReport.wwstPer[i] = (tempPlayer.wwstCount / p) * 100.;
					PlayerSelectionReport.wwsrPer[i] = (tempPlayer.wwsrCount / p) * 100.;
					PlayerSelectionReport.showDownPer[i] = (tempPlayer.showdownCount / p) * 100.;
					PlayerSelectionReport.showdownWonPer[i] = (tempPlayer.showdownWonCount / p) * 100.;
					PlayerSelectionReport.raise1Per[i] = (tempPlayer.raiseCount1 / p) * 100.;
					PlayerSelectionReport.bet1Per[i] = (tempPlayer.betCount1 / p) * 100.;

					PlayerSelectionReport.preflopWinRate[i] = onePlayerCharacteristics.preflopWinRate;
					PlayerSelectionReport.vpip[i] = onePlayerCharacteristics.vpip;
					PlayerSelectionReport.preflopVPIP[i] = onePlayerCharacteristics.preflopVPIP;
					PlayerSelectionReport.aggPct[i] = onePlayerCharacteristics.aggPct;
					PlayerSelectionReport.af[i] = onePlayerCharacteristics.af;
					PlayerSelectionReport.wtsdIfSawFlop[i] = onePlayerCharacteristics.wtsdIfSawFlop;
					PlayerSelectionReport.wtsdIfSawTurn[i] = onePlayerCharacteristics.wtsdIfSawTurn;
					PlayerSelectionReport.wtsdIfSawRiver[i] = onePlayerCharacteristics.wtsdIfSawRiver;
					PlayerSelectionReport.winRateBB100[i] = onePlayerCharacteristics.winRateBB100;
					PlayerSelectionReport.preflopBet3Freq[i] = onePlayerCharacteristics.preflopBet3Freq;
					PlayerSelectionReport.showdownPercentage[i] = onePlayerCharacteristics.showdownPercentage;
					break;
				}
			}
		}
	}

	/*-********************************************************************************************** 
	 * Select players
	************************************************************************************************ */
	private static void selectPlayers() {
		final int playersStart = handHistoryObjects.playersMap.size();
		timerPass2.resetTimer();
		timerPass2.startTimer();
		timerCategorize.startTimer();
		dataForSelection();
		PlayerSelectionReport.values();
		Players aPlayer = handHistoryObjects.playersMap.get(PlayerSelectionReport.playedID[0]);
		Players bPlayer = handHistoryObjects.playersMap.get(PlayerSelectionReport.playedID[1]);
		Players cPlayer = handHistoryObjects.playersMap.get(PlayerSelectionReport.playedID[2]);
		if (aPlayer != null) {
			PlayersReport.values(aPlayer);
		}
//		PlayersReport.values(bPlayer);
//		PlayersReport.values(cPlayer);
//		PlayersReport.valuesBU(aPlayer);
//		PlayersReport.valuesSB(aPlayer);
//		PlayersReport.valuesBB(aPlayer);
//		PlayersReport.allActionPos(aPlayer);
//		PlayersReport.allActionRp(aPlayer);
//		PlayersReport.allOperPos(aPlayer);
//		PlayersReport.allOperRp(aPlayer);
//		PlayersReport.streets(aPlayer);
//		PlayersReport.newArraysPos(aPlayer);
//		PlayersReport.newArraysRp(aPlayer);

//		PlayerCharacteristicsReport.allPercentagePos(onePlayerCharacteristics);
//		PlayerCharacteristicsReport.allPercentageRp(onePlayerCharacteristics);
//		PlayerCharacteristicsReport.values(onePlayerCharacteristics);
//		PlayerCharacteristicsReport.playerStats(onePlayerCharacteristics);
//		PlayerCharacteristicsReport.playerBasicStats(onePlayerCharacteristics);
//		PlayerCharacteristicsReport.playerStatsFlop(onePlayerCharacteristics);
//		PlayerCharacteristicsReport.playerStatsTurn(onePlayerCharacteristics);
		// PlayerCharacteristicsReport.playerStatsRiver(onePlayerCharacteristics);
//		PlayerCharacteristicsReport.playerStatsShowdown(onePlayerCharacteristics);
		displayAnalysisResults();

		updatePass2Display();

		final long MegaBytes = (long) 1024 * (long) 1024;
		final var runtime = Runtime.getRuntime();
		final long totalMemory = runtime.totalMemory() / MegaBytes;
		final long maxMemory = runtime.maxMemory() / MegaBytes;
		final long freeMemory = runtime.freeMemory() / MegaBytes;
		final long memoryInUse = (totalMemory - freeMemory) / MegaBytes;

		Logger.log(new StringBuilder().append("//Pass 2      Memory ").append("\t Memory in use ").append(memoryInUse)
				.append(" \tTotalMemory ").append(totalMemory).append(" \tmaxMemory ").append(maxMemory)
				.append(" \tfreeMemory ").append(freeMemory).toString());
		System.out.println("//pass 2 complete ");
		final var time = timerPass2.getElapsedTimeString();
		Logger.log("GUIPlayerCharacteristics timer pass 2 " + time);
		statusTextPass2.setBackground(GREEN);
		buttonAnalyzeSelected.setEnabled(true);
	}

	/*-************************************************************************************************
	 * pass1 and pass 2 read and analyzed the files in the selected input folder.
	 * Here we display the results of the analysis.
	************************************************************************************************ */
	private static void displayAnalysisResults() {
		buttonAnalyzeSelected.setEnabled(true);
	}

	/*-************************************************************************************************
	 * pass1 and pass 2 read and analyzed the files in the selected input folder and the results of the 
	 * analysis were displayed. 
	 * Here we allow the operator to select the desired characteristics.  
	************************************************************************************************ */
	private static void select() {
		selectPlayers();
		buttonAnalyzeSelected.setEnabled(true);
	}

	/*-************************************************************************************************
	 * The results of the analysis were displayed and the operator selected the characteristics 
	 * for players to be 
	 * Now we scan through all players and keep the ones that match the operators selection
	 * choices. Then we write a list of players.
	************************************************************************************************ */
	private static boolean analyzeSelected() {
		Players tempPlayer = null;
		int siz = handHistoryObjects.playersMap.size();
		int i = 0;
		PlayerIDs p = new PlayerIDs(siz);
		for (Iterator iterator = handHistoryObjects.playersMap.entrySet().iterator(); iterator.hasNext();) {
			final var mentry = (Map.Entry) iterator.next();
			tempPlayer = (Players) mentry.getValue();
			p.playerIDs[i++] = tempPlayer.playerID;
			selectedPlayerIDSet.add(tempPlayer.playerID);
		}
		saveSelection(p);
		analyzeAgain();
		return true;
	}

	/*-************************************************************************************************
	 * Read selected
	************************************************************************************************ */
	private static boolean analyzeAgain() {
		arrayOfHands = new Hand[100000000];
		handHistoryObjects = new HandHistoryObjects();
		playerCharacteristicsMap.clear();

		statusDirectories.setText(new StringBuilder().append("Input Folder    ").append(selectedPathIn)
				.append("         Output folder    ").append(selectedPathOut).toString());
		statusDirectories.update(statusDirectories.getGraphics());
		int result = HandReader.folderPath(selectedPathIn);
		statusDirectories.setText(HandReader.getPathStatus());
		statusDirectories.update(statusDirectories.getGraphics());

		handsPass1 = 0;
		statusTextPass1.setBackground(WHITE);
		boolean done = false;
		skip = 5001;
		while (!done) {
			Hand h = HandReader.getNextHand();
			if (h == null) {
				done = true;
			} else {
				++handIndex;
				++handsPass1;
				if (skip++ >= 5000)
					updateStatus();
				// See if this hand contains selected players.
				int count = 0;
				for (int i = 0; i < PLAYERS; ++i) {
					if (selectedPlayerIDSet.contains(h.IDArray[i])) {
						++count;
					}
				}
				if (count != 0) {
					if (handHistoryObjects.doOneHand(h)) {

					}
					cleanup();
				}
			}
		}
		updateStatus();
		statusTextPass1.setBackground(GREEN);
		buttonWriteSelected.setEnabled(true);

		final int playersStart = handHistoryObjects.playersMap.size();
		timerPass2.resetTimer();
		timerPass2.startTimer();
		timerCategorize.startTimer();
		dataForSelection();
		PlayerSelectionReport.values();
		Players aPlayer = handHistoryObjects.playersMap.get(PlayerSelectionReport.playedID[0]);
		Players bPlayer = handHistoryObjects.playersMap.get(PlayerSelectionReport.playedID[1]);
		Players cPlayer = handHistoryObjects.playersMap.get(PlayerSelectionReport.playedID[2]);
		if (aPlayer != null) {
			PlayersReport.values(aPlayer);
		}
		return true;
	}

	/*-******************************************************************************
	 * Save player selection file
	 * Iterate through the PlayerCharacteristics HashMap to get the playerID
	 * of all selected players and add to the PlayersSelection class.
	 * Then create a description for the file.
	 *******************************************************************************/
	static void saveSelection(PlayerIDs p) {
		final var dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		final var st = dateFormat.format(new Date());
		final var st2 = new StringBuilder().append(st.substring(5, 7)).append(st.substring(8, 10))
				.append(st.substring(11, 13)).append(st.substring(14, 16)).toString();
		fileName = new StringBuilder().append("Characteristic").append(st2).append(".ser").toString();
		fullPath = selectedPathOut + fileName;
		statusTextFinal.setText(new StringBuilder().append("File saved to: ").append(fullPath).toString());

		File file = new File(String.valueOf(fullPath));
		File directory = new File(String.valueOf(selectedPathOut));
		if (!directory.exists()) {
			directory.mkdir();
			if (!file.exists()) {
				file.getParentFile().mkdir();
				try {
					file.createNewFile();
				} catch (Exception e) {
					Crash.log("xxx " + "");
				}
			}
		}
		// pass 2 complete
		// GUIPlayerCharacteristics timer pass 2 00:00:00
		// Player IDs C:\HHSelected\HHNew10000\AverageCharacteristic02252102.ser
		System.out.println("//Player IDs " + fullPath + " " + selectedPathOut);
		p.writeToFile(fullPath);

		System.out.println(new StringBuilder().append("//").append(fullPath).append("").toString());
	}

	/*-************************************************************************************************
	 * Update pass 2 display
	 *************************************************************************************************/
	private static void updatePass2Display() {
		if (filesInFolderCount == 1) {
			final var time = timerPass2.getTimer("");
			statusTextPass2.setText(
					new StringBuilder().append("Hands  ").append(handsPass2).append("").append(time).toString());
			statusTextPass2.update(statusTextPass2.getGraphics());
			handsText.setText(" Players " + Format.format(players));
			handsText.update(handsText.getGraphics());
		} else {
			final var time = timerPass2.getTimer("");
			statusTextPass2.setText(
					new StringBuilder().append("Hands  ").append(handsPass2).append("").append(time).toString());
			statusTextPass2.update(statusTextPass2.getGraphics());
			handsText.setText("Players  " + Format.format(players));
			handsText.update(handsText.getGraphics());
		}
	}

	/*-************************************************************************************************
	 * Display results of timers
	 *************************************************************************************************/
	private static void displayTimers() {
		Logger.log("");
		Logger.log("//List Of Files          \t" + Format.format(timerListOfFiles.getTimer("")));
		Logger.log("//Read  file              \t" + Format.format(timerReadFile.getTimer("")));
		Logger.log("//Pass 1                   \t" + Format.format(timerPass1.getTimer("")));
		Logger.log("//Delete                  \t" + Format.format(timerDelete.getTimer("")));
		Logger.log("//Pass 2                   \t" + Format.format(timerPass2.getTimer("")));
		Logger.log("//Read               \t" + Format.format(timerRead.getTimer("")));
		Logger.log("//Find Players         \t" + Format.format(timerFindPlayers.getTimer("")));
		Logger.log("");
		Logger.log("//all                   \t" + Format.format(timerAll.getTimer("")));
		Logger.log("//BeforeAction \t" + Format.format(timerBeforeAction.getTimer("")));
		Logger.log("//Preflop           \t" + Format.format(timerPreflop.getTimer("")));
		Logger.log("//Flop                \t" + Format.format(timerFlop.getTimer("")));
		Logger.log("//Turn                \t" + Format.format(timerTurn.getTimer("")));
		Logger.log("//River               \t" + Format.format(timerRiver.getTimer("")));
		Logger.log("//Show Down     \t" + Format.format(timerShowdown.getTimer("")));
		Logger.log("//Summary       \t" + Format.format(timerSummary.getTimer("")));
	}

	/*-***********************************************************************************************
	 * Get names of all files in a folder and subdirectories. Recvursive. 
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

	/*-******************************************************************************
	 * Choose input folder
	 *******************************************************************************/
	static String chooseInputDirectory() {
		final var j = new JFileChooser(pathIn);
		j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (j.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			return "";
		}
		final var $ = j.getSelectedFile().getAbsolutePath();
		return $ + "\\";
	}

	/*-************************************************************************************************
	 *Convert player ID to String
	 *Example : id = 22, returns 000022
	 *Arg0 - id
	 *Returns String.
	************************************************************************************************ */
	private static String convertIDToString(int id) {
		final var pat = "000000";
		final var df = new DecimalFormat(pat);
		return df.format(id);
	}

	/*- ***********************************************************************************************-
	 *  Responds to button clicks. 
	***********************************************************************************************  */
	static class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a0) {
			switch (a0.getActionCommand()) {
			case "Select Folders" -> {
				selectedPathIn = chooseInputDirectory();
				if (selectedPathIn.equals(pathIn)) {
					JOptionPane.showMessageDialog(null, "You can not choose the root Folder as your input Folder .");
					selectedPathIn = "";
				}
				String st1 = selectedPathIn.substring(4);
				int n = st1.indexOf("\\");
				selectedPathOut = pathOut + st1.substring(n + 1);
				statusDirectories.setText(new StringBuilder().append("Input Folder    ").append(selectedPathIn)
						.append("         Output folder    ").append(selectedPathOut).toString());
				statusDirectories.update(statusDirectories.getGraphics());
				int result = HandReader.folderPath(selectedPathIn);
				statusDirectories.setText(HandReader.getPathStatus());
				statusDirectories.update(statusDirectories.getGraphics());
				if (result == 0) {
					buttonAnalyze.setEnabled(true);
					buttonCharacteristic.setEnabled(true);
					statusDirectories.setBackground(GREEN);
				}
			}
			case "Edit Type Data" -> rules.editData(100, 500);
			case "Edit Characteristic Data" -> rules.editCharacteristic(100, 500);
			case "Analyze" -> startAnalysis();
			case "Select" -> select();
			case "Analyze Selected" -> analyzeSelected();
			case "Write Selected" -> writeSelected();
			case "Help" -> JOptionPane.showMessageDialog(null, helpString);
			case "Exit" -> exit = true;
			}
		}

		/*-************************************************************************************************
		 * 
		************************************************************************************************ */
		private static boolean writeSelected() {

			return true;
		}
	}

	// @formatter:off
	
	/*-************************************************************************************************
	 * Types
	 *************************************************************************************************/
	private static final Object[] columnsTypes = {"Summary","Counts" };

	private static final Object[][] dataTypes = { 
			{"Hands",""},
			{"Nit",""}, 
			{"Lag",""},
			{"Tag",""}, 
			{"Shark",""},
			{"Fish",""}, 
			{"Average",""},
			{"Winner",""}, 
			{"Looser",""},
			{"",""}, 
			{"playerBetType",""},
			{"playerRaise$",""}, 
			{"playerRaiseFrom$",""},
			{"playerRaiseTo$",""}, 
			{"playerBet$",""},
			{"playerCall$",""}, 
			{"playerCollect$",""},
			{"playerReturnTo$",""}, 
			{"playerStack$",""},
			{"playerWon$",""}, 
			{"playerRake$",""},
			{"playerCollected$",""},
			{"playerShowsHand",""},
			{"playerShowsHandSummary",""}, 
			{"",""},
			{"",""}, 
			{"",""},
			{"",""} };

	private static boolean  typesGUI = false;
	/*-***********************************************************************************************
	 * Report for test and debug
	*********************************************************************************************** */
	private static void GUItypes(int id) {
		final var player = playerCharacteristicsMap.get(id);
		if (!typesGUI) {
			typesGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 12);
			final var frame = new JFrame("Hand History Summary Report");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(10, 10);
			frame.setPreferredSize(new Dimension(500, 1800));

			final var table = new JTable(dataTypes, columnsTypes);
			table.setFont(ff);
 			var pane = new JScrollPane(table);
		//	table.getColumnModel().getColumn(0).setPreferredWidth(150);
			// Create a custom cell renderer to set the font to bold 18.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 18));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			}
		int row = 0;
		dataTypes[row ][1] = Format.format(player.playerID);
			}
	
	
	// @formatter:off  
		private static final String helpString = new StringBuilder().append("Welcome to the Holdem Hand History project.\r\n")
				.append("This GUI reads Universal Hand History files in an input folder and steps through them, calculating\r\n")
				.append("data used to classify players by type and then classifying them.\r\n")
				.append("Players are selected from all of the players that have certain characteristics selected by the operator. \r\n")
				.append("A list of the players selected is written to a file that will be used by GUIAnalysis.\r\n")
				.append("By selecting the players to be analyzed in advance the memory requirements of the analysis application(s)\r\n")
				.append("is greatly reduced and results in much improved performance. This is necessary because there may be millions\r\n")
				.append("of players and millions of hands.\r\n")
				.append("The opertor selects the type of players to be selected. By simply specifying a p[layer type such an Lag, Tag, ....\r\n")
				.append("Or by selecting a combination of characterisrics suchb as win rate, C-Bet percentage, and much more.\r\n")
				.append("In addition, the operator can edit the values used to do the selection\r\n")
				.append("\r\n")
					.append("Try it. Send me your suggestions and comments. Feel free to use any of the source code.\r\n")
				.append("\r\n").toString();
		
		// @formatter:on

}
