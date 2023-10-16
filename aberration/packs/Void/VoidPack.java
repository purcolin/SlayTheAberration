package aberration.packs.Void;

import aberration.AberrationMod;
import aberration.packs.AbstractAberrationPack;
import aberration.packs.WormDescent.WormCard;
import aberration.packs.WormDescent.WormDescentBossPower;
import aberration.packs.WormDescent.WormDescentGene;
import aberration.packs.WormDescent.WormDescentMonsterPower;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.GetPowerClass;

public class VoidPack extends AbstractAberrationPack{
    public static final Logger logger = LogManager.getLogger(VoidPack.class.getName());
    public static String ID = AberrationMod.makeID(VoidPack.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final int[] avaliable = new int[] {1,2};
    public VoidPack() {
        this.packID = ID;
        this.name = TEXT[0];
        this.description = TEXT[1];
        this.author = TEXT[2];
        this.Void = true;
        for(int i:avaliable){
            this.validDungeons.add(i);
        }
        this.type = AberrationPackType.VOID;
        this.BossPower = GetPowerClass(AberrationMod.makeID(this.getClass().getSimpleName().replace("Pack","BossPower")));
    }

    public void ApplyBossPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(m, m, new VoidBossPower(m, m)));
    }
    static {
//        logger.info(ID);
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        logger.info(uiStrings);
        TEXT = uiStrings.TEXT;
    }
}
