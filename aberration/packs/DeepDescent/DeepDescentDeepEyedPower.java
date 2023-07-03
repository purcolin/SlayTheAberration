package aberration.packs.DeepDescent;


import aberration.AberrationMod;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

import static aberration.AberrationMod.makePowerPath;

public class DeepDescentDeepEyedPower extends AbstractPower {
    public static final String POWER_ID = AberrationMod.makeID(DeepDescentDeepEyedPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(DeepDescentDeepEyedPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(DeepDescentDeepEyedPower.class.getSimpleName()+".png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(DeepDescentDeepEyedPower.class.getSimpleName()+"32.png"));

    private static ArrayList<AbstractCard.CardType> used = new ArrayList<>();


    public DeepDescentDeepEyedPower(AbstractCreature owner, AbstractCreature source, int amount) {
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
        if (this.owner != null) {
            this.description = DESCRIPTIONS[0];
        }

    }


    public void onExhaust(AbstractCard card) {
        logger.info("消耗卡牌："+card.name);
        if(!used.contains(card.type)){
            logger.info("新的类别："+card.type);
            used.add(card.type);
            Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

            while(var1.hasNext()) {
                AbstractMonster m = (AbstractMonster)var1.next();
                if (!m.isDeadOrEscaped()) {
                    this.addToBot(new ApplyPowerAction(m,this.owner,new StrengthPower(this.owner,3),3));
                    this.addToBot(new TalkAction(m,DESCRIPTIONS[1], 0.0F, 2.0F));
                }
            }
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target){
        this.addToBot(new ReducePowerAction(this.owner, this.source, POWER_ID, 1));
    }
}