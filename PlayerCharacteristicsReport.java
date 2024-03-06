package holdemhandhistory;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*-******************************************************************************
 * This class does a bunch of reports using data collected by GUIAnalysis.
 * The data was 
 * @author PEAK_
 ****************************************************************************** */
public class PlayerCharacteristicsReport implements Constants {

	private PlayerCharacteristicsReport() {
		throw new IllegalStateException("Utility class");
	}

	/*- 
	* Information related to Preflop play
	*/
	private static boolean playerStatsPreflopGUI = false;

	// @formatter:off


	private static final Object[] columnsStreets = { "Street", "Preflop", "Flop", "Turn", "River" };
private static final Object[][] dataStreets = { 
		{" winRateBB100Streets ","","","","",""},
		{"inPositionFrequencyStreets","","","",""},
		{"outOfPositionFrequency","","","",""},
		{"inPositionFrequencyStreets","","","",""},
		{"outOfPositionFrequency","","","",""},
		{"vpipStreet","","","",""}, // ??? TODO
		{"pfrStreet","","","",""}, // TODO????
		{"aggressionFrequencyStreet","","","",""},
		{"aggressionFactorStreet","","","",""},
		{"winRateStreets","","","",""},
		{"bet3tFreqStreets","","","",""}, // 3-Bet
		{"W$SD_WSD","","","",""}, // Won Money At Showdown
		{"WWSF","","","",""}, // Won When Saw
		{"WTSD","","","",""},
		{"foldTo3BetAfterRaising","","","",""}, // Fold to 3-Bet After R
		{"Squeeze","","","",""}, // Squeeze
		{"Aggression","","","",""}, // Squeeze
		{"CBET","","","",""}, // C-Bet
		{"CBET_IP","","","",""}, // C-Bet in position
		{"CBET_OOP","","","",""}, // C-Bet out of position
		{"CBET_Fold","","","",""}, // C-Bet folded to C-Bet
		{"CBET_Call","","","",""}, // C-Bet called
		{"CBET_Reraise","","","",""}, // C-Bet reraised
		{"CheckCall_IP","","","",""}, // C-Bet called in position
		{"CheckCall_OOP","","","",""}, // C-Bet called out of position
		{"DonkBet","","","",""}, //
		{"DonkBetCall","","","",""}, //
		{"foldToCBET","","","",""}, // Fold to C-Bet
		{"coldCallRise","","","",""}, //
		{"RFI","","","",""}, // Raise first in
		{"callPercentage","","","",""},
		{"raisePercentage","","","",""},
		{"betPercentage","","","",""}, // TODO
		{"foldPercentage","","","",""},
		{"checkPercentage","","","",""} ,
		{ "", "", "", "", "" } 
	
		};

	 	private static final Object[] columnsStats = { "Stat      ", "SB", "BB", "UTG", "MP", "CO", "BU" ,"Combined"};
		private static final Object[][] dataStats  = { 
				{ "vpipStreetPos ", "", "","","","","",""},
				{ "aggPctStreetPos ", "", "","","","","",""},
				{ "afStreetPos  ", "", "","","","","",""},
				{ "bet3FreqStreetPos", "", "","","","","",""},
				{ "WTSD            ", "", "","","","","",""},
				{ "3 Bet Freq    ", "", "","","","","",""},
				{ "winRateStreetPos ", "", "","","","","",""},
				{ "------", "", "","","","","",""},};

 	private static final Object[] columnsBasicStats= { "Stat      ",  "Value"};
	private static final Object[][] dataBasicStats = { 
			{ "VPIP ", "", "","","","","",""},
			{ "PFR ", "", "","","","","",""},
			{ "AggPct    ", "", "","","","","",""},
			{ "AF     ", "", "","","","","",""},
			{ "WinRate    ", "", "","","","","",""},
			{ "3 Bet Freq    ", "", "","","","","",""},
			{ "WTSD            ", "", "","","","","",""},
			{ "WYSD_WSD  ", "", "","","","","",""},
			{ "WWSF    ", "", "","","","","",""},
			{ "FoldTo3Bet    ", "", "","","","","",""},
			{ "Squeeze    ", "", "","","","","",""},
			{ "C_Bet    ", "", "","","","","",""},
			{ "Fold3BetAfterRaising     ", "", "","","","","",""},
			{ " FoldToC_Bet            ", "", "","","","","",""},
			{ "Player Types     ", "", "","","","","",""},
			{ "Average     ", "", "","","","","",""},
			{ "Fish     ", "", "","","","","",""},
			{ "NIT     ", "", "","","","","",""},
			{ "LAG     ", "", "","","","","",""},
			{ "TAG     ", "", "","","","","",""},
			{ " REG             ", "", "","","","","",""},
			{ "Winner     ", "", "","","","","",""},
			{ "Looser    ", "", "","","","","",""},
			{ "Net Won/lost    ", "", "","","","","",""},
			{ "Cold Call     ", "", "","","","","",""},
			{ "4 Bet    ", "", "","","","","",""},
			{ "Squeeze     ", "", "","","","","",""},
			{ "MinRaise First in    ", "", "","","","","",""},
			{ "4 Bet Range    ", "", "","","","","",""},
			{ "RFI    ", "", "", "","","","","",""},
			{ "Steal     ", "", "","","","","",""},
			{ "Fold to Steal     ", "", "","","","","",""},
			{ "Call Steal     ", "", "","","","","",""},
			{ "ReSteal    ", "", "","","","","",""},
			{ "Fold to Steal     ", "", "","","","","",""},
			{ "Open MinRaise     ", "", "","","","","",""},
				{ "------", "", "","","","","",""},};

	private static boolean playerStatsFlopGUI = false;
	/*- 
	*	For all orbits - Actions
 	*/
	private static final Object[] columnsStatsFlop= { "Stat      ", "SB", "BB", "UTG", "MP", "CO", "BU" };
	private static final Object[][] dataStatsFlop = { 
				{ " Flop CBet			     ", "", "","","","",""},
			{ "Flop CBet IP		    ", "", "","","","",""},
			{ "Flop CBet OOP	    ", "", "","","","",""},
			{ "Flop CBet-Call	    ", "", "","","","",""},
			{ "Flop CBet-Reraise     ", "", "","","","",""},
			{ "Flop CBet-Fold		    ", "", "","","","",""},
			{ "Flop Call Donk Bet	    ", "", "","","","",""},
			{ "game.PlayerEvaluation     ", "", "","","","",""},
			{ "Flop Fold to Donk Bet		    ", "", "","","","",""},
			{ "Flop Call CB IP	    ", "", "","","","",""},
			{ "Flop Raise CB IP	     ", "", "","","","",""},
			{ "Flop Fold to CB IP	     ", "", "","","","",""},
			{ " Flop Check-Call CB OOP	    ", "", "","","","",""},
			{ "Flop Check-Raise CB OOP     ", "", "","","","",""},
			{ "Flop Check-Fold to CB OOP    ", "", "","","","",""},
			{ "Flop Donk Bet-Call	    ", "", "","","","",""},
			{ "Flop Donk Bet-ReRaise    ", "", "","","","",""},
			{ "Flop Donk Bet-Fold    ", "", "","","","",""},
			{ " Flop Donk Bet		    ", "", "","","","",""},
			{ " Flop Bet vs Missed CB		    ", "", "","","","",""},
			{ "  Flop Bet - Limped Pot Attempt to Steal IP	    ", "", "","","","",""},
			{ " Flop Agg%			    ", "", "","","","",""},
			{ " Flop CBet			    ", "", "","","","",""},
			{ " Flop Donk Bet		    ", "", "","","","",""},
			{ " Flop Donk Bet		    ", "", "","","","",""},
			{ "------", "", "","","","",""}};
	
