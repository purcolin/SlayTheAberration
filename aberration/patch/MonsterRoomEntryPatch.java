package aberration.patch;

import aberration.AberrationMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch2(
        clz= MonsterRoom.class,
        method="onPlayerEntry"
)
public class MonsterRoomEntryPatch {
    public MonsterRoomEntryPatch(){

    }
    public static void Postfix(MonsterRoom __instance) {
        AberrationMod.CurrentRoomType = AbstractRoom.RoomType.MONSTER;
    }
}
