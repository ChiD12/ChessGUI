package pieces;

import Default.Tile;

import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

public class Queen extends Piece{
    public Queen(char t,int id){
        super('Q',t,id);
    } //TEST3

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

        boolean botRight = false;
        boolean botLeft = false;
        boolean topRight = false;
        boolean topLeft = false;


        ArrayList<String> ijAL = new ArrayList<String>();


        for (int k = 0; k < 8; k++) {
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
                case 4: rangeI = 7 - i; //bottom right
                        rangeJ = 7 - j;
                        botRight = true;
                        break;
                case 5: rangeI = 7 - i; //bottom left
                        rangeJ =  j;
                        botLeft = true;
                        break;
                case 6: rangeI = i; //top right
                        rangeJ = 7-j;
                        topRight = true;
                        break;
                case 7: rangeI = i; //top left
                        rangeJ = j;
                        topLeft = true;
                        break;
            }

            int l = 0;
            boolean pieceInTheWay = false;
            //System.out.println("rangeI = " + rangeI + " rangeJ = " + rangeJ);
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
                if(botRight){
                    newI++;
                    newJ++;
                }
                if(botLeft){
                    newI++;
                    newJ--;
                }
                if(topRight){
                    newI--;
                    newJ++;
                }
                if(topLeft){
                    newI--;
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
            botRight = false;
            botLeft = false;
            topRight = false;
            topLeft = false;
        }
        return ijAL;
    }
}
