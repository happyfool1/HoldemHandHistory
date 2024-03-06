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
 * This class is used by GUIAnallyze to save data about many players.
 * It counts on the fact that GUIAnalysis uses players that share the same characteristics
 * such as player type or win rate.
 * It would be nice to have in Players BUT memory is a big problem.
 * For this analysis we keep them all together.
 * In GUIAnalysis we may have one instance of this class for something other than
 * individual players. 
 * One instance each for position and one instance each for relative position.
 * Maybe something else?
 * 
 * There is no data in this class that is the result of calculations. 
 * Calculated data is in several other classes such as PlayerCharacteristics.
 * GUIAnalyze parses Universal Hand History files to create data in this class.
 * 
 * This class represents a problem because of the memory required to save many 
 * thousands or millions of players in a HashMap during analysis.
 * On modern computers with 64gb memory it is still a problem.
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
public class PlayerBetSize implements java.io.Serializable, Constants {
	private final long serialVersionUID = 1234567L;

	PlayerBetSize() {
		setScale();
	}

	private static int numberOfInstancesAdded = 0; // Number of players added
	private static int dividend = 0; // Divide by this

	/*- ***********************************************************************************************
	 * Identification
	 ************************************************************************************************/
	int playerID = -1; // Key
	int handsPlayed; // Number of games played

	// New TODO

