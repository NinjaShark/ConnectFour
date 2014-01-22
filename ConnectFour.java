import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class holds the all of the
 * logic, and GUI components for the Connect Four game.
 * 
 * @author Eileen Balci
 * @version 3.0
 */
public class ConnectFour extends JPanel{
	//all variables are static because this is the only
	//class that will use them and they are all part of this
	//class!
	
	
	/*------- GUI Variables: ------*/
	//PANELS:
	  //gameboard
	private static JPanel gameBoard;
	  //scoreboards
	private static JPanel p1Scores, p2Scores;
	  //buttons
	private static JPanel buttons;
	  //turn teller
	private static JPanel turnTeller;
	
	//BUTTONS:
	  //drop buttons
	private static JButton drop1, drop2, drop3, drop4, drop5, drop6, drop7;
	  //reset and new game buttons
	private static JButton reset, newGame;
	
	//LABELS:
	  //player labels
	private static JLabel labelP1, labelP2;
	  //whosTurnLabel says who's turn it currently is.
	private static JLabel whosTurnLabel;
	  //empty labels to fill space
	private static JLabel empty1, empty2, empty3, empty4, empty5;
	  //ScoreBoard labels
	private static JLabel scoreBoard1, scoreBoard2, matchScore2, matchScore1;
	  //gameboard images (variables that will never change
	  //therefore they are static final variables)
	private static final ImageIcon EMPTY_PEICE = 
		ImagesLocator.getImage("images/gameboardEMPTY.jpg");
	private static final ImageIcon GREEN_PEICE = 
		ImagesLocator.getImage("images/gameboardwithGREEN.jpg");
	private static final ImageIcon ORANGE_PEICE = 
		ImagesLocator.getImage("images/gameboardwithORANGE.jpg");
	
	
	
	/*-------- Logic Variables: --------*/
	
	//The number of chips you need in a row to win!
	//(a constant) will never change.
	private static final int WINNING_NUMBER = 4;
	//number of rows (a constant).
	private static final int NUM_ROWS = 6;
	//number of columns (a constant).
	private static final int NUM_COLS = 7;
	
	//determins the way the winner won
	//0 is the initial state
	//1 equals Vertical
	//2 equals Horizontal
	//3 equals Diagonal (positive)
	//4 equals Diagonal (negative)
	private static int winningType = 0;
	//counters
	private static int turnCounterP1, turnCounterP2; //counts players turns
	private static int matchCounterP1, matchCounterP2; //counts players games won
	private static int masterCounter; //counts total turns taken 
	
	//when winner = true it means 
	//someone won!
	private static boolean winner = false;
	//someone wants to reset the game as oppose to making a new game
	private static boolean wantReset = false;
	//creates a game array which will fill the gameboard with the 
	//emptyPeice ImageIcon
	private static JLabel[][] gameArray = new JLabel[NUM_ROWS][NUM_COLS]; 
	
	/*creates a logic array which will hold the actual logic to figure out
	 *if a user got 4 in a row, or what color peice should be place in
	 *what location in the array. The array holds strings that determine
	 *what the contents of a location in the array is.*/
	private static String[][] logicArray = new String[NUM_ROWS][NUM_COLS]; 
	
	/*These strings (below) are used to fill the logicArray.
	 *Initially the board is filled completely with the string nothing
	 *to represent an empty location in the array. if p1 hits the drop button
	 *the string green will replace the nothing string to show that this 
	 *location is no longer empty and same with the orange string but it will
	 *replace the nothing string with orange instead of green (of course).
	 *it will also be used to figure out if the user got 4 in a row and won.*/
	private static String green = "p1"; //placed in the array to use green peice
	private static String orange = "p2"; //placed in the array to use orange peice
	private static String nothing = "none"; //tells the array that the location is empty
	
	//asks if user wants to start a new game
	private static String newGamePrompt = "Start a New Game?";
	//asks if user wants to restart the game
	private static String restartGamePrompt = "Are you sure you want to Reset? " +
											  "This will erase all data.";
	
	//These Strings are used for the whosTurnLabel
	//they will be used to change the labels contents
	//depending on user input in later methods.
	private static final String EXTENTION = "'S TURN";//a constant
	private static String defaultP1 = "PLAYER ONE";//default
	private static String defaultP2 = "PLAYER TWO";//defalut
	private static String usersChoiceP1 = "";//initially empty
	private static String usersChoiceP2 = "";//initally empty
	
