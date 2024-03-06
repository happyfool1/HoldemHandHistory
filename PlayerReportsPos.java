package holdemhandhistory;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */
public class PlayerReportsPos implements Constants {

	/*-******************************************************************************
	 * This class does a bunch of reports using data collected by .
	 * The reports are in a seperate class ( this one ) for a couple of resons:
	 * 		 was just too big.
	 * 		By passing PlayerClassification and Players classes to this class it makes reports
	 * 		for filtered players or player tipes very simple. No code changes to report methods.
	 * Most of the reports are designed to assist in debug and testing but
	 * are also usefull for understanding how the collected and analyzed data can be used to
	 * improve exploits.
	 * 
	 * Something new to Holdem applications is the idea of relative positions and
	 * ranges associated.
	 *	Preflop LASTHU last heads up are the Button in orbit 0 BUT if the Button
	 *	folds then the Cutoff becomes in effect the Button, last heads up. 
	 *	So ranges can change in after Orbit 0. 
	 *	In the Button folding example, the range used by the Cutoff is dynamic
	 *	not static as is usually the case now.
	 *	I'll be evaluating this as we progress. 
	 * @author PEAK_
	 *******************************************************************************/

	private static final Object[] columnsRp = { "Action ", "FIRST", "FIRSTHU", "MIDDLE", "LAST", "LASTHU" };
	private static final Object[] columnsPos = { "Action ", "SB", "BB", "UTG", "MP", "Cutoff", "Button" };
	private static final Object[] columnsStreet = { "Street", "Preflop", "Flop", "Turn", "River", "Showdown",
			"Summary" };

// @formatter:off

  
 
	private static final Object[][] dataActionsPosX = { { "Preflop", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Fold", " ", " ", " ", " ", " ", " ", " ", "" }, { "Check", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "Flop ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "Turn", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "River ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" } };
 
 
	private static final Object[][] dataOperPosX = { { "Preflop", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Fold", " ", " ", " ", " ", " ", " ", " ", "" }, { "Check", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "Flop ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "Turn", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "River ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" } };
	

	private static final Object[][] dataActionsPerPos = { { "Preflop ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Fold", " ", " ", " ", " ", " ", " ", " ", "" }, { "Check", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "Flop  ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "Turn ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "River  ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" } };
	
		private static boolean allPerPosHandsGUI = false;
	private static final Object[][] dataActionsPerPosHands = {
			{ "Preflop ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Fold", " ", " ", " ", " ", " ", " ", " ", "" }, { "Check", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "Flop  ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "Turn ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "River  ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" } };

	static void allActionPosX(Players play) {
		if (!allActionPosGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 10);
			final var frame = new JFrame("Action combined orbits");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(50, 50);
			frame.setPreferredSize(new Dimension(500, 850));

			final var tablePos = new JTable(dataActionsPos, columnsPos);
			tablePos.setFont(ff);
			tablePos.setRowHeight(25);
			final var pane = new JScrollPane(tablePos);
			tablePos.getColumnModel().getColumn(0).setPreferredWidth(120);
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			allActionPosGUI = true;
		}
		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				saveRow = row;

				dataActionsPos[row++][j + 1] = Format.format(play.foldPos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.checkPos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.bet1Pos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.callBet1Pos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.bet2Pos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.callBet2Pos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.bet3Pos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.callBet3Pos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.bet4Pos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.callBet4Pos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.allinPos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.callAllinPos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.cBetPos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(play.barrelPos[i][j]);
				dataActionsPos[row++][j + 1] = Format.format(-1);
				row = saveRow;
			}
			row += 16;
		}
	}

	static void allOperPosX(Players play) {
		Logger.logError("HELLO");
		if (!allOperPosGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 10);
			final var frame = new JFrame("Opportunity combined orbits");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(525, 50);
			frame.setPreferredSize(new Dimension(500, 850));

			final var tablePos = new JTable(dataOperPos, columnsPos);
			tablePos.setFont(ff);
			tablePos.setRowHeight(25);
			final var pane = new JScrollPane(tablePos);
			tablePos.getColumnModel().getColumn(0).setPreferredWidth(120);
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			allOperPosGUI = true;
		}
		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				saveRow = row;
				dataOperPos[row++][j + 1] = Format.format(play.foldOperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.checkOperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.bet1OperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.callBet1OperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.bet2OperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.callBet2OperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.bet3OperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.callBet3OperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.bet4OperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.callBet4OperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.allinOperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.callAllinOperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.cBetOperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(play.barrelOperPos[i][j]);
				dataOperPos[row++][j + 1] = Format.format(-1);
				row = saveRow;
			}
			row += 16;
		}
	}

	static void allPerPosX(Players play) {
		if (!allPerPosGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 12);
			final var frame = new JFrame("Action Percentages Opportunity");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(1000, 50);
			frame.setPreferredSize(new Dimension(500, 850));
			final var tablePos = new JTable(dataActionsPerPos, columnsPos);
			tablePos.setFont(ff);
			tablePos.setRowHeight(25);
			final var pane = new JScrollPane(tablePos);
			tablePos.getColumnModel().getColumn(0).setPreferredWidth(120);
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			allPerPosGUI = true;
		}
		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				saveRow = row;/*-
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.foldPerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.checkPerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.bet1PerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.callBet1PerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.bet2PerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.callBet2PerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.bet3PerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.callBet3PerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.bet4PerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.callBet4PerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.allinPerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.callAllinPerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.cBetPerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(pcd.barrelPerPos[i][j]);
				dataActionsPerPos[row++][j + 1] = Format.formatPer(.9999);
				*/
				row = saveRow;
			}
			row += 16;
		}
	}

	static void allPerPosHands(Players play) {
		if (!allPerPosHandsGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 12);
			final var frame = new JFrame("Action Percentages Hands");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(500, 50);
			frame.setPreferredSize(new Dimension(500, 850));
			final var tablePos = new JTable(dataActionsPerPosHands, columnsPos);
			tablePos.setFont(ff);
			tablePos.setRowHeight(25);
			final var pane = new JScrollPane(tablePos);
			tablePos.getColumnModel().getColumn(0).setPreferredWidth(120);
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			allPerPosHandsGUI = true;
		}
		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				saveRow = row;
				/*- TODO Removed from PlayerClassification but avaiable in Players original not a copy. Why allPLayers?
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.foldPerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.checkPerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.bet1PerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.callBet1PerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.bet2PerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.callBet2PerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.bet3PerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.callBet3PerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.bet4PerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.callBet4PerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.allinPerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.callAllinPerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.cBetPerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(play.barrelPerPosHands[i][j]);
				dataActionsPerPosHands[row++][j + 1] = Format.formatPer(.9999);
				*/
				row = saveRow;
			}
			row += 16;
		}
	}

