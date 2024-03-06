package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.util.HashMap;

public class HandHistoryObjects implements java.io.Serializable, Constants {
	private final long serialVersionUID = 1234567L;

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
	 * This class converts one or many Universal Hand History files to several Class instances that each
	 * contain data extracted from these files.
	 * The extraction process for each Class has different levels of analysis but data is still relatively
	 * primitive. 
	 * More a separation of types of information than analysis.
	 * 
	 * GUIPlayerSelection creates an instance of this Class from a Universal Hand History Folder.
	 * 
	 * These objects will be used to do much more detailed analysis bty other Classes such as 
	 * GUIAnalyze.
	*************************************************************************************************/

	/*- ********************************************************************************************** 
	 *Data collected - all public
	*************************************************************************************************/
	final ClassificationData rules = new ClassificationData();

	final int[] playerIDs = { -1, -1, -1, -1, -1, -1 };
	Hand oneHand; // Current hand

	// Players andPlayerClassification are always together. Players data collected
	// andPlayerClassification data calculated.
	static HashMap<Integer, Players> playersMap = new HashMap<>(50000);

	static HashMap<Integer, Integer> playerNamesMap = new HashMap<>(50000);

	static PlayerOneHand[] playerOneHand = new PlayerOneHand[PLAYERS];

	static Players[] playerThisHand = new Players[PLAYERS];

	/*- ******************************************************************************************************************
	 * Arrays of Player.... by position and relative position
	 ****************************************************************************************************************** */
	Players[] playerPos = { new Players(), new Players(), new Players(), new Players(), new Players(), new Players() };
	Players[] playerRp = { new Players(), new Players(), new Players(), new Players(), new Players(), new Players(),
			new Players(), new Players(), new Players() };

	PlayerBetSize[] playerBetSizePos = { new PlayerBetSize(), new PlayerBetSize(), new PlayerBetSize(),
			new PlayerBetSize(), new PlayerBetSize(), new PlayerBetSize() };
	PlayerBetSize[] playerBetSizeRp = { new PlayerBetSize(), new PlayerBetSize(), new PlayerBetSize(),
			new PlayerBetSize(), new PlayerBetSize(), new PlayerBetSize(), new PlayerBetSize(), new PlayerBetSize(),
			new PlayerBetSize(), };

	PlayerMDF[] playerMDFPos = { new PlayerMDF(), new PlayerMDF(), new PlayerMDF(), new PlayerMDF(), new PlayerMDF(),
			new PlayerMDF() };
	PlayerMDF[] playerMDFRp = { new PlayerMDF(), new PlayerMDF(), new PlayerMDF(), new PlayerMDF(), new PlayerMDF(),
			new PlayerMDF(), new PlayerMDF(), new PlayerMDF(), new PlayerMDF(), new PlayerMDF() };

	PlayerMoney[] playerMoneyPos = { new PlayerMoney(), new PlayerMoney(), new PlayerMoney(), new PlayerMoney(),
			new PlayerMoney(), new PlayerMoney() };
	PlayerMoney[] playerMoneyRp = { new PlayerMoney(), new PlayerMoney(), new PlayerMoney(), new PlayerMoney(),
			new PlayerMoney(), new PlayerMoney(), new PlayerMoney(), new PlayerMoney(), new PlayerMoney() };

	PlayerClean[] playerCleanPos = { new PlayerClean(), new PlayerClean(), new PlayerClean(), new PlayerClean(),
			new PlayerClean(), new PlayerClean() };
	PlayerClean[] playerCleanRp = { new PlayerClean(), new PlayerClean(), new PlayerClean(), new PlayerClean(),
			new PlayerClean(), new PlayerClean(), new PlayerClean(), new PlayerClean(), new PlayerClean() };

	PlayerBoardIndexes[] playerBoardIndexesPos = { new PlayerBoardIndexes(), new PlayerBoardIndexes(),
			new PlayerBoardIndexes(), new PlayerBoardIndexes(), new PlayerBoardIndexes(), new PlayerBoardIndexes() };
	PlayerBoardIndexes[] playerBoardIndexesRp = { new PlayerBoardIndexes(), new PlayerBoardIndexes(),
			new PlayerBoardIndexes(), new PlayerBoardIndexes(), new PlayerBoardIndexes(), new PlayerBoardIndexes(),
			new PlayerBoardIndexes(), new PlayerBoardIndexes(), new PlayerBoardIndexes() };

	/*- ********************************************************************************************** 
	* integer values are used because a player may win more than once and / or multiple players 
	* may win. Value is number of times a player has won. 0 is not a winner.
	*************************************************************************************************/
	private int[] showdownWinners = new int[PLAYERS];
	private int[][] streetWinners = new int[6][PLAYERS];

	Players playerPosAll = new Players();
	Players playerRpAll = new Players();
	PlayerBetSize playerBetSizePosAll = new PlayerBetSize();
	PlayerBetSize playerBetSizeRpAll = new PlayerBetSize();
	PlayerMoney playerMoneyPosAll = new PlayerMoney();
	PlayerMoney playerMoneyRpAll = new PlayerMoney();
	PlayerMDF playerMDFPosAll = new PlayerMDF();
	PlayerMDF playerMDFRpAll = new PlayerMDF();
	PlayerBoardIndexes playerBoardIndexesPosAll = new PlayerBoardIndexes();
	PlayerBoardIndexes playerBoardIndexesRpAll = new PlayerBoardIndexes();
	PlayerClean playerCleanPosAll = new PlayerClean();
	PlayerClean playerCleanRpAll = new PlayerClean();

	private int actionArrayIndex = 0;
	private int actionArrayCount = 0;

	private int id = -1; // Player ID
	private int street = -1;
	private int seat = -1;
	private int pos = -1;
	private int rp = -1; // Relative position
	private int action = -1;
	private int betToMeNow = -1;
	private int betTypeNow = -1;

	private int preflopCount = -1;
	private int flopCount = -1;
	private int turnCount = -1;
	private int riverCount = -1;
	private int showdownCount = -1;
	private int summaryCount = -1;

	/*- ************************************************************************************************
	 * Used for preflop advanced play
	  *************************************************************************************************/
	private boolean preflopIsolateBU = false;
	private boolean preflopSqueezeBU = false;
	private boolean preflopMinBetBU = false;
	private boolean preflopStealBU = false;
	private boolean preflopMinBetBB = false;
	private boolean preflopCallMinBetBB = false;
	private boolean preflopSteal3BetMinRaiseBB = false;
	private boolean preflopRaisedBySBBB = false;
	private boolean preflopSteal3BetMinRaiseSB = false;
	private boolean preflopStealCallBBPosBB = false;
	private boolean preflopStealCallMinRaiseBB = false;
	private boolean preflopBet3MinSB = false;
	private boolean preflopRaisedByBBSB = false;
	private boolean preflopSteal3BetSB = false;
	private boolean preflopStealCallSSB = false;
	private boolean preflopstealCallMinRaiseSB = false;
	private boolean preflopFoldedToSB = false;

	private boolean flopCBet = false;

	// TODO
	private final int utgSeat = 0;
	private final int mpSeat = 0;
	private final int cutoffSeat = 0;

	private final int flopIndex = 0; // TODO

	/*-  ************************************************************************************************
	 * These are instances of the Player and PlayerCharacteristics Classes.
	 * player is for the current hand only. It is created new for each hand played.
	 * players is the sum of all hands played. 
	 * At the end of each hand player is added to players.
	 * The reason for a temporary player Class is to simplify analysis.
	 *************************************************************************************************/
	private final boolean[] playerPosted = new boolean[PLAYERS]; // posted to enter early
	private final boolean[][] playerSawStreet = new boolean[PLAYERS][STREET]; // saw street
	private final boolean[] playerSawShowdown = new boolean[PLAYERS]; // saw showdown
	private final boolean[][] playerWonStreet = new boolean[PLAYERS][ALL_STREETS]; // won street
	private final boolean[] playerWonShowdown = new boolean[PLAYERS];// won Show Down

	// Player checked
	private final boolean[][] playerChecked = new boolean[STREET][PLAYERS];

	private final boolean[][] betToMe = new boolean[NUM_POS][BETS_MAX];

	private int index = 0;

	private final int[] selectedPlayerID = new int[2000000];

	private int fileNameNum = 100;

	private final BigDecimal betLast = zeroBD;

	private final int maxHandSize = 0;

	private final int maxArrayOfHands = 0;

	private int lastSeat = -1;

	private int firstSeat = -1;
	private int handIndex = 0;
	private final int callPreflopCount = 0;
	private int bet2PreflopCount = 0;

	private final int pb = 0;

	private int players = 0;

	private final String runNumber = "";

	private boolean exit = false;

	private boolean analyze = false;

	private boolean select = false;

	private boolean finish = false;

	private boolean buttonBet2 = false;
	private boolean buttonBet3 = false;
	private final boolean buttonBet4 = false;
	private boolean buttonAllin = false;
	private final boolean sbRaised = false;
	private final boolean buttonMinBets = false;
	private boolean foldedToSB = false;
	private boolean bbRaiseBySB = false;
	private final boolean sbRaisedByBB = false;

	private final boolean cBet = false;
	private final boolean checkRaise = false;
	private final boolean donkBet = false;
	private boolean barrel = false;
	private boolean bluffing = false;

	private double bet = 0;
	private double mdf = 0;
	private final double[] streetMDF = new double[STREETS];

	/*- Used for c bet analysis */
	private final int[] lastBetStreet = new int[STREETS];
	private final int[] lastRaiseStreet = new int[STREETS];
	private final int[] lastCallStreet = new int[STREETS];
	private final int[] lastCheckStreet = new int[STREETS];
	private final int[] lastFoldStreet = new int[STREETS];

	/*- ********************************************************************************************** 
	* Do one universal format hand
	* This is two of two public methods.
	*************************************************************************************************/
	boolean doOneHand(Hand h) {
		oneHand = h;
		if (!updatePlayers())
			return false;
		if (oneHand != null) {
			processOneHand();
			clearActionArrays();
			oneHandAnalysis();
			if (street < RIVER) {
				updateForMDFPos();
				updateForMDFRp();
			}
			updateForWinLoose();
		}
		return true;
	}

