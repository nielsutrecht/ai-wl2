/**
 * Warlight AI Game Bot
 *
 * Last update: January 29, 2015
 *
 * @author Jim van Eeden
 * @version 1.1
 * @License MIT License (http://opensource.org/Licenses/MIT)
 */

package com.nibado.test.wlai.move;

import com.nibado.test.wlai.map.Region;

/**
 * This Move is used in the second part of each round. It represents the attack or transfer of armies from
 * fromRegion to toRegion. If toRegion is owned by the player himself, it's a transfer. If toRegion is
 * owned by the opponent, this Move is an attack.
 */

public class AttackTransferMove extends Move {

    private final Region fromRegion;
    private final Region toRegion;
    private int armies;

    public AttackTransferMove(final String playerName, final Region fromRegion, final Region toRegion, final int armies)
    {
        super.setPlayerName(playerName);
        this.fromRegion = fromRegion;
        this.toRegion = toRegion;
        this.armies = armies;
    }

    /**
     * @param n Sets the number of armies of this Move
     */
    public void setArmies(final int n) {
        armies = n;
    }

    /**
     * @return The Region this Move is attacking or transferring from
     */
    public Region getFromRegion() {
        return fromRegion;
    }

    /**
     * @return The Region this Move is attacking or transferring to
     */
    public Region getToRegion() {
        return toRegion;
    }

    /**
     * @return The number of armies this Move is attacking or transferring with
     */
    public int getArmies() {
        return armies;
    }

    /**
     * @return A string representation of this Move
     */
    public String getString() {
        if (getIllegalMove().equals(""))
            return getPlayerName() + " attack/transfer " + fromRegion.getId() + " " + toRegion.getId() + " " + armies;
        else
            return getPlayerName() + " illegal_move " + getIllegalMove();
    }

}
