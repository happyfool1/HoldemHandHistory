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
public class PlayerBoardIndexesReports implements Constants {

	/*-*****************************************************************************************
	  * This class does a bunch of reports using data collected by .
	 * The reports are in a separate class ( this one ) for a couple of reasons:
	 * 		 was just too big.
	 * 		By passing PlayerClassification and Players classes to this class it makes reports
	 * 		for filtered players or player types very simple. No code changes to report methods.
	 * Most of the reports are designed to assist in debug and testing but
	 * are also useful for understanding how the collected and analyzed data can be used to
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
	 ******************************************************************************************/

	/*-******************************************************************************
	Data used for all reports in this Class are in the variables below.
	****************************************************************************** */
// @formatter:off
	/*- ******************************************************************************
	*int[][] betCountsHML = new int[BETS_MAX][HML_SIZE];
	* int[][] foldToBetHML = new int[BETS_MAX][HML_SIZE];
	* int[][] checkFoldHML = new int[BETS_MAX][HML_SIZE];
	* int[][] checkBetHML = new int[BETS_MAX][HML_SIZE];
	* int[][] checkCallBetHML = new int[BETS_MAX][HML_SIZE];
	*******************************************************************************/
	private static boolean betCountsHMLGUI = false;
	/*- ******************************************************************************
	*	For all orbits - Actions
	******************************************************************************	*/
	// @formatter:off
	
	
	private static boolean betCountsWetDryGUI = false;
	/*- ******************************************************************************
	*	For all orbits - Actions
	*******************************************************************************/
	
	private static boolean betCountsStaticDynamicGUI = false;


	private static boolean c_BetHMLGUI = false;
	/*- ******************************************************************************
	*	For all orbits - Actions
	*******************************************************************************/
	private static final Object[] columnsC_BetHML = { "Draw", "Flop" };
	private static final Object[][] dataC_BetHML = { 
			{ "HHH ","","","","","","","","" },
			{ "HHM","","","","","","","","" },
			{ "HHL","","","","","","","","" },
			{ "HMM","","","","","","","","" },
			{ "HML","","","","","","","","" },
			{ "HLL","","","","","","","","" },
			{ "MMM ","","","","","","","","" },
			{ "MML","","","","","","","","" },
			{ "MLL ","","","","","","","","" },
			{ "LLL ","","","","","","","","" },
			{ "","","","","","","","","" } };
	private static boolean c_BetWetDryGUI = false;
	/*- ******************************************************************************
	*	For all orbits - Actions
	*******************************************************************************/
	private static final Object[] columnsC_BetWetDry = { "Draw", "count","" };
	private static final Object[][] dataC_BetWetDry = { { "Wet","","","" },
			{ "Dry","","","" }, { "Neither ","","","" }, { "","","","","" } };
	private static boolean c_BetStaticDynamicGUI = false;
 
	private static final Object[] columnsC_BetStaticDynamic = { "Draw", "Count" };
	private static final Object[][] dataC_BetStaticDynamic = { { "Static","","","" },
			{ "Dynamic ","","","" }, { "Neither ","","","" }, { "","","","","" } };
	private static boolean c_BetDrawGUI = false;
 
