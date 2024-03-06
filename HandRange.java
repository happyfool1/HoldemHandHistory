package holdemhandhistory;
/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

/*-
 * This class defines a range of hands The hands are in a Container class. 
 *  This array represents the commonly used 13 X 13 matrix to represent suited,
 * ofsuit, and pairs
 * An element is true if that card is in the range.
 * 
 * This is a new experimental Class derived from the HandRange Class:
 * 		HandRange used a boolean array representing the 169 possible hands.
 * 		This class uses an integers array, not boolean, representing the 169 possible hands.
 * 		The integer values 
 * 
 * The int values are > 0 for hand is not this range and > 0 for hand is in this range.
 * In a future revision I may combine ranges
 * For example:
 * 		1		hand is in this range
 * 		10	Hand is limp		 
 * 		20	Hand is open
*		21	Hand is open if 1 opponent and we are IP
*		22	Hand is open if 1 opponent and we are OOP
* 		30	Hand is call	 	
* 		40	Hand is bet3	 	
* 
* Lots to think about
 */
public class HandRange implements Constants {

	static final int HANDS = 169;
	static final int NUM_ROWS = 13;
	static final int NUM_COLS = 13;

	static final int SUITED = 0;
	static final int OFFSUIT = 1;
	static final int PAIR = 2;
	static final int INVALID = 2;
	/*- - Number of 2 card combinations. */
	static int COMBINATIONS = 1326;

	static final Color open = Color.CYAN;
	static final Color call = Color.YELLOW;
	static final Color minRaise = Color.PINK;
	static final Color bet2 = Color.CYAN;
	static final Color bet3 = Color.RED;
	static final Color bet4 = Color.RED;
	static final Color none = Color.LIGHT_GRAY;
	static final Color control = Color.GREEN;

	/*- shadow effect */
	static final int NOT_RANGE = -1;
	static final int FOLD = 0;
	static final int IN_RANGE = 1;
	static final int LIMP = 10;
	static final int OPEN = 20;

	static final int CALL = 30;
	static final int BET3 = 40;

	static final int CALL_BET3 = 50;
	static final int BET4 = 60;

	static final int CALL_BET4 = 70;
	static final int ALLIN = 80;

	static final int CALL_ALLIN = 90;