	/*- ************************************************************************************************
	 * Initialize action arrays.
	 * Data used for several purposes.
	 *************************************************************************************************/
	private void clearActionArrays() {
		preflopIsolateBU = false;
		preflopSqueezeBU = false;
		preflopMinBetBU = false;
		preflopStealBU = false;
		preflopMinBetBB = false;
		preflopCallMinBetBB = false;
		preflopSteal3BetMinRaiseBB = false;
		preflopRaisedBySBBB = false;
		preflopSteal3BetMinRaiseSB = false;
		preflopStealCallBBPosBB = false;
		preflopStealCallMinRaiseBB = false;
		preflopBet3MinSB = false;
		preflopRaisedByBBSB = false;
		preflopSteal3BetSB = false;
		preflopStealCallSSB = false;
		preflopstealCallMinRaiseSB = false;
		preflopFoldedToSB = false;
		flopCBet = false;
	}

	/*-************************************************************************************************
	 * oneHand is an instance of Player that is one current hand.
	 * That is what we use as our input for these calculations.
	 * Local variables are used to temporarily save the results.
	 * 
	 * Analyze one hand and save data to be used in statistics.
	 * Calculate and save data about how a specific hand was played. Can't do with just counts of actions.
	 * Example:
	 * 	 	CBet Flop depends on preflop player actions for this specific player.
	 * 		If player was last to raise preflop and then was the first to bet the Flop then
	 * 			What Is a Continuation Bet? A continuation bet—also known as a c-bet—is a bet 
	 * 			made by the player who made the last aggressive action on the previous street. 
	 * 			This process starts with a player making the final raise preflop and then firing the 
	 * 			first bet on the flop.
	 * 			A turn c-bet means betting on the turn after raising preflop and betting the flop.
	 *
	 *			For example, if you raise on the Hijack and the button calls, and you bet again on the flop, 
	 *			you’re making a continuation bet, or c-bet. If your opponent calls and you bet again on the turn, 
	 *			you’re making a turn c-bet. Also known as a double barrel.
	 *
	 *  Data to add
	 *  		WTSD = 	Went To Show Down, 
	 *  		WSD = 	Won Money At Show Down, 
	*			WWSF = Won When Saw Flop)
	*			Squeeze
	*			CBet ( Flop, Turn, and River )
	*			Fold to CBet (Flop, Turn, River )
	*			
	*
	 * from HM3
	 * 
	 * 		VPIP 					26.6
	 * 		PFR						17.3
	 * 		3Bet						8.3
	 * 		postflopAgg		26.9
	 * 		WTSD					29.2
	 * 		WWSF					45.7
	 * 		Won SD				50.4
	 * 		flop c-bet			61.7
	 * 		turn c-bet			52.0
	 * 		river c-bet			53.8
	 * 		Fold vs CBET		43.3
	 * 		Fold vs T CBET	42.9
	 * 		Fold vs R CBET	52.3
	 * 		Raise vs F CBet	10.5
	 * 		Raise vs T CBet	 9.51
	 * 		Raise vs R CBet	12.6
	 * 		Squeeze				 7.15
	 *  	Call 2 raisers		 3.18
	 *  	Raise 2 raisers	 3.50
	 * 		fold to 3bet		46.7
	 * 		Call vs 3Bet			40.9
	 * 		Raise vs 3Bet		12.4
	 * 		foldVs c-Bet		43.3
	 * 		Fold to 4Bet		47.1
	 * 		Call vs 4Bet			35.0
	 * 		Raise vs 4Bet		17.9
	 * 
	************************************************************************************************ */
	private void oneHandAnalysis() {
		updateForWinLoose();

		final boolean[][] raiseStreet = new boolean[PLAYERS][STREETS];
		for (int i = 0; i < PLAYERS; ++i) {
			for (int j = 0; j < STREETS; ++j) {
				if (playerOneHand[i].playerRaise[j]) {
					raiseStreet[i][j] = true;
				}
				if (playerOneHand[i].playerFold[j] && playerOneHand[i].playerRaise[j]
						&& playerOneHand[i].playerBetType[j] == BET3) {
					++playerThisHand[i].foldTo3BetAfterRaisingCount[j];
				}
				if (playerOneHand[i].playerBetType[j] == BET3) {
					++playerThisHand[i].PFRCount[j];
				}
				if (raiseStreet[i][j]) {
					++playerThisHand[i].PFRCount[j];
				}
				// TODO k == 0final boolean condition = j > 0 && k == 0 &&
				// playerOneHand[i].playerBetType[j] == BET1
				// && (playerOneHand[i].playerRaise[j] || playerOneHand[i].playerCall[j]) && j >
				// 0
				// && k == 0 && playerOneHand[i].playerFold[j]
				// && playerOneHand[i].playerBetType[j] == BET1;
				final boolean condition = j > 0 && playerOneHand[i].playerBetType[j] == BET1
						&& (playerOneHand[i].playerRaise[j] || playerOneHand[i].playerCall[j]) && j > 0
						&& playerOneHand[i].playerFold[j] && playerOneHand[i].playerBetType[j] == BET1;

				// TODO
				// ++playerThisHand[seat].continuationPos[i][j];
				// ++playerThisHand[seat].continuationRp[i][j];// ERROR not rp index
				// MDF data - Fold
				// MDF data - Raise and continuation
				if (condition) {
					// TODO
					// ++playerThisHand[i].foldPos[i][j];
					// ++playerThisHand[i].foldRp[i][j];
				}
			}
		}

		for (int i = 0; i < PLAYERS; ++i) {
			for (int j = 0; j < STREETS; ++j) {
				if (playerOneHand[i].playerWTSD) {
					++playerThisHand[i].WTSDCount[j];

					if (playerOneHand[i].playerActed[j]) {
						++playerThisHand[i].WWSFCount[j];
					}
					if (playerOneHand[i].playerWonSD) {
						++playerThisHand[i].W$SD_WSDCount[j];
					}
				}
				if (i == HandCalculations.BUSeat && preflopSqueezeBU) {
					++playerThisHand[i].squeezeCount;
				}
				final boolean condition1 = raiseStreet[i][0] && raiseStreet[i][1] && raiseStreet[i][1]
						&& raiseStreet[i][2] && !raiseStreet[i][3];
				if (condition1) {
					++playerThisHand[i].barrellFlopTurnCount;
				}
				if (raiseStreet[i][1] && raiseStreet[i][2] && raiseStreet[i][3]) {
					++playerThisHand[i].barrellFlopTurnRiverCount;
				}
				if (!raiseStreet[i][1] && raiseStreet[i][2] && raiseStreet[i][3]) {
					++playerThisHand[i].barrellTurnRiverCount;
				}
				if (raiseStreet[i][0] && raiseStreet[i][1] && raiseStreet[i][2] && raiseStreet[i][3]) {
					++playerThisHand[i].barrellPreflopFlopTurnRiverCount;
				}
			}
		}
	}

	/*- ************************************************************************************************
	 * Same as updateForMDFPos() but Relative Position 
	 ************************************************************************************************ */
	private void updateForMDFRp() {
		final int[][] totalRp = new int[PLAYERS][STREETS];
		final int[][] contRp = new int[PLAYERS][STREETS];
		final int[][] foldRp = new int[PLAYERS][STREETS];
		for (int seat = 0; seat < PLAYERS; ++seat) {

			for (int street = 0; street < 4; ++street) {
				contRp[seat][street] += playerMDFRp[seat].continuationBet1Rp[street]
						+ playerMDFRp[seat].continuationBet2Rp[street]
						+ playerMDFRp[seat].continuationCallBet1Rp[street]
						+ +playerMDFRp[seat].continuationCallBet2Rp[street]
						+ playerMDFRp[seat].continuationCheckRp[street];
				foldRp[seat][street] += playerMDFRp[seat].continuationFoldToBet1Rp[street]
						+ playerMDFRp[seat].continuationFoldToBet2Rp[street];
				totalRp[seat][street] += foldRp[seat][street] + contRp[seat][street];
			}
			playerMDFRp[seat].contFreqRp[street] = (double) contRp[seat][street] / (double) totalRp[seat][street];
			playerMDFRp[seat].foldFreqRp[street] = (double) foldRp[seat][street] / (double) totalRp[seat][street];
		}
	}

