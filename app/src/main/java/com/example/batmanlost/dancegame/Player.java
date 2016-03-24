package com.example.batmanlost.dancegame;


import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

/**
 * Created by BatmanLost on 22-03-2016.
 */
public class Player {

    private String mName;
    private Queue<Tile> mtilesPressed;

    // Cache the hash code
    private volatile int hashCode;

    /**
     * Creates a player with a name and an empty collection of tiles
     * @param name
     */
    public Player(String name) {
        mName = name;
        mtilesPressed = new LinkedList<>();
    }

    /**
     * Adds this tile to player's collection of pressed tiles
     * @param tile
     */
    public void pressed(Tile tile){
        mtilesPressed.add(tile);
    }

    /**
     * Returns true if player has pressed this tile
     * @param tile
     * @return
     */
    public boolean hasPressed(Tile tile) {
        return mtilesPressed.contains(tile);
    }


    /**
     * Returns name of this player
     * @return
     */
    public String getName() {
        return mName;
    }

    /**
     * Returns true if the object is same as this object
     * That is enough for this game
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        else return false;
    }

    /**
     * Overrides hashcode appropriately
     * @return
     */
    @Override
    public int hashCode() {
        int result = hashCode;
        if (result ==0){
            result = 17;
            result = 31* result + mName.hashCode();
            hashCode = result;
        }
        return result;
    }



    /***** Code for checking if player is using specified number of fingers (Working on it)******/


    // maximum no of tiles a player is allowed to touch
    private static int sMaxTileCount;

    public static void setMaxTileCount(int maxTileCount) {
        sMaxTileCount = maxTileCount;
    }

    public boolean isUsingSpecifiedNumberOfFingers(){
/*        if (mtilesPressed.size() > sMaxTileCount){
            return false;
        }*/
        return true;
    }

    public Tile getFirstPressedTile(){
        return mtilesPressed.poll();
    }

}
