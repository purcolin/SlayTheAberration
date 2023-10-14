package aberration.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch2;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

@SpirePatch2(
        clz= AbstractMonster.class,
        method= SpirePatch.CLASS
)
public class AbstractMonsterHalfedPatch {
    public static SpireField<Boolean> Halfed = new SpireField<>(() -> false);


}