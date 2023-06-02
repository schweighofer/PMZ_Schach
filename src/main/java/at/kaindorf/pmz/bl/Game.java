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
        allOtherPieces = allOtherPieces.stream()
                .filter(p -> !(p instanceof Empty))
                .collect(Collectors.toList());
        allOtherPieces.removeIf(p -> p.isBlack() == piece.isBlack());
        return allOtherPieces;
    }

    public List<Integer> getPossibleMoves(int piecePosition) {
        lastPiece = piecePosition;
        lastPossibleMoves = board.get(lastPiece).obtainPossibleMoves();

        Piece toMove = board.get(piecePosition);

        for (Integer move : lastPossibleMoves) {
            Piece backup = board.get(move);
            board.set(move, toMove);
            board.set(piecePosition, new Empty(this));
            if (checkCheck(toMove.isBlack())) {
                lastPossibleMoves.remove(move);
            }
            board.set(move, backup);
            board.set(piecePosition, toMove);
        }

        return lastPossibleMoves;
    }

    public boolean move(int desiredPosition) {
        if (!lastPossibleMoves.contains(desiredPosition)) {
            return false;
        }
        hasWhiteTurn = !hasWhiteTurn;
        Piece backup = board.get(desiredPosition);
        Piece toMove = board.get(lastPiece);
        board.set(desiredPosition, toMove);
        board.set(lastPiece, new Empty(this));

        return true;
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

    // TODO: könig restrictions fertig machen, [Pawn fixen den 2er sprung am anfange], check, und checkMate, nach den vier sachen fertig

    public boolean hasEnded() {
        return checkCheckMate(true) || checkCheckMate(false);
    }

    public Boolean checkCheck(boolean forBlack) {
        King king = (King) board.stream()
                .filter(p -> p instanceof King)
                .filter(p -> p.isBlack() == forBlack)
                .findFirst()
                .get();
        return checkCheck(king);
    }

    public Boolean checkCheck(King king) {
        return checkCheck(king, getPosition(king));
    }

    public Boolean checkCheck(King king, Integer hypothteicalPosition) {
        List<Piece> allOtherPieces = getAllOtherPieces(king);
        List<Integer> possibleEnemyMoves;
        for (Piece p : allOtherPieces) {
            // vallah der scheiß geht immer noch nicht aber ja
            if (p instanceof Pawn) {
                possibleEnemyMoves = new ArrayList<>();
                int direction = (p.isBlack() ? 1 : -1);
                // left
                if ((getPosition(p) % LINE_SIZE != 0)) {
                    int possibleMove = hypothteicalPosition + LINE_SIZE * direction - 1;
                    possibleEnemyMoves.add(possibleMove);
                }
                // right
                if ((getPosition(p) % LINE_SIZE != 7)) {
                    int possibleMove = hypothteicalPosition + LINE_SIZE * direction + 1;
                    possibleEnemyMoves.add(possibleMove);
                }

            } else if (p instanceof King) {
                possibleEnemyMoves = ((King) p).obtainTheoreticalMoves();
            } else {
                possibleEnemyMoves = p.obtainPossibleMoves();
            }

            if (possibleEnemyMoves.contains(hypothteicalPosition)) {
                return true;
            }
        }
        return false;
    }

    public Boolean checkCheckMate(boolean forBlack) {
        // Muss vorher check sein
        King king = (King) board.stream()
                .filter(p -> p instanceof King)
                .filter(p -> p.isBlack() == forBlack)
                .findFirst()
                .get();

        if (king.obtainPossibleMoves().isEmpty()) {
            return true;
        }

        return false;
    }
}
