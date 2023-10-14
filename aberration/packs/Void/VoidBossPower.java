package aberration.packs.Void;

import aberration.AberrationMod;
import aberration.packs.WormDescent.WormDescentWormLarvaPower;
import aberration.packs.WormDescent.WormLarva;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.BeatOfDeathPower;
import com.megacrit.cardcrawl.powers.InvinciblePower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;
import static aberration.AberrationMod.makeVoidPowerPath;

public class VoidBossPower extends AbstractPower {

    public static final String POWER_ID = AberrationMod.makeID(VoidBossPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(VoidBossPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private int count=0;
    private int turn=0;
    int debuffs = 0;
    private AbstractCreature source;
    private static final Texture tex84 = TextureLoader.getTexture(makeVoidPowerPath(VoidBossPower.class.getSimpleName()+".png"));
    private static final Texture tex32 = TextureLoader.getTexture(makeVoidPowerPath(VoidBossPower.class.getSimpleName()+"32.png"));
    public VoidBossPower(AbstractCreature owner, AbstractCreature source, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.amount = amount;
        this.priority = 100;
        if (this.amount >= 1) {
            this.amount = 1;
        }
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
        this.type = PowerType.BUFF;
    }

    public void updateDescription() {
        logger.info(this.count);
        if (this.owner != null && !this.owner.isPlayer) {
            String desc = DESCRIPTIONS[0];
            for(int var0=1;var0<DESCRIPTIONS.length;var0++){
                if(this.count>=var0-1 && var0<DESCRIPTIONS.length-1){
                    desc += " #y";
                }
                desc+= DESCRIPTIONS[var0];
            }
            logger.info(desc);
            this.description = desc;
        }

    }

    @Override
    public void onInitialApplication() {
        this.count = AberrationMod.geneList.size();
        if(this.count>=1){
            for(AbstractPower p:this.owner.powers){
                if(p.type==PowerType.DEBUFF){
                    debuffs+=1;
                }
            }
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner,this.owner, new StrengthPower(this.owner, debuffs), debuffs));
        }
        if(this.count>=1){
            if(this.owner.hasPower("Invincible"))
                AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(this.owner,this.owner,"Invincible"));
                int invincibleAmt = 250;
                if (AbstractDungeon.ascensionLevel >= 19) {
                    invincibleAmt -= 100;
                }
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner,this.owner, new InvinciblePower(this.owner, invincibleAmt), invincibleAmt));
        }
        this.updateDescription();
    }

    @Override
    public int onLoseHp(int damageAmount) {
        if(this.count>=0){
            return damageAmount*4/5;
        }
        return damageAmount;
    }


    public void atStartOfTurn(){
        int var0 = 0;
        if(this.count>=1){
            for(AbstractPower p:this.owner.powers){
                if(p.type==PowerType.DEBUFF){
                    var0+=1;
                }
            }
            if(var0>debuffs){
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner,this.owner, new StrengthPower(this.owner, debuffs), var0-debuffs));
                this.debuffs = var0;
            }
        }
        if(this.count>=3) {
            if (this.turn % 3 == 0) {
                AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.owner,this.owner, new BeatOfDeathPower(this.owner, 1), 1));
            }
            this.turn++;
        }
    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}