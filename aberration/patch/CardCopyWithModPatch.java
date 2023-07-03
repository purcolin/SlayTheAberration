package aberration.patch;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.cards.AbstractCard;

@SpirePatch2(
        clz= AbstractCard.class,
        method="makeStatEquivalentCopy"
)
public class CardCopyWithModPatch {
    public CardCopyWithModPatch(){

    }
    @SpireInsertPatch(
            rloc = 4,
            localvars = {"card"}
    )
    public static void Insert(AbstractCard __instance,AbstractCard card) {
        CardModifierManager.copyModifiers(__instance,card,true,false,false);
    }


}