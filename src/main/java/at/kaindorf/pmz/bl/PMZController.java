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


    public int startGame(int time) {
        games.put(maxGameId, new Game(time));
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
        if ((id % 2 == 0) == (games.get(id / 2 * 2).getPiece(position).isWhite())) {
            return games.get(id / 2 * 2).getPossibleMoves(position, games.get(id / 2 * 2).getBoard().get(position));
        }
        return new ArrayList<Integer>();
    }

    public boolean makeMove(Integer id, Integer target, int lastPiece) {
        return games.get(id / 2 * 2).move(target, lastPiece);
    }

    public boolean hasBlackTurn(Integer id) {
        return games.get(id / 2 * 2).isHasWhiteTurn();
    }
    public boolean giveUp(Integer id) {
        if(id % 2 == 0){
            return games.get(id / 2 * 2).setHasWhiteGivenUp();
        }
        return games.get(id / 2 * 2).setHasBlackGivenUp();

    }

    public static void main(String[] args) {
        Game game = new Game(99999);
        //game.getBoard().set(11, new Rook(false, game));
        System.out.println(game.checkCheck(true));
        game.printField();

    }

    public boolean isChess(Integer id, boolean forBlack) {
        return games.get(id / 2 * 2).checkCheck(forBlack);
    }

    public boolean isCheckmate(Integer id, boolean forBlack) {
        return games.get(id / 2 * 2).checkCheckMate(forBlack);
    }

    public boolean hasEnded(Integer id) {
        return games.get(id / 2 * 2).hasEnded();
    }

    public String getName(Integer id) {
        return games.get(id / 2 * 2).getPlayerName(id % 2);
    }

    public void setName(Integer id, String name) {
        games.get(id / 2 * 2).setPlayerName(name, id % 2);
    }

    public int getTime(Integer id) {
        return games.get(id / 2 * 2).getTime(id % 2);
    }

    public String getStats(Integer id) {
        Game game = games.get(id / 2 * 2);
        if (game.isCheckMate(false)) {
            return "White won thorugh checkmate";
        }else if (game.isCheckMate(true)) { // hier bei den 2 weiß ich nicht steht so im LW, kann auch andersrum sein
            return "Black won through checkmate";
        } else if (game.isPatt()) {
            return "Draw";
        } else if (game.getHasTimeEnded(0)) { // 0 --> white
            return "Black won through time";
        } else if (game.getHasTimeEnded(1)) { // 1 --> black
            return "White won through time";
        }
        else if (game.isHasBlackGivenUp()) { // 1 --> black
            return "White won through Aufgabe von Black";
        }
        else if (game.isHasWhiteGivenUp()) { // 1 --> black
            return "Black won through Aufgabe von Black";
        }
        return "something happened and game is finished. thank you";
    }
}
