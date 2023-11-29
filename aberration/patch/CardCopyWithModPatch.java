package aberration.patch;

import aberration.packs.ThunderDescent.ThunderCard;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.rooms.RestRoom;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@SpirePatch2(
        clz= AbstractCard.class,
        method="makeStatEquivalentCopy"
)
public class CardCopyWithModPatch {
    private static final Logger logger = LogManager.getLogger(CardCopyWithModPatch.class.getName());
    public CardCopyWithModPatch(){

    }
    @SpireInsertPatch(
//            locator= CardCopyWithModPatch.Locator.class,
            rloc = 4,
            localvars = {"card"}
    )
    public static void Insert(AbstractCard __instance,AbstractCard card) {
        CardModifierManager.copyModifiers(__instance,card,true,false,false);
        logger.info("_______________card copyooo_____________");
        logger.info(AbstractDungeon.getCurrRoom());
        logger.info(AbstractDungeon.getCurrRoom().phase);
//        modifiers(__instance).forEach((mod) -> {
//            if(mod instanceof AbstractInjectedCard){
//                logger.info("复制修改："+((AbstractInjectedCard) mod).getPrefix());
//                modifiers(card).add(mod);
//            }
//        });
    }

    private static class Locator extends SpireInsertLocator {
        // This is the abstract method from SpireInsertLocator that will be used to find the line
        // numbers you want this patch inserted at
        public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
            // finalMatcher is the line that we want to insert our patch before -
            // in this example we are using a `MethodCallMatcher` which is a type
            // of matcher that matches a method call based on the type of the calling
            // object and the name of the method being called. Here you can see that
                Matcher finalMatcher = new Matcher.FieldAccessMatcher(AbstractCard.class, "freeToPlayOnce");
            // the `new ArrayList<Matcher>()` specifies the prerequisites before the line can be matched -
            // the LineFinder will search for all the prerequisites in order before it will match the finalMatcher -
            // since we didn't specify any prerequisites here, the LineFinder will simply find the first expression
            // that matches the finalMatcher.
            return LineFinder.findInOrder(ctMethodToPatch, finalMatcher);
        }

    }


}