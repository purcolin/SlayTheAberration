package aberration.packs.CurseDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractGene;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.DarkstonePeriapt;
import com.megacrit.cardcrawl.relics.DuVuDoll;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class CurseDescentGene extends AbstractGene{

    public static final Logger logger = LogManager.getLogger(CurseDescentGene.class.getName());
    public static final String ID = AberrationMod.makeID(CurseDescentGene.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private int counter;
    public CurseDescentGene() {
        super(ID,TEXT[0], TEXT[1]);
    }

    public void loading(){
        logger.info("loading:Curse......Done!");
    }

    public void onObtainCard(AbstractCard card, AbstractRelic r) {
        if (card.color == AbstractCard.CardColor.CURSE) {
            AbstractDungeon.player.increaseMaxHp(6, true);
        }

    }

    public void onEquip() {
        this.counter = 0;
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.type == AbstractCard.CardType.CURSE) {
                ++this.counter;
                AbstractDungeon.player.increaseMaxHp(6, true);
            }
        }
    }

    public void atBattleStart(AbstractRelic r) {
        this.counter = 0;
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();
        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (c.type == AbstractCard.CardType.CURSE) {
                ++this.counter;
            }
        }
        logger.info("诅咒数量"+this.counter);
        if (this.counter > 0) {
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.counter), this.counter));
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }

}
