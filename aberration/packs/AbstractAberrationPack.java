package aberration.packs;

import aberration.AberrationMod;
import aberration.patch.AbstractRelicPatch;
import aberration.utils.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import java.util.ArrayList;

import static aberration.AberrationMod.logger;
import static aberration.AberrationMod.makePowerPath;

public class AbstractAberrationPack {
    public String packID;

    public String name;
    public String description;
    public String author;
    public AbstractInjectedCard card;
    public AbstractGene gene;
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

    public void ApplyGene(AbstractPlayer player){
        if (player.hasRelic("aberration:AberrationGene")){
            logger.info("adding gene:"+this.gene.name);
            AberrationMod.geneList.add(this.gene);
            player.getRelic("aberration:AberrationGene").onTrigger();
            player.getRelic("aberration:AberrationGene").updateDescription(player.chosenClass);
        }

    }

    public ArrayList<String> getPackPotions() {
        return new ArrayList();
    }

}
