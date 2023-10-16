package aberration.screens;

import aberration.AberrationMod;
import aberration.packs.AbstractAberrationPack;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.Iterator;

public class AberrationShowScreen extends CustomScreen {
    public static final Logger logger = LogManager.getLogger(AberrationShowScreen.class.getName());

    public static ArrayList<Texture> images = new ArrayList<>();
    public static ArrayList<AberrationShowItem> items = new ArrayList<>();

    public static Texture image0;
    public static Texture image1;
    public static Texture image2;
    public static ArrayList<Hitbox> hbs = new ArrayList<>();

    public static Integer count;

    private static int i = 0;
    public static String TEXT = "尖塔遴选了它的畸变";

    private static boolean drawn = false;
    private static boolean hovering = false;
    public ArrayList<AbstractAberrationPack> packs;

    public AberrationShowScreen(){
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return AberrationShowScreen.Enum.ABERRATION_SHOW_SCREEN;
    }

    private void open(ArrayList<AbstractAberrationPack> packs) {
        this.hbs.clear();
        this.images.clear();
        this.items.clear();
        this.packs = packs;
//        image0 = packs.get(0).bossImage;
//        logger.info(p.name);
//        logger.info(p.BossPower);
//        PowerStrings ps = (PowerStrings) ReflectionHacks.getPrivateStatic(p.BossPower,"powerStrings");
//        logger.info(ps.NAME);
//        logger.info(ps.DESCRIPTIONS);
//        this.images.add(p.bossImage);
//            this.hbs.add(new Hitbox(x+i*400.0F* Settings.scale, (float)Settings.HEIGHT / 2.0F - 46.0F * Settings.scale - 433.5F* Settings.scale,650.0F* Settings.scale, 867.0F* Settings.scale));

//        if (AbstractDungeon.screen != AbstractDungeon.CurrentScreen.NONE)
//            AbstractDungeon.previousScreen = AbstractDungeon.screen;
        // Call reopen in this example because the basics of setting the current screen are the same across both
        reopen();
    }

    @Override
    public void reopen()
    {
        AbstractDungeon.overlayMenu.showBlackScreen();
        AberrationMod.IsShowAberrationScreen = true;
        AbstractDungeon.screen = curScreen();
        AbstractDungeon.isScreenUp = true;
        AbstractAberrationPack p;
        this.count = 0;
        Iterator var1 = packs.iterator();
        while(var1.hasNext()) {
            p = (AbstractAberrationPack) var1.next();
            items.add(new AberrationShowItem(p.bossImage,p,80.0F + 450.0F*this.count*Settings.xScale, - 164.0F*Settings.yScale));
            count ++;
        }
        logger.info(items);

        AbstractDungeon.overlayMenu.cancelButton.show("返回");
    }

    @Override
    public void openingSettings()
    {
        // Required if you want to reopen your screen when the settings screen closes
        AbstractDungeon.previousScreen = curScreen();
    }

    // ...
    @Override
    public void close() {
        AbstractDungeon.isScreenUp = false;
        genericScreenOverlayReset();
    }

    @Override
    public void update() {
        for(int i=0;i<items.size();i++){
            items.get(i).update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        if(!drawn&&i<100){
            FontHelper.renderFont(sb, FontHelper.prepFont(80.0F,false), TEXT, (float)Settings.WIDTH/2 -360.0F*Settings.xScale,(float)Settings.HEIGHT - 190.0F*Settings.yScale, Settings.CREAM_COLOR);
            i++;
        }else {
            drawn=true;
        }
        sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
        //        sb.draw(image0,(float)Settings.WIDTH / 2.0F - 960.0F , (float)Settings.HEIGHT / 2.0F - 300.0F, 960.0F, 600.0F, 650.0F, 867.0F, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 650, 867, false, false);
//        sb.draw(image1,(float)Settings.WIDTH / 2.0F - 960.0F + 300.0F, (float)Settings.HEIGHT / 2.0F - 300.0F, 960.0F, 600.0F, 650.0F, 867.0F, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 650, 867, false, false);
//        sb.draw(image2,(float)Settings.WIDTH / 2.0F - 960.0F + 300.0F*2, (float)Settings.HEIGHT / 2.0F - 300.0F, 960.0F, 600.0F, 650.0F, 867.0F, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 650, 867, false, false);

        for(int i=0;i<4;i++) {
             AberrationShowItem image =items.get(i);
//             image.renderHover(sb,hovering);
             image.render(sb);
        }

//            for(int i=0;i<images.size();i++){
//            Hitbox hb = hbs.get(i);
//                sb.draw(images.get(i),(float)Settings.WIDTH / 2.0F - 960.0F + 700.0F*i, (float)Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 650.0F* Settings.xScale, 867.0F* Settings.yScale, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 650, 867, false, false);
//            hb.render(sb);
//            if (hb.hovered) {
//                sb.setBlendFunction(770, 1);
//                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
//                sb.setBlendFunction(770, 771);
//                FontHelper.renderFont(sb, FontHelper.prepFont(40.0F,false), AberrationMod.CurrentAberrationPacks.get(i).name, x+i*400.0F* Settings.scale,(float)Settings.HEIGHT / 2.0F - 46.0F * Settings.scale - 433.5F* Settings.scale, Settings.CREAM_COLOR);
//            }
//            }
//            drawn = true;
//        }else {
//            for(int i=0;i<images.size();i++){
//                Hitbox hb = hbs.get(i);
//                hb.render(sb);
//                if (hb.hovered) {
//                    sb.setBlendFunction(770, 1);
//                    sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
//                    sb.setBlendFunction(770, 771);
//                    FontHelper.renderFont(sb, FontHelper.prepFont(40.0F,false), AberrationMod.CurrentAberrationPacks.get(i).name, x+i*400.0F* Settings.scale,(float)Settings.HEIGHT / 2.0F - 46.0F * Settings.scale - 433.5F* Settings.scale, Settings.CREAM_COLOR);
//                }
//            }
    }

    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen ABERRATION_SHOW_SCREEN;
        public Enum() {
        }
    }
}
