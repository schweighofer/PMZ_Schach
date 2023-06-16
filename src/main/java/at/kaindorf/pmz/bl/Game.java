package at.kaindorf.pmz.bl;

import at.kaindorf.pmz.chess.FieldState;
import at.kaindorf.pmz.chess.Piece;
import at.kaindorf.pmz.chess.pieces.Empty;
import at.kaindorf.pmz.chess.pieces.morestepper.Bishop;
import at.kaindorf.pmz.chess.pieces.morestepper.Queen;
import at.kaindorf.pmz.chess.pieces.morestepper.Rook;
import at.kaindorf.pmz.chess.pieces.onestepper.King;
import at.kaindorf.pmz.chess.pieces.onestepper.Knight;
import at.kaindorf.pmz.chess.pieces.onestepper.Pawn;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @Author Marcus Schweighofer
 * Created on 24.03.2023.
 * Class: Game.java
 */

public class Game {
    public static final Integer LINE_SIZE = 8;
    public static final Float DIVISOR_LINE_SIZE = 1 / (float)(LINE_SIZE);
    public static final Integer FIELD_SIZE = LINE_SIZE * LINE_SIZE;

    private boolean hasWhiteTurn = false;

    private final List<Piece> board;

    private Integer lastPiece;
    private List<Integer> lastPossibleMoves;

    public Game() {
        this.board = new ArrayList<>();
        setupBoard();
    }

