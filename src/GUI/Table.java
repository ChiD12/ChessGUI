package GUI;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import Default.*;
import pieces.*;

import static Default.Driver.board;
import static javax.swing.SwingUtilities.isLeftMouseButton;
import static javax.swing.SwingUtilities.isRightMouseButton;

public class Table {
    private final JFrame gameFrame;
    private final BoardPanel boardPanel;

    private Tile sourceTile;
    private Tile destinationTile;
    private Piece humanMovedPiece;

    private static Dimension outerFrame = new Dimension(1200,1200);
    private final static Dimension boardPanelDimensions = new Dimension(800,700);
    private final static Dimension titlePanelDimensions = new Dimension(20,20);
    private static String defaultPieceImagePath = "art\\simple\\";

    private final Color lightTileColor = Color.decode("#FFFACD");
    private final Color darkTileColor = Color.decode("#593E1A");

    private boolean firstOrSecondClick = true;

    final java.util.List<TilePanel> boardTiles = new ArrayList<>();
    ArrayList<String> ijal;

    public Table(){
        this.gameFrame = new JFrame("Chess");
        this.gameFrame.setLayout(new BorderLayout());
        final JMenuBar tableMenuBar = createTableMenuBar();

        this.gameFrame.setJMenuBar(tableMenuBar);
        this.gameFrame.setSize(outerFrame);
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel,BorderLayout.CENTER);
        this.gameFrame.setVisible(true);
        this.gameFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);


    }

    private JMenuBar createTableMenuBar() {
        final JMenuBar tableMenuBar = new JMenuBar();
        tableMenuBar.add(createFileMenu());
        return tableMenuBar;
    }

    private JMenu createFileMenu(){
            final JMenu fileMenu = new JMenu("File");
            final JMenuItem openPGN = new JMenuItem("Load PGN File");
            openPGN.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    System.out.println("Open File");
                }
            });
            fileMenu.add(openPGN);
            final JMenuItem exitMenuItem = new JMenuItem("Exit");
            exitMenuItem.addActionListener(new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });
            fileMenu.add(exitMenuItem);
            return fileMenu;
    }

    private class BoardPanel extends JPanel{
        //java.util.List<TilePanel> boardTiles;

        BoardPanel(){
            super(new GridLayout(8,8));

            for (int i = 0; i < 64; i++) {
                final TilePanel tilePanel = new TilePanel(this,i);
                boardTiles.add(tilePanel);
                add(tilePanel);
            }

            setPreferredSize(boardPanelDimensions);
            validate();
        }
        public void drawBoard(Tile[][] board){
            removeAll();
            for(TilePanel tilePanel: boardTiles){
                tilePanel.drawTile(board);
                add(tilePanel);
                validate();
                repaint();
            }
        }
    }

    private class TilePanel extends JPanel{
        private final int tileID;
        TilePanel (final BoardPanel boardPanel, final int tileID){
            super(new GridBagLayout());
            this.tileID = tileID;
            setPreferredSize(titlePanelDimensions);
            assignTileColor();
            assignTilePieceIcon(board);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {

                    if(isLeftMouseButton(e)){
                        System.out.println(sourceTile);
                        //first click
                        int[] coordinatesOfPieceToMove=new int[2];
                        if(sourceTile == null){
                            sourceTile = Tile.getTile(tileID);
                            if(sourceTile.getPiecePresent()){

                                if((ChessMethods.isCurrentTurn() && sourceTile.getCurrentPiece().getTeam() == 'W')
                                   || !ChessMethods.isCurrentTurn() && sourceTile.getCurrentPiece().getTeam() == 'B' ) { // if it is that colors turn


                                    humanMovedPiece = sourceTile.getCurrentPiece();

                                    System.out.println(sourceTile.getTileID() / 8 + "and" + sourceTile.getTileID() % 8);
                                    coordinatesOfPieceToMove[0] = sourceTile.getTileID() / 8;
                                    coordinatesOfPieceToMove[1] = sourceTile.getTileID() % 8;
                                    ChessMethods.setCoordinatesOfPieceToMove(coordinatesOfPieceToMove);
                                    //boardPanel.drawBoard(board);
                                    firstOrSecondClick = !firstOrSecondClick;
                                }
                                else {  //if wrong team rest
                                    sourceTile=null;
                                }
                            }else{ //if no piece present rest
                                sourceTile=null;
                            }

                        }
                        //second click
                        else{
                            if(Tile.getTile(tileID).getThisTileIsAValidMove()){
                                int[] coordinatesOfWhereToMoveTo = new int[2];
                                destinationTile = Tile.getTile(tileID);
                                coordinatesOfWhereToMoveTo[0] = destinationTile.getTileID()/8;
                                coordinatesOfWhereToMoveTo[1] = destinationTile.getTileID()%8;
                                ChessMethods.setCoordinatesOfWhereToMoveTo(coordinatesOfWhereToMoveTo);
                                ChessMethods.swapPossitionsofNewAndOldPieces(board);





                                sourceTile=null;
                                destinationTile=null;
                                humanMovedPiece=null;
                                ChessMethods.setCurrentTurn(!ChessMethods.isCurrentTurn());
                            }
                        }
                        boardPanel.drawBoard(board);
                        /*SwingUtilities.invokeLater(new Runnable(){
                            @Override
                            public void run() {
                                boardPanel.drawBoard(board);
                            }
                        });*/
                        firstOrSecondClick = !firstOrSecondClick;

                    }else if(isRightMouseButton(e)){
                        sourceTile=null;
                        destinationTile=null;
                        humanMovedPiece=null;
                        boardPanel.drawBoard(board);
                    }


                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });



            validate();
        }
        public void drawTile(Tile[][] board){
            assignTileColor();
            assignTilePieceIcon(board);
            Tile.getTile(this.tileID).setThisTileIsAValidMove(false);

            highlightLegalMoves(board);
            validate();
            repaint();
        }

        private void assignTilePieceIcon(Tile[][] board){
            this.removeAll();
            //System.out.println(this.tileID);
            Tile currentTile = Tile.getTile(this.tileID);
            if(currentTile.getPiecePresent()){
                try{
                    final BufferedImage image = ImageIO.read(new File(defaultPieceImagePath + currentTile.getCurrentPiece() + ".gif"));
                    add(new JLabel(new ImageIcon(image)));

                }catch(IOException e){
                    e.printStackTrace();
                }
            }

        }

        private void highlightLegalMoves(Tile[][] board){
            int currentTileID1=0;
            if(true) {
                ijal = pieceLegalMoves(board);
                for (String s : ijal) {

                    int i = s.charAt(0) - 48;
                    int j = s.charAt(1) - 48;

                    currentTileID1 = i * 8 + j;
                    Tile.getTile(currentTileID1).setThisTileIsAValidMove(true);

                    if (currentTileID1 == this.tileID) {
                        try {
                            add(new JLabel(new ImageIcon(ImageIO.read(new File("art\\misc\\red_dot.png")))));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        private ArrayList<String> pieceLegalMoves(Tile[][] board){
            if(humanMovedPiece != null ){ //&& humanMovedPiece.getTeam() == ((ChessMethods.isCurrentTurn())?'W':'B')
                System.out.println(humanMovedPiece);
                return humanMovedPiece.canMove(board);
            }
            return new ArrayList<String>();
        }

        private void assignTileColor(){
            int splitTileID = this.tileID /8;
            if(splitTileID %2 == 0){
                setBackground(this.tileID % 2 ==0? lightTileColor: darkTileColor);
            }else if(splitTileID %2 !=0){
                setBackground(this.tileID % 2 !=0? lightTileColor: darkTileColor);
            }
        }
    }
}
