package holdemhandhistory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*- ******************************************************************************
 * This Class is unique to the  PokerStars  Hand History format.
 * This output is a generic hand. Not for any specific website or Hand History format.
 * This will allow for  and other Classes to analyze hands without having
 * poker site specific data.
 * 
 * There is one Class now, HandFromPokerStars that will create instances 
 * of this Class. In the future, other classes will be added for other poker websites.
 * 
 * Each poker website has a unique format for their Hand History files.
 * This Class hides all of that and is generic to the game of 6-max no limit hold'em.
 * 
 * The rules for Texas Holdem are the same for every website.
 * This makes it possible for this class to  exist.
 * 
 * For example:
 * 		Players have names, are assigned to seat numbers, have stacks, .......
 * 		A method like String getPlayerName(int seat) is not unique too any website.
 * 
 * Public Methods:
 *		printHand		 	Prints this hand
 * 		printDetails		Prints static information. ( player names, stacks, .... )
 * 		printActions		Prints player actions for every street
 * 
 ****************************************************************************** */

/*- *************************************************************************************************

The jackpot fees in GGPoker No-Limit Hold'em 6-max hand history files are not explicitly shown 
as a separate entry like the regular rake. 
However, they are factored into the overall "Rake" figure you see in the hand history. 
Here's how to understand it:

How Jackpot Fees are Included in Rake:

GGPoker charges a standard rake on all cash game pots, typically starting at 3% and capping at 5%.
For cash game pots exceeding 30 big blinds, an additional 1 big blind is taken for the Bad Beat Jackpot. 
This is where the jackpot fee gets incorporated into the rake.
So, in a pot where the total rake and jackpot fee apply, the hand history will just show the combined
 amount under the "Rake" field.
Identifying Jackpot Fee Presence:

You can identify if a jackpot fee was taken by checking the pot size and comparing it to the rake amount.
 If the pot size is greater than 30 big blinds and the rake amount is more than the usual rake percentage 
 for that stake, the difference is likely the jackpot fee.
For example, in a $1/$2 game (big blind = $2), a pot exceeding $60 (30 big blinds) with a rake of $4 
is likely to include a $2 jackpot fee in addition to the regular $2 rake.
Tools for Further Analysis:

Some poker tracking software like Hold'em Manager and PokerTracker can parse GGPoker hand histories 
and separate the jackpot fee from the regular rake. This can be helpful for analyzing your rakeback 
or understanding the impact of the jackpot fee on your game profitability.
GGPoker's official rake structure page doesn't explicitly mention the inclusion of jackpot fees
 in the rake calculation. 
However, you can find various forum discussions and community resources explaining this practice.
Remember, although the jackpot fee adds to the overall rake taken from the pot, it contributes to the 
Bad Beat Jackpot pool, which can potentially result in significant payouts for players who experience 
certain unlucky beats.


In No-Limit Hold'em $1/$2 6-max games on GGPoker, the rake structure is a variable percentage 
capped at a maximum amount, with an additional jackpot fee taken on larger pots. 
Here's the breakdown:

Regular Rake:

Starts at 3% of the pot and increases as the pot grows.
Capped at 5% of the pot with a maximum rake of $5.
Jackpot Fee:

An additional 1 big blind is taken from pots exceeding 30 big blinds.
In $1/$2 games, this translates to a $2 jackpot fee on pots larger than $60.
Combined Fee:

On smaller pots (less than $60), the rake will be solely determined by the percentage calculation, 
ranging from 3% to 5% with a maximum of $5.
On larger pots (over $60), the total fee will be the sum of the regular rake and the jackpot fee.
Here's an example:

Pot size: $80
Regular rake (5%): $4
Jackpot fee (1 big blind): $2
Total fee: $4 + $2 = $6
Important Notes:

The 3% starting point for the rake may vary depending on the specific table or promotion running at the time.
GGPoker does not explicitly separate the jackpot fee from the regular rake in its hand history files.
Poker tracking software like Hold'em Manager and PokerTracker can help further analyze rake and jackpot fees.


Bingo on GGPoker No-Limit Hold'em $1/$2 6-max games adds another layer to the fee structure. Here's how it works:

Bingo Fee:

When "All-In or Fold" tables offer bingo, an additional 0.025 to 0.05 big blinds is taken from each player per hand.
In $1/$2 games, this translates to a $0.03 to $0.05 fee per player, per hand.
Points to Remember:

Bingo fees are independent of the regular rake and jackpot fee. They are taken regardless of the pot size.
Not all "All-In or Fold" tables offer bingo. You can typically see if bingo is enabled in the table information before joining.
Only a portion of the bingo fee, 44%, contributes to the actual bingo prize pool. The rest goes to GGPoker.
Impact on Overall Fees:

With bingo enabled, the total fee per hand can include:
Regular rake
Jackpot fee (on pots exceeding $60)
Bingo fee (for each player)
Make sure to factor in all these fees when calculating your effective rake and game profitability.
Here's an example:

Pot size: $70
Regular rake (5%): $3.50
Jackpot fee (1 big blind): $2
Bingo fee (per player): $0.04
Total fee for a player: $3.50 + $2 + $0.04 = $5.54
Final Notes:

The specific bingo fee range (0.025-0.05 big blinds) might vary depending on table stakes and promotions.
GGPoker's official documentation doesn't clearly explain the allocation of bingo fees. 
Community resources and forum discussions provide more details.
************************************************************************************************* */

public class ParsePS implements Constants {

	private static Hand hand = new Hand();
	private static HandPS handPS = new HandPS();

	// Running status of acrionArray
	private static final boolean[] folded = { false, false, false, false, false, false };
	private static final boolean[] allin = { false, false, false, false, false, false };
	private static final boolean[] inactive = { false, false, false, false, false, false };
	private static boolean responsePending = false; // Bet or Raise requires a response
	private static int n = 0;
	private static int n1 = 0;
	private static int n2 = 0;
	private static int n3 = 0;
	private static int n4 = 0;
	private static final double d = 0.;
	private static boolean isAllin = false;
	private static String st = "";
	static final BigDecimal zeroBD = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	private static final BigDecimal[] stack = new BigDecimal[6];
	private static final BigDecimal[] moneyInThisStreet = new BigDecimal[6];
	private static BigDecimal pot = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	private static BigDecimal betNow = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	private static BigDecimal sidePot = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	private static BigDecimal bd = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	private static BigDecimal bd2 = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	private static BigDecimal currentBet = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	private static int foldCount = 0;
	private static int notActiveCount = 0;
	private static int seat = -1;
	private static int firstSeat = -1;
	private static int lastSeat = -1;
	private static int orbit = -1;
	private static int actionsToNextOrbit = -1;
	private static boolean isAction = false;
	private static boolean ignoreLine = false; // Ignore lines like said and dosen't show
	private static int linesIndex = 0;
	private static int actionIndex = 0;
	private static int street = 0; // Preflop, lop, Turn, River, Showdown, Summary
	private static String secondFlopBoard;
	private static String thirdFlopBoard;
	private static String fourthFlopBoard;
	private static String secondTurnBoard;
	private static String thirdTurnBoard;
	private static String fourthTurnBoard;
	private static String secondRiverBoard;
	private static String thirdRiverBoard;
	private static String fourthRiverBoard;
	private static String secondSummaryBoard;
	private static String thirdSummaryBoard;
	private static String fourthSummaryBoard;
	private static int p2;
	private static int handCount = 0;

	private static int lastStreet = -1;

	private static boolean showdown = false;

	// Constructor
	ParsePS() {

	}

	/*- *************************************************************************************
	 * Convert a PokerStars hand into a Hand instance.
	 * Arg0 - A String array of one hand in PokerStars format HandPokerStars Class
	 * Arg1 - An instance of Hand to be updated HandClass
	 * 		Passed by reference so it is just updated directly.
	 * Returns status that was set in hand. 0 = OK, otherwise code numbers defined in  ParseCodes
	 * There reject field in the Hand indicates the result. 
	************************************************************************************* */
	static int convertToHand(HandPS ps, Hand h) {
		handPS = ps;
		hand = h;
		++handCount;
		for (int i = 0; i < 6; ++i) {
			folded[i] = false;
			allin[i] = false;
			inactive[i] = false;
		}

		pot = zeroBD;
		sidePot = zeroBD;
		linesIndex = 0;
		actionIndex = 0;

		int r = checkHandID();
		if (r != ParseCodes.OK) {
			return r;
		}

		if (!beforeAction() || hand.status != ParseCodes.OK) {
			return hand.status;
		}

		HandCalculations.doCalculationsStartOfHand(hand);
		firstSeat = HandCalculations.SBSeat;
		lastSeat = hand.BUSeat;

		for (int i = 0; i < 6; ++i) {
			stack[i] = hand.stack[i];
			moneyInThisStreet[i] = zeroBD;
		}
		currentBet = zeroBD;
		betNow = zeroBD;
		doBlindsPost();

		sidePot = zeroBD;
		linesIndex = 11;
		orbit = 0;
		actionsToNextOrbit = 4;
		seat = HandCalculations.UTGSeat;
		foldCount = 0;
		notActiveCount = 0;
		street = PREFLOP;
		lastStreet = PREFLOP;
		betNow = hand.BBBet;
		while (!handPS.lines[linesIndex].startsWith("*** ")) {
			if (anyStreetPlayerAction()) {
				if (isAction && !updateOrbit()) {
					return hand.status;
				}
				if (!ignoreLine) {
					++actionIndex;
					hand.actionCount = actionIndex;
				} else {
					hand.actionArray[actionIndex] = new Action();
				}
				++linesIndex;
			} else {
				if (hand.status != ParseCodes.OK) {
					Logger.logError(new StringBuilder()
							.append("//ERROR convertToHand()  anyStreetPlayerAction Preflop returned false \r\n//")
							.append(ps.lines).append(ParseCodes.CODES_ST[hand.status]).append(" hand.status")
							.append(actionIndex).append(" linesIndex ").append(linesIndex).append(" handID ")
							.append(hand.handID).toString());
					return hand.status;
				}
			}
		}

		if (flop()) {
			betNow = zeroBD;
			currentBet = zeroBD;
			responsePending = false;
			orbit = 0;
			actionsToNextOrbit = 6 - notActiveCount;
			street = FLOP;
			lastStreet = FLOP;
			betNow = zeroBD;
			for (int i = 0; i < 6; ++i) {
				moneyInThisStreet[i] = zeroBD;
			}
			while (!handPS.lines[linesIndex].startsWith("*** ")) {
				if (anyStreetPlayerAction()) {
					if (isAction && !updateOrbit()) {
						return hand.status;
					}
					if (!ignoreLine) {
						++actionIndex;
						hand.actionCount = actionIndex;
					} else {
						hand.actionArray[actionIndex] = new Action();
					}
					++linesIndex;
				} else {
					if (hand.status != ParseCodes.OK) {
						Logger.logError(new StringBuilder()
								.append("//ERROR convertToHand()  anyStreetPlayerAction Flop returned false ")
								.append(actionIndex).append(" ").append(linesIndex).append(" ").append(hand.handID)
								.toString());
						return hand.status;
					}
				}
			}
			if (responsePending) {
				hand.status = ParseCodes.NO_RESPONSE_TO_BET_OR_RAISE;
				return hand.status;
			}
			if (turn()) {
				betNow = zeroBD;
				currentBet = zeroBD;
				responsePending = false;
				orbit = 0;
				actionsToNextOrbit = 6 - notActiveCount;
				street = TURN;
				lastStreet = TURN;
				betNow = zeroBD;
				for (int i = 0; i < 6; ++i) {
					moneyInThisStreet[i] = zeroBD;
				}
				while (!handPS.lines[linesIndex].startsWith("*** ")) {
					if (anyStreetPlayerAction()) {
						if (isAction && !updateOrbit()) {
							return hand.status;
						}
						if (!ignoreLine) {
							++actionIndex;
							hand.actionCount = actionIndex;
						} else {
							hand.actionArray[actionIndex] = new Action();
						}
						++linesIndex;
					} else {
						if (hand.status != ParseCodes.OK) {
							Logger.logError(new StringBuilder()
									.append("//ERROR convertToHand()  anyStreetPlayerAction Turn returned false ")
									.append(actionIndex).append(" ").append(linesIndex).append(" ").append(hand.handID)
									.toString());
							return hand.status;
						}
					}
				}

				if (river()) {
					currentBet = zeroBD;
					betNow = zeroBD;
					if (responsePending) {
						hand.status = ParseCodes.NO_RESPONSE_TO_BET_OR_RAISE;
						return hand.status;
					}
					responsePending = false;
					orbit = 0;
					actionsToNextOrbit = 6 - notActiveCount;
					street = RIVER;
					lastStreet = RIVER;
					betNow = zeroBD;
					for (int i = 0; i < 6; ++i) {
						moneyInThisStreet[i] = zeroBD;
					}
					while (!handPS.lines[linesIndex].startsWith("*** ")) {
						if (anyStreetPlayerAction()) {
							if (isAction && !updateOrbit()) {
								return hand.status;
							}
							if (!ignoreLine) {
								++actionIndex;
								hand.actionCount = actionIndex;
							} else {
								hand.actionArray[actionIndex] = new Action();
							}
							++linesIndex;
						} else {
							if (hand.status != ParseCodes.OK) {
								Logger.logError(new StringBuilder()
										.append("//ERROR convertToHand()  anyStreetPlayerAction River returned false ")
										.append(actionIndex).append(" ").append(linesIndex).append(" ")
										.append(hand.handID).toString());
								return hand.status;
							}
						}
					}
				}
			}
		}
		if (responsePending) {
			hand.status = ParseCodes.NO_RESPONSE_TO_BET_OR_RAISE;
			return hand.status;
		}
		final int n = handPS.lines[linesIndex].indexOf(" SHOW");
		if (n != -1) {
			street = SHOWDOWN;
			if (!doShowdown() || hand.status != ParseCodes.OK) {
				return hand.status;
			}
			hand.actionCount = actionIndex;
		}

		street = SUMMARY;
		if (!doSummary() || hand.status != ParseCodes.OK) {
			return hand.status;
		}
		hand.actionCount = actionIndex;

		// Move local copy to Hand
		for (int i = 0; i < 6; ++i) {
			hand.finalStack[i] = stack[i];
		}

		if (hand.handID == 0L) {
			HandReports.actionReport(hand);
			handPS.printHand();
			Crash.log("$$$");
		}
		int result = 0;
//	 result = HandErrorChecking.errorCheck(hand);
		if (result != 0) {
			Logger.log(new StringBuilder().append("//ERROR hand.errorCheck code ").append(ERR_ST[result]).append(" ")
					.append(hand.handID).append("ID ").append(handCount).append(" hand count ").append(handCount)
					.toString());
			HandReports.actionReport(hand);
			handPS.printHand();
			// ParseCodes.report();
			// ReportHand.handReport(hand);
			// ReportHand.handReportBySeat(hand);
			hand.status = ParseCodes.FORMAT_ERROR;
			++c;
			// if (c == 3)
			Crash.log("$$$");
			return ParseCodes.FORMAT_ERROR;
		}
		return hand.status;
	}

