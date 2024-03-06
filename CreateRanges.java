package holdemhandhistory;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

public class CreateRanges implements Constants {

	static final String path = "C:\\Hold'emDatabase\\"; // Copied from Options temp fix
	static final int PLAYERS = 6; // Temp fix to avoid needing Options class

	/*-
	 * Preflop Range percentages by position. Percentages are based on the 1326
	 * possible combinations. There are 1326 combinations in a 13 X 13 range array.
	 * A 20% range would be 266 combinations. NOT 34 hands of the possible 169. A
	 * realistic percentage must be based on combinations.
	*/
	static double[] preflopOpen = new double[6];
	static double[] preflopLimp = new double[6];
	static double[] preflopCall = new double[6];

	static double[] preflop3Bet = new double[6];
	static double[] preflop2BetCall = new double[6];

	static double[] preflop4Bet = new double[6];
	static double[] preflop3BetCall = new double[6];

	static double[] preflopAllin = new double[6];
	static double[] preflop4BetCall = new double[6];

	static double[] preflopAllinCall = new double[6];

	/*-
	 * - Ranges
	 */
	static HandRange raiseRange = new HandRange();
	static HandRange callRange = new HandRange();

	static String filePath = "";

	static final String[] pos = { "SB", "BB", "UTG", "MP", "Cutoff", "Button" };
	static String fullPathRaise = "";
	static String fullPathCall = "";

	/*-
	 * Main - . 
	
	public static void main(String[] args) {
	
	
		final var x = new PlayerClassification();
	
		anyPlayer = x.readFromFile(path + "AllPlayerClassification.ser");
		getRangePercentages();
		filePath = path + "Average\\Preflop\\";
		createPreflopRanges();
		Logger.logError(new StringBuilder().append("Hand range files created OK ").append(path)
				.append("AllPlayerClassification.ser").toString());
	
		anyPlayer = x.readFromFile(path + "NitPlayerClassification.ser");
		getRangePercentages();
		filePath = path + "Nit\\Preflop\\";
		createPreflopRanges();
		Logger.logError("Hand range files created OK " + filePath);
	
		anyPlayer = x.readFromFile(path + "LagPlayerClassification.ser");
		getRangePercentages();
		filePath = path + "Lag\\Preflop\\";
		createPreflopRanges();
		Logger.logError("Hand range files created OK " + filePath);
	
		anyPlayer = x.readFromFile(path + "TagPlayerClassification.ser");
		getRangePercentages();
		filePath = path + "Tag\\Preflop\\";
		createPreflopRanges();
		Logger.logError("Hand range files created OK " + filePath);
	
		anyPlayer = x.readFromFile(path + "FishPlayerClassification.ser");
		getRangePercentages();
		filePath = path + "Fish\\Preflop\\";
		createPreflopRanges();
		Logger.logError("Hand range files created OK " + filePath);
	
		anyPlayer = x.readFromFile(path + "RegPlayerClassification.ser");
		getRangePercentages();
		filePath = path + "Reg\\Preflop\\";
		createPreflopRanges();
		Logger.logError("Hand range files created OK " + filePath);
	
		anyPlayer = x.readFromFile(path + "WinnerPlayerClassification.ser");
		getRangePercentages();
		filePath = path + "Winner\\Preflop\\";
		createPreflopRanges();
		Logger.logError("Hand range files created OK " + filePath);
	
		anyPlayer = x.readFromFile(path + "LooserPlayerClassification.ser");
		getRangePercentages();
		filePath = path + "Looser\\Preflop\\";
		createPreflopRanges();
		Logger.logError("Hand range files created OK " + filePath);
	
	}
	
	/*-
	 * Get HandRange range percentages.
	 * The basic information is in PlayerClassification Class instances.
	 */
	private static void getRangePercentages() {
		/*- TODO
		}
		for (int i = 0; i < PLAYERS; ++i) {
			preflopOpen[i] = anyPlayer.bet2PerPos[0][i];
			preflopLimp[i] = anyPlayer.limpPerPos[0][i];
			if (preflopLimp[i] < preflopOpen[i]) {
				Logger.logError("limp " + preflopLimp[i]);
				preflopLimp[i] = preflopOpen[i] + preflopLimp[i] + 12; // TODO
			}
		
			preflop3Bet[i] = anyPlayer.bet3PerPos[0][i];
			preflopCall[i] = anyPlayer.callBet2PerPos[0][i];
			if (preflopCall[i] < preflop3Bet[i]) {
				Crash.log("$$$");
			}
			preflop4Bet[i] = anyPlayer.bet4PerPos[0][i];
			preflop3BetCall[i] = anyPlayer.callBet3PerPos[0][i];
			if (preflop3BetCall[i] < preflop4Bet[i]) {
				Crash.log("$$$");
			}
		
			preflopAllin[i] = anyPlayer.allinPerPos[0][i];
			preflop4BetCall[i] = anyPlayer.callBet4PerPos[0][i];
			if (preflop4BetCall[i] < preflopAllin[i]) {
				Crash.log("$$$");
			}
		
			preflopAllinCall[i] = anyPlayer.callAllinPerPos[0][i];
		}
		 */
	}

