package edu.warbot.agents;

import edu.warbot.agents.actions.ControllableActionsMethods;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.PerceptsGetter;
import edu.warbot.agents.percepts.WallPercept;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.agents.resources.WarFood;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Controllable;
import edu.warbot.brains.implementations.AgentBrainImplementer;
import edu.warbot.communications.WarKernelMessage;
import edu.warbot.communications.WarMessage;
import edu.warbot.game.InGameTeam;
import edu.warbot.tools.WarMathTools;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.PolarCoordinates;
import madkit.kernel.Message;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.logging.Level;

/**
 *
 */
public abstract class ControllableWarAgent extends AliveWarAgent implements ControllableActionsMethods, Controllable {

    private double _angleOfView;
    private double _distanceOfView;
    private int _nbElementsInBag;
    private int _bagSize;
    private int _idNextAgentToGive;
    private double _viewDirection;
    private WarBrain _brain;
    private PerceptsGetter _perceptsGetter;
    private String _debugString;
    private Color _debugStringColor;
    private Shape debugShape;
    private List<WarMessage> thisTickMessages;

    public ControllableWarAgent(String firstActionToDo, InGameTeam inGameTeam, Hitbox hitbox, WarBrain brain, double distanceOfView, double angleOfView, int cost, int maxHealth, int bagSize, int armor) {
        super(firstActionToDo, inGameTeam, hitbox, cost, maxHealth, armor);

        _distanceOfView = distanceOfView;
        _angleOfView = angleOfView;
        _bagSize = bagSize;
        _nbElementsInBag = 0;
        _viewDirection = 180 * new Random().nextDouble();
        _debugString = "";
        _debugStringColor = Color.BLACK;

        _brain = brain;
        if (_brain instanceof AgentBrainImplementer)
            ((AgentBrainImplementer) _brain).setAgent(this);
        
        thisTickMessages = new ArrayList<>();
    }

    @Override
    protected void activate() {
        super.activate();
        _perceptsGetter = getTeam().getGame().getSettings().getPerceptsGetterNewInstance(this, getTeam().getGame());
        randomHeading();
        _brain.activate();
    }

    @Override
    protected void doBeforeEachTick() {
        super.doBeforeEachTick();
        
        // On indique au PerceptGetter qu'un nouveau tick est passé
        _perceptsGetter.setPerceptsOutdated();
        
        // On met à jour la "boite aux lettres"
        thisTickMessages = new ArrayList<>();
        Message msg;
        while ((msg = nextMessage()) != null) {
            if (msg instanceof WarKernelMessage) {
                WarMessage warMsg = new WarMessage((WarKernelMessage) msg, this);
                logger.log(Level.FINER, this.toString() + " received message from " + warMsg.getSenderID());
                logger.log(Level.FINEST, "This message is : " + warMsg.toString());
                thisTickMessages.add(warMsg);
            }
        }
    }

    @Override
    public String give() {
        logger.log(Level.FINEST, this.toString() + " giving...");
        if (getNbElementsInBag() > 0) { // Si l'agent courant a quelque chose à donner
            WarAgent agentToGive = getTeam().getAgentWithID(_idNextAgentToGive);
            if (agentToGive != null) { // Si agent existe
                if (getAverageDistanceFrom(agentToGive) <= MAX_DISTANCE_GIVE) { // Si il n'est pas trop loin
                    if (agentToGive instanceof ControllableWarAgent) { // Si c'est un ControllableWarAgent
                        if (((ControllableWarAgent) agentToGive).getBagSize() > ((ControllableWarAgent) agentToGive).getNbElementsInBag()) { // Si son sac a un emplacement vide
                            logger.log(Level.FINER, this.toString() + " give WarFood to " + agentToGive.toString());
                            ((ControllableWarAgent) agentToGive).addElementInBag();
                            _nbElementsInBag--;
                        }
                    }
                }
            }
        }
        return getBrain().action();
    }

    @Override
    public String eat() {
        logger.finest(this.toString() + " eating...");
        if (getNbElementsInBag() > 0) {
            _nbElementsInBag--;
            heal(WarFood.HEALTH_GIVEN);
            logger.finer(this.toString() + " eat.");
        }
        return getBrain().action();
    }

    @Override
    public String idle() {
        logger.finer(this.toString() + " idle.");
        return getBrain().action();
    }

    public void forcePerceptsUpdate() {
        _perceptsGetter.forcePerceptsUpdate();
    }

    public WarBrain getBrain() {
        return _brain;
    }

    public void setBrain(WarBrain brain) {
        _brain = brain;
    }

