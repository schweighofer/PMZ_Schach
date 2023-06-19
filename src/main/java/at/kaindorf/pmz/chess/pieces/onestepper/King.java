package at.kaindorf.pmz.chess.pieces.onestepper;

import at.kaindorf.pmz.bl.Game;
import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.chess.pieces.Empty;
import at.kaindorf.pmz.chess.pieces.morestepper.Rook;
import at.kaindorf.pmz.pojos.logic.MutableInteger;

import java.util.ArrayList;
import java.util.List;

import static at.kaindorf.pmz.bl.Game.*;


/**
 * @Author Marcus Schweighofer
 * Created on 29.03.2023.
 * Class: King.java
 */

public class King extends Piece {
    public King(Boolean isWhite, Game game, int moveCount) {
        super(isWhite, game, moveCount);
    }

    @Override
    public List<Integer> obtainPossibleMoves() {
        List<Integer> possibleMoves = obtainTheoreticalMoves();
        final int position = game.getPosition(this);



        // check if field is not blocked by other piece
        // Problem: Pawn geht gar nicht, König kann schlagen auch wenn nicht gehen sollte
        // Andere Idee: Festellen ob König schach ist, dann jeden move simulieren (?)
        // ! Ja muss so

        possibleMoves.removeIf(i -> game.checkCheck(this, i));

        return possibleMoves;
    }

    public List<Integer> obtainTheoreticalMoves() {
        List<Integer> possibleMoves = new ArrayList<>();
        final int position = game.getPosition(this);

        MutableInteger assumedPosition = new MutableInteger(position - 1);
        if (assumedPosition.v() >= (int)(position * DIVISOR_LINE_SIZE) * LINE_SIZE) {
           handleMove(assumedPosition, -1, possibleMoves);
        }

        assumedPosition.v(position + 1);
        if (assumedPosition.v() < (int)(position * DIVISOR_LINE_SIZE) * LINE_SIZE + LINE_SIZE) {
            handleMove(assumedPosition, 1, possibleMoves);
        }

        assumedPosition.v(position - LINE_SIZE);
        if ((int)((assumedPosition.v() + LINE_SIZE) * DIVISOR_LINE_SIZE) > 0) {
            handleMove(assumedPosition, -LINE_SIZE, possibleMoves);
        }

        assumedPosition.v(position + LINE_SIZE);
        if ((int)((assumedPosition.v()) * DIVISOR_LINE_SIZE) * LINE_SIZE < FIELD_SIZE) {
            handleMove(assumedPosition, LINE_SIZE, possibleMoves);
        }

        assumedPosition.v(position - LINE_SIZE - 1);
        if (((assumedPosition.v() + LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition.v() >= 0)) {
            handleMove(assumedPosition, -(LINE_SIZE + 1), possibleMoves);
        }

        assumedPosition.v(position - LINE_SIZE + 1);
        if (((assumedPosition.v() + LINE_SIZE - 1) % LINE_SIZE != LINE_SIZE - 1) && (assumedPosition.v() >= 0)) {
            handleMove(assumedPosition, -(LINE_SIZE - 1), possibleMoves);
        }

        assumedPosition.v(position + LINE_SIZE - 1);
        if (((assumedPosition.v() - LINE_SIZE + 1) % LINE_SIZE != 0) && (assumedPosition.v() < FIELD_SIZE)) {
            handleMove(assumedPosition, LINE_SIZE - 1, possibleMoves);
        }

        assumedPosition.v(position + LINE_SIZE + 1);
        if (((assumedPosition.v() - LINE_SIZE - 1) % LINE_SIZE != LINE_SIZE - 1) && (assumedPosition.v() < FIELD_SIZE)) {
            handleMove(assumedPosition, LINE_SIZE + 1, possibleMoves);
        }

        //rochade:
        if(getMoveCount() == 0){

            if(!isWhite && !game.isCheck(!isWhite)){
                //rochade rechts für schwarz:
                if(getPosition() == 59 && game.getBoard().get(63) instanceof Rook && game.getBoard().get(63).getMoveCount() == 0){//schauen ob rook noch 0 gefahren ist und noch dort ist wo er sein soll
                    if(game.getBoard().get(62) instanceof Empty && game.getBoard().get(61) instanceof Empty && game.getBoard().get(60) instanceof Empty){//schauen ob dazwischen Frei
                        possibleMoves.add(61);
                    }
                }
                //rochade links für schwarz:
                if(getPosition() == 59 && game.getBoard().get(56) instanceof Rook && game.getBoard().get(56).getMoveCount() == 0){
                    if(game.getBoard().get(57) instanceof Empty && game.getBoard().get(58) instanceof Empty){//schauen ob dazwischen Frei
                        possibleMoves.add(57);
                    }
                }
            }
            if(isWhite && !game.isCheck(isWhite)){
                //rochade links für weiß:
                if(getPosition() == 3 && game.getBoard().get(0) instanceof Rook && game.getBoard().get(0).getMoveCount() == 0){//schauen ob rook noch 0 gefahren ist und noch dort ist wo er sein soll
                    if(game.getBoard().get(1) instanceof Empty && game.getBoard().get(2) instanceof Empty){//schauen ob dazwischen Frei
                        possibleMoves.add(1);
                    }
                }
                //rochade rechts für weiß:
                if(getPosition() == 3 && game.getBoard().get(7) instanceof Rook && game.getBoard().get(7).getMoveCount() == 0){
                    if(game.getBoard().get(6) instanceof Empty && game.getBoard().get(5) instanceof Empty && game.getBoard().get(4) instanceof Empty){//schauen ob dazwischen Frei
                        possibleMoves.add(5);
                    }
                }
            }
        }

        return possibleMoves;
    }
}
