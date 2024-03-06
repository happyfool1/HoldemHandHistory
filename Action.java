package holdemhandhistory;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Action implements java.io.Serializable, Constants {
	static final long serialVersionUID = 1234567L;

	/*-*****************************************************************************
	 * This class is used to group generic data about one players action.
	 * Used for streets only. 
	 * street 		Street number ( PREFLOP, FLOP, TURN, RIVER )
	 * ID            Player ID, negative if player rejected ( but keep hand for other players )
	 * action		Action constants ( FOLD, CHECK, .... )
	 * seat			Seat number 0 - 5
	 * call	        Call  $
	 * betRaise	Bet or raise  $
	 * Raise To	Raise to  $
	 * 
	 * Calculated velues remove from final version - calculate in application using
	 * money		How much is added to pot
	 * 					If CALL, CALL_ALLIN, BET, BET_ALLIN, RAISE, RAISE_ALLIN, ALLIN, 
	 * 					UNCALLED, RETURNED_TO, or COLLECTED	
	 * moneyIn	Money in this street before action 
	 * stack		Stack $ now - will be final stack after summary 
	 * pot			Pot now 
	***************************************************************************** */

	int street; // preflop, flop, turn, river, showdown, summary
	int ID;
	int action; // player actions fold, check, call, bet, raise, show
	int seat; // seat number 0 - 5
	BigDecimal call = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // size of the bet after players action
	BigDecimal betRaise = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // size of the bet after players action
	BigDecimal raiseTo = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // size of the bet after players action
	BigDecimal moneyIn = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // how much this player has in pot // or
	// Calculated values. Remove in final version after testing complete, Calculate
	// in application.

	BigDecimal money = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // result of player action such as raise
	BigDecimal stack = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // players stack after acting or dealer
	BigDecimal pot = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN); // current pot size after acting
}
