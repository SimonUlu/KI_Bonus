package ki.jfour;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class MyMCTSAI extends AI {


    // init max depth && make max_depth ==

    // maybe also undo the move in function minmax -> still need to check what is the effect of this
    @Override
    public void start(Board b) {
        // make Method find best move
        Player player = b.getCurrentPlayer();
        findBestMonteMove(b, player);
    }


    /// function to simulate the opponent -- choose random move of possible game Moves

    public Move makeRandomMove(Board b) {
        List<Move> possibleMoves = b.possibleMoves();
        Random random = new Random();
        return possibleMoves.get(random.nextInt(possibleMoves.size()));
    }





    public int getBestValue(int[] array) {
        int bestValue = 0;
        int bestRow = 0;
        for(int i = 0; i < array.length; i++) {
            if (array[i] > bestValue) {
                bestValue = array[i];
                bestRow = i;
            }
        }
        //System.out.println(bestValue);
        return bestRow;
    }


    /// function that tries to find the best move by evaluating every possible move
    public Move findBestMonteMove(Board b, Player player) {

        // get Current Board State
        Player[][] boardState = b.getState();

        // get Board Width
        int boardWidth = 0;
        for (int i = 0; i < boardState[0].length; i++) {
            boardWidth += 1;
        }
        setBestMove(new Move(boardWidth/2));

        // create Array to check if column has already been searched and stop loop if already been searched
        boolean[] checkCol = new boolean[boardWidth];
        int[] winsTotal = new int[boardWidth];
        Board beforeBoard = b;
        for (int i = 0; i < boardState.length; i++) {
            for (int j = 0; j < boardState[0].length; j++) {
                if (!checkCol[j] && boardState[i][j] == Player.NONE) { // if not only thrown in col && field = empty
                    b = b.executeMove(new Move(j));
                    //winsTotal = new int[boardWidth];
                    for(int k = 0; k < 100; k++) {
                        winsTotal[j] += playRandomGame(b, player);
                    }
                    checkCol[j] = true;
                    b = beforeBoard;
                }

            }
        }
        int selectedRow = getBestValue(winsTotal);
        //System.out.println(Arrays.toString(winsTotal));
        setBestMove(new Move(selectedRow));
        return new Move(selectedRow);
    }

    public int playRandomGame(Board b, Player player) {

        int step = 0;
        while(b.getWinner() == Player.NONE && b.possibleMoves().size() > 0) {
            b = b.executeMove(makeRandomMove(b));
            step += 1;
        }
        return evaluate(b, step, player);
    }



    /// not so simple heuristic
    public int evaluate(Board b, int step, Player player) {

        Player opponent = Player.RED;
        //System.out.println(b.getCurrentPlayer());
        if (player == Player.RED) {
            opponent = Player.BLUE;
        }

        if (b.getWinner() == player) {
            if (step == 1) {
                //System.out.println("Instant win");
                return 10000;
            }
            return Math.max(100 - (step*20), 20);
        }
        if (b.getWinner() == opponent) {
            if (step == 1) {
                //System.out.println("Instant loss");
                return -10000;
            }
            return Math.min(-100 + (step*20), -20);
        }
        return 0;
    }


    /*
    // simple heurstic
    public int evaluate(Board b, int step, Player player) {
        Player opponent = Player.RED;
        //System.out.println(b.getCurrentPlayer());
        if (player == Player.RED) {
            opponent = Player.BLUE;
        }
        if (b.getWinner() == player) {
            return 50;
        }
        if (b.getWinner() == opponent) {
            return -50;
        }
        return 0;
    }
    */







    // keine Ahnung was das hier für ein kack ist
    @Override
    public String getDescription() {
        return "Simon Unterlugauer";
    }











}



