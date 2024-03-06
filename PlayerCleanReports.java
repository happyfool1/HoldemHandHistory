package holdemhandhistory;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

public class PlayerCleanReports implements Constants {

// @formatter:off
		private static final Object[][] dataBets  = { }; // TODO
		private static final Object[][] columnsBets  = { }; // TODO
	
	
 
	private static final Object[] columnsBetsPot = { "Bet       ","POT_1Q","POT_2Q"," POT_3Q","POT_4Q","POT_5Q","POT_6Q","POT_ALLIN"};
	private static final Object[][] dataBetsPot = { 
			{ "Preflop           ","","","","","","" ,"",""},
			{ "Bet 1  $          ","","","","","","" ,"",""},
			{ "Bet 1  count  ","","","","","","" ,"",""},
			{ "Bet2  $      ","","","","","","" ,"",""},
			{ "Bet 2  count  ","","","","","","" ,"",""},
			{ "Bet 3  $          ","","","","","","" ,"",""},
			{ "Bet 3  count  ","","","","","","" ,"",""},
			{ "Bet 4  $          ","","","","","","" ,"",""},
			{ "Bet 4  count  ","","","","","","" ,"",""},
			{ "All-in  $          ","","","","","","" ,"",""},
			{ "All-In  count  ","","","","","","" ,"",""},
			{ "                        ","","","","","","" ,"",""},
		
			
			{ "Flop         ","","","","","","" ,"",""},
			{ "Bet 1  $          ","","","","","","" ,"",""},
			{ "Bet 1  count  ","","","","","","" ,"",""},
			{ "Bet2  $      ","","","","","","" ,"",""},
			{ "Bet 2  count  ","","","","","","" ,"",""},
			{ "Bet 3  $          ","","","","","","" ,"",""},
			{ "Bet 3  count  ","","","","","","" ,"",""},
			{ "Bet 4  $          ","","","","","","" ,"",""},
			{ "Bet 4  count  ","","","","","","" ,"",""},
			{ "All-in  $          ","","","","","","" ,"",""},
			{ "All-In  count  ","","","","","","" ,"",""},
			{ "                        ","","","","","","" ,"",""},
			
			{ "Turn          ","","","","","","" ,"",""},
			{ "Bet 1  $          ","","","","","","" ,"",""},
			{ "Bet 1  count  ","","","","","","" ,"",""},
			{ "Bet2  $      ","","","","","","" ,"",""},
			{ "Bet 2  count  ","","","","","","" ,"",""},
			{ "Bet 3  $          ","","","","","","" ,"",""},
			{ "Bet 3  count  ","","","","","","" ,"",""},
			{ "Bet 4  $          ","","","","","","" ,"",""},
			{ "Bet 4  count  ","","","","","","" ,"",""},
			{ "All-in  $          ","","","","","","" ,"",""},
			{ "All-In  count  ","","","","","","" ,"",""},
			{ "                        ","","","","","","" ,"",""},
			
			{ "River           ","","","","","","" ,"",""},
			{ "Bet 1  $          ","","","","","","" ,"",""},
			{ "Bet 1  count  ","","","","","","" ,"",""},
			{ "Bet2  $      ","","","","","","" ,"",""},
			{ "Bet 2  count  ","","","","","","" ,"",""},
			{ "Bet 3  $          ","","","","","","" ,"",""},
			{ "Bet 3  count  ","","","","","","" ,"",""},
			{ "Bet 4  $          ","","","","","","" ,"",""},
			{ "Bet 4  count  ","","","","","","" ,"",""},
			{ "All-in  $          ","","","","","","" ,"",""},
			{ "All-In  count  ","","","","","","" ,"",""},
			{ "                        ","","","","","","" ,"",""},
			{ "                        ","","","","","","" ,"",""}};
	
	

	private static final Object[] columnsRp = { "Action ","FIRST","FIRSTHU","MIDDLE","LAST","LASTHU" };
	private static final Object[] columnsPos = { "Action ","SB","BB","UTG","MP","Cutoff","Button" };
 

	 
	private static PlayerClassification allPlayerClassification;

	private static final Object[] columnsClean = { "Data","Value","Description"}; 
	 
	private static boolean preflopCleanPosGUI = false;
 
