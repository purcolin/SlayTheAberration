package aberration.patch;
import aberration.relics.injector;
import aberration.rewards.InfectedBlood;
import aberration.utils.AberrationHooks;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.EnableEndTurnButtonAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAndEnableControlsAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.Careless;
import com.megacrit.cardcrawl.daily.mods.ControlledChaos;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.AbstractImageEvent;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.input.DevInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.potions.BlessingOfTheForge;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.ui.buttons.CancelButton;
import com.megacrit.cardcrawl.vfx.GameSavedEffect;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

public class InfectedMonsterRoom extends MonsterRoom {
    private static final Logger logger;
    private float endBattleTimer;

    private boolean infected;

    public InfectedMonsterRoom(Boolean infected) {
        this.phase = AbstractRoom.RoomPhase.COMBAT;

        if(infected){
            this.mapSymbol = "I";
            this.mapImg = loadImage("aberrationResources/images/inmonster.png");
        }else{
            this.mapSymbol = "M";
            this.mapImg = ImageMaster.MAP_NODE_ENEMY;
        }
        this.mapImgOutline = ImageMaster.MAP_NODE_ENEMY_OUTLINE;
        AbstractRoomInfectedPatch.infected.set(this,infected);
        this.infected = infected;
    }

    public void update() {
        if (!AbstractDungeon.isScreenUp && InputHelper.pressedEscape && AbstractDungeon.overlayMenu.cancelButton.current_x == CancelButton.HIDE_X) {
            AbstractDungeon.settingsScreen.open();
        }

        if (Settings.isDebug) {
            if (InputHelper.justClickedRight) {
                AbstractDungeon.player.obtainPotion(new BlessingOfTheForge());
                AbstractDungeon.scene.randomizeScene();
            }

            if (Gdx.input.isKeyJustPressed(49)) {
                AbstractDungeon.player.increaseMaxOrbSlots(1, true);
            }

            if (DevInputActionSet.gainGold.isJustPressed()) {
                AbstractDungeon.player.gainGold(100);
            }
        }

        switch (this.phase) {
            case EVENT:
                this.event.updateDialog();
                break;
            case COMBAT:
                this.monsters.update();
                if (!(waitTimer > 0.0F)) {
                    if (Settings.isDebug && DevInputActionSet.drawCard.isJustPressed()) {
                        AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
                    }

                    if (!AbstractDungeon.isScreenUp) {
                        AbstractDungeon.actionManager.update();
                        if (!this.monsters.areMonstersBasicallyDead() && AbstractDungeon.player.currentHealth > 0) {
                            AbstractDungeon.player.updateInput();
                        }
                    }

                    if (!AbstractDungeon.screen.equals(AbstractDungeon.CurrentScreen.HAND_SELECT)) {
                        AbstractDungeon.player.combatUpdate();
                    }

                    if (AbstractDungeon.player.isEndingTurn) {
                        this.endTurn();
                    }
                } else {
                    if (AbstractDungeon.actionManager.currentAction == null && AbstractDungeon.actionManager.isEmpty()) {
                        waitTimer -= Gdx.graphics.getDeltaTime();
                    } else {
                        AbstractDungeon.actionManager.update();
                    }

                    if (waitTimer <= 0.0F) {
                        AbstractDungeon.actionManager.turnHasEnded = true;
                        if (!AbstractDungeon.isScreenUp) {
                            AbstractDungeon.topLevelEffects.add(new BattleStartEffect(false));
                        }

                        AbstractDungeon.actionManager.addToBottom(new GainEnergyAndEnableControlsAction(AbstractDungeon.player.energy.energyMaster));
//                        logger.info(AbstractRoomInfectedPatch.infected.get(this));
                        AberrationHooks ah = new AberrationHooks();
                        ah.PreStartBattle(this);
                        AbstractDungeon.player.applyStartOfCombatPreDrawLogic();
                        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, AbstractDungeon.player.gameHandSize));
                        AbstractDungeon.actionManager.addToBottom(new EnableEndTurnButtonAction());
                        AbstractDungeon.overlayMenu.showCombatPanels();
                        AbstractDungeon.player.applyStartOfCombatLogic();
                        if (ModHelper.isModEnabled("Careless")) {
                            Careless.modAction();
                        }

                        if (ModHelper.isModEnabled("ControlledChaos")) {
                            ControlledChaos.modAction();
                        }

                        this.skipMonsterTurn = false;
                        AbstractDungeon.player.applyStartOfTurnRelics();
                        AbstractDungeon.player.applyStartOfTurnPostDrawRelics();
                        AbstractDungeon.player.applyStartOfTurnCards();
                        AbstractDungeon.player.applyStartOfTurnPowers();
                        AbstractDungeon.player.applyStartOfTurnOrbs();
                        AbstractDungeon.actionManager.useNextCombatActions();
                    }
                }

