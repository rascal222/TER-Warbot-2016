package edu.warbot.maps;

import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.WarCircle;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

public abstract class AbstractWarMap {

    public static final float TEAM_POSITION_RADIUS = 100;
    public static final float FOOD_POSITION_RADIUS = 50;
    private static final double MAP_ACCESSIBLE_AREA_PADDING = 5.;
    private static final double BORDER_THICKNESS = 5.;
    protected Area mapAccessibleArea;
    protected Area mapForbidArea;
    private String name;
    private ArrayList<ArrayList<WarCircle>> _teamsPositions;
    private ArrayList<WarCircle> _foodPositions;
    private double mapWidth;
    private double mapHeight;

    public AbstractWarMap(String name, double mapWidth, double mapHeight) {
        this.name = name;
        _teamsPositions = new ArrayList<>();
        _foodPositions = new ArrayList<>();
        mapAccessibleArea = new Area(new Rectangle2D.Double(-MAP_ACCESSIBLE_AREA_PADDING, -MAP_ACCESSIBLE_AREA_PADDING, mapWidth + (MAP_ACCESSIBLE_AREA_PADDING * 2.), mapHeight + (MAP_ACCESSIBLE_AREA_PADDING * 2.)));
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
    }

    public ArrayList<ArrayList<WarCircle>> getTeamsPositions() {
        return new ArrayList<>(_teamsPositions);
    }

    /**
     * Add all possible positions for one same team.
     *
     * @param coords Coordinates of possible positions for this team
     */
    public void addTeamPositions(CartesianCoordinates... coords) {
        ArrayList<WarCircle> positions = new ArrayList<WarCircle>();
        for (CartesianCoordinates coord : coords)
            positions.add(new WarCircle(((Double) coord.getX()).floatValue(), ((Double) coord.getY()).floatValue(), TEAM_POSITION_RADIUS));
        _teamsPositions.add(positions);
    }

    public ArrayList<WarCircle> getFoodPositions() {
        return new ArrayList<>(_foodPositions);
    }

    public void addFoodPosition(double x, double y) {
        _foodPositions.add(new WarCircle(x, y, FOOD_POSITION_RADIUS));
    }

    public int getMaxPlayers() {
        if (_teamsPositions.size() > 0)
            return _teamsPositions.get(0).size() * _teamsPositions.size();
        else
            return 0;
    }

    public int getMaxTeams() {
        return _teamsPositions.size();
    }

    public String getName() {
        return name;
    }

    public Area getMapAccessibleArea() {
        return new Area(mapAccessibleArea);
    }

    public Area getMapForbidArea() {
        if (mapForbidArea == null)
            updateForbidArea();
        return new Area(mapForbidArea);
    }

    public double getWidth() {
        return mapWidth;
    }

    public double getHeight() {
        return mapHeight;
    }

    public double getCenterX() {
        return mapWidth / 2.;
    }

    public double getCenterY() {
        return mapHeight / 2.;
    }

    protected void forbidArea(Shape forbidArea) {
        mapAccessibleArea.subtract(new Area(forbidArea));
        updateForbidArea();
    }

    protected void forbidAllBorders() {
        forbidArea(new Rectangle2D.Double(0, 0, getWidth(), BORDER_THICKNESS));
        forbidArea(new Rectangle2D.Double(getWidth() - BORDER_THICKNESS, 0, BORDER_THICKNESS, getHeight()));
        forbidArea(new Rectangle2D.Double(0, getHeight() - BORDER_THICKNESS, getWidth(), BORDER_THICKNESS));
        forbidArea(new Rectangle2D.Double(0, 0, BORDER_THICKNESS, getHeight()));
    }

    protected void allowArea(Shape allowedArea) {
        mapAccessibleArea.add(new Area(allowedArea));
        updateForbidArea();
    }

    private void updateForbidArea() {
        mapForbidArea = new Area(new Rectangle2D.Double(0, 0, mapWidth, mapHeight));
        mapForbidArea.subtract(mapAccessibleArea);
    }
}
