package at.kaindorf.pmz.bl;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Game.java
 */

public class PMZController {

    public static int TEST_POS1 = 38;
    public static int TEST_POS2 = 63;

    public static void main(String[] args) {
        Game game = new Game();
        game.printField();
        // bis jetzt fertig:
        // - Rook
        // - Bishop
        game.getPiece(TEST_POS1).getPossibleMoves();
    }
}
