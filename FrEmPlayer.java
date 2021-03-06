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
    private int bestValue=0;
    int maxSearchDepth;
    
    /** boolean that indicates that the GUI asked the player to stop thinking. */
    private boolean stopped;

    public FrEmPlayer(int maxSearchDepth) {
        super("best.png"); // ToDo: replace with your own icon
        this.maxSearchDepth = maxSearchDepth;
    }
    
    @Override public Move getMove(DraughtsState s) {
        Move bestMove = null;
        bestValue = 0;
        
        DraughtsNode node = new DraughtsNode(s);    // the root of the search tree
        try {
            // compute bestMove and bestValue in a call to alphabeta
            bestValue = alphaBeta(node, MIN_VALUE, MAX_VALUE, maxSearchDepth);
            
            // store the bestMove found uptill now
            // NB this is not done in case of an AIStoppedException in alphaBeat()
            bestMove  = node.getBestMove();
            
            // print the results for debugging reasons
            System.err.format(
                "%s: depth= %2d, best move = %5s, value=%d\n", 
                this.getClass().getSimpleName(),maxSearchDepth, bestMove, bestValue
            );
        } catch (AIStoppedException ex) {  /* nothing to do */  }
        
        if (bestMove==null) {
            System.err.println("no valid move found!");
            return getRandomValidMove(s);
        } else {
            return bestMove;
        }
    } 

    /** This method's return value is displayed in the AICompetition GUI.
     * 
     * @return the value for the draughts state s as it is computed in a call to getMove(s). 
     */
    @Override public Integer getValue() { 
       return bestValue;
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
        return moves.isEmpty()? null : moves.get(0);
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
    int alphaBeta(DraughtsNode node, int alpha, int beta, int depth)
            throws AIStoppedException
    {
        // if the stop method has been called, invoke this if-statement
        if (stopped) {
            stopped = false;
            throw new AIStoppedException();
        }
        
        if (node.getState().isWhiteToMove()) {
            
            // Maximizing player, hence we call alphaBetaMax in this loop
            return alphaBetaMax(node, alpha, beta, depth);
            
        } else  {
            
            //Minimizing player, hence we call alphabetaMin in this loop
            return alphaBetaMin(node, alpha, beta, depth);
        }
    }
    
    /** Does an alphabeta computation with the given alpha and beta
     * where the player that is to move in node is the minimizing player.
     * 
     * <p>Typical pieces of code used in this method are:
     *     <ul> <li><code>DraughtsState state = node.getState()</code>.</li>
     *          <li><code> state.doMove(move); .... ; state.undoMove(move);</code></li>
     *          <li><code>node.setBestMove(bestMove);</code></li>
     *          <li><code>if(stopped) { stopped=false; throw new AIStoppedException(); }</code></li>
     *     </ul>
     * </p>
     * @param node contains DraughtsState and has field to which the best move can be assigned.
     * @param alpha
     * @param beta
     * @param depth  maximum recursion Depth
     * @return the compute value of this node
     * @throws AIStoppedException thrown whenever the boolean stopped has been set to true.
     */
     int alphaBetaMin(DraughtsNode node, int alpha, int beta, int depth)
            throws AIStoppedException {
         
        // if the stop method has been called, invoke this if-statement
        if (stopped) { 
            stopped = false; 
            throw new AIStoppedException(); 
        }
        
        DraughtsState state = node.getState();
        
        /** 
         * Have we reached our maximum depth or are we in a leaf? Then evaluate
         * the GameState and return its associated value.
         */
        if (depth <= 0 || state.isEndState()) {
            return Evaluate.evaluate(state);
        }
        
        for (Move move : state.getMoves()) {
            state.doMove(move);
            
            /**
             * Part of the recursive call. 
             * Here we move to the maximizing player while also decrementing
             * the search depth by 1 to indicate that we have moved through a
             * node in the tree. 
             */
            int temp = alphaBetaMax(node, alpha, beta, --depth);
            
            // replace beta if we have found a smaller value for beta
            if (temp < beta) {
                beta = temp;
            }
            
            state.undoMove(move);
            
            // alpha beta pruning, return alpha if subtree can be discarded
            if (beta <= alpha) {
                return alpha;
            }

        }
        Move bestMove = state.getMoves().get(0);
        node.setBestMove(bestMove);
        return beta;
     }
    
    int alphaBetaMax(DraughtsNode node, int alpha, int beta, int depth)
            throws AIStoppedException {
        
        // if the stop method has been called, invoke this if-statement
        if (stopped) { 
            stopped = false; 
            throw new AIStoppedException(); 
        }
        
        DraughtsState state = node.getState();
        
         /** 
         * Have we reached our maximum depth or are we in a leaf? Then evaluate
         * the GameState and return its associated value.
         */
        if (depth <= 0 || state.isEndState()) {
            return Evaluate.evaluate(state);
        }
        
        for (Move move: state.getMoves()) {
            state.doMove(move);
            
            /**
             * Part of the recursive call. 
             * Here we move to the minimizing player while also decrementing
             * the search depth by 1 to indicate that we have moved through a
             * node in the tree. 
             */
            int temp = alphaBetaMin(node, alpha, beta, --depth);
            
            // replace alpha if we have found a larger value for alpha
            if (temp > alpha) {
                alpha = temp;
            }
            
            state.undoMove(move);
            
            // alpha beta pruning, return beta if subtree can be discarded
            if (alpha >= beta) {
                return beta;
            }
            
        }
        Move bestMove = state.getMoves().get(0);
        node.setBestMove(bestMove);
        return alpha;
    }

}