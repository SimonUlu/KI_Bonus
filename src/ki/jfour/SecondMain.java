package ki.jfour;

public class SecondMain {

    public static void main(String[] args) {
        Board b = new Board(5, 7);
        b = b.executeMove(new Move(0));
        b = b.executeMove(new Move(0));
        b = b.executeMove(new Move(1));
        b = b.executeMove(new Move(1));
        b = b.executeMove(new Move(2));
        b = b.executeMove(new Move(2));
        System.out.println(b);

        Player player = b.getCurrentPlayer();
        //Move move = new TestMinMaxAI().findBestMove(b,player);
        //System.out.println(move);

    }
}
