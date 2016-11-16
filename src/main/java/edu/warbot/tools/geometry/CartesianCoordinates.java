package edu.warbot.tools.geometry;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 *
 * @since 1.0.0
 */
@SuppressWarnings("serial")
public class CartesianCoordinates extends Point2D.Double {

    /**
     * @param x la coordonnée en X
     * @param y la coordonnée en Y
     */
    public CartesianCoordinates(double x, double y) {
        super(x, y);
    }

    /**
     * Convertie un point en coordonnées cartésiennes
     * @param point le point à convertir
     */
    public CartesianCoordinates(Point point) {
        super(point.getX(), point.getY());
    }

    /**
     * Convertie un point en coordonnées cartésiennesœ
     * @param point le point à convertir
     */
    public CartesianCoordinates(Point2D.Double point) {
        super(point.getX(), point.getY());
    }

    /**
     * Donne des coordonnées cartésiennes aléatoires
     * @param x coordonnée X
     * @param y coordonnée Y
     * @param width la largeur de la zone
     * @param height la hauteur de la zone
     * @return
     */
    public static CartesianCoordinates getRandomInBounds(int x, int y, int width, int height) {
        Random random = new Random();
        return new CartesianCoordinates((random.nextDouble() * (width - x)) + x, (random.nextDouble() * (height - y)) + y);
    }

    /**
     *
     * @param pSrc le point source
     * @param pDest le point destinataire
     * @return récupère l'angle polaire entre les deux points
     */
    public static double getAngleBetween(Point2D pSrc, Point2D pDest) {
        CartesianCoordinates pFromOrigin = new CartesianCoordinates(pDest.getX() - pSrc.getX(), pDest.getY() - pSrc.getY());
        return pFromOrigin.toPolar().getAngle();
    }

    /**
     *
     * @return les coordonnées en polaire
     */
    public PolarCoordinates toPolar() {
        return new PolarCoordinates(Math.sqrt(Math.pow(getX(), 2) + Math.pow(getY(), 2)),
                Math.toDegrees(Math.atan2(getY(), getX())));
    }

    /**
     *
     * @param point
     */
    public void add(Point2D.Double point) {
        setLocation(getX() + point.getX(), getY() + point.getY());
    }

    /**
     *
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     */
    public void normalize(double minX, double maxX, double minY, double maxY) {
        double newX = getX();
        double newY = getY();
        if (getX() < minX)
            newX = minX;
        else if (getX() > maxX)
            newX = maxX;
        if (getY() < minY)
            newY = minY;
        else if (getY() > maxY)
            newY = maxY;
        setLocation(newX, newY);
    }

    /**
     *
     * @return
     */
    public Point toPoint() {
        return new Point((int) getX(), (int) getY());
    }

    public double getAngleToPoint(Point2D.Double p) {
        CartesianCoordinates pFromOrigin = new CartesianCoordinates(p.getX() - getX(), p.getY() - getY());
        return pFromOrigin.toPolar().getAngle();
    }

    @Override
    public String toString() {
        return "(" + getX() + "; " + getY() + ")";
    }
}
