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
            switch (AbstractDungeon.id){
                case "Exordium":
                    AberrationMod.CurrentAberrationPacks.get(0).ApplyMonsterPower(m);
                    break;
                case "TheCity":
                    AberrationMod.CurrentAberrationPacks.get(1).ApplyMonsterPower(m);
                    break;
                case "TheBeyond":
                    AberrationMod.CurrentAberrationPacks.get(2).ApplyMonsterPower(m);
                    break;
                case "TheEnd":
                    AberrationMod.CurrentAberrationPacks.get(3).ApplyMonsterPower(m);
                    break;
            }
//            logger.info(m.name);
        }
    }
}
