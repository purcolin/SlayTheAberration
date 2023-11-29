package aberration.packs.EarthDescent;

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

import static aberration.AberrationMod.GetPowerClass;
import static aberration.AberrationMod.makePowerPath;

public class EarthDescentPack extends AbstractAberrationPack{
    public static final Logger logger = LogManager.getLogger(EarthDescentPack.class.getName());
    public static String ID = AberrationMod.makeID(EarthDescentPack.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final int[] avaliable = new int[] {0,1,2};
    public EarthDescentPack() {
        this.packID = ID;
        this.name = TEXT[0];
        this.description = TEXT[1];
        this.author = TEXT[2];
        for(int i:avaliable){
            this.validDungeons.add(i);
        }
        this.card = new EarthCard();
        this.gene = new EarthDescentGene();
        this.type = AberrationPackType.SEASONS;
        this.bossImage = TextureLoader.getTexture(makePowerPath(this.getClass().getSimpleName().replace("Pack","Boss.png")));
        this.MonsterPower = GetPowerClass(AberrationMod.makeID(this.getClass().getSimpleName().replace("Pack","MonsterPower")));
        this.BossPower = GetPowerClass(AberrationMod.makeID(this.getClass().getSimpleName().replace("Pack","BossPower")));
    }
    public void ApplyMonsterPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new EarthDescentMonsterPower(m, m)));
    }

    public void ApplyBossPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new EarthDescentBossPower(m, m)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new EarthDescentMonsterPower(m, m)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,m,new EarthDescentArmourPiercePower(AbstractDungeon.player,m)));
    }
    static {
//        logger.info(ID);
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        logger.info(uiStrings);
        TEXT = uiStrings.TEXT;
    }
}
