package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerRanges implements java.io.Serializable, Constants {
	static final long serialVersionUID = 1234567L;

	/*- *****************************************************************************
	 * This class is part of a group of classes, all of which hold calculated data about
	 * players. The data is obtained from the Players class.
	 * 
	 * The Players class contains only data collected from parsing Hand History files
	 * in the Universal format defined by the Hand class and the Action class.
	 * There are no calculated values in Players and no other class is used to save
	 * data obtained from parsing.
	 * 
	 * The classes in this group all hold calculations only. The methods that do the 
	 * calculation are in the class with the calculated data.
	 * It is hoped that other programmers will participate in this project by taking
	 * ownership of one of these classes or by creating an all new class that will be
	 * part of this group.  Part of the group are a group of classes that report on the
	 * calculated data. One reporting class with that corresponds to the calculating class.
	 * Names of classes are consistent, PlayerRanges, and ReportPlayerRanges.
	 * 
	 * The core functions are complete. Filtering Hand History files. Parsing the filtered files
	 * and creating Universal Format files, classifying players by type and other characteristics
	 * to create list of players to be analyzed as a group, and parsing the Universal Format files
	 * to create Players objects.
	 * Most of the remaining effort will be in the calculation and reporting classes.
	 * 
	 * Each of the calculation classes has a method:
	 * DoCalculations(Players play, PlayerCharactistics char){}
	 * Calculations are done using data in the Players class and the PlayerCharactistics class.
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
	 * PlayerRanges may represent thousands or even millons of players within the same 
	 * characteristics. They all have a sumAll method and a divideAll method.
	 * sumAll(PlayersX x){} Adds all data from the x instance to this.
	 * divideAll() divides all variables by the number of instances added to the class.
	 * 
	 *  @author PEAK_ 
	***************************************************************************** */

	/*- *****************************************************************************
	 * 	This specific class calculates data about  ....
	***************************************************************************** */

	/*- ****************************************************************************** 
	 * Identification and key to HashMap
	 *********************************************************************************/
	int playerID = -1; // Key

	private static int numberOfInstancesAdded = 0;

	/*-**************************************************************************
	 * Add all elements in one other class instance to this class.
	 *****************************************************************************/
	void sumAll(Players h0) {
		++numberOfInstancesAdded;
		// this.limpOperPos = sumPos(this.limpOperPos, h0.limpOperPos);
	}

	/*-**************************************************************************
	 * Add all elements in one other class instanceclass to another this class.
	 *****************************************************************************/
	void divideAll(Players h0) {
		// this.limpOperPos = dividePos(this.limpOperPos);
	}

	/*- *****************************************************************************
	 * Sum all  - Position. Overloaded method.
	 * Arg0 - Action being added to
	 * Arg1 - Array to add
	 * Returns Arg0 with arg1 added to it
	 ***************************************************************************** */
	private int[][] sumPos(int[][] i0, int[][] i1) {
		if (i0.length != NUM_STREETS || i0[0].length != NUM_POS || i1.length != NUM_STREETS
				|| i1[0].length != NUM_POS) {
			Logger.logError(new StringBuilder().append("//sumPos array length invalid ").append(i1.length).append(" ")
					.append(i1[0]).toString());
			Crash.log("$$$");
		}
		final int[][] $ = new int[NUM_STREETS][NUM_POS];
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				$[i][j] += i0[i][j];
			}
		}
		return $;
	}

	/*- *****************************************************************************
	 *Divide all  - Position. Overloaded method.
	 * Arg0 - Action being added to
	 * Arg1 - Array to add
	 * Returns Arg0 with arg1 added to it
	 ***************************************************************************** */
	private int[][] dividePos(int[][] i0, int[][] i1) {
		if (i0.length != NUM_STREETS || i0[0].length != NUM_POS || i1.length != NUM_STREETS
				|| i1[0].length != NUM_POS) {
			Logger.logError(new StringBuilder().append("//dividePos array length invalid ").append(i1.length)
					.append(" ").append(i1[0].length).append(" ").toString());
			Crash.log("$$$");
		}
		final int[][] $ = new int[NUM_STREETS][NUM_POS];
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				$[i][j] = i0[i][j] / numberOfInstancesAdded;
			}
		}
		return $;
	}

	/*- *****************************************************************************
	 * Sum all players  - Relative Position. Overloaded method.
	 * Arg0 - Action being added to
	 * Arg1 - Array to add
	 * Returns Arg0 with arg1 added to it
	 ***************************************************************************** */
	private int[][] sumRp(int[][] i0, int[][] i1) {
		if (i0.length != NUM_STREETS || i0[0].length != NUM_RP || i1.length != NUM_STREETS || i1[0].length != NUM_RP) {
			Logger.logError(new StringBuilder().append("//sumRp array length invalid ").append(i1.length).append(" ")
					.append(i1[0].length).toString());
			Crash.log("$$$");
			return null;
		}
		final int[][] $ = new int[NUM_STREETS][NUM_RP];
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_RP; ++j) {
				$[i][j] = i0[i][j];
			}
		}
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_RP; ++j) {
				$[i][j] += i1[i][j];
			}
		}
		return $;
	}

	/*- *****************************************************************************
	 * Sum all players  - Position. Overloaded method.
	 * Arg0 - Action being added to
	 * Arg1 - Array to add
	 * Returns Arg0 with arg1 added to it
	 ***************************************************************************** */
	private double[][][] sumPos(double[][][] d0, double[][][] d1) {
		if (d0.length != NUM_STREETS || d0[0].length != NUM_POS || d0[0][0].length != NUM_ORBITS
				|| d1.length != NUM_STREETS || d1[0].length != NUM_POS || d1[0][0].length != NUM_ORBITS) {
			Logger.logError(new StringBuilder().append("//sumPos array length invalid ").append(d0.length).append(" ")
					.append(d0[0].length).append(" ").append(d0[0][0].length).append(" ").append(d1.length).append(" ")
					.append(d1[0].length).append(" ").append(d1[0][0].length).toString());
			Crash.log("$$$");
		}
		final double[][][] $ = new double[NUM_STREETS][NUM_POS][NUM_ORBITS];
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				for (int k = 0; k < NUM_ORBITS; ++k) {
					$[i][j][k] = d0[i][j][k];
				}
			}
		}
		return $;
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
	PlayerRanges readFromFile(String path) {
		PlayerRanges r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (PlayerRanges) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

}
