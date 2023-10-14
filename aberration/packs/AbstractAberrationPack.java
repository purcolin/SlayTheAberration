package aberration.packs;

import aberration.AberrationMod;
import aberration.patch.AbstractMonsterHalfedPatch;
import aberration.patch.AbstractRelicPatch;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;
import java.util.Iterator;

import static aberration.AberrationMod.logger;
import static aberration.AberrationMod.makePowerPath;

public class AbstractAberrationPack {
    public String packID;

    public String name;
    public String description;
    public String author;
    public AberrationPackType type;
    public AbstractInjectedCard card;
    public AbstractGene gene;

    public boolean Void = false;
    public ArrayList<Integer> validDungeons = new ArrayList<Integer>();
    public Texture bossImage = TextureLoader.getTexture((makePowerPath("WormDescentWormBoss.png")));
    public AbstractAberrationPack() {
        this.initializePack();
    }


    public void initializePack() {

    }

    public void ApplyMonsterPower(AbstractMonster m){

    }
    public void ApplyBossPower(AbstractMonster m){

    }

    public Boolean ApplyGene(AbstractPlayer player){
        if (player.hasRelic("aberration:AberrationGene")){
            logger.info("adding gene:"+this.gene.name);
            this.gene.onEquip();
            Iterator var1 = AberrationMod.geneList.iterator();

            while(var1.hasNext()) {
                AbstractGene g = (AbstractGene)var1.next();
                if(g.name==this.gene.name){
                    return false;
                }
            }
            AberrationMod.AddGene(this.gene);

            player.getRelic("aberration:AberrationGene").updateDescription(player.chosenClass);
            return true;
        }else {
            return false;
        }

    }

    public ArrayList<String> getPackPotions() {
        return new ArrayList();
    }

    public static enum AberrationPackType {
        SEASONS,
        BA_GUA,
        VOID,
        DIY;

        private AberrationPackType() {
        }
    }

}
