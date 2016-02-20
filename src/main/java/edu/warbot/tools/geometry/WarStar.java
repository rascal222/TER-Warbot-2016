package edu.warbot.tools.geometry;

import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

@SuppressWarnings("serial")
public class WarStar extends Path2D.Double {

    private Point2D.Double center;
    private double radiusOuterCircle;
    private double radiusInnerCircle;

    public WarStar(int nbArms, Point2D.Double center, double radiusOuterCircle, double radiusInnerCircle) {
        super();
        this.center = center;
        this.radiusOuterCircle = radiusOuterCircle;
        this.radiusInnerCircle = radiusInnerCircle;

        double angle = Math.PI / nbArms;
        for (int i = 0; i < 2 * nbArms; i++) {
            double r = (i & 1) == 0 ? radiusOuterCircle : radiusInnerCircle;
            Point2D.Double p = new Point2D.Double(center.x + Math.cos(i * angle) * r, center.y + Math.sin(i * angle) * r);
            if (i == 0) moveTo(p.getX(), p.getY());
            else lineTo(p.getX(), p.getY());
        }
        closePath();
    }

    public Point2D.Double getCenter() {
        return center;
    }

    public double getRadiusInnerCircle() {
        return radiusInnerCircle;
    }

    public double getRadiusOuterCircle() {
        return radiusOuterCircle;
    }
}
