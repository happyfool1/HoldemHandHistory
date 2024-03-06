package holdemhandhistory;

import java.math.BigDecimal;

public class HandCalculations implements Constants {
	/*- *****************************************************************************
	 * After the parsing of one Hand History hand, this Class is used to calculate
	 * values using the action array. The calculated values are not part of the
	 * Hand Class but calculated using data in that Class, primarily from the Action
	 * array.
	 * 
	 * The calculated data is not in the Universal Hand History file. 
	 * No need to waste the space either in disk files or in memory in GUIAnalyusis. 
	 * Only one instance of the calculated data is needed and that is when the 
	 * Hand file is being processed. Data is calculated then, use to create data 
	 * structures, then discarded. 
	 * 
	 *  @author PEAK_ 
	***************************************************************************** */

	static BigDecimal[] moneyInStreet = new BigDecimal[6];

	/*- *****************************************************************************
	 * Key shared data.
	*****************************************************************************/

	static int[] foldedStreetOrLostShow = { -1, -1, -1, -1, -1, -1 };
	static int[] sawStreetOrShow = { -1, -1, -1, -1, -1, -1 };
	static int[] wonStreetOrShow = { -1, -1, -1, -1, -1, -1 };

	static int lastStreet = -1;
	static boolean showdown = false;
	static BigDecimal totalPot = zeroBD;
	static BigDecimal mainPot = zeroBD;

	/*- *****************************************************************************
	 * Calculated seating values. All starts with Hand.BUSeat at start of new hand.
	***************************************************************************** */
	static boolean[] foldArray = { false, false, false, false, false, false };
	static int foldCount = 0;
	static int[] posArray = { -1, -1, -1, -1, -1, -1 };
	static int[] rpArray = { -1, -1, -1, -1, -1, -1 };

	static int SBSeat = -1;
	static int BBSeat = -1;
	static int UTGSeat = -1;
	static int MPSeat = -1;
	static int COSeat = -1;
	static int BUSeat = -1;

	// To calculate RP
	static int firstSeat = -1;
	static int lastSeat = -1;
	static int nextSeat = -1;

	/*- *****************************************************************************
	 * Calculated values.
	***************************************************************************** */
	static BigDecimal moneyInPreflop = zeroBD;
	static BigDecimal moneyInFlop = zeroBD;
	static BigDecimal moneyInTurn = zeroBD;
	static BigDecimal moneyInRiver = zeroBD;
	static BigDecimal moneyInStreets = zeroBD;
	static BigDecimal moneyOutPreflop = zeroBD;
	static BigDecimal moneyOutFlop = zeroBD;
	static BigDecimal moneyOutTurn = zeroBD;
	static BigDecimal moneyOutRiver = zeroBD;
	static BigDecimal uncalledReturnedTo = zeroBD;
	static BigDecimal moneyOutStreets = zeroBD;
	static BigDecimal moneyOutShowdown = zeroBD;
	static BigDecimal moneyOutSummary = zeroBD;

	/*- *****************************************************************************
	 *  
	***************************************************************************** */
	static BigDecimal[] winLoose = new BigDecimal[6]; // At end of hand
	static BigDecimal[] uncalledReturnedToStreet = new BigDecimal[6];
	static BigDecimal[] collectedStreet = new BigDecimal[6];
	static BigDecimal[] wonStreet = new BigDecimal[6];
	static BigDecimal[] wonSidePotShowdown = new BigDecimal[6];
	static BigDecimal[] collectedMainPotShowdown = new BigDecimal[6];
	static BigDecimal[] collectedSidePotShowdown = new BigDecimal[6];
	static BigDecimal[] collectedPotShowdown = new BigDecimal[6];

	// Summary of actionArray - Summary
	static BigDecimal[] wonSummary = new BigDecimal[6];
	static BigDecimal[] collectedSummary = new BigDecimal[6];

	/*- *****************************************************************************
	 * Local variables
	***************************************************************************** */
	static BigDecimal temp1 = zeroBD;
	static BigDecimal temp2 = zeroBD;
	static BigDecimal temp3 = zeroBD;
	static int handCount = 0;

