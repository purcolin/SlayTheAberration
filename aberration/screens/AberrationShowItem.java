package aberration.screens;

import aberration.AberrationMod;
import aberration.packs.AbstractAberrationPack;
import aberration.packs.AbstractInjectedCard;
import aberration.packs.DeepDescent.DeepDescentPack;
import aberration.packs.Void.VoidPack;
import basemod.BaseMod;
import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.Legend;
import com.megacrit.cardcrawl.map.LegendItem;
import com.megacrit.cardcrawl.powers.AbstractPower;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;

public class AberrationShowItem {
    public static final Logger logger = LogManager.getLogger(AberrationShowItem.class.getName());

    private Texture img;
    public Hitbox hb;
    public ArrayList<AbstractPower> powers = new ArrayList<>();
    public Float x;
    public Float y;
    public String simpleID;
    public static String TEXT = "尖塔遴选了它的畸变";
    public ArrayList<PowerTip> tips = new ArrayList<>();

    public AberrationShowItem(Texture img,AbstractAberrationPack pack, Float x, Float y) {
        this.hb = new Hitbox(384.0F * Settings.xScale, 504.0F * Settings.yScale);
        this.img = img;
        this.x = x;
        this.y = y;
        this.simpleID = pack.packID.replace("Pack","");
        logger.info(this.simpleID);
        logger.info(this.x);
        logger.info(this.y);
        AbstractPower p;
        String pid;
        Iterator var1 = AberrationMod.allAberrationPowers.iterator();
        while(var1.hasNext()) {
            pid = (String) var1.next();
            p = AberrationMod.powersByID.get(pid);
            if(p.ID.contains(simpleID)){
                logger.info(p.ID);
                this.powers.add(p);
                this.tips.add(new PowerTip(p.name, p.description, p.region48));
//                PowerStrings ps = (PowerStrings) ReflectionHacks.getPrivateStatic(p.getClass(),"powerStrings");
            }
        }
        if(!(pack instanceof VoidPack)){
            String name1 = "畸变卡牌："+pack.card.getPrefix();
            String name2 = "畸变基因："+pack.card.getPrefix();
            String desc1 = pack.card.modifyDescription("",new Strike_Blue());
            String desc2 = pack.gene.description;
            this.tips.add(new PowerTip(name1, desc1));
            this.tips.add(new PowerTip(name2, desc2));
        }
    }

    public void update() {
        if (this.hb != null) {
            this.hb.update();
            if (this.hb.hovered) {

            }
        }
    }

    public void render(SpriteBatch sb) {
       if (this.hb.hovered) {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.6F));
            sb.draw(this.img, this.x , this.y, 1500.0F, 600.0F, 650.0F, 867.0F, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 650, 867, false, false);
//            FontHelper.renderFont(sb, FontHelper.prepFont(80.0F,false), TEXT, (float)Settings.WIDTH/2 -360.0F*Settings.xScale,(float)Settings.HEIGHT - 190.0F*Settings.yScale, Settings.CREAM_COLOR);
            TipHelper.queuePowerTips(InputHelper.mX + 60.0F * Settings.scale, InputHelper.mY + 180.0F * Settings.scale, this.tips);
        } else {
            sb.setColor(new Color(1.0F, 1.0F, 1.0F, 1.0F));
//            sb.draw(img, this.x, this.y, 960.0F, 600.0F, 650.0F, 850.0F, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 650, 850, false, false);
//            sb.draw(this.img, (float)Settings.WIDTH / 2.0F - 960.0F + 300.0F , (float)Settings.HEIGHT / 2.0F - 600.0F, 960.0F, 600.0F, 650.0F, 867.0F, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 650, 867, false, false);
            sb.draw(this.img, this.x , this.y, 1500.0F, 600.0F, 650.0F, 867.0F, 1.0F,1.0F, 0.0F, 0, 0, 650, 867, false, false);
//            sb.draw(this.img,(float)Settings.WIDTH / 2.0F - 960.0F , (float)Settings.HEIGHT / 2.0F - 300.0F, 960.0F, 600.0F, 650.0F, 867.0F, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 650, 867, false, false);
        }
        this.hb.move(this.x+ 200.0F , this.y+600.0F);
        this.hb.render(sb);
    }

}
