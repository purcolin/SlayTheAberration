package aberration.packs.ColdDescent;

import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class ColdCardDamage extends AbstractDamageModifier {
    public ColdCardDamage() {
    }


    @Override
    public boolean isInherent(){
        return true;
    }

    public void onLastDamageTakenUpdate(DamageInfo info, int lastDamageTaken, int overkillAmount, AbstractCreature target) {
        if (lastDamageTaken > 0) {
            this.addToBot(new ApplyPowerAction(target, info.owner, new ColdDescentColdPower(target, info.owner, lastDamageTaken)));
        }

    }

    public AbstractDamageModifier makeCopy() {
        return new ColdCardDamage();
    }
}
