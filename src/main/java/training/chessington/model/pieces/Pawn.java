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

        if (hasNoPiecesInFront(from, board,1)){
            addMove(allowedMoves, from,from.plus(-1, 0));
        }
        if (hasNoPiecesInFront(from, board,1)){
            addMove(allowedMoves, from,from.plus(+1, 0));
        }
        if (isStartingPosition(from) && !allowedMoves.isEmpty()){
            if (hasNoPiecesInFront(from,board,2)){
                addMove(allowedMoves, from,from.plus(-2, 0));
            }
            if (hasNoPiecesInFront(from, board,2)){
                addMove(allowedMoves, from,from.plus(+2, 0));
            }
        }

        addCaptureEnemyMoves(from,board, allowedMoves);

        return allowedMoves;
    }

    public boolean hasNoPiecesInFront(Coordinates from, Board board, int steps){

        // if white check row -1
        if (isWhite() && isValidMove(from.plus(-1 *steps,0))){
            return board.get(from.plus(-1 * steps,0)) == null;
        }
        // if black check row +1
        if (isBlack() && isValidMove(from.plus(+1*steps,0))){
            return board.get(from.plus(+1 * steps,0)) == null;
        }
        return false;

    }

    public boolean isStartingPosition(Coordinates from){
        return isBlack() ? from.getRow() == 1 : from.getRow() == 6;
    }

    public boolean isWhite(){
        return colour == PlayerColour.WHITE;
    }
    public boolean isBlack(){
        return colour == PlayerColour.BLACK;
    }

    public void addCaptureEnemyMoves(Coordinates from, Board board, List<Move> allowedMoves){
        List<Coordinates> potentialEnemies = new ArrayList<>();
        potentialEnemies.add(from.plus(+1,+1));
        potentialEnemies.add(from.plus(+1,-1));
        potentialEnemies.add(from.plus(-1,+1));
        potentialEnemies.add(from.plus(-1,-1));

        for (int i = 0; i < potentialEnemies.size(); i++){
            Coordinates to = potentialEnemies.get(i);
            if (isValidMove(to) && hasEnemy(board, to)){
                addMove(allowedMoves, from, to);
            }
        }
    }

    public void addMove(List<Move> allowedMoves, Coordinates from,Coordinates to){
        if (isValidMove(from) && isValidMove(to)) {
            allowedMoves.add(new Move(from, to));
        }
    }

    public boolean isValidMove(Coordinates cord) {
        return cord.getCol() < 8 && cord.getCol() >= 0 && cord.getRow() < 8 && cord.getRow() >= 0;
    }

    public boolean hasEnemy(Board board, Coordinates enemy){
        Piece potentialEnemy = board.get(enemy);
        return potentialEnemy != null && !potentialEnemy.getColour().equals(this.colour);
    }


}
