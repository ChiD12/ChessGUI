package Default;

import pieces.Piece;

import static Default.Driver.board;

public class Tile {

    private static int tileCounter = 0;
    private int tileID;
    private Piece currentPiece= null;
    private Boolean piecePresent = false;
    private Boolean thisTileIsAValidMove = false;



    public Tile(){
        tileID = tileCounter;
        tileCounter++;
    }

    public Tile(Piece p){
        tileID = tileCounter;
        tileCounter++;
        this.setPiecePresent(true);
        currentPiece = p;
    }


    public Boolean getPiecePresent() {
        return piecePresent;
    }

    public void setPiecePresent(Boolean piecePresent) {
        this.piecePresent = piecePresent;
    }

    public int getTileID() {
        return tileID;
    }

    public String toString(){
        if(!thisTileIsAValidMove){
            if(piecePresent)
                return currentPiece.toString();
            return "==";
        }
        else{
            if(piecePresent)
                return currentPiece.toString().toLowerCase();
            return "()";
        }
    }
    public static Tile getTile(int ID){
        int currentID;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                currentID = board[i][j].getTileID();
                if(currentID == ID){
                    return board[i][j];
                }
            }
        }
        return board[0][0];
    }

    public Piece getCurrentPiece() {
        return currentPiece;
    }

    public void setCurrentPiece(Piece currentPiece) {
        this.currentPiece = currentPiece;
    }

    public Boolean getThisTileIsAValidMove() {
        return thisTileIsAValidMove;
    }

    public void setThisTileIsAValidMove(Boolean thisTileIsAValidMove) {
        this.thisTileIsAValidMove = thisTileIsAValidMove;
    }
}