	/**
	 * constructs components, 
	 * assigns variables and adds them
	 * to their panels
	 * 
	 * sets up all the inital stuff
	 * 
	 * key of terms:
	 * p1 means player one
	 * p2 means player two
	 */
	public ConnectFour()
	{
		setLayout(new BorderLayout());
		
		//JPanels
		gameBoard = new JPanel();
		p1Scores = new JPanel();
		p2Scores = new JPanel();
		buttons = new JPanel();
		turnTeller = new JPanel();
		
		//set layouts for all panels
		gameBoard.setLayout(new GridLayout(7, 7));
		p1Scores.setLayout(new GridLayout(12, 1));
		p2Scores.setLayout(new GridLayout(12, 1));
		buttons.setLayout(new GridLayout(1,9));
		
		//set colors for panels to black
		p1Scores.setBackground(Color.black);
		p2Scores.setBackground(Color.black);
		gameBoard.setBackground(Color.black);
		buttons.setBackground(Color.black);
		
        //JLabels
		labelP1 = new JLabel("PLAYER 1");
		labelP1.setForeground(Color.white);
		labelP1.setFont (new Font ("Frutiger SBIN It v.1", Font.BOLD, 18));
		labelP2 = new JLabel("PLAYER 2");
		labelP2.setForeground(Color.white);
		labelP2.setFont (new Font ("Frutiger SBIN It v.1", Font.BOLD, 18));
		
		//empty labels used to fill up space
		empty1 = new JLabel();
		empty2 = new JLabel();
		empty3 = new JLabel();
		empty4 = new JLabel();
		empty5 = new JLabel();
		
		//scoreboard components
		matchScore2 = new JLabel("Games Won: 0");
		matchScore2.setForeground(Color.orange);
		matchScore1 = new JLabel("Games Won: 0");
		matchScore1.setForeground(Color.orange);
		scoreBoard1 = new JLabel("Turn: 0");
		scoreBoard1.setForeground(Color.orange);
	    scoreBoard2 = new JLabel("Turn: 0");
	    scoreBoard2.setForeground(Color.orange);

		//JButtons
		newGame = new JButton("New Game");
		drop1 = new JButton("Drop");
		drop2 = new JButton("Drop");
		drop3 = new JButton("Drop");
		drop4 = new JButton("Drop");
		drop5 = new JButton("Drop");
		drop6 = new JButton("Drop");
		drop7 = new JButton("Drop");
		reset = new JButton("RESET");
		
		//add the buttons to their ButtonListeners
		drop1.addActionListener (new DropButtonListener());
		drop2.addActionListener (new DropButtonListener());
	    drop3.addActionListener (new DropButtonListener());
	    drop4.addActionListener (new DropButtonListener());
	    drop5.addActionListener (new DropButtonListener());
	    drop6.addActionListener (new DropButtonListener());
	    drop7.addActionListener (new DropButtonListener());
	    reset.addActionListener (new ResetButtonListener());
	    newGame.addActionListener (new NewGameButtonListener());
	    
	    //fill the gameBoard with the initial ImageIcon
		this.fill();
		
		//This adds the drop buttons to the
		//gameBoard panel (to have them
		//line up properly under the intended column)
		gameBoard.add(drop1).setMaximumSize(new Dimension(65, 35));
		gameBoard.add(drop2).setMaximumSize(new Dimension(65, 35));
		gameBoard.add(drop3).setMaximumSize(new Dimension(65, 35));
		gameBoard.add(drop4).setMaximumSize(new Dimension(65, 35));
		gameBoard.add(drop5).setMaximumSize(new Dimension(65, 35));
		gameBoard.add(drop6).setMaximumSize(new Dimension(65, 35));
		gameBoard.add(drop7).setMaximumSize(new Dimension(65, 35));
		
		//this adds the reset and new game buttons
		//as well as some empty labels to the button
		//panel
		buttons.add(empty1).setPreferredSize(new Dimension(75, 35));
		buttons.add(empty2).setPreferredSize(new Dimension(75, 35));
		buttons.add(newGame).setPreferredSize(new Dimension(75, 35));
		buttons.add(empty3).setPreferredSize(new Dimension(75, 35));
		buttons.add(reset).setPreferredSize(new Dimension(75, 35));
	    buttons.add(empty4).setPreferredSize(new Dimension(75, 35));
	    buttons.add(empty5).setPreferredSize(new Dimension(75, 35));
		
		//add components to p1Scores 
		p1Scores.add(labelP1);
		p1Scores.add(scoreBoard1);
		p1Scores.add(matchScore1);
		
		//add components to p2Scores
		p2Scores.add(labelP2);
		p2Scores.add(scoreBoard2);
		p2Scores.add(matchScore2);

		//all counters start at zero
		turnCounterP1 = 0; 
		turnCounterP2 = 0;
		masterCounter = 0;
		matchCounterP1 = 0;
		matchCounterP2 = 0;

		//turn teller
		//initializes variable to default value
		whosTurnLabel = new JLabel(defaultP1 + EXTENTION);
		
		//asks user to select name
		//if a valid string was inputed
		//whosTurnLabel will be changed to users desired 
		//name.
		p1SelectName();
		p2SelectName();
		//checks if user names are the same
		checkUserNames();
		//sets the inital label to player ones choice
		whosTurnLabel.setText(usersChoiceP1 + EXTENTION);
		whosTurnLabel.setForeground(Color.red);
		whosTurnLabel.setFont (new Font ("Credit Valley", Font.BOLD, 36));
		turnTeller.setBackground(Color.black);
		turnTeller.add(whosTurnLabel);
		
		//adds the JPanels to the BorderLayout
		add(turnTeller, BorderLayout.NORTH);
		add(gameBoard, BorderLayout.CENTER);
	    add(p1Scores, BorderLayout.WEST);
	    add(p2Scores, BorderLayout.EAST);
	    add(buttons, BorderLayout.SOUTH);
	}
	
