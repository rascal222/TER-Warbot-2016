package edu.warbot.brains;

import edu.warbot.brains.implementations.WarBrainImplementation;

import java.awt.*;

/**
 * Created by beugnon on 02/10/15.
 */
public class DyingBrain extends WarBrainImplementation {

    private String message;

    private int tick = 0;

    public DyingBrain() {
        this.message = "Absent Brain";
    }

    public DyingBrain(String message) {
        this.message = message;
    }


    public String action() {
        tick++;
        setDebugString(message.concat("(Meurt dans " + (500 - tick) + " ticks)"));
        setDebugStringColor(Color.RED);
        if(tick < 500)
            return idle();
        else
            setDebugString(message.concat("(Décède maintenant)"));
            return die();
    }
}
