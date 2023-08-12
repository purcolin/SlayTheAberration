package aberration.packs.ThornsDescent;


import aberration.AberrationMod;
import aberration.packs.AbstractInjectedCard;
import aberration.packs.DeepDescent.DeepDescentDeepPower;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.TooltipInfo;
import com.evacipated.cardcrawl.mod.stslib.damagemods.DamageModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE;

public class ThornsCard extends AbstractInjectedCard {
    public static final String ID = AberrationMod.makeID(ThornsCard.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(ThornsCard.class.getName());
    private static final UIStrings cardStrings;
    public static final String[] TEXT;
    public ThornsCard(){

    }

    @Override
    public String identifier(AbstractCard card){
        return ID;
    }

    @Override
    public void onInitialApplication(AbstractCard card) {
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
    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new ThornsPower(AbstractDungeon.player,3)));

    }
    public boolean canApplyTo(AbstractCard card) {
        return card.type!=CURSE;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new ThornsCard();
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = cardStrings.TEXT;
    }
}