    private void setupBoard() {
        // top row
        board.add(new Rook(    true,    this));
        board.add(new Knight(  true,    this));
        board.add(new Bishop(  true,    this));
        board.add(new Queen(   true,    this));
        board.add(new King(    true,    this));
        board.add(new Bishop(  true,    this));
        board.add(new Knight(  true,    this));
        board.add(new Rook(    true,    this));

        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));
        board.add(new Pawn(    true,    this));

        // null rows
        for (int i = 16; i < 48; i++) {
            board.add(new Empty(this));
        }

        // bottom row
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));
        board.add(new Pawn(    false,   this));

        board.add(new Rook(    false,   this));
        board.add(new Knight(  false,   this));
        board.add(new Bishop(  false,   this));
        board.add(new Queen(   false,   this));
        board.add(new King(    false,   this));
        board.add(new Bishop(  false,   this));
        board.add(new Knight(  false,   this));
        board.add(new Rook(    false,   this));
    }

    public int getPosition(Piece piece) {
        return board.indexOf(piece);
    }

    public FieldState getFieldState(int index) {
        if (board.get(index) instanceof Empty) {
            return FieldState.NULL;
        }
        Piece piece = board.get(index);
        String fieldState = piece.isBlack() ? "BLACK" : "WHITE";
        if (piece instanceof King) {
            fieldState += "_KING";
        }
        return FieldState.valueOf(fieldState);
    }

    public List<Piece> getAllOtherPieces(Piece piece) {
        List<Piece> allOtherPieces = new ArrayList<>(board);
        allOtherPieces = allOtherPieces.stream()
                .filter(p -> !(p instanceof Empty))
                .collect(Collectors.toList());
        allOtherPieces.removeIf(p -> p.isBlack() == piece.isBlack());
        return allOtherPieces;
    }

    public List<Integer> getPossibleMoves(int piecePosition) {
        lastPiece = piecePosition;
        lastPossibleMoves = board.get(lastPiece).obtainPossibleMoves();

        /*Piece toMove = board.get(piecePosition);

        for (Integer move : lastPossibleMoves) {
            Piece backup = board.get(move);
            board.set(move, toMove);
            board.set(piecePosition, new Empty(this));
            if (checkCheck(toMove.isBlack())) {
                lastPossibleMoves.remove(move);
            }
            board.set(move, backup);
            board.set(piecePosition, toMove);

            if (backup instanceof King) {
                lastPossibleMoves.remove(move);
            }
        }

        return lastPossibleMoves;*/
        return legalMoves(board.get(lastPiece));
    }

    public boolean move(int desiredPosition) {
        if (!lastPossibleMoves.contains(desiredPosition)) {
            return false;
        }
        hasWhiteTurn = !hasWhiteTurn;
        Piece toMove = board.get(lastPiece);
        board.set(desiredPosition, toMove);
        board.set(lastPiece, new Empty(this));

        return true;
    }

    public List<Piece> getBoard() {
        return new ArrayList<>(this.board);
    }

    // DEBUG

    public void printField() {
        for (int i = 0; i < LINE_SIZE; i++) {
            for (int j = 0; j < LINE_SIZE; j++) {
                if (board.get(i * LINE_SIZE + j) instanceof Empty) {
                    System.out.print("\uF030");
                } else {
                    System.out.print(board.get(i * LINE_SIZE + j).getChar());
                }
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }

    public Piece getPiece(int index) {
        return board.get(index);
    }

    public boolean isHasWhiteTurn() {
        return hasWhiteTurn;
    }

    // TODO: könig restrictions fertig machen, [Pawn fixen den 2er sprung am anfange], check, und checkMate, nach den vier sachen fertig

    public boolean hasEnded() {
        return (checkCheck(true) && checkCheckMate(true)) || (checkCheck(false) && checkCheckMate(false));
    }

    public Boolean checkCheck(boolean forBlack) {
        King king = (King) board.stream()
                .filter(p -> p instanceof King)
                .filter(p -> p.isBlack() == forBlack)
                .findFirst()
                .get();
        return checkCheck(king);
    }

    public Boolean checkCheck(King king) {
        return checkCheck(king, getPosition(king));
    }

    public Boolean checkCheck(King king, Integer hypothteicalPosition) {
        List<Piece> allOtherPieces = getAllOtherPieces(king);
        List<Integer> possibleEnemyMoves;
        for (Piece p : allOtherPieces) {
            // vallah der scheiß geht immer noch nicht aber ja
            if (p instanceof Pawn) {
                possibleEnemyMoves = new ArrayList<>();
                int direction = (p.isBlack() ? 1 : -1);
                // left
                if ((getPosition(p) % LINE_SIZE != 0)) {
                    int possibleMove = hypothteicalPosition + LINE_SIZE * direction - 1;
                    possibleEnemyMoves.add(possibleMove);
                }
                // right
                if ((getPosition(p) % LINE_SIZE != 7)) {
                    int possibleMove = hypothteicalPosition + LINE_SIZE * direction + 1;
                    possibleEnemyMoves.add(possibleMove);
                }

            } else if (p instanceof King) {
                possibleEnemyMoves = ((King) p).obtainTheoreticalMoves();
            } else {
                possibleEnemyMoves = p.obtainPossibleMoves();
            }

            System.out.println(board.indexOf(p) + " - " + possibleEnemyMoves);
            System.out.println(hypothteicalPosition);

            if (possibleEnemyMoves.contains(hypothteicalPosition)) {
                return true;
            }
        }
        return false;
    }

    public Boolean checkCheckMate(boolean forBlack) {
        // Muss vorher check sein
        King king = (King) board.stream()
                .filter(p -> p instanceof King)
                .filter(p -> p.isBlack() == forBlack)
                .findFirst()
                .get();

        if (king.obtainPossibleMoves().isEmpty()) {
            return true;
        }

        return false;
    }

    public List<Integer> legalMoves(Piece piece){
        List<Integer> legalMoves = new ArrayList<>(); //list of legalMoves (not all possible are legal), gets filled in for loop
        List<Integer> help = new ArrayList<>(lastPossibleMoves);
        Integer outgoingPosition = piece.getPosition(); // Ausgangsposition des schlageneden piece
        Piece killed; // Piece that could get killed
        for(Integer moveToPosition : piece.obtainPossibleMoves()){ //loop through all possibleMoves of piece for checking if legal
            killed = board.get(moveToPosition);
            move(moveToPosition);
            List<Piece> allOtherPieces = getAllOtherPieces(piece);
            List<Integer> allMovesOfOtherPieces = new ArrayList<>();
            allOtherPieces.forEach(enemyPiece -> enemyPiece.obtainPossibleMoves().forEach(enemyMove -> allMovesOfOtherPieces.add(enemyMove)));//put allPossibleMoves of enemy in one list

            King king = (King) board.stream()
                    .filter(p -> p instanceof King)
                    .filter(p -> p.isBlack() == piece.isBlack())
                    .findFirst()
                    .get();
            if(!allMovesOfOtherPieces.contains(king.getPosition())){//see if position of own king is in the list of allMovesOfOtherPieces
                legalMoves.add(moveToPosition);
            }
            board.set(outgoingPosition, piece); //simulation rückgaängig machen
            board.set(moveToPosition, killed);
        }
        lastPossibleMoves = help;
        return legalMoves;
    }

    /*public boolean isCheckMate(boolean isBlack){

    }*/
    //todo: List<Integer> legalMoves() function
        //ALWAYS called before sending out possible moves of selected piece
        //(deine move Validation dinger unnötig dann?)
            /*
                loop through all possibleMoves of piece
                    put the piece in the position via normal move function
                    go through whole board
                        filter for pieces of other color
                            put allPossibleMoves of them in one list
                    see if position of own king is in the list of allPossibleMoves
                    implement function returnSimulationMove
                        this function will move the Piece back to its prior position if it is an illegal move

             */

    //todo: boolean isCheckMate() function
        //always called when you become onTurn
        /*
                go through all own pieces
                    get all their legalMoves
                if there are no legalMoves -> checkmate
         */
    //todo: boolean isCheck() function
        //always called when you become onTurn
        /*
                go through whole board
                        filter for pieces of other color
                            put allPossibleMoves that are also isLegalMove() == true of them in one list
                    if position of king is in the list of allPossibleMoves
                        then is checkmate

         */
    //todo: or maybe just use a chess library (JChess as exp.)
    //todo: time
    /*
                request for set max time
                start blackTimer for white when black made their first move
                start whiteTimer for black when white made their second move
                when one time > max time that side has lost
     */
    //todo: transform pawn
    /*
                when pawn has reached backrow
                automatically transform to queen
                    (asking for choice to much work)
     */
    //todo: rochade
    /*
                in possibleMoves of King
                    if king and rock have not moved and king is not in check and will not be put in check via rochade
                        make them able to switch
     */
    //todo: enpassant
    /*
                in possibleMoves of pawn
                    if pawn is in fifth row and an enemy pawn is beside you
                    make pawn able to hit behind enemy pawn
     */

    //todo: implement stockfish (Piet Hein)
    /*
                find stockfish library
                adapt board to stockfish library accepted board
                get bestMove
     */

}
