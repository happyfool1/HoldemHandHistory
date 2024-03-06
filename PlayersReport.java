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

/*-*****************************************************************************************
  * This class does a bunch of reports using data only in a Players instance.
 * 
 *
 * @author PEAK_
 ******************************************************************************************/
public class PlayersReport implements Constants {

	private PlayersReport() {
		throw new IllegalStateException("Utility class");
	}
	// @formatter:off
	
    private static  Font  ff = new Font(Font.SERIF, Font.BOLD, 18);


private static final Object[][] dataStreets = { 
		{ " player ID ", "", "", "", "" },	
		{ "PFRCount", "", "", "", "" },
		{ "threeBetCount", "", "", "", "" } ,
		{ "WTSDCount", "", "", "", "" },	
		{ "W$SD_WSDCount", "", "", "", "" } ,
		{ "WWSFCount", "", "", "", "" },	
		{ "foldTo3BetAfterRaisingCount", "", "", "", "" } ,
		{ "foldToCBetCount ", "", "", "", "" },	
		{ "streetCounts", "", "", "", "" } ,
		{ "summaryFoldCount", "", "", "", "" },	
		{ "summaryCollectedCount", "", "", "", "" } ,
		{ "", "", "", "", "" } 
	
		};



	
	private static final Object[] columnsNewArraysRp = { "Action ", "First", "First HU", "Middle1", "Middle2","Middle3","Middle4","Last", "Last HU" };
	private static final Object[] columnsNewArraysPos = { "Action ", "SB", "BB", "UTG", "MP", "Cutoff", "Button" };
	private static final Object[] columnsRp = { "Action ", "First", "First HU", "Middle1", "Middle2","Middle3","Middle4","Last", "Last HU" };
	private static final Object[] columnsPos = { "Action ", "SB", "BB", "UTG", "MP", "Cutoff", "Button" };
	private static final Object[] columnsStreets = { "Street", "Preflop", "Flop", "Turn", "River" };
	
