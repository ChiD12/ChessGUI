package pieces;
import Default.*;

import java.util.ArrayList;


public class Piece {
    private char representation = ' ';
    private String teamString = "";
    private String representationString= "";
    private char team = ' ';
    private int currentTileID;


    public Piece(){}

    public Piece(char i, char t, int ID){
        representation = i;
        setTeam(t);
        setCurrentTileID(ID);
    }

    public ArrayList<String> canMove(Tile[][] board){
        return new ArrayList<String>();
    }

    public String toString(){
        representationString =""+ team + representation;
        return representationString;
    }
    
    public char getTeam() {
        return team;
    }

    public int getCurrentTileID() {
        return currentTileID;
    }

    public void setCurrentTileID(int currentTileID) {
        if(currentTileID > 0 && currentTileID < 65)
            this.currentTileID = currentTileID;
    }

    public void setTeam(char team) {
        if(team == 'W' || team == 'B')
            this.team = team;
        else
            System.out.println("Invalid Team");
    }

    public char getRepresentation() {
        return representation;
    }

    public boolean inBounds(int i, int j, int newI, int newJ){
        boolean inBounds= false;
        if (i> newI){
            if(j> newJ){
                if(newI >= 0 && newJ >= 0){
                    inBounds = true;
                    //System.out.println("i- j- newI and NewJ GTZ true");
                    }

            }
            else if(j< newJ){
                if(newI >= 0 && newJ <= 7){
                    inBounds = true;
                    //System.out.println("i- j+ newI  GTZ  newJ STS true");
                }
            }
            else if(j == newJ){
                if(newI>= 0){
                    inBounds = true;
                    //System.out.println("i- j= newI  GTZ  newJ same");
                }
            }
        }
        else if(i< newI){
            if(j> newJ){
                if(newI <= 7 && newJ >= 0){
                    inBounds = true;
                    //System.out.println("i+ j- newI STS and newJ GTZ true");
                    }

            }
            else if(j< newJ){
                if(newI <= 7 && newJ <= 7){
                    inBounds = true;
                    //System.out.println("i+ j+ newI and NewJ STS true");
                    }
            }
            else if(j == newJ){
                if(newI<=7){
                    inBounds = true;
                    //System.out.println("i+ j= newI  STS  newJ same");
                }
            }


        }
        else if(i == newI){
            if(j> newJ){
                if(newJ >= 0){
                    inBounds = true;
                    //System.out.println("i= j- newI same and NewJ GTZ true");
                    }

            }
            else if(j< newJ){
                if(newJ <= 7){
                    inBounds = true;
                    //System.out.println("i= j+ newI  same  newJ STS true");
                }
            }
        }
        return inBounds;
    }

