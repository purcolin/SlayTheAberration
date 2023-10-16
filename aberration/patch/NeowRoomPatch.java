package aberration.patch;

import aberration.AberrationMod;
import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch2(
        clz= NeowRoom.class,
        method="update"
)
public class NeowRoomPatch {
    private static final Logger logger = LogManager.getLogger(NeowRoomPatch.class.getName());
    @SpirePrefixPatch
    public static void Prefix(NeowRoom __instance) {
//        logger.info("=========================  neow room hook =========================");
//        logger.info(AbstractDungeon.screen);
    }
}
