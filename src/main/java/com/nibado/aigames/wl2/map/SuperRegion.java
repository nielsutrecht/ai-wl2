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
import java.util.Set;

public class SuperRegion {

    private final int id;
    private final int armiesReward;
    private final Set<Region> subRegions;

    public SuperRegion(final int id, final int armiesReward)
    {
        this.id = id;
        this.armiesReward = armiesReward;
        subRegions = new HashSet<>();
    }

    public void addSubRegion(final Region subRegion)
    {
        if (!subRegions.contains(subRegion))
            subRegions.add(subRegion);
    }

    /**
     * @return A string with the name of the player that fully owns this SuperRegion
     */
    public String ownedByPlayer()
    {
        String previousPlayerName = null;
        for (final Region region : subRegions)
        {
            if (region.getPlayerName() == null) {
                return null;
            }
            if (previousPlayerName == null) {
                previousPlayerName = region.getPlayerName();
                continue;
            }

            if (!region.getPlayerName().equals(previousPlayerName)) {
                return null;
            }
        }
        return previousPlayerName;
    }

    /**
     * @return The id of this SuperRegion
     */
    public int getId() {
        return id;
    }

    /**
     * @return The number of armies a Player is rewarded when he fully owns this SuperRegion
     */
    public int getArmiesReward() {
        return armiesReward;
    }

    /**
     * @return A list with the Regions that are part of this SuperRegion
     */
    public Set<Region> getSubRegions() {
        return subRegions;
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
        final SuperRegion other = (SuperRegion) obj;
        if (id != other.id)
            return false;
        return true;
    }
}