	private static final Object[] columnsC_BetDraw = { "Draw", "Flop", "Turn", "River" };
	private static final Object[][] dataC_BetDraw = { { "None","","","","","","","","" },
			{ "2 pair","","","","","","","","" },
			{ "Gutshot","","","","","","","","" },
			{ "Gutshot high ","","","","","","","","" },
			{ "Gutshot pair","","","","","","","","" },
			{ "Flush draw","","","","","","","","" },
			{ "OESD","","","","","","","","" },
			{ "OESD  pair","","","","","","","","" },
			{ "Flush Draw pair","","","","","","","","" },
			{ "Flush draw gutshot","","","","","","","","" },
			{ "Flush draw OESD","","","","","","","","" },
			{ "Straight","","","","","","","","" },
			{ "Flush","","","","","","","","" },
			{ "Full House","","","","","","","","" },
			{ "4 of a kind","","","","","","","","" },
			{ "Straight flush","","","","","","","","" },
			{ "Royal Flush","","","","","","","","" },
			{ "","","","","","","","","" },

			{ "","","","","","","","","" } };
	
	
	private static final Object[] columnsDataHML= { "Data","HHH", "HHM", "HHL", "HMM", "HML", "HLL", "MMM", "MML", "MLL", "LLL" };
 	private static final Object[][] dataDataHML = { 
 	{"Check","","","","","","","","","","","",""},
  	{"betCountsHML","","","","","","","","","","","",""},
	{"foldToBetHML","","","","","","","","","","","",""},
	{"checkFoldHML","","","","","","","","","","","",""},
	{"checkBetHML","","","","","","","","","","","",""},
	{"checkCallBetHML","","","","","","","","","","","",""},
	{"Bet1","","","","","","","","","","","",""},
  	{"betCountsHML","","","","","","","","","","","",""},
	{"foldToBetHML","","","","","","","","","","","",""},
	{"checkFoldHML","","","","","","","","","","","",""},
	{"checkBetHML","","","","","","","","","","","",""},
	{"checkCallBetHML","","","","","","","","","","","",""},
	{"Bet2","","","","","","","","","","","",""},
  	{"betCountsHML","","","","","","","","","","","",""},
  	{"foldToBetHML","","","","","","","","","","","",""},
	{"checkFoldHML","","","","","","","","","","","",""},	 
	{"checkBetHML","","","","","","","","","","","",""},
	{"checkCallBetHML","","","","","","","","","","","",""},
	{"Bet3","","","","","","","","","","","",""},
	{"betCountsHML","","","","","","","","","","","",""},
	{"foldToBetHML","","","","","","","","","","","",""},
	{"checkFoldHML","","","","","","","","","","","",""},
	{"checkBetHML","","","","","","","","","","","",""},
	{"checkCallBetHML","","","","","","","","","","","",""},
	{"Bet4","","","","","","","","","","","",""},
  	{"betCountsHML","","","","","","","","","","","",""},
	{"foldToBetHML","","","","","","","","","","","",""},
	{"checkFoldHML","","","","","","","","","","","",""},
	{"checkBetHML","","","","","","","","","","","",""},
	{"checkCallBetHML","","","","","","","","","","","",""},
	{"All-In","","","","","","","","","","","",""},
  	{"betCountsHML","","","","","","","","","","","",""},
	{"foldToBetHML","","","","","","","","","","","",""},
	{"checkFoldHML","","","","","","","","","","","",""},
	{"checkBetHML","","","","","","","","","","","",""},
	{"checkCallBetHML","","","","","","","","","","","",""},
	{"","","","","","","","","","","","",""} };
	
 	
 	
