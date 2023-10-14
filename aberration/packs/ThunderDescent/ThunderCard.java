package aberration.packs.ThunderDescent;


import aberration.AberrationMod;
import aberration.packs.AbstractInjectedCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.Zap;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.orbs.Lightning;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ThunderCard extends AbstractInjectedCard {
    public static final String ID = AberrationMod.makeID(ThunderCard.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(ThunderCard.class.getName());
    private static final UIStrings cardStrings;
    public static final String[] TEXT;
    public ThunderCard(){

    }

    @Override
    public String identifier(AbstractCard card){
        return ID;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
        ++card.cost;
        card.costForTurn = card.cost;
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
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ChannelAction((AbstractOrb)new Lightning()));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ChannelAction((AbstractOrb)new Lightning()));
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
        return new ThunderCard();
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = cardStrings.TEXT;
    }
}
