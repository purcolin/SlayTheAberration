package aberration.packs.CurseDescent;


import aberration.AberrationMod;
import aberration.packs.AbstractInjectedCard;
import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE;

public class CurseCard extends AbstractInjectedCard {
    public static final String ID = AberrationMod.makeID(CurseCard.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(CurseCard.class.getName());
    private static final UIStrings cardStrings;
    public static final String[] TEXT;
    public CurseCard(){

    }

    @Override
    public void onCardModified(AbstractCard card) {
        card.cardsToPreview = new CurseDescentBloodthirsty();
        card.color = AbstractCard.CardColor.CURSE;
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
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect((AbstractCard)new CurseDescentBloodthirsty(), Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
    }
    public boolean canApplyTo(AbstractCard card) {
        return card.type!=CURSE;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new CurseCard();
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = cardStrings.TEXT;
    }
}
