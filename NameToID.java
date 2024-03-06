package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class NameToID implements java.io.Serializable, Constants {

	// Constructor
	NameToID() {
		name = "";
		ID = 0;
	}

	/*-  *****************************************************************************
	 * @author PEAK_
	 * This Class is used to map player names to an integer value.
	 * Used to preserve privacy of players in HandHistory files.
	 * Used for performance. An Integer is more efficient than a String.
	 * Uses HashMap to store player names.
	 ***************************************************************************** */
	static final long serialVersionUID = 1234567L;
	String name = "";
	int ID = 0;

	/*-*****************************************************************************
	 * Read a Range from a disk file. 
	 * Arg0 - The full path name.
	******************************************************************************/
	NameToID readFromFile(String path) {
		NameToID r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (NameToID) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return r;
		}
		return r;
	}

	/*-*****************************************************************************
	 * - Write Object to file - this
	***************************************************************************** */
	void writeToFile(String path) {
		final var filename = path;
		// Saving of object in a file
		try (var file = new FileOutputStream(filename); var out = new ObjectOutputStream(file)) {
			// Method for serialization of object
			out.writeObject(this);
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/*-*****************************************************************************
	 * Read an object from file - this
	 ******************************************************************************/
	void readFromFileX(String path) {
		NameToID object1 = null;
		// Reading the object from a file
		try (var file = new FileInputStream(path); var in = new ObjectInputStream(file)) {
			// Method for deserialization of object
			object1 = (NameToID) in.readObject();
		} catch (ClassNotFoundException | IOException i) {
			i.printStackTrace();
		}
	}

}
