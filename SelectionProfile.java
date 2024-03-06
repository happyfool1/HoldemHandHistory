package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SelectionProfile implements java.io.Serializable, Constants {
	private final long serialVersionUID = 1234567L;

	/*- ******************************************************************************************** 
	* @author PEAK
	********************************************************************************************** */

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
	 * Read .ser file for this Class
	 * Arg0 - The full path name.
	 * Returns null if error
	***************************************************************************** */
	holdemhandhistory.SelectionProfile readFromFile(String path) {
		holdemhandhistory.SelectionProfile r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (holdemhandhistory.SelectionProfile) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

}
