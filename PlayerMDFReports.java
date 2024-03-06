package holdemhandhistory;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

public class PlayerMDFReports implements Constants {

	/*-****************************************************************************************
	 * This class does a bunch of reports using data collected by .
	 * The reports are in a separate class ( this one ) for a couple of resons:
	 * 		 was just too big.
	 * 		By passing PlayerClassification and Players classes to this class it makes reports
	 * 		for filtered players or player tipes very simple. No code changes to report methods.
	 * Most of the reports are designed to assist in debug and testing but
	 * are also usefull for understanding how the collected and analyzed data can be used to
	 * improve exploits.
	 * 
	 * Something new to Holdem applications is the idea of relative positions and
	 * ranges associated.
	 *	Preflop LASTHU last heads up are the Button in orbit 0 BUT if the Button
	 *	folds then the Cutoff becomes in effect the Button, last heads up. 
	 *	So ranges can change in after Orbit 0. 
	 *	In the Button folding example, the range used by the Cutoff is dynamic
	 *	not static as is usually the case now.
	 *	I'll be evaluating this as we progress. 
	 * 
	 * @author PEAK_
	**************************************************************************************** */

	// @formatter:off

	private static PlayerClassification allPlayerClassification;

	private static final Object[] columnsClean = { "Action ", "SB", "BB", "UTG", "MP", "Cutoff", "Button" };
	
