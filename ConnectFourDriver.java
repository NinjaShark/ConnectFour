import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class runs and displays the Connect Four Game
 * it contains the frame, menu, and the shortcut
 * keys information
 * 
 * @author Eileen Balci
 * @version 2.25.07
 */
public class ConnectFourDriver {

	//used to gain access to resetGame() and 
	//makeNewGame() methods from the
	//ConnectFour class
	private static ConnectFour game = new ConnectFour();
	
	/**
	 * Runs the Connect Four Game
	 */
	public static void main(String[] args) {
		
		JFrame window = new JFrame("CONNECT FOUR version 3.0");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.getContentPane().add(game);
		makeMenuBar(window);//adds the menu bar
		
		//makes it so users can't resize window
		window.setResizable(false);
		
		window.pack();
		window.setVisible(true);
	}
	
	/**
     * Create the main frame's menu bar.
     * which will hold menu options such as
     * Quit which will quit the program
     * and
     * New Game which will be another way to 
     * start a new game!
     * 
     * Taken from: 
     * 	Objects First with Java a Pratical Intro Using BlueJ
     *  Authors: Michael Kolling and David J Barnes 
     *  Modified by: Eileen Balci
     */
    private static void makeMenuBar(JFrame frame)
    {
        final int SHORTCUT_MASK =
            Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();

        JMenuBar menubar = new JMenuBar();
        frame.setJMenuBar(menubar);
        
        JMenu menu;
        JMenuItem item;
        
        // create the File menu
        menu = new JMenu("File");
        menubar.add(menu);
        
        //will make a new game when user selects it
        //or if user uses the shortcut ctr+N
        item = new JMenuItem("New Game");
        	item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, SHORTCUT_MASK));
        	item.addActionListener(new ActionListener() {
                           	   public void actionPerformed(ActionEvent e) 
                               { 
                        	      game.makeNewGame("Start a New Game?"); 
                               }
                           });
        menu.add(item);//adds new game to menu
        
        //will reset the game when user selects it
        //or if user uses the shortcut ctr+R
        item = new JMenuItem("Reset");
        	item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, SHORTCUT_MASK));
        	item.addActionListener(new ActionListener() {
                           	   public void actionPerformed(ActionEvent e) 
                               { 
                        	      game.resetGame("Are you sure you want to Reset? " +
                        	    		         "This will erase all data."); 
                               }
                           });
        menu.add(item);//adds reset to menu
        
        menu.addSeparator();//separates the new game and reset options from quit
        
        //will allow user to quit but selecting it from the menu
        //or by useing the shortcut ctr+Q
        item = new JMenuItem("Quit");
            item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, SHORTCUT_MASK));
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) 
                               { 
                            	   quit(); 
                               }
                           });
        menu.add(item);
        
        //create the File menu
        menu = new JMenu("Edit");
        menubar.add(menu);
        
        //will allow users to change their names again
        //or if user uses the shortcut ctr+u
        item = new JMenuItem("Change Names");
        	item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, SHORTCUT_MASK));
        	item.addActionListener(new ActionListener() {
                           	   public void actionPerformed(ActionEvent e) 
                               { 
                           		   int choice = JOptionPane.showConfirmDialog(null, 
                           				   "This will reset the game, is that okay?");
                           		   if(choice == 0){
                           			    game.resetGame("Resetting will erase all data." +
                           			    		       " Continue?");
                           			    //let player two select their name first
                           			   	game.p2SelectName();
                           		   		game.p1SelectName();
                           		   		//check if they are the same or not
                           		   		game.checkUserNames();
                           		   }
                               }
                           });
        menu.add(item);//adds change names to menu
        
        //will change background color when user selects it
        //or if user uses the shortcut ctr+F
        item = new JMenuItem("Change Background Color");
        	item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, SHORTCUT_MASK));
        	item.addActionListener(new ActionListener() {
                           	   public void actionPerformed(ActionEvent e) 
                               { 
                        	      String input = JOptionPane.showInputDialog(null, 
                        	    		  "Type in one of the following" +
                        	    		  " colors to change the background color:\n orange," +
                        	    		  " red, green, blue, dark gray, black, or magenta");
                        	      if(input.equals("orange")){
                        	    	  game.changeBGColor(Color.orange); 
                        	    	  game.getWhosTurnLabel().setForeground(Color.red);
                        	    	  game.getScore1().setForeground(Color.black);
                          			  game.getMatchScore1().setForeground(Color.black);
                          			  game.getScore2().setForeground(Color.black);
                          			  game.getMatchScore2().setForeground(Color.black);
                        	      }
                        	      else if(input.equals("red")){
                        	    	  game.changeBGColor(Color.red); 
                        	    	  game.getWhosTurnLabel().setForeground(Color.darkGray);
                        	    	  game.getScore1().setForeground(Color.lightGray);
                          			  game.getMatchScore1().setForeground(Color.lightGray);
                          			  game.getScore2().setForeground(Color.lightGray);
                          			  game.getMatchScore2().setForeground(Color.lightGray);
                        	      }
                        	      else if(input.equals("green")){
                        	    	  game.changeBGColor(Color.green); 
                        	    	  game.getWhosTurnLabel().setForeground(Color.red);
                        	    	  game.getScore1().setForeground(Color.darkGray);
                          			  game.getMatchScore1().setForeground(Color.darkGray);
                          			  game.getScore2().setForeground(Color.darkGray);
                          			  game.getMatchScore2().setForeground(Color.darkGray);
                        	      }
                        	      else if(input.equals("blue")){
                        	    	  game.changeBGColor(Color.blue);
                        	    	  game.getWhosTurnLabel().setForeground(Color.red);
                        	    	  game.getScore1().setForeground(Color.yellow);
                          			  game.getMatchScore1().setForeground(Color.yellow);
                          			  game.getScore2().setForeground(Color.yellow);
                          			  game.getMatchScore2().setForeground(Color.yellow);
                        	      }
                        	      else if(input.equals("dark gray")){
                        	    	  game.changeBGColor(Color.darkGray); 
                        	    	  game.getWhosTurnLabel().setForeground(Color.red);
                        	    	  game.getScore1().setForeground(Color.orange);
                          			  game.getMatchScore1().setForeground(Color.orange);
                          			  game.getScore2().setForeground(Color.orange);
                          			  game.getMatchScore2().setForeground(Color.orange);
                        	      }
                        	      else if(input.equals("magenta")){
                        	    	  game.changeBGColor(Color.magenta);
                        	    	  game.getWhosTurnLabel().setForeground(Color.pink);
                        	    	  game.getScore1().setForeground(Color.cyan);
                          			  game.getMatchScore1().setForeground(Color.cyan);
                          			  game.getScore2().setForeground(Color.cyan);
                          			  game.getMatchScore2().setForeground(Color.cyan);
                        	      }
                        	      else if(input.equals("black")){
                        	    	  game.changeBGColor(Color.black); 
                        	    	  game.getWhosTurnLabel().setForeground(Color.red);
                        	    	  game.getScore1().setForeground(Color.orange);
                          			  game.getMatchScore1().setForeground(Color.orange);
                          			  game.getScore2().setForeground(Color.orange);
                          			  game.getMatchScore2().setForeground(Color.orange);
                        	      }
                        	      else{
                        	    	  JOptionPane.showMessageDialog(null, "Invalid Input!\n" +
                        	    			  " check to make sure you use all lower case letters" +
                        	    			  " and that you spelled the\n color correctly and that the" +
                        	    			  " color is from the list.\n\nThe background was not changed.");
                        	      }

                               }
                           });
        menu.add(item);//adds change background to menu

        // put a spacer into the menubar, so the next menu appears to the right
        menubar.add(Box.createHorizontalGlue());

        // create the Help menu
        menu = new JMenu("Help");
        menubar.add(menu);
        
        item = new JMenuItem("How To Play...");
            item.addActionListener(new ActionListener() {
                               public void actionPerformed(ActionEvent e) 
                               { 
                            	   showAbout(); 
                               }
                           });
        menu.add(item);
    }
    
    /**
     * Quit function: quit the application.
     */
    private static void quit()
    {
        System.exit(0);
    }
    
    /**
     * An explaination of the
     * Connect Four game and how to play.
     */
    private static void showAbout()
    {
        JOptionPane.showMessageDialog(null, 
                    "CONNECT FOUR\n" + 
                    "Each player takes turns placing " +
                    "a chip into a column.\n" + 
                    "The first one to get their chips" +
                    " FOUR in a row wins!\n" +
                    "You can have 4 in a row horizontally," +
                    " vertically, or diagnoally.\n\n" +
                    "Player One is GREEN and always goes first." +
                    " Player Two is ORANGE and always goes second.\n\n" +
                    "The \"Drop\" buttons are used to drop your chip" +
                    " in the column of your choice.\n" +
                    "The \"New Game\" button starts a new game" +
                    " but keeps track of your previous games" +
                    " that you have won.\n" +
                    "The \"RESET\" button resets everything!\n\n\n" +
                    "ENJOY THE GAME!");
        
        
    }
}