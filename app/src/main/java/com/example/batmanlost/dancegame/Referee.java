package com.example.batmanlost.dancegame;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Game Referee
 * Created by BatmanLost on 22-03-2016.
 */
public class Referee {

    // single referee exists for a game
    private static final Referee SINGLETON_REFEREE = new Referee();

    // all players playing the game
    private List<Player> mPlayers;
    // helps refree to remember players turns while playing
    private int mPlayerTurn;

    // these store the game results after game is finished
    private int mWinner;
    private int mLoser;
    private boolean mGameFinished;


    /**
     * Uninstantaible class since Referee must be a singleton class
     */
    private Referee() {
        mPlayers = new ArrayList<>();
    }

    /**
     * Referee starts game with two players
     * @return
     */
    public static Referee twoPlayerGame(){
        // add two new players to play game
        SINGLETON_REFEREE.callUp(new Player("Player 1"));
        SINGLETON_REFEREE.callUp(new Player("Player 2"));
        // assigns a player to begin game
        SINGLETON_REFEREE.mPlayerTurn = 0;
        // game has just started
        SINGLETON_REFEREE.mGameFinished = false;
//        Player.setMaxTileCount(SINGLETON_REFEREE.maxNoOfTouches);   // ignore
        return SINGLETON_REFEREE;
    }

    /**
     * Adds player to game
     * @param player
     */
    private void callUp(Player player){
        mPlayers.add(player);
    }

    /**
     * Game over if player presses the wrong tile
     * @param tile
     * @return
     */
    public boolean playerHasPressedHighlightedTile(Tile tile){
        Player currentPlayer = mPlayers.get(mPlayerTurn);
        // if player presses an unhighlighted tile, game over, he loses
        if ( !tile.isToBeHighlighted() || !currentPlayer.isUsingSpecifiedNumberOfFingers()){   // ignore the second predicate
            // game is over, declare  results
            declareLoser();
            return false;
        }
        // add that tile to current player's collection and ask the other player to continue
        else {
            currentPlayer.pressed(tile);
            alternateTurn();
            return true;
        }
    }

    /**
     * Declares winner and loser appropriately
     */
    private void declareLoser() {
        // In this game, game is over when a player makes a mistake and he is the loser
        mLoser = mPlayerTurn;
        if (mPlayerTurn ==0){ mWinner = 1;}
        else {mWinner = 0;}
        mGameFinished = true;
    }

    /**
     * Changes player turns alternately
     */
    private void alternateTurn(){
        if (mPlayerTurn == 0){ mPlayerTurn =1;}
        else {mPlayerTurn = 0;}
    }


    /**
     * Game is finished when a player unpresses a tile
     * Referee updates winner and loser
     * @param tile
     */
    public void tileUnPressed(Tile tile){
        int winner, loser;
        if(mPlayers.get(0).hasPressed(tile)){
            winner = 1; loser= 0;
        }
        else { winner = 0; loser = 1;}
        mWinner = winner;
        mLoser = loser;
        mGameFinished = true;
    }

    /**
     *  Returns winner's name
     * @return
     */
    public String getWinnerName() {
        return mPlayers.get(mWinner).getName();
    }

    /**
     * Returns loser's name
     * @return
     */
    public String getLoserrName() {
        return mPlayers.get(mLoser).getName();
    }

    /**
     * Returns the name of player to play now
     * @return
     */
    public String getCurrentPlayerName(){
        return mPlayers.get(mPlayerTurn).getName();
    }


    /**
     * Returns true if game is over
     * @return
     */
    public boolean isGameFinished() {
        return mGameFinished;
    }



    /***** Code for checking if player is using specified number of fingers (Working on it)******/

    private int maxNoOfTouches;

    public void setMaxNoOfTouches(int maxNoOfTouches) {
        this.maxNoOfTouches = maxNoOfTouches;
    }

    public boolean playerHasUnPressedCorrectTile(Tile tile) {
        Player currentPlayer = mPlayers.get(mPlayerTurn);
        if (currentPlayer.getFirstPressedTile() != tile) {
            declareLoser();
            return false;
        }
        return true;
    }

}