 	private static final Object[] columnsWetDry= { "Data","Wet","Dry","Neither" };
 	private static final Object[][] dataWetDry = { 
 	{"Check","","","","","","","","","","","",""},
	{"betCountsWetDry","","","","","","","","","","","",""},
	{"foldToBetWetDry","","","","","","","","","","","",""},
	{"checkFoldWetDry","","","","","","","","","","","",""},
	{"checkBetWetDry","","","","","","","","","","","",""},
	{"checkCallBetWetDry","","","","","","","","","","","",""},
	{"Bet1","","","","","","","","","","","",""},
	{"betCountsWetDry","","","","","","","","","","","",""},
	{"foldToBetWetDry","","","","","","","","","","","",""},
	{"checkFoldWetDry","","","","","","","","","","","",""},
	{"checkBetWetDry","","","","","","","","","","","",""},
	{"checkCallBetWetDry","","","","","","","","","","","",""},
	{"Bet2","","","","","","","","","","","",""},
	{"betCountsWetDry","","","","","","","","","","","",""},
	{"foldToBetWetDry","","","","","","","","","","","",""},
	{"checkFoldWetDry","","","","","","","","","","","",""},
	{"checkBetWetDry","","","","","","","","","","","",""},
	{"checkCallBetWetDry","","","","","","","","","","","",""},
	{"Bet3","","","","","","","","","","","",""},
	{"betCountsWetDry","","","","","","","","","","","",""},
	{"foldToBetWetDry","","","","","","","","","","","",""},
	{"checkFoldWetDry","","","","","","","","","","","",""},
	{"checkBetWetDry","","","","","","","","","","","",""},
	{"checkCallBetWetDry","","","","","","","","","","","",""},
	{"Bet4","","","","","","","","","","","",""},
	{"betCountsWetDry","","","","","","","","","","","",""},
	{"foldToBetWetDry","","","","","","","","","","","",""},
	{"checkFoldWetDry","","","","","","","","","","","",""},
	{"checkBetWetDry","","","","","","","","","","","",""},
	{"checkCallBetWetDry","","","","","","","","","","","",""},
	{"All-In","","","","","","","","","","","",""},
	{"betCountsWetDry","","","","","","","","","","","",""},
	{"foldToBetWetDry","","","","","","","","","","","",""},
	{"checkFoldWetDry","","","","","","","","","","","",""},
	{"checkBetWetDry","","","","","","","","","","","",""},
	{"checkCallBetWetDry","","","","","","","","","","","",""},
	{"","","","","","","","","","","","",""} };
 	
 	
 	
 	private static final Object[] columnsStaticDynamic= { "Data","Static","Dynamic","Neither" };
 	private static final Object[][] dataStaticDynamic= { 
 	{"Check","","","","","","","","","","","",""},
	{"betCountsStaticDynamic","","","","","","","","","","","",""},
	{"foldToBetStaticDynamic","","","","","","","","","","","",""},
	{"checkFoldStaticDynamic","","","","","","","","","","","",""},
	{"checkBetStaticDynamic","","","","","","","","","","","",""},
	{"checkCallBetStaticDynamic","","","","","","","","","","","",""},
	{"Bet1","","","","","","","","","","","",""},
 	{"betCountsStaticDynamic","","","","","","","","","","","",""},
 	{"foldToBetStaticDynamic","","","","","","","","","","","",""},
 	{"checkFoldStaticDynamic","","","","","","","","","","","",""},
 	{"checkBetStaticDynamic","","","","","","","","","","","",""},
 	{"checkCallBetStaticDynamic","","","","","","","","","","","",""},
	{"Bet2","","","","","","","","","","","",""},
	{"betCountsStaticDynamic","","","","","","","","","","","",""},
	{"foldToBetStaticDynamic","","","","","","","","","","","",""},
	{"checkFoldStaticDynamic","","","","","","","","","","","",""},
	{"checkBetStaticDynamic","","","","","","","","","","","",""},
	{"checkCallBetStaticDynamic","","","","","","","","","","","",""},
	{"Bet3","","","","","","","","","","","",""},
	{"betCountsStaticDynamic","","","","","","","","","","","",""},
	{"foldToBetStaticDynamic","","","","","","","","","","","",""},
	{"checkFoldStaticDynamic","","","","","","","","","","","",""},
	{"checkBetStaticDynamic","","","","","","","","","","","",""},
	{"checkCallBetStaticDynamic","","","","","","","","","","","",""},
	{"Bet4","","","","","","","","","","","",""},
	{"betCountsStaticDynamic","","","","","","","","","","","",""},
	{"foldToBetStaticDynamic","","","","","","","","","","","",""},
	{"checkFoldStaticDynamic","","","","","","","","","","","",""},
	{"checkBetStaticDynamic","","","","","","","","","","","",""},
	{"checkCallBetStaticDynamic","","","","","","","","","","","",""},
	{"All-In","","","","","","","","","","","",""},
	{"betCountsStaticDynamic","","","","","","","","","","","",""},
	{"foldToBetStaticDynamic","","","","","","","","","","","",""},
	{"checkFoldStaticDynamic","","","","","","","","","","","",""},
	{"checkBetStaticDynamic","","","","","","","","","","","",""},
	{"checkCallBetStaticDynamic","","","","","","","","","","","",""},
	{"","","","","","","","","","","","",""} };
 	
 	
 	
 	
 	
	
 	
 	
	private static final Object[] columnsData = { "Draw", "HHH", "HHM", "HHL", "HMM", "HML", "HLL", "MMM", "MML", "MLL", "LLL" };
	private static final Object[][] dataData  = { 
	{" c_BetCombo","","","","","","","","","","","",""},
	{" c_BetWetDry","","","","","","","","","","","",""},
	{" c_BetStaticDynamic","","","","","","","","","","","",""},
	{"c_BetDraw","","","","","","","","","","","",""},
	{ "","","","","","","","","","","","",""}};
	
	
	// @formatter:on

