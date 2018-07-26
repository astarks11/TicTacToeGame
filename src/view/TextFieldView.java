package view;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.OurObserver;
import model.ComputerPlayer;
import model.TicTacToeGame;
/*------------------------------------------------------
 * Class: TextAreaView
 * Author: Alex Starks
 * 
 * Purpose: This class contains the method necessary to create a secondary
 * view to the TicTacToeGame. The initial view is the ButtonView. The class
 * utilizes JPanel and various JFrame objects to create a dynamic game board.
 * The game board size is not adjustable and is based of the initial size from ButtonView.java
 * 
 * NOTES: This class extends JPanel and implements OurObserver
 * -----------------------------------------------------*/
public class TextFieldView extends JPanel implements OurObserver {
  
  private TicTacToeGame theGame; // a TicTacToeGame object used by the methods
  private JButton stateButton = new JButton("Click your move"); // jbutton used for submitting player input
  private JTextField[][] labels = null; // the game board
  private ComputerPlayer computerPlayer; // the computer AI object
  private int height, width; // height and width of the board
  private int row, column;  // row and column parsed from input by user
  private JTextField rowInput; // the input from user 
  private JTextField colInput; // the input from user
  private JLabel status; // a label for game wins/ties
  
  /*-------------------------------------
   * Constructor: TextAreaView
   * Purpose: This constructor is used to create the class object.
   * 
   * Parameters: a game object, the width of the JPanel and height of JPanel
   * -------------------------------------*/
  public TextFieldView(TicTacToeGame game, int width, int height)
  {
    theGame = game;
    this.height = height;
    this.width = width;
    computerPlayer = game.getComputerPlayer();
    initializeTextAreaPanel();
  } // textAreaView
  
  /*------------------------------------
   * Method: initializeTextAreaPanel
   * Purpose: This method is used to initialize the JPanel that
   * displays the game board, it consists of the panel, a couple text area fields
   * for input and output, as well as a button for player submittion
   * ------------------------------------*/
  private void initializeTextAreaPanel()
  {
    JPanel textPanel = new JPanel();
    int size = theGame.size();   
    this.setLayout(null);
    textPanel.setLocation(0,0);
    textPanel.setSize(width,height);
    textPanel.setBackground(Color.CYAN);
    this.add(textPanel);
    
    // add button
    textPanel.setLayout(null);
    MyButtonListener buttonListener = new MyButtonListener();
    stateButton.addActionListener(buttonListener);
    stateButton.setEnabled(true);
    stateButton.setSize(125,25);
    stateButton.setLocation(130,20);
    textPanel.add(stateButton);
    
    
    // create input panel
    JPanel inputPanel = new JPanel();
    inputPanel.setSize(100,55);
    inputPanel.setLayout(new GridLayout(2,2,10,5));
    inputPanel.setLocation(10,10);
    inputPanel.setBackground(Color.CYAN);
    textPanel.add(inputPanel);
    
    JLabel cLabel = new JLabel("row");
    JLabel fLabel = new JLabel("column");
    rowInput = new JTextField(1);
    colInput = new JTextField(1);
    
    inputPanel.add(rowInput);
    inputPanel.add(cLabel);
    inputPanel.add(colInput);
    inputPanel.add(fLabel);
    
    // create board
    JPanel board = new JPanel();
    board.setSize(175,175);
    board.setLocation(50,80);
    board.setLayout(new GridLayout(3,3,0,0));
    board.setBackground(Color.WHITE);
    labels = new JTextField[size][size];
    
    for (int i = 0; i < size; i++) {
      for (int j = 0; j < size; j++) {
        labels[i][j] = new JTextField("_");
        labels[i][j].setBorder(null);
        labels[i][j].setHorizontalAlignment(JTextField.CENTER);
        labels[i][j].setFont(new Font("Courier", Font.BOLD, 42));
        //labels[i][j].setLocation((int)labels[i][j].size().getWidth()/2,(int)labels[i][j].size().getHeight()/2);
        board.add(labels[i][j]);
      }
    }
    textPanel.add(board);
    
    // add status label
    status = new JLabel("");
    status.setSize(200, 40);
    status.setLocation(110, height - 105);
    status.setVisible(true);
    status.setFont(new Font("Arial", Font.BOLD, 18));
    status.setBackground(Color.WHITE);
    textPanel.add(status); 
  }
  
