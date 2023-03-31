package at.kaindorf.pmz.pojos.chess;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.pojos.chess.pieces.MoveType;
import at.kaindorf.pmz.pojos.logic.MutableInteger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    public boolean pieceAhead(List<Integer> possibleMoves, int currentPosition, FieldState fieldState) {
        if ((fieldState == FieldState.BLACK) != isBlack) {
            possibleMoves.add(currentPosition);
            return false;
        }
        return ((fieldState == FieldState.BLACK_KING) == isBlack);
    }

    protected boolean moves(int position, List<Integer> possibleMoves, MoveType... types) {
        for (MoveType type : types) {
            try {
                Method method = this.getClass().getSuperclass().getDeclaredMethod(type.name().toLowerCase(), List.class, int.class);
                method.setAccessible(true);
                method.invoke(this, possibleMoves, position);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    protected boolean handleMove(MutableInteger assumedPosition, int step, List<Integer> possibleMoves) {
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

    protected abstract void left(List<Integer> possibleMoves, int position);
    protected abstract void right(List<Integer> possibleMoves, int position);
    protected abstract void up(List<Integer> possibleMoves, int position);
    protected abstract void down(List<Integer> possibleMoves, int position);
    protected abstract void left_up(List<Integer> possibleMoves, int position);
    protected abstract void right_up(List<Integer> possibleMoves, int position);
    protected abstract void left_down(List<Integer> possibleMoves, int position);
    protected abstract void right_down(List<Integer> possibleMoves, int position);
    protected abstract void pawn(List<Integer> possibleMoves, int position);
    protected abstract void knight(List<Integer> possibleMoves, int position);

    public Integer getPosition() {
        return game.getPosition(this);
    }

    public Boolean isBlack() {
        return isBlack;
    }

    public Game getGame() {
        return game;
    }

    @Override
    public String toString() {
        return "" + PieceMap.get(this);
    }
}
