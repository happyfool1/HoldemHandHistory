package holdemhandhistory;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

public class PlayerMoneyReports implements Constants {
	/*-****************************************************************************************
	 * This class does a bunch of reports using data collected by .
	 * The reports are in a separate class ( this one ) for a couple of resons:
	 * 		 was just too big.
	 * 		By passing PlayerClassification and Players classes to this class it makes reports
	 * 		for filtered players or player tipes very simple. No code changes to report methods.
	 * Most of the reports are designed to assist in debug and testing but
	 * are also usefull for understanding how the collected and analyzed data can be used to
	 * improve exploits.
	 * 
	 * Something new to Holdem applications is the idea of relative positions and
	 * ranges associated.
	 *	 LASTHU last heads up are the Button in orbit 0 BUT if the Button
	 *	folds then the Cutoff becomes in effect the Button, last heads up. 
	 *	So ranges can change in after Orbit 0. 
	 *	In the Button folding example, the range used by the Cutoff is dynamic
	 *	not static as is usually the case now.
	 *	I'll be evaluating this as we progress. 
	 * 
	 * @author PEAK_
	**************************************************************************************** */
	// @formatter:off
	
	private static final Object[][] dataMoneyPos = {
	{"Preflop ----","","","","","",""},
	{"raisePos$","","","","","",""},
	{"betPos$","","","","","",""},
	{"callPos$","","","","","",""},
	{"minBetPos$","","","","","",""},
	{"bet1PosBD$","","","","","",""},
	{"callBet1PosBD$","","","","","",""},
	{"bet2PosBD$","","","","","",""},
	{"callBet2PosBD$","","","","","",""},
	{"bet3PosBD$","","","","","",""},
	{"callBet3PosBD$","","","","","",""},
	{"bet4PosBD$","","","","","",""},
	{"callBet4PosBD$","","","","","",""},
	{"allinPosBD$","","","","","",""},
	{"callAllinPosBD$","","","","","",""},
	{"cBetPosBD$","","","","","",""},
	{"barrelPosBD$","","","","","",""},
	{"potOddsPosBD$","","","","","",""},
	{"SPRPosBD$","","","","","",""},
	{"potPosBD$","","","","","",""},
	{"raisePos$","","","","","",""},
	{"betPos$","","","","","",""},
	{"callPos$","","","","","",""},
	{"minBetPos$","","","","","",""},
	{"bet1PosBD$","","","","","",""},
	{"callBet1PosBD$","","","","","",""},
	{"bet2PosBD$","","","","","",""},
	{"callBet2PosBD$","","","","","",""},
	{"bet3PosBD$","","","","","",""},
	{"callBet3PosBD$","","","","","",""},
	{"bet4PosBD$","","","","","",""},
	{"callBet4PosBD$","","","","","",""},
	{"allinPosBD$","","","","","",""},
	{"callAllinPosBD$","","","","","",""},
	{"cBetPosBD$","","","","","",""},
	{"barrelPosBD$","","","","","",""},
	{"potOddsPosBD$","","","","","",""},
	{"SPRPosBD$","","","","","",""},
	{"","","","","","",""},
	{"Flop ----","","","","","",""},
	{"raisePos$","","","","","",""},
	{"betPos$","","","","","",""},
	{"callPos$","","","","","",""},
	{"minBetPos$","","","","","",""},
	{"bet1PosBD$","","","","","",""},
	{"callBet1PosBD$","","","","","",""},
	{"bet2PosBD$","","","","","",""},
	{"callBet2PosBD$","","","","","",""},
	{"bet3PosBD$","","","","","",""},
	{"callBet3PosBD$","","","","","",""},
	{"bet4PosBD$","","","","","",""},
	{"callBet4PosBD$","","","","","",""},
	{"allinPosBD$","","","","","",""},
	{"callAllinPosBD$","","","","","",""},
	{"cBetPosBD$","","","","","",""},
	{"barrelPosBD$","","","","","",""},
	{"potOddsPosBD$","","","","","",""},
	{"SPRPosBD$","","","","","",""},
	{"potPosBD$","","","","","",""},
	{"raisePos$","","","","","",""},
	{"betPos$","","","","","",""},
	{"callPos$","","","","","",""},
	{"minBetPos$","","","","","",""},
	{"bet1PosBD$","","","","","",""},
	{"callBet1PosBD$","","","","","",""},
	{"bet2PosBD$","","","","","",""},
	{"callBet2PosBD$","","","","","",""},
	{"bet3PosBD$","","","","","",""},
	{"callBet3PosBD$","","","","","",""},
	{"bet4PosBD$","","","","","",""},
	{"callBet4PosBD$","","","","","",""},
	{"allinPosBD$","","","","","",""},
	{"callAllinPosBD$","","","","","",""},
	{"cBetPosBD$","","","","","",""},
	{"barrelPosBD$","","","","","",""},
	{"potOddsPosBD$","","","","","",""},
	{"SPRPosBD$","","","","","",""},
	{"","","","","","",""},
	{"Turn ----","","","","","",""},
	{"raisePos$","","","","","",""},
	{"betPos$","","","","","",""},
	{"callPos$","","","","","",""},
	{"minBetPos$","","","","","",""},
	{"bet1PosBD$","","","","","",""},
	{"callBet1PosBD$","","","","","",""},
	{"bet2PosBD$","","","","","",""},
	{"callBet2PosBD$","","","","","",""},
	{"bet3PosBD$","","","","","",""},
	{"callBet3PosBD$","","","","","",""},
	{"bet4PosBD$","","","","","",""},
	{"callBet4PosBD$","","","","","",""},
	{"allinPosBD$","","","","","",""},
	{"callAllinPosBD$","","","","","",""},
	{"cBetPosBD$","","","","","",""},
	{"barrelPosBD$","","","","","",""},
	{"potOddsPosBD$","","","","","",""},
	{"SPRPosBD$","","","","","",""},
	{"potPosBD$","","","","","",""},
	{"raisePos$","","","","","",""},
	{"betPos$","","","","","",""},
	{"callPos$","","","","","",""},
	{"minBetPos$","","","","","",""},
	{"bet1PosBD$","","","","","",""},
	{"callBet1PosBD$","","","","","",""},
	{"bet2PosBD$","","","","","",""},
	{"callBet2PosBD$","","","","","",""},
	{"bet3PosBD$","","","","","",""},
	{"callBet3PosBD$","","","","","",""},
	{"bet4PosBD$","","","","","",""},
	{"callBet4PosBD$","","","","","",""},
	{"allinPosBD$","","","","","",""},
	{"callAllinPosBD$","","","","","",""},
	{"cBetPosBD$","","","","","",""},
	{"barrelPosBD$","","","","","",""},
	{"potOddsPosBD$","","","","","",""},
	{"SPRPosBD$","","","","","",""},
	{"","","","","","",""},
	{"River ----","","","","","",""},
	{"raisePos$","","","","","",""},
	{"betPos$","","","","","",""},
	{"callPos$","","","","","",""},
	{"minBetPos$","","","","","",""},
	{"bet1PosBD$","","","","","",""},
	{"callBet1PosBD$","","","","","",""},
	{"bet2PosBD$","","","","","",""},
	{"callBet2PosBD$","","","","","",""},
	{"bet3PosBD$","","","","","",""},
	{"callBet3PosBD$","","","","","",""},
	{"bet4PosBD$","","","","","",""},
	{"callBet4PosBD$","","","","","",""},
	{"allinPosBD$","","","","","",""},
	{"callAllinPosBD$","","","","","",""},
	{"cBetPosBD$","","","","","",""},
	{"barrelPosBD$","","","","","",""},
	{"potOddsPosBD$","","","","","",""},
	{"SPRPosBD$","","","","","",""},
	{"potPosBD$","","","","","",""},
	{"raisePos$","","","","","",""},
	{"betPos$","","","","","",""},
	{"callPos$","","","","","",""},
	{"minBetPos$","","","","","",""},
	{"bet1PosBD$","","","","","",""},
	{"callBet1PosBD$","","","","","",""},
	{"bet2PosBD$","","","","","",""},
	{"callBet2PosBD$","","","","","",""},
	{"bet3PosBD$","","","","","",""},
	{"callBet3PosBD$","","","","","",""},
	{"bet4PosBD$","","","","","",""},
	{"callBet4PosBD$","","","","","",""},
	{"allinPosBD$","","","","","",""},
	{"callAllinPosBD$","","","","","",""},
	{"cBetPosBD$","","","","","",""},
	{"barrelPosBD$","","","","","",""},
	{"potOddsPosBD$","","","","","",""},
	{"SPRPosBD$","","","","","",""},
	{"","","","","","",""},
	{"","","","","","",""}	};
	