	private static final Object[][] dataActionsNewArraysPos = { 
			{ "Preflop   ------", "", "", "", "", "", "", "", "" },	
			{ "foldCount", "", "", "", "", "", "", "", "" },
			{ "limpCount", "", "", "", "", "", "", "", "" },	
			{ "callCount", "", "", "", "", "", "", "", "" },
			{ "betCount  ", "", "", "", "", "", "", "", "" },
			{ "raiseCount ", "", "", "", "", "", "", "", "" },
			{ " raisePos", "", "", "", "", "", "", "", "" }, 
			{ "betPos ", "", "", "", "", "", "", "", "" },
			{ "callPos", "", "", "", "", "", "", "", "" }, 
			{ "minBetOperPos", "", "", "", "", "", "", "", "" },
			{ "minBetPos", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "Flop  ------", "", "", "", "", "", "", "", "" },	
			{ "foldCount", "", "", "", "", "", "", "", "" },
			{ "limpCount", "", "", "", "", "", "", "", "" },	
			{ "callCount", "", "", "", "", "", "", "", "" },
			{ "betCount  ", "", "", "", "", "", "", "", "" },
			{ "raiseCount ", "", "", "", "", "", "", "", "" },
			{ " raisePos", "", "", "", "", "", "", "", "" }, 
			{ "betPos ", "", "", "", "", "", "", "", "" },
			{ "callPos", "", "", "", "", "", "", "", "" }, 
			{ "minBetOperPos", "", "", "", "", "", "", "", "" },
			{ "minBetPos", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "Turn   ------", "", "", "", "", "", "", "", "" },	
			{ "foldCount", "", "", "", "", "", "", "", "" },
			{ "limpCount", "", "", "", "", "", "", "", "" },	
			{ "callCount", "", "", "", "", "", "", "", "" },
			{ "betCount  ", "", "", "", "", "", "", "", "" },
			{ "raiseCount ", "", "", "", "", "", "", "", "" },
			{ " raisePos", "", "", "", "", "", "", "", "" }, 
			{ "betPos ", "", "", "", "", "", "", "", "" },
			{ "callPos", "", "", "", "", "", "", "", "" }, 
			{ "minBetOperPos", "", "", "", "", "", "", "", "" },
			{ "minBetPos", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "River  ------", "", "", "", "", "", "", "", "" },	
			{ "foldCount", "", "", "", "", "", "", "", "" },
			{ "limpCount", "", "", "", "", "", "", "", "" },	
			{ "callCount", "", "", "", "", "", "", "", "" },
			{ "betCount  ", "", "", "", "", "", "", "", "" },
			{ "raiseCount ", "", "", "", "", "", "", "", "" },
			{ " raisePos", "", "", "", "", "", "", "", "" }, 
			{ "betPos ", "", "", "", "", "", "", "", "" },
			{ "callPos", "", "", "", "", "", "", "", "" }, 
			{ "minBetOperPos", "", "", "", "", "", "", "", "" },
			{ "minBetPos", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		};

	private static final Object[][] dataNewArraysRp= { 
			{ "Preflop   ------", "", "", "", "", "", "", "", "" },	
			{ "raiseRp ", "", "", "", "", "", "", "", "" },
			{ "betRp", "", "", "", "", "", "", "", "" },	
			{ "callRp ", "", "", "", "", "", "", "", "" },
			{ "minBetOperRp ", "", "", "", "", "", "", "", "" },
			{ "minBetRp ", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "Flop  ------", "", "", "", "", "", "", "", "" },	
			{ "raiseRp ", "", "", "", "", "", "", "", "" },
			{ "betRp", "", "", "", "", "", "", "", "" },	
			{ "callRp ", "", "", "", "", "", "", "", "" },
			{ "minBetOperRp ", "", "", "", "", "", "", "", "" },
			{ "minBetRp ", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "Turn   ------", "", "", "", "", "", "", "", "" },	
			{ "raiseRp ", "", "", "", "", "", "", "", "" },
			{ "betRp", "", "", "", "", "", "", "", "" },	
			{ "callRp ", "", "", "", "", "", "", "", "" },
			{ "minBetOperRp ", "", "", "", "", "", "", "", "" },
			{ "minBetRp ", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" },
			{ "River  ------", "", "", "", "", "", "", "", "" },	
			{ "raiseRp ", "", "", "", "", "", "", "", "" },
			{ "betRp", "", "", "", "", "", "", "", "" },	
			{ "callRp ", "", "", "", "", "", "", "", "" },
			{ "minBetOperRp ", "", "", "", "", "", "", "", "" },
			{ "minBetRp ", "", "", "", "", "", "", "", "" },
			{ "", "", "", "", "", "", "", "", "" }
		};
	



	private static boolean allActionPosGUI = false;
 
	private static boolean allOperPosGUI = false;
 
	private static boolean allPerPosGUI = false;
 
	private static final Object[][] dataActionsPos = { 
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
 
	private static final Object[][] dataOperPos = { 
			{ "Preflop ------", "", "", "", "", "", "", "", "" },
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

			{ "Flop ------", "", "", "", "", "", "", "", "" },
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

			{ "River ------", "", "", "", "", "", "", "", "" },
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
	 
	private static final Object[][] dataPercentagePos = {
			{ "Preflop", "", "", "", "", "", "", "", "" },
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

			{ "Flop ------", "", "", "", "", "", "", "", "" },
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

			{ "Turn   ------", "", "", "", "", "", "", "", "" },
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
			{ "", "", "", "", "", "", "", "", "" } };
	

	private static final Object[] columnsValues  = { "Name", "Value" ,"Description"};
	private static final Object[][] dataValues= { 
			{"playerID","",""}, // Key
			{"handsPlayed","",""}, // Number of hands played
			{"playerType","",""}, // LAG","",""}, TAG, NIT, ......
			{"wtsd","",""}, // Went to Showdown
			{"wonShowdownCount","",""},
			{"wonCount","",""},
			{"wsdCount","",""}, // Won at Showdown
			{"wtsdPreflopCount","",""}, // Went to showdown if saw preflop
			{"wtsdFlopCount","",""}, // Went to showdown if saw flop
			{"wtsdTurnCount","",""}, // Went to showdown if saw turn
			{"wtsdRiverCount","",""}, // Went to showdown if saw river
			{"wwspCount","",""}, // Won when saw Preflop
			{"wwsfCount","",""}, // Won when saw Flop
			{"wwstCount","",""}, // Won when saw Turn
			{"wwsrCount","",""}, // Won when saw River
			{"showdownCount","",""},  // Showdown count
			{"showdownWonCount","",""}, // TODO
			{"raiseCount1","",""}, // Raise count
			{"betCount1","",""}, // Bet count
			{"walkCount","",""}, // Walk count
			{"barrellFlopTurnCount","",""}, // Raise then raise turn
			{"barrellFlopTurnRiverCount","",""}, // Raise then raise turn then raise river
			{"barrellTurnRiverCount","",""}, // Raise then raise turn then raise river
			{"barrellPreflopFlopTurnRiverCount","",""}, // Raise 4 streets
			{"walkOper","",""}, // BB can not raise because no other raise
			{"isolateOperBU","",""}, // Button isolate opportunity
			{"minBetOperBU","",""}, // Button min bet opportunity
			{"stealOperBU","",""}, // Button steal opportunity
			{"squeezeOperBU","",""}, // Button squeeze opportunity
			{"minBetOperBB","",""}, // Big blind min bet opportunity
			{"callMinBetOperBB","",""}, // Big blind call min bet opportunity
			{"steal3BetOperBB","",""}, // Big blind steal opportunity
			{"raisedBySBOperBB","",""}, // Big blind raised by SBB opportunity
			{"steal3BetMinRaiseOperBB","",""}, // Big blind steal opportunity
			{"stealCallOperBB","",""}, // Big blind steal opportunity
			{"stealCallMinRaiseOperBB","",""}, // BB steal calling min raise opportunity
				{"bet3MinOperSB","",""}, // Small blind min bet opportunity
			{"raisedByBBOperSB","",""}, // Small blind raised by BBB opportunity
			{"steal3BetOperSB","",""}, // Small blind 3 bet steal opportunity
			{"stealCallOperSB","",""}, // Small blind steal call opportunity
			{"stealCallMinRaiseSBOper","",""}, // Small blind steal calling min raise opportunity
			{"foldedToSBOper","",""}, // Folded to small blind opportunity
			{"walk","",""}, // BB can not raise because no other raise
				{"isolateBU","",""}, // Button isolate
			{"minBetBU","",""}, // Button min bet
			{"stealBU","",""},// Button steal
			{"squeezeBU","",""}, // Button squeeze
			{"minBetBB","",""},// Big blind min bet
			{"callMinBetBB","",""}, // Big blind call min bet
			{"steal3BetBB","",""}, // Big blind steal
			{"raisedBySBBB","",""}, // Big blind raised by SB BB
			{"steal3BetMinRaiseBB","",""}, // Big blind steal
			{"stealCallBB","",""}, // Big blind steal
			{"stealCallMinRaiseBB","",""}, // BB steal calling min raise
				{"bet3MinSB","",""},// Small Blind bet 3 minn bet
			{"raisedByBBSB","",""}, // Small blind raised by BB
			{"steal3BetSB","",""}, // Small blind steal
			{"stealCallSB","",""}, // Small blind steal call
			{"stealCallMinRaiseSB","",""}, // SB steal calling min raise
			{"foldedToSB","",""}, // SB folded
			{"stealCount","",""}, // Button steal count
			{"stealStartP","",""}, // Button steal start position
			{"stealCountP","",""}, // Button steal count position
			{"stealStartEV","",""}, // Button steal start Expected Value
			{"stealCountEV","",""}, // Button steal count Expected Value
			{"isolateCount","",""}, // Button isolate count
			{"isolateStartP","",""}, // Button isolate start position
			{"isolateCountP","",""}, // Button isolate count position
			{"isolateStartEV","",""}, // Button isolate start Expected Value
			{"isolateCountEV","",""}, // Button isolate count Expected Value
			{"squeezeCount","",""}, // Button squeeze count
			{"squeezeStartP","",""}, // Button squeeze start position
			{"squeezeCountP","",""}, // Button squeeze count position
			{"squeezeStartEV","",""}, // Button squeeze start Expected Value
			{"squeezeCountEV","",""}, // Button squeeze count Expected Value
			{"minBetCount","",""}, // Button min bet count
			{"minBetStartP","",""}, // Button min bet start position
			{"minBetCountP","",""}, // Button min bet count position
			{"minBetStartEV","",""}, // Button min bet start Expected Value
			{"minBetCountEV","",""}, // Button min bet count Expected Value
				{"bbStealCount","",""}, // Big blind steal count
			{"bbStealStartP","",""}, // Big blind steal start position
			{"bbStealCountP","",""}, // Big blind steal count position
			{"bbStealStartEV","",""}, // Big blind steal start Expected Value
			{"bbStealCountEV","",""}, // Big blind steal count Expected Value
			{"bbBet2Count","",""}, // Big blind bet 2 count
			{"bbBet2Opportunity","",""}, // Big blind bet 2 opportunity
			{"bbBet2StartP","",""}, // Big blind bet 2 start position
			{"bbBet2CountP","",""}, // Big blind bet 2 count position
			{"bbBet2StartEV","",""}, // Big blind bet 2 start Expected Value
			{"bbBet2CountEV","",""}, // Big blind bet 2 count Expected Value
			{"bbRaisedBySBCount","",""}, // Big blind raised by SB count
			{"bbRaisedBySBStartP","",""}, // Big blind raised by SB start position
			{"bbRaisedBySBCountP","",""}, // Big blind raised by SB count position
			{"bbRaisedBySBStartEV","",""}, // Big blind raised by SB start Expected Value
			{"bbRaisedBySBCountEV","",""}, // Big blind raised by SB count Expected Value
			{"bbCallMinBetCount","",""}, // Big blind call min bet count
			{"bbCallMinBetStartP","",""}, // Big blind call min bet start position
			{"bbCallMinBetCountP","",""}, // Big blind call min bet count position
			{"bbCallMinBetStartEV","",""}, // Big blind min bet start Expected Value
			{"bbCallMinBetCountEV","",""}, // Big blind min bet count Expected Value
			{"bbStealCallCount","",""}, // Big blind steal call count
			{"bbStealCallStartP","",""}, // Big blind steal call start position
			{"bbStealCallCountP","",""}, // Big blind steal call count position
			{"bbStealCallStartEV","",""}, // Big blind steal call start Expected Value
			{"bbStealCallCountEV","",""}, // Big blind steal call count Expected Value
				{"sbFoldedToSBCount","",""}, // Small blind folded to SB count
			{"sbFoldedToSBStartP","",""}, // Small blind folded to SB start position
			{"sbFoldedToSBCountP","",""}, // Small blind folded to SB count position
			{"sbFoldedToSBStartEV","",""}, // Small blind folded to SB start Expected Value
			{"sbFoldedToSBCountEV","",""}, // Small blind folded to SB count Expected Value
			{"sbStealCallCount","",""}, // Small blind steal call count
			{"sbStealCallStartP","",""}, // Small blind steal call start position
			{"sbStealCallCountP","",""}, // Small blind steal call count position
			{"sbStealCallStartEV","",""}, // Small blind steal call start Expected Value
			{"sbStealCallCountEV","",""}, // Small blind steal call count Expected Value
			{"sbBet2StealCount","",""}, // Small blind bet 2 steal call count
			{"sbBet2StealStartP","",""}, // Small blind bet 2 steal call start position
			{"sbBet2StealCountP","",""}, // Small blind bet 2 steal call count position
			{"sbBet2StealStartEV","",""}, // Small blind bet 2 steal call start Expected Value
			{"sbBet2StealCountEV","",""}, // Small blind bet 2 steal call count Expected Value
			{"sbBBRaisedCount","",""}, // Small blind raised by SB count
			{"sbBBRaisedStartP","",""}, // Small blind raised by SB start position
			{"sbBBRaisedCountP","",""}, // Small blind raised by SB count position
			{"sbBBRaisedStartEV","",""}, // Small blind raised by SB start Expected Value
			{"sbBBRaisedCountEV","",""}, // Small blind raised by SB count Expected Value
			{"sbStealCallMinBetCount","",""}, // Small blind steal call min bet count
			{"sbStealCallMinBetOpportunity","",""}, // Small blind steal call min bet opportunity
			{"sbStealCallMinBetStartP","",""}, // Small blind steal call min bet start position
			{"sbStealCallMinBetCountP","",""}, // Small blind steal call min bet count position
			{"sbStealCallMinBetStartEV","",""}, // Small blind steal call min bet start Expected Value
			{"sbStealCallMinBetCountEV","",""}, // Small blind steal call min bet count Expected Value
			{"sbStealMinBetCount","",""}, // Small blind steal min bet count
			{"sbStealMinBetOpportunity","",""}, // Small blind steal min bet opportunity
			{"sbStealMinBetStartP","",""}, // Small blind steal min bet start position
			{"sbStealMinBetCountP","",""}, // Small blind steal min bet count position
			{"sbStealMinBetStartEV","",""}, // Small blind steal call min bet start Expected Value
			{"sbStealMinBetCountEV","",""}, // Small blind steal call min bet count Expected Value
			{"SBLimpCount = 0","",""}, //Small blind limp count

			{ " ", "","" }};
	
	
	private static final Object[][] dataValuesBU= { 
			{"player ID","",""}, // Button isolate opportunity
			{"isolateOperBU","",""}, // Button isolate opportunity
			{"minBetOperBU","",""}, // Button min bet opportunity
			{"stealOperBU","",""}, // Button steal opportunity
			{"squeezeOperBU","",""}, // Button squeeze opportunity
			{"isolateBU","",""}, // Button isolate
			{"minBetBU","",""}, // Button min bet
			{"stealBU","",""},// Button steal
			{"squeezeBU","",""}, // Button squeeze
			{"stealCount","",""}, // Button steal count
			{"stealStartP","",""}, // Button steal start position
			{"stealCountP","",""}, // Button steal count position
			{"stealStartEV","",""}, // Button steal start Expected Value
			{"stealCountEV","",""}, // Button steal count Expected Value
			{"isolateCount","",""}, // Button isolate count
			{"isolateStartP","",""}, // Button isolate start position
			{"isolateCountP","",""}, // Button isolate count position
			{"isolateStartEV","",""}, // Button isolate start Expected Value
			{"isolateCountEV","",""}, // Button isolate count Expected Value
			{"squeezeCount","",""}, // Button squeeze count
			{"squeezeStartP","",""}, // Button squeeze start position
			{"squeezeCountP","",""}, // Button squeeze count position
			{"squeezeStartEV","",""}, // Button squeeze start Expected Value
			{"squeezeCountEV","",""}, // Button squeeze count Expected Value
			{"minBetCount","",""}, // Button min bet count
			{"minBetStartP","",""}, // Button min bet start position
			{"minBetCountP","",""}, // Button min bet count position
			{"minBetStartEV","",""}, // Button min bet start Expected Value
			{"minBetCountEV","",""}, // Button min bet count Expected Value
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
				{" ","",""}	};
				private static final Object[][] dataValuesBB= { 
			{"player ID","",""},  
			{"walkCount","",""}, // Big blind walks
			{"walkOper","",""}, // Big blind walk opportunity	
			{"bbStealStartP","",""}, // Big blind steal start position
			{"bbStealCountP","",""}, // Big blind steal count position
			{"bbStealStartEV","",""}, // Big blind steal start Expected Value
			{"bbStealCountEV","",""}, // Big blind steal count Expected Value
			{"bbBet2Count","",""}, // Big blind bet 2 count
			{"bbBet2Opportunity","",""}, // Big blind bet 2 opportunity
			{"bbBet2StartP","",""}, // Big blind bet 2 start position
			{"bbBet2CountP","",""}, // Big blind bet 2 count position
			{"bbBet2StartEV","",""}, // Big blind bet 2 start Expected Value
			{"bbBet2CountEV","",""}, // Big blind bet 2 count Expected Value
			{"bbRaisedBySBCount","",""}, // Big blind raised by SB count
			{"bbRaisedBySBStartP","",""}, // Big blind raised by SB start position
			{"bbRaisedBySBCountP","",""}, // Big blind raised by SB count position
			{"bbRaisedBySBStartEV","",""}, // Big blind raised by SB start Expected Value
			{"bbRaisedBySBCountEV","",""}, // Big blind raised by SB count Expected Value
			{"bbCallMinBetCount","",""}, // Big blind call min bet count
			{"bbCallMinBetStartP","",""}, // Big blind call min bet start position
			{"bbCallMinBetCountP","",""}, // Big blind call min bet count position
			{"bbCallMinBetStartEV","",""}, // Big blind min bet start Expected Value
			{"bbCallMinBetCountEV","",""}, // Big blind min bet count Expected Value
			{"bbStealCallCount","",""}, // Big blind steal call count
			{"bbStealCallStartP","",""}, // Big blind steal call start position
			{"bbStealCallCountP","",""}, // Big blind steal call count position
			{"bbStealCallStartEV","",""}, // Big blind steal call start Expected Value
			{"bbStealCallCountEV","",""}, // Big blind steal call count Expected Value
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{"minBetOperBB","",""}, // Big blind min bet opportunity
			{"callMinBetOperBB","",""}, // Big blind call min bet opportunity
			{"steal3BetOperBB","",""}, // Big blind steal opportunity
			{"raisedBySBOperBB","",""}, // Big blind raised by SBB opportunity
			{"steal3BetMinRaiseOperBB","",""}, // Big blind steal opportunity
			{"stealCallOperBB","",""}, // Big blind steal opportunity
			{"stealCallMinRaiseOperBB","",""}, // BB steal calling min raise opportunity
				{"bet3MinOperSB","",""}, // Small blind min bet opportunity
			{"raisedByBBOperSB","",""}, // Small blind raised by BBB opportunity
			{"steal3BetOperSB","",""}, // Small blind 3 bet steal opportunity
			{"stealCallOperSB","",""}, // Small blind steal call opportunity
			{"stealCallMinRaiseSBOper","",""}, // Small blind steal calling min raise opportunity
			{"foldedToSBOper","",""}, // Folded to small blind opportunity
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{"bbStealStartP","",""}, // Big blind steal start position
			{"bbStealCountP","",""}, // Big blind steal count position
			{"bbStealStartEV","",""}, // Big blind steal start Expected Value
			{"bbStealCountEV","",""}, // Big blind steal count Expected Value
			{"bbBet2Count","",""}, // Big blind bet 2 count
			{"bbBet2Opportunity","",""}, // Big blind bet 2 opportunity
			{"bbBet2StartP","",""}, // Big blind bet 2 start position
			{"bbBet2CountP","",""}, // Big blind bet 2 count position
			{"bbBet2StartEV","",""}, // Big blind bet 2 start Expected Value
			{"bbBet2CountEV","",""}, // Big blind bet 2 count Expected Value
			{"bbRaisedBySBCount","",""}, // Big blind raised by SB count
			{"bbRaisedBySBStartP","",""}, // Big blind raised by SB start position
			{"bbRaisedBySBCountP","",""}, // Big blind raised by SB count position
			{"bbRaisedBySBStartEV","",""}, // Big blind raised by SB start Expected Value
			{"bbRaisedBySBCountEV","",""}, // Big blind raised by SB count Expected Value
			{"bbCallMinBetCount","",""}, // Big blind call min bet count
			{"bbCallMinBetStartP","",""}, // Big blind call min bet start position
			{"bbCallMinBetCountP","",""}, // Big blind call min bet count position
			{"bbCallMinBetStartEV","",""}, // Big blind min bet start Expected Value
			{"bbCallMinBetCountEV","",""}, // Big blind min bet count Expected Value
			{"bbStealCallCount","",""}, // Big blind steal call count
			{"bbStealCallStartP","",""}, // Big blind steal call start position
			{"bbStealCallCountP","",""}, // Big blind steal call count position
			{"bbStealCallStartEV","",""}, // Big blind steal call start Expected Value
			{"bbStealCallCountEV","",""}, // Big blind steal call count Expected Val
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			
			
			{" ","",""}	};
				private static final Object[][] dataValuesSB= { 
				{"player ID","",""},  
				{"sbFoldedToSBCount","",""}, // Small blind folded to SB count
			{"sbFoldedToSBStartP","",""}, // Small blind folded to SB start position
			{"sbFoldedToSBCountP","",""}, // Small blind folded to SB count position
			{"sbFoldedToSBStartEV","",""}, // Small blind folded to SB start Expected Value
			{"sbFoldedToSBCountEV","",""}, // Small blind folded to SB count Expected Value
			{"sbStealCallCount","",""}, // Small blind steal call count
			{"sbStealCallStartP","",""}, // Small blind steal call start position
			{"sbStealCallCountP","",""}, // Small blind steal call count position
			{"sbStealCallStartEV","",""}, // Small blind steal call start Expected Value
			{"sbStealCallCountEV","",""}, // Small blind steal call count Expected Value
			{"sbBet2StealCount","",""}, // Small blind bet 2 steal call count
			{"sbBet2StealStartP","",""}, // Small blind bet 2 steal call start position
			{"sbBet2StealCountP","",""}, // Small blind bet 2 steal call count position
			{"sbBet2StealStartEV","",""}, // Small blind bet 2 steal call start Expected Value
			{"sbBet2StealCountEV","",""}, // Small blind bet 2 steal call count Expected Value
			{"sbBBRaisedCount","",""}, // Small blind raised by SB count
			{"sbBBRaisedStartP","",""}, // Small blind raised by SB start position
			{"sbBBRaisedCountP","",""}, // Small blind raised by SB count position
			{"sbBBRaisedStartEV","",""}, // Small blind raised by SB start Expected Value
			{"sbBBRaisedCountEV","",""}, // Small blind raised by SB count Expected Value
			{"sbStealCallMinBetCount","",""}, // Small blind steal call min bet count
			{"sbStealCallMinBetOpportunity","",""}, // Small blind steal call min bet opportunity
			{"sbStealCallMinBetStartP","",""}, // Small blind steal call min bet start position
			{"sbStealCallMinBetCountP","",""}, // Small blind steal call min bet count position
			{"sbStealCallMinBetStartEV","",""}, // Small blind steal call min bet start Expected Value
			{"sbStealCallMinBetCountEV","",""}, // Small blind steal call min bet count Expected Value
			{"sbStealMinBetCount","",""}, // Small blind steal min bet count
			{"sbStealMinBetOpportunity","",""}, // Small blind steal min bet opportunity
			{"sbStealMinBetStartP","",""}, // Small blind steal min bet start position
			{"sbStealMinBetCountP","",""}, // Small blind steal min bet count position
			{"sbStealMinBetStartEV","",""}, // Small blind steal call min bet start Expected Value
			{"sbStealMinBetCountEV","",""}, // Small blind steal call min bet count Expected Value
			{"SBLimpCount = 0","",""}, //Small blind limp count
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{"bet3MinOperSB","",""}, // Small blind min bet opportunity
			{"raisedByBBOperSB","",""}, // Small blind raised by BBB opportunity
			{"steal3BetOperSB","",""}, // Small blind 3 bet steal opportunity
			{"stealCallOperSB","",""}, // Small blind steal call opportunity
			{"stealCallMinRaiseSBOper","",""}, // Small blind steal calling min raise opportunity
			{"foldedToSBOper","",""}, // Folded to small blind opportunity
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{"sbFoldedToSBCount","",""}, // Small blind folded to SB count
			{"sbFoldedToSBStartP","",""}, // Small blind folded to SB start position
			{"sbFoldedToSBCountP","",""}, // Small blind folded to SB count position
			{"sbFoldedToSBStartEV","",""}, // Small blind folded to SB start Expected Value
			{"sbFoldedToSBCountEV","",""}, // Small blind folded to SB count Expected Value
			{"sbStealCallCount","",""}, // Small blind steal call count
			{"sbStealCallStartP","",""}, // Small blind steal call start position
			{"sbStealCallCountP","",""}, // Small blind steal call count position
			{"sbStealCallStartEV","",""}, // Small blind steal call start Expected Value
			{"sbStealCallCountEV","",""}, // Small blind steal call count Expected Value
			{"sbBet2StealCount","",""}, // Small blind bet 2 steal call count
			{"sbBet2StealStartP","",""}, // Small blind bet 2 steal call start position
			{"sbBet2StealCountP","",""}, // Small blind bet 2 steal call count position
			{"sbBet2StealStartEV","",""}, // Small blind bet 2 steal call start Expected Value
			{"sbBet2StealCountEV","",""}, // Small blind bet 2 steal call count Expected Value
			{"sbBBRaisedCount","",""}, // Small blind raised by SB count
			{"sbBBRaisedStartP","",""}, // Small blind raised by SB start position
			{"sbBBRaisedCountP","",""}, // Small blind raised by SB count position
			{"sbBBRaisedStartEV","",""}, // Small blind raised by SB start Expected Value
			{"sbBBRaisedCountEV","",""}, // Small blind raised by SB count Expected Value
			{"sbStealCallMinBetCount","",""}, // Small blind steal call min bet count
			{"sbStealCallMinBetOpportunity","",""}, // Small blind steal call min bet opportunity
			{"sbStealCallMinBetStartP","",""}, // Small blind steal call min bet start position
			{"sbStealCallMinBetCountP","",""}, // Small blind steal call min bet count position
			{"sbStealCallMinBetStartEV","",""}, // Small blind steal call min bet start Expected Value
			{"sbStealCallMinBetCountEV","",""}, // Small blind steal call min bet count Expected Value
			{"sbStealMinBetCount","",""}, // Small blind steal min bet count
			{"sbStealMinBetOpportunity","",""}, // Small blind steal min bet opportunity
			{"sbStealMinBetStartP","",""}, // Small blind steal min bet start position
			{"sbStealMinBetCountP","",""}, // Small blind steal min bet count position
			{"sbStealMinBetStartEV","",""}, // Small blind steal call min bet start Expected Value
			{"sbStealMinBetCountEV","",""}, // Small blind steal call min bet count Expected Value
			{"SBLimpCount = 0","",""}, //Small blind limp count
						{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{" ","",""},
			{ " ", "","" }};
	

	// @formatter:off

		
		
		private static final Object[][] dataOperRp  = { 
				{ "Preflop   ------", "", "", "", "", "", "", "", "" },
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
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
				{ "Flop ------", "", "", "", "", "", "", "", "" },
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
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
				{ "Turn  ------", "", "", "", "", "", "", "", "" },
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
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
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
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
				{ "", "", "", "", "", "", "", "", "" } };

		private static final Object[][] dataActionsRp = {
				{ "Preflop ------", "", "", "", "", "", "", "", "" },
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
				{ "Bet1 ", "", "", "", "", "", "", "", "" },
				{ "CallBet1(Limp)", "", "", "", "", "", "", "", "" },
				{ "Bet2", "", "", "", "", "", "", "", "" },
				{ "CallBet2", "", "", "", "", "", "", "", "" },
				{ "3 Bet", "", "", "", "", "", "", "", "" }, 
				{ "Call 3 Bet", "", "", "", "", "", "", "", "" },
				{ "4 Bet", "", "", "", "", "", "", "", "" }, 
				{ "Call 4 Bet", "", "", "", "", "", "", "", "" },
				{ "Allin", "", "", "", "", "", "", "", "" },
{				 "Call Allin", "", "", "", "", "", "", "", "" },
				{ "cBet", "", "", "", "", "", "", "", "" }, 
				{ "Barrell", "", "", "", "", "", "", "", "" },

				{ "", "", "", "", "", "", "", "", "" },


				{ "", "", "", "", "", "", "", "", "" },
				{ "Flop Orbit 1 ------", "", "", "", "", "", "", "", "" },
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
				{ "Bet1 ", "", "", "", "", "", "", "", "" },
				{ "CallBet1(Limp)", "", "", "", "", "", "", "", "" },
				{ "Bet2", "", "", "", "", "", "", "", "" },
				{ "CallBet2", "", "", "", "", "", "", "", "" },
				{ "3 Bet", "", "", "", "", "", "", "", "" }, 
				{ "Call 3 Bet", "", "", "", "", "", "", "", "" },
				{ "4 Bet", "", "", "", "", "", "", "", "" }, 
				{ "Call 4 Bet", "", "", "", "", "", "", "", "" },
				{ "Allin", "", "", "", "", "", "", "", "" },
{				 "Call Allin", "", "", "", "", "", "", "", "" },
				{ "cBet", "", "", "", "", "", "", "", "" }, 
				{ "Barrell", "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "", "" },
				

				{ "", "", "", "", "", "", "", "", "" },
				{ "Turn ------", "", "", "", "", "", "", "", "" },
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
				{ "Bet1 ", "", "", "", "", "", "", "", "" },
				{ "CallBet1(Limp)", "", "", "", "", "", "", "", "" },
				{ "Bet2", "", "", "", "", "", "", "", "" },
				{ "CallBet2", "", "", "", "", "", "", "", "" },
				{ "3 Bet", "", "", "", "", "", "", "", "" }, 
				{ "Call 3 Bet", "", "", "", "", "", "", "", "" },
				{ "4 Bet", "", "", "", "", "", "", "", "" }, 
				{ "Call 4 Bet", "", "", "", "", "", "", "", "" },
				{ "Allin", "", "", "", "", "", "", "", "" },
{				 "Call Allin", "", "", "", "", "", "", "", "" },
				{ "cBet", "", "", "", "", "", "", "", "" }, 
				{ "Barrell", "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "", "" },
				
				{ "River  ------", "", "", "", "", "", "", "", "" },
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
				{ "Bet1 ", "", "", "", "", "", "", "", "" },
				{ "CallBet1(Limp)", "", "", "", "", "", "", "", "" },
				{ "Bet2", "", "", "", "", "", "", "", "" },
				{ "CallBet2", "", "", "", "", "", "", "", "" },
				{ "3 Bet", "", "", "", "", "", "", "", "" }, 
				{ "Call 3 Bet", "", "", "", "", "", "", "", "" },
				{ "4 Bet", "", "", "", "", "", "", "", "" }, 
				{ "Call 4 Bet", "", "", "", "", "", "", "", "" },
				{ "Allin", "", "", "", "", "", "", "", "" },
{				 "Call Allin", "", "", "", "", "", "", "", "" },
				{ "cBet", "", "", "", "", "", "", "", "" }, 
				{ "Barrell", "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "", "" },
				{ "", "", "", "", "", "", "", "", "" } };

		private static final Object[][] dataPercentageRp = {
				{ "Preflop   ------", "", "", "", "", "", "", "", "" },
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
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
				{ "Flop------", "", "", "", "", "", "", "", "" },
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
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
				
				{ "Turn  Orbit 0 ------", "", "", "", "", "", "", "", "" },
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
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
				{ "Turn   ------", "", "", "", "", "", "", "", "" },
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
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
				
				{ "River    ------", "", "", "", "", "", "", "", "" },
				{ "Fold", "", "", "", "", "", "", "", "" }, 
				{ "Check", "", "", "", "", "", "", "", "" },
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

		
		// @formatter:off

		private static final Object[][] dataButtonBlindsX = { 
				{ "Button***", "", "", "", "", "", "", "", "" },
				{ "Limp", "", "", "", "", "", "", "", "" }, 
				{"Steal", "", "", "", "", "", "", "", "" },
				{ "Isolate", "", "", "", "", "", "", "", "" },
				{ "Squeeze", "", "", "", "", "", "", "", "" },
				{ "Min Bet", "", "", "", "", "", "", "", "" },

				{ "Big Blind***", "", "", "", "", "", "", "", "" },
				{ "Steal Call", "", "", "", "", "", "", "", "" }, 
				{ "Steal", "", "", "", "", "", "", "", "" },
				{ "Raised by SB", "", "", "", "", "", "", "", "" },
				{ "Call Min Bet", "", "", "", "", "", "", "", "" },
				{ "Min 3 Bet", "", "", "", "", "", "", "", "" },

				{ "Small Blind***", "", "", "", "", "", "", "", "" },
				{ "Folded to SB", "", "", "", "", "", "", "", "" },
				{ "Steal Call", "", "", "", "", "", "", "", "" },
				{ "Steal 3 Bet", "", "", "", "", "", "", "", "" },
				{ "Call Min Bet", "", "", "", "", "", "", "", "" },
				{ "Raised by BB", "", "", "", "", "", "", "", "" },
				{ "Min Call", "", "", "", "", "", "", "", "" },
				{ "Min 3 Bet", "", "", "", "", "", "", "", "" } };

		
		private static final Object[][] dataActionsRpX = {
				{ "Preflop  ------", "", "", "", "", "", "", "", "" },
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
				
				{ "Flop   ------", "", "", "", "", "", "", "", "" },
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
				
				
				{ "Turn  ------", "", "", "", "", "", "", "", "" },
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
				 
				{ "River    ------", "", "", "", "", "", "", "", "" },
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
				{ "", "", "", "", "", "", "", "", "" }, 
				{ "", "", "", "", "", "", "", "", "" } };

		private static final Object[][] dataPercentageRpX = {
				{ "Preflop  ------", "", "", "", "", "", "", "", "" },
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
				
				{ "Flop Orbit 0 ------", "", "", "", "", "", "", "", "" },
				{ "Check", "", "", "", "", "", "", "", "" }, 
				{ "Fold", "", "", "", "", "", "", "", "" },
				{ "Bet1 ", "", "", "", "", "", "", "", "" }, 
				{ "CallBet1", "", "", "", "", "", "", "", "" },
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
				{ "Flop   ------", "", "", "", "", "", "", "", "" },
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
				
				
				{ "Turn   ------", "", "", "", "", "", "", "", "" },
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

				{ "", "", "", "", "", "", "", "", "" },
				
				{ "River  ------", "", "", "", "", "", "", "", "" },
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

				{ "", "", "", "", "", "", "", "", "" }, 
				{ "", "", "", "", "", "", "", "", "" } };
		

	// @formatter:on

	private static boolean valuesGUI = false;

	static void values(Players play) {
		if (!valuesGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 16);
			final var frame = new JFrame("Values " + Format.format(play.playerID));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(50, 50);
			frame.setPreferredSize(new Dimension(600, 1250));
			final var table = new JTable(dataValues, columnsValues);
			table.setFont(ff);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			table.getColumnModel().getColumn(1).setPreferredWidth(80);
			table.getColumnModel().getColumn(2).setPreferredWidth(400);
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
			valuesGUI = true;
		}
		int saveRow = 0;
		int row = 0;
		dataValues[row][1] = Format.format(play.playerID);
		dataValues[row++][2] = "Player ID";
		dataValues[row][1] = Format.format(play.handsPlayed);
		dataValues[row++][2] = "Number of hands played";
		dataValues[row][1] = Format.format(play.playerType);
		dataValues[row++][2] = "Player type";
		dataValues[row][1] = Format.format(play.wtsdFlopCount);
		dataValues[row++][2] = "Went to Showdown";
		dataValues[row][1] = Format.format(play.wonShowdownCount);
		dataValues[row++][2] = "wonShowdownCount";
		dataValues[row][1] = Format.format(play.wonCount);
		dataValues[row++][2] = "wonCount";
		dataValues[row][1] = Format.format(play.wsdCount);
		dataValues[row++][2] = "Won at Showdown";
		dataValues[row][1] = Format.format(play.wtsdFlopCount);
		dataValues[row++][2] = "Went to showdown if saw preflop";
		dataValues[row][1] = Format.format(play.wtsdPreflopCount);
		dataValues[row++][2] = "Went to showdown if saw flop";
		dataValues[row][1] = Format.format(play.wtsdTurnCount);
		dataValues[row++][2] = "Went to showdown if saw turn";
		dataValues[row][1] = Format.format(play.wtsdRiverCount);
		dataValues[row++][2] = "Went to showdown if saw river";
		dataValues[row][1] = Format.format(play.wwsfCount);
		dataValues[row++][2] = "Won when saw Preflop";
		dataValues[row][1] = Format.format(play.wwspCount);
		dataValues[row++][2] = "Won when saw Flop";
		dataValues[row][1] = Format.format(play.wwstCount);
		dataValues[row++][2] = "Won when saw Turn";
		dataValues[row][1] = Format.format(play.wwsrCount);
		dataValues[row++][2] = "Won when saw River";
		dataValues[row][1] = Format.format(play.showdownCount);
		dataValues[row++][2] = "Showdown count";
		dataValues[row][1] = Format.format(play.showdownWonCount);
		dataValues[row++][2] = "TODO";
		dataValues[row][1] = Format.format(play.raiseCount1);
		dataValues[row++][2] = "Raise count";
		dataValues[row][1] = Format.format(play.betCount1);
		dataValues[row++][2] = "Bet count";
		dataValues[row][1] = Format.format(play.walkCount);
		dataValues[row++][2] = "Walk count";
		dataValues[row][1] = Format.format(play.barrellFlopTurnCount);
		dataValues[row++][2] = "Raise then raise turn";
		dataValues[row][1] = Format.format(play.barrellFlopTurnRiverCount);
		dataValues[row++][2] = "Raise then raise turn then raise river";
		dataValues[row][1] = Format.format(play.barrellTurnRiverCount);
		dataValues[row++][2] = "Raise then raise turn then raise river";
		dataValues[row][1] = Format.format(play.barrellPreflopFlopTurnRiverCount);
		dataValues[row++][2] = "Raise 4 streets";
		dataValues[row][1] = Format.format(play.walkOper);
		dataValues[row++][2] = "BB can not raise because no other raise";
		dataValues[row][1] = Format.format(play.isolateOperBU);
		dataValues[row++][2] = "Button isolate opportunity";
		dataValues[row][1] = Format.format(play.minBetOperBU);
		dataValues[row++][2] = "Button min bet opportunity";
		dataValues[row][1] = Format.format(play.stealOperBU);
		dataValues[row++][2] = "Button steal opportunity";
		dataValues[row][1] = Format.format(play.squeezeOperBU);
		dataValues[row++][2] = "Button squeeze opportunity";
		dataValues[row][1] = Format.format(play.minBetOperBB);
		dataValues[row++][2] = "Big blind min bet opopportunity";
		dataValues[row][1] = Format.format(play.callMinBetOperBB);
		dataValues[row++][2] = "Big blind call min bet opportunity";
		dataValues[row][1] = Format.format(play.steal3BetOperBB);
		dataValues[row++][2] = "Big blind steal opportunity";
		dataValues[row][1] = Format.format(play.raisedBySBOperBB);
		dataValues[row++][2] = "Big blind raised by SBB opportunity";
		dataValues[row][1] = Format.format(play.steal3BetMinRaiseOperBB);
		dataValues[row++][2] = "Big blind steal opportunity";
		dataValues[row][1] = Format.format(play.stealCallOperBB);
		dataValues[row++][2] = "Big blind steal opportunity";
		dataValues[row][1] = Format.format(play.stealCallMinRaiseOperBB);
		dataValues[row++][2] = "BB steal calling min raise opportunity";
		dataValues[row][1] = Format.format(play.bet3MinOperSB);
		dataValues[row++][2] = "Small blind min bet opportunity";
		dataValues[row][1] = Format.format(play.raisedByBBOperSB);
		dataValues[row++][2] = "Small blind raised by BBB opportunity";
		dataValues[row][1] = Format.format(play.steal3BetOperSB);
		dataValues[row++][2] = "Small blind 3 bet steal opportunity";
		dataValues[row][1] = Format.format(play.stealCallOperSB);
		dataValues[row++][2] = "Small blind steal call opportunity";
		dataValues[row][1] = Format.format(play.stealCallMinRaiseSBOper);
		dataValues[row++][2] = "Small blind steal calling min raise opportunity";
		dataValues[row][1] = Format.format(play.foldedToSBOper);
		dataValues[row++][2] = "Folded to small blind opportunity";
		dataValues[row][1] = Format.format(play.walk);
		dataValues[row++][2] = "BB can not raise because no other raise";
		dataValues[row][1] = Format.format(play.isolateBU);
		dataValues[row++][2] = "Button isolate";
		dataValues[row][1] = Format.format(play.minBetBU);
		dataValues[row++][2] = "Button min bet";
		dataValues[row][1] = Format.format(play.stealBU);
		dataValues[row++][2] = "Button steal";
		dataValues[row][1] = Format.format(play.squeezeBU);
		dataValues[row++][2] = "Button squeeze";
		dataValues[row][1] = Format.format(play.minBetBB);
		dataValues[row++][2] = "Big blind min bet";
		dataValues[row][1] = Format.format(play.callMinBetBB);
		dataValues[row++][2] = "Big blind call min bet";
		dataValues[row][1] = Format.format(play.steal3BetBB);
		dataValues[row++][2] = "Big blind steal";
		dataValues[row][1] = Format.format(play.raisedBySBBB);
		dataValues[row++][2] = "Big blind raised by SB BB";
		dataValues[row][1] = Format.format(play.steal3BetMinRaiseBB);
		dataValues[row++][2] = "Big blind steal";
		dataValues[row][1] = Format.format(play.stealCallBB);
		dataValues[row++][2] = "Big blind steal";
		dataValues[row][1] = Format.format(play.stealCallMinRaiseBB);
		dataValues[row++][2] = "BB steal calling min raise";
		dataValues[row][1] = Format.format(play.bet3MinSB);
		dataValues[row++][2] = "Small Blind bet 3 minn bet";
		dataValues[row][1] = Format.format(play.raisedByBBSB);
		dataValues[row++][2] = "Small blind raised by BB";
		dataValues[row][1] = Format.format(play.steal3BetSB);
		dataValues[row++][2] = "Small blind steal";
		dataValues[row][1] = Format.format(play.stealCallSB);
		dataValues[row++][2] = "Small blind steal call";
		dataValues[row][1] = Format.format(play.stealCallMinRaiseSB);
		dataValues[row++][2] = "SB steal calling min raise";
		dataValues[row][1] = Format.format(play.foldedToSB);
		dataValues[row++][2] = "SB folded";
		dataValues[row][1] = Format.format(play.stealCount);
		dataValues[row++][2] = "Button steal count";
		dataValues[row][1] = Format.format(play.stealStartP);
		dataValues[row++][2] = "Button steal start position";
		dataValues[row][1] = Format.format(play.stealCountP);
		dataValues[row++][2] = "Button steal count position";
		dataValues[row][1] = Format.format(play.stealStartEV);
		dataValues[row++][2] = "Button steal start Expected Value";
		dataValues[row][1] = Format.format(play.stealCountEV);
		dataValues[row++][2] = "Button steal count Expected Value";
		dataValues[row][1] = Format.format(play.isolateCount);
		dataValues[row++][2] = "Button isolate count";
		dataValues[row][1] = Format.format(play.isolateStartP);
		dataValues[row++][2] = "Button isolate start position";
		dataValues[row][1] = Format.format(play.isolateCountP);
		dataValues[row++][2] = "Button isolate count position";
		dataValues[row][1] = Format.format(play.isolateStartEV);
		dataValues[row++][2] = "Button isolate start Expected Value";
		dataValues[row][1] = Format.format(play.isolateCountEV);
		dataValues[row++][2] = "Button isolate count Expected Value";
		dataValues[row][1] = Format.format(play.squeezeCount);
		dataValues[row++][2] = "Button squeeze count";
		dataValues[row][1] = Format.format(play.squeezeStartP);
		dataValues[row++][2] = "Button squeeze start position";
		dataValues[row][1] = Format.format(play.squeezeCountP);
		dataValues[row++][2] = "Button squeeze count position";
		dataValues[row][1] = Format.format(play.squeezeStartEV);
		dataValues[row++][2] = "Button squeeze start Expected Value";
		dataValues[row][1] = Format.format(play.squeezeCountEV);
		dataValues[row++][2] = "Button squeeze count Expected Value";
		dataValues[row][1] = Format.format(play.minBetCount);
		dataValues[row++][2] = "Button min bet count";
		dataValues[row][1] = Format.format(play.minBetStartP);
		dataValues[row++][2] = "Button min bet start position";
		dataValues[row][1] = Format.format(play.minBetCountP);
		dataValues[row++][2] = "Button min bet count position";
		dataValues[row][1] = Format.format(play.minBetStartEV);
		dataValues[row++][2] = "Button min bet start Expected Value";
		dataValues[row][1] = Format.format(play.minBetCountEV);
		dataValues[row++][2] = "Button min bet count Expected Value";
		dataValues[row][1] = Format.format(play.bbStealCount);
		dataValues[row++][2] = "Big blind steal count";
		dataValues[row][1] = Format.format(play.bbStealStartP);
		dataValues[row++][2] = "Big blind steal start position";
		dataValues[row][1] = Format.format(play.bbStealCountP);
		dataValues[row++][2] = "Big blind steal count position";
		dataValues[row][1] = Format.format(play.bbStealStartEV);
		dataValues[row++][2] = "Big blind steal start Expected Value";
		dataValues[row][1] = Format.format(play.bbStealCountEV);
		dataValues[row++][2] = "Big blind steal count Expected Value";
		dataValues[row][1] = Format.format(play.bbBet2Count);
		dataValues[row++][2] = "Big blind bet 2 count";
		dataValues[row][1] = Format.format(play.bbBet2Opportunity);
		dataValues[row++][2] = "Big blind bet 2 opportunity";
		dataValues[row][1] = Format.format(play.bbBet2StartP);
		dataValues[row++][2] = "Big blind bet 2 start position";
		dataValues[row][1] = Format.format(play.bbBet2CountP);
		dataValues[row++][2] = "Big blind bet 2 count position";
		dataValues[row][1] = Format.format(play.bbBet2StartEV);
		dataValues[row++][2] = "Big blind bet 2 start Expected Value";
		dataValues[row][1] = Format.format(play.bbBet2CountEV);
		dataValues[row++][2] = "Big blind bet 2 count Expected Value";
		dataValues[row][1] = Format.format(play.bbRaisedBySBCount);
		dataValues[row++][2] = "Big blind raised by SB count";
		dataValues[row][1] = Format.format(play.bbRaisedBySBStartP);
		dataValues[row++][2] = "Big blind raised by SB start position";
		dataValues[row][1] = Format.format(play.bbRaisedBySBCountP);
		dataValues[row++][2] = "Big blind raised by SB count position";
		dataValues[row][1] = Format.format(play.bbRaisedBySBStartEV);
		dataValues[row++][2] = "Big blind raised by SB start Expected Value";
		dataValues[row][1] = Format.format(play.bbRaisedBySBCountEV);
		dataValues[row++][2] = "Big blind raised by SB count Expected Value";
		dataValues[row][1] = Format.format(play.bbCallMinBetCount);
		dataValues[row++][2] = "Big blind call min bet count";
		dataValues[row][1] = Format.format(play.bbCallMinBetStartP);
		dataValues[row++][2] = "Big blind call min bet start position";
		dataValues[row][1] = Format.format(play.bbCallMinBetCountP);
		dataValues[row++][2] = "Big blind call min bet count position";
		dataValues[row][1] = Format.format(play.bbCallMinBetStartEV);
		dataValues[row++][2] = "Big blind min bet start Expected Value";
		dataValues[row][1] = Format.format(play.bbCallMinBetCountEV);
		dataValues[row++][2] = "Big blind min bet count Expected Value";
		dataValues[row][1] = Format.format(play.bbStealCallCount);
		dataValues[row++][2] = "Big blind steal call count";
		dataValues[row][1] = Format.format(play.bbStealCallStartP);
		dataValues[row++][2] = "Big blind steal call start position";
		dataValues[row][1] = Format.format(play.bbStealCallCountP);
		dataValues[row++][2] = "Big blind steal call count position";
		dataValues[row][1] = Format.format(play.bbStealCallStartEV);
		dataValues[row++][2] = "Big blind steal call start Expected Value";
		dataValues[row][1] = Format.format(play.bbStealCallCountEV);
		dataValues[row++][2] = "Big blind steal call count Expected Value";
		dataValues[row][1] = Format.format(play.sbFoldedToSBCount);
		dataValues[row++][2] = "Small blind folded to SB count";
		dataValues[row][1] = Format.format(play.sbFoldedToSBStartP);
		dataValues[row++][2] = "Small blind folded to SB start position";
		dataValues[row][1] = Format.format(play.sbFoldedToSBCountP);
		dataValues[row++][2] = "Small blind folded to SB count position";
		dataValues[row][1] = Format.format(play.sbFoldedToSBStartEV);
		dataValues[row++][2] = "Small blind folded to SB start Expected Value";
		dataValues[row][1] = Format.format(play.sbFoldedToSBCountEV);
		dataValues[row++][2] = "Small blind folded to SB count Expected Value";
		dataValues[row][1] = Format.format(play.sbStealCallCount);
		dataValues[row++][2] = "Small blind steal call count";
		dataValues[row][1] = Format.format(play.sbStealCallStartP);
		dataValues[row++][2] = "Small blind steal call start position";
		dataValues[row][1] = Format.format(play.sbStealCallCountP);
		dataValues[row++][2] = "Small blind steal call count position";
		dataValues[row][1] = Format.format(play.sbStealCallStartEV);
		dataValues[row++][2] = "Small blind steal call start Expected Value";
		dataValues[row][1] = Format.format(play.sbStealCallCountEV);
		dataValues[row++][2] = "Small blind steal call count Expected Value";
		dataValues[row][1] = Format.format(play.sbBet2StealCount);
		dataValues[row++][2] = "Small blind bet 2 steal call count";
		dataValues[row][1] = Format.format(play.sbBet2StealStartP);
		dataValues[row++][2] = "Small blind bet 2 steal call start position";
		dataValues[row][1] = Format.format(play.sbBet2StealCountP);
		dataValues[row++][2] = "Small blind bet 2 steal call count position";
		dataValues[row][1] = Format.format(play.sbBet2StealStartEV);
		dataValues[row++][2] = "Small blind bet 2 steal call start Expected Value";
		dataValues[row][1] = Format.format(play.sbBet2StealCountEV);
		dataValues[row++][2] = "Small blind bet 2 steal call count Expected Value";
		dataValues[row][1] = Format.format(play.sbBBRaisedCount);
		dataValues[row++][2] = "Small blind raised by SB count";
		dataValues[row][1] = Format.format(play.sbBBRaisedStartP);
		dataValues[row++][2] = "Small blind raised by SB start position";
		dataValues[row][1] = Format.format(play.sbBBRaisedCountP);
		dataValues[row++][2] = "Small blind raised by SB count position";
		dataValues[row][1] = Format.format(play.sbBBRaisedStartEV);
		dataValues[row++][2] = "Small blind raised by SB start Expected Value";
		dataValues[row][1] = Format.format(play.sbBBRaisedCountEV);
		dataValues[row++][2] = "Small blind raised by SB count Expected Value";
		dataValues[row][1] = Format.format(play.sbStealCallMinBetCount);
		dataValues[row++][2] = "Small blind steal call min bet count";
		dataValues[row][1] = Format.format(play.sbStealCallMinBetOpportunity);
		dataValues[row++][2] = "Small blind steal call min bet opportunity";
		dataValues[row][1] = Format.format(play.sbStealCallMinBetStartP);
		dataValues[row++][2] = "Small blind steal call min bet start position";
		dataValues[row][1] = Format.format(play.sbStealCallMinBetCountP);
		dataValues[row++][2] = "Small blind steal call min bet count position";
		dataValues[row][1] = Format.format(play.sbStealCallMinBetStartEV);
		dataValues[row++][2] = "Small blind steal call min bet start Expected Value";
		dataValues[row][1] = Format.format(play.sbStealCallMinBetCountEV);
		dataValues[row++][2] = "Small blind steal call min bet count Expected Value";
		dataValues[row][1] = Format.format(play.sbStealMinBetCount);
		dataValues[row++][2] = "Small blind steal min bet count";
		dataValues[row][1] = Format.format(play.sbStealMinBetOpportunity);
		dataValues[row++][2] = "Small blind steal min bet opportunity";
		dataValues[row][1] = Format.format(play.sbStealMinBetStartP);
		dataValues[row++][2] = "Small blind steal min bet start position";
		dataValues[row][1] = Format.format(play.sbStealMinBetCountP);
		dataValues[row++][2] = "Small blind steal min bet count position";
		dataValues[row][1] = Format.format(play.sbStealMinBetStartEV);
		dataValues[row++][2] = "Small blind steal call min bet start Expected Value";
		dataValues[row][1] = Format.format(play.sbStealMinBetCountEV);
		dataValues[row++][2] = "Small blind steal call min bet count Expected Value";
		dataValues[row][1] = Format.format(play.SBLimpCount = 0);
		dataValues[row++][2] = "Small blind limp count";
	}

	private static boolean valuesBUGUI = false;

	static void valuesBU(Players play) {
		if (!valuesBUGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 16);
			final var frame = new JFrame("Values Button " + Format.format(play.playerID));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(80, 80);
			frame.setPreferredSize(new Dimension(800, 800));
			final var table = new JTable(dataValuesBU, columnsValues);
			table.setFont(ff);
			table.setRowHeight(40);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(2).setPreferredWidth(180);

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
			valuesBUGUI = true;
		}
		int saveRow = 0;
		int row = 0;
		dataValuesBU[row][1] = Format.format(play.playerID);
		dataValuesBU[row][1] = Format.format(play.minBetOperBU);
		dataValuesBU[row++][2] = "Button isolate opportunity";
		dataValuesBU[row][1] = Format.format(play.minBetOperBU);
		dataValuesBU[row++][2] = "Button min bet opportunity";
		dataValuesBU[row][1] = Format.format(play.stealOperBU);
		dataValuesBU[row++][2] = "Button steal opportunity";
		dataValuesBU[row][1] = Format.format(play.squeezeOperBU);
		dataValuesBU[row++][2] = "Button squeeze opportunity";
		dataValuesBU[row][1] = Format.format(play.minBetOperBB);
		dataValuesBU[row][1] = Format.format(play.isolateBU);
		dataValuesBU[row++][2] = "Button isolate";
		dataValuesBU[row][1] = Format.format(play.minBetBU);
		dataValuesBU[row++][2] = "Button min bet";
		dataValuesBU[row][1] = Format.format(play.stealBU);
		dataValuesBU[row++][2] = "Button steal";
		dataValuesBU[row][1] = Format.format(play.squeezeBU);
		dataValuesBU[row++][2] = "Button squeeze";
		dataValuesBU[row][1] = Format.format(play.minBetBB);

		dataValuesBU[row++][2] = "Button steal count";
		dataValuesBU[row][1] = Format.format(play.stealStartP);
		dataValuesBU[row++][2] = "Button steal start position";
		dataValuesBU[row][1] = Format.format(play.stealCountP);
		dataValuesBU[row++][2] = "Button steal count position";
		dataValuesBU[row][1] = Format.format(play.stealStartEV);
		dataValuesBU[row++][2] = "Button steal start Expected Value";
		dataValuesBU[row][1] = Format.format(play.stealCountEV);
		dataValuesBU[row++][2] = "Button steal count Expected Value";
		dataValuesBU[row][1] = Format.format(play.isolateCount);
		dataValuesBU[row++][2] = "Button isolate count";
		dataValuesBU[row][1] = Format.format(play.isolateStartP);
		dataValuesBU[row++][2] = "Button isolate start position";
		dataValuesBU[row][1] = Format.format(play.isolateCountP);
		dataValuesBU[row++][2] = "Button isolate count position";
		dataValuesBU[row][1] = Format.format(play.isolateStartEV);
		dataValuesBU[row++][2] = "Button isolate start Expected Value";
		dataValuesBU[row][1] = Format.format(play.isolateCountEV);
		dataValuesBU[row++][2] = "Button isolate count Expected Value";
		dataValuesBU[row][1] = Format.format(play.squeezeCount);
		dataValuesBU[row++][2] = "Button squeeze count";
		dataValuesBU[row][1] = Format.format(play.squeezeStartP);
		dataValuesBU[row++][2] = "Button squeeze start position";
		dataValuesBU[row][1] = Format.format(play.squeezeCountP);
		dataValuesBU[row++][2] = "Button squeeze count position";
		dataValuesBU[row][1] = Format.format(play.squeezeStartEV);
		dataValuesBU[row++][2] = "Button squeeze start Expected Value";
		dataValuesBU[row][1] = Format.format(play.squeezeCountEV);
		dataValuesBU[row++][2] = "Button squeeze count Expected Value";
		dataValuesBU[row][1] = Format.format(play.minBetCount);
		dataValuesBU[row++][2] = "Button min bet count";
		dataValuesBU[row][1] = Format.format(play.minBetStartP);
		dataValuesBU[row++][2] = "Button min bet start position";
		dataValuesBU[row][1] = Format.format(play.minBetCountP);
		dataValuesBU[row++][2] = "Button min bet count position";
		dataValuesBU[row][1] = Format.format(play.minBetStartEV);
		dataValuesBU[row++][2] = "Button min bet start Expected Value";
		dataValuesBU[row][1] = Format.format(play.minBetCountEV);
		dataValuesBU[row++][2] = "Button min bet count Expected Value";
	}

	private static boolean valuesBBGUI = false;

	static void valuesBB(Players play) {
		if (!valuesBBGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 16);
			final var frame = new JFrame("Values Big Blind " + Format.format(play.playerID));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(110, 110);
			frame.setPreferredSize(new Dimension(800, 1180));
			final var table = new JTable(dataValuesBB, columnsValues);
			table.setFont(ff);
			table.setRowHeight(40);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(2).setPreferredWidth(180);

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
			valuesBBGUI = true;
		}
		int saveRow = 0;
		int row = 0;
		dataValuesBB[row][1] = Format.format(play.playerID);
		dataValuesBB[row][1] = Format.format(play.walk);
		dataValuesBB[row++][2] = "Big blind walk count";
		dataValuesBB[row][1] = Format.format(play.walkOper);
		dataValuesBB[row++][2] = "Big blind walk mopportunity";
		dataValuesBB[row][1] = Format.format(play.bbStealCount);
		dataValuesBB[row++][2] = "Big blind steal counte";
		dataValuesBB[row][1] = Format.format(play.minBetOperBB);
		dataValuesBB[row++][2] = "Big blind min bet opopportunity";
		dataValuesBB[row][1] = Format.format(play.callMinBetOperBB);
		dataValuesBB[row++][2] = "Big blind call min bet opportunity";
		dataValuesBB[row][1] = Format.format(play.steal3BetOperBB);
		dataValuesBB[row++][2] = "Big blind steal opportunity";
		dataValuesBB[row][1] = Format.format(play.raisedBySBOperBB);
		dataValuesBB[row++][2] = "Big blind raised by SBB opportunity";
		dataValuesBB[row][1] = Format.format(play.steal3BetMinRaiseOperBB);
		dataValuesBB[row++][2] = "Big blind steal opportunity";
		dataValuesBB[row][1] = Format.format(play.stealCallOperBB);
		dataValuesBB[row++][2] = "Big blind steal opportunity";
		dataValuesBB[row][1] = Format.format(play.stealCallMinRaiseOperBB);
		dataValuesBB[row++][2] = "BB steal calling min raise opportunity";
		dataValuesBB[row][1] = Format.format(play.bet3MinOperSB);
		dataValuesBB[row++][2] = "BB can not raise because no other raise";
		dataValuesBB[row][1] = Format.format(play.isolateBU);
		dataValuesBB[row][1] = Format.format(play.minBetBB);
		dataValuesBB[row++][2] = "Big blind min bet";
		dataValuesBB[row][1] = Format.format(play.callMinBetBB);
		dataValuesBB[row++][2] = "Big blind call min bet";
		dataValuesBB[row][1] = Format.format(play.steal3BetBB);
		dataValuesBB[row++][2] = "Big blind steal";
		dataValuesBB[row][1] = Format.format(play.raisedBySBBB);
		dataValuesBB[row++][2] = "Big blind raised by SB BB";
		dataValuesBB[row][1] = Format.format(play.steal3BetMinRaiseBB);
		dataValuesBB[row++][2] = "Big blind steal";
		dataValuesBB[row][1] = Format.format(play.stealCallBB);
		dataValuesBB[row++][2] = "Big blind steal";
		dataValuesBB[row][1] = Format.format(play.stealCallMinRaiseBB);
		dataValuesBB[row++][2] = "BB steal calling min raise";
		dataValuesBB[row][1] = Format.format(play.bet3MinSB);

		dataValuesBB[row++][2] = "Big blind steal count";
		dataValuesBB[row][1] = Format.format(play.bbStealStartP);
		dataValuesBB[row++][2] = "Big blind steal start position";
		dataValuesBB[row][1] = Format.format(play.bbStealCountP);
		dataValuesBB[row++][2] = "Big blind steal count position";
		dataValuesBB[row][1] = Format.format(play.bbStealStartEV);
		dataValuesBB[row++][2] = "Big blind steal start Expected Value";
		dataValuesBB[row][1] = Format.format(play.bbStealCountEV);
		dataValuesBB[row++][2] = "Big blind steal count Expected Value";
		dataValuesBB[row][1] = Format.format(play.bbBet2Count);
		dataValuesBB[row++][2] = "Big blind bet 2 count";
		dataValuesBB[row][1] = Format.format(play.bbBet2Opportunity);
		dataValuesBB[row++][2] = "Big blind bet 2 opportunity";
		dataValuesBB[row][1] = Format.format(play.bbBet2StartP);
		dataValuesBB[row++][2] = "Big blind bet 2 start position";
		dataValuesBB[row][1] = Format.format(play.bbBet2CountP);
		dataValuesBB[row++][2] = "Big blind bet 2 count position";
		dataValuesBB[row][1] = Format.format(play.bbBet2StartEV);
		dataValuesBB[row++][2] = "Big blind bet 2 start Expected Value";
		dataValuesBB[row][1] = Format.format(play.bbBet2CountEV);
		dataValuesBB[row++][2] = "Big blind bet 2 count Expected Value";
		dataValuesBB[row][1] = Format.format(play.bbRaisedBySBCount);
		dataValuesBB[row++][2] = "Big blind raised by SB count";
		dataValuesBB[row][1] = Format.format(play.bbRaisedBySBStartP);
		dataValuesBB[row++][2] = "Big blind raised by SB start position";
		dataValuesBB[row][1] = Format.format(play.bbRaisedBySBCountP);
		dataValuesBB[row++][2] = "Big blind raised by SB count position";
		dataValuesBB[row][1] = Format.format(play.bbRaisedBySBStartEV);
		dataValuesBB[row++][2] = "Big blind raised by SB start Expected Value";
		dataValuesBB[row][1] = Format.format(play.bbRaisedBySBCountEV);
		dataValuesBB[row++][2] = "Big blind raised by SB count Expected Value";
		dataValuesBB[row][1] = Format.format(play.bbCallMinBetCount);
		dataValuesBB[row++][2] = "Big blind call min bet count";
		dataValuesBB[row][1] = Format.format(play.bbCallMinBetStartP);
		dataValuesBB[row++][2] = "Big blind call min bet start position";
		dataValuesBB[row][1] = Format.format(play.bbCallMinBetCountP);
		dataValuesBB[row++][2] = "Big blind call min bet count position";
		dataValuesBB[row][1] = Format.format(play.bbCallMinBetStartEV);
		dataValuesBB[row++][2] = "Big blind min bet start Expected Value";
		dataValuesBB[row][1] = Format.format(play.bbCallMinBetCountEV);
		dataValuesBB[row++][2] = "Big blind min bet count Expected Value";
		dataValuesBB[row][1] = Format.format(play.bbStealCallCount);
		dataValuesBB[row++][2] = "Big blind steal call count";
		dataValuesBB[row][1] = Format.format(play.bbStealCallStartP);
		dataValuesBB[row++][2] = "Big blind steal call start position";
		dataValuesBB[row][1] = Format.format(play.bbStealCallCountP);
		dataValuesBB[row++][2] = "Big blind steal call count position";
		dataValuesBB[row][1] = Format.format(play.bbStealCallStartEV);
		dataValuesBB[row++][2] = "Big blind steal call start Expected Value";
		dataValuesBB[row][1] = Format.format(play.bbStealCallCountEV);
		dataValuesBB[row++][2] = "Big blind steal call count Expected Value";

	}

	private static boolean valuesSBGUI = false;

	static void valuesSB(Players play) {
		if (!valuesSBGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 16);
			final var frame = new JFrame("Values Small Blind " + Format.format(play.playerID));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(130, 130);
			frame.setPreferredSize(new Dimension(800, 1180));

			final var table = new JTable(dataValuesSB, columnsValues);
			table.setFont(ff);
			table.setRowHeight(40);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(2).setPreferredWidth(180);

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
			valuesSBGUI = true;
		}
		int saveRow = 0;
		int row = 0;
		dataValuesSB[row][1] = Format.format(play.playerID);
		dataValuesSB[row][1] = Format.format(play.bet3MinOperSB);
		dataValuesSB[row++][2] = "Small blind min bet opportunity";
		dataValuesSB[row][1] = Format.format(play.raisedByBBOperSB);
		dataValuesSB[row++][2] = "Small blind raised by BBB opportunity";
		dataValuesSB[row][1] = Format.format(play.steal3BetOperSB);
		dataValuesSB[row++][2] = "Small blind 3 bet steal opportunity";
		dataValuesSB[row][1] = Format.format(play.stealCallOperSB);
		dataValuesSB[row++][2] = "Small blind steal call opportunity";
		dataValuesSB[row][1] = Format.format(play.stealCallMinRaiseSBOper);
		dataValuesSB[row++][2] = "Small blind steal calling min raise opportunity";
		dataValuesSB[row][1] = Format.format(play.foldedToSBOper);
		dataValuesSB[row++][2] = "Folded to small blind opportunity";
		dataValuesSB[row][1] = Format.format(play.walk);

		dataValuesSB[row][1] = Format.format(play.bet3MinSB);
		dataValuesSB[row++][2] = "Small Blind bet 3 minn bet";
		dataValuesSB[row][1] = Format.format(play.raisedByBBSB);
		dataValuesSB[row++][2] = "Small blind raised by BB";
		dataValuesSB[row][1] = Format.format(play.steal3BetSB);
		dataValuesSB[row++][2] = "Small blind steal";
		dataValuesSB[row][1] = Format.format(play.stealCallSB);
		dataValuesSB[row++][2] = "Small blind steal call";
		dataValuesSB[row][1] = Format.format(play.stealCallMinRaiseSB);
		dataValuesSB[row++][2] = "SB steal calling min raise";
		dataValuesSB[row][1] = Format.format(play.foldedToSB);
		dataValuesSB[row++][2] = "SB folded";
		dataValuesSB[row][1] = Format.format(play.stealCount);

		dataValuesSB[row][1] = Format.format(play.sbFoldedToSBCount);
		dataValuesSB[row++][2] = "Small blind folded to SB count";
		dataValuesSB[row][1] = Format.format(play.sbFoldedToSBStartP);
		dataValuesSB[row++][2] = "Small blind folded to SB start position";
		dataValuesSB[row][1] = Format.format(play.sbFoldedToSBCountP);
		dataValuesSB[row++][2] = "Small blind folded to SB count position";
		dataValuesSB[row][1] = Format.format(play.sbFoldedToSBStartEV);
		dataValuesSB[row++][2] = "Small blind folded to SB start Expected Value";
		dataValuesSB[row][1] = Format.format(play.sbFoldedToSBCountEV);
		dataValuesSB[row++][2] = "Small blind folded to SB count Expected Value";
		dataValuesSB[row][1] = Format.format(play.sbStealCallCount);
		dataValuesSB[row++][2] = "Small blind steal call count";
		dataValuesSB[row][1] = Format.format(play.sbStealCallStartP);
		dataValuesSB[row++][2] = "Small blind steal call start position";
		dataValuesSB[row][1] = Format.format(play.sbStealCallCountP);
		dataValuesSB[row++][2] = "Small blind steal call count position";
		dataValuesSB[row][1] = Format.format(play.sbStealCallStartEV);
		dataValuesSB[row++][2] = "Small blind steal call start Expected Value";
		dataValuesSB[row][1] = Format.format(play.sbStealCallCountEV);
		dataValuesSB[row++][2] = "Small blind steal call count Expected Value";
		dataValuesSB[row][1] = Format.format(play.sbBet2StealCount);
		dataValuesSB[row++][2] = "Small blind bet 2 steal call count";
		dataValuesSB[row][1] = Format.format(play.sbBet2StealStartP);
		dataValuesSB[row++][2] = "Small blind bet 2 steal call start position";
		dataValuesSB[row][1] = Format.format(play.sbBet2StealCountP);
		dataValuesSB[row++][2] = "Small blind bet 2 steal call count position";
		dataValuesSB[row][1] = Format.format(play.sbBet2StealStartEV);
		dataValuesSB[row++][2] = "Small blind bet 2 steal call start Expected Value";
		dataValuesSB[row][1] = Format.format(play.sbBet2StealCountEV);
		dataValuesSB[row++][2] = "Small blind bet 2 steal call count Expected Value";
		dataValuesSB[row][1] = Format.format(play.sbBBRaisedCount);
		dataValuesSB[row++][2] = "Small blind raised by SB count";
		dataValuesSB[row][1] = Format.format(play.sbBBRaisedStartP);
		dataValuesSB[row++][2] = "Small blind raised by SB start position";
		dataValuesSB[row][1] = Format.format(play.sbBBRaisedCountP);
		dataValuesSB[row++][2] = "Small blind raised by SB count position";
		dataValuesSB[row][1] = Format.format(play.sbBBRaisedStartEV);
		dataValuesSB[row++][2] = "Small blind raised by SB start Expected Value";
		dataValuesSB[row][1] = Format.format(play.sbBBRaisedCountEV);
		dataValuesSB[row++][2] = "Small blind raised by SB count Expected Value";
		dataValuesSB[row][1] = Format.format(play.sbStealCallMinBetCount);
		dataValuesSB[row++][2] = "Small blind steal call min bet count";
		dataValuesSB[row][1] = Format.format(play.sbStealCallMinBetOpportunity);
		dataValuesSB[row++][2] = "Small blind steal call min bet opportunity";
		dataValuesSB[row][1] = Format.format(play.sbStealCallMinBetStartP);
		dataValuesSB[row++][2] = "Small blind steal call min bet start position";
		dataValuesSB[row][1] = Format.format(play.sbStealCallMinBetCountP);
		dataValuesSB[row++][2] = "Small blind steal call min bet count position";
		dataValuesSB[row][1] = Format.format(play.sbStealCallMinBetStartEV);
		dataValuesSB[row++][2] = "Small blind steal call min bet start Expected Value";
		dataValuesSB[row][1] = Format.format(play.sbStealCallMinBetCountEV);
		dataValuesSB[row++][2] = "Small blind steal call min bet count Expected Value";
		dataValuesSB[row][1] = Format.format(play.sbStealMinBetCount);
		dataValuesSB[row++][2] = "Small blind steal min bet count";
		dataValuesSB[row][1] = Format.format(play.sbStealMinBetOpportunity);
		dataValuesSB[row++][2] = "Small blind steal min bet opportunity";
		dataValuesSB[row][1] = Format.format(play.sbStealMinBetStartP);
		dataValuesSB[row++][2] = "Small blind steal min bet start position";
		dataValuesSB[row][1] = Format.format(play.sbStealMinBetCountP);
		dataValuesSB[row++][2] = "Small blind steal min bet count position";
		dataValuesSB[row][1] = Format.format(play.sbStealMinBetStartEV);
		dataValuesSB[row++][2] = "Small blind steal call min bet start Expected Value";
		dataValuesSB[row][1] = Format.format(play.sbStealMinBetCountEV);
		dataValuesSB[row++][2] = "Small blind steal call min bet count Expected Value";
		dataValuesSB[row][1] = Format.format(play.SBLimpCount = 0);
		dataValuesSB[row++][2] = "Small blind limp count";
	}

	static void allActionPos(Players play) {
		if (!allActionPosGUI) {
			final var frame = new JFrame("Action       All streets " + Format.format(play.playerID));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(160, 160);
			frame.setPreferredSize(new Dimension(500, 850));
			final var table = new JTable(dataActionsPos, columnsPos);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);

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
			allActionPosGUI = true;
		}

		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_POS; ++k) {
				saveRow = row;
				dataActionsPos[row++][k + 1] = Format.format(play.foldPos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.checkPos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.bet1Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.callBet1Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.bet2Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.callBet2Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.bet3Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.callBet3Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.bet4Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.callBet4Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.allinPos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.callAllinPos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.cBetPos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.barrelPos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(-1);
				row = saveRow;
			}
		}
		row += 16;
	}

	private static boolean streetGUI = false;

	static void streets(Players play) {
		if (!streetGUI) {
			final var frame = new JFrame("Streets " + Format.format(play.playerID));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(190, 190);
			frame.setPreferredSize(new Dimension(500, 650));

			final var table = new JTable(dataStreets, columnsStreets);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			// table.getColumnModel().getColumn(0).setPreferredWidth(120);

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
			streetGUI = true;
		}

		int row = 0;
		int col = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			dataStreets[row++][col] = Format.format(play.PFRCount[i]);
			dataStreets[row++][col] = Format.format(play.threeBetCount[i]);
			dataStreets[row++][col] = Format.format(play.WTSDCount[i]);
			dataStreets[row++][col] = Format.format(play.W$SD_WSDCount[i]);
			dataStreets[row++][col] = Format.format(play.WWSFCount[i]);
			dataStreets[row++][col] = Format.format(play.foldTo3BetAfterRaisingCount[i]);
			dataStreets[row++][col] = Format.format(play.foldToCBetCount[i]);
			dataStreets[row++][col] = Format.format(play.streetCounts[i]);
			dataStreets[row++][col] = Format.format(play.summaryFoldCount[i]);
			dataStreets[row++][col] = Format.format(play.summaryCollectedCount[i]);
			++col;
			row = 0;
		}

	}

	private static boolean newArraysPosGUI = false;

	static void newArraysPos(Players play) {
		if (!newArraysPosGUI) {
			final var frame = new JFrame("Action       New arrays " + Format.format(play.playerID));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(220, 220);
			frame.setPreferredSize(new Dimension(500, 850));

			final var table = new JTable(dataActionsNewArraysPos, columnsNewArraysPos);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			// table.getColumnModel().getColumn(0).setPreferredWidth(120);

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
			newArraysPosGUI = true;
		}

		int row = 1;
		int saveRow = 0;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_POS; ++k) {
				saveRow = row;
				dataActionsNewArraysPos[row++][k + 1] = Format.format(play.foldCount[i][k]);
				dataActionsNewArraysPos[row++][k + 1] = Format.format(play.limpCount[i][k]);
				dataActionsNewArraysPos[row++][k + 1] = Format.format(play.callCount[i][k]);
				dataActionsNewArraysPos[row++][k + 1] = Format.format(play.betCount[i][k]);
				dataActionsNewArraysPos[row++][k + 1] = Format.format(play.raiseCount[i][k]);
				dataActionsNewArraysPos[row++][k + 1] = Format.format(play.checkCount[i][k]);
				dataActionsNewArraysPos[row++][k + 1] = Format.format(play.raisePos[i][k]);
				dataActionsNewArraysPos[row++][k + 1] = Format.format(play.callPos[i][k]);
				dataActionsNewArraysPos[row++][k + 1] = Format.format(play.minBetOperPos[i][k]);
				dataActionsNewArraysPos[row++][k + 1] = Format.format(play.minBetPos[i][k]);
				row = saveRow;
			}
			row += 12;
		}
	}

