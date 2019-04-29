package Default;
import pieces.*;
import java.util.ArrayList;

import java.util.Scanner;

public class ChessMethods {
    private static Scanner kb = new Scanner(System.in);



    private static int[] coordinatesOfPieceToMove = null;
    private static int[] coordinatesOfWhereToMoveTo;
    //true = white , false = black
    private static boolean currentTurn = true;
    private static ArrayList<String> ijAL = null;
    private boolean castle = false;
    private static int turnCounter =0;

    private static ArrayList<Piece>  capturedWhite= new ArrayList<Piece>();
    private static ArrayList<Piece>  capturedBlack= new ArrayList<Piece>();

    private static Tile whiteKingPosition;
    private static Tile blackKingPosition;
    private static Boolean whiteKingCheck = false;
    private static Boolean blackKingCheck = false;


    public static void move(Tile[][] board){

        if(turnCounter == 0){
            setWhiteKingPosition(board[7][4]);
            setBlackKingPosition(board[0][4]);
        }

        System.out.println("It is " + ((currentTurn)? "White's ": "Black's ") + "turn");
        print(board);

        //check fo checkmate
        if(whiteKingCheck || blackKingCheck){
            if(checkMate(board)){
                System.out.println(((currentTurn)? "White":"Black") + " has 0 possible moves and is in checkmate, " +((!currentTurn)? "White":"Black") + " has won");
                System.exit(0);
            }
        }

        selectPieceToMove(board);


        ijAL = board[coordinatesOfPieceToMove[0]][coordinatesOfPieceToMove[1]].getCurrentPiece().canMove(board);

        changeValidMoveAndPrint(ijAL,board);

        selectValidInputandMoveThere(board);

        swapPossitionsofNewAndOldPieces(board);

        //if player is able to move, then their king must not be in check, then we check if enemy team king is in check after a turn is complete
        blackKingCheck = false;
        whiteKingCheck = false;
        if(!endOfTurnCheck(board)){
            if(currentTurn){
                blackKingCheck = true;
            }
            else{ // end of black turn
                whiteKingCheck = true;
            }
        }

        if(whiteKingCheck)
            System.out.println("White King in check");
        else
            System.out.println("White king is NOT in check");
        if(blackKingCheck)
            System.out.println("Black king is in check");
        else
            System.out.println("Black king is NOT in check");
        currentTurn=!currentTurn;
        turnCounter++;

        move(board);
    }
    public static void selectPieceToMove(Tile[][] board){
        boolean validmove =false;
        String input;
        while(!validmove) {

            System.out.println("What piece do you want to move?");
            input = kb.nextLine();
            input = input.toUpperCase();
            //int[] coordinatesOfPieceToMove = new int[2];
            coordinatesOfPieceToMove = convertStringInput(input);
            Tile currentTile = board[coordinatesOfPieceToMove[0]][coordinatesOfPieceToMove[1]];
            if(coordinatesOfPieceToMove[0] >= 0 && coordinatesOfPieceToMove[0] <= 7 && coordinatesOfPieceToMove[1]>= 0 && coordinatesOfPieceToMove[1] <=7 ){
                if(currentTile.getCurrentPiece() != null){
                    if((currentTurn && currentTile.getCurrentPiece().getTeam() == 'W') || (!currentTurn && currentTile.getCurrentPiece().getTeam() == 'B'))
                        validmove = true;
                    else
                        System.out.println("That piece is not on your team");
                }
                else
                    System.out.println("No piece present on that tile");

            }
            else
                System.out.println("Move not on board");

        }
    }

    private static void print(Tile[][] board){
        System.out.print("     ");
        for (int i = 0; i < 8; i++){
            System.out.print((char)(i+65)+ "  ");}
        System.out.println();
        for (int i = 0; i < board.length; i++) {
            System.out.print(8-i + " || ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void changeValidMoveAndPrint(ArrayList<String> ijAL, Tile[][] board){
        for (int i = 0; i < ijAL.size(); i++) {
            board[(int)ijAL.get(i).charAt(0)-48][(int)ijAL.get(i).charAt(1)-48].setThisTileIsAValidMove(true);
        }
        print(board);


    }

    public static int[] convertStringInput(String input) {
        int[] convertedInput = new int[2];
        char letter;
        String upper = input.toUpperCase();
        for(int i = 0; i < 2; i++) {
            if (i == 0) {
                letter = upper.charAt(0);
                convertedInput[1] = (int) letter - 65;
            } else if (i ==1) {
                convertedInput[0] = 8-((int) upper.charAt(1) - 48);
            }
        }
        return convertedInput;
    }
    public static void selectValidInputandMoveThere(Tile[][] board){

        boolean validInput = false;
        String input = "";
        coordinatesOfWhereToMoveTo = new int[2];
        boolean escape = false;

        while (!validInput) {
            System.out.println("Where do you want to input?");
            input = kb.nextLine();
            if(input.length() >= 2)
                coordinatesOfWhereToMoveTo = convertStringInput(input);

            if(input.equals("x")){
                validInput= true;
                escape = true;
                continue;
            }
            int x =0;

            boolean foundOne = false;
            while(x< ijAL.size() && !validInput) {

                String possibleLocation = ijAL.get(x);
                int i = possibleLocation.charAt(0) - 48;
                int j = possibleLocation.charAt(1) - 48;
                if (coordinatesOfWhereToMoveTo[0] == i && coordinatesOfWhereToMoveTo[1] == j) {
                    //System.out.println("testing");

                    validInput = true;
                    foundOne = true;
                }
                x++;
            }
            if (!foundOne)
                System.out.print(input + " is not a possible input.");
        }
        if(escape){
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    board[i][j].setThisTileIsAValidMove(false);
                }
            }
            move(board);
        }
    }

