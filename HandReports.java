package holdemhandhistory;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

import java.awt.Dimension;
import java.awt.Font;
import java.math.BigDecimal;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class HandReports implements Constants {

	static Object[] columnsInitial = { "Summary", "Counts" };

	// @formatter:off
	static Object[][] dataInitial = { 
			{ "Status", ""},
			{ "HandID", "",},
			{ "Button seat", ""},
			{ "", ""},
			{ "SB Bet", ""},
			{ "BB Bet", ""},
			{ "", ""},
			{ "Board Array", ""},
			{ "", ""},
			{ "Side Pot 1", ""},
			{ "", ""},
			{ "Rake Rounded", ""},
			{ "Rake No Round", ""},
			{ "", ""},
		 	{ "GGPoker only", ""},
			{ "Rake Jackpot ", ""},
			{ "Rake Bingo",""},
			{ "Rake  Unknown", ""},
			{ "", ""} };
 
	/*-*****************************************************************************************
	 *  - Headers for the table. 
	 ******************************************************************************************/
	static Object[] columnsSeat = { " ", "seat 0", "seat 1", "seat 2", "seat 3", "seat 4", "seat 5" };
	// @formatter:off
	static Object[][] dataSeat= { 
			{ "ID", "","","","","","",""},
			{ "Start Stack", "","","","","","",""},
			{ "Final Stack", "","","","","","",""},
			{ "FoldedArray ", "","","","","","",""},
			{ "PosArray ", "","","","","","",""},
			{ "rpArray", "","","","","","",""} };
	
	
	/*-*****************************************************************************************
	 *  - Headers for the table. 
	 ******************************************************************************************/
	static Object[] columnsCalculationsSeat = { " ", "seat 0", "seat 1	", "seat 2", "seat 3", "seat 4", "seat 5" };
	// @formatter:off
	static Object[][] dataCalculationsSeat= { 
			{ "ID", "","","","","","",""},
			{ "Position", "","","","","","",""},
			{ "Start Stack", "","","","","","",""},
			{ "Final Stack", "","","","","","",""},
			{ "Differfence", "","","","","","",""},
			{ "Win / Loose", "","","","","","",""},
			{ " ", "","","","","","",""},
			{ "Saw Street", "","","","","","",""},
			{ "Collected Street", "","","","","","",""},
			{ "Won Street", "","","","","","",""},
			{ " ", "","","","","","",""},
			{ "Uncalled Returned To Street" , "","","","","","",""},
			{ "Collected Street" , "","","","","","",""}, 
			{ "Won Street", "","","","","","",""},
			{ "Collected Main Pot Showdown", "","","","","","",""}, 
			{ "Collected Pot Showdown", "","","","","","",""},
			{ "Won Side PotS howdown ", "","","","","","",""},
			{ "Collected Side Pot Showdown ", "","","","","","",""},
	 		{ "", "","","","","","",""},
			{ "Won Summary", "","","","","","",""},
			{ "Collected Summary", "","","","","","",""},
			{ "", "","","","","","",""} };


	static Object[] columnsAction = { "Street", "ID", "Action", "Seat", "Call","Bet Raise","Raise To", "Money",
			"Money In", "Stack", "Pot" };

	static Object[][] dataAction = { { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" },
			{ "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" },
			{ "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" },
			{ "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" }, { "" },

			{ "" } };
	
	

	static Object[] columnsCalculatedValues = { "Name ", "Value"};

	static Object[][] dataCalculatedValues= {
			{"handID",""},
			{"handCount",""},
			{"moneyInPreflop",""},
			{"moneyInFlop",""},
			{"moneyInTurn",""},
			{"moneyInRiver",""},
			{"moneyInStreets",""},
			{"moneyOutPreflop",""},
			{"moneyOutFlop",""},
			{"moneyOutTurn",""},
			{"moneyOutRiver",""},
			{"uncalledReturnedTo",""},
			{"moneyOutStreets",""},
			{"moneyOutShowdown",""},
			{"moneyOutSummary",""}, 
			{"temp1",""}, 
			{"temp2",""}, 
			{"temp3",""}, 
			{"SBSeat",""}, 
			{"BBSeat",""}, 
			{"UTGSeat",""}, 
			{"MPSeat ",""}, 
			{"COSeat",""}, 
			{"BUSeat",""}, 
			{ "" ,""}    };
	// @formatter:on 

	static void handReport(Hand hand) {
		JTable table = null;
		final var ff = new Font(Font.SERIF, Font.BOLD, 16);
		final var frame = new JFrame("Values in this hand      handID= " + hand.handID);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(0, 75);
		frame.setPreferredSize(new Dimension(600, 1000));

		final var tableModel = new DefaultTableModel(dataInitial, columnsInitial);
		table = new JTable(tableModel);
		table.setFont(ff);
		table.setRowHeight(30);
		final var pane = new JScrollPane(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(120);
		// Create a custom cell renderer to set the font to bold 16.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		// Apply the custom cell renderer to all table cells.
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		frame.add(pane);
		frame.pack();
		frame.setVisible(true);

		int row = 0;
		int col = 1;
		Card[] card = new Card[5];
		String st = "";

		table.setValueAt(Format.format(hand.status), row++, col);
		table.setValueAt(hand.handID, row++, col);
		table.setValueAt(Format.format(hand.BUSeat), row++, col);
		++row;
		table.setValueAt(Format.format$(hand.SBBet), row++, col);
		table.setValueAt(Format.format$(hand.BBBet), row++, col);
		++row;
		for (int i = 0; i < 5; i++) {
			if (hand.boardArray[i] != -1) {
				card[i] = new Card(hand.boardArray[i]);
				st += card[i].toString();
			}
		}
		table.setValueAt(st, row++, col);
		++row;
		table.setValueAt(Format.format$(hand.sidePot1), row++, col);
		++row;
		table.setValueAt(Format.format$(hand.rakeRound), row++, col);
		table.setValueAt(Format.format$(hand.rakeNoRound), row++, col);

		++row;
		++row;
		table.setValueAt(Format.format$(hand.jackpotRake), row++, col);
		table.setValueAt(Format.format$(hand.bingoRake), row++, col);
		table.setValueAt(Format.format$(hand.unknownRake), row++, col);
	}

	static void handReportBySeat(Hand hand) {
		JTable table = null;
		final var ff = new Font(Font.SERIF, Font.BOLD, 16);
		final var frame = new JFrame("Hand report by seat number      handID= " + hand.handID);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(200, 50);
		frame.setPreferredSize(new Dimension(900, 600));

		final var tableModel = new DefaultTableModel(dataSeat, columnsSeat);
		table = new JTable(tableModel);
		table.setFont(ff);
		table.setRowHeight(30);
		final var pane = new JScrollPane(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(240);
		// table.getColumnModel().getColumn(0).setPreferredWidth(120);
		// Create a custom cell renderer to set the font to bold 16.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		// Apply the custom cell renderer to all table cells.
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		frame.add(pane);
		frame.pack();
		frame.setVisible(true);

		int row = 0;
		int col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(hand.IDArray[i], row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(hand.stack[i]), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(hand.finalStack[i]), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format(HandCalculations.foldArray[i]), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(POSITION_ST2[HandCalculations.posArray[i]], row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(RP_ST[HandCalculations.rpArray[i]], row, col++);
		}
	}

	static void actionReport(Hand hand) {
		JTable table = null;
		final var ff = new Font(Font.SERIF, Font.BOLD, 16);

		final var frame = new JFrame("Action array      handID= " + hand.handID);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(650, 100);
		frame.setPreferredSize(new Dimension(1000, 900));

		final var tableModel = new DefaultTableModel(dataAction, columnsAction);
		table = new JTable(tableModel);
		table.setFont(ff);
		table.setRowHeight(30);
		final var pane = new JScrollPane(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(40);
		table.getColumnModel().getColumn(1).setPreferredWidth(10);
		table.getColumnModel().getColumn(2).setPreferredWidth(160);
		table.getColumnModel().getColumn(3).setPreferredWidth(10);
		table.getColumnModel().getColumn(4).setPreferredWidth(20);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);
		table.getColumnModel().getColumn(6).setPreferredWidth(30);
		table.getColumnModel().getColumn(7).setPreferredWidth(30);
		table.getColumnModel().getColumn(8).setPreferredWidth(30);
		table.getColumnModel().getColumn(9).setPreferredWidth(30);
		table.getColumnModel().getColumn(10).setPreferredWidth(30);

		// table.getColumnModel().getColumn(0).setPreferredWidth(120);
		// Create a custom cell renderer to set the font to bold 16.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		// Apply the custom cell renderer to all table cells.
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		frame.add(pane);
		frame.pack();
		frame.setVisible(true);

		int row = 0;
		for (int i = 0; i < hand.actionCount; ++i) {
			int col = 0;
			table.setValueAt(STREET_ST[hand.actionArray[i].street], row, col++);
			table.setValueAt(hand.actionArray[i].ID, row, col++);
			table.setValueAt(ACTION_ST[hand.actionArray[i].action], row, col++);
			table.setValueAt(hand.actionArray[i].seat, row, col++);
			table.setValueAt(Format.format$(hand.actionArray[i].call), row, col++);
			table.setValueAt(Format.format$(hand.actionArray[i].betRaise), row, col++);
			table.setValueAt(Format.format$(hand.actionArray[i].raiseTo), row, col++);
			if (hand.actionArray[i].action <= Hand.ACTION_LAST_MONEY && !hand.actionArray[i].money.equals(0)) {
				table.setValueAt(Format.format$(hand.actionArray[i].money), row, col);
				++col;
				table.setValueAt(Format.format$(hand.actionArray[i].moneyIn), row, col);
				++col;
			}
			table.setValueAt(Format.format$(hand.actionArray[i].stack), row, col++);
			table.setValueAt(Format.format$(hand.actionArray[i].pot), row, col++);
			++row;
		}

	}

	/*-******************************************************************************
	 * Calculations
	 *******************************************************************************/
	static void calculationsSeat(Hand hand) {
		JTable table = null;
		final var ff = new Font(Font.SERIF, Font.BOLD, 16);

		final var frame = new JFrame("Calculations     handID= " + hand.handID);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(700, 100);
		frame.setPreferredSize(new Dimension(1000, 900));

		final var tableModel = new DefaultTableModel(dataCalculationsSeat, columnsCalculationsSeat);
		table = new JTable(tableModel);
		table.setFont(ff);
		table.setRowHeight(30);
		final var pane = new JScrollPane(table);
		table.getColumnModel().getColumn(0).setPreferredWidth(160);

		// table.getColumnModel().getColumn(0).setPreferredWidth(120);
		// Create a custom cell renderer to set the font to bold 16.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		// Apply the custom cell renderer to all table cells.
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		frame.add(pane);
		frame.pack();
		frame.setVisible(true);

		String st = "";
		int row = 0;
		int col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(hand.IDArray[i], row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			// table.setValueAt(POSITION_ST2[HandCalculations.position[i]], row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(hand.stack[i]), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(hand.finalStack[i]), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			BigDecimal d = hand.stack[i].subtract(hand.finalStack[i]);
			table.setValueAt(Format.format$(d), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(HandCalculations.winLoose[i]), row, col++);
		}
		++row;
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			if (HandCalculations.sawStreetOrShow[i] == -1) {
				st = "__";
			} else if (HandCalculations.sawStreetOrShow[i] == 0) {
				st = "Preflop";
			} else if (HandCalculations.sawStreetOrShow[i] == 1) {
				st = "Flop";
			} else if (HandCalculations.sawStreetOrShow[i] == 2) {
				st = "Turn";
			} else if (HandCalculations.sawStreetOrShow[i] == 3) {
				st = "River";
			}
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			if (HandCalculations.wonStreetOrShow[i] == -1) {
				st = "__";
			} else if (HandCalculations.wonStreetOrShow[i] == 0) {
				st = "Preflop";
			} else if (HandCalculations.wonStreetOrShow[i] == 1) {
				st = "Flop";
			} else if (HandCalculations.wonStreetOrShow[i] == 2) {
				st = "Turn";
			} else if (HandCalculations.wonStreetOrShow[i] == 3) {
				st = "River";
			}
			table.setValueAt(st, row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			if (HandCalculations.foldedStreetOrLostShow[i] == -1) {
				st = "__";
			} else if (HandCalculations.foldedStreetOrLostShow[i] == 0) {
				st = "Preflop";
			} else if (HandCalculations.foldedStreetOrLostShow[i] == 1) {
				st = "Flop";
			} else if (HandCalculations.foldedStreetOrLostShow[i] == 2) {
				st = "Turn";
			} else if (HandCalculations.foldedStreetOrLostShow[i] == 3) {
				st = "River";
			}
			table.setValueAt(st, row, col++);
		}
		++row;
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(HandCalculations.uncalledReturnedToStreet[i]), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(HandCalculations.collectedStreet[i]), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(HandCalculations.wonStreet[i]), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(HandCalculations.collectedMainPotShowdown[i]), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(HandCalculations.collectedPotShowdown[i]), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(HandCalculations.wonSidePotShowdown[i]), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(HandCalculations.collectedSidePotShowdown[i]), row, col++);
		}

		++row;
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(HandCalculations.wonSummary[i]), row, col++);
		}
		++row;
		col = 1;
		for (int i = 0; i < 6; ++i) {
			table.setValueAt(Format.format$(HandCalculations.collectedSummary[i]), row, col++);
		}
	}

	/*-******************************************************************************
	 * Values
	 *******************************************************************************/
	static void calculationsValues(Hand hand) {
		JTable table = null;
		final var ff = new Font(Font.SERIF, Font.BOLD, 16);

		final var frame = new JFrame("Calculations  Values   handID= " + hand.handID);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(800, 150);
		frame.setPreferredSize(new Dimension(400, 1200));

		final var tableModel = new DefaultTableModel(dataCalculatedValues, columnsCalculatedValues);
		table = new JTable(tableModel);
		table.setFont(ff);
		table.setRowHeight(30);
		final var pane = new JScrollPane(table);

		// table.getColumnModel().getColumn(0).setPreferredWidth(120);
		// Create a custom cell renderer to set the font to bold 16.
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setFont(new Font(Font.SERIF, Font.BOLD, 16));
		// Apply the custom cell renderer to all table cells.
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setCellRenderer(renderer);
		}
		frame.add(pane);
		frame.pack();
		frame.setVisible(true);
		int row = 0;
		table.setValueAt(hand.handID, row++, 1);
		table.setValueAt(Format.format(HandCalculations.handCount), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.moneyInPreflop), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.moneyInFlop), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.moneyInTurn), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.moneyInRiver), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.moneyInStreets), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.moneyOutPreflop), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.moneyOutFlop), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.moneyOutTurn), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.moneyOutRiver), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.uncalledReturnedTo), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.moneyOutStreets), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.moneyOutShowdown), row++, 1);
		table.setValueAt(Format.format$(HandCalculations.moneyOutSummary), row++, 1);
		table.setValueAt(Format.format(HandCalculations.temp1), row++, 1);
		table.setValueAt(Format.format(HandCalculations.temp2), row++, 1);
		table.setValueAt(Format.format(HandCalculations.temp3), row++, 1);
		table.setValueAt(Format.format(HandCalculations.SBSeat), row++, 1);
		table.setValueAt(Format.format(HandCalculations.BBSeat), row++, 1);
		table.setValueAt(Format.format(HandCalculations.UTGSeat), row++, 1);
		table.setValueAt(Format.format(HandCalculations.MPSeat), row++, 1);
		table.setValueAt(Format.format(HandCalculations.COSeat), row++, 1);
		table.setValueAt(Format.format(HandCalculations.BUSeat), row++, 1);
	}

}
