package aberration.patch;

import aberration.relics.injector;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.ObtainKeyEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@SpirePatch2(
        clz= SpireHeart.class,
        method="buttonEffect"
)
public class SpireHeartPatch {
        private static final Logger logger = LogManager.getLogger(SpireHeartPatch.class.getName());
        private static final EventStrings eventStrings;
        public static final String[] OPTIONS;

        @SpireInsertPatch(
//                locator= SpireHeartPatch.Locator.class
            rloc = 5
        )

        public static void Insert(SpireHeart __instance) {
//          使用注射器换取钥匙
            logger.info("=========================  heart hook =========================");
            int var1 = 0;
            int var2 = 0;
            int var3 = 0;
            int var4 = AbstractDungeon.player.getRelic(injector.ID).counter;
            if(!Settings.hasRubyKey){
                var1=1;
            }
            if(!Settings.hasEmeraldKey){
                var2=1;
            }
            if(!Settings.hasSapphireKey){
                var3=1;
            }
            int var5 = var4 - (var1+var2+var3);
            logger.info("var1:"+var1+"var2:"+var2+"var3:"+var3+"var4:"+var4+"var5:"+var5);
            if(var5>=0){
                AbstractDungeon.player.getRelic(injector.ID).setCounter(var5);
                String option = OPTIONS[0]+(var1+var2+var3)+OPTIONS[1];
                logger.info(option);
                if(var1==1){
                    option+=OPTIONS[2];
                    AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.RED));
                }
                if(var2==1){
                    option+=OPTIONS[3];
                    AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.GREEN));
                }
                if(var3==1){
                    option+=OPTIONS[4];
                    AbstractDungeon.topLevelEffects.add(new ObtainKeyEffect(ObtainKeyEffect.KeyColor.BLUE));
                }
                logger.info(option);
                __instance.roomEventText.updateDialogOption(0,option);
            }
        }



//        private static class Locator extends SpireInsertLocator {
//            // This is the abstract method from SpireInsertLocator that will be used to find the line
//            // numbers you want this patch inserted at
//            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
//                // finalMatcher is the line that we want to insert our patch before -
//                // in this example we are using a `MethodCallMatcher` which is a type
//                // of matcher that matches a method call based on the type of the calling
//                // object and the name of the method being called. Here you can see that
////                Matcher preMatcher = new Matcher.MethodCallMatcher(ProceedButton.class, "goToDemoVictoryRoom");
//                Matcher finalMatcher = new Matcher.MethodCallMatcher(RoomEventDialog.class, "updateDialogOption");
////                Matcher finalMatcher = new Matcher.InstanceOfMatcher(MonsterRoomBoss.class);
//                // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
//                // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
//                // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
//                // that matches the finalMatcher.
//                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
//            }
//
//
//        }

    static {
        eventStrings = CardCrawlGame.languagePack.getEventString("aberration:SpireHeartPatch");
        OPTIONS = eventStrings.OPTIONS;
    }

}
