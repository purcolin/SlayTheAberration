package aberration.commands;

import aberration.AberrationMod;
import basemod.devcommands.ConsoleCommand;

import java.util.ArrayList;

public class AberrationCPacksReplace extends ConsoleCommand {
    public AberrationCPacksReplace() {
        maxExtraTokens = 1; //How many additional words can come after this one. If unspecified, maxExtraTokens = 1.
        minExtraTokens = 1; //How many additional words have to come after this one. If unspecified, minExtraTokens = 0.
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
        System.out.println(tokens[depth+1]);
        System.out.println(tokens[depth]);
        int var0 = 0;
        switch(tokens[depth+1]){
            case "0":
                var0 = 0;
                break;
            case "1":
                var0 = 1;
                break;
            case "2":
                var0 = 2;
                break;
            case "3":
                var0 = 3;
                break;
        }
        AberrationMod.CurrentAberrationPacks.set(var0 ,AberrationMod.packsByID.get(tokens[depth]));

    }

    @Override
    public ArrayList<String> extraOptions(String[] tokens, int depth) {
        ArrayList<String> result = new ArrayList<>();
        if (tokens.length > depth + 1 && tokens[depth + 1].matches("\\d*")) {
            if (tokens.length > depth + 2) {
                if (tokens[depth + 2].matches("\\d+")) {
                    ConsoleCommand.complete = true;
                } else if (tokens[depth + 2].length() > 0) {
                    tooManyTokensError();
                }
            }
            result.add("0");
            result.add("1");
            result.add("2");
            result.add("3");
        }else {
            for (String i : AberrationMod.packsByID.keySet()) {
                result.add(i);
            }
        }

        return result;
    }
}
