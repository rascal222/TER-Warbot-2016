package edu.warbot.tools.geometry;

import java.awt.geom.Ellipse2D;

@SuppressWarnings("serial")
public class WarCircle extends Ellipse2D.Double {

    public WarCircle(double centerX, double centerY, double radius) {
        super(centerX - radius, centerY - radius, radius * 2., radius * 2.);
    }

    public CartesianCoordinates getCenterPosition() {
        return new CartesianCoordinates(getCenterX(), getCenterY());
    }

    public double getRadius() {
        return getWidth() / 2.;
    }

}
