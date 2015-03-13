package com.nibado.aigames.wl2.map;

import org.junit.Before;

public class MapTest {
    private Map map;

    @Before
    public void setup() {
        map = new Map();
    }

    public void testAddRegion() {
        final SuperRegion sr1 = new SuperRegion(1, 10);
        final SuperRegion sr2 = new SuperRegion(2, 20);
        map.add(sr1);
        map.add(sr2);

        final Region r11 = new Region(11, sr1);
        final Region r12 = new Region(12, sr1);
        final Region r21 = new Region(21, sr2);
        final Region r22 = new Region(22, sr2);

        map.add(r11);
        map.add(r12);
        map.add(r21);
        map.add(r22);

        map.

    }
}
