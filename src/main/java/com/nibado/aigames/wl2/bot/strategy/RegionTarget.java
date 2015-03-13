package com.nibado.aigames.wl2.bot.strategy;

import java.util.Comparator;

import com.nibado.aigames.wl2.map.Region;

public class RegionTarget {
    private final Region region;
    private double score;
    private int targetArmies;
    private int captureCost;
    private Type type;

    public RegionTarget(final Region region) {
        this.region = region;
    }

    public Region getRegion() {
        return region;
    }

    public int getTargetArmies() {
        return targetArmies;
    }

    public void setTargetArmies(final int targetArmies) {
        this.targetArmies = targetArmies;
    }

    public int getCaptureCost() {
        return captureCost;
    }

    public void setCaptureCost(final int captureCost) {
        this.captureCost = captureCost;
    }

    public Type getType() {
        return type;
    }

    public void setType(final Type type) {
        this.type = type;
    }

    public enum Type {
        DEFEND,
        BORDER,
        TERRITORY,
        CAPTURE
    }

    public static Comparator<RegionTarget> getComparator() {
        return new Comparator<RegionTarget>() {
            @Override
            public int compare(final RegionTarget rt1, final RegionTarget rt2) {
                return Double.compare(rt1.score, rt2.score);
            }
        };
    }

}
