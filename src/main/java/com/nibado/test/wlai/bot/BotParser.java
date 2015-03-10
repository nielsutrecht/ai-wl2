/**
 * Warlight AI Game Bot
 *
 * Last update: January 29, 2015
 *
 * @author Jim van Eeden
 * @version 1.1
 * @License MIT License (http://opensource.org/Licenses/MIT)
 */

package com.nibado.test.wlai.bot;

import java.util.ArrayList;
import java.util.Scanner;

import com.nibado.test.wlai.map.Region;
import com.nibado.test.wlai.move.AttackTransferMove;
import com.nibado.test.wlai.move.PlaceArmiesMove;

public class BotParser {

    final Scanner scan;

    final Bot bot;

    BotState currentState;

    public BotParser(final Bot bot)
    {
        this.scan = new Scanner(System.in);
        this.bot = bot;
        this.currentState = new BotState();
    }

    public void run()
    {
        while (scan.hasNextLine())
        {
            final String line = scan.nextLine().trim();
            if (line.length() == 0) {
                continue;
            }
            final String[] parts = line.split(" ");
            if (parts[0].equals("pick_starting_region")) //pick which regions you want to start with
            {
                currentState.setPickableStartingRegions(parts);
                final Region startingRegion = bot.getStartingRegion(currentState, Long.valueOf(parts[1]));

                System.out.println(startingRegion.getId());
            }
            else if (parts.length == 3 && parts[0].equals("go"))
            {
                //we need to do a move
                String output = "";
                if (parts[1].equals("place_armies"))
                {
                    //place armies
                    final ArrayList<PlaceArmiesMove> placeArmiesMoves = bot.getPlaceArmiesMoves(currentState, Long.valueOf(parts[2]));
                    for (final PlaceArmiesMove move : placeArmiesMoves)
                        output = output.concat(move.getString() + ",");
                }
                else if (parts[1].equals("attack/transfer"))
                {
                    //attack/transfer
                    final ArrayList<AttackTransferMove> attackTransferMoves = bot.getAttackTransferMoves(currentState, Long.valueOf(parts[2]));
                    for (final AttackTransferMove move : attackTransferMoves)
                        output = output.concat(move.getString() + ",");
                }
                if (output.length() > 0)
                    System.out.println(output);
                else
                    System.out.println("No moves");
            }
            else if (parts[0].equals("settings")) {
                //update settings
                currentState.updateSettings(parts[1], parts);
            }
            else if (parts[0].equals("setup_map")) {
                //initial full map is given
                currentState.setupMap(parts);
            }
            else if (parts[0].equals("update_map")) {
                //all visible regions are given
                currentState.updateMap(parts);
            }
            else if (parts[0].equals("opponent_moves")) {
                //all visible opponent moves are given
                currentState.readOpponentMoves(parts);
            }
            else {
                System.err.printf("Unable to parse line \"%s\"\n", line);
            }
        }
    }

}
