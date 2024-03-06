package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerIDs implements java.io.Serializable, Constants {

	// Constructor
	PlayerIDs(int size) {
		this.size = size;
		playerIDs = new int[size];
	}

	/*-  *****************************************************************************
	 * @author PEAK_
	 * This Class is used to record limited data about a player. 
	 * Used to remove players rejected for too few hands played.
	 * Used to sort players by type.
	 * Used TBD
	 * 
	 ***************************************************************************** */
	static final long serialVersionUID = 1234567L;
	int size;
	StringBuilder description;
	int[] playerIDs;

	/*-*****************************************************************************
	 * Read a Range from a disk file. 
	 * Arg0 - The full path name.
	******************************************************************************/
	PlayerIDs readFromFile(String path) {
		PlayerIDs r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (PlayerIDs) in.readObject();
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
}
