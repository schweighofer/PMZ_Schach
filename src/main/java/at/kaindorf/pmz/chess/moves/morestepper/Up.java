package at.kaindorf.pmz.chess.moves.morestepper;

import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.chess.moves.Moveable;
import at.kaindorf.pmz.pojos.logic.MutableInteger;

import java.util.List;

import static at.kaindorf.pmz.bl.Game.DIVISOR_LINE_SIZE;
import static at.kaindorf.pmz.bl.Game.LINE_SIZE;

/**
 * @Author Marcus Schweighofer
 * Created on 26.04.2023.
 * Class: Up.java
 */

public interface Up extends Moveable {
    @Override
    default void move(Piece piece, List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position - LINE_SIZE);
        if ((int)((assumedPosition.v() + LINE_SIZE) * DIVISOR_LINE_SIZE) > 0) {
            piece.handleMove(assumedPosition, 0, possibleMoves);
        }
    }
}