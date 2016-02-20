package edu.warbot.agents;

import edu.warbot.agents.actions.MovableActionsMethods;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.capacities.CommonCapacities;
import edu.warbot.brains.capacities.Movable;
import edu.warbot.game.InGameTeam;
import edu.warbot.maps.AbstractWarMap;
import edu.warbot.tools.WarMathTools;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.GeometryTools;
import edu.warbot.tools.geometry.PolarCoordinates;
import edu.warbot.tools.geometry.WarCircle;
import madkit.kernel.AbstractAgent;
import turtlekit.kernel.Turtle;

import java.awt.geom.Area;

/**
 * Définition d'un agent de Warbot à partir d'une "tortue" Turtle de TurtleKit (et par extension Agent de Madkit)
 */
public abstract class WarAgent extends Turtle implements CommonCapacities {

    @SuppressWarnings("unused")
	private static final int MAP_MARGINS = 2;

    private Hitbox hitbox;
    private InGameTeam inGameTeam;
    private int _dyingStep;

    public WarAgent(String firstActionToDo, InGameTeam inGameTeam, Hitbox hitbox) {
        super(firstActionToDo);
        this.inGameTeam = inGameTeam;
        this.hitbox = hitbox;
        _dyingStep = 0;
    }

    @Override
    protected void activate() {
        super.activate();
        getTeam().addWarAgent(this);
        boolean created = createGroupIfAbsent(getTeamName(), InGameTeam.DEFAULT_GROUP_NAME);
        logger.fine("CreateGroupIfAbsent: <" + getTeamName() + "," + InGameTeam.DEFAULT_GROUP_NAME + "> -> " + (created));

        created = createGroupIfAbsent(getTeamName(), InGameTeam.ID_GROUP_NAME);
        logger.fine("CreateGroupIfAbsent: <" + getTeamName() + "," + InGameTeam.ID_GROUP_NAME + "> -> " + (created));

        ReturnCode rc = requestRole(InGameTeam.ID_GROUP_NAME, "" + getID());
        logger.fine("RequestRole:<" + InGameTeam.ID_GROUP_NAME + "," + getID() + "> -> " + (rc.toString()));

        rc = requestRole(InGameTeam.DEFAULT_GROUP_NAME, getType().toString());
        logger.fine("RequestRole:<" + InGameTeam.DEFAULT_GROUP_NAME + "," + getType().toString() + "> -> " + (rc.toString()));

    }

    public abstract void kill();

    protected void doBeforeEachTick() {

    }

    @Override
    public AbstractAgent.ReturnCode requestRole(String group, String role) {
        if (InGameTeam.DEFAULT_GROUP_NAME.equals(group) || InGameTeam.ID_GROUP_NAME.equals(group))
            return ReturnCode.ACCESS_DENIED;
        createGroupIfAbsent(getTeamName(), group);
        return requestRole(getTeamName(), group, role);
    }

    @Override
    public AbstractAgent.ReturnCode leaveRole(String group, String role) {
        if (InGameTeam.DEFAULT_GROUP_NAME.equals(group) || InGameTeam.ID_GROUP_NAME.equals(group))
            return ReturnCode.ACCESS_DENIED;
        return super.leaveRole(getTeamName(), group, role);
    }

    @Override
    public AbstractAgent.ReturnCode leaveGroup(String group) {
        if (InGameTeam.DEFAULT_GROUP_NAME.equals(group) || InGameTeam.ID_GROUP_NAME.equals(group))
            return ReturnCode.ACCESS_DENIED;
        return super.leaveGroup(getTeamName(), group);
    }

    @Override
    public int getNumberOfAgentsInRole(String group, String role) {
        return (getAgentsWithRole(getTeamName(), group, role).size());
    }

    public InGameTeam getTeam() {
        return inGameTeam;
    }

    @Override
    public String getTeamName() {
        return getTeam().getName();
    }

