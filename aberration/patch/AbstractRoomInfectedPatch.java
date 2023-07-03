package aberration.patch;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.map.RoomTypeAssigner;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
@SpirePatch2(
        clz= AbstractRoom.class,
        method= SpirePatch.CLASS
)
public class AbstractRoomInfectedPatch {
    public static SpireField<Boolean> infected = new SpireField<>(() -> false);


}