    @Override
    public ReturnCode sendMessage(int idAgent, String message, String... content) {
        logger.fine(this.toString() + " send message to id " + idAgent);
        logger.finer("This message is : [" + message + "] " + content);
        return sendMessage(getTeamName(), InGameTeam.DEFAULT_GROUP_NAME, "" + idAgent,
                new WarKernelMessage(this, message, content));
//        WarAgent agent = getTeam().getAgentWithID(idAgent);
//
//        if (agent != null) {
//
//            try {
//                //AgentAddress aa = agent.getAgentAddressIn(getTeamName(), InGameTeam.DEFAULT_GROUP_NAME, agent.getType().toString());
//                //System.out.println("computed aa : "+aa.toString());
//                //ReturnCode rc = sendMessage(aa, new WarKernelMessage(this, message, content));
//
//
//                System.err.println(rc.toString());
//                return rc;
//            }
//            catch(Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return ReturnCode.INVALID_AGENT_ADDRESS;
    }

    @Override
    public ReturnCode broadcastMessageToAgentType(WarAgentType agentType, String message, String... content) {
        logger.finer(this.toString() + " send message to default role agents : " + agentType);
        logger.finest("This message is : [" + message + "] " + content);
        return sendMessage(getTeamName(), InGameTeam.DEFAULT_GROUP_NAME, agentType.toString(),
                new WarKernelMessage(this, message, content));
    }

    @Override
    public ReturnCode broadcastMessage(String groupName, String roleName, String message, String... content) {
        logger.finer(this.toString() + " send message to agents from group " + groupName + " with role " + roleName);
        logger.finest("This message is : [" + message + "] " + content);
        return sendMessage(getTeamName(), groupName, roleName, new WarKernelMessage(this, message, content));
    }

    @Override
    public void broadcastMessageToGroup(String groupName, String message, String... content) {
        logger.finer(this.toString() + " send message to agents from group " + groupName);
        logger.finest("This message is : [" + message + "] " + content);
        for (String roleName : getExistingRoles(getTeam().getName(), groupName))
            sendMessage(getTeam().getName(), groupName,
                    roleName, new WarKernelMessage(this, message, content));
    }

    @Override
    public void broadcastMessageToAll(String message, String... content) {
        logger.finer(this.toString() + " send message to all his team.");
        logger.finest("This message is : [" + message + "] " + content);
        broadcastMessage(getTeamName(), InGameTeam.DEFAULT_GROUP_NAME, "broadcast", new WarKernelMessage(this, message, content));
    }

    @Override
    public ReturnCode reply(WarMessage warMessage, String message, String... content) {
        logger.log(Level.FINER, this.toString() + " send reply to " + warMessage.getSenderID());
        logger.log(Level.FINEST, "This message is : [" + message + "] " + content);
        return sendMessage(warMessage.getSenderID(), message, content);
    }

    @Override
    public List<WarMessage> getMessages()
    {    
        return thisTickMessages;
    }

    public double getAngleOfView() {
        return _angleOfView;
    }

    public double getDistanceOfView() {
        return _distanceOfView;
    }

    @Override
    public int getNbElementsInBag() {
        return _nbElementsInBag;
    }

    @Override
    public int getBagSize() {
        return _bagSize;
    }

    @Override
    public boolean isBagEmpty() {
        return getNbElementsInBag() == 0;
    }

    @Override
    public boolean isBagFull() {
        return getNbElementsInBag() == getBagSize();
    }

    public void addElementInBag() {
        _nbElementsInBag++;
    }

    @Override
    public void setIdNextAgentToGive(int idNextAgentToGive) {
        _idNextAgentToGive = idNextAgentToGive;
    }

    @Override
    public double getViewDirection() {
        return _viewDirection;
    }

    @Override
    public void setViewDirection(double newViewDirection) {
        this._viewDirection = newViewDirection;
    }

    @Override
    public List<WarAgentPercept> getPercepts() {
        return _perceptsGetter.getPercepts();
    }

    public List<WarAgentPercept> getPercepts(boolean ally) {
        return _perceptsGetter.getWarAgentsPercepts(ally);
    }

    @Override
    public List<WarAgentPercept> getPerceptsAllies() {
        return getPercepts(true);
    }

    @Override
    public List<WarAgentPercept> getPerceptsEnemies() {
        return getPercepts(false);
    }

    @Override
    public List<WarAgentPercept> getPerceptsResources() {
        return _perceptsGetter.getResourcesPercepts();
    }

    public List<WarAgentPercept> getPerceptsByAgentType(WarAgentType agentType, boolean ally) {
        return _perceptsGetter.getPerceptsByType(agentType, ally);
    }

    @Override
    public List<WarAgentPercept> getPerceptsAlliesByType(WarAgentType agentType) {
        return getPerceptsByAgentType(agentType, true);
    }

    @Override
    public List<WarAgentPercept> getPerceptsEnemiesByType(WarAgentType agentType) {
        return getPerceptsByAgentType(agentType, false);
    }

