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
    protected List<Integer> step(int position, MoveType... types) {
        List<Integer> possibleMoves = new ArrayList<>();
        for (MoveType type : types) {
            try {
                Method method = OneStepPiece.class.getDeclaredMethod(type.name().toLowerCase(), List.class, int.class);
                method.setAccessible(true);
                method.invoke(this, possibleMoves, position);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }

        }
        return possibleMoves;
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
}
