package aberration.events;

import com.megacrit.cardcrawl.events.AbstractImageEvent;

public class AfterKill extends AbstractImageEvent {

    //This isn't technically needed but it becomes useful later
    public static final String ID = "My First Event";

    public AfterKill(){
        super(ID, "My body text", "img/events/eventpicture.png");

        //This is where you would create your dialog options
        this.imageEventText.setDialogOption("选择一张牌"); //This adds the option to a list of options
        this.imageEventText.setDialogOption("离开"); //This adds the option to a list of options
    }

    @Override
    protected void buttonEffect(int buttonPressed) {
        //It is best to look at examples of what to do here in the basegame event classes, but essentially when you click on a dialog option the index of the pressed button is passed here as buttonPressed.
    }
}