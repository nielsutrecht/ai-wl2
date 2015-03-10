package com.nibado.aigames.wl2.bot;

import java.util.ArrayList;

import com.nibado.aigames.wl2.map.Region;
import com.nibado.aigames.wl2.map.SuperRegion;
import com.nibado.aigames.wl2.move.AttackTransferMove;
import com.nibado.aigames.wl2.move.PlaceArmiesMove;

public class HydraBot implements Bot {
    private SuperRegion favoriteSuperRegion;

    @Override
    public Region getStartingRegion(final BotState state, final Long timeOut) {
        if (favoriteSuperRegion == null) {
            favoriteSuperRegion = getBestSuperRegion(state);
        }
        for (final Region r : state.getPickableStartingRegions()) {
            if (favoriteSuperRegion.getSubRegions().contains(r)) {
                return r;
            }
        }

        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<PlaceArmiesMove> getPlaceArmiesMoves(final BotState state, final Long timeOut) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ArrayList<AttackTransferMove> getAttackTransferMoves(final BotState state, final Long timeOut) {
        // TODO Auto-generated method stub
        return null;
    }

    private SuperRegion getBestSuperRegion(final BotState state) {
        SuperRegion picked = null;
        for (final SuperRegion superRegion : state.getVisibleMap().getSuperRegions()) {
            if (picked == null || superRegion.getArmiesReward() > picked.getArmiesReward()) {
                picked = superRegion;
            }
        }

        return picked;

    }

}
