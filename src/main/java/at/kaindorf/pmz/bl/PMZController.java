package at.kaindorf.pmz.bl;

import at.kaindorf.pmz.chess.Piece;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Game.java
 */

public class PMZController {

    private static PMZController theInstance = new PMZController();

    public static PMZController getInstance() {
        return theInstance;
    }

    private PMZController() {
    }

    private int maxGameId = 0;
    private Map<Integer, Game> games = new HashMap<>();

    public int startGame() {
        games.put(maxGameId, new Game());
        maxGameId += 2;
        return maxGameId - 2;
    }

    public List<Piece> getBoard(Integer id) {
        List<Piece> board = games.get(id / 10 * 10).getBoard();
        if (id % 2 == 0) {
            Collections.reverse(board);
        }
        return board;
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
