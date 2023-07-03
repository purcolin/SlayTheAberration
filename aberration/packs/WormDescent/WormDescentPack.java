package aberration.packs.WormDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractAberrationPack;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WormDescentPack extends AbstractAberrationPack{
    public static final Logger logger = LogManager.getLogger(WormDescentPack.class.getName());
    public static String ID = AberrationMod.makeID(WormDescentPack.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final int[] avaliable = new int[] {1,2};
    public WormDescentPack() {
        this.packID = ID;
        this.name = TEXT[0];
        this.description = TEXT[1];
        this.author = TEXT[2];
        for(int i:avaliable){
            this.validDungeons.add(i);
        }
        this.card = new WormCard();
        this.gene = new WormDescentGene();
    }
    public void ApplyMonsterPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new WormDescentMonsterPower(m, m, 1)));
    }

    public void ApplyBossPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new WormDescentBossPower(m, m, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new WormDescentMonsterPower(m, m, 1)));
    }
    static {
//        logger.info(ID);
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        logger.info(uiStrings);
        TEXT = uiStrings.TEXT;
    }
}
