package at.kaindorf.pmz.bl;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Game.java
 */

public class PMZController {

    public static int TEST_POS1 = 48;

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

        //System.out.println(game.getPiece(TEST_POS1).getPossibleMoves());

        System.out.println(game.getPossibleMoves(48));
        game.move(32);
        game.printField();
        System.out.println(game.getPossibleMoves(8));
        game.move(24);
        game.printField();
        game.getPossibleMoves(0);
        game.move(16);
        game.printField();
        game.getPossibleMoves(16);
        game.move(21);
        game.printField();
        game.getPossibleMoves(21);
        game.move(53);
        game.printField();
    }
}
