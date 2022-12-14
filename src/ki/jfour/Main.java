package ki.jfour;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Board b = new Board(6, 7);

        while (b.possibleMoves().size() > 0 && b.getWinner() == Player.NONE) {

            if (b.getWinner() != Player.NONE) {
                b.getWinner();
            }

            Move gameMoveMinMax = new MyMinMaxAI().findBestMove(b);
            b = b.executeMove(gameMoveMinMax);
            System.out.println(b);

            Move gameMoveMCT = new MyMCTSAI().findBestMonteMove(b, Player.BLUE);
            b = b.executeMove(gameMoveMCT);
            System.out.println(b);

        }

    }
}
