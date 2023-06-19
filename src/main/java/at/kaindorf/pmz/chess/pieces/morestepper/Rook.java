package at.kaindorf.pmz.chess.pieces.morestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.pojos.logic.MutableInteger;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.bl.Game.*;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: Rook.java
 */

public class Rook extends Piece {
    public Rook(Boolean isBlack, Game game, int moveCount) {
        super(isBlack, game, moveCount);
    }

    @Override
    public List<Integer> obtainPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);

        MutableInteger assumedPosition = new MutableInteger(position - 1);
        while (assumedPosition.v() >= (int)(position * DIVISOR_LINE_SIZE) * LINE_SIZE) {
            if (handleMove(assumedPosition, -1, possibleMoves)) {
                break;
            }
        }

        assumedPosition.v(position + 1);
        while (assumedPosition.v() < (int)(position * DIVISOR_LINE_SIZE) * LINE_SIZE + LINE_SIZE) {
            if (handleMove(assumedPosition, 1, possibleMoves)) {
                break;
            }
        }

        assumedPosition.v(position - LINE_SIZE);
        while ((int)((assumedPosition.v() + LINE_SIZE) * DIVISOR_LINE_SIZE) > 0) {
            if (handleMove(assumedPosition, -LINE_SIZE, possibleMoves)) {
                break;
            }
        }

        assumedPosition.v(position + LINE_SIZE);
        while ((int)((assumedPosition.v()) * DIVISOR_LINE_SIZE) * LINE_SIZE < FIELD_SIZE) {
            if (handleMove(assumedPosition, LINE_SIZE, possibleMoves)) {
                break;
            }
        }

        return possibleMoves;
    }
}
