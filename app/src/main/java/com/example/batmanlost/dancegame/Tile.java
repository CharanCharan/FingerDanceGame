package com.example.batmanlost.dancegame;

import android.graphics.Color;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by BatmanLost on 22-03-2016.
 */
public class Tile {

    // colors array
    private static final int[] colors = new int[]{ Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN,Color.BLACK, Color.CYAN, Color.MAGENTA, Color.GRAY, Color.DKGRAY};

    // variables for highlighting a random tile
    private static Set<Integer> unHighlightedTiles;
    private static int highlightedTileIndex;

    // list of tiles created
    private static List<Tile> tiles;

    // color of this tile
    private int mTileColor;
    // is this tile highlighted
    private boolean mToBeHighlighted ;

    // Cache the hash code
    private volatile int hashCode;

    /**
     * Creates an unhighlighted tile with a color
     * @param tileColorIndex
     */
    private Tile(int tileColorIndex){
        mTileColor = colors[tileColorIndex];
        mToBeHighlighted = false;
    }

    /**
     * Exports required number of tiles with desired properties
     * @param noOfTiles
     * @return
     */
    public static List<Tile> getTiles(int noOfTiles){
        tiles = new ArrayList<>();
        for (int i=0; i<noOfTiles ; i++){
            int randomColor = randomInt(colors.length);
            tiles.add(new Tile(randomColor));
        }

        // run code to highlight random tile
        updateUnHighlightTiles(noOfTiles);

        return tiles;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else return false;
    }

    @Override
    public int hashCode() {
        int result = hashCode;
        if (result == 0) {
            result = 17;
            result = 31 * result + mTileColor;
            hashCode = result;
        }
        return result;
    }

    /**
     * Returns color of tile
     * @return
     */
    public int getTileColor() {
        return mTileColor;
    }

    /**
     * Returns a random int b/n 0 ans N
     * @param N
     * @return
     */
    private static int randomInt(int N){return (int)(Math.random()*N);}

    /****** Code to highlight random tile ******/

    /**
     * Stores the indices of tiles which are to be highlighted
     * @param noOfTiles
     */
    private static void updateUnHighlightTiles(int noOfTiles){
        unHighlightedTiles = new HashSet<>();
        for (int i=0; i<noOfTiles ;i++){
            unHighlightedTiles.add(i);
        }
    }

    /**
     * Resets the index of the highlighted tile
     */
    public static void resetHighlightTile(){
        int index = setNewRandomHighlightTileIndex();
        highlightedTileIndex = index;
        Tile tile = tiles.get(index);
        tile.mToBeHighlighted = true;
    }

    /**
     * Returns a random index from list of unhighlighted tile indices
     * @return
     */
    private static int setNewRandomHighlightTileIndex(){
        int N = randomInt(unHighlightedTiles.size());
        int highlightTileIndex = -1;

        int i=0;
        for (int index: unHighlightedTiles){
            if(i==N){
                highlightTileIndex = index;
                break;
            }
            i++;
        }

        unHighlightedTiles.remove(highlightTileIndex);
        return highlightTileIndex;
    }

    /**
     * Returns true if a tile is highlighted
     * @return
     */
    public boolean isToBeHighlighted() {
        return mToBeHighlighted;
    }

    /**
     * Sets a highlighted tile to normal
     */
    public void setHighlightedToFalse(){
        mToBeHighlighted = false;
    }

    /**
     * Returns the index of the highlighted tile
     * @return
     */
    public static int getHighlightedTileIndex(){
        return highlightedTileIndex;
    }


}
