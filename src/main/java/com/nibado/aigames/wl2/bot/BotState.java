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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.nibado.aigames.wl2.map.Map;
import com.nibado.aigames.wl2.map.Region;
import com.nibado.aigames.wl2.map.SuperRegion;
import com.nibado.aigames.wl2.move.AttackTransferMove;
import com.nibado.aigames.wl2.move.Move;
import com.nibado.aigames.wl2.move.PlaceArmiesMove;

public class BotState {

    private String myName = "";
    private String opponentName = "";

    private final Map fullMap = new Map(); //This map is known from the start, contains all the regions and how they are connected, doesn't change after initialization
    private Map visibleMap; //This map represents everything the player can see, updated at the end of each round.

    private ArrayList<Region> pickableStartingRegions; //list of regions the player can choose the start from
    private Set<Region> wastelands; //wastelands, i.e. neutral regions with a larger amount of armies on them. Given before the picking of starting regions

    private final ArrayList<Move> opponentMoves; //list of all the opponent's moves, reset at the end of each round

    private int startingArmies; //number of armies the player can place on map
    private int maxRounds;
    private int roundNumber;

    private long totalTimebank; //total time that can be in the timebank
    private long timePerMove; //the amount of time that is added to the timebank per requested move

    public BotState()
    {
        opponentMoves = new ArrayList<Move>();
        roundNumber = 0;
    }

    public void updateSettings(final String key, final String[] parts)
    {
        String value;

        if (key.equals("starting_regions") && parts.length > 3) {
            setPickableStartingRegions(parts);
            return;
        }
        value = parts[2];

        if (key.equals("your_bot")) //bot's own name
            myName = parts[2];
        else if (key.equals("opponent_bot")) //opponent's name
            opponentName = value;
        else if (key.equals("max_rounds"))
            maxRounds = Integer.parseInt(value);
        else if (key.equals("timebank"))
            totalTimebank = Long.parseLong(value);
        else if (key.equals("time_per_move"))
            timePerMove = Long.parseLong(value);
        else if (key.equals("starting_armies"))
        {
            startingArmies = Integer.parseInt(value);
            roundNumber++; //next round
        }
    }

    //initial map is given to the bot with all the information except for player and armies info
    public void setupMap(final String[] mapInput)
    {
        int i, regionId, superRegionId, wastelandId, reward;

        if (mapInput[1].equals("super_regions"))
        {
            for (i = 2; i < mapInput.length; i++)
            {
                try {
                    superRegionId = Integer.parseInt(mapInput[i]);
                    i++;
                    reward = Integer.parseInt(mapInput[i]);
                    fullMap.add(new SuperRegion(superRegionId, reward));
                }
                catch (final Exception e) {
                    System.err.println("Unable to parse SuperRegions");
                }
            }
        }
        else if (mapInput[1].equals("regions"))
        {
            for (i = 2; i < mapInput.length; i++)
            {
                try {
                    regionId = Integer.parseInt(mapInput[i]);
                    i++;
                    superRegionId = Integer.parseInt(mapInput[i]);
                    final SuperRegion superRegion = fullMap.getSuperRegion(superRegionId);
                    fullMap.add(new Region(regionId, superRegion));
                }
                catch (final Exception e) {
                    System.err.println("Unable to parse Regions " + e.getMessage());
                }
            }
        }
        else if (mapInput[1].equals("neighbors"))
        {
            for (i = 2; i < mapInput.length; i++)
            {
                try {
                    final Region region = fullMap.getRegion(Integer.parseInt(mapInput[i]));
                    i++;
                    final String[] neighborIds = mapInput[i].split(",");
                    for (int j = 0; j < neighborIds.length; j++)
                    {
                        final Region neighbor = fullMap.getRegion(Integer.parseInt(neighborIds[j]));
                        region.addNeighbor(neighbor);
                    }
                }
                catch (final Exception e) {
                    System.err.println("Unable to parse Neighbors " + e.getMessage());
                }
            }
        }
        else if (mapInput[1].equals("wastelands"))
        {
            wastelands = new HashSet<>();
            for (i = 2; i < mapInput.length; i++)
            {
                try {
                    wastelandId = Integer.parseInt(mapInput[i]);
                    wastelands.add(fullMap.getRegion(wastelandId));
                }
                catch (final Exception e) {
                    System.err.println("Unable to parse wastelands " + e.getMessage());
                }
            }
        }
    }

