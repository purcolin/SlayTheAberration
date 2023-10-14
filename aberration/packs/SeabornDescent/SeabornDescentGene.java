package aberration.packs.SeabornDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractGene;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SeabornDescentGene extends AbstractGene {

    public static final Logger logger = LogManager.getLogger(SeabornDescentGene.class.getName());
    public static final String ID = AberrationMod.makeID(SeabornDescentGene.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public SeabornDescentGene() {
        super(ID,TEXT[0], TEXT[1]);
    }

    public void loading(){
        logger.info("loading:thunder YES!");
    }


    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target, AbstractRelic r) {
        if(info.type== DamageInfo.DamageType.NORMAL){
            this.addToBot(new ApplyElementPowerAction(target,target,new SeabornDescentSanityPower(target,target,damageAmount/5),damageAmount/5));
        }

    }
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}
