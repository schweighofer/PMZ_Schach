package at.kaindorf.pmz.pojos.chess.pieces.onestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.pojos.chess.Piece;
import at.kaindorf.pmz.pojos.chess.pieces.OneStepPiece;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author Marcus Schweighofer
 * Created on 24.03.2023.
 * Class: Horse.java
 */

public class Knight extends OneStepPiece {
    public Knight(Boolean isBlack, Game game) {
        super(isBlack, game);
    }

    @Override
    public List<Integer> getPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);



        return possibleMoves;
    }
}