	/**
	 * Gets the label that holds the
	 * who's turn information
	 * @return the whosTurnLabel JLabel
	 */
	public static JLabel getWhosTurnLabel()
	{
		return whosTurnLabel;
	}
	
	/**
	 * Gets the label that holds the
	 * turn information for player one
	 * @return the scoreBoard1 JLabel
	 */
	public static JLabel getScore1()
	{
		return scoreBoard1;
	}
	
	/**
	 * Gets the label that holds the
	 * turn information for player two
	 * @return the scoreBoard1 JLabel
	 */
	public static JLabel getScore2()
	{
		return scoreBoard2;
	}
	
	/**
	 * Gets the label that holds the
	 * turn information for player one
	 * @return the matchScore1 JLabel
	 */
	public static JLabel getMatchScore1()
	{
		return matchScore1;
	}
	
	/**
	 * Gets the label that holds the
	 * turn information for player two
	 * @return the matchScore1 JLabel
	 */
	public static JLabel getMatchScore2()
	{
		return matchScore2;
	}
	
	
	
	/**
	 * Gets the number of turns player one
	 * has played so far
	 * @return the number of turns player one has taken
	 */
	public int getP1NumTurns()
	{
		return turnCounterP1;
	}
	
	/**
	 * Gets the number of turns player two
	 * has played so far
	 * @return the number of turns player two has taken
	 */
	public int getP2NumTurns()
	{
		return turnCounterP2;
	}
	
	/**
	 * Get the number of games player one
	 * has won
	 * @return the number of games player one
	 * has won
	 */
	public int getP1NumMatchesWon()
	{
		return matchCounterP1;
	}
	
	/**
	 * Get the number of games
	 * player two has won
	 * @return the number of games player 
	 * two has won
	 */
	public int getP2NumMatchesWon()
	{
		return matchCounterP2;
	}
	
	/**
	 * set winner to false
	 * (this will be used to set
	 * winner to false after someone won)
	 */
	public void setWinnerFalse()
	{
		winner = false;
	}

	/**
	 * prints out the logicArray contents
	 * (in case of debugging, not used for
	 * the actual game in any way)
	 */
	public void printOutArray()
	{
		for (int col=0; col < NUM_COLS; col++){
			for (int row=0; row < NUM_ROWS; row++){
				System.out.println("row= " + row + " col= " + col + " contents: " 
									+ logicArray[row][col]);
			}
		}
	}
	
	/**
	 * This Method allows the user (player one) to choose a 
	 * name other than PLAYER ONE (the default)
	 * it checks to make sure the input isn't longer then
	 * 15 characters and that the input isn't empty or just one space
	 * 
	 * (special characters are allowed and so are numbers)
	 */
	public void p1SelectName()
	{
		//gets the users desired name and makes it upper case.
		usersChoiceP1 = JOptionPane.showInputDialog("User name:", defaultP1).toUpperCase();
		
		//Checks if the user entered nothing or just a space
		if(usersChoiceP1.equals("") || usersChoiceP1.equals(" ")){
			JOptionPane.showMessageDialog(null, 
					 "Invalid Input: must contain at lesat one character" +
					 " (special characters allowed) and " + 
					 "must have more characters then only one space.\nYour user name " +
					 "has not been changed from the default value." +
					 "\n\nYou are PLAYER ONE");
			usersChoiceP1 = defaultP1;
		}
		//checks if the user inputed a string that is too long
		//(longer then 15 chars)
		else if(usersChoiceP1.length() > 16){
			JOptionPane.showMessageDialog(null, 
					 "Invalid Input: user name is too long! Must be less then 16 " +
					 "characters\nYour user name has not been changed from " +
					 "the default value.\n\nYou are PLAYER ONE");
			usersChoiceP1 = defaultP1;
		}
		//if everything above passes the user gets the desired name
		else{
			whosTurnLabel.setText(usersChoiceP1+ EXTENTION);
		}
	}
	
