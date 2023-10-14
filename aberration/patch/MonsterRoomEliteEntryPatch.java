package aberration.patch;

import aberration.AberrationMod;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomElite;

@SpirePatch2(
        clz= MonsterRoomElite.class,
        method="onPlayerEntry"
)
public class MonsterRoomEliteEntryPatch {
    public MonsterRoomEliteEntryPatch(){

    }
    public static void Postfix(MonsterRoom __instance) {
        AberrationMod.CurrentRoomType = AbstractRoom.RoomType.MONSTER;
    }
}