    public boolean checkForCheck(Tile[][] board,int i, int j ,int oldI,int oldJ){
        int rangeI;
        int rangeJ;
        int newI;
        int newJ;
        Tile tempChangePosition = board[oldI][oldJ];
        Tile tempNewPositionHolder = board[i][j];
        Tile kingTile;
        if(ChessMethods.isCurrentTurn())
            kingTile = ChessMethods.getWhiteKingPosition();
        else{
            kingTile = ChessMethods.getBlackKingPosition();
        }

        int kingI = (kingTile.getTileID())/8;
        int kingJ = kingTile.getTileID()%8;

        //System.out.println("oldI is: " + oldI + " oldJ is: " + oldJ);
        //System.out.println("the king should be: " + board[oldI][oldJ].getCurrentPiece());
        board[oldI][oldJ] = new Tile();
        board[i][j] = tempChangePosition;




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

            newI = kingI;
            newJ=kingJ;

            switch(k){
                case 0: rangeI = kingI; //up
                        top = true;
                        break;
                case 1: rangeI = 7 - kingI; //down
                        down = true;
                        break;
                case 2: rangeJ = 7-kingJ; // right
                        right = true;
                        break;
                case 3: rangeJ = kingJ; //left
                        left = true;
                        break;
                case 4: rangeI = 7 - kingI; //bottom right
                        rangeJ = 7 - kingJ;
                        botRight = true;
                        break;
                case 5: rangeI = 7 - kingI; //bottom left
                        rangeJ =  kingJ;
                        botLeft = true;
                        break;
                case 6: rangeI = kingI; //top right
                        rangeJ = 7-kingJ;
                        topRight = true;
                        break;
                case 7: rangeI = kingI; //top left
                        rangeJ = kingJ;
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
                                    board[oldI][oldJ] = tempChangePosition;
                                    board[i][j] = tempNewPositionHolder;
                                    return false;
                                }
                                if (plusOne || minusOne || plusEight || minusEight) {
                                    if (pieceAtNew.getRepresentation() == 'K') {
                                        board[oldI][oldJ] = tempChangePosition;
                                        board[i][j] = tempNewPositionHolder;
                                        return false;
                                    }
                                }
                            }
                            if (k >= 4) {
                                if (pieceAtNew.getRepresentation() == 'Q' || pieceAtNew.getRepresentation() == 'B') {
                                    board[oldI][oldJ] = tempChangePosition;
                                    board[i][j] = tempNewPositionHolder;
                                    return false;
                                }
                                if (plus7 || plus9) {
                                    if ((this.getTeam() == 'B' && pieceAtNew.getRepresentation() == 'P') || pieceAtNew.getRepresentation() == 'K') {
                                        board[oldI][oldJ] = tempChangePosition;
                                        board[i][j] = tempNewPositionHolder;
                                        return false;
                                    }
                                }
                                if (minus7 || minus9) {
                                    if ((this.getTeam() == 'W' && pieceAtNew.getRepresentation() == 'P') || pieceAtNew.getRepresentation() == 'K') {
                                        board[oldI][oldJ] = tempChangePosition;
                                        board[i][j] = tempNewPositionHolder;
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
                case 0: newI = kingI -1;
                        newJ = kingJ-2;
                        //System.out.println("Case 0" + newI + " " +newJ);
                        break;
                case 1: newI = kingI-2;
                        newJ = kingJ-1;
                        //System.out.println("Case 1" + newI + " " +newJ);
                        break;
                case 2: newI = kingI-2;
                        newJ = kingJ+1;
                        //System.out.println("Case 2" + newI + " " +newJ);
                        break;
                case 3: newI = kingI-1;
                        newJ = kingJ+2;
                        //System.out.println("Case 3" + newI + " " +newJ);
                        break;
                case 4: newI = kingI+1;
                        newJ = kingJ+2;
                        //System.out.println("Case 4" + newI + " " +newJ);
                        break;
                case 5: newI = kingI+2;
                        newJ = kingJ+1;
                        //System.out.println("Case 5" + newI + " " +newJ);
                        break;
                case 6: newI = kingI+2;
                        newJ = kingJ-1;
                        //System.out.println("Case 6" + newI + " " +newJ);
                        break;
                case 7: newI = kingI+1;
                        newJ = kingJ-2;
                        //System.out.println("Case 7" + newI + " " +newJ);
                        break;
            }

            inBounds = inBounds(kingI,kingJ,newI,newJ);

            if(inBounds){
                if(board[newI][newJ].getPiecePresent()){
                    if(board[newI][newJ].getCurrentPiece().getRepresentation() == 'N'){
                        if(board[newI][newJ].getCurrentPiece().getTeam() != tempChangePosition.getCurrentPiece().getTeam()) {
                            board[oldI][oldJ] = tempChangePosition;
                            board[i][j] = tempNewPositionHolder;
                            return false;
                        }
                    }
                }

            }inBounds = false;
        }
        //System.out.println("reset************************");
        board[oldI][oldJ] = tempChangePosition;
        board[i][j] = tempNewPositionHolder;
        return true;
    }
}
