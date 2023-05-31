package at.kaindorf.pmz.chess.pieces.onestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.pieces.OneStepPiece;

import java.util.ArrayList;
import java.util.List;

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
        // Problem: Pawn geht gar nicht, König kann schlagen auch wenn nicht gehen sollte
        // Andere Idee: Festellen ob König schach ist, dann jeden move simulieren (?)
        // ! Ja muss so

        possibleMoves.removeIf(i -> game.checkCheck(this, i));

        return possibleMoves;
    }
}
