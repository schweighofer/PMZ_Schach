package at.kaindorf.pmz.chess.pieces.onestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.Piece;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: King.java
 */

public class King extends Piece {
    public King(Boolean isBlack, Game game) {
        super(isBlack, game);
    }

    @Override
    public List<Integer> obtainPossibleMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);



        // check if field is not blocked by other piece
        // Problem: Pawn geht gar nicht, König kann schlagen auch wenn nicht gehen sollte
        // Andere Idee: Festellen ob König schach ist, dann jeden move simulieren (?)
        // ! Ja muss so

        possibleMoves.removeIf(i -> game.checkCheck(this, i));

        return possibleMoves;
    }
}
