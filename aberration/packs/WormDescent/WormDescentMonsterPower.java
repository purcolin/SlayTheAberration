package aberration.packs.WormDescent;

import aberration.AberrationMod;
import aberration.packs.DeepDescent.DeepDescentMonsterPower;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.AnimateShakeAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.actions.common.SuicideAction;
import com.megacrit.cardcrawl.actions.unique.CanLoseAction;
import com.megacrit.cardcrawl.actions.unique.CannotLoseAction;
import com.megacrit.cardcrawl.actions.utility.HideHealthBarAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;

public class WormDescentMonsterPower extends AbstractPower {

    public static final String POWER_ID = AberrationMod.makeID(WormDescentMonsterPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(WormDescentMonsterPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(WormDescentMonsterPower.class.getSimpleName()+".png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(WormDescentMonsterPower.class.getSimpleName()+"32.png"));
    public WormDescentMonsterPower(AbstractCreature owner, AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.priority = 100;
        this.amount = -1;
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

    @Override
    public void atStartOfTurn() {
        if (!this.owner.isDying && this.owner.currentHealth <= this.owner.maxHealth / 2.0F) {
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new TextAboveCreatureAction((AbstractCreature)this.owner, TextAboveCreatureAction.TextType.INTERRUPTED));
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new CannotLoseAction());
            for (AbstractMonster mon : (AbstractDungeon.getMonsters()).monsters) {
                if (!mon.isDeadOrEscaped()) {
                    logger.info(mon.name);
                    logger.info(mon.drawX);
                    logger.info(mon.drawY);
                    logger.info(mon.hb_x);
                    logger.info(mon.hb_y);
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new AnimateShakeAction((AbstractCreature)mon, 1.0F, 0.1F));
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new HideHealthBarAction((AbstractCreature)mon));
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SuicideAction(mon, false));
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new WaitAction(1.0F));
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SFXAction("SLIME_SPLIT"));
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SpawnMonsterAction(new WormLarva((mon.drawX-(float)Settings.WIDTH * 0.75F-100.0F)/Settings.xScale, (mon.drawY-AbstractDungeon.floorY+10.0F)/Settings.yScale, true, mon.currentHealth/2), false));
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SpawnMonsterAction(new WormLarva((mon.drawX-(float)Settings.WIDTH * 0.75F+100.0F)/Settings.xScale, (mon.drawY-AbstractDungeon.floorY-10.0F)/Settings.yScale, true, mon.currentHealth/2), false));
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new CanLoseAction());
                }
            }

        }
    }


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