	/**
	 * This Method allows the user (player two) to choose a 
	 * name other than PLAYER TWO (the default)
	 * it checks to make sure the input isn't longer then
	 * 15 characters and that the input isn't empty or just one space
	 * 
	 * (special characters are allowed and so are numbers)
	 */
	public void p2SelectName()
	{
		//gets the users desired name and makes it upper case
		usersChoiceP2 = JOptionPane.showInputDialog("User name:", defaultP2).toUpperCase();
		 
		//Checks if the user entered nothing or just a space
		if(usersChoiceP2.equals("") || usersChoiceP2.equals(" ")){
			JOptionPane.showMessageDialog(null, 
					"Invalid Input: must contain at least one character" +
					" (special characters and numbers are allowed) and " + 
					"must have more characters then only one space." +
					"\nYour user name has not been changed from the " + 
					"default value.\n\nYou are PLAYER TWO");
			usersChoiceP2 = defaultP2;
		}
		//checks if the user inputed a string that is too long
		//(longer then 15 chars)
		else if(usersChoiceP2.length() > 16){
			JOptionPane.showMessageDialog(null, 
					"Invalid Input: user name is too long! Must be less " +
					"then 16 characters\nYour user name has not been " +
					"changed from the default value.\n\nYou are PLAYER TWO");
			usersChoiceP2 = defaultP2;
		}
		//if everything above passes the user gets the desired name
		else{
			whosTurnLabel.setText(usersChoiceP2 + EXTENTION);
		}
	}
	
	/**
	 * Checks if user names are the same
	 * if they are it resets the names back
	 * to default.
	 */
	public void checkUserNames()
	{
		if(usersChoiceP1.equals(usersChoiceP2)){
			JOptionPane.showMessageDialog(null, 
					 "Invalid Input: both users cannot have the same name! " +
					 "Both users have been reset to default names " + 
					 "PLAYER ONE and PLAYER TWO.");
			//sets them back to default
			usersChoiceP1 = defaultP1;
			usersChoiceP2 = defaultP2;
			//sets label to show player one first
			whosTurnLabel.setText(usersChoiceP1 + EXTENTION);
		}
	}
	
	/**
	 * This method allows the user to change the background color
	 * of the panels (all the panels except gameBoard
	 * will always be the same color)
	 * to keep the look and feel uniform.
	 * @param colorChoice is the color to change the background to
	 */
	public void changeBGColor(Color colorChoice)
	{
		turnTeller.setBackground(colorChoice);
	    p1Scores.setBackground(colorChoice);
	    p2Scores.setBackground(colorChoice);
	    buttons.setBackground(colorChoice);
	}
	
	/**
	 * This Method Fills up the gameBoard JPanel with
	 * labels (makes up the initial gameboard)
	 * and the logicArray with the nothing
	 * string (to represent an empty 2D array).
	 */
	public void fill()
	{
        //fill the board with labels and set the icon
		//to the empty board peice
		for(int rows=0; rows < NUM_ROWS; rows++){
			//cols is length + 1 because it has 7 cols while
			//rows has only 6
			for(int cols=0; cols < NUM_COLS; cols++){
				gameArray[rows][cols] = new JLabel();
				gameArray[rows][cols].setIcon(EMPTY_PEICE);
				gameBoard.add(gameArray[rows][cols]);
			}
		}
		
		//fill the 2D logicArray with the nothing String
		for(int rows=0; rows < NUM_ROWS; rows++){
			for(int cols=0; cols < NUM_COLS; cols++){
				logicArray[rows][cols] = nothing;
			}
		}
	}
	
