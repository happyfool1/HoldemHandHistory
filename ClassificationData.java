package holdemhandhistory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ClassificationData implements java.io.Serializable, Constants {

	/*- *****************************************************************************
	* A data holding class. Data can be edited.
	* Used in the process of classifying players.
	* 
	*  @author PEAK_ 
	***************************************************************************** */

	private JFrame frame;
	private JTable table = new JTable();
	private JPanel pane = new JPanel();
	DefaultTableModel tableModel = null;

	private JFrame frame2;
	private JTable table2 = new JTable();
	private JPanel pane2 = new JPanel();
	DefaultTableModel tableModel2 = null;

	// @formatter:off
	private   Object[] columns=
	 { "Type      ", "vpip low","vpip high", "pfr low","pfr high","aggression% low","aggression% high","wtsd low","wtsd high","Win Rate bb/100 low","Win Rate bb/100 high","Hands played"};
	 
	private   Object[][] data  =  {
			{ "Nit   ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "Lag   ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "Tag   ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "Shark   ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "Fish   ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "Reg   ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "Average   ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "Hero  ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "Winner ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "Looser  ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "User defined 1   ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "User defined 2   ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "User defined 3   ", "", "", "", "", "", "", "", "","" ,"",""},
			{ "User defined 4   ", "", "", "", "", "", "", "", "","" ,"",""},
		};
 
	private   Object[] columnsP=
		 { "Value      ", "PreflopLow","Preflop High", "Flop Low","Flop High", "Turn Low","Turn High", "River Low","River High", "Showdown Low","Showdown High"};
	static final JCheckBox afStreet = new JCheckBox("Agg Factor");

	private static final JCheckBox aggPctStreet = new JCheckBox("Agg Percent");

	private static final JCheckBox threeBetStreet = new JCheckBox("3 Bet Freq");

	private static final JCheckBox cBetBetStreet = new JCheckBox("C-Bet Freq");

	private static final JCheckBox cBetBetFoldToStreet = new JCheckBox("C-Bet Fold to");

	private static final JCheckBox wtsdStreet = new JCheckBox("Went to Showdown");

	private static final JCheckBox rfiStreet = new JCheckBox("Raise first in");

	private static final JCheckBox donkBetStreet = new JCheckBox("Donk Bet");

	private static final JCheckBox foldPerStreet = new JCheckBox("Fold Percentage");

	private static final JCheckBox continuePerStreet = new JCheckBox("Continue Percentage");

	private static final JCheckBox mdfPerStreet = new JCheckBox("MDF Percentage");
		private   Object[][] dataP  =  {
				{ "Win Rate BB/100  ", " ", " ", " ", " ", " ", " ", " ", "","" },
				{ "VPIP ", " ", " ", " ", " ", " ", " ", " ", "","" },
				{ "Agg Factor   ", " ", " ", " ", " ", " ", " ", " ", "","" },
				{ "3 Bet Freq", " ", " ", " ", " ", " ", " ", " ", "","" }, 
				{ "C-Bet Freq", " ", " ", " ", " ", " ", " ", " ", "","" },
				{ "C-Bet Fold to", " ", " ", " ", " ", " ", " ", " ", "","" },
				{ "Went to Showdown", " ", " ", " ", " ", " ", " ", " ", "","" },
				{ "Raise first in", " ", " ", " ", " ", " ", " ", " ", "","" },
				{ "Donk Bet", " ", " ", " ", " ", " ", " ", " ", "","" },
				{ "Fold Percentage", " ", " ", " ", " ", " ", " ", " ", "","" },
				{ "Continue Percentage   ", " ", " ", " ", " ", " ", " ", " ", "","" },
				{ "MDF Percentage", " ", " ", " ", " ", " ", " ", " ", "","" },
						};
	 
	
	// @formatter:on

	/*- **************************************************************************** 
	*  Displays a report of all data collected by Analyze.
	 * All data is in EvalData.both
	 * 
	 * Arg0 - Column for frame
	 * Arg1 - Row for frame
	*******************************************************************************/
	void editData(int c, int r) {
		final var ff = new Font(Font.SERIF, Font.BOLD, 14);
		JScrollPane pane = null;
		DefaultTableModel tableModel = null;
		JPanel panel0 = new JPanel();
		// Check if the display window already exists
		if (frame == null) {
			frame = new JFrame(String.valueOf(""));
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setLocation(c, r);
			frame.setPreferredSize(new Dimension(1000, 700));
			frame.setTitle("Edit Classification Data");
			tableModel = new DefaultTableModel(data, columns);
			table = new JTable(tableModel);
			table.setFont(ff);
			table.setRowHeight(25);
			pane = new JScrollPane(table);
			table.getColumnModel().getColumn(0).setPreferredWidth(140);
			table.setBackground(IVORY);
			panel0.add(pane);
			frame.add(panel0);
			frame.pack();
			frame.setVisible(true);
		} else {
			// Update existing window content
			frame.setTitle("Edit Classification Data");
			table.setModel(new DefaultTableModel(data, columns));
			table.setBackground(IVORY);
			frame.pack();
			frame.setVisible(true);
		}
		JButton buttonSave = new JButton("Save");
		JPanel panel = new JPanel();
		panel.setBackground(Color.gray);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		buttonSave.setBackground(Color.green);
		buttonSave.addActionListener(new Listener());
		buttonSave.setFont(ff);
		panel0.add(buttonSave);

		int row = 0;
		int col = 1;
		table.setValueAt(Format.format(nit_vpip_low), row, col++);
		table.setValueAt(Format.format(nit_vpip_high), row, col++);
		table.setValueAt(Format.format(nit_pfr_low), row, col++);
		table.setValueAt(Format.format(nit_pfr_high), row, col++);
		table.setValueAt(Format.format(nit_agg_pct_low), row, col++);
		table.setValueAt(Format.format(nit_agg_pct_high), row, col++);
		table.setValueAt(Format.format(nit_wtsd_low), row, col++);
		table.setValueAt(Format.format(nit_wtsd_high), row, col++);
		table.setValueAt(Format.format(nit_win_rate_low), row, col++);
		table.setValueAt(Format.format(nit_win_rate_high), row, col++);
		table.setValueAt(Format.format(nit_hands_played), row, col++);
		++row;
		col = 1;
		table.setValueAt(Format.format(lag_vpip_low), row, col++);
		table.setValueAt(Format.format(lag_vpip_high), row, col++);
		table.setValueAt(Format.format(lag_pfr_low), row, col++);
		table.setValueAt(Format.format(lag_pfr_high), row, col++);
		table.setValueAt(Format.format(lag_agg_pct_low), row, col++);
		table.setValueAt(Format.format(lag_agg_pct_high), row, col++);
		table.setValueAt(Format.format(lag_wtsd_low), row, col++);
		table.setValueAt(Format.format(lag_wtsd_high), row, col++);
		table.setValueAt(Format.format(lag_win_rate_low), row, col++);
		table.setValueAt(Format.format(lag_win_rate_high), row, col++);
		table.setValueAt(Format.format(lag_hands_played), row, col++);
	}

	/*- **************************************************************************** 
	*  Displays a report of all data collected by Analyze.
	 * All data is in EvalData.both
	 * 
	 * Arg0 - Column for frame
	 * Arg1 - Row for frame
	*******************************************************************************/
	void editCharacteristic(int c, int r) {
		final var ff = new Font(Font.SERIF, Font.BOLD, 14);
		JScrollPane pane2 = null;
		DefaultTableModel tableModel = null;
		JPanel panel0 = new JPanel();
		// Check if the display window already exists
		if (frame2 == null) {
			frame2 = new JFrame(String.valueOf(""));
			frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame2.setLocation(c, r);
			frame2.setPreferredSize(new Dimension(1000, 700));
			frame2.setTitle("Edit Classification Data");
			tableModel2 = new DefaultTableModel(data, columns);
			table2 = new JTable(tableModel2);
			table2.setFont(ff);
			table2.setRowHeight(25);
			pane2 = new JScrollPane(table2);
			table2.getColumnModel().getColumn(0).setPreferredWidth(140);
			table2.setBackground(IVORY);
			panel0.add(pane);
			frame2.add(panel0);
			frame2.pack();
			frame2.setVisible(true);
		} else {
			// Update existing window content
			frame2.setTitle("Edit Classification Data");
			table2.setModel(new DefaultTableModel(data, columns));
			table2.setBackground(IVORY);
			frame2.pack();
			frame2.setVisible(true);
		}
		JButton buttonSave = new JButton("Save");
		JPanel panel = new JPanel();
		panel.setBackground(Color.gray);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		buttonSave.setBackground(Color.green);
		buttonSave.addActionListener(new Listener());
		buttonSave.setFont(ff);
		panel0.add(buttonSave);

		int row = 0;
		int col = 1;

		++row;
		col = 1;

	}

	/*- **************************************************************************** 
	*  Save edited data
	****************************************************************************  */
	private void saveData() {
		int row = 0;
		int col = 1;
		nit_vpip_low = Double.parseDouble(table.getValueAt(row, col++).toString());
		nit_vpip_high = Double.parseDouble(table.getValueAt(row, col++).toString());
		nit_pfr_low = Double.parseDouble(table.getValueAt(row, col++).toString());
		nit_pfr_high = Double.parseDouble(table.getValueAt(row, col++).toString());
		nit_agg_pct_low = Double.parseDouble(table.getValueAt(row, col++).toString());
		nit_agg_pct_high = Double.parseDouble(table.getValueAt(row, col++).toString());
		nit_wtsd_low = Double.parseDouble(table.getValueAt(row, col++).toString());
		nit_win_rate_high = Double.parseDouble(table.getValueAt(row, col++).toString());
		nit_win_rate_low = Double.parseDouble(table.getValueAt(row, col++).toString());
		nit_wtsd_high = Double.parseDouble(table.getValueAt(row, col++).toString());

		nit_hands_played = Integer.parseInt(table.getValueAt(row, col++).toString());
		++row;
		col = 1;
		lag_vpip_low = Double.parseDouble(table.getValueAt(row, col++).toString());
		lag_vpip_high = Double.parseDouble(table.getValueAt(row, col++).toString());
		lag_pfr_low = Double.parseDouble(table.getValueAt(row, col++).toString());
		lag_pfr_high = Double.parseDouble(table.getValueAt(row, col++).toString());
		lag_agg_pct_low = Double.parseDouble(table.getValueAt(row, col++).toString());
		lag_agg_pct_high = Double.parseDouble(table.getValueAt(row, col++).toString());
		lag_wtsd_low = Double.parseDouble(table.getValueAt(row, col++).toString());
		lag_wtsd_high = Double.parseDouble(table.getValueAt(row, col++).toString());
		lag_win_rate_low = Double.parseDouble(table.getValueAt(row, col++).toString());
		lag_win_rate_high = Double.parseDouble(table.getValueAt(row, col++).toString());
		lag_hands_played = Integer.parseInt(table.getValueAt(row, col++).toString());

		writeToFile("C:\\HHPlayerClassification\\ClassificationData.ser");
	}

	/*- ***********************************************************************************************-
	 *  Responds to button clicks. 
	***********************************************************************************************  */
	class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a0) {

			switch (a0.getActionCommand()) {
			case "Exit":
				break;
			case "Save":
				saveData();
				break;
			}
		}
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
	 * Read a Range from a disk file. 
	 * Arg0 - The full path name.
	 * Returns null if error
	***************************************************************************** */
	ClassificationData readFromFile(String path) {
		ClassificationData r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (ClassificationData) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

	/*-***********************************************************************************************
	 * 
	*********************************************************************************************** */

	/*-***********************************************************************************************
	 * Player classification - FISH, NIT, LAG, TAG, REG, AVERAGE, .....
	* Other characteristics of a single player.
	* See Class PlayerClassification and Class Players
	*********************************************************************************************** */
	double nit_vpip_low = 0;
	double nit_vpip_high = 12.;
	double nit_pfr_low = 0.;
	double nit_pfr_high = 99.;
	double nit_agg_pct_low = 16.;
	double nit_agg_pct_high = 100.;
	double nit_wtsd_low = 99.;
	double nit_wtsd_high = 99.;
	double nit_win_rate_low = 99.;
	double nit_win_rate_high = 99.;
	int nit_hands_played = 360;

	double lag_vpip_low = 25;
	double lag_vpip_high = 55;
	double lag_pfr_low = 20;
	double lag_pfr_high = 35;
	double lag_agg_pct_low = 16;
	double lag_wtsd_low = 99.;
	double lag_wtsd_high = 99.;
	double lag_agg_pct_high = 100;
	double lag_win_rate_low = 99.;
	double lag_win_rate_high = 99.;
	int lag_hands_played = 360;

	double tag_vpip_low = 12;
	double tag_vpip_high = 18;
	double tag_pfr_low = 10;
	double tag_pfr_high = 16;
	double tag_agg_pct_low = 16;
	double tag_agg_pct_high = 100;
	double tag_wtsd_low = 99.;
	double tag_wtsd_high = 99.;
	double tag_win_rate_Low = 99.;
	double tag_win_rate_high = 99.;
	int tag_hands_played = 360;

	double shark_vpip_low = 15;
	double shark_vpip_high = 22;
	double shark_pfr_low = 0;
	double shark_pfr_high = 0;
	double shark_agg_pct_low = 0;
	double shark_agg_pct_high = 100;
	double shark_wtsd_low = 99.;
	double shark_wtsd_high = 99.;
	double shark_win_rate_Low = 99.;
	double shark_win_rate_high = 99.;
	int shark_hands_played = 360;

	double fish_vpip_low = 25.;
	double fish_vpip_high = 100.;
	double fish_pfr_low = 0.;
	double fish_pfr_high = 10.;
	double fish_agg_pct_low = 0.;
	double fish_agg_pct_high = 100.;
	double fish_wtsd_low = 25.;
	double fish_wtsd_high = 100.;
	double fish_win_rate_Low = 99.;
	double fish_win_rate_high = 99.;
	int fish_hands_played = 360;

	double reg_vpip_low = 15;
	double reg_vpip_high = 22;
	double reg_pfr_low = 0;
	double reg_pfr_high = 0;
	double reg_agg_pct_low = 0;
	double reg_agg_pct_high = 100;
	double reg_wtsd_low = 99.;
	double reg_wtsd_high = 99.;
	double reg_win_rate_Low = 99.;
	double reg_win_rate_high = 99.;
	int reg_hands_played = 360;

	double average_vpip_low = 0;
	double average_vpip_high = 0;
	double average_pfr_low = 0;
	double average_pfr_high = 0;
	double average_agg_pct_low = 0;
	double average_agg_pct_high = 100;
	double average_wtsd_low = 99.;
	double average_wtsd_high = 99.;
	double average_win_rate_Low = 99.;
	double nit_average_rate_high = 99.;
	int average_hands_played = 360;

	double hero_vpip_low = 15;
	double hero_vpip_high = 22;
	double hero_pfr_low = 0;
	double hero_pfr_high = 0;
	double hero_agg_pct_low = 0;
	double hero_agg_pct_high = 100;
	double hero_wtsd_low = 99.;
	double hero_wtsd_high = 99.;
	double hero_win_rate_Low = 99.;
	double hero_win_rate_high = 99.;
	int hero_hands_played = 360;

	double winner_vpip_low = 15;
	double winner_vpip_high = 22;
	double winner_pfr_low = 0;
	double winner_pfr_high = 0;
	double winner_agg_pct_low = 0;
	double winner_agg_pct_high = 100;
	double winner_wtsd_low = 99.;
	double winner_wtsd_high = 99.;
	double winner_win_rate_Low = 99.;
	double winner_win_rate_high = 99.;
	int winner_hands_played = 360;

	double looser_vpip_low = 15;
	double looser_vpip_high = 22;
	double looser_pfr_low = 0;
	double looser_pfr_high = 0;
	double looser_agg_pct_low = 0;
	double looser_agg_pct_high = 100;
	double looser_wtsd_low = 99.;
	double looser_wtsd_high = 99.;
	double looser_win_rate_Low = 99.;
	double looser_win_rate_high = 99.;
	int looser_hands_played = 360;

	double userDefined1_vpip_low = 0;
	double userDefined1_vpip_high = 0;
	double userDefined1_pfr_low = 0;
	double userDefined1_pfr_high = 0;
	double userDefined1_agg_pct_low = 0;
	double userDefined1_agg_pct_high = 0;
	double userDefined1_wtsd_low = 99.;
	double userDefined1_wtsd_high = 99.;
	double userDefined1_win_rate_Low = 99.;
	double userDefined1_win_rate_high = 99.;
	int userDefined1_hands_played = 360;

	double userDefined2_vpip_high = 0;
	double userDefined2_pfr_low = 0;
	double userDefined2_pfr_high = 0;
	double userDefined2_agg_pct_low = 0;
	double userDefined2_agg_pct_high = 0;
	double userDefined2_wtsd_low = 99.;
	double userDefined2_wtsd_high = 99.;
	double userDefined2_win_rate_Low = 99.;
	double userDefined2_win_rate_high = 99.;
	int userDefined2_hands_played = 360;

	double userDefined3_vpip_high = 0;
	double userDefined3_pfr_low = 0;
	double userDefined3_pfr_high = 0;
	double userDefined3_agg_pct_low = 0;
	double userDefined3_agg_pct_high = 0;
	double userDefined3_wtsd_low = 99.;
	double userDefined3_wtsd_high = 99.;
	double userDefined3_win_rate_Low = 99.;
	double userDefined3_win_rate_high = 99.;
	int userDefined3_hands_played = 360;

	double userDefined4_vpip_high = 0;
	double userDefined4_pfr_low = 0;
	double userDefined4_pfr_high = 0;
	double userDefined4_agg_pct_low = 0;
	double userDefined4_agg_pct_high = 0;
	double userDefined4_wtsd_low = 99.;
	double userDefined4_wtsd_high = 99.;
	double userDefined4_win_rate_Low = 99.;
	double userDefined4_win_rate_high = 99.;
	int userDefined4_hands_played = 360;

	double[] vpipLow = new double[] { 0, 0, 0, 0, 0, 0, 0 };
	double[] vpipHigh = new double[] { 0, 0, 0, 0, 0, 0, 0 };

	/*-*****************************************************************************
	 * Default values to restore to starting point
	 ******************************************************************************/
	class Default implements java.io.Serializable, Constants {

	}

}