    @Override
    public boolean isEnemy(WarAgentPercept percept) {
        return !percept.getTeamName().equals(getTeamName());
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public Area getActualForm() {
        return getActualFormAtPosition(getX(), getY());
    }

    public Area getActualFormAtPosition(double x, double y) {
        return new Area(GeometryTools.moveToAndRotateShape(getHitbox().getShape(), x - (getHitbox().getWidth() / 2.), y - (getHitbox().getHeight() / 2.), getHeading()));
    }

    public double getHitboxMinRadius() {
        return Math.min(getHitbox().getHeight(), getHitbox().getWidth()) / 2.;
    }

    public double getHitboxMaxRadius() {
        return Math.max(getHitbox().getHeight(), getHitbox().getWidth()) / 2.;
    }

    public String toString() {
        return "[" + getID() + "] " + getClass().getSimpleName() + " (" + getTeam().getName() + ")";
    }

    @Override
    public void setRandomHeading() {
        randomHeading();
    }

    @Override
    public void setRandomHeading(int range) {
        randomHeading(range);
    }

    protected boolean isGoingToBeOutOfMap() {

        CartesianCoordinates nextPos = new CartesianCoordinates(getX(), getY());
        if (this instanceof MovableActionsMethods)
            nextPos.add(new PolarCoordinates(((Movable) this).getSpeed(), getHeading()).toCartesian());

        return !getTeam().getGame().getMap().getMapAccessibleArea().contains(nextPos.getX(), nextPos.getY());
    }

    protected boolean isGoingToBeOverAnOtherAgent() {
        CartesianCoordinates futurePosition = getPosition();
        double searchAreaRadius = getHitboxMaxRadius() * 4.;
        if (this instanceof MovableActionsMethods) {
            searchAreaRadius += ((Movable) this).getSpeed();
            futurePosition = WarMathTools.addTwoPoints(new CartesianCoordinates(getX(), getY()), new PolarCoordinates(((Movable) this).getSpeed(), getHeading()));
        }
        for (WarAgent a : getTeam().getGame().getAllAgentsInRadius(futurePosition.getX(), futurePosition.getY(), searchAreaRadius)) {
            if (a.getID() != getID() && a instanceof AliveWarAgent) {
                if (this instanceof Movable) {
                    return isInCollisionWithAtPosition(futurePosition, a);
                } else
                    return isInCollisionWith(a);
            }
        }
        return false;
    }

    @Deprecated
    protected boolean isInCollisionWith2(WarAgent agent) {
        Area currentAgentArea = new Area(getActualForm());
        Area agentArea = new Area(agent.getActualForm());
        agentArea.intersect(currentAgentArea);
        return !agentArea.isEmpty();

    }

    protected boolean isInCollisionWith(WarAgent agent) {
        return WarMathTools.getDistanceBetweenTwoPoints(getX(), getY(), agent.getX(), agent.getY()) < getHitboxMaxRadius() * 1.5;
    }

    protected boolean isInCollisionWithAtPosition(CartesianCoordinates pos, WarAgent agent) {
        Area currentAgentArea = new Area(getActualFormAtPosition(pos.getX(), pos.getY()));
        Area agentArea = new Area(agent.getActualForm());
        agentArea.intersect(currentAgentArea);
        return !agentArea.isEmpty();
    }

    public void setPositionAroundOtherAgent(WarAgent agent) {
        setPosition(agent.getPosition());
        moveOutOfCollision();
    }

    public void setRandomPositionInCircle(CartesianCoordinates centerPoint, double radius) {
        setPosition(WarMathTools.addTwoPoints(centerPoint, PolarCoordinates.getRandomInBounds(radius).toCartesian()));
        moveOutOfCollision();
    }

    public void setRandomPositionInCircle(WarCircle circle) {
        setRandomPositionInCircle(new CartesianCoordinates(circle.getCenterX(), circle.getCenterY()), circle.getRadius());
    }

    public CartesianCoordinates getPosition() {
        return new CartesianCoordinates(getX(), getY());
    }

    public void setPosition(CartesianCoordinates pos) {
        setPosition(pos.getX(), pos.getY());
    }

    public void setPosition(double a, double b) {
        super.setXY(a, b);
    }

    //TODO TRY TO FOUND A SOLUTION FOR THIS ENDLESS LOOP
    public void moveOutOfCollision() {

        // Test si l'agent est hors carte
        CartesianCoordinates agentPosition = getPosition();
        AbstractWarMap map = getTeam().getGame().getMap();
        if (!map.getMapAccessibleArea().contains(agentPosition.getX(), agentPosition.getY())) {
            PolarCoordinates moveToMapCenter = new PolarCoordinates(1, agentPosition.getAngleToPoint(new CartesianCoordinates(map.getCenterX(), map.getCenterY())));
            do {
                agentPosition.add(moveToMapCenter.toCartesian());
            } while (!map.getMapAccessibleArea().contains(agentPosition.getX(), agentPosition.getY()));
            setPosition(agentPosition);
        }

        // Test de collision avec un autre agent
        double searchAreaRadius = getHitboxMaxRadius();
        for (WarAgent a : getTeam().getGame().getAllAgentsInRadius(getX(), getY(), searchAreaRadius)) {
            if (a.getID() != getID() && a instanceof AliveWarAgent) {
                if (isInCollisionWith(a)) {
                    agentPosition = getPosition();
                    PolarCoordinates moveAwayFromAgent = new PolarCoordinates(1, a.getPosition().getAngleToPoint(getPosition()));
                    do {
                        agentPosition.add(moveAwayFromAgent.toCartesian());
                    }
                    while (isInCollisionWithAtPosition(agentPosition, a)); // On boucle tant que l'agent est en collision avec l'autre agent
                    // Une fois que l'agent hors collision, on lui assigne sa nouvelle position
                    setPosition(agentPosition);
                }
            }
        }
    }

    /**
     * @return Le nombre de pas passés depuis la mort de l'agent
     */
    public int getDyingStep() {
        return _dyingStep;
    }

    public void incrementDyingStep() {
        _dyingStep++;
    }

    public double getAverageDistanceFrom(WarAgent a) {
        return WarMathTools.getDistanceBetweenTwoPoints(getX(), getY(), a.getX(), a.getY())
                - ((getHitboxMaxRadius() + getHitboxMinRadius()) / 2.) - ((a.getHitboxMaxRadius() + a.getHitboxMinRadius()) / 2.);
    }

    public double getMinDistanceFrom(WarAgent a) {
        return WarMathTools.getDistanceBetweenTwoPoints(getX(), getY(), a.getX(), a.getY())
                - getHitboxMaxRadius() - a.getHitboxMaxRadius();
    }

    /**
     * @return le type d'un agent
     */
    public abstract WarAgentType getType();

}
