package edu.warbot.tools.geometry;

import java.util.Random;

/**
 * Définition de coordonnées polaires
 *
 * @since 1.0.0
 */
public class PolarCoordinates {

    /**
     *
     */
    private double _distance;

    /**
     *
     */
    private double _angleInDegrees;

    /**
     * @param distance       la distance
     * @param angleInDegrees l'angle en degrés
     */
    public PolarCoordinates(double distance, double angleInDegrees) {
        super();
        this._distance = distance;
        setAngle(angleInDegrees);
    }

    /**
     * @param maxDistance
     * @return
     */
    public static PolarCoordinates getRandomInBounds(double maxDistance) {
        Random random = new Random();
        return new PolarCoordinates(random.nextDouble() * maxDistance, random.nextDouble() * 359.);
    }

    /**
     *
     * @param angle l'angle à inverser
     * @return l'angle opposé
     */
    public static double invertDirectionAngle(double angle) {
        return (360. - angle) % 360.;
    }

    /**
     *
     * @return la distance
     */
    public double getDistance() {
        return _distance;
    }

    /**
     *
     * @param distance la nouvelle distance
     */
    public void setDistance(double distance) {
        this._distance = distance;
    }

    /**
     *
     * @return l'angle
     */
    public double getAngle() {
        return invertDirectionAngle(_angleInDegrees);
    }

    /**
     *
     * @param angleInDegrees le nouvel angle
     */
    public void setAngle(double angleInDegrees) {
        this._angleInDegrees = invertDirectionAngle(angleInDegrees);
    }

    /**
     *
     * @return les coordonnées en cartésien
     */
    public CartesianCoordinates toCartesian() {
        return new CartesianCoordinates(this._distance * Math.cos(Math.toRadians(getAngle())),
                this._distance * Math.sin(Math.toRadians(getAngle())));
    }
}
