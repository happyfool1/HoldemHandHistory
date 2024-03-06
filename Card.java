package holdemhandhistory;

/*-  *****************************************************************************
 *Card class represents a  single card in a standard 52-card deck.
 *
  * @author PEAK_
  ***************************************************************************** */
public class Card implements Constants {

	// 13 cards
	static String[] CARDS = { "A", "K", "Q", "J", "T", "9", "8", "7", "6", "5", "4", "3", "2" };
	static String[] CARDS_REVERSE = { "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A" };
	static int[] CARD_VALUE = { 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
	static int[] CARD_INDEX = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
	// 52 card deck
	static String[] CARD_STRING = { "As", "Ks", "Qs", "Js", "Ts", "9s", "8s", "7s", "6s", "5s", "4s", "3s", "2s", "Ac",
			"Kc", "Qc", "Jc", "Tc", "9c", "8c", "7c", "6c", "5c", "4c", "3c", "2c", "Ad", "Kd", "Qd", "Jd", "Td", "9d",
			"8d", "7d", "6d", "5d", "4d", "3d", "2d", "Ah", "Kh", "Qh", "Jh", "Th", "9h", "8h", "7h", "6h", "5h", "4h",
			"3h", "2h" };
	// Values for cards in CARDS_STRING - not index but card values 0-51
	static int[] CARD_VALUES = { 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 25, 24, 23, 22, 21, 20, 19, 18, 17, 16, 15,
			14, 13, 38, 37, 36, 35, 34, 33, 32, 31, 30, 29, 28, 27, 26, 51, 50, 49, 48, 47, 46, 45, 44, 43, 42, 41, 40,
			39 };
	// Suits for cards in CARD_STRING
	static String[] CARD_SUITS = { "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "s", "c", "c", "c", "c",
			"c", "c", "c", "c", "c", "c", "c", "c", "c", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d", "d",
			"d", "h", "h", "h", "h", "h", "h", "h", "h", "h", "h", "h", "h", "h" };
	static String[] CARD_SUIT = { "Spade", "Club", "Diamond", "Heart" };
	// Values for cards in
	static int[] VALUES = { 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 12, 11,
			10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0 };
	// Suits values for cards in
	static int[] SUIT_VALUES = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2,
			2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3 };
	/*-
	 * The card. Recent addition is suit and value
	 * This change will allow for performance improvements in
	 * Classes like Table, Play, Evaluate and others.
	 *
	 * Designed for performance uses more storage but conversion is not actually necessary.
	 * Card a = new Card(0);  is Ac
	 * a.st is the String representation of Card a
	 * card 0 is As, card 13 is Ac, card 26 is Ad, 39 is Ah
	 */
	int card; // Card value 0-51 for 52 card deck
	int suit; // Suit
	int value; // Value is 0 - 12, 12 is A

	/*- Constructor Arg0 - Card value 0-51 */
	Card(int value) {
		this.card = value;
		this.suit = SUIT_VALUES[value];
		this.value = VALUES[value];
	}

	/*- Constructor - Card value 0-12 0 is A and suit 0 - 3 */
	Card(int value, int suit) {
		this.card = value + 13 * suit;
		this.suit = suit;
		this.value = value;
	}

	/*-  Constructor- String representation Returns Card. */
	Card(String text) {
		var hit = false;
		for (var i = 0; i < CARD_STRING.length; ++i) {
			if (CARD_STRING[i].equals(text)) {
				this.card = i;
				this.suit = SUIT_VALUES[i];
				this.value = VALUES[i];
				hit = true;
				break;
			}
		}
		if (hit) {
			return;
		}
		if ("".equals(text)) {
			Crash.log(new StringBuilder().append("//CARD ERROR invalid String  is blank -").append(text).append("-")
					.toString());
		} else {
			Crash.log(new StringBuilder().append("//CARD ERROR invalid String  -").append(text).append("-").toString());
		}

	}

	/*- A is 0, 2 is 12 */
	static String cardToString(int c) {
		return CARD_STRING[c];
	}

	/*- c is 0 - 51 for suits Spade 0 - 12, 13-35, */
	static int cardToSuit(Card c) {
		return c.suit;
	}

