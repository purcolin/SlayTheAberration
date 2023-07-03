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
        clz= RoomTypeAssigner.class,
        method="distributeRoomsAcrossMap"
)
public class MonsterRoomPatch {
    private static final Logger logger = LogManager.getLogger(MonsterRoomPatch.class.getName());
    public MonsterRoomPatch(){

    }
    @SpireInsertPatch(
        rloc = 2,
        localvars = {"nodeCount"}
    )
    public static void Insert(Random rng, ArrayList<ArrayList<MapRoomNode>> map, @ByRef ArrayList<AbstractRoom>[] roomList,int nodeCount) {
        logger.info("=========================  Aberration Into Monsters. =========================");
        logger.info(nodeCount);
        float tem = 0.5F;
        while(roomList[0].size() < nodeCount) {
            roomList[0].add(new InfectedMonsterRoom(rng.random.nextInt(100)<100*tem));
            logger.info("add one infected monster");
        }
    }


}