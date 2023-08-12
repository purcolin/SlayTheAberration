package aberration.utils;

import aberration.AberrationMod;
import aberration.patch.AbstractRoomInfectedPatch;
import aberration.patch.InfectedMonsterRoom;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class AberrationHooks {
    public static final Logger logger = LogManager.getLogger(AberrationHooks.class.getName());
    public static Random AberrationRng;

    public AberrationHooks(Random random){
        AberrationHooks.AberrationRng = random;
    }
    public AberrationHooks() {
    }

    public static void SetRng(Random random){
        AberrationHooks.AberrationRng = random;

    }

    public static void PreStartBattle(InfectedMonsterRoom room){
        logger.info("you entered an infected room");
        if(AbstractRoomInfectedPatch.infected.get(room)){
            AbstractMonster m = room.monsters.getRandomMonster(null,true, AberrationHooks.AberrationRng);
            AberrationMod.CurrentAberrationPacks.get(AberrationMod.currentDungeon()).ApplyMonsterPower(m);
//            logger.info(m.name);
        }
    }
}
