package aberration.patch;

import aberration.AberrationMod;
import aberration.relics.injector;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@SpirePatch2(
        clz= GridCardSelectScreen.class,
        method="update"
)
public class GridCardSelectScreenPatch {
    private static final Logger logger = LogManager.getLogger(GridCardSelectScreenPatch.class.getName());
    public GridCardSelectScreenPatch(){

    }

    @SpireInsertPatch(
            locator=Locator.class,
            localvars = {"upgradePreviewCard"}
    )
    public static SpireReturn<Void> Insert(GridCardSelectScreen __instance,AbstractCard ___hoveredCard,String[] ___TEXT) {
        logger.info("=========================  preview hook =========================");
        if(AbstractDungeon.player.getRelic(injector.ID).checkTrigger()){
            CardModifierManager.addModifier(__instance.upgradePreviewCard, AberrationMod.CurrentAberrationPacks.get(AberrationMod.currentDungeon()).card);
            __instance.upgradePreviewCard.displayUpgrades();
            __instance.upgradePreviewCard.drawScale = 0.875F;
            ___hoveredCard.stopGlowing();
            __instance.selectedCards.clear();
            AbstractDungeon.overlayMenu.cancelButton.show(___TEXT[1]);
            __instance.confirmButton.show();
            __instance.confirmButton.isDisabled = false;
//            __instance.lastTip = __instance.tipMsg;
//            __instance.tipMsg = ___TEXT[2];
            return SpireReturn.Return();
        }
        return SpireReturn.Continue();
    }

    private static class Locator extends SpireInsertLocator {
        // This is the abstract method from SpireInsertLocator that will be used to find the line
        // numbers you want this patch inserted at
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            // finalMatcher is the line that we want to insert our patch before -
            // in this example we are using a `MethodCallMatcher` which is a type
            // of matcher that matches a method call based on the type of the calling
            // object and the name of the method being called. Here you can see that
            // we're expecting the `end` method to be called on a `SpireBatch`
            Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractCard.class, "upgrade");

            // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
            // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
            // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
            // that matches the finalMatcher.
            return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<Matcher>(), finalMatcher);
        }
    }
}