    @Override
    public List<WallPercept> getWallPercepts() {
        return _perceptsGetter.getWallsPercepts();
    }

    public Shape getPerceptionArea() {
        return _perceptsGetter.getPerceptionArea();
    }

    /**
     * Renvoi la position d'un agent qui n'est pas dans le percept mais qui est
     * vu par un autre agent allié. L'agent allié doit envoyer un message aux
     * unités qui vont pouvoir connaître la postion de l'agent ennemi.
     *
     * @param message Le message reçu par l'agent qui voit l'enemi dans son
     *                percept. Le message doit OBLIGATOIRETMENT contenir un tableau
     *                de DEUX string : un pour la distance et l'autre pour l'angle
     *                correspondant à la distance et l'angle que l'agent allié voit l'ennemi.
     * @return Coordonnee polaire de l'agent ennemi perçu indirectement
     */
    @Override
    public PolarCoordinates getIndirectPositionOfAgentWithMessage(WarMessage message) {
        if (message.getContent().length != 2) {
            System.out.println("ATTENTION vous devez envoyer un message avec des informations corrects dans getSecondPositionAgent");
            return null;
        }

        PolarCoordinates positionAllie = new PolarCoordinates(message.getDistance(), message.getAngle());
        PolarCoordinates positionEnnemi = new PolarCoordinates(Double.valueOf(message.getContent()[0]), Double.valueOf(message.getContent()[1]));

        CartesianCoordinates vecteurPositionAllie = positionAllie.toCartesian();
        CartesianCoordinates vecteurPositionEnemie = positionEnnemi.toCartesian();

        CartesianCoordinates positionfinal = new CartesianCoordinates(vecteurPositionAllie.getX() + vecteurPositionEnemie.getX(),
                vecteurPositionAllie.getY() + vecteurPositionEnemie.getY());

        PolarCoordinates polaireFinal = positionfinal.toPolar();

        return polaireFinal;
    }

    /**
     * Donne la position moyenne d'un groupe d'unite
     *
     * @param agentType le type d'agent dont on veut connaitre la position moyenne
     * @param ally      = true, annemy = false
     * @return Coordonnee polaire de la position moyenne du groupe ou null si aucune unite
     */
    @Override
    public PolarCoordinates getAveragePositionOfUnitInPercept(WarAgentType agentType, boolean ally) {
        List<WarAgentPercept> listPercept = this.getPerceptsByAgentType(agentType, ally);

        if (listPercept.size() == 0)
            return null;

        int enemyCount = listPercept.size();
        PolarCoordinates enemyPolarList[] = new PolarCoordinates[enemyCount];
        CartesianCoordinates enemyList[] = new CartesianCoordinates[enemyCount];

        double sumX = 0, sumY = 0;

        for (int i = 0; i < enemyCount; i++) {
            enemyList[i] = enemyPolarList[i].toCartesian();

            sumX += enemyList[i].getX();
            sumY += enemyList[i].getY();
        }

        CartesianCoordinates barycenter = new CartesianCoordinates(sumX / enemyCount, sumY / enemyCount);

        PolarCoordinates polarBarycenter = barycenter.toPolar();

        return polarBarycenter;
    }

    @Override
    public PolarCoordinates getTargetedAgentPosition(double angleToAlly, double distanceFromAlly, double angleFromAllyToTarget, double distanceBetweenAllyAndTarget) {
        return WarMathTools.addTwoPoints(new PolarCoordinates(distanceFromAlly, angleToAlly),
                new PolarCoordinates(distanceBetweenAllyAndTarget, angleFromAllyToTarget));
    }

    @Override
    public String getDebugString() {
        return _debugString;
    }

    @Override
    public void setDebugString(String debugString) {
        _debugString = debugString;
    }

    @Override
    public Color getDebugStringColor() {
        return _debugStringColor;
    }

    @Override
    public void setDebugStringColor(Color color) {
        _debugStringColor = color;
    }

    public Shape getDebugShape() {
        return debugShape;
    }

    public void setDebugShape(Shape debugShape) {
        this.debugShape = debugShape;
    }

    public void init(int health, int nbElementsInBag) {
        super.init(health);
        _nbElementsInBag = nbElementsInBag;
    }

    public String die() {
        kill();
        return getBrain().action();
    }


    @Override
    public List<String> myGroups() {
        Set<String> groups = getMyGroups(getTeamName());
        if (groups != null)
            return new ArrayList<>(groups);
        else
            return new ArrayList<>();

    }

    @Override
    public List<String> myRoles(String group) {
        Set<String> groups = getMyRoles(getTeamName(), group);
        if (groups != null)
            return new ArrayList<>(groups);
        else
            return new ArrayList<>();
    }


}
