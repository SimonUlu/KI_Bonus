package ki.jfour;

import java.util.*;

public class MyMinMaxAI extends AI{


    /// idea for optimizing right now checking every col/row --> super unneccessary because chip
    /// only be placed on top

    /// maybe change all i executions to j not to sure
    @Override
    public void start(Board b) {
        int n = 0;
        if (b.getCurrentPlayer() == Player.BLUE) {
            int random_col = (int) (Math.random()*6);
            b = b.executeMove(new Move(random_col));
        }
        System.out.println(b);
        while (b.possibleMoves().size() > 0 && b.getWinner() == Player.NONE) {
            List<Move> possibleMoves = b.possibleMoves();
            if (b.getCurrentPlayer() == Player.BLUE && b.getWinner() == Player.NONE) {
                int random_col = (int) (Math.random()*6);
                b = b.executeMove(new Move(random_col));
            }
            System.out.println(b);
            if (b.getCurrentPlayer() == Player.RED && b.getWinner() == Player.NONE) {
                Move move = findBestMove(b);
                b = b.executeMove(move);
            }
            if(b.getWinner() != Player.NONE) {
                System.out.println(b.getWinner());
            }
            System.out.println(b);
        }

    }

    // function to evaluate the current board state
    public int evaluate(Board b) {
        if (b.getWinner() == Player.RED) {
            //System.out.println("Better move");
            return +10;
        }
        if (b.getWinner() == Player.BLUE) {
            //System.out.println("Weaker move");
            return -10;
        }
        else {
            return 0;
        }
    }

    // make list with Bewertungen that adds 10 when win and cuts 10 when losing
    public int minmax(Board b, int depth ,boolean isMax) {
        int score = evaluate(b);
        int alpha = 0;
        // if myPlayer has won return 10 if vs-player has won return -10
        if (score == 10 || score == -10) {
            return score;
        }
        /*
        if (b.possibleMoves().size() == 0) {
            return score;
        }

         */

        if (isMax) {
            // set alpha and beta to min/max
            alpha  = Integer.MIN_VALUE;
            // save init state of board

            // get Board State as array and loop over all possible cells
            Player[][] boardState = b.getState();
            for (int i = 0; i < boardState.length; i++) {
                for (int j = 0; j < boardState[i].length; j++) {
                    if (boardState[i][j] == Player.NONE) {
                        Board beforeBoard = b;
                        b = b.executeMove(new Move(j)); // b = b after game move
                        // get best move possible
                        alpha = Math.max(alpha, minmax(b, depth +1 ,!isMax));
                        //b = beforeBoard;
                    }
                }
            }
        } else {
            // save init state of board

            // beta = + infinite
            alpha = Integer.MAX_VALUE;
            // get Board State as array and loop over all possible cells
            Player[][] boardState = b.getState();
            for (int i = 0; i < boardState.length; i++) {
                for (int j = 0; j < boardState[i].length; j++) {
                    if (boardState[i][j] == Player.NONE) {
                        Board beforeBoard = b;
                        b = b.executeMove(new Move(j)); // b = b after game move
                        // get best move possible
                        alpha = Math.min(alpha, minmax(b, depth +1 ,!isMax));
                        //undo the move
                        //b = beforeBoard;
                    }
                }
            }

        }
        return alpha;
    }


    public Move findBestMove(Board b) {
        int bestVal = Integer.MIN_VALUE;
        int bestMoveRow = -1;
        Player[][] boardState = b.getState();
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[i].length; j++) {
                if (boardState[i][j] == Player.NONE) {
                    Board startBoard = b;
                    // execute Move in col j
                    b = b.executeMove(new Move(j));
                    int moveValue = minmax(b, 0, true); /// is Max maybe switching
                    System.out.println("First Iteration finished");

                    //undo the move
                    //b = startBoard;

                    if (moveValue > bestVal) {
                        // place in right col (possibly change i to j) not to sure
                        //System.out.println("Change");
                        bestMoveRow = j;
                        bestVal = moveValue;
                    }
                }
            }
            //break;
        }
        System.out.printf("The best move's value is %d: ", bestVal);
        System.out.println();
        System.out.printf("The best move row is %d: ", bestMoveRow);
        System.out.println();
        return new Move(bestMoveRow);
    }




    @Override
    public String getDescription() {
        return null;
    }


    public static void main(String[] args) {

        Board b = new Board(4, 7);
        new MyMinMaxAI().start(b);

         /*
        Board b = new Board(4, 6);
        for (int i = 0; i < b.getState().length; i++) {
            System.out.println(Arrays.toString(b.getState()[i]));
        }
        */


    }


}
