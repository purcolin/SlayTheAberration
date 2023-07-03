package aberration.packs.WormDescent;

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
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;

public class WormDescentWormLarvaPower extends AbstractPower {
    public static final String POWER_ID = AberrationMod.makeID(WormDescentWormLarvaPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(WormDescentWormLarvaPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(WormDescentWormLarvaPower.class.getSimpleName()+"32.png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(WormDescentWormLarvaPower.class.getSimpleName()+"32.png"));



    public WormDescentWormLarvaPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        if (this.amount >= 1) {
            this.amount = 1;
        }
        this.updateDescription();
        this.type = PowerType.DEBUFF;
        this.isTurnBased = true;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

    }

    public void updateDescription() {
        if (this.owner != null) {
            this.description = DESCRIPTIONS[0];
        }

    }

    @Override
    public void onDeath() {
        int count = 0;
        for (AbstractMonster mon : (AbstractDungeon.getMonsters()).monsters) {
            if (mon.isDead) {
               count++;
            }
        }
        for(int var1=0;var1<count;var1++){
            logger.info("死亡的怪物数："+count);
            addToBot((AbstractGameAction)new DamageAction((AbstractCreature)AbstractDungeon.player, new DamageInfo((AbstractCreature)AbstractDungeon.player, 1, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON));
        }
    }


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}