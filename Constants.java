package holdemhandhistory;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;

/*-  *****************************************************************************
 * Constant definitions used throughout entire project
 * In every Class that uses this add to class 
 * 		class xxx implements Constants 
 * 
  * @author PEAK_
  ***************************************************************************** */
public interface Constants {
	static final String APPLICATION_DIRECTORY = "C:\\HHApplicationData\\"; // Directory for this application files
	static final String HH_DIRECTORY = "C:\\HH\\"; // Directory for Hand History files
	static final String APPLICATION_DATA_DIRECTORY = "C:\\Hold'emDatabase\\";
	static final long RANDOM_SEED = 2062142251;

	// Poker sites
	static final int POKER_STARS = 0;
	static final int PEAK_HOLDEM = 1;
	static final int GGPOKER = 2;
	static final int POKER888 = 3;
	static final int CANADA = 4;

	// Currency
	static final int USD = 0;
	static final int CAD = 1;
	static final int EUR = 2;

	static final int MAX_HANDS_IN_FILE = 10000; // Maximum Hand instances in one file

	/*-**********************************************************************************************
	*HandRange
	*************************************************************************************************/
	static final int HANDS = 169;
	static final int NUM_ROWS = 13;
	static final int NUM_COLS = 13;
	static final int SUITED = 0;
	static final int OFFSUIT = 1;
	static final int PAIR = 2;
	static final int INVALID = 2;

	static final int COMBINATIONS = 1326;

	/*- *****************************************************************************
	 * Suits
	  ******************************************************************************/
	static final int SPADE = 0;
	static final int CLUB = 1;
	static final int DIAMOND = 2;
	static final int HEART = 3;
	String[] SUITS_ST = { "s", "c", "d", "h" };
	String[] SUITS2_ST = { "Spade", "Club", "Diamond", "Heart" };