	/**
	 * If it is player one's turn this method
	 * will increment the player one turn counter
	 * and masterCounter, when masterCounter is
	 * odd it will be player two's turn 
	 * then it will increment player two's turn counter and 
	 * the masterCounter again making it even so it is
	 * player one's turn again. It will repeat this process 
	 * each time a drop button is pressed.
	 * 
	 * (to be used in the dropButtonListener class)
	 */
	public void turnChanger()
	{
		if(winner==false){
           //if it is player one's turn it will increment p1's turn counter
			if(masterCounter % 2 == 0){ 
				whosTurnLabel.setText(usersChoiceP2 + EXTENTION);
				masterCounter++; //increments master turn counter
				turnCounterP1 = turnCounterP1 + 1; //increments
				String text1 = getP1NumTurns() + ""; //changes value to String
		    	scoreBoard1.setText("Turn: " + text1); //updates Label
			}
			else if(masterCounter % 2 == 1){ //it must be p2's turn
				whosTurnLabel.setText(usersChoiceP1 + EXTENTION);
				masterCounter++; //increments master turn counter
				turnCounterP2 = turnCounterP2 + 1; //increments
				String text2 = getP2NumTurns() + ""; //changes value to String
				scoreBoard2.setText("Turn: " + text2); //updates Label
	     	}
		}
	}
	
	/**
	 * This method simulates dropping a token into the gameboard
	 * if a column becomes full, the cooresponding drop button is
	 * disabled.
	 * @param col determins what col the item is being dropped into
	 */
	public void drop(int col)
	{
		//if master counter is even, it is player 1's turn
		if(masterCounter % 2 == 0){ 
			if(logicArray[5][col].equals(nothing)){
				//sets the correct peice in the correct
				//row and column
				gameArray[5][col].setIcon(GREEN_PEICE);
				logicArray[5][col] = green;
			}
			else if(logicArray[4][col].equals(nothing)){
				//sets the correct peice in the correct
				//row and column
				gameArray[4][col].setIcon(GREEN_PEICE);
				logicArray[4][col] = green;
			}
			else if(logicArray[3][col].equals(nothing)){
				//sets the correct peice in the correct
				//row and column
				gameArray[3][col].setIcon(GREEN_PEICE);
				logicArray[3][col] = green;
			}
			else if(logicArray[2][col].equals(nothing)){
				//sets the correct peice in the correct
				//row and column
				gameArray[2][col].setIcon(GREEN_PEICE);
				logicArray[2][col] = green;
			}
			else if(logicArray[1][col].equals(nothing)){
				//sets the correct peice in the correct
				//row and column
				gameArray[1][col].setIcon(GREEN_PEICE);
				logicArray[1][col] = green;
			}
			//the arrays last spot avaliable, disables button
			//after user drops the last peice in the column
			else if(logicArray[0][col].equals(nothing)){
				//sets the correct peice in the correct
				//row and column
				gameArray[0][col].setIcon(GREEN_PEICE);
				logicArray[0][col] = green;
				
				//checks if it is the last spot in the column
				if(logicArray[0][col].equals(green) || 
				   logicArray[0][col].equals(orange)){
					
					//disables the full column's button
					//NOTE: if second argument was
					//true it would enable the buttons
					ableOrDisableDropButtons(col, false);
				}
			}
		}
		//now it must be player 2's turn
		else if(masterCounter % 2 == 1){
			if(logicArray[5][col].equals(nothing)){
				//sets the correct peice in the correct
				//row and column
				gameArray[5][col].setIcon(ORANGE_PEICE);
				logicArray[5][col] = orange;
			}
			else if(logicArray[4][col].equals(nothing)){
				//sets the correct peice in the correct
				//row and column
				gameArray[4][col].setIcon(ORANGE_PEICE);
				logicArray[4][col] = orange;
			}
			else if(logicArray[3][col].equals(nothing)){
				//sets the correct peice in the correct
				//row and column
				gameArray[3][col].setIcon(ORANGE_PEICE);
				logicArray[3][col] = orange;
			}
			else if(logicArray[2][col].equals(nothing)){
				//sets the correct peice in the correct
				//row and column
				gameArray[2][col].setIcon(ORANGE_PEICE);
				logicArray[2][col] = orange;
			}
			else if(logicArray[1][col].equals(nothing)){
				//sets the correct peice in the correct
				//row and column
				gameArray[1][col].setIcon(ORANGE_PEICE);
				logicArray[1][col] = orange;
			}
			//the arrays last spot avaliable. this disables the
			//button after user drops the last peice into the column
			else if(logicArray[0][col].equals(nothing)){
				//sets the correct peice in the correct
				//row and column
				gameArray[0][col].setIcon(ORANGE_PEICE);
				logicArray[0][col] = orange;
				
				//checks if it is the last spot in the column
				if(logicArray[0][col].equals(green) || 
				   logicArray[0][col].equals(orange)){
					
					//disables the full column's button
					//NOTE: if second argument was true 
					//it would enable the buttons.
					ableOrDisableDropButtons(col, false);
				}
			}
		}
	}
	
