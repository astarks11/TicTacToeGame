package model;

import java.awt.Point;
import java.util.Random;

/**
 * This TTT strategy tries to prevent the opponent from winning by checking
 * for a space where the opponent is about to win. If none found, it randomly
 * picks a place to win, which an sometimes be a win even if not really trying.
 * 
 * @author mercer
 * @modifier Alex
 */
public class StopperAI implements TicTacToeStrategy {
  
  /*----------------------------------------------
   * Method: desiredMove()
   * Purpose: This determines the move that the AI will take. This
   * is done by a simple algorithm of checking for a win, checking to block,
   * looking to strategize based on player input
   * ---------------------------------------------- */
  @Override
  public Point desiredMove(TicTacToeGame theGame) {
    
    // look for win
    int winHere = lookWin(theGame);
    if (winHere != -1)
      return move(winHere);
    
    // look to block
    int blockHere = blockWin(theGame);   
    
    // If the AI can block, return block
    if (blockHere != 0 && blockHere != 10)
      return move(blockHere);
    
    
    // strat yo
    // If no block or win is possible, pick a move from those still available
    return strategy(theGame);
  }
  
  /*-------------------------------------------
   * Method: strategy()
   * Purpose: This method determines an appropriate move for the
   * AI to beat depending on the plays already made on the board
   * ---------------------------------------------*/
  private Point strategy(TicTacToeGame theGame) {
    
    // stragety1 (player one starts in corner)
    if (theGame.maxMovesRemaining() == 8 && (theGame.isX(1) || theGame.isX(3) || theGame.isX(7) || theGame.isX(9)))
    {
      return move(5);
    }
    
    // strat 2 (player starts in middle)
    if (theGame.maxMovesRemaining() == 8 && theGame.isX(5))
    {
      return move(1);
    }
    
    // strat 3.1 (player starts on edge 2)
    if (theGame.maxMovesRemaining() == 8 && theGame.isX(2))
      return move(3);
    // strat 3.2 (player starts on edge 6)
    if (theGame.maxMovesRemaining() == 8 && theGame.isX(6))
      return move(9);
    // strat 3.3 (player starts on edge 8)
    if (theGame.maxMovesRemaining() == 8 && theGame.isX(8))
      return move(7);
    // strat 3.4 (player starts on edge 4)
    if (theGame.maxMovesRemaining() == 8 && theGame.isX(4))
      return move(1);
    
    // strat 4 (player 1 chooses two corners)
    if (theGame.maxMovesRemaining() == 6 && ((theGame.isX(1) && theGame.isX(9)) || (theGame.isX(7) && theGame.isX(3))))
    {
      return move(8);
    }
    
    // strat 5 (random)
    return randomMove(theGame);
  }
  
  
  private Point randomMove(TicTacToeGame theGame)
  {
    Random rand = new Random();
    int r = rand.nextInt(theGame.size());
    int c = rand.nextInt(theGame.size());
    
    while (!theGame.available(r, c))
    {
      r = rand.nextInt(theGame.size());
      c = rand.nextInt(theGame.size());
    }
    
    return new Point(r,c);
  }
  
  /*
   * Method: move()
   * Purpose: This method is used to return a point on a tic tac toe board.
   * A position is entered as an argument and a point corresponding to that position
   * is returned.
   */
  private Point move(int move) 
  {
    if (move == 1)
      return new Point(0,0);
    if (move == 2)
      return new Point(0,1);
    if (move == 3)
      return new Point(0,2);
    if (move == 4)
      return new Point(1,0);
    if (move == 5)
      return new Point(1,1);
    if (move == 6)
      return new Point(1,2);
    if (move == 7)
      return new Point(2,0);
    if (move == 8)
      return new Point(2,1);
    else
      return new Point(2,2);
  }
  
