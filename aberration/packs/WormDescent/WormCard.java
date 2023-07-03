package aberration.packs.WormDescent;


import aberration.AberrationMod;
import aberration.packs.AbstractInjectedCard;
import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.TooltipInfo;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class WormCard extends AbstractInjectedCard {
    public static final String ID = AberrationMod.makeID(WormCard.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(WormCard.class.getName());
    private static final UIStrings cardStrings;
    public static final String[] TEXT;
    public WormCard(){

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
        return !card.exhaust;
    }

    @Override
    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        for (AbstractMonster mon : (AbstractDungeon.getMonsters()).monsters) {
            if (!mon.isDeadOrEscaped()&&(mon.currentHealth<=mon.maxHealth/2||mon.currentHealth<=20)) {
                if(target instanceof AbstractMonster){
                    logger.info("Current Target:"+target.name);
                    card.use(AbstractDungeon.player, (AbstractMonster)target);
                }else {
                    card.use(AbstractDungeon.player, (AbstractMonster)target);
                }
            }
        }

    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new WormCard();
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = cardStrings.TEXT;
    }
}