	private static final Object[][] dataCleanPos = { 
			{ "Preflop","",""},
 			{ "clean1BetCount","",""},
			{ "cleanCall1BetCount","",""},
			{ "clea2BetCount","",""},
			{ "cleanCall2BetCount","",""},
			{ "clean3BetCount","",""},
			{ "cleanCall3BetCount","",""},
			{ "clean4BetCount ","",""},
			{ "cleanCall4BetCount ","",""},
			{ "cleanAllinCount ","",""},
			{ "cleanCallAllinCount","",""},
			{ "","",""},
			{ "clean1BetOperCount ","",""},
			{ "cleanCall1BetOperCount ","",""},
			{ "clean2BetOperCount ","",""},
			{ "cleanCall2BetOperCount ","",""},
			{ "clean3BetOperCount ","",""},
			{ "cleanCall3BetOperCount ","",""},
			{ "clean4BetOperCount ","",""},
			{ "cleanCall4BetOperCount ","",""},
			{ "cleanAllinOperCount ","",""},
			{ "cleanCallAllinOperCount","",""},
			{ "","",""}, 
			{ "clean1BetPer","",""},
			{ "cleanCall1BetPer","",""},
			{ "clean2BetPer","",""},
			{ "cleanCall2BetPer","",""},
			{ "clean3BetPer ","",""},
			{ "cleanCall3BetPer ","",""},
			{ "clean4BetPer ","",""},
			{ "cleanCall4BetPer","",""},
			{ "cleanAllinPer ","",""},
			{ "cleanCallAllinPer ","",""},
			{ "","",""}, 
			{ "Flop","",""},
			{ "clean1BetCount","",""},
			{ "cleanCall1BetCount","",""},
			{ "clea2BetCount","",""},
			{ "cleanCall2BetCount","",""},
			{ "clean3BetCount","",""},
			{ "cleanCall3BetCount","",""},
			{ "clean4BetCount ","",""},
			{ "cleanCall4BetCount ","",""},
			{ "cleanAllinCount ","",""},
			{ "cleanCallAllinCount","",""},
			{ "","",""},
			{ "clean1BetOperCount ","",""},
			{ "cleanCall1BetOperCount ","",""},
			{ "clean2BetOperCount ","",""},
			{ "cleanCall2BetOperCount ","",""},
			{ "clean3BetOperCount ","",""},
			{ "cleanCall3BetOperCount ","",""},
			{ "clean4BetOperCount ","",""},
			{ "cleanCall4BetOperCount ","",""},
			{ "cleanAllinOperCount ","",""},
			{ "cleanCallAllinOperCount","",""},
			{ "","",""}, 
			{ "clean1BetPer","",""},
			{ "cleanCall1BetPer","",""},
			{ "clean2BetPer","",""},
			{ "cleanCall2BetPer","",""},
			{ "clean3BetPer ","",""},
			{ "cleanCall3BetPer ","",""},
			{ "clean4BetPer ","",""},
			{ "cleanCall4BetPer","",""},
			{ "cleanAllinPer ","",""},
			{ "cleanCallAllinPer ","",""},
			{ "","",""}, 
			{ "Turn","",""}, 
			{ "clean1BetCount","",""},
			{ "cleanCall1BetCount","",""},
			{ "clea2BetCount","",""},
			{ "cleanCall2BetCount","",""},
			{ "clean3BetCount","",""},
			{ "cleanCall3BetCount","",""},
			{ "clean4BetCount ","",""},
			{ "cleanCall4BetCount ","",""},
			{ "cleanAllinCount ","",""},
			{ "cleanCallAllinCount","",""},
			{ "","",""},
			{ "clean1BetOperCount ","",""},
			{ "cleanCall1BetOperCount ","",""},
			{ "clean2BetOperCount ","",""},
			{ "cleanCall2BetOperCount ","",""},
			{ "clean3BetOperCount ","",""},
			{ "cleanCall3BetOperCount ","",""},
			{ "clean4BetOperCount ","",""},
			{ "cleanCall4BetOperCount ","",""},
			{ "cleanAllinOperCount ","",""},
			{ "cleanCallAllinOperCount","",""},
			{ "","",""}, 
			{ "clean1BetPer","",""},
			{ "cleanCall1BetPer","",""},
			{ "clean2BetPer","",""},
			{ "cleanCall2BetPer","",""},
			{ "clean3BetPer ","",""},
			{ "cleanCall3BetPer ","",""},
			{ "clean4BetPer ","",""},
			{ "cleanCall4BetPer","",""},
			{ "cleanAllinPer ","",""},
			{ "cleanCallAllinPer ","",""},
			{ "","",""}, 
			{ "River","",""},
			{ "clean1BetCount","",""},
			{ "cleanCall1BetCount","",""},
			{ "clea2BetCount","",""},
			{ "cleanCall2BetCount","",""},
			{ "clean3BetCount","",""},
			{ "cleanCall3BetCount","",""},
			{ "clean4BetCount ","",""},
			{ "cleanCall4BetCount ","",""},
			{ "cleanAllinCount ","",""},
			{ "cleanCallAllinCount","",""},
			{ "","",""},
			{ "clean1BetOperCount ","",""},
			{ "cleanCall1BetOperCount ","",""},
			{ "clean2BetOperCount ","",""},
			{ "cleanCall2BetOperCount ","",""},
			{ "clean3BetOperCount ","",""},
			{ "cleanCall3BetOperCount ","",""},
			{ "clean4BetOperCount ","",""},
			{ "cleanCall4BetOperCount ","",""},
			{ "cleanAllinOperCount ","",""},
			{ "cleanCallAllinOperCount","",""},
			{ "","",""}, 
			{ "clean1BetPer","",""},
			{ "cleanCall1BetPer","",""},
			{ "clean2BetPer","",""},
			{ "cleanCall2BetPer","",""},
			{ "clean3BetPer ","",""},
			{ "cleanCall3BetPer ","",""},
			{ "clean4BetPer ","",""},
			{ "cleanCall4BetPer","",""},
			{ "cleanAllinPer ","",""},
			{ "cleanCallAllinPer ","",""},
			{ "","",""}, 
			{ "","","" } };

