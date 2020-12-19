import java.util.Random;

public class MemoryGameModel {

    private int[] tiles = new int[16];
    private boolean[] displayed = new boolean[16];
    int firstTileNumber = -1;

    public MemoryGameModel() {

        //Temporary set up. Will be changed later
        for (int i = 0; i < tiles.length /2 ; i ++) {
            tiles[i] = i + 1;
            tiles[i + 8] = i + 1;
        }

        // Shuffle the array
        shuffle(tiles); // italic because it is static

        for(int t : tiles) {
            System.out.println(t + " ");
        }
        System.out.println();
    }

    // When it's your turn, you have to say which tile you've clicked
    public void doClick(int index) {
        // Is this the first click or the second?
        if (firstTileNumber == -1) {
            System.out.println("Model received first click: " + index);
            //This must be the first click
            firstTileNumber = index;
            // Update the
            displayed[firstTileNumber] = true;
        }
        else {
            System.out.println("Model received second click: " + index);
            // This must be the second click
            int secondTileNumber = index;

            if(tiles[firstTileNumber] == tiles[secondTileNumber]) {
                if (firstTileNumber == secondTileNumber) {
                    System.out.println("Same Tile Clicked Twice, Click it again");
                }
                // or just put return;, at outside of the loop and put else if for match
                else {
                    //Match
                    System.out.println("Match found");
                    displayed[firstTileNumber] = true;
                    displayed[secondTileNumber] = true;
                    firstTileNumber = -1;
                }

            }
            else {
                System.out.println("No match");
                // Doesn't Match, Don't display the clicked tile nay more.
                displayed[firstTileNumber] = false;
                firstTileNumber = -1;
            }


        }

    }

    int[] getTilesToDisplay() {
        //Let the UI ask which tiles it should display and which tile should still be hidden.

        int[] returnArray = new int[16];
        for (int i = 0; i < displayed.length; i++) {
            if(displayed[i]) {
                returnArray[i] = tiles[i];
            } else {
                returnArray[i] = -1;
            }
        }
        //loop through the displayed array and when I find a true value at index i,
        // set the ith value of returnArray to the ith value of tiles, or zero otherwise.
        return returnArray;
    }

    public int[] getTiles() {
        return tiles;
    }

    static void shuffle(int[] array) {
        int n = array.length;
        Random random = new Random();
        // Loop over array
        for (int i = 0; i < array.length; i++) {
            // Get a random index of the array past the currnet index
            // The argument is an exclusive bound
            // It will not go past the array's end
            int randomValue = i + random.nextInt(n - i);
            // Swap the random element with the present element
            int randomElement = array[randomValue];
            array[randomValue] = array[i];
            array[i] = randomElement;

        }
    }

}


