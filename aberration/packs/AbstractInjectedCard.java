package aberration.packs;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;

import com.megacrit.cardcrawl.cards.AbstractCard;

public abstract class AbstractInjectedCard extends AbstractCardModifier {

    public AbstractInjectedCard() {
    }

    public String getPrefix() {
        return "";
    }

    public String getSuffix() {
        return "";
    }

    public String modifyName(String cardName, AbstractCard card) {
        String[] nameParts = removeUpgradeText(cardName);
        return this.getPrefix() + nameParts[0] + this.getSuffix() + nameParts[1];
    }


    public boolean canApplyTo(AbstractCard card) {
        return false;
    }

    public boolean hasThisMod(AbstractCard card) {
        return CardModifierManager.modifiers(card).stream().anyMatch((m) -> {
            return m.identifier(card).equals(this.identifier(card));
        });
    }

    public static String[] removeUpgradeText(String name) {
        String[] ret = new String[]{name, ""};
        if (!name.endsWith("+") && !name.endsWith("*")) {
            int index = name.lastIndexOf("+");
            if (index == -1) {
                index = name.lastIndexOf("*");
            }

            if (index != -1 && name.substring(index + 1).chars().allMatch(Character::isDigit)) {
                ret[1] = name.substring(index);
                ret[0] = name.substring(0, index);
            }
        } else {
            ret[1] = name.substring(name.length() - 1);
            ret[0] = name.substring(0, name.length() - 1);
        }

        return ret;
    }
    static {
      }

}