	private static final Object[] columnsValues  = { "Name", "Value" , "Description"};

	private static final Object[][] dataValues= { 
			{"playerID","",""},	// playerID
			{"playerType","",""},	// playerType
			{"vpip ","",""},	// vpip
			{ "preflopVPIP","",""},	// preflop vpip		
			{"aggPct ","",""},	// Aggerssion Percentage
			{"af","",""},		// Aggression Factor
			{"pfr","",""},	// Prefer Flop Raise
			{"wtsdIfSawFlop","",""},		// Wtsd If Saw Flop
			{"wtsdIfSawTurn","",""},	 // Wtsd If Saw Turn
			{"wtsdIfSawRiver","",""},	// Wtsd If Saw River	
			{"winRateBB100","",""},		// Win Rate BB100
			{"preflopBet3Freq","",""},		//	 preflop bet 3 frequency
			{"preflopWinRate","",""}, // preflop win rate
		{" isolateBUPer","",""}, // isolate button percentage
		{" minBetBUPer","",""},// min bet button percentage
		{" stealBUPer","",""}, // steal button percentage
		{" squeezeBUPer","",""}, // squeeze button percentage
		{" minBetBBPer","",""}, // min bet button percentage
		{" callMinBetBBPer","",""}, // min bet button percentage
		{" steal3BetBBPer","",""}, // steal button percentage
		{" raisedBySBBBPer","",""}, // raised by button percentage
		{" steal3BetMinRaiseBBPer","",""}, // steal button percentage
		{" stealCallBBPer","",""}, // steal button percentage
		{" stealCallMinRaiseBBPer","",""}, // steal button percentage
		{" bet3MinSBPer","",""}, // steal button percentage
		{" raisedByBBSBPer","",""}, // raised by button percentage
		{" steal3BetSBPer","",""}, // steal button percentage
		{" stealCallSBPer","",""}, // steal button percentage
		{" stealCallMinRaiseSBPer","",""}, // steal button percentage
		{" foldedToSBPer","",""}, // folded to button percentage
		{" fourBetLimpCallHU","",""}, // 4Bet Limp-Call HU
		{" fourBetLimpReRaiseHU","",""}, // 4Bet Limp-ReRaise HU
		{" sB_Limp_Fold_HU","",""}, // SB Limp-Fold HU
		{" btnOpenLimp","",""}, // BTN Open Limp
		{" fourBetOpenLimp","",""}, // 4Bet Open Limp
		{" bB_Raise_vs_SB_Limp_UOP","",""}, // BB Raise vs SB Limp UOP
		{" flopCBet","",""}, // Flop CBet
		{" flopCBetIP","",""}, // Flop CBet IP
		{" flopCBetOOP","",""}, // Flop CBet OOP
		{" flopCBetCall","",""}, // Flop CBet-Call
		{" flopCBetReraise","",""}, // Flop CBet-Reraise
		{" flopCBetFold","",""}, // Flop CBet-Fold
		{" flopCallDonkBet","",""}, // Flop Call Donk Bet
		{" flopRaiseDonkBet","",""}, // Flop Raise Donk Bet
		{" flopFoldToDonkBet","",""}, // Flop Fold to Donk Bet
		{" skipFlopCBAndCheckCallOOP","",""}, // Skip Flop CB and Check-Call OOP
		{" skipFlopCBAndCheckRaiseOOP","",""}, // Skip Flop CB and Check-Raise OOP
		{" skipFlopCBAndCheckFoldOOP","",""}, // Skip Flop CB and Check-Fold OOP
		{" flopCallCBIP","",""}, // Flop Call CB IP
		{" flopRaiseCBIP","",""}, // Flop Raise CB IP
		{" flopFoldToCBIP","",""}, // Flop Fold to CB IP
		{" flopCheckCallCBOOP","",""}, // Flop Check-Call CB OOP
		{" flopCheckRaiseCBOOP","",""}, // Flop Check-Raise CB OOP
		{" flopCheckFoldToCBOOP","",""}, // Flop Check-Fold to CB OOP
		{" flopDonkBetCall","",""}, // Flop Donk Bet-Call
		{" flopDonkBetReRaise","",""}, // Flop Donk Bet-ReRaise
		{" flopDonkBetFold","",""}, // Flop Donk Bet-Fold
		{" flopDonkBet","",""}, // Flop Donk Bet
		{" flopBetVsMissedCB","",""}, // Flop Bet vs Missed CB
		{" flopBetLimpedPotAttemptToStealIP","",""}, // Flop Bet - Limped Pot Attempt to Steal IP
		{" postflopAggressionPercentage","",""}, // Postflop Aggression Percentage
		{" seenFlopOverall","",""}, // Seen Flop Overall
		{" turnCBet","",""}, // Turn CBet
		{" turnCBetIP","",""}, // Turn CBet IP
		{" turnCBetOOP","",""}, // Turn CBet OOP
		{" turnCBetCall","",""}, // Turn CBet-Call
		{" turnCBetReRaise","",""}, // Turn CBet-ReRaise
		{" delayedTurnCBet","",""}, // Delayed Turn CBet
		{" delayedTurnCBetIP","",""}, // Delayed Turn CBet IP
		{" delayedTurnCBetOOP","",""}, // Delayed Turn CBet OOP
		{" skipTurnCBAndCheckCallOOP","",""}, // Skip Turn CB and Check-Call OOP
		{" skipTurnCBAndCheckRaiseOOP","",""}, // Skip Turn CB and Check-Raise OOP
		{" skipTurnCBAndCheckFoldOOP","",""}, // Skip Turn CB and Check-Fold OOP
		{" turnCallCBIP","",""}, // Turn Call CB IP
		{" turnRaiseCBIP","",""}, // Turn Raise CB IP
		{" turnFoldToCBIP","",""}, // Turn Fold to CB IP
		{" turnCheckCallCBOOP","",""}, // Turn Check-Call CB OOP
		{" turnCheckRaiseCBOOP","",""}, // Turn Check-Raise CB OOP
		{" turnCheckFoldToCBOOP","",""}, // Turn Check-Fold to CB OOP
		{" turnDonkBet","",""}, // Turn Donk Bet
		{" turnBetVsMissedCB","",""}, // Turn Bet vs Missed CB
		{" riverCBet","",""}, // River CBet
		{" riverCBetIP","",""}, // River CBet IP
		{" riverCBetOOP","",""}, // River CBet OOP
		{" riverCBetCall","",""}, // River CBet-Call
		{" riverCBetReRaise","",""}, // River CBet-ReRaise
		{" riverCBetFold","",""}, // River CBet-Fold
		{" skipRiverCBAndCheckCallOOP","",""}, // Skip River CB and Check-Call OOP
		{" skipRiverCBAndCheckRaiseOOP","",""}, // Skip River CB and Check-Raise OOP
		{" skipRiverCBAndCheckFoldOOP","",""}, // Skip River CB and Check-Fold OOP
		{" riverCallCBIP","",""}, // River Call CB IP
		{" riverRaiseCBIP","",""}, // River Raise CB IP
		{" riverFoldToCBIP","",""}, // River Fold to CB IP
		{" riverCheckCallCBOOP","",""}, // River Check-Call CB OOP
		{" riverCheckRaiseCBOOP","",""}, // River Check-Raise CB OOP
		{" riverCheckFoldToCBOOP","",""}, // River Check-Fold to CB OOP
		{" riverDonkBet","",""}, // River Donk Bet
		{" riverBetVsMissedCB","",""}, // River Bet vs Missed CB
		{" wtsdWhenSawTurn","",""}, // WTSD% When Saw Turn
		{" reSteal3BetVsSteal","",""}, // ReSteal (3Bet vs Steal)
		{" threeBetReStealVsHero","",""}, // 3Bet ReSteal vs Hero
		{" sBFoldToSteal","",""}, // SB Fold to Steal
		{" fourBetCallSteal","",""}, // 4Bet Call Steal
		{" fourBetReSteal3BetVsSteal","",""}, // 4Bet ReSteal (3Bet vs Steal)
		{" foldToStealBB","",""}, // Fold to Steal BB
		{" bbCallSteal","",""}, // BB Call Steal
		{" bbReSteal3BetVsSteal","",""}, // BB 3Bet ReSteal vs Steal
		{" bb3BetReStealVsHero","",""}, // BB 3Bet ReSteal vs Hero
		{" foldToStealSB","",""}, // Fold to Steal SB
		{" foldToBTNSteal","",""}, // Fold to BTN Steal
		{" foldToSBSteal","",""}, // Fold to SB Steal
		{" foldSBVsStealFromButtonHero","",""}, // Fold SB vs Steal from Button Hero
		{" foldBBVsStealFromButtonHero","",""}, // Fold BB vs Steal from Button Hero
		{" foldBBVsStealFromSmallBlindHero","",""}, // Fold BB vs Steal from Small Blind Hero
		{" fourBetFoldToBTNSteal","",""}, // 4Bet Fold to BTN Steal
		{" sBLimpFoldHU","",""}, // SB Limp-Fold HU
		{" bBRaiseVsSBLimpUOP","",""}, // BB Raise vs SB Limp UOP
		{"  ","",""},
		{"  ","",""},
		{"  ","",""},
		{"  ","",""},
		{"  ","",""} 
	};
	
