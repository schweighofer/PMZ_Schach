package at.kaindorf.pmz.pojos.chess.pieces.morestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.pojos.chess.FieldState;
import at.kaindorf.pmz.pojos.chess.Piece;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.bl.Game.*;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Bishop.java
 */

public class Bishop extends Piece {
    public Bishop(Boolean isBlack, Game game) {
        super(isBlack, game);
    }

    @Override
    public List<Integer> getPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);
        // 4 while loops, one for every direction

        // left-up
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
