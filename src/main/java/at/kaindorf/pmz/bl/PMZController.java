package at.kaindorf.pmz.bl;

import at.kaindorf.pmz.chess.Piece;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Game.java
 */

public class PMZController {

    private Map<Integer, Game> games = new HashMap<>();

    public void startGame(Integer id) {
        games.put(id, new Game());
    }

    public List<Piece> getBoard(Integer id) {
        // das hier problem
        games.put(id, new Game());
        return games.get(id).getBoard();
    }

    public List<Integer> getPossibleMoves(Integer id, Integer position) {
        return games.get(id).getPossibleMoves(position);
    }

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
