package pieces;
import Default.Tile;
import java.util.ArrayList;

public class Knight extends Piece{
    public Knight(char t,int id){
        super('N',t,id);
    }

    public ArrayList<String> canMove(Tile[][] board){
        int i;
        int j;
        int tileID = this.getCurrentTileID();
        //System.out.println("tileID = " + tileID);
        i = (tileID)/8;
        j= tileID%8;

        ArrayList<String> ijAL = new ArrayList<String>();


        /*The weird i-1, j-2 stuff is to make sure that the move falls on the board
         * checks possible movements for the knight, if not out of bounds or occupied adds coordinates to ArrayLists
         * */


        for (int k = 0; k < 8; k++) {
            int newI = 0;
            int newJ = 0;
            boolean inBounds = false;
            String parser = "";

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
                    if(board[newI][newJ].getCurrentPiece().getTeam() != board[i][j].getCurrentPiece().getTeam()) {
                        parser = ""+newI + newJ;
                        //System.out.println(parser);
                        if(checkForCheck(board,newI,newJ,i,j))
                            ijAL.add(parser);
                        //System.out.println("Piece present but diff team");
                    }
                }
                else{
                    parser = ""+newI + newJ;
                    //System.out.println(parser);
                    if(checkForCheck(board,newI,newJ,i,j))
                        ijAL.add(parser);
                    //System.out.println("no piece present");
                }
            }
        }
        return ijAL;
    }
}

