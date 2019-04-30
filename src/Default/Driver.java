//TEST2

package Default;

import pieces.*;
import GUI.*;

public class Driver {

    public static Tile[][] board = new Tile[8][8];

    public static void main(String[] args) {






        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                //change this
                int idMath = 8*(i)+(j);
                //Rooks
                if((j== 0 || j== 7) && (i==0 || i== 7)){
                    board[i][j] = new Tile( new Rook(((i==0)? 'B':'W'),idMath));
                }
                //Knights
                if((j== 1 || j== 6) && (i==0 || i== 7)){
                    //board[i][j] = new Tile( );
                    board[i][j] = new Tile( new Knight(((i==0)? 'B':'W'),idMath));
                }
                //Bishops
                if((j== 2 || j== 5) && (i==0 || i== 7)){
                    //board[i][j] = new Tile( );
                    board[i][j] = new Tile( new Bishop(((i==0)? 'B':'W'),idMath));
                }
                //Black Queen
                if((j== 3) && (i==0)){
                    //board[i][j] = new Tile( );
                    board[i][j] = new Tile( new Queen(((i==0)? 'B':'W'),idMath));
                }
                //White Queen
                if((j== 3) && (i==7)){
                    //board[i][j] = new Tile();
                    board[i][j] = new Tile( new Queen(((i==0)? 'B':'W'),idMath));
                }
                //Black King
                if((j== 4) && (i==0)){
                    board[i][j] = new Tile( new King(((i==0)? 'B':'W'),idMath));
                }
                //White King
                if((j== 4) && (i==7)){
                    board[i][j] = new Tile( new King(((i==0)? 'B':'W'),idMath));
                }
                //Pawns
                if (i== 1 || i== 6){
                    //board[i][j] = new Tile( );
                    board[i][j] = new Tile( new Pawn(((i==1)? 'B':'W'),idMath));
                }
                if (i>1 && i<6){
                    board[i][j] = new Tile( );
                }

            }

        }
        Table table = new Table();

        /*System.out.print("     ");
        for (int i = 0; i < 8; i++)
            System.out.print((char)(i+65)+ "  ");
        System.out.println();
        //System.out.println("     -----------------------");
        for (int i = 0; i < board.length; i++) {
            System.out.print(8-i + " || ");
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }*/


        //System.out.println(board[7][7].getTileID());
        ChessMethods.move(board);






    }
}