	/*- *************************************************************************** 
	 * Do the calculations 
	***************************************************************************** */
	static void doCalculationsStartOfHand(Hand h) {
		initialize();
		doSeatsStartOfHand(h);
		summarizeAction(h);
		finishArrays(h);
	}

	/*- *************************************************************************** 
	 * Get position
	***************************************************************************** */
	static int getPos(int seat) {
		return posArray[seat];
	}

	/*- *************************************************************************** 
	 * Get relative position
	***************************************************************************** */
	static int getRp(int seat) {
		return rpArray[seat];
	}

	/*- *************************************************************************** 
	 * Set player folded
	 * Update foldCount
	***************************************************************************** */
	static void setPlayerFolded(int seat, boolean b) {
		if (foldArray[seat] != b) {
			if (b)
				++foldCount;
			else
				--foldCount;
		}
		foldArray[seat] = b;
	}

	/*-************************************************************************************************
	 * Start of new Hand only
	 * Start with Button seat and set all others
	 * Each seat number is set using Button seat number as starting point.
	 * Three arrays are created with seat number as index.
	 *  foldedArrayArray is created.  No player  foldedArray.
	 * posArray is created. Value is player position ( SB,BB,UTG,MP,CO,BU )  
	 * rpArray is created. Value is player relative position ( FIRST, MP4,MP3,MP2,MP1,LAST)  
	 *  		If heads up FIRST_HU and LAST_HU are relative positions.	
	************************************************************************************************ */
	private static void doSeatsStartOfHand(Hand h) {
		BUSeat = h.BUSeat;
		SBSeat = BUSeat + 1;
		if (BUSeat == 5) {
			SBSeat = 0;
		}
		BBSeat = SBSeat + 1;
		if (SBSeat == 5) {
			BBSeat = 0;
		}
		UTGSeat = BBSeat + 1;
		if (BBSeat == 5) {
			UTGSeat = 0;
		}
		MPSeat = UTGSeat + 1;
		if (UTGSeat == 5) {
			MPSeat = 0;
		}
		COSeat = MPSeat + 1;
		if (MPSeat == 5) {
			COSeat = 0;
		}

		posArray[SBSeat] = SB;
		posArray[BBSeat] = BB;
		posArray[UTGSeat] = UTG;
		posArray[MPSeat] = MP;
		posArray[COSeat] = CO;
		posArray[BUSeat] = BU;

		rpArray[SBSeat] = RP_FIRST;
		rpArray[BBSeat] = RP_MIDDLE4;
		rpArray[UTGSeat] = RP_MIDDLE3;
		rpArray[MPSeat] = RP_MIDDLE2;
		rpArray[COSeat] = RP_MIDDLE1;
		rpArray[BUSeat] = RP_LAST;
	}

	/*-*************************************************************************************
	 * A player has  folded. 
	 * Set  foldArray seat true, update relative positions. 
	 *Arg0 - Seat number
	 **************************************************************************************/
	static void playerFoldedUpdateRp(int seat) {
		foldArray[seat] = true;
		rpArray[seat] = -1;
		posArray[seat] = -2;
		if (++foldCount == 5)
			return;

		// Find first and last seat
		lastSeat = BUSeat;
		for (int i = 0; i < 5; ++i) {
			if (!foldArray[lastSeat]) {
				break;
			}
			--lastSeat;
			if (lastSeat < 0) {
				lastSeat = 5;
			}
		}
		firstSeat = SBSeat;
		for (int i = 0; i < 5; ++i) {
			if (!foldArray[firstSeat]) {
				break;
			}
			++firstSeat;
			if (firstSeat > 5) {
				firstSeat = 0;
			}
		}

		if (foldCount == 4) {
			rpArray[firstSeat] = RP_FIRSTHU;
			rpArray[lastSeat] = RP_LASTHU;
			return;
		}

		rpArray[firstSeat] = RP_FIRST;
		rpArray[lastSeat] = RP_LAST;
		nextSeat = firstSeat;
		int x = RP_MIDDLE2;
		if (foldCount == 1)
			x = RP_MIDDLE3;
		for (int i = 0; i < 4; ++i) {
			if (++nextSeat > 5)
				nextSeat = 0;
			if (!foldArray[nextSeat]) {
				if (foldCount == 3) {
					rpArray[nextSeat] = RP_MIDDLE1;
					return;
				}
				rpArray[nextSeat] = x++;
			}
		}
	}

