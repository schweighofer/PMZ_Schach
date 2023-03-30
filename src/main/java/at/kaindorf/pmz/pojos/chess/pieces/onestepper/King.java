package at.kaindorf.pmz.pojos.chess.pieces.onestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.pojos.chess.Piece;
import at.kaindorf.pmz.pojos.chess.pieces.OneStepPiece;

import java.util.List;

/**
 * @Author Marcus Schweighofer
 * Created on 24.03.2023.
 * Class: King.java
 */

public class King extends OneStepPiece {
    public King(Boolean isBlack, Game game) {
        super(isBlack, game);
    }

    @Override
    public List<Integer> getPossibleMoves() {
        return null;
    }
}
