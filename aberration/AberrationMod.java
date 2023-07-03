package aberration;
import aberration.commands.Aberration;
import aberration.packs.AbstractAberrationPack;
import aberration.packs.AbstractGene;
import aberration.packs.ColdDescent.ColdDescentPack;
import aberration.packs.DeepDescent.DeepDescentPack;
import aberration.packs.WormDescent.WormDescentBossPower;
import aberration.packs.WormDescent.WormDescentPack;
import aberration.screens.AberrationShowScreen;
import aberration.utils.AberrationHooks;
import aberration.relics.aberrationGene;
import aberration.relics.injector;
import aberration.rewards.AberrationSource;
import basemod.AutoAdd;
import basemod.devcommands.ConsoleCommand;
import basemod.helpers.RelicType;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.evacipated.cardcrawl.mod.stslib.Keyword;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import basemod.BaseMod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static basemod.BaseMod.addCustomScreen;
import static basemod.BaseMod.addRelic;

@SpireInitializer
public class AberrationMod implements PostInitializeSubscriber,
        PostBattleSubscriber,
        EditKeywordsSubscriber,
        EditCardsSubscriber,
        EditStringsSubscriber,
        PostDungeonInitializeSubscriber,
        StartGameSubscriber,
        OnStartBattleSubscriber,
        PreStartGameSubscriber,
        PostCreateStartingRelicsSubscriber,
        EditRelicsSubscriber,
        PostDrawSubscriber
{
    public static final Logger logger = LogManager.getLogger(AberrationMod.class.getName());
    public static ArrayList<AbstractAberrationPack> CurrentAberrationPacks = new ArrayList();
    public static ArrayList<AbstractAberrationPack> allPacks = new ArrayList();
    public static HashMap<String, AbstractAberrationPack> packsByID;

    public static Boolean IsShowAberrationScreen;
    public static ArrayList<AbstractGene> geneList = new ArrayList<>();
    private static String modID = "aberration";

    public static AbstractRoom.RoomType CurrentRoomType;

    public AberrationMod() {
        // TODO: make an awesome mod!
        BaseMod.subscribe(this);
//        setModID("aberration");
    }

//    public static void setModID(String ID) { // DON'T EDIT
//        Gson coolG = new Gson(); // EY DON'T EDIT THIS
//        //   String IDjson = Gdx.files.internal("IDCheckStringsDONT-EDIT-AT-ALL.json").readString(String.valueOf(StandardCharsets.UTF_8)); // i hate u Gdx.files
//        InputStream in = AberrationMod.class.getResourceAsStream("/IDCheckStringsDONT-EDIT-AT-ALL.json"); // DON'T EDIT THIS ETHER
//        IDCheckDontTouchPls EXCEPTION_STRINGS = coolG.fromJson(new InputStreamReader(in, StandardCharsets.UTF_8), IDCheckDontTouchPls.class); // OR THIS, DON'T EDIT IT
//        logger.info("You are attempting to set your mod ID as: " + ID); // NO WHY
//        if (ID.equals(EXCEPTION_STRINGS.DEFAULTID)) { // DO *NOT* CHANGE THIS ESPECIALLY, TO EDIT YOUR MOD ID, SCROLL UP JUST A LITTLE, IT'S JUST ABOVE
//            throw new RuntimeException(EXCEPTION_STRINGS.EXCEPTION); // THIS ALSO DON'T EDIT
//        } else if (ID.equals(EXCEPTION_STRINGS.DEVID)) { // NO
//            modID = EXCEPTION_STRINGS.DEFAULTID; // DON'T
//        } else { // NO EDIT AREA
//            modID = ID; // DON'T WRITE OR CHANGE THINGS HERE NOT EVEN A LITTLE
//        } // NO
//        logger.info("Success! ID is " + modID); // WHY WOULD U WANT IT NOT TO LOG?? DON'T EDIT THIS.
//    } // NO
//
//    public static String getModID() { // NO
//        return modID; // DOUBLE NO
//    } // NU-UH
//

    public static void initialize() {
        logger.info("========================= Initializing Aberration. =========================");
        new AberrationMod();
        logger.info("========================= Aberration Initialized   =========================");

    }
    @Override
    public void receivePostInitialize() {
        logger.info("receive post initialize");
        ConsoleCommand.addCommand("aberration", Aberration.class);
        declarePacks();
        logger.info("Full list of packs: " + allPacks.stream().map((pack) -> {
            return pack.name;
        }).collect(Collectors.toList()));
        BaseMod.addCustomScreen(new AberrationShowScreen());

//        BaseMod.addEvent(new AddEventParams.Builder(AfterKill.ID, AfterKill.class).create());

//        BaseMod.registerCustomReward(RewardsPatch.ABERRATION_INFECTEDBLOOD,  (rewardSave) -> { // this handles what to do when this quest type is loaded.
//                    return new InfectedBlood(1);
//                },
//                (customReward) -> { // this handles what to do when this quest type is saved.
//                    return new RewardSave(customReward.type.toString(), null, ((InfectedBlood)customReward).amount, 0);
//                });
    }


    @Override
    public void receivePostBattle(AbstractRoom r) {
        if(CurrentRoomType == AbstractRoom.RoomType.BOSS){r.rewards.add(new AberrationSource());}

    }

    @Override
    public void receiveEditKeywords() {
//        BaseMod.addKeyword(new String[]{"ice","寒冷"},"Will apply a buff to the next attack with Ice in its description.");

        Gson gson = new Gson();
        String json = Gdx.files.internal(getModID() + "Resources/localization/zhs/AberrationMod-Keyword-Strings.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);

        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword(getModID(), keyword.PROPER_NAME, keyword.NAMES, keyword.DESCRIPTION);
                //  getModID().toLowerCase() makes your keyword mod specific (it won't show up in other cards that use that word)
            }
        }
    }

    @Override
    public void receiveEditCards() {
//        AbstractCard flare = new Flare();
//        BaseMod.addCard(flare);
//        logger.info(flare.name);
//        logger.info(flare.cardID);
    }

    @Override
    public void receivePostDungeonInitialize() {
        logger.info("receive post dungeon initialize");
        AbstractDungeon.isScreenUp = true;
        BaseMod.openCustomScreen(AberrationShowScreen.Enum.ABERRATION_SHOW_SCREEN, allPacks);

    }

    @Override
    public void receiveOnBattleStart(AbstractRoom abstractRoom) {
        logger.info("当前战斗房间类型："+CurrentRoomType);
        logger.info(AbstractDungeon.id);
        if(CurrentRoomType == AbstractRoom.RoomType.BOSS){
            Iterator var1 = abstractRoom.monsters.monsters.iterator();
            while(var1.hasNext()) {
                AbstractMonster m = (AbstractMonster)var1.next();
                logger.info(m.id);
                logger.info(AberrationMod.CurrentAberrationPacks.get(currentDungeon()).name);
                AberrationMod.CurrentAberrationPacks.get(currentDungeon()).ApplyBossPower(m);
//                switch (AbstractDungeon.id){
//                    case "Exordium":
//                        logger.info("infecting");
//                        logger.info(AberrationMod.CurrentAberrationPacks.get(0).name);
//                        AberrationMod.CurrentAberrationPacks.get(0).ApplyBossPower(m);
//                        break;
//                    case "TheCity":
//                        logger.info("infecting");
//                        logger.info(AberrationMod.CurrentAberrationPacks.get(1).name);
//                        AberrationMod.CurrentAberrationPacks.get(1).ApplyBossPower(m);
//                        break;
//                    case "TheBeyond":
//                        logger.info("infecting");
//                        logger.info(AberrationMod.CurrentAberrationPacks.get(2).name);
//                        AberrationMod.CurrentAberrationPacks.get(2).ApplyBossPower(m);
//                        break;
//                    case "TheEnd":
////                        AberrationMod.CurrentAberrationPacks.get(3).ApplyBossPower(m);
//                        break;
//                }
            }
        }
    }

    @Override
    public void receiveStartGame() {
        logger.info("========================= Loading Aberration. =========================");
        logger.info("Full list of packs: " + this.geneList.stream().map((gene) -> {
            gene.loading();
            return gene.name;
        }).collect(Collectors.toList()));
        AberrationHooks ab = new AberrationHooks(new Random(Settings.seed));
        ab.SetRng(new Random(Settings.seed));
        logger.info("randompack:"+ getRandomPackFromAll(ab.AberrationRng).name);
        this.CurrentAberrationPacks = getNPacks(ab.AberrationRng,3);
//        this.CurrentAberrationPacks.add(new ColdDescentPack());
//        this.CurrentAberrationPacks.add(new WormDescentPack());
//        this.CurrentAberrationPacks.add(new DeepDescentPack());
        logger.info("Full list of packs: " + this.CurrentAberrationPacks.stream().map((pack) -> {
            return pack.name;
        }).collect(Collectors.toList()));


    }

    @Override
    public void receivePreStartGame() {
        logger.info("pre start game");
        this.CurrentAberrationPacks.clear();
        logger.info("Restoring gene list: " + this.geneList.stream().map((gene) -> {
            return gene.name;
        }).collect(Collectors.toList()));
        this.geneList.clear();

    }
    @Override
    public void receiveEditRelics() {
        addRelic(new injector(), RelicType.SHARED);
        addRelic(new aberrationGene(), RelicType.SHARED);

    }

    @Override
    public void receivePostCreateStartingRelics(AbstractPlayer.PlayerClass playerClass, ArrayList<String> arrayList) {
        arrayList.add("aberration:Injector");
        arrayList.add("aberration:AberrationGene");
    }

    @Override
    public void receiveEditStrings() {
        BaseMod.loadCustomStringsFile(RelicStrings.class, "aberrationResources/localization/zhs/AberrationMod-Relic-Strings.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "aberrationResources/localization/zhs/AberrationMod-Power-Strings.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "aberrationResources/localization/zhs/AberrationMod-UI-Strings.json");
        BaseMod.loadCustomStringsFile(MonsterStrings.class, "aberrationResources/localization/zhs/AberrationMod-Monster-Strings.json");
    }

    @Override
    public void receivePostDraw(AbstractCard card) {
//    Iterator var2 = CardModifierManager.modifiers(card).iterator();
//    if (var2.hasNext()) {
//        logger.info((AbstractCardModifier)var2.next());
//
//        logger.info("抽卡："+CardModifierManager.onRenderTitle(card, card.name));
//        logger.info(CardModifierManager.modifiers(card));
    }

    public static void declarePacks() {
        packsByID = new HashMap();
        (new AutoAdd("aberration")).packageFilter(AbstractAberrationPack.class).any(AbstractAberrationPack.class, (info, pack) -> {
            if (packsByID.containsKey(pack.packID)) {
                throw new RuntimeException("Duplicate pack detected with ID: " + pack.packID + ". Pack class 1: " + ((AbstractAberrationPack)packsByID.get(pack.packID)).getClass().getName() + ", pack class 2: " + pack.getClass().getName());
            } else if(pack.packID!=null) {
                packsByID.put(pack.packID, pack);
                allPacks.add(pack);
            }
        });
    }

    public static AbstractAberrationPack getRandomPackFromAll(Random rng) {
        return (AbstractAberrationPack)allPacks.get(rng.random(0, allPacks.size() - 1));
    }

    public static Boolean containPack(ArrayList<AbstractAberrationPack> packs,AbstractAberrationPack pack){
        Iterator var10 = packs.iterator();
        while(var10.hasNext()) {
            AbstractAberrationPack p = (AbstractAberrationPack)var10.next();
            if(p.packID==pack.packID){
                return true;
            }
        }
        return false;
    }
    public static ArrayList<AbstractAberrationPack> getNPacks(Random rng, int n){
        ArrayList<AbstractAberrationPack> var4 = new ArrayList<>();
        while(var4.size()<n){
            AbstractAberrationPack var5 = getRandomPackFromAll(rng);
            if(!containPack(var4,var5)&&var5.validDungeons.contains(var4.size())){
                var4.add(var5);
            }
        }
        return var4;
    }


    public static String makePowerPath(String resourcePath) {
        String[] var0 = resourcePath.split("Descent");
        String var2 = var0[0] + "Descent";
        String var3 = var0[1];
        return "aberrationResources/images/packs/"+var2+"/"+resourcePath;
    }

    public static String makeID(String idText) {
        return modID + ":" + idText;
    }

    public static String getClassById(String idText){
        String tem = idText.replace(modID+":","");
        return "aberration.packs."+tem.replace("Gene","")+"."+tem;
    }

    public static Integer currentDungeon(){
        switch (AbstractDungeon.id){
            case "Exordium":
                return 0;
            case "TheCity":
                return 1;
            case "TheBeyond":
                return 2;
            case "TheEnd":
                return 3;
        }
        return -1;
    }

    public String getModID(){
        return this.modID;
    }

}