	/*-************************************************************************************************
	 * MDF
	 * 
	* MDF is basically the percentage of times that a player folds + the percentage
	 * of times that the player continues ( call, bet, raise, check).
	 * The sum must be 100%.
	 * 
	 * MDF is the minimum percentage of time you must call (or raise) to prevent your 
	 * opponent from profiting by always bluffing you. 
	 * If you fold more often than the MDF indicates, your opponent can exploit you by 
	 * over-bluffing when they bet.
	 * To calculate MDF is simple: pot size / (pot size + bet size) 
	* Then, multiply the answer by 100 to express it as a percentage.
	* 
	 * int[][][] handsPos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	 * int[][][] continuationPos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	 * int[][][]continuationBet1Pos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	 * int[][][]continuationBet2Pos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	 * int[][][]continuationCallBet1Pos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	 * int[][][]continuationCallBet2Pos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	 * int[][][]continuationCheckPos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	 * int[][][] continuationFoldToBet1Pos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	 * int[][][] continuationFoldToBet2Pos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	double[][]foldFreqPos = new double[NUM_STREETS][NUM_POS];
	double[][]contFreqPos = new double[NUM_STREETS][NUM_POS];
	double[] foldFreq = new double[NUM_STREETS];
	double[] contFreq = new double[NUM_STREETS];
	 * 
	************************************************************************************************ */
	private void updateForMDFPos() {
		final int[][] totalPos = new int[PLAYERS][STREETS];
		final int[][] contPos = new int[PLAYERS][STREETS];
		final int[][] foldPos = new int[PLAYERS][STREETS];
		final int[][] totalRp = new int[PLAYERS][STREETS];
		final int[][] contRp = new int[PLAYERS][STREETS];
		final int[][] foldRp = new int[PLAYERS][STREETS];

		final int[][] totalStreet = new int[PLAYERS][STREETS];
		final int[][] contStreet = new int[PLAYERS][STREETS];
		final int[][] foldStreet = new int[PLAYERS][STREETS];

		for (int seat = 0; seat < PLAYERS; ++seat) {
			for (int street = 0; street < 4; ++street) {
				contPos[seat][street] += playerMDFPos[seat].continuationBet1Pos[street]
						+ playerMDFPos[seat].continuationBet2Pos[street]
						+ playerMDFPos[seat].continuationCallBet1Pos[street]
						+ +playerMDFPos[seat].continuationCallBet2Pos[street]
						+ playerMDFPos[seat].continuationCheckPos[street];
				foldPos[seat][street] += playerMDFPos[seat].continuationFoldToBet1Pos[street]
						+ playerMDFPos[seat].continuationFoldToBet2Pos[street];
				totalPos[seat][street] += foldPos[seat][street] + contPos[seat][street];
			}
			contStreet[seat][street] += contPos[seat][street];
			foldStreet[seat][street] += foldPos[seat][street];
			totalStreet[seat][street] += totalPos[seat][street];
			playerMDFPos[seat].contFreqPos[street] = (double) contPos[seat][street] / (double) totalPos[seat][street];
			playerMDFPos[seat].foldFreqPos[street] = (double) foldPos[seat][street] / (double) totalPos[seat][street];
			playerMDFPos[seat].contFreq[street] = (double) contStreet[seat][street]
					/ (double) totalStreet[seat][street];
			playerMDFPos[seat].foldFreq[street] = (double) foldStreet[seat][street]
					/ (double) totalStreet[seat][street];
		}
	}

	/*-************************************************************************************************
	 * Player win / loose related to bet steps ( bet to pot size by 1/4 steps )
	 * is saved temporarily because the win / loose for player is not known 
	 * until the hand completes, either at Show Down or on a street before because
	 * all but 1 player folded.
	 * 
	 * When win / loose is determined then data in Allplayers is updated.
	 * 		int[][][] betSize_playerWinLooseShowdownBet = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	 *		int[][][] betSize_playerWinLooseStreetBet = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	 *
	 * BigDecimal stack$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	 *	BigDecimal averageStack$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	 *	BigDecimal wonShowdown$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	 *	BigDecimal stack$Current = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	 *	BigDecimal stack$Previous = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	 *	BigDecimal winnings$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // Won or lost
	 *	BigDecimal won$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	 *************************************************************************************************/
	private void updateForWinLoose() {
		final boolean wonShowdown = false;
		final int wonShowdownSeat = 0;
		boolean wonStreet = false;
		int wonStreetSeat = 0;
		int wonStreetStreet = 0;

		for (int i = 0; i < PLAYERS; ++i) {
			// TODO if (oneHand.wonShowdown[i] > 0) {
			// wonShowdown = true;
			// wonShowdownSeat = i;
			// }
		}
		for (int i = 0; i < PLAYERS; ++i) {
			for (int j = 0; j < STREET; ++j) {
				if (playerWonStreet[i][j]) {
					wonStreet = true;
					wonStreetSeat = i;
					wonStreetStreet = j;
				}
			}
		}

		if (wonShowdown) {
			// TODO?
		}

		if (wonStreet) {
			// TODO ?
		}
	}

	/*-************************************************************************************************
	 * Every player has a unique ID as an Integer. We do not use a player name.
	 * The playerIDs array was initialized to all -1 at the start of this pass.
	 * If playerIDs has the same ID as the player in Hand, we know that there are already 
	 * instances for this ID in playerThisHand and playerClassify and HashMaps.
	 * If that is the case then we update the hand counts in both instances. 
	 * If the playerIDs do not match then we check to see if that ID is the HashMap playersMap already 
	 * If yes, we get a copy from the HashMap and put it in playerThisHand and playerClassify.
	 * If no, then we create new instances, add them to a playerThisHand and playerClassify and to 
	 * the HashMaps.
	************************************************************************************************ */
	private boolean updatePlayers() {
		initializeHandData();
		int count = 0;
		for (int i = 0; i < PLAYERS; ++i) {
			id = oneHand.IDArray[i];
			if (id < 0) {
				++count;
			}
		}
		// If all players are not on the list delete Hand and return
		if (count == 0) {
			return false;
		}
		// At least one id is on the list
		for (int i = 0; i < PLAYERS; ++i) {
			id = oneHand.IDArray[i];
			HandCalculations.setPlayerFolded(i, false);
			playerOneHand[i] = new PlayerOneHand();
			playerOneHand[i].playerID = id;
			playerOneHand[i].handID = oneHand.handID;
			// Same Id then everything is still here from the last hand
			if (playerIDs[i] == id) {
				++playerThisHand[i].handsPlayed;
			} else {
				// Save player from last hand new id in seat
				if (playerThisHand[i] != null) {
					playersMap.put(playerThisHand[i].playerID, playerThisHand[i]);
				}
				playerIDs[i] = id;
				// Old player is back this hand
				playerThisHand[i] = playersMap.get(id);
				if (playerThisHand[i] == null) {
					// New player this hand so create Players for him
					playerThisHand[i] = new Players();
					playerThisHand[i].playerID = id;
					playerThisHand[i].handsPlayed = 1;
				}
			}
		}
		return true;
	}

	/*-************************************************************************************************
	 * Initialize data for new hand
	 *************************************************************************************************/
	private void initializeHandData() {
		for (int i = 0; i < PLAYERS; ++i) {
			playerPosted[i] = false;
			playerSawShowdown[i] = false;
			playerWonShowdown[i] = false;
			for (int j = 0; j < 4; ++j) {
				playerSawStreet[i][j] = false;
				playerWonStreet[i][j] = false;
				playerChecked[j][i] = false;
			}
		}
		for (int i = 0; i < NUM_POS; ++i) {
			for (int j = 0; j < BETS_MAX; ++j) {
				betToMe[i][j] = false;
			}
		}
	}

	/*-************************************************************************************************
	 * Update handsPlayed in all arrays
	************************************************************************************************ */
	private void actionHandsPlayedPos() {
		if (HandCalculations.foldArray[seat]) {
			HandReports.actionReport(oneHand);
			System.out.println("//XXX " + oneHand.handID);
			Crash.log("xxx " + "");
		}

		++playerThisHand[seat].rpCount[street][pos][rp];
		++playerThisHand[seat].handsPlayed;
		++playerPos[pos].rpCount[street][pos][rp];
		++playerPos[pos].handsPlayed;
		++playerRp[rp].rpCount[street][pos][rp];
		++playerRp[rp].handsPlayed;
		if (street == PREFLOP) {
			++preflopCount;
			++playerThisHand[seat].preflopCount;
			++playerPos[pos].preflopCount;
			++playerRp[rp].preflopCount;
		} else if (street == FLOP) {
			++flopCount;
			++playerThisHand[seat].flopCount;
			++playerPos[pos].flopCount;
			++playerRp[rp].flopCount;
		} else if (street == TURN) {
			++turnCount;
			++playerThisHand[seat].turnCount;
			++playerPos[pos].turnCount;
			++playerRp[rp].turnCount;
		} else if (street == RIVER) {
			++riverCount;
			++playerThisHand[seat].riverCount;
			++playerPos[pos].riverCount;
			++playerRp[rp].riverCount;
		}
	}

	/*-************************************************************************************************
	 * Process one hand
	 * This is the top of the tree where we process one arrayOfHands[handIndex].
	 * All hands have been read and arrayOfHands contains Hand objects.
	 * handIndex is the index into arrayOfHands.
	 *************************************************************************************************/
	private boolean processOneHand() {
		actionArrayIndex = 0;
		actionArrayCount = oneHand.actionCount;
		initializeCounts();
		stepThroughActionArrayStreets();
		if (HandCalculations.showdown) {
			stepThroughActionArrayShowdown();
		}
		stepThroughActionArraySummary();
		// Wrap it up
		wrapUp();
		return true;
	}

	/*-   ******************************************************************************************** 
	 * Initialize Game - do for every hand.
	 ***********************************************************************************************/
	private void initializeCounts() {
		for (int i = 0; i < PLAYERS; ++i) {
			for (int j = 0; j < STREET; ++j) {
				playerSawStreet[i][j] = false;
			}
		}
		for (int i = 0; i < STREET; ++i) {
			lastBetStreet[i] = -1;
			lastRaiseStreet[i] = -1;
			lastCallStreet[i] = -1;
		}
		for (int i = 0; i < PLAYERS; ++i) {
			playerSawShowdown[i] = false;
			playerPosted[i] = false;
		}
		street = PREFLOP;
		mdf = 0.;
		for (int i = 0; i < STREETS; ++i) {
			streetMDF[i] = 0.;
		}
		for (int i = 0; i < PLAYERS; i++) {
			showdownWinners[i] = 0;
			for (int j = 0; j < 4; j++) {
				streetWinners[j][i] = 0;
			}
		}
	}

