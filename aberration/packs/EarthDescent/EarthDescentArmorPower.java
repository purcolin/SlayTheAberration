package aberration.packs.EarthDescent;

import aberration.AberrationMod;
import aberration.packs.ColdDescent.ColdDescentColdPower;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;

public class EarthDescentArmorPower extends AbstractPower {
    public static final String POWER_ID = AberrationMod.makeID(EarthDescentArmorPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(EarthDescentArmorPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    private Float percent;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(ColdDescentColdPower.class.getSimpleName()+".png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(ColdDescentColdPower.class.getSimpleName()+"32.png"));

    public EarthDescentArmorPower() {
        this.name = NAME;
        this.ID = POWER_ID;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
    }

    public EarthDescentArmorPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        if (this.amount >= 999) {
            this.amount = 999;
        }
        this.updateDescription();
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }



    public void updateDescription() {
        this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
    }


    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
        return damage;
    }


    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        logger.info(info.owner);

        return (int) Math.ceil(damageAmount*this.getPercent());
    }

    private float getPercent(int pierce){
        int real = this.amount -pierce;

        this.percent = 1.0F-(float) this.amount/(2.0F*(float) this.amount+10.0F);
        logger.info(this.amount);
        logger.info(this.percent);
        return (this.percent);
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

}