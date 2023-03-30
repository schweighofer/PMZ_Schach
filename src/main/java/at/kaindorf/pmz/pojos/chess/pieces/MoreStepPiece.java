package at.kaindorf.pmz.pojos.chess.pieces;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.pojos.chess.FieldState;
import at.kaindorf.pmz.pojos.chess.Piece;

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
    protected List<Integer> step(int position, MoveType... types) {
        List<Integer> possibleMoves = new ArrayList<>();
        for (MoveType type : types) {
            try {
                Method method = MoreStepPiece.class.getDeclaredMethod(type.name().toLowerCase(), List.class, int.class);
                method.setAccessible(true);
                method.invoke(this, possibleMoves, position);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
        return possibleMoves;
    }

    private void left(List<Integer> possibleMoves, int position) {
        int assumedPosition = position - 1;
        while (assumedPosition > (int)(position * DIVISOR_LINE_SIZE) * LINE_SIZE) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
                assumedPosition--;
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
                break;
            }
        }
    }

    private void right(List<Integer> possibleMoves, int position) {
        int assumedPosition = position + 1;
        while (assumedPosition < (int)(position * DIVISOR_LINE_SIZE) * LINE_SIZE + LINE_SIZE) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
                assumedPosition++;
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
                break;
            }
        }
    }

    private void up(List<Integer> possibleMoves, int position) {
        int assumedPosition = position - LINE_SIZE;
        while ((int)((assumedPosition + LINE_SIZE) * DIVISOR_LINE_SIZE) > 0) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
                assumedPosition -= LINE_SIZE;
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
                break;
            }
        }
    }

    private void down(List<Integer> possibleMoves, int position) {
        int assumedPosition = position + LINE_SIZE;
        while ((int)((assumedPosition) * DIVISOR_LINE_SIZE) * LINE_SIZE < FIELD_SIZE) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
                assumedPosition += LINE_SIZE;
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
                break;
            }
        }
    }

    private void left_up(List<Integer> possibleMoves, int position) {
        int assumedPosition = position - LINE_SIZE - 1;
        while (((assumedPosition + LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition >= 0)) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
                assumedPosition -= (LINE_SIZE + 1);
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
                break;
            }
        }
    }

    private void right_up(List<Integer> possibleMoves, int position) {
        int assumedPosition = position - LINE_SIZE + 1;
        while (((assumedPosition + LINE_SIZE - 1) % LINE_SIZE != LINE_SIZE - 1) && (assumedPosition >= 0)) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
                assumedPosition -= (LINE_SIZE - 1);
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
                break;
            }
        }
    }

    private void left_down(List<Integer> possibleMoves, int position) {
        int assumedPosition = position + LINE_SIZE - 1;
        while (((assumedPosition - LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition < FIELD_SIZE)) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
                assumedPosition += (LINE_SIZE - 1);
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
                break;
            }
        }
    }

    private void right_down(List<Integer> possibleMoves, int position) {
        int assumedPosition = position + LINE_SIZE + 1;
        while (((assumedPosition - LINE_SIZE - 1) % LINE_SIZE != LINE_SIZE - 1) && (assumedPosition < FIELD_SIZE)) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
                assumedPosition += (LINE_SIZE + 1);
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
                break;
            }
        }
    }
}