	/*- *************************************************************************** 
	 * Initialize
	***************************************************************************** */
	private static void initialize() {
		++handCount;
		foldCount = 0;
		moneyInPreflop = zeroBD;
		moneyInFlop = zeroBD;
		moneyInTurn = zeroBD;
		moneyInRiver = zeroBD;
		moneyInStreets = zeroBD;
		moneyOutPreflop = zeroBD;
		moneyOutFlop = zeroBD;
		moneyOutTurn = zeroBD;
		moneyOutRiver = zeroBD;
		uncalledReturnedTo = zeroBD;
		moneyOutStreets = zeroBD;
		moneyOutShowdown = zeroBD;
		moneyOutSummary = zeroBD;

		for (int i = 0; i < PLAYERS; i++) {
			winLoose[i] = zeroBD;
			uncalledReturnedToStreet[i] = zeroBD;
			collectedStreet[i] = zeroBD;
			wonStreet[i] = zeroBD;
			wonSidePotShowdown[i] = zeroBD;
			collectedMainPotShowdown[i] = zeroBD;
			collectedSidePotShowdown[i] = zeroBD;
			collectedPotShowdown[i] = zeroBD;
			wonSummary[i] = zeroBD;
			collectedSummary[i] = zeroBD;
			foldArray[i] = false;
		}
		temp1 = zeroBD;
		temp2 = zeroBD;
		temp3 = zeroBD;
	}

	/*-*****************************************************************************
	 * Finish the data in Hand.
	 * Step through actionArray and 
	 *  Values -1 none, 0 PREFLOP, 1 FLOP, 2 TURN, 3 RIVER, 4 SHOWDOWN
	 *		int[]  foldedArrayStreetOrLostShow = { -1, -1, -1, -1, -1, -1 };
	 *		int[] sawStreetOrShow = { -1, -1, -1, -1, -1, -1 };
	 *		int[] wonStreetOrShow = { -1, -1, -1, -1, -1, -1 };
	 ******************************************************************************/
	private static void finishArrays(Hand h) {
		boolean done = false;
		int ndx = 2;
		int street = 0;
		int seat = 0;
		int action = 0;
		boolean[] playerFolded = { false, false, false, false, false, false };
		while (!done && ndx < h.actionCount) {
			street = h.actionArray[ndx].street;
			seat = h.actionArray[ndx].seat;
			action = h.actionArray[ndx].action;
			sawStreetOrShow[seat] = street;
			switch (action) {
			case ACTION_FOLD -> {
				playerFolded[seat] = true;
				foldedStreetOrLostShow[seat] = street;
				break;
			}
			case ACTION_UNCALLED_RETURNED_TO -> {
				break;
			}
			case ACTION_COLLECTED_STREET -> {
				wonStreetOrShow[seat] = street;
				break;
			}
			// Show Down
			case ACTION_WON_SIDE_POT -> {
				wonStreetOrShow[seat] = street;
				break;
			}
			case ACTION_COLLECTED_SIDE_POT -> {
				wonStreetOrShow[seat] = street;
				break;
			}
			case ACTION_COLLECTED_MAIN_POT -> {
				wonStreetOrShow[seat] = street;
				break;
			}
			case ACTION_COLLECTED_POT -> {
				wonStreetOrShow[seat] = street;
				break;
			}
			default -> {
			}
			}
			++ndx;
		}
	}

