package holdemhandhistory;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JDialog;

/*-*******************************************************************************
 * @author PEAK
 * _*******************************************************************************/

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Popup implements Constants {

	private Popup() {
		throw new IllegalStateException("Utility class");
	}

	/*- - Popup message. */
	static void popup(String message) {
		JOptionPane.showMessageDialog(null, message);
	}

	/*- - Popup message Yes or No Returns true if yes or cancel;. */
	static boolean popupYes(String message) {
		final int result = JOptionPane.showConfirmDialog(null, message);
		return result == 0 || result == 1;
	}

	/*-**********************************************************************************************
	* This method will display a popup with no response and close after a delay
	*************************************************************************************************/
	static void textDisplayPopup(String strTitle, String strText, int iDelayInSeconds, int iX_Location, int iY_Location,
			int iMessageType) {
		final JOptionPane optionPane = new JOptionPane(strText, iMessageType, JOptionPane.DEFAULT_OPTION, null,
				new Object[] {}, null);
		final JDialog dialog = new JDialog();
		dialog.setTitle(strTitle);
		dialog.setModal(false);
		dialog.setContentPane(optionPane);
		dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		dialog.pack();
		dialog.setLocation(iX_Location, iY_Location);

		// create timer to dispose of dialog after, iDelayInSeconds, seconds
		Timer timer = new Timer(iDelayInSeconds * 1000, new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent ae) {
				dialog.dispose();
			}
		});
		timer.setRepeats(false); // one-time use timer

		// timer removes dialog after iDelayInSeconds
		timer.start();
		dialog.setVisible(true);
	}

}
