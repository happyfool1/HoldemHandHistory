package holdemhandhistory;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */
public class PLaterReportsRP implements Constants {

	/*-********************************************************************************************
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
	* 
	* @author PEAK_
	* 
	******************************************************************************************** */

// @formatter:off
	private static boolean allOpportunityRpGUI = false;

	private static boolean allPreflopButtonBlindsPosGUI = false;
	//
	private static final Object[] columnsValues = { "Name", "Value", "Description" };

	private static final Object[] columnsNewArraysRp = { "Action ", "First", "First HU", "Middle1", "Middle2",
			"Middle3", "Middle4", "Last", "Last HU" };
	private static final Object[] columnsNewArraysPos = { "Action ", "SB", "BB", "UTG", "MP", "Cutoff", "Button" };
	private static final Object[] columnsRp = { "Action ", "First", "First HU", "Middle1", "Middle2", "Middle3",
			"Middle4", "Last", "Last HU" };
	private static final Object[] columnsPos = { "Action ", "SB", "BB", "UTG", "MP", "Cutoff", "Button" };
	private static final Object[] columnsStreets = { "Street", "Preflop", "Flop", "Turn", "River" };

	private static final Object[][] dataButtonBlinds = { { "Button***", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Limp", " ", " ", " ", " ", " ", " ", " ", "" }, { "Steal", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Isolate", " ", " ", " ", " ", " ", " ", " ", "" }, { "Squeeze", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Min Bet", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "Big Blind***", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Steal Call", " ", " ", " ", " ", " ", " ", " ", "" }, { "Steal", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Raised by SB", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Call Min Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Min 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "Small Blind***", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Folded to SB", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Steal Call", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Steal 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Call Min Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Raised by BB", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Min Call", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Min 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" } };
	/*- ********************************************************************************************
	 * 
	* Player Oppertunity by Position all orbits combined Percentages
	* allPlayers is the sum of each Player instance for every player - no filtering
	* allPlayerClassification was createdfrom allPlayers and percentage calculations done.
	* Display allPlayers data for each player
	* 
	*********************************************************************************************/
	private static boolean allPerRpGUI = false;

