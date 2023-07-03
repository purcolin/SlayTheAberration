package aberration.packs.ColdDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractGene;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ColdDescentGene extends AbstractGene {

    public static final Logger logger = LogManager.getLogger(ColdDescentGene.class.getName());
    public static final String ID = AberrationMod.makeID(ColdDescentGene.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public ColdDescentGene() {
        super(ID,TEXT[0], TEXT[1]);
    }

    public void loading(){
        logger.info("loading:Cold YES!");
    }


    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target, AbstractRelic r) {
        logger.info("adding a frost");
        logger.info(damageAmount);
        logger.info(target.name);
        for(int var1=0;var1<damageAmount/9;var1++){
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new ChannelAction((AbstractOrb)new Frost()));
        }


    }
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}
