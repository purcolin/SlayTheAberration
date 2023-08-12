package aberration.commands;

import basemod.devcommands.ConsoleCommand;

public class AberrationCPacks extends ConsoleCommand {
    public AberrationCPacks() {
        maxExtraTokens = 1; //How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 0; //How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
        requiresPlayer = true; //if true, means the command can only be executed if during a run. If unspecified, requiresplayer = false.
        /**
         * If this flag is true and you don't implement your own logic overriding the command syntax check function, it checks if what is typed in is in the options you said the command has.
         * Note that this only applies to the autocompletion feature of the console, and has no bearing on what the command does when executed!
         * If unspecified, simplecheck = false.
         */


        followup.put("replace", AberrationCPacksReplace.class);
        /**
         * Doing this adds this word as a possible followup to your current command, and passes it to YourSecondCommand.
         * You may add as many of these as you like.
         */
    }

    @Override
    protected void execute(String[] strings, int i) {

    }
}