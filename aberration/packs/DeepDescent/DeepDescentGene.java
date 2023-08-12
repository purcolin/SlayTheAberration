package aberration.packs.DeepDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractGene;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

public class DeepDescentGene extends AbstractGene implements CustomSavable<Integer> {

    public static final String ID = AberrationMod.makeID(DeepDescentGene.class.getSimpleName());
    private int WormCount;
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public DeepDescentGene() {
        super(ID,TEXT[0], TEXT[1]);
    }

    @Override
    public void onMonsterDeath(AbstractMonster m,AbstractRelic r) {
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new StrengthPower(AbstractDungeon.player,1)));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,1)));
        this.addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new FocusPower(AbstractDungeon.player,1)));
    }



    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target, AbstractRelic r) {

    }
    @Override
    public Integer onSave() {
        return this.WormCount;
    }

    @Override
    public void onLoad(Integer integer) {
        this.WormCount = integer;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}
