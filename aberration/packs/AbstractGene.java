package aberration.packs;

import aberration.relics.aberrationGene;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;

public class AbstractGene{
    public String id;
    public String name;
    public static final Logger logger = LogManager.getLogger(AbstractGene.class.getName());
    public String description;
    public AbstractGene(String id,  String name, String description){
        this.id = id;
        this.name = name;
        this.description = description;

    }
    public void loading() {
        logger.info("Error: loading gene of none");
    }

    public String getDescription(){
        return description;
    }

    public void initialize(){

    }
    public void onLoseHp(int damageAmount) {
    }

    protected void addToBot(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action) {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public void onEvokeOrb(AbstractOrb ammo,AbstractRelic r) {
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m,AbstractRelic relic) {
    }

    public void onPreviewObtainCard(AbstractCard c,AbstractRelic relic) {
    }

    public void onObtainCard(AbstractCard c,AbstractRelic relic) {
    }

    public void onGainGold(AbstractRelic relic) {
    }

    public void onLoseGold(AbstractRelic relic) {
    }

    public void onSpendGold(AbstractRelic relic) {
    }

    public void onEquip() {
    }

    public void onUnequip(AbstractRelic relic) {
    }

    public void atPreBattle(AbstractRelic relic) {
    }

    public void atBattleStart(AbstractRelic relic) {
    }

    public void onSpawnMonster(AbstractMonster monster,AbstractRelic r) {
    }

    public void atBattleStartPreDraw(AbstractRelic relic) {
    }

    public void atTurnStart(AbstractRelic relic) {
    }

    public void atTurnStartPostDraw(AbstractRelic relic) {
    }

    public void onPlayerEndTurn(AbstractRelic relic) {
    }

    public void onBloodied(AbstractRelic relic) {
    }

    public void onNotBloodied(AbstractRelic relic) {
    }

    public void onManualDiscard(AbstractRelic relic) {
    }

    public void onUseCard(AbstractCard targetCard, UseCardAction useCardAction,AbstractRelic r) {
    }

    public void onVictory(AbstractRelic relic) {
    }

    public void onMonsterDeath(AbstractMonster m,AbstractRelic r) {
    }

    public void onBlockBroken(AbstractCreature m,AbstractRelic r) {
    }

    public int onPlayerGainBlock(int blockAmount,AbstractRelic r) {
        return blockAmount;
    }

    public int onPlayerGainedBlock(float blockAmount,AbstractRelic r) {
        return MathUtils.floor(blockAmount);
    }

    public int onPlayerHeal(int healAmount,AbstractRelic r) {
        return healAmount;
    }

    public void onMeditate(AbstractRelic relic) {
    }

    public void onEnergyRecharge(AbstractRelic relic) {
    }

    public void addCampfireOption(ArrayList<AbstractCampfireOption> options,AbstractRelic r) {
    }

    public boolean canUseCampfireOption(AbstractCampfireOption option,AbstractRelic r) {
        return true;
    }

    public void onRest(AbstractRelic relic) {
    }

    public void onRitual(AbstractRelic relic) {
    }

    public void onEnterRestRoom(AbstractRelic relic) {
    }

    public void onRefreshHand(AbstractRelic relic) {
    }

    public void onShuffle(AbstractRelic relic) {
    }

    public void onSmith(AbstractRelic relic) {
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target, AbstractRelic r) {
    }

    public int onAttacked(DamageInfo info, int damageAmount,AbstractRelic relic) {
        return damageAmount;
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount,AbstractRelic relic) {
        return damageAmount;
    }

    public int onAttackToChangeDamage(DamageInfo info, int damageAmount,AbstractRelic relic) {
        return damageAmount;
    }

    public void onExhaust(AbstractCard card,AbstractRelic relic) {
    }

    public void onTrigger(AbstractRelic relic) {
    }

    public void onTrigger(AbstractCreature target,AbstractRelic relic) {
    }

    public boolean checkTrigger(AbstractRelic relic) {
        return false;
    }

    public void onEnterRoom(AbstractRoom room,AbstractRelic relic) {
    }

    public void justEnteredRoom(AbstractRoom room,AbstractRelic relic) {
    }

    public void onCardDraw(AbstractCard drawnCard,AbstractRelic relic) {
    }

    public void onChestOpen(boolean bossChest,AbstractRelic relic) {
    }

    public void onChestOpenAfter(boolean bossChest,AbstractRelic relic) {
    }

    public void onDrawOrDiscard(AbstractRelic relic) {
    }

    public void onMasterDeckChange(AbstractRelic relic) {
    }

    public float atDamageModify(float damage, AbstractCard c,AbstractRelic relic) {
        return damage;
    }

    public int changeNumberOfCardsInReward(int numberOfCards,AbstractRelic relic) {
        return numberOfCards;
    }

    public int changeRareCardRewardChance(int rareCardChance,AbstractRelic relic) {
        return rareCardChance;
    }

    public int changeUncommonCardRewardChance(int uncommonCardChance,AbstractRelic relic) {
        return uncommonCardChance;
    }



}
