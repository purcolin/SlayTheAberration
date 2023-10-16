package aberration.packs.ColdDescent;

import aberration.AberrationMod;
import aberration.packs.DeepDescent.DeepDescentBossPower;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static aberration.AberrationMod.makePowerPath;
import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class ColdDescentBossPower extends AbstractPower {

    public static final String POWER_ID = AberrationMod.makeID(ColdDescentBossPower.class.getSimpleName());
    private static final Logger logger = LogManager.getLogger(ColdDescentBossPower.class.getName());
    private static final PowerStrings powerStrings;
    public static final String NAME;
    public static final String[] DESCRIPTIONS;
    private AbstractCreature source;
    private static final Texture tex84 = TextureLoader.getTexture(makePowerPath(ColdDescentBossPower.class.getSimpleName()+".png"));
    private static final Texture tex32 = TextureLoader.getTexture(makePowerPath(ColdDescentBossPower.class.getSimpleName()+"32.png"));
    public ColdDescentBossPower(){
        this.name = NAME;
        this.ID = POWER_ID;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
    }
    public ColdDescentBossPower(AbstractCreature owner, AbstractCreature source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.source = source;
        this.priority = 100;
        this.amount = -1;
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);
        this.updateDescription();
        this.type = AbstractPower.PowerType.BUFF;
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        this.addToBot(new ApplyPowerAction(info.owner, this.owner, new ColdDescentColdPower(info.owner,this.owner, 2), 2));
        return damageAmount;

    }

    static {
        powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
        NAME = powerStrings.NAME;
        DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    }
}