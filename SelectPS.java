package holdemhandhistory;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

public class SelectPS implements Constants {
	private static final int status = 0;
	private static int z = 0;
	private static int ndx = 0;

	/*-******************************************************************************
	 * Is this hands a valid for analysis by the  Class.
	 * The reject flag is set. 0 OK, 1 - n codes defined by constants
	 * 
	 * There are many reasons why a hand will be rejected.
	 * They all have to do with consistency of data.
	 * For example a hand is invalid if:
	 * 		There are not 6 players.
	 * 		There is an ante.
	 * 		Players leaving or arriving during a hand.
	 * 		
	 * The order of checking is important for performance, the most common cause for 
	 * an invalid hand is first.
	 * 
	
	java.lang.NumberFormatException: For input string: "â‚¬hemical"PokerStars Hand #240853216134:  Hold'em No Limit ($1/$2 USD) - 2023/01/07 10:09:03 ET
	Table 'Adria' 6-max Seat #4 is the button
	Seat 1: 001361 ($239.47 in chips)
	Seat 2: 003458 ($102.55 in chips)
	Seat 3: 003443 ($203 in chips)
	Seat 4: 002047 ($212.51 in chips)
	Seat 5: 003459 ($198 in chips)
	Seat 6: 002856 ($200 in chips)
	â‚¬hemical: X: posts small blind $1
	002856: posts big blind $2
	*** HOLE CARDS ***
	001361: folds
	003458: folds
	003443: raises $4 to $6
	002047: raises $12 to $18
	â‚¬hemical: X: folds
	002856: folds
	003443: raises $32 to $50
	002047: calls $32
	*** FLOP *** [6h 7h Ad]
	003443: bets $10
	002047: calls $10
	*** TURN *** [6h 7h Ad] [Kc]
	003443: bets $12
	002047: calls $12
	*** RIVER *** [6h 7h Ad Kc] [9c]
	003443: bets $14.40
	002047: raises $126.11 to $140.51 and is all-in
	003443: folds
	Uncalled bet ($126.11) returned to 002047
	002047 collected $172.80 from pot
	*** SUMMARY ***
	Total pot $175.80 | Rake $3
	Board [6h 7h Ad Kc 9c]
	Seat 1: 001361 folded before Flop (didn't bet)
	Seat 2: 003458 folded before Flop (didn't bet)
	Seat 3: 003443 folded on the River
	Seat 4: 002047 (button) collected ($172.80)
	Seat 5: 003459 (small blind) folded before Flop
	Seat 6: 002856 (big blind) folded before Flop
	//ERROR player name invalid format ---â‚¬hemical---2408532161347100903---
	****************************************************************************** */
	static int selectHand(HandPS handPS) {
		int n = 0;
		String st = "";

		if (handPS.numLines < 26 || handPS.numLines > 100) {
			return SelectCodes.HAND_SIZE_INVALID;
		}

		if (handPS.lines[0] == null || handPS.lines[1] == null || handPS.lines[1] == null || handPS.lines[2] == null
				|| handPS.lines[3] == null || handPS.lines[4] == null || handPS.lines[5] == null
				|| handPS.lines[6] == null || handPS.lines[7] == null || handPS.lines[8] == null
				|| handPS.lines[9] == null || handPS.lines[10] == null || handPS.lines[11] == null) {
			return SelectCodes.NULL_OR_ZERO_LENGTH_LINE;
		}

		if (handPS.lines[0].length() < 35 || handPS.lines[0].length() > 120) {
			return SelectCodes.HEADER_LENGTH;
		}

		if (handPS.lines[handPS.numLines - 1] == null || handPS.lines[handPS.numLines - 1].equals("")
				|| !handPS.lines[handPS.numLines - 1].startsWith("Seat 6: ")) {
			return SelectCodes.SUMMARY_MISSING_SEAT;
		}

		// Most common cause of rejects
		if (GUIFilter.sixPlayersOption) {
			if (handPS.lines[7].indexOf("6:", 5) == -1 || handPS.lines[8].indexOf("sma", 9) == -1
					|| handPS.lines[9].indexOf("big", 9) == -1 || handPS.lines[2].indexOf("1:", 5) == -1
					|| handPS.lines[3].indexOf("2:", 5) == -1 || handPS.lines[4].indexOf("3:", 5) == -1
					|| handPS.lines[5].indexOf("4:", 5) == -1 || handPS.lines[6].indexOf("5:", 5) == -1
					|| !handPS.lines[10].startsWith("*** HOLE")) {
				return SelectCodes.NOT_6;
			}
		}

		if (GUIFilter.player6MaxOption) {
			if (!handPS.lines[1].contains("6-max")) {
				return SelectCodes.NOT_6MAX;
			}
		}

		if (GUIFilter.psOption || GUIFilter.phOption) {
			if (!handPS.lines[0].startsWith("PokerStars")) {
				return SelectCodes.NOT_POKERSTARS;
			}
		}

		// Too long or too short
		for (int i = 2; i < handPS.numLines; ++i) {
			if (handPS.lines[i].length() <= 8) {
				return SelectCodes.SHORT_LINE;
			}
		}

		if (!handPS.lines[1].contains("Table")) {
			return SelectCodes.TABLE_MISSING;
		}
		if (!handPS.lines[2].startsWith("Seat 1: ") || !handPS.lines[7].startsWith("Seat 6: ")) {
			return SelectCodes.SEAT_MISSING;
		}

		if (GUIFilter.GGPokerOption) {
			n = handPS.lines[1].indexOf(" GG_");
			if (n != -1) {
				return SelectCodes.NOT_GGPOKER;
			}
		}

		// Only USD
		if (GUIFilter.usdOption && !GUIFilter.GGPokerOption) {
			n = handPS.lines[0].indexOf(" USD");
			if (n == -1) {
				if (!GUIFilter.GGPokerOption) {
					return SelectCodes.NOT_USD;
				}
			}
		}

		// Only Canadian
		if (GUIFilter.canadaOption) {
			n = handPS.lines[0].indexOf(" CAD");
			if (n == -1) {
				n = handPS.lines[0].indexOf(" USD");
			}
			if (n == -1) {
				return SelectCodes.NOT_CANADIAN;
			}
		}
		// Only Euro
		if (GUIFilter.euroOption) {
			n = handPS.lines[0].indexOf(" EUR");
			if (n == -1) {
				return SelectCodes.NOT_EURO;
			}
		}

		boolean stakesOK = false;
		// Check stakes
		if (GUIFilter.stakes050_010Option) {
			if (handPS.lines[0].contains("$0.05/$0.10")) {
				stakesOK = true;
			}
		}
		if (GUIFilter.stakes025_050Option) {
			if (handPS.lines[0].contains("$0.25/$0.50")) {
				stakesOK = true;
			}
		}
		if (GUIFilter.stakes05_1Option) {
			if (handPS.lines[0].contains("($0.50/$1.00")) {
				stakesOK = true;
			}
		}
		if (GUIFilter.stakes1_2Option) {
			if (handPS.lines[0].contains("$1/$2")) {
				stakesOK = true;
			}
			if (GUIFilter.euroOption && (handPS.lines[0].contains("€1/€2") || handPS.lines[0].contains("â‚¬1/â‚¬2"))) {
				System.out.println("//€1/€2");
				stakesOK = true;
			}
		}
		if (GUIFilter.stakes250_5Option) {
			if (handPS.lines[0].contains("$2.50/$5.00")) {
				stakesOK = true;
			}
		}
		if (GUIFilter.stakes5_10Option) {
			if (handPS.lines[0].contains("$5/$10")) {
				stakesOK = true;
			}
		}
		if (GUIFilter.stakes10_20Option) {
			if (handPS.lines[0].contains("$10/$20")) {
				stakesOK = true;
			}
		}
		if (GUIFilter.stakes25_50Option) {
			if (handPS.lines[0].contains("$25/$50")) {
				stakesOK = true;
			}
		}
		if (!stakesOK) {
			System.out.println("//XXX " + handPS.lines[0]);
			return SelectCodes.INVALID_STAKES;
		}

		/*-*****************************************************************************
		 * Convert hand to a long string for better performance
		****************************************************************************** */
		StringBuilder sb = new StringBuilder(1000);
		for (int i = 1; i < handPS.numLines - 6; ++i) {
			if (!handPS.lines[i].startsWith("*** ") && !handPS.lines[i].startsWith("Total ")
					&& !handPS.lines[i].startsWith("Board ")) {
				sb.append(handPS.lines[i]);
			}
		}
		String longString = sb.toString();
		if (longString.indexOf(" sits ", n) != -1) {
			return SelectCodes.SITS_OUT;
		}
		// is sitting out
		if (longString.indexOf(" sitting ", n) != -1) {
			return SelectCodes.SITS_OUT;
		}
		if (longString.indexOf(" leaves ", n) != -1) {
			return SelectCodes.LEAVES_TABLE;
		}
		if (longString.indexOf(" joins ", n) != -1) {
			return SelectCodes.JOINS_TABLE;
		}
		if (longString.indexOf(" timed ", n) != -1) {
			return SelectCodes.TIMED_OUT;
		}
		if (longString.indexOf(" is dis", n) != -1) {
			return SelectCodes.DISCONNECTED;
		}
		if (longString.indexOf(" is con", n) != -1) {
			return SelectCodes.CONNECTED;
		}
		if (longString.indexOf(" pot not ", n) != -1) {
			return SelectCodes.POT_NOT_AWARDED;
		}
		if (longString.indexOf(" cashed ", n) != -1) {
			return SelectCodes.CASHED_OUT;
		}
		// If said then remove what he said so there are no words like raise or call or
		// collected
		if (longString.indexOf(" said,", n) != -1) {
			for (int i = 1; i < handPS.numLines - 6; ++i) {
				if (!handPS.lines[i].startsWith("*** ") && !handPS.lines[i].startsWith("Total ")
						&& !handPS.lines[i].startsWith("Board ")) {
					n = handPS.lines[i].indexOf(" said, ");
					if (n != -1) {
						handPS.lines[i] = handPS.lines[i].substring(0, n + 8);
					}
					// return SelectCodes.PLAYER_SAID;
				}
			}
		}
		if (longString.indexOf(" allow", n) != -1) {
			return SelectCodes.WILL_BE_ALLOWED;
		}

		boolean playerAllin = false;
		boolean haveFlop = false;
		boolean haveTurn = false;
		boolean haveRiver = false;
		boolean haveSummary = false;
		boolean haveShowdown = false;

		/*-*****************************************************************************
		 * Check for complete hand
		****************************************************************************** */
		for (int i = 10; i < handPS.numLines; ++i) {
			if (handPS.lines[i].contains("and is all-in")) {
				playerAllin = true;
			}
			if (handPS.lines[i].contains("THIRD")) {
				return SelectCodes.THIRD;
			}

			if (handPS.lines[i].startsWith("*** FLOP")) {
				haveFlop = true;
				if (!playerAllin && handPS.lines[i + 1].startsWith("*** ")) {
					return SelectCodes.EMPTY_STREET;
				}
			}

			if (handPS.lines[i].startsWith("*** TURN")) {
				haveTurn = true;
				if (!playerAllin && handPS.lines[i + 1].startsWith("*** ")) {
					return SelectCodes.EMPTY_STREET;
				}
			}
			if (handPS.lines[i].startsWith("*** RIVER")) {
				haveRiver = true;
				if (!playerAllin && handPS.lines[i + 1].startsWith("*** ")) {
					return SelectCodes.EMPTY_STREET;
				}
			}
			if (handPS.lines[i].startsWith("*** SUMMARY")) {
				haveSummary = true;
				ndx = i + 1;
				if (handPS.lines[i + 1].startsWith("*** ")) {
					return SelectCodes.EMPTY_STREET;
				}
				boolean hit = false;
				for (int j = i + 1; j < handPS.numLines; ++j) {
					if (handPS.lines[j].contains(" collected ") || handPS.lines[j].contains(" won ")) {
						hit = true;
						break;
					}
				}
				if (!hit) {
					return SelectCodes.NO_WINNER;
				}
			}
			if (handPS.lines[i].startsWith("*** SHOW")) {
				haveShowdown = true;
				if (handPS.lines[i + 1].startsWith("*** ")) {
					return SelectCodes.EMPTY_STREET;
				}
				boolean hit = false;
				for (int j = i + 1; j < handPS.numLines; ++j) {
					if (handPS.lines[j].contains(" collected ") || handPS.lines[j].contains(" won ")) {
						hit = true;
						break;
					}
				}
				if (!hit) {
					return SelectCodes.NO_WINNER;
				}
			}
		}

		StringBuilder sb2 = new StringBuilder(200);
		for (int i = 2; i < PLAYERS + 2; ++i) {
			sb2.append(handPS.lines[i]);
		}
		st = sb2.toString();
		if (st.contains(" raise ") || st.contains(" bet ") || st.contains(" call ") || st.contains(" check ")
				|| st.contains(" fold ") || st.contains(" said ") || st.contains(" Uncalled ")
				|| st.contains(" collected ") || st.contains(" pot ") || st.contains(" Total pot ")
				|| st.contains("Side ") || st.contains(" Main ") || st.contains(" Rake") || st.contains(" all in ")
				|| st.contains(" won ") || st.contains(" didn't") || st.contains("â") || st.contains("€")) {
			return SelectCodes.INVALID_NAME_FOR_REMOVAL_OPTION;
		}

		for (int i = handPS.numLines - 12; i < handPS.numLines - 8; ++i) {
			if (handPS.lines[i].contains("Side pot-2 ")) {
				return SelectCodes.SIDEPOT2;
			}
		}

		if (haveTurn && !haveFlop) {
			return SelectCodes.MISSING_STREET;
		}
		if (haveRiver && (!haveFlop || !haveTurn)) {
			return SelectCodes.MISSING_STREET;
		}
		if (!haveSummary) {
			return SelectCodes.MISSING_SUMMARY;
		}
		if (haveShowdown && !haveFlop) {
			return SelectCodes.MISSING_STREET;
		}

		return SelectCodes.OK;
	}
}
