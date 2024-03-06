# HoldemHand# HoldemHandHistory This set of applications is for evaluating Texas Hold'em no-limit 6-max poker. It is part of a research project searching for simple methods to increase win rates. I am not a purist. I do not care if the method is mathematically 100% correct, GTO, AI, exploitation, or whatever. The methods must increase win rates and be simple enough for an average person to apply at the table or online.

I have written a lot of code during this research. Anyone who wants is free to use all or part of the code. I do hope that I can get others to participate in this project by suggesting new ideas or by actually developing new code.

The code is in four broad categories:

1. **Hold’em Hand History:** Analyzing many millions of actual hands in a unique way, not like the very successful Hold’em Manager using a relational database but with a collection of objects that allow for a new unique type of analysis. This includes things like the actual results of bet size on opponent responses.

1. **Hold’em Evaluation:** This code does a Monte Carlo-type simulation that evaluates the results of play on the Flop, Turn, River, and Showdown. It creates indexes based on hand structure and board structure, runs millions of simulated hands, and categorizes the results.

1. **Hold’em Simulator:** Using the results from the other two projects, the simulator uses data from the Hand History analysis to simulate how an opponent will respond to situations at the table. The simulation then searches for exploits of the opponent's behavior.

1. **Hold’em Coach:** An application that uses results from the other three to play against the simulator with coaching prompts and exercises to learn how to increase your win rate. The ultimate goal of all this.

All four projects are being done in parallel with code being shared between them. All are at about the same level of completion. They are all in test and debug, past the prototype stage but far from complete. These are research projects that are interrelated. I will be placing the code for all four on GitHub within the next few months.




**Hold'em Hand History Analysis<a name="_hlk160043632"></a> is this application**. It analyzes Hand History files in an entirely different way than Hold’em Manager or any other application. It use several data structures constructed using Hand History files. It is not a relational database. Relational is great, put imposes a ton of restrictions and limitations.

Most importantly, the basic work is complete, now we can write the code analyze millions of hands. It is an open source opportunity to learn things not currently possible otherwise.

Use the data structures and the classes used to construct and analyze them to do some advanced research. Look for patterns and frequencies that will identify exploitation opportunities. 

If you decide to participate, or only want to evaluate, contact me and I will give you millions of Hand History files in a Universal Format. Also JAR files to process those files into class instances as .ser files for analysis. Use one of the classes to convert the .ser file into an instance of the class. Now write your own class to analyze the data. Use all or part of the many classes that I have written to report and analyze the data. Be prepared to help complete the work that I have started in a collaborative fashion.




Hold'em Hand History Converter**.** Reads Hand History files produced by Hold’em Poker Sites such as PokerStars, GGPoker, 888Poker, and some Canadian sites. As time allows, I will add other sites.  

There are two steps. First filter the Hand History files to remove the junk. The filter typically eliminates about 50% of all hands. Most of the filters are optional, some used for sorting such as stakes and currency, some to only keep hands with all six players, some that may result in invalid analysis such as a player disconnected on the Turn. One for privacy by converting player names into Integer equivalent strings so HappyFool becomes 008732. The filtered output can be used for existing applications or as input to step 2.

The next step is to parse the Hand History hands and convert then into a Universal Format, as defined by a Java class. All sites must follow the same rules of Hold’em ( well mostly ).  

Most importantly, the basic work is complete, now we can write the code analyze millions of hands. It is an open source opportunity to learn things not currently possible otherwise. So a Universal Format is possible and makes it possible for all applications that use it to be independent of any site. Also much more efficient. Hold’em Hand History Analysis uses only this format. 

This code is complete. If you want to participate the opportunity is to develop the parser for other sites.

I hope that by making all of this open source the set of applications will be expanded. Collaborators are eagerly invited. 

The application class names all begin with GUI:

`	`GUIFilter. Reads raw Hand History files and gets rid of the ytash.

`	`GUIConvertToUniversal. Reads filtered files and converts to a universal format.

`	`GUISelectionAndCreation. Reads universal files and creates data structure.

GUIEditProfile. Creates and edits profiles used by GUISelectionAndCreation

`	`GUIAnalyzeData. Many different reports using the data structures created.

GUIxxx. To be done, many small applications to analyze data.
History
