package aberration.rewards;

import aberration.AberrationMod;
import aberration.packs.ColdDescent.ColdDescentGene;
import aberration.patch.RewardsPatch;
import aberration.utils.TextureLoader;
import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;

public class AberrationSource extends CustomReward {
    public static final String ID = AberrationMod.makeID(AberrationSource.class.getSimpleName());
    private static final Texture ICON = TextureLoader.getTexture("aberrationResources/images/reward.png");
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    public AberrationSource() {
        super(ICON, TEXT[0], RewardsPatch.ABERRATION_AberrationGene);
    }

    @Override
    public boolean claimReward() {
        AberrationMod.CurrentAberrationPacks.get(AberrationMod.currentDungeon()).ApplyGene(AbstractDungeon.player);
        return true;
    }
    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}