	/*-*****************************************************************************
	 * Summarize action array
	 * Called by ParsePS for summary
	 * Totals are done for streets and Showdown
	 * Mostly about money - money collected, returned, etc..
	 * errorCheck() will be called by ParsePS to check that sums are correct
	 * and match money from individual streets.
	 ******************************************************************************/
	private static void summarizeAction(Hand h) {
		for (int i = 0; i < h.actionCount; ++i) {
			if (h.actionArray[i].street <= lastStreet) {
				if (h.actionArray[i].action >= ACTION_CALL && h.actionArray[i].action <= ACTION_POST) {
					moneyInStreet[h.actionArray[i].seat] = moneyInStreet[h.actionArray[i].seat]
							.add(h.actionArray[i].money);
					if (h.handID == 0L) {
						Logger.log(new StringBuilder().append("//ACTION_CALL  and  ACTION_POST ")
								.append(moneyInStreet[h.actionArray[i].seat]).append(" ").append(h.actionArray[i].money)
								.toString());
					}
					doStreetsIn(h, i);
				}
				if (h.actionArray[i].action == ACTION_UNCALLED_RETURNED_TO) {
					uncalledReturnedToStreet[h.actionArray[i].seat] = uncalledReturnedToStreet[h.actionArray[i].seat]
							.add(h.actionArray[i].money);
					uncalledReturnedTo = uncalledReturnedTo.add(h.actionArray[i].money);
					if (h.handID == 0L) {
						Logger.log(new StringBuilder().append("//ACTION_UNCALLED_RETURNED_TO ")
								.append(uncalledReturnedToStreet[h.actionArray[i].seat]).append(" ")
								.append(h.actionArray[i].money).toString());
					}
					doStreetsOut(h, i);
				}
				if (h.actionArray[i].action == ACTION_COLLECTED_STREET) {
					collectedStreet[h.actionArray[i].seat] = collectedStreet[h.actionArray[i].seat]
							.add(h.actionArray[i].money);
					if (h.handID == 0L) {
						Logger.log(new StringBuilder().append("//col ").append(collectedStreet[h.actionArray[i].seat])
								.append(" ").append(h.actionArray[i].money).toString());
					}
					doStreetsOut(h, i);
				}
				if (h.actionArray[i].action == ACTION_WON) {
					wonStreet[h.actionArray[i].seat] = wonStreet[h.actionArray[i].seat].add(h.actionArray[i].money);
					if (h.handID == 0L) {
						Logger.log(new StringBuilder().append("// ACTION_WON ").append(wonStreet[h.actionArray[i].seat])
								.append(" ").append(h.actionArray[i].money).toString());
					}
					doStreetsOut(h, i);
				}
			}

			if (h.actionArray[i].street == SHOWDOWN) {
				if (h.actionArray[i].action == ACTION_WON_SIDE_POT) {
					wonSidePotShowdown[h.actionArray[i].seat] = wonSidePotShowdown[h.actionArray[i].seat]
							.add(h.actionArray[i].money);
					moneyOutShowdown = moneyOutShowdown.add(h.actionArray[i].money);
				}
				if (h.actionArray[i].action == ACTION_COLLECTED_SIDE_POT) {
					collectedSidePotShowdown[h.actionArray[i].seat] = collectedSidePotShowdown[h.actionArray[i].seat]
							.add(h.actionArray[i].money);
					moneyOutShowdown = moneyOutShowdown.add(h.actionArray[i].money);
				}
				if (h.actionArray[i].action == ACTION_COLLECTED_MAIN_POT) {
					collectedMainPotShowdown[h.actionArray[i].seat] = collectedMainPotShowdown[h.actionArray[i].seat]
							.add(h.actionArray[i].money);
					moneyOutShowdown = moneyOutShowdown.add(h.actionArray[i].money);
				}
				if (h.actionArray[i].action == ACTION_COLLECTED_POT) {
					collectedPotShowdown[h.actionArray[i].seat] = collectedPotShowdown[h.actionArray[i].seat]
							.add(h.actionArray[i].money);
					moneyOutShowdown = moneyOutShowdown.add(h.actionArray[i].money);
				}
			}

			if (h.actionArray[i].street == SUMMARY) {
				if (h.actionArray[i].action == ACTION_WON) {
					wonSummary[h.actionArray[i].seat] = wonSummary[h.actionArray[i].seat].add(h.actionArray[i].money);
					moneyOutSummary = moneyOutSummary.add(h.actionArray[i].money);
				}
				if (h.actionArray[i].action == ACTION_COLLECTED_SUMMARY) {
					collectedSummary[h.actionArray[i].seat] = collectedSummary[h.actionArray[i].seat]
							.add(h.actionArray[i].money);
					moneyOutSummary = moneyOutSummary.add(h.actionArray[i].money);
				}
			}
		}
	}

