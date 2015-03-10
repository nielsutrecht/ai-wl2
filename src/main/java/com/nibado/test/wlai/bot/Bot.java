/**
 * Warlight AI Game Bot
 *
 * Last update: January 29, 2015
 *
 * @author Jim van Eeden
 * @version 1.1
 * @License MIT License (http://opensource.org/Licenses/MIT)
 */

package com.nibado.test.wlai.bot;

import java.util.ArrayList;

import com.nibado.test.wlai.map.Region;
import com.nibado.test.wlai.move.AttackTransferMove;
import com.nibado.test.wlai.move.PlaceArmiesMove;

public interface Bot {

    public Region getStartingRegion(BotState state, Long timeOut);

    public ArrayList<PlaceArmiesMove> getPlaceArmiesMoves(BotState state, Long timeOut);

    public ArrayList<AttackTransferMove> getAttackTransferMoves(BotState state, Long timeOut);

}
