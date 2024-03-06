package holdemhandhistory;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

/*-  ******************************************************************************
 *  @author PEAK_
 ****************************************************************************** */

public class PlayerBetSizeReports2 implements Constants {

	/*-******************************************************************************
	 * This class does a bunch of reports using data collected by .
	 * All of the reports in this Class are about Frequency.
	 * All of these reports created by ChatGPT. 
	 * Not pretty but functional.
	 * I may come back and make them pretty later. TODO
	 * 
	 * The basic idea behind these reports is that a players frequencies, if they
	 * deviate from a calculated MDF, that player can be exploited.
	 * The other basic idea is that knowing an opponents frequecies, even if they were
	 * perfect, would allow:
	 * 		The simulator to simulate opponents mucj more accurately.
	 * 		For the simulator to be able to develop stratagies and ranges to be 
	 *		used against these opponents.
	 * 
	 * In any event, with all of this shit there must be a pony in there somewhere.
	 * We will ba analyzing the frequency data to see what ideas develop.
	 * 
	 * The bet size relative to pot size is broken down to 1/4 pot increments. 
	 * 		< 1/4, 1/4+, 1/2+, 3/4+,1+, 1 1/4+, 1 1/2+, 1 3/4+, 2+, Big, All-in
	 * The AllPlayers Class is used to save the data that will be analyzed 
	 * by PlayerBetSize and reported in this reports class.
	 * There are report Classes for bet, call, check, and fold.
	 * 
	 * @author PEAK_
	 ****************************************************************************** */

	// @formatter:off
	
	
	// @formatter:on		

	static void showBetSize_boardBetHMLGUI(PlayerBetSize pbs) {
		// Create the JFrame
		JFrame frame = new JFrame("betSize_boardBetHML");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Convert 3D array to 2D Object array for JTable
		Object[][] tableData = new Object[BET_SIZE_ACTION_MAX * BET_SIZE_POT_MAX * HML_SIZE][4];
		int row = 0;
		for (int i = 0; i < BET_SIZE_ACTION_MAX; i++) {
			for (int j = 0; j < BET_SIZE_POT_MAX; j++) {
				for (int k = 0; k < HML_SIZE; k++) {
					tableData[row][0] = i;
					tableData[row][1] = j;
					tableData[row][2] = k;
					tableData[row][3] = pbs.betSize_boardBetHML[i][j][k];
					row++;
				}
			}
		}

		// Column names
		String[] columnNames = { "Index 1", "Index 2", "Index 3", "Value" };

		// Create JTable
		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		// Add the table to the frame
		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

		// Display the window
		frame.pack();
		frame.setVisible(true);
	}