	private static final Object[] columnsPercentageRp = { "Action ", "First", "First HU", "Middle1", "Middle2","Middle3","Middle4","Last", "Last HU" };
	private static final Object[] columnsPercentagePos = { "Action ", "SB", "BB", "UTG", "MP", "Cutoff", "Button" };
	
	
	private static final Object[][] dataPercentagePos = { 
			{ "Preflop   ------", "", "", "", "", "", "", "", "" },	
			{ "Limp", "", "", "", "", "", "", "", "" },
			{ "Check", "", "", "", "", "", "", "", "" },	
			{ "Fold", "", "", "", "", "", "", "", "" },
			{ "Bet1 ", "", "", "", "", "", "", "", "" },
			{ "CallBet1(Limp)", "", "", "", "", "", "", "", "" },
			{ "Bet2", "", "", "", "", "", "", "", "" }, 
			{ "CallBet2", "", "", "", "", "", "", "", "" },
			{ "3 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 3 Bet", "", "", "", "", "", "", "", "" },
			{ "4 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 4 Bet", "", "", "", "", "", "", "", "" },
			{ "Allin", "", "", "", "", "", "", "", "" }, 
			{ "Call Allin", "", "", "", "", "", "", "", "" },
			{ "cBet", "", "", "", "", "", "", "", "" }, 
			{ "Barrell", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },

			{ "Flop  ------", "", "", "", "", "", "", "", "" },
			{ "Limp", "", "", "", "", "", "", "", "" },
			{ "Check", "", "", "", "", "", "", "", "" },	
			{ "Fold", "", "", "", "", "", "", "", "" },
			{ "Bet1 ", "", "", "", "", "", "", "", "" },
			{ "CallBet1(Limp)", "", "", "", "", "", "", "", "" },
			{ "Bet2", "", "", "", "", "", "", "", "" }, 
			{ "CallBet2", "", "", "", "", "", "", "", "" },
			{ "3 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 3 Bet", "", "", "", "", "", "", "", "" },
			{ "4 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 4 Bet", "", "", "", "", "", "", "", "" },
			{ "Allin", "", "", "", "", "", "", "", "" }, 
			{ "Call Allin", "", "", "", "", "", "", "", "" },
			{ "cBet", "", "", "", "", "", "", "", "" }, 
			{ "Barrell", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			
			{ "Turn  ------", "", "", "", "", "", "", "", "" },
			{ "Limp", "", "", "", "", "", "", "", "" },
			{ "Check", "", "", "", "", "", "", "", "" },	
			{ "Fold", "", "", "", "", "", "", "", "" },
			{ "Bet1 ", "", "", "", "", "", "", "", "" },
			{ "CallBet1(Limp)", "", "", "", "", "", "", "", "" },
			{ "Bet2", "", "", "", "", "", "", "", "" }, 
			{ "CallBet2", "", "", "", "", "", "", "", "" },
			{ "3 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 3 Bet", "", "", "", "", "", "", "", "" },
			{ "4 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 4 Bet", "", "", "", "", "", "", "", "" },
			{ "Allin", "", "", "", "", "", "", "", "" }, 
			{ "Call Allin", "", "", "", "", "", "", "", "" },
			{ "cBet", "", "", "", "", "", "", "", "" }, 
			{ "Barrell", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "River  ------", "", "", "", "", "", "", "", "" },
			{ "Limp", "", "", "", "", "", "", "", "" },
			{ "Check", "", "", "", "", "", "", "", "" },	
			{ "Fold", "", "", "", "", "", "", "", "" },
			{ "Bet1 ", "", "", "", "", "", "", "", "" },
			{ "CallBet1(Limp)", "", "", "", "", "", "", "", "" },
			{ "Bet2", "", "", "", "", "", "", "", "" }, 
			{ "CallBet2", "", "", "", "", "", "", "", "" },
			{ "3 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 3 Bet", "", "", "", "", "", "", "", "" },
			{ "4 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 4 Bet", "", "", "", "", "", "", "", "" },
			{ "Allin", "", "", "", "", "", "", "", "" }, 
			{ "Call Allin", "", "", "", "", "", "", "", "" },
			{ "cBet", "", "", "", "", "", "", "", "" }, 
			{ "Barrell", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" } };
	
	private static final Object[][] dataPercentageRp = { 
			{ "Preflop   ------", "", "", "", "", "", "", "", "" },	
			{ "Limp", "", "", "", "", "", "", "", "" },
			{ "Check", "", "", "", "", "", "", "", "" },	
			{ "Fold", "", "", "", "", "", "", "", "" },
			{ "Bet1 ", "", "", "", "", "", "", "", "" },
			{ "CallBet1(Limp)", "", "", "", "", "", "", "", "" },
			{ "Bet2", "", "", "", "", "", "", "", "" }, 
			{ "CallBet2", "", "", "", "", "", "", "", "" },
			{ "3 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 3 Bet", "", "", "", "", "", "", "", "" },
			{ "4 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 4 Bet", "", "", "", "", "", "", "", "" },
			{ "Allin", "", "", "", "", "", "", "", "" }, 
			{ "Call Allin", "", "", "", "", "", "", "", "" },
			{ "cBet", "", "", "", "", "", "", "", "" }, 
			{ "Barrell", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },

			{ "Flop  ------", "", "", "", "", "", "", "", "" },
			{ "Limp", "", "", "", "", "", "", "", "" },
			{ "Check", "", "", "", "", "", "", "", "" },	
			{ "Fold", "", "", "", "", "", "", "", "" },
			{ "Bet1 ", "", "", "", "", "", "", "", "" },
			{ "CallBet1(Limp)", "", "", "", "", "", "", "", "" },
			{ "Bet2", "", "", "", "", "", "", "", "" }, 
			{ "CallBet2", "", "", "", "", "", "", "", "" },
			{ "3 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 3 Bet", "", "", "", "", "", "", "", "" },
			{ "4 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 4 Bet", "", "", "", "", "", "", "", "" },
			{ "Allin", "", "", "", "", "", "", "", "" }, 
			{ "Call Allin", "", "", "", "", "", "", "", "" },
			{ "cBet", "", "", "", "", "", "", "", "" }, 
			{ "Barrell", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			
			{ "Turn  ------", "", "", "", "", "", "", "", "" },
			{ "Limp", "", "", "", "", "", "", "", "" },
			{ "Check", "", "", "", "", "", "", "", "" },	
			{ "Fold", "", "", "", "", "", "", "", "" },
			{ "Bet1 ", "", "", "", "", "", "", "", "" },
			{ "CallBet1(Limp)", "", "", "", "", "", "", "", "" },
			{ "Bet2", "", "", "", "", "", "", "", "" }, 
			{ "CallBet2", "", "", "", "", "", "", "", "" },
			{ "3 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 3 Bet", "", "", "", "", "", "", "", "" },
			{ "4 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 4 Bet", "", "", "", "", "", "", "", "" },
			{ "Allin", "", "", "", "", "", "", "", "" }, 
			{ "Call Allin", "", "", "", "", "", "", "", "" },
			{ "cBet", "", "", "", "", "", "", "", "" }, 
			{ "Barrell", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "River  ------", "", "", "", "", "", "", "", "" },
			{ "Limp", "", "", "", "", "", "", "", "" },
			{ "Check", "", "", "", "", "", "", "", "" },	
			{ "Fold", "", "", "", "", "", "", "", "" },
			{ "Bet1 ", "", "", "", "", "", "", "", "" },
			{ "CallBet1(Limp)", "", "", "", "", "", "", "", "" },
			{ "Bet2", "", "", "", "", "", "", "", "" }, 
			{ "CallBet2", "", "", "", "", "", "", "", "" },
			{ "3 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 3 Bet", "", "", "", "", "", "", "", "" },
			{ "4 Bet", "", "", "", "", "", "", "", "" }, 
			{ "Call 4 Bet", "", "", "", "", "", "", "", "" },
			{ "Allin", "", "", "", "", "", "", "", "" }, 
			{ "Call Allin", "", "", "", "", "", "", "", "" },
			{ "cBet", "", "", "", "", "", "", "", "" }, 
			{ "Barrell", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			
			{ "", "", "", "", "", "", "", "", "" } };
	
	
	
	private static final Object[][] dataOperPos = { 
			{ "Preflop ------", "", "", "", "", "", "", "", "" },
			{ "minBetPerPos", "", "", "", "", "", "", "", "" },
			{ " limpPerPos ------", "", "", "", "", "", "", "", "" },
			{ "checkPerPos", "", "", "", "", "", "", "", "" },
			{ "foldPerPos", "", "", "", "", "", "", "", "" },	
			{ "bet1PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet1PerPos", "", "", "", "", "", "", "", "" },
			{ "bet2PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet2PerPos", "", "", "", "", "", "", "", "" }, 
			{ "bet3PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet3PerPos", "", "", "", "", "", "", "", "" }, 
			{ " bet4PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet4PerPos", "", "", "", "", "", "", "", "" }, 
			{ "allinPerPos ", "", "", "", "", "", "", "", "" },
			{ "allinPerPos", "", "", "", "", "", "", "", "" }, 
			{ "callAllinPerPos", "", "", "", "", "", "", "", "" },
			{ "cBetPerPos ", "", "", "", "", "", "", "", "" }, 
			{ "barrelPerPos", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },

			{ "Flop ------", "", "", "", "", "", "", "", "" },
			{ "minBetPerPos", "", "", "", "", "", "", "", "" },
			{ " limpPerPos ------", "", "", "", "", "", "", "", "" },
			{ "checkPerPos", "", "", "", "", "", "", "", "" },
			{ "foldPerPos", "", "", "", "", "", "", "", "" },	
			{ "bet1PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet1PerPos", "", "", "", "", "", "", "", "" },
			{ "bet2PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet2PerPos", "", "", "", "", "", "", "", "" }, 
			{ "bet3PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet3PerPos", "", "", "", "", "", "", "", "" }, 
			{ " bet4PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet4PerPos", "", "", "", "", "", "", "", "" }, 
			{ "allinPerPos ", "", "", "", "", "", "", "", "" },
			{ "allinPerPos", "", "", "", "", "", "", "", "" }, 
			{ "callAllinPerPos", "", "", "", "", "", "", "", "" },
			{ "cBetPerPos ", "", "", "", "", "", "", "", "" }, 
			{ "barrelPerPos", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },

			{ "Turn  ------", "", "", "", "", "", "", "", "" },
			{ "minBetPerPos", "", "", "", "", "", "", "", "" },
			{ " limpPerPos ------", "", "", "", "", "", "", "", "" },
			{ "checkPerPos", "", "", "", "", "", "", "", "" },
			{ "foldPerPos", "", "", "", "", "", "", "", "" },	
			{ "bet1PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet1PerPos", "", "", "", "", "", "", "", "" },
			{ "bet2PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet2PerPos", "", "", "", "", "", "", "", "" }, 
			{ "bet3PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet3PerPos", "", "", "", "", "", "", "", "" }, 
			{ " bet4PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet4PerPos", "", "", "", "", "", "", "", "" }, 
			{ "allinPerPos ", "", "", "", "", "", "", "", "" },
			{ "allinPerPos", "", "", "", "", "", "", "", "" }, 
			{ "callAllinPerPos", "", "", "", "", "", "", "", "" },
			{ "cBetPerPos ", "", "", "", "", "", "", "", "" }, 
			{ "barrelPerPos", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },

			{ "River ------", "", "", "", "", "", "", "", "" },
			{ "minBetPerPos", "", "", "", "", "", "", "", "" },
			{ " limpPerPos ------", "", "", "", "", "", "", "", "" },
			{ "checkPerPos", "", "", "", "", "", "", "", "" },
			{ "foldPerPos", "", "", "", "", "", "", "", "" },	
			{ "bet1PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet1PerPos", "", "", "", "", "", "", "", "" },
			{ "bet2PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet2PerPos", "", "", "", "", "", "", "", "" }, 
			{ "bet3PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet3PerPos", "", "", "", "", "", "", "", "" }, 
			{ " bet4PerPos", "", "", "", "", "", "", "", "" },
			{ "callBet4PerPos", "", "", "", "", "", "", "", "" }, 
			{ "allinPerPos ", "", "", "", "", "", "", "", "" },
			{ "allinPerPos", "", "", "", "", "", "", "", "" }, 
			{ "callAllinPerPos", "", "", "", "", "", "", "", "" },
			{ "cBetPerPos ", "", "", "", "", "", "", "", "" }, 
			{ "barrelPerPos", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" } };
	
	// @formatter:on

	private static boolean allPercentagePosGUI = false;

	static void allPercentagePos(PlayerCharacteristics pc) {
		if (!allPercentagePosGUI) {
			final var frame = new JFrame("Percentages      All streets ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(500, 50);
			frame.setPreferredSize(new Dimension(500, 850));
			final var table = new JTable(dataPercentagePos, columnsPercentagePos);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			// table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 16.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 16));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			allPercentagePosGUI = true;
		}

		int saveRow = 0;
		int row = 0;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_POS; ++k) {
				saveRow = row;

				dataPercentagePos[row++][k + 1] = Format.format(pc.minBetPerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.minBetPerPos[i][k]);

				dataPercentagePos[row++][k + 1] = Format.format(pc.limpPerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.checkPerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.foldPerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.bet1PerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.callBet1PerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.bet2PerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.callBet2PerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.bet3PerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.callBet3PerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.bet4PerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.callBet4PerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.allinPerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.callAllinPerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.cBetPerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(pc.barrelPerPos[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(-1);
				row = saveRow;
			}
		}
		row += 19;
	}

	private static boolean allPercentageRpGUI = false;

	static void allPercentageRp(PlayerCharacteristics pc) {
		if (!allPercentageRpGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 16);
			final var frame = new JFrame("Players Opportunity data for all streets, relative positions");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(550, 70);
			frame.setPreferredSize(new Dimension(600, 850));
			final var table = new JTable(dataPercentageRp, columnsPercentageRp);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			// table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 16.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 16));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			allPercentageRpGUI = true;
		}

		int saveRow = 0;
		int row = 0;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_RP; ++k) {
				saveRow = row;
				dataPercentageRp[row++][k + 1] = Format.format(pc.minBetPerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.minBetPerRp[i][k]);

				dataPercentageRp[row++][k + 1] = Format.format(pc.limpPerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.checkPerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.foldPerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.bet1PerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.callBet1PerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.bet2PerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.callBet2PerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.bet3PerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.callBet3PerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.bet4PerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.callBet4PerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.allinPerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.callAllinPerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.cBetPerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(pc.barrelPerRp[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(-1);
				row = saveRow;
			}
			row += 19;
		}
	}

	private static boolean valuesGUI = false;

	static void values(PlayerCharacteristics pc) {
		if (!valuesGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 16);
			final var frame = new JFrame("Values  ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(600, 90);
			frame.setPreferredSize(new Dimension(700, 1800));

			final var table = new JTable(dataValues, columnsValues);
			table.setFont(ff);
			// table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(2).setPreferredWidth(250);
			// Create a custom cell renderer to set the font to bold 16.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 16));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			valuesGUI = true;
		}
		int row = 0;
		dataValues[row][1] = Format.format(pc.playerID); // Player ID
		dataValues[row++][2] = Format.format("Player ID");
		dataValues[row][1] = Format.format(pc.playerType);
		dataValues[row++][2] = Format.format("Player typer");
		dataValues[row][1] = Format.format(pc.vpip); // Voluntarily Put In Pot
		dataValues[row++][2] = Format.format("Voluntarily Put In Pot");
		dataValues[row][1] = Format.format(pc.preflopVPIP); // Voluntarily Put In Pot Preflop
		dataValues[row++][2] = Format.format("Voluntarily Put In Pot Preflop");
		dataValues[row][1] = Format.format(pc.aggPct); // Aggression Percentage
		dataValues[row++][2] = Format.format("Aggression Percentage");
		dataValues[row][1] = Format.format(pc.pfr); // Preflop raise
		dataValues[row++][2] = Format.format("Preflop Raise");
		dataValues[row][1] = Format.format(pc.wtsdIfSawFlop); // WTSD% If Saw Flop
		dataValues[row++][2] = Format.format(" WTSD% If Saw Turn");
		dataValues[row][1] = Format.format(pc.wtsdIfSawTurn); // WTSD% If Saw Turn
		dataValues[row++][2] = Format.format("WTSD% If Saw Turn");
		dataValues[row][1] = Format.format(pc.wtsdIfSawRiver); // WTSD% If Saw River
		dataValues[row][1] = Format.format("WTSD% If Saw River"); // WTSD% If Saw River
		dataValues[row][1] = Format.format(pc.winRateBB100); // Win Rate BB 100
		dataValues[row++][2] = Format.format("Win Rate BB 100");
		dataValues[row][1] = Format.format(pc.preflopBet3Freq); // Preflop Bet 3 Frequency
		dataValues[row++][2] = Format.format("Preflop Bet 3 Frequency");
		dataValues[row][1] = Format.format(pc.preflopWinRate); // Preflop Wins Rate
		dataValues[row++][2] = Format.format("Preflop Wins Rate");
		dataValues[row][1] = Format.format(pc.walkPer); // Walks Percent - BB no chance to bet
		dataValues[row++][2] = Format.format("Walks Percent - BB no chance to bet");
		dataValues[row][1] = Format.format(pc.isolateBUPer); // Isolate Button
		dataValues[row++][2] = Format.format("Isolate Button");
		dataValues[row][1] = Format.format(pc.minBetBUPer); // Min Bet Button percentage
		dataValues[row++][2] = Format.format("Min Bet Button percentage");
		dataValues[row][1] = Format.format(pc.stealBUPer); // Steal Button percentage
		dataValues[row++][2] = Format.format("Steal Button percentage");
		dataValues[row][1] = Format.format(pc.squeezeBUPer); // Squeeze Button percentage
		dataValues[row++][2] = Format.format("Squeeze Button percentage");
		dataValues[row][1] = Format.format(pc.minBetBBPer); // Min Bet BB percentage
		dataValues[row++][2] = Format.format("Min Bet BB percentage");
		dataValues[row][1] = Format.format(pc.callMinBetBBPer); // Call Min Bet BB percentage
		dataValues[row++][2] = Format.format("Call Min Bet BB percentage");
		dataValues[row][1] = Format.format(pc.steal3BetBBPer); // Steal 3 Bet BB percentage
		dataValues[row++][2] = Format.format("Steal 3 Bet BB percentage");
		dataValues[row][1] = Format.format(pc.raisedBySBBBPer); // Raised By SB Bet BB percentage
		dataValues[row++][2] = Format.format("Raised By SB Bet BB percentage");
		dataValues[row][1] = Format.format(pc.steal3BetMinRaiseBBPer); // Steal 3 Bet Min Raise BB percentage
		dataValues[row++][2] = Format.format("Steal 3 Bet Min Raise BB percentage");
		dataValues[row][1] = Format.format(pc.stealCallBBPer); // Steal Call BB percentage
		dataValues[row++][2] = Format.format("Steal Call BB percentage");
		dataValues[row][1] = Format.format(pc.stealCallMinRaiseBBPer); // Steal Call Min Raise BB percentage
		dataValues[row++][2] = Format.format("Steal Call Min Raise BB percentage");
		dataValues[row][1] = Format.format(pc.bet3MinSBPer); // Steal 3 Bet Min SB percentage
		dataValues[row++][2] = Format.format("Steal 3 Bet Min SB percentage");
		dataValues[row][1] = Format.format(pc.raisedByBBSBPer); // Raised By SB Bet BB percentage
		dataValues[row++][2] = Format.format("Raised By SB Bet BB percentage");
		dataValues[row][1] = Format.format(pc.steal3BetSBPer); // Steal 3 Bet SB percentage
		dataValues[row++][2] = Format.format("Steal 3 Bet SB percentage");
		dataValues[row][1] = Format.format(pc.stealCallSBPer); // Steal Call SB percentage
		dataValues[row++][2] = Format.format("Steal Call SB percentage");
		dataValues[row][1] = Format.format(pc.stealCallMinRaiseSBPer); // Steal Call Min Raise SB percentage
		dataValues[row++][2] = Format.format("Steal Call Min Raise SB percentage");
		dataValues[row][1] = Format.format(pc.foldedToSBPer); // Folded To SB percentage
		dataValues[row++][2] = Format.format("Folded To SB percentage");
		dataValues[row][1] = Format.format(pc.fourBetLimpCallHU); // 4Bet Limp-Call HU
		dataValues[row++][2] = Format.format("4Bet Limp-Call HU");
		dataValues[row][1] = Format.format(pc.fourBetLimpReRaiseHU); // 4Bet Limp-ReRaise HU
		dataValues[row++][2] = Format.format("4Bet Limp-ReRaise HU");
		dataValues[row][1] = Format.format(pc.sB_Limp_Fold_HU); // SB Limp-Fold HU
		dataValues[row++][2] = Format.format("SB Limp-Fold HU");
		dataValues[row][1] = Format.format(pc.btnOpenLimp); // BTN Open Limp
		dataValues[row++][2] = Format.format("BTN Open Limp");
		dataValues[row][1] = Format.format(pc.fourBetOpenLimp); // 4Bet Open Limp
		dataValues[row++][2] = Format.format("4Bet Open Limp");
		dataValues[row][1] = Format.format(pc.bB_Raise_vs_SB_Limp_UOP); // BB Raise vs SB Limp UOP
		dataValues[row++][2] = Format.format("BB Raise vs SB Limp UOP");
		dataValues[row][1] = Format.format(pc.flopCBet); // Flop CBet
		dataValues[row++][2] = Format.format("Flop CBet");
		dataValues[row][1] = Format.format(pc.flopCBetIP); // Flop CBet IP
		dataValues[row++][2] = Format.format("Flop CBet IP");
		dataValues[row][1] = Format.format(pc.flopCBetOOP); // Flop CBet OOP
		dataValues[row++][2] = Format.format("Flop CBet OOP");
		dataValues[row][1] = Format.format(pc.flopCBetCall); // Flop CBet-Call
		dataValues[row++][2] = Format.format("Flop CBet-Call");
		dataValues[row][1] = Format.format(pc.flopCBetReraise); // Flop CBet-Reraise
		dataValues[row++][2] = Format.format("Flop CBet-Reraise");
		dataValues[row][1] = Format.format(pc.flopCBetFold); // Flop CBet-Fold
		dataValues[row++][2] = Format.format("Flop CBet-Fold");
		dataValues[row][1] = Format.format(pc.flopCallDonkBet); // Flop Call Donk Bet
		dataValues[row++][2] = Format.format("Flop Call Donk Bet");
		dataValues[row][1] = Format.format(pc.flopRaiseDonkBet); // Flop Raise Donk Bet
		dataValues[row++][2] = Format.format("Flop Raise Donk Bet");
		dataValues[row][1] = Format.format(pc.flopFoldToDonkBet); // Flop Fold to Donk Bet
		dataValues[row++][2] = Format.format(" Flop Fold to Donk Bet");
		dataValues[row][1] = Format.format(pc.skipFlopCBAndCheckCallOOP); // Skip Flop CB and Check-Call OOP
		dataValues[row++][2] = Format.format("Skip Flop CB and Check-Call OOP");
		dataValues[row][1] = Format.format(pc.skipFlopCBAndCheckRaiseOOP); // Skip Flop CB and Check-Raise OOP
		dataValues[row++][2] = Format.format("Skip Flop CB and Check-Raise OOP");
		dataValues[row][1] = Format.format(pc.skipFlopCBAndCheckFoldOOP); // Skip Flop CB and Check-Fold OOP
		dataValues[row++][2] = Format.format("Skip Flop CB and Check-Fold OOP");
		dataValues[row][1] = Format.format(pc.flopCallCBIP); // Flop Call CB IP
		dataValues[row++][2] = Format.format("Flop Call CB IP");
		dataValues[row][1] = Format.format(pc.flopRaiseCBIP); // Flop Raise CB IP
		dataValues[row++][2] = Format.format("Flop Raise CB IP");
		dataValues[row][1] = Format.format(pc.flopFoldToCBIP); // Flop Fold to CB IP
		dataValues[row++][2] = Format.format("Flop Fold to CB IP");
		dataValues[row][1] = Format.format(pc.flopCheckCallCBOOP); // Flop Check-Call CB OOP
		dataValues[row++][2] = Format.format(" Flop Check-Call CB OOP");
		dataValues[row][1] = Format.format(pc.flopCheckRaiseCBOOP); //
		dataValues[row++][2] = Format.format("Flop Check-Raise CB OOP");
		dataValues[row][1] = Format.format(pc.flopCheckFoldToCBOOP); // Flop Check-Fold to CB OOP
		dataValues[row++][2] = Format.format(" Flop Check-Fold to CB OOP");
		dataValues[row][1] = Format.format(pc.flopDonkBetCall); // Flop Donk Bet-Call
		dataValues[row++][2] = Format.format("Flop Donk Bet-Call-----------------");
		dataValues[row][1] = Format.format(pc.flopDonkBetReRaise); // Flop Donk Bet-ReRaise
		dataValues[row++][2] = Format.format("Flop Donk Bet-ReRaise");
		dataValues[row][1] = Format.format(pc.flopDonkBetFold); // Flop Donk Bet-Fold
		dataValues[row++][2] = Format.format("Flop Donk Bet-Fold");
		dataValues[row][1] = Format.format(pc.flopDonkBet); // Flop Donk Bet
		dataValues[row++][2] = Format.format("Flop Donk Bet");
		dataValues[row][1] = Format.format(pc.flopBetVsMissedCB); // Flop Bet vs Missed CB
		dataValues[row++][2] = Format.format("Flop Bet vs Missed CB");
		dataValues[row][1] = Format.format(pc.flopBetLimpedPotAttemptToStealIP); // Flop Bet - Limped Pot Attempt to

		dataValues[row++][2] = Format.format(" Flop Bet - Limped Pot Attempt to Steal IP");
		dataValues[row][1] = Format.format(pc.postflopAggressionPercentage); // Postflop Aggression Percentage
		dataValues[row++][2] = Format.format("Postflop Aggression Percentage");
		dataValues[row][1] = Format.format(pc.seenFlopOverall); // Seen Flop Overall
		dataValues[row++][2] = Format.format("Seen Flop Overall");
		dataValues[row][1] = Format.format(pc.turnCBet); // Turn CBet
		dataValues[row++][2] = Format.format("Turn CBet");
		dataValues[row][1] = Format.format(pc.turnCBetIP); // Turn CBet IP
		dataValues[row++][2] = Format.format("Turn CBet IP");
		dataValues[row][1] = Format.format(pc.turnCBetOOP); // Turn CBet OOP
		dataValues[row++][2] = Format.format("Turn CBet OOP");
		dataValues[row][1] = Format.format(pc.turnCBetCall); // Turn CBet-Call
		dataValues[row++][2] = Format.format("Turn CBet-Call");
		dataValues[row][1] = Format.format(pc.turnCBetReRaise); // Turn CBet-ReRaise
		dataValues[row++][2] = Format.format("Turn CBet-ReRaise");
		dataValues[row][1] = Format.format(pc.delayedTurnCBet); // Delayed Turn CBet
		dataValues[row++][2] = Format.format("Delayed Turn CBet");
		dataValues[row][1] = Format.format(pc.delayedTurnCBetIP); // Delayed Turn CBet IP
		dataValues[row++][2] = Format.format("Delayed Turn CBet IP");
		dataValues[row][1] = Format.format(pc.delayedTurnCBetOOP); //
		dataValues[row++][2] = Format.format("Delayed Turn CBet OOP");
		dataValues[row][1] = Format.format(pc.skipTurnCBAndCheckCallOOP); // Skip Turn CB and Check-Call OOP
		dataValues[row++][2] = Format.format("Skip Turn CB and Check-Call OOP");
		dataValues[row][1] = Format.format(pc.skipTurnCBAndCheckRaiseOOP); // Skip Turn CB and Check-Raise OOP
		dataValues[row++][2] = Format.format("Skip Turn CB and Check-Raise OOP");
		dataValues[row][1] = Format.format(pc.skipTurnCBAndCheckFoldOOP); // Skip Turn CB and Check-Fold OOP
		dataValues[row++][2] = Format.format("Skip Turn CB and Check-Fold OOP");
		dataValues[row][1] = Format.format(pc.turnCallCBIP); //
		dataValues[row++][2] = Format.format("Turn Call CB IP");
		dataValues[row][1] = Format.format(pc.turnRaiseCBIP); // Turn Raise CB IP
		dataValues[row++][2] = Format.format("Turn Raise CB IP");
		dataValues[row][1] = Format.format(pc.turnFoldToCBIP); // Turn Fold to CB IP
		dataValues[row++][2] = Format.format("Turn Fold to CB IP");
		dataValues[row][1] = Format.format(pc.turnCheckCallCBOOP); // Turn Check-Call CB OOP
		dataValues[row++][2] = Format.format("Turn Check-Call CB OOP");
		dataValues[row][1] = Format.format(pc.turnCheckRaiseCBOOP); //
		dataValues[row++][2] = Format.format(" Turn Check-Raise CB OOP");
		dataValues[row][1] = Format.format(pc.turnCheckFoldToCBOOP); // Turn Check-Fold to CB OOP
		dataValues[row++][2] = Format.format(" Turn Check-Fold to CB OOP");
		dataValues[row][1] = Format.format(pc.turnDonkBet); // Turn Donk Bet
		dataValues[row++][2] = Format.format("Turn Donk Bet");
		dataValues[row][1] = Format.format(pc.turnBetVsMissedCB); //
		dataValues[row++][2] = Format.format("Turn Bet vs Missed CB");
		dataValues[row][1] = Format.format(pc.riverCBet); // River CBet
		dataValues[row++][2] = Format.format("River CBet");
		dataValues[row][1] = Format.format(pc.riverCBetIP); // River CBet IP
		dataValues[row++][2] = Format.format("River CBet IP");
		dataValues[row][1] = Format.format(pc.riverCBetOOP); // River CBet OOP
		dataValues[row++][2] = Format.format("River CBet OOP");
		dataValues[row][1] = Format.format(pc.riverCBetCall); //
		dataValues[row++][2] = Format.format("River CBet-Call");
		dataValues[row][1] = Format.format(pc.riverCBetReRaise); // River CBet-ReRaise
		dataValues[row++][2] = Format.format("River CBet-ReRaise");
		dataValues[row][1] = Format.format(pc.riverCBetFold); // River CBet-Fold
		dataValues[row++][2] = Format.format("River CBet-Fold");
		dataValues[row][1] = Format.format(pc.skipRiverCBAndCheckCallOOP); // Skip River CB and Check-Call OOP
		dataValues[row++][2] = Format.format(" Skip River CB and Check-Call OOP");
		dataValues[row][1] = Format.format(pc.skipRiverCBAndCheckRaiseOOP); // Skip River CB and Check-Raise OOP
		dataValues[row++][2] = Format.format("Skip River CB and Check-Raise OOP");
		dataValues[row][1] = Format.format(pc.skipRiverCBAndCheckFoldOOP); // Skip River CB and Check-Fold OOP
		dataValues[row++][2] = Format.format("Skip River CB and Check-Fold OOP");
		dataValues[row][1] = Format.format(pc.riverCallCBIP); // River Call CB IP
		dataValues[row++][2] = Format.format("River Call CB IP");
		dataValues[row][1] = Format.format(pc.riverRaiseCBIP); // River Raise CB IP
		dataValues[row++][2] = Format.format("River Raise CB IP");
		dataValues[row][1] = Format.format(pc.riverFoldToCBIP); // River Fold to CB IP
		dataValues[row++][2] = Format.format("River Fold to CB IP");
		dataValues[row][1] = Format.format(pc.riverCheckCallCBOOP); // River Check-Call CB OOP
		dataValues[row++][2] = Format.format("River Check-Call CB OOP");
		dataValues[row][1] = Format.format(pc.riverCheckRaiseCBOOP); // River Check-Raise CB OOP
		dataValues[row++][2] = Format.format("River Check-Raise CB OOP");
		dataValues[row][1] = Format.format(pc.riverCheckFoldToCBOOP); //
		dataValues[row++][2] = Format.format(" River Check-Fold to CB OOP");
		dataValues[row][1] = Format.format(pc.riverDonkBet); // River Donk Bet
		dataValues[row++][2] = Format.format("River Donk Bet");
		dataValues[row][1] = Format.format(pc.riverBetVsMissedCB); // River Bet vs Missed CB
		dataValues[row++][2] = Format.format(" River Bet vs Missed CB");
		dataValues[row][1] = Format.format(pc.wtsdWhenSawTurn); // WTSD% When Saw Turn
		dataValues[row++][2] = Format.format("WTSD% When Saw Turn");
		dataValues[row][1] = Format.format(pc.reSteal3BetVsSteal); // ReSteal (3Bet vs Steal)
		dataValues[row++][2] = Format.format(" ReSteal (3Bet vs Steal)");
		dataValues[row][1] = Format.format(pc.threeBetReStealVsHero); // 3Bet ReSteal vs Hero
		dataValues[row++][2] = Format.format("3Bet ReSteal vs Hero");
		dataValues[row][1] = Format.format(pc.sBFoldToSteal); // SB Fold to Steal
		dataValues[row++][2] = Format.format("SB Fold to Steal");
		dataValues[row][1] = Format.format(pc.fourBetCallSteal); // 4Bet Call Steal
		dataValues[row++][2] = Format.format("4Bet Call Steal");
		dataValues[row][1] = Format.format(pc.fourBetReSteal3BetVsSteal); // 4Bet ReSteal (3Bet vs Steal)
		dataValues[row++][2] = Format.format("4Bet ReSteal (3Bet vs Steal)");
		dataValues[row][1] = Format.format(pc.foldToStealBB); // Fold to Steal BB
		dataValues[row++][2] = Format.format("Fold to Steal BB");
		dataValues[row][1] = Format.format(pc.bbCallSteal); // BB Call Steal
		dataValues[row++][2] = Format.format(" BB Call Steal");
		dataValues[row][1] = Format.format(pc.bbReSteal3BetVsSteal); // BB 3Bet ReSteal vs Steal
		dataValues[row++][2] = Format.format("BB 3Bet ReSteal vs Steal");
		dataValues[row][1] = Format.format(pc.bb3BetReStealVsHero); // BB 3Bet ReSteal vs Hero
		dataValues[row++][2] = Format.format("BB 3Bet ReSteal vs Hero");
		dataValues[row][1] = Format.format(pc.foldToStealSB); // Fold to Steal SB
		dataValues[row++][2] = Format.format("Fold to Steal SB");
		dataValues[row][1] = Format.format(pc.foldToBTNSteal); // Fold to BTN Steal
		dataValues[row++][2] = Format.format("Fold to BTN Steal");
		dataValues[row][1] = Format.format(pc.foldToSBSteal); //
		dataValues[row++][2] = Format.format("Fold to SB Steal");
		dataValues[row][1] = Format.format(pc.foldSBVsStealFromButtonHero); // Fold SB vs Steal from Button Hero
		dataValues[row++][2] = Format.format("Fold SB vs Steal from Button Hero");
		dataValues[row][1] = Format.format(pc.foldBBVsStealFromButtonHero); //
		dataValues[row++][2] = Format.format("Fold BB vs Steal from Button Hero");
		dataValues[row][1] = Format.format(pc.foldBBVsStealFromSmallBlindHero); // Fold BB vs Steal from Small Blind
																				// Hero
		dataValues[row++][2] = Format.format("Fold BB vs Steal from Small Blind Hero");
		dataValues[row][1] = Format.format(pc.fourBetFoldToBTNSteal); // 4Bet Fold to BTN Steal
		dataValues[row++][2] = Format.format("4Bet Fold to BTN Steal");
		dataValues[row][1] = Format.format(pc.sBLimpFoldHU); // SB Limp-Fold HU
		dataValues[row++][2] = Format.format("SB Limp-Fold HU");
		dataValues[row][1] = Format.format(pc.bBRaiseVsSBLimpUOP); // BB Raise vs SB Limp UOP
		dataValues[row++][2] = Format.format("BB Raise vs SB Limp UOP");
	}

	private static boolean streetGUI = false;

	static void streets(PlayerCharacteristics pc) {
		if (!streetGUI) {
			final var frame = new JFrame("Streets");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(650, 120);
			frame.setPreferredSize(new Dimension(500, 950));
			final var table = new JTable(dataStreets, columnsStreets);
			final var pane = new JScrollPane(table);
			// Create a custom cell renderer to set the font to bold 16.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 16));
			table.setDefaultRenderer(Object.class, renderer); // Apply the custom renderer to all cells.
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			streetGUI = true;
		}
		int row = 0;
		int col = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			dataStreets[row++][col] = Format.format(pc.winRateBB100Streets[i]);
			dataStreets[row++][col] = Format.format(pc.inPositionFrequencyStreets[i]);
			dataStreets[row++][col] = Format.format(pc.outOfPositionFrequency[i]);
			dataStreets[row++][col] = Format.format(pc.inPositionFrequencyStreets[i]);
			dataStreets[row++][col] = Format.format(pc.outOfPositionFrequency[i]);
			dataStreets[row++][col] = Format.format(pc.vpipStreet[i]); // ??? TODO
			dataStreets[row++][col] = Format.format(pc.pfrStreet[i]); // TODO????
			dataStreets[row++][col] = Format.format(pc.aggressionFrequencyStreet[i]);
			dataStreets[row++][col] = Format.format(pc.aggressionFactorStreet[i]);
			dataStreets[row++][col] = Format.format(pc.winRateStreets[i]);
			dataStreets[row++][col] = Format.format(pc.bet3tFreqStreets[i]); // 3-Bet
			dataStreets[row++][col] = Format.format(pc.w$sd_wsd[i]); // Won Money At Showdown
			dataStreets[row++][col] = Format.format(pc.wwsf[i]); // Won When Saw
			dataStreets[row++][col] = Format.format(pc.wtsd[i]);
			dataStreets[row++][col] = Format.format(pc.foldTo3BetAfterRaising[i]); // Fold to 3-Bet After R
			dataStreets[row++][col] = Format.format(pc.Squeeze[i]); // Squeeze
			dataStreets[row++][col] = Format.format(pc.Aggression[i]); // Squeeze
// TODO			dataStreets[row++][col] = Format.format(pc.CBet[i]); // C-Bet
// TODO			dataStreets[row++][col] = Format.format(pc.CBet_ip[i]); // C-Bet in position
			dataStreets[row++][col] = Format.format(pc.CBet_OOP[i]); // C-Bet out of position
			dataStreets[row++][col] = Format.format(pc.CBet_Fold[i]); // C-Bet folded to C-Bet
			dataStreets[row++][col] = Format.format(pc.CBet_Call[i]); // C-Bet called
			dataStreets[row++][col] = Format.format(pc.CBet_Reraise[i]); // C-Bet feraised
			dataStreets[row++][col] = Format.format(pc.CheckCall_IP[i]); // C-Bet called in position
			dataStreets[row++][col] = Format.format(pc.CheckCall_OOP[i]); // C-Bet called out of position
			dataStreets[row++][col] = Format.format(pc.DonkBet[i]); //
			dataStreets[row++][col] = Format.format(pc.DonkBetCall[i]); //
			dataStreets[row++][col] = Format.format(pc.foldToCBet[i]); // Fold to C-Bet
			dataStreets[row++][col] = Format.format(pc.coldCallRise[i]); //
			dataStreets[row++][col] = Format.format(pc.rfi[i]); // Raise first in
			dataStreets[row++][col] = Format.format(pc.callPercentage[i]);
			dataStreets[row++][col] = Format.format(pc.raisePercentage[i]);
			dataStreets[row++][col] = Format.format(pc.betPercentage[i]); // TODO
			dataStreets[row++][col] = Format.format(pc.foldPercentage[i]);
			dataStreets[row++][col] = Format.format(pc.checkPercentage[i]);
			++col;
			row = 1;
		}

	}

	private static boolean playerStatsGUI = false;

	/*- ******************************************************************************
	* Player Action
	* Display allPlayers data for each player and for each orbit.
	*******************************************************************************/
	static void playerStats(PlayerCharacteristics pc) {
		JTable table = null;
		if (!playerStatsGUI) {
			playerStatsGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 12);
			final var frame = new JFrame("Player stats  ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(660, 200);
			frame.setPreferredSize(new Dimension(400, 300));

			final var tableModel = new DefaultTableModel(dataStats, columnsStats);
			table = new JTable(tableModel);
			table.setFont(ff);
			final var pane = new JScrollPane(table);
			// Create a custom cell renderer to set the font to bold 16.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 16));

			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
		}
		int row = 0;

		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.formatPer(pc.vpipStreetPos[0][i]), row, i + 1);
		}
		table.setValueAt(Format.formatPer(pc.preflopVPIP), row, 7);

		++row;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.formatPer(pc.aggPctStreetPos[0][i]), row, i + 1);
		}
		table.setValueAt(Format.formatPer(pc.aggPct), row, 7);

		++row;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.formatPer(pc.afStreetPos[0][i]), row, i + 1);
		}
		table.setValueAt(Format.formatPer(pc.af), row, 7);

		++row;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.formatPer(pc.bet3FreqStreetPos[0][i]), row, i + 1);
		}
		// table.setValueAt(Format.formatPer(pc.pfr), row, 7);

		++row;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.formatPer(pc.bet3FreqStreetPos[0][i]), row, i + 1);
		}
		// table.setValueAt(Format.formatPer(pc.preflopWTSD), row, 7);
		++row;

		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.formatPer(pc.bet3FreqStreetPos[0][i]), row, i + 1);
		}
		// table.setValueAt(Format.formatPer(pc.preflopWTSD), row, 7);
		++row;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.formatPer(pc.winRateStreetPos[0][i]), row, i + 1);
		}
		// table.setValueAt(Format.formatPer(pc.preflopWTSD), row, 7);
		++row;
	}

	private static boolean playerBasicStatsGUI = false;

	static void playerBasicStats(PlayerCharacteristics pc) {
		JTable table = null;
		if (!playerBasicStatsGUI) {
			playerBasicStatsGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 12);
			final var frame = new JFrame("Basic player stats Preflop");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(700, 230);
			frame.setPreferredSize(new Dimension(400, 400));
			final var tableModel = new DefaultTableModel(dataBasicStats, columnsBasicStats);
			table = new JTable(tableModel);
			table.setFont(ff);
			final var pane = new JScrollPane(table);
			// Create a custom cell renderer to set the font to bold 16.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 16));

			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
		}

	}

	static void playerStatsFlop(PlayerCharacteristics pc) {
		if (!playerStatsFlopGUI) {
			playerStatsFlopGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 16);
			final var frame = new JFrame("Player Statistics by position");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(750, 300);
			frame.setPreferredSize(new Dimension(500, 400));

			final var table = new JTable(dataStatsFlop, columnsStatsFlop);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 16.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 16));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
		}

		for (int i = 0; i < Players.NUM_STREETS; ++i) {
			for (int j = 0; j < Players.BETS_MAX; ++j) {

			}
		}
	}

	// @formatter:on

	static void playerStatsTurn(PlayerCharacteristics pc) {

	}

	static void playerStatsRiver(PlayerCharacteristics pc) {

	}

	static void playerStatsShowdown(PlayerCharacteristics pc) {

	}

}
