package aberration.packs.ColdDescent;


import aberration.AberrationMod;
import aberration.packs.AbstractInjectedCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ColdCard extends AbstractInjectedCard {
    public static final String ID = AberrationMod.makeID(ColdCard.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(ColdCard.class.getName());
    private static final UIStrings cardStrings;
    public static final String[] TEXT;
    public  ColdCard(){

    }

    @Override
    public String identifier(AbstractCard card){
        return ID;
    }

    public void onInitialApplication(AbstractCard card) {
        DamageModifierManager.addModifier(card, new ColdCardDamage());
    }
    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + TEXT[2];
    }

    @Override
    public boolean isInherent(AbstractCard card) {
            return true;
    }

    public String getPrefix() {
        return TEXT[0];
    }

    public String getSuffix() {
        return TEXT[1];
    }
    public List<TooltipInfo> additionalTooltips(AbstractCard card) {
        return null;
    }
    public boolean canApplyTo(AbstractCard card) {
        return card.type == AbstractCard.CardType.ATTACK;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new ColdCard();
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = cardStrings.TEXT;
    }
}
