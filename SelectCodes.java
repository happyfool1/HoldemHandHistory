package holdemhandhistory;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class SelectCodes implements Constants {
	// Reject codes
	static final int OK = 0;
	static final int NOT_POKERSTARS = 1;
	static final int NOT_$ = 2;
	static final int NOT_6MAX = 3;
	static final int NOT_6PLAYERS = 4;
	static final int DUPLICATE_HAND = 5;
	static final int DUPLICATE_HAND_ID = 6;
	static final int NOT_6 = 7;
	static final int INVALID_CURRENCY = 8;
	static final int NOT_GGPOKER = 9;
	static final int NOT_CANADIAN = 10;
	static final int NOT_EURO = 11;
	static final int NOT_USD = 12;
	static final int SITS_OUT = 13;
	static final int TIMED_OUT = 14;
	static final int LEAVES_TABLE = 15;
	static final int TOO_FEW_HANDS_PLAYED = 16;
	static final int INVALID_PLAYER_NAME = 17;
	static final int INVALID_HAND_LENGTH = 18;
	static final int INVALID_HAND_NUMBER = 19;
	static final int NO_DATE_OR_TIME = 20;
	static final int HAND_SIZE_INVALID = 21;
	static final int TABLE_MISSING = 22;
	static final int DISCONNECTED = 23;
	static final int NAME_FORMAT = 24;
	static final int LARGER_THAN_MAX = 25;
	static final int SUMMARY_INCOMPLETE = 26;
	static final int SUMMARY_MISSING_SEAT = 27;
	static final int JOINS_TABLE = 28;
	static final int HEADER_LENGTH = 29;
	static final int NULL_OR_ZERO_LENGTH_LINE = 30;
	static final int SHORT_LINE = 31;
	static final int INVALID_LINE_0 = 32;
	static final int ANTE = 33;
	static final int INVALID_HAND_ID = 34;
	static final int POT_NOT_AWARDED = 35;
	static final int LONG_LINE = 36;
	static final int SEAT_MISSING = 37;
	static final int INVALID_NAME_FOR_REMOVAL_OPTION = 38;
	static final int INVALID_STAKES = 39;
	static final int CASHED_OUT = 40;
	static final int POST_SBBB = 41;
	static final int CONNECTED = 42;
	static final int WILL_BE_ALLOWED = 43;
	static final int MISSING_STREET = 44;
	static final int MISSING_SUMMARY = 45;
	static final int REMOVED = 46;
	static final int PLAYER_SAID = 47;
	static final int EMPTY_STREET = 48;
	static final int NO_WINNER = 49;
	static final int JACKPOT = 50;
	static final int THIRD = 51;
	static final int SIDEPOT2 = 51;
	static final int MAX_CODE = 52;
	static final String[] CODES_ST = { "OK", "Not PokerStars", "Not $", "Not 6-max ", "Not 6 players", "Duplicate hand",
			"Duplicate hand ID", "Not 6 players or other ", "Invalid Currency â ¬", "Not GGPoker", "Not Canadian",
			"Not Euro", "Not US Dollars", "Sits out", "Timed out", "Leaves table", "Too few hands played",
			"Invalid player name", " Invalid hand length", "Invalid hand no hand number", "No date or time",
			"Hand size or too few players ", "Table missing", "Disconnected", "Name format", "Larger than MAX",
			"Summary Incomplete", "Summary Missing Seat", "Joins table ", "Header length", "Null or zero length line",
			"Short line", "Invalid line 0 ", "Has Ante", "Invalid handID", "Pot not awarded cashed out", "Long line",
			"Seat 1: missing", "Invalid player name ", "Invalid Stakes", "Cashed Out", "Post_SBBB", "Connected",
			"Will Be Allowed", "Missing Street", "Missing Summary", "Removed", "Said", "Empty street", "No Winner",
			"Jackpot", "THIRD Flop, Turn, or River", "Second Side Pot", "???" };
	/*-
	 * Counts of codes array indexed by code
	 */
	static int[] codes = new int[MAX_CODE];
	/*-
	 *  - Headers for the table. 
	 */
	static Object[] columnsReject = { "Code", "Counts" };
	/*- Actual data for the table in a 2d array. */
// @formatter:off
	static Object[][] dataReject = { 
			{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},
			{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},
			{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},
			{ "", ""},	{ "", ""},	{ "", ""},{ "", ""},	{ "", ""},{ "", ""},	{ "", ""},{ "", ""},	{ "", ""},{ "", ""},	{ "", ""},
			{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},
			{"",""}};

	// Constructor
	SelectCodes() {

	}

	/*-
	 * Initialize
	 */
	static void initialize() {
		for (int i = 0; i < MAX_CODE; ++i) {
			codes[i] = 0;
		}
	}

	/*-
	 * Increment count in array
	 * Arg0 - code
	 */
	static void updateCodes(int code) {
		++codes[code];
	}

	/*-*************************************************************************
	 * Report code counts
	************************************************************************* */
	static void report() {
		final var ff = new Font(Font.SERIF, Font.BOLD, 12);
		final var frame = new JFrame("Hand History Reject Hands Causes ");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(1200, 50);
		frame.setPreferredSize(new Dimension(400, 950));

		final var tablePos = new JTable(dataReject, columnsReject);
		tablePos.setFont(ff);
		tablePos.setRowHeight(25);
		final var pane = new JScrollPane(tablePos);
		tablePos.getColumnModel().getColumn(0).setPreferredWidth(100);
		frame.add(pane);
		frame.pack();
		frame.setVisible(true);

		for (int i = 0; i < MAX_CODE; ++i) {
			dataReject[i][0] = CODES_ST[i];
		 dataReject[i][1] = Format.format(codes[i]);
		}
	}


}
