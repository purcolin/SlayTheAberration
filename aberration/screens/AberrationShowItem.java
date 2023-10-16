package aberration.screens;

import aberration.AberrationMod;
import aberration.packs.AbstractAberrationPack;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.Legend;
import com.megacrit.cardcrawl.map.LegendItem;
import com.megacrit.cardcrawl.powers.AbstractPower;

import java.util.ArrayList;
import java.util.Iterator;

public class AberrationShowItem {

    private Texture img;
    private static final int W = 128;
    public Hitbox hb;
    public ArrayList<AbstractPower> powers;
    public Float x;
    public Float y;

    public AberrationShowItem(Texture img,AbstractAberrationPack pack) {
        this.hb = new Hitbox(650.0F * Settings.xScale, 850.0F * Settings.yScale);
        this.img = img;
        AbstractPower p;
//        Iterator var1 = AberrationMod.allAberrationPowers.iterator();
//        while(var1.hasNext()) {
//            p = (AbstractPower) var1.next();
//            if()
//        }
//        this.powers
    }

    public void update() {
        this.hb.update();
    }

    public void render(SpriteBatch sb) {
        sb.draw(img,this.x , this.y, 960.0F, 600.0F, 650.0F* Settings.xScale, 867.0F* Settings.yScale, Settings.xScale, Settings.yScale, 0.0F, 0, 0, 650, 867, false, false);
    }
}
