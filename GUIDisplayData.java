package holdemhandhistory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

public class GUIDisplayData implements Constants {

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

	/*- ************************************************************************************************
	* This Class does not directly use Hand History data. 
	* Instead, it uses several instances of Classes that were created using Hand History data, analyzing it and 
	* creating a small number of class instances.
	* 
	* The output of this Class is a group of Classes. One instance of:
	*     Players, PlayerCharacteristics, PLayerClean, PlayerMDF, PlayerMoney, PlayerRanges,
	*     PlayerBetSize, and PlayerIndexes
	* The instance is a composite of the characteristics of each player in the players list.
	* We will almost certainly be adding more classes to the group as development progresses.  
	*  
	************************************************************************************************ */

	/*- ******************************************************************************************************************
	 * Arrays of Player.... by position and relative position
	 ****************************************************************************************************************** */
	private static Players[] playerPos = { new Players(), new Players(), new Players(), new Players(), new Players(),
			new Players() };
	private static Players[] playerRp = { new Players(), new Players(), new Players(), new Players(), new Players(),
			new Players(), new Players(), new Players(), new Players() };

	private static PlayerBetSize[] playerBetSizePos = { new PlayerBetSize(), new PlayerBetSize(), new PlayerBetSize(),
			new PlayerBetSize(), new PlayerBetSize(), new PlayerBetSize() };
	private static PlayerBetSize[] playerBetSizeRp = { new PlayerBetSize(), new PlayerBetSize(), new PlayerBetSize(),
			new PlayerBetSize(), new PlayerBetSize(), new PlayerBetSize(), new PlayerBetSize(), new PlayerBetSize(),
			new PlayerBetSize(), };

	private static PlayerMDF[] playerMDFPos = { new PlayerMDF(), new PlayerMDF(), new PlayerMDF(), new PlayerMDF(),
			new PlayerMDF(), new PlayerMDF() };
	private static PlayerMDF[] playerMDFRp = { new PlayerMDF(), new PlayerMDF(), new PlayerMDF(), new PlayerMDF(),
			new PlayerMDF(), new PlayerMDF(), new PlayerMDF(), new PlayerMDF(), new PlayerMDF(), new PlayerMDF() };

	private static PlayerMoney[] playerMoneyPos = { new PlayerMoney(), new PlayerMoney(), new PlayerMoney(),
			new PlayerMoney(), new PlayerMoney(), new PlayerMoney() };
	private static PlayerMoney[] playerMoneyRp = { new PlayerMoney(), new PlayerMoney(), new PlayerMoney(),
			new PlayerMoney(), new PlayerMoney(), new PlayerMoney(), new PlayerMoney(), new PlayerMoney(),
			new PlayerMoney() };

	private static PlayerClean[] playerCleanPos = { new PlayerClean(), new PlayerClean(), new PlayerClean(),
			new PlayerClean(), new PlayerClean(), new PlayerClean() };
	private static PlayerClean[] playerCleanRp = { new PlayerClean(), new PlayerClean(), new PlayerClean(),
			new PlayerClean(), new PlayerClean(), new PlayerClean(), new PlayerClean(), new PlayerClean(),
			new PlayerClean() };

	private static PlayerBoardIndexes[] playerBoardIndexesPos = { new PlayerBoardIndexes(), new PlayerBoardIndexes(),
			new PlayerBoardIndexes(), new PlayerBoardIndexes(), new PlayerBoardIndexes(), new PlayerBoardIndexes() };
	private static PlayerBoardIndexes[] playerBoardIndexesRp = { new PlayerBoardIndexes(), new PlayerBoardIndexes(),
			new PlayerBoardIndexes(), new PlayerBoardIndexes(), new PlayerBoardIndexes(), new PlayerBoardIndexes(),
			new PlayerBoardIndexes(), new PlayerBoardIndexes(), new PlayerBoardIndexes() };

	private static Players playerPosAll = new Players();
	private static Players playerRpAll = new Players();
	private static PlayerBetSize playerBetSizePosAll = new PlayerBetSize();
	private static PlayerBetSize playerBetSizeRpAll = new PlayerBetSize();
	private static PlayerMoney playerMoneyPosAll = new PlayerMoney();
	private static PlayerMoney playerMoneyRpAll = new PlayerMoney();
	private static PlayerMDF playerMDFPosAll = new PlayerMDF();
	private static PlayerMDF playerMDFRpAll = new PlayerMDF();
	private static PlayerBoardIndexes playerBoardIndexesPosAll = new PlayerBoardIndexes();
	private static PlayerBoardIndexes playerBoardIndexesRpAll = new PlayerBoardIndexes();
	private static PlayerClean playerCleanPosAll = new PlayerClean();
	private static PlayerClean playerCleanRpAll = new PlayerClean();

	private static boolean[] playerFolded = { false, false, false, false, false, false };
	// Ignore player in this seat
	private static boolean[] ignorePlayer = { false, false, false, false, false, false };
	private static int[] rpArray = new int[PLAYERS];

	private static Players onePlayer = new Players();

	// Input and output directories
	private static String pathIn = "C:\\HHData\\";
	private static String selectedPathIn = "";

	private static final JLabel blank = new JLabel("   ");

	private static final JCheckBox crash = new JCheckBox("Crash on severe error");

	/*-************************************************************************************************
	 * Check boxes for reports 
	************************************************************************************************ */
	private static final JCheckBox playersPos = new JCheckBox("Position");
	private static final JCheckBox playersRp = new JCheckBox("Relative Position");
	private static final JCheckBox playersStreets = new JCheckBox("Streets");
	private static final JCheckBox playersValues = new JCheckBox("Values");
	private static final JCheckBox playersValuesBU = new JCheckBox("Values Button");
	private static final JCheckBox playersValuesBB = new JCheckBox("Values Big Blind");
	private static final JCheckBox playersValuesSB = new JCheckBox("Values Small Blind");
	private static final JCheckBox playersActionPos = new JCheckBox("Action Position");
	private static final JCheckBox playersOperPos = new JCheckBox("Opportunity Position");
	private static final JCheckBox playersActionRp = new JCheckBox("Action Relative Position");
	private static final JCheckBox playersOperRp = new JCheckBox("Opportunity Relative Position");
	private static final JCheckBox charactersiticsPos = new JCheckBox("Position");
	private static final JCheckBox charactersiticsChar = new JCheckBox("Characteristic");
	private static final JCheckBox charactersiticsRp = new JCheckBox("Relative Position");
	private static final JCheckBox charactersiticsValues = new JCheckBox("Values");
	private static final JCheckBox charactersiticsStreets = new JCheckBox("Streets");