	private static boolean allActionPosGUI = false;

	static void dataHML(PlayerBoardIndexes pbi) {
		if (!allActionPosGUI) {
			allActionPosGUI = true;
			final var frame = new JFrame("Data HML");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(50, 50);
			frame.setPreferredSize(new Dimension(800, 950));
			final var table = new JTable(dataDataHML, columnsDataHML);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
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
			allActionPosGUI = true;
		}
		int saveRow = 1;
		int row = 1;
		int col = 1;
		for (int i = 0; i < BETS_MAX; ++i) {
			for (int j = 0; j < HML_SIZE; ++j) {
				row = saveRow;
				dataDataHML[row++][col] = Format.format(pbi.betCountsHML[i][j]);
				dataDataHML[row++][col] = Format.format(pbi.foldToBetHML[i][j]);
				dataDataHML[row++][col] = Format.format(pbi.checkFoldHML[i][j]);
				dataDataHML[row++][col] = Format.format(pbi.checkBetHML[i][j]);
				dataDataHML[row][col++] = Format.format(pbi.checkCallBetHML[i][j]);
			}
			col = 1;
			row += 2;
			saveRow = row;
		}
	}

	/*-******************************************************************************
	 * 
	 *******************************************************************************/
	static void betCountsWetDry(PlayerBoardIndexes pbi) {
		if (!betCountsWetDryGUI) {
			betCountsWetDryGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 10);
			final var frame = new JFrame("WetDry  bet counts ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(80, 80);
			frame.setPreferredSize(new Dimension(600, 900));
			final var table = new JTable(dataWetDry, columnsWetDry);
			table.setFont(ff);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			table.setRowHeight(30);
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
		int saveRow = 1;
		int row = 1;
		int col = 1;
		for (int i = 0; i < BETS_MAX; ++i) {
			for (int j = 0; j < WETDRY_MAX; ++j) {
				row = saveRow;
				dataWetDry[row++][col] = Format.format(pbi.betCountsWetDry[i][j]);
				dataWetDry[row++][col] = Format.format(pbi.foldToBetWetDry[i][j]);
				dataWetDry[row++][col] = Format.format(pbi.foldToBetWetDry[i][j]);
				dataWetDry[row++][col] = Format.format(pbi.checkFoldWetDry[i][j]);
				dataWetDry[row][col++] = Format.format(pbi.checkCallBetWetDry[i][j]);
			}
			col = 1;
			row += 2;
			saveRow = row;
		}
	}

	/*-******************************************************************************
	 * 
	 *******************************************************************************/
	static void betCountsStaticDynamic(PlayerBoardIndexes pbi) {
		if (!betCountsStaticDynamicGUI) {
			betCountsStaticDynamicGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 10);
			final var frame = new JFrame("StaticDynamic   bet counts ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(1000, 500);
			frame.setPreferredSize(new Dimension(500, 400));
			final var table = new JTable(dataStaticDynamic, columnsStaticDynamic);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			table.setRowHeight(30);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
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
		int saveRow = 1;
		int row = 1;
		int col = 1;
		for (int i = 0; i < BETS_MAX; ++i) {
			for (int j = 0; j < STATICDYNAMIC_MAX; ++j) {
				row = saveRow;
				dataStaticDynamic[row++][col] = Format.format(pbi.betCountsStaticDynamic[i][j]);
				dataStaticDynamic[row++][col] = Format.format(pbi.foldToBetStaticDynamic[i][j]);
				dataStaticDynamic[row++][col] = Format.format(pbi.checkFoldStaticDynamic[i][j]);
				dataStaticDynamic[row++][col] = Format.format(pbi.checkBetStaticDynamic[i][j]);
				dataStaticDynamic[row][col++] = Format.format(pbi.checkCallBetStaticDynamic[i][j]);
			}
			col = 1;
			row += 2;
			saveRow = row;
		}
	}

	private static boolean betCountsDrawsGUI = false;

	/*-******************************************************************************
	 * 
	 *******************************************************************************/
	static void c_BetHML(PlayerBoardIndexes pbi) {
		if (!c_BetHMLGUI) {
			c_BetHMLGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 10);
			final var frame = new JFrame("c-Bet   HML ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(500, 50);
			frame.setPreferredSize(new Dimension(500, 400));
			final var table = new JTable(dataC_BetHML, columnsC_BetHML);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			table.setRowHeight(30);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
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
		for (int j = 0; j < HML_SIZE; ++j) {
			dataC_BetHML[row][1] = Format.format(pbi.c_BetHML[j]);
			++row;
		}
	}

	static void c_BetWetDry(PlayerBoardIndexes pbi) {
		if (!c_BetWetDryGUI) {
			c_BetWetDryGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 10);
			final var frame = new JFrame("c-Bet WetDry ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(1000, 50);
			frame.setPreferredSize(new Dimension(500, 200));
			final var table = new JTable(dataC_BetWetDry, columnsC_BetWetDry);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			table.setRowHeight(30);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
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
		for (int j = 0; j < WETDRY_MAX; ++j) {
			dataC_BetWetDry[row][1] = Format.format(pbi.c_BetWetDry[j]);
			++row;
		}
	}

	static void c_BetStaticDynamic(PlayerBoardIndexes pbi) {
		if (!c_BetStaticDynamicGUI) {
			c_BetStaticDynamicGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 10);
			final var frame = new JFrame("c-Bet StaticDynamic ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(1000, 250);
			frame.setPreferredSize(new Dimension(500, 200));
			final var table = new JTable(dataC_BetStaticDynamic, columnsC_BetStaticDynamic);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			table.setRowHeight(30);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
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
		for (int j = 0; j < STATICDYNAMIC_MAX; ++j) {
			dataC_BetStaticDynamic[row][1] = Format.format(pbi.c_BetStaticDynamic[j]);
			++row;
		}
	}

	/*-******************************************************************************
	 * 
	 *******************************************************************************/
	static void c_BetDraws(PlayerBoardIndexes pbi) {
		if (!c_BetDrawGUI) {
			c_BetDrawGUI = true;
			final var ff = new Font(Font.SERIF, Font.BOLD, 10);
			final var frame = new JFrame(" c-Bet  Draw ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(50, 50);
			frame.setPreferredSize(new Dimension(400, 550));
			final var table = new JTable(dataC_BetDraw, columnsC_BetDraw);
			table.setFont(ff);
			table.setRowHeight(25);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
			table.setRowHeight(30);
			table.getColumnModel().getColumn(0).setPreferredWidth(120);
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
		for (int j = 0; j < POSSIBLE_MAX; ++j) {
			dataC_BetDraw[row][1] = Format.format(pbi.c_BetDraw[0][j]);
			dataC_BetDraw[row][2] = Format.format(pbi.c_BetDraw[1][j]);
			dataC_BetDraw[row][3] = Format.format(pbi.c_BetDraw[2][j]);
			++row;
		}
	}

}