                if (this.isBattleOver && AbstractDungeon.actionManager.actions.isEmpty()) {
                    this.skipMonsterTurn = false;
                    this.endBattleTimer -= Gdx.graphics.getDeltaTime();
                    if (this.endBattleTimer < 0.0F) {
                        this.phase = AbstractRoom.RoomPhase.COMPLETE;
                        if (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) || !(CardCrawlGame.dungeon instanceof TheBeyond) || Settings.isEndless) {
                            CardCrawlGame.sound.play("VICTORY");
                        }

                        this.endBattleTimer = 0.0F;
                        int card_seed_before_roll;

                        if(this.infected){
                            if(((injector) AbstractDungeon.player.getRelic(injector.ID)).counter>0){
                                this.rewards.add(new InfectedBlood(1));
                            }else{
                                this.rewards.add(new InfectedBlood());
                            }
                        }


                        if (!AbstractDungeon.getMonsters().haveMonstersEscaped()) {
                            ++CardCrawlGame.monstersSlain;
                            logger.info("MONSTERS SLAIN " + CardCrawlGame.monstersSlain);
                            if (Settings.isDailyRun) {
                                this.addGoldToRewards(15);
                            } else {
                                this.addGoldToRewards(AbstractDungeon.treasureRng.random(10, 20));
                            }
                        }



                        if (!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) || !(CardCrawlGame.dungeon instanceof TheBeyond) && !(CardCrawlGame.dungeon instanceof TheEnding) || Settings.isEndless) {
                            if (!AbstractDungeon.loading_post_combat) {
                                this.dropReward();
                                this.addPotionToRewards();
                            }

                            card_seed_before_roll = AbstractDungeon.cardRng.counter;
                            int card_randomizer_before_roll = AbstractDungeon.cardBlizzRandomizer;
                            if (this.rewardAllowed) {
                                if (this.mugged) {
                                    AbstractDungeon.combatRewardScreen.openCombat(TEXT[0]);
                                } else if (this.smoked) {
                                    AbstractDungeon.combatRewardScreen.openCombat(TEXT[1], true);
                                } else {
                                    AbstractDungeon.combatRewardScreen.open();
                                }

                                if (!CardCrawlGame.loadingSave && !AbstractDungeon.loading_post_combat) {
                                    SaveFile saveFile = new SaveFile(SaveFile.SaveType.POST_COMBAT);
                                    saveFile.card_seed_count = card_seed_before_roll;
                                    saveFile.card_random_seed_randomizer = card_randomizer_before_roll;
                                    if (this.combatEvent) {
                                        --saveFile.event_seed_count;
                                    }

                                    SaveAndContinue.save(saveFile);
                                    AbstractDungeon.effectList.add(new GameSavedEffect());
                                } else {
                                    CardCrawlGame.loadingSave = false;
                                }

                                AbstractDungeon.loading_post_combat = false;
                            }
                        }
                    }
                }

                this.monsters.updateAnimations();
                break;
            case COMPLETE:
                if (!AbstractDungeon.isScreenUp) {
                    AbstractDungeon.actionManager.update();
                    if (this.event != null) {
                        this.event.updateDialog();
                    }

                    if (AbstractDungeon.actionManager.isEmpty() && !AbstractDungeon.isFadingOut) {
                        if (this.rewardPopOutTimer > 1.0F) {
                            this.rewardPopOutTimer = 1.0F;
                        }

                        this.rewardPopOutTimer -= Gdx.graphics.getDeltaTime();
                        if (this.rewardPopOutTimer < 0.0F) {
                            if (this.event == null) {
                                AbstractDungeon.overlayMenu.proceedButton.show();
                            } else if (!(this.event instanceof AbstractImageEvent) && !this.event.hasFocus) {
                                AbstractDungeon.overlayMenu.proceedButton.show();
                            }
                        }
                    }
                }
            case INCOMPLETE:
                break;
            default:
                logger.info("MISSING PHASE, bro");
        }

        AbstractDungeon.player.update();
        AbstractDungeon.player.updateAnimations();
    }


    static {
        logger = LogManager.getLogger(AbstractRoom.class.getName());
        blizzardPotionMod = 0;
        waitTimer = 0.0F;
    }
}
