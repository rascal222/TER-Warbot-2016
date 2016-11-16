package edu.warbot.brains.implementations;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WallPercept;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.WarBrain;
import edu.warbot.communications.WarMessage;
import edu.warbot.tools.geometry.PolarCoordinates;
import madkit.kernel.AbstractAgent;

import java.awt.*;
import java.util.List;

public class WarBrainImplementation extends WarBrain implements AgentBrainImplementer {

    private ControllableWarAgent agent;

    protected ControllableWarAgent getAgent() {
        return agent;
    }

    @Override
    public void setAgent(ControllableWarAgent agent) {
        this.agent = agent;
    }

    @Override
    public AbstractAgent.ReturnCode sendMessage(int idAgent, String message, String... content) {
        return getAgent().sendMessage(idAgent, message, content);
    }

    @Override
    public void broadcastMessageToAll(String message, String... content) {
        getAgent().broadcastMessageToAll(message, content);
    }

    @Override
    public AbstractAgent.ReturnCode broadcastMessageToAgentType(WarAgentType agentType, String message, String... content) {
        return getAgent().broadcastMessageToAgentType(agentType, message, content);
    }

    @Override
    public AbstractAgent.ReturnCode broadcastMessage(String groupName, String roleName, String message, String... content) {
        return getAgent().broadcastMessage(groupName, roleName, message, content);
    }

    @Override
    public void broadcastMessageToGroup(String groupName, String message, String... content) {
        getAgent().broadcastMessageToGroup(groupName, message, content);
    }

    @Override
    public AbstractAgent.ReturnCode reply(WarMessage warMessage, String message, String... content) {
        return getAgent().reply(warMessage, message, content);
    }

    @Override
    public List<WarMessage> getMessages() {
        return getAgent().getMessages();
    }

    @Override
    public void setIdNextAgentToGive(int idNextAgentToGive) {
        getAgent().setIdNextAgentToGive(idNextAgentToGive);
    }

    @Override
    public int getBagSize() {
        return getAgent().getBagSize();
    }

    @Override
    public int getNbElementsInBag() {
        return getAgent().getNbElementsInBag();
    }

    @Override
    public boolean isBagEmpty() {
        return getAgent().isBagEmpty();
    }

    @Override
    public boolean isBagFull() {
        return getAgent().isBagFull();
    }

    @Override
    public double getViewDirection() {
        return getAgent().getViewDirection();
    }

    @Override
    public void setViewDirection(double viewDirection) {
        getAgent().setViewDirection(viewDirection);
    }

    @Override
    public List<WarAgentPercept> getPerceptsAllies() {
        return getAgent().getPerceptsAllies();
    }

    @Override
    public List<WarAgentPercept> getPerceptsEnemies() {
        return getAgent().getPerceptsEnemies();
    }

    @Override
    public List<WarAgentPercept> getPerceptsResources() {
        return getAgent().getPerceptsResources();
    }

    @Override
    public List<WarAgentPercept> getPerceptsAlliesByType(WarAgentType agentType) {
        return getAgent().getPerceptsAlliesByType(agentType);
    }

    @Override
    public List<WarAgentPercept> getPerceptsEnemiesByType(WarAgentType agentType) {
        return getAgent().getPerceptsEnemiesByType(agentType);
    }

    @Override
    public List<WarAgentPercept> getPercepts() {
        return getAgent().getPercepts();
    }

    @Override
    public List<WallPercept> getWallPercepts() {
        return getAgent().getWallPercepts();
    }

    @Override
    public String getDebugString() {
        return getAgent().getDebugString();
    }

    @Override
    public void setDebugString(String debugString) {
        getAgent().setDebugString(debugString);
    }

    @Override
    public Color getDebugStringColor() {
        return getAgent().getDebugStringColor();
    }

    @Override
    public void setDebugStringColor(Color color) {
        getAgent().setDebugStringColor(color);
    }

    @Override
    public Shape getDebugShape() {
        return getAgent().getDebugShape();
    }

    @Override
    public void setDebugShape(Shape debugShape) {
        getAgent().setDebugShape(debugShape);
    }

    @Override
    public PolarCoordinates getAveragePositionOfUnitInPercept(WarAgentType agentType, boolean ally) {
        return getAgent().getAveragePositionOfUnitInPercept(agentType, ally);
    }

    @Override
    public PolarCoordinates getIndirectPositionOfAgentWithMessage(WarMessage message) {
        return getAgent().getIndirectPositionOfAgentWithMessage(message);
    }

    @Override
    public PolarCoordinates getTargetedAgentPosition(double angleToAlly, double distanceFromAlly, double angleFromAllyToTarget, double distanceBetweenAllyAndTarget) {
        return getAgent().getTargetedAgentPosition(angleToAlly, distanceFromAlly, angleFromAllyToTarget, distanceBetweenAllyAndTarget);
    }

    @Override
    public List<String> myGroups() {
        return getAgent().myGroups();
    }


    @Override
    public List<String> myRoles(String group) {
        return getAgent().myRoles(group);
    }

    @Override
    public int getHealth() {
        return getAgent().getHealth();
    }

    @Override
    public int getMaxHealth() {
        return getAgent().getMaxHealth();
    }

    @Override
    public AbstractAgent.ReturnCode requestRole(String group, String role) {
        return getAgent().requestRole(group, role);
    }

    @Override
    public AbstractAgent.ReturnCode leaveRole(String group, String role) {
        return getAgent().leaveRole(group, role);
    }

    @Override
    public AbstractAgent.ReturnCode leaveGroup(String group) {
        return getAgent().leaveGroup(group);
    }

    @Override
    public int getNumberOfAgentsInRole(String group, String role) {
        return getAgent().getNumberOfAgentsInRole(group, role);
    }

    @Override
    public double getHeading() {
        return getAgent().getHeading();
    }

    @Override
    public void setHeading(double angle) {
        getAgent().setHeading(angle);
    }

    @Override
    public void setRandomHeading() {
        getAgent().setRandomHeading();
    }

    @Override
    public void setRandomHeading(int range) {
        getAgent().setRandomHeading(range);
    }

    @Override
    public String getTeamName() {
        return getAgent().getTeamName();
    }

    @Override
    public boolean isEnemy(WarAgentPercept percept) {
        return getAgent().isEnemy(percept);
    }

    @Override
    public int getID() {
        return getAgent().getID();
    }


}