	static final int NO = 0;
	static final int UTG = 1;
	static final int MP = 2;
	static final int CO = 3;
	static final int BU = 4;
	static final int SB = 5;
	static final int BB = 6;
	/*- 
	 * Hand order array. 
	 * This array based ao EV calculations
	 */
	static HandRangeOrder order = new HandRangeOrder();
	static boolean rangeRead;
	static boolean firstTime = true;
	/*- 
	 *  Array of all possible 2 card combinations - 169 hands.  From PioSolver
	*/
	static int[] handsArrayPioSolver = { UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG, UTG,
			UTG, UTG, MP, MP, CO, CO, CO, BU, BU, BU, UTG, UTG, UTG, UTG, UTG, MP, CO, BU, BU, BU, SB, SB, SB, UTG, MP,
			MP, UTG, UTG, MP, CO, BU, SB, SB, SB, SB, SB, UTG, CO, CO, CO, UTG, UTG, CO, BU, BU, SB, SB, SB, SB, BU, BU,
			BU, BU, BU, UTG, MP, CO, BU, SB, SB, NO, NO, BU, SB, SB, SB, SB, BU, UTG, MP, CO, BU, SB, NO, NO, BU, SB,
			SB, SB, SB, SB, SB, UTG, MP, CO, SB, SB, NO, BU, SB, SB, NO, NO, NO, SB, SB, UTG, UTG, BU, SB, NO, BU, SB,
			SB, NO, NO, NO, NO, SB, SB, UTG, MP, BU, SB, BU, SB, NO, NO, NO, NO, NO, NO, SB, SB, MP, BU, SB, SB, NO, NO,
			NO, NO, NO, NO, NO, NO, NO, NO, CO, SB, SB, NO, NO, NO, NO, NO, NO, NO, NO, NO, NO, NO, CO };
	/*-
	 *  Equilab Equity vs. 5 random hands - Playability  
	 *  Constructed from playabilityArray.
	 *  
	 *  Array matches handArray but values are playability value, index 0 is AA 169, index 1 AKs  4
	 *  playabilityArrayNumeric
	 *  Values are 1 - 169, 169 being AA the most playable
	 *  
	 *  Array matches handArray but values are index into 
	 *  Values are index into playabilityArray, an array of String
	  */
	static int[] playabilityArrayNumeric = new int[HANDS];
	static int[] playabilityArrayOrder = new int[HANDS];
	static int[] orderArray = new int[HANDS];
	/*- Array of all possible 2 card combinations - 169 hands. @formatter:off  */
	static final String[] handsArray = { "AA", "AKs", "AQs", "AJs", "ATs", "A9s", "A8s", "A7s", "A6s", "A5s", "A4s",
			"A3s", "A2s", "AKo", "KK", "KQs", "KJs", "KTs", "K9s", "K8s", "K7s", "K6s", "K5s", "K4s", "K3s", "K2s",
			"AQo", "KQo", "QQ", "QJs", "QTs", "Q9s", "Q8s", "Q7s", "Q6s", "Q5s", "Q4s", "Q3s", "Q2s", "AJo", "KJo",
			"QJo", "JJ", "JTs", "J9s", "J8s", "J7s", "J6s", "J5s", "J4s", "J3s", "J2s", "ATo", "KTo", "QTo", "JTo",
			"TT", "T9s", "T8s", "T7s", "T6s", "T5s", "T4s", "T3s", "T2s", "A9o", "K9o", "Q9o", "J9o", "T9o", "99",
			"98s", "97s", "96s", "95s", "94s", "93s", "92s", "A8o", "K8o", "Q8o", "J8o", "T8o", "98o", "88", "87s",
			"86s", "85s", "84s", "83s", "82s", "A7o", "K7o", "Q7o", "J7o", "T7o", "97o", "87o", "77", "76s", "75s",
			"74s", "73s", "72s", "A6o", "K6o", "Q6o", "J6o", "T6o", "96o", "86o", "76o", "66", "65s", "64s", "63s",
			"62s", "A5o", "K5o", "Q5o", "J5o", "T5o", "95o", "85o", "75o", "65o", "55", "54s", "53s", "52s", "A4o",
			"K4o", "Q4o", "J4o", "T4o", "94o", "84o", "74o", "64o", "54o", "44", "43s", "42s", "A3o", "K3o", "Q3o",
			"J3o", "T3o", "93o", "83o", "73o", "63o", "53o", "43o", "33", "32s", "A2o", "K2o", "Q2o", "J2o", "T2o",
			"92o", "82o", "72o", "62o", "52o", "42o", "32o", "22" };
	/*- Equilab Equity vs. 5 random hands - Playability @formatter:off  */
	static final String[] playabilityArray = { "AA", "KK", "QQ", "JJ", "AKs", "TT", "AQs", "KQs", "AKo", "AJs",
			"KJs", "ATs", "99", "QJs", "AQo", "KTs", "QTs", "KQo", "JTs", "AJo", "A9s", "88", "KJo", "A8s", "K9s",
			"ATo", "QJo", "Q9s", "A7s", "T9s", "J9s", "A5s", "KTo", "77", "A4s", "A6s", "QTo", "JTo", "K8s", "A3s",
			"Q8s", "K7s", "A2s", "T8s", "J8s", "98s", "A9o", "66", "K6s", "K5s", "K9o", "A8o", "Q7s", "K4s", "87s",
			"T7s", "Q9o", "97s", "T9o", "J7s", "J9o", "K3s", "55", "Q6s", "A7o", "K2s", "A5o", "Q5s", "76s", "86s",
			"Q4s", "A4o", "A6o", "96s", "T6s", "K8o", "44", "J6s", "Q3s", "65s", "A3o", "J5s", "T8o", "Q8o", "Q2s",
			"75s", "J8o", "98o", "K7o", "54s", "J4s", "33", "A2o", "85s", "J3s", "T5s", "64s", "95s", "K6o", "J2s",
			"T4s", "22", "53s", "74s", "K5o", "T3s", "87o", "97o", "Q7o", "T7o", "84s", "J7o", "T2s", "43s", "K4o",
			"63s", "94s", "Q6o", "K3o", "93s", "76o", "52s", "73s", "92s", "Q5o", "86o", "K2o", "42s", "83s", "96o",
			"T6o", "82s", "65o", "Q4o", "62s", "J6o", "32s", "Q3o", "75o", "72s", "J5o", "64o", "Q2o", "85o", "J4o",
			"54o", "95o", "T5o", "J3o", "53o", "T4o", "J2o", "74o", "T3o", "43o", "84o", "63o", "T2o", "94o", "93o",
			"52o", "73o", "92o", "42o", "83o", "62o", "82o", "32o", "72o" };
	private static TitledBorder rangeTitle;
	private static final JTextField[] textArray = new JTextField[HANDS];
	private static final JFrame frame = new JFrame();
	private static int x = 50;
	private static int y = 50;
	private static String title = "";
	/*-
	 * This array represents the commonly used 13 X 13 matrix to represent suited,
	 * ofsuit, and pairs An element is true if that card is in the range.
	 */
	int[] rangeArray = new int[HANDS];
	private final JPanel panel = new JPanel();