	private static final JCheckBox betSizeStepPosPreflop = new JCheckBox("Preflop Step");
	private static final JCheckBox betSizeStepPosFlop = new JCheckBox("Flop Step");
	private static final JCheckBox betSizeStepPosTurn = new JCheckBox("Turn Step");
	private static final JCheckBox betSizeSteoPosRiver = new JCheckBox("River Step");
	private static final JCheckBox betSizeBetPosPreflop = new JCheckBox("Preflop Bet");
	private static final JCheckBox betSizeBetPosFlop = new JCheckBox("Flop Bet");
	private static final JCheckBox betSizeBetPosTurn = new JCheckBox("Turn Bet");
	private static final JCheckBox betSizeBetPosRiver = new JCheckBox("River Bet");
	private static final JCheckBox betSizeCallPosPreflop = new JCheckBox("Preflop Call");
	private static final JCheckBox betSizeCallPosFlop = new JCheckBox("Flop Call");
	private static final JCheckBox betSizeCallPosTurn = new JCheckBox("Turn Call");
	private static final JCheckBox betSizeCallPosRiver = new JCheckBox("River Call");
	private static final JCheckBox betSizeCallPosStreet = new JCheckBox("Streets");

	private static final JCheckBox betSizeStepRpPreflop = new JCheckBox("Preflop Step");
	private static final JCheckBox betSizeStepRpFlop = new JCheckBox("Flop Step");
	private static final JCheckBox betSizeStepRpTurn = new JCheckBox("Turn Step");
	private static final JCheckBox betSizeSteoRpRiver = new JCheckBox("River Step");
	private static final JCheckBox betSizeBetRpPreflop = new JCheckBox("Preflop Bet");
	private static final JCheckBox betSizeBetRpFlop = new JCheckBox("Flop Bet");
	private static final JCheckBox betSizeBetRpTurn = new JCheckBox("Turn Bet");
	private static final JCheckBox betSizeBetRpRiver = new JCheckBox("River Bet");
	private static final JCheckBox betSizeCallRpPreflop = new JCheckBox("Preflop Call");
	private static final JCheckBox betSizeCallRpFlop = new JCheckBox("Flop Call");
	private static final JCheckBox betSizeCallRpTurn = new JCheckBox("Turn Call");
	private static final JCheckBox betSizeCallRpRiver = new JCheckBox("River Call");
	private static final JCheckBox betSizeCallRpStreet = new JCheckBox("Streets");

	private static final JCheckBox betSizeCallHMLPos = new JCheckBox("Call HML");
	private static final JCheckBox betSizeBoardPos = new JCheckBox("Board Indexes");
	private static final JCheckBox betSizeWinShowdownBetPos = new JCheckBox("Win Showdown BetL");
	private static final JCheckBox betSizeWinStreetBetPos = new JCheckBox("Win Street Bet");
	private static final JCheckBox betSizeCallWetDryPos = new JCheckBox("Call Wet Dry");
	private static final JCheckBox betSizeCallStaticDynamicPos = new JCheckBox("Static Dynamic");
	private static final JCheckBox betSizeCallFlopPos = new JCheckBox("Call  Flop");
	private static final JCheckBox betSizePossibleCallPos = new JCheckBox("Possible Call");
	private static final JCheckBox betSizeWinShowdownCallPos = new JCheckBox("Win Showdown Call");
	private static final JCheckBox betSizeWinStreetCallPos = new JCheckBox("Win Street Call");
	private static final JCheckBox betSizeDrawPos = new JCheckBox("Draw");

	private static final JCheckBox betSizeCallHMLRp = new JCheckBox("Call HML");
	private static final JCheckBox betSizeBoardRp = new JCheckBox("Board Indexes");
	private static final JCheckBox betSizeWinShowdownBetRp = new JCheckBox("Win Showdown BetL");
	private static final JCheckBox betSizeWinStreetBetRp = new JCheckBox("Win Street Bet");
	private static final JCheckBox betSizeCallWetDryRp = new JCheckBox("Wet Dry ");
	private static final JCheckBox betSizeCallStaticDynamicRp = new JCheckBox("Static Dynamic");
	private static final JCheckBox betSizeCallFlopRp = new JCheckBox("Call  Flop");
	private static final JCheckBox betSizePossibleCallRp = new JCheckBox("Possible Call");
	private static final JCheckBox betSizeWinShowdownCallRp = new JCheckBox("Win Showdown CallL");
	private static final JCheckBox betSizeWinStreetCallRp = new JCheckBox("Win Street Call");
	private static final JCheckBox betSizeDrawRp = new JCheckBox("Draw");

	private static final JCheckBox cleanPosX = new JCheckBox("Clean Bet");
	private static final JCheckBox cleanPos = new JCheckBox("Clean Bet");

	private static final JCheckBox cleanRpX = new JCheckBox("Clean Bet");
	private static final JCheckBox cleanRp = new JCheckBox("Clean Bet");

	private static final JCheckBox mdfPosX = new JCheckBox("MDF ");
	private static final JCheckBox cleanDataPos = new JCheckBox("MDF Bet");

	private static final JCheckBox mdfRpX = new JCheckBox("MDF ");
	private static final JCheckBox cleanDataRp = new JCheckBox("MDF Bet");

	private static final JCheckBox moneyPos = new JCheckBox("Money");
	private static final JCheckBox moneyRpPos = new JCheckBox("Monet RP Pos");
	private static final JCheckBox moneyValuePos = new JCheckBox("Money Value");
	private static final JCheckBox moneyPlayersPos = new JCheckBox("Money Player");
	private static final JCheckBox moneyStreetePos = new JCheckBox("MoneyStreet");