	private static final Object[][] dataRp = {
			{"Preflop ----","","","","","","","","","","","",""},
			{"raiseRp$","","","","","","","","","","","",""},
			{"betRp$","","","","","","","","","","","",""},
			{"callRp$ ","","","","","","","","","","","",""},
			{"minBetRp$ ","","","","","","","","","","","",""},
			{"bet1RpBD$ ","","","","","","","","","","","",""},
			{"callBet1RpBD$ ","","","","","","","","","","","",""},
			{"bet2RpBD$ ","","","","","","","","","","","",""},
			{"callBet2RpBD$ ","","","","","","","","","","","",""},
			{"bet3RpBD$ ","","","","","","","","","","","",""},
			{"callBet3RpBD$ ","","","","","","","","","","","",""},
			{"bet4RpBD$ ","","","","","","","","","","","",""},
			{"callBet4RpBD$ ","","","","","","","","","","","",""},
			{"allinRpBD$ ","","","","","","","","","","","",""},
			{"callAllinRpBD$ ","","","","","","","","","","","",""},
			{"cBetRpBD$ ","","","","","","","","","","","",""},
			{"barrelRpBD$ ","","","","","","","","","","","",""},
			{"potRpBD$ ","","","","","","","","","","","",""},
			{"","","","","","","","","","","","",""},
			{"Flop ----","","","","","","","","","","","",""},
			{"raiseRp$","","","","","","","","","","","",""},
			{"betRp$","","","","","","","","","","","",""},
			{"callRp$ ","","","","","","","","","","","",""},
			{"minBetRp$ ","","","","","","","","","","","",""},
			{"bet1RpBD$ ","","","","","","","","","","","",""},
			{"callBet1RpBD$ ","","","","","","","","","","","",""},
			{"bet2RpBD$ ","","","","","","","","","","","",""},
			{"callBet2RpBD$ ","","","","","","","","","","","",""},
			{"bet3RpBD$ ","","","","","","","","","","","",""},
			{"callBet3RpBD$ ","","","","","","","","","","","",""},
			{"bet4RpBD$ ","","","","","","","","","","","",""},
			{"callBet4RpBD$ ","","","","","","","","","","","",""},
			{"allinRpBD$ ","","","","","","","","","","","",""},
			{"callAllinRpBD$ ","","","","","","","","","","","",""},
			{"cBetRpBD$ ","","","","","","","","","","","",""},
			{"barrelRpBD$ ","","","","","","","","","","","",""},
			{"potRpBD$ ","","","","","","","","","","","",""},
			{"","","","","","","","","","","","",""},
			{"Turn ----","","","","","","","","","","","",""},
			{"raiseRp$","","","","","","","","","","","",""},
			{"betRp$","","","","","","","","","","","",""},
			{"callRp$ ","","","","","","","","","","","",""},
			{"minBetRp$ ","","","","","","","","","","","",""},
			{"bet1RpBD$ ","","","","","","","","","","","",""},
			{"callBet1RpBD$ ","","","","","","","","","","","",""},
			{"bet2RpBD$ ","","","","","","","","","","","",""},
			{"callBet2RpBD$ ","","","","","","","","","","","",""},
			{"bet3RpBD$ ","","","","","","","","","","","",""},
			{"callBet3RpBD$ ","","","","","","","","","","","",""},
			{"bet4RpBD$ ","","","","","","","","","","","",""},
			{"callBet4RpBD$ ","","","","","","","","","","","",""},
			{"allinRpBD$ ","","","","","","","","","","","",""},
			{"callAllinRpBD$ ","","","","","","","","","","","",""},
			{"cBetRpBD$ ","","","","","","","","","","","",""},
			{"barrelRpBD$ ","","","","","","","","","","","",""},
			{"potRpBD$ ","","","","","","","","","","","",""},
			{"","","","","","","","","","","","",""},
			{"River ----","","","","","","","","","","","",""},
			{"raiseRp$","","","","","","","","","","","",""},
			{"betRp$","","","","","","","","","","","",""},
			{"callRp$ ","","","","","","","","","","","",""},
			{"minBetRp$ ","","","","","","","","","","","",""},
			{"bet1RpBD$ ","","","","","","","","","","","",""},
			{"callBet1RpBD$ ","","","","","","","","","","","",""},
			{"bet2RpBD$ ","","","","","","","","","","","",""},
			{"callBet2RpBD$ ","","","","","","","","","","","",""},
			{"bet3RpBD$ ","","","","","","","","","","","",""},
			{"callBet3RpBD$ ","","","","","","","","","","","",""},
			{"bet4RpBD$ ","","","","","","","","","","","",""},
			{"callBet4RpBD$ ","","","","","","","","","","","",""},
			{"allinRpBD$ ","","","","","","","","","","","",""},
			{"callAllinRpBD$ ","","","","","","","","","","","",""},
			{"cBetRpBD$ ","","","","","","","","","","","",""},
			{"barrelRpBD$ ","","","","","","","","","","","",""},
			{"potRpBD$ ","","","","","","","","","","","",""},
			{"  ","","","","","","","","","","","",""}	};
	
