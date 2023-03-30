package at.kaindorf.pmz.pojos.chess.pieces.morestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.pojos.chess.FieldState;
import at.kaindorf.pmz.pojos.chess.Piece;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.bl.Game.*;
import static at.kaindorf.pmz.bl.Game.LINE_SIZE;

/**
 * @Author Marcus Schweighofer
 * Created on 24.03.2023.
 * Class: Queen.java
 */

public class Queen extends Piece {
    public Queen(Boolean isBlack, Game game) {
        super(isBlack, game);
    }

    @Override
    public List<Integer> getPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);
        // 8 while loops, one for every direction

        // left
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
        // right
        assumedPosition = position + 1;
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
        // up
        assumedPosition = position - LINE_SIZE;
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
        // down
        assumedPosition = position + LINE_SIZE;
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
        assumedPosition = position - LINE_SIZE - 1;
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
        // right-up
        assumedPosition = position - LINE_SIZE + 1;
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
        // left-down
        assumedPosition = position + LINE_SIZE - 1;
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
        // right-down
        assumedPosition = position + LINE_SIZE + 1;
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

        return possibleMoves;
    }
}
