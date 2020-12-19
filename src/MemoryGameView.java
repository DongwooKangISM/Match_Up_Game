import com.sun.xml.internal.bind.WhiteSpaceProcessor;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.Timer;


public class MemoryGameView {

    MemoryGameModel2 model;
    Tile[] tiles;
    int[] tileNumbers = new int[36];
    boolean matchedTiles[] = new boolean[36];
    int firstTileClicked = -1;

    public MemoryGameView(MemoryGameModel2 model) {

        this.model = model;

        SwingUtilities.invokeLater(
                new Runnable() {

                    @Override
                    public void run() {
                        JPanel mainPanel = new JPanel();
                        mainPanel.setLayout(new GridLayout(6,6));
                        mainPanel.setBackground(Color.darkGray);
                        // Create array of tile objects
                        tiles = new Tile[36];
                        tileNumbers = model.getAllTileNumbers();

                        // Instantiate tiles and add to main panel
                        for (int i = 0; i < tiles.length ; i++) {
                            // Create tile objects
                            tiles[i] = new Tile(MemoryGameView.this,i);

                            // Add this tile to the main panel
                            mainPanel.add(tiles[i].getLabel());

                        }

                        JFrame mainFrame = new JFrame();
                        mainFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        mainFrame.getContentPane().add(mainPanel);
                        mainFrame.pack();
                        mainFrame.setVisible(true);
                    }
                }
        );

    }

    void tileClicked(int tileNumber) {
        if (matchedTiles[tileNumber]) {
            return;
            //Already matched
        }

        showClickedTile(tileNumber);

        int secondTileClicked;
        if (firstTileClicked == -1) {
            firstTileClicked = tileNumber;
        }
        else {
            secondTileClicked = tileNumber;

            if (firstTileClicked == secondTileClicked) {
                // Don't do anything because they clicked the same tile twice.
                // Later we can add code here to display an appropriate message.
            }

            else {
                boolean matchFound = model.tryMatch(firstTileClicked, secondTileClicked);

                if(matchFound) {
                    updateMatched();
                    firstTileClicked = -1;
                }

                else {
                    updateColors(firstTileClicked, secondTileClicked);
                    //wait for a seoncd then...
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        @Override
                        public void run() {
                            hideTiles(firstTileClicked, secondTileClicked);
                            firstTileClicked = -1;
                        }
                    };
                    timer.schedule(task, 500);
                }
            }
        }
    }

    void showClickedTile (int tileNumber) {
        tiles[tileNumber].getLabel().setText(String.valueOf(tileNumbers[tileNumber]));
        tiles[tileNumber].getLabel().setForeground(Color.WHITE);
        tiles[tileNumber].getLabel().setBackground(Color.WHITE);
    }

    void hideTiles (int firstTile, int secondTile) {
        tiles[firstTile].getLabel().setText(" ");
        tiles[secondTile].getLabel().setText(" ");
        tiles[firstTile].getLabel().setBackground(Color.BLACK);
        tiles[secondTile].getLabel().setBackground(Color.BLACK);


    }

    void updateColors(int firstTile, int secondTile) {
        tiles[firstTile].getLabel().setForeground(Color.RED);
        tiles[firstTile].getLabel().setBackground(Color.RED);
        tiles[secondTile].getLabel().setForeground(Color.RED);
        tiles[secondTile].getLabel().setBackground(Color.RED);
    }

    void updateMatched() {
        // Ask the model for the tiles to Display. This is an array of ints
        // containing all pairs of matched tiles. All non-matched elements are
        // set to -1.
        matchedTiles = model.getMatchedTiles();

        for (int i = 0; i < matchedTiles.length; i++) {
            if (matchedTiles[i]) {
                tiles[i].getLabel().setText(String.valueOf(tileNumbers[i]));
                tiles[i].getLabel().setBackground(Color.GREEN);
                tiles[i].getLabel().setForeground(Color.GREEN);
            }
            else {
                tiles[i].getLabel().setText(" ");
                tiles[i].getLabel().setBackground(Color.BLACK);
            }
        }
    }
}