	private static final Object[][] dataStreets = {
			{"stack","","","","","",""},
			{"potAtEndOfStreetBD$ ","","","","","",""}	 };
	
	private static final Object[][] dataPlayers= {
			{"potSeatBD$","","","","" ,"",""},
			{"rakeSeatBD$","","","","" ,"",""}  };
	
	private static final Object[][] dataValues = {
			{"stack$ ","",""},
			{"averageStack$ ","",""},  
			{"wonShowdown$ ","",""}, 
			{"stack$Current","",""}, 
			{"stack$Previous ","",""}, 
			{"winnings$","",""},  
			{"won$","",""},
			{"potBD$ ","",""},
			{"rakeBD$ ","",""}, 
			{"bet3BD$ ","",""}, 
			{"callBet3BD$ ","",""}, 
			{"raiseSizeBD$ ","",""}, 
			{"betSizeBD$ ","",""} 	};
	
	private static final Object[] columnsRp = { "Action ", "First", "First HU", "Middle1", "Middle2","Middle3","Middle4","Last", "Last HU" };
	private static final Object[] columnsPos = { "Action ", "SB", "BB", "UTG", "MP", "Cutoff", "Button" };
	private static final Object[] columnsStreets = { "Street", "Preflop", "Flop", "Turn", "River" };
	private static final Object[] columnsPlayers= { "Street", "Preflop", "Flop", "Turn", "River" };
	private static final Object[] columnsValues = { "Data", "Value", "Description" };

	
	// @formatter:on

