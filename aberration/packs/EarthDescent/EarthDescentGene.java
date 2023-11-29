package aberration.packs.EarthDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractGene;
import aberration.patch.AbstractMonsterHalfedPatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

public class EarthDescentGene extends AbstractGene{

    public static final String ID = AberrationMod.makeID(EarthDescentGene.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private int count = 0;
    public EarthDescentGene() {
        super(ID,TEXT[0], TEXT[1]);
    }
    @Override
    public void onSpawnMonster(AbstractMonster monster,AbstractRelic r) {
        AbstractMonsterHalfedPatch.Halfed.set(monster,false);
    }
    @Override
    public void atBattleStart(AbstractRelic r) {
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            AbstractMonsterHalfedPatch.Halfed.set(m,false);
        }
    }

    @Override
    public void onPlayerEndTurn(AbstractRelic r) {
        Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();

        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if(!AbstractMonsterHalfedPatch.Halfed.get(m)&&m.currentHealth<=m.maxHealth/2){
                AbstractMonsterHalfedPatch.Halfed.set(m,true);
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,1)));
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,1)));
                this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new FocusPower(AbstractDungeon.player,1)));
            }
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}
