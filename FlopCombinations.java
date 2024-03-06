package holdemhandhistory;
/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class FlopCombinations {
	/*- 
	 * This class is used to generate flops that represent all possible flops. It
	 * has a method to generate flops and a method to look find the index into the
	 * flop array, Speed is important when doing a simulation.
	 * 
	 * There are 1,755 different flops. This asumes that order is not important. For exalmple
	 * AKQ and AQK are exactly the same. It also assumes that the actual suit in not important. 
	 * For example As Ks Qs are exactly the same as Ad Kd Qd or Ad Qd Kd. No difference at all.
	 * 
	 * 3 of a kind, AAA, 222,... 13  
	 * A pair with 3rd card suited with eiter of the pair cards, AdAs2d, AdAs3d... 13 x 12 = 156  
	 * A pair + 3rd card, all offsuit, same as above, 156  
	 * All suited, 13 x 12 x 11/(3 x 2) = 286  
	 * All offsuit, items 1-3 excluded = 286 
	 * 2 cards suited, 3rd offsuit, 2 lowest suited = 286
	 * 2 cards suited, 3rd offsuit, 2 highest suited = 286
	 * 2 cards suited, 3rd offsuit, low and high suited = 286
	 *	Total 13+156+156+286+286+286 _ 286 + 286 =1755 
	 */
	private static final Timer timer = new Timer();
	static boolean doFlop = true;
	static boolean doTurn = true;
	static boolean doRiver = true;
	static HandRange range = new HandRange();
	private static final int[][] flop = new int[1755][3]; // Value + suit + offset
	private static final int[] flopy = new int[1755];
	private static int flopIndex;
	private static final double[][] values = new double[169][1755];
	private static final int SPADE = 0;
	private static final int CLUB = 1;
	private static final int DIAMOND = 2;
	/*-
	 * 
	 * Constants Used for player actons all streets only 
	 * Not the same as Table class or Players Class
	 * 
	 */
	private static final int STREETS = 4;
	// Bet to me -
	// These values are also used as array index so need to be 0 - 4
	private static final int BET1 = 0;
	private static final int BET2 = 1;
	private static final int BET3 = 2;
	private static final int BET4 = 3;
	private static final int ALLIN = 4;
	private static final int BETS_MAX = 5;
	private static final int CHECK = 5;
	private static final int FOLD = 6;
	private static final int BETS_MAX_CLEAN = 7;
	private static final int RAISE = 7;
	private static final int BET = 8;
	private static final int NA = 9;
	private static final int LIMP = 10;
	private static final int OPEN = 11;
	private static final int CALL = 12;
	private static final int CALLBET1 = 13;
	private static final int CALLBET2 = 14;
	private static final int CALLBET3 = 15;
	private static final int CALLBET4 = 16;
	private static final int CALLALLIN = 17;
	private static final int ANTE = 18;
	// Values for relative positions
	private static final int FIRST = 0;
	private static final int FIRSTHU = 1;
	private static final int MIDDLE = 2;
	private static final int MIDDLE2 = 2;
	private static final int MIDDLE3 = 2;
	private static final int LAST = 3;
	private static final int LASTHU = 4;
	private static final int FISH = 0;
	private static final int NIT = 1;
	private static final int LAG = 2;
	private static final int TAG = 3;
	private static final int REG = 4;
	private static final int WINNER = 5;
	private static final int WIN = WINNER;
	private static final int LOOSER = 6;
	private static final int LOOSE = LOOSER;
	private static final int ALL = 7;
	private static final int HERO = 8;
	private static final int HEROX = 9;
	private static final int AVERAGE = 10;
	private static final int AVERAGEX = 11;
	private static final int NUM_TYPES = 12;
	private static final int PREFLOP = 0;
	private static final int FLOP = 1;
	private static final int TURN = 2;
	private static final int RIVER = 3;
	private static final int SHOWDOWN = 4;
	private static final int SUMMARY = 5;
	private static final int SB = 0;
	private static final int BB = 1;
	private static final int UTG = 2;
	private static final int MP = 3;
	private static final int CUTOFF = 4;
	private static final int CO = 4;
	private static final int BUTTON = 5;
	private static final int BU = 5;
	private static final int NUM_POS = 6; // Positions
	private static final int NUM_RP = 5; // Relative positions
	private static final int NUM_ORBITS = 4;
	private static final int NUM_STREETS = 4;
	private static final int PLAYERS = 6;
	/*-
	 * Hands
	 */
	static final int NONE = 0; // Outs 6
	static final int OVERCARDS = 1; // Outs 3
	static final int ACE_HIGH = 2; // Outs 2
	static final int WEAK_PAIR = 3; // Outs 2
	static final int MIDDLE_PAIR = 4; // Outs 2
	static final int POCKET_PAIR_BELOW_TP = 5; // Outs 2
	// If a player is holding a pocket pair which is higher ranking than any card on
	// the board Outs 2.
	static final int OVER_PAIR = 6;
	// If one of your hole cards pair with the highest ranking card on the board
	// Outs 2.
	static final int TOP_PAIR = 7; // Outs 4
	static final int GUTSHOT = 8; // Outs 5
	static final int GUTSHOT_HIGH = 9; // Outs 6
	static final int GUTSHOT_PAIR = 10; // Outs 8
	static final int OESD = 11; // Outs 9
	static final int FLUSH_DRAW = 12; // Outs 10
	static final int OESD_PAIR = 13; // Outs 11
	static final int FLUSH_DRAW_PAIR = 14; // Outs 13
	static final int FLUSH_DRAW_GUTSHOT = 15; // Outs 17
	static final int FLUSH_DRAW_OESD = 16; // Outs = 15
	/*- - Does not include board pair. */
	static final int TWO_PAIR = 17; // Outs = 4
	// Set
	// if pocket pair Outs =1 + 6 (2 board cards * 3)
	// If one hole card paired, board pair, outs = 1(board pair) + 3(hole card) + 3
	// (unpaired board card)
	static final int THREE_OF_A_KIND = 18; // Outs = 7
	// No outs hand is already made
	static final int STRAIGHT = 19;
	static final int FLUSH = 20;
	static final int FULL_HOUSE = 21;
	static final int FOUR_OF_A_KIND = 22;
	static final int STRAIGHT_FLUSH = 23;
	static final int ROYAL_FLUSH = 24;
	static final int MAX_HANDS = 25;
	static final String[] valuesSt = { "None", "Overcards", "Ace High", "Weak Pair", "Middle Pair",
			"Pocket Pair Below Top", "Over Pair ", "Top Pair", "Gutshot", "Gutshot High", "Gutshot Draw Pair",
			"Straight Draw", "Flush Draw", "Straight Draw Pair", "Flush Draw Pair", "Flush Draw Gutshot",
			"Flush Draw OESD", "Two Pair", "Three of a kind", "Straight", "Flush", "Full House ", "Four of a kind",
			"Straight Flush ", "Royal Flush" };
	/*- - - Board analysis. */
	static final int BOARD_DRY = 0;
	static final int BOARD_WET = 1;
	static final int BOARD_NOT_WET_DRY = 2;
	static final int BOARD_STATIC = 3;
	static final int BOARD_DYNAMIC = 4;
	static final int BOARD_NOT_STATIC_DYNAMIC = 5;
	static final int BOARD_RAINBOW = 6;
	static final int BOARD_ACE_HIGH = 7;
	static final int BOARD_HIGH_CARD = 8;
	static final int BOARD_PAIR = 9;
	static final int BOARD_2PAIR = 10;
	static final int BOARD_SET = 11;
	static final int BOARD_QUADS = 12;
	static final int BOARD_GAP0 = 13;
	static final int BOARD_GAP1 = 14;
	static final int BOARD_GAP2 = 15;
	static final int BOARD_2F = 16;
	static final int BOARD_3F = 17;
	static final int BOARD_4F = 18;
	static final int BOARD_5F = 19;
	static final int BOARD_ARRAY_SIZE = 20;
	static final int BOARD_HHH = 0;
	static final int BOARD_HHM = 1;
	static final int BOARD_HHL = 2;
	static final int BOARD_HMM = 3;
	static final int BOARD_HML = 4;
	static final int BOARD_HLL = 5;
	static final int BOARD_MMM = 6;
	static final int BOARD_MML = 7;
	static final int BOARD_MLL = 8;
	static final int BOARD_LLL = 9;
	static final int HML_SIZE = 10;
	static final int MAXFOLDED = PLAYERS - 1;
	/*- - Max rounds on a street. */
	static final int ORBITS = 4;
	/*- - Streets. */
	static final int STREET = 4; // Number of streets
	/*- - Positions. */
	static final int POSITIONS = 6;
	private static final String[] HMLSt = { "HHH", "HHM", "HHL", "HMM", "HML", "HLL", "MMM", "MML", "MLL", "LLL" };
	private static final int FLOP_COMBO = 1755;
	/*- *********************************************************************************************
	 
	 * Data in this section is used to analyze an opponent.
	 * It is not just for Hero, it is for all players.
	 * Some classes such as PlayABC develop a strategy for any player with that option enabled.
	 * Developing a strategy for opponents is important because it makes opponent play  
	 * simulation much more accurate and as a result improves Hero strategy,
	 * 
	 * Data is here in the Table Class for performance reasons and because multiple classes will 
	 * use this data. Performance because it is more efficient to just record things as they happen
	 * then to reconstruct later.
	 * 
	 * Strategy date names all begin with stgy_
	 */
	/*- Opponent actions */
	static final int STGY_OP_CHECK = 0;
	public static final int STGY__BET1 = 1;
	public static final int STGY__BET2 = 2;
	static final int STGY__BET3 = 3;
	static final int STGY__BET4 = 4;
	static final int STGY__ALLIN = 5;
	static final int STGY__C_BET = 6;
	static final int STGY__MIN_BET = 7;
	static final int STGY__ACTIONS = 8;
	/*- My actions */
	static final int STGY_CHECK = 0;
	static final int STGY_BET = 1;
	static final int STGY_CALL = 2;
	static final int STGY_ALLIN = 3;
	static final int STGY_NO_ACTION = 4;
	static final int STGY_FOLD = 5;
	static final int STGY_MIN_BET = 6;
	/*- 
	  * 
	  * Bet size relative to pot before bet or raise.
	  * At or below.
	  * Example POT_3Q is  between POT_2Q ( 1/2 pot ) and POT_3Q ( 3/4 pot and below ) 
	   * 
	  */
	static final int STGY_POT_1Q = 0;
	static final int STGY_POT_2Q = 1;
	static final int STGY_POT_3Q = 2;
	static final int STGY_POT_4Q = 3;
	static final int STGY_POT_5Q = 4;
	static final int STGY_POT_6Q = 5;
	static final int STGY_POT_7Q = 6;
	static final int STGY_POT_8Q = 7;
	static final int STGY_POT_ALLIN = 8;
	static final int STGY_POT_MAX = 9;
	static final int DRY = 0;
	static final int WET = 1;
	static final int NOT_WET_DRY = 2;
	static final int WETDRY_MAX = 3;
	static final int STATIC = 0;
	static final int DYNAMIC = 1;
	static final int NOT_STATIC_DYNAMIC = 2;
	static final int STATICDYNAMIC_MAX = 3;
	// HML = high, low and medium
	private static final int H = 2;
	private static final int M = 1;
	private static final int L = 0;
	static final BigDecimal zeroBD = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	// Card value + suit offset
	private static int c1 = -1;
	private static int c2 = -1;
	private static int c3 = -1;
	private static final int h1 = -1;
	private static final int h2 = -1;
	private static int c1Value;
	private static int c1Suit;
	private static int c2Value;
	private static int c2Suit;
	private static int c3Value;
	private static int c3Suit;
	/*- Indexes */
	private static int i1 = 0;
	private static int i2 = 0;
	private static int i3 = 0;
	private static int x1 = 0;
	private static int x2 = 0;
	private static int t = 0;
	/*- groupings for 286 indexes */

	private static final int[] group1 = { 66, 121, 166, 202, 230, 251, 266, 276, 282, 285, 286, 286, 286, 286 };
	private static final int[] group2 = { 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 0, 0 };
	// Indexes
	private static int setCount;
	private static int pairsSuitedCount;
	private static int pairsOffsuitCount;
	private static int suitedCount;
	private static int offsuitCount;
	private static int suited2LowCount;
	private static int suited2HighCount;
	private static int suited2LowHighCount;
	// Indexes for larger groups than 13 to avoid doing a search - performance
	// Index is card index
	private static final int[] suitedPairsIndexes = new int[13];
	private static final int[] offsuitPairsIndexes = new int[13];
	// Indexes are card indexes - the 3 cards in flop
	private static final int[][] suitedIndexs = new int[13][13];
	private static final int[][] offsuitIndexs = new int[13][13];
	private static final int[][] suited2LowIndexes = new int[13][13];
	private static final int[][] suited2HighIndexes = new int[13][13];
	private static final int[][] suited2LowHighIndexes = new int[13][13];
	static int hand;
	private static int handValue;
	static int p;
	private static final boolean createFlop = true; // if false only create indexes not flop
	private static int pp = 0;

	FlopCombinations() {

	}

	/*-
	 * 
	 * Public interface to look up flop index
	 * Look up index for flop.
	 * Arg0 - Card 1 of flop
	 * Arg1- Card 2 of flop
	  * Arg2 - Card 3 of flop
	 * Returns int value that is index into array of all possible flops ( 1755 )
	  * 
	 */
	static int getFlopIndex(Card card1, Card card2, Card card3) {

		final int $ = lookupFlop(card1, card2, card3);

		if ($ < 0) {
			// getFlopIndex -1 As-Ah-Ad
			System.out.println(new StringBuilder().append("//getFlopIndex ").append($).append(" ").append(card1)
					.append("-").append(card2).append("-").append(card3).toString());
			// Crash.log("$$$");[99]=0;
		}
		return $;
	}

	/*-
	* Public interface to look up HML index
	 * Look up index for flop.
	 * Arg0 - Card 1 of flop
	 * Arg1- Card 2 of flop
	  * Arg2 - Card 3 of flop
	 * Returns int value that is index into array flops based on H M L
	 */
	static int getHMLIndex(String card1, String card2, String card3) {
		final var c1 = new Card(card1);
		final var c2 = new Card(card2);
		final var c3 = new Card(card3);
		final int $ = HMLBoard(c1, c2, c3);
		if (p < 20) {
			++p;
			// System.out.println("//getHMLIndex " + $ + " -"+ card1 + "-" + card2 + "-" +
			// card3 );
		}
		if ($ < 0) {
			Crash.log("$$$");
		}
		return $;
	}

	static int getHMLIndex(Card card1, Card card2, Card card3) {
		return HMLBoard(card1, card2, card3);
	}

	/*-
	 * 
	 * Public interface to look evaluating flop board
	 * The actual calculations are performed in the Evaluate Class.
	 * Global variables in Evaluate are the result of the flop board evaluation.
	 * 
	 * TODO Need to think this through. 
	 * TODO do we use a wetDry index
	  * TODO do we use a static dynamic  index
	 * TODO do we use evalArray for possible draws?
	 * TODO 
	 * 
	 * 	static final int BOARD_DRY = 0;
	* static  final int BOARD_WET = 1;
	* static  final int BOARD_NOT_WET_DRY = 2;
	* static  final int BOARD_STATIC = 3;
	* static  final int BOARD_DYNAMIC = 4;
	* static  final int BOARD_NOT_STATIC_DYNAMIC = 5;
	* static  final int BOARD_RAINBOW = 6;
	* static  final int BOARD_ACE_HIGH = 7;
	* static  final int BOARD_HIGH_CARD = 8;
	* static  final int BOARD_PAIR = 9;
	* static  final int BOARD_2PAIR = 10;
	* static  final int BOARD_SET = 11;
	* static  final int BOARD_QUADS = 12;
	* static  final int BOARD_GAP0 = 13;
	* static  final int BOARD_GAP1 = 14;
	* static  final int BOARD_GAP2 = 15;
	* static  final int BOARD_2F = 16;
	* static  final int BOARD_3F = 17;
	* static  final int BOARD_4F = 18;
	* static  final int BOARD_5F = 19;
	* static  final int BOARD_ARRAY_SIZE = 20;
	* 
	* 	static final int NONE = 0; // Outs 6
	* static  final int OVERCARDS = 1; // Outs 3
	* static  final int ACE_HIGH = 2; // Outs 2
	* static  final int WEAK_PAIR = 3; // Outs 2
	* static  final int MIDDLE_PAIR = 4; // Outs 2
	* static  final int POCKET_PAIR_BELOW_TP = 5; // Outs 2
	* // If a player is holding a pocket pair which is higher ranking than any card on
	* // the board Outs 2.
	* static  final int OVER_PAIR = 6;
	* // If one of your hole cards pair with the highest ranking card on the board
	* // Outs 2.
	* static  final int TOP_PAIR = 7; // Outs 4
	* static  final int GUTSHOT = 8; // Outs 5
	* static  final int GUTSHOT_HIGH = 9; // Outs 6
	* static  final int GUTSHOT_PAIR = 10; // Outs 8
	* static  final int OESD = 11; // Outs 9
	* static  final int FLUSH_DRAW = 12; // Outs 10
	* static  final int OESD_PAIR = 13; // Outs 11
	* static  final int FLUSH_DRAW_PAIR = 14; // Outs 13
	* static  final int FLUSH_DRAW_GUTSHOT = 15; // Outs 17
	* static  final int FLUSH_DRAW_OESD = 16; // Outs = 15
	*  - Does not include board pair. 
	* static  final int TWO_PAIR = 17; // Outs = 4
	* // Set
	* // if pocket pair Outs =1 + 6 (2 board cards * 3)
	* // If one hole card paired, board pair, outs = 1(board pair) + 3(hole card) + 3
	* // (unpaired board card)
	* static  final int THREE_OF_A_KIND = 18; // Outs = 7
	* // No outs hand is already made
	* static  final int STRAIGHT = 19;
	* static  final int FLUSH = 20;
	* static  final int FULL_HOUSE = 21;
	* static  final int FOUR_OF_A_KIND = 22;
	* static  final int STRAIGHT_FLUSH = 23;
	* static  final int ROYAL_FLUSH = 24;
	* static  final int MAX_HANDS = 25;
	 * 
	 * Arg0 - Card 1 of flop
	 * Arg1- Card 2 of flop
	  * Arg2 - Card 3 of flop
	 * Returns int value that is index into array of all possible flops ( 1755 )
	  * 
	 */
	static void doBoardEvaluation(String card1, String card2, String card3) {
		final var c1 = new Card(card1);
		final var c2 = new Card(card2);
		final var c3 = new Card(card3);
		// TODO Evaluate.evaluateBoardForFlopCombinations(c1, c2, c3);
	}

	/*-
	 * 
	 * wet, dry, neither
	 * Get results of board analysis by Evaluate Class.
	 * Must have called doBoardEvaluation first!
	 * Return 0 wet, 1 dry, 2 not wet or dry, -1 error
	 * 
	 */
	static int getWetDry(String card1, String card2, String card3) {
		final int wetDry = 0;
		// TODO if (Evaluate.boardArray[Evaluate.BOARD_WET])
		// TODO wetDry = 0;
		// TODO else
		// TODO if (Evaluate.boardArray[Evaluate.BOARD_DRY])
		// TODO wetDry = 1;
		// TODO else
		// TODO if (Evaluate.boardArray[Evaluate.BOARD_NOT_WET_DRY])
		// TODO wetDry = 2;
		return wetDry;
	}

	/*-
	 * 
	 * static, dynamic, neither
	 * Get results of board analysis by Evaluate Class.
	 * Must have called doBoardEvaluation first!
	 * Return 0 static, 1dynamic,  2 not static or dynamic, -1 error
	 * 
	 */
	static int getStaticDynamic(String card1, String card2, String card3) {
		int staticDynamic = 0;
		// TODO if (Evaluate.boardArray[Evaluate.BOARD_STATIC])
		// TODO staticDynamic = 0;
		// TODO if (Evaluate.boardArray[Evaluate.BOARD_DYNAMIC])
		// TODO staticDynamic = 1;
		// TODO //TODO if (Evaluate.boardArray[Evaluate.BOARD_NOT_STATIC_DYNAMIC])
		staticDynamic = 2;
		return staticDynamic;
	}

	/*-
	 * 
	 * Draws
	 * Get results of board analysis by Evaluate Class.
	 * Must have called doBoardEvaluation first!
	 * Return ?, -1 error
	 * 
	 * static  final int BOARD_RAINBOW = 6;
	* static  final int BOARD_ACE_HIGH = 7;
	* static  final int BOARD_HIGH_CARD = 8;
	* static  final int BOARD_PAIR = 9;
	* static  final int BOARD_2PAIR = 10;
	* static  final int BOARD_SET = 11;
	* static  final int BOARD_QUADS = 12;
	* static  final int BOARD_GAP0 = 13;
	* static  final int BOARD_GAP1 = 14;
	* static  final int BOARD_GAP2 = 15;
	* static  final int BOARD_2F = 16;
	* static  final int BOARD_3F = 17;
	* static  final int BOARD_4F = 18;
	* static  final int BOARD_5F = 19;
	* 
	static final int POSSIBLE_NONE = 0;
	static final int POSSIBLE_TWO_PAIR = 1;
	static final int POSSIBLE_GUTSHOT = 2;
	static final int POSSIBLE_GUTSHOT_HIGH = 3;
	static final int POSSIBLE_GUTSHOT_PAIR = 4;
	static final int POSSIBLE_FLUSH_DRAW = 5;
	static final int POSSIBLE_OESD = 6;
	static final int POSSIBLE_OESD_PAIR = 7;
	static final int POSSIBLE_FLUSH_DRAW_PAIR = 8;
	static final int POSSIBLE_FLUSH_DRAW_GUTSHOT = 9;
	static final int POSSIBLE_FLUSH_DRAW_OESD = 10;
	static final int POSSIBLE_STRAIGHT = 11;
	static final int POSSIBLE_FLUSH = 12;
	static final int POSSIBLE_FULL_HOUSE = 13;
	static final int POSSIBLE_FOUR_OF_A_KIND = 14;
	static final int POSSIBLE_STRAIGHT_FLUSH = 15;
	static final int POSSIBLE_ROYAL_FLUSH = 16;
	static final int POSSIBLE_MAX = 17;
	
	 * 
	 */

	/*-
	 * 
	 * Returns the maximum possible draw as an int, index into array.
	 * 	static boolean[] boardPossibleArrayFlop = new boolean[POSSIBLE_MAX];
	 * 
	 * @return
	 */
	static int getPossibleDrawsIndex(String card1, String card2, String card3) {
		// TODO return Evaluate.boardPossibleArrayFlopMax;
		return -1;
	}

	static int getPossibleDrawsIndex(String card1, String card2, String card3, String card4) {
		// TODO return Evaluate.boardPossibleArrayTurnMax;
		return -1;
	}

	static int getPossibleDrawsIndex(String card1, String card2, String card3, String card4, String card5) {
		// TODO return Evaluate.boardPossibleArrayRiverMax;
		return -1;
	}

	/*- Test */
	static void test0() {
		makeFlop();
		for (int i = 0; i < 1755; ++i) {
			getFlopCardsFromArrayPlaceInTableFlop();
			// TODO int ndx = lookupFlop(Table.card1, Table.card2, Table.card3);
			// TODO if (ndx != i) {
			// TODO System.out.println(
			// TODO "//Error lookupFlop " + ndx + " " + i + " " + Table.card1.toString() +
			// Table.card2.toString()
			// TODO + Table.card3.toString() + " " + flop[i][0] + " " + flop[i][1] + " " +
			// flop[i][2]);
			++pp;
			if (pp > 12) {
				Crash.log("$$$");
			}
		}
		// TODO }
		for (int i = 0; i < 1755; ++i) {
			lookupFlopForIndex(i);
		}
	}

	/*- Test Evaluate */
	static void test2() {
		evaluateOneHandFlop(33, 33);
		printEvalArray();
		evaluateOneBoardFlop(33);
		printBoardArray();
		printHMLArray();
		printGapScores();

		evaluateOneHandFlop(45, 45);
		printEvalArray();
		evaluateOneBoardFlop(45);
		printHMLArray();
		printGapScores();

		evaluateOneHandFlop(66, 66);
		printEvalArray();
		evaluateOneBoardFlop(66);
		printHMLArray();
		printGapScores();

	}

	/*- Test Evaluate */
	static void test() {
		final var pattern = "##.#";
		final var decimalFormat = new DecimalFormat(pattern);
		long startTime0 = 0;
		long startTime1 = 0;
		long startTime2 = 0;
		long startTime3 = 0;
		long startTime4 = 0;
		// Number of times hit
		final int[] boardArrayFlop = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
				0, 0, 0 };
		final String[] gapSt = { "gap0 ", " gap1 ", " gap2 ", " gap3 " };
		final String[] scoreSt = { "gap0Score ", " gap1Score ", " gap2Score ", " gap3Score ", " gap1Count ", " count" };

		final int boardCountFlop = 0;
		startTime0 = System.nanoTime();
		startTime1 = System.nanoTime();

		// TODO
		// For every flop evaluate then see what hits counts resulted Flop
		/*-
		for (int i = 0; i < 1755; ++i) {
			evaluateOneBoardFlop(i);
		
			for (int j = 0; j < Evaluate.BOARD_ARRAY_SIZE; ++j) {
				if (Evaluate.boardArray[j]) {
					++boardArrayFlop[j];
					++boardCountFlop;
				}
				if (Evaluate.gap0 != 0) {
					++gapArrayFlop[0];
				}
				if (Evaluate.gap1 != 0) {
					++gapArrayFlop[1];
				}
				if (Evaluate.gap2 != 0) {
					++gapArrayFlop[2];
				}
				if (Evaluate.gap3 != 0) {
					++gapArrayFlop[3];
				}
				if (Evaluate.gap0Score != 0) {
					++scoreArrayFlop[0];
				}
				if (Evaluate.gap1Score != 0) {
					++scoreArrayFlop[1];
				}
				if (Evaluate.gap2Score != 0) {
					++scoreArrayFlop[0];
				}
				if (Evaluate.gap0Score != 0) {
					++scoreArrayFlop[2];
				}
				if (Evaluate.gap3Score != 0) {
					++scoreArrayFlop[3];
				}
				if (Evaluate.gap1Count != 0) {
					++scoreArrayFlop[4];
				}
				if (Evaluate.count != 0) {
					++scoreArrayFlop[5];
				}
		
			}
			for (int j = 0; j < Evaluate.HML_SIZE; ++j) {
				if (Evaluate.HMLArray[j])
					++HMLArrayFlop[j];
			}
		}
		*/
		long elapsedTime = (System.nanoTime() - startTime1) / 100;
		final long elapsed = TimeUnit.NANOSECONDS.toMicros(System.nanoTime() - startTime1);
		System.out.println("\r\n//Flop board Time in micro seconds " + elapsedTime);

		startTime2 = System.nanoTime();
		// Flop hands
		if (doFlop) {
			for (int i = 0; i < 169; ++i) {
				for (int j = 0; j < 1755; ++j) {
					evaluateOneHandFlop(i, j);
					for (int k = 0; k < MAX_HANDS; ++k) {
						// TODO if (Evaluate.evalArray[k]) {
						// TODO ++evalArrayFlop[k];
						// TODO ++evalCountFlop;
						// TODO }
					}
				}
			}
		}
		elapsedTime = (System.nanoTime() - startTime2) / 100;
		System.out.println("\r\n//Flop Time in micro seconds " + elapsedTime);
		startTime3 = System.nanoTime();
		// Turn hands
		if (doTurn) {
			for (int i = 0; i < 169; ++i) {
				for (int j = 0; j < 1755; ++j) {
					for (int k = 0; k < 52; ++k) {
						if (!isCardInFlop(i, k)) {
							evaluateOneHandTurn(i, j, k);
							// TODO for (int jj = 0; jj < Evaluate.BOARD_ARRAY_SIZE; ++jj) {
							// TODO if (Evaluate.evalArray[jj]) {
							// TODO ++evalArrayFlop[jj];
							// TODO ++evalCountTurn;
							// TODO }
							// TODO }
						}
					}
				}
			}
		}

		elapsedTime = (System.nanoTime() - startTime3) / 100;
		System.out.println("\r\n//Turn Time in micro seconds " + elapsedTime);
		startTime4 = System.nanoTime();

		// River hands
		if (doRiver) {
			for (int i = 0; i < 169; ++i) {
				System.out.println("XXXX " + i);
				for (int j = 0; j < 1755; ++j) {
					for (int k = 0; k < 52; ++k) {
						if (!isCardInFlop(i, k)) {
							for (int l = 0; l < 52; ++l) {
								if (!isCardInFlop(i, l) && k != l) {
									evaluateOneHandRiver(i, j, k, l);
									// TODO for (int jj = 0; jj < Evaluate.BOARD_ARRAY_SIZE; ++jj) {
									// TODO if (Evaluate.evalArray[jj]) {
									// TODO ++evalArrayFlop[jj];
									// TODO ++evalCountTurn;
									// TODO }
									// TODO }
								}
							}
						}
					}
				}
			}
		}

		elapsedTime = (System.nanoTime() - startTime4) / 100;
		System.out.println("\r\n//River Time in micro seconds " + elapsedTime);
		elapsedTime = (System.nanoTime() - startTime0) / 100;
		System.out.println("\r\nTotal Time in micro seconds " + elapsedTime);

		System.out.println("\r\n//gapArray Flop ");
		for (int i = 0; i < 4; ++i) {
			final double d = (double) boardArrayFlop[i] / (double) boardCountFlop * 100.;
			final var st = decimalFormat.format(d);
			System.out.println(new StringBuilder().append(boardArrayFlop[i]).append("      \t\t  ").append(st)
					.append("%    \t   ").append(gapSt[i]).toString());
		}

		System.out.println("\r\n//scoreArray Flop ");
		for (int i = 0; i < 6; ++i) {
			final double d = (double) boardArrayFlop[i] / (double) boardCountFlop * 100.;
			final var st = decimalFormat.format(d);
			System.out.println(new StringBuilder().append(boardArrayFlop[i]).append("       \t\t  ").append(st)
					.append("%    \t   ").append(scoreSt[i]).toString());
		}

		// TODO
		/*-
		System.out.println("\r\nHMLArray Flop ");
		for (int i = 0; i < Evaluate.HML_SIZE; ++i) {
			double d = (double) HMLArrayFlop[i] / (double) boardCountFlop * 100.;
			String st = decimalFormat.format(d);
			System.out.println(HMLArrayFlop[i] + "    \t\t  " + st + "%    \t   " + Evaluate.HMLSt[i]);
		}
		
		System.out.println("\r\n//evalArray Flop ");
		for (int i = 0; i < Evaluate.MAX_HANDS; ++i) {
			double d = (double) evalArrayFlop[i] / (double) evalCountFlop * 100.;
			String st = decimalFormat.format(d);
			System.out.println(evalArrayFlop[i] + "     \t\t  " + st + "%    \t   " + Evaluate.valuesSt[i]);
		}
		
		System.out.println("\r\n//boardArray Flop ");
		for (int i = 0; i < Evaluate.BOARD_ARRAY_SIZE; ++i) {
			double d = (double) boardArrayFlop[i] / (double) boardCountFlop * 100.;
			String st = decimalFormat.format(d);
			System.out.println(boardArrayFlop[i] + "     \t\t " + st + "%    \t   " + Evaluate.boardSt[i]);
		}
		
		System.out.println("\r\n//boardArray Turn ");
		for (int i = 0; i < Evaluate.BOARD_ARRAY_SIZE; ++i) {
			double d = (double) boardArrayTurn[i] / (double) boardCountTurn * 100.;
			String st = decimalFormat.format(d);
			System.out.println(
					boardArrayTurn[i] + "     \t\t  " + st + "%    \t   " + Evaluate.boardSt[i] + " " + boardCountTurn);
		}
		
		System.out.println("\r\n//boardArray River ");
		for (int i = 0; i < Evaluate.BOARD_ARRAY_SIZE; ++i) {
			double d = (double) boardArrayRiver[i] / (double) boardCountRiver * 100.;
			String st = decimalFormat.format(d);
			System.out.println(
					boardArrayRiver[i] + "//\t\t\t  " + st + "%    \t   " + Evaluate.boardSt[i] + " " + boardCountRiver);
		}
		*/
	}

	/*- Create all flops */
	private static void makeFlop() {
		flopIndex = 0;
		for (int i = 0; i < 1755; ++i) {
			flopy[i] = flop[i][2] = -1;
		}

		for (int i = 0; i < 13; ++i) {
			for (int j = 0; j < 13; ++j) {
				offsuitPairsIndexes[i] = suitedPairsIndexes[i] = -1;
			}
		}

		for (int i = 0; i < 13; ++i) {
			for (int j = 0; j < 13; ++j) {
				suited2LowHighIndexes[i][j] = suited2HighIndexes[i][j] = -1;
			}
		}

		makeSets();
		makePairsSuited();
		makePairsOffsuit();
		makeSuited();
		makeOffsuit();
		make2SuitedLow();
		make2SuitedHigh();
		make2SuitedLowHigh();

		for (int i = 0; i < flopIndex; ++i) {
			if (flop[i][0] == flop[i][1] || flop[i][0] == flop[i][2] || flop[i][1] == flop[i][2]) {
				++p;
				// ERROR duplicate Cards KsKsJs 391
				System.out.println(new StringBuilder().append("//ERROR duplicate Cards ")
						.append(Card.cardToString(flop[i][0])).append(Card.cardToString(flop[i][1]))
						.append(Card.cardToString(flop[i][2])).append(" ").append(i).toString());
				if (p > 20) {
					Crash.log("$$$");
				}
			}
		}

		for (int k = 0; k < flopIndex; ++k) {
			for (int i = k + 1; i < flopIndex; ++i) {
				if (flop[i][0] == flop[k][0] && flop[i][1] == flop[k][1] && flop[i][2] == flop[k][2]) {
					++p;
					System.out.println(
							new StringBuilder().append("//ERROR duplicate Flops ").append(Card.cardToString(flop[i][0]))
									.append(Card.cardToString(flop[i][1])).append(Card.cardToString(flop[i][2]))
									.append(" ").append(k).append(" ").append(i).toString());
					if (p > 20) {
						Crash.log("$$$");
					}
				}
			}
		}
		System.out.println(new StringBuilder().append("//Complete OK ").append(setCount).append(" ")
				.append(pairsSuitedCount).append(" ").append(pairsOffsuitCount).append(" ").append(suitedCount)
				.append(" ").append(offsuitCount).append(" ").append(suited2LowCount).append(" ")
				.append(suited2HighCount).append(" ").append(suited2LowHighCount).toString());
	}

	/*-
	 * Make sets 13 - 3 of a kind, AAA, KKK... 13 pcs
	 */
	private static void makeSets() {
		for (int i = 0; i < 13; ++i) {
			c3Value = c2Value = c1Value = i;
			c1 = Card.indexAndSuitToCard(i, SPADE);
			c2 = Card.indexAndSuitToCard(i, CLUB);
			c3 = Card.indexAndSuitToCard(i, DIAMOND);
			if (createFlop) {
				flop[flopIndex][0] = c1;
				flop[flopIndex][1] = c2;
				flop[flopIndex][2] = c3;
			}
			++flopIndex;
			++setCount;
		}
	}

	/*-
	 * Make Pairs Suited - Pair with 3rd card suited with eiter of the pair cards,
	 * AdAs2d, AdAs3d... 13 x 12 = 156 pcs
	 */
	private static void makePairsSuited() {
		for (int i = 0; i < 13; ++i) {
			suitedPairsIndexes[i] = flopIndex;
			c1 = Card.indexAndSuitToCard(i, SPADE);
			c2 = Card.indexAndSuitToCard(i, CLUB);
			for (int j = 0; j < 13; ++j) {
				if (j != i) {
					c3 = Card.indexAndSuitToCard(j, SPADE);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++pairsSuitedCount;
					++flopIndex;
				}
			}
		}

	}

	/*-
	 * Make Suited - A pair + 3rd card, all offsuit, same as above, 156 pcs
	 */
	private static void makePairsOffsuit() {
		for (int i = 0; i < 13; ++i) {
			offsuitPairsIndexes[i] = flopIndex;
			c1 = Card.indexAndSuitToCard(i, SPADE);
			c2 = Card.indexAndSuitToCard(i, CLUB);
			for (int j = 0; j < 13; ++j) {
				if (j != i) { // not a set
					c3 = Card.indexAndSuitToCard(j, DIAMOND);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++pairsOffsuitCount;
					++flopIndex;
				}
			}
		}
	}

	/*-
	 * Make Suited - All suited, 13 x 12 x 11/(3 x 2) = 286
	 */
	private static void makeSuited() {
		for (int i = 0; i < 13; ++i) {
			c1 = Card.indexAndSuitToCard(i, SPADE);
			for (int j = i + 1; j < 13; ++j) {
				suitedIndexs[i][j] = flopIndex;
				c2 = Card.indexAndSuitToCard(j, SPADE);
				for (int k = j + 1; k < 13; ++k) {
					c3 = Card.indexAndSuitToCard(k, SPADE);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++flopIndex;
					++suitedCount;
				}
			}
		}
	}

	/*-
	 * MakeOffsuit - All offsuit 286 pcs
	 */
	private static void makeOffsuit() {
		for (int i = 0; i < 13; ++i) {
			c1 = Card.indexAndSuitToCard(i, SPADE);
			for (int j = i + 1; j < 13; ++j) {
				offsuitIndexs[i][j] = flopIndex;
				c2 = Card.indexAndSuitToCard(j, CLUB);
				for (int k = j + 1; k < 13; ++k) {
					c3 = Card.indexAndSuitToCard(k, DIAMOND);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++flopIndex;
					++offsuitCount;
				}
			}
		}
	}

	/*-
	 * Make 2 suited - 2 cards suited lowest 286
	 */
	private static void make2SuitedLow() {
		for (int i = 0; i < 13; ++i) {
			for (int j = i + 1; j < 13; ++j) {
				suited2LowIndexes[i][j] = flopIndex;
				for (int k = j + 1; k < 13; ++k) {
					c1 = Card.indexAndSuitToCard(i, CLUB);
					c2 = Card.indexAndSuitToCard(j, SPADE);
					c3 = Card.indexAndSuitToCard(k, SPADE);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++flopIndex;
					++suited2LowCount;
				}
			}
		}
	}

	/*-
	 * Make 2 suited - 2 cards suited two highest 286
	 */
	private static void make2SuitedHigh() {
		for (int i = 0; i < 13; ++i) {
			for (int j = i + 1; j < 13; ++j) {
				suited2HighIndexes[i][j] = flopIndex;
				for (int k = j + 1; k < 13; ++k) {
					c1 = Card.indexAndSuitToCard(i, SPADE);
					c2 = Card.indexAndSuitToCard(j, SPADE);
					c3 = Card.indexAndSuitToCard(k, CLUB);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++flopIndex;
					++suited2HighCount;
				}
			}
		}
	}

	/*-
	 * Make 2 suited - 2 cards suited highest and lowest cards 286
	 */
	private static void make2SuitedLowHigh() {
		for (int i = 0; i < 13; ++i) {
			for (int j = i + 1; j < 13; ++j) {
				suited2LowHighIndexes[i][j] = flopIndex;
				for (int k = j + 1; k < 13; ++k) {
					c1 = Card.indexAndSuitToCard(i, SPADE);
					c2 = Card.indexAndSuitToCard(j, CLUB);
					c3 = Card.indexAndSuitToCard(k, SPADE);
					if (createFlop) {
						flop[flopIndex][0] = c1;
						flop[flopIndex][1] = c2;
						flop[flopIndex][2] = c3;
					}
					++suited2LowHighCount;
					++flopIndex;
				}
			}
		}
	}

	/*- 
	 *  
	 * Sort flop high to low 
	 * Code was copied from the Table Class
	  *  
	 */
	private static void sortFlop() {
		var card1 = new Card(c1);
		var card2 = new Card(c2);
		var card3 = new Card(c3);
		var cardSave = new Card(0);

		System.out.println(new StringBuilder().append("//Before sort  ").append(card1).append(" ").append(card2)
				.append(" ").append(card3).toString());

		// Sort high to low
		// Make the first card the largest - if first card alredy largest don't move
		if (!(card1.value >= card2.value && card2.value >= card3.value)) {
			if (card2.value > card1.value && card2.value >= card3.value) {// card2 largest
				cardSave = card1;
				card1 = card2;
				card2 = cardSave;
			} else if (card3.value > card1.value && card3.value > card2.value) {// card2 largest
				cardSave = card1;
				card1 = card3;
				card3 = cardSave;
			}

			// Now do the second and third cards
			card2.value = card2.value;
			card3.value = card3.value;
			if (card3.value > card2.value) {
				cardSave = card2;
				card2 = card3;
				card3 = cardSave;
			}
		}

		if (!(card1.value < card2.value || card1.value < card3.value || card2.value < card3.value || c1 == c2
				|| c1 == c3 || c2 == c3)) {
			return;
		}
		// Before sort 0 2 7 12 36 44 2s 4d 9h
		// After sort 2 7 0 36 44 12 4d 9h 2s
		System.out.println(new StringBuilder().append("//After sort  ").append(c1Value).append(" ").append(c2Value)
				.append(" ").append(c3Value).append(" ").append(c1).append(" ").append(c2).append(" ").append(c3)
				.append(" ").append(Card.cardToString(c1)).append(" ").append(Card.cardToString(c2)).append(" ")
				.append(Card.cardToString(c3)).toString());
		Crash.log("$$$");

	}

	/*-
	 * 
	 * Get hand using index value from HandRange place in Table
	 * 
	 */
	private static void getHandCardFromRangeArrayPlaceInTable(int ndx) {
		final int type = range.getHandType(ndx);
		final int v1 = range.getCard1ValueForIndex(ndx);
		final int v2 = range.getCard2ValueForIndex(ndx);
		int s2 = 0;

		if (type == range.SUITED) {
			s2 = SPADE;
		} else {
			s2 = CLUB;
		}

		// TODO
		/*-
		Table.card1 = new Card(v1, s1);
		Table.card2 = new Card(v2, s2);
		
		Table.card1Value = Table.card1.getValue();
		Table.card2Value = Table.card2.getValue();
		
		Table.card1Suit = Table.card1.getSuit();
		Table.card2Suit = Table.card2.getSuit();
		
		Table.cards1[0] = Table.card1;
		Table.cards2[0] = Table.card2;
		Table.cards1Value[0] = Table.card1Value;
		Table.cards2Value[0] = Table.card2Value;
		Table.cards1Suit[0] = Table.card1Suit;
		Table.cards2Suit[0] = Table.card2Suit;
		*/
	}

	/*-
	 * Get flop from flop array and place in Table
	 */
	private static void getFlopCardsFromArrayPlaceInTableFlop() {
		// TODO
		/*-
		Table.card1 = new Card(flop[f][0]);
		Table.card2 = new Card(flop[f][1]);
		Table.card3 = new Card(flop[f][2]);
		
		Table.card1Value = Table.card1.getValue();
		Table.card2Value = Table.card2.getValue();
		Table.card3Value = Table.card3.getValue();
		
		Table.card1Suit = Table.card1.getSuit();
		Table.card2Suit = Table.card2.getSuit();
		Table.card3Suit = Table.card3.getSuit();
		*/
	}

	/*-
	 * Get flop from flop array and place in Table + Turn card
	 */
	private static void getFlopCardsFromArrayPlaceInTableTurn() {
		// TODO
		/*-
		Table.card1 = new Card(flop[f][0]);
		Table.card2 = new Card(flop[f][1]);
		Table.card3 = new Card(flop[f][2]);
		Table.card4 = new Card(ca);
		
		Table.card1Value = Table.card1.getValue();
		Table.card2Value = Table.card2.getValue();
		Table.card3Value = Table.card3.getValue();
		Table.card4Value = Table.card4.getValue();
		
		Table.card1Suit = Table.card1.getSuit();
		Table.card2Suit = Table.card2.getSuit();
		Table.card3Suit = Table.card3.getSuit();
		Table.card4Suit = Table.card4.getSuit();
		*/
	}

	/*-
	 * Get flop from flop array and place in Table + river cards
	 */
	private static void getFlopCardsFromArrayPlaceInTableRiver() {
		// TODO
		/*-
		Table.card1 = new Card(flop[f][0]);
		Table.card2 = new Card(flop[f][1]);
		Table.card3 = new Card(flop[f][2]);
		Table.card4 = new Card(ca);
		Table.card5 = new Card(cb);
		
		Table.card1Value = Table.card1.getValue();
		Table.card2Value = Table.card2.getValue();
		Table.card3Value = Table.card3.getValue();
		Table.card4Value = Table.card4.getValue();
		Table.card5Value = Table.card5.getValue();
		
		Table.card1Suit = Table.card1.getSuit();
		Table.card2Suit = Table.card2.getSuit();
		Table.card3Suit = Table.card3.getSuit();
		Table.card4Suit = Table.card4.getSuit();
		Table.card5Suit = Table.card5.getSuit();
		*/
	}

	/*-
	 * 
	 * Find card not in Flop and return in
	 */
	private static boolean isCardInFlop(int f, int c) {
		final var c1 = new Card(flop[f][0]);
		final var c2 = new Card(flop[f][1]);
		final var c3 = new Card(flop[f][2]);
		final var c4 = new Card(c);
		final int i1 = c1.card;
		final int i2 = c2.card;
		final int i3 = c3.card;
		final int i4 = c4.card;

		if (i4 != i1 && i4 != i2 && i4 != i3) {
			return false;
		} else {
			return true;
		}
	}

	/*- Get hand cards from table and convert to h1 and h2 */
	private static void getHandCardsFromTable() {
		// TODO h1 = Table.cards1[0].card;
		// TODO h2 = Table.cards2[0].card;
	}

	/*- Get flop cards from table and assign to c1, c2, and c3 */
	private static void FromTable() {
		// TODO c1 = Table.card1.card;
		// TODO c2 = Table.card2.card;
		// TODO c3 = Table.card3.card;
	}

	/*- Get flop cards and assign to c1, c2, and c3 */
	private static void getFlopCard(int f) {
		c1 = flop[f][0];
		c2 = flop[f][1];
		c3 = flop[f][2];
	}

	/*-
	 * Evaluate one board using flop from flop array
	 */
	private static void evaluateOneBoardFlop(int f) {
		// TODO Table.streetNumber = Table.FLOP;
		// TODO Table.seat = Table.orbit = 0;
		// TODO Card h2Card = new Card("Ac");
		// TODO Table.cards1[0] = new Card("As");
		// TODO Table.cards2[0] = h2Card;

		getFlopCardsFromArrayPlaceInTableFlop();

		// TODO Evaluate.evaluateBoard();
	}

	/*-
	 * Evaluate one board using flop from flop array + one random card
	 */
	private static void evaluateOneBoardTurn(int f, int ca) {
		// TODO Table.streetNumber = Table.TURN;
		// TODO Table.seat = Table.orbit = 0;
		// TODO Card h2Card = new Card("Ac");
		// TODO Table.cards1[0] = new Card("As");
		// TODO Table.cards2[0] = h2Card;

		getFlopCardsFromArrayPlaceInTableTurn();

		// TODO Evaluate.evaluateBoard();
	}

	/*-
	 * Evaluate one board using flop from flop array + two random cards
	 */
	private static void evaluateOneBoardRiver(int f, int ca, int cb) {
		// TODO Table.streetNumber = Table.RIVER;
		// TODO Table.seat = Table.orbit = 0;
		// TODO Card h2Card = new Card("Ac");
		// TODO Table.cards1[0] = new Card("As");
		// TODO Table.cards2[0] = h2Card;

		getFlopCardsFromArrayPlaceInTableRiver();

		// TODO Evaluate.evaluateBoard();
	}

	/*-
	 * Analyze evaluation arrays TODO
	 */
	private static void evaluateArrays() {

	}

	/*- Print gap scores */
	private static void printGapScores() {
		// TODO System.out.println("//gap0Score " + Evaluate.gap0Score + " gap1Score " +
		// Evaluate.gap1Score + " gap0Score "
		// TODO + Evaluate.gap2Score + " gap2Score " + Evaluate.gap2Score + " gap0 " +
		// Evaluate.gap0 + " gap1 "
		// TODO + Evaluate.gap1 + " gap2 " + Evaluate.gap2 + " gap3 " + Evaluate.gap3);
		// TODO System.out.println("//gap1Count " + Evaluate.gap1Count + " count " +
		// Evaluate.count);
	}

	/*-
	 * Print evaluation array
	 */
	private static void printEvalArray() {
	}

	/*-
	 * Print HML array
	 */
	private static void printHMLArray() {
	}

	/*-
	 * Print Board array
	 */
	private static void printBoardArray() {
	}

	/*-
	 * Print wetDry arrays
	 */
	private static void printWetDryArrays() {
		// TODO
		/*-
		System.out.println("\r\n//evalArrayWetDry ");
		for (int i = 0; i < Evaluate.MAX_HANDS; ++i) {
		//			System.out.println("\r\n//HMLRrrayWetDry ");
		}
		for (int i = 0; i < Evaluate.MAX_HANDS; ++i) {
		//			System.out.println("\r\n//HMLRrrayWetDry ");
		}
		*/
	}

	/*- Evaluate board with all flops */
	private static void evaluateOneBoardAllFlops() {
		/*-
		Table.streetNumber = Table.FLOP;
		Table.seat = Table.orbit = 0;
		for (int i = 0; i < 1755; ++i)
			evaluateOneBoardFlop(i);
		*/
	}

	/*-
	 * 
	 * Evaluate one hand with one flop
	 * 
	 */
	private static void evaluateOneHandFlop(int hand, int f) {
		getHandCardFromRangeArrayPlaceInTable(hand);
		getFlopCardsFromArrayPlaceInTableFlop();
		// TODO Table.seat = Table.orbit = 0;
		// TODO handValue = Evaluate.evaluateAll(0);
	}

	/*-
	 * Evaluate one hand with one Turn
	 */
	private static void evaluateOneHandTurn(int hand, int f, int ca) {
		getHandCardFromRangeArrayPlaceInTable(hand);
		getFlopCardsFromArrayPlaceInTableTurn();
		// TODO Table.seat = Table.orbit = 0;
		// TODO handValue = Evaluate.evaluateAll(0);
	}

	/*-
	 * Evaluate one hand with one Turn
	 */
	private static void evaluateOneHandRiver(int hand, int f, int ca, int cb) {
		getHandCardFromRangeArrayPlaceInTable(hand);
		getFlopCardsFromArrayPlaceInTableRiver();
		// TODO Table.seat = Table.orbit = 0;
		// TODO handValue = Evaluate.evaluateAll(0);
	}

	/*-
	 * 
	 * Look up index for flop.
	 * Arg0 - Card 1 of flop
	 * Arg1- Card 2 of flop
	  * Arg2 - Card 3 of flop
	 * Returns int value that is index into array of all possible flops ( 1755 )
	  * 
	 */
	private static int lookupFlop(Card card1, Card card2, Card card3) {
		if (card1.value < card2.value || card1.value < card3.value || card2.value < c3Value) {
			sortFlop();
		}

		final int i1 = 12 - card1.value;
		final int i2 = 12 - card2.value;
		final int i3 = 12 - card3.value;

		// set
		if (card1.value == card2.value && card1.value == card3.value) {
			return i1;
		}

		// pairs suited and offsuit

		// System.out.println("//ZZZZ " + card1.value+ " " + card2.value+ " " +
		// card3.value);
		if (card1.value == card2.value || card2.value == card3.value) {
			if (card1.suit == card3.suit || card2.suit == card3.suit) {
				if (card2.value < card3.value) {
					return suitedPairsIndexes[i1] + i3;
				} else {
					return suitedPairsIndexes[i1] + i3 - 1;
				}
			} else {
				if (card1.value < card3.value) {
					return offsuitPairsIndexes[i1] + i3;
				} else {
					return offsuitPairsIndexes[i1] + i3 - 1;
				}
			}
		}

		// Suited
		if (card1.suit == card2.suit && card2.suit == card3.suit) {
			return suitedIndexs[i1][i2] + i3 - i2 - 1;
		}

		// Offsuit
		if (card1.suit != card2.suit && card1.suit != card3.suit && card2.suit != card3.suit) {
			return offsuitIndexs[i1][i2] + i3 - i2 - 1;
		}

		// 2 lowest are card 2 and card 3
		if (card2.suit == card3.suit & card1.suit != card2.suit) {
			return suited2LowIndexes[i1][i2] + i3 - i2 - 1;
		}

		// 2 higest are card 1 and card 2
		if (card1.suit == card2.suit & card1.suit != card3.suit) {
			return suited2HighIndexes[i1][i2] + i3 - i2 - 1;
		}
		// High is card 1 and low is card 3
		if (card1.suit == card3.suit & card1.suit != card2.suit) {
			return suited2LowHighIndexes[i1][i2] + i3 - i2 - 1;
		}

		Crash.log("$$$");
		return -1;
	}

	/*-
	 * Look up flop index , 0 - 1774. Shared variables c1, c2, and c3 are set
	 */
	private static void lookupFlopForIndex(int ndx) {
		i1 = 0;
		i2 = 0;
		i3 = 0;
		x1 = 0;
		x2 = 0;
		t = 0;

		// Set
		if (ndx < 13) {
			c1 = Card.indexAndSuitToCard(ndx, SPADE);
			c2 = Card.indexAndSuitToCard(ndx, CLUB);
			c3 = Card.indexAndSuitToCard(ndx, DIAMOND);
			return;
		}

		// Pairs with third card suited suited
		if (ndx < 169) {
			x1 = ndx - 13;
			i1 = x1 / 12;
			i2 = x1 % 12;
			if (i2 >= i1) {
				++i2;
			}

			c1 = Card.indexAndSuitToCard(i1, SPADE);
			c2 = Card.indexAndSuitToCard(i1, CLUB);
			c3 = Card.indexAndSuitToCard(i2, SPADE);
			return;
		}

		// Pairs all offsuit
		if (ndx < 325) {
			x1 = ndx - 13 - 156;
			i1 = x1 / 12;
			i2 = x1 % 12;
			if (i2 >= i1) {
				++i2;
			}

			c1 = Card.indexAndSuitToCard(i1, SPADE);
			c2 = Card.indexAndSuitToCard(i1, CLUB);
			c3 = Card.indexAndSuitToCard(i2, DIAMOND);
			return;
		}

		// Suited
		if (ndx < 611) {
			x1 = ndx - 325;

			// find first index
			for (int i = 0; i < 13; ++i) {
				if (x1 <= group1[i] - 1) {
					i1 = i;
					break;
				}
			}
			if (i1 == 0) {
				x2 = x1;
			} else {
				x2 = x1 - group1[i1 - 1];
			}

			// Find second index
			t = x2;
			for (int i = i1; i < 13; ++i) {
				if (t < group2[i]) {
					i2 = i - i1;
					break;
				} else {
					t -= group2[i];
				}
			}
			// Now for the third index
			i3 = t;
			i2 += i1 + 1;
			i3 += i2 + 1;

			c1 = Card.indexAndSuitToCard(i1, SPADE);
			c2 = Card.indexAndSuitToCard(i2, SPADE);
			c3 = Card.indexAndSuitToCard(i3, SPADE);
			if (c1 != flop[ndx][0] || c2 != flop[ndx][1] || c3 != flop[ndx][2]) {
				System.out.println(new StringBuilder().append("//Suited   ").append(Card.cardToString(c1))
						.append(Card.cardToString(c2)).append(Card.cardToString(c3)).toString());
				System.out.println(new StringBuilder().append("//ERROR ").append(Card.cardToString(flop[ndx][0]))
						.append(Card.cardToString(flop[ndx][1])).append(Card.cardToString(flop[ndx][2])).toString());
				Crash.log("$$$");
			}
			return;
		}

		// Offsuit
		if (ndx < 897) {
			x1 = ndx - 611;

			// find first index
			for (int i = 0; i < 13; ++i) {
				if (x1 <= group1[i] - 1) {
					i1 = i;
					break;
				}
			}
			if (i1 == 0) {
				x2 = x1;
			} else {
				x2 = x1 - group1[i1 - 1];
			}

			// Find second index
			t = x2;
			for (int i = i1; i < 13; ++i) {
				if (t < group2[i]) {
					i2 = i - i1;
					break;
				} else {
					t -= group2[i];
				}
			}
			// Now for the third index
			i3 = t;
			i2 += i1 + 1;
			i3 += i2 + 1;

			c1 = Card.indexAndSuitToCard(i1, SPADE);
			c2 = Card.indexAndSuitToCard(i2, CLUB);
			c3 = Card.indexAndSuitToCard(i3, DIAMOND);
			if (c1 != flop[ndx][0] || c2 != flop[ndx][1] || c3 != flop[ndx][2]) {
				System.out.println(new StringBuilder().append("//Offsuit  ").append(Card.cardToString(c1))
						.append(Card.cardToString(c2)).append(Card.cardToString(c3)).toString());
				System.out.println(new StringBuilder().append("//").append(Card.cardToString(flop[ndx][0]))
						.append(Card.cardToString(flop[ndx][1])).append(Card.cardToString(flop[ndx][2])).toString());
				Crash.log("$$$");
			}
			return;
		}

		// 2 suited low
		if (ndx < 1183) {
			x1 = ndx - 897;

			// find first index
			for (int i = 0; i < 13; ++i) {
				if (x1 <= group1[i] - 1) {
					i1 = i;
					break;
				}
			}
			if (i1 == 0) {
				x2 = x1;
			} else {
				x2 = x1 - group1[i1 - 1];
			}

			// Find second index
			t = x2;
			for (int i = i1; i < 13; ++i) {
				if (t < group2[i]) {
					i2 = i - i1;
					break;
				} else {
					t -= group2[i];
				}
			}
			// Now for the third index
			i3 = t;
			i2 += i1 + 1;
			i3 += i2 + 1;

			c1 = Card.indexAndSuitToCard(i1, CLUB);
			c2 = Card.indexAndSuitToCard(i2, SPADE);
			c3 = Card.indexAndSuitToCard(i3, SPADE);
			if (c1 != flop[ndx][0] || c2 != flop[ndx][1] || c3 != flop[ndx][2]) {
				System.out.println(new StringBuilder().append("//paired low  ").append(Card.cardToString(c1))
						.append(Card.cardToString(c2)).append(Card.cardToString(c3)).toString());
				System.out.println(new StringBuilder().append("//ERROR ").append(Card.cardToString(flop[ndx][0]))
						.append(Card.cardToString(flop[ndx][1])).append(Card.cardToString(flop[ndx][2])).toString());
				Crash.log("$$$");
			}
			return;
		}

		// 2 suited high
		if (ndx < 1469) {
			x1 = ndx - 1183;

			// find first index
			for (int i = 0; i < 13; ++i) {
				if (x1 <= group1[i] - 1) {
					i1 = i;
					break;
				}
			}
			if (i1 == 0) {
				x2 = x1;
			} else {
				x2 = x1 - group1[i1 - 1];
			}

			// Find second index
			t = x2;
			for (int i = i1; i < 13; ++i) {
				if (t < group2[i]) {
					i2 = i - i1;
					break;
				} else {
					t -= group2[i];
				}
			}
			// Now for the third index
			i3 = t;
			i2 += i1 + 1;
			i3 += i2 + 1;

			c1 = Card.indexAndSuitToCard(i1, SPADE);
			c2 = Card.indexAndSuitToCard(i2, SPADE);
			c3 = Card.indexAndSuitToCard(i3, CLUB);
			if (c1 != flop[ndx][0] || c2 != flop[ndx][1] || c3 != flop[ndx][2]) {
				System.out.println(new StringBuilder().append("//paired low  ").append(Card.cardToString(c1))
						.append(Card.cardToString(c2)).append(Card.cardToString(c3)).toString());
				System.out.println(new StringBuilder().append("//ERROR ").append(Card.cardToString(flop[ndx][0]))
						.append(Card.cardToString(flop[ndx][1])).append(Card.cardToString(flop[ndx][2])).toString());
				Crash.log("$$$");
			}
			return;
		}

		// 2 suited low high
		if (ndx < 1755) {
			x1 = ndx - 1469;

			// find first index
			for (int i = 0; i < 13; ++i) {
				if (x1 <= group1[i] - 1) {
					i1 = i;
					break;
				}
			}
			if (i1 == 0) {
				x2 = x1;
			} else {
				x2 = x1 - group1[i1 - 1];
			}

			// Find second index
			t = x2;
			for (int i = i1; i < 13; ++i) {
				if (t < group2[i]) {
					i2 = i - i1;
					break;
				} else {
					t -= group2[i];
				}
			}
			// Now for the third index
			i3 = t;
			i2 += i1 + 1;
			i3 += i2 + 1;

			c1 = Card.indexAndSuitToCard(i1, SPADE);
			c2 = Card.indexAndSuitToCard(i2, CLUB);
			c3 = Card.indexAndSuitToCard(i3, SPADE);
			if (c1 != flop[ndx][0] || c2 != flop[ndx][1] || c3 != flop[ndx][2]) {
				System.out.println(new StringBuilder().append("//paired low  ").append(Card.cardToString(c1))
						.append(Card.cardToString(c2)).append(Card.cardToString(c3)).toString());
				System.out.println(new StringBuilder().append("//ERROR ").append(Card.cardToString(flop[ndx][0]))
						.append(Card.cardToString(flop[ndx][1])).append(Card.cardToString(flop[ndx][2])).toString());
				Crash.log("$$$");
			}
			return;
		}

		Crash.log("$$$");
	}

	/*-
	* 
	*	Low Medium High combinations. 
	*  Cards in the Flop oard are classified as High, Medium, or Low
	*  		High - 10 and above
	*  		Medium - 6  to 9
	*  		Low - 2 - 5
	*  This result is 0 - 9 possible flop combinations, HHH - LLL
	*  The idea is to find a small number of combinations thar represent all possible flops.
	*  Easy for a human to classify at the table.
	*  Not nearly as good as the 1555 possible flops, but a heman can not do that classification
	*  at the table.
	* 
	*/
	private static int HMLBoard(Card card1, Card card2, Card card3) {
		final int card1Value = card1.getValue();
		final int card2Value = card2.getValue();
		final int card3Value = card3.getValue();
		// H, M, or L HML values
		int c1 = 0;
		int c2 = 0;
		int c3 = 0;

		// TODO Values have changed with new Card class
		if (card1Value >= Card.TEN) {
			c1 = H;
		} else if (card1Value >= Card.SIX) {
			c1 = M;
		}

		if (card2Value >= Card.TEN) {
			c2 = H;
		} else if (card2Value >= Card.SIX) {
			c2 = M;
		}

		if (card3Value >= Card.TEN) {
			c3 = H;
		} else if (card3Value >= Card.SIX) {
			c3 = M;
		}
		int index = 0;

		switch (c1) {
		case H -> {
			switch (c2) {
			case H:
				if (c3 == H) {
					index = BOARD_HHH;
				} else {
					if (c3 == M) {
						index = BOARD_HHM;
					}
					if (c3 == L) {
						index = BOARD_HHL;
					}
				}
				break;
			case 1:
				if (c3 == M) {
					index = BOARD_HMM;
				}
				if (c3 == L) {
					index = BOARD_HML;
				}
				break;
			default:
				if (c2 == L && c3 == L) {
					index = BOARD_HLL;
				}
				break;
			}
		}
		case 1 -> {
			if (c2 == M) {
				if (c3 == M) {
					index = BOARD_MMM;
				}
				if (c3 == L) {
					index = BOARD_MML;
				}
			}
			if (c2 == L) {
				index = BOARD_MLL;
			}
		}
		default -> {
			if (c1 == L) {
				index = BOARD_LLL;
			}
		}
		}
		return index;
	}

}
