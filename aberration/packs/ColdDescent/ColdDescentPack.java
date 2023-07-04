package aberration.packs.ColdDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractAberrationPack;
import aberration.utils.TextureLoader;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;

public class ColdDescentPack extends AbstractAberrationPack{
    public static final Logger logger = LogManager.getLogger(ColdDescentPack.class.getName());
    public static String ID = AberrationMod.makeID(ColdDescentPack.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final int[] avaliable = new int[] {0,1,2,3};
    public ColdDescentPack() {
        this.packID = ID;
        this.name = TEXT[0];
        this.description = TEXT[1];
        this.author = TEXT[2];
        for(int i:avaliable){
            this.validDungeons.add(i);
        }
        this.card = new ColdCard();
        this.gene = new ColdDescentGene();
        this.bossImage = TextureLoader.getTexture((makePowerPath("ColdDescentColdBoss.png")));
    }
    public void ApplyMonsterPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new ColdDescentMonsterPower(m, m, 1)));
    }

    public void ApplyBossPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new ColdDescentBossPower(m, m, 1)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new ColdDescentMonsterPower(m, m, 1)));
    }
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        logger.info(uiStrings);
        TEXT = uiStrings.TEXT;
    }
}
