package edu.warbot.brains;

import edu.warbot.brains.implementations.WarBrainImplementation;

import java.awt.*;

/**
 * Created by BEUGNON on 13/08/2015.
 *
 * @author BEUGNON
 * @version 1.0
 * @since ${project.version}
 */
public class GhostBrain extends WarBrainImplementation {

    private String message;

    public GhostBrain() {
        this.message = "Absent Brain";
    }

    public GhostBrain(String message) {
        this.message = message;
    }


    public String action() {
        setDebugString(message);
        setDebugStringColor(Color.RED);
        return idle();
    }

}