	/*- ************************************************************************************************
	* Here we step through the Action.
	* Array actionArray in one instance of Hand.
	* Only street actions in this method.
	* Each line in the array is first checked for the Street number.
	* Street number includes Preflop, Flop, Turn, River, Show Down and Summary.
	* Any street but Preflop and Summary can be missing from the array.
	* Flop, Turn, and River may not have been played and there may not have been a Show Down.
	* 
	* Hand arrayOfHands contains objects that are instances of the Action Class.
	* Each element contains the Street number, Player seat number, the orbit number, 
	* and the players relative position. (First, first Heads Up, Middle, Last. and Last Heads Up.
	* Action represents all of what a player can do. ( Fold, Check, Call, Call All-in, Bet, 
	* Bet All-in, Raise, Raise All-in, All-in ).
	* It also represents what may happen to a player ( Uncalled bet returned to, 
	* Collected money when he wins because all other players Folded ).
	*************************************************************************************************/
	private void stepThroughActionArrayStreets() {
		HandCalculations.doCalculationsStartOfHand(oneHand);

		int oldStreet = PREFLOP;
		betTypeNow = BET1;
		actionArrayIndex = 0;
		boolean done = false;
		while (!done && actionArrayIndex < oneHand.actionCount) {
			id = oneHand.actionArray[actionArrayIndex].ID;
			street = oneHand.actionArray[actionArrayIndex].street;
			seat = oneHand.actionArray[actionArrayIndex].seat;
			pos = HandCalculations.getPos(seat);
			rp = HandCalculations.getRp(seat);
			action = oneHand.actionArray[actionArrayIndex].action;
			if (street != oldStreet) {
				betTypeNow = BET_CHECK;
				oldStreet = street;
			}

			switch (street) {
			case PREFLOP -> {
				playerSawStreet[seat][PREFLOP] = true;
				actionHandsPlayedPos();
				streetActions();
				++actionArrayIndex;
				break;
			}
			case FLOP -> {
				playerSawStreet[seat][FLOP] = true;
				actionHandsPlayedPos();
				streetActions();
				++actionArrayIndex;
				break;
			}
			case TURN -> {
				playerSawStreet[seat][TURN] = true;
				actionHandsPlayedPos();
				streetActions();
				++actionArrayIndex;
				break;
			}
			case RIVER -> {
				playerSawStreet[seat][RIVER] = true;
				actionHandsPlayedPos();
				streetActions();
				++actionArrayIndex;
				break;
			}
			case SUMMARY -> {
				done = true;
				break;
			}
			case SHOWDOWN -> {
				done = true;
				break;
			}
			default -> Crash.log("$$$");
			}
		}
	}

	/*-************************************************************************************************
	 * Process one player action, one row in action array.
	 * Helper method for stepThroughActionArray().
	 * Update opportunity counts and specific actions.
	************************************************************************************************ */
	private void streetActions() {
		switch (action) {
		case ACTION_POST -> {
			actionPost();
			break;
		}
		case ACTION_FOLD -> {
			opportunityCounts();
			actionFold();
			HandCalculations.playerFoldedUpdateRp(seat);
			break;
		}
		case ACTION_CHECK -> {
			opportunityCounts();
			actionCheck();
			break;
		}
		case ACTION_CALL -> {
			opportunityCounts();
			actionCall(false);
			break;
		}
		case ACTION_CALL_ALLIN -> {
			opportunityCounts();
			actionCall(true);
			break;
		}
		case ACTION_BET -> {
			opportunityCounts();
			actionBet(false);
			break;
		}
		case ACTION_BET_ALLIN -> {
			opportunityCounts();
			actionBet(true);
		}
		case ACTION_RAISE -> {
			// Oscar_4poker: bets $3
			// godzilla0: raises $7.75 to $10.75
			opportunityCounts();
			actionRaise(false);
			break;
		}
		case ACTION_RAISE_ALLIN -> {
			opportunityCounts();
			actionRaise(true);
			break;
		}
		case ACTION_UNCALLED_RETURNED_TO -> {
			actionUncalledReturnedTo();
			break;
		}
		case ACTION_COLLECTED_STREET -> {
			actionCollectedStreet();
			break;
		}
		case ACTION_SHOWS_STREET -> {
			actionShowsStreet();
			break;
		}
		case ACTION_WON -> {
			Crash.log("xxx " + "");
			actionWon();
			break;
		}
		default -> {
			Logger.log("//ERROR stepThroughActionArrayStreets()  " + ACTION_ST[action]);
			Crash.log("//ERROR stepThroughActionArrayStreets()  " + ACTION_ST[action]);
		}
		}
	}

	/*- ************************************************************************************************
	 * Action UncalledReturnedTo. Summary
	************************************************************************************************ */
	private void actionUncalledReturnedTo() {
		BigDecimal ret$ = oneHand.actionArray[actionArrayIndex].money;
		playerOneHand[seat].playerReturnedTo$[street] = ret$;

		if (street == 0 && betTypeNow == BET1 && seat == HandCalculations.BBSeat
				&& HandCalculations.foldCount == PLAYERS - 1) {
			// It is a walk
			actionCheck();
		}
	}

	/*- ************************************************************************************************
	*Action Collected Street. Summary
	*************************************************************************************************/
	private void actionCollectedStreet() {
		BigDecimal coll$ = oneHand.actionArray[actionArrayIndex].money;
		playerWonStreet[seat][street] = true;
		++playerThisHand[seat].wonCount;
		++playerPos[pos].wonCount;
		++playerRp[rp].wonCount;
		playerOneHand[seat].playerCollect$[street] = coll$;
		playerOneHand[seat].playerCollected$ = coll$;
//	TODO	 playerPos[pos].playerCollect$[street] = coll$;
//		 playerPos[pos].playerCollected$ = coll$;
//		 playerRp[rp]playerCollect$[street] = coll$;
//		 playerRp[rp].playerCollected$ = coll$;
	}

	/*- ********************************************************************************************** 
	 * Action Won. Summary
	************************************************************************************************ */
	private void actionWon() {
		if (!HandCalculations.foldArray[seat]) {
			++streetWinners[street][seat];
			playerWonStreet[seat][street] = true; // TODO Duplicate
			++playerThisHand[seat].wonCount;// TODO Duplicate
			++playerPos[pos].wonCount;
			++playerRp[rp].wonCount;
		} else {
			HandReports.actionReport(oneHand);
			// XXX 015666: folds
			// XXX 001804: folds
			// XXX 018644: raises $4 to $6
			// XXX 008517: folds
			// XXX 018588: folds
			// XXX 003608: raises $29.56 to $35.56 and is all-in
			// XXX 018644: calls $29.56
			// XXX 003608:
			System.out.println("//XXX " + oneHand.handID);
			Crash.log("$$$ ");
		}
	}

	/*- ********************************************************************************************** 
	 * Action Won Side Pot. Show Down
	************************************************************************************************ */
	private void actionWonSidePot() {
		++streetWinners[street][seat];
	}

	/*- ********************************************************************************************** 
	 * Action Collected Side Pot. Show Down
	 * ludatil collected $28.70 from side pot
	************************************************************************************************ */
	private void actionCollectedSidePot() {
		System.out.println("//XXX " + HandCalculations.sawStreetOrShow[seat]);
		// HandReports.actionReport(oneHand);
		// HandReports.handReport(oneHand);
		// HandReports.handReportBySeat(oneHand);
		Crash.log("xxx " + "");
		++streetWinners[street][seat];
	}

	/*- ********************************************************************************************** 
	 * Action Collected Pot. Show Down.
	 * JoSsP1n collected $260.61 from pot
	************************************************************************************************ */
	private void actionCollectedPot() {
		System.out.println("//XXX " + HandCalculations.sawStreetOrShow[seat]);
		// HandReports.actionReport(oneHand);
		// HandReports.handReport(oneHand);
		// HandReports.handReportBySeat(oneHand);
		Crash.log("xxx " + "");
		++streetWinners[street][seat];
	}

	/*- ********************************************************************************************** 
	 * Action Won Show Down
	 * Called from stepThroughActionArray() 
	 * There is one row in ActionArray for each player that that participates in the Show Down.
	 * Participation is defined as winning money. Pot, Main Pot, or Side pots.
	 * Only one player for each row. Seat number is that of row.
	************************************************************************************************ */
	private void actionWonShowdown() {
		System.out.println("//XXX " + HandCalculations.sawStreetOrShow[seat]);
		// HandReports.actionReport(oneHand);
		// HandReports.handReport(oneHand);
		// HandReports.handReportBySeat(oneHand);
		Crash.log("xxx " + "");
		// HandReports.handReport(oneHand);
//		HandReports.handReportBySeat(oneHand);
		Crash.log("xxx " + "");
		++playerThisHand[seat].wonShowdownCount;
		++playerPos[pos].wonShowdownCount;
		++playerRp[rp].wonShowdownCount;
		// TODO playerMoneyRp[rp].wonShowdown$ =
		// playerMoneyPos[pos].wonShowdown$.add(bd);
		// playerMoneyRp[rp].wonShowdown$ = playerMoneyRp[rp].wonShowdown$.add(bd);
		if (playerSawStreet[seat][PREFLOP]) {
			++playerThisHand[seat].wwspCount;
			++playerPos[pos].wwspCount;
			++playerRp[rp].wwspCount;
		}
		// Saw which streets
		if (playerSawStreet[seat][FLOP]) {
			++playerThisHand[seat].wwsfCount;
			++playerPos[pos].wwsfCount;
			++playerRp[rp].wwsfCount;
		}
		if (playerSawStreet[seat][TURN]) {
			++playerThisHand[seat].wwstCount;
			++playerPos[pos].wwstCount;
			++playerRp[rp].wwstCount;
		}
		if (playerSawStreet[seat][RIVER]) {
			++playerThisHand[seat].wwsrCount;
			++playerPos[pos].wwsrCount;
			++playerRp[rp].wwsrCount;
		}
	}

