/**
 * Warlight AI Game Bot
 *
 * Last update: January 29, 2015
 *
 * @author Jim van Eeden
 * @version 1.1
 * @License MIT License (http://opensource.org/Licenses/MIT)
 */

package com.nibado.aigames.wl2.move;

public class Move {

    private String playerName; //name of the player that did this move
    private String illegalMove = ""; //gets the value of the error message if move is illegal, else remains empty

    /**
     * @param playerName Sets the name of the Player that this Move belongs to
     */
    public void setPlayerName(final String playerName) {
        this.playerName = playerName;
    }

    /**
     * @param illegalMove Sets the error message of this move. Only set this if the Move is illegal.
     */
    public void setIllegalMove(final String illegalMove) {
        this.illegalMove = illegalMove;
    }

    /**
     * @return The Player's name that this Move belongs to
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @return The error message of this Move
     */
    public String getIllegalMove() {
        return illegalMove;
    }

}
