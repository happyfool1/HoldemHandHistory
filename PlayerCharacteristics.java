package holdemhandhistory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class PlayerCharacteristics implements java.io.Serializable, Constants {
	static final long serialVersionUID = 1234567L;

	/*-***********************************************************************************************************************
	 * This class is part of a group of classes, all of which hold calculated data about
	 * players. The data is obtained from the Players class.
	 * 
	 * The Players class contains only data collected from parsing Hand History files
	 * in the Universal format defined by the Hand class and the Action class.
	 * There are no calculated values in Players and no other class is used to save
	 * data obtained from parsing.
	 * 
	 * The classes in this group all hold calculations only. The methods that do the 
	 * calculation are in the class with the calculated data.
	 * It is hoped that other programmers will participate in this project by taking
	 * ownership of one of these classes or by creating an all new class that will be
	 * part of this group.  Part of the group are a group of classes that report on the
	 * calculated data. One reporting class with that corresponds to the calculating class.
	 * Names of classes are consistent, PlayerCharacteristics, and ReportPlayerCharacteristics.
	 * 
	 * The core functions are complete. Filtering Hand History files. Parsing the filtered files
	 * and creating Universal Format files, classifying players by type and other characteristics
	 * to create list of players to be analyzed as a group, and parsing the Universal Format files
	 * to create Players objects.
	 * Most of the remaining effort will be in the calculation and reporting classes.
	 * 
	 * Each of the calculation classes has a method:
	 * doCalculations(Players play, PlayerCharactistics pc){}
	 * Calculations are done using data in the Players class and the PlayerCharactistics class.
	 * 
	 * All of the calculation classes share a common key, the player ID. The player ID can either 
	 * identify a specific player or a group if players.
	 * 
	 * The calculation classes do not sort or select players. That has already been done.
	 * 
	 * These classes are all able to write .ser files.
	 * 
	 * These classes are all able to create a single instance of a class object by totalling 
	 * all of the calculated variables and dividing them by the number if instances of the class
	 * that were used to create the composite object. So for example, one instance of 
	 * PlayerCharacteristics may represent thousands or even millons of players within the same 
	 * characteristics. They all have a sumAll method and a divideAll method.
	 * sumAll(PlayersX x){} Adds all data from the x instance to this.
	 * divideAll() divides all variables by the number of instances added to the class.
	 * 
	 *  @author PEAK_ 
	*********************************************************************************************************************** */

	/*-***********************************************************************************************************************
	 * This Class does player evaluations. 
	 * There are several methods that perform different evaluations.
	 * This is a new Class that is just now being defined.
	 * The idea here is to expand to include many of the Holdem Manager 3 statistics.
	 * But it is not to compete with the HM 3 functionality. 
	 * It is because of the statistic is valuable to a player using a HUD, then it is
	 * valuable to a simulator exploiting the average player, or exploiting a player 
	 * by type such as NIT, LAG, TAG, Fish, winner, looser, etc..
	 * 
	 * Methods in this Class have Arg0 as an instance of the Players class or an 
	 * instance of the PLayerData Class.
	 * It is with this class instance that the calculations are performed.
	 * By having the calculations in a seperate class ( this one ) then they can be used 
	 * either with Players orPlayerCharacteristics.
	 * 
	 * Player statistics and classification
	 * Calculate values for one player.
	 * After all Hand History files have been processed there are many instances 
	 * from.class, one for each player.
	 * Data that is in from.class instance will be used to classify thie particular player.
	 * This must be done on the individual player level.
	 * This classifications will be used to filter players by type.
	 * 		Players of a type, such as LAG, will be combined into a Player class that represrnts
	 * 		many players of the same classification.
	 * 
	 * For example, the VPIP is calculated here.
	 * 		Voluntarily Put Money In Pot VPIP
	 * 		Does not include blinds.
	 * 		The  VPIP is a key factor in categorizing a player
		 ***********************************************************************************************************************
	 * Holdem Manager 3 is the model that we use for what to save and calculate. 
	 * The primary focus is on the 10 HUD variables. We use the Holdem Manager
	 * definitions. We verify the calculations by importing the same Hand History 
	 * files that we processed here into Holdem Manager and compare the results. 
	 * The 10 are:
	 * 		VPIP: Voluntarily Put [Money] Into Pot
	 *		PFR:  Raise
	 *		3-Bet Frequency
	 *		WTSD: Went To Showdown
	 * 		W$SD/WSD: Won Money At Showdown
	 *		WWSF: Won When Saw 
	 *		Fold to 3-Bet After Raising
	 *		 Squeeze
	 * 		 C-Bet
	 * 		Fold to  C-Bet
	 * 
	 * Row 1
	 *Note Icon / Player Name / Total Hands / 3Bet Out of Position / 3Bet In Position / Fold to 3Bet Out of Position 
	 * / Fold to 3Bet In Position
	 *
	 * Row 2
	 * VPIP (Voluntarily Put $ Into Pot) / PFR ( Raise) / Agg (Aggression) 
	  * / WTSD% (Went to Showdown) /  W$SD% (Won $ at Showdown) / W$WSF (Won $ When Saw ) 
	  *
	  * Row 3
	  * Early Position RFI (Raise First In) / Middle Position RFI / Cutoff RFI / Button RFI /  Small Blind RFI
	  *
	  * Row 4
	  * CB OOP  (CBet Out of Position) / CB OOP Turn / CB OOP River 
	  * / CB IP  (CBet In Position / CB IP Turn / CB IP River
	  *
	  * Row 5
	* FvsCB OOP  (Fold vs CBet Out of Position) / FvCB OOP Turn / FvCB OOP 
	* River / FvsCB IP  (Fold vs CBet In Position) / FvCB IP Turn / FvCB IP River 
	* The 10 Most Important Poker Statistics for Your HUD
	* Click any of these stats to jump to a fundamental explanation of what it is, why it’s important, and how to use it:
	*
	* VPIP: Voluntarily Put [Money] Into Pot
	* PFR:  Raise
	* 3-Bet Frequency
	* WTSD: Went To Showdown
	* W$SD/WSD: Won Money At Showdown
	* WWSF: Won When Saw 
	* Fold to 3-Bet After Raising
	*  Squeeze
	*  C-Bet
	* Fold to  C-Bet
	*
	* 1. VPIP: Voluntarily Put [Money] Into Pot
	* This is a must-have stat on your HUD.
	*
	* VPIP shows you how often your opponent has voluntarily put money into the pot , 
	* either by raising or calling. 
	* This is fundamental information for player profiling, especially when correlated with  Raise (PFR).
	*
	* A player that has at least a basic  understanding will generally VPIP around 20-30% 
	* of the time in a 6-handed game, with 25% being very close to the norm. 
	* If a player is playing significantly less hands, they are probably a nit. 
	* And if they are playing a lot more hands, they are likely a recreational player.
	*
	* You will need around 300 hands on from.player to be confident enough in what the stat shows you. 
	* That said, players on either extreme can oftentimes be identified sooner. 
	* For example, if a player has a 70% VPIP after 50 hands, it is very likely they are a loose recreational player.
	*
	* 2. PFR:  Raise
	* PFR tells you how often a player has entered the pot  by raising. 
	* This includes raising first in, 3-betting, and cold 4/5-betting.
	*
	* This statistic creates even more context for your opponent’s  strategy. 
	* When used in conjunction with VPIP, it will be enough to form a player profile.
	*
	* A player that has at least a basic understanding of  strategy will have 
	* between 15-25%  raise, with 19% being close to the norm.
	*
	* Similarly to VPIP, you will need around 300 hands on from.player to be confident  
	* enough in the number you are seeing.
	*
	* 3. 3-Bet
	* This is an important stat to have in your arsenal as it shows how often your 
	* opponent 3-bets before the. It will prove useful for building both your 
	*  opening ranges and your defending range against your opponent’s 3-bet.
	*
	* A good overall 3-betting frequency will be something around 6-10%, 
	* with 8% being close to the average for good players.
	*
	* Some adjustments that you can make vs tight or loose 3-bettors are:
	*
	* When a very aggressive 3-bettor is behind, open-raise a slightly tighter range than usual.
	* With only tight 3-bettors behind, you can profitably open-raise a slightly wider range.
	* When you face a 3-bet from an aggressive player, continue more often by 
	* 4-betting and calling with more hands than usual.
	* For an accurate read on the stat, you will need around 1000 hands on a player.
	*
	* 4. WTSD: Went To Showdown
	* This is a very important postflop statistic that tells you how frequently a player reaches
	* showdown after seeing a flop. It is useful for identifying how much of a calling station your opponent is.
	* 
	* For example, if a player saw the flop10 times and went to showdown 4 times in a session, 
	* that player’s WTSD is 40% for that session.
	* 
	* This stat is to be used in conjunction with Won Money at Showdown (W$SD or WSD)
	* and Won When Saw  (WWSF), which I will go over shortly.
	*
	* A good WTSD frequency is somewhere around 27-32%, with 30% being a good place to aim for.
	* Too low and you are probably over-folding postflop; too high and you are probably calling too often.
	*
	* Since the vast majority of pots do not go past  (only 17% of hands see the), 
	* from.stat requires a much bigger sample for an accurate read to be made
	* — aim to have around 8,000 hands on a player before making notable adjustments based on WTSD.
	*
	* 5. WSD: Won Money at Showdown
	* Also abbreviated as W$SD, from.stat tells you how often your opponent has won when 
	* they reached showdown. As alluded to above, from.stat isn’t too helpful on his own, 
	* but it can be helpful when used in conjunction with WTSD.
	*
	* A good W$SD is somewhere between 49% and 54%. A correct frequency is dependent 
	* on the other two statistics mentioned. For example, a player who has a low 
	* Went to Showdown (WTSD) frequency will usually have a relatively high WSD and vice versa. 
	* In other words, if you rarely reach showdown, it’s probably because you’re a tight player 
	* who usually has a strong hand when you go the distance in a hand.
	*
	* I	n general, if your WSD is too low, then it means you are probably calling too many bad 
	* hands and/or bluffing too much earlier in the hand. If it’s too high, it means that you are
	* probably either not bluff-catching enough and/or not bluffing enough.
	*
	* The sample required for a decently accurate read is the same as WTSD, above 8,000 hands.
	*
	* 6. WWSF: Won When Saw 
	* Tying up the 3 statistics that work together…
	*
	* WWSF refers to how often your opponent has won the pot after seeing the.
	*
	* A decent WWSF frequency is anywhere between 45% and 53%, 
	* with a good average being around 48%. Too low? 	
	* That means that your opponent is not bluffing enough and/or giving up too much. 
	* Too high? That means that you are bluffing and/or bluff-catching too much.
	*
	* The sample required for a reasonably accurate read is the same as the one for the previous two:
	* 8,000 or more hands.
	*
	* How WTSD, WSD, and WWSF Work Together
	* Each one of these stats provide important context for the others, which will allow you to draw 
	* major conclusions about your opponents.
	*
	* Let’s consider a few example players.
	*
	* (Remember that: WTSD = Went To Showdown, WSD = Won Money At Showdown, and WWSF = Won When Saw )
	*
	* Player A: WTSD: 32 / WSD: 51 / WWSF: 46
	*
	* This player is more or less a passive calling station. 
	* He has a high WTSD, but he’s apparently calling pretty light to since 
	* he’s only winning 51% of the time at showdown. He’s also not very aggressive, hence the low WWSF.
	*
	* The degree to which Player A is a calling station will be clearer by looking at his VPIP. 
	*A high VPIP, like 40%, means from.player plays a lot of hands and doesn’t do much folding postflop. 
	* Your adjustment against such a player should be to go for more thin value bets and fewer bluffs.
	*
	* Player B: WTSD: 26/ WSD: 56/ WWSF: 44
	*
	* This type of player rarely goes to showdown, but it’s clearly not due to aggression 
	* because he also has a low WWSF. Player B is likely a quite tight player who folds quite often postflop
	* — a conclusion we can draw from his high WSD.
	*
	* Player C: WTSD: 30/ WSD: 52/ WWSF: 49
	*
	* Assuming from.player has decent  stats (~25% VPIP), Player C is quite the terror at the table. 
	* She doesn’t seem to fold too much or too little based on her WTSD. 
	* She’s also clearly aggressive and actively trying to steal pots, indicated by the high WWSF. 
	* Expect to face a lot of tough decisions against a player like from.
	*
	* 7. Fold to 3-Bet After Raising
	* This statistic tells you how often your opponent has folded to a 3-bet after raising .
	*
	* Important warning: When you’re adding from.to your HUD, you will also see a plain 
	* “Fold to 3-Bet” stat — don’t pick that one. Make sure “after raising” is specified in some way. 
	* The plain fold to 3-bet stat also includes the hands in which the player 
	* hadn’t put money into the pot, but had folded to a 3-bet 
	* (e.g. they fold in the big blind after the cutoff raised and the button 3-bet).
	*
	* This stat should be further sub-divided into Out of Position (OOP) and In Position (IP) 
	* because the correct frequencies are different for each of them. Given the same 3-bet size, 
	* you should fold more when OOP than when IP because of the power that being in position 
	* grants you (realizing equity better).
	*
	* The appropriate folding frequencies are somewhere around 40-45% when IP and 45-50% when OOP.
	*
	* The sample size needed here is around 1,500 hands.
	*
	* 8.  Squeeze
	*  squeeze tells you how often a player has re-raised after another player has raised first in 
	* and someone else cold-called. 
	* This stat is useful for determining how much you should defend against 3-bet squeezes.
	*
	* A typical squeeze frequency is around 7-9%. This means that if you see someone rocking a 12% squeeze,
	* for example, you can start calling and 4-betting lighter.
	*
	* The sample needed here is pretty high, upwards of 3,000 hands or so, due to how rare the situation is.
	*
	* 9.  C-Bet
	* This statistic refers to how often a player has continuation bet (c-bet) on the after raising . 
	* This stat needs to be divided into 3-bet pots and single raised pots and then further 
	* sub-divided into In Position and Out of Position.
	*
	* Without going into too much detail (as it’s a complex topic and beyond the scope of from.article), 
	* there can be large fluctuations in what the correct frequencies are for each of them.
	*
	* In general, it’s better to have a high c-bet when in position and when out of position in 3-bet pots
	* (50-70%, but lower can be good as well). It’s usually better to be on the lower side when out of
	* position in single raised pots (0-30%, but higher can be good too).
	*
	* The sample size is not that important here since the fluctuations can be huge. 
	* What is important, however, is getting a general sense of what your opponent’s 
	* approach is. 
	* If you really want a figure, I’d estimate you need at least a few thousand hands on a player 
	* to make from.stat reliable.
	*
	* 10. Fold to  C-bet
	* Fold to  C-Bet tells you how often a player has called a raise  and then folded to a 
	* continuation bet on the. 
	* As with  C-bet, from.stat needs to be divided for single raised pots and 3-bet pots 
	* and then further sub-divided into In Position and Out of Position.
	* 
	* The correct frequency will depend on the bet size that is used, so it’s hard to give precise numbers
	* to look/aim for. Generally speaking, the folding frequency should be on the lower side — below 50%.
	*
	* The sample size is not that important since the fluctuations are big depending on bet sized
	* used on average. If you really want a figure, I’d (again) estimate you need at least a few
	* thousand hands on a player to make from.stat reliable. 
	*
	* Name										Players name
	* Win Rate									Players winrate in bb/100
	* Total Hands								Total hands played
	* Net$ Won									Amount won
	* 
	* VPIP											Pct of hands where player voluntarily put money into the pot
	* PFR											Pct of hands where player raised preflop
	* WTSD% (When Saw Flop)		Pct of hands where player went to showdown
	* WonSD%									Pct of hands player won at showdown
	* Cold Call Raiser						   Pct of hands player calls preflop when facing a raiser
	* 3Bet											Pct of hands player raises (3Bets) preflop when facing single raised pot
	* Call 3Bet									Pct of hands player calls preflop when facing a 3Bet
	* Fold to 3Bet								Pct of hands player folds preflop when facing a 3Bet
	* 4Bet (Raise 3Bet)						Pct of hands player 4Bets ( reraises preflop when facing 3Bet)
	* Call Hero 3Bet							Pct of hands player calls preflop when facing hero's 3Bet
	* Fold to Hero 3Bet					    Pct of hands player folds preflop when facing hero's 3Bet
	* 4Bet vs Hero 3Bet					Pct of hands player 4Bets (reraises preflop when facing hero's 3Bet)
	*
	* Squeeze									Pct of hands player raises (3Bets) preflop when facing a raise and one or more callers
	* Limp											Pct of hands player calls preflop when facing unraised pot
	* MinRaise vs UnRaised Pot		Pct of hands player raises preflop minimum amount (2 big blinds)
	*
	* 4Bet Range								Pct of hands player 4bets - based on PFR% in unraised pots x Raise 3Bet%
	* 5Bet Range								Pct of hands player 5bets - based on 3Bet% x Raise 4Bet%
	* Cold 4-Bet+								Pct of hands player raises preflop when facing two or more raisers
	*
	*
	* RFI												Pct of hands player raises first in unopened pot preflop
	* Steal											Pct of hands player raises first in unopened pot preflop from CO, BTN or SB
	* Open MinRaise							Pct of hands player raises preflop minimum amount (2 big blinds) in unopened pot
	* Fold to Steal								Pct of hands player folds when facing an attempted steal
	* Call Steal									Pct of hands player calls when facing an attempted steal
	* ReSteal (3Bet vs Steal)				Pct of hands player raises (3Bets) when facing an attempted steal
	* 3Bet ReSteal vs Hero				Pct of hands player raises (3Bets) when facing an attempted steal from hero
	* SB Fold to Steal						Pct of hands player folds on SB when facing an attempted steal
	* 4Bet Call Steal							Pct of hands player calls on SB when facing an attempted steal
	* 4Bet ReSteal (3Bet vs Steal)	Pct of hands player raises (3Bets) on SB when facing an attempted steal
	* 4Bet 3Bet ReSteal vs Hero		Pct of hands player raises (3Bets) on SB when facing an attempted steal from hero
	* 	 Fold to Steal							Pct of hands player folds on BB when facing an attempted steal
	* BB  Call Steal							Pct of hands player calls on BB when facing an attempted steal
	* BB  ReSteal (3Bet vs Steal)		Pct of hands player raises (3Bets) on BB when facing an attempted steal
	* BB  3Bet ReSteal vs Hero			Pct of hands player raises (3Bets) on * BB  when facing an attempted steal from hero
	*
	* Fold vs Resteal							Pct of hands player folds after trying to steal and having been 3bet
	* Call Resteal								Pct of hands player calls after trying to steal and having been 3bet
	* 4Bet v Resteal							Pct of hands player raises (4Bets) after trying to steal and having been 3bet
	* 4Bet Fold vs Resteal					Pct of hands player folds after trying to steal on SB and having been 3bet
	* 4Bet Call Resteal						Pct of hands player calls after trying to steal on SB and having been 3bet
	* 4Bet 4Bet vs Resteal				Pct of hands player raises (4Bets) after trying to steal on SB and having been 3bet
	*
	* 4Bet Fold to BTN Steal			Pct of hands player folds when in SB facing an attempted BTN steal
	* BB  Fold to BTN Steal				Pct of hands player folds when in BB facing an attempted BTN steal
	* BB  Fold to SB Steal					Pct of hands player folds when in BB facing an attempted SB steal
	* Fold SB vs Steal from Button Hero	Pct of hands player folds when in SB facing an attempted BTN steal from hero
	* Fold BB vs Steal from Button Hero	Pct of hands player folds when in BB facing an attempted BTN steal from hero
	* Fold BB vs Steal from Small Blind Hero	Pct of hands player folds when in BB facing an attempted SB steal from hero
	* 4Bet Limp-Call HU					Pct of hands player calls after limping in from SB and facing a raise when heads up with BB
	* 4Bet Limp-ReRaise HU			Pct of hands player raises after limping in from SB and facing a raise when heads up with BB
	*SB Limp-Fold HU						Pct of hands player folds after limping in from SB and facing a raise when heads up with BB
	*
	* BTN Open Limp						Pct of hands player calls preflop when in BTN facing unopened pot
	* 4Bet Open Limp						Pct of hands player calls preflop when in SB facing unopened pot
	* BB  Raise vs SB Limp UOP		Pct of hands player raises in BB when facing an
	*
	* openlimp from SB
	*
	* Flop CBet									Pct of hands player bets the Flop as PFR
	* Flop CBet IP								Pct of hands player bets the Flop as PFR when last to act on flop
	* Flop CBet OOP							Pct of hands player bets the Flop as PFR when NOT last to act on flop
	* Flop CBet-Call							Pct of hands player bets Flop as PFR and calls a raise
	* Flop CBet-Reraise					Pct of hands player bets Flop as PFR and reraises a raise
	* Flop CBet-Fold							Pct of hands player bets Flop as PFR and folds to a raise
	* Flop Call Donk Bet					Pct of hands player calls a donkbet on Flop
	* Flop Raise Donk Bet				Pct of hands player raises a donkbet on Flop
	* Flop Fold to Donk Bet				Pct of hands player folds to a donkbet on Flop
	* Skip Flop CB and Check-Call OOP		Pct of hands player check-calls OOP after checking flop CB opportunity
	* Skip Flop CB and Check-Raise OOP	Pct of hands player check-raises OOP after checking flop CB opportunity
	* Skip Flop CB and Check-Fold OOP	Pct of hands player check-folds OOP after checking flop CB opportunity
	* Flop Call CB IP						Pct of hands player calls IP when facing a CB on the flop
	* Flop Raise CB IP					Pct of hands player raises IP when facing a CB on the flop
	* Flop Fold to CB IP				Pct of hands player folds IP when facing a CB on the flop
	* Flop Check-Call CB OOP		Pct of hands player check-calls OOPwhen facing a CB on the flop
	* Flop Check-Raise CB OOP	Pct of hands player check-raises OOPwhen facing a CB on the flop
	* Flop Check-Fold to CB OOP	Pct of hands player check-folds OOPwhen facing a CB on the flop
	* Flop Donk Bet-Call				Pct of hands player bets on flop into preflop aggressor and calls a raise
	* Flop Donk Bet-ReRaise		Pct of hands player bets on flop into preflop aggressor and reraises a raise
	 * Flop Donk Bet-Fold				Pct of hands player bets on flop into preflop aggressor and folds to a raise
	 * Flop Donk Bet						Pct of hands player bets OOP on flop into player who took last aggressive action preflop
	 * Flop Bet vs Missed CB			Pct of hands player bets after opponent missed flop continuation bet
	 * Flop Bet - Limped Pot Attempt to Steal IP		Pct of hands players bets on flop IP in limped pot
	*
	****  postflopAggressionPercentagePer(play);
	* Postflop Aggression%			Pct of postflop streets when player makes aggresive move
	* Seen Flop Overall					Pct of hands player sees the Flop
	*
	* Flop Agg%							Pct of flops the player makes an aggressive play
	* Flop CBet								Pct of hands player bets the Flop as PFR
	* Flop Fold to CBet					Pct of hands player folds when facing a CB on the flop
	* Turn Agg%							Pct of turns the player makes an aggressive play
	* Turn CBet								Pct of hands player bets the Turn as PFR
	* Turn Fold to CBet					Pct of hands player folds when facing a CB on the turn
	* River Agg%							Pct of rivers the player makes an aggressive play
	* River CBet								Pct of hands player bets the river as PFR
	* River Fold to CBet				Pct of hands player folds when facing a CB on the river
	*
	* Turn CBet								Pct of hands player bets the Turn as PFR after CBetting the Flop as PFR
	* Turn CBet IP							Pct of hands player bets the Turn as PFR after CBetting the Flop as PFR when last to act on flop
	* Turn CBet OOP						Pct of hands player bets the Turn as PFR after CBetting the Flop as PFR when NOT last to act on flop
	* Turn CBet-Call						Pct of hands player bets the Turn as PFR after CBetting the Flop as PFR and calls a raise
	* Turn CBet-ReRaise				Pct of hands player bets the Turn as PFR after CBetting the Flop as PFR and reraises a raise
	* Turn CBet-Fold						Pct of hands player bets the Turn as PFR after CBetting the Flop as PFR and folds to a raise
	* Delayed Turn CBet				Pct of hands player bets the turn after checking flop CB opportunity
	* Delayed Turn CBet IP			Pct of hands player bets the turn after checking flop CB opportunity when last to act on flop
		* Delayed Turn CBet OOP		Pct of hands player bets the turn after checking flop CB opportunity when last to act on flop when NOT last to act on flop
	* Skip Turn CB and Check-Call OOP		Pct of hands player check-calls OOP after checking turn CB opportunity
	* Skip Turn CB and Check-Raise OOP	Pct of hands player check-raises OOP after checking turn CB opportunity
	**** skipTurnCBAndCheckFoldOOPPer(play);
	 **** turnCallCBIPPer(play);
	 **** turnRaiseCBIPPer(play);
	 **** turnFoldToCBIPPer(play);
	 **** turnCheckCallCBOOPPer(play);
	* Skip Turn CB and Check-Fold OOP	Pct of hands player check-folds OOP after checking turn CB opportunity
	* Turn Call CB IP						Pct of hands player calls IP when facing a CB on the turn
	* Turn Raise CB IP					Pct of hands player raises IP when facing a CB on the turn
	* Turn Fold to CB IP				Pct of hands player folds IP when facing a CB on the turn
	* Turn Check-Call CB OOP		Pct of hands player check-calls OOP when facing a CB on the turn
	* Turn Check-Raise CB OOP 	Pct of hands player check-raises OOP when facing a CB on the turn
	* Turn Check-Fold to CB OOP	Pct of hands player check-folds OOP when facing a CB on the turn
	* Turn Donk Bet						Pct of hands player bets OOP on turn into player who took last aggressive action on flop
	* Turn Bet vs Missed CB			Pct of hands player bets after opponent missed Turn continuation bet
	*
	* River
	* River CBet								Pct of hands player bets the River as PFR after CBetting the Flop and Turn
	* River CBet IP						Pct of hands player bets the River as PFR after CBetting the Flop and Turn when last to act on flop
	* River CBet OOP					Pct of hands player bets the River as PFR after CBetting the Flop and Turn when NOT last to act on flop
	* River CBet								Pct of hands player bets the River as PFR after CBetting the Flop and Turn and calls a raise
	* River CBet								Pct of hands player bets the River as PFR after CBetting the Flop and Turn and reraises a raise
	* River CBet								Pct of hands player bets the River as PFR after CBetting the Flop and Turn and folds to a raise
	* Skip River CB and Check-Call OOP	Pct of hands player check-calls OOP after checking river CB opportunity
	* Skip River CB and Check-Raise OOP	Pct of hands player check-raises OOP after checking river CB opportunity
	* Skip River CB and Check-Fold OOP	Pct of hands player check-folds OOP after checking river CB opportunity
	* River Call CB IP					Pct of hands player calls IP when facing a CB on the River
	* River Raise CB IP					Pct of hands player raises IP when facing a CB on the River
	* River Fold to CB IP				Pct of hands player folds IP when facing a CB on the River
	**** 	  riverCheckCallCBOOPPer(play);
	* River Check-Call CB OOP	Pct of hands player check-calls OOP when facing a CB on the River
	* River Check-Raise CB OOP	Pct of hands player check-raises OOP when facing a CB on the River
	* River Check-Fold to CB OOP	Pct of hands player check-folds OOP when facing a CB on the River
	* River Donk Bet						Pct of hands player bets OOP on river into player who took last aggressive action on turn
	* River Bet vs Missed CB		Pct of hands player bets after opponent missed River continuation bet
	* WTSD% When Saw Turn	Pct of hands player sees a showdown when he sees the Turn
	 ************************************************************************************************************************/

	/*-**********************************************************************************************
	 * This class is used for most reports and analysis.
	 * This is a one on one companion to the Players class. The Players class contains only data 
	 * extracted from Hand Histoty files. ThePlayerCharacteristics class ( this class ) contains only calculated
	 * values that use data from the corresponding Players class.  The Players class may be for an 
	 * individual player or for a group of players, grouped by type or some opther characteristic.
	 * 
	 * Instead of reports and analysis being written with a multitude of options we have a multitude
	 * of possible class instances. So this Class is used for a multitude of reports. 
	 * We just create an instance having the characteristics that we want to report on.
	 * 
	 * The PlayerEvaluation Class does much of the anaysis. 
	 * TODO Analysis methods wil for all results here will be moved here, to this class.
	 * 
	 * That instance is then used to create an instance ofPlayerCharacteristics that contains all of the data from 
	 * the one Player instance plus many calculations made in thisPlayerCharacteristics Class. wow! complicated.
	 * We need to consolidate data in order to make reporting possible. It would be impossible to report
	 * on millions of individual players. So we group them.
	 * 
	 * What constitutes a player type can be simple like a NIT, LAG, etc.. 
	 * It can be more obscure such as grouping players with a specific characteristic such as a high win rate
	 * or high C-Bet rate. Future versions will take full advantage of this.
	 * 
	 * For convenience we include the instance of Players inside of this class instance.
	 * 
	 * This class is contains all of the data that is in the Players Class.
	 * In addition it contains the results of many calculations.
	 *  
	 * Instances of this class can ve written to a disk .ser file so that we do not need to reprocess all of
	 * the data used to create it.
	 * 
	 * The data in the Players Class is collected by scanning Hand History files.
	 * Collected data only, calculation results are on this class along with all of the source data from 
	 * the Players Class.
	 * 
	 *  After some basic classification, such as player type ( NIT, LAG. ... ) instances of Players are
	 *  created for each player type. A few instances, not the thousands initially collected.
	 *  The player type instances are basically created by adding together many individual 
	 *  player instances.
	 *  
	 * Player data is written to disk files and is then read by various Hold'em classes.
	 * These Classes do things like create range files that represent the various player types.
	 * Data is also used to make decisions about how to play.
	 * It is expected that many uses for the PlayerDate will be developed.
	 *
	 * The PlayerEvaluation class will be adding many new variables to PLayerDate
	* Much yet to be done there.
	 *  
	 * The  class makes multiple passes analyzing Hand History files.
	 * 		In pass 1, Hand History files are read and data saved in this class.  
	 * 			ThePlayerCharacteristics class is used to save the average information for all players.
	 * 			Pass 1 only createsPlayerCharacteristics for the average of all players, no player types.
	 * 
	 * 		In pass 2, data in this class is analyzed and players are classified by type. 
	 * 			Hand History files are not used, only the Players class.	
	 * 			Players contains data about individual player and so there are amny instances of
	 * 			Players, one for each player.
	 * 			There are also Player instances that are for a collection of players.
	 * 			PlayerData contains data that is about all players or all players of a type. 
	 * 			The key difference is thatPlayerCharacteristics instances are save to a disk file
	 * 			but PlayerDate is not saved. Used then discarded.
	 * 
	 * 		In pass 3, data in this class is analyzed again, andPlayerCharacteristics is created for  
	 * 			player types. ( NIT, LAG, TAG, ... )
	 * 			Pass 2 updated player types, and performed many calculations updating the
	 * 			information about each player.
	 * 			
	 * 		There is no plan to save this class in a file but it is a possible future enhancement.
	 * 
	 * In pass 2 each player is analyzed and this class updated.
	 * 
	*
	********************************************************************************************** */

	/*-**********************************************************************************************
	Learning To Read and Interpret Poker Tracking Software Stats
	Poker tracking software such as Poker Tracker or Holdem Manager are programs
	that extract and compile data from hand histories where all actions by you and your
	opponent's are recorded, resulting in detailed statistical analysis which can give 
	a clearer picture of how someone is playing and how to play against them. 
	It's also a great aid for improving your own game.
	It is practically a necessity for online play these days and when used effectively, 
	it will greatly improve your win rate. 
	The long lists of statistics they provide you with may seem daunting or overwhelming 
	at first, just focus on a small number of key statistics and add a new one each 
	time you reach a level of confidence in interpreting each statistic on your HUD. 
	t's important not to over load your HUD with a wide range of stats that you 
	don't comprehend, take things one stat at a time.
	If you're having difficulty setting up your HUD configuration, most poker 
	training sites host video guides for this.
	
	Using Tracker Software to improve your game
	At the end of every session or day it's a good idea to go back through your biggest winners 
	or losers or any other interesting hands and review them. 
	You should learn to recognise if you made any mistakes and try not to repeat them in future. 
	If there are any hands that confused you or you'd like more opinions on, you should consider 
	converting them and posting them on a poker forum for advice. 
	It's always good to hear a variety of opinions on hands you're unsure of.
	For ease of writing, I'll refer to a continuation bet as 'cbet'.
	
	Using Tracking Software to track your opponents play
	When reading blogs and forums etc. you'll often see players, or villains, described as things like
	22/18/2, but what does it mean? The first number is VPIP, the second is PFR and the third is AF.
	
	Voluntarily Put $ In Pot % or VPIP
	This shows how tight, or loose someone is playing pre-flop, it is the best tool for estimating a
	player's range of hands. You want your opponent's VPIP number to be as big as possible 
	at low stakes, the more hands they play the worse they usually are. 
	ou need a sample size of at least 100 hands to be confident assigning ranges based on this statistic.
	
	<15% is very tight. These players are only playing super premium hands from early position 
	and only still maintain a conservative range when in position.
	
	15% - 22% is tight. They will usually be a bit looser from early position then the <15% players,
	make notes on whether they play pocket pairs from early position, or whether they raise or limp. 
	They will tend to have a much wider range in late position.
	
	22% - 30% is semi loose. They'll usually open all PPs and strong non pair hands, like suited broadways
	and strong aces, from early position. They will have a wide range from late position.
	
	30% - 40% is considerably loose. These players are usually playing far too many hands in all positions
	and should make for easy winnings.
	
	40% - 60% is maniac loose. They're playing all sorts of trash from every possible position, these are the
	type of players you really want at your table, though they are becoming much rarer these days.
	
	>60% is free money.
	
	Pre-flop Raise % or PFR
	This is the percentage of hands your opponent is raising pre-flop. I
	t must always be less or equal their VPIP and should be analyzed in context with their VPIP.
	A 60/18 is not that aggressive, while a 20/18 is an extremely aggressive player. 
	Again you need a minimum sample size of at least 50 hands to have any confidence in this stat.
	
	If their PFR is very small (<5%) then you don't need to worry about getting raised off marginal hands.
	If they do raise you can fold nearly all speculative hands unless you have the implied odds to 
	call and stack them with a pocket pair for example, the deeper you're playing the 
	wider the range of hands you can call with.
	
	If their PFR is <1/2 their VPIP, then this player is quite passive pre-flop and limping 
	over 50% of hands they play.
	
	If PFR is between 50% - 75% of VPIP then they're raising more than they're limping, 
	but they're not overly aggressive.
	
	Any player with a ratio >75% is raising the majority of their hands and they're playing 
	aggressively pre-flop.
	
	Aggression Factor or AF
	This is an indicator of post-flop aggression. It is calculated by the following formula (raise% + bet%)/(call%) 
	post-flop. It's the ratio of times a player is aggressive against the times they're passive. 
	You need at least 100 – 200 sample size of hands to be sure of this stat but more hands for tighter players.
	
	It's essential to look at this in the context of VPIP and other statistics to interpret what it means.
	One of the limitations of Aggression Factor is that it doesn't include fold %, so two players with 
	the same AF could have very different ranges they raise.
	
	A weak-tight nit with a VPIP of 12% is going to make much stronger hands on average and fold 
	his marginal hands more often then a maniac with a VPIP of 65% who bets and raises with random hands.
	
	The weak-tight nit will have a high AF because he often folds unless he has the nuts so a raise from
	him will often mean a strong hand. The maniac on the other hand might have the same AF but since 
	he's playing more hands pre-flop and folding fewer hands post-flop his range for raising will be far larger.
	
	It's important to be able to tell what type of player you're playing against. 
	Take note of the type of hands they go to show down with, a maniac will lose a lot of hands at 
	showdown while the nit will go to showdown infrequently and often with nothing but very 
	strong holdings. 
	Using AF with WtSD, W$SD and W$WSF (explained soon) will give you a better idea what type of player
	you're up against. Looking at how they react to cbets can also be a useful indicator.
	
	Rough Guidelines For Aggression Factor or AF
	<1.5 is passive, these players are calling a lot and betting/raising very little, a raise from these
	players usually means a strong hand. You can value bet lighter against these opponents 
	because they tend to call with wide ranges.
	
	1.5 – 2.5 is about average. These players aren't overly aggressive post-flop but it's important to look 
	at it in the context of their VPIP and other stats.
	
	2.5 – 3.5 is aggressive. Be prepared to assign a wider range to bets and raises.
	
	>3.5 is very aggressive. These players prefer to bet or raise rather then call and may do so lightly.
	Against these players it may be profitable to induce bluffs. As mentioned, in some cases a high 
	AF can be an indication of a high fold%, so don't just assume they're raising you light.
	
	Won $ When Saw Flop % (W$WSF)
	Is how often a player wins the pot when he sees a flop; this gives a good indication 
	of how aggressive a player is post-flop.
	
	>45% – this player is likely to be extremely aggressive post-flop and is probably firing multiple barrels.
	It's also likely this player goes too far with marginal holdings, usually indicates a maniac.
	
	40% – 45% – this player is playing aggressively, most commonly a good TAG/LAG
	
	35% - 40% - this player is slightly passive post-flop, he gives up easily and may be playing too many
	hands pre-flop. Usually indicates a weak-tight post-flop player.
	
	<35% - this player is giving up far too easily and may be playing too many hands pre-flop. 
	Usually means that the player is nitty.
	
	Went to Showdown % (WtSD) – How often a player gets to showdown when he sees a flop. 
	The higher this number is the more likely the player is to be a calling station and the lighter
	you can value bet. The lower the number the more you can bluff and the
	less inclined you should be to value bet. 
	This should be looked at with W$SD.
	
	<22%, this player is fairly nitty and doesn't get to showdown often, most likely has a high W$SD
	
	22% – 27%, indicates a reasonably tight range and is the most common, 
	should be looked at in terms of W$SD.
	
	27% - 33%, fairly loose range for getting to showdown, a low W$SD would indicate a calling station 
	while a high W$SD would usually indicate a competent LAG.
	
	>33%, this player is almost definitely going too far with his hands. Value bet relentlessly.
	
	Won $ at Showdown % (W$SD)
	Represents how often a player wins $ at showdown. This can give a approximate measure of a 
	player's post-flop skill, the higher the number the more likely an opponent is to
	have the winning hand at showdown.
	
	This really needs to be looked at in context with a player's WtSD and they're overall style of play.
	A maniac is likely to have a low W$SD while a smart, aggressive player's will be higher. 
	
	A good LAG will have a high W$SD where a bad LAG wouldn't. 
	This will also separate the nits from the TAGs. 
	You can bluff frequently and use smaller bet sizes against players with high W$SD as 
	they're likely to be weak tight. On the other hand a player with a low W$SD is likely 
	calling too much so we can value bet lighter and bet bigger against them.
	
	>55%, this player probably isn't going to showdown too often and can be bluffed more frequently.
	
	48% - 55%, fairly common for TAGs/LAGs, look at in terms of WtSD.
	
	<48%, usually has the worst hand at showdown, value bet relentlessly and don't bluff them.
	
	BB/100 – Lets you know if the player is winning over the sample. 
	A good stat to have up on your own play if you generally multi-table, 
	t will tell you what your table image is like for each table. 
	f you're down a good bit on the table then your image will be bad and you will get called 
	and played back at lighter. 
	If you're up a good bit, haven't shown down much (or only strong hands) your image is
	likely to be good and you'll have more steal and fold equity at that table.
	
	Total Hands Played (Hands) – One of the most critical stats you can have on your HUD. 
	This tells you how much weight you can attribute to their stats. VPIP and PFR only
	become meaningful after roughly 50 hands, AF after about 200-500 hands 
	depending on how loose the player is. W$SD and W$WSF will need several thousand 
	hands to be truly accurate so should be used carefully, WtSD will become accurate a lot faster though.
	
	Aggression Factor by Street (FlopAF/TurnAF/RiverAF)
	These three stats displayed can give you a good idea of what type of player you're up against.
	
	A player with high flop AF will be cbetting frequently, use this with
	Cbet% to determine how likely a player is to cbet.
	
	A player with low flop AF and unusually high turn AF (>2) is most likely a floater. 
	hey tend to have a fairly high Call PFR % as well as a high Call Cbet %.
	
	A player with high flop and turn AFs will often be firing a double barrel, if they have a high 
	W$WSF and Cbet% with a fairly low WtSD you can be fairly confident they will fire 2nd barrels.
	
	A player with low flop and turn AF and high river AF is usually a fish; they like to see all the 
	cards before betting and are often calling stations as well.
	
	A player with normal flop and turn AFs but with a low river AF (<2) is often turning their hand 
	into a bluff catcher on the river, they prefer to check/call then bet/fold. Conversely a player 
	with a high river AF will be bet/folding more of their marginal hands 
	and you can raise their bets as a bluff more often.
	
	Attempt to Steal Blinds % (AttSB)
	Another exceptionally useful stat, it can determine whether or not a player is aware position dynamics. 
	It needs to be considered in context with PFR. If their AttSB is significantly higher then their
	PFR then they're most likely aware of the power of positional and you can give more respect 
	o their UTG opens and less to their cut off/button opens. A high Fold SB/BB to steal % 
	is another solid sign that they know what they're doing.
	
	A player with a high AttSB who is opening from the cutoff and particularly the button is an 
	excellent candidate to consider 3beting light. If they also have a high Cbet% then you could 
	consider calling light with the intention of c/ring (or just raising if your on the button) their cbet. 
	Pocket pairs go down in value against these types of players since you can't expect to 
	get paid off often enough even if you hit gin.
	
	<20% - These players aren't taking advantage of their position and are giving up a lot of value. 
	We would like this type of player in LP when we're in the blinds.
	
	20% – 27% - these players aren't stealing too light and we can give their cutoff/button 
	opens a tighter range. We're not losing much against this type of player.
	
	27% – 35% - these players are stealing fairly lightly, we should assign a wider range to their 
	late position raises and 3bet them more often as they're likely to be getting out of line.
	
	>35% – these players are stealing pretty light and are often raising many of trashy hands. 
	We can 3bet more hands for value and we should be 3betting them frequently as their 
	opening range can't take too much pressure.
	
	Continuation Bet Percentage
	Tells us how often a player bets or raises the flop after raising the pot pre-flop. 
	At micro levels players should be cbetting pretty often, however at small-mid stakes 
	his would be burning money. Pay attention to PFR and AttSB, a player with a very low 
	PFR most likely has a big hand when he cbets and should be given credit. 
	A player with a high PFR and high Cbet% will have a much wider range when they cbet.
	
	>85%, this player is continuation betting almost always and is unlikely to be paying attention 
	to the number of opponents or the flop texture. You can raise and call them lighter. 
	These are also prime candidates for floating unless of course they are prone to firing 
	multiple barrels. If they do fire 2nd barrels often then you can be more inclined to 
	lowplay monsters, or call down lightly against them trying to induce bluffs.
	
	65% - 85%, this player is probably playing closer to optimally at small-mid stakes 
	and is going to be much harder to exploit.
	
	<65%, this player doesn't cbet often enough. We can see more flops with them and
	take the pot away when they check. However, they should be given more credit 
	when they do cbet, as with a low frequency, once they start betting, 
	it's generally because they've connected reasonably well with the flop.
	
	Bet River %
	How frequently a player bets the river. It's important to be aware of what type of 
	villain you are facing. A lot of weaker players like to check call cbets and then bet 
	he river after the turn checks through. 
	They often assume you would have bet a made hand on the turn and believe they 
	can win the pot by betting. Maniacs are probably bluffing the river too frequently.
	
	>30%, this player is likely betting a lot of weak or marginal hands on the river. 
	Adjust your range and be prepared to call these players down lighter.
	
	20% - 30%, this player is betting the river quite frequently, they may bet-fold 
	more often then check-c
	
	<20%, this player should be given a lot more respect on the river.
	
	Fold SB/BB to steal %
	This statistic is very handy and can tell you how likely your steal attempts are to be successful.
	A player with a low fold blind to steal % is defending regularly. 
	If they also have a high call cbet% then stealing light is unlikely to be successful.
	If a player has a high fold blind to steal % or a high fold to cbet % then you can steal 
	extremely lightly but be prepared to shutdown against resistance.
	
	>90%, playing super tight OOP, attack mercilessly.
	
	80% - 90%, still playing fairly tight and a good target for steals, the higher 
	their fold to cbet % the better.
	
	70% - 80%, very player dependent, o.k. steal targets if they give up easily post-flop. 
	Not great targets if they're competent players.
	
	<70%, calling too much OOP and will find it difficult to make hands or continue post-flop 
	without the betting lead, highly exploitable but not great targets to steal light against.
	
	Calls PFR %
	This is a very useful tool to use with Fold SB/BB to steal. If they have a low Call PFR 
	less then about 5% – 6%) and a low Fold SB/BB to steal (less then about 70% – 75%) 
	it's very likely they are 3betting light and you can adjust.
	
	Fold/Call/Raise cbet %
	These extremely useful stats to display on your hud . 
	They can show you how an opponent is likely to react to your cbet.
	
	Fold to cbet %
	This is the most critical stat of all cbet% stats. It's important to know how likely your 
	cbet is to be successful and who you should cbet against.
	
	This is a rough guideline for fold to cbet percentages:
	
	<50%, cbetting against them is often just burning money, they are calling stations 
	and chase too lightly. 
	Value bet them relentlessly, but don't bother cbetting with air on boards
	likely to have hit their range.
	Cbetting on dry boards heads-up can sometimes be ok though.
	
	50% - 70%, they're probably not huge calling stations but may play back
	or chase with marginal hands or draws.
	
	70% - 80%, likely to only be putting money in with a reasonable hand and 
	probably aren't chasing too lightly.
	
	>80%, should nearly always be cbet against in heads- up pots, they need a good hand 
	to continue and usually won't have it.
	
	Call and Raise cbet Percentage
	Very useful when deciding how to play marginal draws on the flop. 
	Against players with high raise cbet compared to call cbet we should be more inclined 
	to check behind a weak hand like a gutshot or a hand we don't want to get raised off.
	If the player rarely raises cbets and is more likely to call if he's continuing then we 
	can bet these hands and expect to see a turn and river if we don't take the pot
	down straight away. 
	It's also more likely that we get to see all 5 cards this way.
	
	A raise cbet % >15 would be fairly high and can be given less respect. 
	Be wary of players whose raise % is larger then their call %.
	
	You can also work out what a donk bet is likely to mean. 
	A player who never raises cbets that has donk bet is likely to be donk betting 
	their strongest 
	hands and conversely a player with a high raise cbet % who donk bets is
	often weak and should be raised.
	
	Fold To Flop Bet
	Quite similar to fold to cbet% but this applies only to unraised pots,
	the figures are usually quite close.
	
	Cold Call Percentage
	Will indicate how often they call a raise with no money already invested. 
	Anything above 2 indicates that a player is probably calling pre-flop raises with 
	marginal or trash holdings. You can assign a wider range to them and you should be
	less inclined to steal from the CO if they're on the button.
	
	General Advice
	You don't want to be basing decisions off incorrect information. 
	If a player gets up and another takes his place before the stats refresh 
	you could make some very costly mistakes.
	
	I also include most of the other stats I don't have displayed in the pop-up screen, 
	you never know when you might need it.
	
	It's possible to put up stats on your own play for the session.
	I highly recommend doing this as it can give you a good indicator of what your 
	image is likely to be. I have VPIP, PFR, AF, AttSB, Cbet%, BB/100 and Hands displayed.
	
	Summary
	As you can see you will require large sample sizes for many statistics to bear any 
	significant relevance, but even when you do have large sample sizes, 
	it's important not to be overly depend on these statistics. Note taking is still vitally important, 
	this can't be stressed enough. It's also vitally important that you use Poker Tracker and 
	Holdem Manager statistics as an aid in your overall decision making process, 
	don't rely solely on stats to base your decisions.
	 ***************************************************************************** */

	/*-******************************************************************************
	 * Holdem Manager 3 HUD statistics
	 * The index is the street number NUM_STREET
	 * Calculated by PlayerEvaluation
	 * 
	 * from HM3
	 * ZZZZ
	 * 		VPIP 					26.6
	 * 		PFR						17.3
	 * 		3Bet						8.3
	 * 		postflopAgg		26.9
	 * 		WTSD					29.2
	 * 		WWSF					45.7
	 * 		Won SD				50.4
	 * 		flop c-bet			61.7
	 * 		turn c-bet			52.0
	 * 		river c-bet			53.8
	 * 		Fold vs CBet		43.3
	 * 		Fold vs T CBet	42.9
	 * 		Fold vs R CBet	52.3
	 * 		Raise vs F CBet	10.5
	 * 		Raise vs T CBet	 9.51
	 * 		Raise vs R CBet	12.6
	 * 		Squeeze				 7.15
	 *  	Call 2 raisers		 3.18
	 *  	Raise 2 raisers	 3.50
	 * 		fold to 3bet		46.7
	 * 		Call vs 3Bet			40.9
	 * 		Raise vs 3Bet		12.4
	 * 		foldVs c-ber		43.3
	 * 		Fold to 4Bet		47.1
	 * 		Call vs 4Bet			35.0
	 * 		Raise vs 4Bet		17.9
	 *********************************************************************************/

	/*- ****************************************************************************** 
	 * Identification and key to HashMap
	 *********************************************************************************/
	int playerID = -1; // Key
	int playerType = 0;
	double vpip = 0.;
	double preflopVPIP = 0;
	double aggPct = 0.;
	double af;
	double pfr;
	double wtsdIfSawPreflop;
	double wtsdIfSawFlop;
	double wtsdIfSawTurn;
	double wtsdIfSawRiver;
	double winRateBB100; // Win rate in bb/ 100 ( or loss rate if - )
	double preflopBet3Freq;
	int preflopWinRate = 0;

	double showdownPercentage = 0;

	BigDecimal bbBet$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);
	BigDecimal winnings$ = new BigDecimal(0).setScale(2, RoundingMode.HALF_EVEN);

	/*- **********************************************************************************************
	 * Some basic counts 
	  ************************************************************************************************/
	int showdownCount = 0;
	int showdownWonCount = 0; // TODO
