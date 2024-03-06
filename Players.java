package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

/*- ****************************************************************************** 
 * This class is used by GUIAnallyze to save data about each individual player. 
 * It is also used to save data about a composite player, many players with their
 * data added together then divided by the number of players.
 * 
 * There is no data in this class that is the result of calculations. 
 * Calculated data is in several other classes such as PlayerCharacteristics.
 * GUIAnalyze parses Universal Hand History files to create data in this class.
 * Calculations are limited to collecting data, such as C-Bet, squeeze, and many other
 * player strategies. 
 * 
 * Calculations are in several classes with corresponding reports.  Examples:
 * 		PlayerFrequency and ReportFrequency. 
 * 		PlayerMDF and ReportMDF 
 * 	PlayerExploitation and ReportExploitation.
 * These classes can be worked on and developed pretty much without worrying 
 * about what other classes might do. Designed that way so new programmers can
 * join this effort. Calculation classes are the future of this project. We have the 
 * date here, in this class. Just needs analysis.
 ***************************************************************************** */

class Players implements java.io.Serializable, Constants {
	private final long serialVersionUID = 1234567L;

// Constructor
	Players() {
		setScale();
	}

	private static int numberOfInstancesAdded = 0; // Number of players added
	private static int dividend = 0; // Divide by this

	/*- ***********************************************************************************************
	 * Identification key
	 ************************************************************************************************/
	int playerID = -1; // Key
	/*- ***********************************************************************************************
	 * Parsed data 
	 ************************************************************************************************/
	int handsPlayed; // Number of games played
	int playerType = -1; // LAG, TAG, NIT, ......
	int wonShowdownCount;
	int wonCount;
	int wsdCount; // Won at Show Down
	int wtsdPreflopCount; // Went to show down if saw preflop
	int wtsdFlopCount; // Went to show down if saw flop
	int wtsdTurnCount; // Went to show down if saw turn
	int wtsdRiverCount; // Went to show down if saw river
	// Won When Saw
	int wwspCount; // Won when saw preflop
	int wwsfCount; // Won when saw Flop
	int wwstCount; // Won when saw Turn
	int wwsrCount; // Won when saw River
	int showdownCount;
	int showdownWonCount; // TODO
	int raiseCount1;
	int betCount1;
	// New
	int preflopCount;
	int flopCount;
	int turnCount;
	int riverCount;

	int barrellFlopTurnCount; // Raise then raise turn
	int barrellFlopTurnRiverCount; // Raise then raise turn then raise river
	int barrellTurnRiverCount; // Raise then raise turn then raise river
	int barrellPreflopFlopTurnRiverCount; // Raise 4 streets

	int walkCount;
	int walkOper; // BB can not raise because no other raise
	// Button
	int isolateOperBU; // Button isolate opportunity
	int minBetOperBU; // Button min bet opportunity
	int stealOperBU; // Button steal opportunity
	int squeezeOperBU; // Button squeeze opportunity
	// Big Blind
	int minBetOperBB; // Big blind min bet opportunity
	int callMinBetOperBB; // Big blind call min bet opportunity
	int steal3BetOperBB; // Big blind steal opportunity
	int raisedBySBOperBB; // Big blind raised by SBB opportunity
	int steal3BetMinRaiseOperBB; // Big blind steal opportunity
	int stealCallOperBB; // Big blind steal opportunity
	int stealCallMinRaiseOperBB; // BB steal calling min raise opportunity
	// Small Blind
	int bet3MinOperSB; // Small blind min bet opportunity
	int raisedByBBOperSB; // Small blind raised by BBB opportunity
	int steal3BetOperSB; // Small blind 3 bet steal opportunity
	int stealCallOperSB; // Small blind steal call opportunity
	int stealCallMinRaiseSBOper; // Small blind steal calling min raise opportunity
	int foldedToSBOper; // Folded to small blind opportunity
	int walk; // BB can not raise because no other raise
	// Button
	int isolateBU; // Button isolate
	int minBetBU; // Button min bet
	int stealBU;// Button steal
	int squeezeBU; // Button squeeze
	// Big Blind
	int minBetBB;// Big blind min bet
	int callMinBetBB; // Big blind call min bet
	int steal3BetBB; // Big blind steal
	int raisedBySBBB; // Big blind raised by SB BB
	int steal3BetMinRaiseBB; // Big blind steal
	int stealCallBB; // Big blind steal
	int stealCallMinRaiseBB; // BB steal calling min raise
	// Small Blind
	int bet3MinSB;// Small Blind bet 3 minn bet
	int raisedByBBSB; // Small blind raised by BB
	int steal3BetSB; // Small blind steal
	int stealCallSB; // Small blind steal call
	int stealCallMinRaiseSB; // SB steal calling min raise
	int foldedToSB; // SB folded

	// Button Only - Preflop
	int stealCount; // Button steal count
	int stealStartP; // Button steal start position
	int stealCountP; // Button steal count position
	int stealStartEV; // Button steal start Expected Value
	int stealCountEV; // Button steal count Expected Value
	int isolateCount; // Button isolate count
	int isolateStartP; // Button isolate start position
	int isolateCountP; // Button isolate count position
	int isolateStartEV; // Button isolate start Expected Value
	int isolateCountEV; // Button isolate count Expected Value
	int squeezeCount; // Button squeeze count
	int squeezeStartP; // Button squeeze start position
	int squeezeCountP; // Button squeeze count position
	int squeezeStartEV; // Button squeeze start Expected Value
	int squeezeCountEV; // Button squeeze count Expected Value
	int minBetCount; // Button min bet count
	int minBetStartP; // Button min bet start position
	int minBetCountP; // Button min bet count position
	int minBetStartEV; // Button min bet start Expected Value
	int minBetCountEV; // Button min bet count Expected Value
	// Big Blind Only - Preflop
	int bbStealCount; // Big blind steal count
	int bbStealStartP; // Big blind steal start position
	int bbStealCountP; // Big blind steal count position
	int bbStealStartEV; // Big blind steal start Expected Value
	int bbStealCountEV; // Big blind steal count Expected Value
	int bbBet2Count; // Big blind bet 2 count
	int bbBet2Opportunity; // Big blind bet 2 opportunity
	int bbBet2StartP; // Big blind bet 2 start position
	int bbBet2CountP; // Big blind bet 2 count position
	int bbBet2StartEV; // Big blind bet 2 start Expected Value
	int bbBet2CountEV; // Big blind bet 2 count Expected Value
	int bbRaisedBySBCount; // Big blind raised by SB count
	int bbRaisedBySBStartP; // Big blind raised by SB start position
	int bbRaisedBySBCountP; // Big blind raised by SB count position
	int bbRaisedBySBStartEV; // Big blind raised by SB start Expected Value
	int bbRaisedBySBCountEV; // Big blind raised by SB count Expected Value
	int bbCallMinBetCount; // Big blind call min bet count
	int bbCallMinBetStartP; // Big blind call min bet start position
	int bbCallMinBetCountP; // Big blind call min bet count position
	int bbCallMinBetStartEV; // Big blind min bet start Expected Value
	int bbCallMinBetCountEV; // Big blind min bet count Expected Value
	int bbStealCallCount; // Big blind steal call count
	int bbStealCallStartP; // Big blind steal call start position
	int bbStealCallCountP; // Big blind steal call count position
	int bbStealCallStartEV; // Big blind steal call start Expected Value
	int bbStealCallCountEV; // Big blind steal call count Expected Value
	// Small Blind Only - preflop
	int sbFoldedToSBCount; // Small blind folded to SB count
	int sbFoldedToSBStartP; // Small blind folded to SB start position
	int sbFoldedToSBCountP; // Small blind folded to SB count position
	int sbFoldedToSBStartEV; // Small blind folded to SB start Expected Value
	int sbFoldedToSBCountEV; // Small blind folded to SB count Expected Value
	int sbStealCallCount; // Small blind steal call count
	int sbStealCallStartP; // Small blind steal call start position
	int sbStealCallCountP; // Small blind steal call count position
	int sbStealCallStartEV; // Small blind steal call start Expected Value
	int sbStealCallCountEV; // Small blind steal call count Expected Value
	int sbBet2StealCount; // Small blind bet 2 steal call count
	int sbBet2StealStartP; // Small blind bet 2 steal call start position
	int sbBet2StealCountP; // Small blind bet 2 steal call count position
	int sbBet2StealStartEV; // Small blind bet 2 steal call start Expected Value
	int sbBet2StealCountEV; // Small blind bet 2 steal call count Expected Value
	int sbBBRaisedCount; // Small blind raised by SB count
	int sbBBRaisedStartP; // Small blind raised by SB start position
	int sbBBRaisedCountP; // Small blind raised by SB count position
	int sbBBRaisedStartEV; // Small blind raised by SB start Expected Value
	int sbBBRaisedCountEV; // Small blind raised by SB count Expected Value
	int sbStealCallMinBetCount; // Small blind steal call min bet count
	int sbStealCallMinBetOpportunity; // Small blind steal call min bet opportunity
	int sbStealCallMinBetStartP; // Small blind steal call min bet start position
	int sbStealCallMinBetCountP; // Small blind steal call min bet count position
	int sbStealCallMinBetStartEV; // Small blind steal call min bet start Expected Value
	int sbStealCallMinBetCountEV; // Small blind steal call min bet count Expected Value
	int sbStealMinBetCount; // Small blind steal min bet count
	int sbStealMinBetOpportunity; // Small blind steal min bet opportunity
	int sbStealMinBetStartP; // Small blind steal min bet start position
	int sbStealMinBetCountP; // Small blind steal min bet count position
	int sbStealMinBetStartEV; // Small blind steal call min bet start Expected Value
	int sbStealMinBetCountEV; // Small blind steal call min bet count Expected Value
	int SBLimpCount = 0; // TODO