// @formatter:off
	private static boolean allActionPosGUI = false;

	private static boolean allOperPosGUI = false;

	private static boolean allPerPosGUI = false;
	
	private static final Object[][] dataActionsPos = { { "Preflop   ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Fold", " ", " ", " ", " ", " ", " ", " ", "" }, { "Check", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "Flop  ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "Turn  ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "River  ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" } };
	/*- 
	*	For all orbits - Actions
	*
	*/
	private static final Object[][] dataOperPos = { { "Preflop ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Fold", " ", " ", " ", " ", " ", " ", " ", "" }, { "Check", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "Flop ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "Turn  ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "River ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" } };
	 
	private static final Object[][] dataPercentagePos = { { "Preflop", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Fold", " ", " ", " ", " ", " ", " ", " ", "" }, { "Check", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "Flop ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "Turn   ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "River  ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" } };

	static void allActionPos(Players play) {
		if (!allActionPosGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 10);
			final var frame = new JFrame("Action       All streets All orbits ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(50, 50);
			frame.setPreferredSize(new Dimension(500, 850));

			final var tablePos = new JTable(dataActionsPos, columnsPos);
			tablePos.setFont(ff);
			tablePos.setRowHeight(25);
			final var pane = new JScrollPane(tablePos);
			tablePos.getColumnModel().getColumn(0).setPreferredWidth(120);
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			allActionPosGUI = true;
		}
		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_POS; ++k) {
				saveRow = row;
				dataActionsPos[row++][k + 1] = Format.format(play.foldPos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.checkPos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.bet1Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.callBet1Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.bet2Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.callBet2Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.bet3Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.callBet3Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.bet4Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.callBet4Pos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.allinPos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.callAllinPos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.cBetPos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(play.barrelPos[i][k]);
				dataActionsPos[row++][k + 1] = Format.format(-1);
				row = saveRow;
			}
			row += 16;
		}
	}

	static void allOperPos(Players play) {
		if (!allOperPosGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 10);
			final var frame = new JFrame(" Oppertunity     All streets All orbits  ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(525, 50);
			frame.setPreferredSize(new Dimension(500, 850));

			final var tablePos = new JTable(dataOperPos, columnsPos);
			tablePos.setFont(ff);
			tablePos.setRowHeight(25);
			final var pane = new JScrollPane(tablePos);
			tablePos.getColumnModel().getColumn(0).setPreferredWidth(120);
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			allOperPosGUI = true;
		}

		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_POS; ++k) {
				saveRow = row;
				dataOperPos[row++][k + 1] = Format.format(play.foldOperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.checkOperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.bet1OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.callBet1OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.bet2OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.callBet2OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.bet3OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.callBet3OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.bet4OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.callBet4OperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.allinOperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.callAllinOperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.cBetOperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(play.barrelOperPos[i][k]);
				dataOperPos[row++][k + 1] = Format.format(-1);
				row = saveRow;
			}
			row += 16;
		}
	}

	static void allPerPos(Players play) {
		if (!allPerPosGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 12);
			final var frame = new JFrame(" Percentage     All streets All orbits ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(1000, 50);
			frame.setPreferredSize(new Dimension(500, 850));
			final var tablePos = new JTable(dataPercentagePos, columnsPos);
			tablePos.setFont(ff);
			tablePos.setRowHeight(25);
			final var pane = new JScrollPane(tablePos);
			tablePos.getColumnModel().getColumn(0).setPreferredWidth(120);
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			allPerPosGUI = true;
		}
		/*- TODO
		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_POS; ++k) {
				saveRow = row;
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.foldPosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.checkPosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.bet1PosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.callBet1PosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.bet2PosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.callBet2PosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.bet3PosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.callBet3PosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.bet4PosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.callBet4PosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.allinPosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.callAllinPosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.cBetPosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.formatPer(pcd.barrelPosPer[i][k]);
				dataPercentagePos[row++][k + 1] = Format.format(-1);
				row = saveRow;
			}
			row += 16;
		}
		*/
	}

	
}