	/**
	 * This method disables the drop button that
	 * is related to the full column, when the
	 * column is full.
	 * 
	 * @param colNum is the number of the column
	 * that is full.
	 * @param isEnabled is true if you want the buttons
	 * abled and false if you want the buttons disabled
	 */
	public void ableOrDisableDropButtons(int colNum, boolean isEnabled)
	{
		//figures out what column is full
		//and then disables that column's drop button
		//if isEnabled is false
		//(if isEnabled is true it enables the button)
		if(colNum == 0){
			drop1.setEnabled(isEnabled);
		}
		else if(colNum == 1){
			drop2.setEnabled(isEnabled);
		}
		else if(colNum == 2){
			drop3.setEnabled(isEnabled);
		}
		else if(colNum == 3){
			drop4.setEnabled(isEnabled);
		}
		else if(colNum == 4){
			drop5.setEnabled(isEnabled);
		}
		else if(colNum == 5){
			drop6.setEnabled(isEnabled);
		}
		else if(colNum == 6){
			drop7.setEnabled(isEnabled);
		}
	}
	
	/**
	 * this method adds back the components for 
	 * the game after the user hits the 
	 * reset or new game button
	 */
	public void addBackComponents()
	{
		gameBoard.removeAll();//removes all gameBoard components
	      
	    fill();//fills gameBoard back up to its initial state
	    
	    //re-adds the buttons
	    gameBoard.add(drop1).setPreferredSize(new Dimension(75, 35));
	    gameBoard.add(drop2).setPreferredSize(new Dimension(75, 35));
	    gameBoard.add(drop3).setPreferredSize(new Dimension(75, 35));
		gameBoard.add(drop4).setPreferredSize(new Dimension(75, 35));
		gameBoard.add(drop5).setPreferredSize(new Dimension(75, 35));
		gameBoard.add(drop6).setPreferredSize(new Dimension(75, 35));
	    gameBoard.add(drop7).setPreferredSize(new Dimension(75, 35));
	    
		//re-enables all the drop buttons if any where disabled
  	    for(int col = 0; col < NUM_COLS; col++){
  	    	ableOrDisableDropButtons(col, true);
  	    }
	}
	
	/**
	 * This method sets everything 
	 * (except the match counters) back
	 * to initial state to start a new game
	 * -----------------------------------
	 * It does not set the match counters
	 * to zero, those remain with whatever
	 * values they held before. because
	 * it is what keeps track of how many
	 * games the player has won.
	 * (resetGame() is what sets everything
	 * to initial state including the match counters)
	 */
	public void makeNewGame(String dialog)
	{
		int choice = JOptionPane.showConfirmDialog(null, dialog);
		//if the choice equals zero, yes was choosen
		if(choice == 0){
  	  		//sets only the turn counters to zero
			//match counters still display their
  	  		//values.
  	  		scoreBoard1.setText("Turn: 0");
  	  		turnCounterP1 = 0;
  	  		scoreBoard2.setText("Turn: 0");
  	  		turnCounterP2 = 0;
  	  		
  	  		//set the master counter to zero
  	  		masterCounter = 0;
  	  		//set the label to the right players turn
  	  		//(player one always goes first)
  	  		whosTurnLabel.setText(usersChoiceP1 + EXTENTION);
  	  		
  	  		//set winningType to zero
  	  		winningType = 0;

  	  		//add back the components
  	  		addBackComponents();
  	  		
  	  		if(wantReset){
  	  			//reset sets these fields back to initial state
  	  			//(back to zero) but not new game
  	  			matchScore1.setText("Games Won: 0");
  	  			matchCounterP1 = 0; //sets value back to zero
  	  			matchScore2.setText("Games Won: 0");
  	  			matchCounterP2 = 0; //sets value back to zero
  	  		}
		}
	}
	
	/**
	 * This method sets everything back
	 * to initial state and starts a new game
	 * -----------------------------------
	 * It will set EVERYTHING to its initial
	 * state (zero for the counters),
	 * (empty labels for the gameboard),
	 * (enabled drop buttons that where disabled)
	 * etc. 
	 * 
	 * NOTE: If the user doesn't want to reset the 
	 * match counters, they should use the "New Game"
	 * button.
	 */
	public void resetGame(String dialog)
	{
		wantReset = true;
		//reset dose the same thing as new game
		//but wantReset is true so it also
		//sets the match counters back to zero
		makeNewGame(dialog);
		//after makeNewGame resets the game
		//wantReset becomes false again.
		wantReset = false;
	}
	
