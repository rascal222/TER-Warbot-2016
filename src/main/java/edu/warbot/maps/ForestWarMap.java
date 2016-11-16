package edu.warbot.maps;

import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.GeometryTools;
import edu.warbot.tools.geometry.WarStar;

import java.awt.geom.Rectangle2D;
import java.util.Random;

public class ForestWarMap extends AbstractWarMap {

    private static final double WIDTH = 1000;
    private static final double HEIGHT = 600;
    private static final double MIDDLE_FOREST_WIDTH = WIDTH - 500;
    private static final double TREE_OUTER_RADIUS = 15;
    private static final double TREE_INNER_RADIUS = 8;
    private static final int TREE_MAX_ARMS = 7;
    private static final int TREE_MIN_ARMS = 3;
    private static final double SPACE_BETWEEN_TREES = 60;

    private static final WarStar[] availableTrees;

    static {
        availableTrees = new WarStar[TREE_MAX_ARMS - TREE_MIN_ARMS + 1];
        int tabIndex = 0;
        CartesianCoordinates initialPosition = new CartesianCoordinates(0, 0);
        for (int nbArms = TREE_MIN_ARMS; nbArms <= TREE_MAX_ARMS; nbArms++) {
            availableTrees[tabIndex] = new WarStar(nbArms, initialPosition, TREE_OUTER_RADIUS, TREE_INNER_RADIUS);
            tabIndex++;
        }
    }

    public ForestWarMap() {
        super("Forêt", WIDTH, HEIGHT);

        forbidAllBorders();

        // OpenWorld on forest borders
        allowArea(new Rectangle2D.Double((WIDTH - MIDDLE_FOREST_WIDTH) / 2., 0, MIDDLE_FOREST_WIDTH, HEIGHT));

        // Trees
        boolean gapY = false;
        for (double posX = (WIDTH - MIDDLE_FOREST_WIDTH) / 2.; posX < (WIDTH + MIDDLE_FOREST_WIDTH) / 2.; posX += SPACE_BETWEEN_TREES) {
            for (double posY = 15; posY < HEIGHT; posY += SPACE_BETWEEN_TREES) {
                double displacedPosY = posY;
                if (gapY)
                    displacedPosY = posY + (SPACE_BETWEEN_TREES / 2.);
                forbidArea(GeometryTools.translateShape(availableTrees[new Random().nextInt(availableTrees.length)], posX, displacedPosY));
            }
            gapY = !gapY;
        }

        addTeamPositions(
                new CartesianCoordinates(TEAM_POSITION_RADIUS, TEAM_POSITION_RADIUS),
                new CartesianCoordinates(TEAM_POSITION_RADIUS, getHeight() / 2),
                new CartesianCoordinates(TEAM_POSITION_RADIUS, getHeight() - TEAM_POSITION_RADIUS)
        );
        addTeamPositions(
                new CartesianCoordinates(getWidth() - TEAM_POSITION_RADIUS, TEAM_POSITION_RADIUS),
                new CartesianCoordinates(getWidth() - TEAM_POSITION_RADIUS, getHeight() / 2),
                new CartesianCoordinates(getWidth() - TEAM_POSITION_RADIUS, getHeight() - TEAM_POSITION_RADIUS)
        );

        addFoodPosition(getCenterX(), getCenterY() - (getCenterY() / 2.));
        addFoodPosition(getCenterX(), getCenterY());
        addFoodPosition(getCenterX(), getCenterY());
        addFoodPosition(getCenterX(), getCenterY() + (getCenterY() / 2.));
    }
}
