package aberration.patch;

import aberration.packs.AbstractGene;
import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;


@SpirePatch2(
        clz= AbstractRelic.class,
        method= SpirePatch.CLASS
)
public class AbstractRelicPatch {
    public static SpireField<ArrayList<AbstractGene>> geneList = new SpireField<>(() -> new ArrayList<>());


}