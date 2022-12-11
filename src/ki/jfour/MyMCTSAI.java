package ki.jfour;

import java.util.List;

public class MyMCTSAI extends AI{

    @Override
    public void start(Board b) {

        while (b.possibleMoves().size() > 0) {

            Player currentPlayer = b.getCurrentPlayer();

            System.out.println();

            if (b.getWinner() != Player.NONE) {
                System.out.println("Game over");
                break;
            }

            // perform move -> bestMove
            b = b.executeMove(new Move(1));
            System.out.println(b);
            // save all possible moves in list
            List<Move> movesList = b.possibleMoves();
            System.out.println(movesList);
            break;
        }

    }

    @Override
    public String getDescription() {
        return "Simon Unterlugauer";
    }


    public int getMoveHeuristic(List<Move> moves, Board b) {

        return 2;
    }

    /*
    public static void main(String[] args) {
        Board b = new Board(10, 10);
        new MyMCTSAI().start(b);
    }
    */

}
