package aberration.patch;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.Legend;
import com.megacrit.cardcrawl.map.LegendItem;

import static com.megacrit.cardcrawl.helpers.ImageMaster.loadImage;

@SpirePatch(
        clz= Legend.class,
        method= SpirePatch.CONSTRUCTOR
)
public class MapLegendPatch {
    public static final String[] TEXT;
    private static final UIStrings uiStrings;
    public static void Postfix(Legend __instance){
	    __instance.items.add(new LegendItem(TEXT[0], loadImage("aberrationResources/images/inmonster.png"), TEXT[1], TEXT[2], 6));
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString("aberration:MapLegendPatch");
        TEXT = uiStrings.TEXT;
    }
}
