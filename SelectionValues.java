package holdemhandhistory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

public class SelectionValues implements java.io.Serializable, Constants {
	private final long serialVersionUID = 1234567L;

	/*- ******************************************************************************************** 
	* @author PEAK
	********************************************************************************************** */

	/*- ******************************************************************************************** 
	* Class to define a value, and it's range, and description.
	********************************************************************************************** */
	class ValueDouble implements Constants {
		// Constructor
		ValueDouble(int index, double low, double high, String description) {
			this.index = index;
			this.low = low;
			this.high = high;
			this.description = description;
		}

		int index;
		double low;
		double high;
		String description;
	}

	class ValueInteger {
		// Constructor
		ValueInteger(int index, int low, int high, String description) {
			this.index = index;
			this.low = low;
			this.high = high;
			this.description = description;
		}

		int index;
		int low;
		int high;
		String description;
	}

	class ValueBigDecimal {
		// Constructor
		ValueBigDecimal(int index, BigDecimal low, BigDecimal high, String description) {
			this.index = index;
			this.low = low;
			this.high = high;
			this.description = description;
		}

		int index;
		BigDecimal low;
		BigDecimal high;
		String description;
	}

	ValueDouble[] v = new ValueDouble[100];
	double[] ndx = new double[v.length];
	JButton[] buttons = new JButton[v.length];

