package at.kaindorf.pmz.chess.moves.morestepper;

import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.chess.moves.Moveable;
import at.kaindorf.pmz.pojos.logic.MutableInteger;

import java.util.List;

import static at.kaindorf.pmz.bl.Game.FIELD_SIZE;
import static at.kaindorf.pmz.bl.Game.LINE_SIZE;

/**
 * @Author Marcus Schweighofer
 * Created on 26.04.2023.
 * Class: Knight.java
 */

public interface Knight extends Moveable {
    @Override
    default void move(Piece piece, List<Integer> possibleMoves, int position) {
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
                piece.handleMove(possibleMove, 0, possibleMoves);
            }
        }
        // right down
        for (int i = 2; i < 4; i++) {
            MutableInteger possibleMove = new MutableInteger(position + options[i]);
            if ((possibleMove.v() < FIELD_SIZE) && (possibleMove.v() % LINE_SIZE != 0)) {
                piece.handleMove(possibleMove, 0, possibleMoves);
            }
        }
        // left down
        for (int i = 4; i < 6; i++) {
            MutableInteger possibleMove = new MutableInteger(position + options[i]);
            if ((possibleMove.v() < FIELD_SIZE) && (possibleMove.v() % LINE_SIZE != 7)) {
                piece.handleMove(possibleMove, 0, possibleMoves);
            }
        }
        // right up
        for (int i = 6; i < 8; i++) {
            MutableInteger possibleMove = new MutableInteger(position + options[i]);
            if ((possibleMove.v() >= 0) && (possibleMove.v() % LINE_SIZE != 7)) {
                piece.handleMove(possibleMove, 0, possibleMoves);
            }
        }
    }
}
