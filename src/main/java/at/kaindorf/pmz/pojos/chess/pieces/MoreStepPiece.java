package at.kaindorf.pmz.pojos.chess.pieces;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.pojos.chess.FieldState;
import at.kaindorf.pmz.pojos.chess.Piece;
import at.kaindorf.pmz.pojos.logic.MutableInteger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.bl.Game.*;
import static at.kaindorf.pmz.bl.Game.LINE_SIZE;

/**
 * @Author Marcus Schweighofer
 * Created on 30.03.2023.
 * Class: MoreStepPiece.java
 */

public abstract class MoreStepPiece extends Piece {
    public MoreStepPiece(Boolean isBlack, Game game) {
        super(isBlack, game);
    }

    @Override
    public abstract List<Integer> getPossibleMoves();

    @Override
    public boolean pieceAhead(List<Integer> possibleMoves, int currentPosition, FieldState fieldState) {
        return super.pieceAhead(possibleMoves, currentPosition, fieldState);
    }

    @Override
    protected boolean step(int position, List<Integer> possibleMoves, MoveType... types) {
        for (MoveType type : types) {
            try {
                Method method = MoreStepPiece.class.getDeclaredMethod(type.name().toLowerCase(), List.class, int.class);
                method.setAccessible(true);
                method.invoke(this, possibleMoves, position);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        return false;
    }

    // mutable integer unnötig ???

    @Override
    protected void left(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position - 1);
        while (assumedPosition.v() >= (int)(position * DIVISOR_LINE_SIZE) * LINE_SIZE) {
            if (handleMove(assumedPosition, -1, possibleMoves)) {
                break;
            }
        }
    }

    @Override
    protected void right(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position + 1);
        while (assumedPosition.v() < (int)(position * DIVISOR_LINE_SIZE) * LINE_SIZE + LINE_SIZE) {
            if (handleMove(assumedPosition, 1, possibleMoves)) {
                break;
            }
        }
    }

    @Override
    protected void up(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position - LINE_SIZE);
        while ((int)((assumedPosition.v() + LINE_SIZE) * DIVISOR_LINE_SIZE) > 0) {
            if (handleMove(assumedPosition, -LINE_SIZE, possibleMoves)) {
                break;
            }
        }
    }

    @Override
    protected void down(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position + LINE_SIZE);
        while ((int)((assumedPosition.v()) * DIVISOR_LINE_SIZE) * LINE_SIZE < FIELD_SIZE) {
            if (handleMove(assumedPosition, LINE_SIZE, possibleMoves)) {
                break;
            }
        }
    }

    @Override
    protected void left_up(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position - LINE_SIZE - 1);
        while (((assumedPosition.v() + LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition.v() >= 0)) {
            if (handleMove(assumedPosition, -(LINE_SIZE + 1), possibleMoves)) {
                break;
            }
        }
    }

    @Override
    protected void right_up(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position - LINE_SIZE + 1);
        while (((assumedPosition.v() + LINE_SIZE - 1) % LINE_SIZE != LINE_SIZE - 1) && (assumedPosition.v() >= 0)) {
            if (handleMove(assumedPosition, -(LINE_SIZE - 1), possibleMoves)) {
                break;
            }
        }
    }

    @Override
    protected void left_down(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position + LINE_SIZE - 1);
        while (((assumedPosition.v() - LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition.v() < FIELD_SIZE)) {
            if (handleMove(assumedPosition, LINE_SIZE - 1, possibleMoves)) {
                break;
            }
        }
    }

    @Override
    protected void right_down(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position + LINE_SIZE + 1);
        while (((assumedPosition.v() - LINE_SIZE - 1) % LINE_SIZE != LINE_SIZE - 1) && (assumedPosition.v() < FIELD_SIZE)) {
            if (handleMove(assumedPosition, LINE_SIZE + 1, possibleMoves)) {
                break;
            }
        }
    }

    @Override
    protected void pawn(List<Integer> possibleMoves, int position) {
        return;
    }

    @Override
    protected void knight(List<Integer> possibleMoves, int position) {
        return;
    }
}
