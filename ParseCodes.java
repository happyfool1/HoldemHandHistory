package holdemhandhistory;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class ParseCodes implements Constants {
	/*-  ******************************************************************************
	 * This Class simply holds the reject codes set by 
	 *  @author PEAK_
	 ****************************************************************************** */
	private ParseCodes() {
		throw new IllegalStateException("Utility class");
	}

	// Reject codes
	static final int OK = 0;
	static final int TOO_FEW_HANDS_PLAYED = 1;
	static final int INVALID_PLAYER_NAME = 2;
	static final int INVALID_HAND_LENGTH = 3;
	static final int INVALID_HAND_NUMBER = 4;
	static final int NO_DATE_OR_TIME = 5;
	static final int HAND_SIZE_INVALID = 6;
	static final int TABLE_MISSING = 7;
	static final int NOT_6 = 8;
	static final int NAME_FORMAT = 9;
	static final int LARGER_THAN_MAX = 10;
	static final int NULL_OR_ZERO_LENGTH_LINE = 11;
	static final int TOO_MANY_ORBITS = 12;
	static final int SUMMARY_INCOMPLETE = 13;
	static final int SUMMARY_MISSING_SEAT = 14;
	static final int POST_BB_AND_IS_ALLIN = 15;
	static final int DUPLICATE_HAND_ID = 16;
	static final int BUTTON_SEAT_NUMBER = 17;
	static final int INDEX_OF_$ = 18;
	static final int IN_CHIPS = 19;
	static final int INDEX_OF_ = 20;
	static final int SB_BET = 21;
	static final int BB_BET = 22;
	static final int SUMMARY_MISSING = 23;
	static final int TOTAL_POT = 24;
	static final int TOTAL_POT_VALUE = 25;
	static final int RAKE_INDEX = 26;
	static final int RAKE_INVALID = 27;
	static final int RAKE_BLANK = 28;
	static final int RAKE_VALUE = 29;
	static final int MAIN_POT = 30;
	static final int MAIN_POT_VALUE = 31;
	static final int SIDE_POT = 32;
	static final int SIDE_POT_VALUE = 33;
	static final int SIDE_POT1 = 34;
	static final int SIDE_POT1_VALUE = 35;
	static final int SIDE_POT2 = 36;
	static final int SIDE_POT2_VALUE = 37;
	static final int SIDE_POT3 = 38;
	static final int SIDE_POT3_VALUE = 39;
	static final int SIDE_POT4 = 40;
	static final int SIDE_POT4_VALUE = 41;
	static final int WON$ = 42;
	static final int WON$_INDEX = 43;
	static final int COLLECTED_INDEX = 44;
	static final int COLLECTED_VALUE = 45;
	static final int SHOWDOWN_INDEX = 46;
	static final int SHOWDOWN_VALUE = 47;
	static final int SHOWDOWN_COLLECTED = 48;
	static final int SHOWDOWN_SIDE_POT = 49;
	static final int SHOWDOWN_SEAT = 50;
	static final int SEAT_NUMBER = 51;
	static final int CALL$ = 52;
	static final int RAISE_TO = 53;
	static final int RAISE_TO_VALUE = 54;
	static final int BET$ = 55;
	static final int UNCALLED_INDEX = 56;
	static final int UNCALLED_SEAT = 57;
	static final int UNCALLED$_INDEX = 58;
	static final int UNCALLED_TO = 59;
	static final int UNCALLED_TO_SEAT = 60;
	static final int UNCALLED_NAME = 61;
	static final int UNCALLED_SEAT_NAME = 62;
	static final int COLLECTED_FROM_POT = 63;
	static final int COLLECTED_FROM_POT_VALUE = 64;
	static final int COLLECTED_SEAT = 65;
	static final int SIDE_INDEX = 66;
	static final int NO_STREET_ACTION = 67;
	static final int PLAYER_NAME_NOT_FOUND = 68;
	static final int UNCALLED_$_INDEX = 69;
	static final int SIDE_POT4_EXIST = 70;
	static final int HAND_LENGTH = 71;
	static final int STACK_CONVERSION = 72;
	static final int INVALID_HAND_ID = 73;
	static final int FORMAT_ERROR = 74;
	static final int INVALID_STAKES = 75;
	static final int CONVERTING_HAND_ID = 76;
	static final int VALUE_IN_HAND = 77;
	static final int HAND_ERROR_CHECK_LINE = 78;
	static final int NO_RESPONSE_TO_BET_OR_RAISE = 79;
	static final int BLANK = 80;
	static final int MAX_CODE = 81;

	static final String[] CODES_ST = { "OK", "TOO_FEW_HANDS_PLAYED", "INVALID_PLAYER_NAME ", "INVALID_HAND_LENGTH ",
			"INVALID_HAND_NUMBER ", "NO_DATE_OR_TIME", "HAND_SIZE_INVALID ", "TABLE_MISSING", "NOT_6", "NAME_FORMAT",
			"LARGER_THAN_MAX ", "NULL_OR_ZERO_LENGTH_LINE ", "TOO_MANY_ORBITS ", "SUMMARY_INCOMPLETE",
			"SUMMARY_MISSING_SEAT", "POST_BB_AND_IS_ALLIN ", "DUPLICATE_HAND_ID", "BUTTON_SEAT_NUMBER", "INDEX_OF_$",
			"IN_CHIPS", "INDEX_OF", "SB_BET", "BB_BET", "SUMMARY_MISSING", "TOTAL_POT", "TOTAL_POT_VALUE", "RAKE_INDEX",
			"RAKE_INVALID", "RAKE_BLANK", "RAKE_VALUE", "MAIN_POT", "MAIN_POT_VALUE", "SIDE_POT", "SIDE_POT_VALUE",
			"SIDE_POT1", "SIDE_POT1_VALUE", "SIDE_POT2", "SIDE_POT2_VALUE", "SIDE_POT3=", "SIDE_POT3_VALUE",
			"SIDE_POT4", "SIDE_POT4_VALUE", "WON$", "WON$_INDEX", "COLLECTED_TO", "COLLECTED_VALUE", "SHOWDOWN_INDEX",
			"SHOWDOWN_VALUE", "SHOWDOWN_COLLECTED", "SHOWDOWN_SIDE_POT", "SHOWDOWN_SEAT", "SEAT_NUMBER", "CALL$",
			"RAISE_TO", "RAISE_TO_VALUE", "BET$", "UNCALLED_INDEX", "UNCALLED_SEAT", "UNCALLED$_INDEX", "UNCALLED_TO",
			"UNCALLED_TO_SEAT", "UNCALLED_NAME", "UNCALLED_SEAT_NAME", "COLLECTED_FROM_POT", "COLLECTED_FROM_POT_VALUE",
			"COLLECTED_SEAT", "SIDE_INDEX", "NO_STREET_ACTION", "PLAYER_NAME_NOT_FOUND", "UNCALLED_$_INDEX",
			"SIDE_POT4_EXIST", "HAND_LENGTH", "STACK_CONVERSION", "Invalid handID", "Format error", "Invalid Stakes",
			"ERROR converting handID", "Value in Hansd", "Hand.errorCheckLine() ", "No response to bert or raise",
			"Blank after :", "???" };

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
		{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	
		{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	
		{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},
		{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},	{ "", ""},
		{ "", ""},	{ "", ""},	{ "", ""},{ "", ""},	{ "", ""},	{ "", ""},	{"",""}};

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
	++ codes[code] ;
}

/*-
 * Report code counts
 */
static void report() {
	final var ff = new Font(Font.SERIF, Font.BOLD, 12);
	final var frame = new JFrame("Hand History Reject Hands ");
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.setLocation(50, 50);
	frame.setPreferredSize(new Dimension(400, 850));

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
	// TODO	dataReject[i][1] = Format.format(codes[i]);
	}
}


}
