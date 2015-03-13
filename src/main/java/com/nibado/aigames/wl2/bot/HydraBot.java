package com.nibado.aigames.wl2.bot;

import java.util.ArrayList;

import com.nibado.aigames.wl2.map.Region;
import com.nibado.aigames.wl2.move.AttackTransferMove;
import com.nibado.aigames.wl2.move.PlaceArmiesMove;

public class HydraBot implements Bot {

    private final RegionPicker picker;
    private final Strategy strategy;

    public HydraBot(final RegionPicker picker, final Strategy strategy) {
        this.picker = picker;
        this.strategy = strategy;
    }

    @Override
    public ArrayList<PlaceArmiesMove> getPlaceArmiesMoves(final BotState state, final Long timeOut) {
        strategy.update(state, timeOut);

        return strategy.getPlaceArmiesMoves();
    }

    @Override
    public ArrayList<AttackTransferMove> getAttackTransferMoves(final BotState state, final Long timeOut) {
        return strategy.getAttackTransferMoves();
    }

    @Override
    public Region getStartingRegion(final BotState state, final Long timeOut) {
        return picker.getStartingRegion(state, timeOut);
    }

}
