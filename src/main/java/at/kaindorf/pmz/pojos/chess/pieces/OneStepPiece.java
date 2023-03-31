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
 * Class: OneStepPiece.java
 */

public abstract class OneStepPiece extends Piece {
    public OneStepPiece(Boolean isBlack, Game game) {
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
                Method method = OneStepPiece.class.getDeclaredMethod(type.name().toLowerCase(), List.class, int.class);
                method.setAccessible(true);
                method.invoke(this, possibleMoves, position);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
        return false;
    }

    public void pawn(List<Integer> possibleMoves, int position) {
        int direction = (isBlack ? -1 : 1);
        int assumedPosition = position + LINE_SIZE * direction;
        // Move
        if (assumedPosition >= 0 && assumedPosition < FIELD_SIZE) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
                System.out.println((int)(assumedPosition * DIVISOR_LINE_SIZE) * LINE_SIZE + LINE_SIZE);
                if (((int)(assumedPosition * DIVISOR_LINE_SIZE) * LINE_SIZE + LINE_SIZE) == (isBlack ? FIELD_SIZE - 2 * LINE_SIZE : 2 * LINE_SIZE)) {
                    possibleMoves.add(assumedPosition + LINE_SIZE);
                }
            }
        }
        // Kill
        // left
        if ((position % LINE_SIZE != 0)) {
            int possibleMove = position + LINE_SIZE * direction - 1;
            FieldState fieldState = game.getFieldState(possibleMove);
            if (fieldState != FieldState.NULL) {
                super.pieceAhead(possibleMoves, possibleMove, fieldState);
            }
        }
        // right
        if ((position % LINE_SIZE != 7)) {
            int possibleMove = position + LINE_SIZE * direction + 1;
            FieldState fieldState = game.getFieldState(possibleMove);
            if (fieldState != FieldState.NULL) {
                super.pieceAhead(possibleMoves, possibleMove, fieldState);
            }
        }
    }

    public void knight(List<Integer> possibleMoves, int position) {
        int[] options = {
                -(2 * LINE_SIZE + 1),
                -(2 * LINE_SIZE - 1),
                -(LINE_SIZE - 2),
                LINE_SIZE + 2,
                2 * LINE_SIZE + 1,
                2 * LINE_SIZE - 1,
                LINE_SIZE - 2,
                - (LINE_SIZE + 2)
        };

        for (int i : options) {
            System.out.println(position + i);
        }
    }

    private void left(List<Integer> possibleMoves, int position) {
        int assumedPosition = position - 1;
        if (assumedPosition > (int)(position * DIVISOR_LINE_SIZE) * LINE_SIZE) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
            }
        }
    }

    private void right(List<Integer> possibleMoves, int position) {
        int assumedPosition = position + 1;
        if (assumedPosition < (int)(position * DIVISOR_LINE_SIZE) * LINE_SIZE + LINE_SIZE) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
            }
        }
    }

    private void up(List<Integer> possibleMoves, int position) {
        int assumedPosition = position - LINE_SIZE;
        if ((int)((assumedPosition + LINE_SIZE) * DIVISOR_LINE_SIZE) > 0) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
            }
        }
    }

    private void down(List<Integer> possibleMoves, int position) {
        int assumedPosition = position + LINE_SIZE;
        if ((int)((assumedPosition) * DIVISOR_LINE_SIZE) * LINE_SIZE < FIELD_SIZE) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
            }
        }
    }

    private void left_up(List<Integer> possibleMoves, int position) {
        int assumedPosition = position - LINE_SIZE - 1;
        if (((assumedPosition + LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition >= 0)) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
            }
        }
    }

    private void right_up(List<Integer> possibleMoves, int position) {
        int assumedPosition = position - LINE_SIZE + 1;
        if (((assumedPosition + LINE_SIZE - 1) % LINE_SIZE != LINE_SIZE - 1) && (assumedPosition >= 0)) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
            }
        }
    }

    private void left_down(List<Integer> possibleMoves, int position) {
        int assumedPosition = position + LINE_SIZE - 1;
        if (((assumedPosition - LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition < FIELD_SIZE)) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
            }
        }
    }

    private void right_down(List<Integer> possibleMoves, int position) {
        int assumedPosition = position + LINE_SIZE + 1;
        if (((assumedPosition - LINE_SIZE - 1) % LINE_SIZE != LINE_SIZE - 1) && (assumedPosition < FIELD_SIZE)) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
            } else {
                super.pieceAhead(possibleMoves, assumedPosition, fieldState);
            }
        }
    }
}