	/**
	* This method will return true if one of the
	* players has won the game.
	* 
	* @return true if a player has won 
	*/
	public boolean hasPlayerWon() 
	{
		//checks to see if user won VERTICALLY
		if(winner == false){
			for (int col=0; col < NUM_COLS; col++){
				for (int row=NUM_ROWS-1; row > WINNING_NUMBER-2; row--){
					if (!logicArray[row][col].equals(nothing) &&
						logicArray[row][col].equals(logicArray[row-1][col]) &&
						logicArray[row][col].equals(logicArray[row-2][col]) &&
						logicArray[row][col].equals(logicArray[row-3][col]))
					{
						//winner won vertically
						winningType = 1;
						return winner = true;
					}
				}
			}
		}
		//checks to see if user won HORIZONTALLY
		if(winner == false){
			for (int row=0; row < NUM_ROWS; row++) {
				for (int col=0; col < WINNING_NUMBER; col++) {
					if (!logicArray[row][col].equals(nothing) &&
						logicArray[row][col].equals(logicArray[row][col+1]) &&
						logicArray[row][col].equals(logicArray[row][col+2]) &&
						logicArray[row][col].equals(logicArray[row][col+3]))
					{ 
						//winner won horizontally
						winningType = 2;
						return winner = true;
					}
				}
			}
		}
		//checks if user won DIAGONALLY with a positive slope
		if(winner == false){
			for (int row=WINNING_NUMBER-1; row < NUM_ROWS; row++) {
				for (int col=0; col < WINNING_NUMBER; col++) {
					if (!logicArray[row][col].equals(nothing) &&
							logicArray[row][col].equals(logicArray[row-1][col+1]) &&
							logicArray[row][col].equals(logicArray[row-2][col+2]) &&
							logicArray[row][col].equals(logicArray[row-3][col+3])) 
					{
						//winner won diagonally (pos)
						winningType = 3;
						return winner = true;
					}
				}
			}
		}
		//checks if user won DIAGONALLY with a negative slope
		if(winner == false){
			for (int row=0; row < WINNING_NUMBER-1; row++) {
				for (int col=WINNING_NUMBER-1; col > -1; col--) {
					if (!logicArray[row][col].equals(nothing) &&
						logicArray[row][col].equals(logicArray[row+1][col+1]) &&
						logicArray[row][col].equals(logicArray[row+2][col+2]) &&
						logicArray[row][col].equals(logicArray[row+3][col+3])) 
					{
						//winner won diagonally (neg)
						winningType = 4;
						return winner = true;
					}
				}
			}
		}
	    return winner; //returns whether or not a user won 
	}

	/**
	 * Check to see who the winner was
	 * based on who's turn it was when the
	 * winning game peice was dropped
	 */
	public void getWinner()
	{
		//check to see who won by
		//using masterCounter and whether it 
		//was even or odd. then add to matchCounters
		if(winner){
			if(masterCounter % 2 == 0){
				//updates how many matches
				//the player won
				matchCounterP1 += 1;
				//updates the label that 
				//holds the match information
				matchScore1.setText("Games Won: " + getP1NumMatchesWon());
				
				//announces winner
				if(winningType == 1){
					JOptionPane.showMessageDialog(null, 
							usersChoiceP1 + " WINS VERTICALLY!");
				}
				else if(winningType == 2){
					JOptionPane.showMessageDialog(null, 
							usersChoiceP1 + " WINS HORIZONTALLY!");
				}
				else if(winningType == 3){
					JOptionPane.showMessageDialog(null, 
							usersChoiceP1 + " WINS DIAGONALLY (POSITIVE)!");
				}
				else if(winningType == 4){
					JOptionPane.showMessageDialog(null, 
							usersChoiceP1 + " WINS DIAGONALLY (NEGATIVE)!");
				}
				
				//Disables all the drop buttons because the game is over
		  	    for(int col = 0; col < NUM_COLS; col++){
		  	    	ableOrDisableDropButtons(col, false);
		  	    }
				
				//asks user if they would like to start a new game
				makeNewGame(newGamePrompt);
			}
			else if(masterCounter % 2 == 1){
				//updates how many matches
				//the player won
				matchCounterP2 += 1;
				//updates the label that 
				//holds the match information
				matchScore2.setText("Games Won: " + getP2NumMatchesWon());

				//announces winner
				if(winningType == 1){
					JOptionPane.showMessageDialog(null, 
							usersChoiceP2 + " WINS VERTICALLY!");
				}
				else if(winningType == 2){
					JOptionPane.showMessageDialog(null, 
							usersChoiceP2 + " WINS HORIZONTALLY!");
				}
				else if(winningType == 3){
					JOptionPane.showMessageDialog(null, 
							usersChoiceP2 + " WINS DIAGONALLY (POSITIVE)!");
				}
				else if(winningType == 4){
					JOptionPane.showMessageDialog(null, 
							usersChoiceP2 + " WINS DIAGONALLY (NEGATIVE)!");
				}
				
				//Disables all the drop buttons because the game is over
		  	    for(int col = 0; col < NUM_COLS; col++){
		  	    	ableOrDisableDropButtons(col, false);
		  	    }

				//asks user if they would like to start a new game
				makeNewGame(newGamePrompt);
			}
		}
	}
		
