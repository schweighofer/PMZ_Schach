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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private int globalMoveCount = 0;

    public Game() {
        this.board = new ArrayList<>();
        setupBoard();
    }

    private synchronized void setupBoard() {
        // top row
        board.add(new Rook(    true,    this, 0));
        board.add(new Knight(  true,    this, 0));
        board.add(new Bishop(  true,    this, 0));
        board.add(new King(    true,    this, 0));
        board.add(new Queen(   true,    this, 0));
        board.add(new Bishop(  true,    this, 0));
        board.add(new Knight(  true,    this, 0));
        board.add(new Rook(    true,    this, 0));

        board.add(new Pawn(    true,    this, 0));
        board.add(new Pawn(    true,    this, 0));
        board.add(new Pawn(    true,    this, 0));
        board.add(new Pawn(    true,    this, 0));
        board.add(new Pawn(    true,    this, 0));
        board.add(new Pawn(    true,    this, 0));
        board.add(new Pawn(    true,    this, 0));
        board.add(new Pawn(    true,    this, 0));

        // null rows
        for (int i = 16; i < 48; i++) {
            board.add(new Empty(this));
        }

        // bottom row
        board.add(new Pawn(    false,   this, 0));
        board.add(new Pawn(    false,   this, 0));
        board.add(new Pawn(    false,   this, 0));
        board.add(new Pawn(    false,   this, 0));
        board.add(new Pawn(    false,   this, 0));
        board.add(new Pawn(    false,   this, 0));
        board.add(new Pawn(    false,   this, 0));
        board.add(new Pawn(    false,   this, 0));

        board.add(new Rook(    false,   this, 0));
        board.add(new Knight(  false,   this, 0));
        board.add(new Bishop(  false,   this, 0));
        board.add(new King(    false,   this, 0));
        board.add(new Queen(   false,   this, 0));
        board.add(new Bishop(  false,   this, 0));
        board.add(new Knight(  false,   this, 0));
        board.add(new Rook(    false,   this, 0));

    }

    public synchronized int getPosition(Piece piece) {
        return board.indexOf(piece);
    }

    public synchronized FieldState getFieldState(int index) {
        if (board.get(index) instanceof Empty) {
            return FieldState.NULL;
        }
        Piece piece = board.get(index);
        String fieldState = piece.isWhite() ? "BLACK" : "WHITE";
        if (piece instanceof King) {
            fieldState += "_KING";
        }
        return FieldState.valueOf(fieldState);
    }

    public synchronized List<Piece> getAllOtherPieces(Piece piece) {
        List<Piece> allOtherPieces = new ArrayList<>(board);
        allOtherPieces = allOtherPieces.stream()
                .filter(p -> !(p instanceof Empty))
                .collect(Collectors.toList());
        allOtherPieces.removeIf(p -> p.isWhite() == piece.isWhite());
        return allOtherPieces;
    }

    public synchronized List<Piece> getAllOwnPiecesWithoutKing(Piece piece) {
        return getAllOwnPiecesWithoutKing(piece.isWhite());
    }

    public synchronized List<Piece> getAllOwnPiecesWithoutKing(boolean color) {
        List<Piece> allOtherPieces = new ArrayList<>(board);
        allOtherPieces = allOtherPieces.stream()
                .filter(p -> !(p instanceof Empty))
                .collect(Collectors.toList());
        allOtherPieces.removeIf(p -> p.isWhite() != color);
        allOtherPieces.removeIf(p -> p instanceof King);
        return allOtherPieces;
    }
    public synchronized List<Piece> getAllOwnPiecesWithKing(boolean color) {
        List<Piece> allOtherPieces = new ArrayList<>(board);
        allOtherPieces = allOtherPieces.stream()
                .filter(p -> !(p instanceof Empty))
                .collect(Collectors.toList());
        allOtherPieces.removeIf(p -> p.isWhite() != color);
        return allOtherPieces;
    }

    public synchronized List<Integer> getPossibleMoves(int piecePosition, Piece lastPiece) {


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
        return legalMoves(lastPiece);
    }

    public synchronized Map<Integer, Piece> simMove(int desiredPosition, int lastPiece) {
        Piece toMove = board.get(lastPiece);

        Map<Integer, Piece> history = new HashMap<>();
        history.put(desiredPosition, board.get(desiredPosition));
        history.put(lastPiece, toMove);
        boolean specialMove = false;


        //castle:
        if(toMove instanceof King){
            if(!toMove.isWhite() && !isCheck(!toMove.isWhite())){
                //rochade rechts für schwarz:
                if(desiredPosition == 61){
                    if(toMove.getPosition() == 59 && board.get(63) instanceof Rook && board.get(63).getMoveCount() == 0){//schauen ob rook noch 0 gefahren ist und noch dort ist wo er sein soll
                        if(board.get(62) instanceof Empty && board.get(61) instanceof Empty && board.get(60) instanceof Empty){//schauen ob dazwischen Frei
                            history.put(59, toMove);
                            history.put(63, board.get(63));
                            history.put(60, new Empty(this));
                            history.put(61, new Empty(this));
                            history.put(62, new Empty(this));


                            board.set(toMove.getPosition(), new Empty(this));
                            board.set(61, toMove);
                            board.set(60, board.get(63));
                            board.set(63, new Empty(this));
                            specialMove = true;
                        }
                    }
                }

                //rochade links für schwarz:
                if(desiredPosition == 57){
                    if(toMove.getPosition() == 59 && board.get(56) instanceof Rook && board.get(56).getMoveCount() == 0){
                        if(board.get(57) instanceof Empty && board.get(58) instanceof Empty){//schauen ob dazwischen Frei
                            history.put(59, toMove);
                            history.put(56, board.get(56));
                            history.put(57, new Empty(this));
                            history.put(58, new Empty(this));

                            board.set(toMove.getPosition(), new Empty(this));
                            board.set(57, toMove);
                            board.set(58, board.get(56));
                            board.set(56, new Empty(this));
                            specialMove = true;
                        }
                    }
                }

            }
            if(toMove.isWhite() && !isCheck(toMove.isWhite())){
                //rochade links für weiß:
                if(desiredPosition == 1){
                    if(toMove.getPosition() == 3 && board.get(0) instanceof Rook && board.get(0).getMoveCount() == 0){//schauen ob rook noch 0 gefahren ist und noch dort ist wo er sein soll
                        if(board.get(1) instanceof Empty && board.get(2) instanceof Empty){//schauen ob dazwischen Frei
                            history.put(3, toMove);
                            history.put(0, board.get(0));
                            history.put(1, new Empty(this));
                            history.put(2, new Empty(this));

                            board.set(toMove.getPosition(), new Empty(this));
                            board.set(1, toMove);
                            board.set(2, board.get(0));
                            board.set(0, new Empty(this));
                            specialMove = true;
                        }
                    }
                }

                //rochade rechts für weiß:
                if(desiredPosition == 5){
                    if(toMove.getPosition() == 3 && board.get(7) instanceof Rook && board.get(7).getMoveCount() == 0){
                        if(board.get(6) instanceof Empty && board.get(5) instanceof Empty && board.get(4) instanceof Empty){//schauen ob dazwischen Frei
                            history.put(3, toMove);
                            history.put(7, board.get(7));
                            history.put(4, new Empty(this));
                            history.put(5, new Empty(this));
                            history.put(6, new Empty(this));

                            board.set(toMove.getPosition(), new Empty(this));
                            board.set(5, toMove);
                            board.set(4, board.get(7));
                            board.set(7, new Empty(this));
                            specialMove = true;
                        }
                    }
                }

            }
        }
        //pawn transformation:
        if (toMove instanceof Pawn && ((!toMove.isWhite() && desiredPosition <= 7) || (toMove.isWhite() && desiredPosition >= 56))){//transformation of pawn to queen
            specialMove = true;
            board.set(toMove.getPosition(), new Empty(this));
            board.set(desiredPosition, new Queen(toMove.isWhite(), this, 0));//automatically transform to queen because choice is too much work

        }
        //en passant:

        if(toMove instanceof Pawn){
            if(toMove.isWhite()){
                if(toMove.getPosition() >= 33 && toMove.getPosition() <= 39){
                    if(desiredPosition == toMove.getPosition()+7){
                        if(board.get(toMove.getPosition()-1) instanceof Pawn && !board.get(toMove.getPosition()-1).isWhite() && board.get(toMove.getPosition()-1).getMoveCount() == 1 && board.get(toMove.getPosition()+7) instanceof Empty){
                            history.put(toMove.getPosition() - 1, board.get(toMove.getPosition() - 1));
                            board.set(toMove.getPosition() - 1, new Empty(this));
                        }
                    }

                }
                if(toMove.getPosition() >= 32 && toMove.getPosition() <= 38){
                    if(desiredPosition == toMove.getPosition()+9){
                        if(board.get(toMove.getPosition()+1) instanceof Pawn && !board.get(toMove.getPosition()+1).isWhite() && board.get(toMove.getPosition()+1).getMoveCount() == 1 && board.get(toMove.getPosition()+9) instanceof Empty){
                            history.put(toMove.getPosition() + 1, board.get(toMove.getPosition() + 1));
                            board.set(toMove.getPosition() + 1, new Empty(this));
                        }
                    }

                }
            }
            if(!toMove.isWhite()){
                if(toMove.getPosition() >= 25 && toMove.getPosition() <= 31){
                    if(desiredPosition == toMove.getPosition()-9){
                        if(board.get(toMove.getPosition()-1) instanceof Pawn && board.get(toMove.getPosition()-1).isWhite() && board.get(toMove.getPosition()-1).getMoveCount() == 1 && board.get(toMove.getPosition()-9) instanceof Empty){
                            history.put(toMove.getPosition() - 1, board.get(toMove.getPosition() - 1));
                            board.set(toMove.getPosition() - 1, new Empty(this));
                        }
                    }

                }
                if(toMove.getPosition() >= 24 && toMove.getPosition() <= 30){
                    if(desiredPosition == toMove.getPosition()-7){
                        if(board.get(toMove.getPosition()+1) instanceof Pawn && board.get(toMove.getPosition()+1).isWhite() && board.get(toMove.getPosition()+1).getMoveCount() == 1 && board.get(toMove.getPosition()-7) instanceof Empty){
                            history.put(toMove.getPosition() + 1, board.get(toMove.getPosition() + 1));
                            board.set(toMove.getPosition() + 1, new Empty(this));
                        }
                    }
                }
            }
        }

        if(!specialMove){
            board.set(desiredPosition, toMove);
        }
        board.set(lastPiece, new Empty(this));

        return history;
    }
    public synchronized boolean move(int desiredPosition, int lastPiece) {

        hasWhiteTurn = !hasWhiteTurn;
        globalMoveCount++;
        Piece toMove = board.get(lastPiece);
        simMove(desiredPosition, lastPiece);

        /*DebugPiece debugPiece = new DebugPiece(false,this,0);
        String debugText = (toMove.isWhite()) + "\n" +
                "" + (board.get(toMove.getPosition()-1).isWhite()) + " " + (board.get(toMove.getPosition()+1).isWhite()) + "\n" +
                "" + (board.get(toMove.getPosition()-1) instanceof Pawn) + " " + (board.get(toMove.getPosition()+1) instanceof Pawn) + "\n" +
                "" +  (board.get(toMove.getPosition()-1).getMoveCount()) + " " + (board.get(toMove.getPosition()+1).getMoveCount()) + "\n";
        debugPiece.setDebugText(debugText);
        board.set(30, debugPiece);*/


        toMove.setMoveCount(toMove.getMoveCount() + 1);

        return true;
    }

    public synchronized List<Piece> getBoard() {
        return new ArrayList<>(this.board);
    }

    // DEBUG

    public synchronized void printField() {
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

    public synchronized Piece getPiece(int index) {
        return board.get(index);
    }

    public synchronized boolean isHasWhiteTurn() {
        return hasWhiteTurn;
    }


    //todo: problem that white and black access function at same time and move pieces wrongly around?
    private boolean isHasEndedInUse = false;

    public synchronized boolean hasEnded() {
        while (isHasEndedInUse) {
            try {
                wait(); // Wait until the previous call finishes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isHasEndedInUse = true;

        boolean hasEnded = (isCheckMate(true) || isCheckMate(false) || isPatt());

        isHasEndedInUse = false;
        notify();

        return hasEnded;
    }

    public synchronized Boolean checkCheck(boolean forBlack) {
        King king = (King) board.stream()
                .filter(p -> p instanceof King)
                .filter(p -> p.isWhite() == forBlack)
                .findFirst()
                .get();
        return checkCheck(king);
    }

    public synchronized Boolean checkCheck(King king) {
        return checkCheck(king, getPosition(king));
    }

    public synchronized Boolean checkCheck(King king, Integer hypothteicalPosition) {
        List<Piece> allOtherPieces = getAllOtherPieces(king);
        List<Integer> possibleEnemyMoves;
        for (Piece p : allOtherPieces) {
            // vallah der scheiß geht immer noch nicht aber ja
            if (p instanceof Pawn) {
                possibleEnemyMoves = new ArrayList<>();
                int direction = (p.isWhite() ? 1 : -1);
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

    public synchronized Boolean checkCheckMate(boolean forBlack) {
        // Muss vorher check sein
        King king = (King) board.stream()
                .filter(p -> p instanceof King)
                .filter(p -> p.isWhite() == forBlack)
                .findFirst()
                .get();

        if (king.obtainPossibleMoves().isEmpty()) {
            return true;
        }

        return false;
    }

    private boolean isLegalMovesInUse = false;
    public synchronized List<Integer> legalMoves(Piece piece){
        while (isLegalMovesInUse) {
            try {
                wait(); // Wait until the previous call finishes
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        isLegalMovesInUse = true;

        List<Integer> legalMoves = new ArrayList<>(); //list of legalMoves (not all possible are legal), gets filled in for loop
        Map<Integer, Piece> history;
        for(Integer moveToPosition : piece.obtainPossibleMoves()){ //loop through all possibleMoves of piece for checking if legal
            history = simMove(moveToPosition, getPosition(piece));

            List<Piece> allOtherPieces = getAllOtherPieces(piece);
            List<Integer> allMovesOfOtherPieces = new ArrayList<>();
            allOtherPieces.forEach(enemyPiece -> enemyPiece.obtainPossibleMoves().forEach(enemyMove -> allMovesOfOtherPieces.add(enemyMove)));//put allPossibleMoves of enemy in one list

            King king = (King) board.stream()
                    .filter(p -> p instanceof King)
                    .filter(p -> p.isWhite() == piece.isWhite())
                    .findFirst()
                    .get();
            if(!allMovesOfOtherPieces.contains(king.getPosition())){//see if position of own king is in the list of allMovesOfOtherPieces
                legalMoves.add(moveToPosition);
            }
            for(Integer positionOfKilled : history.keySet()){
                board.set(positionOfKilled, history.get(positionOfKilled));
            }
            history = new HashMap<>();
            //board.set(initialPosition, piece);
        }

        isLegalMovesInUse = false;
        notify();

        return legalMoves;
    }

    public synchronized boolean isCheck(boolean isWhite){
        List<Piece> allOtherPieces = getAllOwnPiecesWithoutKing(!isWhite); //getting all enemy pieces (getAllOwnPieces reversed)
        List<Integer> allMovesOfOtherPieces = new ArrayList<>();
        allOtherPieces.forEach(enemyPiece -> enemyPiece.obtainPossibleMoves().forEach(enemyMove -> allMovesOfOtherPieces.add(enemyMove)));//put allPossibleMoves of enemy in one list

        King king = (King) board.stream()//getKing for seeing if check
                .filter(p -> p instanceof King)
                .filter(p -> p.isWhite() == isWhite)
                .findFirst()
                .get();

        return allMovesOfOtherPieces.contains(king.getPosition());

    }

    public synchronized boolean isCheckMate(boolean isWhite){
        List<Piece> allOwnPieces = getAllOwnPiecesWithKing(isWhite);//get all own pieces
        List<Integer> allMovesOfOwnPieces = new ArrayList<>();
        allOwnPieces.forEach(ownPiece -> legalMoves(ownPiece).forEach(ownMove -> allMovesOfOwnPieces.add(ownMove)));

        return isCheck(isWhite) && allMovesOfOwnPieces.isEmpty(); //wenn könig im schach und keine legalenzüge mehr dann is erst schachmatt
    }
    public synchronized boolean isPatt(){
        List<Piece> allOwnPieces = getAllOwnPiecesWithKing(true);//get all own pieces
        List<Integer> allMovesOfOwnPieces = new ArrayList<>();
        allOwnPieces.forEach(ownPiece -> legalMoves(ownPiece).forEach(ownMove -> allMovesOfOwnPieces.add(ownMove)));

        List<Piece> allOtherPieces = getAllOwnPiecesWithKing(false);//get all other pieces
        List<Integer> allMovesOfOtherPieces = new ArrayList<>();
        allOtherPieces.forEach(otherPiece -> legalMoves(otherPiece).forEach(otherMove -> allMovesOfOtherPieces.add(otherMove)));


        return allMovesOfOwnPieces.isEmpty() || allMovesOfOtherPieces.isEmpty(); //wenn keine legalen züge mehr is patt(unentschieden
    }
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
