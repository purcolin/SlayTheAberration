package aberration.packs.DeepDescent;

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

public class DeepDescentPack extends AbstractAberrationPack{
    public static final Logger logger = LogManager.getLogger(DeepDescentPack.class.getName());
    public static String ID = AberrationMod.makeID(DeepDescentPack.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final int[] avaliable = new int[] {0,1,2};
    public DeepDescentPack() {
        this.packID = ID;
        this.name = TEXT[0];
        this.description = TEXT[1];
        this.author = TEXT[2];
        for(int i:avaliable){
            this.validDungeons.add(i);
        }
        this.card = new DeepCard();
        this.gene = new DeepDescentGene();
        this.type = AberrationPackType.SEASONS;
        this.bossImage = TextureLoader.getTexture((makePowerPath("DeepDescentDeepBoss.png")));
    }
    public void ApplyMonsterPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new DeepDescentMonsterPower(m, m)));
    }

    public void ApplyBossPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new DeepDescentBossPower(m, m)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new DeepDescentMonsterPower(m, m)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,m,new DeepDescentDeepEyedPower(AbstractDungeon.player,m,1)));
    }
    static {
//        logger.info(ID);
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        logger.info(uiStrings);
        TEXT = uiStrings.TEXT;
    }
}