  /*
   * Method: blockWin
   * Purpose: This method takes a tic tac toe game object and
   * determines if the X character has a potential move to get three
   * in a row. There are 8 ways to win in tic tac toe and each of those
   * have 3 variations where two positions are in place to win.
   * 
   * NOTE: 3 return situations 
   *  1. if there is no potential move for x to win, 0 is returned. 
   *  2. if there is one potential threat for x to win, the position of that winning spot is returned
   *  3. if there is more that one potential threat for x to win, 10 is returned
   */
  private int blockWin(TicTacToeGame g) 
  { 
    int potentialWin = 0; // number of potential wins by X
    int blockPosition = -1;
    
    // ---------- 1 4 7 ---------------------
    if (g.isX(1) && g.isX(4))
    {
      if (g.available(2,0))
      {
        blockPosition = 7;
        potentialWin++;
      }
    }
    if (g.isX(1) && g.isX(7))
    {
      if (g.available(1,0))
      {
        blockPosition = 4;
        potentialWin++;
      }
    }
    if (g.isX(7) && g.isX(4))
    {
      if (g.available(0,0))
      {
        blockPosition = 1;
        potentialWin++;
      }
    }
    // ---------- 1 4 7 ---------------------
    
    // ---------- 2 5 8 ---------------------
    if (g.isX(2) && g.isX(5))
    {
      if (g.available(2,1))
      {
        blockPosition = 8;
        potentialWin++;
      }
    }
    if (g.isX(2) && g.isX(8))
    {
      if (g.available(1,1))
      {
        blockPosition = 5;
        potentialWin++;
      }
    }
    if (g.isX(5) && g.isX(8))
    {
      if (g.available(0,1))
      {
        blockPosition = 2;
        potentialWin++;
      }
    }
    // ---------- 2 5 8 ---------------------
    
    // ---------- 3 6 9 ---------------------
    if (g.isX(3) && g.isX(6))
    {
      if (g.available(2,2))
      {
        blockPosition = 9;
        potentialWin++;
      }
    }
    if (g.isX(3) && g.isX(9))
    {
      if (g.available(1,2))
      {
        blockPosition = 6;
        potentialWin++;
      }
    }
    if (g.isX(6) && g.isX(9))
    {
      if (g.available(0,2))
      {
        blockPosition = 3;
        potentialWin++;
      }
    }
    // ---------- 3 6 9 ---------------------
    
    // ---------- 1 5 9 ---------------------
    if (g.isX(1) && g.isX(5))
    {
      if (g.available(2,2))
      {
        blockPosition = 9;
        potentialWin++;
      }
    }
    if (g.isX(1) && g.isX(9))
    {
      if (g.available(1,1))
      {
        blockPosition = 5;
        potentialWin++;
      }
    }
    if (g.isX(5) && g.isX(9))
    {
      if (g.available(0,0))
      {
        blockPosition = 1;
        potentialWin++;
      }
    }
    // ---------- 1 5 9 ---------------------
    
    // ---------- 7 8 9 ---------------------
    if (g.isX(7) && g.isX(8))
    {
      if (g.available(2,2))
      {
        blockPosition = 9;
        potentialWin++;
      }
    }
    if (g.isX(7) && g.isX(9))
    {
      if (g.available(2,1))
      {
        blockPosition = 8;
        potentialWin++;
      }
    }
    if (g.isX(8) && g.isX(9))
    {
      if (g.available(2,0))
      {
        blockPosition = 7;
        potentialWin++;
      }
    }
    // ---------- 7 8 9 ---------------------
    
    // ---------- 4 5 6 ---------------------
    if (g.isX(4) && g.isX(5))
    {
      if (g.available(1,2))
      {
        blockPosition = 6;
        potentialWin++;
      }
    }
    if (g.isX(4) && g.isX(6))
    {
      if (g.available(1,1))
      {
        blockPosition = 5;
        potentialWin++;
      }
    }
    if (g.isX(5) && g.isX(6))
    {
      if (g.available(1,0))
      {
        blockPosition = 4;
        potentialWin++;
      }
    }
    // ---------- 4 5 6 ---------------------  
    
    // ---------- 1 2 3 ---------------------
    if (g.isX(1) && g.isX(2))
    {
      if (g.available(0,2))
      {
        blockPosition = 3;
        potentialWin++;
      }
    }
    if (g.isX(1) && g.isX(3))
    {
      if (g.available(0,1))
      {
        blockPosition = 2;
        potentialWin++;
      }
    }
    if (g.isX(2) && g.isX(3))
    {
      if (g.available(0,0))
      {
        blockPosition = 1;
        potentialWin++;
      }
    }
    // ---------- 1 2 3 ---------------------
    
    // ---------- 7 5 3 ---------------------
    if (g.isX(7) && g.isX(5))
    {
      if (g.available(0,2))
      {
        blockPosition = 3;
        potentialWin++;
      }
    }
    if (g.isX(7) && g.isX(3))
    {
      if (g.available(1,1))
      {
        blockPosition = 5;
        potentialWin++;
      }
    }
    if (g.isX(5) && g.isX(3))
    {
      if (g.available(2,0))
      {
        blockPosition = 7;
        potentialWin++;
      }
    }
    // ---------- 7 5 3 ---------------------
    
    if (potentialWin > 1)
      return 10;
    if (blockPosition != -1)
      return blockPosition;
    
    return 0;
  }
  
  
  /*
   * Method: lookWin()
   * Purpose: This method determines if the O character can win
   * at the tictactoegame object. This happens if three O's align 
   * on the board. This method determines if it is possible and if so at
   * what location it is possible to win. There are 8 ways to win tictactoe 
   * and each way has 3 variations where 2 align and a winning spot is available.
   * 
   * Notes: There are 2 return situations:
   *  1. if a winning situation is found, return the winning position
   *  2. if there are no winning situations, return -1
   */
  private int lookWin(TicTacToeGame g) 
  { 
    int blockPosition = -1;
    
    // ---------- 1 4 7 ---------------------
    if (g.isO(1) && g.isO(4))
    {
      if (g.available(2,0))
        blockPosition = 7;
    }
    if (g.isO(1) && g.isO(7))
    {
      if (g.available(1,0))
        blockPosition = 4;
      
    }
    if (g.isO(7) && g.isO(4))
    {
      if (g.available(0,0))
        blockPosition = 1;
    }
    // ---------- 1 4 7 ---------------------
    
    // ---------- 2 5 8 ---------------------
    if (g.isO(2) && g.isO(5))
    {
      if (g.available(2,1))
        blockPosition = 8;
    }
    if (g.isO(2) && g.isO(8))
    {
      if (g.available(1,1))
        blockPosition = 5;
    }
    if (g.isO(5) && g.isO(8))
    {
      if (g.available(0,1))
        blockPosition = 2;
    }
    // ---------- 2 5 8 ---------------------
    
    // ---------- 3 6 9 ---------------------
    if (g.isO(3) && g.isO(6))
    {
      if (g.available(2,2))
        blockPosition = 9;
    }
    if (g.isO(3) && g.isO(9))
    {
      if (g.available(1,2))
        blockPosition = 6;
    }
    if (g.isO(6) && g.isO(9))
    {
      if (g.available(0,2))
        blockPosition = 3;
    }
    // ---------- 3 6 9 ---------------------
    
    // ---------- 1 5 9 ---------------------
    if (g.isO(1) && g.isO(5))
    {
      if (g.available(2,2))
        blockPosition = 9;
    }
    if (g.isO(1) && g.isO(9))
    {
      if (g.available(1,1))
        blockPosition = 5;
    }
    if (g.isO(5) && g.isO(9))
    {
      if (g.available(0,0))
        blockPosition = 1;
    }
    // ---------- 1 5 9 ---------------------
    
    // ---------- 7 8 9 ---------------------
    if (g.isO(7) && g.isO(8))
    {
      if (g.available(2,2))
        blockPosition = 9;
    }
    if (g.isO(7) && g.isO(9))
    {
      if (g.available(2,1))
        blockPosition = 8;
    }
    if (g.isO(8) && g.isO(9))
    {
      if (g.available(2,0))
        blockPosition = 7;
    }
    // ---------- 7 8 9 ---------------------
    
    // ---------- 4 5 6 ---------------------
    if (g.isO(4) && g.isO(5))
    {
      if (g.available(1,2))
        blockPosition = 6;
    }
    if (g.isO(4) && g.isO(6))
    {
      if (g.available(1,1))
        blockPosition = 5;
    }
    if (g.isO(5) && g.isO(6))
    {
      if (g.available(1,0))
        blockPosition = 4;
    }
    // ---------- 4 5 6 ---------------------  
    
    // ---------- 1 2 3 ---------------------
    if (g.isO(1) && g.isO(2))
    {
      if (g.available(0,2))
        blockPosition = 3;
    }
    if (g.isO(1) && g.isO(3))
    {
      if (g.available(0,1))
        blockPosition = 2;
    }
    if (g.isO(2) && g.isO(3))
    {
      if (g.available(0,0))
        blockPosition = 1;
    }
    // ---------- 1 2 3 ---------------------
    
    // ---------- 7 5 3 ---------------------
    if (g.isO(7) && g.isO(5))
    {
      if (g.available(0,2))
        blockPosition = 3;
    }
    if (g.isO(7) && g.isO(3))
    {
      if (g.available(1,1))
        blockPosition = 5;
    }
    if (g.isO(5) && g.isO(3))
    {
      if (g.available(2,0))
        blockPosition = 7;
    }
    // ---------- 7 5 3 ---------------------
    
    return blockPosition;
  }
  
  
}