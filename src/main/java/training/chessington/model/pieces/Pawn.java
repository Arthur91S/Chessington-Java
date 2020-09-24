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

        if (isStartingPosition(from)  && isBlack() && hasNoPiecesBelow(from, board)){
            allowedMoves.add(new Move(from,new Coordinates(from.getCol() - 1, from.getCol())));
            allowedMoves.add(new Move(from,new Coordinates(from.getCol() - 2, from.getCol())));
        } else {
            allowedMoves.add(new Move(from,new Coordinates(from.getCol() - 1, from.getCol())));
        }

        if (isStartingPosition(from)  && isWhite() && hasNoPiecesAbove(from, board)){
            allowedMoves.add(new Move(from,new Coordinates(from.getCol() + 1, from.getCol())));
            allowedMoves.add(new Move(from,new Coordinates(from.getCol() + 2, from.getCol())));
        } else {
            allowedMoves.add(new Move(from,new Coordinates(from.getCol() + 1, from.getCol())));
        }
        return allowedMoves;
    }

    public boolean isStartingPosition(Coordinates from){

        if ( from.getRow() == 1 ){
            return true;
        }

        if ( from.getRow() == 6 ){
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
        return board.get(from.plus(1 ,0)) == null;
    }

    public boolean hasNoPiecesBelow(Coordinates from, Board board) {
        return board.get(from.plus(-1 ,0)) == null;
    }
}
