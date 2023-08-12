package aberration.packs.ThornsDescent;

import aberration.AberrationMod;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;

public class ThornsDescentMonsterPower extends AbstractPower {

    public static final String POWER_ID = AberrationMod.makeID(ThornsDescentMonsterPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(ThornsDescentMonsterPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(ThornsDescentThornsedPower.class.getSimpleName()+".png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(ThornsDescentThornsedPower.class.getSimpleName()+"32.png"));
    public ThornsDescentMonsterPower(AbstractCreature owner, AbstractCreature source, int coldAmt) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = coldAmt;
        this.priority = 100;
        if (this.amount >= 1) {
            this.amount = 1;
        }
        logger.info(this.ID);
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        if (this.owner != null && !this.owner.isPlayer) {
            this.description = DESCRIPTIONS[0];
        }

    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if(damageAmount>0){
            addToBot((AbstractGameAction)new DamageAction((AbstractCreature) AbstractDungeon.player, new DamageInfo((AbstractCreature)AbstractDungeon.player, damageAmount/3, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        }
        return damageAmount;
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