	private static final Object[][] dataOperRp = { { "Preflop Orbit 0 ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Fold", " ", " ", " ", " ", " ", " ", " ", "" }, { "Check", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "Flop Orbit 0 ------", " ", " ", " ", " ", " ", " ", " ", "" },
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
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" } };
 
	private static final Object[][] dataActionsRp = { { "Preflop  ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Fold", " ", " ", " ", " ", " ", " ", " ", "" }, { "Check", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "Flop Orbit 0 ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "Flop   ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "Turn  Orbit 0 ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "Turn   ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "River   ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },

			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" } };
	/*- ********************************************************************************************
	*
	*
	*********************************************************************************************/
	private static final Object[][] dataPercentageRp = {
			{ "Preflop  ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Fold", " ", " ", " ", " ", " ", " ", " ", "" }, { "Check", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "CallBet1(Limp)", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },
		 
			{ "Flop   ------", " ", " ", " ", " ", " ", " ", " ", "" },
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
			
			{ "River    ------", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Check", " ", " ", " ", " ", " ", " ", " ", "" }, { "Fold", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet1 ", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet1", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Bet2", " ", " ", " ", " ", " ", " ", " ", "" }, { "CallBet2", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "3 Bet", "", " ", " ", "", " ", " ", " ", "" }, { "Call 3 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "4 Bet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call 4 Bet", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "Allin", " ", " ", " ", " ", " ", " ", " ", "" }, { "Call Allin", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "cBet", " ", " ", " ", " ", " ", " ", " ", "" }, { "Barrell", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" },
			 
			
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" },
			{ "", " ", " ", " ", " ", " ", " ", " ", "" }, { "", " ", " ", " ", " ", " ", " ", " ", "" } };
	
	// @formatter:on

	static void allOpportunityRp(Players play) {
		if (!allOpportunityRpGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 10);
			final var frame = new JFrame("Players Opportunity data for all streets, relative positions, and orbits");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(200, 200);
			frame.setPreferredSize(new Dimension(600, 100));

			final var tablePos = new JTable(dataOperRp, columnsRp);
			tablePos.setFont(ff);
			tablePos.setRowHeight(25);
			final var pane = new JScrollPane(tablePos);
			tablePos.getColumnModel().getColumn(0).setPreferredWidth(120);
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			allOpportunityRpGUI = true;
		}

		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_POS; ++k) {
				saveRow = row;
				dataActionsRp[row++][k + 1] = Format.format(play.checkOperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.foldOperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.bet1OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.callBet1OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.bet2OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.callBet2OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.bet3OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.callBet3OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.bet4OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.callBet4OperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.allinOperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.callAllinOperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.cBetOperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(play.barrelOperRp[i][k]);
				dataActionsRp[row++][k + 1] = Format.format(-1);
				row = saveRow;
			}
			row += 16;
		}
	}

	static void allPreflopButtonBlindsPos(Players play) {
		if (!allPreflopButtonBlindsPosGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 10);
			final var frame = new JFrame("Players Opportunity data for all streets, relative positions, and orbits");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(250, 250);
			frame.setSize(600, 100);

			final var tablePos = new JTable(dataButtonBlinds, columnsRp);
			tablePos.setFont(ff);
			tablePos.setRowHeight(25);
			final var pane = new JScrollPane(tablePos);
			tablePos.getColumnModel().getColumn(0).setPreferredWidth(120);
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			allPreflopButtonBlindsPosGUI = true;
		}
		int row = 1;
		dataButtonBlinds[row++][1] = Format.format(play.walk);
		dataButtonBlinds[row++][1] = Format.format(play.isolateBU);
		dataButtonBlinds[row++][1] = Format.format(play.minBetBU);
		dataButtonBlinds[row++][1] = Format.format(play.stealBU);
		dataButtonBlinds[row++][1] = Format.format(play.squeezeBU);
		++row;
		dataButtonBlinds[row++][1] = Format.format(play.minBetBB);
		dataButtonBlinds[row++][1] = Format.format(play.callMinBetBB);
		dataButtonBlinds[row++][1] = Format.format(play.steal3BetBB);
		dataButtonBlinds[row++][1] = Format.format(play.raisedBySBBB);
		dataButtonBlinds[row++][1] = Format.format(play.steal3BetMinRaiseBB);
		dataButtonBlinds[row++][1] = Format.format(play.stealCallBB);
		dataButtonBlinds[row++][1] = Format.format(play.stealCallMinRaiseBB);
		++row;
		dataButtonBlinds[row++][1] = Format.format(play.bet3MinSB);
		dataButtonBlinds[row++][1] = Format.format(play.raisedByBBSB);
		dataButtonBlinds[row++][1] = Format.format(play.steal3BetSB);
		dataButtonBlinds[row++][1] = Format.format(play.stealCallSB);
		dataButtonBlinds[row++][1] = Format.format(play.stealCallMinRaiseSB);
		dataButtonBlinds[row++][1] = Format.format(play.foldedToSB);
		dataButtonBlinds[row++][1] = Format.format(-1);
	}

	static void allPerRp(Players play, PlayerClassification pcd) {
		if (!allPerRpGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 12);
			final var frame = new JFrame("Preflop Opportunity Percentages All players by Position");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(350, 350);
			frame.setSize(600, 100);
			final var tablePos = new JTable(dataPercentageRp, columnsPos);
			tablePos.setFont(ff);
			tablePos.setRowHeight(25);
			final var pane = new JScrollPane(tablePos);
			tablePos.getColumnModel().getColumn(0).setPreferredWidth(120);
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			allPerRpGUI = true;
		}
		/*- TODO
		int saveRow = 0;
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int k = 0; k < NUM_POS; ++k) {
				saveRow = row;
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.limpPosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.walkPosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.checkPosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.foldPosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.bet1PosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.callBet1PosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.bet2PosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.callBet2PosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.bet3PosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.callBet3PosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.bet4PosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.callBet4PosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.allinPosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.callAllinPosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.cBetPosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.formatPer(pcd.barrelPosPer[i][k]);
				dataPercentageRp[row++][k + 1] = Format.format(-1);
				row = saveRow;
			}
			row += 16;
		
		}
		*/
	}

}
