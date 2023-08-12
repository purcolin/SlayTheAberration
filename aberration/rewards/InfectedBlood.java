package aberration.rewards;


import aberration.AberrationMod;
import aberration.patch.RewardsPatch;
import aberration.relics.injector;
import aberration.utils.TextureLoader;
import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class InfectedBlood extends CustomReward {
    private static final Texture ICON = TextureLoader.getTexture("aberrationResources/images/injector64.png");
    public static final String ID = AberrationMod.makeID(InfectedBlood.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public int amount;

    public InfectedBlood(int amount) {
        super(ICON, TEXT[0], RewardsPatch.ABERRATION_INFECTEDBLOOD);
        this.amount = amount;
    }

    public InfectedBlood() {
        super(ICON, TEXT[1], RewardsPatch.ABERRATION_INFECTEDBLOOD);
        this.amount = 1;
    }

    @Override
    public boolean claimReward() {
        if(AbstractDungeon.player.getRelic(injector.ID).checkTrigger()){
            AbstractDungeon.player.getRelic(injector.ID).onTrigger();
        }

        return false;
    }
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }

}