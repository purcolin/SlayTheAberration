package aberration.packs.FlightDescent;


import aberration.AberrationMod;
import aberration.packs.AbstractInjectedCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;


public class FlightCard extends AbstractInjectedCard {
    public static final String ID = AberrationMod.makeID(FlightCard.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(FlightCard.class.getName());
    private static final UIStrings cardStrings;
    public static final String[] TEXT;
    public FlightCard(){

    }

    @Override
    public String identifier(AbstractCard card){
        return ID;
    }

    public void onInitialApplication(AbstractCard card) {
        card.exhaust = true;
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
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(target,new DamageInfo(target,1)));
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new FlightCard();
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = cardStrings.TEXT;
    }
}