	static boolean moneyPosGUI = false;

	static void moneyPos(PlayerMoney pm) {
		if (!moneyPosGUI) {
			moneyPosGUI = true;
			final var frame = new JFrame("Positions");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(90, 90);
			frame.setPreferredSize(new Dimension(500, 850));
			final var table = new JTable(dataMoneyPos, columnsPos);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			// table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 18.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 18));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
		}

		int row = 1;
		int col = 1;
		int saveRow = 1;
		for (int street = 0; street < NUM_STREETS; street++) {
			for (int i = 0; i < NUM_POS; i++) {
				row = saveRow;
				dataMoneyPos[row++][col] = Format.format$(pm.raisePos$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.betPos$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.callPos$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.minBetPos$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.bet1PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.callBet1PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.bet2PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.callBet2PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.bet3PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.callBet3PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.bet4PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.callBet4PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.allinPosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.callAllinPosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.cBetPosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.barrelPosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.potOddsPosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.SPRPosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.potPosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.raisePos$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.betPos$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.callPos$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.minBetPos$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.bet1PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.callBet1PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.bet2PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.callBet2PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.bet3PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.callBet3PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.bet4PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.callBet4PosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.allinPosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.callAllinPosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.cBetPosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.barrelPosBD$[street][i]);
				dataMoneyPos[row++][col] = Format.format$(pm.potOddsPosBD$[street][i]);
				dataMoneyPos[row][col++] = Format.format$(pm.SPRPosBD$[street][i]);
			}
			row += 3;
			saveRow = row;
			col = 1;
		}
	}

	static boolean moneyRpGUI = false;

