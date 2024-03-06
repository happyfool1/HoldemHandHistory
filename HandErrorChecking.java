package holdemhandhistory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class HandErrorChecking implements Constants {

	/*-*****************************************************************************
	 * Check for errors
	 * Returns false 0 if no error, error code.
	 * The reason that this method is in the Hand class is for the future 
	 * ParseXX class that will be created. Currently only ParsePS.
	 * The errors checked for are not poker site unique.
	 * Hold'em has a set of playing rules that is universal.
	 * 
	 * In most cases the check uses the principal that it's a zero sum game + rake.
	 * Money put in the pot must always equal money taken out of the pot + rake;
	 * The change is stack sizes must be the same for every player that has not folded 
	 * or gone all-in at the end of every street
	***************************************************************************** */
	static int errorCheck(Hand h) {
		HandCalculations.doCalculationsStartOfHand(h);
		var temp1 = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
		var temp2 = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
		var temp3 = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);

		// starting stacks vs final stack
		temp1 = zeroBD;
		temp2 = zeroBD;
		temp3 = zeroBD;
		for (int i = 0; i < 6; ++i) {
			temp1 = temp1.add(h.stack[i]);
			temp2 = temp2.add(h.finalStack[i]);
			// System.out.println("//stacks " + i + " stack " + h.stack[i] + " finalStack
			// " + h.finalStack[i]
			// + " diff " + (h.stack[i].subtract(h.finalStack[i])));
		}

		if (!HandCalculations.showdown) {
			temp3 = temp2.add(h.rakeNoRound);
			BigDecimal diff = temp1.subtract(temp3);
			if (!temp1.equals(temp3)) {
				Logger.logError(new StringBuilder().append(
						"//ERROR HandErrorChecking.errorCheck(Hand h) no showdown  Sum of stack, sum of finalStack + rake , not equal")
						.append("\r\n//temp1 ").append(temp1).append(" ").append(" temp3 ").append(temp3)
						.append(" difference ").append(diff).append(" temp2 ").append(temp2).append(" rakeNoRound ")
						.append(h.rakeNoRound).append("\r\n// sum stacks ").append(temp1).append(" sum final stacks  ")
						.append(temp2).append("\r\n// sum final plus rake no round ").append(temp3)
						.append(" rake no round ").append(h.rakeNoRound).append(" rakeRound ").append(h.rakeRound)
						.append(" ID ").append(h.handID).toString());
				// Crash.log("$$$");
				return ERR_START_STACKS_FINAL_STACKS;
			}
		} else {
			temp2 = temp2.add(h.rakeNoRound);
			temp3 = temp1.subtract(temp2).subtract(h.jackpotRake);

			if (!temp3.equals(zeroBD)) {
				if (isDiffGreaterThan(h, temp3)) {
					Logger.logError(new StringBuilder().append(
							"//ERROR HandErrorChecking.errorCheck(Hand h) showdown Sum of stack, sum of finalStack + rake , not equal  \r\n ")
							.append("// sum stacks ").append(temp1).append("  sun final stacks ").append(temp2)
							.append(" temp3 diff ").append(temp3).append(" rake no round ").append(h.rakeNoRound)
							.append(" rake round ").append(h.rakeRound).append(" uncalledReturnedTo ")
							.append(HandCalculations.uncalledReturnedTo).append(" ID ").append(h.handID).toString());
					Crash.log("$$$");
					return ERR_START_STACKS_FINAL_STACKS;
				}
			}
		}

		for (int i = 0; i < h.actionCount; ++i) {
			// collected street == collected summary
			if (!HandCalculations.showdown) {
				temp1 = zeroBD;
				temp2 = zeroBD;
				for (int j = 0; j < 6; ++j) {
					temp1 = temp1.add(HandCalculations.collectedStreet[j]);
					temp2 = temp2.add(HandCalculations.collectedSummary[j]);
				}
				if (!temp1.equals(temp2)) {
					Logger.logError(new StringBuilder()
							.append("//ERROR HandErrorChecking.errorCheck(Hand h) collectedStreet != collectedSummary ")
							.append(temp1).append(" ").append(temp2).append(" ID ").append(h.handID).toString());
					Crash.log("$$$");
					return ERR_COLLECTED_STREET_COLLECTED_SUMMARY;
				}
			}
			// money in must equal moneyOut
			temp1 = HandCalculations.moneyOutStreets.add(h.rakeNoRound).subtract(HandCalculations.moneyInStreets);
			final boolean condition = !HandCalculations.showdown && !temp1.equals(zeroBD);
			// ERROR HandErrorChecking.errorCheck(Hand h) Money in streets not equal
			// moneyOutStreets diff
			// 2.47 moneyInStreets 0.00 moneyOutStreets 0.00 rakeNoRound 2.47 ID
			// 1981622942555133131
			if (condition) {
				Logger.logError(new StringBuilder().append(
						"//ERROR HandErrorChecking.errorCheck(Hand h) Money in streets not equal  moneyOutStreets   diff \r\n//")
						.append(temp1).append(" moneyInStreets ").append(HandCalculations.moneyInStreets)
						.append(" moneyOutStreets ").append(HandCalculations.moneyOutStreets).append(" rakeNoRound ")
						.append(h.rakeNoRound).append(" ID ").append(h.handID).toString());
				Crash.log("$$$");
				return ERR_MONEY_IN_STREETS_MONEY_OUT_STREETS;
			}

			// money in streets - uncalled == totalPot
			if (!HandCalculations.showdown) {
				temp1 = HandCalculations.moneyInStreets.subtract(HandCalculations.uncalledReturnedTo)
						.subtract(HandCalculations.totalPot);
				if (!temp1.equals(zeroBD)) {
					Logger.logError(new StringBuilder().append(
							"//ERROR HandErrorChecking.errorCheck(Hand h) Money in streets  - uncalledReturnedTo   totalPot not equal  \r\n//temp1 ")
							.append(temp1).append(" moneyInStreets ").append(HandCalculations.moneyInStreets)
							.append(" uncalledReturnedTo ").append(HandCalculations.uncalledReturnedTo)
							.append(" totalPot ").append(HandCalculations.totalPot).append(" ID ").append(h.handID)
							.toString());
					Crash.log("$$$");
					return ERR_IN_VS_TOTAL_POT;
				}
			}

			// summaryWons should equal pot
			temp1 = HandCalculations.moneyOutSummary.add(h.rakeNoRound).subtract(HandCalculations.totalPot)
					.add(h.jackpotRake);
			if (!temp1.equals(zeroBD)) {
				if (isDiffGreaterThan(h, temp1)) {
					Logger.logError(new StringBuilder().append(
							// ERROR HandErrorChecking.errorCheck(Hand h) moneyOutSummary + rake not equal
							// totalPot -46.99 49.46
							// moneyOutSummary 0.00 Rake no round 2.47 mainPot 0.00 ID 1981622942555133131
							"//ERROR HandErrorChecking.errorCheck(Hand h)  moneyOutSummary + rake not equal totalPot ")
							.append(temp1).append(" ").append(HandCalculations.totalPot).append("     moneyOutSummary ")
							.append(HandCalculations.moneyOutSummary).append("  Rake no round ").append(h.rakeNoRound)
							.append("   mainPot ").append(HandCalculations.mainPot).append(" ID ").append(h.handID)
							.toString());
					Crash.log("$$$");
					return ERR_SUMMARY_WON_TOTAL_POT;
				}
			}

			// money in streets == moneyOutShowdown
			if (HandCalculations.showdown && HandCalculations.moneyOutShowdown.equals(temp1)) {
				Logger.logError(new StringBuilder().append(
						"//ERROR HandErrorChecking.errorCheck(Hand h) moHandErrorChecking.errorCheck(Hand h)  equal to sum of streets in ")
						.append(HandCalculations.moneyOutShowdown).append(" ").append(temp1).append(" ID ")
						.append(h.handID).toString());
				Crash.log("$$$");
				return ERR_IN_VS_SHOWDOWN;
			}

			// moneyStreets -uncalledReturnedTo == moneyOutSummary ( collected )
			if (!HandCalculations.showdown) {
				temp1 = HandCalculations.moneyInStreets.subtract(HandCalculations.uncalledReturnedTo);
				temp2 = HandCalculations.moneyOutSummary.add(h.rakeNoRound);
				if (!temp1.equals(temp2)) {
					Logger.logError(new StringBuilder().append(
							"//ERROR HandErrorChecking.errorCheck(Hand h) moneyInSummary  not equal moneyOutSummary   ")
							.append(temp1).append(" ").append(temp2).append(" ").append(HandCalculations.moneyInStreets)
							.append(" ").append(HandCalculations.moneyOutSummary).append(" ")
							.append(HandCalculations.uncalledReturnedTo).append(" ").append(h.rakeNoRound)
							.append(" ID ").append(h.handID).toString());
					Crash.log("$$$");
					return ERR_IN_VS_SUMMARY;
				}
			}
		}
		return ERR_OK;
	}

	/*-*****************************************************************************
	 * Check for errors
	 * Returns false 0 if no error, error code.
	 * The  reason that this method is in the Hand class is for the future 
	 * ParseXX class that will be created. Currently only  ParsePS.
	 * The errors checked for are not poker site unique.
	 * Hold'em has a set of playing rules that is universal.
	 * 
	 * This method is called at the end of each line. 
	 * Checking is on the math. The pot, stacks, and data in the Action line.
	 * 
	 * TODO Bypass this method after completion of all testing and debug.
	 * ( does that ever happen? 
	 ***************************************************************************** */
	static int errorCheckLine(Hand h) {
		if (HandCalculations.mainPot.compareTo(zeroBD) < 0) {
			logError(h, "main pot", HandCalculations.mainPot);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.totalPot.compareTo(zeroBD) < 0) {
			logError(h, "totalPot", HandCalculations.totalPot);
			return ERR_VALUE_IN_HAND;
		}
		if (h.sidePot1.compareTo(zeroBD) < 0) {
			logError(h, "sidePot1", h.sidePot1);
			return ERR_VALUE_IN_HAND;
		}
		if (h.rakeRound.compareTo(zeroBD) < 0) {
			logError(h, "rakeRound", h.rakeRound);
			return ERR_VALUE_IN_HAND;
		}
		if (h.rakeNoRound.compareTo(zeroBD) < 0) {
			logError(h, "rakeNoRound", h.rakeNoRound);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.moneyInPreflop.compareTo(zeroBD) < 0) {
			logError(h, "moneyInPreflop", HandCalculations.moneyInPreflop);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.moneyInFlop.compareTo(zeroBD) < 0) {
			logError(h, "moneyInFlop", HandCalculations.moneyInFlop);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.moneyInTurn.compareTo(zeroBD) < 0) {
			logError(h, "moneyInTurn", HandCalculations.moneyInTurn);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.moneyInRiver.compareTo(zeroBD) < 0) {
			logError(h, "moneyInRiver", HandCalculations.moneyInRiver);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.moneyOutPreflop.compareTo(zeroBD) < 0) {
			logError(h, "moneyOutPreflop", HandCalculations.moneyOutPreflop);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.moneyOutFlop.compareTo(zeroBD) < 0) {
			logError(h, "moneyOutFlop", HandCalculations.moneyOutFlop);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.moneyOutTurn.compareTo(zeroBD) < 0) {
			logError(h, "moneyOutTurnt", HandCalculations.moneyOutTurn);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.moneyOutRiver.compareTo(zeroBD) < 0) {
			logError(h, "moneyOutRiver", HandCalculations.moneyOutRiver);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.uncalledReturnedTo.compareTo(zeroBD) < 0) {
			logError(h, "uncalledReturnedT", HandCalculations.uncalledReturnedTo);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.moneyOutStreets.compareTo(zeroBD) < 0) {
			logError(h, "moneyOutStreets", HandCalculations.moneyOutStreets);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.moneyOutShowdown.compareTo(zeroBD) < 0) {
			logError(h, "moneyOutShowdown", HandCalculations.moneyOutShowdown);
			return ERR_VALUE_IN_HAND;
		}
		if (HandCalculations.moneyOutSummary.compareTo(zeroBD) < 0) {
			logError(h, "moneyOutSummary", HandCalculations.moneyOutSummary);
			return ERR_VALUE_IN_HAND;
		}

		for (int i = 0; i < PLAYERS; ++i) {
			if (h.stack[i].compareTo(zeroBD) < 0) {
				logError(h, "stack[i]", h.stack[i]);
				return ERR_VALUE_IN_HAND;
			}
			if (h.finalStack[i].compareTo(zeroBD) < 0) {
				logError(h, "finalStack[i]", h.finalStack[i]);
				return ERR_VALUE_IN_HAND;
			}
			if (HandCalculations.winLoose[i].compareTo(zeroBD) < 0) {
				logError(h, "winLoose[i]", HandCalculations.winLoose[i]);
				return ERR_VALUE_IN_HAND;
			}
			if (HandCalculations.uncalledReturnedToStreet[i].compareTo(zeroBD) < 0) {
				logError(h, "uncalledReturnedToStreet[i]", HandCalculations.uncalledReturnedToStreet[i]);
				return ERR_VALUE_IN_HAND;
			}
			if (HandCalculations.collectedStreet[i].compareTo(zeroBD) < 0) {
				logError(h, "collectedStreet[i]", HandCalculations.collectedStreet[i]);
				return ERR_VALUE_IN_HAND;
			}
			if (HandCalculations.wonStreet[i].compareTo(zeroBD) < 0) {
				logError(h, "wonStreet[i]", HandCalculations.wonStreet[i]);
				return ERR_VALUE_IN_HAND;
			}
			if (HandCalculations.wonSidePotShowdown[i].compareTo(zeroBD) < 0) {
				logError(h, "wonSidePotShowdown[i]", HandCalculations.wonSidePotShowdown[i]);
				return ERR_VALUE_IN_HAND;
			}
			if (HandCalculations.collectedMainPotShowdown[i].compareTo(zeroBD) < 0) {
				logError(h, "collectedMainPotShowdown[i]", HandCalculations.collectedMainPotShowdown[i]);
				return ERR_VALUE_IN_HAND;
			}
			if (HandCalculations.collectedSidePotShowdown[i].compareTo(zeroBD) < 0) {
				logError(h, "collectedSidePotShowdown[i]", HandCalculations.collectedSidePotShowdown[i]);
				return ERR_VALUE_IN_HAND;
			}
			if (HandCalculations.collectedPotShowdown[i].compareTo(zeroBD) < 0) {
				logError(h, "collectedPotShowdown[i]", HandCalculations.collectedPotShowdown[i]);
				return ERR_VALUE_IN_HAND;
			}
		}

		if (h.actionArray[h.actionCount].moneyIn.compareTo(zeroBD) < 0) {
			logError(h, ".actionArray[h.actionCount].moneyIn", h.actionArray[h.actionCount].moneyIn);
			return ERR_VALUE_IN_HAND;
		}
		if (h.actionArray[h.actionCount].call.compareTo(zeroBD) < 0) {
			logError(h, "actionArray[h.actionCount].call", h.actionArray[h.actionCount].call);
			return ERR_VALUE_IN_HAND;
		}
		if (h.actionArray[h.actionCount].betRaise.compareTo(zeroBD) < 0) {
			logError(h, "actionArray[h.actionCount].betRaise", h.actionArray[h.actionCount].betRaise);
			return ERR_VALUE_IN_HAND;
		}
		if (h.actionArray[h.actionCount].raiseTo.compareTo(zeroBD) < 0) {
			logError(h, "actionArray[h.actionCount].raiseTo", h.actionArray[h.actionCount].raiseTo);
			return ERR_VALUE_IN_HAND;
		}
		return 0;
	}

	/*-*****************************************************************************
	* Helper method to see if difference is greater than 1
	* In many GGPoker Hand History files there is an error in  the final stack
	* of the winning player. An error, or an expense that I do not understand.
	* Only in hands that have the Jackpot option and Bingo option in the 
	* Hand History line SUMMARY line  
	* Total pot $36 | Rake $1.8 | Jackpot $0 | Bingo $0
	* The winning players stack is short a small amount, less than 1
	* This method checks to see if difference is less than quarter. +1 or -1
	* Arg0 - Value to check for 1. range.
	* Returns true if difference is >  
	******************************************************************************/
	private static boolean isDiffGreaterThan(Hand h, BigDecimal diff) {
		if (h.source != GGPOKER) {
			if (diff.equals(zeroBD))
				return true;
			else
				return false;
		}
		var d = new BigDecimal(1.0).setScale(2, RoundingMode.HALF_EVEN);
		var plus = new BigDecimal(1.0).setScale(2, RoundingMode.HALF_EVEN);
		var minus = new BigDecimal(-1.0).setScale(2, RoundingMode.HALF_EVEN);
		d = diff;
		d = d.subtract(h.bingoRake);
		h.unknownRake = h.unknownRake.add(d);
		if (d.compareTo(zeroBD) < 0) {
			if (d.compareTo(minus) < 0) {
				System.out.println("//MinusTrue " + diff);
				return true;
			} else {
				System.out.println("//MinusFalse " + diff);
				return false;
			}
		} else {
			if (d.compareTo(plus) > 0) {
				System.out.println("//PlusTrue " + diff);
				return true;
			} else {
				System.out.println("//PlusFalse " + diff);
				return false;
			}
		}
	}

	/*-*****************************************************************************
	 * Helper method to display  errors
	 ******************************************************************************/
	private static void logError(Hand h, String msg, BigDecimal value) {
		Logger.logError(new StringBuilder().append("//ERROR Hand.errorCheckLine() ").append(msg).append(" value ")
				.append(value).append(" handID ").append(h.handID).toString());
	}

}
