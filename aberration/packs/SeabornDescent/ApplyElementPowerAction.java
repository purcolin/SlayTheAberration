package aberration.packs.SeabornDescent;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.utility.TextAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerBuffEffect;
import com.megacrit.cardcrawl.vfx.combat.PowerDebuffEffect;

import java.util.Collections;
import java.util.Iterator;

import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.NoDrawPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ApplyElementPowerAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private AbstractPower powerToApply;
    private float startingDuration;

    public ApplyElementPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast, AbstractGameAction.AttackEffect effect) {
        if (Settings.FAST_MODE) {
            this.startingDuration = 0.1F;
        } else if (isFast) {
            this.startingDuration = Settings.ACTION_DUR_FASTER;
        } else {
            this.startingDuration = Settings.ACTION_DUR_FAST;
        }

        this.setValues(target, source, stackAmount);
        this.duration = this.startingDuration;
        this.powerToApply = powerToApply;
        if (AbstractDungeon.player.hasRelic("Snake Skull") && source != null && source.isPlayer && target != source && powerToApply.ID.equals("Poison")) {
            AbstractDungeon.player.getRelic("Snake Skull").flash();
            ++this.powerToApply.amount;
            ++this.amount;
        }

        this.actionType = ActionType.POWER;
        this.attackEffect = effect;
        if (AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.duration = 0.0F;
            this.startingDuration = 0.0F;
            this.isDone = true;
        }

    }

    public ApplyElementPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, boolean isFast) {
        this(target, source, powerToApply, stackAmount, isFast, AttackEffect.NONE);
    }

    public ApplyElementPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply) {
        this(target, source, powerToApply, powerToApply.amount);
    }

    public ApplyElementPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount) {
        this(target, source, powerToApply, stackAmount, false, AttackEffect.NONE);
    }

    public ApplyElementPowerAction(AbstractCreature target, AbstractCreature source, AbstractPower powerToApply, int stackAmount, AbstractGameAction.AttackEffect effect) {
        this(target, source, powerToApply, stackAmount, false, effect);
    }

    public void update() {
        if (this.target != null && !this.target.isDeadOrEscaped()) {
            if (this.duration == this.startingDuration) {
                if (this.powerToApply instanceof NoDrawPower && this.target.hasPower(this.powerToApply.ID)) {
                    this.isDone = true;
                    return;
                }

                if (this.source != null) {
                    Iterator var1 = this.source.powers.iterator();
                    if(this.powerToApply instanceof SeabornDescentSanityPower && this.target.hasPower(SeabornDescentSanityPower.POWER_ID)){
                        if(this.amount+target.getPower(SeabornDescentSanityPower.POWER_ID).amount>=SeabornDescentSanityPower.max){
                            this.addToBot(new DamageAction(this.target, new DamageInfo(this.source,20,SeabornDescentDamageTypePatch.ABERRATION_ELEMENT),20));
                            this.addToBot(new ApplyPowerAction(this.target,this.source,new WeakPower(this.target,1,!this.source.isPlayer),1));
                            this.addToBot(new ReducePowerAction(this.target, this.source, SeabornDescentSanityPower.POWER_ID, this.amount+this.target.getPower(SeabornDescentSanityPower.POWER_ID).amount));
                        }
                    }
                    while(var1.hasNext()) {
                        AbstractPower pow = (AbstractPower)var1.next();
                        pow.onApplyPower(this.powerToApply, this.target, this.source);
                    }
                }

                if (this.target instanceof AbstractMonster && this.target.isDeadOrEscaped()) {
                    this.duration = 0.0F;
                    this.isDone = true;
                    return;
                }

                if (this.target.hasPower("Artifact") && this.powerToApply.type == AbstractPower.PowerType.DEBUFF) {
                    this.addToTop(new TextAboveCreatureAction(this.target, TEXT[0]));
                    this.duration -= Gdx.graphics.getDeltaTime();
                    CardCrawlGame.sound.play("NULLIFY_SFX");
                    this.target.getPower("Artifact").flashWithoutSound();
                    this.target.getPower("Artifact").onSpecificTrigger();
                    return;
                }

                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
                boolean hasBuffAlready = false;
                Iterator var6 = this.target.powers.iterator();

                label148:
                while(true) {
                    AbstractPower p;
                    do {
                        do {
                            if (!var6.hasNext()) {
                                if (this.powerToApply.type == AbstractPower.PowerType.DEBUFF) {
                                    this.target.useFastShakeAnimation(0.5F);
                                }

                                if (!hasBuffAlready) {
                                    this.target.powers.add(this.powerToApply);
                                    Collections.sort(this.target.powers);
                                    this.powerToApply.onInitialApplication();
                                    this.powerToApply.flash();
                                    if (this.amount >= 0 || !this.powerToApply.ID.equals("Strength") && !this.powerToApply.ID.equals("Dexterity") && !this.powerToApply.ID.equals("Focus")) {
                                        if (this.powerToApply.type == AbstractPower.PowerType.BUFF) {
                                            AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name));
                                        } else {
                                            AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name));
                                        }
                                    } else {
                                        AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name + TEXT[3]));
                                    }

                                    AbstractDungeon.onModifyPower();
                                    if (this.target.isPlayer) {
                                        int buffCount = 0;
                                        Iterator var8 = this.target.powers.iterator();

                                        while(var8.hasNext()) {
                                            AbstractPower p1 = (AbstractPower)var8.next();
                                            if (p1.type == AbstractPower.PowerType.BUFF) {
                                                ++buffCount;
                                            }
                                        }

                                        if (buffCount >= 10) {
                                            UnlockTracker.unlockAchievement("POWERFUL");
                                        }
                                    }
                                }
                                break label148;
                            }

                            p = (AbstractPower)var6.next();
                        } while(!p.ID.equals(this.powerToApply.ID));
                    } while(p.ID.equals("Night Terror"));

                    p.stackPower(this.amount);
                    p.flash();
                    if ((p instanceof StrengthPower || p instanceof DexterityPower) && this.amount <= 0) {
                        AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name + TEXT[3]));
                    } else if (this.amount > 0) {
                        if (p.type != AbstractPower.PowerType.BUFF && !(p instanceof StrengthPower) && !(p instanceof DexterityPower)) {
                            AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, "+" + Integer.toString(this.amount) + " " + this.powerToApply.name));
                        } else {
                            AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, "+" + Integer.toString(this.amount) + " " + this.powerToApply.name));
                        }
                    } else if (p.type == AbstractPower.PowerType.BUFF) {
                        AbstractDungeon.effectList.add(new PowerBuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name + TEXT[3]));
                    } else {
                        AbstractDungeon.effectList.add(new PowerDebuffEffect(this.target.hb.cX - this.target.animX, this.target.hb.cY + this.target.hb.height / 2.0F, this.powerToApply.name + TEXT[3]));
                    }

                    p.updateDescription();
                    hasBuffAlready = true;
                    AbstractDungeon.onModifyPower();
                }
            }

            this.tickDuration();
        } else {
            this.isDone = true;
        }
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("ApplyPowerAction");
        TEXT = uiStrings.TEXT;
    }
}
