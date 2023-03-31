package at.kaindorf.pmz.bl;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Game.java
 */

public class PMZController {

    public static int TEST_POS1 = 23;
    public static int TEST_POS2 = 47;

    public static void main(String[] args) {
        Game game = new Game();
        game.printField();
        // bis jetzt fertig:
        // - Rook
        // - Bishop
        // - Queen
        // - Pawn
        // - King
        // - Knight

        // rook eins zu wenig nach links
        System.out.println(game.getPiece(TEST_POS2).getPossibleMoves());
    }
}
