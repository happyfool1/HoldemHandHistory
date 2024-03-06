package holdemhandhistory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GUIReports implements Constants {

	/*- *****************************************************************************
	 * Called by GUIAnalysis Class, there is no main in this Class.
	 * 
	 * This class is used to guide the details of what is in a this.
	 * Each poker website has a unique format for their Hand History files.
	 * But, the rules for Texas Holde'm are the same for every website.
	 * This makes it possible for this class to have a set of methods that are not 
	 * for a particular website but are potentially for all websites.
	 * For example:
	 * 		Players have names, are assigned to seat numbers, have finalStacks, .......
	 * 		A method like String getPlayerName(int seat) is not unique too any website.
	 * 
	 * Public methods:
	 * 		 printHandStart()  
	 * 	     errorCheck()
	 * 		summarizeAction()
	 * 		boolean writeToFile(String path)
	 * 		Hand readFromFile(String path)  
	 * 
	  *  @author PEAK_ 
	***************************************************************************** */

	/*-************************************************************************************************
	 * Check boxes for reports 
	************************************************************************************************ */
	private static final JCheckBox reportSummary = new JCheckBox("Summary");

	private static final JCheckBox reportPlayerStatsPreflop = new JCheckBox("Players Preflop");

	private static final JCheckBox reportPlayerStatsFlop = new JCheckBox("Players Flop");

	private static final JCheckBox reportPlayerStatsTurn = new JCheckBox("Players Turn");

	private static final JCheckBox reportPlayerStatsRiver = new JCheckBox("Players River");

	private static final JCheckBox reportPlayerStatsShowdown = new JCheckBox("Players Showdown");

	private static final JCheckBox reportAllAction = new JCheckBox("Action");

	private static final JCheckBox reportAllOper = new JCheckBox("Opportunity");

	private static final JCheckBox reportAllPer = new JCheckBox("Percentage");

	private static final JCheckBox reportAllActionB = new JCheckBox("Action");

	private static final JCheckBox reportAllOperB = new JCheckBox("Opportunity");

	private static final JCheckBox reportAllPerB = new JCheckBox("Oper Percentage");

	private static final JCheckBox reportAllPerBHands = new JCheckBox("Hand Percentage");

	private static final JCheckBox reportPreflopAll = new JCheckBox("Preflop");

	private static final JCheckBox reportFlopAll = new JCheckBox("Flop");

	private static final JCheckBox reportTurnAll = new JCheckBox("Turn");

	private static final JCheckBox reportRiverAll = new JCheckBox("River");

	private static final JCheckBox reportPreflopRp = new JCheckBox("Preflop");

	private static final JCheckBox reportFlopRp = new JCheckBox("Flop");

	private static final JCheckBox reportTurnRp = new JCheckBox("Turn");

	private static final JCheckBox reportRiverRp = new JCheckBox("River");

	private static final JCheckBox reportFlopOrbitsAll = new JCheckBox("Flop");

	private static final JCheckBox reportTurnOrbitsAll = new JCheckBox("Turn");

	private static final JCheckBox reportRiverOrbitsAll = new JCheckBox("River");

	private static final JCheckBox reportMDF = new JCheckBox("MDF");

	private static final JCheckBox reportClassification = new JCheckBox("Classification");

	private static final JCheckBox reportPercentages = new JCheckBox("Percentages");

	private static final JCheckBox reportOptimumPreflopRp = new JCheckBox("Preflop");

	private static final JCheckBox reportOptimumFlopRp = new JCheckBox("Flop");

	private static final JCheckBox reportOptimumTurnRp = new JCheckBox("Turn");

	private static final JCheckBox reporOptimumRiverRp = new JCheckBox("River");

	private static final JCheckBox reportMoneyBasicStats = new JCheckBox("Basic Statistics");

	private static final JCheckBox reportMoneybetSizeCountArrayAverageBet = new JCheckBox("Bet %");

	private static final JCheckBox reportMoneybetSizeAnalysisCombinedBet = new JCheckBox("Combined ");

	private static final JCheckBox reportMoneybetSizeAnalysisHMLBet = new JCheckBox("HML ");

	private static final JCheckBox reportMoneybetSizeAnalysisRpBet = new JCheckBox("Rel Pos ");

	private static final JCheckBox reportMoneybetSizeAnalysisBoardBet = new JCheckBox("Board ");

	private static final JCheckBox reportMoneybetSizeAnalysisBoardWetDryBet = new JCheckBox("Wet/Dry Static/Dynamic ");

	private static final JCheckBox reportFrequency = new JCheckBox("Frequency ");

	private static final JCheckBox reportMoneybetSizeCountArrayAverageCall = new JCheckBox("Call %");

	private static final JCheckBox reportMoneybetSizeAnalysisCombinedCall = new JCheckBox("Combined ");

	private static final JCheckBox reportMoneybetSizeAnalysisHMLCall = new JCheckBox("HML ");

	private static final JCheckBox reportMoneybetSizeAnalysisRpCall = new JCheckBox("Rel Pos ");

	private static final JCheckBox reportMoneybetSizeAnalysisBoardCall = new JCheckBox("Board ");

	private static final JCheckBox reportMoneybetSizeAnalysisBoardWetDryCall = new JCheckBox("Wet/Dry Static/Dynamic ");

	private static final JCheckBox reportMoneybetSizeCountArrayAverageFold = new JCheckBox("Fold %");

	private static final JCheckBox reportMoneybetSizeAnalysisCombinedFold = new JCheckBox("Combined ");

	private static final JCheckBox reportMoneybetSizeAnalysisHMLFold = new JCheckBox("HML ");

	private static final JCheckBox reportMoneybetSizeAnalysisRpFold = new JCheckBox("Rel Pos ");

	private static final JCheckBox reportMoneybetSizeAnalysisBoardFold = new JCheckBox("Board ");

	private static final JCheckBox reportMoneybetSizeAnalysisBoardWetDryFold = new JCheckBox("Wet/Dry Static/Dynamic ");

	private static final JCheckBox reportMoneybetSizeCountArrayAverageCheck = new JCheckBox("Check %");

	private static final JCheckBox reportMoneybetSizeAnalysisCombinedCheck = new JCheckBox("Combined ");

	private static final JCheckBox reportMoneybetSizeAnalysisHMLCheck = new JCheckBox("HML ");

	private static final JCheckBox reportMoneybetSizeAnalysisRpCheck = new JCheckBox("Rel Pos ");

	private static final JCheckBox reportMoneybetSizeAnalysisBoardCheck = new JCheckBox("Board ");

	private static final JCheckBox reportMoneybetSizeAnalysisBoardWetDryCheck = new JCheckBox(
			"Wet/Dry Static/Dynamic ");

	private static final JCheckBox reportBetCountsHML = new JCheckBox("HML bet counts");

	private static final JCheckBox reportBetCountsWetDry = new JCheckBox("Wet Dry bet counts");

	private static final JCheckBox reportBetCountsStaticDynamic = new JCheckBox("Static Dynamic bet counts");

	private static final JCheckBox reportBetCountsPossibleDraws = new JCheckBox("Draws bet counts");

	private static final JCheckBox reportC_BetHML = new JCheckBox("HML c-bet counts");

	private static final JCheckBox reportC_BetWetDry = new JCheckBox("Wet Dry c-bat counts");

	private static final JCheckBox reportC_BetStaticDynamic = new JCheckBox("Static Dynamic c-bet counts");

	private static final JCheckBox reportC_BetPossibleDraws = new JCheckBox("Draws c-bet counts");

	private static final JCheckBox reportPlayerClassification1 = new JCheckBox("Player Actions");

	private static final JCheckBox reportPlayerClassification2 = new JCheckBox("PlayerMoney");

	private static final JCheckBox reportPlayerClassification3 = new JCheckBox("Player Preflop");

	private static final JPanel panel0 = new JPanel();

	private static final JPanel panel1 = new JPanel();

	private static final JPanel panelA = new JPanel();

	private static final JPanel panelB = new JPanel();

	private static final JPanel panel2 = new JPanel();

	private static final JPanel panel3 = new JPanel();

	private static final JPanel panel4 = new JPanel();

	private static final JPanel panel5 = new JPanel();

	private static final JPanel panel6 = new JPanel();

	private static final JPanel panel7 = new JPanel();

	private static final JPanel panelBet = new JPanel();

	private static final JPanel panelFrequency = new JPanel();

	private static final JPanel panelCall = new JPanel();

	private static final JPanel panelCheck = new JPanel();

	private static final JPanel panelFold = new JPanel();

	private static final JPanel panel8 = new JPanel();

	private static final JPanel panel9 = new JPanel();

	private static final JPanel panel10 = new JPanel();

	private static final JPanel panelC = new JPanel();

	private static final JPanel panelType = new JPanel();

	private static final JButton buttonRun = new JButton("Run");

	private static final JButton buttonExit = new JButton("Exit");

	private static int selectedType = -1;

	private static String type = "Average";

	private static Players play;
	private static PlayerCharacteristics pcd;

	/*- ***********************************************************************************************
	 * Display Report Selection window 
	*********************************************************************************************** */
	static void selectReportsWindow(Players p, PlayerClassification pc) {
		play = p;
		final var frame = new JFrame("Hold'em  Select Reports    ");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setLocation(50, 450);
		frame.setPreferredSize(new Dimension(1100, 500));

		panel0.setLayout(new GridLayout(10, 2));
		final var f = new Font(Font.SERIF, Font.BOLD, 12);

		final var options1 = Box.createHorizontalBox();
		options1.setFont(f);
		panel1.setBorder(BorderFactory.createTitledBorder("Summary and Player Stats by street        "));
		options1.add(reportSummary);
		options1.add(reportPlayerStatsPreflop);
		options1.add(reportPlayerStatsFlop);
		options1.add(reportPlayerStatsTurn);
		options1.add(reportPlayerStatsRiver);
		options1.add(reportPlayerStatsShowdown);
		panel1.add(reportSummary);
		panel1.add(reportPlayerStatsPreflop);
		panel1.add(reportPlayerStatsFlop);
		panel1.add(reportPlayerStatsTurn);
		panel1.add(reportPlayerStatsRiver);
		panel1.add(reportPlayerStatsShowdown);
		panel0.add(panel1);

		final var optionsA = Box.createHorizontalBox();
		optionsA.setFont(f);
		panelA.setBorder(BorderFactory.createTitledBorder("All streets All orbits      Actions, Opportunity"));
		optionsA.add(reportAllAction);
		optionsA.add(reportAllOper);
		optionsA.add(reportAllPer);
		panelA.add(reportAllAction);
		panelA.add(reportAllOper);
		panelA.add(reportAllPer);
		panel0.add(panelA);

		final var optionsB = Box.createHorizontalBox();
		optionsB.setFont(f);
		panelB.setBorder(
				BorderFactory.createTitledBorder("All streets combined orbits    Actions, Opportunity, Percentage"));
		optionsB.add(reportAllActionB);
		optionsB.add(reportAllOperB);
		optionsB.add(reportAllPerB);
		optionsB.add(reportAllPerBHands);
		panelB.add(reportAllActionB);
		panelB.add(reportAllOperB);
		panelB.add(reportAllPerB);
		panelB.add(reportAllPerBHands);
		panel0.add(panelB);

		final var options2 = Box.createHorizontalBox();
		options2.setFont(f);
		panel2.setBorder(BorderFactory.createTitledBorder("Position - All players by Position and Orbit"));
		options2.add(reportPreflopAll);
		options2.add(reportFlopAll);
		options2.add(reportTurnAll);
		options2.add(reportRiverAll);
		panel2.add(reportPreflopAll);
		panel2.add(reportFlopAll);
		panel2.add(reportTurnAll);
		panel2.add(reportRiverAll);
		reportPreflopAll.setSelected(false); // TODO
		panel0.add(panel2);

		final var options4 = Box.createHorizontalBox();
		options4.setFont(f);
		panel4.setBorder(
				BorderFactory.createTitledBorder("Relative Position - All players by Relative Position and Orbit"));
		options4.add(reportPreflopRp);
		options4.add(reportFlopRp);
		options4.add(reportTurnRp);
		options4.add(reportRiverRp);
		panel4.add(reportPreflopRp);
		panel4.add(reportFlopRp);
		panel4.add(reportTurnRp);
		panel4.add(reportRiverRp);
		panel0.add(panel4);

		final var options5 = Box.createHorizontalBox();
		options5.setFont(f);
		panel5.setBorder(
				BorderFactory.createTitledBorder("Relative Position - All players by Relative Position and Orbit"));
		options5.add(reportFlopOrbitsAll);
		options5.add(reportTurnOrbitsAll);
		options5.add(reportRiverOrbitsAll);
		panel5.add(reportFlopOrbitsAll);
		panel5.add(reportTurnOrbitsAll);
		panel5.add(reportRiverOrbitsAll);
		panel0.add(panel5);

		final var options3 = Box.createHorizontalBox();
		options3.setFont(f);
		panel3.setBorder(BorderFactory.createTitledBorder("Optimum Ranges"));
		options3.add(reportOptimumPreflopRp);
		options3.add(reportOptimumFlopRp);
		options3.add(reportOptimumTurnRp);
		options3.add(reporOptimumRiverRp);
		panel3.add(reportOptimumPreflopRp);
		panel3.add(reportOptimumFlopRp);
		panel3.add(reportOptimumTurnRp);
		panel3.add(reporOptimumRiverRp);
		panel0.add(panel3);

		final var options6 = Box.createHorizontalBox();
		options6.setFont(f);
		panel6.setBorder(BorderFactory.createTitledBorder("MDF and Clean Reports"));
		options6.add(reportMDF);
		options6.add(reportClassification);
		options6.add(reportPercentages);
		panel6.add(reportMDF);
		panel6.add(reportClassification);
		panel6.add(reportPercentages);
		panel0.add(panel6);

		final var optionsBet = Box.createHorizontalBox();
		optionsBet.setFont(f);
		panelBet.setBorder(BorderFactory.createTitledBorder("Money Bet"));
		optionsBet.add(reportMoneyBasicStats);
		optionsBet.add(reportMoneybetSizeCountArrayAverageBet);
		optionsBet.add(reportMoneybetSizeAnalysisCombinedBet);
		optionsBet.add(reportMoneybetSizeAnalysisHMLBet);
		optionsBet.add(reportMoneybetSizeAnalysisRpBet);
		optionsBet.add(reportMoneybetSizeAnalysisBoardBet);
		optionsBet.add(reportMoneybetSizeAnalysisBoardWetDryBet);
		panelBet.add(reportMoneyBasicStats);
		panelBet.add(reportMoneybetSizeCountArrayAverageBet);
		panelBet.add(reportMoneybetSizeAnalysisCombinedBet);
		panelBet.add(reportMoneybetSizeAnalysisHMLBet);
		panelBet.add(reportMoneybetSizeAnalysisRpBet);
		panelBet.add(reportMoneybetSizeAnalysisBoardBet);
		panelBet.add(reportMoneybetSizeAnalysisBoardWetDryBet);
		panel0.add(panelBet);

		final var optionsFrequency = Box.createHorizontalBox();
		optionsFrequency.setFont(f);
		panelFrequency.setBorder(BorderFactory.createTitledBorder("Frequency Analysis"));
		optionsBet.add(reportFrequency);
		panelFrequency.add(reportFrequency);
		panel0.add(panelFrequency);

		final var optionsCall = Box.createHorizontalBox();
		optionsCall.setFont(f);
		panelCall.setBorder(BorderFactory.createTitledBorder("Money Call"));
		optionsCall.add(reportMoneybetSizeCountArrayAverageCall);
		optionsCall.add(reportMoneybetSizeAnalysisCombinedCall);
		optionsCall.add(reportMoneybetSizeAnalysisHMLCall);
		optionsCall.add(reportMoneybetSizeAnalysisRpCall);
		optionsCall.add(reportMoneybetSizeAnalysisBoardCall);
		optionsCall.add(reportMoneybetSizeAnalysisBoardWetDryCall);
		panelCall.add(reportMoneybetSizeCountArrayAverageCall);
		panelCall.add(reportMoneybetSizeAnalysisCombinedCall);
		panelCall.add(reportMoneybetSizeAnalysisHMLCall);
		panelCall.add(reportMoneybetSizeAnalysisRpCall);
		panelCall.add(reportMoneybetSizeAnalysisBoardCall);
		panelCall.add(reportMoneybetSizeAnalysisBoardWetDryCall);
		panel0.add(panelCall);

		final var optionsCheck = Box.createHorizontalBox();
		optionsCheck.setFont(f);
		panelCheck.setBorder(BorderFactory.createTitledBorder("Money Check"));
		optionsCheck.add(reportMoneybetSizeCountArrayAverageCheck);
		optionsCheck.add(reportMoneybetSizeAnalysisCombinedCheck);
		optionsCheck.add(reportMoneybetSizeAnalysisHMLCheck);
		optionsCheck.add(reportMoneybetSizeAnalysisRpCheck);
		optionsCheck.add(reportMoneybetSizeAnalysisBoardCheck);
		optionsCheck.add(reportMoneybetSizeAnalysisBoardWetDryCheck);
		panelCheck.add(reportMoneybetSizeCountArrayAverageCheck);
		panelCheck.add(reportMoneybetSizeAnalysisCombinedCheck);
		panelCheck.add(reportMoneybetSizeAnalysisHMLCheck);
		panelCheck.add(reportMoneybetSizeAnalysisRpCheck);
		panelCheck.add(reportMoneybetSizeAnalysisBoardCheck);
		panelCheck.add(reportMoneybetSizeAnalysisBoardWetDryCheck);
		panel0.add(panelCheck);

		final var optionsFold = Box.createHorizontalBox();
		optionsFold.setFont(f);
		panelFold.setBorder(BorderFactory.createTitledBorder("Money Fold"));
		optionsFold.add(reportMoneybetSizeCountArrayAverageFold);
		optionsFold.add(reportMoneybetSizeAnalysisCombinedFold);
		optionsFold.add(reportMoneybetSizeAnalysisHMLFold);
		optionsFold.add(reportMoneybetSizeAnalysisRpFold);
		optionsFold.add(reportMoneybetSizeAnalysisBoardFold);
		optionsFold.add(reportMoneybetSizeAnalysisBoardWetDryFold);
		panelFold.add(reportMoneybetSizeCountArrayAverageFold);
		panelFold.add(reportMoneybetSizeAnalysisCombinedFold);
		panelFold.add(reportMoneybetSizeAnalysisHMLFold);
		panelFold.add(reportMoneybetSizeAnalysisRpFold);
		panelFold.add(reportMoneybetSizeAnalysisBoardFold);
		panelFold.add(reportMoneybetSizeAnalysisBoardWetDryFold);
		panel0.add(panelFold);

		final var options8 = Box.createHorizontalBox();
		options8.setFont(f);
		panel8.setBorder(BorderFactory.createTitledBorder("Counts by board index"));
		options8.add(reportBetCountsHML);
		options8.add(reportBetCountsWetDry);
		options8.add(reportBetCountsStaticDynamic);
		options8.add(reportBetCountsPossibleDraws);
		panel8.add(reportBetCountsHML);
		panel8.add(reportBetCountsWetDry);
		panel8.add(reportBetCountsStaticDynamic);
		panel8.add(reportBetCountsPossibleDraws);
		panel0.add(panel8);

		final var options9 = Box.createHorizontalBox();
		options9.setFont(f);
		panel9.setBorder(BorderFactory.createTitledBorder("C-Bet counts by board index"));
		options9.add(reportC_BetHML);
		options9.add(reportC_BetWetDry);
		options9.add(reportC_BetStaticDynamic);
		options9.add(reportC_BetPossibleDraws);
		panel9.add(reportC_BetHML);
		panel9.add(reportC_BetWetDry);
		panel9.add(reportC_BetStaticDynamic);
		panel9.add(reportC_BetPossibleDraws);
		panel0.add(panel9);

		final var options10 = Box.createHorizontalBox();
		options10.setFont(f);
		panel10.setBorder(BorderFactory.createTitledBorder("Player Actions, Money, Preflop"));
		options10.add(reportPlayerClassification1);
		options10.add(reportPlayerClassification2);
		options10.add(reportPlayerClassification3);
		panel10.add(reportPlayerClassification1);
		panel10.add(reportPlayerClassification2);
		panel10.add(reportPlayerClassification3);
		panel0.add(panel10);

		buttonRun.addActionListener(new Listener());
		buttonRun.setToolTipText("Run selected reports for player type");
		buttonRun.setBackground(Color.GREEN);
		buttonExit.addActionListener(new Listener());
		buttonExit.setToolTipText("Exit");
		buttonExit.setBackground(Color.GREEN);

		final var control = Box.createHorizontalBox();
		final var fff = new Font(Font.SERIF, Font.BOLD, 16);
		control.setFont(fff);
		panelC.add(buttonRun);
		panelC.add(buttonExit);
		panelC.add(control);
		panel0.add(panelC);
		frame.add(panel0);

		frame.pack();
		frame.setVisible(true);
	}

	/*- ***********************************************************************************************
	*	Reports. 
	* The check boxes are checked for selected
	* If selected then the report is created.
	************************************************************************************************/
	private static void reports() {
		PlayerCharacteristics pc = new PlayerCharacteristics();
		if (reportPlayerStatsPreflop.isSelected()) {
			PlayerCharacteristicsReport.playerStats(pc);
		}
		if (reportPlayerStatsFlop.isSelected()) {
			PlayerCharacteristicsReport.playerStatsFlop(pc);
		}
		if (reportPlayerStatsTurn.isSelected()) {
			PlayerCharacteristicsReport.playerStatsTurn(pc);
		}
		if (reportPlayerStatsRiver.isSelected()) {
			PlayerCharacteristicsReport.playerStatsRiver(pc);
		}
		if (reportPlayerStatsShowdown.isSelected()) {
			PlayerCharacteristicsReport.playerStatsShowdown(pc);
		}

		if (reportAllAction.isSelected()) {
			// ReportsStreetPos.allActionPos(play, pc);
		}
		if (reportAllOper.isSelected()) {
			// ReportsStreetPos.allOperPos(play, pc);
		}
		if (reportAllPer.isSelected()) {
			// ReportsStreetPos.allPerPos(play, pc);
		}

		if (reportAllActionB.isSelected()) {
			// ReportsPDStreetPos.allActionPos(play, pc);
		}
		if (reportAllOperB.isSelected()) {
			// ReportsPDStreetPos.allOperPos(play, pc);
		}
		if (reportAllPerB.isSelected()) {
			// ReportsPDStreetPos.allPerPos(play, pc);
		}
		if (reportAllPerBHands.isSelected()) {
			// ReportsPDStreetPos.allPerPosHands(play, pc);
		}

		if (reportPreflopAll.isSelected()) {
			// ReportsAll.MDFDataPos(play, pc);
			// ReportsAll.playerClassification(play, pc);
			// ReportsAll.playerClassification2(play, pc);
			// ReportsAll.playerDataCounts(play, pc);
		}

		if (reportMoneybetSizeCountArrayAverageBet.isSelected()) {
			// ReportsMoneyBetA.betSizeCountArrayAveragePot(play, pc);
		}
		if (reportMoneyBasicStats.isSelected()) {
			// ReportsMoneyBetA.basicStatsMoney(play, pc);
		}
		if (reportMoneybetSizeAnalysisCombinedBet.isSelected()) {
			// ReportsMoneyBetA.betSizeAnalysisCombinedBet(play, pc);
			// ReportsMoney.betSizeAnalysisCombinedCall(play,pc);
		}
		if (reportMoneybetSizeAnalysisHMLBet.isSelected()) {
			// ReportsMoneyBetB.betSizeAnalysisBetHML(play, pc);
		}
		if (reportMoneybetSizeAnalysisRpBet.isSelected()) {
			// ReportsMoneyBetB.betSizeAnalysisBetRp(play, pc);
		}
		if (reportMoneybetSizeAnalysisBoardBet.isSelected()) {
			// ReportsMoneyBetB.betSizeAnalysisBoard(play, pc);
		}
		if (reportMoneybetSizeAnalysisBoardWetDryBet.isSelected()) {
			// ReportsMoneyBetB.betSizeAnalysisBoardBetWetDry(play, pc);
		}

		if (reportFrequency.isSelected()) {
			// ReportsFrequency.frequencyByStreetBetAndStep(play, pc);
		}

		if (reportBetCountsHML.isSelected()) {
			// ReportsBoard.betCountsHML(play, pc);
		}
		if (reportBetCountsWetDry.isSelected()) {
			// ReportsBoard.betCountsWetDry(play, pc);
		}
		if (reportBetCountsStaticDynamic.isSelected()) {
			// ReportsBoard.betCountsStaticDynamic(play, pc);
		}
		if (reportBetCountsPossibleDraws.isSelected()) {
			// ReportsBoard.betCountsDraws(play, pc);
		}
		if (reportC_BetHML.isSelected()) {
			// ReportsBoard.c_BetHML(play, pc);
		}
		if (reportC_BetWetDry.isSelected()) {
			// ReportsBoard.c_BetWetDry(play, pc);
		}
		if (reportC_BetStaticDynamic.isSelected()) {
			// ReportsBoard.c_BetStaticDynamic(play, pc);
		}
		if (reportC_BetPossibleDraws.isSelected()) {
			/// ReportsBoard.c_BetDraws(play, pc);
		}

		if (reportPreflopRp.isSelected()) {

		}
		if (reportFlopAll.isSelected()) {

		}
		if (reportFlopOrbitsAll.isSelected()) {

		}
		if (reportTurnAll.isSelected()) {

		}
		if (reportTurnOrbitsAll.isSelected()) {

		}
		if (reportRiverAll.isSelected()) {

		}
		if (reportRiverOrbitsAll.isSelected()) {

		}
		if (reportOptimumPreflopRp.isSelected()) {

		}
		if (reportOptimumFlopRp.isSelected()) {

		}
		if (reportOptimumTurnRp.isSelected()) {

		}
		if (reporOptimumRiverRp.isSelected()) {

		}
	}

	/*- ***********************************************************************************************-
	 *  Responds to button clicks. 
	***********************************************************************************************  */
	static class Listener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent a0) {
			switch (a0.getActionCommand()) {
			case "Run":
				reports();
				break;
			}
		}
	}

}
