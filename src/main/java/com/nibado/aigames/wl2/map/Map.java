/**
 * Warlight AI Game Bot
 *
 * Last update: January 29, 2015
 *
 * @author Jim van Eeden
 * @version 1.1
 * @License MIT License (http://opensource.org/Licenses/MIT)
 */

package com.nibado.aigames.wl2.map;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Map {

    private final Set<Region> regions;
    private final Set<SuperRegion> superRegions;

    public Map()
    {
        this.regions = new HashSet<>();
        this.superRegions = new HashSet<>();
    }

    public Map(final List<Region> regions, final List<SuperRegion> superRegions)
    {
        this.regions = new HashSet<>(regions);
        this.superRegions = new HashSet<>(superRegions);
    }

    /**
     * add a Region to the map
     * @param region : Region to be added
     */
    public void add(final Region region)
    {
        regions.add(region);
    }

    /**
     * add a SuperRegion to the map
     * @param superRegion : SuperRegion to be added
     */
    public void add(final SuperRegion superRegion)
    {
        superRegions.add(superRegion);
    }

    /**
     * @return : a new Map object exactly the same as this one
     */
    public Map getMapCopy() {
        final Map newMap = new Map();
        for (final SuperRegion sr : superRegions) //copy superRegions
        {
            final SuperRegion newSuperRegion = new SuperRegion(sr.getId(), sr.getArmiesReward());
            newMap.add(newSuperRegion);
        }
        for (final Region r : regions) //copy regions
        {
            final Region newRegion = new Region(r.getId(), newMap.getSuperRegion(r.getSuperRegion().getId()), r.getPlayerName(), r.getArmies());
            newMap.add(newRegion);
        }
        for (final Region r : regions) //add neighbors to copied regions
        {
            final Region newRegion = newMap.getRegion(r.getId());
            for (final Region neighbor : r.getNeighbors())
                newRegion.addNeighbor(newMap.getRegion(neighbor.getId()));
        }
        return newMap;
    }

    /**
     * @return : the list of all Regions in this map
     */
    public Set<Region> getRegions() {
        return regions;
    }

    /**
     * @return : the list of all SuperRegions in this map
     */
    public Set<SuperRegion> getSuperRegions() {
        return superRegions;
    }

    /**
     * @param id : a Region id number
     * @return : the matching Region object
     */
    public Region getRegion(final int id)
    {
        for (final Region region : regions)
            if (region.getId() == id)
                return region;
        return null;
    }

    /**
     * @param id : a SuperRegion id number
     * @return : the matching SuperRegion object
     */
    public SuperRegion getSuperRegion(final int id)
    {
        for (final SuperRegion superRegion : superRegions)
            if (superRegion.getId() == id)
                return superRegion;
        return null;
    }

    public String getMapString()
    {
        String mapString = "";
        for (final Region region : regions)
        {
            mapString = mapString.concat(region.getId() + ";" + region.getPlayerName() + ";" + region.getArmies() + " ");
        }
        return mapString;
    }

}
