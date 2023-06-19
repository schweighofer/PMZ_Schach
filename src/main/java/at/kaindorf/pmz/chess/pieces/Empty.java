package at.kaindorf.pmz.chess.pieces;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.Piece;

import java.util.List;

/**
 * @Author Marcus Schweighofer
 * Created on 26.04.2023.
 * Class: Empty.java
 */

public class Empty extends Piece {

    public Empty(Game game) {
        super(true, game, 0);
    }

    @Override
    public List<Integer> obtainPossibleMoves() {
        return null;
    }
}
