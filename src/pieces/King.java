package pieces;

import Default.Tile;

import java.util.ArrayList;

public class King extends Piece{

    Tile kingTile;

    public King(char t,int id){
        super('K',t, id);
    }
    public ArrayList<String> canMove(Tile[][] board){
        int tileID = this.getCurrentTileID();
        //System.out.println("tileID = " + tileID);
        final int i = (tileID)/8;
        final int j= tileID%8;
        int newI = 0;
        int newJ = 0;
        boolean inBounds = false;
        String parser = "";

        ArrayList<String> ijAL = new ArrayList<String>();

        // check white castle
        if(i == 7 && j == 4 && this.getTeam() == 'W'){
            if(board[7][0].getPiecePresent()) {
                if (board[7][0].getCurrentPiece().getRepresentation() == 'R' && board[7][0].getCurrentPiece().getTeam() == 'W') {
                    if (board[7][1].getPiecePresent() == false && board[7][2].getPiecePresent() == false && board[7][3].getPiecePresent() == false) {

                        newI = i;
                        newJ = j - 2;

                        parser = "" + newI + newJ;
                        //System.out.println(parser);
                        if(checkForCheck(board, newI,newJ,i,j) && checkForCheck(board,newI,newJ+1,i,j) && checkForCheck(board,newI,newJ-1,i,j))
                            ijAL.add(parser);
                        //System.out.println("white king can castle left");
                    }
                }
            }

            if(board[7][7].getPiecePresent()) {
                if (board[7][7].getCurrentPiece().getRepresentation() == 'R' && board[7][7].getCurrentPiece().getTeam() == 'W') {
                    if (board[7][5].getPiecePresent() == false && board[7][6].getPiecePresent() == false) {

                        newI = i;
                        newJ = j + 2;
                        parser = "" + newI + newJ;
                        //System.out.println(parser);
                        if(checkForCheck(board, newI,newJ,i,j) && checkForCheck(board,newI,newJ-1,i,j))
                            ijAL.add(parser);
                        //System.out.println("white king can castle right");
                    }
                }
            }
        }
        // check black castle
        if(i == 0 && j == 4 && this.getTeam() == 'B'){
            if(board[0][0].getPiecePresent()){
                if(board[0][0].getCurrentPiece().getRepresentation() == 'R' && board[0][0].getCurrentPiece().getTeam() == 'B'){
                    if(board[0][1].getPiecePresent() == false && board[0][2].getPiecePresent() == false && board[0][3].getPiecePresent() == false){
                        newI = i;
                        newJ = j-2;
                        parser = ""+newI + newJ;
                        //System.out.println(parser);
                        if(checkForCheck(board, newI,newJ,i,j) && checkForCheck(board,newI,newJ+1,i,j) && checkForCheck(board,newI,newJ-1,i,j))
                            ijAL.add(parser);
                        //System.out.println(" black king can castle left");
                    }
                }
            }
            if(board[0][7].getPiecePresent()){
                if(board[0][7].getCurrentPiece().getRepresentation() == 'R' && board[0][7].getCurrentPiece().getTeam() == 'B'){
                    if(board[0][5].getPiecePresent() == false && board[0][6].getPiecePresent() == false){
                        newI = i;
                        newJ = j+2;parser = ""+newI + newJ;
                        //System.out.println(parser);
                        if(checkForCheck(board, newI,newJ,i,j) && checkForCheck(board,newI,newJ-1,i,j))
                            ijAL.add(parser);
                        //System.out.println("black king can castle right");
                    }
                }
            }
        }

        for (int k = 0; k < 8; k++) {
            switch(k){
                case 0: newI = i-1; //move up
                        newJ = j;
                        break;
                case 1: newI = i-1; //move top right
                        newJ = j+1;
                        break;
                case 2: newI = i; //move right
                        newJ = j+1;
                        break;
                case 3: newI = i+1; // move down right
                        newJ = j+1;
                        break;
                case 4: newI = i+1; // move down
                        newJ = j;
                        break;
                case 5: newI = i+1; //move down left
                        newJ = j-1;
                        break;
                case 6: newI = i; //move left
                        newJ = j-1;
                        break;
                case 7: newI = i-1; // move top left;
                        newJ = j-1;
                        break;

            }
            inBounds =inBounds(i,j,newI,newJ);
            if(inBounds){
                if(board[newI][newJ].getPiecePresent()){
                    if(board[newI][newJ].getCurrentPiece().getTeam() != board[i][j].getCurrentPiece().getTeam()){
                        parser = ""+newI + newJ;
                        //System.out.println(parser);
                        //System.out.println("checking for check for: newI: " + newI + " newJ: " + newJ);
                        if(checkForCheck(board, newI,newJ,i,j))
                            ijAL.add(parser);
                        //System.out.println("king can take piece at that tile");
                    }
                }
                else{
                    parser = ""+newI + newJ;
                    //System.out.println(parser);
                    //System.out.println("checking for check for: newI: " + newI + " newJ: " + newJ);
                    if(checkForCheck(board, newI,newJ,i,j))
                        ijAL.add(parser);
                    //System.out.println("king can move there no piece present");
                }
            }

        }
        return ijAL;
    }

