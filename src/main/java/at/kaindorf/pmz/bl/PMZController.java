package at.kaindorf.pmz.bl;

import at.kaindorf.pmz.chess.Piece;

import java.util.*;

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
        if ((id % 2 == 0) != (games.get(id / 10 * 10).getPiece(position).isBlack())) {
            return games.get(id / 10 * 10).getPossibleMoves(position);
        }
        return new ArrayList<Integer>();
    }

    public boolean makeMove(Integer id, Integer target) {
        return games.get(id / 10 * 10).move(target);
    }
}
