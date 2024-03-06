package holdemhandhistory;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PlayerClassification implements Constants {

	/*- *************************************************************************************************
	 * The application GUIPLayerClassification creates this class.
	 * It makes a pass through the universal hand history defined by the Hand class
	 * and the Action class. 
	 * No other data collected, just what is used to create this class.
	 * 
	 * GUIPLayerClassification will likely be run several times as it's function is to 
	 * classify players for analysis in the next application GUIAnalysis. 
	 * This is for a couple of reasons. Because literally millions of Hand Histories are analyzed
	 * memory is a significant problem as is performance. 
	 * By creating player selection based on player types we can have our cake and eat it too.
	 * Reports and analysis do not have to be aware of player classification, they simply
	 * analyze the date provided.
	 * 
	 * GUIPLayerClassification uses an instance of this class to select only the p[layers in the 
	 * universal Hand History for a given player. 
	 * Actually, a single hand may have a player of interest and several other players that are ignored. 
	 * Players to be ignored have a playerID that is a negative number. Original number is complimented.
	 * 
	 * The folder containing PlayerClassification files will likely have several files in it, one file for
	 * each player type. Future revisions will allow selection by more subtile ways, such as by
	 * player aggression level, player bet sizing, or whatever is of interest.
	 * 
	 * This makes it possible to collect a lot more data in Players without hitting
	 * memory limits because of the very large size and possible 100,000 players or more.
	 * We would still use the AllPlayers Class to save data without any player classification.
	 * Or just maybe have AllPlayers instances based on classification. 
	 * Lots of possibilities here.
	 * Much TODO !!!!
	 * 
	 *  @author PEAK_
	*************************************************************************************************/

	// Constructor
	PlayerClassification() {

	}

	ClassificationData rules = new ClassificationData();

	/*- ***********************************************************************************************
	* Values calculated  by PlayerClassification and copied here
	 ************************************************************************************************/
	int playerID = -1; // Key
	int handsPlayed; // Number of games played
	int playerType = -1; // LAG, TAG, NIT, ......

	double aggPct; // Aggression percentage
	double af; // Preflop Raise
	double vpip; // Aggression percentage
	double pfr; // Preflop Raise
	double wtsd; // Went to showdown

	double wtsdFlop; // Went to showdown if saw flop
	double wtsdTurn; // Went to showdown if saw turn
	double wtsdRiver; // Went to showdown if saw river
	int betCount1;

	// TODO Used here? will use?
	int showdownCount;
	int wonShowdownCount;
	int handPrevious;
	int wonCount;
	int wwsfCount; // Won when saw Flop
	int wwstCount; // Won when saw Turn
	int wwsrCount; // Won when saw River

	// Values updated by the ???
	int wtsdCount; // Went to showdown if saw flop
	int wtsdTurnCount; // Went to showdown if saw flop
	int wtsdRiverCount; // Went to showdown if saw flop
	int[] streetCount = new int[NUM_STREETS]; // TODO
	// Orbit 0 only - count of times player saw street
	int[] streetCounts = new int[NUM_STREETS];

	int wsdCount; // Won at Showdown
	int winRateBB100; // Win rate in bb/ 100 ( or loss rate if - )
	double bet3Freq; // 3 bet freq

	BigDecimal winnings$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	BigDecimal bbBet$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);

	// Calculated data for player selection
	double[] VPIP = new double[NUM_STREETS];

	int[][] betCount = new int[NUM_STREETS][NUM_POS];
	int[][] raiseCount = new int[NUM_STREETS][NUM_POS];
	int[][] checkOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] foldOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet1OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] limpOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callAllinOperPos = new int[NUM_STREETS][NUM_POS];
	int[][] allinOperPos = new int[NUM_STREETS][NUM_POS];

	// actions All orbits
	int[][] checkPos = new int[NUM_STREETS][NUM_POS];
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
	// Opportunity All orbits
	int[][] callBet1OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet2OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet2OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet3OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet3OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] bet4OperPos = new int[NUM_STREETS][NUM_POS];
	int[][] callBet4OperPos = new int[NUM_STREETS][NUM_POS];

	// @formatter:off
	/*-*****************************************************************************
	 * Convert this object to a string. 
	 ******************************************************************************/
	String toEquivalentString() {
		StringBuilder sb1 = new StringBuilder(3000).append(Integer.valueOf(this.playerID)).append(",")
				.append(Integer.valueOf(this.handsPlayed)).append(",")
				.append(Integer.valueOf(this.playerType)).append(",")
				.append(Double.valueOf(this.aggPct)).append(",")
				.append(Double.valueOf(this.af)).append(",")
				.append(Double.valueOf(this.vpip)).append(",")
				.append(Double.valueOf(this.pfr)).append(",")
				.append(Double.valueOf(this.wtsd)).append(",")	
				.append(Double.valueOf(this.wtsdFlop)).append(",")	
				.append(Double.valueOf(this.wtsdTurn)).append(",")	
				.append(Double.valueOf(this.wtsdRiver)).append(",")	
				.append(Double.valueOf(this.betCount1)).append(",")	
				.append(Integer.valueOf(this.showdownCount)).append(",")
				.append(Integer.valueOf(this.wonShowdownCount)).append(",")
				.append(Integer.valueOf(this.handPrevious)).append(",")
				.append(Integer.valueOf(this.wonCount)).append(",")
				.append(Integer.valueOf(this.wwsfCount)).append(",")
				.append(Integer.valueOf(this.wwstCount)).append(",")
				.append(Integer.valueOf(this.wwsrCount)).append(",")
				.append(Integer.valueOf(this.wtsdCount)).append(",");
		
		
		
		StringBuilder sb2 = new StringBuilder(3000);
		for (int i = 0; i < NUM_STREETS; i++) {
			for (int j = 0; j < NUM_POS; j++) {
				sb2.append(Integer.valueOf(this.checkPos[i][j])).append(",") 
				.append(Integer.valueOf(this.limpPos[i][j])).append(",") 
				.append(Integer.valueOf(this.bet1Pos[i][j])).append(",") 
				.append(Integer.valueOf(this.callBet1Pos [i][j])).append(",") 
				.append(Integer.valueOf(this.bet2Pos [i][j])).append(",") 
				.append(Integer.valueOf(this.callBet2Pos[i][j])).append(",") 
				.append(Integer.valueOf(this.bet3Pos[i][j])).append(",") 
				.append(Integer.valueOf(this. callBet3Pos [i][j])).append(",") 
				.append(Integer.valueOf(this.bet4Pos[i][j])).append(",") 
				.append(Integer.valueOf(this.callBet4Pos[i][j])).append(",") 
				.append(Integer.valueOf(this.allinPos [i][j])).append(",") 
				.append(Integer.valueOf(this.callAllinPos[i][j])).append(",") ;
			}
		}
			
			StringBuilder sb3 = new StringBuilder(3000);
			for (int i = 0; i < NUM_STREETS; i++) {
				sb3.append(Integer.valueOf(this. streetCounts [i])).append(",") ;
			}
			
			StringBuilder sb4 = new StringBuilder(3000);
			sb4.append(Integer.valueOf(this.wsdCount)).append(",") 
			.append(Integer.valueOf(this.winRateBB100)).append(",")		 
			.append(Double.valueOf(this.bet3Freq)).append(",")
			.append(this.winnings$.toString()).append(",") 
			.append(this.bbBet$.toString()).append(",");
		
			StringBuilder sb5 = new StringBuilder(3000);
			for (int i = 0; i < NUM_STREETS; i++) {
				for (int j = 0; j < NUM_POS; j++) {
					sb5.append(Integer.valueOf(this. callBet1OperPos [i][j])).append(",") 
					.append(Integer.valueOf(this.limpPos[i][j])).append(",") 
					.append(Integer.valueOf(this.bet2OperPos[i][j])).append(",") 
					.append(Integer.valueOf(this.callBet2OperPos [i][j])).append(",") 
					.append(Integer.valueOf(this.bet2Pos [i][j])).append(",") 
					.append(Integer.valueOf(this.callBet2Pos[i][j])).append(",") 
					.append(Integer.valueOf(this.bet3OperPos[i][j])).append(",") 
					.append(Integer.valueOf(this. callBet3OperPos [i][j])).append(",") 
					.append(Integer.valueOf(this.bet4OperPos [i][j])).append(",") 
					.append(Integer.valueOf(this.callBet4OperPos[i][j])).append(",") ;
				}
        }
			
			
			StringBuilder sb6 = new StringBuilder(3000);
			for (int i = 0; i < NUM_STREETS; i++) {
				for (int j = 0; j < NUM_POS; j++) {
					sb6.append(Integer.valueOf(this. betCount  [i][j])).append(",") 
					.append(Integer.valueOf(this. raiseCount  [i][j])).append(",") 
					.append(Integer.valueOf(this.checkOperPos [i][j])).append(",") 
					.append(Integer.valueOf(this. foldOperPos [i][j])).append(",") 
					.append(Integer.valueOf(this.   bet1OperPos[i][j])).append(",") 
					.append(Integer.valueOf(this. limpOperPos  [i][j])).append(",") 
					.append(Integer.valueOf(this.callAllinOperPos  [i][j])).append(",") 
					.append(Integer.valueOf(this.allinOperPos  [i][j])).append(",") ;
				}
        }
		sb1.append(sb2).append(sb3).append(sb4).append(sb5);		
 
		return sb1.toString();
	}

	/*-*****************************************************************************
	 *Convert String created by toEquivalentString()) to an object
	 ******************************************************************************/
	PlayerClassification fromStringEquivalent(String st) {
		int n = 0;
		int n2 = 0;
		PlayerClassification pc = new PlayerClassification();

		n2 = st.indexOf(",");
		pc.playerID =Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.handsPlayed = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.playerType= Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.aggPct= Double.parseDouble(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.af = Double.parseDouble(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.vpip = Double.parseDouble(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.vpip = Double.parseDouble(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.pfr = Double.parseDouble(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.wtsd = Double.parseDouble(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.wtsdFlop = Double.parseDouble(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.wtsdTurn= Double.parseDouble(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.wtsdRiver = Double.parseDouble(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.betCount1 =Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.showdownCount = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.wonShowdownCount = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.handPrevious= Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.wonCount = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.wwsfCount = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.wwstCount= Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.wwsrCount = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.wtsdCount= Integer.parseInt(st.substring(n, n2));
	 
		
		for (int i = 0; i < NUM_STREETS; i++) {
			for (int j = 0; j < NUM_POS; j++) {
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.checkPos[i][j] = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.limpPos[i][j] = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.bet1Pos[i][j] = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.callBet1Pos[i][j] = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.bet2Pos [i][j] = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.callBet2Pos[i][j] = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.bet3Pos[i][j] = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.callBet3Pos[i][j] = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.bet4Pos[i][j] = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.callBet4Pos[i][j] = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.allinPos[i][j] = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.callAllinPos[i][j] = Integer.parseInt(st.substring(n, n2));
			}
		}
			 
		for (int i = 0; i < NUM_STREETS; i++) {
			n = n2 + 1;
			n2 = st.indexOf(",", n);
			pc.streetCounts [i]  = Integer.parseInt(st.substring(n, n2));
		}
		
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.wsdCount  = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.winRateBB100  = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.bet3Freq  = Double.parseDouble(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.winnings$  = new BigDecimal(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		pc.bbBet$  = new BigDecimal(st.substring(n, n2));
		
		for (int i = 0; i < NUM_STREETS; i++) {
			for (int j = 0; j < NUM_POS; j++) {
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.callBet1OperPos[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.limpPos[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.bet2OperPos[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.callBet2OperPos[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.bet2Pos[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.callBet2Pos[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.bet3OperPos[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.callBet3OperPos[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.bet4OperPos[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.callBet4OperPos[i][j]  = Integer.parseInt(st.substring(n, n2));
							}
			}
		
		for (int i = 0; i < NUM_STREETS; i++) {
			for (int j = 0; j < NUM_POS; j++) {
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.betCount[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.raiseCount[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.checkOperPos[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc. foldOperPos[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.callAllinOperPos[i][j]  = Integer.parseInt(st.substring(n, n2));
				n = n2 + 1;
				n2 = st.indexOf(",", n);
				pc.allinOperPos[i][j]  = Integer.parseInt(st.substring(n, n2));
				
							}
			}
			return pc;
	}

	
	// @formatter:on

	/*- *****************************************************************************
	 * Player classification - FISH, NIT, LAG, TAG, REG, AVERAGE, .....
	 * Calculates several variables such as af. Aggression Factor
	 * These things apply to only one player. This instance of Players Class.
	 * Used to create summarized Players Classes with many players of the same type.
	 * Then used to createPlayerClassification.
	 * 
	 * Called when parsing of Hand History files is complete.
	 * Called from the  Class.
	  ******************************************************************************/
	void playerClassification() {
		calculateWinRate();
		calculateWTSD();
		calculateAggPctPostflop();
		calculatePostflopAF();
		calculatePFR();
		calculateVPIPPreflop();

	}

	//
	void playerCalculations() {
		calculateWinRate();
		calculateWTSD();
		calculateAggPctPostflop();
		calculatePostflopAF();
		calculatePFR();
		calculateVPIPPreflop();

	}

	/*- *****************************************************************************
	*	How do you calculate BB 100?
	* Count the amount you win (will be negative if you lose).
	* Divide it by the size of the big blind.
	* Divide that by the number of hands you played.
	* Multiply it by 100.
	* The answer is in bb/100. 
	***************************************************************************** */
	private void calculateWinRate() {
		BigDecimal hundred = new BigDecimal(100);
		if (handsPlayed != 0 && !this.bbBet$.equals(zeroBD)) {
			BigDecimal played = new BigDecimal(this.handsPlayed);
			BigDecimal x = this.winnings$.divide(this.bbBet$, 2, RoundingMode.HALF_EVEN);
			x = x.divide(played, 2, RoundingMode.HALF_EVEN);
			x = x.multiply(hundred);
			this.winRateBB100 = x.intValue();
		} else {
			this.winRateBB100 = 0;
		}
	}

	/*- *****************************************************************************
	* Calculate aggression percentage.
	*	Bets and raises are counted as aggressive actions, while calls and folds are   not.
	*	Postflop Aggression Percentage = (Bets + Raises) / (Bets + Raises + Calls + Checks) * 100%
	*
	*	Postflop Aggression Percent = Percentage of postflop streets players make aggressive moves
	*
	*	Aggression Percentage = aggressive action on each street. 
	*	So if I bet the turn and the river but check the flop I would have 
	*	66% Agg Pct because I made 2 out of 3 aggressive actions.
	*	So it's ( (Bets + Raises) / (Bets + Raises + Calls + Checks ) * 100%.
	*	In the example above the action results in a Agg Pct of 66%.
	*	The Agg Factor would be: 2 / 1 = 2
	*	Both results mean the same: the opponent is 2x more likely to bet, than he is to call.
	*	We only use Aggression Factor and Aggression Percentage.
	 ******************************************************************************/
	void calculateAggPctPostflop() {
		double raiseCount = 0.;
		double callCount = 0.;
		double checkCount = 0.;

		if (this.handsPlayed < 50) {
			this.aggPct = 0.;
			return;
		}

		for (int i = 1; i < STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				raiseCount += this.bet1Pos[i][j] + this.bet2Pos[i][j] + this.bet3Pos[i][j] + this.bet4Pos[i][j];
				callCount += this.callBet1Pos[i][j] + this.callBet2Pos[i][j] + this.callBet3Pos[i][j]
						+ this.callBet4Pos[i][j];
				checkCount += this.checkPos[i][j];
			}
		}

		if (callCount + checkCount + raiseCount == 0) {
			// ERROR calculateAggPctPostflop() raise + call + check = 0 0 0 0
			Logger.logError(new StringBuilder().append("//ERROR calculateAggPctPostflop()  raise + call + check = 0 ")
					.append("  \traise ").append(Format.format(raiseCount)).append("\t call ")
					.append(Format.format(callCount)).append("\t check ").append(Format.format(checkCount))
					.append(" \thands ").append(this.handsPlayed).toString());

			this.aggPct = 0.;
			return;
		}

		this.aggPct = 100. * (raiseCount) / (raiseCount + callCount + checkCount);
		if (this.aggPct < 0. || this.aggPct > 100.) {
			Logger.logError(new StringBuilder().append("//ERROR  calculatePostflopAggPct Invalid percentage result ")
					.append(Format.format(aggPct)).append(" ").append(raiseCount).append(" ").append(callCount)
					.append(" ").append(checkCount).toString());
			Logger.logError(new StringBuilder().append("//aggPct ").append(Format.format(this.aggPct)).append(" ")
					.append(Format.format(raiseCount)).append(" ").append(Format.format(callCount)).append(" ")
					.append(Format.format(checkCount)).toString());
			Crash.log("$$$");
		}
	}

	/*- *****************************************************************************
	* Calculate Went To Showdown percentage.  WTSD wtsd
	* Sets variable in this Class instance
	 ******************************************************************************/
	void calculateWTSD() {
		if (streetCounts[1] == 0 || this.handsPlayed < 300) {
			this.wtsd = 0;
		} else {
			this.wtsd = ((double) this.wtsdCount / (double) streetCounts[1]) * 100.;
		}

		if (streetCounts[1] == 0 || this.handsPlayed < 300) {
			this.wtsdTurn = 0;
		} else {
			this.wtsdTurn = ((double) this.wtsdCount / (double) streetCounts[2]) * 100.;
		}

		if (streetCounts[3] == 0 || this.handsPlayed < 300) {
			this.wtsdRiver = 0;
		} else {
			this.wtsdRiver = ((double) this.wtsdCount / (double) streetCounts[3]) * 100.;
		}
	}

	/*- *****************************************************************************
	 * Calculate Aggression factor. AF
	* 
	*  Postflop Aggression Factor = Ratio of Bets + Raises vs calls on postflop streets	
	*	Aggression Factor = (Bets + Raises) / Calls
	*	(checking/folding doesn't affect it)
	*
	*	This results in a value between 0 and INF (infinite)
	*	If a player never Calls, but always bets/raises, his AGG will be infinite.
	*	The statistics are used in postflop decision making. Since it will tell you the 
	* 	likelihood the opponent will bet.
	*	The formulas:
	*	Aggression Factor: (% Raise + % Bet) / % Call
	*	Aggression Factor: (Bets + Raise) / Calls
	*
	*	Hold'em Manager, which expresses the ratio of a player's aggressive  actions
	*  (bets or raises) to their calls.
	*	For example, a player with an AF of 3 has bet/raised three times as often as
	*	they've called.
	 ******************************************************************************/
	void calculatePostflopAF() {
		int raiseCount = 0;
		int betCount = 0;
		int callCount = 0;
		for (int i = 1; i < STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				raiseCount += this.bet1Pos[i][j] + this.bet2Pos[i][j] + this.bet3Pos[i][j] + this.bet4Pos[i][j];
				callCount += this.callBet1Pos[i][j] + this.callBet2Pos[i][j] + this.callBet3Pos[i][j]
						+ this.callBet4Pos[i][j];
				betCount = this.bet1Pos[i][j];
			}
		}
		if (callCount == 0) {
			this.af = 9999999;
			return;
		}
		this.af = ((double) betCount + (double) raiseCount) / callCount;
	}

	/*- *****************************************************************************
	 * Calculate PFR - Preflop Raise
	 * This statistic creates even more context for your opponent’s preflop strategy.
	 * When used in conjunction with VPIP, it will be enough to form a player profile.
	*
	 * A player that has at least a basic understanding of preflop strategy will have 
	 * between 15-25% preflop raise, with 19% being close to the norm.
	 *
	 * Similarly to VPIP, you will need around 300 hands on this player to be confident 
	 * enough in the number you are seeing.
	  ***************************************************************************** */
	void calculatePFR() {
		int operCount = 0;
		int raiseCount = 0;

		for (int j = 0; j < NUM_POS; ++j) {
			raiseCount += this.bet2Pos[0][j] + this.bet3Pos[0][j] + this.bet4Pos[0][j];
			operCount += this.callBet2OperPos[0][j] + this.callBet3OperPos[0][j] + this.callBet4OperPos[0][j];
			raiseCount += this.bet1Pos[0][j];
			operCount += this.callBet1OperPos[0][j];
		}
		if (operCount == 0) {
			this.pfr = 0.;
		} else {
			this.pfr = ((double) raiseCount / (double) operCount) * 100.;
		}
	}

	/*-  *****************************************************************************
	 * VPIP  Preflop all positions and all orbits
	 * Used only locally to catagorize player types FISH, NIT, ...	 * 
	 * 
	 * 		vpip = 100.0 * (moneyInRaise + moneyInCall) / from.streetCounts[street];
	 *		int[][] callBet2AllOrbitsPos = new int[NUM_STREETS][NUM_POS];
	 * 		bet2AllOrbitsPos = new int[NUM_STREETS][NUM_POS];
	 * 
	 * Uses opportunity counts not streets played - TODO verify this is correct way
	 * 
	 * 		int[][][] bet2Pos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	 * 		int[][][] bet2OperPos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	 ***************************************************************************** */
	void calculateVPIPPreflop() {
		double moneyInRaise = 0;
		double moneyInCall = 0;
		double operCount = 0;
		vpip = 0.;
		for (int i = 0; i < NUM_POS; ++i) {
			moneyInRaise += this.bet2Pos[0][i] + this.bet3Pos[0][i] + this.bet4Pos[0][i] + this.allinPos[0][i];
			moneyInCall += this.callBet2Pos[0][i] + this.callBet3Pos[0][i] + this.callBet4Pos[0][i]
					+ this.callAllinPos[0][i];
			operCount += this.bet2OperPos[0][i] + this.bet3OperPos[0][i] + this.bet4OperPos[0][i]
					+ this.callBet2OperPos[0][i] + this.callBet3OperPos[0][i] + this.callBet4OperPos[0][i];
		}

		if (this.handsPlayed != 0) {
			this.vpip = 100.0 * (moneyInCall + moneyInRaise) / this.handsPlayed;
		}

		if (this.vpip >= 0 && this.vpip <= 100) {
			return;
		}
	}

	/*-  *****************************************************************************
	 * Calculate percentage with error checking
	 ***************************************************************************** */
	private static double calculatePercentage(double a, double b) {
		if (b <= 0.) {
			return 0.;
		}
		final double $ = 100.0 * (a / b);
		final boolean condition = ($ > 100 || $ < 0);
		if (condition) {
			Logger.logError(new StringBuilder().append("//ERROR calculatePercentage ").append($).append(" a ").append(a)
					.append(" b ").append(b).toString());
		}
		return $;
	}

}
