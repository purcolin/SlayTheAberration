package aberration.packs.ThornsDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractGene;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

public class ThornsDescentGene extends AbstractGene {

    public static final Logger logger = LogManager.getLogger(ThornsDescentGene.class.getName());
    public static final String ID = AberrationMod.makeID(ThornsDescentGene.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public ThornsDescentGene() {
        super(ID,TEXT[0], TEXT[1]);
    }

    public void loading(){
        logger.info("loading:Thorns......Done!");
    }


    @Override
    public void atBattleStart(AbstractRelic r) {
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster) var1.next();
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ApplyPowerAction(m,AbstractDungeon.player,new ThornsDescentThornsedPower(m,AbstractDungeon.player,1),1));
        }


    }
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}