  /*--------------------------------------------------
   * Class: MyButtonListener
   * Author: Alex Starks
   * 
   * Purpose: This class utilizes ActionListener to crate my own
   * version of an action listener for my button. If a button
   * is clicked, setBoard() is called for processing
   * ---------------------------------------------------*/
  private class MyButtonListener implements ActionListener 
  {
    
    @Override
    public void actionPerformed(ActionEvent arg0)
    {
      JButton buttonClicked = (JButton) arg0.getSource();
      
      if (buttonClicked.isEnabled())
        setBoard();
    }
  }
  
  /*-------------------------------------
   * Method: setBoard()
   * Purpose: This method is used to modify the board
   * based of user input. Edge cases are checked and
   * proper exceptions are thrown if the user enters
   * bad input. If the input is correct, the game object
   * is told to choose the appropriate location and
   * an update is called.
   * ------------------------------------*/
  private void setBoard(){ 
    
    String rowString = rowInput.getText();
    String colString = colInput.getText();
    
    try {
      row = Integer.parseInt(rowString);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "Invalid move");
    }
    
    try {
      column = Integer.parseInt(colString);
    } catch (NumberFormatException e) {
      JOptionPane.showMessageDialog(null, "Invalid move");
    }
    
    if (row < 0 || column < 0 || row >= theGame.size() || column >= theGame.size())
    {
      JOptionPane.showMessageDialog(null, "Invalid move");
      return;
    }
    
    if (labels[row][column].getText().compareTo("_") != 0)
    {
      JOptionPane.showMessageDialog(null, "Move not available");
      return;
    }
    theGame.choose(row,column);
    
    if (theGame.tied())
    {
      stateButton.setText("Tied");
      status.setText("Tied");
      update();
    } else if (theGame.didWin('X')) {
      stateButton.setText("X Wins");
      status.setText("X Wins");
      update();   
    } else if (theGame.didWin('O')) {
      stateButton.setText("O");
      status.setText("O Wins");
      update();
    } else {
      // If the game is not over, let the computer player choose
      // This algorithm assumes the computer player always
      // goes after the human player and is represented by 'O', not 'X'
      Point play = computerPlayer.desiredMove(theGame);
      theGame.choose(play.x, play.y);
    }   
  }
  
  /*------------------------------------------------
   * Method: update()
   * Purpose: This method calls resetButtons and updateButtons to
   * modify the game board
   * 
   * NOTE: This method is called by OurObservable's notifyObservers()
   * ------------------------------------------------------------ */
  public void update() {
    
    if (theGame.maxMovesRemaining() == theGame.size() * theGame.size())
    {
      resetButtons();
    }
    
    if (!theGame.stillRunning())
    {
      updateButtons();
      stateButton.setEnabled(false);
    }
    else {
      updateButtons();
      stateButton.setText("Click your move");
    }
  }
  
  
  /*---------------------------------
   * Method: resetButtons()
   * Purpose: This method resets the game board to
   * its origional position by setting the text within
   * every label[][] to "_"
   * ----------------------------------*/
  private void resetButtons() {
    for (int i = 0; i < theGame.size(); i++) {
      for (int j = 0; j < theGame.size(); j++) {
        labels[i][j].setText("_");
      }
    }
    rowInput.setText("");
    colInput.setText("");
    status.setText("");
    stateButton.setEnabled(true);
  }
  
  
  /*------------------------------------------
   * Method: updateButtons()
   * Purpose: This method updates the buttons to values 
   * containde by the char[][] in theGame object
   * ------------------------------------------*/
  public void updateButtons() {
    char[][] temp = theGame.getTicTacToeBoard();
    for (int i = 0; i < temp.length; i++) {
      for (int j = 0; j < temp[i].length; j++) {
        String text = "" + temp[i][j];
        if (text.equals("X") || text.equals("O")) {
          labels[i][j].setText(text);
        }
      }
    }
  }
}