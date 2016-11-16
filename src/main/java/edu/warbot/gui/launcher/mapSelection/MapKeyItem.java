package edu.warbot.gui.launcher.mapSelection;

import edu.warbot.game.WarGame;
import edu.warbot.gui.PaintablePanel;
import edu.warbot.gui.launcher.HiddenLongTextJLabel;
import edu.warbot.tools.geometry.WarCircle;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;

@SuppressWarnings("serial")
public class MapKeyItem extends JPanel implements PaintablePanel.Painter {

    public static final int ID_KEY_TEAMS_POSITIONS = 0;
    public static final int ID_KEY_FOOD_POSITIONS = 1;
    public static final int ID_KEY_WALLS = 2;
    public static final int ID_KEY_MAP_LIMITS = 3;

    private static final int PICTURE_WIDTH = 50;
    private static final int PICTURE_HEIGHT = 50;

    private static final int DESCRIPTION_MAX_WIDTH = 30;

    private PaintablePanel drawablePanel;

    public MapKeyItem(int idKey, String title, String description) {
        super(new BorderLayout());

        drawablePanel = new PaintablePanel(new Dimension(PICTURE_WIDTH, PICTURE_HEIGHT), idKey, this);
        JPanel pnlDrawableContainer = new JPanel(new FlowLayout());
        pnlDrawableContainer.add(drawablePanel);
        add(pnlDrawableContainer, BorderLayout.WEST);

        JPanel pnlDescription = new JPanel(new BorderLayout());
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 14));
        pnlDescription.add(lblTitle, BorderLayout.NORTH);
        HiddenLongTextJLabel lblDescription = new HiddenLongTextJLabel(description, DESCRIPTION_MAX_WIDTH, 5);
        lblDescription.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
        pnlDescription.add(lblDescription, BorderLayout.CENTER);
        add(pnlDescription, BorderLayout.CENTER);

        setPreferredSize(new Dimension(300, 80));
        setMaximumSize(getPreferredSize());
    }

    @Override
    public void paint(Graphics2D g, int paintingID) {
        Shape shape;
        switch (paintingID) {
            case ID_KEY_FOOD_POSITIONS:
                shape = new WarCircle(PICTURE_WIDTH / 2., PICTURE_HEIGHT / 2., Math.min(PICTURE_HEIGHT, PICTURE_WIDTH) / 2.);
                g.setColor(MapMiniature.COLOR_FOOD);
                g.fill(shape);
                g.setColor(MapMiniature.COLOR_FOOD.darker());
                g.draw(shape);
                break;
            case ID_KEY_TEAMS_POSITIONS:
                shape = new WarCircle(PICTURE_WIDTH / 3., PICTURE_HEIGHT / 3., Math.min(PICTURE_HEIGHT, PICTURE_WIDTH) / 3.);
                Color color = new Color(WarGame.TEAM_COLORS[0].getRed(), WarGame.TEAM_COLORS[0].getGreen(), WarGame.TEAM_COLORS[0].getBlue(), MapMiniature.FOOD_AND_TEAMS_POSITIONS_TRANSPARENCY);
                g.setColor(color);
                g.fill(shape);
                g.setColor(color.darker());
                g.draw(shape);
                shape = new WarCircle(PICTURE_WIDTH - (PICTURE_WIDTH / 3.), PICTURE_HEIGHT - (PICTURE_HEIGHT / 3.), Math.min(PICTURE_HEIGHT / 3., PICTURE_WIDTH / 3.));
                color = new Color(WarGame.TEAM_COLORS[1].getRed(), WarGame.TEAM_COLORS[1].getGreen(), WarGame.TEAM_COLORS[1].getBlue(), MapMiniature.FOOD_AND_TEAMS_POSITIONS_TRANSPARENCY);
                g.setColor(color);
                g.fill(shape);
                g.setColor(color.darker());
                g.draw(shape);
                break;
            case ID_KEY_WALLS:
                shape = new Rectangle2D.Double(0, 0, PICTURE_WIDTH, PICTURE_HEIGHT);
                g.setColor(Color.GRAY);
                g.fill(shape);
                break;
            case ID_KEY_MAP_LIMITS:
                shape = new Rectangle2D.Double(0, 0, PICTURE_WIDTH - 1, PICTURE_HEIGHT - 1);
                g.setColor(Color.RED);
                g.draw(shape);
                break;
        }
    }
}
