package pieces;
import Default.Tile;
import java.util.ArrayList;

public class Pawn extends Piece{
    public Pawn(char t,int id){
        super('P',t,id);
    }

    public ArrayList<String> canMove(Tile[][] board){
        int i;
        int j;
        int tileID = this.getCurrentTileID();
        //System.out.println("tileID = " + tileID);
        i = (tileID)/8;
        j= tileID%8;
        int newI = 0;
        int newJ = 0;
        boolean inBounds = false;
        String parser = "";

        ArrayList<String> ijAL = new ArrayList<String>();

        //for white pawn
        if(this.getTeam() == 'W'){
            for (int k = 0; k < 4; k++) {
                switch(k){
                    case 0 : newI = i-1; // white pawn moves up
                             newJ = j;
                             break;
                    case 1: newI = i-1; // white pawn taking a piece to the left
                            newJ = j-1;
                            break;
                    case 2: newI = i-1;// white pawn taking a piece to the right
                            newJ = j+1;
                            break;
                    case 3: newI = i-2; //white pawn moving two at starting position
                            newJ = j;
                            break;
                }

                inBounds =inBounds(i,j,newI,newJ);
                //System.out.println(inBounds);
                if(inBounds){
                    if(k == 0)  {
                        if(!board[newI][newJ].getPiecePresent()){
                            parser = ""+newI + newJ;
                            //System.out.println(parser);
                            if(checkForCheck(board,newI,newJ,i,j))
                                ijAL.add(parser);
                            //System.out.println("Nothing above white pawn, it can move up");
                        }
                    }
                    if(k == 1 || k == 2){
                        if(board[newI][newJ].getPiecePresent()){
                            if(board[newI][newJ].getCurrentPiece().getTeam() != board[i][j].getCurrentPiece().getTeam()) {
                                parser = ""+newI + newJ;
                                //System.out.println(parser);
                                if(checkForCheck(board,newI,newJ,i,j))
                                    ijAL.add(parser);
                                //System.out.println("enemy piece diagonal, pawn can take");
                            }

                        }
                    }
                    if(k == 3){
                        if(i == 6){
                            int m =0;
                            boolean blocker = false;
                            while(m < 2 && !blocker){
                                if(board[i -(m+1)][newJ].getPiecePresent()){
                                    blocker = true;
                                }
                                m++;
                            }
                            if(!blocker){
                                parser = ""+newI + newJ;
                                //System.out.println(parser);
                                if(checkForCheck(board,newI,newJ,i,j))
                                    ijAL.add(parser);
                                //System.out.println("nothing above the white piece, and its in starting position, it can move two up");
                            }
                        }
                    }

                }
            }
        }
        //for black pawn
        else{for (int k = 0; k < 4; k++) {
            switch(k){
                case 0: newI = i+1; // black pawn moves down
                        newJ = j;
                        break;
                case 1: newI = i+1; // black pawn taking a piece to the left
                        newJ = j-1;
                        break;
                case 2: newI = i+1;// black pawn taking a piece to the right
                        newJ = j+1;
                        break;
                case 3: newI = i+2; //black pawn moving two at starting position
                        newJ = j;
                        break;
            }

            inBounds =inBounds(i,j,newI,newJ);

            if(inBounds){
                if(k == 0)  {
                    if(!board[newI][newJ].getPiecePresent()){
                        parser = ""+newI + newJ;
                        //System.out.println(parser);
                        if(checkForCheck(board,newI,newJ,i,j))
                            ijAL.add(parser);
                        //System.out.println("Nothing above white pawn, it can move up");
                    }
                }
                if(k == 1 || k == 2){
                    if(board[newI][newJ].getPiecePresent()){
                        if(board[newI][newJ].getCurrentPiece().getTeam() != board[i][j].getCurrentPiece().getTeam()) {
                            parser = ""+newI + newJ;
                            //System.out.println(parser);
                            if(checkForCheck(board,newI,newJ,i,j))
                                ijAL.add(parser);
                            //System.out.println("enemy piece diagonal, pawn can take");
                        }

                    }
                }
                if(k == 3){
                    if(i == 1){
                        int m =0;
                        boolean blocker = false;
                        while(m < 2 && !blocker){
                            if(board[i +(m+1)][newJ].getPiecePresent()){
                                blocker = true;
                            }
                            m++;
                        }
                        if(!blocker){
                            parser = ""+newI + newJ;
                            //System.out.println(parser);
                            if(checkForCheck(board,newI,newJ,i,j))
                                ijAL.add(parser);
                            //System.out.println("nothing above the white piece, and its in starting position, it can move two up");
                        }
                    }
                }

            }
        }

        }
        return ijAL;
    }

}