	// @formatter:on

	private static boolean allBetSizeCountGUI = false;

	static void allBetSizeCountArray(PlayerClean pc) {
		if (!allBetSizeCountGUI) {
			allBetSizeCountGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 18);
			final var frame = new JFrame("Bet count  and bet size by bet type");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(50, 50);
			frame.setPreferredSize(new Dimension(400, 1200));

			final var table = new JTable(dataBets, columnsBets);
			table.setFont(ff);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 22.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 22));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
		}

		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			// dataBets[row][1] = Format.format$(pc.betSizeCountArrayBet[i] );
			// dataBets[row++][2] = Format.format(pc.betSizeCountArrayBet[i] );
			// ++row;
		}
	}

	private static boolean betSizeCountArrayAverageGUI = false;

	static void betSizeCountArrayAveragePot(PlayerClean pc) {
		if (!betSizeCountArrayAverageGUI) {
			betSizeCountArrayAverageGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 18);
			final var frame = new JFrame("AllPlayers bet count and bet size by bet and pot");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(550, 50);
			frame.setPreferredSize(new Dimension(600, 400));

			final var model = new DefaultTableModel(dataBetsPot, columnsBetsPot);
			final var table = new JTable(model);
			table.setFont(ff);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			// Create a custom cell renderer to set the font to bold 22.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 22));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
		}

		/*-
			{ "Preflop           ","","","","","","" ,"",""},
			{ "Bet 1  $          ","","","","","","" ,"",""},
			{ "Bet 1  count  ","","","","","","" ,"",""},
			{ "Bet2  $      ","","","","","","" ,"",""},
			{ "Bet 2  count  ","","","","","","" ,"",""},
			{ "Bet 3  $          ","","","","","","" ,"",""},
			{ "Bet 3  count  ","","","","","","" ,"",""},
			{ "Bet 4  $          ","","","","","","" ,"",""},
			{ "Bet 4  count  ","","","","","","" ,"",""},
			{ "All-in  $          ","","","","","","" ,"",""},
			{ "All-In  count  ","","","","","","" ,"",""},
			{ "                        ","","","","","","" ,"",""},
		
			
			{ "Flop         ","","","","","","" ,"",""},
		 */
		/*- TODO does calculation on the fly but pc gone
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < BETS_MAX; ++j) {
			 
		
				dataBetsPot[row][1] = Format.format$(pc.betSizeCountArrayPotBet(playerData, i, j, 0));
				dataBetsPot[row][2] = Format.format$(pc.betSizeCountArrayPotBet(playerData, i, j, 1));
				dataBetsPot[row][3] = Format.format$(pc.betSizeCountArrayPotBet(playerData, i, j, 2));
				dataBetsPot[row][4] = Format.format$(pc.betSizeCountArrayPotBet(playerData, i, j, 3));
				dataBetsPot[row][5] = Format.format$(pc.betSizeCountArrayPotBet(playerData, i, j, 4));
				dataBetsPot[row][6] = Format.format$(pc.betSizeCountArrayPotBet(playerData, i, j, 5));
				dataBetsPot[row][7] = Format.format$(pc.betSizeCountArrayPotBet(playerData, i, j, 6));
				++row;
				dataBetsPot[row][1] = Format.format(pc.betSizeCountArrayPotBet(playerData, i, j, 0));
				dataBetsPot[row][2] = Format.format(pc.betSizeCountArrayPotBet(playerData, i, j, 1));
				dataBetsPot[row][3] = Format.format(pc.betSizeCountArrayPotBet(playerData, i, j, 2));
				dataBetsPot[row][4] = Format.format(pc.betSizeCountArrayPotBet(playerData, i, j, 3));
				dataBetsPot[row][5] = Format.format(pc.betSizeCountArrayPotBet(playerData, i, j, 4));
				dataBetsPot[row][6] = Format.format(pc.betSizeCountArrayPotBet(playerData, i, j, 5));
				dataBetsPot[row][7] = Format.format(pc.betSizeCountArrayPotBet(playerData, i, j, 6));
				++row;
			}
			++row;
		
		}
		*/
	}

	static void cleanPos(PlayerClean pc) {
		if (!preflopCleanPosGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 18);
			final var frame = new JFrame("Clean Data");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(90, 90);
			frame.setPreferredSize(new Dimension(800, 1400));

			final var table = new JTable(dataCleanPos, columnsClean);
			table.setFont(ff);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(160);
			table.getColumnModel().getColumn(1).setPreferredWidth(60);
			table.getColumnModel().getColumn(2).setPreferredWidth(300);
			// Create a custom cell renderer to set the font to bold 22.
			DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
			renderer.setFont(new Font(Font.SERIF, Font.BOLD, 22));
			// Apply the custom cell renderer to all table cells.
			for (int i = 0; i < table.getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setCellRenderer(renderer);
			}
			frame.add(pane);
			frame.pack();
			frame.setVisible(true);
			preflopCleanPosGUI = true;
		}
		int row = 1;
		for (int i = 0; i < NUM_STREETS; ++i) {
			dataCleanPos[row++][1] = Format.format(pc.clean1BetCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.cleanCall1BetCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.clean2BetCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.cleanCall2BetCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.clean3BetCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.cleanCall3BetCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.clean4BetCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.cleanCall4BetCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.cleanAllinCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.cleanCallAllinCount[i]);
			++row;
			dataCleanPos[row++][1] = Format.format(pc.clean1BetOperCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.cleanCall1BetOperCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.clean2BetOperCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.cleanCall2BetOperCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.clean3BetOperCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.cleanCall3BetOperCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.clean4BetOperCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.cleanCall4BetOperCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.cleanAllinOperCount[i]);
			dataCleanPos[row++][1] = Format.format(pc.cleanCallAllinOperCount[i]);
			++row;
			dataCleanPos[row++][1] = Format.formatPer(pc.clean1BetPer[i]);
			dataCleanPos[row++][1] = Format.formatPer(pc.cleanCall1BetPer[i]);
			dataCleanPos[row++][1] = Format.formatPer(pc.clean2BetPer[i]);
			dataCleanPos[row++][1] = Format.formatPer(pc.cleanCall2BetPer[i]);
			dataCleanPos[row++][1] = Format.formatPer(pc.clean3BetPer[i]);
			dataCleanPos[row++][1] = Format.formatPer(pc.cleanCall3BetPer[i]);
			dataCleanPos[row++][1] = Format.formatPer(pc.clean4BetPer[i]);
			dataCleanPos[row++][1] = Format.formatPer(pc.cleanCall4BetPer[i]);
			dataCleanPos[row++][1] = Format.formatPer(pc.cleanAllinPer[i]);
			dataCleanPos[row++][1] = Format.formatPer(pc.cleanCallAllinPer[i]);
			row += 2;
		}

	}

}