	/*-************************************************************************************************
	 * Here we step through the Action Array actionArray in one instance of Hand.
	* Only Summary actions in this method.
	* Each line in the array is first checked for the Street number.
	* Street number includes Preflop, Flop, Turn, River, Show Down and Summary.
	* Any street but Preflop and Summary can be missing from the array.
	* Flop, Turn, and River may not have been played and there may not have been a Show Down.
	*
	* *** SUMMARY ***
	* Total pot $69.50 | Rake $2.75 
	* Board [Qs 6h 3s Jd 9h]
	* Seat 1: 0godzilla0 (big blind) showed [9c Qc] and won ($66.75) with two pair, Queens and Nines
	* Seat 2: burigat folded before Flop (didn't bet)
	* Seat 3: ChaosGen folded before Flop (didn't bet)
	* Seat 4: simplereshe folded before Flop (didn't bet)
	* Seat 5: San1tr0n (button) folded before Flop (didn't bet)
	* Seat 6: Oscar_4poker (small blind) showed [Qh Td] and lost with a pair of Queens 
	* 
	* *** SUMMARY ***
	 * Total pot $523.96 | Rake $2.75 
	 * Hand was run twice
	 * FIRST Board [5h 6h Ah 8s Ks]
	 * SECOND Board [5h 6h Ah Tc 9d]
	 * Seat 1: Naebushkin folded before Flop (didn't bet) 
	 * Seat 5: toprmaw (big blind) folded before Flop
	 * 1 winner 2 times
	 * Seat 6: JoSsP1n showed [9h Jh] and won ($260.61) with a flush, Ace high, and won ($260.60) with a flush, Ace high 
	 * 
	 * 2 winners
	 *	Seat 1: Satan'sBingo showed [Kd Ac] and won ($136.39) with two pair, Aces and Fives, and won ($136.38) with 
	 * a pair of SevensSeat 2: mike21_4life (button) folded before Flop (didn't bet)
	 * Seat 4: nicofellow (small blind) showed [Kh As] and won ($136.39) with two pair, Aces and Fives, and won ($136.39) 
	 * with a pair of Sevens
	 * 
	 * Note that player collected on showdown but won on summary.  Duplicate information
	 * *** SHOW DOWN ***
	* 777Koresh777 collected $55.75 from pot
	* *** SUMMARY ***
	* Total pot $57 | Rake $1.25 
	* Board [Ah Td 4s 4d 8c]
	* Seat 1: 777Koresh777 (big blind) showed [Ac 5s] and won ($55.75) with two pair, Aces and Fours
	* 
	* There can be a river without a showdown
	* *** RIVER *** [As Qd 3d Qc] [5h]
	* ripper-Pol: checks 
	* AnastasiiaVL: bets $4
	* ripper-Pol: folds 
	* Uncalled bet ($4) returned to AnastasiiaVL
	* AnastasiiaVL collected $11.59 from pot
	* 
	* Side pot
	 * *** SHOW DOWN ***
	* ozone2003 collected $52.04 from side pot
	* 8310Geiner collected $30.19 from main pot
	* *** SUMMARY ***
	* Total pot $84.98 Main pot $30.19. Side pot $52.04. | Rake $2.75 
	* Board [8d 5d 6c 3h 8h]
	* Seat 2: 8310Geiner (big blind) showed [5c 9h] and won ($30.19) with two pair, Eights and Fives
	* Seat 5: ozone2003 showed [Ad Kc] and won ($52.04) with a pair of Eights
	 *************************************************************************************************/
	private void stepThroughActionArraySummary() {
		boolean done = false;
		while (!done && actionArrayIndex < oneHand.actionCount) {
			id = oneHand.actionArray[actionArrayIndex].ID;
			street = oneHand.actionArray[actionArrayIndex].street;
			seat = oneHand.actionArray[actionArrayIndex].seat;
			pos = HandCalculations.getPos(seat);
			rp = HandCalculations.getRp(seat);

			action = oneHand.actionArray[actionArrayIndex].action;
			switch (action) {
			case ACTION_WON -> {
				actionWon();
				++actionArrayIndex;
				break;
			}
			case ACTION_COLLECTED_MAIN_POT -> {
				actionCollectedMainPot();
				++actionArrayIndex;
				break;
			}
			case ACTION_COLLECTED_SUMMARY -> {
				actionCollectedSummary();
				++actionArrayIndex;
				break;
			}
			case ACTION_SHOWS_STREET -> {
				actionShowsStreet();
				++actionArrayIndex;
				break;
			}
			default -> {
				// ERROR stepThroughActionArraySummary() Collected Main Pot
				Logger.log("//ERROR stepThroughActionArraySummary() " + ACTION_ST[action]);
				Crash.log("//ERROR stepThroughActionArraySummary() " + ACTION_ST[action]);
			}
			}
		}
	}

	/*-************************************************************************************************
	 * Here we step through the Action Array actionArray in one instance of Hand.
	* Only Show Down actions in this method.
	* Each line in the array is first checked for the Street number.
	* Street number includes Preflop, Flop, Turn, River, Show Down and Summary.
	* Any street but Preflop and Summary can be missing from the array.
	* Flop, Turn, and River may not have been played and there may not have been a Show Down.
	 * Here we step through the Action Array actionArray in one instance of Hand.
	* Only Show Down actions in this method.
	* 
	 * Actions that dealer took on Showdown
	 * *** SHOW DOWN ***
	* 0godzilla0 collected $66.75 from pot 
	 *************************************************************************************************/
	private void stepThroughActionArrayShowdown() {
		boolean done = false;
		while (!done && actionArrayIndex < oneHand.actionCount) {
			id = oneHand.actionArray[actionArrayIndex].ID;
			street = oneHand.actionArray[actionArrayIndex].street;
			if (street == SUMMARY) {
				done = true;
				break;
			}
			seat = oneHand.actionArray[actionArrayIndex].seat;
			pos = HandCalculations.getPos(seat);
			rp = HandCalculations.getRp(seat);
			action = oneHand.actionArray[actionArrayIndex].action;

			switch (action) {
			case ACTION_WON_SIDE_POT -> {
				actionWonSidePot();
				++actionArrayIndex;
				break;
			}
			case ACTION_COLLECTED_SIDE_POT -> {
				actionCollectedSidePot();
				++actionArrayIndex;
				break;
			}
			case ACTION_COLLECTED_POT -> {
				actionCollectedPot();
				++actionArrayIndex;
				break;
			}
			case ACTION_SHOWS_SHOWDOWN -> {
				actionShowsShowdown();
				++actionArrayIndex;
				break;
			}
			default -> {
				Logger.log("//ERROR stepThroughActionArrayShowdown() " + ACTION_ST[action]);
				Crash.log("//ERROR stepThroughActionArrayShowdown() " + ACTION_ST[action]);
			}
			}
		}
	}

	/*- ********************************************************************************************** 
	 * Action Collected Summary. Summary
	************************************************************************************************ */
	private void actionCollectedSummary() {
		// TODO
	}

	/*- ********************************************************************************************** 
	 * Action player shows cards.  Street
	************************************************************************************************ */
	private void actionShowsStreet() {
// TODO
	}

	/*- ************************************************************************************************
	 * Action Won. Summary
	 * and won ($310.24) with 
	 ************************************************************************************************ */
	private void actionWonSummary() {
//		updateSummaryPotAndRake(oneHand.totalPot, oneHand.rakeRound);
//		updateSummaryWithSides(oneHand.mainPot, oneHand.sidePot1,
		// oneHand.sidePot2, oneHand.sidePot3,
		// oneHand.sidePot4);

		if (HandCalculations.wonSummary[seat].equals(zeroBD)) {
			// updateSummaryPlayers(oneHand.wonSummary[seat],
			// oneHand.collectedSummary[seat]);
		}

		if (HandCalculations.wonSummary[seat].equals(zeroBD)) {
			// updateSummaryPlayers(oneHand.wonSummary[seat]);
		}
		// if (!won.equals(zeroBD)) {
		// ++playerThisHand[seat].wonCount;
		// }
	}

	/*- ********************************************************************************************** 
	 * Action Collected Main Pot. Show Down
	 * ludatil collected $281.54 from main pot 
	************************************************************************************************ */
	private void actionCollectedMainPot() {
		++streetWinners[street][seat];
	}

