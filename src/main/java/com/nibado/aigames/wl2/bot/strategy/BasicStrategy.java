package com.nibado.aigames.wl2.bot.strategy;

import java.util.ArrayList;

import com.nibado.aigames.wl2.bot.BotState;
import com.nibado.aigames.wl2.bot.Strategy;
import com.nibado.aigames.wl2.move.AttackTransferMove;
import com.nibado.aigames.wl2.move.PlaceArmiesMove;

/*
 * v2
 * - Tries to claim regions in a favorable super-region
 * - Goes for regions that border other super-regions firsts
 */
public class BasicStrategy implements Strategy {

    @Override
    public void update(final BotState state, final long timeout) {
    }

    @Override
    public ArrayList<PlaceArmiesMove> getPlaceArmiesMoves() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<AttackTransferMove> getAttackTransferMoves() {
        // TODO Auto-generated method stub
        return null;
    }

}
