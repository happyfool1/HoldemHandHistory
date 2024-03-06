package holdemhandhistory;

import java.math.BigDecimal;
import java.math.RoundingMode;

/*- *****************************************************************************
 * This class is used to guide the details of what is in a this.
 * The rules for Texas Hold'em are the same for every site.
 * This makes it possible for this class to have a set of methods that are not 
 * for a particular site, such as PokerStars, but are for all sites.
 * That is what we have defined as Universal Hand History Format.
 * 
 * Many poker sites have converted to the PokerStars format, such as GGPoker and
 * Poker888 with small variations. We al;ready support all of these sites.
 * Even sites that use the Euro.
 * 
 * This is the definition of Hand History Universal Format.
 * 
 * Because the very poor performance of java.io.Serializable  we do not use a .ser file.
 * Instead, we use a simple .txt file in a comma separated format. 
 * Crude, but far better performance.
 * 
  *  @author PEAK_ 
***************************************************************************** */

public class Hand implements Constants {
	// Maximum number of lines
	static final int MAX = 65;
	static final int MAX_LINES = 30;
	static final int MAX_ACTION_ARRAY = 4 * 6 * 4; // 4 streets, 6 players, 4 orbits

	/*-  *************************************************************************** 
	* This is it.  
	 ******************************************************************************/
	long handID = 0L;
	int status = 0;
	int source = POKER_STARS; // Source of this action array
	int currency = USD;
	int BUSeat = -1;
	BigDecimal SBBet = zeroBD;
	BigDecimal BBBet = zeroBD;
	int[] IDArray = { 0, 0, 0, 0, 0, 0 }; // ID array as int values. see Class NameToID
	BigDecimal[] stack = new BigDecimal[6]; // Starting stack
	BigDecimal[] finalStack = new BigDecimal[6]; // Stack at end of hand
	BigDecimal sidePot1 = zeroBD;
	BigDecimal rakeRound = zeroBD;
	BigDecimal rakeNoRound = zeroBD;
	// From GGPoker sites with jackpot option
	BigDecimal jackpotRake = zeroBD;
	BigDecimal bingoRake = zeroBD;
	BigDecimal unknownRake = zeroBD;
	// Cards
	int[][] showsArray = new int[PLAYERS][2]; // hole cards as int 0 - 51
	int[] boardArray = { 0, 0, 0, 0, 0 }; // Flop, Turn, River, Show Down, and Summary board as int 0 - 51

	/*-  *************************************************************************** 
	* This array is the primary source of information.
	* Step by step through play of hand.
	* Each line in the array roughly corresponds to a line of text in the 
	* Hand History file.  Initial implementation was for  PokerStars so that is the 
	* primary point of reference for understanding.
	* Values that can be calculated are not done until later in the process. 
	* See HandCalculations and GUIEvaluate.
	 ******************************************************************************/
	int actionCount = 0;
	Action[] actionArray = new Action[MAX_ACTION_ARRAY];

	// Constructor
	Hand() {
		for (int i = 0; i < 6; ++i) {
			stack[i] = zeroBD;
			finalStack[i] = zeroBD;
		}
	}