	/*- c is 0 - 51 for values 12 - 0, 12 - 0 */
	static int cardToValue(Card c) {
		return c.value;
	}

	/*-
	 * Check that all cards in array are valid, not null
	 * Check that there are no duplicates in array
	 * Check value, suit, card for in range
	 * Arg0 - An array of Card
	 * arg1 - Size of array ( .length not used because array might be Flop in a board array. )
	 * Return false if error.
	 */
	static boolean checkCardsValid(Card[] cards, int num) {
		for (var i = 0; i < num; i++) {
			if (cards[i] == null) {
				Logger.log("ERROR Invalid card null " + cards[i].card);
				return false;
			}
			if (cards[i].value < 0 || cards[i].value > 12 || cards[i].card < 0 || cards[i].card > 51
					|| cards[i].suit < 0 || cards[i].suit > 3) {
				Logger.log(new StringBuilder().append("ERROR Invalid card value suit or card  ").append(cards[i].value)
						.append(" ").append(cards[i].card).append(" ").append(cards[i].suit).append(cards[i])
						.toString());
				return false;
			}
		}
		for (var i = 0; i < num - 1; i++) {
			for (var j = i + 1; j < num; j++) {
				if (cards[i].card == cards[j].card) {
					Logger.log("ERROR duplicate cards " + cards[i].toString());
					return false;
				}
			}
		}
		return true;
	}

	/*- index is 0 - 12 for A - 2 */
	// CARD_INDEX { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 }
	static int indexAndSuitToCard(int index, int suit) {
		return CARD_INDEX[index] + 13 * suit;
	}

	/*- index is 0 - 12 for values 12 - 0 */
	static int indexToValue(int index) {
		return CARD_VALUE[index];
	}

	/*- c is 0 - 51 for suits Spade 0 - 12, 13-35, */
	static int intToSuit(int c) {
		return SUIT_VALUES[c];
	}

	/*- c is 0 - 51 for values 12 - 0, 12 - 0 */
	static int intToValue(int c) {
		return VALUES[c];
	}

	/*-
	 * Is this a valid card.
	 * Arg0 = Card as String
	 * Returns tue or false
	 */
	static boolean isValid(String text) {
		for (final var aCARD_STRING : CARD_STRING) {
			if (aCARD_STRING.equals(text)) {
				return true;
			}
		}
		return false;
	}

	/*- value is 12 - 0 for A - 2 , suit is 0 - for Spade, 13 for Club ... */
	static int valueAndSuitToCard(int value, int suit) {
		return CARD_VALUE[value] + 13 * suit;
	}

	/*- value is 12 - 0 for index 0 - 12 */
	static int valueToIndex(int value) {
		return CARD_INDEX[value];
	}

	/*- for use in sorting - returns 0 if equal, -1 if less than, 1 if greater than */
	int compare(Card a, Card b) {
		if (a.value == b.value) {
			return 0;
		}
		return a.value >= b.value ? 1 : 0;
	}

	/*- Compare card for equal in both value and suit  return 0 is same, 1 if not */
	int compareCard(Card a, Card b) {
		return a.card == b.card ? 0 : 1;
	}

	/*- Compare card suits  return 0 is same, 1 if not */
	int compareSuit(Card a, Card b) {
		return a.suit == b.suit ? 0 : 1;
	}

	/*- Check this and another card for equal. */
	boolean equals(Card c) {
		return (this.card == c.card);
	}

	/*- Second letter of card - Ac returns c */
	String getCardSingleChar() {
		return CARD_STRING[this.card].substring(0, 1);
	}

	/*- First letter of card - Ac returns A */
	String getCardSuitSingleChar() {
		return CARD_STRING[this.card].substring(1, 2);
	}

	/*- this.card 0 is As */
	int getInt() {
		return this.card;
	}

	/*- Suit of this.card */
	int getSuit() {
		return this.suit;
	}

	/*- value A = 12 for this.card */
	int getValue() {
		return this.value;
	}

	/*- String value for this.card */
	@Override
	public String toString() {
		return CARD_STRING[this.card];
	}

}
