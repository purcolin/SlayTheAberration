package aberration.packs.ThunderDescent;

import aberration.AberrationMod;
import aberration.commands.AberrationGene;
import aberration.packs.AbstractGene;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SpirePatch2(
        clz= ChannelAction.class,
        method=SpirePatch.CONSTRUCTOR,
        paramtypez = {
                AbstractOrb.class,
                boolean.class
        }
)
public class ChannelActionPatch {
    private static final Logger logger = LogManager.getLogger(ChannelActionPatch.class.getName());


    public static void Prefix(ChannelAction __instance,@ByRef AbstractOrb[] newOrbType) {
        logger.info("=========================  channel hook =========================");
        boolean var0 = false;
        for(AbstractGene g: AberrationMod.geneList){
            if(g.id == ThunderDescentGene.ID){
                var0 = true;
                logger.info("geng enene a Enhanced lightning");
            }
        }
        if(newOrbType[0].ID == Lightning.ORB_ID && var0){
            newOrbType[0] = new ThunderDescentPlasmaLightning();
            logger.info("channel a Enhanced lightning");
        }
    }

}
