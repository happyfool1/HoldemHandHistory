package holdemhandhistory;
/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

class HandRangeOrder implements Constants {

	private static final int HANDS = 169;
	public static final int SUITED = 0;
	public static final int OFFSUIT = 1;
	public static final int PAIR = 2;
	public static final int INVALID = 2;
	/*- Number of 2 card combinations. */
	public static int COMBINATIONS = 1326;
	/*- Equilab Equity vs. 5 random hands - Playability */
	private static final String[] playabilityArray = { "AA", "KK", "QQ", "JJ", "AKs", "TT", "AQs", "KQs", "AKo", "AJs",
			"KJs", "ATs", "99", "QJs", "AQo", "KTs", "QTs", "KQo", "JTs", "AJo", "A9s", "88", "KJo", "A8s", "K9s",
			"ATo", "QJo", "Q9s", "A7s", "T9s", "J9s", "A5s", "KTo", "77", "A4s", "A6s", "QTo", "JTo", "K8s", "A3s",
			"Q8s", "K7s", "A2s", "T8s", "J8s", "98s", "A9o", "66", "K6s", "K5s", "K9o", "A8o", "Q7s", "K4s", "87s",
			"T7s", "Q9o", "97s", "T9o", "J7s", "J9o", "K3s", "55", "Q6s", "A7o", "K2s", "A5o", "Q5s", "76s", "86s",
			"Q4s", "A4o", "A6o", "96s", "T6s", "K8o", "44", "J6s", "Q3s", "65s", "A3o", "J5s", "T8o", "Q8o", "Q2s",
			"75s", "J8o", "98o", "K7o", "54s", "J4s", "33", "A2o", "85s", "J3s", "T5s", "64s", "95s", "K6o", "J2s",
			"T4s", "22", "53s", "74s", "K5o", "T3s", "87o", "97o", "Q7o", "T7o", "84s", "J7o", "T2s", "43s", "K4o",
			"63s", "94s", "Q6o", "K3o", "93s", "76o", "52s", "73s", "92s", "Q5o", "86o", "K2o", "42s", "83s", "96o",
			"T6o", "82s", "65o", "Q4o", "62s", "J6o", "32s", "Q3o", "75o", "72s", "J5o", "64o", "Q2o", "85o", "J4o",
			"54o", "95o", "T5o", "J3o", "53o", "T4o", "J2o", "74o", "T3o", "43o", "84o", "63o", "T2o", "94o", "93o",
			"52o", "73o", "92o", "42o", "83o", "62o", "82o", "32o", "72o" };

	/*-
	 * This array represents the commonly used 13 X 13 matrix to represent suited,
	 * offsuit, and pairs An element is true if that card is in the range.
	 */
	private int[] rangeArray = new int[HANDS];

	/*- Constructor. */
	public HandRangeOrder() {
		for (int i = 0; i < HANDS; ++i) {
			rangeArray[i] = 0;
		}
	}

	/*- - Clear hand range. */
	public void clearRange() {
		for (int i = 0; i < HANDS; ++i) {
			rangeArray[i] = -1;
		}
	}

	/*-  Get location in RangeArray. */
	public int getHandIndex(int ndx) {
		return this.rangeArray[ndx];
	}

	/*- - Get hand. */
	void setHand(int ndx, int value) {
		this.rangeArray[ndx] = value;
	}

	/*- - Count Hands. */
	int countHands() {
		int $ = 0;
		for (int i = 0; i < HANDS; ++i) {
			if (this.rangeArray[i] != -1) {
				$++;
			}
		}
		return $;
	}

	/*- - Debug. */
	void makePlayability() {
		int k = 0;
		final var range = new HandRange();
		clearRange();
		for (int i = 0; i < HANDS; ++i) {
			k = range.getHandStringToIndex(playabilityArray[i]);
			rangeArray[i] = k;
		}
		writeRange("C:\\Hold'emDatabase\\PlayabilityOrder.ser");
	}

	/*- - Write a HandRange object to a disk file Arg0 - The full path name. */
	void writeRange(String path) {
		// deleteFile(path);
		try {
			final var fileOut = new FileOutputStream(path);
			final var out = new ObjectOutputStream(fileOut);
			out.writeObject(this.rangeArray);
			out.close();
		} catch (IOException i) {
			i.printStackTrace();
		}
	}

	/*- - Read a rangeArray from a disk file Arg0 - The full path name. */
	void readRange(String path) {
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			this.rangeArray = (int[]) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return;
		}
	}

	/*- - Delete file. */
	static boolean deleteFile(String fileN) {
		final boolean result = true;
		final var f = new File(fileN);
		if (f.exists()) {
			f.delete();
		}
		return result;
	}

}
