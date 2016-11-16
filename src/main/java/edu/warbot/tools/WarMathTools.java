package edu.warbot.tools;

import com.badlogic.gdx.math.Circle;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.PolarCoordinates;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class WarMathTools {

    public static double getDistanceBetweenTwoPoints(double x1, double y1, double x2, double y2) {
        return Math.hypot(x2 - x1, y2 - y1);
    }

    public static CartesianCoordinates addTwoPoints(Point2D.Double p1, Point2D.Double p2) {
        CartesianCoordinates toReturn = new CartesianCoordinates(p1.getX(), p1.getY());
        toReturn.add(p2);
        return toReturn;
    }

    public static CartesianCoordinates addTwoPoints(Point2D.Double p1, PolarCoordinates p2) {
        return addTwoPoints(p1, p2.toCartesian());
    }

    public static PolarCoordinates addTwoPoints(PolarCoordinates p1, PolarCoordinates p2) {
        return addTwoPoints(p1.toCartesian(), p2.toCartesian()).toPolar();
    }

    public static CartesianCoordinates getCenterOfPoints(ArrayList<Circle> points) {
        double sumX = 0;
        double sumY = 0;
        for (Circle c : points) {
            sumX += c.x;
            sumY += c.y;
        }
        return new CartesianCoordinates(sumX / points.size(), sumY / points.size());
    }
}
