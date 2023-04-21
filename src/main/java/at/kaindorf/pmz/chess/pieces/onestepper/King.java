package at.kaindorf.pmz.chess.pieces.onestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.chess.pieces.OneStepPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static at.kaindorf.pmz.chess.pieces.MoveType.*;

/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: King.java
 */

public class King extends OneStepPiece {
    public King(Boolean isBlack, Game game) {
        super(isBlack, game);
    }

    @Override
    public List<Integer> obtainPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);

        moves(position, possibleMoves, LEFT, RIGHT, DOWN, UP, LEFT_UP, RIGHT_UP, LEFT_DOWN, RIGHT_DOWN);

        // check if field is not blocked by other piece

        List<Piece> allOtherPieces = game.getAllOtherPieces(this);
        for (Piece p : allOtherPieces) {
            for (Integer i : p.obtainPossibleMoves().stream()
                    .filter(k -> !k.equals(position))
                    .collect(Collectors.toList())
            ) {
                possibleMoves.remove(i);
            }
        }

        return possibleMoves;
    }
}