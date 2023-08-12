package aberration.patch;

import aberration.rewards.AberrationSource;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.CursedRun;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.GameSavedEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

@SpirePatch2(
        clz= AbstractRoom.class,
        method="update"
)
public class AbstractRoomPatch {
        private static final Logger logger = LogManager.getLogger(AbstractRoomPatch.class.getName());

        @SpireInsertPatch(
                locator= AbstractRoomPatch.Locator1.class
        )
        public static void Insert1(AbstractRoom __instance) {
//          为三层boss添加掉落
            if (__instance.isBattleOver && AbstractDungeon.actionManager.actions.isEmpty()) {

                if (AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss && CardCrawlGame.dungeon instanceof TheBeyond && (AbstractDungeon.ascensionLevel < 20 || (AbstractDungeon.ascensionLevel >= 20 && AbstractDungeon.bossList.size() < 2))){
                    if (!AbstractDungeon.loading_post_combat) {
                        __instance.rewards.clear();
                        __instance.rewards.add(new AberrationSource());
                    }
                    AbstractDungeon.combatRewardScreen.open();
                    AbstractDungeon.loading_post_combat = false;
                }
                }
        }

        private static class Locator1 extends SpireInsertLocator {
            // This is the abstract method from SpireInsertLocator that will be used to find the line
            // numbers you want this patch inserted at
            public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
                // finalMatcher is the line that we want to insert our patch before -
                // in this example we are using a `MethodCallMatcher` which is a type
                // of matcher that matches a method call based on the type of the calling
                // object and the name of the method being called. Here you can see that
//                Matcher preMatcher = new Matcher.MethodCallMatcher(ProceedButton.class, "goToDemoVictoryRoom");
                Matcher finalMatcher = new Matcher.MethodCallMatcher(MonsterGroup.class, "updateAnimations");
//                Matcher finalMatcher = new Matcher.InstanceOfMatcher(MonsterRoomBoss.class);
                // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
                // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
                // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
                // that matches the finalMatcher.
                return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
            }


        }

}
