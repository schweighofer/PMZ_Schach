package at.kaindorf.pmz.chess.moves.morestepper;

import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.chess.moves.Moveable;
import at.kaindorf.pmz.pojos.logic.MutableInteger;

import java.util.List;

import static at.kaindorf.pmz.bl.Game.LINE_SIZE;

/**
 * @Author Marcus Schweighofer
 * Created on 26.04.2023.
 * Class: LeftUp.java
 */

public interface LeftUp extends Moveable {
    @Override
    default void move(Piece piece, List<Integer> possibleMoves, int position) {
        MutableInteger assumedPosition = new MutableInteger(position - LINE_SIZE - 1);
        if (((assumedPosition.v() + LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition.v() >= 0)) {
            piece.handleMove(assumedPosition, 0, possibleMoves);
        }
    }
}
