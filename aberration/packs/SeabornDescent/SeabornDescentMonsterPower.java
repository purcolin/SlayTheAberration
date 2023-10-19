package aberration.packs.SeabornDescent;

import aberration.AberrationMod;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerIconShowEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

import static aberration.AberrationMod.makePowerPath;

public class SeabornDescentMonsterPower extends AbstractPower {

    public static final String POWER_ID = AberrationMod.makeID(SeabornDescentMonsterPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(SeabornDescentMonsterPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    public int baseThreshold = 3;
    public int count = 0;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(SeabornDescentMonsterPower.class.getSimpleName()+".png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(SeabornDescentMonsterPower.class.getSimpleName()+"32.png"));

    public SeabornDescentMonsterPower() {
        this.name = NAME;
        this.ID = POWER_ID;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
    }

    public SeabornDescentMonsterPower(AbstractCreature owner, AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = -1;
        if(this.amount > -1){
            this.amount = -1;
        }
        this.priority = 100;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.canGoNegative = true;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        if (this.owner != null && !this.owner.isPlayer) {
            if(AbstractDungeon.player.hasPower(SeabornDescentSanityPower.POWER_ID)){
                this.description = DESCRIPTIONS[0]+DESCRIPTIONS[2]+(3-this.baseThreshold)+DESCRIPTIONS[3];
            }else {
                this.description = DESCRIPTIONS[0]+DESCRIPTIONS[1];
            }
        }else {
            this.description = DESCRIPTIONS[0];
        }

    }
    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        if(info.owner == AbstractDungeon.player){
            if(info.owner.hasPower(SeabornDescentSanityPower.POWER_ID)){
//            this.amount++;
                this.stackPower(1);
                if(this.amount>=3){
                    damageAmount = 0;
                    this.reducePower(3);
                }
            }
        }

        return damageAmount;
    }
    @Override
    public void atStartOfTurn() {
        this.addToBot(new ApplyElementPowerAction(AbstractDungeon.player,this.owner,new SeabornDescentSanityPower(AbstractDungeon.player,this.owner,1),1));
    }


    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
