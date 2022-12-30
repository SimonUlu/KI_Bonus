package ki.jfour;

import java.util.List;
import java.util.Random;
import java.util.stream.*;
import java.util.function.*;

public class MyMCTSAI extends AI {

    @Override
    public void start(Board b) {
        // make Method find best move - new test y
        Player player = b.getCurrentPlayer();
        findBestMonteMove(b, player);
    }

    public Move makeRandomMove(Board b) {
        List<Move> possibleMoves = b.possibleMoves();
        Random random = new Random();
        return possibleMoves.get(random.nextInt(possibleMoves.size()));
    }

    public int getWidth(Board b) {
        Player[][] board = b.getState();
        int width = board[0].length;
        return width;
    }

    public int getBestValue(int[] array) {
        int bestValue = Integer.MIN_VALUE;
        int bestRow = -1;
        for(int i = 0; i < array.length; i++) {
            if (array[i] > bestValue && array[i] != 0) {
                bestValue = array[i];
                bestRow = i;
            }
        }
        //System.out.println(bestValue);
        return bestRow;
    }

    /// function that tries to find the best move by evaluating every possible move
    // refactor this function to possible Moves
    public Move findBestMonteMove(Board b, Player player) {

        // get Current Board State - 2
        Player[][] boardState = b.getState();

        // get Board Width
        int boardWidth = getWidth(b);
        setBestMove(makeRandomMove(b));
        List <Move> possibleMoves = b.possibleMoves();

        // create Array to check if column has already been searched and stop loop if already been searched
        //boolean[] checkCol = new boolean[boardWidth];
        int[] winsTotal = new int[boardWidth];
        Board beforeBoard = b;
        for (Move possibleMove: possibleMoves) {
            b = b.executeMove(possibleMove);
            //winsTotal = new int[boardWidth];
            for(int k = 0; k < 90; k++) {
                winsTotal[possibleMove.column] += playRandomGame(b, player);
            }
            b = beforeBoard;
            //int selectedRow = getBestValue(winsTotal);
            //setBestMove(new Move(selectedRow));
        }
        //System.out.println(Arrays.toString(winsTotal));
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
            if (step == 0) {
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





    // simple heurstic
    /*
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


    // keine Ahnung was das hier für ein zeug ist
    @Override
    public String getDescription() {
        return "Simon Unterlugauer";
    }
















}