	private static int c = 0;

	/*-*************************************************************************************
	 * Helper method for Hand error checking of one line
	 **************************************************************************************/
	int errorCheckX() {
		int result = HandErrorChecking.errorCheckLine(hand);
		if (result == 0)
			return result;

		Logger.log(
				new StringBuilder().append("//ERROR hand.errorCheckLine() ").append(ERR_ST[result]).append(" handID ")
						.append(hand.handID).append(" hand count ").append(handCount).append(handCount).toString());
		HandReports.actionReport(hand);
		handPS.printHand();
		// ParseCodes.report();
		HandReports.handReport(hand);
		// ReportHand.handReportBySeat(hand);
		hand.status = ParseCodes.HAND_ERROR_CHECK_LINE;
		Crash.log("$$$");
		return -1;
	}

	/*-*************************************************************************************
	 * Update orbit number
	 * Return false if any error.
	 **************************************************************************************/
	private static boolean updateOrbit() {
		if (orbit > 3) {
			hand.status = ParseCodes.TOO_MANY_ORBITS;
			// Crash.log("$$$");
			return false;
		}
		if (!(isAction && notActiveCount != 6 && --actionsToNextOrbit == 0 && notActiveCount < 5)) {
			return true;
		}
		++orbit;
		actionsToNextOrbit = 6 - notActiveCount;
		return true;
	}

	/*-*************************************************************************************
	 * Do Blinds post
	 **************************************************************************************/
	private static void doBlindsPost() {
		pot = hand.SBBet;
		betNow = hand.SBBet;
		moneyInThisStreet[HandCalculations.SBSeat] = hand.SBBet;
		stack[HandCalculations.SBSeat] = stack[HandCalculations.SBSeat].subtract(hand.SBBet);
		hand.actionArray[actionIndex] = new Action();
		++hand.actionCount;
		currentBet = hand.SBBet;
		defaultAction(PREFLOP, HandCalculations.SBSeat, ACTION_POST);
		hand.actionArray[actionIndex].betRaise = hand.SBBet;
		hand.actionArray[actionIndex].raiseTo = hand.SBBet;
		hand.actionArray[actionIndex].money = hand.SBBet;
		hand.actionArray[actionIndex].moneyIn = hand.SBBet;
		++actionIndex;

		pot = pot.add(hand.BBBet);
		betNow = hand.BBBet;
		moneyInThisStreet[HandCalculations.BBSeat] = hand.BBBet;
		stack[HandCalculations.BBSeat] = stack[HandCalculations.BBSeat].subtract(hand.BBBet);
		hand.actionArray[actionIndex] = new Action();
		++hand.actionCount;
		currentBet = hand.BBBet;
		defaultAction(PREFLOP, HandCalculations.BBSeat, ACTION_POST);
		hand.actionArray[actionIndex].betRaise = hand.BBBet;
		hand.actionArray[actionIndex].raiseTo = hand.BBBet;
		hand.actionArray[actionIndex].money = hand.BBBet;
		hand.actionArray[actionIndex].moneyIn = hand.BBBet;
		++actionIndex;
	}

