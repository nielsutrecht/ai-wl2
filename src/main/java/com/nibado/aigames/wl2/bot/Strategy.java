package com.nibado.aigames.wl2.bot;

import java.util.ArrayList;

import com.nibado.aigames.wl2.move.AttackTransferMove;
import com.nibado.aigames.wl2.move.PlaceArmiesMove;

public interface Strategy {
    void update(BotState state, long timeout);

    ArrayList<PlaceArmiesMove> getPlaceArmiesMoves();

    ArrayList<AttackTransferMove> getAttackTransferMoves();
}