	/*-************************************************************************************************
	 * One hand has been processed.
	 * We have stepped through the action array.
	 * Now we cleanup a few things
	 * TODO There is duplication here. Example wwsp counted if won street, wonSummary, collecetd.
	 * TODO think this through
	************************************************************************************************ */
	private void wrapUp() {
		for (int i = 0; i < PLAYERS; ++i) {
			pos = HandCalculations.getPos(seat); // TODO are pos and rp correct???
			rp = HandCalculations.getRp(seat);
			if (HandCalculations.sawStreetOrShow[i] == SHOWDOWN) {
				++playerThisHand[i].showdownCount;
				++playerPos[pos].showdownCount;
				++playerRp[rp].showdownCount;
			}
			if (playerSawStreet[i][PREFLOP]) {
				++playerThisHand[i].streetCount[PREFLOP];
				if (playerSawShowdown[i]) {
					++playerThisHand[i].wtsdPreflopCount;
					++playerPos[pos].wtsdPreflopCount;
					++playerRp[rp].wtsdPreflopCount;
				}
				if (HandCalculations.wonSummary[i].compareTo(zeroBD) > 0) {
					++playerThisHand[i].wwspCount;
					++playerPos[pos].wwspCount;
					++playerRp[rp].wwspCount;
				}
				if (HandCalculations.wonStreet[i].compareTo(zeroBD) > 0) {
					++playerThisHand[i].wwspCount;
					++playerPos[pos].wwspCount;
					++playerRp[rp].wwspCount;
				}
				// if (HandCalculations.collectedMainPotShowdown[i].compareTo(zeroBD) > 0
				// TODO || oneHand.wonSidePotShowdown[i].compareTo(zeroBD) > 0
				// || oneHand.collectedSidePotShowdown[i].compareTo(zeroBD) > 0
				// || HandCalculations.collectedPotShowdown[i].compareTo(zeroBD) > 0) {
				// ++playerThisHand[i].wwspCount;
				// ++playerPos[pos]. wwspCount;
				// ++playerRp[rp]. wwspCount;
				// }
			}

			if (playerSawStreet[i][FLOP]) {
				++playerThisHand[i].streetCount[FLOP];
				if (playerSawShowdown[i]) {
					++playerThisHand[i].wtsdFlopCount;
					++playerPos[pos].wtsdFlopCount;
					++playerRp[rp].wtsdFlopCount;
				}
				if (HandCalculations.wonSummary[i].compareTo(zeroBD) > 0) {
					++playerThisHand[i].wwsfCount;
					++playerPos[pos].wwsfCount;
					++playerRp[rp].wwsfCount;
				}
				if (HandCalculations.wonStreet[i].compareTo(zeroBD) > 0) {
					++playerThisHand[i].wwsfCount;
					++playerPos[pos].wwsfCount;
					++playerRp[rp].wwsfCount;
				}
				if (HandCalculations.collectedMainPotShowdown[i].compareTo(zeroBD) > 0
						// TODO || oneHand.wonSidePotShowdown[i].compareTo(zeroBD) > 0
						// || oneHand.collectedSidePotShowdown[i].compareTo(zeroBD) > 0
						|| HandCalculations.collectedPotShowdown[i].compareTo(zeroBD) > 0) {
					++playerThisHand[i].wwspCount;
					++playerPos[pos].wwsfCount;
					++playerRp[rp].wwsfCount;
				}
			}
			if (playerSawStreet[i][TURN]) {
				++playerThisHand[i].streetCount[TURN];
				if (playerSawShowdown[i]) {
					++playerThisHand[i].wtsdTurnCount;
					++playerPos[pos].wtsdTurnCount;
					++playerRp[rp].wtsdTurnCount;
				}
				if (HandCalculations.wonSummary[i].compareTo(zeroBD) > 0) {
					++playerThisHand[i].wwspCount;
					++playerPos[pos].wwstCount;
					++playerRp[rp].wwstCount;
				}
				if (HandCalculations.wonStreet[i].compareTo(zeroBD) > 0) {
					++playerThisHand[i].wwstCount;
					// ++playerPos[pos].wwstCount;
					// ++playerRp[rp]].wwstCount;
				}
				if (HandCalculations.collectedMainPotShowdown[i].compareTo(zeroBD) > 0
						// TODO || oneHand.wonSidePotShowdown[i].compareTo(zeroBD) > 0
						// || oneHand.collectedSidePotShowdown[i].compareTo(zeroBD) > 0
						|| HandCalculations.collectedPotShowdown[i].compareTo(zeroBD) > 0) {
					++playerThisHand[i].wwstCount;
					++playerPos[pos].wwstCount;
					++playerRp[rp].wwstCount;
				}
			}
			if (playerSawStreet[i][RIVER]) {
				++playerThisHand[i].streetCount[RIVER];
				if (playerSawShowdown[i]) {
					++playerThisHand[i].wtsdRiverCount;
					++playerPos[pos].wtsdRiverCount;
					++playerRp[rp].wtsdRiverCount;
				}
				if (HandCalculations.wonSummary[i].compareTo(zeroBD) > 0) {
					++playerThisHand[i].wwsrCount;
					++playerPos[pos].wwsrCount;
					++playerRp[rp].wwsrCount;
				}
				if (HandCalculations.wonStreet[i].compareTo(zeroBD) > 0) {
					++playerThisHand[i].wwsrCount;
					++playerPos[pos].wwsrCount;
					++playerRp[rp].wwsrCount;
				}
				if (HandCalculations.collectedMainPotShowdown[i].compareTo(zeroBD) > 0
						// || oneHand.wonSidePotShowdown[i].compareTo(zeroBD) > 0
						// || oneHand.collectedSidePotShowdown[i].compareTo(zeroBD) > 0
						|| HandCalculations.collectedPotShowdown[i].compareTo(zeroBD) > 0) {
					++playerThisHand[i].wwsrCount;
					++playerPos[pos].wwsrCount;
					++playerRp[rp].wwsrCount;
				}
			}
		}
	}

	/*- ************************************************************************************************
	 * UpdateCollected. 
	 * Called from stepThroughActionArray()   	ACTION_POST -> {}
	*************************************************************************************************/
	private void actionPost() {
		opportunityCounts();
		if (seat == HandCalculations.SBSeat) {
			rp = RP_FIRST;
			pos = SB;
			++playerThisHand[seat].callBet1OperPos[0][pos];
			++playerThisHand[seat].callBet1OperRp[0][rp];
			++playerPos[pos].callBet1OperPos[0][pos];
			++playerPos[pos].callBet1OperRp[0][rp];
			++playerRp[rp].callBet1OperPos[0][pos];
			++playerRp[rp].callBet1OperRp[0][rp];

		} else if (seat == HandCalculations.BBSeat) {
			rp = RP_MIDDLE1;
			pos = BB;
			++playerThisHand[seat].foldOperPos[0][pos];
			++playerThisHand[seat].foldOperRp[0][rp];
			++playerPos[pos].callBet1OperPos[0][pos];
			++playerPos[pos].callBet1OperRp[0][rp];
			++playerRp[rp].callBet1OperPos[0][pos];
			++playerRp[rp].callBet1OperRp[0][rp];
		}
	}

	/*- ********************************************************************************************** 
	 * Update Show Down
	 * Called from stepThroughActionArray()    case SHOWDOWN -> {}
	 * There is one row in ActionArray for each player that that participates in the Show Down.
	 * Participation is defined as winning money. Pot, Main Pot, or Side pots.
	 * Only one player for each row. Seat number is that of row.
	************************************************************************************************ */
	private void actionShowdown() {
		++streetWinners[street][seat];

		++playerThisHand[seat].showdownCount;
		if (playerSawStreet[seat][PREFLOP]) {
			++playerThisHand[seat].wwspCount;
			++playerPos[pos].wwspCount;
			++playerRp[rp].wwspCount;
		}
		if (playerSawStreet[seat][FLOP]) {
			++playerThisHand[seat].wwsfCount;
			++playerPos[pos].wwsfCount;
			++playerRp[rp].wwsfCount;
		}
		if (playerSawStreet[seat][TURN]) {
			++playerThisHand[seat].wwstCount;
			++playerPos[pos].wwstCount;
			++playerRp[rp].wwstCount;
		}
		if (playerSawStreet[seat][RIVER]) {
			++playerThisHand[seat].wwsrCount;
			++playerPos[pos].wwsrCount;
			++playerRp[rp].wwsrCount;
		}
	}

	/*- ********************************************************************************************** 
	 * Action player shows cards. Show Down
	 * Called from stepThroughActionArray()   	case ACTION_SHOWS_SHOWDOWN -> {
	 * Player shows one or more cards
	 * Only one player for each row. Seat number is that of row.
	************************************************************************************************ */
	private void actionShowsShowdown() {
		System.out.println("//XXX " + HandCalculations.sawStreetOrShow[seat]);
		// HandReports.actionReport(oneHand);
		// HandReports.handReport(oneHand);
		// HandReports.handReportBySeat(oneHand);
		Crash.log("xxx " + "");
	}

	/*-***********************************************************************************************
	 *  Opportunity counts.
	*********************************************************************************************** */
	private void opportunityCounts() {
		if (street == FLOP && betTypeNow == BET_CHECK && lastRaiseStreet[PREFLOP] == seat) {
			++playerThisHand[seat].cBetOperPos[street][pos];
			++playerThisHand[seat].cBetOperRp[street][rp];
		}
		if (street == TURN && betTypeNow == BET_CHECK && lastBetStreet[FLOP] == seat) {
			++playerThisHand[seat].barrelOperPos[street][pos];
			++playerThisHand[seat].barrelOperRp[street][rp];
		}
		if (street == RIVER && betTypeNow == BET_CHECK && lastBetStreet[FLOP] == seat && lastBetStreet[TURN] == seat) {
			++playerThisHand[seat].barrelOperPos[street][pos];
			++playerThisHand[seat].barrelOperRp[street][rp];
		}
		if (street != PREFLOP && betTypeNow == BET_CHECK) {
			++playerThisHand[seat].checkOperPos[street][pos];
			++playerThisHand[seat].checkOperRp[street][rp];
		}
		if (street == PREFLOP && betTypeNow == BET1 && seat == HandCalculations.BBSeat) {
			++playerThisHand[seat].checkOperPos[0][pos];
			++playerThisHand[seat].checkOperRp[0][rp];
		}

		// Special cases for Button
		if (seat == HandCalculations.BUSeat) {
			// TODO
		}
		// Special cases for Small Blind
		if (seat == HandCalculations.SBSeat) {
			// TODO
		}
		// Special cases forBig Blind
		if (seat == HandCalculations.BBSeat) {
			// TODO
		}
		++playerThisHand[seat].allinOperPos[street][pos];
		++playerThisHand[seat].allinOperRp[street][rp];
		++playerThisHand[seat].foldOperPos[street][pos];
		++playerThisHand[seat].foldOperRp[street][rp];

		if (betTypeNow == BET_CHECK) {
			betToMe[seat][BET_CHECK] = true;
			betToMeNow = BET_CHECK;
			++playerThisHand[seat].bet1OperPos[street][pos];
			++playerThisHand[seat].bet1OperRp[street][rp];
			++playerThisHand[seat].minBetOperPos[street][pos];
			++playerThisHand[seat].minBetOperRp[street][rp];
			return;
		}

		if (betTypeNow == BET1) {
			betToMe[seat][BET1] = true;
			if (street == 0) {
				++playerThisHand[seat].limpOperPos[street][pos];
			}
			++playerThisHand[seat].callBet1OperPos[street][pos];
			++playerThisHand[seat].callBet1OperPos[street][pos];

			++playerThisHand[seat].bet2OperPos[street][pos];
			++playerThisHand[seat].minBetOperPos[street][pos];
			++playerThisHand[seat].limpOperRp[street][rp];
			++playerThisHand[seat].callBet1OperRp[street][rp];
			++playerThisHand[seat].bet2OperRp[street][rp];
			++playerThisHand[seat].minBetOperRp[street][rp];
			betToMeNow = BET1;
			return;
		}
		if (betTypeNow == BET2) {
			betToMe[seat][BET2] = true;
			++playerThisHand[seat].callBet2OperPos[street][pos];
			++playerThisHand[seat].callBet2OperRp[street][rp];
			++playerThisHand[seat].bet3OperPos[street][pos];
			++playerThisHand[seat].bet3OperRp[street][rp];
			++playerThisHand[seat].minBetOperPos[street][pos];
			++playerThisHand[seat].minBetOperRp[street][rp];
			betToMeNow = BET2;
			return;
		}
		if (betTypeNow == BET3) {
			betToMe[seat][BET3] = true;
			++playerThisHand[seat].callBet3OperPos[street][pos];
			++playerThisHand[seat].callBet3OperRp[street][rp];
			++playerThisHand[seat].bet4OperPos[street][pos];
			++playerThisHand[seat].minBetOperPos[street][pos];
			++playerThisHand[seat].bet4OperRp[street][rp];
			++playerThisHand[seat].minBetOperRp[street][rp];
			betToMeNow = BET3;
			return;
		}
		if (betTypeNow == BET4) {
			betToMe[seat][BET4] = true;
			++playerThisHand[seat].callBet4OperPos[street][pos];
			++playerThisHand[seat].minBetOperPos[street][pos];
			++playerThisHand[seat].callBet4OperRp[street][rp];
			++playerThisHand[seat].minBetOperRp[street][rp];
			betToMeNow = BET4;
			return;
		}
		if (betTypeNow == BET_ALLIN) {
			betToMe[seat][BET_ALLIN] = true;
			++playerThisHand[seat].callAllinOperPos[street][pos];
			++playerThisHand[seat].callAllinOperRp[street][rp];
			betToMeNow = BET_ALLIN;
			return;
		}
		Logger.logError(new StringBuilder().append("//opportunityCounts ").append(betTypeNow).append(" ")
				.append(BET_TO_ME_ST[betTypeNow]).toString());
		Crash.log("$$$");
	}

