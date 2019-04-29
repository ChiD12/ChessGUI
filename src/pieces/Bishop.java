package pieces;

import Default.Tile;

import java.util.ArrayList;

public class Bishop extends Piece{
    public Bishop(char t,int id){
        super('B',t, id);
    }


    public ArrayList<String> canMove(Tile[][] board){
        int i;
        int j;
        int rangeI = 0;
        int rangeJ = 0;
        int tileID = this.getCurrentTileID();
        String parser = "";

        //System.out.println("tileID = " + tileID);
        i = (tileID)/8;
        j= tileID%8;
        int newI = 0;
        int newJ= 0;

        boolean botRight = false;
        boolean botLeft = false;
        boolean topRight = false;
        boolean topLeft = false;


        ArrayList<String> ijAL = new ArrayList<String>();


        for (int k = 0; k < 4; k++) {
            newI = i;
            newJ = j;

            switch(k){
                case 0: rangeI = 7 - i; //bottom right
                        rangeJ = 7 - j;
                        botRight = true;
                        break;
                case 1: rangeI = 7 - i; //bottom left
                        rangeJ =  j;
                        botLeft = true;
                        break;
                case 2: rangeI = i; //top right
                        rangeJ = 7-j;
                        topRight = true;
                        break;
                case 3: rangeI = i; //top left
                        rangeJ = j;
                        topLeft = true;
                        break;
            }



            int l = 0;
            boolean pieceInTheWay = false;
            //System.out.println("rangeI = " + rangeI + " rangeJ = " + rangeJ);
            while (l < ((rangeI < rangeJ) ? rangeI : rangeJ) && !pieceInTheWay) {
                l++;

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
            botRight = false;
            botLeft = false;
            topRight = false;
            topLeft = false;
        }
        return ijAL;
    }
}