	static void allOperPos(Players play) {
		if (!allOperPosGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 16);
			final var frame = new JFrame(" Oppertunity     All streets  " + Format.format(play.playerID));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(250, 250);
			frame.setPreferredSize(new Dimension(500, 850));

			final var table = new JTable(dataOperPos, columnsPos);
			table.setFont(ff);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			// table.getColumnModel().getColumn(0).setPreferredWidth(120);

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
			allOperPosGUI = true;
		}

		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_POS; ++k) {
				saveRow = row;
				dataOperPos[row++][k + 1] = Format.format(play.foldOperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.checkOperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.bet1OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.callBet1OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.bet2OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.callBet2OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.bet3OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.callBet3OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.bet4OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.callBet4OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.allinOperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.callAllinOperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.cBetOperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.barrelOperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(-1);
				row = saveRow;
			}
			row += 16;
		}
	}

	private static boolean newArraysRpGUIX = false;

	static void newArraysRp(Players play) {
		if (!newArraysRpGUIX) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 16);
			final var frame = new JFrame(
					"Players Opportunity data for all streets, relative positions " + Format.format(play.playerID));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(280, 280);
			frame.setPreferredSize(new Dimension(600, 850));

			final var table = new JTable(dataNewArraysRp, columnsNewArraysRp);
			table.setFont(ff);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			// table.getColumnModel().getColumn(0).setPreferredWidth(120);

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
			newArraysRpGUIX = true;
		}

		int row = 1;
		int saveRow = 0;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_RP; ++k) {
				saveRow = row;
				dataNewArraysRp[row++][k + 1] = Format.format(play.raiseRp[i][k]);
				dataNewArraysRp[row++][k + 1] = Format.format(play.betRp[i][k]);
				dataNewArraysRp[row++][k + 1] = Format.format(play.callRp[i][k]);
				dataNewArraysRp[row++][k + 1] = Format.format(play.minBetOperRp[i][k]);
				dataNewArraysRp[row++][k + 1] = Format.format(play.minBetRp[i][k]);
				dataNewArraysRp[row++][k + 1] = Format.format(-1);
				row = saveRow;
			}
			row += 7;
		}
	}

	private static boolean allActionRpGUI = false;

	static void allActionRp(Players play) {
		if (!allActionRpGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 16);
			final var frame = new JFrame(
					"Players Opportunity data for all streets, relative positions " + Format.format(play.playerID));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(310, 310);
			frame.setPreferredSize(new Dimension(600, 850));

			final var table = new JTable(dataActionsRp, columnsRp);
			table.setFont(ff);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			// table.getColumnModel().getColumn(0).setPreferredWidth(120);

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
			allActionRpGUI = true;
		}

		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_POS; ++k) {
				saveRow = row;
				dataActionsRp[row++][k + 1] = Format.format(play.checkOperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.foldOperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.bet1OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.callBet1OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.bet2OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.callBet2OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.bet3OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.callBet3OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.bet4OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.callBet4OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.allinOperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.callAllinOperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.cBetOperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.barrelOperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(-1);
				row = saveRow;
			}
			row += 17;
		}
	}

	private static boolean allOperRpGUI = false;

	static void allOperRp(Players play) {
		if (!allOperRpGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 16);
			final var frame = new JFrame(
					"Players Opportunity data for all streets, relative positions " + Format.format(play.playerID));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(340, 340);
			frame.setPreferredSize(new Dimension(600, 850));
			final var table = new JTable(dataOperRp, columnsRp);
			table.setFont(ff);
			table.setRowHeight(30);

			final var pane = new JScrollPane(table);
			// table.getColumnModel().getColumn(0).setPreferredWidth(120);

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
			allOperRpGUI = true;
		}

		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_POS; ++k) {
				saveRow = row;
				dataOperRp[row++][k + 1] = Format.format(play.checkOperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.foldOperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.bet1OperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.callBet1OperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.bet2OperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.callBet2OperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.bet3OperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.callBet3OperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.bet4OperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.callBet4OperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.allinOperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.callAllinOperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.cBetOperRp[i][k]);
				dataOperRp[row++][k + 1] = Format.format(play.barrelOperRp[i][k]);
				row = saveRow;
			}
			row += 17;
		}
	}

}