	/*- ************************************************************************************************
	 * Save the data required to classify a player by type and characteristics.
	 * Used for calculating actual MDF
	 *************************************************************************************************/
	private void actionFold() {
		betToMe[seat][TOME_FOLD] = true;
		playerOneHand[seat].playerAction[street] = TOME_FOLD;

		playerOneHand[seat].playerActed[street] = true;
		playerOneHand[seat].playerRp[street] = rp;
		playerOneHand[seat].playerFold[street] = true;

		++playerThisHand[seat].foldPos[street][pos];
		++playerThisHand[seat].foldRp[street][rp];
		++playerOneHand[seat].foldPos[street][pos];
		++playerOneHand[seat].foldRp[street][rp];
		++playerThisHand[seat].foldCount[street][pos];

		// TODO double count ??
		if (street == 0 && (seat == HandCalculations.SBSeat || seat == HandCalculations.BBSeat)) {
			++playerThisHand[seat].foldCount[street][pos];
		}

	}

	/*- ************************************************************************************************
	 * Save the data required to classify a player by type and characteristics.
	************************************************************************************************ */
	private void actionCheck() {
		lastCheckStreet[street] = seat;
		final double pot = oneHand.actionArray[actionArrayIndex].pot.doubleValue();
		playerOneHand[seat].playerCheck[street] = true;
		playerOneHand[seat].playerAction[street] = BET_CHECK;
		playerOneHand[seat].playerActed[street] = true;
		playerOneHand[seat].playerRp[street] = rp;
		playerOneHand[seat].playerBetType[street] = BET_CHECK;
		playerOneHand[seat].response[pos][betToMeNow] = BET_CHECK;
		playerOneHand[seat].betToMe[pos][BET_CHECK] = true;
		++playerThisHand[seat].checkCount[street][pos];
		++playerThisHand[seat].checkPos[street][pos];
		++playerThisHand[seat].checkRp[street][rp];
		if (street == 0 && betTypeNow == BET1 && seat == HandCalculations.BBSeat
				&& HandCalculations.foldCount == PLAYERS - 1) {
			++playerThisHand[seat].walk;
			++playerOneHand[seat].walk;
			++playerThisHand[seat].walkCount;
			playerOneHand[seat].playerWalk[street] = true;
			playerOneHand[seat].playerWalk[street] = true;
			playerChecked[street][seat] = true;
		}
	}

	/*- ************************************************************************************************
	 * Save the data required to classify a player by type and characteristics.
	 * Orbits 0 and 1 are unique because preflopCallCount and preflopLimpCount
	 * are defined as the first time that a player acts.
	 * The first acts are used for calculations such as VPIP.
	 * The Players arrays callPos and callRP do not mean just any call they only
	 * mean callBet2. So limpPos, limpRp, callBet3Pos, etc. not included
	 * in callPos or callRp.  A bit confusing but very important.
	 * 
	 * If allin is true then this is a call all-in short:
	 * 		
	 * Depends on how you define call
	 * in this case call is not the current bet now, call is how much this player puts into the pot
	 * So as BB he already has 2.0 in, 2.0 + 2.5 call is the 4.5
	 * What is important is that is NOT how play defines call - HandHistory defines it as betNow WRONG
	 *  
	 * Arg0 - Call amount - how much he actually put in, so betNow - moneyIn 
	 * Arg1 - true if all-in
	************************************************************************************************ */
	private void actionCall(boolean allin) {
		BigDecimal call$ = oneHand.actionArray[actionArrayIndex].call;
		lastCallStreet[street] = seat;

		final double pot = oneHand.actionArray[actionArrayIndex].pot.doubleValue();

		bet = call$.doubleValue();
		if (bet != call$.doubleValue()) {
			Logger.logError(new StringBuilder().append("CCCC ").append(bet).append(" ").append(call$).toString());
			Crash.log("$$$");
		}

		playerOneHand[seat].playerCall[street] = true;
		playerOneHand[seat].playerAction[street] = TOME_CALL;
		playerOneHand[seat].playerActed[street] = true;
		playerOneHand[seat].playerRp[street] = rp;
		betToMe[seat][betToMeNow] = true;

		if (!allin) {
			// TODO
		} else {
			playerOneHand[seat].playerCall$[street] = call$;
			playerOneHand[seat].playerCallShort$[street] = oneHand.actionArray[actionArrayIndex].call;
			playerOneHand[seat].playerCallShort$[street] = playerOneHand[seat].playerCallShort$[street].subtract(call$);
		}

		++playerThisHand[seat].callPos[street][pos];
		++playerThisHand[seat].callRp[street][rp];

		// ++playerThisHand[seat].stealCallOperSBPos[pos];
		// ++playerThisHand[seat].stealCallOperSBRp[rp];
		// ++playerPos[pos].stealCallOperSBPos[pos];
		// ++playerPos[pos].stealCallOperSBRp[rp];
		// ++playerRp[rp].stealCallOperSBPos[pos];
		// ++playerRp[rp].stealCallOperSBRp[rp];

		if (betTypeNow == BET1) {
			++playerThisHand[seat].callBet1Pos[street][pos];
			++playerThisHand[seat].callBet1Rp[street][rp];
			if (street == 0) {
				++playerThisHand[seat].limpPos[street][pos];
				++playerThisHand[seat].limpRp[street][rp];
				// Double counting
				// if ((seat == HandCalculations.SBSeat || seat ==
				// HandCalculations.BBSeat)) {
				// ++playerThisHand[seat].limpPos[street][pos];
				// ++playerThisHand[seat].limpRp[street][rp];
				// }
			}
		} else if (betTypeNow == BET2) {
			++playerThisHand[seat].callBet2Pos[street][pos];
			++playerThisHand[seat].callBet2Rp[street][rp];
		} else if (betTypeNow == BET3) {
			++playerThisHand[seat].callBet3Pos[street][pos];
			++playerThisHand[seat].callBet3Rp[street][rp];

		} else if (betTypeNow == BET4) {
			++playerThisHand[seat].callBet4Pos[street][pos];
			++playerThisHand[seat].callBet4Rp[street][rp];
		} else if (betTypeNow == BET_ALLIN || allin) {
			++playerThisHand[seat].callAllinPos[street][pos];
			++playerThisHand[seat].callAllinRp[street][rp];

		}
	}

	/*- ************************************************************************************************
	 * Save the data required to classify a player by type and characteristics.
	 * A bet is in response to a check, a raise is in response to a bet
	 * This method handles a bet only.
	 *************************************************************************************************/
	private void actionBet(boolean allin) {
		BigDecimal bet = oneHand.actionArray[actionArrayIndex].betRaise;
		playerOneHand[seat].playerBet[street] = true;
		playerOneHand[seat].playerAction[street] = TOME_BET;
		playerOneHand[seat].playerActed[street] = true;
		playerOneHand[seat].playerRp[street] = rp;

		playerOneHand[seat].playerBet$[street] = bet;

		if (betTypeNow != BET_CHECK) {
			Crash.log("$$$");
		}
		betTypeNow = BET1;

		++playerThisHand[seat].betCount[street][pos];
		if (street == 0 && (seat == HandCalculations.SBSeat || seat == HandCalculations.BBSeat)) {
			++playerThisHand[seat].betCount[street][pos];
			++playerThisHand[seat].betCount[street][pos];
			++playerThisHand[seat].betCount[street][pos];
		}
		++playerThisHand[seat].bet1Pos[street][pos];
		++playerThisHand[seat].bet1Rp[street][rp];
		++playerThisHand[seat].betPos[street][pos];
		++playerThisHand[seat].betPos[street][pos];
		++playerThisHand[seat].betRp[street][rp];
		playerOneHand[seat].playerBetType[street] = betTypeNow;
		betToMe[seat][betToMeNow] = true;
		if (allin) {
			betTypeNow = BET_ALLIN;
			++playerThisHand[seat].allinPos[street][pos];
			++playerThisHand[seat].allinRp[street][rp];

		}

		// Is this bet a min bet
		// final double minSize = potSize$.doubleValue() * .25; // TODO never used
		// if (bet.doubleValue() < potSize$.doubleValue() * .25 && !allin) {
		// TODO never used
		final double minSize = oneHand.actionArray[actionArrayIndex].pot.doubleValue() * .25;
		if (bet.doubleValue() < oneHand.actionArray[actionArrayIndex].pot.doubleValue() * .25 && !allin) {
			++playerThisHand[seat].minBetPos[street][pos];
			++playerThisHand[seat].minBetRp[street][rp];
			if (seat == HandCalculations.BUSeat) {
				++playerThisHand[seat].minBetBU;
			}
		}
	}

