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
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.screens.compendium.RelicViewScreen;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;

public class AberrationShowScreen extends CustomScreen {
    public static final Logger logger = LogManager.getLogger(AberrationShowScreen.class.getName());

    public static ArrayList<Texture> images = new ArrayList<>();
    public static ArrayList<Hitbox> hbs = new ArrayList<>();
    public static float x = (float) Settings.WIDTH / 2.0F;

    private static boolean drawn = false;

    public AberrationShowScreen(){
    }

    @Override
    public AbstractDungeon.CurrentScreen curScreen() {
        return AberrationShowScreen.Enum.ABERRATION_SHOW_SCREEN;
    }

    // Note that this can be private and take any parameters you want.
    // When you call openCustomScreen it finds the first method named "open"
    // and calls it with whatever arguments were passed to it.
    private void open(ArrayList<AbstractAberrationPack> packs) {
        this.hbs.clear();
        this.images.clear();
        logger.info("x:"+Settings.xScale+"y:"+Settings.yScale);
        logger.info("W:"+Settings.WIDTH+"H:"+Settings.HEIGHT);
        for(int i=0;i<packs.size()-1;i++){
            AbstractAberrationPack p = packs.get(i);
            logger.info(p.name);
            logger.info(p.BossPower);
            logger.info((PowerStrings) ReflectionHacks.getPrivateStatic(p.BossPower,"powerStrings"));
            this.images.add(p.bossImage);
            System.out.println(BaseMod.getPowerKeys());
//            this.hbs.add(new Hitbox(x+i*400.0F* Settings.scale, (float)Settings.HEIGHT / 2.0F - 46.0F * Settings.scale - 433.5F* Settings.scale,650.0F* Settings.scale, 867.0F* Settings.scale));
        }
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
        AbstractDungeon.overlayMenu.proceedButton.show();
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

    }

    @Override
    public void update() {
        for(int i=0;i<hbs.size();i++){
            hbs.get(i).update();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
//        logger.info("rendering");
        if(!drawn){
            logger.info("drawing");
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
            FontHelper.renderFont(sb, FontHelper.prepFont(80.0F,false), "尖塔遴选了它的畸变", (float)Settings.WIDTH/2 -360.0F*Settings.scale,(float)Settings.HEIGHT - 190.0F*Settings.scale, Settings.CREAM_COLOR);
            for(int i=0;i<images.size();i++){
                Texture t = images.get(i);
//            Hitbox hb = hbs.get(i);
                sb.draw(t,x+i*400.0F* Settings.xScale, (float)Settings.HEIGHT / 2.0F, 0, 0, 650.0F* Settings.xScale, 867.0F* Settings.yScale, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 650, 867, false, false);
//            hb.render(sb);
//            if (hb.hovered) {
//                sb.setBlendFunction(770, 1);
//                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
//                sb.setBlendFunction(770, 771);
//                FontHelper.renderFont(sb, FontHelper.prepFont(40.0F,false), AberrationMod.CurrentAberrationPacks.get(i).name, x+i*400.0F* Settings.scale,(float)Settings.HEIGHT / 2.0F - 46.0F * Settings.scale - 433.5F* Settings.scale, Settings.CREAM_COLOR);
//            }
            }
            drawn = true;
        }else {
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


    }

    public static class Enum {
        @SpireEnum
        public static AbstractDungeon.CurrentScreen ABERRATION_SHOW_SCREEN;
        public Enum() {
        }
    }
}
