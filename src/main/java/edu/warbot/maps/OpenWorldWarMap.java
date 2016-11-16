package edu.warbot.maps;

import edu.warbot.tools.geometry.CartesianCoordinates;

public class OpenWorldWarMap extends AbstractWarMap {

    private static final double WIDTH = 1000;
    private static final double HEIGHT = 600;

    public OpenWorldWarMap() {
        super("Monde ouvert", WIDTH, HEIGHT);

        double widthQuarter = WIDTH / 4.;
        double heightThird = HEIGHT / 3.;
        addTeamPositions(
                new CartesianCoordinates(widthQuarter, heightThird),
                new CartesianCoordinates(widthQuarter, HEIGHT - heightThird)
        );
        addTeamPositions(
                new CartesianCoordinates(WIDTH - widthQuarter, heightThird),
                new CartesianCoordinates(WIDTH - widthQuarter, HEIGHT - heightThird)
        );

        addFoodPosition(getCenterX(), getCenterY() - (getCenterY() / 2.));
        addFoodPosition(getCenterX(), getCenterY());
        addFoodPosition(getCenterX(), getCenterY());
        addFoodPosition(getCenterX(), getCenterY() + (getCenterY() / 2.));
    }

}