	/*-
	 *  Create Preflop Ranges for Average player type.
	 */
	private static void createPreflopRanges() {
		for (int i = 0; i < 6; ++i) {
			create1BetRanges(i, preflopOpen[i], preflopLimp[i]);
			create2BetRanges(i, preflop3Bet[i], preflopCall[i]);
			create3BetRanges(i, preflop4Bet[i], preflop3BetCall[i]);
			create4BetRanges(i, preflopAllin[i], preflop4BetCall[i]);
			createAllinBetRanges(i, preflopAllin[i]);
		}
	}

	/*- 
	 * Create Ranges for the bet a preflop player is facing, 1 Bet ( Limp ), 2
	 * Bet, 3 Bet, 4 Bet, Allin. 
	 * We do 2 Ranges at a time for a bet size, the Raise  or Open Range and the Call Range. 
	 * For 2 Bet, we do a 3 Bet Raise Range and a 2 Bet Call Range.
	 * 
	 * This is relatively complicated because of the shadow effect. A call range
	 * will not include hands that are in the open range. This is because the Open
	 * range is evaluated first and if there is an open then the Call rang will not
	 * be checked. If no open, then the Call range is checked next. Hands that were
	 * in the open range will never be actually used in the Call range.
	 * 
	 * Example 1: 
	 * The bet to the player is 1 Bet ( limp ). Open includes AA, AKs,
	 * and AKo + several other strong hands. Limp includes 66, 55, 44, + weaker
	 * hands. If the hole cards are AA then the player will always open. If the hole
	 * cards are 66 then the player will never open but will always Limp. If the
	 * Limp range included AA it would never actually be used. Some would argue that
	 * the Ranges will be different for a loose player or a very tight player, but
	 * that is actually a different set of ranges. Hold'em can have multiple
	 * ranges based on condition or can dynamically change existing ranges by
	 * increasing or decreasing by some percentage or combinations. We have chosen
	 * to not have duplicate hands in 2 ranges facing the same bet. In the example
	 * above.
	 * 
	 * Example 2: 
	 * The bet to the player is 2 Bet ( limp ). Bet includes AA, AKs, and
	 * AKo + several other strong hands. Call 3 Bet includes TT, 99, 88 + weaker
	 * hands. If the hole cards are AA then the player will always Raise to a 3 Bet.
	 * If the hole cards are 99 then the player will never open but will always
	 * Call. If the Call 3 Bet range included AA it would never actually be used.
	 * There are also cases where the decision may be to bluff ( based on a MDF
	 * percentage ) but there is no practical way to include that in a hand range.
	 * In that case Hold'em will check the 3 Bet range first, then before Call 3
	 * Bet will bluff randomly. We have chosen to not have duplicate hands in 2
	 * ranges facing the same bet. In the example above.
	 * 
	 * Example 2 can be easily extended for facing a 3 Bet, a 4 Bet, or Allin
	 * 
	 * Another consideration is range percentages. Calculated by using combinations
	 * for each hand. There are 1326 combinations in a 13 X 13 range array. A 20%
	 * range would be 266 combinations. NOT 34 hands of the possible 169. A
	 * realistic percentage must be based on combinations.
	 * 
	 * When eveluating an opponents range the shadow effect must be considered. For
	 * example: If you are trying to estimate an opponents range while playing the
	 * Flop and he called a 2 Bet and his range preclop for the call is 17%, it
	 * certainly did not include AA. He would have raised with that. So his range
	 * after call is a condensed range, not polatized. Raise hands are removed from
	 * the calling range. We establish the average ranges by processsing millions of
	 * Hand History hands from real players. Currently only 6-max, $1-$2, at
	 * PokerStars. Later, we will include other sites and other stakes, For now,
	 * most of the sites are pretty close to each other so the PokerStars Hand
	 * histories are very useful for almost any site.
	 * 
	 * arg0 is the position - 0 SB, 5 Button 
	 * arg1 is the Raise range as a percentage of combinations. arg1 is the Call range 
	 * as a percentage of combinations and will exclude the hands in the Raise range.
		 */