	static final int D_WINRATEBB100 = 0; // // Win rate in bb/ 100 ( or loss rate if - )
	static final int D_SHOWDOWNPERCENTAGE = 1; //
	static final int D_FOURBETLIMPCALLHU = 2; // // 4Bet Limp-Call HU
	static final int D_FOURBETLIMPRERAISEHU = 3; // // 4Bet Limp-ReRaise HU
	static final int D_SB_LIMP_FOLD_HU = 4; // // SB Limp-Fold HU
	static final int D_BTNOPENLIMP = 5; // // BTN Open Limp
	static final int D_FOURBETOPENLIMP = 6; // // 4Bet Open Limp
	static final int D_BB_RAISE_VS_SB_LIMP_UOP = 7; // // BB Raise vs SB Limp UOP
	static final int D_FLOPCBET = 8; // // Flop CBet
	static final int D_FLOPCBETIP = 9; // // Flop CBet IP
	static final int D_FLOPCBETOOP = 10; // // Flop CBet OOP
	static final int D_FLOPCBETCALL = 11; // // Flop CBet-Call
	static final int D_FLOPCBETRERAISE = 12; // // Flop CBet-Reraise
	static final int D_FLOPCBETFOLD = 13; // // Flop CBet-Fold
	static final int D_FLOPCALLDONKBET = 14; // // Flop Call Donk Bet
	static final int D_FLOPRAISEDONKBET = 15; // // Flop Raise Donk Bet
	static final int D_FLOPFOLDTODONKBET = 16; // // Flop Fold to Donk Bet
	static final int D_SKIPFLOPCBANDCHECKCALLOOP = 17; // // Skip Flop CB and Check-Call OOP
	static final int D_SKIPFLOPCBANDCHECKRAISEOOP = 18; // // Skip Flop CB and Check-Raise OOP
	static final int D_SKIPFLOPCBANDCHECKFOLDOOP = 19; // // Skip Flop CB and Check-Fold OOP
	static final int D_FLOPCALLCBIP = 20; // // Flop Call CB IP
	static final int D_FLOPRAISECBIP = 21; // // Flop Raise CB IP
	static final int D_FLOPFOLDTOCBIP = 22; // // Flop Fold to CB IP
	static final int D_FLOPCHECKCALLCBOOP = 23; // // Flop Check-Call CB OOP
	static final int D_FLOPCHECKRAISECBOOP = 24; // // Flop Check-Raise CB OOP
	static final int D_FLOPCHECKFOLDTOCBOOP = 25; // // Flop Check-Fold to CB OOP
	static final int D_FLOPDONKBETCALL = 26; // // Flop Donk Bet-Call
	static final int D_FLOPDONKBETRERAISE = 27; // // Flop Donk Bet-ReRaise
	static final int D_FLOPDONKBETFOLD = 28; // // Flop Donk Bet-Fold
	static final int D_FLOPDONKBET = 29; // // Flop Donk Bet
	static final int D_FLOPBETVSMISSEDCB = 30; // // Flop Bet vs Missed CB
	static final int D_FLOPBETLIMPEDPOTATTEMPTTOSTEALIP = 31; // // Flop Bet - Limped Pot Attempt to Steal IP
	static final int D_POSTFLOPAGGRESSIONPERCENTAGE = 32; // // Postflop Aggression Percentage
	static final int D_SEENFLOPOVERALL = 33; // // Seen Flop Overall
	static final int D_TURNCBET = 34; // // Turn CBet
	static final int D_TURNCBETIP = 35; // // Turn CBet IP
	static final int D_TURNCBETOOP = 36; // // Turn CBet OOP
	static final int D_TURNCBETCALL = 37; // // Turn CBet-Call
	static final int D_TURNCBETRERAISE = 38; // // Turn CBet-ReRaise
	static final int D_DELAYEDTURNCBET = 39; // // Delayed Turn CBet
	static final int D_DELAYEDTURNCBETIP = 40; // // Delayed Turn CBet IP
	static final int D_DELAYEDTURNCBETOOP = 41; // // Delayed Turn CBet OOP
	static final int D_SKIPTURNCBANDCHECKCALLOOP = 42; // // Skip Turn CB and Check-Call OOP
	static final int D_SKIPTURNCBANDCHECKRAISEOOP = 43; // // Skip Turn CB and Check-Raise OOP
	static final int D_SKIPTURNCBANDCHECKFOLDOOP = 44; // // Skip Turn CB and Check-Fold OOP
	static final int D_TURNCALLCBIP = 45; // // Turn Call CB IP
	static final int D_TURNRAISECBIP = 46; // // Turn Raise CB IP
	static final int D_TURNFOLDTOCBIP = 47; // // Turn Fold to CB IP
	static final int D_TURNCHECKCALLCBOOP = 48; // // Turn Check-Call CB OOP
	static final int D_TURNCHECKRAISECBOOP = 49; // // Turn Check-Raise CB OOP
	static final int D_TURNCHECKFOLDTOCBOOP = 50; // // Turn Check-Fold to CB OOP
	static final int D_TURNDONKBET = 51; // // Turn Donk Bet
	static final int D_TURNBETVSMISSEDCB = 52; // // Turn Bet vs Missed CB
	static final int D_RIVERCBET = 53; // // River CBet
	static final int D_RIVERCBETIP = 54; // // River CBet IP
	static final int D_RIVERCBETOOP = 55; // // River CBet OOP
	static final int D_RIVERCBETCALL = 56; // // River CBet-Call
	static final int D_RIVERCBETRERAISE = 57; // // River CBet-ReRaise
	static final int D_RIVERCBETFOLD = 58; // // River CBet-Fold
	static final int D_SKIPRIVERCBANDCHECKCALLOOP = 59; // // Skip River CB and Check-Call OOP
	static final int D_SKIPRIVERCBANDCHECKRAISEOOP = 60; // // Skip River CB and Check-Raise OOP
	static final int D_SKIPRIVERCBANDCHECKFOLDOOP = 61; // // Skip River CB and Check-Fold OOP
	static final int D_RIVERCALLCBIP = 62; // // River Call CB IP
	static final int D_RIVERRAISECBIP = 63; // // River Raise CB IP
	static final int D_RIVERFOLDTOCBIP = 64; // // River Fold to CB IP
	static final int D_RIVERCHECKCALLCBOOP = 65; // // River Check-Call CB OOP
	static final int D_RIVERCHECKRAISECBOOP = 66; // // River Check-Raise CB OOP
	static final int D_RIVERCHECKFOLDTOCBOOP = 67; // // River Check-Fold to CB OOP
	static final int D_RIVERDONKBET = 68; // // River Donk Bet
	static final int D_RIVERBETVSMISSEDCB = 69; // // River Bet vs Missed CB
	static final int D_WTSDWHENSAWTURN = 70; // // WTSD% When Saw Turn
	static final int D_RESTEAL3BETVSSTEAL = 71; // // ReSteal (3Bet vs Steal)
	static final int D_THREEBETRESTEALVSHERO = 72; // // 3Bet ReSteal vs Hero
	static final int D_SBFOLDTOSTEAL = 73; // // SB Fold to Steal
	static final int D_FOURBETCALLSTEAL = 74; // // 4Bet Call Steal
	static final int D_FOURBETRESTEAL3BETVSSTEAL = 75; // // 4Bet ReSteal (3Bet vs Steal)
	static final int D_FOLDTOSTEALBB = 76; // // Fold to Steal BB
	static final int D_BBCALLSTEAL = 77; // // BB Call Steal
	static final int D_BBRESTEAL3BETVSSTEAL = 78; // // BB 3Bet ReSteal vs Steal
	static final int D_BB3BETRESTEALVSHERO = 79; // // BB 3Bet ReSteal vs Hero
	static final int D_FOLDTOSTEALSB = 80; // // Fold to Steal SB
	static final int D_FOLDTOBTNSTEAL = 81; // // Fold to BTN Steal
	static final int D_FOLDTOSBSTEAL = 82; // // Fold to SB Steal
	static final int D_FOLDSBVSSTEALFROMBUTTONHERO = 83; // // Fold SB vs Steal from Button Hero
	static final int D_FOLDBBVSSTEALFROMBUTTONHERO = 84; // // Fold BB vs Steal from Button Hero
	static final int D_FOLDBBVSSTEALFROMSMALLBLINDHERO = 85; // // Fold BB vs Steal from Small Blind Hero
	static final int D_FOURBETFOLDTOBTNSTEAL = 86; // // 4Bet Fold to BTN Steal
	static final int D_SBLIMPFOLDHU = 87; // // SB Limp-Fold HU
	static final int D_BBRAISEVSSBLIMPUOP = 88; // // BB Raise vs SB Limp UOP

