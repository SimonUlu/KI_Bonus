package ki.jfour;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Board b = new Board(7, 8);


        while (b.possibleMoves().size() > 0 && b.getWinner() == Player.NONE) {

            if (b.getWinner() != Player.NONE) {
                System.out.println(b.getWinner());
            }

            int gameMoveMinMax = new TestMinMaxAI().findBestMove(b, 5, Integer.MIN_VALUE, Integer.MAX_VALUE, true, Player.RED)[1];
            Move gameMove = new Move(gameMoveMinMax);
            if (gameMoveMinMax < 0 ) {
                gameMove = null;
            }

            b = b.executeMove(gameMove);
            System.out.println(b);

            Move gameMoveMCT = new MyMCTSAI().findBestMonteMove(b, Player.BLUE);
            b = b.executeMove(gameMoveMCT);
            System.out.println(b);

        }

    }
}
