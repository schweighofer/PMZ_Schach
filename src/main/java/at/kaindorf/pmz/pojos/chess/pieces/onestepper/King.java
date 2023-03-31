package at.kaindorf.pmz.pojos.chess.pieces.onestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.pojos.chess.Piece;
import at.kaindorf.pmz.pojos.chess.pieces.OneStepPiece;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.pojos.chess.pieces.MoveType.*;

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
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);

        step(position, possibleMoves, LEFT, RIGHT, DOWN, UP, LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN);

        // check if field is not blocked by other piece

        List<Piece> allOtherPieces = game.getAllOtherPieces(this);
        for (Piece p : allOtherPieces) {
            for (Integer i : p.getPossibleMoves()) {
                possibleMoves.remove(i);
            }
        }

        return possibleMoves;
    }
}
