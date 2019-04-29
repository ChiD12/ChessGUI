package pieces;

import Default.Tile;

import java.util.ArrayList;

public class Rook extends Piece{
    public Rook(char t, int id){
        super('R',t, id);
    }

    public ArrayList<String> canMove(Tile[][] board){
        int i;
        int j;
        int rangeI;
        int rangeJ;
        int tileID = this.getCurrentTileID();
        String parser = "";

        //System.out.println("tileID = " + tileID);
        i = (tileID)/8;
        j= tileID%8;
        int newI = 0;
        int newJ= 0;

        boolean top = false;
        boolean down = false;
        boolean right = false;
        boolean left = false;


        ArrayList<String> ijAL = new ArrayList<String>();


        for (int k = 0; k < 4; k++) {
            newI = i;
            newJ = j;
            rangeI = 8;   //default value is 8, which is OOB , if its this value then it wont be used
            rangeJ = 8;

            switch(k){
                case 0: rangeI = i; //up
                        top = true;
                        break;
                case 1: rangeI = 7 - i; //down
                        down = true;
                        break;
                case 2: rangeJ = 7-j; // right
                        right = true;
                        break;
                case 3: rangeJ = j; //left
                        left = true;
                        break;
            }

            int l = 0;
            boolean pieceInTheWay = false;
            System.out.println("rangeI = " + rangeI + " rangeJ = " + rangeJ);
            while (l < ((rangeI < rangeJ) ? rangeI : rangeJ) && !pieceInTheWay) {
                l++;

                if(top){
                    newI--;
                }
                if(down){
                    newI++;
                }
                if(right){
                    newJ++;
                }
                if(left){
                    newJ--;
                }

                //System.out.println("l is " + l + " newI is " + newI + " newJ is " + newJ);
                if (board[newI][newJ].getPiecePresent()) {
                    if (board[newI][newJ].getCurrentPiece().getTeam() != board[i][j].getCurrentPiece().getTeam()) {
                        parser = "" + newI + newJ;
                        //System.out.println(parser);
                        if(checkForCheck(board,newI,newJ,i,j))
                            ijAL.add(parser);

                    }
                    pieceInTheWay = true;
                } else {
                    parser = "" + newI + newJ;
                    //System.out.println(parser);
                    if(checkForCheck(board,newI,newJ,i,j))
                        ijAL.add(parser);
                }
            }
            top = false;
            down = false;
            right = false;
            left = false;
        }
        return ijAL;
    }
}
