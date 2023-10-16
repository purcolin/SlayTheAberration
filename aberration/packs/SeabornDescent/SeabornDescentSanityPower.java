package aberration.packs.SeabornDescent;

import aberration.AberrationMod;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;

public class SeabornDescentSanityPower extends AbstractPower {
    public static final String POWER_ID = AberrationMod.makeID(SeabornDescentSanityPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(SeabornDescentSanityPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(SeabornDescentSanityPower.class.getSimpleName()+"32.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(SeabornDescentSanityPower.class.getSimpleName()+"32.png"));

    public static final Integer max = 5;

    public SeabornDescentSanityPower() {
        this.name = NAME;
        this.ID = POWER_ID;
    }

    public SeabornDescentSanityPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        if (this.amount >= 9999) {
            this.amount = 9999;
        }
        this.updateDescription();
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

    }

    public void updateDescription() {
        if (this.owner != null) {
            this.description = DESCRIPTIONS[0] + this.max + DESCRIPTIONS[1];
        }

    }

    public void onInitialApplication(){
        logger.info(this.amount);
        if(this.amount>=this.max){
            this.addToBot(new DamageAction(this.owner, new DamageInfo(source,20,SeabornDescentDamageTypePatch.ABERRATION_ELEMENT),20));
            this.addToBot(new ApplyPowerAction(this.owner,source,new WeakPower(this.owner,1,!source.isPlayer),1));
            this.addToBot(new ReducePowerAction(this.owner, this.source, POWER_ID, this.amount));
        }
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        logger.info(power.ID);
        logger.info(target.name);
        logger.info(source.name);
        logger.info(owner.name);
        logger.info(target == owner);
        if(power instanceof SeabornDescentSanityPower && target == this.owner){
            logger.info(this.amount+power.amount);

            if(this.amount+power.amount>=this.max){
                this.addToBot(new DamageAction(target, new DamageInfo(source,20,SeabornDescentDamageTypePatch.ABERRATION_ELEMENT),20));
                this.addToBot(new ApplyPowerAction(target,source,new WeakPower(target,1,!source.isPlayer),1));
                this.addToBot(new ReducePowerAction(this.owner, this.source, POWER_ID, this.amount+power.amount));
            }
        }

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }


}
