package aberration.packs.SeabornDescent;


import aberration.AberrationMod;
import aberration.packs.AbstractInjectedCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class SeabornCard extends AbstractInjectedCard {
    public static final String ID = AberrationMod.makeID(SeabornCard.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(SeabornCard.class.getName());
    private static final UIStrings cardStrings;
    public static final String[] TEXT;
    public SeabornCard(){

    }

    @Override
    public String identifier(AbstractCard card){
        return ID;
    }

    public String modifyDescription(String rawDescription, AbstractCard card) {
        return rawDescription + TEXT[2];
    }

    @Override
    public boolean isInherent(AbstractCard card) {
            return true;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action){
        AbstractDungeon.actionManager.addToTop(new ApplyElementPowerAction(target,AbstractDungeon.player,new SeabornDescentSanityPower(target,AbstractDungeon.player,card.costForTurn+1),card.costForTurn+1));
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
        return card.type != AbstractCard.CardType.CURSE;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new SeabornCard();
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = cardStrings.TEXT;
    }
}
