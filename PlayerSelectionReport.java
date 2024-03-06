package holdemhandhistory;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/*-*****************************************************************************************
 * This class does a bunch of reports for GUIPlayerSelection.
 * The firstv step in Player Selection is to choose an input folder then to do a first pass 
 * evaluation of the data in that folder.  The results of that are displayed here by yhis Class.
 * The next step is to start selecting Characteristics that will be used in the final selection.
 * It can be a very itterative process or can be simple as selecting a player type like Shark;
 * 
 * 
 * @author PEAK_
 ******************************************************************************************/
public class PlayerSelectionReport implements Constants {

	/*-************************************************************************************************
	* Data for selection
	************************************************************************************************ */
// From Players
	static int[] numPlayers = { 0, 0, 0, 0, 0, 0 };
	static int[] played = { 0, 0, 0, 0, 0, 0 };
	static int[] playedID = { 0, 0, 0, 0, 0, 0 };
	static double[] wonShowdownPer = { 0, 0, 0, 0, 0, 0 };
	static int[] wonShowdownPerID = { 0, 0, 0, 0, 0, 0 };
	static double[] wonPer = { 0, 0, 0, 0, 0, 0 };
	static int[] wonPerID = { 0, 0, 0, 0, 0, 0 };
	static double[] wsdPer = { 0, 0, 0, 0, 0, 0 };
	static double[] wtsdPreflopPer = { 0, 0, 0, 0, 0, 0 };
	static double[] wtsdFlopPer = { 0, 0, 0, 0, 0, 0 };
	static double[] wtsdTurnPer = { 0, 0, 0, 0, 0, 0 };
	static double[] wtsdRiverPer = { 0, 0, 0, 0, 0, 0 };
	static double[] wwspPer = { 0, 0, 0, 0, 0, 0 };
	static double[] wwsfPer = { 0, 0, 0, 0, 0, 0 };
	static double[] wwstPer = { 0, 0, 0, 0, 0, 0 };
	static double[] wwsrPer = { 0, 0, 0, 0, 0, 0 };
	static double[] showDownPer = { 0, 0, 0, 0, 0, 0 };
	static double[] showdownWonPer = { 0, 0, 0, 0, 0, 0 };
	static double[] raise1Per = { 0, 0, 0, 0, 0, 0 };
	static double[] bet1Per = { 0, 0, 0, 0, 0, 0 };
	// From PlayerCharacteristics
	static double[] preflopWinRate = { 0, 0, 0, 0, 0, 0 };
	static double[] vpip = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	static double[] preflopVPIP = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	static double[] aggPct = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	static double[] af = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	static double[] pfr = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	static double[] wtsdIfSawFlop = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	static double[] wtsdIfSawTurn = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	static double[] wtsdIfSawRiver = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	static double[] winRateBB100 = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	static double[] preflopBet3Freq = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	static double[] showdownPercentage = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 };
	// @formatter:off
	private static final Object[] columnsValues  = { "Value","1","2","3","4","5","6"};
	private static final Object[][] dataValues= { 
			{"hands Played","","","","","",""},  
			{"wonShowdown Per","","","","","",""},  
			{"won Per","","","","","",""},  
			{"wsd Per ","","","","","",""},  
			{"wsd Per ","","","","","",""},  
 			{"wtsd Preflop Per","","","","","",""},   
			{"wtsd Flop Per","","","","","",""},   
			{"wtsd Turn Per","","","","","",""},   
			{"wtsd River Per","","","","","",""},   
			{" wwsp Per","","","","","",""},   
			{" wwsf Per","","","","","",""},   
			{" wwst Per","","","","","",""},   
			{" wwsr Per","","","","","",""},   
			{" wwsp Per","","","","","",""},   
			{"showDownPer","","","","","",""},   
			{"showdownWonPer","","","","","",""},   
			{"showdownLostPer","","","","","",""},   
			{"raisePer1","","","","","",""},   
			{"betPer1","","","","","",""},   
			{"preflopWinRate","","","","","",""},   
			{"vpip","","","","","",""},   
			{"aggPct","","","","","",""},   
			{"af","","","","","",""},   
			{"pfr","","","","","",""},   
			{"wtsdIfSawFlop","","","","","",""},   
			{"wtsdIfSawTurn","","","","","",""},   
			{"wtsdIfSawRiver","","","","","",""},   
			{"winRateBB100","","","","","",""},   
			{"preflopBet3Freq","","","","","",""},   
			{"showdownPercentage","","","","","",""},   
			{"","","","","","",""},   
			{"","","","","","",""},   
			{"","","","","","",""},   
			{"","","","","","",""},   
			{"","","","","","",""},   
			{"","","","","","",""},   
			{"","","","","","",""},   
	
			
			{"","","","","","",""}};
	
			// @formatter:on
	private static boolean valuesGUI = false;

	static void values() {
		if (!valuesGUI) {
			final var ff = new Font(Font.SERIF, Font.BOLD, 16);
			final var frame = new JFrame("Values ");
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(700, 50);
			frame.setPreferredSize(new Dimension(800, 1250));
			final var table = new JTable(dataValues, columnsValues);
			table.setFont(ff);
			table.setRowHeight(30);
			final var pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(100);
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
			valuesGUI = true;
		}
		int row = 0;
		for (int i = 0; i < 6; i++) {
			dataValues[row++][i + 1] = Format.format(played[i]);
			dataValues[row++][i + 1] = Format.format(playedID[i]);
			dataValues[row++][i + 1] = Format.formatPer(wonShowdownPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(wonPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(wsdPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(wtsdPreflopPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(wtsdPreflopPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(wtsdFlopPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(wtsdTurnPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(wtsdRiverPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(wwspPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(wwsfPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(wwstPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(wwsrPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(showDownPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(showdownWonPer[i]);
			dataValues[row++][i + 1] = Format.formatPer(raise1Per[i]);
			dataValues[row++][i + 1] = Format.formatPer(bet1Per[i]);

			dataValues[row++][i + 1] = Format.formatPer(preflopWinRate[i]);
			dataValues[row++][i + 1] = Format.formatPer(vpip[i]);
			dataValues[row++][i + 1] = Format.formatPer(preflopVPIP[i]);
			dataValues[row++][i + 1] = Format.formatPer(aggPct[i]);
			dataValues[row++][i + 1] = Format.format(af[i]);
			dataValues[row++][i + 1] = Format.formatPer(pfr[i]);
			dataValues[row++][i + 1] = Format.formatPer(wtsdIfSawFlop[i]);
			dataValues[row++][i + 1] = Format.formatPer(wtsdIfSawTurn[i]);
			dataValues[row++][i + 1] = Format.formatPer(wtsdIfSawRiver[i]);
			dataValues[row++][i + 1] = Format.format(winRateBB100[i]);
			dataValues[row++][i + 1] = Format.formatPer(preflopBet3Freq[i]);
			dataValues[row++][i + 1] = Format.formatPer(showdownPercentage[i]);
			row = 0;
		}
	}
}