	static void showBetSize_boardPossibleBetGUI(PlayerBetSize pbs) {
		JFrame frame = new JFrame("betSize_boardPossibleBet");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[][] tableData = new Object[3 * BET_SIZE_ACTION_MAX * BET_SIZE_POT_MAX * POSSIBLE_MAX][5];
		int row = 0;
		for (int a = 0; a < 3; a++) {
			for (int b = 0; b < BET_SIZE_ACTION_MAX; b++) {
				for (int c = 0; c < BET_SIZE_POT_MAX; c++) {
					for (int d = 0; d < POSSIBLE_MAX; d++) {
						tableData[row][0] = a;
						tableData[row][1] = b;
						tableData[row][2] = c;
						tableData[row][3] = d;
						tableData[row][4] = pbs.betSize_boardPossibleBet[a][b][c][d];
						row++;
					}
				}
			}
		}

		String[] columnNames = { "Index 1", "Index 2", "Index 3", "Index 4", "Value" };
		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	static void showBetSize_playerWinShowdownBetGUI(PlayerBetSize pbs) {
		JFrame frame = new JFrame("betSize_playerWinShowdownBet");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[][] tableData = new Object[STREET * BET_SIZE_ACTION_MAX * BET_SIZE_POT_MAX][4];
		int row = 0;
		for (int i = 0; i < STREET; i++) {
			for (int j = 0; j < BET_SIZE_ACTION_MAX; j++) {
				for (int k = 0; k < BET_SIZE_POT_MAX; k++) {
					tableData[row][0] = i;
					tableData[row][1] = j;
					tableData[row][2] = k;
					tableData[row][3] = pbs.betSize_playerWinShowdownBet[i][j][k];
					row++;
				}
			}
		}

		String[] columnNames = { "Street", "Action Max", "Pot Max", "Value" };
		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	static void showBetSize_playerWinStreetBetGUI(PlayerBetSize pbs) {
		JFrame frame = new JFrame("betSize_playerWinStreetBet");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[][] tableData = new Object[STREET * BET_SIZE_ACTION_MAX * BET_SIZE_POT_MAX][4];
		int row = 0;
		for (int i = 0; i < STREET; i++) {
			for (int j = 0; j < BET_SIZE_ACTION_MAX; j++) {
				for (int k = 0; k < BET_SIZE_POT_MAX; k++) {
					tableData[row][0] = i;
					tableData[row][1] = j;
					tableData[row][2] = k;
					tableData[row][3] = pbs.betSize_playerWinStreetBet[i][j][k];
					row++;
				}
			}
		}

		String[] columnNames = { "Street", "Action Max", "Pot Max", "Value" };
		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	static void showBetSize_boardCallHMLGUI(PlayerBetSize pbs) {
		JFrame frame = new JFrame("betSize_boardCallHML");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[][] tableData = new Object[BET_SIZE_ACTION_MAX * BET_SIZE_POT_MAX * HML_SIZE][4];
		int row = 0;
		for (int i = 0; i < BET_SIZE_ACTION_MAX; i++) {
			for (int j = 0; j < BET_SIZE_POT_MAX; j++) {
				for (int k = 0; k < HML_SIZE; k++) {
					tableData[row][0] = i;
					tableData[row][1] = j;
					tableData[row][2] = k;
					tableData[row][3] = pbs.betSize_boardCallHML[i][j][k];
					row++;
				}
			}
		}

		String[] columnNames = { "Action Max", "Pot Max", "HML Size", "Value" };
		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	static void showBetSize_boardCallWetDryGUI(PlayerBetSize pbs) {
		JFrame frame = new JFrame("betSize_boardCallWetDry");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[][] tableData = new Object[BET_SIZE_ACTION_MAX * BET_SIZE_POT_MAX * 3][4];
		int row = 0;
		for (int i = 0; i < BET_SIZE_ACTION_MAX; i++) {
			for (int j = 0; j < BET_SIZE_POT_MAX; j++) {
				for (int k = 0; k < 3; k++) {
					tableData[row][0] = i;
					tableData[row][1] = j;
					tableData[row][2] = k;
					tableData[row][3] = pbs.betSize_boardCallWetDry[i][j][k];
					row++;
				}
			}
		}

		String[] columnNames = { "Action Max", "Pot Max", "Wet/Dry", "Value" };
		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	static void showBetSize_boardCallStaticDynamicGUI(PlayerBetSize pbs) {
		JFrame frame = new JFrame("betSize_boardCallStaticDynamic");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[][] tableData = new Object[BET_SIZE_ACTION_MAX * BET_SIZE_POT_MAX * 3][4];
		int row = 0;
		for (int i = 0; i < BET_SIZE_ACTION_MAX; i++) {
			for (int j = 0; j < BET_SIZE_POT_MAX; j++) {
				for (int k = 0; k < 3; k++) {
					tableData[row][0] = i;
					tableData[row][1] = j;
					tableData[row][2] = k;
					tableData[row][3] = pbs.betSize_boardCallStaticDynamic[i][j][k];
					row++;
				}
			}
		}

		String[] columnNames = { "Action Max", "Pot Max", "Static/Dynamic", "Value" };
		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	static void showBetSize_boardCallFlopGUI(PlayerBetSize pbs) {
		JFrame frame = new JFrame("betSize_boardCallFlop");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[][] tableData = new Object[BET_SIZE_ACTION_MAX * BET_SIZE_POT_MAX * FLOP_COMBO][4];
		int row = 0;
		for (int i = 0; i < BET_SIZE_ACTION_MAX; i++) {
			for (int j = 0; j < BET_SIZE_POT_MAX; j++) {
				for (int k = 0; k < FLOP_COMBO; k++) {
					tableData[row][0] = i;
					tableData[row][1] = j;
					tableData[row][2] = k;
					tableData[row][3] = pbs.betSize_boardCallFlop[i][j][k];
					row++;
				}
			}
		}

		String[] columnNames = { "Action Max", "Pot Max", "Flop Combo", "Value" };
		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	static void showBetSize_boardPossibleCallGUI(PlayerBetSize pbs) {
		JFrame frame = new JFrame("betSize_boardPossibleCall");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[][] tableData = new Object[3 * BET_SIZE_ACTION_MAX * BET_SIZE_POT_MAX * POSSIBLE_MAX][5];
		int row = 0;
		for (int a = 0; a < 3; a++) {
			for (int b = 0; b < BET_SIZE_ACTION_MAX; b++) {
				for (int c = 0; c < BET_SIZE_POT_MAX; c++) {
					for (int d = 0; d < POSSIBLE_MAX; d++) {
						tableData[row][0] = a;
						tableData[row][1] = b;
						tableData[row][2] = c;
						tableData[row][3] = d;
						tableData[row][4] = pbs.betSize_boardPossibleCall[a][b][c][d];
						row++;
					}
				}
			}
		}

		String[] columnNames = { "Index 1", "Action Max", "Pot Max", "Possible Max", "Value" };
		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	static void showBetSize_playerWinShowdownCallGUI(PlayerBetSize pbs) {
		JFrame frame = new JFrame("betSize_playerWinShowdownCall");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[][] tableData = new Object[STREET * BET_SIZE_ACTION_MAX * BET_SIZE_POT_MAX][4];
		int row = 0;
		for (int i = 0; i < STREET; i++) {
			for (int j = 0; j < BET_SIZE_ACTION_MAX; j++) {
				for (int k = 0; k < BET_SIZE_POT_MAX; k++) {
					tableData[row][0] = i;
					tableData[row][1] = j;
					tableData[row][2] = k;
					tableData[row][3] = pbs.betSize_playerWinShowdownCall[i][j][k];
					row++;
				}
			}
		}

		String[] columnNames = { "Street", "Action Max", "Pot Max", "Value" };
		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	static void showBetSize_playerWinStreetCallGUI(PlayerBetSize pbs) {
		JFrame frame = new JFrame("betSize_playerWinStreetCall");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[][] tableData = new Object[STREET * BET_SIZE_ACTION_MAX * BET_SIZE_POT_MAX][4];
		int row = 0;
		for (int i = 0; i < STREET; i++) {
			for (int j = 0; j < BET_SIZE_ACTION_MAX; j++) {
				for (int k = 0; k < BET_SIZE_POT_MAX; k++) {
					tableData[row][0] = i;
					tableData[row][1] = j;
					tableData[row][2] = k;
					tableData[row][3] = pbs.betSize_playerWinStreetCall[i][j][k];
					row++;
				}
			}
		}

		String[] columnNames = { "Street", "Action Max", "Pot Max", "Value" };
		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	static void showBetSize_drawGUI(PlayerBetSize pbs) {
		JFrame frame = new JFrame("betSize_draw");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Object[][] tableData = new Object[3 * BETS_MAX * POSSIBLE_MAX][4];
		int row = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < BETS_MAX; j++) {
				for (int k = 0; k < POSSIBLE_MAX; k++) {
					tableData[row][0] = i;
					tableData[row][1] = j;
					tableData[row][2] = k;
					tableData[row][3] = pbs.betSize_draw[i][j][k];
					row++;
				}
			}
		}

		String[] columnNames = { "Index", "Bets Max", "Possible Max", "Value" };
		JTable table = new JTable(tableData, columnNames);
		JScrollPane scrollPane = new JScrollPane(table);
		table.setFillsViewportHeight(true);

		frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

}
