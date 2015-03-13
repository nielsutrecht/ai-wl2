package com.nibado.aigames.wl2.bot.strategy;

import com.nibado.aigames.wl2.bot.BotState;
import com.nibado.aigames.wl2.bot.RegionPicker;
import com.nibado.aigames.wl2.map.Region;
import com.nibado.aigames.wl2.map.SuperRegion;

public class BasicRegionPicker implements RegionPicker {
    private SuperRegion favoriteSuperRegion;

    @Override
    public Region getStartingRegion(final BotState state, final Long timeOut) {
        if (favoriteSuperRegion == null) {
            favoriteSuperRegion = getBestSuperRegion(state);
        }
        Region last = null;
        for (final Region r : state.getPickableStartingRegions()) {
            if (favoriteSuperRegion.getSubRegions().contains(r)) {
                return r;
            }
            last = r;
        }

        return last;
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
