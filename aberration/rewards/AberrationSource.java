package aberration.rewards;

import aberration.AberrationMod;
import aberration.patch.RewardsPatch;
import aberration.utils.TextureLoader;
import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class AberrationSource extends CustomReward {
    private static final Texture ICON = TextureLoader.getTexture("aberrationResources/images/reward.png");

    public AberrationSource() {
        super(ICON, "接受畸变基因", RewardsPatch.ABERRATION_AberrationGene);
    }

    @Override
    public boolean claimReward() {
        AberrationMod.CurrentAberrationPacks.get(AberrationMod.currentDungeon()).ApplyGene(AbstractDungeon.player);
        return true;
    }
}