	/*-  
	 * Constructor.
	 */
	HandRange() {
		for (int i = 0; i < HANDS; ++i) {
			rangeArray[i] = NOT_RANGE;
		}
		if (!rangeRead) {
			order.readRange("C:\\Hold'emDatabase\\PlayabilityOrder.ser");
			createPlayabilityArrayNumeric();
			rangeRead = true;
		}
	}


	/*-
	 * Create  playabilityArrayNumeric 
	 * The array will contain values from 1 - 169
	 * AA is 169, 72o is 1
	 */
	static void createPlayabilityArray  () {
			int k = 0;
			int num = 169;
			for (int i = 0; i < HANDS; ++i) {
				k = getHandStringToIndex(playabilityArray[i]);				 playabilityArrayNumeric[k] = num--;
			}
		}


	/*-
	 * Create  playabilityArrayNumeric 
	 * The array will contain values from 1 - 169
	 * AA is 169, 72o is 1
	 * Create  playabilityArrayOrder 
	 * AA is 0, AKs is 4, the reverse of playabilityArrayNumeric 
	 * Copy RangeOrder values to array   TODO 
	 */
	static void createPlayabilityArrayNumeric () {
			int k = 0;
			for (int i = 0; i < HANDS; ++i) {
				k = getHandStringToIndex(playabilityArray[i]);				
				playabilityArrayNumeric[k] = 169-k;
				playabilityArrayOrder[i] = k;
				orderArray[i] = order.getHandIndex(i);
				}
		}


	/*- Get playabilityNumeric value */
	static int getPlayabilityNumeric(int index) {
		return 	playabilityArrayNumeric[index]; 
	}


	/*- Get playabilityOrder value */
	static int getPlayabilityOrder(int index) {
		return 	playabilityArrayOrder[index]; 
	}


	/*- Get order value */
	static int getOrder(int index) {
		return 	orderArray[index]; 
	}


	/*- Get hand string from rankingArray using index. */
	String getPlayabilityArrayString(int index) {
		return handsArray[order.getHandIndex(index)];
	}


	/*- Get hand string from handArray using index. */
	String getHandString(int index) {
		return handsArray[index];
	}


	/*- Find hand in handArray and return index Arg0 - hand in string format. */
	static int getHandStringToIndex(String hand) {
		for (int $ = 0; $ < HANDS; ++$) {
			if (handsArray[$].equals(hand)) {
				return $;
			}
		}
		System.out.println("ERROR HandRange handStringToIndex() Invalid hand " + hand);
		return -1;
	}


	/*- Clear hand range. */
	void clearRange() {
		for (int i = 0; i < HANDS; ++i) {
			rangeArray[i] = NOT_RANGE;
		}
	}


	/*- Count Hands. */
	int countHands() {
		int $ = 0;
		for (int i = 0; i < HANDS; ++i) {
			if (this.rangeArray[i] > 0) {
				$++;
			}
		}
		return $;
	}


	/*- First hand Hands. */
	int firstHand() {
		for (int $ = 0, i = 0; i < HANDS; i++) {
			if (this.rangeArray[i] > 0) {
				$ = order.getHandIndex(i);
				return $;
			}
		}
		return 0;
	}


	/*- Count Hands from starting hand. */
	int countHandsFrom(int from) {
		int $ = 0;
		for (int i = from; i < HANDS; ++i) {
			if (this.rangeArray[i] > 0) {
				$++;
			}
		}
		return $;
	}


	/*-
	 *  Build Range using Playability and the number of 2 card hands Arg0 -
	 * Number of cards in new range.
	 */
	void buildRangePlayabilityHands(int hands) {
		int k = 0;
		clearRange();
		for (int i = 0; i < hands; ++i) {
			k = order.getHandIndex(i);
			rangeArray[k] = IN_RANGE;
		}
	}


	/*-
	 * Build Range using Playability and the number of 2 card hands Arg0 - The
	 * Playability index to start with Arg1 - The number of hands to add
	 */
	void buildRangePlayabilityHandsStartEnd(int start, int count) {
		int k = 0;
		final int end = count + start;
		clearRange();
		// Find
		for (int i = start; i <= end; ++i) {
			k = order.getHandIndex(i);
			rangeArray[k] = IN_RANGE;
		}
	}


	/*-
	 * Build Range using Playability and the number of 2 card hands Returns the
	 * Playability index of the first card in the array
	 */
	int getFirstHandInRangePlayability() {
		for (int $ = 0, i = 0; i < HANDS; i++) {
			if (this.rangeArray[i] > 0) {
				$ = order.getHandIndex(i);
				return $;
			}
		}
		return -0;
	}


