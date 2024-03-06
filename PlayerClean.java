package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

public class PlayerClean implements java.io.Serializable, Constants {
	static final long serialVersionUID = 1234567L;

	/*- *****************************************************************************
	 * This class is part of a group of classes, all of which hold calculated data about
	 * players. The data is obtained from the PlayerClean class.
	 * 
	 * The PlayerClean class contains only data collected from parsing Hand History files
	 * in the Universal format defined by the Hand class and the Action class.
	 * There are no calculated values in PlayerClean and no other class is used to save
	 * data obtained from parsing.
	 * 
	 * The classes in this group all hold calculations only. The methods that do the 
	 * calculation are in the class with the calculated data.
	 * It is hoped that other programmers will participate in this project by taking
	 * ownership of one of these classes or by creating an all new class that will be
	 * part of this group.  Part of the group are a group of classes that report on the
	 * calculated data. One reporting class with that corresponds to the calculating class.
	 * Names of classes are consistent, PlayerClean, and ReportPlayerClean.
	 * 
	 * The core functions are complete. Filtering Hand History files. Parsing the filtered files
	 * and creating Universal Format files, classifying players by type and other characteristics
	 * to create list of players to be analyzed as a group, and parsing the Universal Format files
	 * to create PlayerClean objects.
	 * Most of the remaining effort will be in the calculation and reporting classes.
	 * 
	 * Each of the calculation classes has a method:
	 * DoCalculations(PlayerClean play, PlayerCharactistics char){}
	 * Calculations are done using data in the PlayerClean class and the PlayerCharactistics class.
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
	 * PlayerClean may represent thousands or even millons of players within the same 
	 * characteristics. They all have a sumAll method and a divideAll method.
	 * sumAll(PlayerCleanX x){} Adds all data from the x instance to this.
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

	// Percentages - Calculated
	double[] clean1BetPer = new double[NUM_STREETS];
	double[] cleanCall1BetPer = new double[NUM_STREETS];
	double[] clean2BetPer = new double[NUM_STREETS];
	double[] cleanCall2BetPer = new double[NUM_STREETS];
	double[] clean3BetPer = new double[NUM_STREETS];
	double[] cleanCall3BetPer = new double[NUM_STREETS];
	double[] clean4BetPer = new double[NUM_STREETS];
	double[] cleanCall4BetPer = new double[NUM_STREETS];
	double[] cleanAllinPer = new double[NUM_STREETS];
	double[] cleanCallAllinPer = new double[NUM_STREETS];

	/*-**********************************************************************************************
	 * If there was a previous bet or call before this one then possible range is 
	* reduced to previous bet or call range
	* Example Hero opens 2 Bet, Villan raises to 3 bet , hero calls 3 bet --
	* possible range is Hero open.
	* So percentage calculation is of smaller range, not 1326 combos.
	* 1/4 of a 20% range is 5%, 
	* Opportunity count is misleading if previous bet.
	* Index is position.  only
	 ************************************************************************************************/
	// Action counts -
	int[] clean1BetCount = new int[NUM_STREETS];
	int[] cleanCall1BetCount = new int[NUM_STREETS];
	int[] clean2BetCount = new int[NUM_STREETS];
	int[] cleanCall2BetCount = new int[NUM_STREETS];
	int[] clean3BetCount = new int[NUM_STREETS];
	int[] cleanCall3BetCount = new int[NUM_STREETS];
	int[] clean4BetCount = new int[NUM_STREETS];
	int[] cleanCall4BetCount = new int[NUM_STREETS];
	int[] cleanAllinCount = new int[NUM_STREETS];
	int[] cleanCallAllinCount = new int[NUM_STREETS];
	// Opportunity
	int[] clean1BetOperCount = new int[NUM_STREETS]; // TODO never ref by GUIAnalyze
	int[] cleanCall1BetOperCount = new int[NUM_STREETS];
	int[] clean2BetOperCount = new int[NUM_STREETS];
	int[] cleanCall2BetOperCount = new int[NUM_STREETS];
	int[] clean3BetOperCount = new int[NUM_STREETS];
	int[] cleanCall3BetOperCount = new int[NUM_STREETS];
	int[] clean4BetOperCount = new int[NUM_STREETS];
	int[] cleanCall4BetOperCount = new int[NUM_STREETS];
	int[] cleanAllinOperCount = new int[NUM_STREETS];
	int[] cleanCallAllinOperCount = new int[NUM_STREETS];

	/*-**************************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	***************************************************************************************/
	void sumAllPlayers(PlayerClean h0) {
		++numberOfInstancesAdded;
		sumAllClean(h0);
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
		divideAllClean();
	}

	/*- **************************************************************************
	* 
	 *****************************************************************************/
	private void sumAllClean(PlayerClean h0) {
		add(this.clean1BetCount, h0.clean1BetCount);
		add(this.cleanCall1BetCount, h0.cleanCall1BetCount);
		add(this.clean2BetCount, h0.cleanCall1BetCount);
		add(this.cleanCall2BetCount, h0.cleanCall2BetCount);
		add(this.clean3BetCount, h0.clean3BetCount);
		add(this.cleanCall3BetCount, h0.cleanCall3BetCount);
		add(this.clean4BetCount, h0.clean4BetCount);
		add(this.cleanCall4BetCount, h0.cleanCall4BetCount);
		add(this.cleanAllinCount, h0.cleanAllinCount);
		add(this.cleanCallAllinCount, h0.cleanCallAllinCount);
		add(this.cleanCall2BetOperCount, h0.cleanCall2BetOperCount);
		add(this.clean3BetOperCount, h0.clean3BetOperCount);
		add(this.cleanCall3BetOperCount, h0.cleanCall3BetOperCount);
		add(this.clean4BetOperCount, h0.clean4BetOperCount);
		add(this.cleanCall4BetOperCount, h0.cleanCall4BetOperCount);
		add(this.cleanAllinOperCount, h0.cleanAllinOperCount);
		add(this.cleanCallAllinOperCount, h0.cleanCallAllinOperCount);
	}

	/*- **************************************************************************
	* Divide all elements by the number of players added together 
	 *****************************************************************************/
	private void divideAllClean() {
		divide(this.clean1BetCount);
		divide(this.cleanCall1BetCount);
		divide(this.clean2BetCount);
		divide(this.cleanCall2BetCount);
		divide(this.clean3BetCount);
		divide(this.cleanCall3BetCount);
		divide(this.clean4BetCount);
		divide(this.cleanCall4BetCount);
		divide(this.cleanAllinCount);
		divide(this.cleanCallAllinCount);
		divide(this.cleanCall2BetOperCount);
		divide(this.clean3BetOperCount);
		divide(this.cleanCall3BetOperCount);
		divide(this.clean4BetOperCount);
		divide(this.cleanCall4BetOperCount);
		divide(this.cleanAllinOperCount);
		divide(this.cleanCallAllinOperCount);
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
	PlayerClean readFromFile(String path) {
		PlayerClean r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (PlayerClean) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

}
