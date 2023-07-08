package aberration.relics;

import aberration.AberrationMod;
import aberration.packs.AbstractAberrationPack;
import aberration.rewards.AberrationSource;
import aberration.utils.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import basemod.helpers.CardModifierManager;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;
import java.util.stream.Collectors;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

//public class injector extends CustomRelic implements CustomSavable<Integer> {
public class injector extends CustomRelic{
    public static final String ID = "aberration:Injector";
    private static final int cost = 1;
    private boolean cardSelected = true; // A boolean to indicate whether or not we selected a card for injecting.

//    private int used = 0;

    private int bloodFrom = 0;

    public boolean isTrigged = false;
    public static final Logger logger = LogManager.getLogger(injector.class.getName());


    public injector() {
        super(ID, TextureLoader.getTexture("aberrationResources/images/injector.png"), // you could create the texture in this class if you wanted too
                RelicTier.STARTER, LandingSound.MAGICAL); // this relic is uncommon and sounds magic when you click it
        this.setCounter(2);
        this.description = getUpdatedDescription();
    }
    @Override
    public String getUpdatedDescription() {
        String var2 = DESCRIPTIONS[0];
        for(AbstractAberrationPack p :AberrationMod.CurrentAberrationPacks){
            logger.info(p.name);
            var2+=(p.name+" NL ");
        }
        return var2; // DESCRIPTIONS pulls from your localization file
    }

    @Override
    public void onEquip() {
        this.isTrigged = false;
        this.description = getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    @Override
    public void onTrigger() {
        cardSelected = false;
        if (AbstractDungeon.isScreenUp) { // 3. If the map is open - hide it.
            AbstractDungeon.dynamicBanner.hide();
            AbstractDungeon.overlayMenu.cancelButton.hide();
            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        }
        CardGroup group = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();
        while(var1.hasNext()) {
            AbstractCard c = (AbstractCard)var1.next();
            if (AberrationMod.CurrentAberrationPacks.get(bloodFrom).card.canApplyTo(c) && !AberrationMod.CurrentAberrationPacks.get(bloodFrom).card.hasThisMod(c)){
                group.addToTop(c);
            }
        }
        AbstractDungeon.gridSelectScreen.open(group, 1, "注入至一张卡牌", true, false, true, false);
    }

    @Override
    public boolean checkTrigger(){
        bloodFrom = AberrationMod.currentDungeon();
        if(this.counter>0){
            isTrigged = true;
            return true;
        }else {
            return false;
        }
    }
    @Override
    public void update() {
        super.update();

        if (!cardSelected && !AbstractDungeon.gridSelectScreen.selectedCards.isEmpty() && isTrigged) {
            cardSelected = true;
            AbstractCard card = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            CardModifierManager.addModifier(card, AberrationMod.CurrentAberrationPacks.get(bloodFrom).card);
            this.setCounter(this.counter - this.cost);
            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(card));
            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.description = getUpdatedDescription();
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.initializeTips();
            cardSelected = false;
            isTrigged = false;
            AbstractDungeon.combatRewardScreen.rewards.remove(0);
            AbstractDungeon.combatRewardScreen.positionRewards();
            if (AbstractDungeon.combatRewardScreen.rewards.isEmpty()) {
                AbstractDungeon.combatRewardScreen.hasTakenAll = true;
                AbstractDungeon.overlayMenu.proceedButton.show();
            }
        }
    }

    @Override
    public void onVictory(){
        if(AberrationMod.CurrentRoomType == AbstractRoom.RoomType.BOSS){
            AbstractPlayer p = AbstractDungeon.player;
            this.addToTop(new RelicAboveCreatureAction(p, this));
            this.setCounter(this.counter + 1);
            this.description = getUpdatedDescription();
            this.tips.clear();
            this.tips.add(new PowerTip(this.name, this.description));
            this.initializeTips();
        }

    }
    @Override
    public injector makeCopy() { // always override this method to return a new instance of your relic
        return new injector();
    }
    @Override
    public void onMonsterDeath(AbstractMonster m) {
    }

//    @Override
//    public Integer onSave() {
//        return this.counter;
//    }
//
//    @Override
//    public void onLoad(Integer integer) {
//        this.counter = integer;
//    }
}
