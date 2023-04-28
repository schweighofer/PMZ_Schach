package at.kaindorf.pmz.chess.moves.morestepper;

import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.chess.moves.Moveable;

import java.util.List;

/**
 * @Author Marcus Schweighofer
 * Created on 26.04.2023.
 * Class: Pawn.java
 */

public interface Pawn extends Moveable {
    @Override
    default void move(Piece piece, List<Integer> possibleMoves, int position) {
    }
}