	static void moneyRp(PlayerMoney pm) {
		if (!moneyRpGUI) {
			moneyRpGUI = true;
			final var frame = new JFrame("Relative Positions");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(130, 130);
			frame.setPreferredSize(new Dimension(500, 850));
			final var table = new JTable(dataRp, columnsRp);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			// table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 18.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 18));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
		}

		int row = 1;
		int col = 1;
		int saveRow = 1;
		for (int street = 0; street < NUM_STREETS; street++) {
			for (int i = 0; i < NUM_RP; i++) {
				row = saveRow;
				dataRp[row++][col] = Format.format$(pm.raiseRp$[street][i]);
				dataRp[row++][col] = Format.format$(pm.betRp$[street][i]);
				dataRp[row++][col] = Format.format$(pm.callRp$[street][i]);
				dataRp[row++][col] = Format.format$(pm.minBetRp$[street][i]);
				dataRp[row++][col] = Format.format$(pm.bet1RpBD$[street][i]);
				dataRp[row++][col] = Format.format$(pm.callBet1RpBD$[street][i]);
				dataRp[row++][col] = Format.format$(pm.bet2RpBD$[street][i]);
				dataRp[row++][col] = Format.format$(pm.callBet2RpBD$[street][i]);
				dataRp[row++][col] = Format.format$(pm.bet3RpBD$[street][i]);
				dataRp[row++][col] = Format.format$(pm.callBet3RpBD$[street][i]);
				dataRp[row++][col] = Format.format$(pm.bet4RpBD$[street][i]);
				dataRp[row++][col] = Format.format$(pm.callBet4RpBD$[street][i]);
				dataRp[row++][col] = Format.format$(pm.allinRpBD$[street][i]);
				dataRp[row++][col] = Format.format$(pm.callAllinRpBD$[street][i]);
				dataRp[row++][col] = Format.format$(pm.cBetRpBD$[street][i]);
				dataRp[row++][col] = Format.format$(pm.barrelRpBD$[street][i]);
				dataRp[row][col++] = Format.format$(pm.potRpBD$[street][i]);
			}
			col = 1;
			row += 3;
			saveRow = row;
		}
	}

	static boolean moneyStreetGUI = false;

	static void moneyStreet(PlayerMoney pm) {
		if (!moneyStreetGUI) {
			moneyStreetGUI = true;
			final var frame = new JFrame("Streets");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(160, 160);
			frame.setPreferredSize(new Dimension(500, 850));
			final var table = new JTable(dataStreets, columnsStreets);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			// table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 18.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 18));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
		}
		int row = 0;
		int col = 1;
		for (int street = 0; street < NUM_STREETS; street++) {
			row = 0;
			dataStreets[row++][col] = Format.format$(pm.stack[street]);
			dataStreets[row][col++] = Format.format$(pm.potAtEndOfStreetBD$[street]);
		}
	}

	static boolean moneyPlayersGUI = false;

	static void moneyPlayers(PlayerMoney pm) {
		if (!moneyPlayersGUI) {
			moneyPlayersGUI = true;
			final var frame = new JFrame("Players");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(190, 190);
			frame.setPreferredSize(new Dimension(500, 850));
			final var table = new JTable(dataPlayers, columnsPlayers);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			// table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 18.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 18));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
		}
		int row = 0;
		int col = 1;
		for (int i = 0; i < PLAYERS; i++) {
			row = 0;
			dataPlayers[row++][col] = Format.format$(pm.potSeatBD$[i]);
			dataPlayers[row][col++] = Format.format$(pm.rakeSeatBD$[i]);
		}
	}

	static boolean moneyValueGUI = false;

	static void moneyValue(PlayerMoney pm) {
		if (!moneyValueGUI) {
			moneyValueGUI = true;
			final var frame = new JFrame("Values");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(210, 210);
			frame.setPreferredSize(new Dimension(400, 450));
			final var table = new JTable(dataValues, columnsValues);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
			table.getColumnModel().getColumn(1).setPreferredWidth(60);
			table.getColumnModel().getColumn(2).setPreferredWidth(400);
			// Create a custom cell renderer to set the font to bold 18.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 18));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
		}
		int row = 0;
		int col = 1;
		dataValues[row++][col] = Format.format$(pm.stack$);
		dataValues[row++][col] = Format.format$(pm.averageStack$);
		dataValues[row++][col] = Format.format$(pm.wonShowdown$);
		dataValues[row++][col] = Format.format$(pm.stack$Current);
		dataValues[row++][col] = Format.format$(pm.stack$Previous);
		dataValues[row++][col] = Format.format$(pm.winnings$);
		dataValues[row++][col] = Format.format$(pm.won$);
		dataValues[row++][col] = Format.format$(pm.potBD$);
		dataValues[row++][col] = Format.format$(pm.rakeBD$);
		dataValues[row++][col] = Format.format$(pm.bet3BD$);
		dataValues[row++][col] = Format.format$(pm.callBet3BD$);
		dataValues[row++][col] = Format.format$(pm.raiseSizeBD$);
		dataValues[row][col] = Format.format$(pm.betSizeBD$);
	}

}
