package ki.jfour;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Board b = new Board(10, 10);

        // new test
        while (b.possibleMoves().size() > 0 && b.getWinner() == Player.NONE) {

            if (b.getWinner() != Player.NONE) {
                System.out.println(b.getWinner());
            }

            //System.out.println("Min Maxs Turn");

            int gameMoveMinMax = new MyMinMaxAI().findBestMove(b, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true, Player.RED)[1];
            //int gameMoveMinMax = new TestMinMaxAI().findBestMove(b, 4, Integer.MIN_VALUE, Integer.MAX_VALUE, true, Player.RED)[1];
            Move gameMove = new Move(gameMoveMinMax);


            b = b.executeMove(gameMove);
            System.out.println(b);
            //System.out.println("Montes Turn");

            if (b.getWinner() != Player.NONE) {
                break;
            }

            Move gameMoveMCT = new MyMCTSAI().findBestMonteMove(b, Player.BLUE);
            b = b.executeMove(gameMoveMCT);
            System.out.println(b);
        }
        System.out.println(b.getWinner());
    }
}
