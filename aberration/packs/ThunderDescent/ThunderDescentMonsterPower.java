package aberration.packs.ThunderDescent;

import aberration.AberrationMod;
import aberration.patch.AbstractMonsterHalfedPatch;
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
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.MummifiedHand;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerIconShowEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

import static aberration.AberrationMod.getRandomPackFromAll;
import static aberration.AberrationMod.makePowerPath;

public class ThunderDescentMonsterPower extends AbstractPower {

    public static final String POWER_ID = AberrationMod.makeID(ThunderDescentMonsterPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(ThunderDescentMonsterPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    private boolean trigged = false;
    private int paralysis = 0;
    public int baseThreshold = 6;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(ThunderDescentMonsterPower.class.getSimpleName()+".png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(ThunderDescentMonsterPower.class.getSimpleName()+"32.png"));

    public ThunderDescentMonsterPower() {
        this.name = NAME;
        this.ID = POWER_ID;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
    }

    public ThunderDescentMonsterPower(AbstractCreature owner, AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = 0;
        this.priority = 100;
        this.updateDescription();
        this.type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
    }

    public void updateDescription() {
        if (this.owner != null && !this.owner.isPlayer) {
            if(AbstractDungeon.player.hasPower(ThunderDescentParalysisPower.POWER_ID)){
                this.description = DESCRIPTIONS[0]+DESCRIPTIONS[2]+this.amount+DESCRIPTIONS[3];
            }else {
                this.description = DESCRIPTIONS[0]+DESCRIPTIONS[1];
            }
        }else {
            this.description = DESCRIPTIONS[0];
        }

    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if(damageAmount>0&&!this.trigged){
            this.addToBot(new ApplyPowerAction(target, this.owner, new ThunderDescentParalysisPower(target,this.owner, 1), 1));
            this.paralysis++;
            this.trigged = true;
            if(this.paralysis==this.baseThreshold){
                this.paralysis = this.baseThreshold-1;
            }
            if (this.amount == this.baseThreshold-this.paralysis) {
                this.AddCostToRandomCard();
            }
        }
    }

    public void atStartOfTurn() {
        this.trigged = false;
    }


    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if(AbstractDungeon.player.hasPower(ThunderDescentParalysisPower.POWER_ID)){
            ++this.amount;
            this.flashWithoutSound();
        }
        if (this.amount == this.baseThreshold-this.paralysis) {
            this.AddCostToRandomCard();
        }
        this.updateDescription();
    }

    private void AddCostToRandomCard(){
        this.amount = 0;
        this.playApplyPowerSfx();
        CardCrawlGame.sound.play("ORB_LIGHTNING_EVOKE", 0.1F);
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.GOLD, true));
        AbstractDungeon.topLevelEffectsQueue.add(new PowerIconShowEffect(this));
        Iterator var0 = AbstractDungeon.player.hand.group.iterator();
        ArrayList<AbstractCard> groupCopy = new ArrayList();
        while (true){
            while(var0.hasNext()) {
                AbstractCard c = (AbstractCard) var0.next();
                if (!c.freeToPlayOnce) {
                    groupCopy.add(c);
                } else {
                    logger.info("Not free to play once " + c.name);
                }
            }
            var0 = AbstractDungeon.actionManager.cardQueue.iterator();
            while(var0.hasNext()) {
                CardQueueItem i = (CardQueueItem)var0.next();
                if (i.card != null) {
                    logger.info("INVALID: " + i.card.name);
                    groupCopy.remove(i.card);
                }
            }
            AbstractCard c = null;
            if (groupCopy.isEmpty()) {
                logger.info("NO VALID CARDS");
            } else {
                logger.info("VALID CARDS: ");
                Iterator var1 = groupCopy.iterator();

                while(var1.hasNext()) {
                    AbstractCard cc = (AbstractCard)var1.next();
                    logger.info(cc.name);
                }

                c = (AbstractCard)groupCopy.get(AbstractDungeon.cardRandomRng.random(0, groupCopy.size() - 1));
            }
            if (c != null) {
                logger.info("Paralysis Card: " + c.name);
                c.modifyCostForCombat(1);
            } else {
                logger.info("ERROR: MUMMIFIED HAND NOT WORKING");
            }
            break;
        }
    }

    public void onInitialApplication() {
        logger.info("Check for one time;--------------------------");
        if(this.owner.hasPower(ThunderDescentBossPower.POWER_ID)){
            this.baseThreshold = 3;
        }else {
            this.baseThreshold = 6;
        }
    }
    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}
