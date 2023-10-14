package aberration.packs.SeabornDescent;

import aberration.AberrationMod;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;

public class SeabornDescentBossPower extends AbstractPower {

    public static final String POWER_ID = AberrationMod.makeID(SeabornDescentBossPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(SeabornDescentBossPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    private AbstractMonster monster;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(SeabornDescentBossPower.class.getSimpleName()+".png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(SeabornDescentBossPower.class.getSimpleName()+"32.png"));
    public SeabornDescentBossPower(AbstractCreature owner, AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.monster = (AbstractMonster) owner;
        this.source = source;
        this.priority = 100;
        this.amount = -1;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        if (this.owner != null && !this.owner.isPlayer) {
            this.description = DESCRIPTIONS[0];
        }
    }
    @Override
    public void atStartOfTurn() {
        if(
                this.monster.intent==AbstractMonster.Intent.ATTACK ||
                this.monster.intent==AbstractMonster.Intent.ATTACK_BUFF ||
                this.monster.intent==AbstractMonster.Intent.ATTACK_DEBUFF ||
                this.monster.intent==AbstractMonster.Intent.ATTACK_DEFEND
        ){
            this.addToBot(new ApplyElementPowerAction(AbstractDungeon.player,this.owner,new SeabornDescentSanityPower(AbstractDungeon.player,this.owner,1),1));
        }else {
            this.addToBot(new ApplyElementPowerAction(AbstractDungeon.player,this.owner,new SeabornDescentSanityPower(AbstractDungeon.player,this.owner,2),2));
        }

    }


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}