package aberration.commands;

import aberration.AberrationMod;
import aberration.packs.ColdDescent.ColdDescentPack;
import basemod.devcommands.ConsoleCommand;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import org.lwjgl.Sys;

import java.util.ArrayList;

public class AberrationGeneAdd extends ConsoleCommand {
    public AberrationGeneAdd() {
        maxExtraTokens = 1; //How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 0; //How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = true; //if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
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
        AberrationMod.packsByID.get(tokens[depth]).ApplyGene(AbstractDungeon.player);
    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        for (String i : AberrationMod.packsByID.keySet()) {
            result.add(i);
        }

        if(result.contains(tokens[depth])) {
            complete = true;
            /**
             * Setting complete to true displays "Command is complete" in the autocomplete window.
             * This is not necessary if "simplecheck = true" in the constructor and you don't have additional logic for it!
             */
        }

        return result;
    }
}
