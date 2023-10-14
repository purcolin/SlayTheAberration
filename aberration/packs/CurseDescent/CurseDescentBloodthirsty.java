package aberration.packs.CurseDescent;

import aberration.AberrationMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import static aberration.AberrationMod.makePowerPath;

public class CurseDescentBloodthirsty extends CustomCard {
    public static final String ID = AberrationMod.makeID(CurseDescentBloodthirsty.class.getSimpleName());
    private static UIStrings cardStrings;
    private static int mNumber = 2;
    // Get object containing the strings that are displayed in the game.
    public static final String[] TEXT;
    private static final String IMG_PATH = makePowerPath(CurseDescentBloodthirsty.class.getSimpleName()+".png");
    private static final int COST = 0;

    public CurseDescentBloodthirsty() {
        super(ID, TEXT[0], IMG_PATH, COST, TEXT[1]+mNumber+TEXT[2],
                CardType.CURSE, CardColor.CURSE,
                CardRarity.CURSE, CardTarget.SELF);
        this.baseMagicNumber = mNumber;
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.magicNumber = this.baseMagicNumber+(AbstractDungeon.player.maxHealth/90);
        this.addToBot(new DamageAction(p, new DamageInfo(p,this.magicNumber)));
        this.addToBot(new DrawCardAction(1));
    }

    @Override
    public AbstractCard makeCopy() {
        return new CurseDescentBloodthirsty();
    }

    @Override
    public void upgrade() {
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = cardStrings.TEXT;
    }
}
