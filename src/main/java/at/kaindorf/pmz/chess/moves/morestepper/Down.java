package at.kaindorf.pmz.chess.moves.morestepper;

import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.chess.moves.Moveable;
import at.kaindorf.pmz.pojos.logic.MutableInteger;

import java.util.List;

import static at.kaindorf.pmz.bl.Game.*;

/**
 * @Author Marcus Schweighofer
 * Created on 26.04.2023.
 * Class: Down.java
 */

public interface Down extends Moveable {
    @Override
    default void move(Piece piece, List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position + LINE_SIZE);
        if ((int)((assumedPosition).v() * DIVISOR_LINE_SIZE) * LINE_SIZE < FIELD_SIZE) {
            piece.handleMove(assumedPosition, 0, possibleMoves);
        }
    }
}