// Orbit 0 only for count of times player saw street
	int preflopCount = 0;
	int flopCount = 0;
	int turnCount = 0;
	int riverCount = 0;
	int raiseCount1 = 0;
	int betCount1 = 0;
	int barrellFlopTurnCount = 0; // Raise then raise turn
	int barrellFlopTurnRiverCount = 0; // Raise then raise turn then raise river
	int barrellTurnRiverCount = 0; // Not raise then raise turn then raise river
	int barrellPreflopFlopTurnRiverCount = 0; // Raise 4 streets

	/*- **********************************************************************************************
	 * Arrays
	  ************************************************************************************************/

	double[] pfrPos = new double[NUM_POS];

	double[] winRateBB100Streets = new double[NUM_STREETS];
	double[] inPositionFrequencyStreets = new double[NUM_STREETS];
	double[] outOfPositionFrequency = new double[NUM_STREETS];
	double[] vpipStreet = new double[NUM_STREETS]; // ??? TODO
	double[] pfrStreet = new double[NUM_STREETS]; // TODO????
	double[] aggressionFrequencyStreet = new double[NUM_STREETS];
	double[] aggressionFactorStreet = new double[NUM_STREETS];
	double[] winRateStreets = new double[NUM_STREETS];
	double[] bet3tFreqStreets = new double[NUM_STREETS]; // 3-Bet
	// zzz
	double[] w$sd_wsd = new double[NUM_STREETS]; // Won Money At Showdown
	double[] wwsf = new double[NUM_STREETS]; // Won When Saw
	double[] wtsd = new double[NUM_STREETS];
	double[] foldTo3BetAfterRaising = new double[NUM_STREETS]; // Fold to 3-Bet After R
	double[] Squeeze = new double[NUM_STREETS]; // Squeeze
	double[] Aggression = new double[NUM_STREETS]; // Squeeze
	double[] cBet = new double[NUM_STREETS]; // C-Bet
	double[] CBet_IP = new double[NUM_STREETS]; // C-Bet in position
	double[] CBet_OOP = new double[NUM_STREETS]; // C-Bet out of position
	double[] CBet_Fold = new double[NUM_STREETS]; // C-Bet folded to C-Bet
	double[] CBet_Call = new double[NUM_STREETS]; // C-Bet called
	double[] CBet_Reraise = new double[NUM_STREETS]; // C-Bet feraised
	double[] CheckCall_IP = new double[NUM_STREETS]; // C-Bet called in position
	double[] CheckCall_OOP = new double[NUM_STREETS]; // C-Bet called out of position
// a bet that is made into the aggressor from the prior betting round,
// denying them an opportunity to make a continuation bet
	double[] DonkBet = new double[NUM_STREETS]; //
	double[] DonkBetCall = new double[NUM_STREETS]; //
	double[] foldToCBet = new double[NUM_STREETS]; // Fold to C-Bet
	double[] coldCallRise = new double[NUM_STREETS]; //
	double[] rfi = new double[NUM_STREETS]; // Raise first in
	double[] callPercentage = new double[NUM_STREETS];
	double[] raisePercentage = new double[NUM_STREETS];
	double[] betPercentage = new double[NUM_STREETS]; // TODO
	double[] foldPercentage = new double[NUM_STREETS];
	double[] checkPercentage = new double[NUM_STREETS];

	double[][] vpipStreetPos = new double[NUM_STREETS][NUM_POS];
	double[][] aggPctStreetPos = new double[NUM_STREETS][NUM_POS];
	double[][] afStreetPos = new double[NUM_STREETS][NUM_POS];
	double[][] bet3FreqStreetPos = new double[NUM_STREETS][NUM_POS];
	int[][] winRateStreetPos = new int[NUM_STREETS][NUM_POS];

	/*- **********************************************************************************************
	* Percentage data  
	* The Players Class holds data with a second index of orbit.
	* These variables are calculated by adding data in Players together
	* There are 2 types of arrays:
	* 		 Indexed by position ( SB, BB, UTG, MP, CO, BU  )
	* 		 Indexed by relative position ( FIRST, FIRSTHU, LAST, LASTHU, MIDDLE )
	* All values are double and are a percentage  opportunity / action
	 ************************************************************************************************/
// Acted
	double[][] minBetPerPos = new double[NUM_STREETS][NUM_POS];
	double[][] minBetPerRp = new double[NUM_STREETS][NUM_RP];
