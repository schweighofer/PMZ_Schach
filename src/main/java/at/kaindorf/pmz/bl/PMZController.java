package at.kaindorf.pmz.bl;

import at.kaindorf.pmz.chess.Piece;

import java.util.*;

import static at.kaindorf.pmz.bl.Game.LINE_SIZE;

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
        List<Piece> board = games.get(id / 2 * 2).getBoard();
        if (id % 2 == 0) {
            //return turnBoard(board);
            List<Piece> turnedBoard = new ArrayList<>(board);
            Collections.reverse(turnedBoard);
            return turnedBoard;
        }
        return board;
    }

    public static List<Piece> turnBoard(List<Piece> board) {
        List<Piece> turnedBoard = new ArrayList<>();
        for (int i = LINE_SIZE * LINE_SIZE - LINE_SIZE; i >= 0; i -= LINE_SIZE) {
            for (int j = 0; j < LINE_SIZE; j++) {
                turnedBoard.add(board.get(i + j));
            }
        }
        return turnedBoard;
    }

    public List<Integer> getPossibleMoves(Integer id, Integer position) {
        if ((id % 2 == 0) == (games.get(id / 2 * 2).getPiece(position).isBlack())) {
            return games.get(id / 2 * 2).getPossibleMoves(position);
        }
        return new ArrayList<Integer>();
    }

    public boolean makeMove(Integer id, Integer target) {
        return games.get(id / 2 * 2).move(target);
    }

    public boolean hasBlackTurn(Integer id) {
        return games.get(id / 2 * 2).isHasWhiteTurn();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.printField();
        System.out.println(game.getPossibleMoves(8));
        System.out.println(game.getPossibleMoves(48));
    }

    public boolean isChess(Integer id, boolean forBlack) {
        return games.get(id / 2 * 2).checkCheck(forBlack);
    }

    public boolean isCheckmate(Integer id, boolean forBlack) {
        return games.get(id / 2 * 2).checkCheckMate(forBlack);
    }
}
