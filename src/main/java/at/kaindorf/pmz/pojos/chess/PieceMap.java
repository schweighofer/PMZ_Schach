package at.kaindorf.pmz.pojos.chess;

import at.kaindorf.pmz.pojos.chess.pieces.morestepper.Bishop;
import at.kaindorf.pmz.pojos.chess.pieces.onestepper.King;
import at.kaindorf.pmz.pojos.chess.pieces.onestepper.Knight;
import at.kaindorf.pmz.pojos.chess.pieces.onestepper.Pawn;
import at.kaindorf.pmz.pojos.chess.pieces.morestepper.Queen;
import at.kaindorf.pmz.pojos.chess.pieces.morestepper.Rook;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author Marcus Schweighofer
 * Created on 24.03.2023.
 * Class: PieceMap.java
 */

public class PieceMap {
    private static Map<Class<?>, Character> pieceMap = new HashMap<>();

    static {
        pieceMap.put(King.class,    '♔');
        pieceMap.put(Queen.class,   '♕');
        pieceMap.put(Rook.class,    '♖');
        pieceMap.put(Bishop.class,  '♗');
        pieceMap.put(Knight.class,  '♘');
        pieceMap.put(Pawn.class,    '♙');
    }

    public static Character get(Piece piece) {
        return (char)(pieceMap.get(piece.getClass()).charValue() - 6 * Boolean.compare(piece.isBlack(), true));
    }
}
