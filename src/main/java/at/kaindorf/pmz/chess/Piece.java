package at.kaindorf.pmz.chess;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.pojos.logic.MutableInteger;
import jakarta.json.bind.annotation.JsonbTransient;

import java.util.List;

/**
 * @Author Marcus Schweighofer
 * Created on 24.03.2023.
 * Class: Piece.java
 */

public abstract class Piece {
    protected Boolean isBlack;
    protected Game game;

    public Piece(Boolean isBlack, Game game) {
        this.isBlack = isBlack;
        this.game = game;
    }

    @JsonbTransient
    public abstract List<Integer> obtainPossibleMoves();

    // handles enemy pieces and only returns true if the piece is the enemy king
    public boolean pieceAhead(List<Integer> possibleMoves, int currentPosition, FieldState fieldState) {
        // PROBLEM: isBlack ist immer false?
        if ((fieldState == FieldState.BLACK || fieldState == FieldState.BLACK_KING) != isBlack) {
            possibleMoves.add(currentPosition);
            return false;
        }
        return false;
    }

    public boolean handleMove(MutableInteger assumedPosition, int step, List<Integer> possibleMoves) {
        FieldState fieldState = game.getFieldState(assumedPosition.v());
        if (fieldState == FieldState.NULL) {
            possibleMoves.add(assumedPosition.v());
            assumedPosition.v(assumedPosition.v() + step);
            return false;
        } else {
            pieceAhead(possibleMoves, assumedPosition.v(), fieldState);
            return true;
        }
    }

    @JsonbTransient
    public Boolean isBlack() {
        return isBlack;
    }

    public Integer getPosition() {
        return game.getPosition(this);
    }

    public char getChar() {
        return PieceMap.get(this);
    }
}