	/*-*****************************************************************************
	 * Convert this object to a string. 
	 ******************************************************************************/
	String toEquivalentString() {
		StringBuilder sb1 = new StringBuilder(4000).append(Long.valueOf(this.handID)).append(",")
				.append(Integer.valueOf(this.status)).append(",").append(Integer.valueOf(this.source)).append(",")
				.append(Integer.valueOf(this.currency)).append(",").append(Integer.valueOf(this.BUSeat)).append(",")
				.append(this.SBBet.toString()).append(",").append(this.BBBet.toString()).append(",");

		for (int i = 0; i < PLAYERS; i++) {
			sb1 = sb1.append(Integer.valueOf(this.IDArray[i])).append(",");
			sb1 = sb1.append(this.stack[i].toString()).append(",");
			sb1 = sb1.append(this.finalStack[i].toString()).append(",");
		}

		sb1 = sb1.append(this.sidePot1.toString()).append(",").append(this.rakeRound.toString()).append(",")
				.append(this.rakeNoRound.toString()).append(",").append(this.jackpotRake.toString()).append(",")
				.append(this.bingoRake.toString()).append(",").append(this.unknownRake.toString()).append(",");

		sb1 = sb1.append(Integer.valueOf(this.actionCount)).append(",");
		for (int i = 0; i < this.actionCount; ++i) {
			sb1 = sb1.append(Integer.valueOf(this.actionArray[i].street)).append(",")
					.append(Integer.valueOf(this.actionArray[i].ID)).append(",")
					.append(Integer.valueOf(this.actionArray[i].action)).append(",")
					.append(Integer.valueOf(this.actionArray[i].seat)).append(",")
					.append(this.actionArray[i].call.toString()).append(",")
					.append(this.actionArray[i].betRaise.toString()).append(",")
					.append(this.actionArray[i].raiseTo.toString()).append(",")
					.append(this.actionArray[i].moneyIn.toString()).append(",")
					.append(this.actionArray[i].money.toString()).append(",")
					.append(this.actionArray[i].stack.toString()).append(",").append(this.actionArray[i].pot.toString())
					.append(",");
		}

		for (int i = 0; i < PLAYERS; i++) {
			sb1 = sb1.append(Integer.valueOf(this.showsArray[i][0])).append(",");
			sb1 = sb1.append(Integer.valueOf(this.showsArray[i][1])).append(",");
		}
		for (int i = 0; i < 5; i++) {
			sb1 = sb1.append(Integer.valueOf(this.boardArray[i])).append(",");
		}

		sb1 = sb1.append(Integer.valueOf(this.actionCount)).append(",");
		for (int i = 0; i < this.actionCount; ++i) {
			sb1 = sb1.append(Integer.valueOf(this.actionArray[i].street)).append(",")
					.append(Integer.valueOf(this.actionArray[i].ID)).append(",")
					.append(Integer.valueOf(this.actionArray[i].action)).append(",")
					.append(Integer.valueOf(this.actionArray[i].seat)).append(",")
					.append(this.actionArray[i].call.toString()).append(",")
					.append(this.actionArray[i].betRaise.toString()).append(",")
					.append(this.actionArray[i].raiseTo.toString()).append(",")
					.append(this.actionArray[i].money.toString()).append(",")
					.append(this.actionArray[i].moneyIn.toString()).append(",")
					.append(this.actionArray[i].stack.toString()).append(",")
					.append(this.actionArray[i].pot.toString());
		}
		return sb1.toString();
	}

	/*-*****************************************************************************
	 *Convert String created by toEquivalentString()) to an object
	 ******************************************************************************/
	Hand fromStringEquivalent(String st) {
		int n = 0;
		int n2 = 0;
		Hand h = new Hand();

		n2 = st.indexOf(",");
		h.handID = Long.parseLong(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.status = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.source = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.currency = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.BUSeat = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.SBBet = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.BBBet = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);

		for (int i = 0; i < PLAYERS; i++) {
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.IDArray[i] = Integer.parseInt(st.substring(n, n2));
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.stack[i] = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.finalStack[i] = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
		}

		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.sidePot1 = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.rakeRound = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.rakeNoRound = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.jackpotRake = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.bingoRake = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.unknownRake = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);

		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.actionCount = Integer.parseInt(st.substring(n, n2));
		for (int i = 0; i < h.actionCount; ++i) {
			h.actionArray[i] = new Action();
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.actionArray[i].street = Integer.parseInt(st.substring(n, n2));
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.actionArray[i].ID = Integer.parseInt(st.substring(n, n2));
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.actionArray[i].action = Integer.parseInt(st.substring(n, n2));
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.actionArray[i].seat = Integer.parseInt(st.substring(n, n2));
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.actionArray[i].call = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.actionArray[i].betRaise = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.actionArray[i].raiseTo = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.actionArray[i].money = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.actionArray[i].moneyIn = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.actionArray[i].stack = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.actionArray[i].pot = new BigDecimal(st.substring(n, n2)).setScale(2, RoundingMode.HALF_EVEN);
		}

		for (int i = 0; i < PLAYERS; i++) {
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.showsArray[i][0] = Integer.parseInt(st.substring(n, n2));
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.showsArray[i][1] = Integer.parseInt(st.substring(n, n2));
		}
		for (int i = 0; i < 5; i++) {
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			h.boardArray[i] = Integer.parseInt(st.substring(n, n2));
		}
		return h;
	}

}