// Acted
	double[][] limpPerPos = new double[NUM_STREETS][NUM_POS];
	double[][] checkPerPos = new double[NUM_STREETS][NUM_POS];
	double[][] foldPerPos = new double[NUM_STREETS][NUM_POS];
	double[][] bet1PerPos = new double[NUM_STREETS][NUM_POS];
	double[][] callBet1PerPos = new double[NUM_STREETS][NUM_POS];
	double[][] bet2PerPos = new double[NUM_STREETS][NUM_POS];
	double[][] callBet2PerPos = new double[NUM_STREETS][NUM_POS];
	double[][] bet3PerPos = new double[NUM_STREETS][NUM_POS];
	double[][] callBet3PerPos = new double[NUM_STREETS][NUM_POS];
	double[][] bet4PerPos = new double[NUM_STREETS][NUM_POS];
	double[][] callBet4PerPos = new double[NUM_STREETS][NUM_POS];
	double[][] allinPerPos = new double[NUM_STREETS][NUM_POS];
	double[][] callAllinPerPos = new double[NUM_STREETS][NUM_POS];
	double[][] cBetPerPos = new double[NUM_STREETS][NUM_POS];
	double[][] barrelPerPos = new double[NUM_STREETS][NUM_POS];
// Acted
	double[][] limpPerRp = new double[NUM_STREETS][NUM_RP];
	double[][] checkPerRp = new double[NUM_STREETS][NUM_RP];
	double[][] foldPerRp = new double[NUM_STREETS][NUM_RP];
	double[][] bet1PerRp = new double[NUM_STREETS][NUM_RP];
	double[][] callBet1PerRp = new double[NUM_STREETS][NUM_RP];
	double[][] bet2PerRp = new double[NUM_STREETS][NUM_RP];
	double[][] callBet2PerRp = new double[NUM_STREETS][NUM_RP];
	double[][] bet3PerRp = new double[NUM_STREETS][NUM_RP];
	double[][] callBet3PerRp = new double[NUM_STREETS][NUM_RP];
	double[][] bet4PerRp = new double[NUM_STREETS][NUM_RP];
	double[][] callBet4PerRp = new double[NUM_STREETS][NUM_RP];
	double[][] allinPerRp = new double[NUM_STREETS][NUM_RP];
	double[][] callAllinPerRp = new double[NUM_STREETS][NUM_RP];
	double[][] cBetPerRp = new double[NUM_STREETS][NUM_RP];
	double[][] barrelPerRp = new double[NUM_STREETS][NUM_RP];

	/*-************************************************************************************
	 * Percentages
	 * Applies to Button, Small Blind, and Big Blind only.
	 ****************************************************************************************/
	double walkPer = 0;
// Button
	double isolateBUPer = 0;
	double minBetBUPer = 0;
	double stealBUPer = 0;
	double squeezeBUPer = 0;
// Big Blind
	double minBetBBPer = 0;
	double callMinBetBBPer = 0;
	double steal3BetBBPer = 0;
	double raisedBySBBBPer = 0;
	double steal3BetMinRaiseBBPer = 0;
	double stealCallBBPer = 0;
	double stealCallMinRaiseBBPer = 0;
// Small Blind
	double bet3MinSBPer = 0;
	double raisedByBBSBPer = 0;
	double steal3BetSBPer = 0;
	double stealCallSBPer = 0;
	double stealCallMinRaiseSBPer = 0;
	double foldedToSBPer = 0;

	double fourBetLimpCallHU; // 4Bet Limp-Call HU
	double fourBetLimpReRaiseHU; // 4Bet Limp-ReRaise HU
	double sB_Limp_Fold_HU; // SB Limp-Fold HU
	double btnOpenLimp; // BTN Open Limp
	double fourBetOpenLimp; // 4Bet Open Limp
	double bB_Raise_vs_SB_Limp_UOP; // BB Raise vs SB Limp UOP
	double flopCBet; // Flop CBet
	double flopCBetIP; // Flop CBet IP
	double flopCBetOOP; // Flop CBet OOP
	double flopCBetCall; // Flop CBet-Call
	double flopCBetReraise; // Flop CBet-Reraise
	double flopCBetFold; // Flop CBet-Fold
	double flopCallDonkBet; // Flop Call Donk Bet
	double flopRaiseDonkBet; // Flop Raise Donk Bet
	double flopFoldToDonkBet; // Flop Fold to Donk Bet
	double skipFlopCBAndCheckCallOOP; // Skip Flop CB and Check-Call OOP
	double skipFlopCBAndCheckRaiseOOP; // Skip Flop CB and Check-Raise OOP
	double skipFlopCBAndCheckFoldOOP; // Skip Flop CB and Check-Fold OOP
	double flopCallCBIP; // Flop Call CB IP
	double flopRaiseCBIP; // Flop Raise CB IP
	double flopFoldToCBIP; // Flop Fold to CB IP
	double flopCheckCallCBOOP; // Flop Check-Call CB OOP
	double flopCheckRaiseCBOOP; // Flop Check-Raise CB OOP
	double flopCheckFoldToCBOOP; // Flop Check-Fold to CB OOP
	double flopDonkBetCall; // Flop Donk Bet-Call
	double flopDonkBetReRaise; // Flop Donk Bet-ReRaise
	double flopDonkBetFold; // Flop Donk Bet-Fold
	double flopDonkBet; // Flop Donk Bet
	double flopBetVsMissedCB; // Flop Bet vs Missed CB
	double flopBetLimpedPotAttemptToStealIP; // Flop Bet - Limped Pot Attempt to Steal IP
	double postflopAggressionPercentage; // Postflop Aggression Percentage
	double seenFlopOverall; // Seen Flop Overall
	double turnCBet; // Turn CBet
	double turnCBetIP; // Turn CBet IP
	double turnCBetOOP; // Turn CBet OOP
	double turnCBetCall; // Turn CBet-Call
	double turnCBetReRaise; // Turn CBet-ReRaise
	double delayedTurnCBet; // Delayed Turn CBet
	double delayedTurnCBetIP; // Delayed Turn CBet IP
	double delayedTurnCBetOOP; // Delayed Turn CBet OOP
	double skipTurnCBAndCheckCallOOP; // Skip Turn CB and Check-Call OOP
	double skipTurnCBAndCheckRaiseOOP; // Skip Turn CB and Check-Raise OOP
	double skipTurnCBAndCheckFoldOOP; // Skip Turn CB and Check-Fold OOP
	double turnCallCBIP; // Turn Call CB IP
	double turnRaiseCBIP; // Turn Raise CB IP
	double turnFoldToCBIP; // Turn Fold to CB IP
	double turnCheckCallCBOOP; // Turn Check-Call CB OOP
	double turnCheckRaiseCBOOP; // Turn Check-Raise CB OOP
	double turnCheckFoldToCBOOP; // Turn Check-Fold to CB OOP
	double turnDonkBet; // Turn Donk Bet
	double turnBetVsMissedCB; // Turn Bet vs Missed CB
	double riverCBet; // River CBet
	double riverCBetIP; // River CBet IP
	double riverCBetOOP; // River CBet OOP
	double riverCBetCall; // River CBet-Call
	double riverCBetReRaise; // River CBet-ReRaise
	double riverCBetFold; // River CBet-Fold
	double skipRiverCBAndCheckCallOOP; // Skip River CB and Check-Call OOP
	double skipRiverCBAndCheckRaiseOOP; // Skip River CB and Check-Raise OOP
	double skipRiverCBAndCheckFoldOOP; // Skip River CB and Check-Fold OOP
	double riverCallCBIP; // River Call CB IP
	double riverRaiseCBIP; // River Raise CB IP
	double riverFoldToCBIP; // River Fold to CB IP
	double riverCheckCallCBOOP; // River Check-Call CB OOP
	double riverCheckRaiseCBOOP; // River Check-Raise CB OOP
	double riverCheckFoldToCBOOP; // River Check-Fold to CB OOP
	double riverDonkBet; // River Donk Bet
	double riverBetVsMissedCB; // River Bet vs Missed CB
	double wtsdWhenSawTurn; // WTSD% When Saw Turn
	double reSteal3BetVsSteal; // ReSteal (3Bet vs Steal)
	double threeBetReStealVsHero; // 3Bet ReSteal vs Hero
	double sBFoldToSteal; // SB Fold to Steal
	double fourBetCallSteal; // 4Bet Call Steal
	double fourBetReSteal3BetVsSteal; // 4Bet ReSteal (3Bet vs Steal)
	double foldToStealBB; // Fold to Steal BB
	double bbCallSteal; // BB Call Steal
	double bbReSteal3BetVsSteal; // BB 3Bet ReSteal vs Steal
	double bb3BetReStealVsHero; // BB 3Bet ReSteal vs Hero
	double foldToStealSB; // Fold to Steal SB
	double foldToBTNSteal; // Fold to BTN Steal
	double foldToSBSteal; // Fold to SB Steal
	double foldSBVsStealFromButtonHero; // Fold SB vs Steal from Button Hero
	double foldBBVsStealFromButtonHero; // Fold BB vs Steal from Button Hero
	double foldBBVsStealFromSmallBlindHero; // Fold BB vs Steal from Small Blind Hero
	double fourBetFoldToBTNSteal; // 4Bet Fold to BTN Steal
	double sBLimpFoldHU; // SB Limp-Fold HU
	double bBRaiseVsSBLimpUOP; // BB Raise vs SB Limp UOP

	/*-**********************************************************************************************
	 * This group of arrays is for tracking money.
	 * Bet size determines several things:
	 * 		SPR - Stack to Pot Ratio
	 * 		Pot Odds
	 * 		......
	 * 
	 * These arrays age for all streets, Preflop, Flop, Turn, and River.
	 * Index 1 - Street number
	 * Index 2 - Position ( SB, BB, UTG, MP, CO, BUTTON or RElarive Positiob SB, BB, ...
	 * Index 3 - Orbit
	 * TODO
	 ************************************************************************************************/
// TODO
	double[][] bet1Pos$ = new double[NUM_STREETS][NUM_POS];
	double[][] callBet1Pos$ = new double[NUM_STREETS][NUM_POS];
	double[][] bet2Pos$ = new double[NUM_STREETS][NUM_POS];
	double[][] callBet2Pos$ = new double[NUM_STREETS][NUM_POS];
	double[][] bet3Pos$ = new double[NUM_STREETS][NUM_POS];
	double[][] callBet3Pos$ = new double[NUM_STREETS][NUM_POS];
	double[][] bet4Pos$ = new double[NUM_STREETS][NUM_POS];
	double[][] callBet4Pos$ = new double[NUM_STREETS][NUM_POS];
	double[][] allinPos$ = new double[NUM_STREETS][NUM_POS];
	double[][] callAllinPos$ = new double[NUM_STREETS][NUM_POS];
	double[][] cBetPos$ = new double[NUM_STREETS][NUM_POS];
	double[][] barrelPos$ = new double[NUM_STREETS][NUM_POS];
// TODO
	double[][] bet1Rp$ = new double[NUM_STREETS][NUM_RP];
	double[][] callBet1Rp$ = new double[NUM_STREETS][NUM_RP];
	double[][] bet2Rp$ = new double[NUM_STREETS][NUM_RP];
	double[][] callBet2Rp$ = new double[NUM_STREETS][NUM_RP];
	double[][] bet3Rp$ = new double[NUM_STREETS][NUM_RP];
	double[][] callBet3Rp$ = new double[NUM_STREETS][NUM_RP];
	double[][] bet4Rp$ = new double[NUM_STREETS][NUM_RP];
	double[][] callBet4Rp$ = new double[NUM_STREETS][NUM_RP];
	double[][] allinRp$ = new double[NUM_STREETS][NUM_RP];
	double[][] callAllinRp$ = new double[NUM_STREETS][NUM_RP];
	double[][] cBetRp$ = new double[NUM_STREETS][NUM_RP];
	double[][] barrelRp$ = new double[NUM_STREETS][NUM_RP];
// TODO
	double[][] potOddsPos$ = new double[NUM_STREETS][NUM_POS];
	double[][] SPRPos$ = new double[NUM_STREETS][NUM_POS];
	double[][] potPos$ = new double[NUM_STREETS][NUM_POS];
	double[] potAtEndOfStreet$ = new double[NUM_STREETS];

	/*-
	 * These counts are for orbit 0 only.
	 * For preflop, they include the Small Blind and the Big blind only.
	 * The reason for this is that they are used in calculating certain 
	 * statistics such as VPIP and aggression factor.
	 * For example:
	 */
	int[][] checkRaiseCountEV = new int[NUM_STREETS][NUM_POS];
	int[][] foldCount = new int[NUM_STREETS][NUM_POS];
	int[][] foldOpportunity = new int[NUM_STREETS][NUM_POS];
	int[][] foldStartP = new int[NUM_STREETS][NUM_POS];
	int[][] foldCountP = new int[NUM_STREETS][NUM_POS];
	int[][] foldStartEV = new int[NUM_STREETS][NUM_POS];
	int[][] foldCountEV = new int[NUM_STREETS][NUM_POS];
// TODO
	int[][] limpCount = new int[NUM_STREETS][NUM_POS];
	int[][] limpOpportunity = new int[NUM_STREETS][NUM_POS];
	int[][] limpStartP = new int[NUM_STREETS][NUM_POS];
	int[][] limpCountP = new int[NUM_STREETS][NUM_POS];
	int[][] limpStartEV = new int[NUM_STREETS][NUM_POS];
	int[][] limpCountEV = new int[NUM_STREETS][NUM_POS];
// TODO
	int[][] callCount = new int[NUM_STREETS][NUM_POS];
	int[][] callOpportunity = new int[NUM_STREETS][NUM_POS];
	int[][] callStartP = new int[NUM_STREETS][NUM_POS];
	int[][] callCountP = new int[NUM_STREETS][NUM_POS];
	int[][] callStartEV = new int[NUM_STREETS][NUM_POS];
	int[][] callCountEV = new int[NUM_STREETS][NUM_POS];
// TODO
	int[][] betCount = new int[NUM_STREETS][NUM_POS];
	int[][] betOpportunity = new int[NUM_STREETS][NUM_POS];
	int[][] betStartP = new int[NUM_STREETS][NUM_POS];
	int[][] betCountP = new int[NUM_STREETS][NUM_POS];
	int[][] betStartEV = new int[NUM_STREETS][NUM_POS];
	int[][] betCountEV = new int[NUM_STREETS][NUM_POS];
// TODO
	int[][] raiseCount = new int[NUM_STREETS][NUM_POS];
	int[][] raiseOpportunity = new int[NUM_STREETS][NUM_POS];
	int[][] raiseStartP = new int[NUM_STREETS][NUM_POS];
	int[][] raiseCountP = new int[NUM_STREETS][NUM_POS];
	int[][] raiseStartEV = new int[NUM_STREETS][NUM_POS];
	int[][] raiseCountEV = new int[NUM_STREETS][NUM_POS];
	int[][] checkCount = new int[NUM_STREETS][NUM_POS];
	int[][] checkOpportunity = new int[NUM_STREETS][NUM_POS];
	int[][] checkStartP = new int[NUM_STREETS][NUM_POS];
	int[][] checkCountP = new int[NUM_STREETS][NUM_POS];
	int[][] checkStartEV = new int[NUM_STREETS][NUM_POS];
	int[][] checkCountEV = new int[NUM_STREETS][NUM_POS];
	int[][] bet1Count = new int[NUM_STREETS][NUM_POS];
	int[][] bet1StartP = new int[NUM_STREETS][NUM_POS];
	int[][] bet1CountP = new int[NUM_STREETS][NUM_POS];
	int[][] bet1StartEV = new int[NUM_STREETS][NUM_POS];
	int[][] bet1CountEV = new int[NUM_STREETS][NUM_POS];
	int[][] callBet1Count = new int[NUM_STREETS][NUM_POS];
	int[][] callBet1StartP = new int[NUM_STREETS][NUM_POS];
	int[][] callBet1CountP = new int[NUM_STREETS][NUM_POS];
	int[][] callBet1StartEV = new int[NUM_STREETS][NUM_POS];
	int[][] callBet1CountEV = new int[NUM_STREETS][NUM_POS];
	int[][] bet2Count = new int[NUM_STREETS][NUM_POS];
	int[][] bet2StartP = new int[NUM_STREETS][NUM_POS];
	int[][] bet2CountP = new int[NUM_STREETS][NUM_POS];
	int[][] bet2StartEV = new int[NUM_STREETS][NUM_POS];
	int[][] bet2CountEV = new int[NUM_STREETS][NUM_POS];
	int[][] callBet2Count = new int[NUM_STREETS][NUM_POS];
	int[][] callBet2StartP = new int[NUM_STREETS][NUM_POS];
	int[][] callBet2CountP = new int[NUM_STREETS][NUM_POS];
	int[][] callBet2StartEV = new int[NUM_STREETS][NUM_POS];
	int[][] callBet2CountEV = new int[NUM_STREETS][NUM_POS];
	int[][] bet3Count = new int[NUM_STREETS][NUM_POS];
	int[][] bet3StartP = new int[NUM_STREETS][NUM_POS];
	int[][] bet3CountP = new int[NUM_STREETS][NUM_POS];
	int[][] bet3StartEV = new int[NUM_STREETS][NUM_POS];
	int[][] bet3CountEV = new int[NUM_STREETS][NUM_POS];
	int[][] callBet3Count = new int[NUM_STREETS][NUM_POS];
	int[][] callBet3StartP = new int[NUM_STREETS][NUM_POS];
	int[][] callBet3CountP = new int[NUM_STREETS][NUM_POS];
	int[][] callBet3StartEV = new int[NUM_STREETS][NUM_POS];
	int[][] callBet3CountEV = new int[NUM_STREETS][NUM_POS];
	int[][] bet4Count = new int[NUM_STREETS][NUM_POS];
	int[][] bet4StartP = new int[NUM_STREETS][NUM_POS];
	int[][] bet4CountP = new int[NUM_STREETS][NUM_POS];
	int[][] bet4StartEV = new int[NUM_STREETS][NUM_POS];
	int[][] bet4CountEV = new int[NUM_STREETS][NUM_POS];
	int[][] callBet4Count = new int[NUM_STREETS][NUM_POS];
	int[][] callBet4StartP = new int[NUM_STREETS][NUM_POS];
	int[][] callBet4CountP = new int[NUM_STREETS][NUM_POS];
	int[][] callBet4StartEV = new int[NUM_STREETS][NUM_POS];
	int[][] callBet4CountEV = new int[NUM_STREETS][NUM_POS];
	int[][] allinCount = new int[NUM_STREETS][NUM_POS];
	int[][] allinStartP = new int[NUM_STREETS][NUM_POS];
	int[][] allinCountP = new int[NUM_STREETS][NUM_POS];
	int[][] allinStartEV = new int[NUM_STREETS][NUM_POS];
	int[][] allinCountEV = new int[NUM_STREETS][NUM_POS];
	int[][] callAllinCount = new int[NUM_STREETS][NUM_POS];
	int[][] callAllinStartP = new int[NUM_STREETS][NUM_POS];
	int[][] callAllinCountP = new int[NUM_STREETS][NUM_POS];
	int[][] callAllinStartEV = new int[NUM_STREETS][NUM_POS];
	int[][] callAllinCountEV = new int[NUM_STREETS][NUM_POS];
