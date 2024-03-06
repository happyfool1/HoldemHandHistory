package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayersSelected implements java.io.Serializable, Constants {

	/*- *************************************************************************************
	  * This Class contains a list of all of the players selected by the GUIPlayerClassification
	  * application. 
	  * There is an integer array and a count of the number of player IDs in the array.
	  * There is also a man readable description of what criterion was used to create this list.
	  * 
	  *  @author PEAK_ 
	************************************************************************************* */
	static final long serialVersionUID = 1234567L;

	PlayersSelected(int size) {
		playersIDArray = new int[size];
		numberOfPlayers = size;
	}

	// Before reading from a file
	PlayersSelected() {
	}

	/*-**************************************************************************************
	 * An array of player IDs and a description of what is in this file.
	**************************************************************************************/
	int numberOfPlayers = 0;
	StringBuilder description;
	int[] playersIDArray; // Array if player ID

	/*-*************************************************************************************
	 * Write Object to file - this
	 * Returns false if error
	 **************************************************************************************/
	boolean writeToFile(String path) {
		System.out.println("//WWW " + path);
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
	PlayersSelected readFromFile(String path) {
		// XXX C:\HHUniversal\HHNew10000\playerinfo.txt

		PlayersSelected r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (PlayersSelected) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

}
