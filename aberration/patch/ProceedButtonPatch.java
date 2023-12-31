package aberration.patch;

import aberration.AberrationMod;
import aberration.relics.injector;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import com.megacrit.cardcrawl.screens.select.GridCardSelectScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;

import static com.megacrit.cardcrawl.neow.NeowEvent.OPTIONS;

@SpirePatch2(
        clz= ProceedButton.class,
        method="update"
)
public class ProceedButtonPatch {
        private static final Logger logger = LogManager.getLogger(ProceedButtonPatch.class.getName());

        @SpireInsertPatch(
                locator= ProceedButtonPatch.Locator.class
        )
        public static SpireReturn<Void> Insert(ProceedButton __instance) {
//          添加显示畸变的screen
            logger.info("=========================  proceed hook =========================");
            logger.info(AbstractDungeon.screen);
//            if(AberrationMod.IsShowAberrationScreen){
//                logger.info("this is neo!");
//                __instance.setLabel("深入调查");
//                AberrationMod.IsShowAberrationScreen = false;
////                AbstractDungeon.screen = AbstractDungeon.CurrentScreen.COMBAT_REWARD;
//                logger.info(AbstractDungeon.getCurrRoom().getClass().getSimpleName());
//                logger.info(AbstractDungeon.getCurrRoom().phase);
//                AbstractDungeon.getCurrRoom().event.roomEventText.addDialogOption(OPTIONS[1]);
//                AbstractDungeon.isScreenUp = false;
//                AbstractDungeon.overlayMenu.proceedButton.hide();
//                return SpireReturn.Return();
//            }
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
//                Matcher preMatcher = new Matcher.MethodCallMatcher(ProceedButton.class, "goToDemoVictoryRoom");
//                Matcher finalMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "getCurrRoom");
                Matcher finalMatcher = new Matcher.InstanceOfMatcher(MonsterRoomBoss.class);
                // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
                // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
                // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
                // that matches the finalMatcher.
                ArrayList<Matcher> pre = new ArrayList<>();
                pre.add(new Matcher.InstanceOfMatcher(NeowRoom.class));
                return LineFinder.findInOrder(ctMethodToPatch, pre, finalMatcher);
            }


        }



}