	/*-************************************************************************************
	 * Initialize arrays
	 * Arg0 - Player Characteristics instance
	 *************************************************************************************/
	void initializeArrays(PlayerCharacteristics pc) {
		ndx = new double[89];
		v = new ValueDouble[89];
		v[D_WINRATEBB100] = new ValueDouble(D_WINRATEBB100, 0., 0., "// Win rate in bb/ 100 ( or loss rate if - )");
		ndx[D_WINRATEBB100] = pc.winRateBB100;
		v[D_SHOWDOWNPERCENTAGE] = new ValueDouble(D_SHOWDOWNPERCENTAGE, 0., 0., "");
		ndx[D_SHOWDOWNPERCENTAGE] = pc.showdownPercentage;
		v[D_FOURBETLIMPCALLHU] = new ValueDouble(D_FOURBETLIMPCALLHU, 0., 0., "// 4Bet Limp-Call HU");
		ndx[D_FOURBETLIMPCALLHU] = pc.fourBetLimpCallHU;
		v[D_FOURBETLIMPRERAISEHU] = new ValueDouble(D_FOURBETLIMPRERAISEHU, 0., 0., "// 4Bet Limp-ReRaise HU");
		ndx[D_FOURBETLIMPRERAISEHU] = pc.fourBetLimpReRaiseHU;
		v[D_SB_LIMP_FOLD_HU] = new ValueDouble(D_SB_LIMP_FOLD_HU, 0., 0., "// SB Limp-Fold HU");
		ndx[D_SB_LIMP_FOLD_HU] = pc.sB_Limp_Fold_HU;
		v[D_BTNOPENLIMP] = new ValueDouble(D_BTNOPENLIMP, 0., 0., "// BTN Open Limp");
		ndx[D_BTNOPENLIMP] = pc.btnOpenLimp;
		v[D_FOURBETOPENLIMP] = new ValueDouble(D_FOURBETOPENLIMP, 0., 0., "// 4Bet Open Limp");
		ndx[D_FOURBETOPENLIMP] = pc.fourBetOpenLimp;
		v[D_BB_RAISE_VS_SB_LIMP_UOP] = new ValueDouble(D_BB_RAISE_VS_SB_LIMP_UOP, 0., 0., "// BB Raise vs SB Limp UOP");
		ndx[D_BB_RAISE_VS_SB_LIMP_UOP] = pc.bB_Raise_vs_SB_Limp_UOP;
		v[D_FLOPCBET] = new ValueDouble(D_FLOPCBET, 0., 0., "// Flop CBet");
		ndx[D_FLOPCBET] = pc.flopCBet;
		v[D_FLOPCBETIP] = new ValueDouble(D_FLOPCBETIP, 0., 0., "// Flop CBet IP");
		ndx[D_FLOPCBETIP] = pc.flopCBetIP;
		v[D_FLOPCBETOOP] = new ValueDouble(D_FLOPCBETOOP, 0., 0., "// Flop CBet OOP");
		ndx[D_FLOPCBETOOP] = pc.flopCBetOOP;
		v[D_FLOPCBETCALL] = new ValueDouble(D_FLOPCBETCALL, 0., 0., "// Flop CBet-Call");
		ndx[D_FLOPCBETCALL] = pc.flopCBetCall;
		v[D_FLOPCBETRERAISE] = new ValueDouble(D_FLOPCBETRERAISE, 0., 0., "// Flop CBet-Reraise");
		ndx[D_FLOPCBETRERAISE] = pc.flopCBetReraise;
		v[D_FLOPCBETFOLD] = new ValueDouble(D_FLOPCBETFOLD, 0., 0., "// Flop CBet-Fold");
		ndx[D_FLOPCBETFOLD] = pc.flopCBetFold;
		v[D_FLOPCALLDONKBET] = new ValueDouble(D_FLOPCALLDONKBET, 0., 0., "// Flop Call Donk Bet");
		ndx[D_FLOPCALLDONKBET] = pc.flopCallDonkBet;
		v[D_FLOPRAISEDONKBET] = new ValueDouble(D_FLOPRAISEDONKBET, 0., 0., "// Flop Raise Donk Bet");
		ndx[D_FLOPRAISEDONKBET] = pc.flopRaiseDonkBet;
		v[D_FLOPFOLDTODONKBET] = new ValueDouble(D_FLOPFOLDTODONKBET, 0., 0., "// Flop Fold to Donk Bet");
		ndx[D_FLOPFOLDTODONKBET] = pc.flopFoldToDonkBet;
		v[D_SKIPFLOPCBANDCHECKCALLOOP] = new ValueDouble(D_SKIPFLOPCBANDCHECKCALLOOP, 0., 0.,
				"// Skip Flop CB and Check-Call OOP");
		ndx[D_SKIPFLOPCBANDCHECKCALLOOP] = pc.skipFlopCBAndCheckCallOOP;
		v[D_SKIPFLOPCBANDCHECKRAISEOOP] = new ValueDouble(D_SKIPFLOPCBANDCHECKRAISEOOP, 0., 0.,
				"// Skip Flop CB and Check-Raise OOP");
		ndx[D_SKIPFLOPCBANDCHECKRAISEOOP] = pc.skipFlopCBAndCheckRaiseOOP;
		v[D_SKIPFLOPCBANDCHECKFOLDOOP] = new ValueDouble(D_SKIPFLOPCBANDCHECKFOLDOOP, 0., 0.,
				"// Skip Flop CB and Check-Fold OOP");
		ndx[D_SKIPFLOPCBANDCHECKFOLDOOP] = pc.skipFlopCBAndCheckFoldOOP;
		v[D_FLOPCALLCBIP] = new ValueDouble(D_FLOPCALLCBIP, 0., 0., "// Flop Call CB IP");
		ndx[D_FLOPCALLCBIP] = pc.flopCallCBIP;
		v[D_FLOPRAISECBIP] = new ValueDouble(D_FLOPRAISECBIP, 0., 0., "// Flop Raise CB IP");
		ndx[D_FLOPRAISECBIP] = pc.flopRaiseCBIP;
		v[D_FLOPFOLDTOCBIP] = new ValueDouble(D_FLOPFOLDTOCBIP, 0., 0., "// Flop Fold to CB IP");
		ndx[D_FLOPFOLDTOCBIP] = pc.flopFoldToCBIP;
		v[D_FLOPCHECKCALLCBOOP] = new ValueDouble(D_FLOPCHECKCALLCBOOP, 0., 0., "// Flop Check-Call CB OOP");
		ndx[D_FLOPCHECKCALLCBOOP] = pc.flopCheckCallCBOOP;
		v[D_FLOPCHECKRAISECBOOP] = new ValueDouble(D_FLOPCHECKRAISECBOOP, 0., 0., "// Flop Check-Raise CB OOP");
		ndx[D_FLOPCHECKRAISECBOOP] = pc.flopCheckRaiseCBOOP;
		v[D_FLOPCHECKFOLDTOCBOOP] = new ValueDouble(D_FLOPCHECKFOLDTOCBOOP, 0., 0., "// Flop Check-Fold to CB OOP");
		ndx[D_FLOPCHECKFOLDTOCBOOP] = pc.flopCheckFoldToCBOOP;
		v[D_FLOPDONKBETCALL] = new ValueDouble(D_FLOPDONKBETCALL, 0., 0., "// Flop Donk Bet-Call");
		ndx[D_FLOPDONKBETCALL] = pc.flopDonkBetCall;
		v[D_FLOPDONKBETRERAISE] = new ValueDouble(D_FLOPDONKBETRERAISE, 0., 0., "// Flop Donk Bet-ReRaise");
		ndx[D_FLOPDONKBETRERAISE] = pc.flopDonkBetReRaise;
		v[D_FLOPDONKBETFOLD] = new ValueDouble(D_FLOPDONKBETFOLD, 0., 0., "// Flop Donk Bet-Fold");
		ndx[D_FLOPDONKBETFOLD] = pc.flopDonkBetFold;
		v[D_FLOPDONKBET] = new ValueDouble(D_FLOPDONKBET, 0., 0., "// Flop Donk Bet");
		ndx[D_FLOPDONKBET] = pc.flopDonkBet;
		v[D_FLOPBETVSMISSEDCB] = new ValueDouble(D_FLOPBETVSMISSEDCB, 0., 0., "// Flop Bet vs Missed CB");
		ndx[D_FLOPBETVSMISSEDCB] = pc.flopBetVsMissedCB;
		v[D_FLOPBETLIMPEDPOTATTEMPTTOSTEALIP] = new ValueDouble(D_FLOPBETLIMPEDPOTATTEMPTTOSTEALIP, 0., 0.,
				"// Flop Bet - Limped Pot Attempt to Steal IP");
		ndx[D_FLOPBETLIMPEDPOTATTEMPTTOSTEALIP] = pc.flopBetLimpedPotAttemptToStealIP;
		v[D_POSTFLOPAGGRESSIONPERCENTAGE] = new ValueDouble(D_POSTFLOPAGGRESSIONPERCENTAGE, 0., 0.,
				"// Postflop Aggression Percentage");
		ndx[D_POSTFLOPAGGRESSIONPERCENTAGE] = pc.postflopAggressionPercentage;
		v[D_SEENFLOPOVERALL] = new ValueDouble(D_SEENFLOPOVERALL, 0., 0., "// Seen Flop Overall");
		ndx[D_SEENFLOPOVERALL] = pc.seenFlopOverall;
		v[D_TURNCBET] = new ValueDouble(D_TURNCBET, 0., 0., "// Turn CBet");
		ndx[D_TURNCBET] = pc.turnCBet;
		v[D_TURNCBETIP] = new ValueDouble(D_TURNCBETIP, 0., 0., "// Turn CBet IP");
		ndx[D_TURNCBETIP] = pc.turnCBetIP;
		v[D_TURNCBETOOP] = new ValueDouble(D_TURNCBETOOP, 0., 0., "// Turn CBet OOP");
		ndx[D_TURNCBETOOP] = pc.turnCBetOOP;
		v[D_TURNCBETCALL] = new ValueDouble(D_TURNCBETCALL, 0., 0., "// Turn CBet-Call");
		ndx[D_TURNCBETCALL] = pc.turnCBetCall;
		v[D_TURNCBETRERAISE] = new ValueDouble(D_TURNCBETRERAISE, 0., 0., "// Turn CBet-ReRaise");
		ndx[D_TURNCBETRERAISE] = pc.turnCBetReRaise;
		v[D_DELAYEDTURNCBET] = new ValueDouble(D_DELAYEDTURNCBET, 0., 0., "// Delayed Turn CBet");
		ndx[D_DELAYEDTURNCBET] = pc.delayedTurnCBet;
		v[D_DELAYEDTURNCBETIP] = new ValueDouble(D_DELAYEDTURNCBETIP, 0., 0., "// Delayed Turn CBet IP");
		ndx[D_DELAYEDTURNCBETIP] = pc.delayedTurnCBetIP;
		v[D_DELAYEDTURNCBETOOP] = new ValueDouble(D_DELAYEDTURNCBETOOP, 0., 0., "// Delayed Turn CBet OOP");
		ndx[D_DELAYEDTURNCBETOOP] = pc.delayedTurnCBetOOP;
		v[D_SKIPTURNCBANDCHECKCALLOOP] = new ValueDouble(D_SKIPTURNCBANDCHECKCALLOOP, 0., 0.,
				"// Skip Turn CB and Check-Call OOP");
		ndx[D_SKIPTURNCBANDCHECKCALLOOP] = pc.skipTurnCBAndCheckCallOOP;
		v[D_SKIPTURNCBANDCHECKRAISEOOP] = new ValueDouble(D_SKIPTURNCBANDCHECKRAISEOOP, 0., 0.,
				"// Skip Turn CB and Check-Raise OOP");
		ndx[D_SKIPTURNCBANDCHECKRAISEOOP] = pc.skipTurnCBAndCheckRaiseOOP;
		v[D_SKIPTURNCBANDCHECKFOLDOOP] = new ValueDouble(D_SKIPTURNCBANDCHECKFOLDOOP, 0., 0.,
				"// Skip Turn CB and Check-Fold OOP");
		ndx[D_SKIPTURNCBANDCHECKFOLDOOP] = pc.skipTurnCBAndCheckFoldOOP;
		v[D_TURNCALLCBIP] = new ValueDouble(D_TURNCALLCBIP, 0., 0., "// Turn Call CB IP");
		ndx[D_TURNCALLCBIP] = pc.turnCallCBIP;
		v[D_TURNRAISECBIP] = new ValueDouble(D_TURNRAISECBIP, 0., 0., "// Turn Raise CB IP");
		ndx[D_TURNRAISECBIP] = pc.turnRaiseCBIP;
		v[D_TURNFOLDTOCBIP] = new ValueDouble(D_TURNFOLDTOCBIP, 0., 0., "// Turn Fold to CB IP");
		ndx[D_TURNFOLDTOCBIP] = pc.turnFoldToCBIP;
		v[D_TURNCHECKCALLCBOOP] = new ValueDouble(D_TURNCHECKCALLCBOOP, 0., 0., "// Turn Check-Call CB OOP");
		ndx[D_TURNCHECKCALLCBOOP] = pc.turnCheckCallCBOOP;
		v[D_TURNCHECKRAISECBOOP] = new ValueDouble(D_TURNCHECKRAISECBOOP, 0., 0., "// Turn Check-Raise CB OOP");
		ndx[D_TURNCHECKRAISECBOOP] = pc.turnCheckRaiseCBOOP;
		v[D_TURNCHECKFOLDTOCBOOP] = new ValueDouble(D_TURNCHECKFOLDTOCBOOP, 0., 0., "// Turn Check-Fold to CB OOP");
		ndx[D_TURNCHECKFOLDTOCBOOP] = pc.turnCheckFoldToCBOOP;
		v[D_TURNDONKBET] = new ValueDouble(D_TURNDONKBET, 0., 0., "// Turn Donk Bet");
		ndx[D_TURNDONKBET] = pc.turnDonkBet;
		v[D_TURNBETVSMISSEDCB] = new ValueDouble(D_TURNBETVSMISSEDCB, 0., 0., "// Turn Bet vs Missed CB");
		ndx[D_TURNBETVSMISSEDCB] = pc.turnBetVsMissedCB;
		v[D_RIVERCBET] = new ValueDouble(D_RIVERCBET, 0., 0., "// River CBet");
		ndx[D_RIVERCBET] = pc.riverCBet;
		v[D_RIVERCBETIP] = new ValueDouble(D_RIVERCBETIP, 0., 0., "// River CBet IP");
		ndx[D_RIVERCBETIP] = pc.riverCBetIP;
		v[D_RIVERCBETOOP] = new ValueDouble(D_RIVERCBETOOP, 0., 0., "// River CBet OOP");
		ndx[D_RIVERCBETOOP] = pc.riverCBetOOP;
		v[D_RIVERCBETCALL] = new ValueDouble(D_RIVERCBETCALL, 0., 0., "// River CBet-Call");
		ndx[D_RIVERCBETCALL] = pc.riverCBetCall;
		v[D_RIVERCBETRERAISE] = new ValueDouble(D_RIVERCBETRERAISE, 0., 0., "// River CBet-ReRaise");
		ndx[D_RIVERCBETRERAISE] = pc.riverCBetReRaise;
		v[D_RIVERCBETFOLD] = new ValueDouble(D_RIVERCBETFOLD, 0., 0., "// River CBet-Fold");
		ndx[D_RIVERCBETFOLD] = pc.riverCBetFold;
		v[D_SKIPRIVERCBANDCHECKCALLOOP] = new ValueDouble(D_SKIPRIVERCBANDCHECKCALLOOP, 0., 0.,
				"// Skip River CB and Check-Call OOP");
		ndx[D_SKIPRIVERCBANDCHECKCALLOOP] = pc.skipRiverCBAndCheckCallOOP;
		v[D_SKIPRIVERCBANDCHECKRAISEOOP] = new ValueDouble(D_SKIPRIVERCBANDCHECKRAISEOOP, 0., 0.,
				"// Skip River CB and Check-Raise OOP");
		ndx[D_SKIPRIVERCBANDCHECKRAISEOOP] = pc.skipRiverCBAndCheckRaiseOOP;
		v[D_SKIPRIVERCBANDCHECKFOLDOOP] = new ValueDouble(D_SKIPRIVERCBANDCHECKFOLDOOP, 0., 0.,
				"// Skip River CB and Check-Fold OOP");
		ndx[D_SKIPRIVERCBANDCHECKFOLDOOP] = pc.skipRiverCBAndCheckFoldOOP;
		v[D_RIVERCALLCBIP] = new ValueDouble(D_RIVERCALLCBIP, 0., 0., "// River Call CB IP");
		ndx[D_RIVERCALLCBIP] = pc.riverCallCBIP;
		v[D_RIVERRAISECBIP] = new ValueDouble(D_RIVERRAISECBIP, 0., 0., "// River Raise CB IP");
		ndx[D_RIVERRAISECBIP] = pc.riverRaiseCBIP;
		v[D_RIVERFOLDTOCBIP] = new ValueDouble(D_RIVERFOLDTOCBIP, 0., 0., "// River Fold to CB IP");
		ndx[D_RIVERFOLDTOCBIP] = pc.riverFoldToCBIP;
		v[D_RIVERCHECKCALLCBOOP] = new ValueDouble(D_RIVERCHECKCALLCBOOP, 0., 0., "// River Check-Call CB OOP");
		ndx[D_RIVERCHECKCALLCBOOP] = pc.riverCheckCallCBOOP;
		v[D_RIVERCHECKRAISECBOOP] = new ValueDouble(D_RIVERCHECKRAISECBOOP, 0., 0., "// River Check-Raise CB OOP");
		ndx[D_RIVERCHECKRAISECBOOP] = pc.riverCheckRaiseCBOOP;
		v[D_RIVERCHECKFOLDTOCBOOP] = new ValueDouble(D_RIVERCHECKFOLDTOCBOOP, 0., 0., "// River Check-Fold to CB OOP");
		ndx[D_RIVERCHECKFOLDTOCBOOP] = pc.riverCheckFoldToCBOOP;
		v[D_RIVERDONKBET] = new ValueDouble(D_RIVERDONKBET, 0., 0., "// River Donk Bet");
		ndx[D_RIVERDONKBET] = pc.riverDonkBet;
		v[D_RIVERBETVSMISSEDCB] = new ValueDouble(D_RIVERBETVSMISSEDCB, 0., 0., "// River Bet vs Missed CB");
		ndx[D_RIVERBETVSMISSEDCB] = pc.riverBetVsMissedCB;
		v[D_WTSDWHENSAWTURN] = new ValueDouble(D_WTSDWHENSAWTURN, 0., 0., "// WTSD% When Saw Turn");
		ndx[D_WTSDWHENSAWTURN] = pc.wtsdWhenSawTurn;
		v[D_RESTEAL3BETVSSTEAL] = new ValueDouble(D_RESTEAL3BETVSSTEAL, 0., 0., "// ReSteal (3Bet vs Steal)");
		ndx[D_RESTEAL3BETVSSTEAL] = pc.reSteal3BetVsSteal;
		v[D_THREEBETRESTEALVSHERO] = new ValueDouble(D_THREEBETRESTEALVSHERO, 0., 0., "// 3Bet ReSteal vs Hero");
		ndx[D_THREEBETRESTEALVSHERO] = pc.threeBetReStealVsHero;
		v[D_SBFOLDTOSTEAL] = new ValueDouble(D_SBFOLDTOSTEAL, 0., 0., "// SB Fold to Steal");
		ndx[D_SBFOLDTOSTEAL] = pc.sBFoldToSteal;
		v[D_FOURBETCALLSTEAL] = new ValueDouble(D_FOURBETCALLSTEAL, 0., 0., "// 4Bet Call Steal");
		ndx[D_FOURBETCALLSTEAL] = pc.fourBetCallSteal;
		v[D_FOURBETRESTEAL3BETVSSTEAL] = new ValueDouble(D_FOURBETRESTEAL3BETVSSTEAL, 0., 0.,
				"// 4Bet ReSteal (3Bet vs Steal)");
		ndx[D_FOURBETRESTEAL3BETVSSTEAL] = pc.fourBetReSteal3BetVsSteal;
		v[D_FOLDTOSTEALBB] = new ValueDouble(D_FOLDTOSTEALBB, 0., 0., "// Fold to Steal BB");
		ndx[D_FOLDTOSTEALBB] = pc.foldToStealBB;
		v[D_BBCALLSTEAL] = new ValueDouble(D_BBCALLSTEAL, 0., 0., "// BB Call Steal");
		ndx[D_BBCALLSTEAL] = pc.bbCallSteal;
		v[D_BBRESTEAL3BETVSSTEAL] = new ValueDouble(D_BBRESTEAL3BETVSSTEAL, 0., 0., "// BB 3Bet ReSteal vs Steal");
		ndx[D_BBRESTEAL3BETVSSTEAL] = pc.bbReSteal3BetVsSteal;
		v[D_BB3BETRESTEALVSHERO] = new ValueDouble(D_BB3BETRESTEALVSHERO, 0., 0., "// BB 3Bet ReSteal vs Hero");
		ndx[D_BB3BETRESTEALVSHERO] = pc.bb3BetReStealVsHero;
		v[D_FOLDTOSTEALSB] = new ValueDouble(D_FOLDTOSTEALSB, 0., 0., "// Fold to Steal SB");
		ndx[D_FOLDTOSTEALSB] = pc.foldToStealSB;
		v[D_FOLDTOBTNSTEAL] = new ValueDouble(D_FOLDTOBTNSTEAL, 0., 0., "// Fold to BTN Steal");
		ndx[D_FOLDTOBTNSTEAL] = pc.foldToBTNSteal;
		v[D_FOLDTOSBSTEAL] = new ValueDouble(D_FOLDTOSBSTEAL, 0., 0., "// Fold to SB Steal");
		ndx[D_FOLDTOSBSTEAL] = pc.foldToSBSteal;
		v[D_FOLDSBVSSTEALFROMBUTTONHERO] = new ValueDouble(D_FOLDSBVSSTEALFROMBUTTONHERO, 0., 0.,
				"// Fold SB vs Steal from Button Hero");
		ndx[D_FOLDSBVSSTEALFROMBUTTONHERO] = pc.foldSBVsStealFromButtonHero;
		v[D_FOLDBBVSSTEALFROMBUTTONHERO] = new ValueDouble(D_FOLDBBVSSTEALFROMBUTTONHERO, 0., 0.,
				"// Fold BB vs Steal from Button Hero");
		ndx[D_FOLDBBVSSTEALFROMBUTTONHERO] = pc.foldBBVsStealFromButtonHero;
		v[D_FOLDBBVSSTEALFROMSMALLBLINDHERO] = new ValueDouble(D_FOLDBBVSSTEALFROMSMALLBLINDHERO, 0., 0.,
				"// Fold BB vs Steal from Small Blind Hero");
		ndx[D_FOLDBBVSSTEALFROMSMALLBLINDHERO] = pc.foldBBVsStealFromSmallBlindHero;
		v[D_FOURBETFOLDTOBTNSTEAL] = new ValueDouble(D_FOURBETFOLDTOBTNSTEAL, 0., 0., "// 4Bet Fold to BTN Steal");
		ndx[D_FOURBETFOLDTOBTNSTEAL] = pc.fourBetFoldToBTNSteal;
		v[D_SBLIMPFOLDHU] = new ValueDouble(D_SBLIMPFOLDHU, 0., 0., "// SB Limp-Fold HU");
		ndx[D_SBLIMPFOLDHU] = pc.sBLimpFoldHU;
		v[D_BBRAISEVSSBLIMPUOP] = new ValueDouble(D_BBRAISEVSSBLIMPUOP, 0., 0., "// BB Raise vs SB Limp UOP");
		ndx[D_BBRAISEVSSBLIMPUOP] = pc.bBRaiseVsSBLimpUOP;
	}

