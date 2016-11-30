package edu.warbot.maps;

import edu.warbot.tools.geometry.CartesianCoordinates;

public class DefaultWarMap extends AbstractWarMap {

    private static final double WIDTH = 1000;
    private static final double HEIGHT = 600;

    public DefaultWarMap() {
        super("Arène", WIDTH, HEIGHT);

        forbidAllBorders();

        addTeamPositions(
                new CartesianCoordinates(TEAM_POSITION_RADIUS + 50, TEAM_POSITION_RADIUS + 50),
                new CartesianCoordinates(TEAM_POSITION_RADIUS + 50, getHeight() / 2),
                new CartesianCoordinates(TEAM_POSITION_RADIUS + 50, getHeight() - TEAM_POSITION_RADIUS - 50)
        );
        addTeamPositions(
                new CartesianCoordinates(getWidth() - TEAM_POSITION_RADIUS - 50, TEAM_POSITION_RADIUS + 50),
                new CartesianCoordinates(getWidth() - TEAM_POSITION_RADIUS - 50, getHeight() / 2),
                new CartesianCoordinates(getWidth() - TEAM_POSITION_RADIUS - 50, getHeight() - TEAM_POSITION_RADIUS - 50)
        );

        addFoodPosition(getCenterX(), getCenterY() - (getCenterY() / 2.));
        addFoodPosition(getCenterX(), getCenterY());
        addFoodPosition(getCenterX(), getCenterY());
        addFoodPosition(getCenterX(), getCenterY() + (getCenterY() / 2.));
    }

}
