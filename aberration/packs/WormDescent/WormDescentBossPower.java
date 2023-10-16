package aberration.packs.WormDescent;

import aberration.AberrationMod;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;

public class WormDescentBossPower extends AbstractPower {

    public static final String POWER_ID = AberrationMod.makeID(WormDescentBossPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(WormDescentBossPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private static Float[] xList = {-800F,-600F,-400F,-200F,0F,200F,-800F,-600F,-400F,200F,-800F,-600F,-400F,200F,-600F,-400F,-200F,0F,200F};
    private static Float[] yList = {540F,540F,540F,540F,540F,540F,360F,360F,360F,360F,180F,180F,180F,180F,-180F,-180F,-180F,-180F,-180F};
    private int turn=0;
    private AbstractCreature source;
    private int count = 0;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(WormDescentBossPower.class.getSimpleName()+".png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(WormDescentBossPower.class.getSimpleName()+"32.png"));

    public WormDescentBossPower() {
        this.name = NAME;
        this.ID = POWER_ID;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
    }

    public WormDescentBossPower(AbstractCreature owner, AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.priority = 100;
        this.amount = -1;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
        this.type = PowerType.BUFF;

    }

    public void updateDescription() {
        if (this.owner != null && !this.owner.isPlayer) {
            this.description = DESCRIPTIONS[0]+this.owner.maxHealth/15+DESCRIPTIONS[1];
        }else {
            this.description = DESCRIPTIONS[0]+DESCRIPTIONS[1];
        }

    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        for(int var0=0;var0<damageAmount/(this.owner.maxHealth/15);var0++){
            this.SummonLarva();
        }
        return damageAmount;

    }

    public void atStartOfTurn(){
        if(this.turn%3==0){
            this.SummonLarva();
        }
        this.turn++;
    }

    private void SummonLarva(){
        if(this.count<19){
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SpawnMonsterAction(new WormLarva(xList[count], yList[count]), true));
        }else{
            AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new SpawnMonsterAction(new WormLarva(xList[10], yList[10]), true));
        }
        this.count++;
    }



    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}