	/*-
	 *  Build Maximum range Range The resulting range will be the hands that are
	 * possible after using other ranges. 
	 * For example, preflop, after orbit 0 the  possible hands to start orbit 1 with are the combination of Open, Call, and
	 * Limp. 
	  */
	void buildRangeMaxRange(HandRange r) {
		for (int i = 0; i < HANDS; ++i) {
			if (r.isRangeArray(i) > 0) {
				this.setRangeArray(i, IN_RANGE);
			}
		}
	}


	/*-
	 *  Build Range using Playability and the number of 2 card hands 
	 *  Arg0 - Number of card to put in new Range 
	 *  Arg1 - Range with hands to exclude
	 * 
	 * This Method constructs a Range array that does not start with the first card.
	 * This is generally because the Range being constructed is in the shadow of
	 * another range, Only cards not in the first Range win be added
	 */
	void buildRangePlayabilityHands(int hands, HandRange range2) {
		int k = 0;
		final int r2 = range2.countHands();
		clearRange();
		for (int i = r2; i < hands; ++i) {
			k = order.getHandIndex(i);
			rangeArray[k] = IN_RANGE;
		}
	}


	/*- 
	 * Build Range using an OrderArray and the number of 2 card hands 
	 * Arg0 -  Number of card to put in new Range 
	 * Arg1 - Range with hands to exclude 
	 * Arg2 - Order array to use in building new range
	 * 
	 * This Method constructs a Range array that does not start with the first card.
	 * This is generally because the Range being constructed is in the shadow of
	 * another range, Only cards not in the first Range win be added
	 */
	void buildRangeOrder(int hands, HandRange range2, HandRangeOrder o) {
		int k = 0;
		final int r2 = range2.countHands();
		clearRange();
		for (int i = r2; i < hands; ++i) {
			k = o.getHandIndex(i);
			rangeArray[k] =IN_RANGE;
		}
	}


	/*-
	 * Build Range using Playability and the number of 2 card hands 
	 * Arg0 - Number  of card to start with 
	 * Arg1 - Number of cards to add
	 * 
	 * This Method constructs a Range array that does not start with the first card.
	 * This is generally because the Range being constructed is in the shadow of
	 */
	void buildRangeOrder(int hands, HandRangeOrder o) {
		int k = 0;
		clearRange();
		for (int i = 0; i < hands; ++i) {
			k = o.getHandIndex(i);
			rangeArray[k] = IN_RANGE;
		}
	}


	/*-
	 * Subtract one range from another range. 
	 * All hands that are in the second  range are removed from this range. 
	 * Arg0 - Second range. 
	 * The other range is this.
	 */
	void subtractRange(HandRange r) {
		for (int i = 0; i < HANDS; ++i) {
			if (r.isRangeArray(i) > 0) {
				setRangeArray(i, NOT_RANGE);
			}
		}
	}


	/*-
	 * Subtract one range from another range. 
	 * All hands that are in the second range are removed from this range. 
	 * Arg0 - Second range. 
	 * The other range is  this.
	 */
	void addRange(HandRange r) {
		for (int i = 0; i < HANDS; ++i) {
			if (r.isRangeArray(i) > 0) {
				setRangeArray(i, IN_RANGE);
			}
		}
	}


	/*-
	 * Build Range using Playability and percentage 
	 * A Range for the percentage is constructed using the playability array that is part of this class.
	 * Arg0 - percentage
	 */
	void buildRangePercentage(double percentage) {
		double percentageNew = 0;
		int k = 0;
		clearRange();
		if (percentage != 0) {
			for (int i = 0; i < HANDS; ++i) {
				k = order.getHandIndex(i);
				setRangeArray(k, IN_RANGE);
				percentageNew = getRangePercent();
				final int kk = order.getHandIndex(i + 1);
				double p = getHandType(kk) == PAIR ? .452 : .905;
				if (getHandType(kk) == SUITED) {
					p = .302;
				}
				if (percentageNew >= percentage || percentage - percentageNew < p) {
					break;
				}
			}
		}
	}


	/*-
	 * Build Range using a hand range 
	 * Arg0 - Percentage of card to start with 
	 * Arg1 - Range with hands to exclude
	 * 
	 * This Method constructs a Range array that does not start with the first card.
	 * This is generally because the Range being constructed is in the shadow of
	 * another range, Only cards not in the first Range win be added
	 */
	void buildRangePercentage(double percentage, HandRange range2) {
		double percentageNew = 0;
		int k = 0;
		this.clearRange();
		if (percentage != 0) {
			for (int i = range2.countHands(); i < HANDS; i++) {
				k = order.getHandIndex(i);
				setRangeArray(k, IN_RANGE);
				percentageNew = getRangePercent();
				final int kk = order.getHandIndex(i + 1);
				double p = .905;
				if (getHandType(kk) == PAIR) {
					p = .452;
				}
				if (getHandType(kk) == SUITED) {
					p = .302;
				}
				if (percentageNew >= percentage) {
					break;
				}
				if (percentage - percentageNew < p) {
					break;
				}
			}
		}
	}


