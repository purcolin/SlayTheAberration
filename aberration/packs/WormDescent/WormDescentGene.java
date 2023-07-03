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
    private int WormCount;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public WormDescentGene() {
        super(ID,TEXT[0], TEXT[1]);
    }

    public static void initialize() {
        BaseMod.addSaveField(AberrationMod.makeID("aberrationGene"), new aberrationGene());
    }

    public String getDescription(){
        return TEXT[2]+this.WormCount+"个。";
    }
    @Override
    public void onEquip(AbstractRelic r){
        this.WormCount = 2;
    }

    @Override
    public void onMonsterDeath(AbstractMonster m,AbstractRelic r) {
        this.WormCount++;
    }

    @Override
    public void atBattleStart(AbstractRelic r) {
        addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, r));
        int healAmt = AbstractDungeon.player.maxHealth / 4;
        if(healAmt<1){
            healAmt = 1;
        }
        this.WormCount--;
        AbstractDungeon.player.currentHealth = 0;
        AbstractDungeon.player.heal(healAmt, true);
        r.updateDescription(AbstractDungeon.player.chosenClass);
    }


    @Override
    public int onAttacked(DamageInfo info, int damageAmount, AbstractRelic r) {
        if (AbstractDungeon.player.currentHealth<=damageAmount){
            AbstractDungeon.player.currentHealth = 0;
            addToTop((AbstractGameAction)new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, r));
            int healAmt = AbstractDungeon.player.maxHealth / 2;
            if(healAmt<1){
                healAmt = 1;
            }
            AbstractDungeon.player.heal(healAmt, true);
            this.WormCount--;
            return 0;
        }
        return damageAmount;
    }
    @Override
    public Integer onSave() {
        return this.WormCount;
    }

    @Override
    public void onLoad(Integer WormCount) {
        this.WormCount = WormCount;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}
