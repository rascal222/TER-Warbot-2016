package edu.warbot.gui.viewer;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.WarProjectile;
import edu.warbot.agents.actions.MovableActionsMethods;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WallPercept;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.launcher.AbstractWarViewer;
import edu.warbot.launcher.WarGameConfig;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.GeometryTools;
import edu.warbot.tools.geometry.WarStar;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WarDefaultViewer extends AbstractWarViewer {

    private static final int healthBarDefaultSize = 10;
    private static final int spaceBetweenAgentAndHealthBar = 2;

    private List<WarStar> explosions;

    private Map<WarAgentType, Shape> spriteByAgents;

    public WarDefaultViewer(WarGame warGame) {
        super(warGame, true);

        explosions = new ArrayList<>();
        spriteByAgents = new HashMap<>();
        for (WarAgentType warAgentType : WarAgentType.values())
            spriteByAgents.put(warAgentType, WarGameConfig.getHitboxOfWarAgent(warAgentType).getShape());
    }

    @Override
    protected void render(Graphics g) {
        super.render(g);
        Graphics2D g2d = (Graphics2D) g;

        // affichage du mode de jeu
        g2d.drawString(WarGame.getInstance().getClass().getSimpleName(), 5, 15);
        //affichage du nombre de ticks par seconde
        g2d.drawString("TPS : " + getGame().getFPS().toString(), 5, 30);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.translate(getMapOffsetX(), getMapOffsetY());

        // Map forbid area
        g2d.setColor(Color.GRAY);
        g2d.fill(GeometryTools.resize(getGame().getMap().getMapForbidArea(), cellSize));

        // Affichage des équipes
        for (InGameTeam t : getGame().getAllTeams()) {
            paintTeam(g2d, t);
        }

        // Affichage des explosions
        for (WarStar s : explosions)
            paintExplosionShape(g2d, s);
        explosions.clear();

        g2d.setColor(Color.RED);
        g2d.drawRect(0, 0, getWidth() * cellSize, getHeight() * cellSize);

        getAgentsIDsSeenBySelectedAgent().clear();
    }


    private void paintTeam(Graphics g, InGameTeam inGameTeam) {
        Graphics2D g2d = (Graphics2D) g;

        Color backgroundColor = inGameTeam.getColor();
        Color borderColor = backgroundColor.darker();
        Color perceptsColor = new Color(backgroundColor.getRed(), backgroundColor.getGreen(),
                backgroundColor.getBlue(), 100);
        boolean isCurrentAgentTheSelectedOne = false;
        boolean haveOneColorChanged = false;

        for (WarAgent agent : inGameTeam.getAllAgents()) {

            // Si les couleurs ont été modifiées, on restaure les couleurs
            if (haveOneColorChanged) {
                backgroundColor = inGameTeam.getColor();
                borderColor = backgroundColor.darker();
                isCurrentAgentTheSelectedOne = false;
                haveOneColorChanged = false;
            }

            if (getDebugModePanel().isVisible()) {
                if (getDebugModePanel().getDebugTools().getSelectedAgent() != null) {
                    if (agent.getID() == getDebugModePanel().getDebugTools().getSelectedAgent().getID()) {
                        borderColor = Color.GRAY;
                        backgroundColor = Color.WHITE;
                        isCurrentAgentTheSelectedOne = true;
                        haveOneColorChanged = true;
                    }
                }
            }

            // Si l'agent courant est vu par l'agent sélectionné
            if (getAgentsIDsSeenBySelectedAgent().contains(agent.getID())) {
                borderColor = Color.YELLOW;
                haveOneColorChanged = true;
            }

            if (agent instanceof ControllableWarAgent) {
                if (getWarToolBar().isShowPercepts()) {
                    paintPerceptionArea(g2d, (ControllableWarAgent) agent, perceptsColor);
                    if (isCurrentAgentTheSelectedOne)
                        paintSeenWalls(g2d, (ControllableWarAgent) agent, Color.GREEN);
                }
                if (getWarToolBar().isShowDebugMessages() || isCurrentAgentTheSelectedOne)
                    paintDebugMessage(g2d, (ControllableWarAgent) agent);
                if (isCurrentAgentTheSelectedOne && ((ControllableWarAgent) agent).getDebugShape() != null)
                    paintDebugShape(g2d, ((ControllableWarAgent) agent).getDebugShape());
            }


            //TODO CHANGE THIS FOR SPRITE
//            Rectangle.Double r = new Rectangle.Double(agent.getX()-agent.getHitboxMinRadius(),agent.getY()-agent.getHitboxMinRadius(),10.0,10.0);
            Shape s = GeometryTools.moveToAndRotateShape(spriteByAgents.get(agent.getType()), agent.getX() - agent.getHitboxMinRadius(), agent.getY() - agent.getHitboxMinRadius(), agent.getHeading());
            Shape agentShape = GeometryTools.resize(s, cellSize);
            g2d.setColor(backgroundColor);
            g2d.fill(agentShape);
            g2d.setColor(borderColor);
            g2d.draw(agentShape);

            if (agent instanceof MovableActionsMethods) {
                paintHeading(g2d, agent, borderColor);
            }

            if (agent instanceof AliveWarAgent) {
                if (getWarToolBar().isShowHealthBars())
                    paintHealthBar(g2d, (AliveWarAgent) agent);
                if (getWarToolBar().isShowInfos())
                    paintInfos(g2d, (AliveWarAgent) agent, backgroundColor);
            }


        }

        if (getDebugModePanel().getDebugTools().getSelectedAgent() != null) {
            if (getDebugModePanel().getDebugTools().getSelectedAgent() instanceof ControllableWarAgent)
                paintDebugMessage(g2d, (ControllableWarAgent) getDebugModePanel().getDebugTools().getSelectedAgent());
        }

        // Affichage des agents mourants
        for (WarAgent a : inGameTeam.getDyingAgents()) {
            if (a instanceof WarProjectile)
                explosions.add(createExplosionShape(a.getPosition(), (int) (((WarProjectile) a).getExplosionRadius() - InGameTeam.MAX_DYING_STEP + a.getDyingStep())));
            else
                explosions.add(createExplosionShape(a.getPosition(), (int) ((a.getDyingStep() + a.getHitboxMinRadius()) * 2)));
        }
    }

    private void paintHeading(Graphics g, WarAgent agent, Color color) {
        g.setColor(color);
        double xPos = agent.getX() * cellSize;
        double yPos = agent.getY() * cellSize;
        double hitboxRadius = agent.getHitboxMinRadius() * cellSize;
        g.drawLine((int) xPos, (int) yPos,
                (int) (xPos + hitboxRadius * Math.cos(Math.toRadians(agent.getHeading()))),
                (int) (yPos + hitboxRadius * Math.sin(Math.toRadians(agent.getHeading()))));
    }

    private void paintHealthBar(Graphics g, AliveWarAgent agent) {
        Color previousColor = g.getColor();
        double xPos = agent.getX() * cellSize;
        double yPos = agent.getY() * cellSize;
        double hitboxRadius = agent.getHitboxMinRadius() * cellSize;
        int healthBarHeight = 3 * cellSize;
        double healthBarWidth = healthBarDefaultSize * cellSize;
        int healthWidth = (int) (healthBarWidth * (Double.valueOf(agent.getHealth()) / Double.valueOf(agent.getMaxHealth())));
        double xBarPos = xPos - (healthBarWidth / 2);
        double yBarPos = yPos - hitboxRadius - healthBarHeight - (spaceBetweenAgentAndHealthBar * cellSize);

        if (agent.getHealth() <= (agent.getMaxHealth() * 0.25))
            g.setColor(Color.RED);
        else
            g.setColor(Color.ORANGE);
        g.fillRect((int) xBarPos, (int) yBarPos, (int) healthBarWidth, healthBarHeight);

        g.setColor(Color.GREEN);
        g.fillRect((int) xBarPos, (int) yBarPos, healthWidth, healthBarHeight);

        g.setColor(Color.DARK_GRAY);
        g.drawRect((int) xBarPos, (int) yBarPos, (int) healthBarWidth, healthBarHeight);

        g.setColor(previousColor);
    }

    private void paintDebugMessage(Graphics g, ControllableWarAgent agent) {
        if (agent.getDebugString() != "") {
            String msg = agent.getDebugString();
            Color fontColor = agent.getDebugStringColor();

            int distanceBubbleFromAgent = 20;
            int padding = 2;

            Font font = new Font("Arial", Font.PLAIN, 10);
            FontMetrics metrics = g.getFontMetrics(font);
            Dimension speechBubbleSize = new Dimension(metrics.stringWidth(msg) + (2 * padding), metrics.getHeight() + (2 * padding));

            Color backgroundColor;
            boolean fontIsDark = ((fontColor.getRed() + fontColor.getGreen() + fontColor.getBlue()) / 3) < 127;
            if (fontIsDark)
                backgroundColor = Color.WHITE;
            else
                backgroundColor = Color.BLACK;

            int posX = (int) ((agent.getX()) * cellSize - (5 / cellSize) - speechBubbleSize.width - distanceBubbleFromAgent);
            int posY = (int) ((agent.getY()) * cellSize - (5 / cellSize) - speechBubbleSize.height - distanceBubbleFromAgent);
            g.setColor(Color.BLACK);
            g.drawLine(posX, posY, ((int) agent.getX() * cellSize), ((int) agent.getY() * cellSize));
            g.setColor(backgroundColor);
            g.fillRect(posX, posY, speechBubbleSize.width, speechBubbleSize.height);
            g.setColor(Color.BLACK);
            g.drawRect(posX, posY, speechBubbleSize.width, speechBubbleSize.height);
            g.setColor(fontColor);
            g.setFont(font);
            g.drawString(msg, posX + padding, posY + speechBubbleSize.height - padding);
        }
    }

    private void paintInfos(Graphics g, AliveWarAgent agent, Color color) {
        g.setColor(color);
        double xPos = agent.getX() * cellSize;
        double yPos = agent.getY() * cellSize;
        g.drawString(agent.getClass().getSimpleName() + " " + agent.getID()
                        + ": " + agent.getTeam().getName() + ", " + agent.getHealth()
                        + " HP, heading: " + (int) agent.getHeading(),
                (int) (xPos + (agent.getHitboxMinRadius() * cellSize)),
                (int) yPos);
    }

    private void paintPerceptionArea(Graphics2D g, ControllableWarAgent agent, Color color) {
        g.setColor(color);
        g.draw(GeometryTools.resize(agent.getPerceptionArea(), cellSize));
    }

    private void paintSeenWalls(Graphics2D g, ControllableWarAgent agent, Color color) {
        g.setColor(color);
        Stroke previousStroke = g.getStroke();
        g.setStroke(new BasicStroke(3));
        for (WallPercept wallPercept : agent.getWallPercepts())
            g.draw(GeometryTools.resize(wallPercept.getSeenWall(), cellSize));
        g.setStroke(previousStroke);
    }

    private void paintDebugShape(Graphics2D g, Shape debugShape) {
        Color previousColor = g.getColor();
        g.setColor(Color.MAGENTA);
        g.draw(GeometryTools.resize(debugShape, cellSize));
        g.setColor(previousColor);
    }

    private WarStar createExplosionShape(CartesianCoordinates pos, int radius) {
        int newRadius = radius * cellSize;
        return createStar(10, new CartesianCoordinates(pos.getX() * cellSize, pos.getY() * cellSize), newRadius, newRadius / 2);
    }

    private void paintExplosionShape(Graphics2D g2d, WarStar s) {
        if (s.getRadiusOuterCircle() > 0) { // Erreur de source inconnue qui arrivait souvent
            RadialGradientPaint color = new RadialGradientPaint(new CartesianCoordinates(s.getCenter().getX(), s.getCenter().getY()),
                    (float) s.getRadiusOuterCircle(),
                    new float[]{0.0f, 0.8f},
                    new Color[]{Color.RED, Color.YELLOW});
            g2d.setPaint(color);
            g2d.fill(s);
        }
    }

    private WarStar createStar(int nbArms, Point2D.Double center, double radiusOuterCircle, double radiusInnerCircle) {
        return new WarStar(nbArms, center, radiusOuterCircle, radiusInnerCircle);
    }

//
//    class SwingView extends JPanel {
//
//        private WarGame game;
//
//        public SwingView(WarGame game) {
//            this.game = game;
//        }
//
//        @Override
//        public void paintComponent(Graphics g) {
//            super.paintComponent(g);
//
//        }
//
//    }
}
