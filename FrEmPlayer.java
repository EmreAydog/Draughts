package nl.tue.s2id90.group48;

import static java.lang.Integer.MAX_VALUE;
import static java.lang.Integer.MIN_VALUE;
import java.util.Collections;
import java.util.List;
import nl.tue.s2id90.draughts.DraughtsState;
import nl.tue.s2id90.draughts.player.DraughtsPlayer;
import org10x10.dam.game.Move;

/**
 * Implementation of the DraughtsPlayer interface.
 * @author huub
 */

public class FrEmPlayer  extends DraughtsPlayer{
    private int lastScore=0;
    int maxSearchDepth;
    
    /** boolean that indicates that the GUI asked the player to stop thinking. */
    private boolean stopped;

    public FrEmPlayer() {
        super("FrEmPlayer.png"); 
    }
        
    /** Tries to make alphabeta search stop. Search should be implemented such that it
     * throws an AIStoppedException when boolean stopped is set to true;
    **/
    @Override public void stop() {
       stopped = true; 
    }
    
    /** returns random valid move in state s, or null if no moves exist. */
    Move getRandomValidMove(DraughtsState s) {
        List<Move> moves = s.getMoves();
        Collections.shuffle(moves);
        System.out.println("a random move was taken");
        return moves.isEmpty()? null : moves.get(0);
    }
    
    @Override public Move getMove(DraughtsState s) {
        int maxDepth = 0;   // a counter that stores how deep the search has gone
        Move bestMove = s.getMoves().get(0);    // initialize bestMove to just be the first available move
        
        /**
         * Using Iterative deepening, search for the best move using alphabeta 
         * search and evaluating game states.
         */
        try {
            int currentDepth = 1; // counter that stores how deep the search currently is
            List<Move> moves = s.getMoves();
            
            // we keep iterating until we reach a depth of 200, or until we are forced to stop
            // N.B. testing reveals that we never come close to this depth of 200.
            while (currentDepth < 200) {
                bestMove = getBestMove(s, currentDepth, moves);
                maxDepth = currentDepth;
                currentDepth++;
            }
          
        } catch (AIStoppedException ex) {  /* nothing to do */  }
        
        
        System.out.println("Reached depth: " + maxDepth); // Debugging reasons
        
        // if we somehow end up with a null move, do any valid move
        if (bestMove == null) {
            bestMove = getRandomValidMove(s); 
        }
        // else return the best move we have found
        return bestMove;
    } 

    /** This method's return value is displayed in the AICompetition GUI.
     * 
     * @return the value for the draughts state s as it is computed in a call to getMove(s). 
     */
    @Override public Integer getValue() { 
       return lastScore;
    }
    
    Move getBestMove(DraughtsState s, int maxDepth, List<Move> moves) throws AIStoppedException {
        Move bestMove = null;
        int bestScore = 0;  // an integer that stores what is, up until then, the highest score
        
        if (s.isWhiteToMove()) {
            // in this if-statement we maximize the score for the search
            int maxScore = Integer.MIN_VALUE;   // hence we start with a value as low as possible
            
            for (Move move : moves) {
                s.doMove(move);
                
                // now we go to a minimizing node
                int score = alphaBeta(new DraughtsNode(s), maxDepth - 1, maxScore, Integer.MAX_VALUE);
                
                // found a more favorable score? set both the score and the bestMove to reflect that
                if (score > maxScore) {
                    maxScore = score;
                    bestMove = move;
                    bestScore = maxScore;
                }
                
                s.undoMove(move);
            }
                    
        } else {
            // the minimizing player
            int minScore = Integer.MAX_VALUE; // hence we start with a value as big as possible
            
            for (Move move : moves) {
                s.doMove(move);
                
                // now we go to a maximizing node
                int score = alphaBeta(new DraughtsNode(s), maxDepth - 1, Integer.MIN_VALUE, minScore);
                
                // found a more favourable score? set both the score and the bestMove to reflect that
                if (score < minScore) {
                    minScore = score;
                    bestMove = move;
                    bestScore = minScore;
                }
                
                s.undoMove(move);
            }
        }
        lastScore = bestScore;  // store bestScore in the lastScore variable for retrieval via the getValue() method
        
        return bestMove;    // finally return the bestMove itself
    }
    
    /** Implementation of alphabeta that automatically chooses the white player
     *  as maximizing player and the black player as minimizing player.
     * @param node contains DraughtsState and has field to which the best move can be assigned.
     * @param alpha
     * @param beta
     * @param depth maximum recursion Depth
     * @return the computed value of this node
     * @throws AIStoppedException
     **/
    int alphaBeta(DraughtsNode node, int remainingDepth, int alpha, int beta)
            throws AIStoppedException
    {
        DraughtsState s = node.getState();
        
        // stop the search if we are in a leaf or if we are through with our depth and
        // continue by evaluating the gamestate 
        if(remainingDepth == 0 || s.isEndState()) {
            return Evaluate.evaluate(s);
        }
        
        // stop the search if the GUI asks us to
        if (stopped) {
            stopped = false;
            throw new AIStoppedException();
        }
        
        List<Move> moves = s.getMoves();
        
        // maximizing player
        if(s.isWhiteToMove()) {
            for (Move move : moves) {
                s.doMove(move);                             
                alpha = Math.max(alpha, alphaBeta(new DraughtsNode(s), remainingDepth - 1, alpha, beta));
                s.undoMove(move);
                
                // alphabeta pruning
                if(beta <= alpha) {
                    break;
                }
            }
            return alpha;
        
        // minimizing player
        } else {
            for (Move move : moves) {
                s.doMove(move);
                beta = Math.min(beta, alphaBeta(new DraughtsNode(s), remainingDepth - 1, alpha, beta));
                s.undoMove(move);
                
                // alphabeta pruning
                if (beta <= alpha) {
                    break;
                }
            }
            return beta;
        }
      
    }


}