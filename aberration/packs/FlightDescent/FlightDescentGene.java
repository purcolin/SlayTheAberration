package aberration.packs.FlightDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractGene;
import aberration.patch.AbstractMonsterHalfedPatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.unique.PoisonLoseHpAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.Iterator;

public class FlightDescentGene extends AbstractGene{

    public static final String ID = AberrationMod.makeID(FlightDescentGene.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private static AbstractCreature target;
    private int BaseDamage  = 1;
    public FlightDescentGene() {
        super(ID,TEXT[0], TEXT[1]);
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target, AbstractRelic r) {
        this.target = target;
        int max = 0;
        for(AbstractPower power:info.owner.powers){
            if(power.ID == "Strength"||power.ID=="Dexterity"||power.ID=="Focus"){
                if(power.amount>max){
                    max = power.amount;
                };
            }
        }
        this.addToBot(new DamageAction(this.target,new DamageInfo(this.target,BaseDamage+max)));
    }

//    public int onAttackToChangeDamage(DamageInfo info, int damageAmount, AbstractRelic relic) {
//        logger.info("change"+damageAmount);
//        return damageAmount;
//    }




    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}
