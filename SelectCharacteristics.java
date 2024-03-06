package holdemhandhistory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SelectCharacteristics implements Constants {

	private static final ClassificationData rules = new ClassificationData();

	private static final HashMap<Integer, PlayerCharacteristics> playerCharacteristicsMap = new HashMap<>(50000);
	private static PlayerCharacteristics onePlayerCharacteristics;

	private static StringBuilder description; // Description of selected players file
	private static int playerType = -1;

	/*- ******************************************************************************************** 
	 * Array of playerIDs    
	 * Used to select or exclude players.
	* @author PEAK
	********************************************************************************************** */

	private static final JPanel panelRadio = new JPanel();
	private static final JPanel panelCheckCharacteristic = new JPanel();
	private static final JPanel panelCheckStreetOptions = new JPanel();
	private static final JPanel panelCheckStreet = new JPanel();
	private static final JPanel panelControl = new JPanel();
	private static final JPanel panelControl2 = new JPanel();

	/*-************************************************************************************************
	 * Panels
	************************************************************************************************ */
	private static final JPanel panel0 = new JPanel();
	private static final JPanel panel12 = new JPanel();
	private static final JPanel panelBlank = new JPanel();

	private static final JCheckBox crash = new JCheckBox("Crash on severe error");
	private static final JPanel panelCheck = new JPanel();
	/*-************************************************************************************************
	 * Selection type
	************************************************************************************************ */
	private static final JCheckBox selectByType = new JCheckBox("Type");
	private static final JCheckBox selectByCharacteristic = new JCheckBox("Characteristic");
	private static final JCheckBox selectByStreet = new JCheckBox("By Street");

	/*-************************************************************************************************
	 * Player Type
	************************************************************************************************ */
	private static final JRadioButton all = new JRadioButton("All");
	private static final JRadioButton average = new JRadioButton("Average");
	private static final JRadioButton fish = new JRadioButton("Fish");
	private static final JRadioButton nit = new JRadioButton("Nit");
	private static final JRadioButton lag = new JRadioButton("Lag");
	private static final JRadioButton tag = new JRadioButton("Tag ");
	private static final JRadioButton shark = new JRadioButton("Shark");
	private static final JRadioButton hero = new JRadioButton("Hero");
	private static final JRadioButton winner = new JRadioButton("Winner");
	private static final JRadioButton looser = new JRadioButton("Looser");
	private static final JRadioButton user1 = new JRadioButton("User 1");
	private static final JRadioButton user2 = new JRadioButton("User 2");
	private static final JRadioButton user3 = new JRadioButton("User 3");
	private static final JRadioButton user4 = new JRadioButton("User 4");

	/*-************************************************************************************************
	 * Characteristic - not by street
	* 	double preflopVPIP = 0;
	*  	double preflopPFR = 0;
	************************************************************************************************ */
	private static final JCheckBox preflopRaise = new JCheckBox("Preflop Raise");
	private static final JCheckBox agg_pct = new JCheckBox("Aggression");
	private static final JCheckBox a = new JCheckBox("Aggression Factor");
	private static final JCheckBox wtsd = new JCheckBox("Went to Show Down");

	/*-************************************************************************************************
	 *Street for property by street
	************************************************************************************************ */
	private static final JCheckBox preflop = new JCheckBox("Preflop");
	private static final JCheckBox flop = new JCheckBox("Flop");
	private static final JCheckBox turn = new JCheckBox("Turn");
	private static final JCheckBox river = new JCheckBox("River");
	private static final JCheckBox showdown = new JCheckBox("Show Down");

	/*-************************************************************************************************
	 * Characteristic by street
	************************************************************************************************ */
	private static final JCheckBox winRateStreet = new JCheckBox("Win rate");
	private static final JCheckBox vpipStreet = new JCheckBox("VPIP");
	static final JCheckBox afStreet = new JCheckBox("Agg Factor");
	private static final JCheckBox aggPctStreet = new JCheckBox("Agg Percent");
	private static final JCheckBox threeBetStreet = new JCheckBox("3 Bet Freq");
	private static final JCheckBox cBetBetStreet = new JCheckBox("C-Bet Freq");
	private static final JCheckBox cBetBetFoldToStreet = new JCheckBox("C-Bet Fold to");
	private static final JCheckBox wtsdStreet = new JCheckBox("Went to Show Down");
	private static final JCheckBox rfiStreet = new JCheckBox("Raise first in");
	private static final JCheckBox donkBetStreet = new JCheckBox("Donk Bet");
	private static final JCheckBox foldPerStreet = new JCheckBox("Fold Percentage");
	private static final JCheckBox continuePerStreet = new JCheckBox("Continue Percentage");
	private static final JCheckBox mdfPerStreet = new JCheckBox("MDF Percentage");

	private static boolean allOption = false;
	private static boolean nitOption = false;
	private static boolean lagOption = false;
	private static boolean tagOption = false;
	private static boolean averageOption = false;
	private static boolean fishOption = false;
	private static boolean sharkOption = false;
	private static boolean heroOption = false;
	private static boolean winnerOption = false;
	private static boolean looserOption = false;
	private static boolean user1Option = false;
	private static boolean user2Option = false;
	private static boolean user3Option = false;
	private static boolean user4Option = false;

	private static boolean exit = false;

	/*-************************************************************************************************
	 * Display main window and options to select folder ansd to run program
	 *************************************************************************************************/
	private static void startupWindow() {
		final var frame = new JFrame("Hold'em  Hand History Analysis");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(50, 50);
		frame.setPreferredSize(new Dimension(1400, 900));
		panel0.setLayout(new GridLayout(15, 1));
		panel0.setSize(1300, 600);

		final var f = new Font(Font.SERIF, Font.BOLD, 18);

		final var options1 = Box.createHorizontalBox();
		options1.setFont(f);

		panelCheckCharacteristic.setLayout(new GridLayout(1, 1));
		final var optionsCheckCharacteristic = Box.createHorizontalBox();
		optionsCheckCharacteristic.setFont(f);
		panelCheckCharacteristic.setBorder(BorderFactory.createTitledBorder("What kind of Filters"));
		panelCheckCharacteristic.add(selectByType);
		panelCheckCharacteristic.add(selectByCharacteristic);
		panelCheckCharacteristic.add(selectByStreet);
		panel0.add(panelCheckCharacteristic);
		selectByType.setSelected(true);
		selectByType.addActionListener(new Listener());
		selectByCharacteristic.addActionListener(new Listener());
		selectByStreet.addActionListener(new Listener());
		panelCheckCharacteristic.setFont(f);
		panel0.add(panelBlank);
		panel0.add(panelBlank);

		panel12.setSize(300, 40);
		panel12.setBackground(Color.white);
		panel12.setLayout(new BoxLayout(panel12, BoxLayout.X_AXIS));
		final var dim3 = new Dimension(300, 30);
		panel12.setMaximumSize(dim3);
		panel12.setMinimumSize(dim3);

		panelCheck.setLayout(new GridLayout(1, 1));
		final var optionsCheck = Box.createHorizontalBox();
		optionsCheck.setFont(f);
		panelCheck.setBorder(BorderFactory.createTitledBorder("Options"));
		panelCheck.add(crash);
		panel0.add(panelCheck);
		crash.setSelected(true);
		crash.addActionListener(new Listener());
		panelCheck.setFont(f);
		panel0.add(panelBlank);
		panel0.add(panelBlank);
		crash.setSelected(true);

		panel0.add(panel12);

		panelRadio.setLayout(new GridLayout(1, 8));
		final var radio = Box.createHorizontalBox();
		radio.setFont(f);
		final var radioGroup = new ButtonGroup();
		panelRadio.setFont(f);
		panelRadio.setBorder(BorderFactory.createTitledBorder("Player type to accept"));
		panelRadio.add(all);
		panelRadio.add(average);
		panelRadio.add(fish);
		panelRadio.add(nit);
		panelRadio.add(lag);
		panelRadio.add(tag);
		panelRadio.add(shark);
		panelRadio.add(hero);
		panelRadio.add(winner);
		panelRadio.add(looser);
		panelRadio.add(user1);
		panelRadio.add(user2);
		panelRadio.add(user3);
		panelRadio.add(user4);
		radioGroup.add(all);
		radioGroup.add(average);
		radioGroup.add(fish);
		radioGroup.add(nit);
		radioGroup.add(lag);
		radioGroup.add(tag);
		radioGroup.add(shark);
		radioGroup.add(hero);
		radioGroup.add(winner);
		radioGroup.add(looser);
		radioGroup.add(user1);
		radioGroup.add(user2);
		radioGroup.add(user3);
		radioGroup.add(user4);
		panel0.add(panelRadio);
		average.setSelected(true);

		all.addActionListener(new Listener());
		average.addActionListener(new Listener());
		fish.addActionListener(new Listener());
		nit.addActionListener(new Listener());
		lag.addActionListener(new Listener());
		tag.addActionListener(new Listener());
		shark.addActionListener(new Listener());
		winner.addActionListener(new Listener());
		looser.addActionListener(new Listener());
		user1.addActionListener(new Listener());
		user2.addActionListener(new Listener());
		user3.addActionListener(new Listener());
		user4.addActionListener(new Listener());

		panelCheckStreet.setLayout(new GridLayout(1, 1));
		final var optionsCheckStreet = Box.createHorizontalBox();
		optionsCheckStreet.setFont(f);
		panelCheckStreet.setBorder(BorderFactory.createTitledBorder("Which Streets"));
		panelCheckStreet.add(preflop);
		panelCheckStreet.add(flop);
		panelCheckStreet.add(turn);
		panelCheckStreet.add(river);
		panelCheckStreet.add(showdown);
		panel0.add(panelCheckStreet);
		preflop.addActionListener(new Listener());
		flop.addActionListener(new Listener());
		turn.addActionListener(new Listener());
		river.addActionListener(new Listener());
		showdown.addActionListener(new Listener());
		panelCheckStreet.setFont(f);
		panel0.add(panelCheckStreet);
		panel0.add(panelBlank);
		panel0.add(panelBlank);

		panelCheckStreetOptions.setLayout(new GridLayout(1, 1));
		final var optionsCheckStreetOPtions = Box.createHorizontalBox();
		optionsCheckStreetOPtions.setFont(f);
		panelCheckStreetOptions.setBorder(BorderFactory.createTitledBorder("Which options for selected street"));
		panelCheckStreetOptions.add(vpipStreet);
		panelCheckStreetOptions.add(winRateStreet);
		panelCheckStreetOptions.add(aggPctStreet);
		panelCheckStreetOptions.add(threeBetStreet);
		panelCheckStreetOptions.add(cBetBetStreet);
		panelCheckStreetOptions.add(cBetBetFoldToStreet);
		panelCheckStreetOptions.add(wtsdStreet);
		panelCheckStreetOptions.add(rfiStreet);
		panelCheckStreetOptions.add(donkBetStreet);
		panelCheckStreetOptions.add(foldPerStreet);
		panelCheckStreetOptions.add(continuePerStreet);
		panelCheckStreetOptions.add(mdfPerStreet);
		panel0.add(panelCheckStreetOptions);
		vpipStreet.addActionListener(new Listener());
		winRateStreet.addActionListener(new Listener());
		aggPctStreet.addActionListener(new Listener());
		threeBetStreet.addActionListener(new Listener());
		cBetBetFoldToStreet.addActionListener(new Listener());
		wtsdStreet.addActionListener(new Listener());
		rfiStreet.addActionListener(new Listener());
		donkBetStreet.addActionListener(new Listener());
		foldPerStreet.addActionListener(new Listener());
		continuePerStreet.addActionListener(new Listener());
		mdfPerStreet.addActionListener(new Listener());
		panelCheckStreetOptions.setFont(f);
		panel0.add(panelCheckStreetOptions);
		panel0.add(panelBlank);
		panel0.add(panelBlank);

		panelRadio.setVisible(false);
		panelCheckStreetOptions.setVisible(false);
		panelCheckStreet.setVisible(false);
		frame.add(panel0);
		frame.pack();
		frame.setVisible(true);
	}

	/*-************************************************************************************************
	 * The user has selected the option to select players by their property.
	 * We check each property by street and then compare the values to the values in the 
	 * PlayerCharacteristics class.
	 * If the value is between the high and the low values then that player is selected.
	 * There may be multiple properties selected by the user so multiple ways to select any player.
	 * private static final JCheckBox winRateStreet = new JCheckBox("Win rate");
	************************************************************************************************ */
	private static boolean selectByCharacteristic() {
		if (vpipStreet.isSelected() && compareHighLow(onePlayerCharacteristics.vpipStreet, rules.vpipLow)) {
			return true;
		}
		if (afStreet.isSelected() && compareHighLow(onePlayerCharacteristics.vpipStreet, rules.vpipLow)) {
			return true;
		}
		if (aggPctStreet.isSelected() && compareHighLow(onePlayerCharacteristics.vpipStreet, rules.vpipLow)) {
			return true;
		}
		if (threeBetStreet.isSelected() && compareHighLow(onePlayerCharacteristics.vpipStreet, rules.vpipLow)) {
			return true;
		}
		if (cBetBetStreet.isSelected() && compareHighLow(onePlayerCharacteristics.vpipStreet, rules.vpipLow)) {
			return true;
		}
		if (cBetBetFoldToStreet.isSelected() && compareHighLow(onePlayerCharacteristics.vpipStreet, rules.vpipLow)) {
			return true;
		}
		if (wtsdStreet.isSelected() && compareHighLow(onePlayerCharacteristics.vpipStreet, rules.vpipLow)) {
			return true;
		}
		if (rfiStreet.isSelected() && compareHighLow(onePlayerCharacteristics.vpipStreet, rules.vpipLow)) {
			return true;
		}
		if (donkBetStreet.isSelected() && compareHighLow(onePlayerCharacteristics.vpipStreet, rules.vpipLow)) {
			return true;
		}
		if (foldPerStreet.isSelected() && compareHighLow(onePlayerCharacteristics.vpipStreet, rules.vpipLow)) {
			return true;
		}
		if (continuePerStreet.isSelected() && compareHighLow(onePlayerCharacteristics.vpipStreet, rules.vpipLow)) {
			return true;
		}
		if (mdfPerStreet.isSelected() && compareHighLow(onePlayerCharacteristics.vpipStreet, rules.vpipLow)) {
			return true;
		}
		return false;
	}

	/*-************************************************************************************************
	 * Compare to low and high values. 
	 * Arg0 - value to compare
	 * Arg1 - low value
	 * Arg2 - high value
	 * returns true if value is between the low and high values.
	 *************************************************************************************************/
	private static boolean compareHighLow(double[] value, double[] low) {
		final boolean condition = preflop.isSelected() && value[0] < low[0] && value[0] > low[0];
		if (condition) {
			return true;
		}
		final boolean condition1 = flop.isSelected() && value[0] < low[1] && value[1] > low[0];
		if (condition1) {
			return true;
		}
		final boolean condition2 = turn.isSelected() && value[0] < low[2] && value[2] > low[2];
		if (condition2) {
			return true;
		}
		final boolean condition3 = river.isSelected() && value[0] < low[3] && value[3] > low[0];
		if (condition3) {
			return true;
		}
		return false;
	}

	/*-************************************************************************************************
	 * Add description of variable to selected file description
	************************************************************************************************ */
	private static void addVariableDescriptions() {
		if (vpipStreet.isSelected()) {
			addHighLowDescription("vpip street ", onePlayerCharacteristics.vpipStreet, rules.vpipLow, rules.vpipHigh);
		}
		if (afStreet.isSelected()) {
			addHighLowDescription("afStreet", onePlayerCharacteristics.vpipStreet, rules.vpipLow, rules.vpipHigh);
		}
		if (aggPctStreet.isSelected()) {
			addHighLowDescription("aggPctStreet", onePlayerCharacteristics.vpipStreet, rules.vpipLow, rules.vpipHigh);
		}
		if (threeBetStreet.isSelected()) {
			addHighLowDescription("threeBetStreet", onePlayerCharacteristics.vpipStreet, rules.vpipLow, rules.vpipHigh);
		}
		if (cBetBetStreet.isSelected()) {
			addHighLowDescription("cBetBetStreet", onePlayerCharacteristics.vpipStreet, rules.vpipLow, rules.vpipHigh);
		}
		if (cBetBetFoldToStreet.isSelected()) {
			addHighLowDescription("cBetBetFoldToStreet", onePlayerCharacteristics.vpipStreet, rules.vpipLow,
					rules.vpipHigh);
		}
		if (wtsdStreet.isSelected()) {
			addHighLowDescription("wtsdStreet", onePlayerCharacteristics.vpipStreet, rules.vpipLow, rules.vpipHigh);
		}
		if (rfiStreet.isSelected()) {
			addHighLowDescription("rfiStreet", onePlayerCharacteristics.vpipStreet, rules.vpipLow, rules.vpipHigh);
		}
		if (donkBetStreet.isSelected()) {
			addHighLowDescription("donkBetStreet", onePlayerCharacteristics.vpipStreet, rules.vpipLow, rules.vpipHigh);
		}
		if (foldPerStreet.isSelected()) {
			addHighLowDescription("foldPerStreet", onePlayerCharacteristics.vpipStreet, rules.vpipLow, rules.vpipHigh);
		}
		if (continuePerStreet.isSelected()) {
			addHighLowDescription("continuePerStreet", onePlayerCharacteristics.vpipStreet, rules.vpipLow,
					rules.vpipHigh);
		}
		if (mdfPerStreet.isSelected()) {
			addHighLowDescription("mdfPerStreet", onePlayerCharacteristics.vpipStreet, rules.vpipLow, rules.vpipHigh);
		}
	}

	/*-************************************************************************************************
	 * Add description of variable name, street, value, low, and high to file description.
	 * Arg0 - String description
	 * Arg1 - value to compare
	 * Arg2 - low value
	 * Arg3 - high value
	 * returns true if value is between the low and high values.
	 *************************************************************************************************/
	private static void addHighLowDescription(String st, double[] value, double[] low, double[] high) {
		if (preflop.isSelected()) {
			description.append("").append(st).append(" Preflop value ").append(Format.format(value[0])).append(" low ")
					.append(Format.format(low[0])).append(" high ").append(Format.format(high[0]));
		}
		if (flop.isSelected()) {
			description.append("").append(st).append(" Flop value ").append(Format.format(value[1])).append(" low ")
					.append(Format.format(low[1])).append(" high ").append(Format.format(high[1]));
		}
		if (turn.isSelected()) {
			description.append("").append(st).append("").append(st).append(" Turn value ")
					.append(Format.format(value[0])).append(" low ").append(Format.format(low[2])).append(" high ")
					.append(Format.format(high[2]));
		}
		if (river.isSelected()) {
			description.append("").append(st).append(" River value ").append(Format.format(value[3])).append(" low ")
					.append(Format.format(low[3])).append(" high ").append(Format.format(high[3]));
		}
	}

	/*-************************************************************************************************
	* Helper method to add the selected player type to the description.
	************************************************************************************************ */
	private static void addTypeDescription() {
		description.append(" The selected type is: ");
		description.append(PLAYER_TYPE_ST[playerType]);
	}

	/*-************************************************************************************************
	* Helper method to add streets to the description.
	************************************************************************************************ */
	private static void addStreetDescription() {
		description.append(" The streets selected are: ");
		if (preflop.isSelected()) {
			description = description.append(" Preflop ");
		}
		if (flop.isSelected()) {
			description = description.append(" Flop ");
		}
		if (turn.isSelected()) {
			description = description.append(" Turn ");
		}
		if (river.isSelected()) {
			description = description.append(" River ");
		}
		if (showdown.isSelected()) {
			description = description.append(" Show Down ");
		}
	}

	/*-******************************************************************************
	 * The file description is created from 
	 *******************************************************************************/
	private static void createFileDescription() {
		description = new StringBuilder();
		addTypeDescription();
		addStreetDescription();
		addVariableDescriptions();
	}

	/*-******************************************************************************
	 * Choose input folder
	 *******************************************************************************/
	private static void checkSelected() {
		Crash.crash = crash.isSelected() ? true : false;

		if (selectByType.isSelected()) {
			panelRadio.setVisible(true);
		} else {
			panelRadio.setVisible(false);
		}

		if (selectByStreet.isSelected()) {
			panelCheckStreet.setVisible(true);
		} else {
			panelCheckStreet.setVisible(false);
		}

		if (selectByCharacteristic.isSelected()) {
			panelCheckStreetOptions.setVisible(true);
		} else {
			panelCheckStreetOptions.setVisible(false);
		}

		if (all.isSelected()) {
			allOption = true;
			playerType = ALL;
		} else {
			allOption = false;
		}
		playerType = -1;
		if (average.isSelected()) {
			averageOption = true;
			playerType = AVERAGE;
		} else {
			averageOption = false;
		}
		if (nit.isSelected()) {
			nitOption = true;
			playerType = NIT;
		} else {
			nitOption = false;
		}
		if (lag.isSelected()) {
			lagOption = true;
			playerType = LAG;
		} else {
			lagOption = false;
		}
		if (tag.isSelected()) {
			tagOption = true;
			playerType = TAG;
		} else {
			tagOption = false;
		}
		if (fish.isSelected()) {
			fishOption = true;
			playerType = FISH;
		} else {
			fishOption = false;
		}
		if (shark.isSelected()) {
			sharkOption = true;
			playerType = SHARK;
		} else {
			sharkOption = false;
		}
		if (hero.isSelected()) {
			heroOption = true;
			playerType = HERO;
		} else {
			heroOption = false;
		}
		if (winner.isSelected()) {
			winnerOption = true;
			playerType = WINNER;
		} else {
			winnerOption = false;
		}
		if (looser.isSelected()) {
			looserOption = true;
			playerType = LOOSER;
		} else {
			looserOption = false;
		}
		if (user1.isSelected()) {
			user1Option = true;
			playerType = USER1;
		} else {
			user1Option = false;
		}
		if (user2.isSelected()) {
			user2Option = true;
			playerType = USER2;
		} else {
			user2Option = false;
		}
		if (user3.isSelected()) {
			user3Option = true;
			playerType = USER3;
		} else {
			user3Option = false;
		}
		if (user4.isSelected()) {
			user4Option = true;
			playerType = USER4;
		} else {
			user4Option = false;
		}
	}

	/*- ***********************************************************************************************-
	 *  Responds to button clicks. 
	***********************************************************************************************  */
	static class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a0) {
			checkSelected();
			switch (a0.getActionCommand()) {
			case "Edit Type Data" -> rules.editData(100, 500);
			case "Edit Characteristic Data" -> rules.editCharacteristic(100, 500);
			case "Exit" -> exit = true;
			}
		}
	}
}
