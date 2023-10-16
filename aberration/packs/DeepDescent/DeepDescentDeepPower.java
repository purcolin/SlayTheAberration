package aberration.packs.DeepDescent;

import aberration.AberrationMod;
import aberration.actions.RecoverMoveAction;
import aberration.actions.RemoveMoveAction;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;

public class DeepDescentDeepPower extends AbstractPower {
    public static final String POWER_ID = AberrationMod.makeID(DeepDescentDeepPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(DeepDescentDeepPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;

    public DeepDescentDeepPower() {
        this.name = NAME;
        this.ID = POWER_ID;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
    }

    private Boolean tried = false;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(DeepDescentDeepPower.class.getSimpleName()+".png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(DeepDescentDeepPower.class.getSimpleName()+"32.png"));



    public DeepDescentDeepPower(AbstractCreature owner, AbstractCreature source, int amount) {
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
        try {
            //  要尝试的代码块
            if (this.owner.isPlayer) {
                this.description = DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1];
            }else {
                this.description = DESCRIPTIONS[2]+this.amount+DESCRIPTIONS[3];
            }
        }
        catch(NullPointerException e) {
            //  处理错误的代码块
            this.description = "For Player:"+DESCRIPTIONS[0]+this.amount+DESCRIPTIONS[1] + " NL For Monster:" +DESCRIPTIONS[2]+this.amount+DESCRIPTIONS[3];
        }


    }


    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if(this.owner.isPlayer){
            if(this.amount>0){
                action.exhaustCard = true;
//            this.addToBot(new ExhaustSpecificCardAction(card,AbstractDung+++++++++++++++++++++++++++++++++++++++++++++++++++++++eon.player.hand));
            }
            this.addToBot(new ReducePowerAction(this.owner, this.source, POWER_ID, 1));
        }
    }

    @Override
    public void atStartOfTurn(){
    }

    @Override
    public void atEndOfTurn(boolean isPlayer){
        if(!isPlayer){
            logger.info("deep end!");
            logger.info(this.owner);
            logger.info(this.amount);
            if(this.amount == 1){
                logger.info(this.tried);
                if(this.tried){
                    this.addToTop(new ReducePowerAction(this.owner, this.source, POWER_ID, 1));
                    this.addToTop(new RecoverMoveAction((AbstractMonster) this.owner));
                    this.tried = false;
                }else {
                    this.addToBot(new RemoveMoveAction((AbstractMonster) this.owner));
                    this.tried = true;
                }
            }else {
                if (this.tried){

                }
                this.addToBot(new RemoveMoveAction((AbstractMonster) this.owner));
                this.addToBot(new ReducePowerAction(this.owner, this.source, POWER_ID, 1));
            }


        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }

}