	// Moved/copied here TODO
	BigDecimal won$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	BigDecimal[] won$Street = { zeroBD, zeroBD, zeroBD, zeroBD };
	BigDecimal bbBet$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);

	int betCount1Hero; // NEW <<<<<<<<<<<<<<<<<

	// New added because of ChatGPT generated code
	int[][] fourBet = new int[3][2]; // see fourBetLimpCallHU()
	int[] limped = new int[2]; // see fourBetLimpCallHU()
	int[][] foldBet1OperPos = new int[1][1]; // double flopCBetFoldPer
	int[][] checkBet1OperPos = new int[1][1]; // double skipFlopCBCheckFoldOOPPer
	int[][] checkBet2OperPos = new int[1][6]; // double skipTurnCBCheckCallOOPPer()
	int[][] raiseBet2OperPos = new int[1][4]; // double skipTurnCBCheckRaiseOOPPer
	int[][] foldBet2OperPos = new int[1][1]; // double turnCheckFoldToCBOOPPer
	int[][] checkCallOperPos = new int[1][8]; // double skipRiverCBAndCheckCallOOPPer
	// Number of hands where the player check-calls OOP
	int[][] checkRaiseOperPos = new int[1][4]; // double skipRiverCBAndCheckRaiseOOPPer
	// Number of hands where the player folds IP when facing
	int[][] foldBet3OperPos = new int[0][7]; // double riverFoldToCBIPPer
	// Number of hands where the player check-folds
	// OOP when facing a CB on the River
	int[][] checkFoldOperPos = new int[1][7]; // double riverCheckFoldToCBOOPPer
	// Number of hands where the player has the opportunity
	// to bet OOP on the River
	int[][] betOperPos = new int[1][9]; // double riverDonkBetPer
	// Number of hands where the player bets on the River
	// after the opponent missed a River continuation bet
	int[][] checkBetOperPos = new int[0][11]; // double riverBetVsMissedCBPer
	// Number of hands where the player sees a showdown when they saw the
	// Turn
	int[] wtsdX = new int[0]; // double WTSDWhenSawTurnPer
	// Number of hands where the player saw the Turn
	int[] sawTurn = new int[0]; // double WTSDWhenSawTurnPer
	int[] streetCountsX = new int[NUM_STREETS]; // private double calculateVPIPStreetPos
	// Number of hands where the player raises IP when facing
	// a CB on the River
	int[][] raiseBet3OperPos = new int[0][7]; // double riverRaiseCBIPPer(

	/*-**********************************************************************************************
	 * Data used to calculate statistics
	********************************************************************************************** */
	int[] PFRCount = new int[NUM_STREETS]; // Raised count
	int[] threeBetCount = new int[NUM_STREETS]; // 3-bet count // TODO
	int[] WTSDCount = new int[NUM_STREETS]; // Went To Show Down count
	int[] W$SD_WSDCount = new int[NUM_STREETS]; // Won Money At Show Down count
	int[] WWSFCount = new int[NUM_STREETS]; // Won When Saw
	int[] foldTo3BetAfterRaisingCount = new int[NUM_STREETS]; // Fold to 3-Bet After Raising count
	int[] foldToCBetCount = new int[NUM_STREETS]; // Fold to C-Bet count
	int[] streetCount = new int[NUM_STREETS]; // TODO
	// Orbit 0 only for count of times player saw street
	int[] streetCounts = new int[NUM_STREETS];
	int[] summaryFoldCount = new int[NUM_STREETS + 2];
	int[] summaryCollectedCount = new int[NUM_STREETS + 2];
	int[] buttonLimpCount = new int[NUM_POS]; // TODO

	/*-**********************************************************************************************
	 * TODO
	 ************************************************************************************************/
	int[][] foldCount = new int[NUM_STREETS][NUM_POS];
	int[][] limpCount = new int[NUM_STREETS][NUM_POS];
	int[][] callCount = new int[NUM_STREETS][NUM_POS];
	int[][] betCount = new int[NUM_STREETS][NUM_POS];
	int[][] raiseCount = new int[NUM_STREETS][NUM_POS];
	int[][] checkCount = new int[NUM_STREETS][NUM_POS];

	/*-**********************************************************************************************
	 * These arrays age for all streets, Preflop, Flop, Turn, and River.
	 * Index 1 - Street number
	 * Index 2 - Position ( SB, BB, UTG, MP, CO, BUTTON
	 * Index 3 - Orbit
	 * There are 2 types of arrays:
	 ************************************************************************************************/
	// Opportunity
	int[][] limpOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] checkOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] foldOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet1OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet1OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet2OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet2OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet3OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet3OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet4OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet4OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] allinOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callAllinOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] cBetOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] barrelOperPos = new int[NUM_STREETS][NUM_POS];
	// Acted
	int[][] checkPos = new int[NUM_STREETS][NUM_POS];
	int[][] foldPos = new int[NUM_STREETS][NUM_POS];
	int[][] limpPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet1Pos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet1Pos = new int[NUM_STREETS][NUM_POS];
	int[][] bet2Pos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet2Pos = new int[NUM_STREETS][NUM_POS];
	int[][] bet3Pos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet3Pos = new int[NUM_STREETS][NUM_POS];
	int[][] bet4Pos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet4Pos = new int[NUM_STREETS][NUM_POS];
	int[][] allinPos = new int[NUM_STREETS][NUM_POS];
	int[][] callAllinPos = new int[NUM_STREETS][NUM_POS];
	int[][] cBetPos = new int[NUM_STREETS][NUM_POS];
	int[][] barrelPos = new int[NUM_STREETS][NUM_POS];
	// Opportunity
	int[][] checkOperRp = new int[NUM_STREETS][NUM_RP];
	int[][] limpOperRp = new int[NUM_STREETS][NUM_RP];
	int[][] foldOperRp = new int[NUM_STREETS][NUM_RP];
	int[][] bet1OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] callBet1OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] bet2OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] callBet2OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] bet3OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] callBet3OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] bet4OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] callBet4OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] allinOperRp = new int[NUM_STREETS][NUM_RP];
	int[][] callAllinOperRp = new int[NUM_STREETS][NUM_RP];
	int[][] cBetOperRp = new int[NUM_STREETS][NUM_RP];
	int[][] barrelOperRp = new int[NUM_STREETS][NUM_RP];
	// Acted
	int[][] bet1Rp = new int[NUM_STREETS][NUM_RP];
	int[][] limpRp = new int[NUM_STREETS][NUM_RP];
	int[][] callBet1Rp = new int[NUM_STREETS][NUM_RP];
	int[][] bet2Rp = new int[NUM_STREETS][NUM_RP];
	int[][] callBet2Rp = new int[NUM_STREETS][NUM_RP];
	int[][] cBetRp = new int[NUM_STREETS][NUM_RP];
	int[][] barrelRp = new int[NUM_STREETS][NUM_RP];
	int[][] checkRp = new int[NUM_STREETS][NUM_RP];
	int[][] foldRp = new int[NUM_STREETS][NUM_RP];
	int[][] bet3Rp = new int[NUM_STREETS][NUM_RP];
	int[][] callBet3Rp = new int[NUM_STREETS][NUM_RP];
	int[][] bet4Rp = new int[NUM_STREETS][NUM_RP];
	int[][] callBet4Rp = new int[NUM_STREETS][NUM_RP];
	int[][] allinRp = new int[NUM_STREETS][NUM_RP];
	int[][] callAllinRp = new int[NUM_STREETS][NUM_RP];
