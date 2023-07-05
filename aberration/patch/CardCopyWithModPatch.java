package aberration.patch;

import aberration.packs.AbstractInjectedCard;
import aberration.packs.ColdDescent.ColdCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static basemod.helpers.CardModifierManager.modifiers;

@SpirePatch2(
        clz= AbstractCard.class,
        method="makeStatEquivalentCopy"
)
public class CardCopyWithModPatch {
    private static final Logger logger = LogManager.getLogger(CardCopyWithModPatch.class.getName());
    public CardCopyWithModPatch(){

    }
    @SpireInsertPatch(
            rloc = 4,
            localvars = {"card"}
    )
    public static void Insert(AbstractCard __instance,AbstractCard card) {
//        CardModifierManager.copyModifiers(__instance,card,true,false,false);
        modifiers(__instance).forEach((mod) -> {
            if(mod instanceof AbstractInjectedCard){
                logger.info("复制修改："+((AbstractInjectedCard) mod).getPrefix());
                modifiers(card).add(mod);
            }
        });
    }



}