	private static final JCheckBox moneyRpX = new JCheckBox("Money Relative Position ");
	private static final JCheckBox moneyRp = new JCheckBox("Money");
	private static final JCheckBox moneyRpRp = new JCheckBox("Monet RP Rp");
	private static final JCheckBox moneyValueRp = new JCheckBox("Money Value");
	private static final JCheckBox moneyPlayersRp = new JCheckBox("Money Player");
	private static final JCheckBox moneyStreeteRp = new JCheckBox("MoneyStreet");

	private static final JCheckBox boardIndexesHMLPos = new JCheckBox("HML");
	private static final JCheckBox boardIndexesWetDryPos = new JCheckBox("WetDry");
	private static final JCheckBox boardIndexesStaticDynamicPos = new JCheckBox("StaticDynamic");
	private static final JCheckBox boardIndexesBetHMLPos = new JCheckBox("Bet HML");
	private static final JCheckBox boardIndexesBetWetDryPos = new JCheckBox("Bet Wet Dry");
	private static final JCheckBox boardIndexesBetStaticDynamicPos = new JCheckBox("Bet Static Dynamic");
	private static final JCheckBox boardIndexesDrawsPos = new JCheckBox("Draw");

	private static final JCheckBox boardIndexesRpX = new JCheckBox("Board Indexes Rpition ");
	private static final JCheckBox boardIndexesHMLRp = new JCheckBox("HML");
	private static final JCheckBox boardIndexesWetDryRp = new JCheckBox("WetDry");
	private static final JCheckBox boardIndexesStaticDynamicRp = new JCheckBox("StaticDynamic");
	private static final JCheckBox boardIndexesBetHMLRp = new JCheckBox("Bet HML");
	private static final JCheckBox boardIndexesBetWetDryRp = new JCheckBox("Bet Wet Dry");
	private static final JCheckBox boardIndexesBetStaticDynamicRp = new JCheckBox("Bet Static Dynamic");
	private static final JCheckBox boardIndexesDrawsRp = new JCheckBox("Draw");

	private static final JPanel panel0 = new JPanel();
	private static final JPanel panel1 = new JPanel();
	private static final JPanel panelA = new JPanel();
	private static final JPanel panelB = new JPanel();
	private static final JPanel panelBRp = new JPanel();
	private static final JPanel panel2 = new JPanel();
	private static final JPanel panel2Rp = new JPanel();
	private static final JPanel panel3 = new JPanel();
	private static final JPanel panel3Rp = new JPanel();
	private static final JPanel panel4 = new JPanel();
	private static final JPanel panel5 = new JPanel();
	private static final JPanel panel5Rp = new JPanel();
	private static final JPanel panel6 = new JPanel();
	private static final JPanel panel7 = new JPanel();
	private static final JPanel panelBet = new JPanel();
	private static final JPanel panelFrequency = new JPanel();
	private static final JPanel panelCall = new JPanel();
	private static final JPanel panelCheck = new JPanel();
	private static final JPanel panelFold = new JPanel();
	private static final JPanel panel8 = new JPanel();
	private static final JPanel panel9 = new JPanel();
	private static final JPanel panel10 = new JPanel();
	private static final JPanel panelC = new JPanel();
	private static final JPanel panelType = new JPanel();
	private static final JButton buttonRun = new JButton("Run");
	private static final JButton buttonSelectReports = new JButton("Select Reports");
	private static final JButton buttonFolder = new JButton("Select Folders");
	private static final JButton buttonHelp = new JButton("Help");
	private static final JButton buttonExit = new JButton("Exit");
	private static final JTextField entryText = new JTextField(6);
	private static final JTextField statusDirectories = new JTextField(
			"                                                                            ");

	private static final JPanel panelBlank = new JPanel();

	private static boolean run = false;
	private static boolean exit = false;

