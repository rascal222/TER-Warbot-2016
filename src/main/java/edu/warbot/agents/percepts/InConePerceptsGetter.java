package edu.warbot.agents.percepts;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.game.WarGame;

import java.awt.*;
import java.awt.geom.Arc2D;

public class InConePerceptsGetter extends PerceptsGetter {

    public InConePerceptsGetter(ControllableWarAgent agent, WarGame game) {
        super(agent, game);
    }

    @Override
    public Shape getPerceptionAreaShape() {
        double halfAngleOfView = getAgent().getAngleOfView() / 2.;
        return new Arc2D.Double(getAgent().getX() - getAgent().getDistanceOfView(),
                getAgent().getY() - getAgent().getDistanceOfView(),
                2. * getAgent().getDistanceOfView(),
                2. * getAgent().getDistanceOfView(),
                360. - getAgent().getHeading() - halfAngleOfView,
                getAgent().getAngleOfView(),
                Arc2D.PIE);
    }
}
