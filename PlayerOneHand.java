package holdemhandhistory;

import java.math.BigDecimal;

class PlayerOneHand implements java.io.Serializable, Constants {
	static final long serialVersionUID = 1234567L;
	/*- ***********************************************************************************************
	 * This class is used to represent a single player one hand.
	 * Temporary, allows calculations to be performed that require exactly one hand.
	 * Example:
	 * 		 Determine if a player C-bet.
	 *      Determine if a player barreled.
	 *  These calculations can not be performed until the hand is complete.
	 *  
	 *  Methods in this class do a variety of calculations.
	 *  As the software matures this method will likely have additional methods added. 
	 * 
	 *  @author PEAK_
	************************************************************************************************/

	static int pp;

	/*- ***********************************************************************************************
	 * Identification
	 ************************************************************************************************/
	int playerID = -1; // Key
	Long handID; // For test and debug - A unique ID for every hand

	/*-**********************************************************************************************
	 * These arrays age for all streets, Preflop, Flop, Turn, and River.
	 * Index 1 - Street number
	 * Index 2 - Position ( SB, BB, UTG, MP, CO, BUTTON
	 * Index 3 - Orbit
	 * There are 2 types of arrays:
	 ************************************************************************************************/
	// Opportunity
	int[][] limpOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] checkOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] foldOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet1OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet1OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet2OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet2OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet3OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet3OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet4OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet4OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] allinOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callAllinOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] cBetOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] barrelOperPos = new int[NUM_STREETS][NUM_POS];
	// Acted
	int[][] checkPos = new int[NUM_STREETS][NUM_POS];
	int[][] foldPos = new int[NUM_STREETS][NUM_POS];
	int[][] limpPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet1Pos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet1Pos = new int[NUM_STREETS][NUM_POS];
	int[][] bet2Pos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet2Pos = new int[NUM_STREETS][NUM_POS];
	int[][] bet3Pos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet3Pos = new int[NUM_STREETS][NUM_POS];
	int[][] bet4Pos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet4Pos = new int[NUM_STREETS][NUM_POS];
	int[][] allinPos = new int[NUM_STREETS][NUM_POS];
	int[][] callAllinPos = new int[NUM_STREETS][NUM_POS];
	int[][] cBetPos = new int[NUM_STREETS][NUM_POS];
	int[][] barrelPos = new int[NUM_STREETS][NUM_POS];
	// Opportunity
	int[][] checkOperRp = new int[NUM_STREETS][NUM_RP];
	int[][] foldRp = new int[NUM_STREETS][NUM_RP];
	int[][] limpOperRp = new int[NUM_STREETS][NUM_RP];
	int[][] foldOperRp = new int[NUM_STREETS][NUM_RP];
	int[][] bet1OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] callBet1OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] bet2OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] callBet2OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] bet3OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] callBet3OperRp = new int[NUM_STREETS][NUM_RP];
	int[][] bet4OperRp = new int[NUM_STREETS][NUM_RP];

	/*- ************************************************************************************************
	 * Current hand player data. Used for several purpses. 
	 * First index is player seat number 
	 * Second index is street number 
	 * Third index is index is orbit
	  *************************************************************************************************/
	// new
	boolean[] playerRaisedLast = new boolean[STREETS];
	int[] playerAction = new int[STREETS];
	int[] playerRp = new int[STREETS]; // Rp
	boolean[] playerActed = new boolean[STREETS];
	boolean[] playerFold = new boolean[STREETS];
	boolean[] playerLimp = new boolean[STREETS];
	boolean[] playerCall = new boolean[STREETS];
	boolean[] playerCheck = new boolean[STREETS];
	boolean[] playerRaise = new boolean[STREETS];
	boolean[] playerBet = new boolean[STREETS];
	boolean[] playerWalk = new boolean[STREETS];
	int[] playerBetType = new int[STREETS];

	boolean playerWTSD = false;
	boolean playerWonSD = false;

	boolean[] raiseStreet = new boolean[STREETS];

	BigDecimal[] playerRaise$ = new BigDecimal[STREETS];
	BigDecimal[] playerRaiseFrom$ = new BigDecimal[STREETS];
	BigDecimal[] playerRaiseTo$ = new BigDecimal[STREETS];
	BigDecimal[] playerBet$ = new BigDecimal[STREETS];
	BigDecimal[] playerCall$ = new BigDecimal[STREETS];
	BigDecimal[] playerCallShort$ = new BigDecimal[STREETS];
	BigDecimal[] playerCollect$ = new BigDecimal[STREETS];
	BigDecimal[] playerReturnedTo$ = new BigDecimal[STREETS];

	BigDecimal playerStack$ = zeroBD;
	BigDecimal playerPot$ = zeroBD;
	BigDecimal playerWon$ = zeroBD;
	BigDecimal playerPost$ = zeroBD;
	BigDecimal playerAnte$ = zeroBD;
	BigDecimal playerRake$ = zeroBD;
	BigDecimal playerCollected$ = zeroBD;
	int walk;
	double mdf = 0;

	/*-***********************************************************************************************
	 * Clean data Bet and response 
	 * First index is player position  
	 * Second index is bet type. BET1, BET2,BET3, BET4, ALLIN, FOLD, CHECK  ( no calls )
	 ************************************************************************************************/
	boolean[][] betToMe = new boolean[NUM_POS][BETS_MAX];
	// Value is action BET, ALLIN, RAISE, CALL, FOLD
	int[][] response = new int[NUM_POS][BETS_MAX];

	/*-  *****************************************************************************
	 * Do the calculations that this Class was developed for.
	 * 
	 ***************************************************************************** */
	void calculations() {

	}

	/*-  *****************************************************************************
	 * Calculate percentage with error checking
	 ***************************************************************************** */
	double calculatePercentage(double a, double b) {
		if (b <= 0.) {
			return 0.;
		}
		final double $ = 100.0 * (a / b);
		final boolean condition = ($ > 100 || $ < 0) && pp < 10;
		if (condition) {
			++pp;
			Logger.logError(new StringBuilder().append("//ERROR calculatePercentage ").append($).append(" a ").append(a)
					.append(" b ").append(b).toString());
		}
		return $;
	}

}
