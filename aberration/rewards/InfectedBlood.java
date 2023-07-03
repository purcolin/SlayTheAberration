package aberration.rewards;


import aberration.patch.RewardsPatch;
import aberration.relics.injector;
import aberration.utils.TextureLoader;
import basemod.abstracts.CustomReward;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class InfectedBlood extends CustomReward {
    private static final Texture ICON = TextureLoader.getTexture("aberrationResources/images/injector64.png");

    public int amount;

    public InfectedBlood(int amount) {
        super(ICON, "注入至一张卡牌(消耗1个注射器)", RewardsPatch.ABERRATION_INFECTEDBLOOD);
        this.amount = amount;
    }

    public InfectedBlood() {
        super(ICON, "你当前没有注射器", RewardsPatch.ABERRATION_INFECTEDBLOOD);
        this.amount = 1;
    }

    @Override
    public boolean claimReward() {
        if(AbstractDungeon.player.getRelic(injector.ID).checkTrigger()){
            AbstractDungeon.player.getRelic(injector.ID).onTrigger();
        }

        return false;
    }

}