	/*- *****************************************************************************
	 * Card
	  ******************************************************************************/
	static final int ACE = 12;
	static final int KING = 11;
	static final int QUEEN = 10;
	static final int JACK = 9;
	static final int TEN = 8;
	static final int NINE = 7;
	static final int EIGHT = 6;
	static final int SEVEN = 5;
	static final int SIX = 4;
	static final int FIVE = 3;
	static final int FOUR = 2;
	static final int THREE = 1;
	static final int TWO = 0;
	BigDecimal zeroBD = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);

	/*- *****************************************************************************
	 *  Opponent action
	  ******************************************************************************/
	static final int BET_SIZE_OP_CHECK = 0;
	static final int STGBET_SIZE_T1 = 1;
	static final int BET_SIZE__BET2 = 2;
	static final int BET_SIZE__BET3 = 3;
	static final int BET_SIZE__BET4 = 4;
	static final int BET_SIZE__ALLIN = 5;
	static final int BET_SIZE__C_BET = 6;
	static final int BET_SIZE__MIN_BET = 7;
	static final int BET_SIZE__ACTIONS = 8;
	/*- *****************************************************************************
	 *  My actions
	  ******************************************************************************/
	static final int BET_SIZE_CHECK = 0;
	static final int BET_SIZE_BET = 1;
	static final int BET_SIZE_CALL = 2;
	static final int BET_SIZE_ALLIN = 3;
	static final int BET_SIZE_NO_ACTION = 4;
	static final int BET_SIZE_FOLD = 5;
	static final int BET_SIZE_MIN_BET = 6;
	static final int BET_SIZE_ACTION_MAX = 7;
	/*- *****************************************************************************
	 *   et size relative to pot before bet or raise.
	  * At or below.
	  * Example POT_3Q  > .5 and  < 1.0 , pot steps are .25
	  ******************************************************************************/
	static final int BET_SIZE_POT_0Q = 0;
	static final int BET_SIZE_POT_1Q = 1;
	static final int BET_SIZE_POT_2Q = 2;
	static final int BET_SIZE_POT_3Q = 3;
	static final int BET_SIZE_POT_4Q = 4;
	static final int BET_SIZE_POT_5Q = 5;
	static final int BET_SIZE_POT_6Q = 6;
	static final int BET_SIZE_POT_7Q = 7;
	static final int BET_SIZE_POT_8Q = 8;
	static final int BET_SIZE_POT_BIG = 9;
	static final int BET_SIZE_POT_ALLIN = 10;
	static final int BET_SIZE_POT_MAX = 11;
	/*- *****************************************************************************
	 *  From Evaluate
	  ******************************************************************************/
	static final int DRY = 0;
	static final int WET = 1;
	static final int NOT_WET_DRY = 2;
	static final int WETDRY_MAX = 3;
	String[] wetDrySt = { "Dry", "Wet", "Neither" };
	static final int STATIC = 0;
	static final int DYNAMIC = 1;
	static final int NOT_STATIC_DYNAMIC = 2;
	static final int STATICDYNAMIC_MAX = 3;
	String[] staticDynamicSt = { "Static", "Dynamic", "Neither" };
	/*- *****************************************************************************
	* HML = high, low and medium		
	  ******************************************************************************/
	static final int H = 2;
	static final int M = 1;
	static final int L = 0;
	static final int STREETS = 4;

	/*- *****************************************************************************
	 * Bet to me -
	* These values are also used as array index so need to be 0 - 4
	 ******************************************************************************/
	static final int BET_CHECK = 0;
	static final int BET1 = 1;
	static final int BET2 = 2;
	static final int BET3 = 3;
	static final int BET4 = 4;
	static final int BET_ALLIN = 5;
	static final int BETS_MAX = 6;
	String[] BETS_ST = { "Check", "Bet1", "Bet2", "Bet3", "Bet4", "All-In" };
	/*- *****************************************************************************
	 *  Bet to me
	  ******************************************************************************/
	static final int TOME_CHECK = 0;
	static final int TOME_FOLD = 1;
	static final int TOME_RAISE = 2;
	static final int TOME_BET = 3;
	static final int TOME_NA = 4;
	static final int TOME_LIMP = 5;
	static final int TOME_OPEN = 6;
	static final int TOME_CALL = 7;
	static final int TOME_CALLBET1 = 8;
	static final int TOME_CALLBET2 = 9;
	static final int TOME_CALLBET3 = 10;
	static final int TOME_CALLBET4 = 11;
	static final int TOME_CALLALLIN = 12;
	static final int TOME_MAX = 13;
	static final int TOME_MAX_CLEAN = 13;
	String[] BET_TO_ME_ST = { "Bet1", "Bet2", "Bet3", "Bet4", "All-In", "Check", "Fold", "Raise", "Bet", "NA", "Limp",
			"Open", "Call", "CallBet1", "CallBet2", "CallBet3", "CallBet4", "CallAllIn" };

	/*- *****************************************************************************
	*  
	  ******************************************************************************/
	static final int ALL = 7;
	static final int NUM_POS = 6; // Positions
	static final int NUM_RP = 8; // Relative positions
	static final int NUM_ORBITS = 4;
	static final int NUM_STREETS = 4;
	static final int FLOP_COMBO = 1755;

	/*- *****************************************************************************
	 * Basic definitions
	  ******************************************************************************/
	static final int PLAYERS = 6;
	static final int MAXFOLDED = PLAYERS - 1;

	/*- *****************************************************************************
	 * Streets
	  ******************************************************************************/
	static final int PREFLOP = 0;
	static final int FLOP = 1;
	static final int TURN = 2;
	static final int RIVER = 3;
	static final int SHOWDOWN = 4;
	static final int SUMMARY = 5;
	static final int ALL_STREETS = 6;
	String[] STREET_ST = { "Preflop", "Flop", "Turn", "River", "Showdown", "Summary", "All Streets" };

	/*- *****************************************************************************
	 * Basic definitions
	  ******************************************************************************/

	static final int LAST_SEAT = 5;

	static final int ORBITS = 4;

	static final int STREET = 4;

	static final int NO = 0;

	/*- **************************************************************************** 
	*  Positions
	*******************************************************************************/
	static final int SB = 0;
	static final int BB = 1;
	static final int UTG = 2;
	static final int MP = 3;
	static final int CUTOFF = 4;
	static final int CO = 4;
	static final int BUTTON = 5;
	static final int BU = 5;
	static final String[] POSITION_ST = { "Small Blind", "Big Blind", "Under The Gun", "Middle", "Cutoff", "Button",
			"Folded" };
	static final String[] POSITION_ST2 = { "SB", "BB", "UTG", "MP", "CO", "BU" };

	/*- **************************************************************************** 
	*  Values for relative positions 
	*  F M1 M2 M3 M4 L          F M1 M2 M3 L        F M1 M2 L        F M1 L       FH LH
	*   RP_MIDDLEX X is number of players to act after this one, 1 = one before RP_LAST
	*******************************************************************************/
	static final int RP_FIRST = 0; // SB
	static final int RP_FIRSTHU = 1;
	static final int RP_MIDDLE4 = 2; // BB
	static final int RP_MIDDLE3 = 3; // UTG
	static final int RP_MIDDLE2 = 4; // MP
	static final int RP_MIDDLE1 = 5; // CO
	static final int RP_LAST = 6; // BU
	static final int RP_LASTHU = 7;
	String[] RP_ST = { "First", "First HU", "Middle4", "Middle3", "Middle2", "Middle1", "Last", "Last HU" };

	/*- **************************************************************************** 
	*  Player types
	*******************************************************************************/
	static final int HERO = 0;
	static final int FISH = 1;
	static final int NIT = 2;
	static final int LAG = 3;
	static final int TAG = 4;
	static final int AVERAGE = 5;
	static final int REG = 6;
	static final int SHARK = 7;
	static final int WINNER = 8;
	static final int LOOSER = 9;
	static final int USER1 = 10;
	static final int USER2 = 11;
	static final int USER3 = 12;
	static final int USER4 = 13;
	static final int NUM_TYPES = 14;
	static final String[] PLAYER_TYPE_ST = { "Hero", "Fish", "Nit", "Lag", "Tag", "Average", "Regular", "Shark",
			"Winner", "Looser", "User defined 1", "User defined 2", "User defined 3", "User defined 4" };
	static final int WIN = WINNER;
	static final int LOOSE = LOOSER;

	/*- *****************************************************************************
	 * Hand hole cards + board cards. Flop, Turn, and River
	  ******************************************************************************/
	static final int NONE = 0;
	static final int OVERCARDS = 1;
	static final int ACE_HIGH = 2;
	static final int WEAK_PAIR = 3;
	static final int MIDDLE_PAIR = 4;
	static final int POCKET_PAIR_BELOW_TP = 5;
	static final int OVER_PAIR = 6;
	static final int TOP_PAIR = 7;
	static final int GUTSHOT = 8;
	static final int GUTSHOT_HIGH = 9;
	static final int GUTSHOT_PAIR = 10;
	static final int OESD = 11;
	static final int FLUSH_DRAW = 12;
	static final int OESD_PAIR = 13;
	static final int FLUSH_DRAW_PAIR = 14;
	static final int FLUSH_DRAW_GUTSHOT = 15;
	static final int FLUSH_DRAW_OESD = 16;
	static final int TWO_PAIR = 17;
	static final int THREE_OF_A_KIND = 18;
	static final int STRAIGHT = 19;
	static final int FLUSH = 20;
	static final int FULL_HOUSE = 21;
	static final int FOUR_OF_A_KIND = 22;
	static final int STRAIGHT_FLUSH = 23;
	static final int ROYAL_FLUSH = 24;
	static final int MAX_HANDS = 25;
	/*- *****************************************************************************
	 * Board analysis
	  ******************************************************************************/
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
	/*- *****************************************************************************
	 * HLM analysis of Flop - High Medium Low
	  ******************************************************************************/
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
	/*- *****************************************************************************
	 * Possible draws from board only - Flop and Turn
	  ******************************************************************************/
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
	static final int POSSIBLE_SET = 11;
	static final int POSSIBLE_STRAIGHT = 12;
	static final int POSSIBLE_FLUSH = 13;
	static final int POSSIBLE_FULL_HOUSE = 14;
	static final int POSSIBLE_FOUR_OF_A_KIND = 15;
	static final int POSSIBLE_STRAIGHT_FLUSH = 16;
	static final int POSSIBLE_ROYAL_FLUSH = 17;
	static final int POSSIBLE_MAX = 18;

	/*- *****************************************************************************
	 * Types of 1755 possible Flops  
	  ******************************************************************************/
	static final int SET = 0;
	static final int SUITED_PAIR = 1;
	static final int ALL_SUITED = 3;
	static final int ALL_OFFSUIT = 4;
	static final int TWO_SUITED_LOW = 5;
	static final int TWO_SUITED_HIGH = 6;
	static final int TWO_SUITED_HIGH_LOW = 7;

	/*- *****************************************************************************
	 * HHHand unique definitions for Actions
	 ******************************************************************************/
	static final int ACTION_ERROR = 0;
	static final int ACTION_FOLD = 1;
	static final int ACTION_CHECK = 2;
	static final int ACTION_CALL = 3;
	static final int ACTION_CALL_ALLIN = 4;
	static final int ACTION_BET = 5;
	static final int ACTION_BET_ALLIN = 6;
	static final int ACTION_RAISE = 7;
	static final int ACTION_RAISE_ALLIN = 8;
	static final int ACTION_POST = 9;
	static final int ACTION_SHOWS_STREET = 10;
	static final int ACTION_UNCALLED_RETURNED_TO = 11;
	static final int ACTION_COLLECTED_STREET = 12;
	static final int ACTION_COLLECTED_SUMMARY = 13;
	static final int ACTION_WON = 14;
	// Applies to Showdown only
	static final int ACTION_WON_SIDE_POT = 15;
	static final int ACTION_COLLECTED_SIDE_POT = 16;
	static final int ACTION_COLLECTED_MAIN_POT = 17;
	static final int ACTION_COLLECTED_POT = 18;
	static final int ACTION_LAST_MONEY = 19;
	static final int ACTION_SHOWS_SHOWDOWN = 20;
	// Ignored
	static final int ACTION_DOES_NOT_SHOW = 21;
	static final int ACTION_SAID = 22;
	static final int ACTION_NO_ACTION = 23;
	static final int ACTION_MAX_ACTIONS = 24;

	static final String[] ACTION_ST = { "*ERROR*", "Fold", "Check", "Call", "Call All-in", "Bet", "Bet All-in", "Raise",
			"Raise All-in", "Post", "Shows Street", "Uncalled Returned To", "Collected Street", "Collected Summary",
			"Won", "Won Side Pot", "Collected Side Pot", "Collected Main Pot", "Collected Pot", "Last Money",
			"Shows at Showdown", "Does not Show", "Said", "No Action Ignore" };

	static final int ERR_OK = 0;
	static final int ERR_IN_VS_TOTAL_POT = 1;
	static final int ERR_IN_VS_SHOWDOWN = 2;
	static final int ERR_IN_VS_SUMMARY = 3;
	static final int ERR_START_STACKS_FINAL_STACKS = 4;
	static final int ERR_SUMMARY_WON_TOTAL_POT = 5;
	static final int ERR_TOTAL_POT_MONEY_OUT_SREETS = 6;
	static final int ERR_MONEY_IN_STREETS_MONEY_OUT_STREETS = 7;
	static final int ERR_COLLECTED_STREET_COLLECTED_SUMMARY = 8;
	static final int ERR_VALUE_IN_HAND = 9;
	static final int ERR_MAX = 10;
	static final String[] ERR_ST = { "OK", "Money in streets vs totalPot", "Money in streets vs Showdown",
			"Money in streets vs Summary", "Start stacks vs Final stacks", "Won summary vs totalPot",
			"totalPot vs money out streets", "moneyInStreets != moneyOutStreets", "CollectedStreet vs collectedSummary",
			"Invalid BigDecimal value", "?" };

	/*- *****************************************************************************
	*  TODO replace with new
	  ******************************************************************************/
	static final String[] valuesSt = { "None", "Overcards", "Ace High", "Weak Pair", "Middle Pair",
			"Pocket Pair Below Top", "Over Pair ", "Top Pair", "Gutshot", "Gutshot High", "Gutshot Draw Pair",
			"Straight Draw", "Flush Draw", "Straight Draw Pair", "Flush Draw Pair", "Flush Draw Gutshot",
			"Flush Draw OESD", "Two Pair", "Three of a kind", "Straight", "Flush", "Full House ", "Four of a kind",
			"Straight Flush ", "Royal Flush" };
	static final String[] POSSIBLESt = { "None", "Two_Pair", "Gutshot", "Gutshot High", "Gutshot + Pair", "Flush Draw",
			"OESD", "OESD + Pair", "Flush Draw + Pair", "Flush Draw + Gutshot", "Flush Draw + OESD", "Straight",
			"Flush", "Full House", "4 of a kind", "Straight Flush", "Royal Flush" };
	static final String[] HMLSt = { "HHH", "HHM", "HHL", "HMM", "HML", "HLL", "MMM", "MML", "MLL", "LLL" };
	/*- *****************************************************************************
	 * Index values are used to convert numbers to String
	  ******************************************************************************/
	// Index array with same index as SharedData.evalArray
	static final String[] EVAL_ARRAY_ST = { "None", "Overcards", "Ace High", "Weak Pair", "Middle Pair",
			"Pocket Pair Below Top", "Over Pair ", "Top Pair", "Gutshot", "Gutshot High", "Gutshot Draw Pair",
			"Straight Draw", "Flush Draw", "Straight Draw Pair", "Flush Draw Pair", "Flush Draw Gutshot",
			"Flush Draw OESD", "Two Pair", "Three of a kind", "Straight", "Flush", "Full House ", "Four of a kind",
			"Straight Flush ", "Royal Flush" };
	// Index array with same index as SharedData.boardArray
	static final String[] BOARD_ARRAY_ST = { "Dry", "Wet", "Not wet or dry", "Static", "Dynamic",
			"Not Static or Dynamic", "Rainbow", "Ace high", "High Card", "Pair", "Two Pair", "Set", "Quads", "Gap 0",
			"Gap 1", "Gap 2", "2 suited", "3 suited", "4 suited", "Flush" };
	// Index array with same index as SharedData. boardPossibleArrayFlop and
	// SharedData. boardPossibleArrayTurn
	static final String[] POSSIBLE_ST = { "None", "2 pair", "Gutshot", "Gutshot High", "Gutshot Pair", "Flush Draw",
			"OESD", "OESD Pair", "Flush Draw Pair", "Flush Draw Gutshot", "Flush Draw OESD", "Set", "Straight", "Flush",
			"Full House", "4 of a Kind", "Straight Flush", "Royal Flush," };
	// SharedData.HMLIndex is index
	static final String[] HML_ST = { "HHH", "HHM", "HHL", "HMM", "HML", "HLL", "MMM", "MML", "MLL", "LLL" };
	// SharedData.flopTypeOf1755 is index
	static final String[] TYPE_OF_FLOP_ST = { "Coordinated", "Uncoordinated", "Paired", "Rainbow", "Monotone" };
	// Index array with same index as SharedData.evalArray
	static final String[] handValueSt = { "Nothing", "Poor", "Poor", "Fair", "50/50", "Above average", "Very goog",
			"Very good", "Excelent", "The NUTS" };
	// Index array with same index as SharedData.evalArray
	static final String[] TYPE_OF_1755_ST = { "Set", "Suited pair", "All suited", "All offsuit", "Two suited low",
			"Two suited high", "Two suited high low" };

	/*- *****************************************************************************
	 *Colors for GUI
	  ******************************************************************************/
	Color RED = Color.red;
	Color BLUE = Color.blue;
	Color GREEN = Color.green;
	Color YELLOW = Color.yellow;
	Color ORANGE = Color.orange;
	Color PINK = Color.pink;
	Color MAGENTA = Color.magenta;
	Color CYAN = Color.cyan;
	Color GRAY = Color.gray;
	Color DARK_GRAY = Color.darkGray;
	Color LIGHT_GRAY = Color.lightGray;
	Color WHITE = Color.white;
	Color BLACK = Color.black;
	Color IVORY = new Color(255, 255, 240);
	Color BEIGE = new Color(245, 245, 220);
	Color WHEAT = new Color(245, 222, 179);
	Color TAN = new Color(210, 180, 140);
	Color KHAKI = new Color(195, 176, 145);
	Color SILVER = new Color(192, 192, 192);
	Color DARK_SLATE_GRAY = new Color(47, 79, 79);
	Color SLATE_GRAY = new Color(112, 128, 144);
	Color LIGHT_SLATE_GRAY = new Color(119, 136, 153);
	Color NAVY = new Color(0, 0, 128);
	Color MIDNIGHT_BLUE = new Color(25, 25, 112);
	Color CORNFLOWER_BLUE = new Color(100, 149, 237);
	Color STEEL_BLUE = new Color(70, 130, 180);
	Color ROYAL_BLUE = new Color(65, 105, 225);
	Color DODGER_BLUE = new Color(30, 144, 255);
	Color DEEP_SKY_BLUE = new Color(0, 191, 255);
	Color SKY_BLUE = new Color(135, 206, 235);
	Color LIGHT_SKY_BLUE = new Color(135, 206, 250);
	Color TEAL = new Color(0, 128, 128);
	Color DARK_CYAN = new Color(0, 139, 139);
	Color CADET_BLUE = new Color(95, 158, 160);
	Color AQUAMARINE = new Color(127, 255, 212);
	Color TURQUOISE = new Color(64, 224, 208);
	Color MEDIUM_TURQUOISE = new Color(72, 209, 204);
	Color PALE_TURQUOISE = new Color(175, 238, 238);
	Color GREEN_YELLOW = new Color(173, 255, 47);
	Color CHARTREUSE = new Color(127, 255, 0);
	Color LAWN_GREEN = new Color(124, 252, 0);
	Color LIME_GREEN = new Color(50, 205, 50);
	Color FOREST_GREEN = new Color(34, 139, 34);
	Color OLIVE_DRAB = new Color(107, 142, 35);
	Color DARK_KHAKI = new Color(189, 183, 107);
	Color GOLDEN_ROD = new Color(218, 165, 32);
	Color DARK_GOLDEN_ROD = new Color(184, 134, 11);
	Color SADDLE_BROWN = new Color(139, 69, 19);
	Color SIENNA = new Color(160, 82, 45);
	Color CHOCOLATE = new Color(210, 105, 30);
	Color PERU = new Color(205, 133, 63);
	Color ROSY_BROWN = new Color(188, 143, 143);
	Color SANDY_BROWN = new Color(244, 164, 96);
	Color LIGHT_SALMON = new Color(255, 160, 122);
	Color SALMON = new Color(250, 128, 114);
	Color DARK_SALMON = new Color(233, 150, 122);
	Color ORANGE_RED = new Color(255, 69, 0);
	Color TOMATO = new Color(255, 99, 71);
	Color CORAL = new Color(255, 127, 80);
	Color DARK_ORANGE = new Color(255, 140, 0);
	Color LIGHT_CORAL = new Color(240, 128, 128);
	Color HOT_PINK = new Color(255, 105, 180);
	Color DEEP_PINK = new Color(255, 20, 147);
	Color LIGHT_PINK = new Color(255, 182, 193);
	Color PLUM = new Color(221, 160, 221);
	Color VIOLET = new Color(238, 130, 238);
	Color ORCHID = new Color(218, 112, 214);
	Color MEDIUM_ORCHID = new Color(186, 85, 211);
	Color DARK_ORCHID = new Color(153, 50, 204);
	Color PURPLE = new Color(128, 0, 128);
	Color MEDIUM_PURPLE = new Color(147, 112, 219);
	Color THISTLE = new Color(216, 191, 216);
	Color GHOST_WHITE = new Color(248, 248, 255);
	Color LAVENDER = new Color(230, 230, 250);
	Color MISTY_ROSE = new Color(255, 228, 225);
	Color ANTIQUE_WHITE = new Color(250, 235, 215);

}