    public static void swapPossitionsofNewAndOldPieces(Tile[][] board){
        Tile oldPosition = board[coordinatesOfPieceToMove[0]][coordinatesOfPieceToMove[1]];
        Tile newPosition = board[coordinatesOfWhereToMoveTo[0]][coordinatesOfWhereToMoveTo[1]];

        if (oldPosition.getCurrentPiece().getRepresentation()== 'K'){
            //white king set current position
            if(currentTurn){
                setWhiteKingPosition(board[coordinatesOfWhereToMoveTo[0]][coordinatesOfWhereToMoveTo[1]]);
            }
            else{
                setBlackKingPosition(board[coordinatesOfWhereToMoveTo[0]][coordinatesOfWhereToMoveTo[1]]);
            }

            // top black king
            if(oldPosition.getTileID() == 4){
                //System.out.println("2");
                // top left rook
                if(newPosition.getTileID() == 2){
                    //System.out.println("pass if statement for castle");
                    board[0][3].setCurrentPiece(board[0][0].getCurrentPiece());
                    board[0][3].setPiecePresent(true);
                    board[0][0].setCurrentPiece(null);
                    board[0][0].setPiecePresent(false);
                    board[0][3].getCurrentPiece().setCurrentTileID(3);
                }
                //top right rook
                if(newPosition.getTileID() == 6){
                    //System.out.println("pass if statement for castle");
                    board[0][5].setCurrentPiece(board[0][7].getCurrentPiece());
                    board[0][5].setPiecePresent(true);
                    board[0][7].setCurrentPiece(null);
                    board[0][7].setPiecePresent(false);
                    board[0][5].getCurrentPiece().setCurrentTileID(5);
                }
            }
            //bottom white king
            if(oldPosition.getTileID() == 60){
                //System.out.println("2");
                // bot left rook
                if(newPosition.getTileID() == 58){
                    //System.out.println("pass if statement for castle");
                    board[7][3].setCurrentPiece(board[7][0].getCurrentPiece());
                    board[7][3].setPiecePresent(true);
                    board[7][0].setCurrentPiece(null);
                    board[7][0].setPiecePresent(false);
                    board[7][3].getCurrentPiece().setCurrentTileID(61);
                }
                //bot right rook
                if(newPosition.getTileID() == 62){
                    //System.out.println("pass if statement for castle");
                    board[7][5].setCurrentPiece(board[7][7].getCurrentPiece());
                    board[7][5].setPiecePresent(true);
                    board[7][7].setCurrentPiece(null);
                    board[7][7].setPiecePresent(false);
                    board[7][5].getCurrentPiece().setCurrentTileID(61);
                }
            }

        }
        if(newPosition.getPiecePresent()){
            if(currentTurn){
                capturedBlack.add(newPosition.getCurrentPiece());
            }
            else{
                capturedWhite.add(newPosition.getCurrentPiece());
            }
        }
        newPosition.setCurrentPiece(oldPosition.getCurrentPiece());
        newPosition.setPiecePresent(true);
        oldPosition.setCurrentPiece(null);
        oldPosition.setPiecePresent(false);
        newPosition.getCurrentPiece().setCurrentTileID(8*(coordinatesOfWhereToMoveTo[0])+(coordinatesOfWhereToMoveTo[1]));

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j].setThisTileIsAValidMove(false);
            }
        }

        if (newPosition.getCurrentPiece().getRepresentation()== 'K'){
            if(currentTurn){
                setWhiteKingPosition(newPosition);
            }
            else{
                setBlackKingPosition(newPosition);
            }
        }
    }

    public static boolean endOfTurnCheck(Tile[][] board){
        Tile currentKingPosition;

        if(currentTurn){ //end of white turn
            currentKingPosition = blackKingPosition;
        }
        else{ // end of black turn
            currentKingPosition = whiteKingPosition;
        }

        final int i = (currentKingPosition.getTileID())/8;
        final int j= currentKingPosition.getTileID()%8;

        int rangeI;
        int rangeJ;
        int newI;
        int newJ;

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
                        if (pieceAtNew.getTeam() != currentKingPosition.getCurrentPiece().getTeam()) {
                            //System.out.println("in");
                            if (k >= 0 && k <= 3) {
                                // rooks and queens vertical or horizontal
                                if (pieceAtNew.getRepresentation() == 'Q' || pieceAtNew.getRepresentation() == 'R') {
                                    return false;
                                }
                                if (plusOne || minusOne || plusEight || minusEight) {
                                    if (pieceAtNew.getRepresentation() == 'K') {
                                        return false;
                                    }
                                }
                            }
                            if (k >= 4) {
                                if (pieceAtNew.getRepresentation() == 'Q' || pieceAtNew.getRepresentation() == 'B') {
                                    return false;
                                }
                                if (plus7 || plus9) {
                                    if ((currentKingPosition.getCurrentPiece().getTeam() == 'B' && pieceAtNew.getRepresentation() == 'P') || pieceAtNew.getRepresentation() == 'K') {
                                        return false;
                                    }
                                }
                                if (minus7 || minus9) {
                                    if ((currentKingPosition.getCurrentPiece().getTeam() == 'W' && pieceAtNew.getRepresentation() == 'P') || pieceAtNew.getRepresentation() == 'K') {
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
            System.out.println("check for knight k: " + k);
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

            //inBounds = inBounds(i,j,newI,newJ);

            if(inBounds){
                if(board[newI][newJ].getPiecePresent()){
                    if(board[newI][newJ].getCurrentPiece().getRepresentation() == 'N'){
                        if(board[newI][newJ].getCurrentPiece().getTeam() != currentKingPosition.getCurrentPiece().getTeam()) {
                            return false;
                        }
                    }
                }

            }inBounds = false;
        }
        return true;
    }

    private static boolean checkMate(Tile[][] board){
        char currentTurnChar;
        ArrayList<String> ijal;
        int totalPossibleMoves= 0;
        if (currentTurn)
            currentTurnChar = 'W';
        else
            currentTurnChar = 'B';
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if(board[i][j].getPiecePresent()){
                    if(board[i][j].getCurrentPiece().getTeam() == currentTurnChar){
                        ijal = board[i][j].getCurrentPiece().canMove(board);
                        totalPossibleMoves += ijal.size();
                        if(totalPossibleMoves > 0)
                            return false;
                    }
                }
            }
        }
        return true;
    }






    public boolean isCastle() {
        return castle;
    }

    public void setCastle(boolean castle) {
        this.castle = castle;
    }

    public static Tile getWhiteKingPosition() {
        return whiteKingPosition;
    }

    public static void setWhiteKingPosition(Tile whiteKingPosition) {
        ChessMethods.whiteKingPosition = whiteKingPosition;
    }

    public static Tile getBlackKingPosition() {
        return blackKingPosition;
    }

    public static void setBlackKingPosition(Tile blackKingPosition) {
        ChessMethods.blackKingPosition = blackKingPosition;
    }

    public static Boolean getWhiteKingCheck() {
        return whiteKingCheck;
    }

    public static void setWhiteKingCheck(Boolean whiteKingCheck) {
        ChessMethods.whiteKingCheck = whiteKingCheck;
    }

    public static Boolean getBlackKingCheck() {
        return blackKingCheck;
    }

    public static void setBlackKingCheck(Boolean blackKingCheck) {
        ChessMethods.blackKingCheck = blackKingCheck;
    }

    public static boolean isCurrentTurn() {
        return currentTurn;
    }
    public static int[] getCoordinatesOfPieceToMove() {
        return coordinatesOfPieceToMove;
    }

    public static void setCoordinatesOfPieceToMove(int[] coordinatesOfPieceToMove) {
        ChessMethods.coordinatesOfPieceToMove = coordinatesOfPieceToMove;
    }

    public static int[] getCoordinatesOfWhereToMoveTo() {
        return coordinatesOfWhereToMoveTo;
    }

    public static void setCoordinatesOfWhereToMoveTo(int[] coordinatesOfWhereToMoveTo) {
        ChessMethods.coordinatesOfWhereToMoveTo = coordinatesOfWhereToMoveTo;
    }

    public static void setCurrentTurn(boolean currentTurn) {
        ChessMethods.currentTurn = currentTurn;
    }
}

