package aberration.actions;

import aberration.packs.DeepDescent.DeepDescentDeepPower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RollMoveAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RemoveMoveAction extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(RemoveMoveAction.class.getName());
    private AbstractMonster monster;

    public RemoveMoveAction(AbstractMonster monster) {
        this.monster = monster;
    }

    public void update() {
        this.monster.setMove("被吞噬",(byte)-1, AbstractMonster.Intent.STUN);
        logger.info(this.monster.name);
        this.monster.intent = AbstractMonster.Intent.STUN;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this.monster,AbstractDungeon.player,new DeepDescentDeepPower(this.monster, AbstractDungeon.player,1)));
        this.isDone = true;
    }
}