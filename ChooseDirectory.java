package holdemhandhistory;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

import javax.swing.JFileChooser;

// Java program to use JFileChooser 
// to select  directory only 

class ChooseDirectory implements Constants {

	private ChooseDirectory() {
		throw new IllegalStateException("Utility class");
	}

	/*-
	 * Primary entry point 
	 */
	public static String start() {
		final var j = new JFileChooser("C:");
		j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (j.showSaveDialog(null) != JFileChooser.APPROVE_OPTION) {
			return "";
		}
		final var $ = j.getSelectedFile().getAbsolutePath();
		return $ + "\\";
	}

}
