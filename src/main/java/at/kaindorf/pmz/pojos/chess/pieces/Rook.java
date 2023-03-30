package at.kaindorf.pmz.pojos.chess.pieces;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.pojos.chess.FieldState;
import at.kaindorf.pmz.pojos.chess.Piece;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.bl.Game.*;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Rook.java
 */

public class Rook extends Piece {
    public Rook(Boolean isBlack, Game game) {
        super(isBlack, game);
    }

    @Override
    public List<Integer> getPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);
        // 4 while loops, one for every direction

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
        return possibleMoves;
    }
}