	 //*****************************************************************
     //  Button Listener for DROP buttons
     //*****************************************************************
	   private class DropButtonListener implements ActionListener
	   {
	      //--------------------------------------------------------------
	      //  places a game peice in the correct location thenb checks 
		  //  if someone won, if there is no winner, it changes turns and 
		  //  keeps winner false. If there is a winner turnChanger gets 
		  //  skipped (because of the condition in the turnChanger()  
		  //  method) then it sets winner back to false.
	      //--------------------------------------------------------------
	      public void actionPerformed (ActionEvent event)
	      {
	    	  //figures out what button is being pressed 
	    	  //and preforms its designated action
	    	  if (event.getSource() == drop1){
	    		  //drops it in columns index zero
	    		  //which is the first column.
	    	     drop(0);
	    	     //checks if that was a winning drop
	    	     hasPlayerWon();
	    	     //finds out who the winner was if
	    	     //there is a winner
	    	     getWinner();
	    	     //changes turns unless winner is true
	    	     turnChanger();
	    	     //if there was a winner, 
	    	     //winner would equal true so
	    	     //this setter changes winner
	    	     //back to false. otherwise, winner
	    	     //will just continue being false.
	    	     setWinnerFalse();
	    	  }
	    	  else if(event.getSource() == drop2){
	    		  drop(1);
	    		  hasPlayerWon();
	    		  getWinner();
	    		  turnChanger();
	    		  setWinnerFalse();
	    		  
	    	  }
	    	  else if(event.getSource() == drop3){
	    		  drop(2);
	    		  hasPlayerWon();
	    		  getWinner();
	    		  turnChanger();
	    		  setWinnerFalse();
	    	  }
	    	  else if(event.getSource() == drop4){
	    		  drop(3);
	    		  hasPlayerWon();
	    		  getWinner();
	    		  turnChanger();
	    		  setWinnerFalse();
	    	  }
	    	  else if(event.getSource() == drop5){
	    		  drop(4);
	    		  hasPlayerWon();
	    		  getWinner();
	    		  turnChanger();
	    		  setWinnerFalse();
	    	  }
	    	  else if(event.getSource() == drop6){
	    		  drop(5);
	    		  hasPlayerWon();
	    		  getWinner();
	    		  turnChanger();
	    		  setWinnerFalse();
	    	  }
	    	  else if(event.getSource() == drop7){
	    		  drop(6);
	    		  hasPlayerWon();
	    		  getWinner();
	    		  turnChanger();
	    		  setWinnerFalse();
	    	  }
	          else {
	        	  //This should NEVER be reached
	             JOptionPane.showMessageDialog(null, "ERROR with drop!");
	          }
	      }
	   }
	   
     //*****************************************************************
	 //  Button Listener for RESET button
	 //*****************************************************************
	   private class ResetButtonListener implements ActionListener
	   {
		  //--------------------------------------------------------------
		  //  resets the game by making everything go back to initial state
		  //  (ABSOULUTLY EVERYTHING WILL BE RESET TO INITIAL STATE!)
		  //--------------------------------------------------------------
		  public void actionPerformed (ActionEvent event)
	      {
		    	  if (event.getSource() == reset){
		    		  resetGame(restartGamePrompt);
		    	  } 
		    	  else {
		    		  //This should NEVER be reached
			          JOptionPane.showMessageDialog(null, "ERROR with reset!");
			      }
		   }
		 }
      
	   //*****************************************************************
	   //  Button Listener for NEWGAME button
	   //  This button unlike reset, only clears the board and 
	   //  the turn counter, but the match counter value
	   //  remains the same.
	   //*****************************************************************
		 private class NewGameButtonListener implements ActionListener
		 {
			  //--------------------------------------------------------------
			  //  moves in the specifide direction when pushed
			  //--------------------------------------------------------------
			  public void actionPerformed (ActionEvent event)
			  {
			      //The actions that should be preformed 
				  //when a certain button is pressed
			      if (event.getSource() == newGame){
			    	  makeNewGame(newGamePrompt);
			      }
			      else {
			    	  //This should NEVER be reached
			          JOptionPane.showMessageDialog(null, "ERROR with new game!");
			      }
			   }
		  }
}




