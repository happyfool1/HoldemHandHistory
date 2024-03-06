package holdemhandhistory;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*-
 * For PokerStars only.
 * A hand is an array of String.
 */
public class HandPS implements java.io.Serializable, Constants {
	private static final long serialVersionUID = 1234567L;

	static ObjectOutputStream out;

	static FileOutputStream file;

	int numLines = 0;

	String[] lines = { null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
			null, null, null, null, null, null, null, null, null, null, null, null, null };

	/*-
	 * Print hand
	 */
	void printHand() {
		for (int i = 0; i < this.numLines; ++i) {
			if (this.lines[i] == null) {
				break;
			}
			System.out.println(this.lines[i]);
		}
	}

	/*-
	 *  Write Object to file - this
	 *  Appends to file
	 *  Arg0 - path to file
	 *  Calling method must call flushAndClose when complete
	 */
	void writeToFile(String path) {
		final var filename = path;
		// Serialization
		try {
			// Saving of object in a file
			file = new FileOutputStream(filename, true);
			out = new ObjectOutputStream(file);
			// Method for serialization of object
			out.writeObject(this);

		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/*-
	 * Flush and close 
	 */
	void close() {
		try {
			// Saving of object in a file
			out.close();
			file.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/*-
	 * Read a Range from a disk file. 
	 * Arg0 - The full path name.
	 */
	HandPS readFromFile(String path) {
		HandPS r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (HandPS) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return r;
		}
		return r;
	}

}