	/*-
	 * Build Range using order 
	 * Arg0 - Percentage of card to start with 
	 * Arg1 - Range to start with 
	 * Arg2 - Max number of hands in range
	 * 
	 * This Method constructs a Range array that does not start with the first card.
	 * This is generally because the Range being constructed is in the shadow of
	 * another range, Only cards not in the first Range win be added. The max number
	 * is the most hands that can be in the range. For example, max may be the
	 * combination preflop of open, call, and limp.
	 */
	void buildRangePercentageMaxOrder(double percentage, HandRange range2, int max) {
		double percentageNew = 0;
		int k = 0;

		this.clearRange();
		if (percentage != 0) {
			for (int i = range2.countHands(); i < max; i++) {
				k = order.getHandIndex(i);
				setRangeArray(k, IN_RANGE);
				percentageNew = getRangePercent();
				final int kk = order.getHandIndex(i + 1);
				double p = .905;
				if (getHandType(kk) == PAIR) {
					p = .452;
				}
				if (getHandType(kk) == SUITED) {
					p = .302;
				}
				if (percentageNew >= percentage) {
					break;
				}
				if (percentage - percentageNew < p) {
					break;
				}
			}
		}
	}


	/*-
	 * Build Range using order Arg0 - Percentage of card to start with Arg1 -
	 * Range to start with Arg2 - Max number of hands in range
	 * 
	 * This Method constructs a Range array that does not start with the first card.
	 * This is generally because the Range being constructed is in the shadow of
	 * another range, Only cards not in the first Range win be added. The max number
	 * is the most hands that can be in the range. For example, max may be the
	 * combination preflop of open, call, and limp.
	 */
	void buildRangePercentageMax(double percentage, int max) {
		double percentageNew = 0;
		int k = 0;

		this.clearRange();
		if (percentage != 0) {
			for (int i = 0; i < max; ++i) {
				k = order.getHandIndex(i);
				setRangeArray(k, IN_RANGE);
				percentageNew = getRangePercent();
				final int kk = order.getHandIndex(i + 1);
				double p = .905;
				if (getHandType(kk) == PAIR) {
					p = .452;
				}
				if (getHandType(kk) == SUITED) {
					p = .302;
				}
				if (percentageNew >= percentage || percentage - percentageNew < p) {
					break;
				}
			}
		}
	}


	/*-
	 * This method will search the hand array for the last card, in playability
	 * order. The key assumption is that the range was initially constructed in
	 * playability order, The index is into the rangeArray
	 */
	int getLastHandPlayability() {
		int $ = 0;
		for (int i = 168; i > 0; ++i) {
			$ = order.getHandIndex(i);
			if (rangeArray[$] > 0) {
				break;
			}
		}
		return $;
	}


	/*-
	 * This method will search the hand array for the last card, in playability
	 * order. The key assumption is that the range was initially constructed in
	 * playability order, The last hand in the array is removed ( changed to false )
	 */
	boolean removeOneHandPlayability() {
		rangeArray[getLastHandPlayability()] =NOT_RANGE;
		return true;
	}


	/*-
	 *This method will search the hand array for the last card, in playability
	 * order. The key assumption is that the range was initially constructed in
	 * playability order, The last playability hand in the array + 1, is added using
	 * playability ( changed to true )
	 */
	void addOneHandPlayability() {
		int k = 0;
		int last = 0;
		for (int i = 0; i < 169; ++i) {
			k = order.getHandIndex(i);
			if (rangeArray[k] < 0) {
				last = i;
				break;
			}
		}
		k = order.getHandIndex(last + 1);
		rangeArray[k] = IN_RANGE;
	}


	/*-
	 *  Set the value of an element in an array 0f 169 possible hands Arg0 is the
	 * first card Arg1 is the second card Arg2 is the boolean condition to set this
	 * element An element in rangeArray is set to true or false.
	 */
	void setRangeArray(Card card1, Card card2, int b) {
		rangeArray[getArrayIndexCard(card1, card2)] = b;
	}


	/*-
	 * Set the value of an element in an array 0f 169 possible hands Arg0 is the
	 * index into array Arg1 is the boolean condition to set this element An element
	 * in rangeArray is set to true or false.
		 */
	void setRangeArray(int ndx, int b) {
		if (ndx >= 0 && ndx <= HANDS) {
			rangeArray[ndx] = b;
		} else {
			System.out.println("ERROR HandRange setRangeArray() invalid index " + ndx);
		}
	}