	/*- ********************************************************************************************
	* 	This data is used to analyze bet sizing.
	* I was very surprised to discover the wide distribution of bet sizes.
	* The data used is in steps of 1/4 pot.
	* Examples:
	* 	Preflop  5/4  pot bets were 16% and 6/4 pot bets were 40%
	* 	Flop 1/4 pot bets were 54% and 1/2 pot bets were 17%
	* 
	* These variables are an attempt to see if there is a correlation between 
	* bet sizes and other factors:
	* 		Position 
	* 		Relative position
	* 		Pot Odds
	* 		Hand strength
	* 		HML
	* 		Wet/Dry
	* 		StaticDynamic
	* 
	* The win / loose ratio may be a key determining factor
	*
	 * These arrays contain data that relates the Flop board with the type of flop.
	 * First index 		- street number 0-3
	 * Second Index - Bet ( BET1, BET2, BET3, BET4, ALLIN )
	 * Third index		- pot Steps    
	 *
	 * Data used for player action frequency analysis
	 * Frequency is very important in that it exposes player mistakes and can be used
	 * to exploit those mistakes.
	 * 
	 * A basic frequency analysis is MDF, Minimum Defense Frequency.
	 * Minimum defense frequency (MDF) describes the portion of your range that you must continue 
	 * with when facing a bet in order to remain unexploitable by bluffs.
	 * In other words, MDF is the minimum percentage of time you must call (or raise) to prevent your 
	 * opponent from profiting by always bluffing you. 
	 * If you fold more often than the MDF indicates, your opponent can exploit you by 
	 * over-bluffing when they bet.
	 * The formula to calculate MDF is simple:
	 * pot size / (pot size + bet size)
	 *
	 *	frequency = action / count.
	 * 		action is how many times the player responded in a specific way ( fold, check, call, raise, or all-in )
	 * 		count is how many times a player faces an action, a check or a bet. 
	 * 
	 * The data here is arrays of counts, actions, and arrays of the frequency calculated.
	 * There is also an arrays of the calculated MDF and the difference between calculated and actual.
	 * The difference is what we can exploit.
	 * 
	 * We use bet size in relation to pot size as one index.
	 * The index is in steps of 1/4 pot. < 1/4, 1/4 to < 1/2, 1/2 to < 3/4, ........
	 ************************************************************************************************/
	int[][][] betSize_playerFacingBetCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerResponseFoldCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerResponseCallCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerResponseBetCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];

	// Bet
	int[][][] betSize_BetSizeCountArrayPotFold = new int[STREET][BETS_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_BetSizeCountArrayPotCheck = new int[STREET][BETS_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_BetSizeCountArrayPotBet = new int[STREET][BETS_MAX][BET_SIZE_POT_MAX];
	double[][][] betSize_playerPotOddsBet = new double[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerPotOddsBetCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerBetIP = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerBetOOP = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	double[][][] betSize_playerBetMDF = new double[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerBetMDFCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	BigDecimal[][][] betSize_BetSize$ArrayPotFold = new BigDecimal[STREET][BETS_MAX][BET_SIZE_POT_MAX];
	BigDecimal[][][] betSize_BetSize$ArrayPotCheck = new BigDecimal[STREET][BETS_MAX][BET_SIZE_POT_MAX];
	BigDecimal[][][] betSize_BetSize$ArrayPotBet = new BigDecimal[STREET][BETS_MAX][BET_SIZE_POT_MAX];
// Call data  
	int[][][] betSize_BetSizeCountArrayPotCall = new int[STREET][BETS_MAX][BET_SIZE_POT_MAX];
	double[][][] betSize_playerPotOddsCall = new double[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerPotOddsCallCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerCallIP = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerCallOOP = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerCallMDFCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerCallMDFCountActual = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	BigDecimal[][][] betSize_BetSize$ArrayPotCall = new BigDecimal[STREET][BETS_MAX][BET_SIZE_POT_MAX];

	int[][][][] betSize_playerBetRp = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX][NUM_RP];
	int[][][][] betSize_playerCallRp = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX][NUM_RP];

	BigDecimal[][] betSize_BetSize$ArrayAverageBet = new BigDecimal[STREET][BETS_MAX];
	BigDecimal[][] betSize_BetSize$ArrayAverageCall = new BigDecimal[STREET][BETS_MAX];
	BigDecimal[][] betSize_BetSize$ArrayAverageFold = new BigDecimal[STREET][BETS_MAX];
	BigDecimal[][] betSize_BetSize$ArrayAverageCheck = new BigDecimal[STREET][BETS_MAX];

	int[][] betSize_BetSizeCountArrayAverageBet = new int[STREET][BETS_MAX];
	int[][] betSize_countsStreetBetBet = new int[NUM_STREETS][BETS_MAX]; // Number hands played
	int[][] betSize_BetSizeCountArrayAverageCall = new int[STREET][BETS_MAX];
	int[][] betSize_countsStreetBetCall = new int[NUM_STREETS][BETS_MAX]; // Number hands played
	// Fold
	int[][] betSize_BetSizeCountArrayAverageFold = new int[STREET][BETS_MAX];
	int[][] betSize_countsStreetBetFold = new int[NUM_STREETS][BETS_MAX]; // Number hands played
	// Check
	int[][] betSize_BetSizeCountArrayAverageCheck = new int[STREET][BETS_MAX];
	int[][] betSize_countsStreetBetCheck = new int[NUM_STREETS][BETS_MAX]; // Number hands played

	// For a Check there is no second or third index
	int[] betSize_playerPlayerFacingCheckCount = new int[STREET];
	int[] betSize_playerResponseCheckCount = new int[STREET];

	int[][][] betSize_playerBetFreqCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX]; // Not used
	int[][][] betSize_playerBet1FreqCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];// Not used
	int[][][] betSize_playerBet2FreqCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];// Not used
	int[][][] betSize_playerBet3FreqCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];// Not used
	int[][][] betSize_playerBet4FreqCount = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];// Not used

	// No street Flop only
	int[][][] betSize_boardBetHML = new int[BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX][HML_SIZE];
	int[][][] betSize_boardBetWetDry = new int[BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX][3];
	int[][][] betSize_boardBetStaticDynamic = new int[BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX][3];
	int[][][] betSize_boardBetFlop = new int[BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX][FLOP_COMBO];
	// No preflop
	int[][][][] betSize_boardPossibleBet = new int[3][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX][POSSIBLE_MAX];
	int[][][] betSize_playerWinShowdownBet = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerWinStreetBet = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];

	// No street Flop only
	int[][][] betSize_boardCallHML = new int[BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX][HML_SIZE];
	int[][][] betSize_boardCallWetDry = new int[BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX][3];
	int[][][] betSize_boardCallStaticDynamic = new int[BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX][3];
	int[][][] betSize_boardCallFlop = new int[BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX][FLOP_COMBO];
	// No preflop
	int[][][][] betSize_boardPossibleCall = new int[3][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX][POSSIBLE_MAX];
	int[][][] betSize_playerWinShowdownCall = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_playerWinStreetCall = new int[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	int[][][] betSize_draw = new int[3][BETS_MAX][POSSIBLE_MAX]; // postflop only

	/*- *****************************************************************************
	 * Calculated data
	***************************************************************************** */

	// Player frequence in response to a bet ( second index )
	double[][][] betSize_playerFoldFrequency = new double[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	double[][][] betSize_playerCallFrequency = new double[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	double[][][] betSize_playerBetFrequency = new double[STREET][BET_SIZE_ACTION_MAX][BET_SIZE_POT_MAX];
	double[] betSize_playerCheckFrequency = new double[STREET];

	/*- *****************************************************************************
	 * Do all of the calculations
	***************************************************************************** */
	void doCalculations(Players play) {

	}

	/*-***********************************************************************************************
	 * Set BigDecimal variables rounding mode to HALF_EVEN  bankers mode for currency.
	 * Called only by constructor.
	 * Sets all BigDecimal arrays to ZeroBD. Rounding 2 decimal places using bankers rounding
	 ************************************************************************************************/
	private void setScale() {
		for (int i = 0; i < NUM_STREETS; i += 1) {
			for (int j = 0; j < BETS_MAX; ++j) {
				for (int k = 0; k < BET_SIZE_POT_MAX; ++k) {
					this.betSize_BetSize$ArrayPotBet[i][j][k] = zeroBD;
					this.betSize_BetSize$ArrayPotCall[i][j][k] = zeroBD;
					this.betSize_BetSize$ArrayPotFold[i][j][k] = zeroBD;
					this.betSize_BetSize$ArrayPotCheck[i][j][k] = zeroBD;
				}
				this.betSize_BetSize$ArrayAverageBet[i][j] = zeroBD;
				this.betSize_BetSize$ArrayAverageCall[i][j] = zeroBD;
				this.betSize_BetSize$ArrayAverageFold[i][j] = zeroBD;
				this.betSize_BetSize$ArrayAverageCheck[i][j] = zeroBD;
			}
		}
	}

	/*-**************************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	***************************************************************************************/
	void sumAllPlayers(PlayerBetSize h0) {
		++numberOfInstancesAdded;
		sumAllStgy(h0);
		sumAllStgyBigDecimal(h0);
		sumAllStgyIndex(h0);
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
		divideAllStgy();
		divideAllStgyBigDecimal();
		divideAllStgyIndex();
	}

	/*- **************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	 *****************************************************************************/
	private void sumAllStgy(PlayerBetSize h0) {
		add(this.betSize_playerFacingBetCount, h0.betSize_playerFacingBetCount);
		add(this.betSize_playerResponseFoldCount, h0.betSize_playerResponseFoldCount);
		add(this.betSize_playerResponseCallCount, h0.betSize_playerResponseCallCount);
		add(this.betSize_playerResponseBetCount, h0.betSize_playerResponseBetCount);

		add(this.betSize_playerPlayerFacingCheckCount, h0.betSize_playerPlayerFacingCheckCount);
		add(this.betSize_playerResponseCheckCount, h0.betSize_playerResponseCheckCount);
		add(this.betSize_BetSizeCountArrayPotBet, h0.betSize_BetSizeCountArrayPotBet);
		add(this.betSize_BetSizeCountArrayAverageBet, h0.betSize_BetSizeCountArrayAverageBet);
		add(this.betSize_countsStreetBetBet, h0.betSize_countsStreetBetBet);
		add(this.betSize_BetSizeCountArrayPotCall, h0.betSize_BetSizeCountArrayPotCall);
		add(this.betSize_BetSizeCountArrayAverageCall, h0.betSize_BetSizeCountArrayAverageCall);
		add(this.betSize_countsStreetBetCall, h0.betSize_countsStreetBetCall);

		add(this.betSize_BetSizeCountArrayPotFold, h0.betSize_BetSizeCountArrayPotFold);
		add(this.betSize_BetSizeCountArrayAverageFold, h0.betSize_BetSizeCountArrayAverageFold);
		add(this.betSize_countsStreetBetFold, h0.betSize_countsStreetBetFold);
		add(this.betSize_BetSizeCountArrayPotCheck, h0.betSize_BetSizeCountArrayPotCheck);
		add(this.betSize_BetSizeCountArrayAverageCheck, h0.betSize_BetSizeCountArrayAverageCheck);
		add(this.betSize_countsStreetBetCheck, h0.betSize_countsStreetBetCheck);

		add(this.betSize_playerPotOddsBet, h0.betSize_playerPotOddsBet);
		add(betSize_playerPotOddsBetCount, h0.betSize_playerPotOddsBetCount);
		add(this.betSize_playerBetIP, h0.betSize_playerBetIP);
		add(this.betSize_playerBetOOP, h0.betSize_playerBetOOP);
		add(this.betSize_playerBetRp, h0.betSize_playerBetRp);
		add(this.betSize_playerBetMDF, h0.betSize_playerBetMDF);
		add(this.betSize_playerBetMDFCount, h0.betSize_playerBetMDFCount);
		add(this.betSize_playerBetFreqCount, h0.betSize_playerBetFreqCount);
		add(this.betSize_playerBet1FreqCount, h0.betSize_playerBet1FreqCount);
		add(this.betSize_playerBet2FreqCount, h0.betSize_playerBet2FreqCount);
		add(this.betSize_playerBet3FreqCount, h0.betSize_playerBet3FreqCount);
		add(this.betSize_playerBet4FreqCount, h0.betSize_playerBet4FreqCount);

		add(this.betSize_boardBetHML, h0.betSize_boardBetHML);
		add(this.betSize_boardBetWetDry, h0.betSize_boardBetWetDry);
		add(this.betSize_boardBetStaticDynamic, h0.betSize_boardBetStaticDynamic);
		add(this.betSize_boardBetFlop, h0.betSize_boardBetFlop);
		add(this.betSize_boardPossibleBet, h0.betSize_boardPossibleBet);
		add(this.betSize_playerWinShowdownBet, h0.betSize_playerWinShowdownBet);
		add(this.betSize_playerWinStreetBet, h0.betSize_playerWinStreetBet);

		add(this.betSize_playerPotOddsCall, h0.betSize_playerPotOddsCall);
		add(this.betSize_playerPotOddsCallCount, h0.betSize_playerPotOddsCallCount);
		add(this.betSize_playerCallIP, h0.betSize_playerCallIP);
		add(this.betSize_playerCallOOP, h0.betSize_playerCallOOP);
		add(this.betSize_playerCallMDFCount, h0.betSize_playerCallMDFCount);
		add(this.betSize_playerCallMDFCountActual, h0.betSize_playerCallMDFCountActual);
		add(this.betSize_playerCallRp, h0.betSize_playerCallRp);

		add(this.betSize_boardCallHML, h0.betSize_boardCallHML);
		add(this.betSize_boardCallWetDry, h0.betSize_boardCallWetDry);
		add(this.betSize_boardCallStaticDynamic, h0.betSize_boardCallStaticDynamic);
		add(this.betSize_boardCallFlop, h0.betSize_boardCallFlop);
		add(this.betSize_boardPossibleCall, h0.betSize_boardPossibleCall);
		add(this.betSize_playerWinShowdownCall, h0.betSize_playerWinShowdownCall);
		add(this.betSize_playerWinStreetCall, h0.betSize_playerWinStreetCall);
		add(this.betSize_draw, h0.betSize_draw);
	}

	/*- **************************************************************************
	* Divide all elements by the number of players added together 
	 *****************************************************************************/
	private void divideAllStgy() {
		divide(this.betSize_playerFacingBetCount);
		divide(this.betSize_playerResponseFoldCount);
		divide(this.betSize_playerResponseCallCount);
		divide(this.betSize_playerResponseBetCount);

		divide(this.betSize_playerPlayerFacingCheckCount);
		divide(this.betSize_playerResponseCheckCount);
		divide(this.betSize_BetSizeCountArrayPotBet);
		divide(this.betSize_BetSizeCountArrayAverageBet);
		divide(this.betSize_countsStreetBetBet);
		divide(this.betSize_BetSizeCountArrayPotCall);
		divide(this.betSize_BetSizeCountArrayAverageCall);
		divide(this.betSize_countsStreetBetCall);

		divide(this.betSize_BetSizeCountArrayPotFold);
		divide(this.betSize_BetSizeCountArrayAverageFold);
		divide(this.betSize_countsStreetBetFold);
		divide(this.betSize_BetSizeCountArrayPotCheck);
		divide(this.betSize_BetSizeCountArrayAverageCheck);
		divide(this.betSize_countsStreetBetCheck);

		divide(this.betSize_playerPotOddsBet);
		divide(betSize_playerPotOddsBetCount);
		divide(this.betSize_playerBetIP);
		divide(this.betSize_playerBetOOP);
		divide(this.betSize_playerBetRp);
		divide(this.betSize_playerBetMDF);
		divide(this.betSize_playerBetMDFCount);
		divide(this.betSize_playerBetFreqCount);
		divide(this.betSize_playerBet1FreqCount);
		divide(this.betSize_playerBet2FreqCount);
		divide(this.betSize_playerBet3FreqCount);
		divide(this.betSize_playerBet4FreqCount);

		divide(this.betSize_boardBetHML);
		divide(this.betSize_boardBetWetDry);
		divide(this.betSize_boardBetStaticDynamic);
		divide(this.betSize_boardBetFlop);
		divide(this.betSize_boardPossibleBet);
		divide(this.betSize_playerWinShowdownBet);
		divide(this.betSize_playerWinStreetBet);

		divide(this.betSize_playerPotOddsCall);
		divide(this.betSize_playerPotOddsCallCount);
		divide(this.betSize_playerCallIP);
		divide(this.betSize_playerCallOOP);
		divide(this.betSize_playerCallMDFCount);
		divide(this.betSize_playerCallMDFCountActual);
		divide(this.betSize_playerCallRp);

		divide(this.betSize_boardCallHML);
		divide(this.betSize_boardCallWetDry);
		divide(this.betSize_boardCallStaticDynamic);
		divide(this.betSize_boardCallFlop);
		divide(this.betSize_boardPossibleCall);
		divide(this.betSize_playerWinShowdownCall);
		divide(this.betSize_playerWinStreetCall);
		divide(this.betSize_draw);

	}

	/*-**************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	 *****************************************************************************/
	private void sumAllStgyBigDecimal(PlayerBetSize h0) {
		add(this.betSize_BetSize$ArrayPotBet, h0.betSize_BetSize$ArrayPotBet);
		add(this.betSize_BetSize$ArrayAverageBet, h0.betSize_BetSize$ArrayAverageBet);
		add(this.betSize_BetSize$ArrayPotCall, h0.betSize_BetSize$ArrayPotCall);
		add(this.betSize_BetSize$ArrayAverageCall, h0.betSize_BetSize$ArrayAverageCall);
		add(this.betSize_BetSize$ArrayPotFold, h0.betSize_BetSize$ArrayPotFold);
		add(this.betSize_BetSize$ArrayAverageFold, h0.betSize_BetSize$ArrayAverageFold);
		add(this.betSize_BetSize$ArrayPotCheck, h0.betSize_BetSize$ArrayPotCheck);
		add(this.betSize_BetSize$ArrayAverageCheck, h0.betSize_BetSize$ArrayAverageCheck);
	}

	/*-**************************************************************************
	* Divide all elements by the number of players added together 
	 *****************************************************************************/
	private void divideAllStgyBigDecimal() {
		divide(this.betSize_BetSize$ArrayPotBet);
		divide(this.betSize_BetSize$ArrayAverageBet);
		divide(this.betSize_BetSize$ArrayPotCall);
		divide(this.betSize_BetSize$ArrayAverageCall);
		divide(this.betSize_BetSize$ArrayPotFold);
		divide(this.betSize_BetSize$ArrayAverageFold);
		divide(this.betSize_BetSize$ArrayPotCheck);
		divide(this.betSize_BetSize$ArrayAverageCheck);
	}

	/*- **************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	 *****************************************************************************/
	private void sumAllStgyIndex(PlayerBetSize h0) {
		add(this.betSize_playerPotOddsCall, h0.betSize_playerPotOddsCall);
		add(this.betSize_playerPotOddsBet, h0.betSize_playerPotOddsCall);
		add(this.betSize_playerBetIP, h0.betSize_playerBetIP);
		add(this.betSize_playerBetOOP, h0.betSize_playerBetOOP);
		add(this.betSize_playerWinShowdownBet, h0.betSize_playerWinShowdownBet);
		add(this.betSize_playerWinStreetBet, h0.betSize_playerWinStreetBet);

		add(this.betSize_playerBetRp, h0.betSize_playerBetRp);
		add(this.betSize_boardBetHML, h0.betSize_boardBetHML);
		add(this.betSize_boardBetWetDry, h0.betSize_boardBetWetDry);
		add(this.betSize_boardBetStaticDynamic, h0.betSize_boardBetStaticDynamic);
		add(this.betSize_boardBetFlop, h0.betSize_boardBetFlop);
	}

	/*- **************************************************************************
	* Divide all elements by the number of players added together 
	 *****************************************************************************/
	private void divideAllStgyIndex() {
		divide(this.betSize_playerPotOddsCall);
		divide(this.betSize_playerPotOddsBet);
		divide(this.betSize_playerBetIP);
		divide(this.betSize_playerBetOOP);
		divide(this.betSize_playerWinShowdownBet);
		divide(this.betSize_playerWinStreetBet);

		divide(this.betSize_playerBetRp);
		divide(this.betSize_boardBetHML);
		divide(this.betSize_boardBetWetDry);
		divide(this.betSize_boardBetStaticDynamic);
		divide(this.betSize_boardBetFlop);
	}

	/*- ******************************************************************************
	 * Calculate Average  
	****************************************************************************** */
	private double calculateAverage(double a, double b) {
		if (b <= 0.) {
			return 0.;
		}
		return (a / b);
	}

	/*- ******************************************************************************
	 * Calculates bet size
	  * Arg0 -PlayerCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns a BigDecimal
	 * 
	 * In AllPlayerBetSize:
	 *  		BigDecimal[][] betSize_BetSize$ArrayAverage = new BigDecimal[STREET][BETS_MAX];
	 *  		int[][] betSize_BetSizeCountArrayAverage = new int[STREET][BETS_MAX];
	 *  		size / count is average bet size
	*******************************************************************************/
	private BigDecimal calculateBetSizeBet(int street, int type) {
		var betBD = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
		var $ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
		final int count = this.betSize_BetSizeCountArrayAverageBet[street][type];
		if (count == 0) {
			return zeroBD;
		}

		final var countBD = new BigDecimal(count).setScale(2, RoundingMode.HALF_EVEN);

		betBD = this.betSize_BetSize$ArrayAverageBet[street][type];
		$ = betBD.divide(countBD, 2, RoundingMode.HALF_EVEN);
		return $;
	}

	/*- ******************************************************************************
	 * Preflop bet2 avarage size
	 * Returns a BigDecimal
	*******************************************************************************/
	private BigDecimal calculatePreflopBet2SizeBet(PlayerBetSize play) {
		var bet2 = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
		calculatePreflopBetSizeBet(BET2);
		bet2 = calculateBetSizeBet(0, BET2);

		Logger.logError("//PREFLOP BET2 SIZE " + bet2);
		return bet2;
	}

	/*- ******************************************************************************
	 * Calculates bet size  
	* Returns a BigDecimal
	*******************************************************************************/
	private BigDecimal calculatePreflopBetSizeBet(int type) {
		return calculateBetSizeBet(0, type);
	}

	private BigDecimal calculateFlopBetSizeBet(int type) {
		return calculateBetSizeBet(1, type);
	}

	private BigDecimal calculateTurnBetSizeBet(int type) {
		return calculateBetSizeBet(2, type);
	}

	private BigDecimal calculateRiverBetSizeBet(int type) {
		return calculateBetSizeBet(3, type);
	}

	/*- ******************************************************************************
	 * Returns  count as an int
	 * int[][] betSize_BetSizeCountArrayAverage = new int[STREET][BETS_MAX];
	 * BigDecimal[][] betSize_BetSize$ArrayAverage = new BigDecimal[STREET][BETS_MAX];
	*******************************************************************************/
	private int betSizeCountArrayBet(int street, int type) {
		return this.betSize_BetSizeCountArrayAverageBet[street][type];
	}

	/*- ************************************************************************************
	 * Calculate frequencies 
	 * Frquency is the number of times that an action is taken divide by 
	 *  the number of times that a player faces a bet.
	 *  The indexes are:
	 *  	street number
	 *  	bet type BET1, BET2, BET3, BET4, or ALLIN
	 *  	The bet in relation to pot size ( < 1/4 pot, 1/4 pot to < 1/2 pot, ......
	 *  
	 *  Check is a special case because there is no bet
	*************************************************************************************/
	private void calculateFrequencies(PlayerBetSize play) {
		for (int i = 0; i < STREETS; ++i) {
			this.betSize_playerCheckFrequency[i] = (double) this.betSize_playerResponseCheckCount[i]
					/ (double) this.betSize_playerPlayerFacingCheckCount[i];
		}

		for (int i = 0; i < STREETS; ++i) {
			for (int j = 0; j < BET_SIZE_ACTION_MAX; ++j) {
				for (int k = 0; k < BET_SIZE_POT_MAX; ++k) {
					if (this.betSize_playerFacingBetCount[i][j][k] == 0) {
						this.betSize_playerFoldFrequency[i][j][k] = 0.;
						this.betSize_playerCallFrequency[i][j][k] = 0.;
						this.betSize_playerBetFrequency[i][j][k] = 0.;
					} else {
						this.betSize_playerFoldFrequency[i][j][k] = this.betSize_playerResponseFoldCount[i][j][k]
								/ (double) this.betSize_playerFacingBetCount[i][j][k];
						this.betSize_playerCallFrequency[i][j][k] = this.betSize_playerResponseCallCount[i][j][k]
								/ (double) this.betSize_playerFacingBetCount[i][j][k];
						this.betSize_playerBetFrequency[i][j][k] = this.betSize_playerResponseBetCount[i][j][k]
								/ (double) this.betSize_playerFacingBetCount[i][j][k];
					}
				}
			}
		}
	}

	/*- ******************************************************************************
	 * Calculate number of bets for a 1/4 pot increment.
	 * Arg0 -PlayerBetSizeCharacteristics Class instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns  count as an int
	 * int[][][] betSize_BetSizeCountArrayPot = new int[STREET][BETS_MAX][BET_SIZE_POT_MAX];
	 * BigDecimal[][][] betSize_BetSize$ArrayPot = new BigDecimal[STREET][BETS_MAX][BET_SIZE_POT_MAX];
	 * betSizeCountArrayPot(allPlayerBetSize, i, j, 0));
	*******************************************************************************/
	private int betSizeCountArrayPotBet(int street, int type, int potSteps) {
		return this.betSize_BetSizeCountArrayPotBet[street][type][potSteps];
	}

	/*- ******************************************************************************
	 * Calculate percentage of bets for a 1/4 pot step
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   percentage as double
	 * 
	 * stepCount  
	*******************************************************************************/
	private double betSizeCountArrayPotPercentageBet(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_BetSizeCountArrayPotBet[street][type][potSteps],
				this.betSize_countsStreetBetBet[street][type]);
	}

	/*- ******************************************************************************
	 * Calculate percentage of bets for a 1/4 pot step
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   percentage as double
	 * 
	 * stepCount  
	*******************************************************************************/
	private double betSizeCountArrayPotPercentageCall(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_BetSizeCountArrayPotCall[street][type][potSteps],
				this.betSize_countsStreetBetCall[street][type]);
	}

	/*- ******************************************************************************
	 * Calculate percentage pot odds for a 1/4 potstep
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns odds as double
	 * 
	 * 	double [][][] betSize_playerPotOdds = new double[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeCountArrayPotOddsCallAverage(int street, int type, int potSteps) {
		return calculateAverage(this.betSize_playerPotOddsCall[street][type][potSteps],
				this.betSize_playerPotOddsCallCount[street][type][potSteps]);
	}

	/*- ******************************************************************************
	 * Calculate percentage pot odds for a 1/4 potstep
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns  odds as double
	 * 
	 * 	double [][][] betSize_playerPotOdds = new double[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeCountArrayPotOddsBetAverage(int street, int type, int potSteps) {
		return calculateAverage(this.betSize_playerPotOddsBet[street][type][potSteps],
				this.betSize_playerPotOddsBetCount[street][type][potSteps]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis - player is In Position IP
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   percentage as double
	 * 	int [][][] betSize_playerIP = new int [PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeAnalysisIPBet(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_playerBetIP[street][type][potSteps],
				this.betSize_countsStreetBetBet[street][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis - player is Out Of Position OOP
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   percentage as double
	 * 	int [][][] betSize_playerIP = new int [PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeAnalysisOOPBet(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_playerBetOOP[street][type][potSteps],
				this.betSize_countsStreetBetBet[street][type]);
	}

	/*- ******************************************************************************
	 *Call size Analysis - player is In Position IP
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   percentage as double
	 * 	int [][][] betSize_playerIP = new int [PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeAnalysisIPCall(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_playerCallIP[street][type][potSteps],
				this.betSize_countsStreetBetCall[street][type]);
	}

	/*- ******************************************************************************
	 * Call size Analysis - player is Out Of Position OOP
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   percentage as double
	 * 	int [][][] betSize_playerIP = new int [PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeAnalysisOOPCall(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_playerCallOOP[street][type][potSteps],
				this.betSize_countsStreetBetCall[street][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis - Percentage of the time that player wins Showdown with type and step
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   double percentage
	 *		int[][][] betSize_playerWinLooseShowdownBet = new int[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	 *		int[][][] betSize_playerWinLooseStreetBet = new int[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeAnalysisWinShowdown(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_playerWinShowdownBet[street][type][potSteps],
				this.betSize_countsStreetBetCall[street][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis -  Bet size Analysis - Percentage of the time that player wins Showdown with type and step
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   double percentage
	 *		int[][][] betSize_playerWinLooseShowdownBet = new int[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	 *		int[][][] betSize_playerWinLooseStreetBet = new int[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeAnalysisWinStreet(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_playerWinStreetBet[street][type][potSteps],
				this.betSize_countsStreetBetCall[street][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis -  Bet size Analysis - Average MDF
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   double percentage
	 *		int[][][] betSize_playerWinLooseShowdownBet = new int[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	 *		int[][][] betSize_playerWinLooseStreetBet = new int[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeAnalysisMDFBet(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_playerBetMDF[street][type][potSteps],
				this.betSize_playerBetMDFCount[street][type][potSteps]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis - board HML value   TODO
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   HML found the most as string
	 * 
	 * Finds the maximum number and returns a String for that
	*******************************************************************************/
	private String betSizeAnalysisHML(int type, int potSteps) {
		int max = -1;
		int ndx = -1;
		for (int i = 0; i < PlayerBetSize.HML_SIZE; ++i) {
			if (this.betSize_boardBetHML[type][potSteps][i] > max) {
				max = this.betSize_boardBetHML[type][potSteps][i];
				ndx = i;
			}
		}
		if (ndx == -1) {
			return "error";
		}
		return PlayerBetSize.HMLSt[ndx];
	}

	/*- ******************************************************************************
	 * Bet size Analysis - board RP HML value   TODO
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   Relative position found the most as string
	 * 
	 * Finds the maximum number and returns a String for that
	*******************************************************************************/
	private String betSizeAnalysisRpBet(int street, int type, int potSteps) {
		int max = -1;
		int ndx = -1;
		for (int i = 0; i < PlayerBetSize.NUM_RP; ++i) {
			if (this.betSize_playerBetRp[street][type][potSteps][i] > max) {
				max = this.betSize_playerBetRp[street][type][potSteps][i];
				ndx = i;
			}
		}
		if (ndx == -1) {
			return "error";
		}
		return RP_ST[ndx];
	}

	/*- ******************************************************************************
	 * Call size Analysis - board RP HML value   TODO
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   Relative position found the most as string
	 * 
	 * Finds the maximum number and returns a String for that
	*******************************************************************************/
	private String betSizeAnalysisRpCall(int street, int type, int potSteps) {
		int max = -1;
		int ndx = -1;
		for (int i = 0; i < PlayerBetSize.NUM_RP; ++i) {
			if (this.betSize_playerCallRp[street][type][potSteps][i] > max) {
				max = this.betSize_playerCallRp[street][type][potSteps][i];
				ndx = i;
			}
		}
		if (ndx == -1) {
			return "error";
		}
		return RP_ST[ndx];
	}

	/*- ******************************************************************************
	 * Bet size Analysis - board RP  Percentage
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Arg3 - relative position
	 * Returns   Relative position found the most as string
	 * 
	 * 	int[][][][] betSize_playerBetRp = new int[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][PlayerBetSize.NUM_RP];
	*******************************************************************************/
	private double betSizeAnalysisRpPerBet(int street, int type, int potSteps, int rp) {
		return calculatePercentage(this.betSize_playerBetRp[street][type][potSteps][rp],
				this.betSize_countsStreetBetBet[street][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis - board RP  Percentage
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Arg3 - relative position
	 * Returns   Relative position found the most as string
	 * 
	 * 	int[][][][] betSize_playerBetRp = new int[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][PlayerBetSize.NUM_RP];
	*******************************************************************************/
	private double betSizeAnalysisRpPerCall(int street, int type, int potSteps, int rp) {
		return calculatePercentage(this.betSize_playerCallRp[street][type][potSteps][rp],
				this.betSize_countsStreetBetCall[street][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis -   RP  Percentage
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   double Percentage
	 * 
	 *	int[][][] betSize_playerBetIP = new int[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeAnalysisIPPerBet(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_playerBetIP[street][type][potSteps],
				this.betSize_countsStreetBetBet[street][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis - OOP  Percentage
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns  double Percentage
	 * 
	 * int[][][] betSize_playerBetOOP = new int[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeAnalysisOOPPerBet(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_playerBetOOP[street][type][potSteps],
				this.betSize_countsStreetBetBet[street][type]);
	}

	/*- ******************************************************************************
	 * Call size Analysis -   RP  Percentage
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   double Percentage
	 * 
	 *	int[][][] betSize_playerCallIP = new int[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeAnalysisIPPerCall(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_playerCallIP[street][type][potSteps],
				this.betSize_countsStreetBetCall[street][type]);
	}

	/*- ******************************************************************************
	 * Call size Analysis - OOP  Percentage
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns  double Percentage
	 * 
	 * int[][][] betSize_playerCallOOP = new int[PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private double betSizeAnalysisOOPPerCall(int street, int type, int potSteps) {
		return calculatePercentage(this.betSize_playerCallOOP[street][type][potSteps],
				this.betSize_countsStreetBetCall[street][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis - board   Percentage
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Arg3 - Board possible index
	 * Returns  double Percentage
	 * 
	 * 	int[][][][] betSize_boardPossibleBet = new int[3][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][PlayerBetSize.POSSIBLE_MAX];
	*******************************************************************************/
	private double betSizeAnalysisBoardPerBet(int street, int type, int potSteps, int board) {
		return calculatePercentage(this.betSize_boardPossibleBet[street][type][potSteps][board],
				this.betSize_countsStreetBetBet[street][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis - Board  Wet or Dry  
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   Wet Dry or Neither as string
	 * 
	 * Finds the maximum number and returns a String for that
	****************************************************************************** */
	private String betSizeAnalysisWetDryBet(int type, int potSteps) {
		int max = -1;
		int ndx = -1;
		for (int i = 0; i < 3; ++i) {
			if (this.betSize_boardBetWetDry[type][potSteps][i] > max) {
				max = this.betSize_boardBetWetDry[type][potSteps][i];
				ndx = i;
			}
		}
		return PlayerBetSize.wetDrySt[ndx];
	}

	/*- ******************************************************************************
	 * Bet size Analysis - Board  Wet or Dry  
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   Wet Dry or Neither as string
	 * 
	 * Finds the maximum number and returns a String for that
	 *******************************************************************************/
	private String betSizeAnalysisWetDryCall(int type, int potSteps) {
		int max = -1;
		int ndx = -1;
		for (int i = 0; i < 3; ++i) {
			if (this.betSize_boardCallWetDry[type][potSteps][i] > max) {
				max = this.betSize_boardCallWetDry[type][potSteps][i];
				ndx = i;
			}
		}
		return PlayerBetSize.wetDrySt[ndx];
	}

	/*- ******************************************************************************
	 *Call size Analysis - Board  Wet or Dry  percentage
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   Percentage
	 * 
	 * 	int[][][] betSize_boardCallWetDry = new int[PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][3];
	 * int[][][] betSize_boardCallStaticDynamic = new int[PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][3];
	 *******************************************************************************/
	private Double betSizeAnalysisWetDryPerCall(int type, int potSteps, int ndx) {
		return calculatePercentage(this.betSize_boardCallWetDry[type][potSteps][ndx],
				this.betSize_countsStreetBetCall[1][type]);
	}

	/*- ******************************************************************************
	 * Call size Analysis - Board Static or Dynamic  
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   Static Dynamicor  Neither as string
	 * 
	 * Finds the maximum number and returns a String for that
	******************************************************************************	*/
	private String betSizeAnalysisStaticDynamicBet(int type, int potSteps) {
		int max = -1;
		int ndx = -1;
		for (int i = 0; i < PlayerBetSize.STATICDYNAMIC_MAX; ++i) {
			if (this.betSize_boardBetStaticDynamic[type][potSteps][i] > max) {
				max = this.betSize_boardBetStaticDynamic[type][potSteps][i];
				ndx = i;
			}
		}
		return PlayerBetSize.staticDynamicSt[ndx];
	}

	/*- ******************************************************************************
	 * Call size Analysis - Board Static or Dynamic  
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   Static Dynamicor  Neither as string
	 * 
	 * Finds the maximum number and returns a String for that
	*******************************************************************************/
	private String betSizeAnalysisStaticDynamicCall(int type, int potSteps) {
		int max = -1;
		int ndx = -1;
		for (int i = 0; i < PlayerBetSize.STATICDYNAMIC_MAX; ++i) {
			if (this.betSize_boardCallStaticDynamic[type][potSteps][i] > max) {
				max = this.betSize_boardCallStaticDynamic[type][potSteps][i];
				ndx = i;
			}
		}
		return PlayerBetSize.staticDynamicSt[ndx];
	}

	/*- ******************************************************************************
	 * Call size Analysis - Board  Staticb Dynamic  percentage
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   Percentage
	 * 
	 * 	int[][][] betSize_boardCallWetDry = new int[PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][3];
	 * int[][][] betSize_boardCallStaticDynamic = new int[PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][3];
	****************************************************************************** */
	private Double betSizeAnalysisStaticDynamicPerCall(int type, int potSteps, int ndx) {
		return calculatePercentage(this.betSize_boardCallStaticDynamic[type][potSteps][ndx],
				this.betSize_countsStreetBetCall[1][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis - Board  Wet or Dry  percentage
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   Percentage
	 * 
	 * 	int[][][] betSize_boardBetWetDry = new int[PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][3];
	 * int[][][] betSize_boardBetStaticDynamic = new int[PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][3];
	****************************************************************************** */
	private Double betSizeAnalysisWetDryPer(int type, int potSteps, int ndx) {
		return calculatePercentage(this.betSize_boardBetWetDry[type][potSteps][ndx],
				this.betSize_countsStreetBetBet[1][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis - Board  Wet or Dry  percentage
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   Percentage
	 * 
	 * 	int[][][] betSize_boardBetWetDry = new int[PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][3];
	 * int[][][] betSize_boardBetStaticDynamic = new int[PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][3];
	 *******************************************************************************/
	private Double betSizeAnalysisWetDryPerBet(int type, int potSteps, int ndx) {
		return calculatePercentage(this.betSize_boardBetWetDry[type][potSteps][ndx],
				this.betSize_countsStreetBetBet[1][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis - Board  Staticb Dynamic  percentage
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   Percentage
	 * 
	 * 	int[][][] betSize_boardBetWetDry = new int[PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][3];
	 * int[][][] betSize_boardBetStaticDynamic = new int[PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][3];
	 *******************************************************************************/
	private Double betSizeAnalysisStaticDynamicPerBet(int type, int potSteps, int ndx) {
		return calculatePercentage(this.betSize_boardBetStaticDynamic[type][potSteps][ndx],
				this.betSize_countsStreetBetBet[1][type]);
	}

	/*- ******************************************************************************
	 * Bet size Analysis - Board   TODO
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns  ?
	 * 	int [][][] betSize_playerIP = new int [PlayerBetSize.STREET][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX];
	*******************************************************************************/
	private String betSizeAnalysisFlop(int street, int type, int potSteps) {
		return "?";
	}

	/*- ******************************************************************************
	 * Bet size Analysis - Board    
	 * Arg0 -PlayerBetSizeCharacteristicsClass instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   ?
	 * 	int[][][][] betSize_boardPossibleBet = new int[3][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][PlayerBetSize.POSSIBLE_MAX];
	*******************************************************************************/
	private String betSizeAnalysisPossibleBet(int street, int type, int potSteps) {
		int max = -1;
		int ndx = -1;
		for (int i = 0; i < PlayerBetSize.POSSIBLE_MAX; ++i) {
			if (this.betSize_boardPossibleBet[street][type][potSteps][i] > max) {
				max = this.betSize_boardPossibleBet[street][type][potSteps][i];
				ndx = i;
			}
		}
		return PlayerBetSize.POSSIBLESt[ndx];
	}

	/*- ******************************************************************************
	 * Bet size Analysis - Board    
	 * Arg0 -PlayerBetSizeCharacteristics Class instance
	 * Arg1 - Street number 0 - 3
	 * Arg2 - Type BET1, BET2, BET3, BET4, ALLIN
	 * Returns   ?
	 * 	int[][][][] betSize_boardPossibleBet = new int[3][PlayerBetSize.BET_SIZE_ACTION_MAX][PlayerBetSize.BET_SIZE_POT_MAX][PlayerBetSize.POSSIBLE_MAX];
	*******************************************************************************/
	private String betSizeAnalysisPossibleCall(int street, int type, int potSteps) {
		int max = -1;
		int ndx = -1;
		for (int i = 0; i < PlayerBetSize.POSSIBLE_MAX; ++i) {
			if (this.betSize_boardPossibleCall[street][type][potSteps][i] > max) {
				max = this.betSize_boardPossibleCall[street][type][potSteps][i];
				ndx = i;
			}
		}
		return PlayerBetSize.POSSIBLESt[ndx];
	}

	/*- ******************************************************************************
	 * Calculates Preflop bet2 size  ( open )
	 * Returns a BigDecimal
	*******************************************************************************/
	private BigDecimal calculateBetSizeStgyBet(int street, int type, int stgy) {
		var betBD = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
		var $ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
		final int count = this.betSize_BetSizeCountArrayPotBet[street][type][stgy];
		if (count == 0) {
			return zeroBD;
		}

		final var countBD = new BigDecimal(count).setScale(2, RoundingMode.HALF_EVEN);
		betBD = this.betSize_BetSize$ArrayPotBet[street][type][stgy];
		$ = betBD.divide(countBD, 2, RoundingMode.HALF_EVEN);
		return $;
	}

	/*- ******************************************************************************
	 * Calculates Preflop bet2 size  ( open )
	 * Returns a BigDecimal
	*******************************************************************************/
	private BigDecimal calculateBetSizeStgyCall(int street, int type, int stgy) {
		var betBD = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
		var $ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
		final int count = this.betSize_BetSizeCountArrayPotCall[street][type][stgy];
		if (count == 0) {
			return zeroBD;
		}

		final var countBD = new BigDecimal(count).setScale(2, RoundingMode.HALF_EVEN);
		betBD = this.betSize_BetSize$ArrayPotCall[street][type][stgy];
		$ = betBD.divide(countBD, 2, RoundingMode.HALF_EVEN);
		return $;
	}

	/*-  *****************************************************************************
	 * Calculate percentage with error checking
	 ***************************************************************************** */
	private double calculatePercentage(double a, double b) {
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
	PlayerBetSize readFromFile(String path) {
		PlayerBetSize r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (PlayerBetSize) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

}
