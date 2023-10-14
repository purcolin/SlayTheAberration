package aberration.packs.ThunderDescent;

import aberration.AberrationMod;
import aberration.packs.AbstractGene;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ThunderDescentGene extends AbstractGene {

    public static final Logger logger = LogManager.getLogger(ThunderDescentGene.class.getName());
    public static final String ID = AberrationMod.makeID(ThunderDescentGene.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    public ThunderDescentGene() {
        super(ID,TEXT[0], TEXT[1]);
    }

    public void loading(){
        logger.info("loading:thunder YES!");
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(ID);
        TEXT = uiStrings.TEXT;
    }
}
