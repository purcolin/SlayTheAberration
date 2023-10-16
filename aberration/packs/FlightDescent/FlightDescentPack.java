package aberration.packs.FlightDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractAberrationPack;
import aberration.utils.TextureLoader;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.GetPowerClass;
import static aberration.AberrationMod.makePowerPath;

public class FlightDescentPack extends AbstractAberrationPack{
    public static final Logger logger = LogManager.getLogger(FlightDescentPack.class.getName());
    public static String ID = AberrationMod.makeID(FlightDescentPack.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final int[] available = new int[] {1,2};
    public FlightDescentPack() {
        this.packID = ID;
        this.name = TEXT[0];
        this.description = TEXT[1];
        this.author = TEXT[2];
        for(int i:available){
            this.validDungeons.add(i);
        }
        this.card = new FlightCard();
        this.gene = new FlightDescentGene();
        this.type = AberrationPackType.BA_GUA;
        this.bossImage = TextureLoader.getTexture(makePowerPath(this.getClass().getSimpleName().replace("Pack","Boss.png")));
        this.MonsterPower = GetPowerClass(AberrationMod.makeID(this.getClass().getSimpleName().replace("Pack","MonsterPower")));
        this.BossPower = GetPowerClass(AberrationMod.makeID(this.getClass().getSimpleName().replace("Pack","BossPower")));
    }
    public void ApplyMonsterPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new FlightDescentMonsterPower(m, m)));
    }

    public void ApplyBossPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new FlightDescentBossPower(m, m)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new FlightDescentMonsterPower(m, m)));
    }
    static {
//        logger.info(ID);
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        logger.info(uiStrings);
        TEXT = uiStrings.TEXT;
    }
}
