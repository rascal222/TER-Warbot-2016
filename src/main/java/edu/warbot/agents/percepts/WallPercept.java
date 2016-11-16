package edu.warbot.agents.percepts;

import edu.warbot.agents.ControllableWarAgent;

import java.awt.geom.Line2D;

public class WallPercept extends WarPercept {

    private Line2D.Double seenWall;

    public WallPercept(ControllableWarAgent observer, Line2D.Double seenWall) {
        super(observer);
        this.seenWall = seenWall;
    }

    public Line2D.Double getSeenWall() {
        return seenWall;
    }
}
