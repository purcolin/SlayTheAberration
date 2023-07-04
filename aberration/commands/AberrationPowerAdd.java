package aberration.commands;

import aberration.AberrationMod;
import aberration.packs.AbstractAberrationPack;
import aberration.packs.ColdDescent.ColdDescentPack;
import aberration.packs.DeepDescent.DeepDescentPack;
import basemod.devcommands.ConsoleCommand;
import basemod.devcommands.relic.Relic;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.Arrays;
import java.util.Iterator;

public class AberrationPowerAdd extends ConsoleCommand {
    public AberrationPowerAdd() {
        maxExtraTokens = 0; //How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
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
    protected void execute(String[] strings, int i) {
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        DeepDescentPack d = new DeepDescentPack();
        while (var1.hasNext()) {
            AbstractMonster m = (AbstractMonster) var1.next();
            d.ApplyBossPower(m);
        }
    }
}