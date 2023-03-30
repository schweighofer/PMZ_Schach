package at.kaindorf.pmz.pojos.chess;

import at.kaindorf.pmz.bl.Game;

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

    public abstract List<Integer> getPossibleMoves();

    // handles enemy pieces and only returns true if the piece is the enemy king
    protected boolean pieceAhead(List<Integer> possibleMoves, int currentPosition, FieldState fieldState) {
        if ((fieldState == FieldState.BLACK) != isBlack) {
            possibleMoves.add(currentPosition);
            return false;
        }
        return ((fieldState == FieldState.BLACK_KING) == isBlack);
    }

    public Integer getPosition() {
        return game.getPosition(this);
    }

    public Boolean isBlack() {
        return isBlack;
    }

    @Override
    public String toString() {
        return "" + PieceMap.get(this);
    }
}