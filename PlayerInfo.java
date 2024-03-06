package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PlayerInfo implements java.io.Serializable, Constants {

	// Constructor
	PlayerInfo() {
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

	int playerID = 0;
	int played = 0;
	int type = -1;
	int status = 0; // 0 okay, 1 tbd not okay

	/*-*****************************************************************************
	 * Convert this object to a string. 
	 ******************************************************************************/
	String toEquivalentString() {
		StringBuilder sb1 = new StringBuilder(20).append(Long.valueOf(this.playerID)).append(",")
				.append(Integer.valueOf(this.played)).append(",").append(Integer.valueOf(this.type)).append(",")
				.append(Integer.valueOf(this.status)).append(",");
		return sb1.toString();
	}

	/*-*****************************************************************************
	 *Convert String created by toEquivalentString()) to an object
	 ******************************************************************************/
	PlayerInfo fromStringEquivalent(String st) {
		int n = 0;
		int n2 = 0;
		PlayerInfo h = new PlayerInfo();

		n2 = st.indexOf(",");
		h.playerID = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.played = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.type = Integer.parseInt(st.substring(n, n2));
		n = n2 + 1;
		n2 = st.indexOf(",", n);
		h.status = Integer.parseInt(st.substring(n, n2));
		return h;
	}

	/*-*****************************************************************************
	 * Read a Range from a disk file. 
	 * Arg0 - The full path name.
	******************************************************************************/
	PlayerInfo readFromFile(String path) {
		PlayerInfo r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (PlayerInfo) in.readObject();
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
