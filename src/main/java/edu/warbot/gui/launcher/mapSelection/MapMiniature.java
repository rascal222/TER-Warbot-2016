package edu.warbot.gui.launcher.mapSelection;

import edu.warbot.game.MotherNatureTeam;
import edu.warbot.game.WarGame;
import edu.warbot.maps.AbstractWarMap;
import edu.warbot.tools.geometry.GeometryTools;
import edu.warbot.tools.geometry.WarCircle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

/**
 * @since 3.2.2
 */
@SuppressWarnings("serial")
public class MapMiniature extends JPanel {

    public static final double SIZE_SMALL = 0.5;
    public static final double SIZE_MEDIUM = 1.;
    public static final double SIZE_LARGE = 2.;
    public static final double SIZE_VERY_LARGE = 3.;
    public static final int FOOD_AND_TEAMS_POSITIONS_TRANSPARENCY = 120;
    public static final Color COLOR_FOOD = new Color(MotherNatureTeam.COLOR.getRed(), MotherNatureTeam.COLOR.getGreen(), MotherNatureTeam.COLOR.getBlue(), FOOD_AND_TEAMS_POSITIONS_TRANSPARENCY);
    private static final double MAP_MARGIN = 5.;
    private static final double DEFAULT_WIDTH = 200.;
    private static final double DEFAULT_HEIGHT = 120.;
    private AbstractWarMap map;
    private double width;
    private double height;
    private double size;

    public MapMiniature(AbstractWarMap map, double size) {
        super();
        this.size = size;
        width = DEFAULT_WIDTH * size;
        height = DEFAULT_HEIGHT * size;
        Dimension dimension = new Dimension(((Double) width).intValue(), ((Double) height).intValue());
        setPreferredSize(dimension);
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        this.map = map;

        setBorder(BorderFactory.createLineBorder(Color.BLACK));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        double resizeMultiplier = Math.min((height - (MAP_MARGIN * 2.)) / map.getHeight(), (width - (MAP_MARGIN * 2.)) / map.getWidth());

        // Translation
        Shape mapBorders = GeometryTools.resize(new Rectangle2D.Double(0, 0, map.getWidth(), map.getHeight()), resizeMultiplier);
        double translateX = (width - mapBorders.getBounds2D().getWidth()) / 2.;
        double translateY = (height - mapBorders.getBounds2D().getHeight()) / 2.;

        // Map forbid area
        g2d.setColor(Color.GRAY);
        Shape resizedShape = GeometryTools.resize(map.getMapForbidArea(), resizeMultiplier);
        g2d.fill(GeometryTools.translateShape(resizedShape, translateX, translateY));

        // Map borders
        g2d.setColor(Color.RED);
        g2d.draw(GeometryTools.translateShape(mapBorders, translateX, translateY));

        // Food positions
        Color backgroundColor = COLOR_FOOD;
        Color borderColor = backgroundColor.darker();
        for (WarCircle position : map.getFoodPositions()) {
            resizedShape = GeometryTools.translateShape(GeometryTools.resize(position, resizeMultiplier), translateX, translateY);
            g2d.setColor(backgroundColor);
            g2d.fill(resizedShape);
            g2d.setColor(borderColor);
            g2d.draw(resizedShape);
        }

        // Teams positions
        int teamCount = 0;
        for (ArrayList<WarCircle> teamPositions : map.getTeamsPositions()) {
            Color currentTeamColor = WarGame.TEAM_COLORS[teamCount];
            backgroundColor = new Color(currentTeamColor.getRed(), currentTeamColor.getGreen(), currentTeamColor.getBlue(), FOOD_AND_TEAMS_POSITIONS_TRANSPARENCY);
            borderColor = backgroundColor.darker();
            for (WarCircle position : teamPositions) {
                resizedShape = GeometryTools.translateShape(GeometryTools.resize(position, resizeMultiplier), translateX, translateY);
                g2d.setColor(backgroundColor);
                g2d.fill(resizedShape);
                g2d.setColor(borderColor);
                g2d.draw(resizedShape);
            }
            teamCount++;
        }

    }

    public AbstractWarMap getMap() {
        return map;
    }

    public void setMap(AbstractWarMap map) {
        this.map = map;
        repaint();
    }

    public double getMiniatureSize() {
        return size;
    }
}
