package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

public class PlayerMDF implements java.io.Serializable, Constants {
	static final long serialVersionUID = 1234567L;

	/*- *****************************************************************************
	 * This class is part of a group of classes, all of which hold calculated data about
	 * players. The data is obtained from the PlayerMDF class.
	 * 
	 * The PlayerMDF class contains only data collected from parsing Hand History files
	 * in the Universal format defined by the Hand class and the Action class.
	 * There are no calculated values in PlayerMDF and no other class is used to save
	 * data obtained from parsing.
	 * 
	 * The classes in this group all hold calculations only. The methods that do the 
	 * calculation are in the class with the calculated data.
	 * It is hoped that other programmers will participate in this project by taking
	 * ownership of one of these classes or by creating an all new class that will be
	 * part of this group.  Part of the group are a group of classes that report on the
	 * calculated data. One reporting class with that corresponds to the calculating class.
	 * Names of classes are consistent, PlayerMDF , and ReportPlayerMDF .
	 * 
	 * The core functions are complete. Filtering Hand History files. Parsing the filtered files
	 * and creating Universal Format files, classifying players by type and other characteristics
	 * to create list of players to be analyzed as a group, and parsing the Universal Format files
	 * to create PlayerMDF objects.
	 * Most of the remaining effort will be in the calculation and reporting classes.
	 * 
	 * Each of the calculation classes has a method:
	 * DoCalculations(PlayerMDF play, PlayerCharactistics char){}
	 * Calculations are done using data in the PlayerMDF class and the PlayerCharactistics class.
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
	 * PlayerMDF  may represent thousands or even millons of players within the same 
	 * characteristics. They all have a sumAll method and a divideAll method.
	 * sumAll(PlayerMDFX x){} Adds all data from the x instance to this.
	 * divideAll() divides all variables by the number of instances added to the class.
	 * 
	 *  @author PEAK_ 
	***************************************************************************** */

	/*- *****************************************************************************
	 * 	This specific class calculates data about  ....
	***************************************************************************** */

	/*-**********************************************************************************************
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
	 * 		action is how many times the player responded in a specific way ( fold, check, c raise, or all-in )
	 * 		count is how many times a player faces an action, a check or a bet. 
	 * 
	 * The data here is arrays of counts, actions, and arrays of the frequency calculated.
	 * There is also an arrays of the calculated MDF and the difference between calculated and actual.
	 * The difference is what we can exploit.
	 * 
	 * We use bet size in relation to pot size as one index.
	 * The index is in steps of 1/4 pot. < 1/4, 1/4 to < 1/2, 1/2 to < 3/4, ........
	  ***********************************************************************************************/
	private static int numberOfInstancesAdded = 0;
	private static int dividend = 0; // Divide by this

	/*- ****************************************************************************** 
	 * Identification and key to HashMap
	 *********************************************************************************/
	int playerID = -1; // Key
	int handsPlayed; // Number of games played

	/*-**********************************************************************************
	 * Data that is used to calculate MDF percentages.
	 * MDF is basically the percentage of times that a player folds + the percentage
	 * of times that the player continues ( call, raise, check).
	 * The sum must be 100%.
	 * 
	 * MDF is the minimum percentage of time you must call (or raise) to prevent your 
	 * opponent from profiting by always bluffing you. 
	 * If you fold more often than the MDF indicates, your opponent can exploit you by 
	 * over-bluffing when they bet.
	 * To calculate MDF is simple: pot size / (pot size + bet size) 
	* Then, multiply the answer by 100 to express it as a percentage.
	* 
	* There are arrays for recording folds and continuation.
	 ************************************************************************************************/
	int[] gamesPos = new int[NUM_STREETS];
	double[] mdfPos = new double[NUM_STREETS];
	int[] continuationPos = new int[NUM_STREETS];
	int[] continuationBet1Pos = new int[NUM_STREETS];
	int[] continuationBet2Pos = new int[NUM_STREETS];
	int[] continuationCallBet1Pos = new int[NUM_STREETS];
	int[] continuationCallBet2Pos = new int[NUM_STREETS];
	int[] continuationCheckPos = new int[NUM_STREETS];
	int[] continuationFoldToBet1Pos = new int[NUM_STREETS];
	int[] continuationFoldToBet2Pos = new int[NUM_STREETS];
	double[] foldFreqPos = new double[NUM_STREETS];
	double[] contFreqPos = new double[NUM_STREETS];
	double[] foldFreq = new double[NUM_STREETS];
	double[] contFreq = new double[NUM_STREETS];
	int[] gamesRp = new int[NUM_STREETS];
	double[] mdfRp = new double[NUM_STREETS];
	int[] continuationRp = new int[NUM_STREETS];
	int[] continuationBet1Rp = new int[NUM_STREETS];
	int[] continuationBet2Rp = new int[NUM_STREETS];
	int[] continuationCallBet1Rp = new int[NUM_STREETS];
	int[] continuationCallBet2Rp = new int[NUM_STREETS];
	int[] continuationCheckRp = new int[NUM_STREETS];
	int[] continuationFoldToBet1Rp = new int[NUM_STREETS];
	int[] continuationFoldToBet2Rp = new int[NUM_STREETS];
	double[] foldFreqRp = new double[NUM_STREETS];
	double[] contFreqRp = new double[NUM_STREETS];

