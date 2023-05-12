package at.kaindorf.pmz.bl;

import at.kaindorf.pmz.chess.FieldState;
import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.chess.pieces.Empty;
import at.kaindorf.pmz.chess.pieces.morestepper.Bishop;
import at.kaindorf.pmz.chess.pieces.morestepper.Queen;
import at.kaindorf.pmz.chess.pieces.morestepper.Rook;
import at.kaindorf.pmz.chess.pieces.onestepper.King;
import at.kaindorf.pmz.chess.pieces.onestepper.Knight;
import at.kaindorf.pmz.chess.pieces.onestepper.Pawn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author Marcus Schweighofer
 * Created on 24.03.2023.
 * Class: Game.java
 */

public class Game {
    public static final Integer LINE_SIZE = 8;
    public static final Float DIVISOR_LINE_SIZE = 1 / (float)(LINE_SIZE);
    public static final Integer FIELD_SIZE = LINE_SIZE * LINE_SIZE;

    private boolean hasWhiteTurn = false;
    private Boolean isWhiteCheck = null;

    private final List<Piece> board;

    private Integer lastPiece;
    private List<Integer> lastPossibleMoves;

    public Game() {
        this.board = new ArrayList<>();
        setupBoard();
    }

    private void setupBoard() {
        // top row
        board.add(new Rook(    true,    this));
        board.add(new Knight(  true,    this));
        board.add(new Bishop(  true,    this));
        board.add(new Queen(   true,    this));
        board.add(new King(    true,    this));
        board.add(new Bishop(  true,    this));
        board.add(new Knight(  true,    this));
        board.add(new Rook(    true,    this));

        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));

        // null rows
        for (int i = 16; i < 48; i++) {
            board.add(new Empty(this));
        }

        // bottom row
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));

        board.add(new Rook(    false,   this));
        board.add(new Knight(  false,   this));
        board.add(new Bishop(  false,   this));
        board.add(new Queen(   false,   this));
        board.add(new King(    false,   this));
        board.add(new Bishop(  false,   this));
        board.add(new Knight(  false,   this));
        board.add(new Rook(    false,   this));


    }

    public int getPosition(Piece piece) {
        return board.indexOf(piece);
    }

    public FieldState getFieldState(int index) {
        if (board.get(index) instanceof Empty) {
            return FieldState.NULL;
        }
        Piece piece = board.get(index);
        String fieldState = piece.isBlack() ? "BLACK" : "WHITE";
        if (piece instanceof King) {
            fieldState += "_KING";
        }
        return FieldState.valueOf(fieldState);
    }

    public List<Piece> getAllOtherPieces(Piece piece) {
        List<Piece> allOtherPieces = new ArrayList<>(board);
        return allOtherPieces.stream()
                .filter(p -> p != null)
                .filter(p -> p.isBlack() != piece.isBlack())
                .collect(Collectors.toList());
    }

    public List<Integer> getPossibleMoves(int piecePosition) {
        lastPiece = piecePosition;
        lastPossibleMoves = board.get(lastPiece).obtainPossibleMoves();
        return lastPossibleMoves;
    }

    public boolean move(int desiredPosition) {
        if (!lastPossibleMoves.contains(desiredPosition)) {
            return false;
        }
        hasWhiteTurn = !hasWhiteTurn;
        board.set(desiredPosition, board.get(lastPiece));
        board.set(lastPiece, new Empty(this));
        return true;
    }

    public Boolean getBlackCheck() {
        return isWhiteCheck;
    }

    public void setBlackCheck(Boolean blackCheck) {
        isWhiteCheck = blackCheck;
    }

    public List<Piece> getBoard() {
        return new ArrayList<>(this.board);
    }

    // DEBUG

    public void printField() {
        for (int i = 0; i < LINE_SIZE; i++) {
            for (int j = 0; j < LINE_SIZE; j++) {
                if (board.get(i * LINE_SIZE + j) instanceof Empty) {
                    System.out.print("\uF030");
                } else {
                    System.out.print(board.get(i * LINE_SIZE + j).getChar());
                }
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }

    public Piece getPiece(int index) {
        return board.get(index);
    }

    public boolean isHasWhiteTurn() {
        return hasWhiteTurn;
    }
}
