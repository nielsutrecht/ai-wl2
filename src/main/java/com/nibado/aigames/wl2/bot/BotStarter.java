/**
 * Warlight AI Game Bot
 *
 * Last update: January 29, 2015
 *
 * @author Jim van Eeden
 * @version 1.1
 * @License MIT License (http://opensource.org/Licenses/MIT)
 */

package com.nibado.aigames.wl2.bot;

/**
 * This is a simple bot that does random (but correct) moves.
 * This class implements the Bot interface and overrides its Move methods.
 * You can implement these methods yourself very easily now,
 * since you can retrieve all information about the match from variable “state”.
 * When the bot decided on the move to make, it returns an ArrayList of Moves.
 * The bot is started by creating a Parser to which you add
 * a new instance of your bot, and then the parser is started.
 */

import java.util.ArrayList;
import java.util.Set;

import com.nibado.aigames.wl2.map.Region;
import com.nibado.aigames.wl2.move.AttackTransferMove;
import com.nibado.aigames.wl2.move.PlaceArmiesMove;

public class BotStarter implements Bot
{
    @Override
    /**
     * A method that returns which region the bot would like to start on, the pickable regions are stored in the BotState.
     * The bots are asked in turn (ABBAABBAAB) where they would like to start and return a single region each time they are asked.
     * This method returns one random region from the given pickable regions.
     */
    public Region getStartingRegion(final BotState state, final Long timeOut)
    {
        final double rand = Math.random();
        final int r = (int) (rand * state.getPickableStartingRegions().size());
        final int regionId = state.getPickableStartingRegions().get(r).getId();
        final Region startingRegion = state.getFullMap().getRegion(regionId);

        return startingRegion;
    }

    @Override
    /**
     * This method is called for at first part of each round. This example puts two armies on random regions
     * until he has no more armies left to place.
     * @return The list of PlaceArmiesMoves for one round
     */
    public ArrayList<PlaceArmiesMove> getPlaceArmiesMoves(final BotState state, final Long timeOut)
    {

        final ArrayList<PlaceArmiesMove> placeArmiesMoves = new ArrayList<PlaceArmiesMove>();
        final String myName = state.getMyPlayerName();
        final int armies = 2;
        int armiesLeft = state.getStartingArmies();
        final Set<Region> visibleRegions = state.getVisibleMap().getRegions();

        while (armiesLeft > 0)
        {
            final double rand = Math.random();
            final int r = (int) (rand * visibleRegions.size());
            final Region region = null;//visibleRegions.get(r);

            if (region.ownedByPlayer(myName))
            {
                placeArmiesMoves.add(new PlaceArmiesMove(myName, region, armies));
                armiesLeft -= armies;
            }
        }

        return placeArmiesMoves;
    }

    @Override
    /**
     * This method is called for at the second part of each round. This example attacks if a region has
     * more than 6 armies on it, and transfers if it has less than 6 and a neighboring owned region.
     * @return The list of PlaceArmiesMoves for one round
     */
    public ArrayList<AttackTransferMove> getAttackTransferMoves(final BotState state, final Long timeOut)
    {
        final ArrayList<AttackTransferMove> attackTransferMoves = new ArrayList<AttackTransferMove>();
        final String myName = state.getMyPlayerName();
        final int armies = 5;
        final int maxTransfers = 10;
        int transfers = 0;

        for (final Region fromRegion : state.getVisibleMap().getRegions())
        {
            if (fromRegion.ownedByPlayer(myName)) //do an attack
            {
                final ArrayList<Region> possibleToRegions = new ArrayList<Region>();
                possibleToRegions.addAll(fromRegion.getNeighbors());

                while (!possibleToRegions.isEmpty())
                {
                    final double rand = Math.random();
                    final int r = (int) (rand * possibleToRegions.size());
                    final Region toRegion = possibleToRegions.get(r);

                    if (!toRegion.getPlayerName().equals(myName) && fromRegion.getArmies() > 6) //do an attack
                    {
                        attackTransferMoves.add(new AttackTransferMove(myName, fromRegion, toRegion, armies));
                        break;
                    }
                    else if (toRegion.getPlayerName().equals(myName) && fromRegion.getArmies() > 1
                        && transfers < maxTransfers) //do a transfer
                    {
                        attackTransferMoves.add(new AttackTransferMove(myName, fromRegion, toRegion, armies));
                        transfers++;
                        break;
                    }
                    else
                        possibleToRegions.remove(toRegion);
                }
            }
        }

        return attackTransferMoves;
    }

    public static void main(final String[] args)
    {
        final BotParser parser = new BotParser(new BotStarter());
        parser.run();
    }

}
