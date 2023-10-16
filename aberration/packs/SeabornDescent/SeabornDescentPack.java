package aberration.packs.SeabornDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractAberrationPack;
import aberration.utils.TextureLoader;
import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rewards.RewardItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.GetPowerClass;
import static aberration.AberrationMod.makePowerPath;

public class SeabornDescentPack extends AbstractAberrationPack{
    public static final Logger logger = LogManager.getLogger(SeabornDescentPack.class.getName());
    public static String ID = AberrationMod.makeID(SeabornDescentPack.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static final int[] avaliable = new int[] {0,1,2};
    public SeabornDescentPack() {
        this.packID = ID;
        this.name = TEXT[0];
        this.description = TEXT[1];
        this.author = TEXT[2];
        for(int i:avaliable){
            this.validDungeons.add(i);
        }
        this.card = new SeabornCard();
        this.gene = new SeabornDescentGene();
        this.type = AberrationPackType.BA_GUA;
        this.bossImage = TextureLoader.getTexture(makePowerPath(this.getClass().getSimpleName().replace("Pack","Boss.png")));
        this.MonsterPower = GetPowerClass(AberrationMod.makeID(this.getClass().getSimpleName().replace("Pack","MonsterPower")));
        this.BossPower = GetPowerClass(AberrationMod.makeID(this.getClass().getSimpleName().replace("Pack","BossPower")));
    }
    public void ApplyMonsterPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new SeabornDescentMonsterPower(m, m)));
    }

    public void ApplyBossPower(AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new SeabornDescentBossPower(m, m)));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new SeabornDescentMonsterPower(m, m)));
    }
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        logger.info(uiStrings);
        TEXT = uiStrings.TEXT;
    }

}