	private static void create1BetRanges(int position, double raise, double call) {
		fullPathRaise = new StringBuilder().append(filePath).append(pos[position]).append("\\Open.ser").toString();
		fullPathCall = new StringBuilder().append(filePath).append(pos[position]).append("\\Limp.ser").toString();
		raiseRange.buildRangePercentage(raise);
		callRange.buildRangePercentage(call);
		callRange.subtractRange(raiseRange);
		raiseRange.writeRange(fullPathRaise);
		callRange.writeRange(fullPathCall);
	}

	private static void create2BetRanges(int position, double raise, double call) {
		fullPathRaise = new StringBuilder().append(filePath).append(pos[position]).append("\\Bet3.ser").toString();
		fullPathCall = new StringBuilder().append(filePath).append(pos[position]).append("\\Call.ser").toString();
		raiseRange.buildRangePercentage(raise);
		callRange.buildRangePercentage(call);
		callRange.subtractRange(raiseRange);
		raiseRange.writeRange(fullPathRaise);
		callRange.writeRange(fullPathCall);

	}

	private static void create3BetRanges(int position, double raise, double call) {
		fullPathRaise = new StringBuilder().append(filePath).append(pos[position]).append("\\Bet4.ser").toString();
		fullPathCall = new StringBuilder().append(filePath).append(pos[position]).append("\\Bet3Call.ser").toString();
		raiseRange.buildRangePercentage(raise);
		callRange.buildRangePercentage(call);
		callRange.subtractRange(raiseRange);
		raiseRange.writeRange(fullPathRaise);
		callRange.writeRange(fullPathCall);
	}

	private static void create4BetRanges(int position, double raise, double call) {
		fullPathRaise = new StringBuilder().append(filePath).append(pos[position]).append("\\Allin.ser").toString();
		fullPathCall = new StringBuilder().append(filePath).append(pos[position]).append("\\Bet4Call.ser").toString();
		raiseRange.buildRangePercentage(raise);
		callRange.buildRangePercentage(call);
		callRange.subtractRange(raiseRange);
		raiseRange.writeRange(fullPathRaise);
		callRange.writeRange(fullPathCall);

	}

	private static void createAllinBetRanges(int position, double call) {
		fullPathCall = new StringBuilder().append(filePath).append(pos[position]).append("\\AllinCall.ser").toString();
		callRange.buildRangePercentage(call);

		callRange.writeRange(fullPathCall);
	}

}
