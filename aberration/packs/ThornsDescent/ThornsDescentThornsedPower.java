package aberration.packs.ThornsDescent;

import aberration.AberrationMod;
import aberration.commands.AberrationFight;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;

public class ThornsDescentThornsedPower extends AbstractPower {
    public static final String POWER_ID = AberrationMod.makeID(ThornsDescentThornsedPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(ThornsDescentThornsedPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(ThornsDescentThornsedPower.class.getSimpleName()+"32.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(ThornsDescentThornsedPower.class.getSimpleName()+"32.png"));



    public ThornsDescentThornsedPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        if (this.amount >= 1) {
            this.amount = 1;
        }
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

    }

    public void updateDescription() {
        if (this.owner != null) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        }

    }
    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.NORMAL && damageAmount > 0) {
            AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new ThornsPower(AbstractDungeon.player,damageAmount/3)));
        }

    }
//    public float atDamageFinalReceive(float damage, DamageInfo.DamageType type) {
//        if (type != DamageInfo.DamageType.NORMAL) {
//            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new ThornsPower(AbstractDungeon.player,(int)damage/3)));
//            return damage;
//        } else {
//            return damage;
//        }
//    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