	/*-
	 *  Returns the value index into an array of the 169 shorthand hands Arg0 is
	 * the first card Arg1 is the second card Returns an index 0 - 195.
	 */
	static int getArrayIndexString(String card1, String card2) {
		final var c2 = new Card(card2);
		return getArrayIndexCard((new Card(card1)), c2);
	}


	/*-
	 */
	static int getCard1ValueForIndex(int ndx) {
		for (int i = 0; i < 13; ++i) {
			if (handsArray[ndx].substring(0, 1).equals(Card.CARDS[i])) {
				return Card.CARD_VALUE[i];
			}
		}
		return -1;
	}


	/*-
	 *  Get value at index for first card - class A = 0, 2 = 1, etc..
	 */
	static int getCard2ValueForIndex(int ndx) {
		for (int i = 0; i < 13; ++i) {
			if (handsArray[ndx].substring(1, 2).equals(Card.CARDS[i])) {
				return Card.CARD_VALUE[i];
			}
		}
		return -1;
	}


	/*-
	 * Returns the value index into an array of the 169 shorthand hands Arg0 is
	 * the first card Arg1 is the second card Returns an index 0 - 195.
	 */
	static int getArrayIndexCard(Card card1, Card card2) {
		final int v1 = card1.getValue() == 0 ? 13 : card1.getValue();
		int v2 = card2.getValue();
		if (v2 == 0) {
			v2 = 13;
		}
		final int $ = (-(v1 - 13)) * 1;
		final int j = (-(v2 - 13)) * 1;
		return card1.getSuit() != card2.getSuit() ? $ + 13 * j : j + 13 * $;
	}


	/*-
	 * - Returns the type of hand Arg0 is the first card Arg1 is the second card.
	 */
	static int getHandType(int ndx) {
		final var $ = handsArray[ndx];
		if ($.length() == 2) {
			return PAIR;
		}
		if ($.length() != 3) {
			return INVALID;
		}
		if ("s".equals($.substring(2))) {
			return SUITED;
		}
		return "o".equals($.substring(2)) ? OFFSUIT : INVALID;
	}


	/*-
	 * Returns the value of of an element in rangeArray Arg0 is the first card
	 * Arg1 is the second card Returns to true or false.
	 */
	int isRangeArray(Card card1, Card card2) {
		return rangeArray[getArrayIndexCard(card1, card2)];
	}


	/*-
	 *Returns the value of of an element in rangeArray Arg0 is the array index
	 * Returns to true or false.
	 */
	int isRangeArray(int ndx) {
		if (ndx <= HANDS && ndx >= 0) {
			return rangeArray[ndx];
		}
		System.out.println(new StringBuilder().append("ERROR  HandRange isRangeArray() index out of range ").append(ndx).append(" ").append(rangeArray.length).toString());
		return NOT_RANGE;
	}


	/*-
	 * Returns the String value of hand Arg0 is the first card Arg1 is the second
	 * card Returns hand as a string.
	 */
	static String getRangeArrayString(Card card1, Card card2) {
		return handsArray[getArrayIndexCard(card1, card2)];
	}


	/*-
	 * Returns the String value of hand Arg0 is the index Returns hand as a
	 * string.
	  */
	static String getRangeArrayString(int ndx) {
		return handsArray[ndx];
	}


	/*-
	 *Returns the String value of hand Arg0 is the first card as a String Arg1 is
	 * the second card as a String Returns hand as a string.
	 */
	String getRangeArrayStringx(String c1, String c2) {
		final var card2 = new Card(c2);
		return handsArray[getArrayIndexCard((new Card(c1)), card2)];
	}


	/*-
 	*	Count combinations. 
  	*/
	int getCombos() {
		int $ = 0;
		for (int i = 0; i < HANDS; ++i) {
			if (rangeArray[i] > 0) {
				if (getHandType(i) == SUITED) {
					$ += 4;
				}
				if (getHandType(i) == PAIR) {
					$ += 6;
				}
				if (getHandType(i) == OFFSUIT) {
					$ += 12;
				}
			}
		}
		return $;
	}


	/*-
	 * Show panel for this range
	 */
	void showRange() {
		constructPanel();
	}


	/*-
	 * Show panel for this range
	 * Arg0 - frame title
	  * Arg1 - frame x position
	  * Arg2 - frame y position
	 */
	void showRange(String titl, int xx, int yy) {
		x = xx;
		y = yy;
		title = titl;
		constructPanel();
	}


