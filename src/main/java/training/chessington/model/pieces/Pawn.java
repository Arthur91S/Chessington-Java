package training.chessington.model.pieces;

import training.chessington.model.Board;
import training.chessington.model.Coordinates;
import training.chessington.model.Move;
import training.chessington.model.PlayerColour;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends AbstractPiece {
    public Pawn(PlayerColour colour) {
        super(Piece.PieceType.PAWN, colour);
    }

    @Override
    public List<Move> getAllowedMoves(Coordinates from, Board board) {
        List<Move> allowedMoves = new ArrayList<>();

        if (isWhite() && hasNoPiecesAbove(from, board)){
            addMove(allowedMoves, from,from.plus(-1, 0));
        }

        if (isBlack() && hasNoPiecesBelow(from, board)){
            addMove(allowedMoves, from,from.plus(+1, 0));
        }

        if (isStartingPosition(from) && !allowedMoves.isEmpty()){
            if (isWhite() && hasNoPiecesAboveTwoSteps(from,board)){
                addMove(allowedMoves, from,from.plus(-2, 0));
            }
            if (isBlack() && hasNoPiecesBelowTwoSteps(from, board)){
                    addMove(allowedMoves, from,from.plus(+2, 0));
            }
        }

        canCaptureRightWhiteEnemy(from,board,allowedMoves);
        canCaptureLeftWhiteEnemy(from,board,allowedMoves);
        canCaptureRightBlackEnemy(from,board,allowedMoves);
        canCaptureLeftBlackEnemy(from,board,allowedMoves);

        return allowedMoves;
    }

    public boolean isStartingPosition(Coordinates from){

        if ( from.getRow() == 1 && isBlack() ){
            return true;
        }
        if ( from.getRow() == 6 && isWhite() ){
            return true;
        }
        return false;
    }

    public boolean isWhite(){
        return colour == PlayerColour.WHITE;
    }
    public boolean isBlack(){
        return colour == PlayerColour.BLACK;
    }

    public boolean hasNoPiecesAbove(Coordinates from, Board board){
        if( isValidMove(from.plus(-1,0))){
            return board.get(from.plus(-1 ,0)) == null;
        }
        return false;
    }

    public boolean hasNoPiecesAboveTwoSteps(Coordinates from, Board board){
        return board.get(from.plus(-2 ,0)) == null;
    }

    public boolean hasNoPiecesBelow(Coordinates from, Board board) {
        if (isValidMove(from.plus(+1,0))) {
            return board.get(from.plus(+1 ,0)) == null;
        }
        return false;
    }

    public boolean hasNoPiecesBelowTwoSteps(Coordinates from, Board board) {
        return board.get(from.plus(+2 ,0)) == null;
    }

    public void canCaptureRightWhiteEnemy(Coordinates from, Board board, List<Move> allowedMoves) {
        if (canMoveRight(from) && isValidMove(from.plus(+1,+1))){
            Piece potentialEnemy = board.get(from.plus(+1, +1));

            if (potentialEnemy != null && !potentialEnemy.getColour().equals(this.colour)){
                addMove(allowedMoves, from,from.plus(+1, +1));
            }
        }
    }
    public void canCaptureLeftWhiteEnemy(Coordinates from, Board board, List<Move> allowedMoves) {
        if (canMoveLeft(from) && isValidMove(from.plus(+1, -1))) {
            Piece potentialEnemy = board.get(from.plus(+1, -1));

            if (potentialEnemy != null && !potentialEnemy.getColour().equals(this.colour)) {
                addMove(allowedMoves, from,from.plus(+1, -1));
            }
        }
    }
    public void canCaptureRightBlackEnemy(Coordinates from, Board board, List<Move> allowedMoves) {
        if (canMoveRight(from) && isValidMove(from.plus(-1,+1))) {
            Piece potentialEnemy = board.get(from.plus(-1, +1));

            if (potentialEnemy != null && !potentialEnemy.getColour().equals(this.colour)) {
                addMove(allowedMoves, from,from.plus(-1, +1));
            }
        }
    }
    public void canCaptureLeftBlackEnemy(Coordinates from, Board board, List<Move> allowedMoves) {
        if (canMoveLeft(from) && isValidMove(from.plus(-1,-1))) {
            Piece potentialEnemy = board.get(from.plus(-1, -1));
            if (potentialEnemy != null && !potentialEnemy.getColour().equals(this.colour)) {
                addMove(allowedMoves, from,from.plus(-1, -1));
            }
        }
    }

    public boolean canMoveLeft(Coordinates from) {
        return from.getCol() > 0;
    }
    public boolean canMoveRight(Coordinates from) {
        return from.getCol()  < 7;
    }

    public void addMove(List<Move> allowedMoves, Coordinates from,Coordinates to){
        if (isValidMove(from) && isValidMove(to)) {
            allowedMoves.add(new Move(from, to));
        }
    }

    public boolean isValidMove(Coordinates cord) {
            if( cord.getCol() < 8 && cord.getCol() >= 0) {
                if(cord.getRow() < 8 && cord.getRow() >= 0) {
                    return true;
                }
            }
        return false;
    }

}