    //regions from wich a player is able to pick his preferred starting region
    public void setPickableStartingRegions(final String[] input)
    {
        pickableStartingRegions = new ArrayList<Region>();
        for (int i = 2; i < input.length; i++)
        {
            int regionId;
            try {
                regionId = Integer.parseInt(input[i]);
                final Region pickableRegion = fullMap.getRegion(regionId);
                pickableStartingRegions.add(pickableRegion);
            }
            catch (final Exception e) {
                System.err.println("Unable to parse pickable regions " + e.getMessage());
            }
        }
    }

    //visible regions are given to the bot with player and armies info
    public void updateMap(final String[] mapInput)
    {
        visibleMap = fullMap.getMapCopy();
        for (int i = 1; i < mapInput.length; i++)
        {
            try {
                final Region region = visibleMap.getRegion(Integer.parseInt(mapInput[i]));
                final String playerName = mapInput[i + 1];
                final int armies = Integer.parseInt(mapInput[i + 2]);

                region.setPlayerName(playerName);
                region.setArmies(armies);
                i += 2;
            }
            catch (final Exception e) {
                System.err.println("Unable to parse Map Update " + e.getMessage());
            }
        }
        final ArrayList<Region> unknownRegions = new ArrayList<Region>();

        //remove regions which are unknown.
        for (final Region region : visibleMap.getRegions())
            if (region.getPlayerName().equals("unknown"))
                unknownRegions.add(region);
        for (final Region unknownRegion : unknownRegions)
            visibleMap.getRegions().remove(unknownRegion);
    }

    //Parses a list of the opponent's moves every round.
    //Clears it at the start, so only the moves of this round are stored.
    public void readOpponentMoves(final String[] moveInput)
    {
        opponentMoves.clear();
        for (int i = 1; i < moveInput.length; i++)
        {
            try {
                Move move;
                if (moveInput[i + 1].equals("place_armies")) {
                    final Region region = visibleMap.getRegion(Integer.parseInt(moveInput[i + 2]));
                    final String playerName = moveInput[i];
                    final int armies = Integer.parseInt(moveInput[i + 3]);
                    move = new PlaceArmiesMove(playerName, region, armies);
                    i += 3;
                }
                else if (moveInput[i + 1].equals("attack/transfer")) {
                    Region fromRegion = visibleMap.getRegion(Integer.parseInt(moveInput[i + 2]));
                    if (fromRegion == null) //might happen if the region isn't visible
                        fromRegion = fullMap.getRegion(Integer.parseInt(moveInput[i + 2]));

                    Region toRegion = visibleMap.getRegion(Integer.parseInt(moveInput[i + 3]));
                    if (toRegion == null) //might happen if the region isn't visible
                        toRegion = fullMap.getRegion(Integer.parseInt(moveInput[i + 3]));

                    final String playerName = moveInput[i];
                    final int armies = Integer.parseInt(moveInput[i + 4]);
                    move = new AttackTransferMove(playerName, fromRegion, toRegion, armies);
                    i += 4;
                }
                else { //never happens
                    continue;
                }
                opponentMoves.add(move);
            }
            catch (final Exception e) {
                System.err.println("Unable to parse Opponent moves " + e.getMessage());
            }
        }
    }

    public String getMyPlayerName() {
        return myName;
    }

    public String getOpponentPlayerName() {
        return opponentName;
    }

    public int getStartingArmies() {
        return startingArmies;
    }

    public int getRoundNumber() {
        return roundNumber;
    }

    public Map getVisibleMap() {
        return visibleMap;
    }

    public Map getFullMap() {
        return fullMap;
    }

    public ArrayList<Move> getOpponentMoves() {
        return opponentMoves;
    }

    public ArrayList<Region> getPickableStartingRegions() {
        return pickableStartingRegions;
    }

    public Set<Region> getWasteLands() {
        return wastelands;
    }

    public int getMaxRounds() {
        return maxRounds;
    }

    public long getTotalTimebank() {
        return totalTimebank;
    }

    public long getTimePerMove() {
        return timePerMove;
    }

}