	/*- *******************************************************************************************************
	 * Create an array of buttons and button listeners
	 ********************************************************************************************************/
	JButton[] createButtons() {
		for (int i = 0; i < v.length; i++) {
			buttons[i] = new JButton(v[i].description); // Use the description for the button text
			final int index = i; // Effective final for use in lambda expressions
			buttons[i].addActionListener(e -> {
				// Open an input dialog to edit values
				String newLow = JOptionPane.showInputDialog("Enter new low value for " + v[index].description + ":");
				String newHigh = JOptionPane.showInputDialog("Enter new high value for " + v[index].description + ":");
				try {
					double low = Double.parseDouble(newLow);
					double high = Double.parseDouble(newHigh);
					v[index].low = low;
					v[index].high = high;
					JOptionPane.showMessageDialog(null, "Values updated for " + v[index].description);
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(null, "Invalid input. Please enter valid numbers.");
				}
			});
		}
		return buttons;
	}

	/*-  ****************************************************************************
	 * Show panel for this range
	  *****************************************************************************/
	void show() {
		constructPanel();
	}

	/*-  ****************************************************************************
	*  Construct panel.
	 *****************************************************************************/
	void constructPanel() {
		final var panel = new JPanel();
		final var panel0 = new JPanel();
		panel0.setLayout(new BoxLayout(panel0, BoxLayout.Y_AXIS));
		final var f0 = new Font(Font.SERIF, Font.BOLD, 18);
		panel0.setFont(f0);
		panel0.setSize(1700, 400);
		final var panel1Layout = new GridLayout(10, 10);
		final var panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		panel1.setLayout(panel1Layout);
		panel1.setFont(f0);
		final var panel2 = new JPanel();
		final var panel3 = new JPanel();
		final TitledBorder frameTitle;
		final var textArray = new JTextField[100];
		final var frame = new JFrame();
		new Dimension(1700, 400);
		final var frameDimN = new Dimension(1700, 400);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setLocation(10, 100);
		frame.setPreferredSize(new Dimension(1700, 400));
		frame.setTitle("Edit Values for Selection Profiles");
		frame.setMaximumSize(frameDimN);
		frame.setMinimumSize(frameDimN);
		final var ff = new Font(Font.SERIF, Font.BOLD, 10);
		panel.setLayout(new GridLayout(10, 10));
		panel.setMaximumSize(new Dimension(100, 30));
		// matrix
		final var buttonDim = new Dimension(1, 1);
		frameTitle = BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(""));
		panel1.setBorder(frameTitle);
		frameTitle.setTitle("");
		for (var i = 0; i < 100; ++i) {
			textArray[i] = new JTextField("");
			textArray[i].setFont(ff);
			textArray[i].setMaximumSize(buttonDim);
			textArray[i].setMinimumSize(buttonDim);
			textArray[i].setBackground(Color.LIGHT_GRAY);
			textArray[i].setForeground(Color.BLACK);
			panel1.add(textArray[i]);
		}
		panel0.add(panel1);
		for (var i = 0; i < 100; ++i) {
			textArray[i].setBackground(Color.LIGHT_GRAY);
		}
		panel2.setSize(400, 40);
		panel2.setBackground(Color.gray);
		panel2.setLayout(new BoxLayout(panel2, BoxLayout.X_AXIS));
		final var dim3 = new Dimension(400, 40);
		panel2.setMaximumSize(dim3);
		panel2.setMinimumSize(dim3);
		panel0.add(panel2);
		panel3.setSize(300, 40);
		panel3.setBackground(Color.gray);
		panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
		panel3.setMaximumSize(dim3);
		panel3.setMinimumSize(dim3);
		panel0.add(panel3);
		frame.add(panel0);
		panel.repaint();
		frame.pack();
		frame.setVisible(true);
	}

	/*-*****************************************************************************
	 * Write Object to file - this
	 * Returns false if error
	 ******************************************************************************/
	boolean writeToFile(String path) {
		final var filename = path;
		// Saving of object in a file
		try (var file = new FileOutputStream(filename); var out = new ObjectOutputStream(file)) {
			// Method for serialization of object
			out.writeObject(this);
			return true;
		} catch (IOException i) {
			i.printStackTrace();
			return false;
		}
	}

	/*-*****************************************************************************
	 * Read .ser file for this Class
	 * Arg0 - The full path name.
	 * Returns null if error
	***************************************************************************** */
	SelectionValues readFromFile(String path) {
		SelectionValues r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (SelectionValues) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

}
