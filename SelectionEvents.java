package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.BitSet;
import java.util.HashMap;

public class SelectionEvents implements java.io.Serializable, Constants {
	private final long serialVersionUID = 1234567L;

	/*- ******************************************************************************************** 
	* @author PEAK
	********************************************************************************************** */

	/*- ******************************************************************************************** 
	* Class to define an event in the selection process.
	* An event is related to a Hand, not to a Player.
	* An event are things like there was a Show Down or a player showed his hole cards.
	* If related to a player who showed cards it's the cards we are interested in, not the player.
	* 
	********************************************************************************************** */

	/*- ******************************************************************************************** 
	* Event names for Bitset
	* An event is for a hand, not for an individual player.
	* Example: WAS_SHOWDOWN means that this hand had a showdown
	* Used with a HashMap of HandIds and events.
	* The idea is to reduce the memory requirements when there could be billions of hands.
	********************************************************************************************** */
	static final int WAS_SHOWDOWN = 0; // There was a Showdown
	static final int WAS_SHOWDOWN2 = 1; // There was a Showdown with 2 players
	static final int WAS_SHOWDOWN3 = 2; // There was a Showdown with 3 players
	static final int WAS_SHOWDOWN4 = 3; // There was a Showdown with 4 players
	static final int WAS_SHOWDOWN5 = 4; // There was a Showdown with 5 players
	static final int PLAYER_SHOWED_STREET = 5; // A player showed his hole cards on a street
	static final int PLAYER_SHOWED_SHOWDOWN = 6; // A player showed his hole cards at showdown

	private static BitSet event = new BitSet();
	private static HashMap<Long, BitSet> eventMap = new HashMap<Long, BitSet>();
	private static long handID = 0L;

	private static void x() {
		event.set(WAS_SHOWDOWN);
		eventMap.put(handID, event);
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
	 * Read .ser file for this Class
	 * Arg0 - The full path name.
	 * Returns null if error
	***************************************************************************** */
	SelectionEvents readFromFile(String path) {
		SelectionEvents r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (SelectionEvents) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}
}