	/*-
	*  Construct panel. 
	*/
	void constructPanel() {
	
		final var panel = new JPanel();
		
		final var panel0 = new JPanel();
		panel0.setLayout(new BoxLayout(panel0, BoxLayout.Y_AXIS));
		final var f0 = new Font(Font.SERIF, Font.BOLD, 18);
		panel0.setFont(f0);
		panel0.setSize(200, 200);
		
		final var panel1Layout = new GridLayout(NUM_ROWS, NUM_COLS);
		final var panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		panel1.setLayout(panel1Layout);
		panel1.setFont(f0);
		
		final var panel3 = new JPanel();
		final TitledBorder rangeTitle;
		final var textArray = new JTextField[HANDS];

		final var combos1 = new JTextField("Combos1");
		final var comboPer1 = new JTextField("Percent1");
		final var hands1 = new JTextField("Hands");
		final var frame = new JFrame();
		
		final var frameDim = new Dimension(300, 350);
		final var frameDimN = new Dimension(200, 150);
				
  //	frame.setLayout(new GridLayout(2,1));
 	//	frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(x, y);
		frame.setPreferredSize(new Dimension(500, 600));
		frame.setTitle(title);
		frame.setMaximumSize(frameDimN);
		frame.setMinimumSize(frameDimN);
	
		final var ff = new Font(Font.SERIF, Font.BOLD, 10);
		panel.setLayout(new GridLayout(13, 13));
		panel.setMaximumSize(new Dimension(30, 30));
 		
		// Hand matrix
		final var buttonDim = new Dimension(1, 1);

		rangeTitle = BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(""));
		panel1.setBorder(rangeTitle);
		rangeTitle.setTitle("");
		for (int i = 0; i < HANDS; ++i) {
			textArray[i] = new JTextField(this.getHandString(i));
			textArray[i].setFont(ff);
			textArray[i].setMaximumSize(buttonDim);
			textArray[i].setMinimumSize(buttonDim);
			textArray[i].setBackground(Color.LIGHT_GRAY);
			textArray[i].setForeground(Color.BLACK);
			panel1.add(textArray[i]);
		}
		panel0.add(panel1);
		
		for (int i = 0; i < HANDS; ++i) {
			textArray[i].setBackground(Color.LIGHT_GRAY);
		}
		for (int i = 0; i < HANDS; ++i) {
			if (this.isRangeArray(i)> 0) {
				textArray[i].setBackground(Color.GREEN);
			}
		}
		
		panel3.setSize(300, 40);
		panel3.setBackground(Color.gray);
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
		final var dim3 = new Dimension(300, 40);
		panel3.setMaximumSize(dim3);
		panel3.setMinimumSize(dim3);
		final var f3 = new Font(Font.SERIF, Font.BOLD, 16);
		combos1.setFont(f3);
		comboPer1.setFont(f3);
		hands1.setFont(f3);
		combos1.setBackground(open);
		comboPer1.setBackground(open);
		hands1.setBackground(open);

		panel3.add(combos1);
		panel3.add(comboPer1);
		panel3.add(hands1);
		panel0.add(panel3);

		
		final var pattern = "##.0";
		final var df = new DecimalFormat(pattern);
		final int i = this.getCombos();
		var st1 = new StringBuilder().append(" ").append(i).append(" ").toString();
		combos1.setText(st1);
		final double per1 =this.getRangePercent();
		 st1 = df.format(per1);
		comboPer1.setText(new StringBuilder().append(" ").append(st1).append("%    ").toString());
		combos1.setBackground(open);
		comboPer1.setBackground(open);
		hands1.setBackground(open);
		final int j = this.countHands();
		st1 = new StringBuilder().append(" ").append(j).append(" ").toString();
		hands1.setText(st1);
		
		panel3.add(combos1);
		panel3.add(comboPer1);
		panel3.add(hands1);
		
