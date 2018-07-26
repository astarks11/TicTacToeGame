package model;

import java.awt.Point;
import java.util.Random;

/**
 * This strategy selects the first available move at random.  It is easy to beat
 * 
 * @throws IGotNowhereToGoException whenever asked for a move that is impossible to deliver
 * 
 * @author mercer
 * @modifier Alex
 */
public class RandomAI implements TicTacToeStrategy {
  
  // Randomly find an open spot while ignoring possible wins and stops.
  // This should be easy to beat as a human. 
  
  /*---------------------------------------------
   * Method: desiredMove
   * Purpose: chooses a random number within the game boards size and
   * determine if it is taken, if it is generate new random, if not play move
   * at that location
   * ----------------------------------------------*/
  @Override
  public Point desiredMove(TicTacToeGame theGame)  {
    
    // check for invalid move
    if (theGame.maxMovesRemaining() == 0)
      throw new IGotNowhereToGoException("Invalid Move");
    
    Random rand = new Random();
    int r = rand.nextInt(theGame.size());
    int c = rand.nextInt(theGame.size());
    
    // generate new number until finding one that is available
    while (!theGame.available(r, c))
    {
      r = rand.nextInt(theGame.size());
      c = rand.nextInt(theGame.size());
    }
    
    return new Point(r,c);
  }
}