	/*-**************************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	***************************************************************************************/
	void sumAllPlayers(PlayerMDF h0) {
		++numberOfInstancesAdded;
		sumAllGTO(h0);
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
		divideAllGTO();
	}

	/*- **************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	 *****************************************************************************/
	private void sumAllGTO(PlayerMDF h0) {
		add(this.gamesPos, h0.gamesPos);
		add(this.mdfPos, h0.mdfPos);
		add(this.continuationPos, h0.continuationPos);
		add(this.continuationBet1Pos, h0.continuationBet1Pos);
		add(this.continuationBet2Pos, h0.continuationBet2Pos);
		add(this.continuationCallBet1Pos, h0.continuationCallBet1Pos);
		add(this.continuationCallBet2Pos, h0.continuationCallBet2Pos);
		add(this.continuationCheckPos, h0.continuationCheckPos);
		add(this.continuationFoldToBet1Pos, h0.continuationFoldToBet1Pos);
		add(this.continuationFoldToBet2Pos, h0.continuationFoldToBet2Pos);
		add(this.foldFreqPos, h0.foldFreqPos);
		add(this.contFreqPos, h0.contFreqPos);

		add(this.gamesRp, h0.gamesRp);
		add(this.mdfRp, h0.mdfRp);
		add(this.continuationRp, h0.continuationRp);
		add(this.continuationBet1Rp, h0.continuationBet1Rp);
		add(this.continuationBet2Rp, h0.continuationBet2Rp);
		add(this.continuationCallBet1Rp, h0.continuationCallBet1Rp);
		add(this.continuationCallBet2Rp, h0.continuationCallBet2Rp);
		add(this.continuationCheckRp, h0.continuationCheckRp);
		add(this.continuationFoldToBet1Rp, h0.continuationFoldToBet1Rp);
		add(this.continuationFoldToBet2Rp, h0.continuationFoldToBet2Rp);
		add(this.foldFreqRp, h0.foldFreqRp);
		add(this.contFreqRp, h0.contFreqRp);
		add(this.foldFreq, h0.foldFreq);
		add(this.contFreq, h0.contFreq);
	}

	/*- **************************************************************************
	* Divide all elements by the number of players added together 
	 *****************************************************************************/
	private void divideAllGTO() {
		divide(this.gamesPos);
		divide(this.mdfPos);
		divide(this.continuationPos);
		divide(this.continuationBet1Pos);
		divide(this.continuationBet2Pos);
		divide(this.continuationCallBet1Pos);
		divide(this.continuationCallBet2Pos);
		divide(this.continuationCheckPos);
		divide(this.continuationFoldToBet1Pos);
		divide(this.continuationFoldToBet2Pos);
		divide(this.foldFreqPos);
		divide(this.contFreqPos);

		divide(this.gamesRp);
		divide(this.mdfRp);
		divide(this.continuationRp);
		divide(this.continuationBet1Rp);
		divide(this.continuationBet2Rp);
		divide(this.continuationCallBet1Rp);
		divide(this.continuationCallBet2Rp);
		divide(this.continuationCheckRp);
		divide(this.continuationFoldToBet1Rp);
		divide(this.continuationFoldToBet2Rp);
		divide(this.foldFreqRp);
		divide(this.contFreqRp);
		divide(this.foldFreq);
		divide(this.contFreq);
	}

	/*-**************************************************************************
	 * Add all elements in one other class instance to this class.
	 *****************************************************************************/
	void sumAll(PlayerMDF h0) {
		++numberOfInstancesAdded;
		// this.limpOperPos = sumPos(this.limpOperPos, h0.limpOperPos);
	}

	/*-**************************************************************************
	 * Add all elements in one other class instanceclass to another this class.
	 *****************************************************************************/
	void divideAll(PlayerMDF h0) {
		// this.limpOperPos = dividePos(this.limpOperPos);
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
	* Divided double array dimension 1
	****************************************************************************************/
	private void divide(double[] dividend) {
		for (int i = 0; i < dividend.length; i++) {
			dividend[i] = dividend[i] / numberOfInstancesAdded;
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
	PlayerMDF readFromFile(String path) {
		PlayerMDF r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (PlayerMDF) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

}
