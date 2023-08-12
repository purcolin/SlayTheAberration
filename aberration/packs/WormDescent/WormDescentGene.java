package aberration.packs.WormDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractGene;
import aberration.relics.aberrationGene;
import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class WormDescentGene extends AbstractGene implements CustomSavable<Integer> {

    public static final String ID = AberrationMod.makeID(WormDescentGene.class.getSimpleName());
    public int WormCount = 2;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public WormDescentGene() {
        super(ID,TEXT[0], TEXT[1]);
        BaseMod.addSaveField(AberrationMod.makeID("aberrationGene"), new aberrationGene());
    }

    public String getDescription(){
        return TEXT[2]+this.WormCount;
    }
    @Override
    public void onEquip(){
        this.WormCount = 2;
        logger.info("当前虫卵数:"+this.WormCount);
    }

    @Override
    public void onMonsterDeath(AbstractMonster m,AbstractRelic r) {
        this.WormCount++;
        logger.info("当前虫卵数:"+this.WormCount);
        r.updateDescription(AbstractDungeon.player.chosenClass);
    }

    @Override
    public void atBattleStart(AbstractRelic r) {
        logger.info("当前虫卵数:"+this.WormCount);
        addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, r));
        int healAmt = AbstractDungeon.player.maxHealth / 4;
        if(healAmt<1){
            healAmt = 1;
        }
        if(this.WormCount>0){
            this.WormCount--;
        }
        AbstractDungeon.player.currentHealth = 0;
        AbstractDungeon.player.heal(healAmt, true);
        r.updateDescription(AbstractDungeon.player.chosenClass);
    }


    @Override
    public int onAttacked(DamageInfo info, int damageAmount, AbstractRelic r) {
        if (AbstractDungeon.player.currentHealth<=damageAmount && this.WormCount>0){
            AbstractDungeon.player.currentHealth = 0;
            addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, r));
            int healAmt = AbstractDungeon.player.maxHealth / 4;
            if(healAmt<1){
                healAmt = 1;
            }
            AbstractDungeon.player.heal(healAmt, true);
            this.WormCount--;
            logger.info("当前虫卵数:"+this.WormCount);
            r.updateDescription(AbstractDungeon.player.chosenClass);
            return 0;
        }
        return damageAmount;
    }
    @Override
    public Integer onSave(){
        logger.info("当前虫卵数:"+this.WormCount);
        return this.WormCount;
    }

    @Override
    public void onLoad(Integer WormCount) {
        this.WormCount = WormCount;
        logger.info("当前虫卵数:"+this.WormCount);
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}
