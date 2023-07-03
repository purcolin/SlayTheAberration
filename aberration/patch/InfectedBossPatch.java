package aberration.patch;

import aberration.AberrationMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Iterator;

@SpirePatch2(
        clz= MonsterRoomBoss.class,
        method="onPlayerEntry"
)
public class InfectedBossPatch {
    public static final Logger logger = LogManager.getLogger(InfectedBossPatch.class.getName());

    public InfectedBossPatch(){

    }
    public static void Postfix(MonsterRoomBoss __instance) {
        AberrationMod.CurrentRoomType = AbstractRoom.RoomType.BOSS;
    }
}