	/*- ************************************************************************************************
	 * Save the data required to classify a player by type and characteristics.
	 * A bet is in response to a check, a raise is in response to a bet or raise.
	 * This method handles a raise only.
	 * Arg0 - allin if true
	 *************************************************************************************************/
	private void actionRaise(boolean allin) {
		BigDecimal raise = oneHand.actionArray[actionArrayIndex].raiseTo;
		BigDecimal raiseTo = oneHand.actionArray[actionArrayIndex].betRaise;
		lastRaiseStreet[street] = seat;
		playerOneHand[seat].playerRaise[street] = true;
		playerOneHand[seat].playerAction[street] = TOME_RAISE;
		playerOneHand[seat].playerActed[street] = true;
		playerOneHand[seat].playerRp[street] = rp;
		playerOneHand[seat].playerRaise$[street] = raiseTo;
		playerOneHand[seat].playerRaiseTo$[street] = raiseTo;
		playerOneHand[seat].playerRaiseFrom$[street] = raise; // ????

		playerOneHand[seat].playerRaiseFrom$[street] = oneHand.actionArray[actionArrayIndex].betRaise;

		boolean minBet = false;
		// Is this bet a min bet
		// final double minSize = potSize$.doubleValue() * .25;
		final double minSize = oneHand.actionArray[actionArrayIndex].pot.doubleValue() * .25;
		if (raiseTo.doubleValue() < minSize && allin) {
			minBet = true;
		}
		if (betTypeNow == BET1) {
			betTypeNow = BET2;
		} else if (betTypeNow == BET2) {
			betTypeNow = BET3;
		} else if (betTypeNow == BET3) {
			betTypeNow = BET4;
		} else if (betTypeNow == BET4) {
			betTypeNow = BET_ALLIN; // TODO Maybe BET5?
		} else if (allin) {
			betTypeNow = BET_ALLIN;
		}
		playerOneHand[seat].playerBetType[street] = betTypeNow;

		betToMe[seat][betToMeNow] = true;

		if (betTypeNow == BET2) {
			++bet2PreflopCount;
			if (seat == HandCalculations.BBSeat && HandCalculations.foldCount == PLAYERS - 2) {
				bbRaiseBySB = true;
			}
		}
		if (allin) {
			betTypeNow = BET_ALLIN;
			++playerThisHand[seat].allinPos[street][pos];
			++playerThisHand[seat].allinRp[street][rp];
		}

		if (betTypeNow == BET1) {
			Crash.log("$$$");
			++playerThisHand[seat].bet1Pos[street][pos];

		}
		if (betTypeNow == BET2) {
			++playerThisHand[seat].bet2Pos[street][pos];
			++playerThisHand[seat].bet2Rp[street][rp];
			if (seat == HandCalculations.BUSeat) {
				buttonBet2 = true;
			}
		}
		if (betTypeNow == BET3) {
			++playerThisHand[seat].bet3Pos[street][pos];

			++playerThisHand[seat].bet3Rp[street][rp];
			if (seat == HandCalculations.BUSeat) {
				buttonBet3 = true;
			}
		}
		if (betTypeNow == BET4) {
			++playerThisHand[seat].bet4Pos[street][pos];
			++playerThisHand[seat].bet4Rp[street][rp];
		}
		if (betTypeNow == BET_ALLIN) {
			++playerThisHand[seat].allinPos[street][pos];
			++playerThisHand[seat].allinRp[street][rp];
			if (seat == HandCalculations.BUSeat) {
				buttonAllin = true;
			}
		}

		if (seat == HandCalculations.BUSeat) {
			preflopButtonAdvanced();
		} else if (seat == HandCalculations.SBSeat) {
			preflopSBAdvanced();
		} else if (seat == HandCalculations.BBSeat) {
			preflopBBAdvanced();
		}
		++playerThisHand[seat].raiseCount[street][pos];
		if (street == 0 && (seat == HandCalculations.SBSeat || seat == HandCalculations.BBSeat)) {
			++playerThisHand[seat].raiseCount[street][pos];
		}

		++playerThisHand[seat].raisePos[street][pos];
		++playerThisHand[seat].raiseRp[street][rp];
		if (minBet) {
			++playerThisHand[seat].minBetPos[street][pos];
			++playerThisHand[seat].minBetRp[street][rp];
			if (seat == HandCalculations.BUSeat) {
				++playerThisHand[seat].minBetBU;
			}
		}
	}

	/*-************************************************************************************************
	 * Advanced preflop play
	 *************************************************************************************************/
	private void advancedPreflopButton() {
		++playerThisHand[seat].minBetOperBU;
		if (HandCalculations.foldArray[utgSeat] && HandCalculations.foldArray[mpSeat]
				&& HandCalculations.foldArray[cutoffSeat]) {
			++playerThisHand[seat].stealOperBU;
		} else if (bet2PreflopCount == 1 && callPreflopCount == 1) {
			++playerThisHand[seat].squeezeOperBU;
		} else if (bet2PreflopCount == 1 && callPreflopCount == 0) {
			++playerThisHand[seat].isolateOperBU;
		}
	}

	/*-************************************************************************************************
	 *  Count preflop opportunities for Small Blind 
	************************************************************************************************ */
	private void advancedPreflopSB() {
		if (HandCalculations.foldCount == 4) {
			foldedToSB = true;
			++playerThisHand[seat].foldedToSBOper;

		}
		if (betTypeNow == BET2) {
			if (sbRaisedByBB) {
				++playerThisHand[seat].raisedByBBOperSB;
			}
			if (buttonBet2) {
				++playerThisHand[seat].steal3BetOperSB;
				++playerThisHand[seat].steal3BetOperSB;
				++playerThisHand[seat].steal3BetOperSB;
			}
		}
		if (buttonBet3) {
			++playerThisHand[seat].stealCallOperSB;
		}
	}

	/*-************************************************************************************************
	 *  Count preflop opportunities for Big Blind 
	 *************************************************************************************************/
	private void advancedPreflopBB() {
		if (bbRaiseBySB) {
			++playerThisHand[seat].raisedBySBOperBB;
		}
		if (buttonBet2) {
			++playerThisHand[seat].steal3BetMinRaiseOperBB;
			++playerThisHand[seat].stealCallOperBB;
			++playerThisHand[seat].stealCallMinRaiseOperBB;
		}
		if (buttonBet3) {
			++playerThisHand[seat].steal3BetOperBB;
		}
		// TODO
		++playerThisHand[seat].minBetOperBB;
		++playerThisHand[seat].callMinBetOperBB;
	}

	/*-************************************************************************************************
	 *  Button Advanced play preflop.
	 *  Called by updateRaise()  if seat = HandCalculations.BUSeat  
	 *************************************************************************************************/
	private void preflopButtonAdvanced() {
		// TODO set utgSeat
		if (HandCalculations.foldArray[utgSeat] && HandCalculations.foldArray[mpSeat]
				&& HandCalculations.foldArray[cutoffSeat]) {
			++playerThisHand[seat].stealBU;

			preflopStealBU = true;
		} else if (bet2PreflopCount == 1 && callPreflopCount == 1) {
			++playerThisHand[seat].squeezeBU;

			preflopSqueezeBU = true;
		} else if (bet2PreflopCount == 1 && callPreflopCount == 0) {
			++playerThisHand[seat].isolateBU;

			preflopIsolateBU = true;
		}
	}

	/*-************************************************************************************************
	 *  Small Blind Advanced play preflop
	 *  Called by updateRaise()  if seat = HandCalculations.SBSeat  
	  *************************************************************************************************/
	private void preflopSBAdvanced() {
		if (HandCalculations.foldCount == 4) {
			++playerThisHand[seat].foldedToSB;
		}
		if (betTypeNow != BET2) {
			return;
		}
		if (sbRaisedByBB) {
			++playerThisHand[seat].raisedByBBSB;
		}
		if (buttonBet2) {
			++playerThisHand[seat].steal3BetSB;
		}

	}

	/*-************************************************************************************************
	 *  Big Blind Advanced play preflop
	 *  Called by updateRaise()  if seat = HandCalculations.BBSeat  
	 * Advanced play includes:
	 * 		Isolate.
	 * 		Steal.
	 * 		Squeeze
	 * Advanced play includes:
	 * 		Isolate.
	************************************************************************************************ */
	private void preflopBBAdvanced() {
		if (bbRaiseBySB) {
			++playerThisHand[seat].raisedBySBBB;
		}
		if (buttonBet2) {
			++playerThisHand[seat].steal3BetMinRaiseBB;
		}
		if (buttonBet3) {
			++playerThisHand[seat].steal3BetBB;
		}

		++playerThisHand[seat].minBetBB;
		++playerPos[pos].minBetBB;
		++playerRp[rp].minBetBB;
		++playerThisHand[seat].callMinBetBB;
		++playerPos[pos].callMinBetBB;
		++playerRp[rp].callMinBetBB;
	}

	/*-************************************************************************************************
	 * Money
	 * The Hand class contains a lot od data concerning money.
	 * Data extracted from the Hand History file and data calculated while parsing the Hand History file.
	 * TODO  probably much more needed here
	************************************************************************************************ */
	private void money() {
		for (int i = 0; i < PLAYERS; ++i) {
			playerThisHand[i].won$ = oneHand.finalStack[i].subtract(oneHand.stack[i]);
			playerThisHand[i].bbBet$ = oneHand.BBBet;
		}
	}

	/*- ************************************************************************************************
	 * Get player seat number for ID 
	 * Arg0 - ID
	 * Returns seat number
	************************************************************************************************ */
	private int getPlayerSeatNumber(int id) {
		int seatNum = -1;
		for (int i = 0; i < PLAYERS; ++i) {
			if (playerIDs[i] == id) {
				seatNum = i;
				break;
			}
		}
		return seatNum;
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
	 * Read .ser file for this Class
	 * Arg0 - The full path name.
	 * Returns null if error
	***************************************************************************** */
	HandHistoryObjects readFromFile(String path) {
		HandHistoryObjects r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (HandHistoryObjects) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

}
