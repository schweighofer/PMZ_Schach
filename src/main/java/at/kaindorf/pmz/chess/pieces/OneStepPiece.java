package at.kaindorf.pmz.chess.pieces;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.FieldState;
import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.pojos.logic.MutableInteger;

import java.util.List;

import static at.kaindorf.pmz.bl.Game.*;

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
    protected void pawn(List<Integer> possibleMoves, int position) {
        int direction = (isBlack ? 1 : -1);
        int assumedPosition = position + LINE_SIZE * direction;
        // Move
        if (assumedPosition >= 0 && assumedPosition < FIELD_SIZE) {
            FieldState fieldState = game.getFieldState(assumedPosition);
            if (fieldState == FieldState.NULL) {
                possibleMoves.add(assumedPosition);
                if (((int)(assumedPosition * DIVISOR_LINE_SIZE) * LINE_SIZE + LINE_SIZE * (isBlack ? -1 : 1)) == (isBlack ? LINE_SIZE : FIELD_SIZE - 2 * LINE_SIZE)) {
                    possibleMoves.add(assumedPosition + LINE_SIZE * (isBlack ? 1 : -1));
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

    @Override
    protected void knight(List<Integer> possibleMoves, int position) {
        int[] options = {
                // right up
                - (2 * LINE_SIZE - 1),
                - (LINE_SIZE - 2),
                // right down
                LINE_SIZE + 2,
                2 * LINE_SIZE + 1,
                // left down
                2 * LINE_SIZE - 1,
                LINE_SIZE - 2,
                // left up
                - (LINE_SIZE + 2),
                - (2 * LINE_SIZE + 1)
        };

        // right up
        for (int i = 0; i < 2; i++) {
            MutableInteger possibleMove = new MutableInteger(position + options[i]);
            if ((possibleMove.v() >= 0) && (possibleMove.v() % LINE_SIZE != 0)) {
                handleMove(possibleMove, 0, possibleMoves);
            }
        }
        // right down
        for (int i = 2; i < 4; i++) {
            MutableInteger possibleMove = new MutableInteger(position + options[i]);
            if ((possibleMove.v() < FIELD_SIZE) && (possibleMove.v() % LINE_SIZE != 0)) {
                handleMove(possibleMove, 0, possibleMoves);
            }
        }
        // left down
        for (int i = 4; i < 6; i++) {
            MutableInteger possibleMove = new MutableInteger(position + options[i]);
            if ((possibleMove.v() < FIELD_SIZE) && (possibleMove.v() % LINE_SIZE != 7)) {
                handleMove(possibleMove, 0, possibleMoves);
            }
        }
        // right up
        for (int i = 6; i < 8; i++) {
            MutableInteger possibleMove = new MutableInteger(position + options[i]);
            if ((possibleMove.v() >= 0) && (possibleMove.v() % LINE_SIZE != 7)) {
                handleMove(possibleMove, 0, possibleMoves);
            }
        }
    }

    @Override
    protected void left(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position - 1);
        if (assumedPosition.v() >= (int)(position * DIVISOR_LINE_SIZE) * LINE_SIZE) {
            handleMove(assumedPosition, 0, possibleMoves);
        }
    }

    @Override
    protected void right(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position + 1);
        if (assumedPosition.v() < (int)(position * DIVISOR_LINE_SIZE) * LINE_SIZE + LINE_SIZE) {
            handleMove(assumedPosition, 0, possibleMoves);
        }
    }

    @Override
    protected void up(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position - LINE_SIZE);
        if ((int)((assumedPosition.v() + LINE_SIZE) * DIVISOR_LINE_SIZE) > 0) {
            handleMove(assumedPosition, 0, possibleMoves);
        }
    }

    @Override
    protected void down(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position + LINE_SIZE);
        if ((int)((assumedPosition).v() * DIVISOR_LINE_SIZE) * LINE_SIZE < FIELD_SIZE) {
            handleMove(assumedPosition, 0, possibleMoves);
        }
    }

    @Override
    protected void left_up(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position - LINE_SIZE - 1);
        if (((assumedPosition.v() + LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition.v() >= 0)) {
            handleMove(assumedPosition, 0, possibleMoves);
        }
    }

    @Override
    protected void right_up(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position - LINE_SIZE + 1);
        if (((assumedPosition.v() + LINE_SIZE - 1) % LINE_SIZE != LINE_SIZE - 1) && (assumedPosition.v() >= 0)) {
            handleMove(assumedPosition, 0, possibleMoves);
        }
    }

    @Override
    protected void left_down(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position + LINE_SIZE - 1);
        if (((assumedPosition.v() - LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition.v() < FIELD_SIZE)) {
            handleMove(assumedPosition, 0, possibleMoves);
        }
    }

    @Override
    protected void right_down(List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position + LINE_SIZE + 1);
        if (((assumedPosition.v() - LINE_SIZE - 1) % LINE_SIZE != LINE_SIZE - 1) && (assumedPosition.v() < FIELD_SIZE)) {
            handleMove(assumedPosition, 0, possibleMoves);
        }
    }
}
