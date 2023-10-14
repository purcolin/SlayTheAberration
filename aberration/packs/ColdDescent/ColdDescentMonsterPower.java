package aberration.packs.ColdDescent;

import aberration.AberrationMod;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;

public class ColdDescentMonsterPower extends AbstractPower {

    public static final String POWER_ID = AberrationMod.makeID(ColdDescentMonsterPower .class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(ColdDescentMonsterPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(ColdDescentColdPower.class.getSimpleName()+".png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(ColdDescentColdPower.class.getSimpleName()+"32.png"));
    public ColdDescentMonsterPower(AbstractCreature owner, AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = -1;
        this.priority = 100;
        logger.info(this.ID);
        this.updateDescription();
        this.type = AbstractPower.PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        if (this.owner != null && !this.owner.isPlayer) {
            this.description = DESCRIPTIONS[0];
        }

    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(damageAmount>0){
            this.addToBot(new ApplyPowerAction(target, this.owner, new ColdDescentColdPower(target,this.owner, damageAmount), damageAmount));
        }

//        logger.info("apply cold to you!");

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
