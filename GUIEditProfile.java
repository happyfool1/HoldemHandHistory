package holdemhandhistory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GUIEditProfile {

	/*- ******************************************************************************************** 
	* @author PEAK
	********************************************************************************************** */

	/*- ******************************************************************************************** 
	* Class to  
	********************************************************************************************** */

	// Input and output directories
	private static String pathIn = "C:\\HHUniversal\\";
	private static String pathOut = "C:\\HHSelected\\";
	private static String selectedPathIn = "";
	private static String selectedPathOut = "";
	private static String fileName = "";
	private static String fullPath = "";

	private static final ClassificationData rules = new ClassificationData();

	/*-************************************************************************************************
	 * Panels
	************************************************************************************************ */
	private static final JPanel panel0 = new JPanel();
	private static final JPanel panel12 = new JPanel();
	private static final JPanel panelBlank = new JPanel();
	private static final JButton buttonType = new JButton("Edit Type Data");
	private static final JButton buttonCharacteristic = new JButton("Edit Characteristic Data");
	private static final JButton buttonHelp = new JButton("Help");
	private static final JButton buttonExit = new JButton("Exit");
	private static final JTextField statusTextPass1 = new JTextField(
			"                                                                            ");
	private static final JTextField statusTextPass2 = new JTextField(
			"                                                                            ");
	private static final JTextField statusTextFinal = new JTextField(
			"                                                                            ");
	/*-************************************************************************************************
	 * Panels
	************************************************************************************************ */
	private static final JTextField handsText = new JTextField(6);
	private static final JPanel panelControl = new JPanel();
	private static final JPanel panelControl2 = new JPanel();

	private static final JTextField statusDirectories = new JTextField(
			"                                                                            ");

	private static final JLabel blank = new JLabel("   ");

	private static final JCheckBox crash = new JCheckBox("Crash on severe error");
	private static final JPanel panelCheck = new JPanel();

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

		Box.createHorizontalBox();
		statusDirectories.setBorder(BorderFactory.createTitledBorder("Input Folder      "));
		statusDirectories.setBackground(Color.WHITE);
		statusDirectories.setFont(f);
		panel0.add(statusDirectories);
		panel0.add(panelBlank);
		panel0.add(panelBlank);
		panelBlank.setFont(f);

		final var control = Box.createHorizontalBox();
		panelControl.setLayout(new GridLayout(1, 8));
		panelControl.setBounds(10, 770, 1000, 40);
		control.setFont(f);
		buttonHelp.setBackground(Color.GREEN);
		panelControl.add(control);
		panel0.add(panelControl);

		final var control2 = Box.createHorizontalBox();
		panelControl2.setLayout(new GridLayout(1, 5));
		panelControl2.setBounds(10, 770, 1000, 40);
		control2.setFont(f);
		buttonCharacteristic.setToolTipText("Edit Characteristic Data");
		buttonCharacteristic.setBackground(Color.GREEN);
		buttonType.setToolTipText("Edit Type Data");
		buttonType.setBackground(Color.GREEN);
		buttonHelp.setToolTipText("Help");
		buttonHelp.setBackground(Color.GREEN);
		buttonExit.setBackground(Color.GREEN);
		buttonExit.setToolTipText("Exit");
		control2.add(buttonType);
		control2.add(buttonCharacteristic);
		control2.add(buttonHelp);
		control2.add(buttonExit);
		panelControl2.add(control2);
		panel0.add(panelControl2);

		buttonType.addActionListener(new Listener());
		buttonCharacteristic.addActionListener(new Listener());
		buttonHelp.addActionListener(new Listener());
		buttonExit.addActionListener(new Listener());

		panel12.setSize(300, 40);
		panel12.setBackground(Color.white);
		panel12.setLayout(new BoxLayout(panel12, BoxLayout.X_AXIS));
		final var dim3 = new Dimension(300, 30);
		panel12.setMaximumSize(dim3);
		panel12.setMinimumSize(dim3);

		Box.createHorizontalBox();
		statusTextPass1.setBorder(BorderFactory.createTitledBorder("Pass 1      "));
		statusTextPass1.setBackground(Color.WHITE);
		statusTextPass1.setFont(f);
		panel0.add(statusTextPass1);

		Box.createHorizontalBox();
		statusTextPass2.setBorder(BorderFactory.createTitledBorder("Pass 2      "));
		statusTextPass2.setBackground(Color.WHITE);
		statusTextPass2.setFont(f);
		panel0.add(statusTextPass2);

		Box.createHorizontalBox();
		statusTextFinal.setBorder(BorderFactory.createTitledBorder("Selection file written     "));
		statusTextFinal.setBackground(Color.WHITE);
		statusTextFinal.setFont(f);
		panel0.add(statusTextFinal);

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

		frame.add(panel0);
		frame.pack();
		frame.setVisible(true);
	}

	/*- ***********************************************************************************************-
	 *  Responds to button clicks. 
	***********************************************************************************************  */
	static class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a0) {
			switch (a0.getActionCommand()) {

			case "Edit Type Data" -> rules.editData(100, 500);
			case "Edit Characteristic Data" -> rules.editCharacteristic(100, 500);

			case "Help" -> JOptionPane.showMessageDialog(null, helpString);

			}
		}

		/*-************************************************************************************************
		 * 
		************************************************************************************************ */
		private static boolean writeSelected() {

			return true;
		}
	}

	// @formatter:off  
				private static final String helpString = new StringBuilder().append("Welcome to the Holdem Hand History project.\r\n")
						.append("This GUI ")
						.append("\r\n")
							.append("Try it. Send me your suggestions and comments. Feel free to use any of the source code.\r\n")
						.append("\r\n").toString();
				
				// @formatter:on

}
