package aberration.commands;

import aberration.patch.InfectedMonsterRoom;
import basemod.BaseMod;
import basemod.DevConsole;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapEdge;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class AberrationFight extends ConsoleCommand {
    public AberrationFight() {
        maxExtraTokens = 1; //How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 1; //How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = true; //if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
        this.simpleCheck = true;
        /**
         * If this flag is true and you don't implement your own logic overriding the command syntax check function, it checks if what is typed in is in the options you said the command has.
         * Note that this only applies to the autocompletion feature of the console, and has no bearing on what the command does when executed!
         * If unspecified, simplecheck = false.
         */

        /**
         * Doing this adds this word as a possible followup to your current command, and passes it to YourSecondCommand.
         * You may add as many of these as you like.
         */
    }

    @Override
    protected void execute(String[] tokens, int depth) {
        MapRoomNode cur = AbstractDungeon.currMapNode;
        if (cur == null) {
            DevConsole.log("cannot fight when there is no map");
        } else {
            String[] encounterArray = (String[]) Arrays.copyOfRange(tokens, 1, tokens.length);
            String encounterName = String.join(" ", encounterArray);
            if (BaseMod.underScoreEncounterIDs.containsKey(encounterName)) {
                encounterName = (String)BaseMod.underScoreEncounterIDs.get(encounterName);
            }

            if (AbstractDungeon.getCurrRoom() instanceof MonsterRoom) {
                AbstractDungeon.monsterList.add(1, encounterName);
            } else {
                AbstractDungeon.monsterList.add(0, encounterName);
            }

            MapRoomNode node = new MapRoomNode(cur.x, cur.y);
            node.room = new InfectedMonsterRoom(true);
            ArrayList<MapEdge> curEdges = cur.getEdges();
            Iterator var8 = curEdges.iterator();

            while(var8.hasNext()) {
                MapEdge edge = (MapEdge)var8.next();
                node.addEdge(edge);
            }

            AbstractDungeon.nextRoom = node;
            AbstractDungeon.nextRoomTransitionStart();
        }
    }

    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList();
        Iterator var4 = BaseMod.encounterList.iterator();

        while(var4.hasNext()) {
            String id = (String)var4.next();
            result.add(id.replace(' ', '_'));
        }

        return result;
    }
}