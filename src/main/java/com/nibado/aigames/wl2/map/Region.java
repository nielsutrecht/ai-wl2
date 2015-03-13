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

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Region {

    private final int id;
    private final LinkedList<Region> neighbors;
    private final SuperRegion superRegion;
    private int armies;
    private String playerName;

    public Region(final int id, final SuperRegion superRegion)
    {
        this.id = id;
        this.superRegion = superRegion;
        this.neighbors = new LinkedList<Region>();
        this.playerName = "unknown";
        this.armies = 0;

        superRegion.addSubRegion(this);
    }

    public Region(final int id, final SuperRegion superRegion, final String playerName, final int armies)
    {
        this.id = id;
        this.superRegion = superRegion;
        this.neighbors = new LinkedList<Region>();
        this.playerName = playerName;
        this.armies = armies;

        superRegion.addSubRegion(this);
    }

    public void addNeighbor(final Region neighbor)
    {
        if (!neighbors.contains(neighbor))
        {
            neighbors.add(neighbor);
            neighbor.addNeighbor(this);
        }
    }

    /**
     * @param region a Region object
     * @return True if this Region is a neighbor of given Region, false otherwise
     */
    public boolean isNeighbor(final Region region)
    {
        if (neighbors.contains(region))
            return true;
        return false;
    }

    /**
     * @param playerName A string with a player's name
     * @return True if this region is owned by given playerName, false otherwise
     */
    public boolean ownedByPlayer(final String playerName)
    {
        if (playerName.equals(this.playerName))
            return true;
        return false;
    }

    public boolean sameSuperRegion(final Region other) {
        return superRegion.getId() == other.superRegion.getId();
    }

    public List<Region> getNeighborsInDifferentSuperRegion() {
        final List<Region> result = new ArrayList<>();

        for (final Region neighbor : neighbors) {
            if (!sameSuperRegion(neighbor)) {
                result.add(neighbor);
            }
        }

        return result;
    }

    /**
     * @param armies Sets the number of armies that are on this Region
     */
    public void setArmies(final int armies) {
        this.armies = armies;
    }

    /**
     * @param playerName Sets the Name of the player that this Region belongs to
     */
    public void setPlayerName(final String playerName) {
        this.playerName = playerName;
    }

    /**
     * @return The id of this Region
     */
    public int getId() {
        return id;
    }

    /**
     * @return A list of this Region's neighboring Regions
     */
    public LinkedList<Region> getNeighbors() {
        return neighbors;
    }

    /**
     * @return The SuperRegion this Region is part of
     */
    public SuperRegion getSuperRegion() {
        return superRegion;
    }

    /**
     * @return The number of armies on this region
     */
    public int getArmies() {
        return armies;
    }

    /**
     * @return A string with the name of the player that owns this region
     */
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final Region other = (Region) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