	/*-*************************************************************************************
	 * Initialize hand data for analysis
	 * Scans hand and fills in values for player names, positions, etc..
	 * Everything up to start of play.
	 * 
	 * Returns false if there is any error. Cause of reject is in hand.status.
	 **************************************************************************************/
	private static boolean beforeAction() {
		if (handPS.lines[1].startsWith("Table ")) {
			n = handPS.lines[1].indexOf("Seat #") + 6;
			try {
				hand.BUSeat = Integer.parseInt(handPS.lines[1].substring(n, n + 1)) - 1;
			} catch (Exception e) {
				Logger.logError(
						new StringBuilder().append("//ERROR  beforeAction( ) inbutton seat conversion to integer ")
								.append(linesIndex).append(" ").append("--- ").append(handPS.lines[linesIndex])
								.append(" ").append(hand.handID).toString());
				hand.status = ParseCodes.CONVERTING_HAND_ID;
				handPS.printHand();
				Crash.log("$$$ ");
				return false;
			}
		}

		linesIndex = 2;
		for (int i = 0; i < 6; i++) {
			n = handPS.lines[linesIndex].indexOf(": ");
			n2 = handPS.lines[linesIndex].indexOf(" ($");
			if (n == 1 || n2 == -1) {
				Logger.logError(new StringBuilder().append("//ERROR beforeAction()  index of  : or ($   ").append(i)
						.append(" ").append(linesIndex).append(" ").append(n).append(" ").append(n2).append(" ---")
						.append(handPS.lines[linesIndex]).append("---  ").append(hand.handID).toString());
				handPS.printHand();
				Crash.log("$$$ " + "");
				hand.status = ParseCodes.INDEX_OF_$;
				return false;
			}
			try {
				hand.IDArray[i] = Integer.valueOf(handPS.lines[linesIndex].substring(n + 2, n2));
			} catch (Exception e) {
				Logger.logError(new StringBuilder().append("//ERROR  beforeAction() in hand ID conversion to integer ")
						.append(i).append(" ").append(linesIndex).append(" ").append(n).append(" ").append(n2)
						.append("--- ").append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
				hand.status = ParseCodes.CONVERTING_HAND_ID;
				handPS.printHand();
				Crash.log("$$$ ");
				return false;
			}
			// Stacks
			n3 = handPS.lines[linesIndex].indexOf(" in chips)", n2 + 3);
			if (n3 == -1) {
				Logger.logError(new StringBuilder().append("//ERROR  beforeAction()in chips ").append(i).append(" ")
						.append(linesIndex).append(" ").append(n2).append(" ").append(n3).append("--- ")
						.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
				hand.status = ParseCodes.IN_CHIPS;
				return false;
			}
			try {
				bd = new BigDecimal(handPS.lines[linesIndex].substring(n2 + 3, n3));
			} catch (Exception e) {
				Logger.logError(new StringBuilder().append("//ERROR  beforeAction()in stack conversion ").append(i)
						.append(" ").append(linesIndex).append(" ").append(n2).append(" ").append(n3).append("--- ")
						.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
				hand.status = ParseCodes.STACK_CONVERSION;
				return false;
			}
			hand.stack[i] = bd;
			++linesIndex;
		}
		n4 = handPS.lines[1].indexOf("#");
		if (n4 == -1) {
			Logger.logError(new StringBuilder().append("//ERROR beforeAction String # ").append(n).append(" ")
					.append(handPS.lines[2]).append(" ").append(hand.handID).toString());
			hand.status = ParseCodes.INDEX_OF_;
			return false;
		}

		n = handPS.lines[8].indexOf(": ");
		n2 = handPS.lines[8].indexOf("$", n + 18);
		try {
			hand.SBBet = new BigDecimal(handPS.lines[8].substring(n2 + 1));
		} catch (Exception e) {
			Logger.logError(
					new StringBuilder().append("//ERROR  beforeAction String SBBet conversion ").append(n).append(" ")
							.append(n2).append(" ").append(handPS.lines[1]).append(" ").append(hand.handID).toString());
			hand.status = ParseCodes.SB_BET;
			return false;
		}
		n = handPS.lines[9].indexOf(": ");
		n2 = handPS.lines[9].indexOf("$", n + 18);
		if (handPS.lines[9].contains(" and is all-in")) {
			hand.status = ParseCodes.POST_BB_AND_IS_ALLIN;
			return false;
		}
		// For input string: "2 and is all-in"
		try {
			hand.BBBet = new BigDecimal(handPS.lines[9].substring(n2 + 1));
		} catch (Exception e) {
			Logger.logError(
					new StringBuilder().append("//ERROR  beforeAction String BBBet conversion ").append(n).append(" ")
							.append(n2).append(" ").append(handPS.lines[1]).append(" ").append(hand.handID).toString());
			hand.status = ParseCodes.BB_BET;
			return false;
		}
		return true;
	}

	/*-*************************************************************************************
	 * Initialize hand data for analysis
	 * Scans hand and fills in values from Showdown and Summary
	 * Everything after River play.
	 * 
	 * Returns false if there ia any error. Cause of reject is in hand.status.
	 * 
	 * PokerStars will run a hand multiple times and so there may be multiple Showdowns.
	************************************************************************************* */
	private static boolean doShowdown() {
		if (handPS.lines[linesIndex].startsWith("*** SHOW")) {
			++linesIndex;
			if (!anyShowdown()) {
				return false;
			}
		} else {
			if (handPS.lines[linesIndex].startsWith("*** FIRST SHOW")) {
				++linesIndex;
				if (!anyShowdown()) {
					return false;
				}
				if (handPS.lines[linesIndex].startsWith("*** SECOND SHOW")) {
					++linesIndex;
					if (!anyShowdown()) {
						return false;
					}
					if (handPS.lines[linesIndex].startsWith("*** THIRD SHOW")) {
						++linesIndex;
						if (!anyShowdown()) {
							return false;
						}
						if (handPS.lines[linesIndex].startsWith("*** FOURTH SHOW")) {
							++linesIndex;
							if (!anyShowdown()) {
								return false;
							}
						}
					}
				}
			}
		}
		return true;
	}

	/*-*************************************************************************************
	 * Initialize hand data for analysis
	 * Scans hand and fills in values from Showdown and Summary
	 * Everything after River play.
	 * 
	 * Returns false if there is any error. Cause of reject is in hand.status.
	 **************************************************************************************/
	private static boolean doSummary() {
		String st = "";
		if (hand.actionArray[actionIndex] == null) {
			hand.actionArray[actionIndex] = new Action();
			++hand.actionCount;
		}
		seat = 0;

		if (handPS.lines[linesIndex] == null || (handPS.numLines - linesIndex) < 7) {
			hand.status = ParseCodes.SUMMARY_INCOMPLETE;
			return false;
		}

		if (handPS.lines[linesIndex].contains(" said,")) {
			hand.actionArray[actionIndex].action = ACTION_SAID;
			ignoreLine = true;
			return true;
		}

		if (!handPS.lines[linesIndex].startsWith("*** SU")) {
			handPS.printHand();
			Logger.logError(new StringBuilder().append("//ERROR Summary  ").append(handPS.lines[linesIndex]).append(" ")
					.append(hand.handID).toString());
			hand.status = ParseCodes.SUMMARY_MISSING;
			return false;
		}

		++linesIndex;

		// Total pot
		if (handPS.lines[linesIndex].startsWith("Total pot")) {
			n2 = handPS.lines[linesIndex].indexOf(" ", 11);
			if (n2 == -1 || n2 > handPS.lines[linesIndex].length()) {
				Logger.logError(new StringBuilder().append("//ERROR doSuummary()SUMMARY Total  blank  ").append(n)
						.append(" ").append(n2).append(" ").append(handPS.lines[linesIndex]).append(" ")
						.append(hand.handID).toString());
				hand.status = ParseCodes.TOTAL_POT;
				return false;
			}
			try {
				// hand.totalPot = new BigDecimal(handPS.lines[linesIndex].substring(11, n2));
			} catch (Exception e) {
				Logger.logError(new StringBuilder().append("//ERROR Summary pot conversion error ")
						.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
				hand.status = ParseCodes.TOTAL_POT_VALUE;
				return false;
			}
		}

		// Rake
		n = handPS.lines[linesIndex].indexOf("| Rake ");
		if (n == -1) {
			Logger.logError(new StringBuilder().append("//ERROR  doSummary() SUMMARY no | ")
					.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
			hand.status = ParseCodes.RAKE_INDEX;
			return false;
		}

		// ERROR doSummary() Summary rake conversion error Total pot $113.06 | Rake
		// $5.55 | Jackpot $2 | Bingo $0 13065810874204431 --5.55 | Jackpot $2 | Bingo
		// $0--
		n2 = handPS.lines[linesIndex].length();
		n1 = handPS.lines[linesIndex].indexOf("| Jackpot ");
		if (n1 != -1) {
			n2 = n1 - 1;
			st = handPS.lines[linesIndex].substring(n1 + 11, n1 + 12);
			if (!st.equals("0")) {
				hand.jackpotRake = new BigDecimal(st);
			}
			n1 = handPS.lines[linesIndex].indexOf("| Bingo ");
			st = handPS.lines[linesIndex].substring(n1 + 9, n1 + 10);
			if (!st.equals("0")) {
				hand.bingoRake = new BigDecimal(st);
			}
		}
		if (n + 8 >= n2 + 3) {
			hand.rakeRound = zeroBD;
			hand.rakeNoRound = zeroBD;
			Logger.logError(new StringBuilder().append("//ERROR  doSummary() SUMMARY rake blank ")
					.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
			handPS.printHand();
		} else {
			try {
				hand.rakeNoRound = new BigDecimal(handPS.lines[linesIndex].substring(n + 8, n2).trim());
				hand.rakeRound = hand.rakeNoRound.setScale(2, RoundingMode.HALF_EVEN);
			} catch (Exception e) {
				Logger.logError(new StringBuilder().append("//ERROR doSummary() Summary  rake conversion error ")
						.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString() + " --"
						+ handPS.lines[linesIndex].substring(n + 8, n2) + "-- ");
				hand.status = ParseCodes.RAKE_VALUE;
				Crash.log("$$$ " + "");
				return false;
			}
			pot = pot.subtract(hand.rakeRound);
		}

		// Main pot
		n = handPS.lines[linesIndex].indexOf("Main pot ", n2);
		if (n != -1) {
			n2 = handPS.lines[linesIndex].indexOf(" ", n + 10);
			try {
				bd = new BigDecimal(handPS.lines[linesIndex].substring(n + 10).substring(0, n2 - 1));
			} catch (Exception e) {
				Logger.log(new StringBuilder().append("//ERROR doSuummary()SUMMARY Main pot ").append(bd).append("---")
						.append(handPS.lines[linesIndex]).append("---").append(n).append(" ").append(n2).append(" ")
						.append(hand.handID).toString());
				hand.status = ParseCodes.MAIN_POT;
				return false;
			}
			// hand.mainPot = bd;
		}

		// side pots
		n = handPS.lines[linesIndex].indexOf("Side pot ");
		if (n != -1) {
			n2 = handPS.lines[linesIndex].indexOf(" $", n + 8);
			if (n2 > 1) {
				final int n3 = handPS.lines[linesIndex].indexOf(" |", n2);
				try {
					bd = new BigDecimal(handPS.lines[linesIndex].substring(n2 + 2, n3 - 1));
				} catch (Exception e) {
					Logger.log(new StringBuilder().append("doSuummary()SUMMARY Side pot ").append(hand.sidePot1)
							.append("---").append(handPS.lines[linesIndex]).append("---").append(n).append(" ")
							.append(n2).append(" ").append(hand.handID).toString());
					hand.status = ParseCodes.SIDE_POT;
					return false;
				}
				hand.sidePot1 = bd;
			}
			n = handPS.lines[linesIndex].indexOf("Side pot-1 ");
			if (n != -1) {
				n2 = handPS.lines[linesIndex].indexOf(". ");
				if (n2 > 1) {
					try {
						hand.sidePot1 = new BigDecimal(handPS.lines[linesIndex].substring(n + 10).substring(0, n2));
					} catch (Exception e) {
						Logger.log(new StringBuilder().append("doSuummary()SUMMARY Side pot-1 conversion x ")
								.append(hand.sidePot1).append("---").append(handPS.lines[linesIndex]).append("---")
								.append(n).append(" ").append(n2).append(" ").append(hand.handID).toString());
						hand.status = ParseCodes.SIDE_POT1;
						return false;
					}

				}
				n = handPS.lines[linesIndex].indexOf("Side pot-2 ");
				if (n != -1) {
					n2 = handPS.lines[linesIndex].indexOf(". ", n + 10);
					if (n2 > 1) {
						try {
							bd = new BigDecimal(handPS.lines[linesIndex].substring(0, n2));
							System.out.println("//sidePot2 ");
						} catch (Exception e) {
							Logger.log(new StringBuilder().append("doSuummary()SUMMARY Side pot-2 ").append(bd)
									.append("---").append(handPS.lines[linesIndex]).append("---").append(n).append(" ")
									.append(n2).append(" ").append(hand.handID).toString());
							hand.status = ParseCodes.SIDE_POT2;
							return false;
						}
						if (d < 0) {
							Logger.logError(new StringBuilder()
									.append("//ERROR  doSummary() SUMMARY sidePot2 conversion  ").append(bd).append(" ")
									.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
							hand.status = ParseCodes.SIDE_POT2_VALUE;
							return false;
						}
					}
					n = handPS.lines[linesIndex].indexOf("Side pot-3 ");
					if (n != -1) {
						n2 = handPS.lines[linesIndex].indexOf(". ", n + 10);
						if (n2 > 1) {
							try {
								bd = new BigDecimal(handPS.lines[linesIndex].substring(n + 10).substring(0, n2));
								System.out.println("//sidePot3 ");
							} catch (Exception e) {
								Logger.log(new StringBuilder().append("doSuummary()SUMMARY Side pot-3 ").append(bd)
										.append("---").append(handPS.lines[linesIndex]).append("---").append(" ")
										.append(hand.handID).toString());
								hand.status = ParseCodes.SIDE_POT3;
								return false;
							}
							if (d < 0) {
								Logger.logError(
										new StringBuilder().append("//ERROR  doSummary() SUMMARY sidePot3 conversion  ")
												.append(bd).append(" ").append(handPS.lines[linesIndex]).append(" ")
												.append(hand.handID).toString());
								hand.status = ParseCodes.SIDE_POT3_VALUE;
								return false;
							}
							Logger.log(new StringBuilder().append("doSuummary()SUMMARY Side pot-4 ").append(bd)
									.append("---").append(handPS.lines[linesIndex]).append("---").append(" ")
									.append(hand.handID).toString());
							hand.status = ParseCodes.SIDE_POT4;
							return false;
						}
						n = handPS.lines[linesIndex].indexOf("Side pot-4 ");
						if (n != -1) {
							n2 = handPS.lines[linesIndex].indexOf(". ", n + 10);
							if (n2 > 1) {
								try {
									bd = new BigDecimal(handPS.lines[linesIndex].substring(n + 10).substring(0, n2));
									System.out.println("//sidePot4 ");
								} catch (Exception e) {
									Logger.log(new StringBuilder().append("doSuummary()SUMMARY Side pot-4 ").append(bd)
											.append("---").append(handPS.lines[linesIndex]).append("---").append(" ")
											.append(hand.handID).toString());
									hand.status = ParseCodes.SIDE_POT4_EXIST;
									return false;
								}
								return true;
							}
						}
					}
				}
			}
		}

		++linesIndex;
		if (handPS.lines[linesIndex].startsWith("Hand was ")) {
			++linesIndex;
		}
// Do boards
		if (handPS.lines[linesIndex].startsWith("Board")) {
			n = handPS.lines[linesIndex].indexOf("]");
			st = handPS.lines[linesIndex].substring(7, 15).replace("\\ ", "");
			if (n > 15) {
				st += " " + handPS.lines[linesIndex].substring(16, 18);
			}
			if (n > 18) {
				st += " " + handPS.lines[linesIndex].substring(19, 21);
			}
			hand.boardArray = convertStringToNumbers(st);
			if (hand.boardArray[0] == -1) {
				Logger.log(new StringBuilder().append("doSuummary()SUMMARY Board ---").append(handPS.lines[linesIndex])
						.append("---").append(handPS.lines[linesIndex]).append("---").append(" ").append(hand.handID)
						.toString());
				hand.status = ParseCodes.SIDE_POT4_EXIST;
				return false;
			}
			++linesIndex;
		} else {
			if (handPS.lines[linesIndex].startsWith("FIRST Board")) {
				n = handPS.lines[linesIndex].indexOf("]");
				st = handPS.lines[linesIndex].substring(13, 21).replace("\\ ", "");
				if (n > 22) {
					st += handPS.lines[linesIndex].substring(22, 24);
				}
				if (n > 25) {
					st += handPS.lines[linesIndex].substring(25, 27);
				}
				convertStringToNumbers(st);
				++linesIndex;

				if (handPS.lines[linesIndex].startsWith("SECOND Board")) {
					n = handPS.lines[linesIndex].indexOf("]");
					secondSummaryBoard = handPS.lines[linesIndex].substring(14, 22).replace("\\ ", "");
					st = handPS.lines[linesIndex].substring(14, 22).replace("\\ ", "");
					if (n > 22) {
						secondSummaryBoard += handPS.lines[linesIndex].substring(23, 25);
						st += handPS.lines[linesIndex].substring(23, 25);
					}
					if (n > 25) {
						secondSummaryBoard += handPS.lines[linesIndex].substring(26, 28);
						st += handPS.lines[linesIndex].substring(26, 28);
					}
					convertStringToNumbers(st);
					++linesIndex;

					if (handPS.lines[linesIndex].startsWith("THIRD Board")) {
						n = handPS.lines[linesIndex].indexOf("]");
						thirdSummaryBoard = handPS.lines[linesIndex].substring(13, 21).replace("\\ ", "");
						st = handPS.lines[linesIndex].substring(13, 21).replace("\\ ", "");
						if (n > 22) {
							thirdSummaryBoard += handPS.lines[linesIndex].substring(22, 24);
							st += handPS.lines[linesIndex].substring(22, 24);
						}
						if (n > 25) {
							thirdSummaryBoard += handPS.lines[linesIndex].substring(25, 28);
							st += handPS.lines[linesIndex].substring(25, 28);
						}
						convertStringToNumbers(st);
						Logger.logError(new StringBuilder().append("//ERROR flop3 ---").append(thirdSummaryBoard)
								.append("--- ").append(handPS.lines[linesIndex]).toString());
						++linesIndex;

						if (handPS.lines[linesIndex].startsWith("FOURTH Board")) {
							n = handPS.lines[linesIndex].indexOf("]");
							fourthSummaryBoard = handPS.lines[linesIndex].substring(13, 21).replace("\\ ", "");
							st = handPS.lines[linesIndex].substring(13, 21).replace("\\ ", "");
							if (n > 22) {
								fourthSummaryBoard += handPS.lines[linesIndex].substring(22, 24);
								st += handPS.lines[linesIndex].substring(22, 24);
							}
							if (n > 25) {
								fourthSummaryBoard += handPS.lines[linesIndex].substring(25, 28);
								st += handPS.lines[linesIndex].substring(25, 28);
							}
							convertStringToNumbers(st);
							Logger.logError(new StringBuilder().append("//ERROR flop4---").append(fourthSummaryBoard)
									.append("--- ").append(handPS.lines[linesIndex]).toString());
							++linesIndex;
						}
					}
				}
			}
		}
		// Check the 6 seats in summary
		seat = 0;
		for (int j = 0; j < 6; ++j) {
			if (handPS.lines[linesIndex] == null || !handPS.lines[linesIndex].startsWith("Seat ")) {
				hand.status = ParseCodes.SUMMARY_MISSING_SEAT;
				return false;
			}

			// Won
			n = handPS.lines[linesIndex].indexOf(" won ($");
			if (n != -1) {
				if (n == -1) {
					Logger.logError(new StringBuilder().append("//ERROR doSuummary()won ($ ").append(n).append(" ")
							.append(n2).append(" ").append(handPS.lines[linesIndex]).append(" ").append(hand.handID)
							.toString());
					hand.status = ParseCodes.WON$;
					return false;
				}
				n2 = handPS.lines[linesIndex].indexOf(")", n + 7);
				try {
					bd = new BigDecimal(handPS.lines[linesIndex].substring(n + 7, n2));
				} catch (Exception e) {
					Logger.logError(new StringBuilder().append("//ERROR doSuummary()won ($ d ").append(d).append(" ")
							.append(n).append(" ").append(n2).append(" ").append(handPS.lines[linesIndex]).append(" ")
							.append(hand.handID).toString());
					hand.status = ParseCodes.WON$_INDEX;
					return false;
				}
				if (hand.actionArray[actionIndex] == null) {
					hand.actionArray[actionIndex] = new Action();
					++hand.actionCount;
				}
				defaultAction(SUMMARY, seat, ACTION_WON);
				hand.actionArray[actionIndex].money = bd;
//				hand.actionArray[actionIndex].sidePot = hand.sidePot1;

				++actionIndex;
				// did he win a second hand?
				n = handPS.lines[linesIndex].indexOf(" won ($", n2);
				if (n != -1) {
					n2 = handPS.lines[linesIndex].indexOf(")", n + 7);
					try {
						bd = new BigDecimal(handPS.lines[linesIndex].substring(n + 7, n2));
					} catch (Exception e) {
						Logger.logError(new StringBuilder().append("//ERROR doSuummary()won ($ d ").append(d)
								.append(" ").append(n).append(" ").append(n2).append(" ")
								.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
						hand.status = ParseCodes.WON$_INDEX;
						return false;
					}
					if (hand.actionArray[actionIndex] == null) {
						hand.actionArray[actionIndex] = new Action();
						++hand.actionCount;
					}
					defaultAction(SUMMARY, seat, ACTION_WON);
					hand.actionArray[actionIndex].money = bd;
//					hand.actionArray[actionIndex].sidePot = sidePot;
					++actionIndex;
				}

			} else {
				// Collected
				n = handPS.lines[linesIndex].indexOf(" collected ");
				if (n != -1) {
					n2 = handPS.lines[linesIndex].indexOf(")", n + 13);
					if (n2 == -1) {
						Logger.logError(new StringBuilder().append(" doSuummary()Summary summarycollected ) ").append(n)
								.append(" ").append(n2).append(" ").append(handPS.lines[linesIndex]).append(" ")
								.append(hand.handID).toString());
						hand.status = ParseCodes.COLLECTED_INDEX;
						return false;
					}
					try {
						bd = new BigDecimal(handPS.lines[linesIndex].substring(n + 13, n2));
					} catch (Exception e) {
						Logger.logError(new StringBuilder().append(" doSuummary()won ($ ").append(n).append(" ")
								.append(n2).append(" ").append(handPS.lines[linesIndex]).append(" ").append(hand.handID)
								.toString());
						hand.status = ParseCodes.COLLECTED_VALUE;
						return false;
					}
					if (hand.actionArray[actionIndex] == null) {
						hand.actionArray[actionIndex] = new Action();
						++hand.actionCount;
					}
					defaultAction(SUMMARY, seat, ACTION_COLLECTED_SUMMARY);
					hand.actionArray[actionIndex].money = bd;
					hand.actionArray[actionIndex].call = zeroBD;
					// hand.actionArray[actionIndex].sidePot = sidePot;
					++actionIndex;
				}
			}
			++linesIndex;
			++seat;
		}
		return true;
	}

	/*-*************************************************************************************
	 * Convert a string representing a board to an int array
	 **************************************************************************************/
	private static int[] convertStringToNumbers(String st) {
		int[] x = { -1, -1, -1, -1, -1 };
		Card card = null;
		if (st.length() < 8) {
			Logger.log("ERROR convertStringToNumbers() " + st.length() + " " + st);
			return x;
		}
		if (st.length() == 8) {
			card = new Card(st.substring(0, 2));
			x[0] = card.card;
			card = new Card(st.substring(3, 5));
			x[1] = card.card;
			card = new Card(st.substring(6, 8));
			x[2] = card.card;
		} else if (st.length() == 11) {
			card = new Card(st.substring(0, 2));
			x[0] = card.card;
			card = new Card(st.substring(3, 5));
			x[1] = card.card;
			card = new Card(st.substring(6, 8));
			x[2] = card.card;
			card = new Card(st.substring(9, 11));
			x[3] = card.card;
		} else if (st.length() == 14) {
			card = new Card(st.substring(0, 2));
			x[0] = card.card;
			card = new Card(st.substring(3, 5));
			x[1] = card.card;
			card = new Card(st.substring(6, 8));
			x[2] = card.card;
			card = new Card(st.substring(9, 11));
			x[3] = card.card;
			card = new Card(st.substring(12, 14));
			x[4] = card.card;
		}
		return x;
	}

	/*-*************************************************************************************
	 * Check for one Flop, or no Flop or multiple Flops.
	 * Returns true if there is is 1 Flop.
	 * Returns false if there is no Flop or multiple Flops.
	 * Saves board in Hand.
	 * The shared variable linesIndex is updated.
	 * 
	 * PokerStars unique.
	 * A hand may be played multiple times.
	 * In that case, hands are dealt but there is no betting on a strees,
	 * they simply go to Showdown and there can be multiple showdowns.
	 * What we do is to simply skip the on to Show Down.
	 * 
	 * For the FIRST boards we save them in Hand.
	 * For the SECOND, THIRD, and FOURTH boards we save them in  in 
	 * 
		 * 
	 * PokerStars plays multiple hands when:
	 * 		2 or more players All-in
	 * 		?	
	 * 
	 *** FIRST FLOP *** [5s 5c 6s]
	 *** FIRST TURN *** [5s 5c 6s] [8h]
	 *** FIRST RIVER *** [5s 5c 6s 8h] [Ah]
	 *** SECOND FLOP *** [9c 4h 7d]
	 *** SECOND TURN *** [9c 4h 7d] [6c]
	 *** SECOND RIVER *** [9c 4h 7
	************************************************************************************* */
	private static boolean flop() {
		String st = "";
		n = handPS.lines[linesIndex].indexOf(" FLOP ");
		if (n == -1) {
			return false;
		}
		if (n == 3) {
			st = handPS.lines[linesIndex].substring(14, 22).replace("\\ ", "");
			++linesIndex;
			return true;
		}
		if (n == 9) {
			st = handPS.lines[linesIndex].substring(20, 28).replace("\\ ", "");
			++linesIndex;
			if (handPS.lines[linesIndex].startsWith("*** FIRST TURN")) {
				st = handPS.lines[linesIndex].substring(20, 33).replace("\\  ", "");
				++linesIndex;
				if (handPS.lines[linesIndex].startsWith("*** FIRST RIVER")) {
					st = handPS.lines[linesIndex].substring(21, 37).replace("\\  ", "");
					++linesIndex;
					if (handPS.lines[linesIndex].startsWith("*** SECOND FLOP")) {
						secondFlopBoard = handPS.lines[linesIndex].substring(21, 29).replace("\\  ", "");
						secondFlopBoard = secondFlopBoard.replace("\\[", "");
						++linesIndex;
						if (handPS.lines[linesIndex].startsWith("*** SECOND TURN")) {
							secondTurnBoard = handPS.lines[linesIndex].substring(21, 34).replace("\\  ", "");
							secondTurnBoard = secondTurnBoard.replace("\\[", "");
							++linesIndex;
							if (handPS.lines[linesIndex].startsWith("*** SECOND RIVER")) {
								secondRiverBoard = handPS.lines[linesIndex].substring(22, 38).replace("\\  ", "");
								secondRiverBoard = secondRiverBoard.replace("\\[", "");
								++linesIndex;
								if (handPS.lines[linesIndex].startsWith("*** THIRD FLOP")) {
									thirdFlopBoard = handPS.lines[linesIndex].substring(20, 28).replace("\\  ", "");
									thirdFlopBoard = thirdFlopBoard.replace("\\[", "");
									Logger.logError(new StringBuilder().append("//ERROR  third FlopBoard  ").append(" ")
											.append(handPS.lines[linesIndex]).append(" ").append(hand.handID)
											.toString());
									++linesIndex;
									if (handPS.lines[linesIndex].startsWith("*** THIRD TURN")) {
										thirdTurnBoard = handPS.lines[linesIndex].substring(20, 33).replace("\\  ", "");
										thirdTurnBoard = thirdTurnBoard.replace("\\[", "");
										Logger.logError(new StringBuilder().append("//ERROR  third Turn Board  ")
												.append(" ").append(handPS.lines[linesIndex]).append(" ")
												.append(hand.handID).toString());
										++linesIndex;
										if (handPS.lines[linesIndex].startsWith("*** THIRD RIVER")) {
											thirdRiverBoard = handPS.lines[linesIndex].substring(21, 37).replace("\\  ",
													"");
											thirdRiverBoard = thirdRiverBoard.replace("\\[", "");
											Logger.logError(new StringBuilder().append("//ERROR  third River Board  ")
													.append(" ").append(handPS.lines[linesIndex]).append(" ")
													.append(hand.handID).toString());
											++linesIndex;
											if (handPS.lines[linesIndex].startsWith("*** FOURTH FLOP")) {
												fourthFlopBoard = handPS.lines[linesIndex].substring(21, 29)
														.replace("\\  ", "");
												fourthFlopBoard = fourthFlopBoard.replace("\\[", "");
												Logger.logError(
														new StringBuilder().append("//ERROR  fourth FlopBoard  ")
																.append(" ").append(handPS.lines[linesIndex])
																.append(" ").append(hand.handID).toString());
												++linesIndex;
												if (handPS.lines[linesIndex].startsWith("*** FOURTH TURN")) {
													fourthTurnBoard = handPS.lines[linesIndex].substring(21, 34)
															.replace("\\  ", "");
													fourthTurnBoard = fourthTurnBoard.replace("\\[", "");
													Logger.logError(
															new StringBuilder().append("//ERROR  fourth Turn Board  ")
																	.append(" ").append(handPS.lines[linesIndex])
																	.append(" ").append(hand.handID).toString());
													++linesIndex;
													if (handPS.lines[linesIndex].startsWith("*** FOURTH RIVER")) {
														fourthRiverBoard = handPS.lines[linesIndex].substring(22, 38)
																.replace("\\  ", "");
														fourthRiverBoard = fourthRiverBoard.replace("\\[", "");
														Logger.logError(new StringBuilder()
																.append("//ERROR fourth River Board  ").append(" ")
																.append(handPS.lines[linesIndex]).append(" ")
																.append(hand.handID).toString());
														++linesIndex;
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	/*-*************************************************************************************
	 * Check for one Turn, or no Turnor multiple Turns.
	 * Returns true if there is is 1 Turn.
	 * Returns false if there is noTurn or multiple Turns.
	 * Saves board in Hand.
	 * The shared variable linesIndex is updated.
	************************************************************************************* */
	private static boolean turn() {
		n = handPS.lines[linesIndex].indexOf(" TURN ");
		if (n == -1) {
			return false;
		}

		if (n == 3) {
			st = handPS.lines[linesIndex].substring(14, 27).replace("\\ ", "");
			st = st.replace("\\[", "");
			++linesIndex;
			return true;
		}
		if (n == 9) {
			st = handPS.lines[linesIndex].substring(20, 33).replace("\\ ", "");
			st = st.replace("\\[", "");
			++linesIndex;
			if (handPS.lines[linesIndex].startsWith("*** FIRST RIVER")) {
				st = handPS.lines[linesIndex].substring(21, 37).replace("\\ ", "");
				st = st.replace("\\[", "");
				++linesIndex;
				if (handPS.lines[linesIndex].startsWith("*** SECOND TURN")) {
					secondTurnBoard = handPS.lines[linesIndex].substring(21, 34).replace("\\ ", "");
					secondTurnBoard = secondTurnBoard.replace("\\[", "");
					++linesIndex;
					if (handPS.lines[linesIndex].startsWith("*** SECOND RIVER")) {
						secondRiverBoard = handPS.lines[linesIndex].substring(22, 38).replace("\\ ", "");
						secondRiverBoard = secondRiverBoard.replace("\\[", "");
						++linesIndex;
						if (handPS.lines[linesIndex].startsWith("*** THIRD TURN")) {
							thirdTurnBoard = handPS.lines[linesIndex].substring(20, 33).replace("\\ ", "");
							thirdTurnBoard = thirdTurnBoard.replace("\\[", "");
							Logger.logError(new StringBuilder().append("//ERROR  third Turn Board  ").append(" ")
									.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
							++linesIndex;
							if (handPS.lines[linesIndex].startsWith("*** THIRD RIVER")) {
								thirdRiverBoard = handPS.lines[linesIndex].substring(21, 37).replace("\\ ", "");
								thirdRiverBoard = thirdRiverBoard.replace("\\[", "");
								Logger.logError(new StringBuilder().append("//ERROR  third River Board  ").append(" ")
										.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
								++linesIndex;
								if (handPS.lines[linesIndex].startsWith("*** FOURTH TURN")) {
									fourthTurnBoard = handPS.lines[linesIndex].substring(21, 34).replace("\\ ", "");
									fourthTurnBoard = fourthTurnBoard.replace("\\[", "");
									Logger.logError(new StringBuilder().append("//ERROR  fourth Turn Board  ")
											.append(" ").append(handPS.lines[linesIndex]).append(" ")
											.append(hand.handID).toString());
									++linesIndex;
									if (handPS.lines[linesIndex].startsWith("*** FOURTH RIVER")) {
										fourthRiverBoard = handPS.lines[linesIndex].substring(22, 38).replace("\\ ",
												"");
										fourthRiverBoard = fourthRiverBoard.replace("\\[", "");
										Logger.logError(new StringBuilder().append("//ERROR  fourth River Board  ")
												.append(" ").append(handPS.lines[linesIndex]).append(" ")
												.append(hand.handID).toString());
										++linesIndex;
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	/*-*************************************************************************************
	 * Check for one River, or no River or multiple Rivers.
	 * Returns true if there is is 1 River.
	 * Returns false if there is noRiver or multiple Rivers.
	 * Saves board in Hand.
	 * The shared variable linesIndex is updated.
	************************************************************************************* */
	private static boolean river() {
		n = handPS.lines[linesIndex].indexOf(" RIVER ");
		if (n == -1) {
			return false;
		}
		if (n == 3) {
			st = handPS.lines[linesIndex].substring(15, 31).replace("\\ ", "");
			st = st.replace("\\[", "");
			++linesIndex;
			return true;
		}
		if (n == 9) {
			st = handPS.lines[linesIndex].substring(21, 37).replace("\\ ", "");
			st = st.replace("\\[", "");
			++linesIndex;
			if (handPS.lines[linesIndex].startsWith("*** SECOND RIVER")) {
				secondRiverBoard = handPS.lines[linesIndex].substring(22, 38).replace("\\ ", "");
				secondRiverBoard = secondRiverBoard.replace("\\[", "");
				++linesIndex;
				if (handPS.lines[linesIndex].startsWith("*** THIRD RIVER")) {
					thirdRiverBoard = handPS.lines[linesIndex].substring(21, 37).replace("\\ ", "");
					thirdRiverBoard = thirdRiverBoard.replace("\\[", "");
					Logger.logError(new StringBuilder().append("//ERROR  third River Board  ").append(" ")
							.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
					++linesIndex;
					if (handPS.lines[linesIndex].startsWith("*** FOURTH RIVER")) {
						fourthRiverBoard = handPS.lines[linesIndex].substring(22, 38).replace("\\ ", "");
						fourthRiverBoard = fourthRiverBoard.replace("\\[", "");
						Logger.logError(new StringBuilder().append("//ERROR  fourth River Board  ").append(" ")
								.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
						++linesIndex;
					}
				}
			}
		}
		return false;
	}

	/*-*************************************************************************************
	 * Any Showdown
	 * A helper method for  Showdown
	 * The header line is the only real difference so common code is here.
	 * Returns true if all is OK, false if there is any error
	 * The shared variable linesIndex is updated.
	 * 
	 * PokerStars is unique in that it will run a hand multiple times.
	 * This happens when:
	 * 		2 or more players are All-in
	 * 		TODO
	 * 
	 *** SHOW DOWN ***
	psek1: shows [4d 2d] (a straight, Ace to Five)
	Rifa92: mucks hand 
	psek1 collected $56.13 from pot
	
	//*** FIRST SHOW DOWN ***
	//JoSsP1n: shows [9h Jh] (a flush, Ace high)
	//Yaaaazz: shows [Kh As] (two pair, Aces and Kings)
	//JoSsP1n collected $260.61 from pot
	//*** SECOND SHOW DOWN ***
	//JoSsP1n: shows [9h Jh] (a flush, Ace high)
	//Yaaaazz: shows [Kh As] (a pair of Aces)
	//JoSsP1n collected $260.60 from pot
	//*** SUMMARY ***
	************************************************************************************* */
	private static boolean anyShowdown() {
		showdown = true;
		String st = "";
		var bd = zeroBD;
		seat = 0;
		for (int j = linesIndex; j < handPS.numLines; ++j) {
			if (handPS.lines[linesIndex].startsWith("*** ")) {
				break;
			}
			if (hand.actionArray[actionIndex] == null) {
				hand.actionArray[actionIndex] = new Action();
				++hand.actionCount;
			}
			n = handPS.lines[linesIndex].indexOf(" collected ");
			if (n != -1) {
				n2 = handPS.lines[linesIndex].indexOf(" from pot");
				n3 = handPS.lines[linesIndex].indexOf(" from side pot");
				n4 = handPS.lines[linesIndex].indexOf(" from main pot");
				if (n2 == -1 && n3 == -1 && n4 == -1) {
					Logger.logError(new StringBuilder().append("//ERROR doShowdown()SHOW DOWN from error ").append(seat)
							.append(" ").append(n).append(" ").append(n2).append(" ").append(n3).append(" ").append(n4)
							.append(" ").append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
					hand.status = ParseCodes.SHOWDOWN_INDEX;
					return false;
				}
				if (n2 != -1) {
					try {
						bd = new BigDecimal(handPS.lines[linesIndex].substring(n + 12, n2));
					} catch (Exception e) {
						Logger.logError(new StringBuilder().append("//ERROR doShowdown()SHOW DOWN  from pot ").append(n)
								.append(" ").append(n2).append(" ").append(" ").append(handPS.lines[linesIndex])
								.append("---").append(handPS.lines[linesIndex].substring(n + 12, n2)).append("---")
								.append(" ").append(hand.handID).toString());
						hand.status = ParseCodes.SHOWDOWN_VALUE;
						return false;
					}
					hand.actionArray[actionIndex].action = ACTION_COLLECTED_POT;
				} else if (n3 != -1) {
					try {
						bd = new BigDecimal(handPS.lines[linesIndex].substring(n + 12, n3));
					} catch (Exception e) {
						Logger.logError(new StringBuilder().append("//ERROR doShowdown()SHOW DOWN  from side pot   ")
								.append(n).append(" ").append(n2).append(" ").append(handPS.lines[linesIndex])
								.append("---").append(handPS.lines[linesIndex].substring(n + 12, n3)).append("---")
								.append(" ").append(hand.handID).toString());
						hand.status = ParseCodes.SHOWDOWN_COLLECTED;
						return false;
					}
					hand.actionArray[actionIndex].action = ACTION_COLLECTED_SIDE_POT;
//					hand.actionArray[actionIndex].sidePot = bd;
					hand.sidePot1 = bd;

				} else if (n4 != -1) {
					try {
						bd = new BigDecimal(handPS.lines[linesIndex].substring(n + 12, n4));
					} catch (Exception e) {
						Logger.logError(new StringBuilder().append("//ERROR doShowdown()SHOW DOWN  from main pot   ")
								.append(n).append(" ").append(n2).append(" ").append(handPS.lines[linesIndex])
								.append("---").append(handPS.lines[linesIndex].substring(n + 12, n3)).append("---")
								.append(" ").append(hand.handID).toString());
						hand.status = ParseCodes.SHOWDOWN_SIDE_POT;
						return false;
					}

					// hand.mainPot = bd;
				}

				// Find players seat number
				seat = getPlayerSeat(handPS.lines[linesIndex].substring(0, n));
				if (seat < 0) {
					Logger.logError(new StringBuilder().append("//ERROR doShowdown()SHOW DOWN  seat# ").append(seat)
							.append(" ").append(n2).append(" ").append(handPS.lines[linesIndex]).append("---")
							.append(handPS.lines[linesIndex].substring(n, n2)).append("---").append(" ")
							.append(hand.handID).toString());
					hand.status = ParseCodes.SHOWDOWN_SEAT;
					return false;
				}
				defaultAction(SHOWDOWN, seat, ACTION_COLLECTED_MAIN_POT);
				stack[seat] = stack[seat].add(bd);
				hand.actionArray[actionIndex].money = bd;
				hand.actionArray[actionIndex].pot = pot;
				// hand.actionArray[actionIndex].sidePot = hand.sidePot1;
				++actionIndex;
			} else {
				if (handPS.lines[linesIndex].contains(" mucks hand")) {
					// Ignore
				} else {
					n = handPS.lines[linesIndex].indexOf(" shows [");
					if (n != -1) {
						seat = getPlayerSeat(handPS.lines[linesIndex].substring(0, n - 1));
						if (handPS.lines[linesIndex].length() < n + 13) {
							st = handPS.lines[linesIndex].substring(n + 8, n + 10);
							Card card = new Card(st);
							hand.showsArray[seat][0] = card.card;
						} else {
							st = handPS.lines[linesIndex].substring(n + 8, n + 10);
							Card card = new Card(st);
							hand.showsArray[seat][0] = card.card;
							st = handPS.lines[linesIndex].substring(n + 11, n + 13);
							card = new Card(st);
							hand.showsArray[seat][0] = card.card;
						}
						defaultAction(SHOWDOWN, seat, ACTION_SHOWS_SHOWDOWN);
					}
				}
			}
			++linesIndex;
		}
		return true;
	}

	/*- ****************************************************************************************
	 * This section is code for any street, Preflop, Flop, Turn or River.
	 * There are several methods:
	 * 		Uncalled
	 * 		Returned to
	 * 		playerCollected
	 * 		Current player seat
	 * 		Current player name
	 * 		Current player action 
	 * 			fold, check, calls, bets, raises,  calls all-in, bets all-in, raises all-in
	 * 
	 * For PokerStars:
	 * 		it's a player if name followed by :
	 *  	It's a status otherwise, line starts with Uncalled or
	 *  		other status about player
	 * 
	*/

	/*-
	 * This method parses one line in linesArray ( PokerStars ) and creates one element in actionArray.
	 * PokerStars Hand History is converted to a website independent action array.
	 * 
	 * Arg0 - PokerStars hand
	 * Arg1 - Hand 
	 * Arg2 - Street number
	 * Arg3 - Index into actionArray - Hand output
	 * Arg4 = Index into lines array - PokerStars input
	 * Parses one line for one street.
	 * Sets one or more variables in hand.
	 * 		The variables are indexed by street, relative position ( index within street ) and seat  
	 * When the action has been determined and the and variables set this method
	 * 
	  * Returns  false if there is an error or action to be ignored like dosent show.
	  * If there is an error then the status field in hand is set.
	  * The caller must check the status!
	  * 
	  * Some lines start with a players name followed by :
	  * Others start with an acton such as Uncalled.
	  * This method handles the lines that srart with a player name first.
	 **************************************************************************************/
	static boolean anyStreetPlayerAction() {
		var bd = zeroBD;
		if (hand.actionArray[actionIndex] == null) {
			hand.actionArray[actionIndex] = new Action();
			++hand.actionCount;
		}
		isAllin = false;
		isAction = false;
		ignoreLine = false;

		if (handPS.lines[linesIndex].contains(" said,")) {
			hand.actionArray[actionIndex].action = ACTION_SAID;
			ignoreLine = true;
			return true;
		}

		n = handPS.lines[linesIndex].indexOf(": ");
		if (n != -1) {
			seat = getPlayerSeat(handPS.lines[linesIndex].substring(0, n));
			if (seat < 0) {
				Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() seat number ")
						.append(handPS.lines[linesIndex]).append(" linesIndex ").append(linesIndex).append(" ")
						.append(hand.handID).toString());
				hand.status = ParseCodes.BLANK;
				return false;
			}
			n3 = handPS.lines[linesIndex].substring(n + 2).length();
			if (n3 <= 1) {
				// Ignore hand if there is no action after :
				hand.status = ParseCodes.BLANK;
				return false;
			}

			defaultAction(street, seat, ACTION_FOLD);
			hand.actionArray[actionIndex].betRaise = zeroBD;
			hand.actionArray[actionIndex].moneyIn = moneyInThisStreet[seat];
			hand.actionArray[actionIndex].stack = stack[seat];
			hand.actionArray[actionIndex].pot = pot;
			responsePending = false;
			n3 = handPS.lines[linesIndex].indexOf(" folds", n);
			if (n3 != -1) {
				hand.actionArray[actionIndex].action = ACTION_FOLD;
				hand.actionArray[actionIndex].moneyIn = moneyInThisStreet[seat];
				folded[seat] = true;
				inactive[seat] = true;
				++foldCount;
				++notActiveCount;
				isAction = true;
				return true;
			}

			n = handPS.lines[linesIndex].indexOf("and is all-in");
			if (n != -1) {
				isAllin = true;
				allin[seat] = true;
				inactive[seat] = true;
				++notActiveCount;
			} else {
				isAllin = false;
			}

			n3 = handPS.lines[linesIndex].indexOf("calls $");
			if (n3 != -1) {
				if (isAllin) {
					try {
						bd = new BigDecimal(handPS.lines[linesIndex].substring(n3 + 7, n - 1));
					} catch (Exception e) {
						Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() calls $ ")
								.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
						hand.status = ParseCodes.CALL$;
						return false;
					}
				} else {
					try {
						bd = new BigDecimal(handPS.lines[linesIndex].substring(n3 + 7));
					} catch (Exception e) {
						Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() calls $ ")
								.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
						hand.status = ParseCodes.CALL$;
						return false;
					}
				}

				if (isAllin) {
					hand.actionArray[actionIndex].action = ACTION_CALL_ALLIN;
					moneyInThisStreet[seat] = betNow.subtract(moneyInThisStreet[seat]);
					hand.actionArray[actionIndex].moneyIn = moneyInThisStreet[seat];
					pot = pot.add(moneyInThisStreet[seat]);
					currentBet = stack[seat];
					stack[seat] = zeroBD;
					hand.actionArray[actionIndex].money = moneyInThisStreet[seat];
					hand.actionArray[actionIndex].stack = stack[seat];
				} else {
					hand.actionArray[actionIndex].action = ACTION_CALL;

					if (moneyInThisStreet[seat].equals(zeroBD)) {
						moneyInThisStreet[seat] = betNow;
						hand.actionArray[actionIndex].moneyIn = betNow;
						stack[seat] = stack[seat].subtract(betNow);
						pot = pot.add(moneyInThisStreet[seat]);
						hand.actionArray[actionIndex].money = moneyInThisStreet[seat];
					} else {
						bd = betNow.subtract(moneyInThisStreet[seat]);
						hand.actionArray[actionIndex].moneyIn = moneyInThisStreet[seat];
						moneyInThisStreet[seat] = betNow;
						stack[seat] = stack[seat].subtract(bd);
						pot = pot.add(bd);
						hand.actionArray[actionIndex].money = bd;
						hand.actionArray[actionIndex].moneyIn = bd;
						hand.actionArray[actionIndex].call = currentBet;
					}
					hand.actionArray[actionIndex].betRaise = zeroBD;
					hand.actionArray[actionIndex].stack = stack[seat];
				}
				hand.actionArray[actionIndex].pot = pot;
//				hand.actionArray[actionIndex].sidePot = sidePot;
				responsePending = false;
				if (isAllin) {
					hand.actionArray[actionIndex].action = ACTION_CALL_ALLIN;
					currentBet = stack[seat];
				} else {
					hand.actionArray[actionIndex].action = ACTION_CALL;
				}
				isAction = true;
				return true;
			}

			n3 = handPS.lines[linesIndex].indexOf(" checks");
			if (n3 != -1) {
				hand.actionArray[actionIndex].action = ACTION_CHECK;
				hand.actionArray[actionIndex].betRaise = betNow;
				hand.actionArray[actionIndex].moneyIn = moneyInThisStreet[seat];
				isAction = true;
				return true;
			}

			n3 = handPS.lines[linesIndex].indexOf(" raises $");
			if (n3 != -1) {
				n = handPS.lines[linesIndex].indexOf(" to ", n3 + 9);
				if (n == -1) {
					Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() raises $ no to ")
							.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
					hand.status = ParseCodes.RAISE_TO;
					return false;
				} else {
					try {
						bd = new BigDecimal(handPS.lines[linesIndex].substring(n3 + 9, n));
					} catch (Exception e) {
						Logger.logError(
								new StringBuilder().append("//ERROR anyStreetPlayerAction() raises $ stringToDecimal")
										.append(handPS.lines[linesIndex]).append(" ")
										.append(handPS.lines[linesIndex].substring(n3, n)).append(" ")
										.append(hand.handID).toString());
						return false;
					}

					hand.actionArray[actionIndex].stack = stack[seat];
					n2 = n + 5;
					if (isAllin) {
						n3 = handPS.lines[linesIndex].indexOf(" and", n2);
						try {
							bd2 = new BigDecimal(handPS.lines[linesIndex].substring(n2, n3));
						} catch (Exception e) {
							Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() raises pot ")
									.append(handPS.lines[linesIndex]).append(" ---")
									.append(handPS.lines[linesIndex].substring(n2, n3)).append("--- ").append(" ")
									.append(hand.handID).toString());
							hand.status = ParseCodes.RAISE_TO_VALUE;
							return false;
						}
					} else {
						try {
							bd2 = new BigDecimal(handPS.lines[linesIndex].substring(n2));
						} catch (Exception e) {
							Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() raises pot ")
									.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
							hand.status = ParseCodes.RAISE_TO_VALUE;
							return false;
						}
					}
					hand.actionArray[actionIndex].moneyIn = bd;
					betNow = bd2;
					moneyInThisStreet[seat] = betNow.subtract(moneyInThisStreet[seat]);
					pot = pot.add(moneyInThisStreet[seat]);
					stack[seat] = stack[seat].subtract(moneyInThisStreet[seat]);
					hand.actionArray[actionIndex].money = moneyInThisStreet[seat];
					hand.actionArray[actionIndex].call = currentBet;
					hand.actionArray[actionIndex].betRaise = bd;
					hand.actionArray[actionIndex].raiseTo = bd2;
					hand.actionArray[actionIndex].stack = stack[seat];
					hand.actionArray[actionIndex].pot = pot;
//					hand.actionArray[actionIndex].sidePot = sidePot;
					currentBet = bd2;
					moneyInThisStreet[seat] = betNow;

					responsePending = true;

					if (isAllin) {
						hand.actionArray[actionIndex].action = ACTION_RAISE_ALLIN;
					} else {
						hand.actionArray[actionIndex].action = ACTION_RAISE;
					}
					isAction = true;
					return true;
				}
			}

			n3 = handPS.lines[linesIndex].indexOf("bets $");
			if (n3 != -1) {
				if (isAllin) {
					try {
						bd = new BigDecimal(handPS.lines[linesIndex].substring(n3 + 6, n - 1));
					} catch (Exception e) {
						Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() bets $ ")
								.append(handPS.lines[linesIndex]).append(" ---")
								.append(handPS.lines[linesIndex].substring(n3 + 6, n)).append("---  ")
								.append(hand.handID).toString());
						hand.status = ParseCodes.BET$;
						return false;
					}
				} else {
					try {
						bd = new BigDecimal(handPS.lines[linesIndex].substring(n3 + 6));
					} catch (Exception e) {
						Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() bets $ ")
								.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
						hand.status = ParseCodes.BET$;
						return false;
					}
				}
				betNow = bd;
				moneyInThisStreet[seat] = bd;
				pot = pot.add(bd);
				stack[seat] = stack[seat].subtract(bd);
				hand.actionArray[actionIndex].stack = stack[seat];
				hand.actionArray[actionIndex].pot = pot;
//				hand.actionArray[actionIndex].sidePot = sidePot;
				hand.actionArray[actionIndex].money = bd;
				hand.actionArray[actionIndex].moneyIn = bd;
				hand.actionArray[actionIndex].betRaise = bd;
				hand.actionArray[actionIndex].raiseTo = currentBet.add(bd);
				responsePending = true;
				if (isAllin) {
					hand.actionArray[actionIndex].action = ACTION_BET_ALLIN;
					hand.actionArray[actionIndex].raiseTo = stack[seat];
					currentBet = stack[seat];
				} else {
					hand.actionArray[actionIndex].action = ACTION_BET;
					currentBet = currentBet.add(bd);
				}
				isAction = true;
				return true;
			}

			n3 = handPS.lines[linesIndex].indexOf(" shows");
			if (n3 != -1) {
				Card card;
				n = handPS.lines[linesIndex].indexOf("]");
				if (n - n3 < 13) {
					st = handPS.lines[linesIndex].substring(n3 + 8, n3 + 10);
					if (Card.isValid(st)) {
						card = new Card(st);
						hand.showsArray[seat][0] = card.card;
					}
				} else {
					st = handPS.lines[linesIndex].substring(n3 + 8, n3 + 10);
					if (Card.isValid(st)) {
						card = new Card(st);
						hand.showsArray[seat][0] = card.card;
					}
					st = handPS.lines[linesIndex].substring(n3 + 11, n3 + 13);
					if (Card.isValid(st)) {
						card = new Card(st);
						hand.showsArray[seat][1] = card.card;
					}
				}
				defaultAction(street, seat, ACTION_SHOWS_STREET);
				hand.actionArray[actionIndex].stack = stack[seat];
				hand.actionArray[actionIndex].pot = pot;
				// hand.actionArray[actionIndex].sidePot = sidePot;
				isAction = false;
				return true;
			}

			if (handPS.lines[linesIndex].length() < handPS.lines[linesIndex].indexOf(": ") + 4) {
				isAction = false;
				return true;
			}
		}

		isAction = false;
		// Does not start with player name - Uncalled, returned to, collected
		n = handPS.lines[linesIndex].indexOf("Uncalled ");
		if (n != -1) {
			n3 = handPS.lines[linesIndex].indexOf("returned to ");
		}
		if (n != -1 && n3 != -1) {
			// Find name and seat
			n = handPS.lines[linesIndex].indexOf(" to ");
			if (n == -1) {
				Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() returned to ")
						.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
				hand.status = ParseCodes.UNCALLED_INDEX;
				return false;
			}
			seat = getPlayerSeat(handPS.lines[linesIndex].substring(n + 4));
			if (seat < 0) {
				Logger.logError(new StringBuilder().append("//ERROR() uncalled seat number ")
						.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
				hand.status = ParseCodes.UNCALLED_SEAT;
				return false;
			}
			n = handPS.lines[linesIndex].indexOf("($");
			if (n == -1) {
				Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() uncalled ")
						.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
				hand.status = ParseCodes.UNCALLED$_INDEX;
				return false;
			} else {
				n2 = handPS.lines[linesIndex].indexOf(")");
				if (n2 != -1) {
					try {
						bd = new BigDecimal(handPS.lines[linesIndex].substring(n + 2, n2));
					} catch (Exception e) {
						Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() uncalled $ ")
								.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
						hand.status = ParseCodes.UNCALLED_$_INDEX;
						return false;
					}
					// Now find name and seat
					n3 = handPS.lines[linesIndex].indexOf(" to ");
					if (n3 == 0) {
						Logger.logError(
								new StringBuilder().append("//ERROR anyStreetPlayerAction() Uncalled or returned to ")
										.append(linesIndex).append(" ").append(handPS.lines[linesIndex]).append(" ")
										.append(hand.handID).toString());
						hand.status = ParseCodes.UNCALLED_TO;
					}
					seat = getPlayerSeat(handPS.lines[linesIndex].substring(n3 + 4));
					if (seat == -1) {
						Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() uncalled seat  ")
								.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
						hand.status = ParseCodes.UNCALLED_TO_SEAT;
						return false;
					}
					if (n != -1) {
						// This is a walk if preflop orbit 1 BB and folded to BB then returns false
						// it is a normal hand.
					}
				}
			}
			betNow = betNow.subtract(bd);
			moneyInThisStreet[seat] = moneyInThisStreet[seat].subtract(bd);
			pot = pot.subtract(bd);
			stack[seat] = stack[seat].add(bd);
			defaultAction(street, seat, ACTION_UNCALLED_RETURNED_TO);
			hand.actionArray[actionIndex].money = bd;
			hand.actionArray[actionIndex].moneyIn = moneyInThisStreet[seat];
			hand.actionArray[actionIndex].betRaise = betNow;
			hand.actionArray[actionIndex].call = betNow;
			hand.actionArray[actionIndex].stack = stack[seat];
			hand.actionArray[actionIndex].pot = pot;
//			hand.actionArray[actionIndex].sidePot = sidePot;
			return true;
		}

		n3 = handPS.lines[linesIndex].indexOf(" collected ");
		if (n3 != -1) {
			n3 += 12;
			n2 = handPS.lines[linesIndex].indexOf(" from pot");
			if (n2 == -1) {
				Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() collected from pot ")
						.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
				hand.status = ParseCodes.COLLECTED_FROM_POT;
				return false;
			}
			try {
				bd = new BigDecimal(handPS.lines[linesIndex].substring(n3, n2));
			} catch (Exception e) {
				Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() uncalled $ ")
						.append(handPS.lines[linesIndex]).append(" ")
						.append(handPS.lines[linesIndex].substring(n + 1, n2)).append(" ").append(hand.handID)
						.toString());
				hand.status = ParseCodes.COLLECTED_FROM_POT_VALUE;
				return false;
			}
			// Now find name and seat
			n = handPS.lines[linesIndex].indexOf(" collected");
			seat = getPlayerSeat(handPS.lines[linesIndex].substring(0, n));
			if (seat == -1) {
				Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() uncalled seat  ")
						.append(handPS.lines[linesIndex]).append(" ").append(hand.handID).toString());
				hand.status = ParseCodes.COLLECTED_SEAT;
				return false;
			}
			n = handPS.lines[linesIndex].indexOf(" side");
			if (n != -1) {
				Logger.logError("//ERROR SIDE " + handPS.lines[linesIndex]);
				hand.status = ParseCodes.SIDE_INDEX;
				return false;
			}
			defaultAction(street, seat, ACTION_COLLECTED_STREET);
			hand.actionArray[actionIndex].money = bd;
			hand.actionArray[actionIndex].moneyIn = moneyInThisStreet[seat];
			hand.actionArray[actionIndex].betRaise = betNow;
			hand.actionArray[actionIndex].call = betNow;
			stack[seat] = stack[seat].add(bd);
			hand.actionArray[actionIndex].stack = stack[seat];
			pot = pot.subtract(bd);

			hand.actionArray[actionIndex].pot = pot;

//			hand.actionArray[actionIndex].sidePot = sidePot;
			return true;
		}

		if (handPS.lines[linesIndex].contains("doesn't show")) {
			hand.actionArray[actionIndex].action = ACTION_DOES_NOT_SHOW;
			ignoreLine = true;
			return true;
		}

		/*-
		PokerStars Hand #223245656967:  Hold'em No Limit ($1/$2 USD) - 2021/01/30 20:38:05 ET
		Table 'Aaltje II' 9-max Seat #6 is the button
		Seat 1: MASUR0N1KE ($204 in chips)
		Seat 2: MPH84 ($240.29 in chips)
		Seat 3: gkm0nk3y ($564.91 in chips)
		Seat 4: lucky83o ($200 in chips)
		Seat 5: Battul ($245.50 in chips)
		Seat 6: 19Sega92 ($214.52 in chips)
		MASUR0N1KE: posts small blind $1
		MPH84: posts big blind $2
		*** HOLE CARDS ***
		gkm0nk3y: folds
		lucky83o: folds
		Battul: folds
		19Sega92: folds
		MASUR0N1KE: folds
		Uncalled bet ($1) returned to MPH84
		MPH84 collected $2 from pot
		MPH84: doesn't show hand
		*** SUMMARY ***
		Total pot $2 | Rake $0
		Seat 1: MASUR0N1KE (small blind) folded before Flop
		Seat 2: MPH84 (big blind) collected ($2)
		Seat 3: gkm0nk3y folded before Flop (didn't bet)
		Seat 4: lucky83o folded before Flop (didn't bet)
		Seat 5: Battul folded before Flop (didn't bet)
		Seat 6: 19Sega92 (button) folded before Flop (didn't bet)
		//ERROR convertToHand()  anyStreetPlayerAction Preflop returned false NO_STREET_ACTION 2 11 2232456569670203805
		PokerStars Hand #223245656967:  Hold'em No Limit ($1/$2 USD) - 2021/01/30 20:38:05 ET
		Table 'Aaltje II' 9-max Seat #6 is the button
		Seat 1: MASUR0N1KE ($204 in chips)
		Seat 2: MPH84 ($240.29 in chips)
		Seat 3: gkm0nk3y ($564.91 in chips)
		Seat 4: lucky83o ($200 in chips)
		Seat 5: Battul ($245.50 in chips)
		Seat 6: 19Sega92 ($214.52 in chips)
		MASUR0N1KE: posts small blind $1
		MPH84: posts big blind $2
		*** HOLE CARDS ***
		gkm0nk3y: folds
		lucky83o: folds
		Battul: folds
		19Sega92: folds
		MASUR0N1KE: folds
		Uncalled bet ($1) returned to MPH84
		MPH84 collected $2 from pot
		MPH84: doesn't show hand
		*** SUMMARY ***
		Total pot $2 | Rake $0
		Seat 1: MASUR0N1KE (small blind) folded before Flop
		Seat 2: MPH84 (big blind) collected ($2)
		Seat 3: gkm0nk3y folded before Flop (didn't bet)
		Seat 4: lucky83o folded before Flop (didn't bet)
		Seat 5: Battul folded before Flop (didn't bet)
		Seat 6: 19Sega92 (button) folded before Flop (didn't bet)
		*/
		if (hand.status != ParseCodes.OK) {

			Logger.logError(new StringBuilder().append("//ERROR anyStreetPlayerAction() No action ")
					.append(" hand.status ").append(ParseCodes.CODES_ST[hand.status]).append("  ").append(hand.status)
					.append(hand.actionArray[actionIndex].action).append(" ").append(handPS.lines[linesIndex])
					.append(" ").append(hand.handID).toString());
			hand.status = ParseCodes.NO_STREET_ACTION;
			return false;
		}
		return true;
	}

	/*-   Print this hand. */
	private static void printHand(HandPS handPS) {
		for (int i = 0; i < handPS.numLines; ++i) {
			Logger.log("//" + handPS.lines[i]);
		}
	}

	/*-*************************************************************************************
	 * Get a players name for a seat number.
	 * Arg0 - Player name
	 * Returns seat number
	 * Returns -1 if player not found
	************************************************************************************* */
	private static int getPlayerSeat(String nam) {
		try {
			int x = Integer.valueOf(nam);
		} catch (Exception e) {
			System.out.print(e);
			handPS.printHand();
			Logger.logError(new StringBuilder().append("//ERROR player name invalid format ---").append(nam)
					.append("---").append(hand.handID).append("---").toString());
			hand.status = ParseCodes.PLAYER_NAME_NOT_FOUND;

			Crash.log("$$$ " + "");
			return -1;
		}

		int id = Integer.valueOf(nam);
		for (int i = 0; i < 6; ++i) {
			if (hand.IDArray[i] == id) {
				return i;
			}
		}
		Logger.logError(new StringBuilder().append("//ERROR player name or ID not found ---").append(nam).append("---")
				.append(" ").append(hand.handID).toString());
		hand.status = ParseCodes.PLAYER_NAME_NOT_FOUND;
		Crash.log("$$$ " + "");
		return -1;
	}

	/*-*************************************************************************************
	 * Checks for a valid handID
	 * Sets that value in hand.handID
	 * Returns an error code or hand.OK
	 * PokerStars Hand #223978588787:  Hold'em No Limit ($1/$2 USD) - 2021/02/19 4:23:43 ET
	 **************************************************************************************/
	private static int checkHandID() {
		if (handPS.lines[0] == null || handPS.lines[0].length() > 120 || handPS.lines[1].length() < 35) {
			hand.status = ParseCodes.INVALID_HAND_LENGTH;
			handPS.printHand();
			Logger.logError(new StringBuilder().append("//ERROR invalid hand length or hand in null ")
					.append(handPS.lines[0]).toString());
			return ParseCodes.INVALID_HAND_LENGTH;
		}
		hand.handID = getHandID(handPS.lines[0]);
		if (hand.handID == 0L) {
			hand.status = ParseCodes.INVALID_HAND_ID;
			return ParseCodes.INVALID_HAND_ID;
		}
		hand.status = ParseCodes.OK;
		return ParseCodes.OK;
	}

	/*-*************************************************************************************
	 * Returns a unique identification number for this hand
	 * Does not use Hand or HandPS. 
	 * Otherwise identical to  getHandID(HandPS handPS, Hand hand 
	* Arg0 - The first line of a Hand History hand
	 * 		PokerStars Hand #223978588787:  Hold'em No Limit ($1/$2 USD) - 2021/02/19 4:23:43 ET
	 * Returns a long hand ID
	 * 		Returns 0L if any error
	************************************************************************************* */
	static long getHandID(String header) {
		if (header.length() < 35 || header.length() > 116) {
			Logger.logError(new StringBuilder().append("//ERROR invalid hand length  ").append(header.length())
					.append("---").append(header).append("---").toString());
			return 0L;
		}
		n1 = header.indexOf('#');
		n2 = header.indexOf(':');
		if (n1 == -1 || n2 == -1) {
			Logger.logError(new StringBuilder().append("//ERROR invalid hand no hand number ").append(n1).append("---")
					.append(n2).append("---").append(header).append("---").toString());
			return 0L;
		}
		n3 = header.indexOf(':', n2 + 25) - 4;
		if ((n3 != -1)) {
			return getID(header);
		}
		Logger.logError(new StringBuilder().append("//ERROR invalid hand ID ").append(n3).append(" -").append(header)
				.append("---").append(st.substring(n2 + 25)).append("--- ").toString());
		return 0L;
	}

	/*-*************************************************************************************
	 * Create a unique hand ID number for this hand.
	 * Probably an over kill but:
	 * We start with the PokerStars hand number and append the day
	************************************************************************************* */
	private static long getID(String header) {
		StringBuilder sb = new StringBuilder();
		sb = sb.append(header.substring(n1 + 1, n2).trim());
		sb = sb.append(header.substring(n3, n3 + 1).trim());
		sb = sb.append(header.substring(n3 + 2, n3 + 4).trim());
		sb = sb.append(header.substring(n3 + 5, n3 + 7).trim());
		sb = sb.append(header.substring(n3 + 8, n3 + 10));
		Long l = Long.parseLong(sb.toString());
		return l;
	}

	/*-*************************************************************************************
	 * Initialize Action instance to default values
	************************************************************************************* */
	private static void defaultAction(int str, int sea, int act) {
		hand.actionArray[actionIndex].street = str;
		hand.actionArray[actionIndex].seat = sea;
		hand.actionArray[actionIndex].ID = hand.IDArray[sea];
		hand.actionArray[actionIndex].action = act;
		hand.actionArray[actionIndex].call = currentBet;
		hand.actionArray[actionIndex].betRaise = zeroBD;
		hand.actionArray[actionIndex].raiseTo = zeroBD;
		hand.actionArray[actionIndex].stack = stack[sea];
		hand.actionArray[actionIndex].pot = pot;
		hand.actionArray[actionIndex].money = zeroBD;
	}

	/*-	
	//PokerStars Hand #198636031317:  Hold'em No Limit ($1/$2 USD) - 2019/03/28 3:48:15 ET
	//Table 'Aaltje' 6-max Seat #3 is the button
	//Seat 1: Naebushkin ($450.06 in chips) 
	//Seat 2: Yaaaazz ($220.48 in chips) 
	//Seat 3: zijian0502 ($341.85 in chips) 
	//Seat 4: wbcrew8 ($212.45 in chips) 
	//Seat 5: toprmaw ($224.60 in chips) 
	//Seat 6: JoSsP1n ($242.49 in chips) 
	//wbcrew8: posts small blind $1
	//toprmaw: posts big blind $2
	//*** HOLE CARDS ***
	//JoSsP1n: raises $3 to $5
	//Naebushkin: folds 
	//Yaaaazz: raises $15 to $20
	//zijian0502: calls $20
	//wbcrew8: folds 
	//toprmaw: folds 
	//JoSsP1n: calls $15
	//*** FLOP *** [5h 6h Ah]
	//JoSsP1n: checks 
	//Yaaaazz: checks 
	//zijian0502: bets $60
	//JoSsP1n: raises $162.49 to $222.49 and is all-in
	//Yaaaazz: calls $200.48 and is all-in
	//zijian0502: folds 
	//Uncalled bet ($22.01) returned to JoSsP1n
	//*** FIRST TURN *** [5h 6h Ah] [8s]
	//*** FIRST RIVER *** [5h 6h Ah 8s] [Ks]
	//*** SECOND TURN *** [5h 6h Ah] [Tc]
	//*** SECOND RIVER *** [5h 6h Ah Tc] [9d]
	//*** FIRST SHOW DOWN ***
	//JoSsP1n: shows [9h Jh] (a flush, Ace high)
	//Yaaaazz: shows [Kh As] (two pair, Aces and Kings)
	//JoSsP1n collected $260.61 from pot
	//*** SECOND SHOW DOWN ***
	//JoSsP1n: shows [9h Jh] (a flush, Ace high)
	//Yaaaazz: shows [Kh As] (a pair of Aces)
	//JoSsP1n collected $260.60 from pot
	//*** SUMMARY ***
	//Total pot $523.96 | Rake $2.75 
	//Hand was run twice
	//FIRST Board [5h 6h Ah 8s Ks]
	//SECOND Board [5h 6h Ah Tc 9d]
	//Seat 1: Naebushkin folded before Flop (didn't bet)
	//Seat 2: Yaaaazz showed [Kh As] and lost with two pair, Aces and Kings, and lost with a pair of Aces
	//Seat 3: zijian0502 (button) folded on the Flop
	//Seat 4: wbcrew8 (small blind) fbefore Flop
	//Seat 5: toprmaw (big blind) folded before Flop
	//Seat 6: JoSsP1n showed [9h Jh] and won ($260.61) with a flush, Ace high, and won ($260.60) with a flush, Ace high
		 */
	/*-
	PokerStars Hand #233368388734:  Hold'em No Limit ($1/$2 USD) - 2022/01/22 6:14:27 ET
	Table 'Arethusa III' 6-max Seat #1 is the button
	Seat 1: happy0607 ($200 in chips) 
	Seat 2: AlexEliseev7 ($200.93 in chips) 
	Seat 3: Toriiiii ($220.69 in chips) 
	Seat 4: Da Latta ($264.08 in chips) 
	Seat 5: Greippi28 ($329.90 in chips) 
	Seat 6: Faradaay ($223.45 in chips) 
	AlexEliseev7: posts small blind $1
	Toriiiii: posts big blind $2
	*** HOLE CARDS ***
	Da Latta: folds 
	Greippi28: folds 
	Faradaay: folds 
	
	AlexEliseev7: folds 
	Toriiiii: calls $3
	*** FLOP *** [9d 8d Ts]
	Toriiiii: checks 
	happy0607: checks 
	*** TURN *** [9d 8d Ts] [Ac]
	Toriiiii: bets $8.05
	happy0607: calls $8.05
	*** RIVER *** [9d 8d Ts Ac] [9s]
	Toriiiii: checks 
	happy0607: bets $19.99
	Toriiiii: calls $19.99
	*** SHOW DOWN ***
	happy0607: shows [As Kc] (two pair, Aces and Nines)
	Toriiiii: mucks hand 
	happy0607 collected $64.33 from pot
	*** SUMMARY ***
	Total pot $67.08 | Rake $2.75 
	Board [9d 8d Ts Ac 9s]
	Seat 1: happy0607 (button) showed [As Kc] and won ($64.33) with two pair, Aces and Nines
	Seat 2: AlexEliseev7 (small blind) folded before Flop
	Seat 3: Toriiiii (big blind) mucked
	Seat 4: Da Latta folded before Flop (didn't bet)
	Seat 5: Greippi28 folded before Flop (didn't bet)
	Seat 6: Faradaay folded before Flop (didn't bet)
	
	Uncalled bet ($40.19) returned to nicofellow
	 *** FIRST FLOP *** [5s 5c 6s]
	 *** FIRST TURN *** [5s 5c 6s] [8h]
	 *** FIRST RIVER *** [5s 5c 6s 8h] [Ah]
	 *** SECOND FLOP *** [9c 4h 7d]
	 *** SECOND TURN *** [9c 4h 7d] [6c]
	 *** SECOND RIVER *** [9c 4h 7d 6c] [7c]
	 *** FIRST SHOW DOWN ***
	nicofellow: shows [Kh As] (two pair, Aces and Fives)
	Satan'sBingo: shows [Kd Ac] (two pair, Aces and Fives)
	nicofellow collected $136.39 from pot
	Satan'sBingo collected $136.39 from pot
	 *** SECOND SHOW DOWN ***
	nicofellow: shows [Kh As] (a pair of Sevens)
	Satan'sBingo: shows [Kd Ac] (a pair of Sevens)
	nicofellow collected $136.39 from pot
	Satan'sBingo collected $136.38 from pot
	 *** SUMMARY ***
	Total pot $548.30 | Rake $2.75 
	Hand was run twice
	FIRST Board [5s 5c 6s 8h Ah]
	SECOND Board [9c 4h 7d 6c 7c]
	Seat 1: Satan'sBingo showed [Kd Ac] and won ($136.39) with two pair, Aces and Fives, and won ($136.38) with a pair of SevensSeat 2: mike21_4life (button) folded before Flop (didn't bet)
	Seat 4: nicofellow (small blind) showed [Kh As] and won ($136.39) with two pair, Aces and Fives, and won ($136.39) with a pair of Sevens
	Seat 5: EBE_77 (big blind) folded before Flop
	Seat 6: vest78 folded before Flop (didn't bet)
	
	//Belqi: raises $292.32 to $310.44 and is all-in
	//Rietzel: calls $76.31 and is all-in
	//ludatil: calls $90.66 and is all-in
	//Uncalled bet ($201.66) returned to Belqi
	//*-* FLOP *** [9d 3h 5s]
	//*-* TURN *** [9d 3h 5s] [Kd]
	//*-* RIVER *** [9d 3h 5s Kd] [6h]
	//*-* SHOW DOWN ***
	//ludatil: shows [6c 5c] (two pair, Sixes and Fives)
	//Belqi: shows [Td Ts] (a pair of Tens)
	//ludatil collected $28.70 from side pot
	//Rietzel: shows [Ks Ah] (a pair of Kings)
	//ludatil collected $281.54 from main pot
	//*-* SUMMARY ***
	//Total pot $312.99 Main pot $281.54. Side pot $28.70. | Rake $2.75 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
	//Board [9d 3h 5s Kd 6h]
	//Seat 1: alesikStar (button) folded before Flop (didn't bet)
	//Seat 2: MatoStar14 (small blind) folded before Flop
	//Seat 3: ludatil (big blind) showed [6c 5c] and won ($310.24) with two pair, Sixes and Fives
	//Seat 4: Belqi showed [Td Ts] and lost with a pair of Tens
	//Seat 5: A.E.M.07 folded before Flop (didn't bet)
	//Seat 6: Rietzel showed [Ks Ah] and lost with a pair of Kings	
	 * 
	 * 
	 * 
	 *  * *** HOLE CARDS ***
	* 777Koresh777: folds 
	* Golden-darkk: folds 
	* RAidakCo: folds 
	* Redmaster91: calls $2
	* tiburon101: calls $1
	* C1awViper: checks 
	* *** FLOP *** [6c 9d Qh]
	* tiburon101: checks 
	* C1awViper: checks 
	* Redmaster91: bets $2
	* tiburon101: calls $2
	* C1awViper: folds 
	* *** TURN *** [6c 9d Qh] [Ac]
	* tiburon101: checks 
	* Redmaster91: bets $7.13
	* tiburon101: calls $7.13
	* *** RIVER *** [6c 9d Qh Ac] [5d]
	* tiburon101: checks 
	* Redmaster91: bets $26
	* tiburon101: folds 
	* Uncalled bet ($26) returned to Redmaster91
	* Redmaster91 collected $23.05 from pot
	* Redmaster91: doesn't show hand 
	* *** SUMMARY 
	* 
	 * 
	 */

}