	private static final double[][] checkOptimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] foldOptimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] bet1Optimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] callBet1Optimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] bet2Optimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] callBet2Optimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] bet3Optimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] callBet3Optimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] bet4Optimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] callBet4Optimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] allinOptimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] callAllinOptimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] cBetOptimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] MDFOptimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] MDFBet2Optimum = new double[NUM_STREETS][NUM_POS];
	private static final double[][] MDFCallOptimum = new double[NUM_STREETS][NUM_POS];
	
	
	private static boolean optimumRangesGUI = false;
	private static final Object[][] dataActualOptimum = {

			{ "Preflop ", "", "", "", "", "", "", "","" },
			{ "Fold Actual       ", "", "", "", "", "", "", "","" },
			{ "Fold Optimum  ", "", "", "", "", "", "", "","" },
			{ "Fold Difference ", "", "", "", "", "", "", "","" },
			{ "Check Actual       ", "", "", "", "", "", "", "","" },
			{ "Check Optimum  ", "", "", "", "", "", "", "","" },
			{ "Check Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 2 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 2 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 2 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 2 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 2 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 2 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet3 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 3 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 3 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 3 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 3 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 3 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 4 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 4 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 4 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 4 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 4 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 4 Difference ", "", "", "", "", "", "", "","" },
			{ "All in Actual", "", "", "", "", "", "", "","" },
			{ "All in Optimum  ", "", "", "", "", "", "", "","" },
			{ "All in Difference ", "", "", "", "", "", "", "","" },
			{ "Call All in Actual", "", "", "", "", "", "", "","" },
			{ "Call All in Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call All In Difference ", "", "", "", "", "", "", "","" },
			{ "cBet Actual", "", "", "", "", "", "", "","" },
			{ "CBet Optimum  ", "", "", "", "", "", "", "","" },
			{ "cBet Difference ", "", "", "", "", "", "", "","" },
			{ "", "", "", "", "", "", "", "","" }, { "Flop ", "", "", "", "", "", "", "","" },
			{ "Fold Actual       ", "", "", "", "", "", "", "","" },
			{ "Fold Optimum  ", "", "", "", "", "", "", "","" },
			{ "Fold Difference ", "", "", "", "", "", "", "","" },
			{ "Check Actual       ", "", "", "", "", "", "", "","" },
			{ "Check Optimum  ", "", "", "", "", "", "", "","" },
			{ "Check Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 2 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 2 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 2 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 2 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 2 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 2 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet3 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 3 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 3 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 3 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 3 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 3 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 4 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 4 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 4 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 4 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 4 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 4 Difference ", "", "", "", "", "", "", "","" },
			{ "All in Actual", "", "", "", "", "", "", "","" },
			{ "All in Optimum  ", "", "", "", "", "", "", "","" },
			{ "All in Difference ", "", "", "", "", "", "", "","" },
			{ "Call All in Actual", "", "", "", "", "", "", "","" },
			{ "Call All in Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call All In Difference ", "", "", "", "", "", "", "","" },
			{ "cBet Actual", "", "", "", "", "", "", "","" },
			{ "CBet Optimum  ", "", "", "", "", "", "", "","" },
			{ "cBet Difference ", "", "", "", "", "", "", "","" },
			{ "", "", "", "", "", "", "", "","" }, { "Turn ", "", "", "", "", "", "", "","" },
			{ "Fold Actual       ", "", "", "", "", "", "", "","" },
			{ "Fold Optimum  ", "", "", "", "", "", "", "","" },
			{ "Fold Difference ", "", "", "", "", "", "", "","" },
			{ "Check Actual       ", "", "", "", "", "", "", "","" },
			{ "Check Optimum  ", "", "", "", "", "", "", "","" },
			{ "Check Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 2 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 2 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 2 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 2 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 2 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 2 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet3 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 3 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 3 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 3 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 3 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 3 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 4 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 4 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 4 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 4 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 4 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 4 Difference ", "", "", "", "", "", "", "","" },
			{ "All in Actual", "", "", "", "", "", "", "","" },
			{ "All in Optimum  ", "", "", "", "", "", "", "","" },
			{ "All in Difference ", "", "", "", "", "", "", "","" },
			{ "Call All in Actual", "", "", "", "", "", "", "","" },
			{ "Call All in Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call All In Difference ", "", "", "", "", "", "", "","" },
			{ "cBet Actual", "", "", "", "", "", "", "","" },
			{ "CBet Optimum  ", "", "", "", "", "", "", "","" },
			{ "cBet Difference ", "", "", "", "", "", "", "","" },
			{ "", "", "", "", "", "", "", "","" }, { "River ", "", "", "", "", "", "", "","" },
			{ "Fold Actual       ", "", "", "", "", "", "", "","" },
			{ "Fold Optimum  ", "", "", "", "", "", "", "","" },
			{ "Fold Difference ", "", "", "", "", "", "", "","" },
			{ "Check Actual       ", "", "", "", "", "", "", "","" },
			{ "Check Optimum  ", "", "", "", "", "", "", "","" },
			{ "Check Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 1 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 2 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 2 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 2 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 2 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 2 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 2 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet3 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 3 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 3 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 3 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 3 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 3 Difference ", "", "", "", "", "", "", "","" },
			{ "Bet 4 Actual", "", "", "", "", "", "", "","" },
			{ "Bet 4 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Bet 4 Difference ", "", "", "", "", "", "", "","" },
			{ "Call Bet 4 Actual", "", "", "", "", "", "", "","" },
			{ "Call Bet 4 Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call Bet 4 Difference ", "", "", "", "", "", "", "","" },
			{ "All in Actual", "", "", "", "", "", "", "","" },
			{ "All in Optimum  ", "", "", "", "", "", "", "","" },
			{ "All in Difference ", "", "", "", "", "", "", "","" },
			{ "Call All in Actual", "", "", "", "", "", "", "","" },
			{ "Call All in Optimum  ", "", "", "", "", "", "", "","" },
			{ "Call All In Difference ", "", "", "", "", "", "", "","" },
			{ "cBet Actual", "", "", "", "", "", "", "","" },
			{ "CBet Optimum  ", "", "", "", "", "", "", "","" },
			{ "cBet Difference ", "", "", "", "", "", "", "","" },
			{ "", "", "", "", "", "", "", "","" }, { "", "", "", "", "", "", "", "","" },
			{ "", "", "", "", "", "", "", "","" }, { "", "", "", "", "", "", "", "","" },
			{ "", "", "", "", "", "", "", "","" }, { "", "", "", "", "", "", "", "","" },
			{ "", "", "", "", "", "", "", "","" }, { "", "", "", "", "", "", "", "","" },
			{ "MDF Actual       ", "", "", "", "", "", "", "","" },
			{ "MDF Optimum  ", "", "", "", "", "", "", "","" },
			{ "MDF Difference ", "", "", "", "", "", "", "","" },
			{ "MDF Bet 2 Actual      ", "", "", "", "", "", "", "","" },
			{ "MDF Bet 2 Optimum ", "", "", "", "", "", "", "","" },
			{ "MDF Bet2 Difference", "", "", "", "", "", "", "","" },
			{ "MDF Call Actual      ", "", "", "", "", "", "", "","" },
			{ "MDF Call Optimum ", "", "", "", "", "", "", "","" },
			{ "MDF Call Difference", "", "", "", "", "", "", "","" },
			{ "", "", "", "", "", "", "", "","" }, { "", "", "", "", "", "", "", "","" },
			{ "", "", "", "", "", "", "", "","" }, { "", "", "", "", "", "", "", "","" },
			{ "", "", "", "", "", "", "", "","" }, { "", "", "", "", "", "", "", "","" },
			{ "", "", "", "", "", "", "", "","" }, { "", "", "", "", "", "", "", "","" },
			{ "", "", "", "", "", "", "", "","" }, };
	private static final Object[] columnsMDF  = { "Data", "Value", "Description" };
	private static final Object[] columnsClassification = { "Variable ", "Value" };
	private static final Object[] columnsStreet = { "Street ", "Preflop", "Flop", "Turn", "River", "Showdown",
			"Summary" };

 
	private static boolean MDFDataPosGUI = false;
 
 
	

 
	private static final Object[][] dataMDFPos = {
			{"Preflop","",""},
			{"gamesPos","",""},
			{" mdfPos","",""},
			{"continuationPos","",""},
			{"continuationBet1Pos","",""},
			{"continuationBet2Pos","",""},
			{"continuationCallBet1Pos","",""},
			{"continuationCallBet2Pos","",""},
			{"continuationCheckPos","",""},
			{"continuationFoldToBet1Pos","",""},
			{"continuationFoldToBet2Pos","",""},
			{" foldFreqPos","",""},
			{" contFreqPos","",""},
			{" foldFreq","",""},
			{" contFreq","",""},
			{"","",""},
			{"Flop","",""},
			{"gamesPos","",""},
			{" mdfPos","",""},
			{"continuationPos","",""},
			{"continuationBet1Pos","",""},
			{"continuationBet2Pos","",""},
			{"continuationCallBet1Pos","",""},
			{"continuationCallBet2Pos","",""},
			{"continuationCheckPos","",""},
			{"continuationFoldToBet1Pos","",""},
			{"continuationFoldToBet2Pos","",""},
			{" foldFreqPos","",""},
			{" contFreqPos","",""},
			{" foldFreq","",""},
			{" contFreq","",""},
			{"","",""},
			{"Turn","",""},
			{"gamesPos","",""},
			{" mdfPos","",""},
			{"continuationPos","",""},
			{"continuationBet1Pos","",""},
			{"continuationBet2Pos","",""},
			{"continuationCallBet1Pos","",""},
			{"continuationCallBet2Pos","",""},
			{"continuationCheckPos","",""},
			{"continuationFoldToBet1Pos","",""},
			{"continuationFoldToBet2Pos","",""},
			{" foldFreqPos","",""},
			{" contFreqPos","",""},
			{" foldFreq","",""},
			{" contFreq","",""},
			{"","",""},
			{"River","",""},
			{"gamesPos","",""},
			{" mdfPos","",""},
			{"continuationPos","",""},
			{"continuationBet1Pos","",""},
			{"continuationBet2Pos","",""},
			{"continuationCallBet1Pos","",""},
			{"continuationCallBet2Pos","",""},
			{"continuationCheckPos","",""},
			{"continuationFoldToBet1Pos","",""},
			{"continuationFoldToBet2Pos","",""},
			{" foldFreqPos","",""},
			{" contFreqPos","",""},
			{" foldFreq","",""},
			{" contFreq","",""},
			{"","",""},
			{ "","",""} };
			private static final Object[][] dataMDFRp = {
			{"Preflop","",""},
			{"gamesRp","",""},
			{" mdfRp","",""},
			{"continuationRp","",""},
			{"continuationBet1Rp","",""},
			{"continuationBet2Rp","",""},
			{"continuationCallBet1Rp","",""},
			{"continuationCallBet2Rp","",""},
			{"continuationCheckRp","",""},
			{"continuationFoldToBet1Rp","",""},
			{"continuationFoldToBet2Rp","",""},
			{"foldFreqRp","",""},
			{"contFreqRp","",""},
			{"","",""},
			{"Flop","",""},
			{"gamesRp","",""},
			{" mdfRp","",""},
			{"continuationRp","",""},
			{"continuationBet1Rp","",""},
			{"continuationBet2Rp","",""},
			{"continuationCallBet1Rp","",""},
			{"continuationCallBet2Rp","",""},
			{"continuationCheckRp","",""},
			{"continuationFoldToBet1Rp","",""},
			{"continuationFoldToBet2Rp","",""},
			{"foldFreqRp","",""},
			{"contFreqRp","",""},
			{"","",""},
			{"Turn","",""},
			{"gamesRp","",""},
			{" mdfRp","",""},
			{"continuationRp","",""},
			{"continuationBet1Rp","",""},
			{"continuationBet2Rp","",""},
			{"continuationCallBet1Rp","",""},
			{"continuationCallBet2Rp","",""},
			{"continuationCheckRp","",""},
			{"continuationFoldToBet1Rp","",""},
			{"continuationFoldToBet2Rp","",""},
			{"foldFreqRp","",""},
			{"contFreqRp","",""},
			{"","",""},
			{"River","",""},
			{"gamesRp","",""},
			{" mdfRp","",""},
			{"continuationRp","",""},
			{"continuationBet1Rp","",""},
			{"continuationBet2Rp","",""},
			{"continuationCallBet1Rp","",""},
			{"continuationCallBet2Rp","",""},
			{"continuationCheckRp","",""},
			{"continuationFoldToBet1Rp","",""},
			{"continuationFoldToBet2Rp","",""},
			{"foldFreqRp","",""},
			{"contFreqRp","",""},
			{"","",""},
			{ "","","" } };
 
	private static boolean playerClassificationGUI = false;
	 
	private static final Object[][] dataClassification = { { "preflopVPIP","" }, { "preflopFold","" },
			{ "preflopRaise","" },
			// {"preflopCall",""},
			{ "preflopCheck","" }, { "preflopAggressionFactor","" }, { "preflopAggressionFrequency","" },
			// {"preflopBet3",""},
			// {"preflopCallBet3",""},

			{ "","" }, { "","" }, { "","" }, { "","" }, { "","" }, { "","" }, { "","" } };
	 
	private static boolean playerClassification3GUI = false;
	 
	private static final Object[][] dataClassification3 = { { "gamesPlayed","" }, { "playerType","" },
			{ "tight","" }, { "loose","" }, { "regular","" }, { "passive","" }, { "aggressive","" },
			{ "showdownCount","" },

			{ "preflopRaiseCount","" }, { "preflopCallCount","" }, { "preflopFoldCount","" },
			{ "preflopCheckCount","" }, { "flopRaiseCount","" }, { "flopCallCount","" }, { "flopFoldCount","" },
			{ "flopCheckCount","" }, { "turnRaiseCount","" }, { "turnCallCount","" }, { "turnFoldCount","" },
			{ "turnCheckCount","" }, { "riverRaiseCount","" }, { "riverCallCount","" }, { "riverFoldCount","" },
			{ "riverCheckCount","" }, { "showdownCount","" }, { "stack","" }, { "averageStack","" },

			{ "wonShowdown$","" }, { "wonShowdownCount","" }, { "won$","" }, { "wonCount","" },
			{ "raiseSize$","" },
			// { "raiseCount","" },
			{ "pot$ ","" }, { "rake$","" }, { "","" }, { "","" }, { "pot$","" }, { "pot$","" }, { "pot$","" },
			{ "pot$","" }, { "pot$","" }, { "pot$","" }, { "","" }, { "","" }, { "rakeSeat$","" },
			{ "rakeSeat$","" }, { "rakeSeat$","" }, { "rakeSeat$","" }, { "rakeSeat$","" }, { "rakeSeat$","" },
			{ "","" }, { "","" }, { "","" }, { "","" }, { "","" }, { "","" }, { "","" } };
	 
	private static boolean playerClassification2GUI = false;
 
	private static final Object[][] dataClassification2 = { { "preflopVPIP","" }, { "callPercentage","" },
			{ "raisePercentage ","" }, { "foldPercentage","" }, { "checkPercentage ","" },
			{ "aggressionFrequency ","" }, { "aggressionFactor ","" }, { "","" },

			{ "","" }, { "tight","" }, { "loose","" }, { "regular","" }, { "passive","" }, { "aggressive","" },
			{ "","" }, { " showdownCount","" }, { "","" }, { " stack$","" }, { "averageStack$ ","" }, { "","" },

			{ "","" }, { "wonShowdown$ ","" }, { "wonShowdownCount ","" }, { "won$ ","" }, { "raiseSize$ ","" },
			{ "raiseCount ","" }, { ".pot$ ","" }, { "wonCount ","" }, { "rake$ ","" }, { "","" },

			{ "summaryFoldCount","" }, { "summaryFoldCountPreflop","" }, { "summaryFoldCountFlop","" },
			{ " summaryFoldCountTurn","" }, { " summaryFoldCountRiver","" }, { "summaryCollectedCount","" },
			{ "","" }, { "","" }, { "","" }, { "","" }, { "","" }, { "","" } };
 
	private static boolean playerDataCountsGUI = false;
 
	private static final Object[][] dataPlayerCounts = { { "PFRCount  ","" }, { "preflop3BetCount ","" },
			{ "WTSDCount ","" }, { "W$SD_WSDCount ","" }, { "WWSFCount ","" },
			{ "foldTo3BetAfterRaisingCount ","" }, { "preflopSqueezeCount  ","" }, { "flopCBetCount ","" },
			{ "flopFoldToCBetCount  ","" }, { "barrellFlopTurnCount ","" }, { "barrellFlopTurnRiverCount ","" },
			{ "barrellTurnRiverCount ","" }, { "","" }, { "","" }, { "","" }, { "","" }, { "","" }, { "","" },
			{ "","" }, { "","" }, { "","" }, { "","" }, { "","" }, { "","" }, { "","" }, { "","" }, { "","" },
			{ "","" } };

