package com.nibado.aigames.wl2.bot;

import com.nibado.aigames.wl2.map.Region;

public interface RegionPicker {
    Region getStartingRegion(final BotState state, final Long timeOut);
}
