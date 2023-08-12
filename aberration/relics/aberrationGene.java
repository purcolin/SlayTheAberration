package aberration.relics;

import aberration.AberrationMod;
import aberration.packs.AbstractGene;
import aberration.utils.TextureLoader;
import basemod.abstracts.CustomRelic;
import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class aberrationGene extends CustomRelic implements CustomSavable<ArrayList<AbstractGene>>{
    public static final String ID = "aberration:AberrationGene";
    private static final int GeneNum = 0;
    public static final Logger logger = LogManager.getLogger(aberrationGene.class.getName());


    public aberrationGene() {
        super(ID, TextureLoader.getTexture("aberrationResources/images/InfectedGene.png"), // you could create the texture in this class if you wanted too
                RelicTier.STARTER, LandingSound.MAGICAL); // this relic is uncommon and sounds magic when you click it
    }


    @Override
    public void updateDescription(AbstractPlayer.PlayerClass c) {

        if(AberrationMod.geneList.isEmpty()){
            this.description = DESCRIPTIONS[1];
        }else {
            String d = DESCRIPTIONS[0];
            for(AbstractGene g: AberrationMod.geneList){
                d += g.name+": NL "+g.getDescription()+" NL ";
            }
            this.description = d;
        }
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        initializeTips();

    }
    @Override
    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        for(AbstractGene g: AberrationMod.geneList){
            logger.info(g.name);
            g.onAttack(info, damageAmount, target,this);
        }
    }

    @Override
    public String getUpdatedDescription() {
        if (AberrationMod.geneList.isEmpty()) {
            return DESCRIPTIONS[1];
        } else {
            String d = DESCRIPTIONS[0];
            for (AbstractGene g : AberrationMod.geneList) {
                d += g.name+": NL "+g.getDescription()+" NL ";
            }
            return d;
        }
    }
    @Override
    public void onMonsterDeath(AbstractMonster m) {
        for(AbstractGene g: AberrationMod.geneList){
            g.onMonsterDeath(m,this);
        }
    }

    @Override
    public int onAttacked(DamageInfo info, int damageAmount) {
        for(AbstractGene g: AberrationMod.geneList){
            damageAmount = g.onAttacked(info,damageAmount,this);
        }
        return damageAmount;
    }

    @Override
    public void onLoseHp(int damageAmount) {
        for(AbstractGene g: AberrationMod.geneList){
            g.onLoseHp(damageAmount);
        }
    }

    @Override
    public void atBattleStart() {
        for(AbstractGene g: AberrationMod.geneList){
            g.atBattleStart(this);
        }
    }


    @Override
    public AbstractRelic makeCopy() { // always override this method to return a new instance of your relic
        return new aberrationGene();
    }


    @Override
    public ArrayList<AbstractGene> onSave() {
        return AberrationMod.geneList;
    }

    @Override
    public void onLoad(ArrayList<AbstractGene> abstractGenes) {
        for(AbstractGene g: abstractGenes){
            logger.info("restoring:"+g.name);
            logger.info("restoring:"+g.getClass().getName());
            String tem = AberrationMod.getClassById(g.id);
            logger.info("restoring:"+tem);
            try {
                AberrationMod.geneList.add((AbstractGene) Class.forName(tem).newInstance());
            } catch (InstantiationException e) {
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        this.updateDescription(AbstractDungeon.player.chosenClass);
    }
}
