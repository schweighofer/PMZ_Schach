package at.kaindorf.pmz.chess.moves;

import at.kaindorf.pmz.chess.Piece;

import java.util.List;

/**
 * @Author Marcus Schweighofer
 * Created on 26.04.2023.
 * Class: Moveable.java
 */

public interface Moveable {
    void move(Piece piece, List<Integer> possibleMoves, int position);
}