		frame.add(panel0);
	 // 	frame.add(panel3);
		panel.repaint();
		frame.pack();
		frame.setVisible(true);
	}


	/*-
	 *
 	*	Return shorthand for a range 
 	*
 	*/
	String shorthand() {
		var st = "";
		var singleSt = "";
		var sequentialSt = "";
		int first = 0;
		int last = 0;
		int len = 0;
		boolean plus = false;
		boolean cont = false;

		// Pairs in a range
		first = -1;
		if (rangeArray[0] > 0) {
			plus = true;
		}
		singleSt = "";
		sequentialSt = "";
		for (int i = 0; i < HANDS; i += 14) {
			first = -1;
			last = i;
			cont = false;
			if (rangeArray[i]> 0) {
				plus = true;
				cont = false;
			} else {
				plus = false;
				cont = true;
			}
			if (rangeArray[i]> 0) {
				if (first == -1 && rangeArray[i] > 0) {
					first = i;
				}
				if ((i >= HANDS ||  rangeArray[i + 14] < 0) && !cont && first != -1) {
					last = i;
					if (plus && first != last) {
						sequentialSt += getRangeArrayString(last) + "+, ";
					} else {
						singleSt += getRangeArrayString(first) + ", ";
					}
					plus = false;
					first = -1;
				} else {
					last = i;
					if (first != -1) {
						if (first == last) {
							singleSt += getRangeArrayString(first) + ", ";
						} else if (first != 0) {
							last = i;
							sequentialSt += new StringBuilder().append(getRangeArrayString(first)).append("-").append(getRangeArrayString(last)).append(",  ").toString();
						} else {
							sequentialSt += getRangeArrayString(last) + "+, ";
						}
					}
				}
			}
		}
		st += sequentialSt;
		st += singleSt;

		// Suited
		len = 12;
		for (int i = 1; i < HANDS; i += 14) {
			singleSt = "";
			sequentialSt = "";
			first = -1;
			last = i;
			cont = false;
			if (rangeArray[0] > 0) {
				plus = true;
				cont = false;
			} else {
				plus = false;
				cont = true;
			}
			for (int j = i; j <= i + len - 1; ++j) {
				if (first == -1 && rangeArray[j] > 0) {
					first = j;
				}
				if ((j == i + len - 1 || rangeArray[j + 1] < 0) && !cont && first != -1) {
					last = j;
					if (plus && first != last) {
						sequentialSt += getRangeArrayString(last) + "+, ";
					} else {
						singleSt += getRangeArrayString(first) + ", ";
					}
					cont = true;
					plus = false;
					first = -1;
				} else {
					// Now we start a new search for single or sequential not starting with column 0
					if ((j == i + len - 1 || rangeArray[j + 1] < 0) && cont && first != -1) {
						last = j;
						if (first == last) {
							singleSt += getRangeArrayString(first) + ", ";
						} else {
							sequentialSt += new StringBuilder().append(getRangeArrayString(first)).append("-").append(getRangeArrayString(last)).append(",  ").toString();
						}
						first = -1;
					}
				}
			}
			--len;
			first = -1;
			plus = false;
			st += sequentialSt;
			st += singleSt;
		}

		// Offsuit
		len = 0;
		for (int i = 13; i < HANDS - 13; i += 13) {
			singleSt = "";
			sequentialSt = "";
			first = -1;
			last = i;
			cont = false;
			if (rangeArray[i] > 0) {
				plus = true;
				cont = false;
			} else {
				plus = false;
				cont = true;
			}
			for (int j = i; j <= i + len; ++j) {
				if (first == -1 && rangeArray[j] > 0) {
					first = j;
				}
				if ((j == i + len || rangeArray[j + 1] < 0) && !cont && first != -1) {
					last = j;
					if (plus && first != last) {
						sequentialSt += getRangeArrayString(last) + "+, ";
					} else {
						singleSt += getRangeArrayString(first) + ", ";
					}
					cont = true;
					plus = false;
					first = -1;
				} else {
					// Now we start a new search for single or sequential not starting with column 0
					if ((j == i + len ||  rangeArray[j + 1] < 0) && cont && first != -1) {
						last = j;
						if (first == last) {
							singleSt += getRangeArrayString(first) + ", ";
						} else {
							sequentialSt += new StringBuilder().append(getRangeArrayString(first)).append("-").append(getRangeArrayString(last)).append(",  ").toString();
						}
						first = -1;
					}
				}
			}
			++len;
			first = -1;
			plus = false;
			st += sequentialSt;
			st += singleSt;
		}

		final int l = st.length();
		final boolean condition = l != 0 && ", ".equals(st.substring(l - 3, l - 1));
		if (condition) {
			st = st.substring(0, l - 3);
		}

		return st;
	}


	/*-
	 * Range percent. What percentage of all two card combinations (1326) does
	 * this range use.
	 */
	double getRangePercent() {
		final int $ = getCombos();
		return $ != 0 ? ((double) $ / COMBINATIONS) * 100 : 0;
	}


	/*-
 	*	Write a HandRange object to a disk file Arg0 - The full path name. 
 	*/
	void writeRange(String path) {
		FileUtils.writeFile(path, rangeArray);
	}


	/*-
	*	Read a rangeArray from a disk file Arg0 - The full path name. 
 	*/
	boolean readRange(String path) {
		final var file = new File(path);
		if (!file.exists()) {
			System.out.println("ERROR HandRange readRange() File does not exist " + path);
			return false;
		}
		this.rangeArray = FileUtils.readFile(path, rangeArray);
		if (this.rangeArray == null) {
			return false;
		}
		return true;
	}


	/*-
	*	Delete file. 
	*/
	static boolean deleteFile(String fileN) {
		return FileUtils.deleteFile(fileN);
	}
}