// Special ??
// TODO
	int[][] checksCount = new int[NUM_STREETS][NUM_POS];
	int[][] checksStartP = new int[NUM_STREETS][NUM_POS];
	int[][] checksCountP = new int[NUM_STREETS][NUM_POS];
	int[][] checksStartEV = new int[NUM_STREETS][NUM_POS];
	int[][] checksCountEV = new int[NUM_STREETS][NUM_POS];
	int[][] cBetCount = new int[NUM_STREETS][NUM_POS];
	int[][] cBetStartP = new int[NUM_STREETS][NUM_POS];
	int[][] cBetCountP = new int[NUM_STREETS][NUM_POS];
	int[][] cBetStartEV = new int[NUM_STREETS][NUM_POS];
	int[][] cBetCountEV = new int[NUM_STREETS][NUM_POS];
	int[][] barrelCount = new int[NUM_STREETS][NUM_POS];
	int[][] barrelOpportunity = new int[NUM_STREETS][NUM_POS];
	int[][] barrelStartP = new int[NUM_STREETS][NUM_POS];
	int[][] barrelCountP = new int[NUM_STREETS][NUM_POS];
	int[][] barrelStartEV = new int[NUM_STREETS][NUM_POS];
	int[][] barrelCountEV = new int[NUM_STREETS][NUM_POS];
	int[][] checkRaiseCount = new int[NUM_STREETS][NUM_POS];
	int[][] checkRaiseOpportunity = new int[NUM_STREETS][NUM_POS];
	int[][] checkRaiseStartP = new int[NUM_STREETS][NUM_POS];
	int[][] checkRaiseCountP = new int[NUM_STREETS][NUM_POS];
	int[][] checkRaiseStartEV = new int[NUM_STREETS][NUM_POS];
	/*-************************************************************************************
	 * These values are calculated by scanning all or selected  instances of Player.
	 * TODO
	 ***************************************************************************************/
	int[] openStartP = new int[NUM_POS]; // TODO
	int[] openCountP = new int[NUM_POS]; // TODO

	/*-************************************************************************************
	 * Calculate percentages 
	************************************************************************************ */
	private void calculatePercentages2(Players play) {

		this.walkPer = total(play.walk, play.walkOper);
// Button
		this.isolateBUPer = total(play.isolateBU, play.isolateOperBU);
		this.minBetBUPer = total(play.minBetBU, play.minBetOperBU);
		this.stealBUPer = total(play.stealBU, play.stealOperBU);
		this.squeezeBUPer = total(play.squeezeBU, play.squeezeOperBU);
// Big Blind
		this.minBetBBPer = total(play.minBetBB, play.minBetOperBB);
		this.callMinBetBBPer = total(play.callMinBetBB, play.callMinBetOperBB);
		this.steal3BetBBPer = total(play.steal3BetBB, play.steal3BetOperBB);
		this.raisedBySBBBPer = total(play.raisedBySBBB, play.raisedBySBOperBB);
		this.steal3BetMinRaiseBBPer = total(play.steal3BetMinRaiseBB, play.steal3BetMinRaiseOperBB);
		this.stealCallBBPer = total(play.stealCallBB, play.stealCallOperBB);
		this.stealCallMinRaiseBBPer = total(play.stealCallMinRaiseBB, play.stealCallMinRaiseOperBB);
// Small Blind
		this.bet3MinSBPer = total(play.bet3MinSB, play.bet3MinOperSB);
		this.raisedByBBSBPer = total(play.raisedByBBSB, play.raisedByBBOperSB);
		this.steal3BetSBPer = total(play.steal3BetSB, play.steal3BetOperSB);
		this.stealCallSBPer = total(play.stealCallSB, play.stealCallOperSB);
		this.stealCallMinRaiseSBPer = total(play.stealCallMinRaiseSB, play.stealCallMinRaiseSBOper);
		this.foldedToSBPer = total(play.foldedToSB, play.foldedToSBOper);
	}

	/*- *****************************************************************************
	 * Player classification - FISH, NIT, LAG, TAG, REG, AVERAGE, .....
	 * Calculates several variables such as af. Aggression Factor
	 * These things apply to only one player. This instance of Players Class.
	 * Used to create summarized Players Classes with many players of the same type.
	 * Then used to createPlayerClassification.
	 * 
	 * Called when parsing of Hand History files is complete.
	 * Called from the  Class.
	  ******************************************************************************/
	void doPlayerClassification(Players play) {
		calculateWinRateAll(play);
		calculateWTSDAll(play);
		calculateAggPerAllStreets(play);
		calculatePostflopAF(play);
		calculatePFR(play);
		calculateVPIPPreflop(play);
		calculatePlayerType(play);
	}

	/*- *****************************************************************************
	 * Do all of the calculations
	***************************************************************************** */
	void doCalculations(Players play) {
		calculatePercentages(play);
		calculatePercentages2(play);
		calculatePercentages(play);
		calculateAFAll(play);
		calculateAggPctAll(play);
		calculateAFAll(play);
		calculatePFRAll(play);
		calculateWTSDAll(play);
		calculateWinRateAll(play);
		calculateAggPerAllStreets(play);
		calculateAggFactorAllStreets(play);
		calculateWinRateAll(play);
		calculateBet3FreqAll(play);
		calculateIP_OOPFreqAll(play);

		calculateVPIPAll(play);
	}

	/*-*****************************************************************************
	 * ChatGPT generated
	 * 
	  ******************************************************************************/
	private void chatGPT(Players play) {
		fourBetLimpCallHU = fourBetLimpCallHU(play);
		fourBetLimpReRaiseHU = fourBetLimpReRaiseHuPer(play);
		sB_Limp_Fold_HU = sB_Limp_Fold_HUPer(play);
		btnOpenLimp = btnOpenLimpPer(play);
		fourBetOpenLimp = fourBetOpenLimpPer(play);
		bB_Raise_vs_SB_Limp_UOP = bB_Raise_vs_SB_Limp_UOPPer(play);
		flopCBet = flopCBetPer(play);
		flopCBetIP = flopCBetIPPer(play);
		flopCBetOOP = flopCBetOOPPer(play);
		flopCBetCall = flopCBetCallPer(play);
		flopCBetReraise = flopCBetReraisePer(play);
		flopCBetFold = flopCBetFoldPer(play);
		flopCallDonkBet = flopCallDonkBetPer(play);
		flopRaiseDonkBet = flopRaiseDonkBetPer(play);
		flopFoldToDonkBet = flopFoldToDonkBetPer(play);
		skipFlopCBAndCheckCallOOP = skipFlopCBAndCheckCallOOPPer(play);
		skipFlopCBAndCheckRaiseOOP = skipFlopCBAndCheckRaiseOOPPer(play);
		skipFlopCBAndCheckFoldOOP = skipFlopCBAndCheckFoldOOPPer(play);
		flopCallCBIP = flopCallCBIPPer(play);
		flopRaiseCBIP = flopRaiseCBIPPer(play);
		flopFoldToCBIP = flopFoldToCBIPPer(play);
		flopCheckCallCBOOP = flopCheckCallCBOOPPer(play);
		flopCheckRaiseCBOOP = flopCheckRaiseCBOOPPer(play);
		flopCheckFoldToCBOOP = flopCheckFoldToCBOOPPer(play);
		flopDonkBetCall = flopDonkBetCallPer(play);
		flopDonkBetReRaise = flopDonkBetReRaisePer(play);
		flopDonkBetFold = flopDonkBetFoldPer(play);
		flopDonkBet = flopDonkBetPer(play);
		flopBetVsMissedCB = flopBetVsMissedCBPer(play);
		flopBetLimpedPotAttemptToStealIP = flopBetLimpedPotAttemptToStealIPPer(play);
		postflopAggressionPercentage = postflopAggressionPercentagePer(play);
		seenFlopOverall = seenFlopOverallPer(play);
		turnCBet = turnCBetPer(play);
		turnCBetIP = turnCBetIPPer(play);
		turnCBetOOP = turnCBetOOPPer(play);
		turnCBetCall = turnCBetCallPer(play);
		turnCBetReRaise = turnCBetReRaisePer(play);
		delayedTurnCBet = delayedTurnCBetPer(play);
		delayedTurnCBetIP = delayedTurnCBetIPPer(play);
		delayedTurnCBetOOP = delayedTurnCBetOOPPer(play);
		skipTurnCBAndCheckCallOOP = skipTurnCBAndCheckCallOOPPer(play);
		skipTurnCBAndCheckRaiseOOP = skipTurnCBAndCheckRaiseOOPPer(play);
		skipTurnCBAndCheckFoldOOP = skipTurnCBAndCheckFoldOOPPer(play);
		turnCallCBIP = turnCallCBIPPer(play);
		turnRaiseCBIP = turnRaiseCBIPPer(play);
		turnFoldToCBIP = turnFoldToCBIPPer(play);
		turnCheckCallCBOOP = turnCheckCallCBOOPPer(play);
		turnCheckRaiseCBOOP = turnCheckRaiseCBOOPPer(play);
		turnCheckFoldToCBOOP = turnCheckFoldToCBOOPPer(play);
		turnDonkBet = turnDonkBetPer(play);
		turnBetVsMissedCB = turnBetVsMissedCBPer(play);
		riverCBet = riverCBetPer(play);
		riverCBetIP = riverCBetIPPer(play);
		riverCBetOOP = riverCBetOOPPer(play);
		riverCBetCall = riverCBetCallPer(play);
		riverCBetReRaise = riverCBetReRaisePer(play);
		riverCBetFold = riverCBetFoldPer(play);
		skipRiverCBAndCheckCallOOP = skipRiverCBAndCheckCallOOPPer(play);
		skipRiverCBAndCheckRaiseOOP = skipRiverCBAndCheckRaiseOOPPer(play);
		skipRiverCBAndCheckFoldOOP = skipRiverCBAndCheckFoldOOPPer(play);
		riverCallCBIP = riverCallCBIPPer(play);
		riverRaiseCBIP = riverRaiseCBIPPer(play);
		riverFoldToCBIP = riverFoldToCBIPPer(play);
		riverCheckCallCBOOP = riverCheckCallCBOOPPer(play);
		riverCheckRaiseCBOOP = riverCheckRaiseCBOOPPer(play);
		riverCheckFoldToCBOOP = riverCheckFoldToCBOOPPer(play);
		riverDonkBet = riverDonkBetPer(play);
		riverBetVsMissedCB = riverBetVsMissedCBPer(play);
		wtsdWhenSawTurn = wtsdWhenSawTurnPer(play);
	}

	/*-*****************************************************************************
	 * Calculate vpip for all variables.
	 * 		double vpip  
	 * 		double preflopVPIP  
	 * 		double[][] vpipStreetPos = new double[NUM_STREETS][NUM_POS] 
	 ******************************************************************************/
	private void calculateVPIPAll(Players play) {
		this.vpip = calculateVPIPAllPosOneStreet(play, 0);
		this.preflopVPIP = calculateVPIPAllPosOneStreet(play, 0);
		this.vpipStreetPos = calculateVPIPStreetEachPos(play);
	}

	/*-*****************************************************************************
	 * Calculate  aggPct for all variables.
	 * 	double aggPct = 0.;
	 * double[][] aggPctStreetPos = new double[NUM_STREETS][NUM_POS];
	 ******************************************************************************/
	private void calculateAggPctAll(Players play) {
		this.aggPct = calculateAggPctPostflopAllPos(play);
		this.aggPctStreetPos = calculateAggPctEachPos(play);
	}

	/*-*****************************************************************************
	 * Calculate  af for all variables.
	 * double af;
	 * double preflopAf = 0; 
	 * double[][] afStreetPos = new double[NUM_STREETS][NUM_POS]; 
	 ******************************************************************************/
	private void calculateAFAll(Players play) {
		this.af = calculatePostflopAFStreet(play);
		this.afStreetPos = calculatePostflopAFEachStreet(play);
	}

	/*-*****************************************************************************
	 * Calculate pfr for all variables.
	 * 	double pfr;
	 * double[] pfrPos = new double[NUM_POS];
	 ******************************************************************************/
	private void calculatePFRAll(Players play) {
		this.pfr = calculatePFRAllPos(play);
		this.pfrPos = calculatePFREachPos(play);
	}

	/*-*****************************************************************************
	*Calculate wtsd for all variables.
	* double wtsd;
	 ******************************************************************************/
	private void calculateWTSDAll(Players play) {
		this.wtsdIfSawFlop = calculateWTSDSawFlop(play);
		this.wtsdIfSawTurn = calculateWTSDSawTurn(play);
		this.wtsdIfSawRiver = calculateWTSDSawRiver(play);
	}

	/*-*****************************************************************************
	 * Calculate winRate for all variables.
	
	 ******************************************************************************/
	private void calculateWinRateAll(Players play) {
		this.winRateBB100 = calculateWinRateAllStreets(play);
		this.winRateBB100Streets = calculateWinRateEachStreet(play);
	}

	/*-*****************************************************************************
	 * Calculate bet3Freq for all variables.
	 * double preflopBet3Freq 
	 * double[][] bet3FreqStreetPos = new double[NUM_STREETS][NUM_POS] 
	 ******************************************************************************/
	private void calculateBet3FreqAll(Players play) {
		this.preflopBet3Freq = calculatePreflop3BetFrequency(play);
		this.bet3FreqStreetPos = calculate3BetFrequencyEachStreetEachPos(play);
	}

	/*-*****************************************************************************
	 * Calculate IP and OPP frequency- In Position, Out Of Position
	 * double [] inPositionFrequencyStreets = new double[NUM_STREETS];
	 * double [] outOfPositionFrequency = new double[NUM_STREETS];
	 ******************************************************************************/
	private void calculateIP_OOPFreqAll(Players play) {
		this.inPositionFrequencyStreets = calculateFrequencyForIPEachStreet(play);
		this.outOfPositionFrequency = calculateFrequencyForOOPEachStreet(play);
	}

	/*-*****************************************************************************
	*
	 ******************************************************************************/
	double postflopAggressionPercentagePer(Players play) {
		// Calculate Postflop Aggression%
		int totalStreets = play.flopCount + play.turnCount + play.riverCount;
		int aggressiveStreets = play.cBetOperPos[0][0] + play.barrelOperPos[0][0] + play.cBetOperPos[1][0]
				+ play.barrelOperPos[1][0] + play.cBetOperPos[2][0] + play.barrelOperPos[2][0];

		return (double) aggressiveStreets / totalStreets * 100;
	}

	double skipTurnCBAndCheckFoldOOPPer(Players play) {
		// Calculate the percentage of hands player check-folds OOP after checking turn
		// CB opportunity
		int totalOpportunities = play.checkOperPos[1][0];
		int checkFoldOOP = play.foldOperPos[1][0];

		return (double) checkFoldOOP / totalOpportunities * 100;
	}

	double turnCallCBIPPer(Players play) {
		// Calculate the percentage of hands player calls IP when facing a CB on the
		// turn
		int totalOpportunities = play.callBet1OperPos[1][0] + play.callBet2OperPos[1][0] + play.callBet3OperPos[1][0]
				+ play.callBet4OperPos[1][0];

		return (double) totalOpportunities / play.turnCount * 100;
	}

	double turnRaiseCBIPPer(Players play) {
		// Calculate the percentage of hands player raises IP when facing a CB on the
		// turn
		int totalOpportunities = play.bet1OperPos[1][0] + play.bet2OperPos[1][0] + play.bet3OperPos[1][0]
				+ play.bet4OperPos[1][0];

		return (double) totalOpportunities / play.turnCount * 100;
	}

	double turnFoldToCBIPPer(Players play) {
		// Calculate the percentage of hands player folds IP when facing a CB on the
		// turn
		int totalOpportunities = play.foldOperPos[1][0];

		return (double) totalOpportunities / play.turnCount * 100;
	}

	double turnCheckCallCBOOPPer(Players play) {
		// Calculate the percentage of hands player check-calls OOP when facing a CB on
		// the turn
		int totalOpportunities = play.checkPos[1][0];
		int checkCallOOP = play.callBet1OperPos[1][0] + play.callBet2OperPos[1][0] + play.callBet3OperPos[1][0]
				+ play.callBet4OperPos[1][0];

		return (double) checkCallOOP / totalOpportunities * 100;
	}

	double riverCheckCallCBOOPPer(Players play) {
		// Calculate the percentage of hands player check-calls OOP when facing a CB on
		// the River
		int totalOpportunities = play.checkPos[2][0];
		int checkCallOOP = play.callBet1OperPos[2][0] + play.callBet2OperPos[2][0] + play.callBet3OperPos[2][0]
				+ play.callBet4OperPos[2][0];

		return (double) checkCallOOP / totalOpportunities * 100;
	}

	double wtsdWhenSawTurnPer(Players play) {
		// Calculate the percentage of hands player sees a showdown when he sees the
		// Turn
		int handsSawTurn = play.turnCount;
		int handsAtShowdown = play.wsdCount;

		return (double) handsAtShowdown / handsSawTurn * 100;
	}

	private void calculatezl(Players play) {
		this.inPositionFrequencyStreets = calculateFrequencyForIPEachStreet(play);
		this.outOfPositionFrequency = calculateFrequencyForOOPEachStreet(play);
	}

	double wtsdPer(Players play) {
		int handsSawFlop = play.flopCount; // Number of hands where the player saw the flop
		int handsWentToShowdown = play.wtsdFlopCount; // Number of hands where the player went to showdown after seeing
														// the flop

		if (handsSawFlop == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsWentToShowdown / handsSawFlop * 100.0;
		}
	}

	double wonSDPer(Players play) {
		int handsWentToShowdown = play.wtsdFlopCount; // Number of hands where the player went to showdown
		int handsWonAtShowdown = play.wsdCount; // Number of hands where the player won at showdown

		if (handsWentToShowdown == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsWonAtShowdown / handsWentToShowdown * 100.0;
		}
	}

	double coldCallRaiserPer(Players play) {
		int handsFacedRaisePreflop = play.raiseCount1; // Number of hands where the player faced a preflop raise
		int handsCalledRaiserPreflop = play.callBet1Pos[0][0]; // Number of hands where the player called the preflop
																// raise

		if (handsFacedRaisePreflop == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsCalledRaiserPreflop / handsFacedRaisePreflop * 100.0;
		}
	}

	double threeBetPer(Players play) {
		int handsFacedSingleRaisedPot = play.raiseCount1; // Number of hands where the player faced a single raised pot
		int handsThreeBetPreflop = play.bet2Pos[0][0]; // Number of hands where the player 3-bet preflop

		if (handsFacedSingleRaisedPot == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsThreeBetPreflop / handsFacedSingleRaisedPot * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player calls
	// preflop when facing a 3Bet. You can calculate it as follows:
	double callThreeBetPer(Players play) {
		int handsFacedThreeBet = play.bet3Pos[0][0]; // Number of hands where the player faced a 3Bet
		int handsCalledThreeBet = play.callBet3Pos[0][0]; // Number of hands where the player called the 3Bet

		if (handsFacedThreeBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsCalledThreeBet / handsFacedThreeBet * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player folds
	// preflop when facing a 3Bet. You can calculate it as follows:
	double foldToThreeBetPer(Players play) {
		int handsFacedThreeBet = play.bet3Pos[0][0]; // Number of hands where the player faced a 3Bet
		int handsFoldedToThreeBet = play.foldPos[0][0]; // Number of hands where the player folded to the 3Bet

		if (handsFacedThreeBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFoldedToThreeBet / handsFacedThreeBet * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player 4-bets
	// (reraises preflop) when facing a 3Bet. You can calculate it as follows:
	double fourBetPer(Players play) {
		int handsFacedThreeBet = play.bet3Pos[0][0]; // Number of hands where the player faced a 3Bet
		int handsFourBetThreeBet = play.bet4Pos[0][0]; // Number of hands where the player 4-bet the 3Bet

		if (handsFacedThreeBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFourBetThreeBet / handsFacedThreeBet * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player calls
	// preflop when facing the hero's 3Bet. You can calculate it as follows:
	double callHeroThreeBetPer(Players play) {
		int handsFacedHeroThreeBet = play.bet3Pos[0][0]; // Number of hands where the player faced hero's 3Bet
		int handsCalledHeroThreeBet = play.callBet3Pos[0][0]; // Number of hands where the player called hero's 3Bet

		if (handsFacedHeroThreeBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsCalledHeroThreeBet / handsFacedHeroThreeBet * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player folds
	// preflop when facing the hero's 3Bet. You can calculate it as follows:
	double foldToHeroThreeBetPer(Players play) {
		int handsFacedHeroThreeBet = play.bet3Pos[0][0]; // Number of hands where the player faced hero's 3Bet
		int handsFoldedToHeroThreeBet = play.foldPos[0][0]; // Number of hands where the player folded to hero's 3Bet

		if (handsFacedHeroThreeBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFoldedToHeroThreeBet / handsFacedHeroThreeBet * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player 4-bets
	// (reraises preflop) when facing the hero's 3Bet. You can calculate it as
	// follows:
	double fourBetVsHeroThreeBetPer(Players play) {
		int handsFacedHeroThreeBet = play.bet3Pos[0][0]; // Number of hands where the player faced hero's 3Bet
		int handsFourBetVsHeroThreeBet = play.bet4Pos[0][0]; // Number of hands where the player 4-bet vs hero's 3Bet

		if (handsFacedHeroThreeBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFourBetVsHeroThreeBet / handsFacedHeroThreeBet * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player raises
	// (3-bets) preflop when facing a raise and one or more callers. You can
	// calculate it as follows:
	double squeezePer(Players play) {
		int handsFacedRaiseAndCallersPreflop = play.bet3Pos[0][0]; // Number of hands where the player faced a raise and
																	// one or more callers preflop
		int handsSqueezedPreflop = play.bet3Pos[0][0]; // Number of hands where the player squeezed preflop

		if (handsFacedRaiseAndCallersPreflop == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsSqueezedPreflop / handsFacedRaiseAndCallersPreflop * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player calls preflop when facing an unraised pot. You can calculate it as follows:
	double limpPer(Players play) {
		int handsFacedUnraisedPotPreflop = play.limpOperPos[0][0]; // Number of hands where the player faced an unraised
																	// pot preflop
		int handsLimpedPreflop = play.limpPos[0][0]; // Number of hands where the player limped preflop

		if (handsFacedUnraisedPotPreflop == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsLimpedPreflop / handsFacedUnraisedPotPreflop * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player 4-bets based on their PFR% in unraised pots and the Raise 3Bet%. You can calculate it as follows:
	double fourBetRangePer(Players play) {
		double pfrPercentage = PFRPercentage(); // Calculate PFR% (Player's Preflop Raise Percentage) in unraised
												// pots
		double raise3BetPercentage = threeBetPer(play); // Calculate Raise 3Bet%

		return pfrPercentage * raise3BetPercentage;
	}

	// TODO
	double PFRPercentage() {
		return 0.;
	}

//  This percentage represents the proportion of hands where a player 5-bets based on their 3Bet% and Raise 4Bet%. You can calculate it as follows:
	double fiveBetRangePer(Players play) {
		double threeBetPercentage = threeBetPer(play); // Calculate 3Bet%
		double raise4BetPercentage = fourBetPer(play); // Calculate Raise 4Bet%

		return threeBetPercentage * raise4BetPercentage;
	}

//  This percentage represents the proportion of hands where a player raises preflop when facing two or more raisers. You can calculate it as follows:
	double coldFourBetPlusPer(Players play) {
		int handsFacedMultipleRaisers = play.bet4OperPos[0][0]; // Number of hands where the player faced two or more
																// raisers
		int handsColdFourBetPlus = play.bet4Pos[0][0]; // Number of hands where the player cold 4-bet (raised when
														// facing two or more raisers)

		if (handsFacedMultipleRaisers == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsColdFourBetPlus / handsFacedMultipleRaisers * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player raises first in an unopened pot preflop. You can calculate it as follows:
	double RFI(Players play) {
		int handsOpenUnopenedPot = play.flopCount; // Number of hands where the player raises first in an unopened pot
													// preflop
		int handsRFI = play.raiseCount1; // Number of hands where the player raises first in an unopened pot preflop

		if (handsOpenUnopenedPot == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRFI / handsOpenUnopenedPot * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player raises first in an unopened pot preflop from CO, BTN, or SB. You can calculate it as follows:
	double steal(Players play) {
		int handsOpenUnopenedPotCOBTN = play.flopCount; // Number of hands where the player raises first in an unopened
														// pot from CO, BTN, or SB
		int handsSteal = play.raiseCount1; // Number of hands where the player raises first in an unopened pot from CO,
											// BTN, or SB

		if (handsOpenUnopenedPotCOBTN == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsSteal / handsOpenUnopenedPotCOBTN * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player raises preflop with the minimum amount (2 big blinds) in an unopened pot. You can calculate it as follows:
	double openMinRaise(Players play) {
		int handsOpenUnopenedPot = play.flopCount; // Number of hands where the player raises first in an unopened pot
													// preflop
		int handsOpenMinRaise = play.bet1Pos[0][0]; // Number of hands where the player raises with the minimum amount
													// in an unopened pot preflop

		if (handsOpenUnopenedPot == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsOpenMinRaise / handsOpenUnopenedPot * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player folds when facing an attempted steal. You can calculate it as follows:
	double foldToSteal(Players play) {
		int handsFacedStealAttempt = play.bet1OperPos[0][0]; // Number of hands where the player faced an attempted
																// steal
		int handsFoldedToSteal = play.foldPos[0][0]; // Number of hands where the player folded when facing an attempted
														// steal

		if (handsFacedStealAttempt == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFoldedToSteal / handsFacedStealAttempt * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player raises
	// (3Bets) when facing an attempted steal. You can calculate it as follows:
	double reStealPer(Players play) {
		int handsReSteal = play.bet2OperPos[0][1]; // Number of hands where the player raises (3Bets) when facing an
													// attempted steal
		int stealAttempts = play.betCount1; // Number of steal attempts (raises) by opponents

		if (stealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsReSteal / stealAttempts * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player raises
	// (3Bets) when facing an attempted steal from the hero. You can calculate it as
	// follows:
	double threeBetReStealVsHeroPer(Players play) {
		int hands3BetReStealVsHero = play.bet2OperPos[0][1]; // Number of hands where the player raises (3Bets) when
																// facing an attempted steal from hero
		int heroStealAttempts = play.betCount1Hero; // Number of steal attempts (raises) by the hero

		if (heroStealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) hands3BetReStealVsHero / heroStealAttempts * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player folds on
	// the SB when facing an attempted steal. You can calculate it as follows:
	double sbFoldToStealPer(Players play) {
		int handsSbFoldToSteal = play.foldOperPos[0][0]; // Number of hands where the player folds on SB when facing an
															// attempted steal
		int stealAttempts = play.betCount1; // Number of steal attempts (raises) by opponents when player is on SB

		if (stealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsSbFoldToSteal / stealAttempts * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player calls on SB
	// when facing an attempted steal. You can calculate it as follows:
	double fourBetCallStealPer(Players play) {
		int hands4BetCallSteal = play.callBet1OperPos[0][1]; // Number of hands where the player calls on SB when facing
																// an attempted steal
		int stealAttempts = play.betCount1; // Number of steal attempts (raises) by opponents when player is on SB

		if (stealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) hands4BetCallSteal / stealAttempts * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player raises
	// (3Bets) on SB when facing an attempted steal. You can calculate it as
	// follows:
	double fourBetReStealPer(Players play) {
		int hands4BetReSteal = play.bet2OperPos[0][1]; // Number of hands where the player raises (3Bets) on SB when
														// facing an attempted steal
		int stealAttempts = play.betCount1; // Number of steal attempts (raises) by opponents when player is on SB

		if (stealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) hands4BetReSteal / stealAttempts * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player folds on BB
	// when facing an attempted steal. You can calculate it as follows:
	double bbFoldToStealPer(Players play) {
		int handsBbFoldToSteal = play.foldOperPos[1][0]; // Number of hands where the player folds on BB when facing an
															// attempted steal
		int stealAttempts = play.betCount1; // Number of steal attempts (raises) by opponents when player is on BB

		if (stealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsBbFoldToSteal / stealAttempts * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player calls on BB
	// when facing an attempted steal. You can calculate it as follows:
	double bbCallStealPer(Players play) {
		int handsBbCallSteal = play.callBet1OperPos[1][1]; // Number of hands where the player calls on BB when facing
															// an attempted steal
		int stealAttempts = play.betCount1; // Number of steal attempts (raises) by opponents when player is on BB

		if (stealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsBbCallSteal / stealAttempts * 100.0;
		}
	}

//	// This percentage represents the proportion of hands where a player raises (3Bets) on BB when facing an attempted steal. You can calculate it as follows:
	double bbReStealPer(Players play) {
		int handsBbReSteal = play.bet2OperPos[1][1]; // Number of hands where the player raises (3Bets) on BB when
														// facing an attempted steal
		int stealAttempts = play.betCount1; // Number of steal attempts (raises) by opponents when player is on BB

		if (stealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsBbReSteal / stealAttempts * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player raises
	// (3Bets) on BB when facing an attempted steal from the hero. You can calculate
	// it as follows:
	double bbThreeBetReStealVsHeroPer(Players play) {
		int handsBbThreeBetReStealVsHero = play.bet2OperPos[1][1]; // Number of hands where the player raises (3Bets) on
																	// BB when facing an attempted steal from hero
		int heroStealAttempts = play.betCount1Hero; // Number of steal attempts (raises) by the hero when player is on
													// BB

		if (heroStealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsBbThreeBetReStealVsHero / heroStealAttempts * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player folds after trying to steal and having been 3-bet. You can calculate it as follows:
	double foldVsRestealPer(Players play) {
		int handsAttemptedSteal = play.bet1Pos[0][0]; // Number of hands where the player attempted to steal
		int hands3BetAfterStealAttempt = play.bet3Pos[0][0]; // Number of hands where the player was 3-bet after
																// attempting to steal
		int handsFoldedVsResteal = play.foldPos[0][0]; // Number of hands where the player folded after being 3-bet

		if (handsAttemptedSteal == 0 || hands3BetAfterStealAttempt == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFoldedVsResteal / hands3BetAfterStealAttempt * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player calls after trying to steal and having been 3-bet. You can calculate it as follows:
	double callRestealPer(Players play) {
		int handsAttemptedSteal = play.bet1Pos[0][0]; // Number of hands where the player attempted to steal
		int hands3BetAfterStealAttempt = play.bet3Pos[0][0]; // Number of hands where the player was 3-bet after
																// attempting to steal
		int handsCalledResteal = play.callBet3Pos[0][0]; // Number of hands where the player called after being 3-bet

		if (handsAttemptedSteal == 0 || hands3BetAfterStealAttempt == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsCalledResteal / hands3BetAfterStealAttempt * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player raises (4-bets) after trying to steal and having been 3-bet. You can calculate it as follows:
	double fourBetVsRestealPer(Players play) {
		int handsAttemptedSteal = play.bet1Pos[0][0]; // Number of hands where the player attempted to steal
		int hands3BetAfterStealAttempt = play.bet3Pos[0][0]; // Number of hands where the player was 3-bet after
																// attempting to steal
		int handsFourBetVsResteal = play.bet4Pos[0][0]; // Number of hands where the player 4-bet after being 3-bet

		if (handsAttemptedSteal == 0 || hands3BetAfterStealAttempt == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFourBetVsResteal / hands3BetAfterStealAttempt * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player folds after trying to steal on SB and having been 3-bet. You can calculate it as follows:
	double fourBetFoldVsRestealPer(Players play) {
		int handsAttemptedStealSB = play.bet1Pos[0][0]; // Number of hands where the player attempted to steal from SB
		int hands3BetAfterStealAttempt = play.bet3Pos[0][0]; // Number of hands where the player was 3-bet after
																// attempting to steal
		int handsFoldedSBVsResteal = play.foldPos[0][0]; // Number of hands where the player folded from SB after being
															// 3-bet

		if (handsAttemptedStealSB == 0 || hands3BetAfterStealAttempt == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFoldedSBVsResteal / hands3BetAfterStealAttempt * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player calls after trying to steal on SB and having been 3-bet. You can calculate it as follows:
	double fourBetCallVsRestealPer(Players play) {
		int handsAttemptedStealSB = play.bet1Pos[0][0]; // Number of hands where the player attempted to steal from SB
		int hands3BetAfterStealAttempt = play.bet3Pos[0][0]; // Number of hands where the player was 3-bet after
																// attempting to steal
		int handsCalledSBVsResteal = play.callBet3Pos[0][0]; // Number of hands where the player called from SB after
																// being 3-bet

		if (handsAttemptedStealSB == 0 || hands3BetAfterStealAttempt == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsCalledSBVsResteal / hands3BetAfterStealAttempt * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player folds in
	// the SB when facing an attempted BTN steal. You can calculate it as follows:
	double foldSbVsBtnStealPer(Players play) {
		int handsFoldSbVsBtnSteal = play.foldOperPos[0][0]; // Number of hands where the player folds in SB when facing
															// an attempted BTN steal
		int btnStealAttempts = play.betCount1; // Number of BTN steal attempts (raises) by opponents

		if (btnStealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFoldSbVsBtnSteal / btnStealAttempts * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player folds in
	// the BB when facing an attempted BTN steal. You can calculate it as follows:
	double bbFoldToBtnStealPer(Players play) {
		int handsBbFoldToBtnSteal = play.foldOperPos[1][0]; // Number of hands where the player folds in BB when facing
															// an attempted BTN steal
		int btnStealAttempts = play.betCount1; // Number of BTN steal attempts (raises) by opponents when player is on
												// BB

		if (btnStealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsBbFoldToBtnSteal / btnStealAttempts * 100.0;
		}
	}

	// BB Fold to SB Steal:
	// This percentage represents the proportion of hands where a player folds in
	// the BB when facing an attempted SB steal. You can calculate it as follows:
	double bbFoldToSbStealPer(Players play) {
		int handsBbFoldToSbSteal = play.foldOperPos[1][0]; // Number of hands where the player folds in BB when facing
															// an attempted SB steal
		int sbStealAttempts = play.betCount1; // Number of SB steal attempts (raises) by opponents when player is on BB

		if (sbStealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsBbFoldToSbSteal / sbStealAttempts * 100.0;
		}
	}

	// Fold SB vs Steal from Button Hero:
	// This percentage represents the proportion of hands where a player folds in
	// the SB when facing an attempted BTN steal from the hero. You can calculate it
	// as follows:
	double foldSbVsBtnStealHeroPer(Players play) {
		int handsFoldSbVsBtnStealHero = play.foldOperPos[0][0]; // Number of hands where the player folds in SB when
																// facing an attempted BTN steal from the hero
		int btnStealAttemptsHero = play.betCount1Hero; // Number of BTN steal attempts (raises) by the hero

		if (btnStealAttemptsHero == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFoldSbVsBtnStealHero / btnStealAttemptsHero * 100.0;
		}
	}

	// Fold BB vs Steal from Button Hero:
	// This percentage represents the proportion of hands where a player folds in
	// the BB when facing an attempted BTN steal from the hero. You can calculate it
	// as follows:
	double bbFoldToBtnStealHeroPer(Players play) {
		int handsBbFoldToBtnStealHero = play.foldOperPos[1][0]; // Number of hands where the player folds in BB when
																// facing an attempted BTN steal from the hero
		int btnStealAttemptsHero = play.betCount1Hero; // Number of BTN steal attempts (raises) by the hero when player
														// is on BB

		if (btnStealAttemptsHero == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsBbFoldToBtnStealHero / btnStealAttemptsHero * 100.0;
		}
	}

	// Fold BB vs Steal from SB Hero:
	// This percentage represents the proportion of hands where a player folds in
	// the BB when facing an attempted SB steal from the hero. You can calculate it
	// as follows:
	double bbFoldToSbStealHeroPer(Players play) {
		int handsBbFoldToSbStealHero = play.foldOperPos[1][0]; // Number of hands where the player folds in BB when
																// facing an attempted SB steal from the hero
		int sbStealAttemptsHero = play.betCount1Hero; // Number of SB steal attempts (raises) by the hero when player is
														// on BB

		if (sbStealAttemptsHero == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsBbFoldToSbStealHero / sbStealAttemptsHero * 100.0;
		}
	}

	// 4Bet Fold to BTN Steal:
	// This percentage represents the proportion of hands where a player 4Bets and
	// folds when facing an attempted BTN steal. You can calculate it as follows:
	double fourBetFoldToBtnStealPer(Players play) {
		int handsFourBetFoldToBtnSteal = play.foldOperPos[1][1]; // Number of hands where the player 4Bets and folds
																	// when facing an attempted BTN steal
		int btnStealAttempts = play.betCount1; // Number of BTN steal attempts (raises) by opponents when player is on
												// BB

		if (btnStealAttempts == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFourBetFoldToBtnSteal / btnStealAttempts * 100.0;
		}
	}

	// 4Bet Limp-Call HU:
	// This percentage represents the proportion of hands where a player raises
	// (4Bets) after limping in from SB and calls when heads up with BB. You can
	// calculate it as follows:
	double fourBetLimpCallHU(Players play) {
		int handsFourBetLimpCallHU = play.fourBet[3][2]; // Number of hands where the player 4Bets after limping in from
															// SB and calls when HU with BB
		int handsLimpedSB = play.limped[2]; // Number of hands where the player limped in from SB

		if (handsLimpedSB == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFourBetLimpCallHU / handsLimpedSB * 100.0;
		}
	}

	// 4Bet Limp-ReRaise HU:
	// This percentage represents the proportion of hands where a player raises
	// (4Bets) after limping in from SB and facing a raise when heads up with BB.
	// You can calculate it as follows:
	double fourBetLimpReRaiseHuPer(Players play) {
		double fourBetLimpCallHU; // 4Bet Limp-ReRaise HU:
		fourBetLimpCallHU = fourBetLimpCallHU(play);
		int handsFourBetLimpReRaiseHu = play.bet4OperPos[0][1]; // Number of hands where the player raises (4Bets) after
																// limping in from SB and facing a raise when heads up
																// with BB
		int limpHuCount = play.limpPos[0][1]; // Number of times the player limps in from SB and is heads up with BB

		if (limpHuCount == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFourBetLimpReRaiseHu / limpHuCount * 100.0;
		}
	}

	// SB Limp-Fold HU:
	// This percentage represents the proportion of hands where a player folds after
	// limping in from SB and facing a raise when heads up with BB. You can
	// calculate it as follows:
	double sB_Limp_Fold_HUPer(Players play) {
		int handsSbLimpFoldHu = play.foldPos[0][1]; // Number of hands where the player folds after limping in from SB
													// and facing a raise when heads up with BB
		int limpHuCount = play.limpPos[0][1]; // Number of times the player limps in from SB and is heads up with BB

		if (limpHuCount == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsSbLimpFoldHu / limpHuCount * 100.0;
		}
	}

	// BTN Open Limp:
	// This percentage represents the proportion of hands where a player calls
	// preflop when in BTN facing an unopened pot. You can calculate it as follows:
	double btnOpenLimpPer(Players play) {
		int handsBtnOpenLimp = play.limpOperPos[0][3]; // Number of hands where the player calls preflop when in BTN
														// facing an unopened pot
		int btnUnopenedPotCount = play.flopCount; // Number of unopened pots when player is in BTN

		if (btnUnopenedPotCount == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsBtnOpenLimp / btnUnopenedPotCount * 100.0;
		}
	}

	// BB Raise vs SB Limp UOP:
	// This percentage represents the proportion of hands where a player raises in
	// BB when facing an unopened pot with a SB limper. You can calculate it as
	// follows:
	double bbRaiseVsSbLimpUopPer(Players play) {
		int handsBbRaiseVsSbLimpUop = play.bet1OperPos[1][1]; // Number of hands where the player raises in BB when
																// facing an unopened pot with a SB limper
		int sbLimpUopCount = play.limpOperPos[0][1]; // Number of times the player limps in from SB and there is an
														// unopened pot

		if (sbLimpUopCount == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsBbRaiseVsSbLimpUop / sbLimpUopCount * 100.0;
		}
	}

	// Flop CBet-Call:
	// This percentage represents the proportion of hands where a player bets the
	// Flop as PFR and calls a raise. You can calculate it as follows:
	double flopCBetCallPer(Players play) {
		int handsFlopCBetCall = play.callBet1OperPos[1][1]; // Number of hands where the player bets the Flop as PFR and
															// calls a raise
		int handsFlopCBet = play.cBetOperPos[1][1]; // Number of hands where the player bets the Flop as PFR

		if (handsFlopCBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopCBetCall / handsFlopCBet * 100.0;
		}
	}

	// Flop CBet-Reraise:
	// This percentage represents the proportion of hands where a player bets the
	// Flop as PFR and reraises a raise. You can calculate it as follows:
	double flopCBetReraisePer(Players play) {
		int handsFlopCBetReraise = play.bet2OperPos[1][1]; // Number of hands where the player bets the Flop as PFR and
															// reraises a raise
		int handsFlopCBet = play.cBetOperPos[1][1]; // Number of hands where the player bets the Flop as PFR

		if (handsFlopCBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopCBetReraise / handsFlopCBet * 100.0;
		}
	}

	// Flop CBet-Fold:
	// This percentage represents the proportion of hands where a player bets the
	// Flop as PFR and folds to a raise. You can calculate it as follows:
	double flopCBetFoldPer(Players play) {
		int handsFlopCBetFold = play.foldBet1OperPos[1][1]; // Number of hands where the player bets the Flop as PFR and
															// folds to a raise
		int handsFlopCBet = play.cBetOperPos[1][1]; // Number of hands where the player bets the Flop as PFR

		if (handsFlopCBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopCBetFold / handsFlopCBet * 100.0;
		}
	}

	// Flop Call Donk Bet:
	// This percentage represents the proportion of hands where a player calls a
	// donkbet on the Flop. You can calculate it as follows:
	double flopCallDonkBetPer(Players play) {
		int handsFlopCallDonkBet = play.callBet1OperPos[1][0]; // Number of hands where the player calls a donkbet on
																// the Flop
		int handsFlopDonkBet = play.bet1OperPos[0][0]; // Number of hands where the player bets (donkbets) on the Flop

		if (handsFlopDonkBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopCallDonkBet / handsFlopDonkBet * 100.0;
		}
	}

	// Flop Raise Donk Bet:
	// This percentage represents the proportion of hands where a player raises a
	// donkbet on the Flop. You can calculate it as follows:
	double flopRaiseDonkBetPer(Players play) {
		int handsFlopRaiseDonkBet = play.bet2OperPos[1][0]; // Number of hands where the player raises a donkbet on the
															// Flop
		int handsFlopDonkBet = play.bet1OperPos[0][0]; // Number of hands where the player bets (donkbets) on the Flop

		if (handsFlopDonkBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopRaiseDonkBet / handsFlopDonkBet * 100.0;
		}
	}

	// Flop Fold to Donk Bet:
	// This percentage represents the proportion of hands where a player folds to a
	// donkbet on the Flop. You can calculate it as follows:
	double flopFoldToDonkBetPer(Players play) {
		int handsFlopFoldToDonkBet = play.foldBet1OperPos[1][0]; // Number of hands where the player folds to a donkbet
																	// on the Flop
		int handsFlopDonkBet = play.bet1OperPos[0][0]; // Number of hands where the player bets (donkbets) on the Flop

		if (handsFlopDonkBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopFoldToDonkBet / handsFlopDonkBet * 100.0;
		}
	}

	// Skip Flop CB and Check-Call OOP:
	// This percentage represents the proportion of hands where a player check-calls
	// out of position (OOP) after checking the flop CB opportunity. You can
	// calculate it as follows:
	double skipFlopCBAndCheckCallOOPPer(Players play) {
		int handsSkipFlopCBCheckCallOOP = play.callBet1OperPos[1][1]; // Number of hands where the player check-calls
																		// OOP after checking flop CB opportunity
		int handsSkipFlopCB = play.checkBet1OperPos[1][1]; // Number of hands where the player checks flop CB
															// opportunity

		if (handsSkipFlopCB == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsSkipFlopCBCheckCallOOP / handsSkipFlopCB * 100.0;
		}
	}

	// Skip Flop CB and Check-Raise OOP:
	// This percentage represents the proportion of hands where a player
	// check-raises out of position (OOP) after checking the flop CB opportunity.
	// You can calculate it as follows:
	double skipFlopCBAndCheckRaiseOOPPer(Players play) {
		int handsSkipFlopCBCheckRaiseOOP = play.bet1OperPos[1][1]; // Number of hands where the player check-raises OOP
																	// after checking flop CB opportunity
		int handsSkipFlopCB = play.checkBet1OperPos[1][1]; // Number of hands where the player checks flop CB
															// opportunity

		if (handsSkipFlopCB == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsSkipFlopCBCheckRaiseOOP / handsSkipFlopCB * 100.0;
		}
	}

	// Skip Flop CB and Check-Fold OOP:
	// This percentage represents the proportion of hands where a player check-folds
	// out of position (OOP) after checking the flop CB opportunity. You can
	// calculate it as follows:
	double skipFlopCBAndCheckFoldOOPPer(Players play) {
		int handsSkipFlopCBCheckFoldOOP = play.foldBet1OperPos[1][1]; // Number of hands where the player check-folds
																		// OOP after checking flop CB opportunity
		int handsSkipFlopCB = play.checkBet1OperPos[1][1]; // Number of hands where the player checks flop CB
															// opportunity

		if (handsSkipFlopCB == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsSkipFlopCBCheckFoldOOP / handsSkipFlopCB * 100.0;
		}
	}

	// Flop Call CB IP:
	// This percentage represents the proportion of hands where a player calls when
	// in position (IP) and facing a CB on the flop. You can calculate it as
	// follows:
	double flopCallCBIPPer(Players play) {
		int handsFlopCallCBCallIP = play.callBet1OperPos[0][1]; // Number of hands where the player calls IP when facing
																// a CB on the flop
		int handsFlopCBIP = play.bet1OperPos[0][1]; // Number of hands where the player CBets IP on the flop

		if (handsFlopCBIP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopCallCBCallIP / handsFlopCBIP * 100.0;
		}
	}
	// Flop Raise CB IP:

	// This percentage represents the proportion of hands where a player raises when
	// in position (IP) and facing a CB on the flop. You can calculate it as
	// follows:
	double flopRaiseCBIPPer(Players play) {
		int handsFlopRaiseCBCallIP = play.bet2OperPos[0][1]; // Number of hands where the player raises IP when facing a
																// CB on the flop
		int handsFlopCBIP = play.bet1OperPos[0][1]; // Number of hands where the player CBets IP on the flop

		if (handsFlopCBIP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopRaiseCBCallIP / handsFlopCBIP * 100.0;
		}
	}

	// Flop Fold to CB IP:
	// This percentage represents the proportion of hands where a player folds when
	// in position (IP) and facing a CB on the flop. You can calculate it as
	// follows:
	double flopFoldToCBIPPer(Players play) {
		int handsFlopFoldToCBIP = play.foldBet1OperPos[0][1]; // Number of hands where the player folds IP when facing a
																// CB on the flop
		int handsFlopCBIP = play.bet1OperPos[0][1]; // Number of hands where the player CBets IP on the flop

		if (handsFlopCBIP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopFoldToCBIP / handsFlopCBIP * 100.0;
		}
	}

	// Flop Check-Call CB OOP:
	// This percentage represents the proportion of hands where a player check-calls
	// out of position (OOP) when facing a CB on the flop. You can calculate it as
	// follows:
	double flopCheckCallCBOOPPer(Players play) {
		int handsFlopCheckCallCB = play.callBet1OperPos[1][1]; // Number of hands where the player check-calls OOP when
																// facing a CB on the flop
		int handsFlopCB = play.bet1OperPos[1][1]; // Number of hands where the player CBets on the flop

		if (handsFlopCB == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopCheckCallCB / handsFlopCB * 100.0;
		}
	}

	// Flop Check-Raise CB OOP:
	// This percentage represents the proportion of hands where a player
	// check-raises out of position (OOP) when facing a CB on the flop. You can
	// calculate it as follows:
	double flopCheckRaiseCBOOPPer(Players play) {
		int handsFlopCheckRaiseCB = play.bet2OperPos[1][1]; // Number of hands where the player check-raises OOP when
															// facing a CB on the flop
		int handsFlopCB = play.bet1OperPos[1][1]; // Number of hands where the player CBets on the flop

		if (handsFlopCB == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopCheckRaiseCB / handsFlopCB * 100.0;
		}
	}

	// Flop Check-Fold to CB OOP:
	// This percentage represents the proportion of hands where a player check-folds
	// out of position (OOP) when facing a CB on the flop. You can calculate it as
	// follows:
	double flopCheckFoldToCBOOPPer(Players play) {
		int handsFlopCheckFoldToCB = play.foldBet1OperPos[1][1]; // Number of hands where the player check-folds OOP
																	// when facing a CB on the flop
		int handsFlopCB = play.bet1OperPos[1][1]; // Number of hands where the player CBets on the flop

		if (handsFlopCB == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopCheckFoldToCB / handsFlopCB * 100.0;
		}
	}

	// Flop Donk Bet-Call:
	// This percentage represents the proportion of hands where a player bets on the
	// flop into the preflop aggressor and calls a raise. You can calculate it as
	// follows:
	double flopDonkBetCallPer(Players play) {
		int handsFlopDonkBetCall = play.callBet1OperPos[1][0]; // Number of hands where the player donk bets on the flop
																// and calls a raise
		int handsFlopDonkBet = play.bet1OperPos[1][0]; // Number of hands where the player donk bets on the flop

		if (handsFlopDonkBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopDonkBetCall / handsFlopDonkBet * 100.0;
		}
	}

	// Flop Donk Bet-ReRaise:
	// This percentage represents the proportion of hands where a player bets on the
	// flop into the preflop aggressor and re-raises a raise. You can calculate it
	// as follows:
	double flopDonkBetReRaisePer(Players play) {
		int handsFlopDonkBetReraise = play.bet3OperPos[1][0]; // Number of hands where the player donk bets on the flop
																// and re-raises a raise
		int handsFlopDonkBet = play.bet1OperPos[1][0]; // Number of hands where the player donk bets on the flop

		if (handsFlopDonkBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopDonkBetReraise / handsFlopDonkBet * 100.0;
		}
	}

	// Flop Donk Bet-Fold:
	// This percentage represents the proportion of hands where a player bets on the
	// flop into the preflop aggressor and folds to a raise. You can calculate it as
	// follows:
	double flopDonkBetFoldPer(Players play) {
		int handsFlopDonkBetFold = play.foldBet1OperPos[1][0]; // Number of hands where the player donk bets on the flop
																// and folds to a raise
		int handsFlopDonkBet = play.bet1OperPos[1][0]; // Number of hands where the player donk bets on the flop

		if (handsFlopDonkBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopDonkBetFold / handsFlopDonkBet * 100.0;
		}
	}

	// Flop Donk Bet:
	// This percentage represents the proportion of hands where a player bets out of
	// position (OOP) on the flop into the player who took the last aggressive
	// action preflop. You can calculate it as follows:
	double flopDonkBetPer(Players play) {
		int handsFlopDonkBet = play.bet1OperPos[1][1]; // Number of hands where the player donk bets on the flop into
														// the last preflop aggressor

		return (double) handsFlopDonkBet / play.handsPlayed * 100.0;
	}

	// Flop Bet vs Missed CB:
	// This percentage represents the proportion of hands where a player bets on the
	// flop after the opponent missed a flop continuation bet. You can calculate it
	// as follows:
	double flopBetVsMissedCBPer(Players play) {
		int handsFlopBetVsMissedCB = play.barrelOperPos[1][0]; // Number of hands where the player bets on the flop
																// after opponent missed a flop CB
		int handsMissedCB = play.checkOperPos[1][0]; // Number of hands where the opponent missed a flop CB opportunity

		if (handsMissedCB == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopBetVsMissedCB / handsMissedCB * 100.0;
		}
	}

	// Flop Bet - Limped Pot Attempt to Steal IP:
	// This percentage represents the proportion of hands where a player bets on the
	// flop in a limped pot when in position (IP) and attempts to steal. You can
	// calculate it as follows:
	double flopBetLimpedPotAttemptToStealIPPer(Players play) {
		int handsFlopBetLimpedPotStealIP = play.bet1OperPos[1][2]; // Number of hands where the player bets on the flop
																	// in a limped pot when IP
		int handsLimpedPot = play.limpOperPos[1][2]; // Number of hands where it's a limped pot

		if (handsLimpedPot == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopBetLimpedPotStealIP / handsLimpedPot * 100.0;
		}
	}

	// Turn CBet:
	// This percentage represents the proportion of hands where a player bets on the
	// Turn as the preflop raiser (PFR) after CBetting the Flop as PFR. You can
	// calculate it as follows:
	double turnCBetPer(Players play) {
		int handsTurnCBet = play.bet2OperPos[1][0]; // Number of hands where the player bets the Turn as PFR after
													// CBetting the Flop as PFR
		int handsFlopCBet = play.cBetOperPos[1][0]; // Number of hands where the player CBets the Flop as PFR

		if (handsFlopCBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsTurnCBet / handsFlopCBet * 100.0;
		}
	}

	// Turn CBet IP:
	// This percentage represents the proportion of hands where a player bets on the
	// Turn as PFR after CBetting the Flop as PFR when they are the last to act on
	// the flop. You can calculate it as follows:
	double turnCBetIPPer(Players play) {
		int handsTurnCBetIP = play.bet2OperPos[1][1]; // Number of hands where the player bets the Turn as PFR after
														// CBetting the Flop as PFR when last to act on the flop
		int handsFlopCBetLastToAct = play.cBetOperPos[1][1]; // Number of hands where the player CBets the Flop as PFR
																// when last to act on the flop

		if (handsFlopCBetLastToAct == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsTurnCBetIP / handsFlopCBetLastToAct * 100.0;
		}
	}

	// Turn CBet OOP:
	// This percentage represents the proportion of hands where a player bets on the
	// Turn as PFR after CBetting the Flop as PFR when they are NOT the last to act
	// on the flop. You can calculate it as follows:
	double turnCBetOOPPer(Players play) {
		int handsTurnCBetOOP = play.bet2OperPos[1][2]; // Number of hands where the player bets the Turn as PFR after
														// CBetting the Flop as PFR when NOT last to act on the flop
		int handsFlopCBetNotLastToAct = play.cBetOperPos[1][2]; // Number of hands where the player CBets the Flop as
																// PFR when NOT last to act on the flop

		if (handsFlopCBetNotLastToAct == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsTurnCBetOOP / handsFlopCBetNotLastToAct * 100.0;
		}
	}

	// Turn CBet-Call:
	// This percentage represents the proportion of hands where a player bets the
	// Turn as PFR after CBetting the Flop as PFR and calls a raise. You can
	// calculate it as follows:
	double turnCBetCallPer(Players play) {
		int handsTurnCBetCall = play.callBet2OperPos[1][0]; // Number of hands where the player bets the Turn as PFR
															// after CBetting the Flop as PFR and calls a raise
		int handsTurnCBet = play.bet2OperPos[1][0]; // Number of hands where the player bets the Turn as PFR after
													// CBetting the Flop as PFR

		if (handsTurnCBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsTurnCBetCall / handsTurnCBet * 100.0;
		}
	}

	// Turn CBet-ReRaise:
	// This percentage represents the proportion of hands where a player bets the
	// Turn as PFR after CBetting the Flop as PFR and re-raises a raise. You can
	// calculate it as follows:
	double turnCBetReRaisePer(Players play) {
		int handsTurnCBetReraise = play.bet4OperPos[1][0]; // Number of hands where the player bets the Turn as PFR
															// after CBetting the Flop as PFR and re-raises a raise
		int handsTurnCBet = play.bet2OperPos[1][0]; // Number of hands where the player bets the Turn as PFR after
													// CBetting the Flop as PFR

		if (handsTurnCBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsTurnCBetReraise / handsTurnCBet * 100.0;
		}
	}

	// Delayed Turn CBet:
	// This percentage represents the proportion of hands where a player bets the
	// turn after checking a flop CB opportunity. You can calculate it as follows:
	double delayedTurnCBetPer(Players play) {
		int handsDelayedTurnCBet = play.bet1OperPos[1][4]; // Number of hands where the player bets the turn after
															// checking flop CB opportunity
		int handsDelayedTurnCBetOpportunity = play.checkBet1OperPos[1][4]; // Number of hands where the player has the
																			// opportunity to check the flop and then
																			// bet the turn

		if (handsDelayedTurnCBetOpportunity == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsDelayedTurnCBet / handsDelayedTurnCBetOpportunity * 100.0;
		}
	}

	// Delayed Turn CBet IP:
	// This percentage represents the proportion of hands where a player bets the
	// turn after checking a flop CB opportunity when they are the last to act on
	// the flop. You can calculate it as follows:
	double delayedTurnCBetIPPer(Players play) {
		int handsDelayedTurnCBetIP = play.bet2OperPos[1][4]; // Number of hands where the player bets the turn after
																// checking flop CB opportunity when last to act on the
																// flop
		int handsDelayedTurnCBetOpportunityIP = play.checkBet2OperPos[1][4]; // Number of hands where the player has the
																				// opportunity to check the flop and
																				// then bet the turn when last to act on
																				// the flop

		if (handsDelayedTurnCBetOpportunityIP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return ((double) handsDelayedTurnCBetIP / (double) handsDelayedTurnCBetOpportunityIP) * 100.0;
		}
	}

	// Delayed Turn CBet OOP:
	// This percentage represents the proportion of hands where a player bets the
	// turn after checking a flop CB opportunity when they are NOT the last to act
	// on the flop. You can calculate it as follows:
	double delayedTurnCBetOOPPer(Players play) {
		int handsDelayedTurnCBetOOP = play.bet2OperPos[1][5]; // Number of hands where the player bets the turn after
																// checking flop CB opportunity when NOT last to act on
																// the flop
		int handsDelayedTurnCBetOpportunityOOP = play.checkBet2OperPos[1][5]; // Number of hands where the player has
																				// the opportunity to check the flop and
																				// then bet the turn when NOT last to
																				// act on the flop

		if (handsDelayedTurnCBetOpportunityOOP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsDelayedTurnCBetOOP / handsDelayedTurnCBetOpportunityOOP * 100.0;
		}
	}

	// Skip Turn CB and Check-Call OOP:
	// This percentage represents the proportion of hands where a player check-calls
	// out of position (OOP) after checking a turn CB opportunity. You can calculate
	// it as follows:
	double skipTurnCBAndCheckCallOOPPer(Players play) {
		int handsSkipTurnCBCheckCallOOP = play.callBet2OperPos[1][4]; // Number of hands where the player check-calls
																		// OOP after checking a turn CB opportunity
		int handsSkipTurnCBCheckOpportunityOOP = play.checkBet2OperPos[1][6]; // Number of hands where the player has
																				// the opportunity to check the turn and
																				// then check-call OOP

		if (handsSkipTurnCBCheckOpportunityOOP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsSkipTurnCBCheckCallOOP / handsSkipTurnCBCheckOpportunityOOP * 100.0;
		}
	}

	// Skip Turn CB and Check-Raise OOP:
	// This percentage represents the proportion of hands where a player
	// check-raises out of position (OOP) after checking a turn CB opportunity. You
	// can calculate it as follows:
	double skipTurnCBAndCheckRaiseOOPPer(Players play) {
		int handsSkipTurnCBCheckRaiseOOP = play.raiseBet2OperPos[1][4]; // Number of hands where the player check-raises
																		// OOP after checking a turn CB opportunity
		int handsSkipTurnCBCheckOpportunityOOP = play.checkBet2OperPos[1][6]; // Number of hands where the player has
																				// the opportunity to check the turn and
																				// then check-raise OOP

		if (handsSkipTurnCBCheckOpportunityOOP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsSkipTurnCBCheckRaiseOOP / handsSkipTurnCBCheckOpportunityOOP * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player
	// check-raises out of position (OOP) when facing a CB on the Turn. You can
	// calculate it as follows:
	double turnCheckRaiseCBOOPPer(Players play) {
		int handsTurnCheckRaiseCBOOP = play.raiseBet2OperPos[1][1]; // Number of hands where the player check-raises OOP
																	// when facing a CB on the Turn
		int handsTurnCBOOP = play.bet2OperPos[1][3]; // Number of hands where the player bets the Turn as PFR when OOP

		if (handsTurnCBOOP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsTurnCheckRaiseCBOOP / handsTurnCBOOP * 100.0;
		}
	}

	// Turn Check-Fold to CB OOP:
	// This percentage represents the proportion of hands where a player check-folds
	// out of position (OOP) when facing a CB on the Turn. You can calculate it as
	// follows:
	double turnCheckFoldToCBOOPPer(Players play) {
		int handsTurnCheckFoldToCBOOP = play.foldBet2OperPos[1][1]; // Number of hands where the player check-folds OOP
																	// when facing a CB on the Turn
		int handsTurnCBOOP = play.bet2OperPos[1][3]; // Number of hands where the player bets the Turn as PFR when OOP

		if (handsTurnCBOOP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsTurnCheckFoldToCBOOP / handsTurnCBOOP * 100.0;
		}
	}

	// Turn Donk Bet:
	// This percentage represents the proportion of hands where a player bets out of
	// position (OOP) on the Turn into a player who took the last aggressive action
	// on the flop. You can calculate it as follows:
	double turnDonkBetPer(Players play) {
		int handsTurnDonkBet = play.bet3OperPos[1][1]; // Number of hands where the player donk bets OOP on the Turn
														// into a player who took the last aggressive action on the flop
		int handsTurnCB3BetOOP = play.bet3OperPos[1][3]; // Number of hands where the player bets the Turn as PFR after
															// CBetting the flop and facing a 3Bet when OOP

		if (handsTurnCB3BetOOP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsTurnDonkBet / handsTurnCB3BetOOP * 100.0;
		}
	}

	// Turn Bet vs Missed CB:
	// This percentage represents the proportion of hands where a player bets on the
	// Turn after the opponent missed a Turn continuation bet. You can calculate it
	// as follows:
	double turnBetVsMissedCBPer(Players play) {
		int handsTurnBetVsMissedCB = play.bet3OperPos[1][5]; // Number of hands where the player bets on the Turn after
																// the opponent missed a Turn continuation bet
		int handsTurnMissedCBOOP = play.bet2OperPos[1][2]; // Number of hands where the opponent misses a Turn CB when
															// the player is OOP

		if (handsTurnMissedCBOOP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsTurnBetVsMissedCB / handsTurnMissedCBOOP * 100.0;
		}
	}

	// River CBet:
	// This percentage represents the proportion of hands where a player bets on the
	// River as the preflop raiser (PFR) after CBetting the Flop and Turn and then
	// folds to a raise. You can calculate it as follows:
	double riverCBetAndFoldToRaisePer(Players play) {
		int handsRiverCBetAndFoldToRaise = play.bet3OperPos[1][6]; // Number of hands where the player bets the River as
																	// PFR after CBetting the Flop and Turn and folds to
																	// a raise
		int handsRiverCBetAndCallRaise = play.bet3OperPos[1][7]; // Number of hands where the player bets the River as
																	// PFR after CBetting the Flop and Turn and calls a
																	// raise

		if (handsRiverCBetAndFoldToRaise + handsRiverCBetAndCallRaise == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCBetAndFoldToRaise / (handsRiverCBetAndFoldToRaise + handsRiverCBetAndCallRaise)
					* 100.0;
		}
	}

	// Skip River CB and Check-Call OOP:
	// This percentage represents the proportion of hands where a player checks and
	// calls out of position (OOP) after checking the opportunity to CB on the
	// River. You can calculate it as follows:
	double skipRiverCBAndCheckCallOOPPer(Players play) {
		int handsRiverCheckCallOOP = play.checkCallOperPos[1][8]; // Number of hands where the player check-calls OOP
																	// after checking river CB opportunity
		int handsRiverCheckOpportunityOOP = play.checkOperPos[1][4]; // Number of hands where the player checks the
																		// River when OOP

		if (handsRiverCheckOpportunityOOP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCheckCallOOP / handsRiverCheckOpportunityOOP * 100.0;
		}
	}

	// Skip River CB and Check-Raise OOP:
	// This percentage represents the proportion of hands where a player checks and
	// raises out of position (OOP) after checking the opportunity to CB on the
	// River. You can calculate it as follows:
	double skipRiverCBAndCheckRaiseOOPPer(Players play) {
		int handsRiverCheckRaiseOOP = play.checkRaiseOperPos[1][8]; // Number of hands where the player check-raises OOP
																	// after checking river CB opportunity
		int handsRiverCheckOpportunityOOP = play.checkOperPos[1][4]; // Number of hands where the player checks the
																		// River when OOP

		if (handsRiverCheckOpportunityOOP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCheckRaiseOOP / handsRiverCheckOpportunityOOP * 100.0;
		}
	}

	// Skip River CB and Check-Fold OOP:
	// This percentage represents the proportion of hands where a player checks and
	// folds out of position (OOP) after checking the opportunity to CB on the
	// River. You can calculate it as follows:
	double skipRiverCBAndCheckFoldOOPPer(Players play) {
		int handsRiverCheckFoldOOP = play.checkFoldOperPos[1][8]; // Number of hands where the player check-folds OOP
																	// after checking river CB opportunity
		int handsRiverCheckOpportunityOOP = play.checkOperPos[1][4]; // Number of hands where the player checks the
																		// River when OOP

		if (handsRiverCheckOpportunityOOP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCheckFoldOOP / handsRiverCheckOpportunityOOP * 100.0;
		}
	}

	// River Call CB IP:
	// This percentage represents the proportion of hands where a player calls in
	// position (IP) when facing a CB on the River. You can calculate it as follows:
	double riverCallCBIPPer(Players play) {
		int handsRiverCallCBIP = play.callBet3OperPos[0][7]; // Number of hands where the player calls IP when facing a
																// CB on the River
		int handsRiverCBIP = play.bet3OperPos[0][7]; // Number of hands where the player bets the River as PFR when IP

		if (handsRiverCBIP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCallCBIP / handsRiverCBIP * 100.0;
		}
	}

	// River Raise CB IP:
	// This percentage represents the proportion of hands where a player raises in
	// position (IP) when facing a CB on the River. You can calculate it as follows:
	double riverRaiseCBIPPer(Players play) {
		int handsRiverRaiseCBIP = play.raiseBet3OperPos[0][7]; // Number of hands where the player raises IP when facing
																// a CB on the River
		int handsRiverCBIP = play.bet3OperPos[0][7]; // Number of hands where the player bets the River as PFR when IP

		if (handsRiverCBIP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverRaiseCBIP / handsRiverCBIP * 100.0;
		}
	}

	// River Fold to CB IP:
	// This percentage represents the proportion of hands where a player folds in
	// position (IP) when facing a CB on the River. You can calculate it as follows:
	double riverFoldToCBIPPer(Players play) {
		int handsRiverFoldToCBIP = play.foldBet3OperPos[0][7]; // Number of hands where the player folds IP when facing
																// a CB on the River
		int handsRiverCBIP = play.bet3OperPos[0][7]; // Number of hands where the player bets the River as PFR when IP

		if (handsRiverCBIP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverFoldToCBIP / handsRiverCBIP * 100.0;
		}
	}

	// River Check-Raise CB OOP:
	// This percentage represents the proportion of hands where a player
	// check-raises out of position (OOP) when facing a CB on the River. You can
	// calculate it as follows:
	double riverCheckRaiseCBOOPPer(Players play) {
		int handsRiverCheckRaiseCBOOP = play.checkRaiseOperPos[1][7]; // Number of hands where the player check-raises
																		// OOP when facing a CB on the River
		int handsRiverCB = play.bet3OperPos[1][7]; // Number of hands where the player bets the River as PFR when OOP

		if (handsRiverCB == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCheckRaiseCBOOP / handsRiverCB * 100.0;
		}
	}

	// River Check-Fold to CB OOP:
	// This percentage represents the proportion of hands where a player check-folds
	// out of position (OOP) when facing a CB on the River. You can calculate it as
	// follows:
	double riverCheckFoldToCBOOPPer(Players play) {
		int handsRiverCheckFoldToCBOOP = play.checkFoldOperPos[1][7]; // Number of hands where the player check-folds
																		// OOP when facing a CB on the River
		int handsRiverCB = play.bet3OperPos[1][7]; // Number of hands where the player bets the River as PFR when OOP

		if (handsRiverCB == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCheckFoldToCBOOP / handsRiverCB * 100.0;
		}
	}

	// River Donk Bet:
	// This percentage represents the proportion of hands where a player bets out of
	// position (OOP) on the River into a player who took the last aggressive action
	// on the Turn. You can calculate it as follows:
	double riverDonkBetPer(Players play) {
		int handsRiverDonkBet = play.bet3OperPos[1][10]; // Number of hands where the player bets OOP on the River into
															// a player who took the last aggressive action on the Turn
		int handsRiverBetOpportunityOOP = play.betOperPos[1][9]; // Number of hands where the player has the opportunity
																	// to bet OOP on the River

		if (handsRiverBetOpportunityOOP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverDonkBet / handsRiverBetOpportunityOOP * 100.0;
		}
	}

	// River Bet vs Missed CB:
	// This percentage represents the proportion of hands where a player bets on the
	// River after the opponent missed a River continuation bet. You can calculate
	// it as follows:
	double riverBetVsMissedCBPer(Players play) {
		int handsRiverBetVsMissedCB = play.bet3OperPos[0][11]; // Number of hands where the player bets on the River
																// after the opponent missed a River continuation bet
		int handsRiverMissedCBOpp = play.checkBetOperPos[0][11]; // Number of hands where the opponent missed a River
																	// continuation bet

		if (handsRiverMissedCBOpp == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverBetVsMissedCB / handsRiverMissedCBOpp * 100.0;
		}
	}

	// WTSD% When Saw Turn:
	// This percentage represents the proportion of hands where a player sees a
	// showdown when they saw the Turn. You can calculate it as follows:
	double WTSDWhenSawTurnPer(Players play) {
		int handsWTSDWhenSawTurn = play.wtsdX[0]; // Number of hands where the player sees a showdown when they saw the
													// Turn
		int handsSawTurn = play.sawTurn[0]; // Number of hands where the player saw the Turn

		if (handsSawTurn == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsWTSDWhenSawTurn / handsSawTurn * 100.0;
		}
	}

	// This percentage represents the proportion of hands where a player raises
	// (4-bets) after trying to steal on SB and having been 3-bet. You can calculate
	// it as follows:
	double fourBetFourBetVsRestealPer(Players play) {
		int handsAttemptedStealSB = play.bet1Pos[0][0]; // Number of hands where the player attempted to steal from SB
		int hands3BetAfterStealAttempt = play.bet3Pos[0][0]; // Number of hands where the player was 3-bet after
																// attempting to steal
		int handsFourBetSBVsResteal = play.bet4Pos[0][0]; // Number of hands where the player 4-bet from SB after being
															// 3-bet

		if (handsAttemptedStealSB == 0 || hands3BetAfterStealAttempt == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFourBetSBVsResteal / hands3BetAfterStealAttempt * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player calls preflop when in the BTN (Button) facing an unopened pot. You can calculate it as follows:
	double BTNOpenLimpPer(Players play) {
		int handsOpenUnopenedPotBTN = play.flopCount; // Number of hands where the player is in BTN and the pot is
														// unopened preflop
		int handsOpenLimpBTN = play.limpPos[0][0]; // Number of hands where the player calls preflop in BTN facing an
													// unopened pot

		if (handsOpenUnopenedPotBTN == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsOpenLimpBTN / handsOpenUnopenedPotBTN * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player calls preflop when in SB (Small Blind) facing an unopened pot. You can calculate it as follows:
	double fourBetOpenLimpPer(Players play) {
		int handsOpenUnopenedPotSB = play.flopCount; // Number of hands where the player is in SB and the pot is
														// unopened preflop
		int handsOpenLimpSB = play.limpPos[1][0]; // Number of hands where the player calls preflop in SB facing an
													// unopened pot

		if (handsOpenUnopenedPotSB == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsOpenLimpSB / handsOpenUnopenedPotSB * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player raises in BB (Big Blind) when facing a limp from SB (Small Blind) in an unopened pot. You can calculate it as follows:
	double bB_Raise_vs_SB_Limp_UOPPer(Players play) {
		int handsOpenUnopenedPotSB = play.flopCount; // Number of hands where the player is in SB and the pot is
														// unopened preflop
		int handsRaiseBBVsSBLimpUOP = play.bet1OperPos[1][0]; // Number of hands where the player raises in BB facing SB
																// limp in an unopened pot

		if (handsOpenUnopenedPotSB == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRaiseBBVsSBLimpUOP / handsOpenUnopenedPotSB * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player bets the Flop as the preflop raiser (PFR) when last to act on the flop. You can calculate it as follows:
	double flopCBetIPPer(Players play) {
		int handsFlopCBetIP = play.cBetPos[0][0]; // Number of hands where the player bets the Flop as PFR when last to
													// act on the flop
		int handsPFR = play.raiseCount1; // Number of hands where the player is the preflop raiser (PFR)

		if (handsPFR == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopCBetIP / handsPFR * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player bets the Flop as the preflop raiser (PFR) when NOT last to act on the flop. You can calculate it as follows:
	double flopCBetOOPPer(Players play) {
		int handsFlopCBetOOP = play.cBetPos[0][0]; // Number of hands where the player bets the Flop as PFR when NOT
													// last to act on the flop
		int handsPFR = play.raiseCount1; // Number of hands where the player is the preflop raiser (PFR)

		if (handsPFR == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopCBetOOP / handsPFR * 100.0;
		}
	}

//  This percentage represents the proportion of postflop streets when a player makes an aggressive move. You can calculate it as follows:
	double postflopAggressionPer(Players play) {
		int aggressiveStreets = play.barrelPos[0][0] + play.barrelPos[1][0] + play.barrelPos[2][0]; // Aggressive
																									// streets on flop,
																									// turn, and river
		int totalStreets = play.flopCount + play.turnCount + play.riverCount; // Total postflop streets

		if (totalStreets == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) aggressiveStreets / totalStreets * 100.0;
		}
	}

//  This percentage represents the proportion of hands a player sees the Flop. You can calculate it as follows:
	double seenFlopOverallPer(Players play) {
		int handsSeenFlop = play.flopCount; // Number of hands where the player sees the Flop
		int totalHands = play.handsPlayed; // Total hands played

		if (totalHands == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsSeenFlop / totalHands * 100.0;
		}
	}

//  This percentage represents the proportion of flops where the player makes an aggressive play. You can calculate it as follows:
	double flopAggressionPer(Players play) {
		int aggressiveFlops = play.barrelPos[0][0]; // Aggressive flops
		int totalFlops = play.flopCount; // Total flops seen

		if (totalFlops == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) aggressiveFlops / totalFlops * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player bets the Flop as the preflop raiser (PFR). You can calculate it as follows:
	double flopCBetPer(Players play) {
		int handsFlopCBet = play.cBetPos[0][0]; // Number of hands where the player bets the Flop as PFR
		int handsPFR = play.raiseCount1; // Number of hands where the player is the preflop raiser (PFR)

		if (handsPFR == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFlopCBet / handsPFR * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player folds when facing a CBet on the Flop. You can calculate it as follows:
	double flopFoldToCBetPer(Players play) {
		int handsFoldToFlopCBet = play.foldPos[0][0]; // Number of hands where the player folds when facing a CBet on
														// the Flop
		int handsFlopCBetFaced = play.cBetOperPos[0][0]; // Number of hands where the player faces a CBet on the Flop

		if (handsFlopCBetFaced == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsFoldToFlopCBet / handsFlopCBetFaced * 100.0;
		}
	}

//  This percentage represents the proportion of turns where the player makes an aggressive play. You can calculate it as follows:
	double turnAggressionPer(Players play) {
		int aggressiveTurns = play.barrelPos[1][0]; // Aggressive turns
		int totalTurns = play.turnCount; // Total turns seen

		if (totalTurns == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) aggressiveTurns / totalTurns * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player bets the River as the preflop raiser (PFR) after CBetting the Flop and Turn. You can calculate it as follows:
	double riverCBetPer(Players play) {
		int handsRiverCBet = play.cBetPos[2][0]; // Number of hands where the player bets the River as PFR after
													// CBetting the Flop and Turn
		int handsCBetFlopAndTurn = play.barrelPos[0][0] + play.barrelPos[1][0]; // Number of hands where the player
																				// CBets Flop and Turn

		if (handsCBetFlopAndTurn == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCBet / handsCBetFlopAndTurn * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player bets the River as PFR after CBetting the Flop and Turn when last to act on the flop. You can calculate it as follows:
	double riverCBetIPPer(Players play) {
		int handsRiverCBetIP = play.cBetPos[2][0]; // Number of hands where the player bets the River as PFR after
													// CBetting the Flop and Turn
		int handsCBetFlopAndTurnIP = play.barrelPos[0][0] + play.barrelPos[1][0]; // Number of hands where the player
																					// CBets Flop and Turn when last to
																					// act on flop

		if (handsCBetFlopAndTurnIP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCBetIP / handsCBetFlopAndTurnIP * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player bets the River as PFR after CBetting the Flop and Turn when NOT last to act on the flop. You can calculate it as follows:
	double riverCBetOOPPer(Players play) {
		int handsRiverCBetOOP = play.cBetPos[2][0]; // Number of hands where the player bets the River as PFR after
													// CBetting the Flop and Turn
		int handsCBetFlopAndTurnOOP = play.barrelPos[0][0] + play.barrelPos[1][0]; // Number of hands where the player
																					// CBets Flop and Turn when NOT last
																					// to act on flop

		if (handsCBetFlopAndTurnOOP == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCBetOOP / handsCBetFlopAndTurnOOP * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player bets the River as PFR after CBetting the Flop and Turn and calls a raise. You can calculate it as follows:
	double riverCBetCallPer(Players play) {
		int handsRiverCBetCall = play.callBet1Pos[2][0]; // Number of hands where the player bets the River as PFR after
															// CBetting the Flop and Turn and calls a raise
		int handsRiverCBet = play.cBetPos[2][0]; // Number of hands where the player bets the River as PFR after
													// CBetting the Flop and Turn

		if (handsRiverCBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCBetCall / handsRiverCBet * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player bets the River as PFR after CBetting the Flop and Turn and reraises a raise. You can calculate it as follows:
	double riverCBetReRaisePer(Players play) {
		int handsRiverCBetReraise = play.bet2Pos[2][0]; // Number of hands where the player bets the River as PFR after
														// CBetting the Flop and Turn and reraises a raise
		int handsRiverCBet = play.cBetPos[2][0]; // Number of hands where the player bets the River as PFR after
													// CBetting the Flop and Turn

		if (handsRiverCBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCBetReraise / handsRiverCBet * 100.0;
		}
	}

//  This percentage represents the proportion of hands where a player bets the River as PFR after CBetting the Flop and Turn and folds to a raise. You can calculate it as follows:
	double riverCBetFoldPer(Players play) {
		int handsRiverCBetFold = play.foldPos[2][0]; // Number of hands where the player bets the River as PFR after
														// CBetting the Flop and Turn and folds to a raise
		int handsRiverCBet = play.cBetPos[2][0]; // Number of hands where the player bets the River as PFR after
													// CBetting the Flop and Turn

		if (handsRiverCBet == 0) {
			return 0.0; // Avoid division by zero
		} else {
			return (double) handsRiverCBetFold / handsRiverCBet * 100.0;
		}
	}

	/*- *****************************************************************************
	 * 	This specific class calculates data about  ....
	***************************************************************************** */
	private static int numberOfInstancesAdded = 0;

	/*-************************************************************************************
	 * Calculate percentages for data from 
	 * Calculations use opportunity counts
	 * 	int[][] checkOperPos = new int[NUM_STREETS][NUM_POS];
	 *************************************************************************************/
	private void calculatePercentages(Players play) {
// TODO this.minBetPosPer = percentPos(this.minBetPos,
// this.minBetOperPos);
		this.limpPerPos = percentPos(play.limpPos, play.limpOperPos);
//		this.checkPerPos = percentPos(play.checkPos, play.checkOperPos);
		this.foldPerPos = percentPos(play.foldPos, play.foldOperPos);
		this.callBet1PerPos = percentPos(play.callBet1Pos, play.callBet1OperPos);
		this.bet1PerPos = percentPos(play.bet1Pos, play.bet1OperPos);
		this.callBet2PerPos = percentPos(play.callBet2Pos, play.callBet2OperPos);
		this.bet2PerPos = percentPos(play.bet2Pos, play.bet2OperPos);
		this.bet3PerPos = percentPos(play.bet3Pos, play.bet3OperPos);
		this.callBet3PerPos = percentPos(play.callBet3Pos, play.callBet3OperPos);
		this.bet4PerPos = percentPos(play.bet4Pos, play.bet4OperPos);
		this.callBet4PerPos = percentPos(play.callBet4Pos, play.callBet4OperPos);
//		this.allinPerPos = percentPos(play.allinPos, play.allinOperPos);
//		this.callAllinPerPos = percentPos(play.callAllinPos, play.callAllinOperPos);

// TODO this.minBetPerRp = percentRp(play.minBetRp,
// this.minBetOperPerRp);
		// TODO
		// There is a problem with any percentages that involve Relative Position.
		// Relative position can changes whenever a player folds, so the opportunity and
		// the action are not on the same relative position.
		// this.limpPerRp = percentRp(play.limpRp, play.limpOperRp);
		// this.checkPerRp = percentRp(play.checkRp, play.checkOperRp);
		// this.foldPerRp = percentRp(play.foldRp, play.foldOperRp);
		// this.callBet1PerRp = percentRp(play.callBet1Rp, play.callBet1OperRp);
		// this.bet1PerRp = percentRp(play.bet1Rp, play.bet1OperRp);
		// this.callBet2PerRp = percentRp(play.callBet2Rp, play.callBet2OperRp);
		// this.bet2PerRp = percentRp(play.bet2Rp, play.bet2OperRp);
		// this.bet3PerRp = percentRp(play.bet3Rp, play.bet3OperRp);
		// this.callBet3PerRp = percentRp(play.callBet3Rp, play.callBet3OperRp);
		// this.bet4PerRp = percentRp(play.bet4Rp, play.bet4OperRp);
		// this.callBet4PerRp = percentRp(play.callBet4Rp, play.callBet4OperRp);
		// this.allinPerRp = percentRp(play.allinRp, play.allinOperRp);
		// this.callAllinPerRp = percentRp(play.callAllinRp, play.callAllinOperRp);
	}

	/*- *****************************************************************************
	* Calculate aggression percentage.
	*	Bets and raises are counted as aggressive actions, while calls and folds are   not.
	*	Postflop Aggression Percentage = (Bets + Raises) / (Bets + Raises + Calls + Checks) * 100%
	*
	*	Postflop Aggression Percent = Percentage of postflop streets players make aggressive moves
	*
	*	Aggression Percentage = aggressive action on each street. 
	*	So if I bet the turn and the river but check the flop I would have 
	*	66% Agg Pct because I made 2 out of 3 aggressive actions.
	*	So it's ( (Bets + Raises) / (Bets + Raises + Calls + Checks ) * 100%.
	*	In the example above the action results in a Agg Pct of 66%.
	*	The Agg Factor would be: 2 / 1 = 2
	*	Both results mean the same: the opponent is 2x more likely to bet, than he is to call.
	*	We only use Aggression Factor and Aggression Percentage.
	* Arg0 - The Players object.
	* Arg1 - The street number.
	* Arg2 - The position number.
	* Returns - The aggression percentage.
	 ******************************************************************************/
	private double calculateAggPctPostflop(Players play, int street, int pos) {
		double raiseCount = 0.;
		double callCount = 0.;
		double checkCount = 0.;
		double aggpct = 0.;
		raiseCount += play.bet1Pos[street][pos] + play.bet2Pos[street][pos] + play.bet3Pos[street][pos]
				+ play.bet4Pos[street][pos];
		callCount += play.callBet1Pos[street][pos] + play.callBet2Pos[street][pos] + play.callBet3Pos[street][pos]
				+ play.callBet4Pos[street][pos];
		checkCount += play.checkPos[street][pos];

		if (callCount + checkCount + raiseCount == 0) {
			// ERROR calculateAggPctPostflop() raise + call + check = 0 raise 0 call 0 check
			// 0 hands 265
			// TODO Logger.logError(new StringBuilder().append("//ERROR
			// calculateAggPctPostflop() raise + call + check = 0 ")
			// .append(" \traise ").append(Format.format(raiseCount)).append("\t call ")
			// .append(Format.format(callCount)).append("\t check
			// ").append(Format.format(checkCount))
			// .append(" \thands ").append(play.handsPlayed).toString());
			return 0.;
		}

		aggPct = 100. * (raiseCount) / (raiseCount + callCount + checkCount);
		if (aggPct < 0. || aggPct > 100.) {
			Logger.logError(new StringBuilder().append("//ERROR  calculatePostflopAggPct Invalid percentage result ")
					.append(Format.format(aggPct)).append(" ").append(raiseCount).append(" ").append(callCount)
					.append(" ").append(checkCount).toString());
			Logger.logError(new StringBuilder().append("//aggPct ").append(Format.format(aggPct)).append(" ")
					.append(Format.format(raiseCount)).append(" ").append(Format.format(callCount)).append(" ")
					.append(Format.format(checkCount)).toString());
			Crash.log("$$$");
		}
		return aggPct;
	}

	/*-*****************************************************************************
	 * Calculate postflop Aggression Percentage for all postflop streets
	***************************************************************************** */
	private double calculateAggPctPostflopAllPos(Players play) {
		double sum = 0;
		for (int i = 1; i < STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				sum += calculateAggPctPostflop(play, i, j);
			}
		}
		return sum;
	}

	/*-*****************************************************************************
	 * Calculate Aggression Percentage for all streets. and all positions
	***************************************************************************** */
	private double[][] calculateAggPctEachPos(Players play) {
		double[][] aggPctPos = new double[STREETS][NUM_POS];
		for (int i = 0; i < STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				aggPctPos[i][j] = calculateAggPctPostflop(play, i, j);
			}
		}
		return aggPctPos;
	}

	/*- *****************************************************************************
	 * Calculate Aggression factor. AF
	* 
	*  Postflop Aggression Factor = Ratio of Bets + Raises vs calls on postflop streets	
	*	Aggression Factor = (Bets + Raises) / Calls
	*	(checking/folding doesn't affect it)
	*
	*	This results in a value between 0 and INF (infinite)
	*	If a player never Calls, but always bets/raises, his AGG will be infinite.
	*	The statistics are used in postflop decision making. Since it will tell you the 
	* 	likelihood the opponent will bet.
	*	The formulas:
	*	Aggression Factor: (% Raise + % Bet) / % Call
	*	Aggression Factor: (Bets + Raise) / Calls
	*
	*	Hold'em Manager, which expresses the ratio of a player's aggressive  actions
	*  (bets or raises) to their calls.
	*	For example, a player with an AF of 3 has bet/raised three times as often as
	*	they've called.
	 ******************************************************************************/
	void calculatePostflopAF(Players play) {
		int raiseCount = 0;
		int betCount = 0;
		int callCount = 0;
		for (int i = 1; i < STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				raiseCount += play.bet1Pos[i][j] + play.bet2Pos[i][j] + play.bet3Pos[i][j] + play.bet4Pos[i][j];
				callCount += play.callBet1Pos[i][j] + play.callBet2Pos[i][j] + play.callBet3Pos[i][j]
						+ play.callBet4Pos[i][j];
				betCount = play.bet1Pos[i][j];
			}
		}
		if (callCount == 0) {
			this.af = 9999999;
			return;
		}
		this.af = ((double) betCount + (double) raiseCount) / callCount;
	}

	/*- *****************************************************************************
	 * Calculate PFR - Preflop Raise
	 * This statistic creates even more context for your opponent’s preflop strategy.
	 * When used in conjunction with VPIP, it will be enough to form a player profile.
	*
	 * A player that has at least a basic understanding of preflop strategy will have 
	 * between 15-25% preflop raise, with 19% being close to the norm.
	 *
	 * Similarly to VPIP, you will need around 300 hands on this player to be confident 
	 * enough in the number you are seeing.
	  ***************************************************************************** */
	void calculatePFR(Players play) {
		int operCount = 0;
		int raiseCount = 0;

		for (int j = 0; j < NUM_POS; ++j) {
			raiseCount += play.bet2Pos[0][j] + play.bet3Pos[0][j] + play.bet4Pos[0][j];
			operCount += play.callBet2OperPos[0][j] + play.callBet3OperPos[0][j] + play.callBet4OperPos[0][j];
			raiseCount += play.bet1Pos[0][j];
			operCount += play.callBet1OperPos[0][j];
		}
		if (operCount == 0) {
			this.pfr = 0.;
		} else {
			this.pfr = ((double) raiseCount / (double) operCount) * 100.;
		}
	}

	/*-  *****************************************************************************
	 * VPIP  Preflop all positions and all orbits
	 * Used only locally to catagorize player types FISH, NIT, ...	 * 
	 * 
	 * 		vpip = 100.0 * (moneyInRaise + moneyInCall) / from.streetCounts[street];
	 *		int[][] callBet2AllOrbitsPos = new int[NUM_STREETS][NUM_POS];
	 * 		bet2AllOrbitsPos = new int[NUM_STREETS][NUM_POS];
	 * 
	 * Uses opportunity counts not streets played - TODO verify this is correct way
	 * 
	 * 		int[][][] bet2Pos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	 * 		int[][][] bet2OperPos = new int[NUM_STREETS][NUM_POS][NUM_ORBITS];
	 ***************************************************************************** */
	void calculateVPIPPreflop(Players play) {
		double moneyInRaise = 0;
		double moneyInCall = 0;
		double operCount = 0;
		vpip = 0.;
		for (int i = 0; i < NUM_POS; ++i) {
			moneyInRaise += play.bet2Pos[0][i] + play.bet3Pos[0][i] + play.bet4Pos[0][i] + play.allinPos[0][i];
			moneyInCall += play.callBet2Pos[0][i] + play.callBet3Pos[0][i] + play.callBet4Pos[0][i]
					+ play.callAllinPos[0][i];
			operCount += play.bet2OperPos[0][i] + play.bet3OperPos[0][i] + play.bet4OperPos[0][i]
					+ play.callBet2OperPos[0][i] + play.callBet3OperPos[0][i] + play.callBet4OperPos[0][i];
		}

		if (play.handsPlayed != 0) {
			this.vpip = 100.0 * (moneyInCall + moneyInRaise) / play.handsPlayed;
		}

		if (this.vpip >= 0 && this.vpip <= 100) {
			return;
		}
	}

	/*-  *****************************************************************************
	 * Calculate percentage with error checking
	 ***************************************************************************** */
	private static double calculatePercentage(double a, double b) {
		if (b <= 0.) {
			return 0.;
		}
		final double $ = 100.0 * (a / b);
		final boolean condition = ($ > 100 || $ < 0);
		if (condition) {
			Logger.logError(new StringBuilder().append("//ERROR calculatePercentage ").append($).append(" a ").append(a)
					.append(" b ").append(b).toString());
		}
		return $;
	}

	/*-************************************************************************************
	 * Copy data frpm Players class to this class
	 * Arg0 - Action array
	 * arg1 - Opportunity array
	 * Returns percentage array
	 *************************************************************************************/
	private double total(int action, int oper) {
		double p = 0;

		if (oper != 0) {
			p = (double) action / (double) oper;
		}
		if (p > 1 || p < 0) {
			Logger.logError(new StringBuilder().append("//ERROR total()  invalide percentage ").append(p)
					.append(" action ").append(action).append(" oper ").append(oper).toString());
			Crash.log("$$$");
		}
		return p;
	}

	/*-************************************************************************************
	 * Calculate percentage
	 * Arg0 - Action array
	 * Arg1 - Opportunity array
	 *************************************************************************************/
	private double[][] percentPos(int[][] action, int[][] oper) {
		if (action.length != NUM_STREETS || oper.length != NUM_STREETS || action[0].length != NUM_POS
				|| oper[0].length != NUM_POS) {
			Logger.logError(new StringBuilder().append("//totalOrbitsPos invalid array length ").append(action.length)
					.append(" ").append(action[0].length).append(" ").append(oper.length).append(" ")
					.append(oper[0].length).toString());
			Crash.log("$$$");
		}
		final double[][] p = new double[NUM_STREETS][NUM_POS];
		for (int i = 0; i < NUM_STREETS; ++i) {
			for (int j = 0; j < NUM_POS; ++j) {
				if (oper[i][j] == 0) {
					p[i][j] = 0.;
				} else {
					p[i][j] = ((double) action[i][j] / (double) oper[i][j]) * 100.;
				}

				if (p[i][j] > 100. || p[i][j] < 0. || action[i][j] > oper[i][j]) {
					// ERROR percentPos oper 8 action 28 percent 350.0 street 0 pos 2
					Logger.logError(new StringBuilder().append("//ERROR percentPos ").append(" oper ")
							.append(oper[i][j]).append(" action ").append(action[i][j]).append(" percent ")
							.append(p[i][j]).append(" street ").append(i).append(" pos ").append(j).toString());
					Crash.log("$$$");
				}
			}
		}
		return p;
	}

	/*- ***********************************************************************************************************************
	 * Calculates Preflop VPIP.
	 * Returns a single double.
	 * The VPIP is for the entire street, all positions combined.
	 * There is a version of this methos that returns an atrray indexed 
	 * by position.
	 * Another method returns VPIP for postflop.
	 * 
	*	The more current formula for VPIP is actually Number of Times
	*	Player Put Money In Pot / (Number of Hands – Number of walks).
	*	Number of walks applies only to preflop Big Blind, all other streets 
	*	walk is not considered ( always 0 ).
	*	And a walk is simply when everyone folds to the BB and he wins the pot.
	*	We subtract walks because in a walk situation the big blind has no
	*
	*
	* This is a must-have stat on your HUD.
	*
	* VPIP shows you how often your opponent has voluntarily put money into the pot preflop,
	* either by raising or calling. This is fundamental information for player profiling, 
	* especially when correlated with Preflop Raise (PFR).
	*
	* A player that has at least a basic preflop understanding will generally VPIP 
	* around 20-30% of the time in a 6-handed game, with 25% being very close to the norm. 
	* If a player is playing significantly less hands, they are probably a nit. And if they are playing 
	* a lot more hands, they are likely a recreational player.
	*
	* You will need around 300 hands on this player to be confident enough in what the stat shows you. 
	* That said, players on either extreme can oftentimes be identified sooner. 
	* For example, if a player has a 70% VPIP after 50 hands, it is very likely they are a loose recreational player.
	*
	*
	 * Arg0 - Player instance
	 * Returns double VPIP
	 * 
	 * 
	 * 
	 * from HM3
	 * ZZZZ
	 * 		VPIP 					26.6
	 * 		PFR						17.3
	 * 		3Bet						8.3
	 * 		postflopAgg		26.9
	 * 		WTSD					29.2
	 * 		WWSF					45.7
	 * 		Won SD				50.4
	 * 		flop c-bet			61.7
	 * 		turn c-bet			52.0
	 * 		river c-bet			53.8
	 * 		Fold vs CBet		43.3
	 * 		Fold vs T CBet	42.9
	 * 		Fold vs R CBet	52.3
	 * 		Raise vs F CBet	10.5
	 * 		Raise vs T CBet	 9.51
	 * 		Raise vs R CBet	12.6
	 * 		Squeeze				 7.15
	 *  	Call 2 raisers		 3.18
	 *  	Raise 2 raisers	 3.50
	 * 		fold to 3bet		46.7
	 * 		Call vs 3Bet			40.9
	 * 		Raise vs 3Bet		12.4
	 * 		foldVs c-ber		43.3
	 * 		Fold to 4Bet		47.1
	 * 		Call vs 4Bet			35.0
	 * 		Raise vs 4Bet		17.9
	 * 
	 * Need to select 1 player and compare his stats
	 * VPIP BB = 27.6 in our calc
	************************************************************************************************************************/

	/*- ******************************************************************************
	 * Calculates Preflop bet 3
	 * Returns a single double.	
	* 3Bet	- Pct of hands player raises (3Bets) preflop when facing single raised pot
	* TODO need  to record this
	*******************************************************************************/
	double calculatePreflop3Bet() {
		final double bet3 = 0;
		Logger.logError("1Bet " + bet3);
		return bet3;
	}

	// VPIP Voluntarily Put In Pot
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*- ***********************************************************************************	
	 * Calculate VPIP for all preflop positions on one street
	 * Arg0 - Player instance
	 * Arg1 - street 0 - 3
	 * Returns double VPIP
	 ************************************************************************************/
	private double calculateVPIPAllPosOneStreet(Players play, int street) {
		double sum = 0.;
		for (int j = 0; j < NUM_POS; j++) {
			sum += calculatePreflopVPIPPos(play, street);
		}
		return sum;
	}

	/*- ******************************************************************************
	 * VPIP by street all positions as an array 
	 * Arg0 - Player instance
	 * Arg1 - street 0 - 3
	 * Arg2 - position 0 - 5
	 * Returns vpip for specified street, all positions]
	 * double[][] vpipStreetPos = new double[NUM_STREETS][NUM_POS] 
	****************************************************************************** */
	private double[][] calculateVPIPStreetEachPos(Players play) {
		double[][] sum = new double[PLAYERS][NUM_POS];
		for (int i = 0; i < NUM_STREETS; i++) {
			for (int j = 0; j < NUM_POS; j++) {
				sum[i][j] = calculateVPIPStreetPos(play, i, j);
			}
		}
		return sum;
	}

	/*- ******************************************************************************
	 * Save as calculatePreflopVPIP() but returns array indexed by position.
	 * Arg0 - Player instance
	 * Arg1 - position SB, BB, UTG, MP, CO, BU
	 * Returns double VPIP
	****************************************************************************** */
	double calculatePreflopVPIPPos(Players play, int pos) {
		return calculateVPIPStreetPos(play, 0, pos);
	}

	double calculateFlopVPIPPos(Players play, int pos) {
		return calculateVPIPStreetPos(play, 1, pos);
	}

	double calculateTurnVPIPPos(Players play, int pos) {
		return calculateVPIPStreetPos(play, 2, pos);
	}

	double calculateRiverVPIPPos(Players play, int pos) {
		return calculateVPIPStreetPos(play, 3, pos);
	}

	/*- ***********************************************************************************
	 * VPIP by street and by position - helper method
	 * Arg0 - Player instance
	 * Arg1 - street 0 - 3
	 * Arg2 - position SB, BB, UTG, MP, CO, BU
	 * Returns double VPIP
	 * 
	 * 		vpip = 100.0 * (moneyInRaise + moneyInCall) / from.streetCounts[street];
	 *		int[][] callBet2Pos = new int[NUM_STREETS][NUM_POS];
	 * 		bet2Pos = new int[NUM_STREETS][NUM_POS];
	 * 
	 * Uses opportunity counts not streets played - TODO verify this is correct way
	 * 
	 * 	int[][] callBet2Pos = new int[NUM_STREETS][NUM_POS];
	 * 	int[][] callBet2OperPos = new int[NUM_STREETS][NUM_POS];
	 ************************************************************************************/
	private double calculateVPIPStreetPos(Players play, int street, int pos) {
		double vpip = 0;
		int moneyInRaise = 0;
		int moneyInCall = 0;
		int operCount = 0;
		moneyInRaise = play.bet2Pos[street][pos] + play.bet3Pos[street][pos] + play.bet4Pos[street][pos]
				+ play.allinPos[street][pos] + play.bet2Pos[street][pos];
		moneyInCall = play.callBet2Pos[street][pos] + play.callBet3Pos[street][pos] + play.callBet4Pos[street][pos]
				+ play.callAllinPos[street][pos];
		operCount += play.bet2OperPos[street][pos] + play.bet3OperPos[street][pos] + play.bet4OperPos[street][pos];

		if (street != 0) {
			moneyInRaise += play.bet1Pos[street][pos];
			moneyInCall += play.callBet1Pos[street][pos];
			operCount += play.bet1OperPos[street][pos];
		}
		if (play.streetCountsX[street] != 0) {
			vpip = 100.0 * (moneyInRaise + moneyInCall) / operCount;
		}
		if (vpip < 0 || vpip > 100) {
			Logger.logError("//Error calculatePreflopVPIPStreetPos() " + vpip);
			Crash.log("$$$");
		}
		return vpip;
	}

	// PFR preflop raise <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*-******************************************************************************
	 * Calculate PFR - Preflop Raise
	* Arg0 - Player instance
	 * Arg1 = position , SB, BB, UTG, MP, CO, BU
	 * Returns double percentage
	 * 
	 * Returns a double, PFR.		 
	 * 		PFR - Preflop Raise  
	 * 		PFR tells you how often a player has entered the pot  by raising. 
	 * 		This includes raising first in, 3-betting, and cold 4/5-betting.
	 * 
	 * This statistic creates even more context for your opponent’s preflop strategy.
	 * When used in conjunction with VPIP, it will be enough to form a player profile.
	*
	 * A player that has at least a basic understanding of preflop strategy will have 
	 * between 15-25% preflop raise, with 19% being close to the norm.
	 *
	 * Similarly to VPIP, you will need around 300 hands on this player to be confident 
	 * enough in the number you are seeing.
	 * 
	 * 	int[][] bet2Pos = new int[NUM_STREETS][NUM_POS];
	 *******************************************************************************/
	private double calculatePFRPos(Players play, int pos) {
		int sum = 0;
		return (double) (play.bet1Pos[0][pos] + play.bet2Pos[0][pos] + play.bet3Pos[0][pos] + play.bet4Pos[0][pos])
				/ play.handsPlayed;
	}

	/*- *******************************************************************************
	 * Calculate PFR  sum for all positions.
	 * Returns a single double  PFR.         
	 ******************************************************************************* */
	private double calculatePFRAllPos(Players play) {
		double sum = 0.;
		for (int i = 0; i < NUM_POS; i++) {
			sum += calculatePFRPos(play, i);
		}
		return sum;
	}

	/*- *******************************************************************************
	 * Calculate PFR  for all preflop positions.
	 ******************************************************************************* */
	private double[] calculatePFREachPos(Players play) {
		double sum[] = new double[NUM_POS];
		for (int i = 0; i < NUM_POS; i++) {
			sum[i] = calculatePFRPos(play, i);
		}
		return sum;
	}

	// af aggression factor <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*-******************************************************************************
	 * Calculate Aggression factor. AF
	*  Postflop Aggression Factor = Ratio of Bets + Raises vs calls on postflop streets	
	*	Aggression Factor = (Bets + Raises) / Calls
	*	(checking/folding doesn't affect it) 
	*	This results in a value between 0 and INF (infinite)
	*	If a player never CALLS, but always bets/raises, his AGG will be infinite.
	*	The stats are used in postflop decision making. Since it will tell you the 
	* 	likelyhood the opponent will bet.
	*	The formulas:
	*	Aggression Factor: (% Raise + % Bet) / % Call
	*	Aggression Factor: (Bets + Raise) / Calls
	*
	*	Hold'em Manager, which expresses the ratio of a player's aggressive  actions
	*  (bets or raises) to their calls.
	*	For example, a player with an AF of 3 has bet/raised three times as often as
	*	they've called.
	* Arg0 - Player instance
	* Arg1 = position SB, BB, UTG, MP, CO, BU
	* Returns double - not a percentage
	 ********************************************************************************/
	private double[][] calculatePostflopAFEachStreet(Players play) {
		double[][] af = new double[NUM_STREETS][NUM_POS];
		for (int street = 1; street < NUM_STREETS; ++street) {
			for (int pos = 0; pos < NUM_POS; ++pos) {
				af[street][pos] = calculateStreetPosAF(play, street, pos);
			}
		}
		return af;
	}

	/*-*******************************************************************************
	 * Calculate aggression factor for all streets and all positions
	 ********************************************************************************/
	private double calculatePostflopAFStreet(Players play) {
		double af = 0.;
		for (int street = 1; street < NUM_STREETS; ++street) {
			for (int pos = 0; pos < NUM_POS; ++pos) {
				af += calculateStreetPosAF(play, street, pos);
			}
		}
		return af;
	}

	/*-******************************************************************************
	* Calculate AggressonFactor. AF
	* Arg0 - Player instance
	 * Arg1 = street , 1 - 3
	 * Arg2 = position , SB, BB, UTG, MP, CO, BU
	 * Returns double percentage
	*
	*	Bets and raises are counted as aggressive actions, while calls and folds are   not.
	*	Postflop Aggression Percentage = (Bets + Raises) / (Bets + Raises + Calls + Checks) * 100%
	*******************************************************************************/
	private double calculateStreetPosAF(Players play, int street, int pos) {
		double af = 0;
		double raiseCount = 0;
		double callCount = 0;

		raiseCount += play.bet1Pos[street][pos] + play.bet2Pos[street][pos] + play.bet3Pos[street][pos]
				+ play.bet4Pos[street][pos];
		callCount += play.callBet1Pos[street][pos] + play.callBet2Pos[street][pos] + play.callBet3Pos[street][pos]
				+ play.callBet4Pos[street][pos];
		if (callCount == 0.) {
			return 9999999;
		}
		af = ((raiseCount + callCount) / callCount) / 100.;

		if (af < 0 || af > 1) {
			Logger.logError(new StringBuilder().append("//ERROR  calculatePostflopAF Invalid percentage result ")
					.append(af).append(" ").append(raiseCount).append(" ").append(callCount).toString());
			Crash.log("$$$");
		}
		return af;
	}

	// aggPct aggression percent<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*-******************************************************************************
		* Calculate aggression percentage.
		* Arg0 - Player instance
		 * Arg1 = position , SB, BB, UTG, MP, CO, BU
		 * Returns double percentage
		*
		*	Bets and raises are counted as aggressive actions, while calls and folds are   not.
		*	Postflop Aggression Percentage = (Bets + Raises) / (Bets + Raises + Calls + Checks) * 100%
		*
		*	Postflop Aggression Percent = Percentage of postflop streets players make aggressive moves
		*
		*	Aggression Percentage = aggressive action on each street. 
		*	So if I bet the turn and the river but check the flop I would have 
		*	66% Agg Pct because I made 2 out of 3 aggressive actions.
		*	So it's ( (Bets + Raises) / (Bets + Raises + Calls + Checks ) * 100%.
		*	In the example above the action results in a Agg Pct of 66%.
		*	The Agg Factor would be: 2 / 1 = 2
		*	Both results mean the same: the opponent is 2x more likely to bet, than he is to c
		*	We only use Aggression Factor and Aggression Percentage.
		*******************************************************************************/
	private double calculatePostflopAggPctPos(Players play, int pos) {
		double aggPct = 0;
		double raiseCount = 0;
		double callCount = 0;
		double checkCount = 0;

		for (int street = 1; street < 4; ++street) {
			raiseCount += play.bet1Pos[street][pos] + play.bet2Pos[street][pos] + play.bet3Pos[street][pos]
					+ play.bet4Pos[street][pos];
			callCount += play.callBet1Pos[street][pos] + play.callBet2Pos[street][pos] + play.callBet3Pos[street][pos]
					+ play.callBet4Pos[street][pos];
			checkCount += play.checkPos[street][pos];
		}

		aggPct = ((raiseCount + callCount) / (callCount + checkCount)) * 100.;

		if (aggPct < 0 || aggPct > 1) {
			Logger.logError(new StringBuilder().append("//ERROR  calculatePostflopAggPct Invalid percentage result ")
					.append(aggPct).append(" ").append(raiseCount).append(" ").append(callCount).append(" ")
					.append(checkCount).toString());
			Crash.log("$$$");
		}
		return aggPct;
	}

	/*- ******************************************************************************
	 * Same as  calculatePostflopAggPctPos but just for Flop 
	****************************************************************************** */
	private double calculatePostflopAggPctFlopPos(Players play, int pos) {
		double aggPctFlop = 0;
		double raiseCount = 0;
		double callCount = 0;
		double checkCount = 0;

		raiseCount += play.bet1Pos[1][pos] + play.bet2Pos[1][pos] + play.bet3Pos[1][pos] + play.bet4Pos[1][pos];
		callCount += play.callBet1Pos[1][pos] + play.callBet2Pos[1][pos] + play.callBet3Pos[1][pos]
				+ play.callBet4Pos[1][pos];
		checkCount += play.checkPos[1][pos];

		aggPctFlop = ((raiseCount + callCount) / (callCount + checkCount)) * 100.;

		if (aggPctFlop < 0 || aggPctFlop > 1) {
			Logger.logError(new StringBuilder().append("//ERROR  calculatePostflopAggPct Invalid percentage result ")
					.append(aggPctFlop).append(" ").append(raiseCount).append(" ").append(callCount).append(" ")
					.append(checkCount).toString());
			Crash.log("$$$");
		}
		return aggPctFlop;
	}

	/*-******************************************************************************
	 * Calculate aggresson percentage for all positions
	* Arg0 - Player instance
	 *******************************************************************************/
	private double calculatePostflopAggPct(Players play) {
		double sum = 0;
		for (int i = 0; i < Players.NUM_POS; ++i) {
			sum += calculatePostflopAggPctPos(play, i);
		}
		return sum / Players.NUM_POS;
	}

	/*-******************************************************************************
	* Calculate aggression percentage.
	* Arg0 - Player instance
	 * Arg1 = street , 1 - 3
	 * Arg2 = position , SB, BB, UTG, MP, CO, BU
	 * Returns double percentage
	*
	*	Bets and raises are counted as aggressive actions, while calls and folds are   not.
	*	Postflop Aggression Percentage = (Bets + Raises) / (Bets + Raises + Calls + Checks) * 100%
	*******************************************************************************/
	double calculateStreetAggPctPos(Players play, int street, int pos) {
		double aggPct = 0;
		double raiseCount = 0;
		double callCount = 0;
		double checkCount = 0;

		raiseCount += play.bet1Pos[street][pos] + play.bet2Pos[street][pos] + play.bet3Pos[street][pos]
				+ play.bet4Pos[street][pos];
		callCount += play.callBet1Pos[street][pos] + play.callBet2Pos[street][pos] + play.callBet3Pos[street][pos]
				+ play.callBet4Pos[street][pos];
		checkCount += play.checkPos[street][pos];

		aggPct = ((raiseCount + callCount) / (callCount + checkCount)) * 100.;

		if (aggPct < 0 || aggPct > 1) {
			Logger.logError(new StringBuilder().append("//ERROR  calculateStreetAggPct Invalid percentage result ")
					.append(aggPct).append(" ").append(raiseCount).append(" ").append(callCount).append(" ")
					.append(checkCount).toString());
			Crash.log("$$$");
		}
		return aggPct;
	}

	/*-******************************************************************************
	* Calculate  aggressionFrequency all streets
	*******************************************************************************/
	private void calculateAggPerAllStreets(Players play) {
		for (int i = 0; i < STREETS; i++) {
			// TODO aggressionFrequency[i] = calculatePostflopAggPctPos(play, i);
		}
	}

	/*-******************************************************************************
	* Calculate aggressionFactor all streets
	*******************************************************************************/
	private void calculateAggFactorAllStreets(Players play) {
		for (int i = 0; i < STREETS; i++) {
			// TODO aggressionFactor[i] = calculatePostflopAggPctFlopPos(play, i);
		}
	}

	// Went To Showdown <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*-******************************************************************************
	 * Calculate WTSD 
	 * Arg0 - Player instance
	 * Arg1 = street , 1 - 3
	 * Arg2 = position , SB, BB, UTG, MP, CO, BU
	 * Returns double percentage
	 * 		
	 * WTSD: Went To Showdown
	 * 
	* WTSD: Went To Showdown
	*	This is a very important postflop statistic that tells you how frequently a player 
	*	reaches showdown after seeing a flop.
	*	It is useful for identifying how much of a calling station your opponent is.
	*	For example, if a player saw the flop 10 times and went to showdown 
	*	4 times in a session, that player’s WTSD is 40% for that session.
	*
	*	This stat is to be used in conjunction with Won Money at Showdown 
	*	(W$SD or WSD) and Won When Saw Flop (WWSF).
	*	A good WTSD frequency is somewhere around 27-32%, 
	*	with 30% being a good place to aim for. Too low and you are probably over-folding postflop; 
	*	too high and you are probably calling too often.
	*
	*	Since the vast majority of pots do not go past preflop (only 17% of hands see the flop), 
	*	this statistic requires a much bigger sample for an accurate read to be made.
	*	— aim to have around 8,000 hands on a player 
	*	before making notable adjustments based on WTSD.
	* Arg0 - Player instance
	* Returns double  
	****************************************************************************** */
	private double calculateWTSDSawFlop(Players play) {
		double num = 0.;
		if (play.wtsdFlopCount > 0) {
			num = (double) play.wtsdFlopCount / (double) play.showdownCount;
		}
		return num;
	}

	private double calculateWTSDSawTurn(Players play) {
		double num = 0.;
		if (play.wtsdTurnCount > 0) {
			num = (double) play.wtsdTurnCount / (double) play.showdownCount;
		}
		return num;
	}

	private double calculateWTSDSawRiver(Players play) {
		double num = 0.;
		if (play.wtsdRiverCount > 0) {
			num = (double) play.wtsdRiverCount / (double) play.showdownCount;
		}
		return num;
	}

	// Won Money At Showdown <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*-******************************************************************************
	* Calculate W$SD/WSD: Won Money At Showdown
	 * Arg0 - Player instance
	 * Returns double percentage
	 * 
	 * WSD: Won Money at Showdown
	*	Also abbreviated as W$SD, this stat tells you how often your opponent has 
	*	won when they reached showdown. As alluded to above, this stat isn’t too helpful
	*	on his own, but it can be helpful when used in conjunction with WTSD.
	*
	*	A good W$SD is somewhere between 49% and 54%. A correct frequency is dependent 
	*	on the other two statistics mentioned. For example, a player who has a 
	*	low Went to Showdown (WTSD) frequency will usually have a relatively 
	*	high WSD and vice versa. In other words, if you rarely reach showdown,
	*	it’s probably because you’re a tight player who usually has a strong hand 
	*	when you go the distance in a hand.
	*
	*	In general, if your WSD is too low, then it means you are probably calling too many 
	*	bad hands and/or bluffing too much earlier in the hand. If it’s too high, 
	*	it means that you are probably either not bluff-catching enough and/or not bluffing enough.
	*
	*	The sample required for a decently accurate read is the same as WTSD, above 8,000 hands.
	*******************************************************************************/
	double calculateWYSD_WSD(Players play) {
		double wysd = 0;
		wysd = (double) play.showdownWonCount / (double) play.handsPlayed;
		return wysd;
	}

	// TODO
	double calculateWYSD_WSDPos(int ppos) {
		double wysd = 0;
		wysd = -1.;
		return wysd;
	}

	/*-******************************************************************************
	* Calculate W$SD/WSD: Won Money At Showdown
	* Arg0 - Player instance
	* Returns double percentage
	*
	*	Tying up the 3 statistics that work together…
	*
	*	WWSF refers to how often your opponent has won the pot after seeing the flop.
	*
	*	A decent WWSF frequency is anywhere between 45% and 53%, 
	*	with a good average being around 48%. Too low? 
	*	That means that your opponent is not bluffing enough and/or giving up too much. 
	*	Too high? That means that you are bluffing and/or bluff-catching too much.
	*
	*	The sample required for a reasonably accurate read is the same as the one for the
	*	previous two: 8,000 or more hands.
	*
	*	How WTSD, WSD, and WWSF Work Together
	*	Each one of these stats provide important context for the others, 
	*	which will allow you to draw major conclusions about your opponents.
	*
	*	Let’s consider a few example players.
	*
	*	(Remember that: WTSD = Went To Showdown, WSD = Won Money At Showdown, 
	*	and WWSF = Won When Saw Flop)
	*
	*	Player A: WTSD: 32 / WSD: 51 / WWSF: 46
	*
	*	This player is more or less a passive calling station. 
	*	He has a high WTSD, but he’s apparently calling pretty light to since 
	*	he’s only winning 51% of the time at showdown. He’s also not very aggressive, hence the low WWSF.
	*
	*	The degree to which Player A is a calling station will be clearer by looking at his VPIP. 
	*	A high VPIP, like 40%, means this player plays a lot of hands and doesn’t 
	*	do much folding postflop. 
	*	Your adjustment against such a player should be to go for more thin value bets and fewer bluffs.
	*
	*	Player B: WTSD: 26/ WSD: 56/ WWSF: 44
	*
	*	This type of player rarely goes to showdown, but it’s clearly not due to aggression
	*	because he also has a low WWSF. Player B is likely a quite tight player 
	*	who folds quite often postflop — a conclusion we can draw from his high WSD.
	*
	*	Player C: WTSD: 30/ WSD: 52/ WWSF: 49
	*
	*	Assuming this player has decent preflop stats (~25% VPIP), 
	*	Player C is quite the terror at the table. 
	*	She doesn’t seem to fold too much or too little based on her WTSD. 
	*	She’s also clearly aggressive and actively trying to steal pots, indicated by the 
	*	high WWSF. 
	*	Expect to face a lot of tough decisions against a player like this.
	 *******************************************************************************/
	double calculateWWSF() {
		final double wwsf = -1.;
		// TODO need new data in Players for won after preflop
		return wwsf;
	}

	double calculateWWSFPos(int pos) {
		final double wwsf = -1.;
		// TODO need new data in Players for won after preflop
		return wwsf;
	}

	// 3 bet frequency <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*-******************************************************************************
	 * Calculate 3 bet frequency for one street and one position.
	*
	*	This is an important stat to have in your arsenal as it shows how often your 
	*	opponent 3-bets before the flop. 
	*	It will prove useful for building both your preflop opening ranges and your 
	*	defending range against your opponent’s 3-bet.
	*	A good overall 3-betting frequency will be something around 6-10%, 
	*	with 8% being close to the average for good players.
	*
	*	Some adjustments that you can make vs tight or loose 3-bettors are:
	*	When a very aggressive 3-bettor is behind, open-raise a slightly tighter range than usual.
	*	With only tight 3-bettors behind, you can profitably open-raise a slightly wider range.
	*	When you face a 3-bet from an aggressive player, continue more often by 
	*	4-betting and calling with more hands than usual.
	*	For an accurate read on the stat, you will need around 1000 hands on a player.
	* Arg0 - Player instance
	* Arg1 - street, 1 - 3
	* Arg2 - position, SB, BB, UTG, MP, CO, BU
	****************************************************************************** */
	private double calculate3BetFrequencyStreetPos(Players play, int street, int pos) {
		double oper = 0;
		double action = 0;
		action = play.bet3Pos[street][pos];
		oper = play.bet3OperPos[street][pos];
		return action / oper;
	}

	/*-******************************************************************************
	 * Calculate 3 bet frequency average for all positions in one street
	* Arg0 - Player instance
	 * Arg1 = street , 1 - 3
	 * Arg2 = position , SB, BB, UTG, MP, CO, BU
	 * Returns double percentage
	 * 	int[][] bet3Pos = new int[NUM_STREETS][NUM_POS];
	 * 	int[][] bet3OperPos = new int[NUM_STREETS][NUM_POS];
	*******************************************************************************/
	private double calculate3BetFrequencyStreetAllPos(Players play, int street) {
		double sum = 0.;
		for (int pos = 0; pos < NUM_POS; pos++) {
			sum += calculate3BetFrequencyStreetPos(play, street, pos);
		}
		return sum;
	}

	/*-******************************************************************************
	 * Calculate 3 bet frequency average for all streets and all positions
	* Arg0 - Player instance
	 * Arg1 = street , 1 - 3
	 * Arg2 = position , SB, BB, UTG, MP, CO, BU
	 * Returns double percentage
	 * 	int[][] bet3Pos = new int[NUM_STREETS][NUM_POS];
	 * 	int[][] bet3OperPos = new int[NUM_STREETS][NUM_POS];
	*******************************************************************************/
	private double calculate3BetFrequencyStreetAllPos(Players play) {
		double sum = 0.;
		for (int street = 0; street < NUM_STREETS; street++) {
			sum += calculate3BetFrequencyStreetAllPos(play, street);
		}
		return sum;
	}

	/*-******************************************************************************
	 * Calculate 3 bet frequency average for all streets and all positions
	* Arg0 - Player instance
	 * Arg1 = street , 1 - 3
	 * Arg2 = position , SB, BB, UTG, MP, CO, BU
	 * Returns double percentage
	 * 	int[][] bet3Pos = new int[NUM_STREETS][NUM_POS];
	 * 	int[][] bet3OperPos = new int[NUM_STREETS][NUM_POS];
	*******************************************************************************/
	private double calculatePreflop3BetFrequency(Players play) {
		double sum = 0.;
		sum += calculate3BetFrequencyStreetAllPos(play, 0);
		return sum;
	}

	/*-******************************************************************************
	 * Calculate 3 bet frequency average for all streets and all positions
	* Arg0 - Player instance
	 * Arg1 = street , 1 - 3
	 * Arg2 = position , SB, BB, UTG, MP, CO, BU
	 * Returns double percentage
	 * 	int[][] bet3Pos = new int[NUM_STREETS][NUM_POS];
	 * 	int[][] bet3OperPos = new int[NUM_STREETS][NUM_POS];
	*******************************************************************************/
	private double[][] calculate3BetFrequencyEachStreetEachPos(Players play) {
		double[][] sum = new double[NUM_STREETS][NUM_POS];
		for (int street = 0; street < NUM_STREETS; street++) {
			for (int pos = 0; pos < NUM_POS; pos++) {
				sum[street][pos] += calculate3BetFrequencyStreetPos(play, street, pos);
			}
		}
		return sum;
	}

	// winRate Win Rate in Big Bets / 100<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*-******************************************************************************
	 * CalculateWin Rate for one street.
	 * Win Rate	- Players winrate in bb/100
	 * Returns win rate as BB / 100
	 * Total won or lost / handsPlayed / 200 ( bb = 2 )
	* Arg0 - Players instance
	 * Arg1 = street , 1 - 3
	 * Arg2 = position , SB, BB, UTG, MP, CO, BU
	 * returns double winRate
	****************************************************************************** */
	private double calculateWinRateOneStreet(Players play, int street) {
		double winRate = 0.;
		BigDecimal bd = zeroBD;
		final var hp = new BigDecimal((play.handsPlayed / 100.) / 2.).setScale(2, RoundingMode.HALF_EVEN);
		if (hp.compareTo(zeroBD) == 0) {
			this.winRateStreets[street] = 0.;
			return winRate;
		} else if (play.bbBet$.compareTo(zeroBD) == 0) {
			this.winRateStreets[street] = 0.;
			// TODO Logger.logError("//ERROR calculateWinRateOneStreet play.bbBet$" + " " +
			// play.bbBet$ + " " + play.won$);
			return winRate;
		}
		bd = play.won$.divide(hp, RoundingMode.HALF_EVEN);
		// XXX 0.00 0.00 0.00 5.23
		System.out.println("//XXX " + play.bbBet$ + " " + bd + " " + play.won$ + " " + hp);
		bd = bd.divide(play.bbBet$, RoundingMode.HALF_EVEN);
		winRate = bd.doubleValue();
		winRate = winRate / 100.;
		return winRate;
	}

	/*- ****************************************************************************************************
	 * CalculateWin Rate all streets
	**************************************************************************************************** */
	private double calculateWinRateAllStreets(Players play) {
		double winRate = 0.;
		winRate = calculateWinRateOneStreet(play, 0);
		winRate += calculateWinRateOneStreet(play, 1);
		winRate += calculateWinRateOneStreet(play, 2);
		winRate += calculateWinRateOneStreet(play, 3);
		winRate = winRate / 4;
		return winRate;
	}

	/*- ****************************************************************************************************
	 * CalculateWin Rate eachstreets
	**************************************************************************************************** */
	private double[] calculateWinRateEachStreet(Players play) {
		double[] winRate = new double[NUM_STREETS];
		winRate[0] = calculateWinRateOneStreet(play, 0);
		winRate[1] = calculateWinRateOneStreet(play, 1);
		winRate[2] = calculateWinRateOneStreet(play, 2);
		winRate[3] = calculateWinRateOneStreet(play, 3);
		return winRate;
	}

	// IP and OOP frequency In Position, Out Of Position
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*-******************************************************************************
	 * Calculate frequency for In Position IP
	* Arg0 - Player instance
	 * Arg1 = street , 1 - 3
	 * Arg2 = position , SB, BB, UTG, MP, CO, BU
	 * Returns double percentage
	 * 
	 * 	int[][] bet3Pos = new int[NUM_STREETS][NUM_POS] 
	 * int[][] bet3OperPos = new int[NUM_STREETS][NUM_POS] 
	*******************************************************************************/
	private double calculateFrequencyForIPStreet(Players play, int street) {
		double oper = 0;
		double action = 0;
		action += play.bet3Rp[street][RP_LAST];
		action += play.bet3Rp[street][RP_LASTHU];
		oper += play.bet3OperRp[street][RP_LAST];
		oper += play.bet3OperRp[street][RP_LASTHU];

		return action / oper;
	}

	/*-******************************************************************************
	 * Calculate frequency for Out Of Position OOP
	* Arg0 - Player instance
	 * Arg1 = street , 1 - 3
	 * Arg2 = position , SB, BB, UTG, MP, CO, BU
	 * Returns double percentage
	 * 
	 * We do the calculation using opportunity - double check with HM3   TODO
	 * 
	 * 	int[][] bet3Pos = new int[NUM_STREETS][NUM_POS];
	*******************************************************************************/
	private double calculateFrequencyForOOPStreet(Players play, int street) {
		double oper = 0;
		double action = 0;
		action += play.bet3Rp[street][RP_FIRST];
		action += play.bet3Rp[street][RP_FIRSTHU];

		action += play.bet3Rp[street][RP_MIDDLE1];
		action += play.bet3Rp[street][RP_MIDDLE2];
		action += play.bet3Rp[street][RP_MIDDLE3];
		action += play.bet3Rp[street][RP_MIDDLE4];

		oper += play.bet3OperRp[street][RP_FIRST];
		oper += play.bet3OperRp[street][RP_FIRSTHU];
		oper += play.bet3OperRp[street][RP_MIDDLE1];
		oper += play.bet3OperRp[street][RP_MIDDLE2];
		oper += play.bet3OperRp[street][RP_MIDDLE3];
		oper += play.bet3OperRp[street][RP_MIDDLE4];
		return action / oper;
	}

	/*-******************************************************************************
	 * Each street
	 *******************************************************************************/
	private double[] calculateFrequencyForIPEachStreet(Players play) {
		double[] sum = new double[NUM_STREETS];
		sum[0] = calculateFrequencyForIPStreet(play, 0);
		sum[1] = calculateFrequencyForIPStreet(play, 1);
		sum[2] = calculateFrequencyForIPStreet(play, 2);
		sum[3] = calculateFrequencyForIPStreet(play, 3);
		return sum;
	}

	/*-******************************************************************************
	 * Each street
	 *******************************************************************************/
	private double[] calculateFrequencyForOOPEachStreet(Players play) {
		double[] sum = new double[NUM_STREETS];
		sum[0] = calculateFrequencyForOOPStreet(play, 0);
		sum[1] = calculateFrequencyForOOPStreet(play, 1);
		sum[2] = calculateFrequencyForOOPStreet(play, 2);
		sum[3] = calculateFrequencyForOOPStreet(play, 3);
		return sum;
	}

	// raise percent <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*- ******************************************************************************
	 * Calculate raise percentage for a street and a position
	* Arg0 - Player instance
	 * Arg1 = street , 0 - 3
	 * Arg2 = position , SB, BB, UTG, MP, CO, BU
	 * Returns double percentage
	 * 
	 * Uses opportunity counts not streets played - TODO verify this is correct way
	 * 
	 * 	int[][] callBet2Pos = new int[NUM_STREETS][NUM_POS];
	 * 	int[][] callBet2OperPos = new int[NUM_STREETS][NUM_POS];
	 *******************************************************************************/
	private double calculateRaisePercentage(Players play, int street, int pos) {
		double raiseCount = (double) play.bet2Pos[street][pos] + (double) play.bet3Pos[street][pos]
				+ play.bet4Pos[street][pos];
		double operCount = (double) play.callBet2OperPos[street][pos] + (double) play.callBet3OperPos[street][pos]
				+ play.callBet4OperPos[street][pos];
		if (street != 0) {
			raiseCount += play.bet1Pos[street][pos];
			operCount += play.callBet1OperPos[street][pos];
		}
		return raiseCount / operCount;
	}

	// fold to 3 bet after raising
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*-******************************************************************************
	 * Calculate Fold to 3-Bet After Raising
	 *	Returns a single value 0 - .9999
	 *******************************************************************************/
	double calculateFoldTo3BetAfterRaising() {
		// TODO need new data in Players for won after preflop
		// TODO also foe IP and OOP
		return 0;
	}

	// fold to 3 bet after raising
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*-******************************************************************************
	* Calculate Squeeze
	*	Returns a single value 0 - .9999
	*  Preflop Squeeze
	*	Preflop squeeze tells you how often a player has re-raised after another player 
	*	has raised first in and someone else cold-called. 
	*	This stat is useful for determining how much you should defend against 3-bet squeezes.
	*
	*	A typical squeeze frequency is around 7-9%. 
	*	This means that if you see someone rocking a 12% squeeze, 
	*	for example, you can start calling and 4-betting lighter.
	*
	*	The sample needed here is pretty high, upwards of 3,000 hands or so, 
	*	due to how rare the situation is.
	*******************************************************************************/
	double calculateSqueeze() {
		// TODO need new data in Players for won after preflop
		return 0;
	}

	/*-******************************************************************************
	 * Calculate Fold to  C-Bet
	*	Returns a single value 0 - .9999
	*	This statistic refers to how often a player has continuation bet (c-bet) on the flop after raising preflop. 
	*	This stat needs to be divided into 3-bet pots and single raised pots and then further sub-divided 
	*	into In Position and Out of Position.
	*
	*	Without going into too much detail (as it’s a complex topic and beyond the scope of this article), 
	*	there can be large fluctuations in what the correct frequencies are for each of them.
	*
	*	In general, it’s better to have a high flop c-bet when in position and when out of position 
	*	in 3-bet pots (50-70%, but lower can be good as well). 
	*	It’s usually better to be on the lower side when out of position in single raised pots 
	*	(0-30%, but higher can be good too).
	*
	*	The sample size is not that important here since the fluctuations can be huge. 
	*	What is important, however, is getting a general sense of what your opponent’s approach is. 
	*	If you really want a figure, I’d estimate you need at least a few thousand hands on a player to make this stat reliable.
	*
	*	10. Fold to Flop C-bet
	*	Fold to Flop C-Bet tells you how often a player has called a raise preflop and then folded to a 
	*	continuation bet on the flop. 
	*	As with Flop C-bet, this stat needs to be divided for single raised pots and 3-bet pots 
	*	and then further sub-divided into In Position and Out of Position.
	*
	*	The correct frequency will depend on the bet size that is used, so it’s hard to give precise
	*	numbers to look/aim for. Generally speaking, the folding frequency should be on 
	*	the lower side — below 50%.
	*
	*	The sample size is not that important since the fluctuations are big depending on bet sized
	*	used on average. 
	*	If you really want a figure, I’d (again) estimate you need at least a few thousand hands 
	*	on a player to make this stat reliable.
	*  
	*******************************************************************************/
	double calculateC_Bet() {
		// TODO need new data in Players for won after preflop
		return 0;
	}

	/*-******************************************************************************
	* Calculate  Fold3BetAfterRaising
	*	Returns a single value 0 - .9999
	* This statistic tells you how often your opponent has folded to a 3-bet after raising preflop.
	*
	*	Important warning: When you’re adding this to your HUD, you will also see a plain
	*	“Fold to 3-Bet” stat — don’t pick that one. 
	*	Make sure “after raising” is specified in some way. 
	*	The plain fold to 3-bet stat also includes the hands in which the player hadn’t
	*	put money into the pot, but had folded to a 3-bet 
	*	\(e.g. they fold in the big blind after the cutoff raised and the button 3-bet).
	*
	*	This stat should be further sub-divided into Out of Position (OOP) 
	*	and In Position (IP) because the correct frequencies are different for each of them.
	*	Given the same 3-bet size, you should fold more when OOP than when IP because 
	*	of the power that being in position grants you (realizing equity better).
	*
	*	The appropriate folding frequencies are somewhere around 40-45% when IP and 45-50% when OOP.
	*
	*	The sample size needed here is around 1,500 hands.
	*******************************************************************************/
	double calculateFold3BetAfterRaising() {
		// TODO need to provide data
		return 0;
	}

	/*-******************************************************************************
	* Calculate Fold to  C-Bet
	*	Returns a single value 0 - .9999
	*
	*	Flop C-Bet
	*	This statistic refers to how often a player has continuation bet 
	*	(c-bet) on the flop after raising preflop. 
	*	This stat needs to be divided into 3-bet pots and single raised pots 
	*	and then further sub-divided into In Position and Out of Position.
	*
	*	Without going into too much detail (as it’s a complex topic and 
	*	beyond the scope of this article), there can be large fluctuations in 
	*	what the correct frequencies are for each of them.
	*
	*	In general, it’s better to have a high flop c-bet when in position and 
	*	when out of position in 3-bet pots (50-70%, but lower can be good as well). 
	*	It’s usually better to be on the lower side when out of position 
	*	in single raised pots (0-30%, but higher can be good too).
	*
	*	The sample size is not that important here since the fluctuations can be huge. 
	*	What is important, however, is getting a general sense of what your 
	*	opponent’s approach is. 
	*	If you really want a figure, I’d estimate you need at least a few thousand hands 
	*	on a player to make this stat reliable.
	*
	*	Fold to Flop C-bet
	*	Fold to Flop C-Bet tells you how often a player has called a raise preflop and 
	*	then folded to a continuation bet on the flop. 
	*	As with Flop C-bet, this stat needs to be divided for single raised pots and 
	*	3-bet pots and then further sub-divided into In Position and Out of Position.
	*
	*	The correct frequency will depend on the bet size that is used, so it’s hard to 
	*	give precise numbers to look/aim for. Generally speaking, 
	*	the folding frequency should be on the lower side — below 50%.
	*
	*	The sample size is not that important since the fluctuations are
	*	big depending on bet sized used on average. 
	*	If you really want a figure, I’d (again) estimate you need at least 
	*	a few thousand hands on a player to make this stat reliable.
	*
	*	Final Thoughts
	*	These 10 poker stats (more like 18 when you include all of the sub-stats) 
	*	are the must have numbers that you should include on your HUD.
	*
	*	I will end this article by professing that you should not over-rely on poker statistics.
	*	It’s very easy to come up with very erroneous conclusions if the sample size is too sm 
	*	and that will lead to poor plays based on unreliable evidence.
	*
	*	Furthermore, it’s important to realize that regulars play differently against 
	*	recreational players than they do against other regulars, so you may get 
	*	untrustworthy information even if the sample seems big enough. 
	*	Thankfully, some software, such as Hand2Note, has a function that 
	*	allows you to exclude Regular vs Recreational hands from the statistics, resulting in cleaner data.
	*
	*	That’s all for this article! Please drop any questions or feedback in the comments below.
	*
	*	If you want to learn a super quick and effective way to identify leaks in your 
	*	opponents’ games, read How to Destroy Your Opponent After Seeing One Showdown.
	*
	*	The method you’ll learn in that article works great in live games as well 
	*	as online, though I don’t expect many live players have reached this point of an online HUD article .
	*
	*	Till’ next time, good luck, grinders!
	*
	*	Note: Ready to join 5,000+ players currently upgrading their No Limit Hold’em skills? 
	*	Crush your competition with the expert strategies you will learn inside the 
	*	Upswing Lab training course. Learn more now!
	*******************************************************************************/
	double calculateFoldToC_Bet() {
		// TODO need data from
		return 0;
	}

	// fold to 3 bet after raising
	// <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

	/*- ******************************************************************************
	 * Calculate Average  
	****************************************************************************** */
	private double calculateAverage(double a, double b) {
		if (b <= 0.) {
			return 0.;
		}
		return (a / b);
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
	 * Read a Range from a disk file. 
	 * Arg0 - The full path name.
	 * Returns null if error
	***************************************************************************** */
	PlayerCharacteristics readFromFile(String path) {
		PlayerCharacteristics r = null;
		try (var fileIn = new FileInputStream(path); var in = new ObjectInputStream(fileIn)) {
			r = (PlayerCharacteristics) in.readObject();
		} catch (IOException i) {
			i.printStackTrace();
		} catch (ClassNotFoundException i) {
			i.printStackTrace();
			return null;
		}
		return r;
	}

	/*-******************************************************************************************
	VPIP/PFR/Total AF
	Nit: (<12)/(<10)/(<1.8)
	Loose Nit (P-Fish): (12-36)/<10/<1.8
	Regular: (24-39)/(15-30)/>1.8
	LAG: (40-55)/(15-35)/>1.8 and positive currency won
	TAG: (12-24)/(10-20)/>1.8
	Fish: (>30)/(<10)/(<1.0) and negative currency won 
		
	High VPIP/Low PFR = fish.
	High VPIP/High PFR = LAG.
	Average VPIP/Average PFR = TAG
	High WTSD = calling station.
	
	13/11 = nit reg.
	19/17 - 22/20 = solid tag.
	25/20 - 30/27 = potentially good lag.
	62/7 = very fishy.
	44/32 = aggro fish
	28/6 = tighter fish
		
	autorate Rules Begin
	Aggression:N
	ARI_12 1 Calling Station
	1 2 25.00 Vol. Put Money In Pot % is greater than or equal to 25.00
	15 4 1.20 Aggression Factor - Total is less than or equal to 1.20
	3 1 35.00 Went To Showdown % is greater than 35.00
	ARI_4 2 Vanilla/ABC
	15 5 1.00 2.00 Aggression Factor - Total is between 1.00 and 2.00
	1 5 25.00 35.00 Vol. Put Money In Pot % is between 25.00 and 35.00
	2 5 4.00 9.00 Pre-flop Raise % is between 4.00 and 9.00
	ARI_2 0 Fishy (LPP)
	1 1 40.00 Vol. Put Money In Pot % is greater than 40.00
	2 3 7.00 Pre-flop Raise % is less than 7.00
	15 4 1.20 Aggression Factor - Total is less than or equal to 1.20
	ARI_3 0 sLP P (mini-fish)
	15 4 1.20 Aggression Factor - Total is less than or equal to 1.20
	1 5 25.00 40.00 Vol. Put Money In Pot % is between 25.00 and 40.00
	2 3 7.00 Pre-flop Raise % is less than 7.00
	ARI_5 0 sLA P
	15 4 1.20 Aggression Factor - Total is less than or equal to 1.20
	2 2 7.00 Pre-flop Raise % is greater than or equal to 7.00
	1 5 25.00 40.00 Vol. Put Money In Pot % is between 25.00 and 40.00
	ARI_6 0 TA P (tricky?)
	1 3 25.00 Vol. Put Money In Pot % is less than 25.00
	2 2 7.00 Pre-flop Raise % is greater than or equal to 7.00
	15 4 1.20 Aggression Factor - Total is less than or equal to 1.20
	ARI_7 0 LA A
	1 1 40.00 Vol. Put Money In Pot % is greater than 40.00
	2 2 7.00 Pre-flop Raise % is greater than or equal to 7.00
	15 1 1.20 Aggression Factor - Total is greater than 1.20
	ARI_8 0 LA P
	15 4 1.20 Aggression Factor - Total is less than or equal to 1.20
	1 1 40.00 Vol. Put Money In Pot % is greater than 40.00
	2 2 7.00 Pre-flop Raise % is greater than or equal to 7.00
	ARI_9 0 TP P (Rock)
	1 3 25.00 Vol. Put Money In Pot % is less than 25.00
	15 4 1.20 Aggression Factor - Total is less than or equal to 1.20
	2 3 7.00 Pre-flop Raise % is less than 7.00
	ARI_10 0 sLA A
	2 2 7.00 Pre-flop Raise % is greater than or equal to 7.00
	15 1 1.20 Aggression Factor - Total is greater than 1.20
	1 5 25.00 40.00 Vol. Put Money In Pot % is between 25.00 and 40.00
	ARI_11 0 sLP A
	1 5 25.00 40.00 Vol. Put Money In Pot % is between 25.00 and 40.00
	2 3 7.00 Pre-flop Raise % is less than 7.00
	15 1 1.20 Aggression Factor - Total is greater than 1.20
	ARI_13 0 TP A
	15 1 1.20 Aggression Factor - Total is greater than 1.20
	1 3 25.00 Vol. Put Money In Pot % is less than 25.00
	2 3 7.00 Pre-flop Raise % is less than 7.00
	ARI_14 0 LP A
	1 1 40.00 Vol. Put Money In Pot % is greater than 40.00
	2 3 7.00 Pre-flop Raise % is less than 7.00
	15 1 1.20 Aggression Factor - Total is greater than 1.20
	ARI_15 0 TA A
	15 1 1.20 Aggression Factor - Total is greater than 1.20
	1 3 25.00 Vol. Put Money In Pot % is less than 25.00
	2 2 7.00 Pre-flop Raise % is greater than or equal to 7.00
	
	 * Calculate player Type
	 * Set the variable playerType in this instance of Players to  LAG, TAG, FISH, NIT, REG, AVERAGE, ....
	 * 
	 * In most cases the VPIP is set based on 2 or 3 key variables:
	 * 		vpip 		- Voluntarily Put In Pot
	 * 		pfr 			- Preflop Raise Percentage
	 * 		aggPct		- Aggression Percentage 
	 * 
	 *	High VPIP/Low PFR = fish.
	*	High VPIP/High PFR = LAG.
	*	Average VPIP/Average PFR = TAG
	*	High WTSD = calling station.
	*	 
	*	13/11 = nit reg.	 
	*	19/17 - 22/20 = solid tag.
	*	25/20 - 30/27 = potentially good lag.
	*	62/7 = very fishy.
	*	44/32 = aggro fish
	*	28/6 = tighter fish
	*
	*	Agg Pct starts to become useful for the different streets after about 300 hands. 
	*	Knowing these stats allows you to clearly identify overly passive and aggressive players. 
	*	As far as ranges go, an Agg Pct less than 30% is extremely passive and 
	*	more than 60% extremely aggressive. 
	*	
	*	As always, remember to look at the stats for the individual streets – 
	*	often players are very aggressive on the flop but proceed differently on the turn and the river.
	*	
	*	Examples using Agg Pct and AFq
	*	/For sake of simplicity we’ll assume all opponents have stacks of about 100BB/
	*	
	*	FR (full ring) table, you are in middle position with a pair of 6s. 
	*	A player in EP (early position) limps in, you limp in, and the BB (big blind) checks. 
	*	Flop comes 2 5 9 rainbow (of different suits). 
	*	The big blind bets half of the pot and the early position player folds. 
	*	Now what? Well, let’s look at his stats (we’ll assume a decent hand sample is available).
	*	
	*	a)      VPIP=27 / PFR=15 / Agg Pct=55 (Agg Pct Flop=64).
	*	
	*	The opponent is definitely a loose-aggressive player (LAG). 
	*	He obviously plays a lot of trash hands, so his flop aggression percentage 
	*	of 64 is borderline crazy. 
	*	He didn’t raise pre-flop, so a big overpair is unlikely; probably he caught
	*	something on the flop. 
	*	Against a passive player we can consider folding here, but from.particular 
	*	opponent can bet just as easily second or third pair, or simply two overcards. 
	*	You should at least call, but a better option may be to look at the villain’s 
	*	Fold to F Raise percentage and if it’s high enough, raise him to force a fold.
	*	
	*	b)      VPIP=12 / PFR=5 / Agg Pct= 22 (Agg Pct Flop=28).
	*	
	*	A weak-tight player bets two opponents out of position – a definite sign of a big hand
	*	– at least top pair with decent kicker. He probably would have slow-played a set or two pairs,
	*	but a nine in his hand would explain the bet as protection from over cards on the turn. 
	*	Anyway, under the circumstances we have no more business in the hand and should fold.
	*
	 ****************************************************************************************************/
	private static final ClassificationData rules = new ClassificationData();

	private void calculatePlayerType(Players play) {
		if (play.handsPlayed > 800) {
			if (this.wtsdIfSawFlop >= rules.fish_wtsd_low && this.wtsdIfSawFlop <= rules.fish_wtsd_high) {
				playerType = FISH;
			}
		}

		if (this.vpip >= rules.fish_vpip_low && this.vpip <= rules.fish_vpip_high
				&& this.aggPct >= rules.fish_agg_pct_low && this.aggPct <= rules.fish_agg_pct_high) {
			playerType = FISH;
			return;
		}

		if (this.vpip >= rules.reg_vpip_low && this.vpip <= rules.reg_vpip_high && this.aggPct >= rules.reg_agg_pct_low
				&& this.aggPct <= rules.reg_agg_pct_high) {
			playerType = SHARK;
			return;
		}

		if (this.vpip >= rules.nit_vpip_low && this.vpip <= rules.nit_vpip_high && this.aggPct >= rules.nit_agg_pct_low
				&& this.aggPct <= rules.nit_agg_pct_high) {
			playerType = NIT;
			return;
		}

		if (this.vpip >= rules.lag_vpip_low && this.vpip <= rules.lag_vpip_high && this.aggPct >= rules.lag_agg_pct_low
				&& this.aggPct <= rules.lag_agg_pct_high) {
			playerType = LAG;
			return;
		}

		if (this.vpip >= rules.tag_vpip_low && this.vpip <= rules.tag_vpip_high && this.aggPct >= rules.tag_agg_pct_low
				&& this.aggPct <= rules.tag_agg_pct_high) {
			playerType = TAG;
			return;
		}

		// MUCH TODO
		if (this.vpip == -1.) {
			playerType = AVERAGE;
			return;
		}
		if (this.vpip == -1.) {
			playerType = REG;
			return;
		}
		if (this.vpip == -1.) {
			playerType = HERO;
			return;
		}
		if (this.vpip == -1.) {
			playerType = WINNER;
			return;
		}
		if (this.vpip == -1.) {
			playerType = LOOSER;
			return;
		}
		if (this.vpip == -1.) {
			playerType = USER1;
			return;
		}

		playerType = AVERAGE;
	}

}
