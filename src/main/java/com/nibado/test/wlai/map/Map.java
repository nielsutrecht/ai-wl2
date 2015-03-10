/**
 * Warlight AI Game Bot
 *
 * Last update: January 29, 2015
 *
 * @author Jim van Eeden
 * @version 1.1
 * @License MIT License (http://opensource.org/Licenses/MIT)
 */

package com.nibado.test.wlai.map;

import java.util.LinkedList;

public class Map {

    public LinkedList<Region> regions;
    public LinkedList<SuperRegion> superRegions;

    public Map()
    {
        this.regions = new LinkedList<Region>();
        this.superRegions = new LinkedList<SuperRegion>();
    }

    public Map(final LinkedList<Region> regions, final LinkedList<SuperRegion> superRegions)
    {
        this.regions = regions;
        this.superRegions = superRegions;
    }

    /**
     * add a Region to the map
     * @param region : Region to be added
     */
    public void add(final Region region)
    {
        for (final Region r : regions)
            if (r.getId() == region.getId())
            {
                System.err.println("Region cannot be added: id already exists.");
                return;
            }
        regions.add(region);
    }

    /**
     * add a SuperRegion to the map
     * @param superRegion : SuperRegion to be added
     */
    public void add(final SuperRegion superRegion)
    {
        for (final SuperRegion s : superRegions)
            if (s.getId() == superRegion.getId())
            {
                System.err.println("SuperRegion cannot be added: id already exists.");
                return;
            }
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
    public LinkedList<Region> getRegions() {
        return regions;
    }

    /**
     * @return : the list of all SuperRegions in this map
     */
    public LinkedList<SuperRegion> getSuperRegions() {
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
