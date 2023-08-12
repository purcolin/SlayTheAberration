package aberration.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecoverMoveAction extends AbstractGameAction {
    private static final Logger logger = LogManager.getLogger(RecoverMoveAction.class.getName());
    private AbstractMonster monster;

    public RecoverMoveAction(AbstractMonster monster) {
        this.monster = monster;
    }

    public void update() {
        this.monster.rollMove();
        this.monster.createIntent();
        this.isDone = true;
    }
}