	/*-*****************************************************************************
	 *  Helper method
	 *  Does one row in action array
	 *  Calculates money in for a street. 
	 *  Adds the money column to  total for a street.
	 *  Arg0 0 row
	 ******************************************************************************/
	private static void doStreetsIn(Hand h, int i) {
		if (h.actionArray[i].street == PREFLOP) {
			moneyInPreflop = moneyInPreflop.add(h.actionArray[i].money);
		} else if (h.actionArray[i].street == FLOP) {
			moneyInFlop = moneyInFlop.add(h.actionArray[i].money);
		} else if (h.actionArray[i].street == TURN) {
			moneyInTurn = moneyInTurn.add(h.actionArray[i].money);
		} else if (h.actionArray[i].street == RIVER) {
			moneyInRiver = moneyInRiver.add(h.actionArray[i].money);
		}
		moneyInStreets = moneyInPreflop.add(moneyInFlop).add(moneyInTurn).add(moneyInRiver);
		if (h.handID == 0L) {
			Logger.log(new StringBuilder().append("//ins ").append(moneyInStreets).append(" ")
					.append(h.actionArray[i].money).toString());
		}
	}

	/*-*****************************************************************************
	 *  Helper method
	 ******************************************************************************/
	private static void doStreetsOut(Hand h, int i) {
		if (h.actionArray[i].street == PREFLOP) {
			moneyOutPreflop = moneyOutPreflop.add(h.actionArray[i].money);
		} else if (h.actionArray[i].street == FLOP) {
			moneyOutFlop = moneyOutFlop.add(h.actionArray[i].money);
		} else if (h.actionArray[i].street == TURN) {
			moneyOutTurn = moneyOutTurn.add(h.actionArray[i].money);
		} else if (h.actionArray[i].street == RIVER) {
			moneyOutRiver = moneyOutRiver.add(h.actionArray[i].money);
		}
		moneyOutStreets = moneyOutPreflop.add(moneyOutFlop).add(moneyOutTurn).add(moneyOutRiver);
		if (h.handID == 0L) {
			Logger.log(new StringBuilder().append(" ").append(moneyOutStreets).append(" ")
					.append(h.actionArray[i].money).toString());
		}
	}

	/*-*****************************************************************************
	* Print initial data
	 ******************************************************************************/
	private static void printHandStart(Hand h) {
		Logger.log(new StringBuilder().append("// ").append("handID ").append(h.handID).append(" BUSeat ")
				.append(h.BUSeat).toString());
		Logger.log(new StringBuilder().append("// ").append(" SBBet ").append(h.SBBet).append(" BBBet ").append(h.BBBet)
				.toString());
		Logger.log(new StringBuilder().append("// Stacks ").append(h.stack[0]).append(" \t\t").append(h.stack[1])
				.append(" \t\t").append(h.stack[2]).append(" \t\t").append(h.stack[3]).append(" \t\t")
				.append(h.stack[4]).append(" \t\t ").append(h.stack[5]).toString());
		Logger.log(new StringBuilder().append("// Final Stacks ").append(h.finalStack[0]).append(" \t\t")
				.append(h.finalStack[1]).append(" \t\t").append(h.finalStack[2]).append(" \t\t").append(h.stack[3])
				.append(" \t\t").append(h.finalStack[4]).append(" \t\t ").append(h.finalStack[5]).toString());
	}

}
