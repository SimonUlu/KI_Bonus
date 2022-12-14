package ki.jfour;

import java.util.*;

public class MyMinMaxAI extends AI{


    // init max depth && make max_depth ==

    // maybe also undo the move in function minmax -> still need to check what is the effect of this
    @Override
    public void start(Board b) {

        while (b.possibleMoves().size() > 0 && b.getWinner() == Player.NONE) {
            if (b.getCurrentPlayer() == Player.BLUE && b.getWinner() == Player.NONE) {
                Move randomMove = makeRandomMove(b);
                b = b.executeMove(randomMove);
                //System.out.println(b);
            }
            if (b.possibleMoves().size() == 0) {
                System.out.println("Draw");
                break;

            }
            if (b.getCurrentPlayer() == Player.RED && b.getWinner() == Player.NONE) {
                Move move = findBestMove(b);
                b = b.executeMove(move);
                //System.out.println(b);
            }
            if (b.getWinner() == Player.BLUE) {
                System.out.println(b.getWinner());
            }
            if (b.getWinner() == Player.RED) {
                System.out.println(b.getWinner());
            }

        }
    }


    /// function to simulate the opponent -- choose random move of possible game Moves
    public Move makeRandomMove(Board b) {
        List<Move> possibleMoves = b.possibleMoves();
        Random random = new Random();
        return possibleMoves.get(random.nextInt(possibleMoves.size()));
    }


    // function to evaluate the current board state
    public int evaluate(Board b) {
        if (b.getWinner() == Player.RED) {
            return +10;
        }
        if (b.getWinner() == Player.BLUE) {
            return -10;
        }
        return 0;
    }


    // make list with ratings that adds 10 when win and cuts 10 when losing
    public int minmax(Board b, int depth ,boolean isMax) {
        int score = evaluate(b);
        int alpha = 0;

        // if myPlayer has won return 10 if vs-player has won return -10
        if (score == 10) {
            return score;
        }

        if (score == -10) {
            return score;
        }

        if (b.possibleMoves().size() == 0) {
            return 0;
        }

        if(depth == 5) {
            return 0;
        }

        if (isMax) {
            // set value to -infinite
            alpha  = Integer.MIN_VALUE;

            // get Board State as array and loop over all possible cells and make move for every opportunity
            Player[][] boardState = b.getState();
            for (int i = 0; i < boardState.length; i++) {
                for (int j = 0; j < boardState[i].length; j++) {
                    if (boardState[i][j] == Player.NONE) {

                        // Board beforeBoard = b; /* ? not sure if beforeBoard state is neccessary ? */
                        b = b.executeMove(new Move(j)); // b = b after game move
                        alpha = Math.max(alpha, minmax(b, depth +1 ,!isMax));
                        //b = beforeBoard;
                    }
                }
            }
        } else {
            // set value to +infinite
            alpha = Integer.MAX_VALUE;
            // get Board State as array and loop over all possible cells
            Player[][] boardState = b.getState();
            for (int i = 0; i < boardState.length; i++) {
                for (int j = 0; j < boardState[i].length; j++) {
                    if (boardState[i][j] == Player.NONE) {
                        // Board beforeBoard = b; /* ? not sure if beforeBoard state is neccessary ? */
                        b = b.executeMove(new Move(j)); // b = b after game move
                        alpha = Math.min(alpha, minmax(b, depth +1 ,!isMax));
                        //undo the move
                        //b = beforeBoard;

                    }
                }
            }

        }
        return alpha-depth;
    }


    /// function that tries to find the best move by evaluating every possible move
    public Move findBestMove(Board b) {
        int bestVal = Integer.MIN_VALUE;
        int bestMoveRow = -1;
        Player[][] boardState = b.getState();



        // new loop only check columns not every single position
        // create array that has width of my board
        int boardWidth = 0;
        for (int i = 0; i < boardState[0].length; i++) {
            boardWidth += 1;
        }
        boolean[] checkCol = new boolean[boardWidth];
        Board initBoardState = b;
        // stop loop if column has already been checked
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[0].length; j++) {
                if (!checkCol[j] && boardState[i][j] == Player.NONE) {
                    b = b.executeMove(new Move(j));
                    int moveValue = minmax(b, 0, false);
                    //System.out.println(moveValue);
                    //System.out.println(j);

                    if (moveValue > bestVal) {
                        // place in right col (possibly change i to j) not to sure
                        //System.out.println("Change");
                        bestMoveRow = j;
                        bestVal = moveValue;
                    }
                    checkCol[j] = true;
                    // set back to initial board state
                    b = initBoardState;
                }
            }
        }
        /* For testing purpose -> when you want to check finishing abilities
        System.out.printf("The best move's value is %d: ", bestVal);
        System.out.println(); // only to break-line
        System.out.printf("The best move row is %d: ", bestMoveRow);
        System.out.println(); // only to break-line
        System.out.println(b.executeMove(new Move(bestMoveRow)));
        */
        return new Move(bestMoveRow);
    }




    // keine Ahnung was das hier für ein kack ist
    @Override
    public String getDescription() {
        return null;
    }


    public static void main(String[] args) {

        /*
        Board b = new Board(4, 6);
        b = b.executeMove(new Move(0));
        b = b.executeMove(new Move(0));
        b = b.executeMove(new Move(2));
        b = b.executeMove(new Move(1));
        b = b.executeMove(new Move(1));
        b = b.executeMove(new Move(2));
        System.out.println(b);

        Move move = new MyMinMaxAI().findBestMove(b);
        System.out.println(move);

        */
        int i = 0;

        while (i < 10) {
            Board b = new Board(5, 7);
            new MyMinMaxAI().start(b);
            i++;
        }
    }


}


/* Old Loop of function findNextBestMove
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
        }
         */