// @formatter:on

	static void optimumRangesRp(PlayerMDF pmdf) {
		if (!optimumRangesGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 18);
			final var frame = new JFrame(" Optimum vs. Acual and Difference by Relative Position");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(1000, 100);
			frame.setPreferredSize(new Dimension(500, 500));
			final var table = new JTable(dataActualOptimum, columnsMDF);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(200);
			// Create a custom cell renderer to set the font to bold 22.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 22));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			optimumRangesGUI = true;
		}

		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_RP; ++j) {
				/// dataActualOptimum[row++][j + 1] =
				/// Format.formatPer(allPlayerClassification.foldPerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(foldOptimum[i][j]);
				// dataActualOptimum[row++][j + 1] = Format.formatPer(foldOptimum[i][j] -
				// allPlayerClassification.foldPerRp[i][j]);
				// dataActualOptimum[row++][j + 1] =
				// Format.formatPer(allPlayerClassification.checkPerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(checkOptimum[i][j]);
				// dataActualOptimum[row++][j + 1] = Format.formatPer(checkOptimum[i][j] -
				// allPlayerClassification.checkPerRp[i][j]);

//				dataActualOptimum[row++][j + 1] = Format.formatPer(allPlayerClassification.bet1PerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(bet1Optimum[i][j]);
//				dataActualOptimum[row++][j + 1] = Format.formatPer(bet1Optimum[i][j] - allPlayerClassification.bet1PerRp[i][j]);
//				dataActualOptimum[row++][j + 1] = Format.formatPer(allPlayerClassification.callBet1PerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(callBet1Optimum[i][j]);
//				dataActualOptimum[row++][j + 1] = Format
//						.formatPer(callBet1Optimum[i][j] - allPlayerClassification.callBet1PerRp[i][j]);

//				dataActualOptimum[row++][j + 1] = Format.formatPer(allPlayerClassification.bet2PerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(bet2Optimum[i][j]);
//				dataActualOptimum[row++][j + 1] = Format.formatPer(bet2Optimum[i][j] - allPlayerClassification.bet2PerRp[i][j]);
//				dataActualOptimum[row++][j + 1] = Format.formatPer(allPlayerClassification.callBet2PerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(callBet2Optimum[i][j]);
//				dataActualOptimum[row++][j + 1] = Format
//						.formatPer(callBet2Optimum[i][j] - allPlayerClassification.callBet2PerRp[i][j]);

//				dataActualOptimum[row++][j + 1] = Format.formatPer(allPlayerClassification.bet3PerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(bet3Optimum[i][j]);
//				dataActualOptimum[row++][j + 1] = Format.formatPer(bet3Optimum[i][j] - allPlayerClassification.bet3PerRp[i][j]);
//				dataActualOptimum[row++][j + 1] = Format.formatPer(allPlayerClassification.callBet3PerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(callBet3Optimum[i][j]);
//				dataActualOptimum[row++][j + 1] = Format
//						.formatPer(callBet3Optimum[i][j] - allPlayerClassification.callBet3PerRp[i][j]);

//				dataActualOptimum[row++][j + 1] = Format.formatPer(allPlayerClassification.bet4PerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(bet4Optimum[i][j]);
////				dataActualOptimum[row++][j + 1] = Format.formatPer(bet4Optimum[i][j] - allPlayerClassification.bet4PerRp[i][j]);
//				dataActualOptimum[row++][j + 1] = Format.formatPer(allPlayerClassification.callBet4PerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(callBet4Optimum[i][j]);
//				dataActualOptimum[row++][j + 1] = Format
//						.formatPer(callBet4Optimum[i][j] - allPlayerClassification.callBet4PerRp[i][j]);

//				dataActualOptimum[row++][j + 1] = Format.formatPer(allPlayerClassification.allinPerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(allinOptimum[i][j]);
//				dataActualOptimum[row++][j + 1] = Format.formatPer(allinOptimum[i][j] - allPlayerClassification.allinPerRp[i][j]);
//				dataActualOptimum[row++][j + 1] = Format.formatPer(allPlayerClassification.callAllinPerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(callAllinOptimum[i][j]);
//				dataActualOptimum[row++][j + 1] = Format
//						.formatPer(callAllinOptimum[i][j] - allPlayerClassification.callAllinPerRp[i][j]);

//				dataActualOptimum[row++][j + 1] = Format.formatPer(allPlayerClassification.cBetPerRp[i][j]);
				dataActualOptimum[row++][j + 1] = Format.formatPer(cBetOptimum[i][j]);
//				dataActualOptimum[row++][j + 1] = Format.formatPer(cBetOptimum[i]pmdf - allPlayerClassification.cBetPerRp[i][j]);

				// TODO
				// dataActualOptimum[9][i + 1] =
				// Format.formatPer(allPlayerClassification.MDFPerRp[i] );
				// dataActualOptimum[10][i + 1] =Format.formatPer(MDFOptimum[i]);
				// dataActualOptimum[11][i + 1] =Format.formatPer(MDFOptimum[i] -
				// allPlayerClassification.MDFPerRp[i]);
				// dataActualOptimum[9][i + 1] =
				// Format.formatPer(allPlayerClassification.MDFBet2PerRp[i] );
				// dataActualOptimum[10][i + 1] =Format.formatPer(MDFBet2Optimum[i]);
				// dataActualOptimum[11][i + 1] =Format.formatPer(MDFBet2Optimum[i] -
				// allPlayerClassification.MDFBet2PerRp[i]);
				// dataActualOptimum[9][i + 1] =
				// Format.formatPer(allPlayerClassification.MDFCallPerRp[i] );
				// dataActualOptimum[10][i + 1] =Format.formatPer(MDFCallOptimum[i]);
				// dataActualOptimum[11][i + 1] =Format.formatPer(MDFCalklOptimum[i]
				// - allPlayerClassification.MDFCallPerRp[i]);
			}
		}
	}

	static void optimumRangesTurnRp() {
		// TODO
	}

	static void optimumRangesRiverRp() {
		// TODO
	}

	/*-******************************************************************************
	*
	*******************************************************************************/
	static void mdfDataPos(PlayerMDF pmdf) {
		if (!MDFDataPosGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 18);
			final var frame = new JFrame("MDF Positions");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(90, 90);
			frame.setPreferredSize(new Dimension(500, 1200));

			final var table = new JTable(dataMDFPos, columnsMDF);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 22.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 22));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			MDFDataPosGUI = true;
		}
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			dataMDFPos[row++][1] = Format.format(pmdf.gamesPos[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.mdfPos[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.continuationPos[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.continuationBet1Pos[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.continuationBet2Pos[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.continuationCallBet1Pos[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.continuationCallBet2Pos[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.continuationCheckPos[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.continuationFoldToBet1Pos[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.continuationFoldToBet2Pos[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.foldFreqPos[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.contFreqPos[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.foldFreq[i]);
			dataMDFPos[row++][1] = Format.format(pmdf.contFreq[i]);
			row += 2;
		}
	}

	private static boolean MDFDataRpGUI = false;

	/*-******************************************************************************
	*
	*******************************************************************************/
	static void mdfDataRp(PlayerMDF pmdf) {
		if (!MDFDataRpGUI) {
			MDFDataRpGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 18);
			final var frame = new JFrame("MDF Relative Positions");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(130, 139);
			frame.setPreferredSize(new Dimension(500, 1200));

			final var table = new JTable(dataMDFRp, columnsMDF);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 22.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 22));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			MDFDataPosGUI = true;
		}
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			dataMDFRp[row++][1] = Format.format(pmdf.gamesRp[i]);
			dataMDFRp[row++][1] = Format.format(pmdf.mdfRp[i]);
			dataMDFRp[row++][1] = Format.format(pmdf.continuationRp[i]);
			dataMDFRp[row++][1] = Format.format(pmdf.continuationBet1Rp[i]);
			dataMDFRp[row++][1] = Format.format(pmdf.continuationBet2Rp[i]);
			dataMDFRp[row++][1] = Format.format(pmdf.continuationCallBet1Rp[i]);
			dataMDFRp[row++][1] = Format.format(pmdf.continuationCallBet2Rp[i]);
			dataMDFRp[row++][1] = Format.format(pmdf.continuationCheckRp[i]);
			dataMDFRp[row++][1] = Format.format(pmdf.continuationFoldToBet1Rp[i]);
			dataMDFRp[row++][1] = Format.format(pmdf.continuationFoldToBet2Rp[i]);
			dataMDFRp[row++][1] = Format.format(pmdf.foldFreqRp[i]);
			dataMDFRp[row++][1] = Format.format(pmdf.contFreqRp[i]);
			row += 2;

		}
	}

	/*-******************************************************************************
	*
	*******************************************************************************/
	static void playerClassification(PlayerMDF pmdf) {
		if (!playerClassificationGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 18);
			final var frame = new JFrame("Player Classification Data ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(500, 90);
			frame.setPreferredSize(new Dimension(600, 100));

			final var table = new JTable(dataClassification, columnsStreet);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 22.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 22));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			playerClassificationGUI = true;
		}
		int row = 0;
		for (int i = 0; i < NUM_STREETS; ++i) {
			/*-
			dataClassification[row++][1] = Format.format(pmdf.handsPlayed);
			dataClassification[row++][1] = Format.format(pmdf.playerType);
			dataClassification[row++][1] = Format.format(pmdf.VPIP[i]);
			dataClassification[row++][1] =			Format.format(pmdf.foldCount[i]);
			dataClassification[row++][1] =			Format.format(pmdf.raiseCount[i]);
			dataClassification[row++][1] =			Format.format(pmdf.callCount[i]);
			dataClassification[row++][1] =			Format.format(pmdf.checkCount[i]);
			dataClassification[row++][1] = Format.format(pmdf.aggressionFactor[i]);
			dataClassification[row++][1] = Format.format(pmdf.aggressionFrequency[i]);
			dataClassification[row++][1] =			Format.format(pmdf.bet3[i]);
			dataClassification[row++][1] =			Format.format(pmdf.callBet3[i]);
			*/
		}

	}

	/*-******************************************************************************
	*
	*******************************************************************************/
	static void playerClassification3(PlayerMDF pmdf) {
		if (!playerClassification3GUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 18);
			final var frame = new JFrame("Player Classification Data ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(600, 190);
			frame.setPreferredSize(new Dimension(500, 500));

			final var table = new JTable(dataClassification3, columnsClassification);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 22.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 22));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			playerClassification3GUI = true;
		}
		int row = 0;
		dataClassification3[row++][1] = Format.format(pmdf.handsPlayed);
// TODO		dataClassification3[row++][1] = Format.format(pmdf.playerType);
// TODP		dataClassification3[row++][1] = Format.format(play.showdownCount);

// TODO		dataClassification3[row++][1] = Format.format(pmdf.showdownCount);
		/*- TODO
				dataClassification3[row++][1] = Format.format(play.stack$);
				dataClassification3[row++][1] = Format.format(play.averageStack$);
		
				dataClassification3[row++][1] = Format.format(play.wonShowdown$);
				dataClassification3[row++][1] = Format.format(play.wonShowdownCount);
				dataClassification3[row++][1] = Format.format(play.won$);
				dataClassification3[row++][1] = Format.format(play.raiseSizeBD$);
				// dataClassification3[row++][1] =
				// Format.format(pmdf.raiseCount);
				dataClassification3[row++][1] = Format.format(play.potBD$);
				dataClassification3[row++][1] = Format.format(play.wonCount);
				dataClassification3[row++][1] = Format.format(play.rakeBD$);
		
				row += 2;
				for (int i = 0; i < 6; ++i) {
					dataClassification3[row++][1] = Format.format(play.potSeatBD$[i]);
				}
		*/
		row += 2;
		for (int i = 0; i < 6; ++i) {
// TODO			dataClassification3[row++][1] = Format.format(play.rakeSeatBD$[i]);
		}
	}

	/*-******************************************************************************
	*
	*******************************************************************************/
	static void playerClassification2(PlayerMDF pmdf) {
		if (!playerClassification2GUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 18);
			final var frame = new JFrame("Player Classification Data ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(700, 200);
			frame.setPreferredSize(new Dimension(500, 500));

			final var table = new JTable(dataClassification2, columnsStreet);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 22.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 22));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			playerClassification2GUI = true;
		}
		int row = 0;
		for (int i = 0; i < NUM_STREETS; ++i) {
// TODO			dataClassification2[row++][1] = Format.format(pmdf.VPIP[i]);
			// dataClassification2[row++][1] =
			// Format.format(pmdf.callPercentag[i]);
			// dataClassification2[row++][1] = Format.format(pmdf.raisePercentage[i]);
			// dataClassification2[row++][1] = Format.format(pmdf.foldPercentage[i]);
			// dataClassification2[row++][1] = Format.format(pmdf.checkPercentage[i]);
//			dataClassification2[row++][1] = Format.format(pmdf.aggressionFrequency[i]);
//			dataClassification2[row++][1] = Format.format(pmdf.aggressionFactor[i]);
			++row;
			// dataClassification2[row++][1] = Format.format(pmdf.summaryFoldCount[i]);
			// dataClassification2[row++][1] = Format.format(pmdf.summaryCollectedCount[i]);
		}
		++row;
		// dataClassification2[row++][1] = Format.format(pmdf.showdownCount);
		++row;
		/*- TODO
		dataClassification2[row++][1] = Format.format(play.stack$);
		dataClassification2[row++][1] = Format.format(play.averageStack$);
		
		++row;
		dataClassification2[row++][1] = Format.format(play.wonShowdown$);
		dataClassification2[row++][1] = Format.format(play.wonShowdownCount);
		dataClassification2[row++][1] = Format.format(play.won$);
		dataClassification2[row++][1] = Format.format(play.raiseSizeBD$);
		// dataClassification2[row++][1] =
		// Format.format(play.raiseCount);
		dataClassification2[row++][1] = Format.format(play.potBD$);
		dataClassification2[row++][1] = Format.format(play.wonCount);
		dataClassification2[row++][1] = Format.format(play.rakeBD$);
		*/
	}

	/*-******************************************************************************
	*
	*******************************************************************************/
	static void playerDataCounts(PlayerMDF pmdf) {
		if (!playerDataCountsGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 18);
			final var frame = new JFrame("Player Data Counts");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(1000, 90);
			frame.setPreferredSize(new Dimension(500, 500));

			final var table = new JTable(dataPlayerCounts, columnsStreet);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 22.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 22));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			playerDataCountsGUI = true;
		}
		int row = 0;
		for (int i = 0; i < NUM_STREETS; ++i) {
			// dataPlayerCounts[row+i + i] = Format.format(play.PFRCount[i]);
			// dataPlayerCounts[row++][i +i] =
			// Format.format(play.preflop3BetCount);
			// dataPlayerCounts[row++][i + i] = Format.format(play.WTSDCount[i]);
			// dataPlayerCounts[row++][i + i] = Format.format(play.W$SD_WSDCount[i]);
			// dataPlayerCounts[row++][i + i] = Format.format(play.WWSFCount[i]);
			// dataPlayerCounts[row++][i + i] =
			// Format.format(play.foldTo3BetAfterRaisingCount[i]);
			// dataPlayerCounts[row++][i +i] =
			// Format.format(play.squeezeCount[i]);
			// dataPlayerCounts[row++][i +i] =
			// Format.format(play.cBetCount[i]);
			// dataPlayerCounts[row++][i + i] = Format.format(play.foldToCBetCount[i]);

		}
	}
}