	public static void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (Exception e) {

		}
	}

	/*-************************************************************************************************
	 * Main - . 
	 * Display startup window.
	 * Wait for operator to select folder.
	 * Step through each pass.
	 * Select reports.
	 *************************************************************************************************/
	public static void main(String[] s0) {
		if (!ValidInstallation.checkValid()) {
			return;
		}
		run = false;
		exit = false;
		selectionWindow();

		if (exit) {
			return;
		}

		// Wait for run or exit
		while (!run) {
			sleep(1000);
			if (exit) {
				return;
			}
		}
	}

	/*- ***********************************************************************************************
	 * Display Report Selection window 
	*********************************************************************************************** */
	static void selectionWindow() {
		JPanel panelS = new JPanel();
		final var frame = new JFrame("Hand History Data Display");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(150, 50);
		frame.setPreferredSize(new Dimension(400, 400));

		panelS.setLayout(new GridLayout(10, 2));
		final var f = new Font(Font.SERIF, Font.BOLD, 16);
		Box.createHorizontalBox();

		buttonFolder.setToolTipText("Input Folder for Hand History Data Files");
		buttonFolder.setBackground(Color.GREEN);
		buttonFolder.addActionListener(new Listener());
		buttonSelectReports.setToolTipText("SelectReports");
		buttonSelectReports.setBackground(Color.GREEN);
		buttonSelectReports.addActionListener(new Listener());
		buttonHelp.setToolTipText("Help");
		buttonHelp.setBackground(Color.GREEN);
		buttonHelp.addActionListener(new Listener());
		buttonExit.setToolTipText("Exit");
		buttonExit.setBackground(Color.GREEN);
		buttonExit.addActionListener(new Listener());
		buttonRun.setEnabled(false);

		Box.createHorizontalBox();
		statusDirectories.setBorder(BorderFactory.createTitledBorder("Input Folder      "));
		statusDirectories.setBackground(Color.WHITE);
		statusDirectories.setFont(f);
		panelS.add(statusDirectories);
		panelS.add(panelBlank);
		panelS.add(panelBlank);
		panelBlank.setFont(f);

		final var control = Box.createHorizontalBox();
		final var fff = new Font(Font.SERIF, Font.BOLD, 16);
		control.setFont(fff);
		panelC.add(buttonFolder);
		panelC.add(buttonSelectReports);
		panelC.add(buttonHelp);
		panelC.add(buttonExit);
		panelC.add(control);
		panelS.add(panelC);
		frame.add(panelS);
		buttonSelectReports.setEnabled(false);
		buttonRun.setEnabled(false);
		frame.pack();
		frame.setVisible(true);
	}

	/*- ***********************************************************************************************
	 * Display Report Selection window 
	*********************************************************************************************** */
	static void startupWindow() {
		final var frame = new JFrame("Select Reports    ");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(100, 50);
		frame.setPreferredSize(new Dimension(1400, 1000));

		panel0.setLayout(new GridLayout(6, 2));
		final var f = new Font(Font.SERIF, Font.BOLD, 16);
		Box.createHorizontalBox();

		final var options1 = Box.createHorizontalBox();
		options1.setFont(f);
		panel1.setBorder(BorderFactory.createTitledBorder("Players "));
		options1.add(playersPos);
		options1.add(playersRp);
		options1.add(playersStreets);
		options1.add(playersValues);
		options1.add(playersValuesBU);
		options1.add(playersValuesBB);
		options1.add(playersValuesSB);
		options1.add(playersActionPos);
		options1.add(playersOperPos);
		options1.add(playersActionRp);
		options1.add(playersOperRp);
		panel1.add(playersPos);
		panel1.add(playersRp);
		panel1.add(playersStreets);
		panel1.add(playersValues);
		panel1.add(playersValuesBU);
		panel1.add(playersValuesBB);
		panel1.add(playersValuesSB);
		panel1.add(playersActionPos);
		panel1.add(playersOperPos);
		panel1.add(playersActionRp);
		panel1.add(playersOperRp);
		panel0.add(panel1);

		final var optionsA = Box.createHorizontalBox();
		optionsA.setFont(f);
		panelA.setBorder(BorderFactory.createTitledBorder("Player Characteristics"));
		optionsA.add(charactersiticsPos);
		optionsA.add(charactersiticsChar);
		optionsA.add(charactersiticsRp);
		optionsA.add(charactersiticsValues);
		optionsA.add(charactersiticsStreets);
		panelA.add(charactersiticsPos);
		panelA.add(charactersiticsChar);
		panelA.add(charactersiticsRp);
		panelA.add(charactersiticsValues);
		panelA.add(charactersiticsStreets);
		panel0.add(panelA);

		final var optionsB = Box.createHorizontalBox();
		optionsB.setFont(f);
		panelB.setBorder(BorderFactory.createTitledBorder("Bet Size Steps by Position"));
		optionsB.add(betSizeStepPosPreflop);
		optionsB.add(betSizeStepPosFlop);
		optionsB.add(betSizeStepPosTurn);
		optionsB.add(betSizeSteoPosRiver);
		optionsB.add(betSizeBetPosPreflop);
		optionsB.add(betSizeBetPosFlop);
		optionsB.add(betSizeBetPosTurn);
		optionsB.add(betSizeBetPosRiver);
		optionsB.add(betSizeCallPosPreflop);
		optionsB.add(betSizeCallPosFlop);
		optionsB.add(betSizeCallPosTurn);
		optionsB.add(betSizeCallPosRiver);
		panelB.add(betSizeStepPosPreflop);
		panelB.add(betSizeStepPosFlop);
		panelB.add(betSizeStepPosTurn);
		panelB.add(betSizeSteoPosRiver);
		panelB.add(betSizeBetPosPreflop);
		panelB.add(betSizeBetPosFlop);
		panelB.add(betSizeBetPosTurn);
		panelB.add(betSizeBetPosRiver);
		panelB.add(betSizeCallPosPreflop);
		panelB.add(betSizeCallPosFlop);
		panelB.add(betSizeCallPosTurn);
		panelB.add(betSizeCallPosRiver);
		panel0.add(panelB);

		final var optionsBRp = Box.createHorizontalBox();
		optionsBRp.setFont(f);
		panelBRp.setBorder(BorderFactory.createTitledBorder("Bet Size Steps by Relative Position"));
		optionsBRp.add(betSizeStepRpPreflop);
		optionsBRp.add(betSizeStepRpFlop);
		optionsBRp.add(betSizeStepRpTurn);
		optionsBRp.add(betSizeSteoRpRiver);
		optionsBRp.add(betSizeBetRpPreflop);
		optionsBRp.add(betSizeBetRpFlop);
		optionsBRp.add(betSizeBetRpTurn);
		optionsBRp.add(betSizeBetRpRiver);
		optionsBRp.add(betSizeCallRpPreflop);
		optionsBRp.add(betSizeCallRpFlop);
		optionsBRp.add(betSizeCallRpTurn);
		optionsBRp.add(betSizeCallRpRiver);
		panelBRp.add(betSizeStepRpPreflop);
		panelBRp.add(betSizeStepRpFlop);
		panelBRp.add(betSizeStepRpTurn);
		panelBRp.add(betSizeSteoRpRiver);
		panelBRp.add(betSizeBetRpPreflop);
		panelBRp.add(betSizeBetRpFlop);
		panelBRp.add(betSizeBetRpTurn);
		panelBRp.add(betSizeBetRpRiver);
		panelBRp.add(betSizeCallRpPreflop);
		panelBRp.add(betSizeCallRpFlop);
		panelBRp.add(betSizeCallRpTurn);
		panelBRp.add(betSizeCallRpRiver);
		panel0.add(panelBRp);

		final var options2 = Box.createHorizontalBox();
		options2.setFont(f);
		panel2.setBorder(BorderFactory.createTitledBorder("Bet Size Very Detailed by Position"));
		options2.add(betSizeCallHMLPos);
		options2.add(betSizeBoardPos);
		options2.add(betSizeWinShowdownBetPos);
		options2.add(betSizeWinStreetBetPos);
		options2.add(betSizeCallWetDryPos);
		options2.add(betSizeCallStaticDynamicPos);
		options2.add(betSizeCallFlopPos);
		options2.add(betSizePossibleCallPos);
		options2.add(betSizeWinShowdownCallPos);
		options2.add(betSizeWinStreetCallPos);
		options2.add(betSizeDrawPos);
		panel2.add(betSizeCallHMLPos);
		panel2.add(betSizeBoardPos);
		panel2.add(betSizeWinShowdownBetPos);
		panel2.add(betSizeWinStreetBetPos);
		panel2.add(betSizeCallWetDryPos);
		panel2.add(betSizeCallStaticDynamicPos);
		panel2.add(betSizeCallFlopPos);
		panel2.add(betSizePossibleCallPos);
		panel2.add(betSizeWinShowdownCallPos);
		panel2.add(betSizeWinStreetCallPos);
		panel2.add(betSizeDrawPos);
		panel0.add(panel2);

		final var options2Rp = Box.createHorizontalBox();
		options2Rp.setFont(f);
		panel2Rp.setBorder(BorderFactory.createTitledBorder("Bet Size Very Detailed by Relative Position"));
		options2Rp.add(betSizeCallHMLRp);
		options2Rp.add(betSizeBoardRp);
		options2Rp.add(betSizeWinShowdownBetRp);
		options2Rp.add(betSizeWinStreetBetRp);
		options2Rp.add(betSizeCallWetDryRp);
		options2Rp.add(betSizeCallStaticDynamicRp);
		options2Rp.add(betSizeCallFlopRp);
		options2Rp.add(betSizePossibleCallRp);
		options2Rp.add(betSizeWinShowdownCallRp);
		options2Rp.add(betSizeWinStreetCallRp);
		options2Rp.add(betSizeDrawRp);
		panel2Rp.add(betSizeCallHMLRp);
		panel2Rp.add(betSizeBoardRp);
		panel2Rp.add(betSizeWinShowdownBetRp);
		panel2Rp.add(betSizeWinStreetBetRp);
		panel2Rp.add(betSizeCallWetDryRp);
		panel2Rp.add(betSizeCallStaticDynamicRp);
		panel2Rp.add(betSizeCallFlopRp);
		panel2Rp.add(betSizePossibleCallRp);
		panel2Rp.add(betSizeWinShowdownCallRp);
		panel2Rp.add(betSizeWinStreetCallRp);
		panel2Rp.add(betSizeDrawPos);
		panel0.add(panel2Rp);

		final var options4 = Box.createHorizontalBox();
		options4.setFont(f);
		panel4.setBorder(BorderFactory.createTitledBorder("Clean bets by Position and Relative Position"));
		options4.add(cleanPos);
		options4.add(cleanRp);
		panel4.add(cleanPos);
		panel4.add(cleanRp);
		panel0.add(panel4);

		final var options5Rp = Box.createHorizontalBox();
		options5Rp.setFont(f);
		panel5Rp.setBorder(BorderFactory.createTitledBorder("Money by Relative Position "));
		options5Rp.add(moneyRp);
		options5Rp.add(moneyRpRp);
		options5Rp.add(moneyValueRp);
		options5Rp.add(moneyPlayersRp);
		options5Rp.add(moneyStreeteRp);
		panel5Rp.add(moneyRp);
		panel5Rp.add(moneyRpRp);
		panel5Rp.add(moneyValueRp);
		panel5Rp.add(moneyPlayersRp);
		panel5Rp.add(moneyStreeteRp);
		panel0.add(panel5Rp);

		final var options5 = Box.createHorizontalBox();
		options5.setFont(f);
		panel5.setBorder(BorderFactory.createTitledBorder("Money by Position "));
		options5.add(moneyPos);
		options5.add(moneyRpPos);
		options5.add(moneyValuePos);
		options5.add(moneyPlayersPos);
		options5.add(moneyStreetePos);
		panel5.add(moneyPos);
		panel5.add(moneyRpPos);
		panel5.add(moneyValuePos);
		panel5.add(moneyPlayersPos);
		panel5.add(moneyStreetePos);
		panel0.add(panel5);

		final var options3 = Box.createHorizontalBox();
		options3.setFont(f);
		panel3.setBorder(BorderFactory.createTitledBorder("Board Indexes by Position"));
		options3.add(boardIndexesHMLPos);
		options3.add(boardIndexesWetDryPos);
		options3.add(boardIndexesStaticDynamicPos);
		options3.add(boardIndexesBetHMLPos);
		options3.add(boardIndexesBetWetDryPos);
		options3.add(boardIndexesBetStaticDynamicPos);
		options3.add(boardIndexesDrawsPos);
		panel3.add(boardIndexesHMLPos);
		panel3.add(boardIndexesWetDryPos);
		panel3.add(boardIndexesStaticDynamicPos);
		panel3.add(boardIndexesBetHMLPos);
		panel3.add(boardIndexesBetWetDryPos);
		panel3.add(boardIndexesBetStaticDynamicPos);
		panel3.add(boardIndexesDrawsPos);
		panel0.add(panel3);

		final var options3Rp = Box.createHorizontalBox();
		options3Rp.setFont(f);
		panel3Rp.setBorder(BorderFactory.createTitledBorder("Board Indexes by Relative Position"));
		options3Rp.add(boardIndexesHMLRp);
		options3Rp.add(boardIndexesWetDryRp);
		options3Rp.add(boardIndexesStaticDynamicRp);
		options3Rp.add(boardIndexesBetHMLRp);
		options3Rp.add(boardIndexesBetWetDryRp);
		options3Rp.add(boardIndexesBetStaticDynamicRp);
		options3Rp.add(boardIndexesDrawsRp);
		panel3Rp.add(boardIndexesHMLRp);
		panel3Rp.add(boardIndexesWetDryRp);
		panel3Rp.add(boardIndexesStaticDynamicRp);
		panel3Rp.add(boardIndexesBetHMLRp);
		panel3Rp.add(boardIndexesBetWetDryRp);
		panel3Rp.add(boardIndexesBetStaticDynamicRp);
		panel3Rp.add(boardIndexesDrawsRp);
		panel0.add(panel3Rp);

		buttonRun.addActionListener(new Listener());
		buttonRun.setToolTipText("Run selected reports for player type");
		buttonRun.setBackground(Color.GREEN);
		buttonExit.addActionListener(new Listener());
		buttonHelp.setToolTipText("Help");
		buttonHelp.setBackground(Color.GREEN);
		buttonHelp.addActionListener(new Listener());
		buttonExit.setToolTipText("Exit");
		buttonExit.setBackground(Color.GREEN);

		final var control = Box.createHorizontalBox();
		final var fff = new Font(Font.SERIF, Font.BOLD, 16);
		control.setFont(fff);
		panelC.add(buttonRun);
		panelC.add(buttonHelp);
		panelC.add(buttonExit);
		panelC.add(control);
		panel0.add(panelC);
		frame.add(panel0);
		frame.pack();
		frame.setVisible(true);
	}

	/*-************************************************************************************************
	* Read from files
	************************************************************************************************ */
	private static void readAllFiles() {
		String path = pathIn;
		onePlayer = onePlayer.readFromFile(path + "Players.ser");
		playerPosAll = playerPosAll.readFromFile(path + "PlayersPos.ser");
		playerRpAll = playerRpAll.readFromFile(path + "PlayersRp.ser");
		playerBetSizePosAll = playerBetSizePosAll.readFromFile(path + " BetSizePos.ser");
		playerBetSizeRpAll = playerBetSizeRpAll.readFromFile(path + " BetSizeRp.ser");
		playerMoneyPosAll = playerMoneyPosAll.readFromFile(path + " MoneyPos.ser");
		playerMoneyRpAll = playerMoneyRpAll.readFromFile(path + " MoneyRp.ser");
		playerMDFPosAll = playerMDFPosAll.readFromFile(path + " MDFPos.ser");
		playerMDFRpAll = playerMDFRpAll.readFromFile(path + " MDFRp.ser");
		playerBoardIndexesPosAll = playerBoardIndexesPosAll.readFromFile(path + " IndexPos.ser");
		playerBoardIndexesRpAll = playerBoardIndexesRpAll.readFromFile(path + " IndexRp.ser");
		playerCleanPosAll = playerCleanPosAll.readFromFile(path + " CleanPos.ser");
		playerCleanRpAll = playerCleanRpAll.readFromFile(path + " CleanRp.ser");
	}

	/*- ***********************************************************************************************
	*	Reports. 
	* The check boxes are checked for selected
	* If selected then the report is created.
	************************************************************************************************/
	private static void reports() {
		PlayerCharacteristics pc = new PlayerCharacteristics();
		if (playersPos.isSelected()) {
			PlayersReport.newArraysPos(onePlayer);
		}
		if (playersRp.isSelected()) {
			PlayersReport.newArraysRp(onePlayer);
		}
		if (playersStreets.isSelected()) {
			PlayersReport.allActionPos(onePlayer);
		}
		if (playersValues.isSelected()) {
			PlayersReport.values(onePlayer);
		}
		if (playersValuesBU.isSelected()) {
			PlayersReport.valuesBU(onePlayer);
		}
		if (playersValuesBB.isSelected()) {
			PlayersReport.valuesBB(onePlayer);
		}
		if (playersValuesSB.isSelected()) {
			PlayersReport.valuesSB(onePlayer);
		}
		if (playersActionPos.isSelected()) {
			PlayersReport.allActionPos(onePlayer);
		}
		if (playersOperPos.isSelected()) {
			PlayersReport.allOperPos(onePlayer);
		}
		if (playersActionRp.isSelected()) {
			PlayersReport.allActionRp(onePlayer);
		}
		if (playersOperRp.isSelected()) {
			PlayersReport.allOperRp(onePlayer);
		}

		if (charactersiticsPos.isSelected()) {
			PlayerCharacteristicsReport.values(pc);
		}
		if (charactersiticsChar.isSelected()) {
			PlayerCharacteristicsReport.streets(pc);
		}
		if (charactersiticsRp.isSelected()) {
			PlayerCharacteristicsReport.playerStats(pc);
		}
		if (charactersiticsValues.isSelected()) {
			PlayerCharacteristicsReport.allPercentagePos(pc);
		}
		if (charactersiticsStreets.isSelected()) {
			PlayerCharacteristicsReport.allPercentageRp(pc);
		}

		// TODO WTF screwed up
		if (betSizeStepPosPreflop.isSelected()) {
			PlayerBetSizeReports.frequencyByStreetBetAndStepPreflop(playerBetSizePosAll);
		}
		if (betSizeStepPosFlop.isSelected()) {
			PlayerBetSizeReports.frequencyByStreetBetAndStepFlop(playerBetSizePosAll);
		}
		if (betSizeStepPosTurn.isSelected()) {
			PlayerBetSizeReports.frequencyByStreetBetAndStepTurn(playerBetSizePosAll);
		}
		if (betSizeSteoPosRiver.isSelected()) {
			PlayerBetSizeReports.frequencyByStreetBetAndStepRiver(playerBetSizePosAll);
		}
		if (betSizeBetPosPreflop.isSelected()) {
			PlayerBetSizeReports.frequencyBetPreflop(playerBetSizePosAll);
		}
		if (betSizeBetPosFlop.isSelected()) {
			PlayerBetSizeReports.frequencyBetFlop(playerBetSizePosAll);
		}
		if (betSizeBetPosTurn.isSelected()) {
			PlayerBetSizeReports.frequencyBetTurn(playerBetSizePosAll);
		}
		if (betSizeBetPosRiver.isSelected()) {
			PlayerBetSizeReports.frequencyBetRiver(playerBetSizePosAll);
		}
		if (betSizeCallPosPreflop.isSelected()) {
			PlayerBetSizeReports.frequencyCallPreflop(playerBetSizePosAll);
		}
		if (betSizeCallPosFlop.isSelected()) {
			PlayerBetSizeReports.frequencyCallFlop(playerBetSizePosAll);
		}
		if (betSizeCallPosTurn.isSelected()) {
			PlayerBetSizeReports.frequencyCallTurn(playerBetSizePosAll);
		}
		if (betSizeCallPosRiver.isSelected()) {
			PlayerBetSizeReports.frequencyCallRiver(playerBetSizePosAll);
		}
		if (betSizeCallPosStreet.isSelected()) {
			PlayerBetSizeReports.betsByStreet(playerBetSizePosAll);
		}
		if (playersPos.isSelected()) {
			PlayerBetSizeReports.betCallRpPreflop(playerBetSizePosAll);
		}
		if (playersPos.isSelected()) {
			PlayerBetSizeReports.betCallRpFlop(playerBetSizePosAll);
		}
		if (playersPos.isSelected()) {
			PlayerBetSizeReports.betCallRpTurn(playerBetSizePosAll);
		}
		if (playersPos.isSelected()) {
			PlayerBetSizeReports.betCallRpRiver(playerBetSizePosAll);
		}

		if (betSizeStepRpPreflop.isSelected()) {
			PlayerBetSizeReports.frequencyByStreetBetAndStepPreflop(playerBetSizeRpAll);
		}
		if (betSizeStepRpFlop.isSelected()) {
			PlayerBetSizeReports.frequencyByStreetBetAndStepFlop(playerBetSizeRpAll);
		}
		if (betSizeStepRpTurn.isSelected()) {
			PlayerBetSizeReports.frequencyByStreetBetAndStepTurn(playerBetSizeRpAll);
		}
		if (betSizeSteoRpRiver.isSelected()) {
			PlayerBetSizeReports.frequencyByStreetBetAndStepRiver(playerBetSizeRpAll);
		}
		if (betSizeBetRpPreflop.isSelected()) {
			PlayerBetSizeReports.frequencyBetPreflop(playerBetSizeRpAll);
		}
		if (betSizeBetRpFlop.isSelected()) {
			PlayerBetSizeReports.frequencyBetFlop(playerBetSizeRpAll);
		}
		if (betSizeBetRpTurn.isSelected()) {
			PlayerBetSizeReports.frequencyBetTurn(playerBetSizeRpAll);
		}
		if (betSizeBetRpRiver.isSelected()) {
			PlayerBetSizeReports.frequencyBetRiver(playerBetSizeRpAll);
		}
		if (betSizeCallRpPreflop.isSelected()) {
			PlayerBetSizeReports.frequencyCallPreflop(playerBetSizeRpAll);
		}
		if (betSizeCallRpFlop.isSelected()) {
			PlayerBetSizeReports.frequencyCallFlop(playerBetSizeRpAll);
		}
		if (betSizeCallRpTurn.isSelected()) {
			PlayerBetSizeReports.frequencyCallTurn(playerBetSizeRpAll);
		}
		if (betSizeCallRpRiver.isSelected()) {
			PlayerBetSizeReports.frequencyCallRiver(playerBetSizeRpAll);
		}
		if (betSizeCallRpStreet.isSelected()) {
			// TODO PlayerBetSizeReports.frequencyCallRiver(playerBetSizeRpAll);
		}

		if (betSizeCallHMLPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_boardBetHMLGUI(playerBetSizePosAll);
		}
		if (betSizeBoardPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_boardPossibleBetGUI(playerBetSizePosAll);
		}
		if (betSizeWinShowdownBetPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_playerWinShowdownBetGUI(playerBetSizePosAll);
		}
		if (betSizeWinStreetBetPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_playerWinStreetBetGUI(playerBetSizePosAll);
		}
		if (betSizeCallWetDryPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_boardCallHMLGUI(playerBetSizePosAll);
		}
		if (betSizeCallStaticDynamicPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_boardCallWetDryGUI(playerBetSizePosAll);
		}
		if (betSizeCallFlopPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_boardCallStaticDynamicGUI(playerBetSizePosAll);
		}
		if (betSizePossibleCallPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_boardCallFlopGUI(playerBetSizePosAll);
		}
		if (betSizeWinShowdownCallPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_boardPossibleCallGUI(playerBetSizePosAll);
		}
		if (betSizeWinStreetCallPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_playerWinShowdownCallGUI(playerBetSizePosAll);
		}
		if (betSizeDrawPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_playerWinStreetCallGUI(playerBetSizePosAll);
		}
		if (betSizeDrawPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_drawGUI(playerBetSizePosAll);
		}
		/*-
		// PlayerBetSizeReports2.showBetSize_boardBetHMLGUI(playerBetSizePosAll);
		// PlayerBetSizeReports2.showBetSize_boardPossibleBetGUI(playerBetSizeRpAll);
		// PlayerBetSizeReports2.showBetSize_playerWinShowdownBetGUI(playerBetSizeRpAll);
		// PlayerBetSizeReports2.showBetSize_playerWinStreetBetGUI(playerBetSizeRpAll);
		// PlayerBetSizeReports2.showBetSize_boardCallHMLGUI(playerBetSizeRpAll);
		// PlayerBetSizeReports2.showBetSize_boardCallWetDryGUI(playerBetSizeRpAll);
		// PlayerBetSizeReports2.showBetSize_boardCallStaticDynamicGUI(playerBetSizeRpAll);
		// PlayerBetSizeReports2.showBetSize_boardCallFlopGUI(playerBetSizeRpAll);
		// PlayerBetSizeReports2.showBetSize_boardPossibleCallGUI(playerBetSizeRpAll);
		// PlayerBetSizeReports2.showBetSize_playerWinShowdownCallGUI(playerBetSizeRpAll);
		// PlayerBetSizeReports2.showBetSize_playerWinStreetCallGUI(playerBetSizeRpAll);
		// PlayerBetSizeReports2.showBetSize_drawGUI(playerBetSizeRpAll);
		
		 */
		if (betSizeCallHMLPos.isSelected()) {
			PlayerBetSizeReports2.showBetSize_boardPossibleBetGUI(playerBetSizePosAll);
		}
		if (betSizeCallHMLRp.isSelected()) {
			PlayerBetSizeReports2.showBetSize_boardPossibleBetGUI(playerBetSizeRpAll);
		}
		if (betSizeBoardRp.isSelected()) {

		}
		if (betSizeWinShowdownBetRp.isSelected()) {

		}
		if (betSizeWinStreetBetRp.isSelected()) {

		}
		if (betSizeCallWetDryRp.isSelected()) {

		}
		if (betSizeCallStaticDynamicRp.isSelected()) {

		}
		if (betSizeCallFlopRp.isSelected()) {

		}
		if (betSizePossibleCallRp.isSelected()) {

		}
		if (betSizeWinShowdownCallRp.isSelected()) {

		}
		if (betSizeWinStreetCallRp.isSelected()) {

		}
//	TODO	if (betSizeDraw.isSelected()) {

//		}

		if (cleanPos.isSelected()) {
			PlayerCleanReports.cleanPos(playerCleanPosAll);
		}
		if (cleanRp.isSelected()) {
			PlayerCleanReports.cleanPos(playerCleanPosAll);
		}
		if (cleanDataPos.isSelected()) {
			PlayerMDFReports.mdfDataPos(playerMDFPosAll);
		}
//TODO		if (cleanDataRps.isSelected()) {
		// PlayerMDFReports.mdfDataPos(playerMDFPosAll);
//		}

		if (moneyPos.isSelected()) {
			PlayerMoneyReports.moneyPos(playerMoneyPosAll);
		}
		if (moneyRpPos.isSelected()) {
			PlayerMoneyReports.moneyRp(playerMoneyPosAll);
		}
		if (moneyValuePos.isSelected()) {
			PlayerMoneyReports.moneyValue(playerMoneyPosAll);
		}
		if (moneyPlayersPos.isSelected()) {
			PlayerMoneyReports.moneyPlayers(playerMoneyPosAll);
		}
		if (moneyStreetePos.isSelected()) {
			PlayerMoneyReports.moneyStreet(playerMoneyPosAll);

		}

		if (moneyRp.isSelected()) {
			PlayerMoneyReports.moneyRp(playerMoneyPosAll);
		}
		if (moneyRpRp.isSelected()) {
			PlayerMoneyReports.moneyRp(playerMoneyRpAll);
		}
		if (moneyValueRp.isSelected()) {
			PlayerMoneyReports.moneyValue(playerMoneyRpAll);
		}
		if (moneyPlayersRp.isSelected()) {
			PlayerMoneyReports.moneyPlayers(playerMoneyRpAll);
		}
		if (moneyStreeteRp.isSelected()) {
			PlayerMoneyReports.moneyStreet(playerMoneyRpAll);
		}

		if (boardIndexesHMLPos.isSelected()) {
			PlayerBoardIndexesReports.dataHML(playerBoardIndexesPosAll);
		}
		if (boardIndexesWetDryPos.isSelected()) {
			PlayerBoardIndexesReports.betCountsWetDry(playerBoardIndexesPosAll);
		}
		if (boardIndexesStaticDynamicPos.isSelected()) {
			PlayerBoardIndexesReports.betCountsStaticDynamic(playerBoardIndexesPosAll);
		}
		if (boardIndexesBetHMLPos.isSelected()) {
			PlayerBoardIndexesReports.c_BetHML(playerBoardIndexesPosAll);
		}
		if (boardIndexesBetWetDryPos.isSelected()) {
			PlayerBoardIndexesReports.c_BetWetDry(playerBoardIndexesPosAll);
		}
		if (boardIndexesBetStaticDynamicPos.isSelected()) {
			PlayerBoardIndexesReports.c_BetStaticDynamic(playerBoardIndexesPosAll);
		}
		if (boardIndexesDrawsPos.isSelected()) {
			PlayerBoardIndexesReports.c_BetDraws(playerBoardIndexesPosAll);
		}

		if (boardIndexesHMLRp.isSelected()) {
			PlayerBoardIndexesReports.dataHML(playerBoardIndexesRpAll);
		}
		if (boardIndexesWetDryRp.isSelected()) {
			PlayerBoardIndexesReports.betCountsWetDry(playerBoardIndexesRpAll);
		}
		if (boardIndexesStaticDynamicRp.isSelected()) {
			PlayerBoardIndexesReports.betCountsStaticDynamic(playerBoardIndexesRpAll);
		}
		if (boardIndexesBetHMLRp.isSelected()) {
			PlayerBoardIndexesReports.c_BetHML(playerBoardIndexesRpAll);
		}
		if (boardIndexesBetWetDryRp.isSelected()) {
			PlayerBoardIndexesReports.c_BetWetDry(playerBoardIndexesRpAll);
		}
		if (boardIndexesBetStaticDynamicRp.isSelected()) {
			PlayerBoardIndexesReports.c_BetStaticDynamic(playerBoardIndexesRpAll);
		}
		if (boardIndexesDrawsRp.isSelected()) {
			PlayerBoardIndexesReports.c_BetDraws(playerBoardIndexesRpAll);
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
		var $ = j.getSelectedFile().getAbsolutePath();
		return $ + "\\";
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
				entryText.setText("Input Folder " + selectedPathIn);
				buttonRun.setEnabled(true);
			}
			case "Help" -> JOptionPane.showMessageDialog(null, helpString);
			case "Exit" -> exit = true;
			case "Select Reports" -> {
				readAllFiles();
				startupWindow();
			}
			case "Run" -> run = true;
			}
		}
	}

	// @formatter:off  
			private static final String helpString = new StringBuilder().append("Welcome to the Holdem Hand History project.\r\n")
					.append("This GUI displays the data created and saved in a Folder by GUIAnalyze. \r\n")
					.append("There is no need to run GUIAnalyze again every time that you want to examine the data created.\r\n")
					.append("The files are saved in a Folder that the user of GYIAnalyze selects.\r\n")
					.append("This application will ask the user to select an input Folder where the data that he is interested\r\n")
					.append("in has previously been saved.\r\n")
					.append("Once the data is read, the operator can then choose from many reports that show the data saved.\r\n")
					.append("Note that when the files that GUIAnalyze were created by GUIPlayerSelection the characteristics of\r\n")
					.append("the players to be analyzed were first selected. Things like win rate, player type ( Lag, Tag, Shark... )\r\n")
					.append("and mayy other possibilities and combinations. So there are thousands of possible types of analysis that\r\n")
					.append("may have been done.\r\n")
					.append("\r\n")
					.append("In addition to the many reports available by this application, there is another application,\r\n")
					.append("GUIAnalyzeData that is an interactive application that does dynamic analysis of the saved data,\r\n")
					.append("not static reports on the data saved. GUIAnalyzeData works much like this application, starting with\r\n")
					.append("the selection of a Folder containing files. \r\n")
					.append("\r\n")
					.append("There are other applications in this project: \r\n")
					.append("    A hand evaluation application.\r\n")
					.append("    A game simulator that includes play optimization by running millions of hands while adjusting and evaluating.\r\n")
					.append("    An application that allows you to play against the Simulator.\r\n")
					.append("    Feedback between the applications to confirm theories.\r\n")
					.append("\r\n")
					.append("Try it. Send me your suggestions and comments. Feel free to use any of the source code.\r\n")  
					.append("We need programmers to join our effort in several areas. GUIAnalyzeData ia a very key one. We need new ideas.\r\n")  
					.append("This is open source, not a commercial project, so it is all volunteer.\r\n")
					.toString();
			// @formatter:on

}
