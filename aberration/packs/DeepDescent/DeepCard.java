package aberration.packs.DeepDescent;


import aberration.AberrationMod;
import aberration.actions.RemoveMoveAction;
import aberration.packs.AbstractInjectedCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import thePackmaster.actions.rippack.ExhaustRandomNonArtCardsAction;
import thePackmaster.actions.upgradespack.ExhaustRandomPredicateCardAction;

import java.util.List;

public class DeepCard extends AbstractInjectedCard {
    public static final String ID = AberrationMod.makeID(DeepCard.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(DeepCard.class.getName());
    private static final UIStrings cardStrings;
    public static final String[] TEXT;
    public DeepCard(){

    }

    @Override
    public String identifier(AbstractCard card){
        return ID;
    }

    public void onInitialApplication(AbstractCard card) {
        if(card.cost<2){
            card.cost=2;
        }else {
            ++card.cost;
        }
        card.costForTurn = card.cost;
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
        return !card.exhaust;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractMonster monster= AbstractDungeon.getRandomMonster();
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster,AbstractDungeon.player,new DeepDescentDeepPower(monster, AbstractDungeon.player,1)));
//        AbstractDungeon.actionManager.addToBottom(new ExhaustRandomNonArtCardsAction());
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new DeepCard();
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = cardStrings.TEXT;
    }
}
