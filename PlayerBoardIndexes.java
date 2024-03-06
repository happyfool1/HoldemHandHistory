package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PlayerBoardIndexes implements java.io.Serializable, Constants {
	static final long serialVersionUID = 1234567L;

	/*- *****************************************************************************
	 * This class is part of a group of classes, all of which hold calculated data about
	 * players. The data is obtained from the PlayerBoardIndexesclass.
	 * 
	 * The PlayerBoardIndexesclass contains only data collected from parsing Hand History files
	 * in the Universal format defined by the Hand class and the Action class.
	 * There are no calculated values in PlayerBoardIndexesand no other class is used to save
	 * data obtained from parsing.
	 * 
	 * The classes in this group all hold calculations only. The methods that do the 
	 * calculation are in the class with the calculated data.
	 * It is hoped that other programmers will participate in this project by taking
	 * ownership of one of these classes or by creating an all new class that will be
	 * part of this group.  Part of the group are a group of classes that report on the
	 * calculated data. One reporting class with that corresponds to the calculating class.
	 * Names of classes are consistent,  PlayerBoardIndexes, and Report PlayerBoardIndexes.
	 * 
	 * The core functions are complete. Filtering Hand History files. Parsing the filtered files
	 * and creating Universal Format files, classifying players by type and other characteristics
	 * to create list of players to be analyzed as a group, and parsing the Universal Format files
	 * to create PlayerBoardIndexesobjects.
	 * Most of the remaining effort will be in the calculation and reporting classes.
	 * 
	 * Each of the calculation classes has a method:
	 * DoCalculations(PlayerBoardIndexesplay, PlayerCharactistics char){}
	 * Calculations are done using data in the PlayerBoardIndexesclass and the PlayerCharactistics class.
	 * 
	 * All of the calculation classes share a common key, the player ID. The player ID can either 
	 * identify a specific player or a group if players.
	 * 
	 * The calculation classes do not sort or select players. That has already been done.
	 * 
	 * These classes are all able to write .ser files.
	 * 
	 * These classes are all able to create a single instance of a class object by totalling 
	 * all of the calculated variables and dividing them by the number if instances of the class
	 * that were used to create the composite object. So for example, one instance of 
	 *  PlayerBoardIndexes may represent thousands or even millons of players within the same 
	 * characteristics. They all have a sumAll method and a divideAll method.
	 * sumAll(PlayerBoardIndexesX x){} Adds all data from the x instance to this.
	 * divideAll() divides all variables by the number of instances added to the class.
	 * 
	 *  @author PEAK_ 
	***************************************************************************** */

	/*- *****************************************************************************
	 * 	This specific class calculates data about  ....
	***************************************************************************** */
	private static int numberOfInstancesAdded = 0;
	private static int dividend = 0; // Divide by this

	/*- ****************************************************************************** 
	 * Identification and key to HashMap
	 *********************************************************************************/
	int playerID = -1; // Key
	int handsPlayed; // Number of games played

	/*-**********************************************************************************************
	 * New TODO
	 * These arrays contain data that relates the Flop board with the type of flop.
	 * The FlopCombinations Class calculates:
	 * 		The index into an array of 1755 flops that represent all possible flops. 
	 * 		The index into an array of 10 flops that represent all possible flops. 
	 * 
	 * There are 1,755 different flops. this asumes that order is not important. For exalmple
	 * AKQ and AQK are exactly the same. It also assumes that the actual suit in not important. 
	 * For example As Ks Qs are exactly the same as Ad Kd Qd or Ad Qd Kd. No difference at all.
	 * 
	 * The action arrays are for when the first action that the player takes on the flop.
	 * 		Check is the exception, always paired with the next action	
	 * 
	*	Low Medium High combinations. 
	*  Cards in the Flop oard are classified as High, Medium, or Low
	*  		High - 10 and above
	*  		Medium - 6  to 9
	*  		Low - 2 - 5
	*  this result is 0 - 9 possible flop combinations, HHH - LLL
	*  The idea is to find a small number of combinations that represent all possible flops.
	*  Easy for a human to classify at the table.
	*  Not nearly as good as the 1555 possible flops, but a human can not do that classification
	*  at the table.
	*  
	*  The first index is the bet type BET1, BET2, BET3, BET4, ALLIN
	*  The second index is HML
	 ************************************************************************************************/
	int[][] betCountsHML = new int[BETS_MAX][HML_SIZE];
	int[][] foldToBetHML = new int[BETS_MAX][HML_SIZE];
	int[][] checkFoldHML = new int[BETS_MAX][HML_SIZE];
	int[][] checkBetHML = new int[BETS_MAX][HML_SIZE];
	int[][] checkCallBetHML = new int[BETS_MAX][HML_SIZE];
	int[][] betCountsCombo = new int[BETS_MAX][FLOP_COMBO];
	int[][] foldToBetCombo = new int[BETS_MAX][FLOP_COMBO];
	int[][] checkFoldCombo = new int[BETS_MAX][FLOP_COMBO];
	int[][] checkBetCombo = new int[BETS_MAX][FLOP_COMBO];
	int[][] checkCallBet1Combo = new int[BETS_MAX][FLOP_COMBO];
	int[][] betCountsWetDry = new int[BETS_MAX][WETDRY_MAX];
	int[][] foldToBetWetDry = new int[BETS_MAX][WETDRY_MAX];
	int[][] checkFoldWetDry = new int[BETS_MAX][WETDRY_MAX];
	int[][] checkBetWetDry = new int[BETS_MAX][WETDRY_MAX];
	int[][] checkCallBetWetDry = new int[BETS_MAX][WETDRY_MAX];
	int[][] betCountsStaticDynamic = new int[BETS_MAX][STATICDYNAMIC_MAX];
	int[][] foldToBetStaticDynamic = new int[BETS_MAX][STATICDYNAMIC_MAX];
	int[][] checkFoldStaticDynamic = new int[BETS_MAX][STATICDYNAMIC_MAX];
	int[][] checkBetStaticDynamic = new int[BETS_MAX][STATICDYNAMIC_MAX];
	int[][] checkCallBetStaticDynamic = new int[BETS_MAX][STATICDYNAMIC_MAX];
	int[][] betCountsPossibleDraws = new int[BETS_MAX][POSSIBLE_MAX];
	int[][] foldToBetPossibleDraws = new int[BETS_MAX][POSSIBLE_MAX];
	int[][] checkFoldPossibleDraws = new int[BETS_MAX][POSSIBLE_MAX];
	int[][] checkBetPossibleDraws = new int[BETS_MAX][POSSIBLE_MAX];
	int[][] checkCallBetPossibleDraws = new int[BETS_MAX][POSSIBLE_MAX];
	int[] c_BetHML = new int[HML_SIZE];
	int[] c_BetCombo = new int[FLOP_COMBO];
	int[] c_BetWetDry = new int[WETDRY_MAX];
	int[] c_BetStaticDynamic = new int[STATICDYNAMIC_MAX];
	int[][] c_BetDraw = new int[3][POSSIBLE_MAX]; // postflop only

	/*-**************************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	***************************************************************************************/
	void sumAllPlayers(PlayerBoardIndexes h0) {
		++numberOfInstancesAdded;
		sumAllIndex(h0);

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
		divideAllIndex();
	}

	/*- **************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	 *****************************************************************************/
	private void sumAllIndex(PlayerBoardIndexes h0) {
		add(this.betCountsHML, h0.betCountsHML);
		add(this.foldToBetHML, h0.foldToBetHML);
		add(this.checkFoldHML, h0.checkFoldHML);
		add(this.checkBetHML, h0.checkBetHML);
		add(this.checkCallBetHML, h0.checkCallBetHML);

		add(this.betCountsCombo, h0.betCountsCombo);
		add(this.foldToBetCombo, h0.foldToBetCombo);
		add(this.checkFoldCombo, h0.checkFoldCombo);
		add(this.checkBetCombo, h0.checkBetCombo);
		add(this.checkCallBet1Combo, h0.checkCallBet1Combo);

		add(this.betCountsWetDry, h0.betCountsWetDry);
		add(this.foldToBetWetDry, h0.foldToBetWetDry);
		add(this.checkFoldWetDry, h0.checkFoldWetDry);
		add(this.checkBetWetDry, h0.checkBetWetDry);
		add(this.checkCallBetWetDry, h0.checkCallBetWetDry);

		add(this.betCountsStaticDynamic, h0.betCountsStaticDynamic);
		add(this.foldToBetStaticDynamic, h0.foldToBetStaticDynamic);
		add(this.checkFoldStaticDynamic, h0.checkFoldStaticDynamic);
		add(this.checkBetStaticDynamic, h0.checkBetStaticDynamic);
		add(this.checkCallBetStaticDynamic, h0.checkCallBetStaticDynamic);

		add(this.betCountsPossibleDraws, h0.betCountsPossibleDraws);
		add(this.foldToBetPossibleDraws, h0.foldToBetPossibleDraws);
		add(this.checkFoldPossibleDraws, h0.checkFoldPossibleDraws);
		add(this.checkBetPossibleDraws, h0.checkBetPossibleDraws);
		add(this.checkCallBetPossibleDraws, h0.checkCallBetPossibleDraws);

		add(this.c_BetHML, h0.c_BetHML);
		add(this.c_BetCombo, h0.c_BetCombo);
		add(this.c_BetWetDry, h0.c_BetWetDry);
		add(this.c_BetStaticDynamic, h0.c_BetStaticDynamic);

		add(this.c_BetDraw, h0.c_BetDraw);
	}

	/*- **************************************************************************
	* Divide all elements by the number of players added together 
	 *****************************************************************************/
	private void divideAllIndex() {
		divide(this.betCountsHML);
		divide(this.foldToBetHML);
		divide(this.checkFoldHML);
		divide(this.checkBetHML);
		divide(this.checkCallBetHML);

		divide(this.betCountsCombo);
		divide(this.foldToBetCombo);
		divide(this.checkFoldCombo);
		divide(this.checkBetCombo);
		divide(this.checkCallBet1Combo);

		divide(this.betCountsWetDry);
		divide(this.foldToBetWetDry);
		divide(this.checkFoldWetDry);
		divide(this.checkBetWetDry);
		divide(this.checkCallBetWetDry);

		divide(this.betCountsStaticDynamic);
		divide(this.foldToBetStaticDynamic);
		divide(this.checkFoldStaticDynamic);
		divide(this.checkBetStaticDynamic);
		divide(this.checkCallBetStaticDynamic);

		divide(this.betCountsPossibleDraws);
		divide(this.foldToBetPossibleDraws);
		divide(this.checkFoldPossibleDraws);
		divide(this.checkBetPossibleDraws);
		divide(this.checkCallBetPossibleDraws);

		divide(this.c_BetHML);
		divide(this.c_BetCombo);
		divide(this.c_BetWetDry);
		divide(this.c_BetStaticDynamic);

		divide(this.c_BetDraw);
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
	PlayerBoardIndexes readFromFile(String path) {
		PlayerBoardIndexes r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (PlayerBoardIndexes) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

}