    public boolean checkForCheck(Tile[][] board,int i, int j ,int oldI,int oldJ){
        int rangeI;
        int rangeJ;
        int newI;
        int newJ;
        Tile kingTile = board[oldI][oldJ];
        //System.out.println("oldI is: " + oldI + " oldJ is: " + oldJ);
        //System.out.println("the king should be: " + board[oldI][oldJ].getCurrentPiece());
        board[oldI][oldJ] = new Tile();




        for (int k = 0; k < 8; k++) {

            boolean top = false;
            boolean down = false;
            boolean right = false;
            boolean left = false;

            boolean botRight = false;
            boolean botLeft = false;
            boolean topRight = false;
            boolean topLeft = false;

            rangeI = 8;   //default value is 8, which is OOB , if its this value then it wont be used
            rangeJ = 8;

            newI = i;
            newJ=j;

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
            //System.out.println("k = " + k);
            //System.out.println("rangeI is " + rangeI + " rangeJ is " + rangeJ);
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
                //System.out.println(l);
                //System.out.println("newI is " + newI + "newJ is " + newJ);
                Piece pieceAtNew = board[newI][newJ].getCurrentPiece();
                boolean plusOne = false;
                boolean minusOne = false;
                boolean plusEight = false;
                boolean minusEight = false;
                boolean minus7 = false;
                boolean minus9 = false;
                boolean plus7 = false;
                boolean plus9 = false;

                if(j != 7)
                    plusOne = board[newI][newJ].getTileID() == board[i][j].getTileID() +1;
                if(j!=0)
                    minusOne = board[newI][newJ].getTileID() == board[i][j].getTileID() -1;
                if(i != 7)
                    plusEight = board[newI][newJ].getTileID() == board[i][j].getTileID() +8;
                if(i!=0)
                    minusEight = board[newI][newJ].getTileID() == board[i][j].getTileID() -8;
                if(i!=0 && j!=0)
                    minus7 = board[newI][newJ].getTileID() == board[i][j].getTileID() -7;
                if(i!=0 && j!= 7)
                    minus9 = board[newI][newJ].getTileID() == board[i][j].getTileID() -9;
                if(i!=7 && j!= 0)
                    plus7 = board[newI][newJ].getTileID() == board[i][j].getTileID() +7;
                if(i!=7 && j!= 7)
                    plus9 = board[newI][newJ].getTileID() == board[i][j].getTileID() +9;

                if (board[newI][newJ].getPiecePresent()) {
                    if(board[newI][newJ].getPiecePresent()){
                        //System.out.println(pieceAtNew);
                        //System.out.println("new is " + pieceAtNew.getTeam());
                        //System.out.println("i is " + i + "j is " + j);
                        if (pieceAtNew.getTeam() != this.getTeam()) {
                            //System.out.println("in");
                            if (k >= 0 && k <= 3) {
                                // rooks and queens vertical or horizontal
                                if (pieceAtNew.getRepresentation() == 'Q' || pieceAtNew.getRepresentation() == 'R') {
                                    board[oldI][oldJ] = kingTile;
                                    return false;
                                }
                                if (plusOne || minusOne || plusEight || minusEight) {
                                    if (pieceAtNew.getRepresentation() == 'K') {
                                        board[oldI][oldJ] = kingTile;
                                        return false;
                                    }
                                }
                            }
                            if (k >= 4) {
                                if (pieceAtNew.getRepresentation() == 'Q' || pieceAtNew.getRepresentation() == 'B') {
                                    board[oldI][oldJ] = kingTile;
                                    return false;
                                }
                                if (plus7 || plus9) {
                                    if ((this.getTeam() == 'B' && pieceAtNew.getRepresentation() == 'P') || pieceAtNew.getRepresentation() == 'K') {
                                        board[oldI][oldJ] = kingTile;
                                        return false;
                                    }
                                }
                                if (minus7 || minus9) {
                                    if ((this.getTeam() == 'W' && pieceAtNew.getRepresentation() == 'P') || pieceAtNew.getRepresentation() == 'K') {
                                        board[oldI][oldJ] = kingTile;
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                    pieceInTheWay = true;
                }
            }
        }
        //check for knights
        for(int k = 0; k < 8; k++) {
            //System.out.println("check for knight k: " + k);
            Boolean inBounds = false;

            newI = 0;
            newJ = 0;

            switch (k){
                case 0: newI = i -1;
                        newJ = j-2;
                        //System.out.println("Case 0" + newI + " " +newJ);
                        break;
                case 1: newI = i-2;
                        newJ = j-1;
                        //System.out.println("Case 1" + newI + " " +newJ);
                        break;
                case 2: newI = i-2;
                        newJ = j+1;
                        //System.out.println("Case 2" + newI + " " +newJ);
                        break;
                case 3: newI = i-1;
                        newJ = j+2;
                        //System.out.println("Case 3" + newI + " " +newJ);
                        break;
                case 4: newI = i+1;
                        newJ = j+2;
                        //System.out.println("Case 4" + newI + " " +newJ);
                        break;
                case 5: newI = i+2;
                        newJ = j+1;
                        //System.out.println("Case 5" + newI + " " +newJ);
                        break;
                case 6: newI = i+2;
                        newJ = j-1;
                        //System.out.println("Case 6" + newI + " " +newJ);
                        break;
                case 7: newI = i+1;
                        newJ = j-2;
                        //System.out.println("Case 7" + newI + " " +newJ);
                        break;
            }

            inBounds = inBounds(i,j,newI,newJ);

            if(inBounds){
                if(board[newI][newJ].getPiecePresent()){
                    if(board[newI][newJ].getCurrentPiece().getRepresentation() == 'N'){
                        if(board[newI][newJ].getCurrentPiece().getTeam() != kingTile.getCurrentPiece().getTeam()) {
                            board[oldI][oldJ] = kingTile;
                            return false;
                        }
                    }
                }

            }inBounds = false;
        }
        //System.out.println("reset************************");
        board[oldI][oldJ] = kingTile;
        return true;
    }
}
