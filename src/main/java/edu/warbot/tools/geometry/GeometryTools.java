package edu.warbot.tools.geometry;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

public class GeometryTools {

    public static Path2D.Double moveToAndRotateShape(Shape shape, double newX, double newY, double angle) {
        Path2D.Double transformedShape = new Path2D.Double();
        transformedShape.append(shape, false);
        AffineTransform t = new AffineTransform();
        Rectangle2D shapeBounds = shape.getBounds2D();
        t.setToRotation(Math.toRadians(angle), shapeBounds.getWidth() / 2., shapeBounds.getHeight() / 2.);
        transformedShape.transform(t);
        t = new AffineTransform();
        t.setToTranslation(newX, newY);
        transformedShape.transform(t);
        return transformedShape;
    }

    public static Path2D.Double translateShape(Shape shape, double newX, double newY) {
        Path2D.Double transformedShape = new Path2D.Double();
        transformedShape.append(shape, false);
        AffineTransform t = new AffineTransform();
        t.setToTranslation(newX, newY);
        transformedShape.transform(t);
        return transformedShape;
    }

    public static Path2D.Double resize(Shape shape, double multiplier) {
        Path2D.Double transformedShape = new Path2D.Double();
        transformedShape.append(shape, false);
        AffineTransform t = new AffineTransform();
        t.scale(multiplier, multiplier);
        transformedShape.transform(t);
        return transformedShape;
    }

    public static List<Point2D.Double> getPointsFromPath(Path2D.Double path) {
        List<Point2D.Double> points = new ArrayList<Point2D.Double>();

        PathIterator it = path.getPathIterator(null);
        double[] coords = new double[6];
        while (!it.isDone()) {
            int type = it.currentSegment(coords);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    points.add(new Point2D.Double(coords[0], coords[1]));
                    break;
                case PathIterator.SEG_LINETO:
                    points.add(new Point2D.Double(coords[0], coords[1]));
                    break;
                case PathIterator.SEG_CUBICTO:
                    points.add(new Point2D.Double(coords[0], coords[1]));
                    points.add(new Point2D.Double(coords[4], coords[5]));
                    break;
                case PathIterator.SEG_QUADTO:
                    points.add(new Point2D.Double(coords[0], coords[1]));
                    points.add(new Point2D.Double(coords[2], coords[3]));
                    break;
            }
            it.next();
        }

        return points;
    }
}