// Frequency of RP
	int[][][] rpCount = new int[NUM_STREETS][NUM_POS][NUM_RP];

	/*-**********************************************************************************************
	 * These arrays age for all streets, Preflop, Flop, Turn, and River.
	 * Index 1 - Street number
	 * Index 2 - Position ( SB, BB, UTG, MP, CO, BUTTON
	 * Index 3 - Orbit
	 * TODO
	 ************************************************************************************************/
	int[][] raisePos = new int[NUM_STREETS][NUM_POS];
	int[][] betPos = new int[NUM_STREETS][NUM_POS];
	int[][] callPos = new int[NUM_STREETS][NUM_POS];
	int[][] minBetOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] minBetPos = new int[NUM_STREETS][NUM_POS];
	int[][] raiseRp = new int[NUM_STREETS][NUM_RP];
	int[][] betRp = new int[NUM_STREETS][NUM_RP];
	int[][] callRp = new int[NUM_STREETS][NUM_RP];
	int[][] minBetOperRp = new int[NUM_STREETS][NUM_RP];
	int[][] minBetRp = new int[NUM_STREETS][NUM_RP];

	/*-***********************************************************************************************
	 * Set BigDecimal variables rounding mode to HALF_EVEN  bankers mode for currency.
	 * Called only by constructor.
	 * Sets all BigDecimal arrays to ZeroBD. Rounding 2 decimal places using bankers rounding
	 ************************************************************************************************/
	private void setScale() {

	}

	/*-**************************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	***************************************************************************************/
	void sumAllPlayers(Players h0) {
		++numberOfInstancesAdded;
		sumAllPosAndRp(h0);
		sumAllNotArray(h0);
		sumAllArray2(h0);
		sumAllTODO(h0);
		sumAllMisc(h0);
		sumAllAdvanced(h0);

	}

	/*-**************************************************************************************
	 * Divide all elements in this class or composite class by number of players added.
	 * Apply's only to a composite class, where multiple players have been added together.
	***************************************************************************************/
	void divideAllByPlayersAdded() {
		divideAll(numberOfInstancesAdded);
	}

	/*-**************************************************************************************
	 * Divide all elements in this class or composite class by number of s played. 
	 * Apply's both to a single instance of a single player or to a group of players 
	 * that have been added together. handsPlayed has also been added.
	 ***************************************************************************************/
	void divideAllByHandsPlayed() {
		divideAll(this.handsPlayed);
	}

	/*-**************************************************************************************
	 * Divide all elements in this class or composite class by number of 
	 * players added or by number of games played. 
	 * Arg0 - Number to divide by
	***************************************************************************************/
	private void divideAll(int num) {
		dividend = num;
		divideAllPosAndRp();
		divideAllNotArray();
		divideAllArray2();
		divideAllTODO();
		divideAllMisc();

		divideAllAdvanced();

	}

	/*-**************************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	***************************************************************************************/
	private void sumAllPosAndRp(Players h0) {
		add(this.limpOperPos, h0.limpOperPos);
		add(this.checkOperPos, h0.checkOperPos);
		add(this.foldOperPos, h0.foldOperPos);
		add(this.callBet1OperPos, h0.callBet1OperPos);
		add(this.bet1OperPos, h0.bet1OperPos);
		add(this.callBet2OperPos, h0.callBet2OperPos);
		add(this.bet2OperPos, h0.bet2OperPos);
		add(this.bet3OperPos, h0.bet3OperPos);
		add(this.callBet3OperPos, h0.callBet3OperPos);
		add(this.bet4OperPos, h0.bet4OperPos);
		add(this.callBet4OperPos, h0.callBet4OperPos);
		add(this.allinOperPos, h0.allinOperPos);
		add(this.callAllinOperPos, h0.callAllinOperPos);
		add(this.cBetOperPos, h0.cBetOperPos);
		add(this.barrelOperPos, h0.barrelOperPos);

		add(this.checkPos, h0.checkPos);
		add(this.foldPos, h0.foldPos);
		add(this.callBet1Pos, h0.callBet1Pos);
		add(this.bet1Pos, h0.bet1Pos);
		add(this.callBet2Pos, h0.callBet2Pos);
		add(this.bet2Pos, h0.bet2Pos);
		add(this.bet3Pos, h0.bet3Pos);
		add(this.callBet3Pos, h0.callBet3Pos);
		add(this.bet4Pos, h0.bet4Pos);
		add(this.callBet4Pos, h0.callBet4Pos);
		add(this.allinPos, h0.allinPos);
		add(this.callAllinPos, h0.callAllinPos);
		add(this.cBetPos, h0.cBetPos);
		add(this.barrelPos, h0.barrelPos);

		add(this.limpOperRp, h0.limpOperRp);
		add(this.checkOperRp, h0.checkOperRp);
		add(this.foldOperRp, h0.foldOperRp);
		add(this.callBet1OperRp, h0.callBet1OperRp);
		add(this.bet1OperRp, h0.bet1OperRp);
		add(this.callBet2OperRp, h0.callBet2OperRp);
		add(this.bet2OperRp, h0.bet2OperRp);
		add(this.bet3OperRp, h0.bet3OperRp);
		add(this.callBet3OperRp, h0.callBet3OperRp);
		add(this.bet4OperRp, h0.bet4OperRp);
		add(this.callBet4OperRp, h0.callBet4OperRp);
		add(this.allinOperRp, h0.allinOperRp);
		add(this.callAllinOperRp, h0.callAllinOperRp);
		add(this.cBetOperRp, h0.cBetOperRp);
		add(this.barrelOperRp, h0.barrelOperRp);

		add(this.checkRp, h0.checkRp);
		add(this.foldRp, h0.foldRp);
		add(this.callBet1Rp, h0.callBet1Rp);
		add(this.bet1Rp, h0.bet1Rp);
		add(this.callBet2Rp, h0.callBet2Rp);
		add(this.bet2Rp, h0.bet2Rp);
		add(this.bet3Rp, h0.bet3Rp);
		add(this.callBet3Rp, h0.callBet3Rp);
		add(this.bet4Rp, h0.bet4Rp);
		add(this.callBet4Rp, h0.callBet4Rp);
		add(this.allinRp, h0.allinRp);
		add(this.callAllinRp, h0.callAllinRp);
		add(this.cBetRp, h0.cBetRp);
		add(this.barrelRp, h0.barrelRp);
	}

	/*-**************************************************************************************
	 * Divide all elements by the number of players added together 
	***************************************************************************************/
	private void divideAllPosAndRp() {
		divide(this.limpOperPos);
		divide(this.limpOperPos);
		divide(this.checkOperPos);
		divide(this.foldOperPos);
		divide(this.callBet1OperPos);
		divide(this.bet1OperPos);
		divide(this.callBet2OperPos);
		divide(this.bet2OperPos);
		divide(this.bet3OperPos);
		divide(this.callBet3OperPos);
		divide(this.bet4OperPos);
		divide(this.callBet4OperPos);
		divide(this.allinOperPos);
		divide(this.callAllinOperPos);
		divide(this.cBetOperPos);
		divide(this.barrelOperPos);

		divide(this.checkPos);
		divide(this.foldPos);
		divide(this.callBet1Pos);
		divide(this.bet1Pos);
		divide(this.callBet2Pos);
		divide(this.bet2Pos);
		divide(this.bet3Pos);
		divide(this.callBet3Pos);
		divide(this.bet4Pos);
		divide(this.callBet4Pos);
		divide(this.allinPos);
		divide(this.callAllinPos);
		divide(this.cBetPos);
		divide(this.barrelPos);

		divide(this.limpOperRp);
		divide(this.checkOperRp);
		divide(this.foldOperRp);
		divide(this.callBet1OperRp);
		divide(this.bet1OperRp);
		divide(this.callBet2OperRp);
		divide(this.bet2OperRp);
		divide(this.bet3OperRp);
		divide(this.callBet3OperRp);
		divide(this.bet4OperRp);
		divide(this.callBet4OperRp);
		divide(this.allinOperRp);
		divide(this.callAllinOperRp);
		divide(this.cBetOperRp);
		divide(this.barrelOperRp);

		divide(this.checkRp);
		divide(this.foldRp);
		divide(this.callBet1Rp);
		divide(this.bet1Rp);
		divide(this.callBet2Rp);
		divide(this.bet2Rp);
		divide(this.bet3Rp);
		divide(this.callBet3Rp);
		divide(this.bet4Rp);
		divide(this.callBet4Rp);
		divide(this.allinRp);
		divide(this.callAllinRp);
		divide(this.cBetRp);
		divide(this.barrelRp);
	}

	/*-**************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	 *****************************************************************************/
	private void sumAllNotArray(Players h0) {
		this.handsPlayed += h0.handsPlayed;
		this.wonShowdownCount += h0.wonShowdownCount;
		this.wonCount += h0.wonCount;
		this.wsdCount += h0.wsdCount;
		this.wtsdPreflopCount += h0.wtsdPreflopCount;
		this.wtsdFlopCount += h0.wtsdFlopCount;
		this.wtsdTurnCount += h0.wtsdTurnCount;
		this.wtsdRiverCount += h0.wtsdRiverCount;
		this.wwspCount += h0.wwspCount;
		this.wwsfCount += h0.wwsfCount;
		this.wwstCount += h0.wwstCount;
		this.wwsrCount += h0.wwsrCount;
		this.showdownCount += h0.showdownCount;
		this.showdownWonCount += h0.showdownWonCount;
		this.raiseCount1 += h0.raiseCount1;
		this.betCount1 += h0.betCount1;
		this.walkCount += h0.walkCount;
		this.preflopCount += h0.preflopCount;
		this.flopCount += h0.flopCount;
		this.turnCount += h0.turnCount;
		this.riverCount += h0.riverCount;
	}

	/*-**************************************************************************
	 * Divide all elements by the number of players added together 
	 *****************************************************************************/
	private void divideAllNotArray() {
		divide(handsPlayed);
		divide(wonShowdownCount);
		divide(wonCount);
		divide(wsdCount);
		divide(wtsdPreflopCount);
		divide(wtsdFlopCount);
		divide(wtsdTurnCount);
		divide(wtsdRiverCount);
		divide(wwspCount);
		divide(wwsfCount);
		divide(wwstCount);
		divide(wwsrCount);
		divide(showdownCount);
		divide(showdownWonCount);
		divide(raiseCount1);
		divide(betCount1);
		divide(walkCount);
		divide(preflopCount);
		divide(flopCount);
		divide(turnCount);
		divide(riverCount);
	}

	/*-**************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	 *****************************************************************************/
	private void sumAllArray2(Players h0) {
		add(this.PFRCount, h0.PFRCount);
		add(this.threeBetCount, h0.threeBetCount);
		add(this.WTSDCount, h0.WTSDCount);
		add(this.W$SD_WSDCount, h0.W$SD_WSDCount);
		add(this.WWSFCount, h0.WWSFCount);
		add(this.foldTo3BetAfterRaisingCount, h0.foldTo3BetAfterRaisingCount);
		add(this.foldToCBetCount, h0.foldToCBetCount);
		add(this.streetCounts, h0.streetCounts);
		add(this.summaryFoldCount, h0.summaryFoldCount);
		add(this.summaryCollectedCount, h0.summaryCollectedCount);
		add(this.buttonLimpCount, h0.buttonLimpCount);
		this.SBLimpCount += h0.SBLimpCount;
	}

	/*-**************************************************************************
	 * Divide all elements by the number of players added together 
	 *****************************************************************************/
	private void divideAllArray2() {
		divide(this.PFRCount);
		divide(this.threeBetCount);
		divide(this.WTSDCount);
		divide(this.W$SD_WSDCount);
		divide(this.WWSFCount);
		divide(this.foldTo3BetAfterRaisingCount);
		divide(this.foldToCBetCount);
		divide(this.streetCounts);
		divide(this.summaryFoldCount);
		divide(this.summaryCollectedCount);
		divide(this.buttonLimpCount);
		divide(this.SBLimpCount);
	}

	/*- **************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	 *****************************************************************************/
	private void sumAllMisc(Players h0) {
		// new
		add(this.minBetOperPos, h0.minBetOperPos);
		add(this.minBetPos, h0.minBetPos);
		// new
		add(this.raisePos, h0.raisePos);
		add(this.betPos, h0.betPos);
		add(this.callPos, h0.callPos);

		add(this.raiseRp, h0.raiseRp);
		add(this.betRp, h0.betRp);
		add(this.callRp, h0.callRp);

		add(this.minBetOperRp, h0.minBetOperRp);
		add(this.minBetRp, h0.minBetRp);
		add(this.minBetOperRp, h0.minBetOperRp);
		add(this.minBetRp, h0.minBetRp);

	}

	/*- **************************************************************************
	* Divide all elements by the number of players added together 
	 *****************************************************************************/
	private void divideAllMisc() {
		divide(this.minBetOperPos);
		divide(this.minBetPos);
		divide(this.raisePos);
		divide(this.betPos);
		divide(this.callPos);
		divide(this.raiseRp);
		divide(this.betRp);

		divide(this.minBetOperRp);
		divide(this.minBetRp);
		divide(this.minBetOperRp);
		divide(this.minBetRp);
	}

	/*- **************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	 *****************************************************************************/
	private void sumAllAdvanced(Players h0) {
		this.barrellFlopTurnCount += h0.barrellFlopTurnCount;
		this.barrellFlopTurnRiverCount += h0.barrellFlopTurnRiverCount;
		this.barrellTurnRiverCount += h0.barrellTurnRiverCount;
		this.barrellPreflopFlopTurnRiverCount += h0.barrellPreflopFlopTurnRiverCount;
		this.walkOper += h0.walkOper;
		this.isolateOperBU += h0.isolateOperBU;
		this.minBetOperBU += h0.minBetOperBU;
		this.stealOperBU += h0.stealOperBU;
		this.squeezeOperBU += h0.squeezeOperBU;
		this.minBetOperBB += h0.minBetOperBB;
		this.callMinBetOperBB += h0.callMinBetOperBB;
		this.steal3BetOperBB += h0.steal3BetOperBB;
		this.raisedBySBOperBB += h0.raisedBySBOperBB;
		this.steal3BetMinRaiseOperBB += h0.steal3BetMinRaiseOperBB;
		this.stealCallOperBB += h0.stealCallOperBB;
		this.stealCallMinRaiseOperBB += h0.stealCallMinRaiseOperBB;
		this.bet3MinOperSB += h0.bet3MinOperSB;
		this.raisedByBBOperSB += h0.raisedByBBOperSB;
		this.steal3BetOperSB += h0.steal3BetOperSB;
		this.stealCallOperSB += h0.stealCallOperSB;
		this.stealCallMinRaiseSBOper += h0.stealCallMinRaiseSBOper;
		this.foldedToSBOper += h0.foldedToSBOper;
		this.walk += h0.walk;
		this.isolateBU += h0.isolateBU;
		this.minBetBU += h0.minBetBU;
		this.stealBU += h0.stealBU;
		this.squeezeBU += h0.squeezeBU;
		this.minBetBB += h0.minBetBB;
		this.callMinBetBB += h0.callMinBetBB;
		this.steal3BetBB += h0.steal3BetBB;
		this.raisedBySBBB += h0.raisedBySBBB;
		this.steal3BetMinRaiseBB += h0.steal3BetMinRaiseBB;
		this.stealCallBB += h0.stealCallBB;
		this.stealCallMinRaiseBB += h0.stealCallMinRaiseBB;
		this.bet3MinSB += h0.bet3MinSB;
		this.raisedByBBSB += h0.raisedByBBSB;
		this.steal3BetSB += h0.steal3BetSB;
		this.stealCallSB += h0.stealCallSB;
		this.stealCallMinRaiseSB += h0.stealCallMinRaiseSB;
		this.foldedToSB += h0.foldedToSB;
		this.stealCount += h0.stealCount;
		this.stealStartP += h0.stealStartP;
		this.stealCountP += h0.stealCountP;
		this.stealStartEV += h0.stealStartEV;
		this.stealCountEV += h0.stealCountEV;
		this.isolateCount += h0.isolateCount;
		this.isolateStartP += h0.isolateStartP;
		this.isolateCountP += h0.isolateCountP;
		this.isolateStartEV += h0.isolateStartEV;
		this.isolateCountEV += h0.isolateCountEV;
		this.squeezeCount += h0.squeezeCount;
		this.squeezeStartP += h0.squeezeStartP;
		this.squeezeCountP += h0.squeezeCountP;
		this.squeezeStartEV += h0.squeezeStartEV;
		this.squeezeCountEV += h0.squeezeCountEV;
		this.minBetCount += h0.minBetCount;
		this.minBetStartP += h0.minBetStartP;
		this.minBetCountP += h0.minBetCountP;
		this.minBetStartEV += h0.minBetStartEV;
		this.minBetCountEV += h0.minBetCountEV;
		this.bbStealCount += h0.bbStealCount;
		this.bbStealStartP += h0.bbStealStartP;
		this.bbStealCountP += h0.bbStealCountP;
		this.bbStealStartEV += h0.bbStealStartEV;
		this.bbStealCountEV += h0.bbStealCountEV;
		this.bbBet2Count += h0.bbBet2Count;
		this.bbBet2Opportunity += h0.bbBet2Opportunity;
		this.bbBet2StartP += h0.bbBet2StartP;
		this.bbBet2CountP += h0.bbBet2CountP;
		this.bbBet2StartEV += h0.bbBet2StartEV;
		this.bbBet2CountEV += h0.bbBet2CountEV;
		this.bbRaisedBySBCount += h0.bbRaisedBySBCount;
		this.bbRaisedBySBStartP += h0.bbRaisedBySBStartP;
		this.bbRaisedBySBCountP += h0.bbRaisedBySBCountP;
		this.bbRaisedBySBStartEV += h0.bbRaisedBySBStartEV;
		this.bbRaisedBySBCountEV += h0.bbRaisedBySBCountEV;
		this.bbCallMinBetCount += h0.bbCallMinBetCount;
		this.bbCallMinBetStartP += h0.bbCallMinBetStartP;
		this.bbCallMinBetCountP += h0.bbCallMinBetCountP;
		this.bbCallMinBetStartEV += h0.bbCallMinBetStartEV;
		this.bbCallMinBetCountEV += h0.bbCallMinBetCountEV;
		this.bbStealCallCount += h0.bbStealCallCount;
		this.bbStealCallStartP += h0.bbStealCallStartP;
		this.bbStealCallCountP += h0.bbStealCallCountP;
		this.bbStealCallStartEV += h0.bbStealCallStartEV;
		this.bbStealCallCountEV += h0.bbStealCallCountEV;
		this.sbFoldedToSBCount += h0.sbFoldedToSBCount;
		this.sbFoldedToSBStartP += h0.sbFoldedToSBStartP;
		this.sbFoldedToSBCountP += h0.sbFoldedToSBCountP;
		this.sbFoldedToSBStartEV += h0.sbFoldedToSBStartEV;
		this.sbFoldedToSBCountEV += h0.sbFoldedToSBCountEV;
		this.sbStealCallCount += h0.sbStealCallCount;
		this.sbStealCallStartP += h0.sbStealCallStartP;
		this.sbStealCallCountP += h0.sbStealCallCountP;
		this.sbStealCallStartEV += h0.sbStealCallStartEV;
		this.sbStealCallCountEV += h0.sbStealCallCountEV;
		this.sbBet2StealCount += h0.sbBet2StealCount;
		this.sbBet2StealStartP += h0.sbBet2StealStartP;
		this.sbBet2StealCountP += h0.sbBet2StealCountP;
		this.sbBet2StealStartEV += h0.sbBet2StealStartEV;
		this.sbBet2StealCountEV += h0.sbBet2StealCountEV;
		this.sbBBRaisedCount += h0.sbBBRaisedCount;
		this.sbBBRaisedStartP += h0.sbBBRaisedStartP;
		this.sbBBRaisedCountP += h0.sbBBRaisedCountP;
		this.sbBBRaisedStartEV += h0.sbBBRaisedStartEV;
		this.sbBBRaisedCountEV += h0.sbBBRaisedCountEV;
		this.sbStealCallMinBetCount += h0.sbStealCallMinBetCount;
		this.sbStealCallMinBetOpportunity += h0.sbStealCallMinBetOpportunity;
		this.sbStealCallMinBetStartP += h0.sbStealCallMinBetStartP;
		this.sbStealCallMinBetCountP += h0.sbStealCallMinBetCountP;
		this.sbStealCallMinBetStartEV += h0.sbStealCallMinBetStartEV;
		this.sbStealCallMinBetCountEV += h0.sbStealCallMinBetCountEV;
		this.sbStealMinBetCount += h0.sbStealMinBetCount;
		this.sbStealMinBetOpportunity += h0.sbStealMinBetOpportunity;
		this.sbStealMinBetStartP += h0.sbStealMinBetStartP;
		this.sbStealMinBetCountP += h0.sbStealMinBetCountP;
		this.sbStealMinBetStartEV += h0.sbStealMinBetStartEV;
		this.sbStealMinBetCountEV += h0.sbStealMinBetCountEV;
	}

	/*- **************************************************************************
	* Divide all elements by the number of players added together 
	 *****************************************************************************/
	private void divideAllAdvanced() {
		divide(this.barrellFlopTurnCount);
		divide(this.barrellFlopTurnRiverCount);
		divide(this.barrellTurnRiverCount);
		divide(this.barrellPreflopFlopTurnRiverCount);
		divide(this.walkOper);
		divide(this.isolateOperBU);
		divide(this.minBetOperBU);
		divide(this.stealOperBU);
		divide(this.squeezeOperBU);
		divide(this.minBetOperBB);
		divide(this.callMinBetOperBB);
		divide(this.steal3BetOperBB);
		divide(this.raisedBySBOperBB);
		divide(this.steal3BetMinRaiseOperBB);
		divide(this.stealCallOperBB);
		divide(this.stealCallMinRaiseOperBB);
		divide(this.bet3MinOperSB);
		divide(this.raisedByBBOperSB);
		divide(this.steal3BetOperSB);
		divide(this.stealCallOperSB);
		divide(this.stealCallMinRaiseSBOper);
		divide(this.foldedToSBOper);
		divide(this.walk);
		divide(this.isolateBU);
		divide(this.minBetBU);
		divide(this.stealBU);
		divide(this.squeezeBU);
		divide(this.minBetBB);
		divide(this.callMinBetBB);
		divide(this.steal3BetBB);
		divide(this.raisedBySBBB);
		divide(this.steal3BetMinRaiseBB);
		divide(this.stealCallBB);
		divide(this.stealCallMinRaiseBB);
		divide(this.bet3MinSB);
		divide(this.raisedByBBSB);
		divide(this.steal3BetSB);
		divide(this.stealCallSB);
		divide(this.stealCallMinRaiseSB);
		divide(this.foldedToSB);
		divide(this.stealCount);
		divide(this.stealStartP);
		divide(this.stealCountP);
		divide(this.stealStartEV);
		divide(this.stealCountEV);
		divide(this.isolateCount);
		divide(this.isolateStartP);
		divide(this.isolateCountP);
		divide(this.isolateStartEV);
		divide(this.isolateCountEV);
		divide(this.squeezeCount);
		divide(this.squeezeStartP);
		divide(this.squeezeCountP);
		divide(this.squeezeStartEV);
		divide(this.squeezeCountEV);
		divide(this.minBetCount);
		divide(this.minBetStartP);
		divide(this.minBetCountP);
		divide(this.minBetStartEV);
		divide(this.minBetCountEV);
		divide(this.bbStealCount);
		divide(this.bbStealStartP);
		divide(this.bbStealCountP);
		divide(this.bbStealStartEV);
		divide(this.bbStealCountEV);
		divide(this.bbBet2Count);
		divide(this.bbBet2Opportunity);
		divide(this.bbBet2StartP);
		divide(this.bbBet2CountP);
		divide(this.bbBet2StartEV);
		divide(this.bbBet2CountEV);
		divide(this.bbRaisedBySBCount);
		divide(this.bbRaisedBySBStartP);
		divide(this.bbRaisedBySBCountP);
		divide(this.bbRaisedBySBStartEV);
		divide(this.bbRaisedBySBCountEV);
		divide(this.bbCallMinBetCount);
		divide(this.bbCallMinBetStartP);
		divide(this.bbCallMinBetCountP);
		divide(this.bbCallMinBetStartEV);
		divide(this.bbCallMinBetCountEV);
		divide(this.bbStealCallCount);
		divide(this.bbStealCallStartP);
		divide(this.bbStealCallCountP);
		divide(this.bbStealCallStartEV);
		divide(this.bbStealCallCountEV);
		divide(this.sbFoldedToSBCount);
		divide(this.sbFoldedToSBStartP);
		divide(this.sbFoldedToSBCountP);
		divide(this.sbFoldedToSBStartEV);
		divide(this.sbFoldedToSBCountEV);
		divide(this.sbStealCallCount);
		divide(this.sbStealCallStartP);
		divide(this.sbStealCallCountP);
		divide(this.sbStealCallStartEV);
		divide(this.sbStealCallCountEV);
		divide(this.sbBet2StealCount);
		divide(this.sbBet2StealStartP);
		divide(this.sbBet2StealCountP);
		divide(this.sbBet2StealStartEV);
		divide(this.sbBet2StealCountEV);
		divide(this.sbBBRaisedCount);
		divide(this.sbBBRaisedStartP);
		divide(this.sbBBRaisedCountP);
		divide(this.sbBBRaisedStartEV);
		divide(this.sbBBRaisedCountEV);
		divide(this.sbStealCallMinBetCount);
		divide(this.sbStealCallMinBetOpportunity);
		divide(this.sbStealCallMinBetStartP);
		divide(this.sbStealCallMinBetCountP);
		divide(this.sbStealCallMinBetStartEV);
		divide(this.sbStealCallMinBetCountEV);
		divide(this.sbStealMinBetCount);
		divide(this.sbStealMinBetOpportunity);
		divide(this.sbStealMinBetStartP);
		divide(this.sbStealMinBetCountP);
		divide(this.sbStealMinBetStartEV);
		divide(this.sbStealMinBetCountEV);
	}

	/*- **************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	 *****************************************************************************/
	private void sumAllTODO(Players h0) {
		add(this.foldCount, h0.foldCount);
		add(this.limpCount, h0.limpCount);
		add(this.callCount, h0.callCount);
		add(this.betCount, h0.betCount);
		add(this.raiseCount, h0.raiseCount);
		add(this.checkCount, h0.checkCount);
	}

	/*- **************************************************************************
	* Divide all elements by the number of players added together 
	 *****************************************************************************/
	private void divideAllTODO() {
		divide(this.foldCount);
		divide(this.limpCount);
		divide(this.callCount);
		divide(this.betCount);
		divide(this.raiseCount);
		divide(this.checkCount);
	}

	/*-  *****************************************************************************
	 * Calculate percentage with error checking
	 ***************************************************************************** */
	double calculatePercentage(double a, double b) {
		if (b <= 0.) {
			return 0.;
		}
		final double $ = 100.0 * (a / b);
		boolean condition = ($ > 100 || $ < 0);
		if (condition) {
			Logger.logError(new StringBuilder().append("//ERROR calculatePercentage ").append($).append(" a ").append(a)
					.append(" b ").append(b).toString());
		}
		return $;
	}

	/*-***************************************************************************************
	* Add int  
	****************************************************************************************/
	private void add(int to, int from) {
		to += from;
	}

	/*-***************************************************************************************
	* Add double
	****************************************************************************************/
	private void add(double to, double from) {
		to += from;
	}

	/*-***************************************************************************************
	* Add BigDecimal
	****************************************************************************************/
	private void add(BigDecimal to, BigDecimal from) {
		to = to.add(from);
	}

	/*-***************************************************************************************
	* Divide int  
	****************************************************************************************/
	private void divide(int dividend) {
		// Calculate the quotient without rounding
		int quotient = dividend / numberOfInstancesAdded;
		// Calculate the remainder
		int remainder = dividend % numberOfInstancesAdded;
		// If the remainder is greater than or equal to half of the divisor, round up
		if (remainder >= numberOfInstancesAdded / 2) {
			if (dividend >= 0) {
				quotient++;
			} else {
				quotient--;
			}
		}
		dividend = quotient;
	}

	/*-***************************************************************************************
	*Divide double
	****************************************************************************************/
	private void divide(double dividend) {
		dividend = dividend / numberOfInstancesAdded;
	}

	/*-***************************************************************************************
	* Divide BigDecimal
	****************************************************************************************/
	private void divide(BigDecimal dividend) {
		BigDecimal d = new BigDecimal(numberOfInstancesAdded);
		dividend = dividend.divide(d);
	}

	/*-***************************************************************************************
	* Add int array dimension 1
	****************************************************************************************/
	private void add(int[] to, int[] add) {
		if (to.length != add.length) {
			Logger.logError("ERROR invalid array lengths " + to.length + " " + add.length);
			return;
		}
		for (int i = 0; i < to.length; i++) {
			to[i] += add[i];
		}
	}

	/*-***************************************************************************************
	 * Add int arrays dimension 2
	 ****************************************************************************************/
	private void add(int[][] to, int[][] add) {
		if (to.length != add.length || to[0].length != add[0].length) {
			Logger.logError("ERROR invalid array lengths " + to.length + " " + add.length + " " + to[0].length + " "
					+ add[0].length);
			return;
		}
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to[0].length; j++) {
				to[i][j] += add[i][j];
			}
		}
	}

	/*-***************************************************************************************
	 * Add int arrays dimension 3
	 ****************************************************************************************/
	private void add(int[][][] to, int[][][] add) {
		if (to.length != add.length || to[0].length != add[0].length || to[0][0].length != add[0][0].length) {
			Logger.logError("ERROR invalid array lengths " + to.length + " " + add.length + " " + to[0].length + " "
					+ add[0].length + " " + to[0][0].length + " " + add[0][0]);
			return;
		}
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to[0].length; j++) {
				for (int k = 0; k < to[0][0].length; k++) {
					to[i][j][k] += add[i][j][k];
				}
			}
		}
	}

	/*-***************************************************************************************
	 * Add int arrays dimension 4
	 ****************************************************************************************/
	private void add(int[][][][] to, int[][][][] add) {
		if (to.length != add.length || to[0].length != add[0].length || to[0][0].length != add[0][0].length
				|| to[0][0][0].length != add[0][0][0].length) {
			Logger.logError("ERROR invalid array lengths " + to.length + " " + add.length + " " + to[0].length + " "
					+ add[0].length + " " + to[0][0].length + " " + add[0][0] + " " + to[0][0][0].length + " "
					+ add[0]);
			return;
		}
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to[0].length; j++) {
				for (int k = 0; k < to[0][0].length; k++) {
					for (int l = 0; l < to[0][0][0].length; l++) {
						to[i][j][k][l] += add[i][j][k][l];
					}
				}
			}
		}
	}

	/*-***************************************************************************************
	* Add int array dimension 1
	****************************************************************************************/
	private void add(double[] to, double[] add) {
		if (to.length != add.length) {
			Logger.logError("ERROR invalid array lengths " + to.length + " " + add.length);
			return;
		}
		for (int i = 0; i < to.length; i++) {
			to[i] += add[i];
		}
	}

	/*-***************************************************************************************
	 * Add int arrays dimension 2
	 ****************************************************************************************/
	private void add(double[][] to, double[][] add) {
		if (to.length != add.length || to[0].length != add[0].length) {
			Logger.logError("ERROR invalid array lengths " + to.length + " " + add.length + " " + to[0].length + " "
					+ add[0].length);
			return;
		}
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to[0].length; j++) {
				to[i][j] += add[i][j];
			}
		}
	}

	/*-***************************************************************************************
	 * Add int arrays dimension 3
	 ****************************************************************************************/
	private void add(double[][][] to, double[][][] add) {
		if (to.length != add.length || to[0].length != add[0].length || to[0][0].length != add[0][0].length) {
			Logger.logError("ERROR invalid array lengths " + to.length + " " + add.length + " " + to[0].length + " "
					+ add[0].length + " " + to[0][0].length + " " + add[0][0]);
			return;
		}
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to[0].length; j++) {
				for (int k = 0; k < to[0][0].length; k++) {
					to[i][j][k] += add[i][j][k];
				}
			}
		}
	}

	/*-***************************************************************************************
	 * Add int arrays dimension 4
	 ****************************************************************************************/
	private void add(double[][][][] to, double[][][][] add) {
		if (to.length != add.length || to[0].length != add[0].length || to[0][0].length != add[0][0].length
				|| to[0][0][0].length != add[0][0][0].length) {
			Logger.logError("ERROR invalid array lengths " + to.length + " " + add.length + " " + to[0].length + " "
					+ add[0].length + " " + to[0][0].length + " " + add[0][0] + " " + to[0][0][0].length + " "
					+ add[0]);
			return;
		}
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to[0].length; j++) {
				for (int k = 0; k < to[0][0].length; k++) {
					for (int l = 0; l < to[0][0][0].length; l++) {
						to[i][j][k][l] += add[i][j][k][l];
					}
				}
			}
		}
	}

	/*-***************************************************************************************
	* Add int array dimension 1
	****************************************************************************************/
	private void add(BigDecimal[] to, BigDecimal[] add) {
		if (to.length != add.length) {
			Logger.logError("ERROR invalid array lengths " + to.length + " " + add.length);
			return;
		}
		for (int i = 0; i < to.length; i++) {
			to[i] = to[i].add(add[i]);
		}
	}

	/*-***************************************************************************************
	 * Add int arrays dimension 2
	 ****************************************************************************************/
	private void add(BigDecimal[][] to, BigDecimal[][] add) {
		if (to.length != add.length || to[0].length != add[0].length) {
			Logger.logError("ERROR invalid array lengths " + to.length + " " + add.length + " " + to[0].length + " "
					+ add[0].length);
			return;
		}
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to[0].length; j++) {
				to[i][j] = to[i][j].add(add[i][j]);
			}
		}
	}

	/*-***************************************************************************************
	 * Add int arrays dimension 3
	 ****************************************************************************************/
	private void add(BigDecimal[][][] to, BigDecimal[][][] add) {
		if (to.length != add.length || to[0].length != add[0].length || to[0][0].length != add[0][0].length) {
			Logger.logError("ERROR invalid array lengths " + to.length + " " + add.length + " " + to[0].length + " "
					+ add[0].length + " " + to[0][0].length + " " + add[0][0]);
			return;
		}
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to[0].length; j++) {
				for (int k = 0; k < to[0][0].length; k++) {
					to[i][j][k] = to[i][j][k].add(add[i][j][k]);
				}
			}
		}
	}

	/*-***************************************************************************************
	 * Add int arrays dimension 4
	 ****************************************************************************************/
	private void add(BigDecimal[][][][] to, BigDecimal[][][][] add) {
		if (to.length != add.length || to[0].length != add[0].length || to[0][0].length != add[0][0].length
				|| to[0][0][0].length != add[0][0][0].length) {
			Logger.logError("ERROR invalid array lengths " + to.length + " " + add.length + " " + to[0].length + " "
					+ add[0].length + " " + to[0][0].length + " " + add[0][0] + " " + to[0][0][0].length + " "
					+ add[0]);
			return;
		}
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to[0].length; j++) {
				for (int k = 0; k < to[0][0].length; k++) {
					for (int l = 0; l < to[0][0][0].length; l++) {
						to[i][j][k][l] = to[i][j][k][l].add(add[i][j][k][l]);
					}
				}
			}
		}
	}

	/*-***************************************************************************************
	* Divided int array dimension 1
	****************************************************************************************/
	private void divide(int[] dividend) {
		int quotient = 0;
		int remainder = 0;
		for (int i = 0; i < dividend.length; i++) {
			quotient = dividend[i] / numberOfInstancesAdded;
			remainder = dividend[i] % numberOfInstancesAdded;
			if (remainder >= numberOfInstancesAdded / 2) {
				if (dividend[i] >= 0) {
					quotient++;
				} else {
					quotient--;
				}
			}
			dividend[i] = quotient;
		}
	}

	/*-***************************************************************************************
	* Divided int array dimension 2
	****************************************************************************************/
	private void divide(int[][] dividend) {
		int quotient = 0;
		int remainder = 0;
		for (int i = 0; i < dividend.length; i++) {
			for (int j = 0; j < dividend[0].length; j++) {
				quotient = dividend[i][j] / numberOfInstancesAdded;
				remainder = dividend[i][j] % numberOfInstancesAdded;
				if (remainder >= numberOfInstancesAdded / 2) {
					if (dividend[i][j] >= 0) {
						quotient++;
					} else {
						quotient--;
					}
				}
				dividend[i][j] = quotient;
			}
		}
	}

	/*-***************************************************************************************
	* Divided int array dimension 3
	****************************************************************************************/
	private void divide(int[][][] dividend) {
		int quotient = 0;
		int remainder = 0;
		for (int i = 0; i < dividend.length; i++) {
			for (int j = 0; j < dividend[0].length; j++) {
				for (int k = 0; k < dividend[0][0].length; k++) {
					quotient = dividend[i][j][k] / numberOfInstancesAdded;
					remainder = dividend[i][j][k] % numberOfInstancesAdded;
					if (remainder >= numberOfInstancesAdded / 2) {
						if (dividend[i][j][k] >= 0) {
							quotient++;
						} else {
							quotient--;
						}
					}
					dividend[i][j][k] = quotient;
				}
			}
		}
	}

	/*-***************************************************************************************
	* Divided int array dimension 4
	****************************************************************************************/
	private void divide(int[][][][] dividend) {
		int quotient = 0;
		int remainder = 0;
		for (int i = 0; i < dividend.length; i++) {
			for (int j = 0; j < dividend[0].length; j++) {
				for (int k = 0; k < dividend[0][0].length; k++) {
					for (int l = 0; l < dividend[0][0][0].length; l++) {
						quotient = dividend[i][j][k][l] / numberOfInstancesAdded;
						remainder = dividend[i][j][k][l] % numberOfInstancesAdded;
						if (remainder >= numberOfInstancesAdded / 2) {
							if (dividend[i][j][k][l] >= 0) {
								quotient++;
							} else {
								quotient--;
							}
						}
						dividend[i][j][k][l] = quotient;
					}
				}
			}
		}
	}

	/*-***************************************************************************************
	* Divided double array dimension 1
	****************************************************************************************/
	private void divide(double[] dividend) {
		for (int i = 0; i < dividend.length; i++) {
			dividend[i] = dividend[i] / numberOfInstancesAdded;
		}
	}

	/*-***************************************************************************************
	* Divided double array dimension 2
	****************************************************************************************/
	private void divide(double[][] dividend) {
		for (int i = 0; i < dividend.length; i++) {
			for (int j = 0; j < dividend[0].length; j++) {
				dividend[i][j] = dividend[i][j] / numberOfInstancesAdded;
			}
		}
	}

	/*-***************************************************************************************
	* Divided double array dimension 3
	****************************************************************************************/
	private void divide(double[][][] dividend) {
		for (int i = 0; i < dividend.length; i++) {
			for (int j = 0; j < dividend[0].length; j++) {
				for (int k = 0; k < dividend[0][0].length; k++) {
					dividend[i][j][k] = dividend[i][j][k] / numberOfInstancesAdded;
				}
			}
		}
	}

	/*-***************************************************************************************
	* Divided double array dimension 4
	****************************************************************************************/
	private void divide(double[][][][] dividend) {
		for (int i = 0; i < dividend.length; i++) {
			for (int j = 0; j < dividend[0].length; j++) {
				for (int k = 0; k < dividend[0][0].length; k++) {
					for (int l = 0; l < dividend[0][0][0].length; l++) {
						dividend[i][j][k][l] = dividend[i][j][k][l] / numberOfInstancesAdded;
					}
				}
			}
		}
	}

	/*-***************************************************************************************
	* Divided BigDecimal array dimension 1
	****************************************************************************************/
	private void divide(BigDecimal[] to) {
		BigDecimal dBD = new BigDecimal(numberOfInstancesAdded).setScale(2, RoundingMode.HALF_EVEN);
		for (int i = 0; i < to.length; i++) {
			to[i] = to[i].divide(dBD, 2, RoundingMode.HALF_EVEN);
		}
	}

	/*-***************************************************************************************
	* Divided BigDecimal array dimension 2
	****************************************************************************************/
	private void divide(BigDecimal[][] to) {
		BigDecimal dBD = new BigDecimal(numberOfInstancesAdded).setScale(2, RoundingMode.HALF_EVEN);
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to[0].length; j++) {
				to[i][j] = to[i][j].divide(dBD, 2, RoundingMode.HALF_EVEN);
			}
		}
	}

	/*-***************************************************************************************
	* Divided BigDecimal array dimension 3
	****************************************************************************************/
	private void divide(BigDecimal[][][] to) {
		BigDecimal dBD = new BigDecimal(numberOfInstancesAdded).setScale(2, RoundingMode.HALF_EVEN);
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to[0].length; j++) {
				for (int k = 0; k < to[0][0].length; k++) {
					to[i][j][k] = to[i][j][k].divide(dBD, 2, RoundingMode.HALF_EVEN);
				}
			}
		}
	}

	/*-***************************************************************************************
	* Divided BigDecimal array dimension 4
	****************************************************************************************/
	private void divide(BigDecimal[][][][] to) {
		BigDecimal dBD = new BigDecimal(numberOfInstancesAdded).setScale(2, RoundingMode.HALF_EVEN);
		for (int i = 0; i < to.length; i++) {
			for (int j = 0; j < to[0].length; j++) {
				for (int k = 0; k < to[0][0].length; k++) {
					for (int l = 0; l < to[0][0][0].length; l++) {
						to[i][j][k][l] = to[i][j][k][l].divide(dBD, 2, RoundingMode.HALF_EVEN);
					}
				}
			}
		}
	}

	/*-*****************************************************************************
	 * Write Object to file - this
	 * Returns false if error
	 ******************************************************************************/
	boolean writeToFile(String path) {
		final var filename = path;
		// Saving of object in a file
		try (var file = new FileOutputStream(filename); var out = new ObjectOutputStream(file)) {
			// Method for serialization of object
			out.writeObject(this);
			return true;
		} catch (IOException i) {
			i.printStackTrace();
			return false;
		}
	}

	/*-*****************************************************************************
	 * Read a Range from a disk file. 
	 * Arg0 - The full path name.
	 * Returns null if error
	***************************************************************************** */
	Players readFromFile(String path) {
		Players r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (Players) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

}
