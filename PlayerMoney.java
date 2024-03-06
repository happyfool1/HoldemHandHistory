package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PlayerMoney implements java.io.Serializable, Constants {
	static final long serialVersionUID = 1234567L;

	PlayerMoney() {
		setScale();
	}

	/*- *****************************************************************************
	 * This class is part of a group of classes, all of which hold calculated data about
	 * players. The data is obtained from the PlayerMoney class.
	 * 
	 * The PlayerMoney class contains only data collected from parsing Hand History files
	 * in the Universal format defined by the Hand class and the Action class.
	 * There are no calculated values in PlayerMoney and no other class is used to save
	 * data obtained from parsing.
	 * 
	 * The classes in this group all hold calculations only. The methods that do the 
	 * calculation are in the class with the calculated data.
	 * It is hoped that other programmers will participate in this project by taking
	 * ownership of one of these classes or by creating an all new class that will be
	 * part of this group.  Part of the group are a group of classes that report on the
	 * calculated data. One reporting class with that corresponds to the calculating class.
	 * Names of classes are consistent, PlayerMoney, and ReportPlayerMoney.
	 * 
	 * The core functions are complete. Filtering Hand History files. Parsing the filtered files
	 * and creating Universal Format files, classifying players by type and other characteristics
	 * to create list of players to be analyzed as a group, and parsing the Universal Format files
	 * to create PlayerMoney objects.
	 * Most of the remaining effort will be in the calculation and reporting classes.
	 * 
	 * Each of the calculation classes has a method:
	 * DoCalculations(PlayerMoney play, PlayerCharactistics char){}
	 * Calculations are done using data in the PlayerMoney class and the PlayerCharactistics class.
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
	 * PlayerMoney may represent thousands or even millons of players within the same 
	 * characteristics. They all have a sumAll method and a divideAll method.
	 * sumAll(PlayerMoneyX x){} Adds all data from the x instance to this.
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
	 * Money - uses HALF_EVEN rounding mode - bankers rules
	 ************************************************************************************************/
	BigDecimal stack$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	BigDecimal averageStack$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // TODO
	BigDecimal wonShowdown$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);// TODO
	BigDecimal stack$Current = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);// TODO
	BigDecimal stack$Previous = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);// TODO
	BigDecimal winnings$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // TODO
	BigDecimal won$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);

	BigDecimal[] stack = { zeroBD, zeroBD, zeroBD, zeroBD };
	BigDecimal[][] raisePos$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] betPos$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] callPos$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] raiseRp$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] betRp$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] callRp$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] minBetPos$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] minBetRp$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] bet1PosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] callBet1PosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] bet2PosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] callBet2PosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] bet3PosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] callBet3PosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] bet4PosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] callBet4PosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] allinPosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] callAllinPosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] cBetPosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];// TODO
	BigDecimal[][] barrelPosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];// TODO
	BigDecimal[][] bet1RpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] callBet1RpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] bet2RpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] callBet2RpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] bet3RpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] callBet3RpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] bet4RpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] callBet4RpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] allinRpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] callAllinRpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];
	BigDecimal[][] cBetRpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];// TODO
	BigDecimal[][] barrelRpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];// TODO
	BigDecimal[][] potOddsPosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];
	BigDecimal[][] SPRPosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];// TODO
	// What will this be used for? TODO
	BigDecimal[][] potPosBD$ = new BigDecimal[NUM_STREETS][NUM_POS];// TODO
	BigDecimal[][] potRpBD$ = new BigDecimal[NUM_STREETS][NUM_RP];// TODO

	BigDecimal[] potAtEndOfStreetBD$ = new BigDecimal[NUM_STREETS];// TODO
	BigDecimal potBD$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	BigDecimal rakeBD$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // TODO
	BigDecimal bet3BD$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // TODO
	BigDecimal callBet3BD$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // TODO
	BigDecimal raiseSizeBD$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // TODO
	BigDecimal betSizeBD$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // TODO
	BigDecimal[] potSeatBD$ = new BigDecimal[PLAYERS];
	BigDecimal[] rakeSeatBD$ = new BigDecimal[PLAYERS];

	/*-***********************************************************************************************
	 * Set BigDecimal variables rounding mode to HALF_EVEN  bankers mode for currency.
	 * Called only by constructor.
	 * Sets all BigDecimal arrays to ZeroBD. Rounding 2 decimal places using bankers rounding
	 ************************************************************************************************/
	private void setScale() {
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				this.bet1PosBD$[i][j] = zeroBD;
				this.callBet1PosBD$[i][j] = zeroBD;
				this.bet2PosBD$[i][j] = zeroBD;
				this.callBet2PosBD$[i][j] = zeroBD;
				this.bet3PosBD$[i][j] = zeroBD;
				this.callBet3PosBD$[i][j] = zeroBD;
				this.bet4PosBD$[i][j] = zeroBD;
				this.callBet4PosBD$[i][j] = zeroBD;
				this.allinPosBD$[i][j] = zeroBD;
				this.callAllinPosBD$[i][j] = zeroBD;
				this.cBetPosBD$[i][j] = zeroBD;
				this.barrelPosBD$[i][j] = zeroBD;
				this.potOddsPosBD$[i][j] = zeroBD;
				this.SPRPosBD$[i][j] = zeroBD;
				this.potPosBD$[i][j] = zeroBD;
				this.raisePos$[i][j] = zeroBD;
				this.betPos$[i][j] = zeroBD;
				this.callPos$[i][j] = zeroBD;
				this.minBetPos$[i][j] = zeroBD;
			}
			this.potAtEndOfStreetBD$[i] = zeroBD;
		}

		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_RP; ++j) {
				this.bet1RpBD$[i][j] = zeroBD;
				this.callBet1RpBD$[i][j] = zeroBD;
				this.bet2RpBD$[i][j] = zeroBD;
				this.callBet2RpBD$[i][j] = zeroBD;
				this.bet3RpBD$[i][j] = zeroBD;
				this.callBet3RpBD$[i][j] = zeroBD;
				this.bet4RpBD$[i][j] = zeroBD;
				this.callBet4RpBD$[i][j] = zeroBD;
				this.allinRpBD$[i][j] = zeroBD;
				this.callAllinRpBD$[i][j] = zeroBD;
				this.cBetRpBD$[i][j] = zeroBD;
				this.barrelRpBD$[i][j] = zeroBD;
				this.potRpBD$[i][j] = zeroBD;
				this.raiseRp$[i][j] = zeroBD;
				this.betRp$[i][j] = zeroBD;
				this.callRp$[i][j] = zeroBD;
				this.minBetRp$[i][j] = zeroBD;
			}
		}

		for (int i = 0; i < PLAYERS; ++i) {
			this.potSeatBD$[i] = zeroBD;
			this.rakeSeatBD$[i] = zeroBD;
		}
	}

	/*-**************************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	***************************************************************************************/
	void sumAllPlayer(PlayerMoney h0) {
		++numberOfInstancesAdded;
		sumAllMoney(h0);
	}

	/*-**************************************************************************************
	 * Divide all elements in this class or composite class by number of players added.
	 * Apply's only to a composite class, where multiple players have been added together.
	***************************************************************************************/
	void divideAllByPlayerAdded() {
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
		divideAllMoney();
	}

	/*- **************************************************************************
	 * Add all elements in one class to another this class.
	 * Allows multiple individual players to be added to a group
	 *****************************************************************************/
	private void sumAllMoney(PlayerMoney h0) {
		add(this.stack, h0.stack);
		add(this.stack$, h0.stack$);
		add(this.averageStack$, h0.averageStack$);
		add(this.wonShowdown$, h0.wonShowdown$);
		add(this.stack$Current, h0.stack$Current);
		add(this.stack$Previous, h0.stack$Previous);
		add(this.winnings$, h0.winnings$);
		add(this.won$, h0.won$);

		add(this.raisePos$, h0.raisePos$);
		add(this.betPos$, h0.betPos$);
		add(this.callPos$, h0.callPos$);
		add(this.raiseRp$, h0.raiseRp$);
		add(this.betRp$, h0.betRp$);
		add(this.callRp$, h0.callRp$);
		add(this.minBetPos$, h0.minBetPos$);
		add(this.minBetRp$, h0.minBetRp$);
		add(this.bet1PosBD$, h0.bet1PosBD$);
		add(this.callBet1PosBD$, h0.callBet1PosBD$);
		add(this.bet2PosBD$, h0.bet2PosBD$);
		add(this.callBet2PosBD$, h0.callBet2PosBD$);
		add(this.bet3PosBD$, h0.bet3PosBD$);
		add(this.callBet3PosBD$, h0.callBet3PosBD$);
		add(this.bet4PosBD$, h0.bet4PosBD$);
		add(this.callBet4PosBD$, h0.callBet4PosBD$);
		add(this.allinPosBD$, h0.allinPosBD$);
		add(this.callAllinPosBD$, h0.callAllinPosBD$);
		add(this.cBetPosBD$, h0.cBetPosBD$);
		add(this.barrelPosBD$, h0.barrelPosBD$);
		add(this.bet1RpBD$, h0.bet1RpBD$);
		add(this.callBet1RpBD$, h0.callBet1RpBD$);
		add(this.bet2RpBD$, h0.bet2RpBD$);
		add(this.callBet2RpBD$, h0.callBet2RpBD$);
		add(this.bet3RpBD$, h0.bet3RpBD$);
		add(this.callBet3RpBD$, h0.callBet3RpBD$);
		add(this.bet4RpBD$, h0.bet4RpBD$);
		add(this.callBet4RpBD$, h0.callBet4RpBD$);
		add(this.allinRpBD$, h0.allinRpBD$);
		add(this.callAllinRpBD$, h0.callAllinRpBD$);
		add(this.cBetRpBD$, h0.cBetRpBD$);
		add(this.barrelRpBD$, h0.barrelRpBD$);
		add(this.potOddsPosBD$, h0.potOddsPosBD$);
		add(this.SPRPosBD$, h0.SPRPosBD$);
		add(this.potPosBD$, h0.potPosBD$);
		add(this.potRpBD$, h0.potRpBD$);
		add(this.potAtEndOfStreetBD$, h0.potAtEndOfStreetBD$);
		add(this.potBD$, h0.potBD$);
		add(this.rakeBD$, h0.rakeBD$);
		add(this.bet3BD$, h0.bet3BD$);
		add(this.callBet3BD$, h0.callBet3BD$);
		add(this.raiseSizeBD$, h0.raiseSizeBD$);
		add(this.betSizeBD$, h0.betSizeBD$);
		add(this.potSeatBD$, h0.potSeatBD$);
		add(this.rakeSeatBD$, h0.rakeSeatBD$);
	}

	/*- **************************************************************************
	* Divide all elements by the number of players added together 
	 *****************************************************************************/
	private void divideAllMoney() {
		divide(this.stack);
		divide(this.stack$);
		divide(this.averageStack$);
		divide(this.wonShowdown$);
		divide(this.stack$Current);
		divide(this.stack$Previous);
		divide(this.winnings$);
		divide(this.won$);

		divide(this.raisePos$);
		divide(this.betPos$);
		divide(this.callPos$);
		divide(this.raiseRp$);

		divide(this.callRp$);
		divide(this.minBetPos$);
		divide(this.minBetRp$);
		divide(this.bet1PosBD$);
		divide(this.callBet1PosBD$);
		divide(this.bet2PosBD$);
		divide(this.callBet2PosBD$);
		divide(this.bet3PosBD$);
		divide(this.callBet3PosBD$);
		divide(this.bet4PosBD$);
		divide(this.callBet4PosBD$);
		divide(this.allinPosBD$);
		divide(this.callAllinPosBD$);
		divide(this.cBetPosBD$);
		divide(this.barrelPosBD$);
		divide(this.bet1RpBD$);
		divide(this.callBet1RpBD$);
		divide(this.bet2RpBD$);
		divide(this.callBet2RpBD$);
		divide(this.bet3RpBD$);
		divide(this.callBet3RpBD$);
		divide(this.bet4RpBD$);
		divide(this.callBet4RpBD$);
		divide(this.allinRpBD$);
		divide(this.callAllinRpBD$);
		divide(this.cBetRpBD$);
		divide(this.barrelRpBD$);
		divide(this.potOddsPosBD$);
		divide(this.SPRPosBD$);
		divide(this.potPosBD$);
		divide(this.potRpBD$);
		divide(this.potAtEndOfStreetBD$);
		divide(this.potBD$);
		divide(this.rakeBD$);
		divide(this.bet3BD$);
		divide(this.callBet3BD$);
		divide(this.raiseSizeBD$);
		divide(this.betSizeBD$);
		divide(this.potSeatBD$);
		divide(this.rakeSeatBD$);
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
	* Add BigDecimal
	****************************************************************************************/
	private void add(BigDecimal to, BigDecimal from) {
		to = to.add(from);
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
	PlayerMoney readFromFile(String path) {
		PlayerMoney r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (PlayerMoney) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

}
