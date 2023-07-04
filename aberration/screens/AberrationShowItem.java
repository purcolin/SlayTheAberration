package aberration.screens;

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

import java.util.ArrayList;

public class AberrationShowItem {

    private static final float ICON_X;
    private static final float TEXT_X;
    private static final float SPACE_Y;
    private static final float OFFSET_Y;
    private Texture img;
    private static final int W = 128;
    private int index;
    private String label;
    public Hitbox hb;
    public AberrationShowItem(String label, Texture img, int index) {
        this.hb = new Hitbox(230.0F * Settings.xScale, SPACE_Y - 2.0F);
        this.label = label;
        this.img = img;
        this.index = index;
    }

    public void update() {
        this.hb.update();
    }

    public void render(SpriteBatch sb, Color c) {
        sb.setColor(c);
        if (!Settings.isMobile) {
            if (this.hb.hovered) {
                sb.draw(this.img, ICON_X - 64.0F, Legend.Y - SPACE_Y * (float)this.index + OFFSET_Y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale / 1.2F, Settings.scale / 1.2F, 0.0F, 0, 0, 128, 128, false, false);
            } else {
                sb.draw(this.img, ICON_X - 64.0F, Legend.Y - SPACE_Y * (float)this.index + OFFSET_Y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale / 1.65F, Settings.scale / 1.65F, 0.0F, 0, 0, 128, 128, false, false);
            }
        } else if (this.hb.hovered) {
            sb.draw(this.img, ICON_X - 64.0F, Legend.Y - SPACE_Y * (float)this.index + OFFSET_Y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 128, 128, false, false);
        } else {
            sb.draw(this.img, ICON_X - 64.0F, Legend.Y - SPACE_Y * (float)this.index + OFFSET_Y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, Settings.scale / 1.3F, Settings.scale / 1.3F, 0.0F, 0, 0, 128, 128, false, false);
        }

        if (Settings.isMobile) {
            FontHelper.panelNameFont.getData().setScale(1.2F);
        }

        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, this.label, TEXT_X - 50.0F * Settings.scale, Legend.Y - SPACE_Y * (float)this.index + OFFSET_Y + 13.0F * Settings.yScale, c);
        if (Settings.isMobile) {
            FontHelper.panelNameFont.getData().setScale(1.0F);
        }

        this.hb.move(TEXT_X, Legend.Y - SPACE_Y * (float)this.index + OFFSET_Y);
        if (c.a != 0.0F) {
            this.hb.render(sb);
        }

    }

    static {
        ICON_X = 1575.0F * Settings.xScale;
        TEXT_X = 1670.0F * Settings.xScale;
        SPACE_Y = Settings.isMobile ? 64.0F * Settings.yScale : 58.0F * Settings.yScale;
        OFFSET_Y = Settings.isMobile ? 110.0F * Settings.yScale : 100.0F * Settings.yScale;
    }
}
