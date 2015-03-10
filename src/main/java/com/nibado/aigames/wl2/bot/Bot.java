/**
 * Warlight AI Game Bot
 *
 * Last update: January 29, 2015
 *
 * @author Jim van Eeden
 * @version 1.1
 * @License MIT License (http://opensource.org/Licenses/MIT)
 */

package com.nibado.aigames.wl2.bot;

import java.util.ArrayList;

import com.nibado.aigames.wl2.map.Region;
import com.nibado.aigames.wl2.move.AttackTransferMove;
import com.nibado.aigames.wl2.move.PlaceArmiesMove;

public interface Bot {

    public Region getStartingRegion(BotState state, Long timeOut);

    public ArrayList<PlaceArmiesMove> getPlaceArmiesMoves(BotState state, Long timeOut);

    public ArrayList<AttackTransferMove> getAttackTransferMoves(BotState state, Long timeOut);

}
