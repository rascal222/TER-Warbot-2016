package edu.warbot.game;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.brains.capacities.Building;
import edu.warbot.agents.WarProjectile;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.teams.Team;
import edu.warbot.brains.capacities.Builder;
import edu.warbot.brains.capacities.Creator;
import edu.warbot.exceptions.UnauthorizedAgentException;
import edu.warbot.game.listeners.TeamListener;
import edu.warbot.gui.launcher.WarLauncherInterface;
import edu.warbot.tools.WarMathTools;

import javax.swing.*;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class InGameTeam {

    public static final String DEFAULT_GROUP_NAME = "defaultGroup-Warbot";

    public static int MAX_DYING_STEP = 5;

    private List<TeamListener> listeners;

    private Team team;

    private Color color;

    private List<WarAgent> allAgents;
    private List<ControllableWarAgent> controllableAgents;
    private List<WarProjectile> projectiles;
    private List<AliveWarAgent> buildings;
    private Map<WarAgentType, Integer> nbUnitsLeft;
    private List<WarAgent> dyingAgents;

    private WarGame game;

    private boolean hasLost;

    public InGameTeam(Team team) {
        this(team,
                Color.WHITE,
                new ArrayList<WarAgent>(),
                new ArrayList<ControllableWarAgent>(),
                new ArrayList<WarProjectile>(),
                new ArrayList<AliveWarAgent>(),
                new HashMap<WarAgentType, Integer>(),
                new ArrayList<WarAgent>());

        for (WarAgentType type : WarAgentType.values())
            nbUnitsLeft.put(type, 0);
    }

    public InGameTeam(Team team, Color color,
    				  List<WarAgent> allAgent,
                      List<ControllableWarAgent> controllableAgents,
                      List<WarProjectile> projectiles,
                      List<AliveWarAgent> buildings,
                      Map<WarAgentType, Integer> nbUnitsLeft, List<WarAgent> dyingAgents) {

        this.team = team;
        this.color = color;//GAME DRIVED
        this.allAgents = allAgent;
        this.controllableAgents = controllableAgents;//GAME DRIVED
        this.projectiles = projectiles;//GAME DRIVED
        this.buildings = buildings;//GAME DRIVED
        this.nbUnitsLeft = nbUnitsLeft;//GAME DRIVED
        this.dyingAgents = dyingAgents;//GAME DRIVED

        listeners = new ArrayList<>();//GAME DRIVED
        hasLost = false;//GAME DRIVED
    }

    public static String getRealNameFromTeamName(String teamName) {
        if (teamName.endsWith(WarLauncherInterface.TEXT_ADDED_TO_DUPLICATE_TEAM_NAME))
            return teamName.substring(0, teamName.lastIndexOf(WarLauncherInterface.TEXT_ADDED_TO_DUPLICATE_TEAM_NAME));
        else
            return teamName;
    }

    public ImageIcon getImage() {
        return team.getLogo();
    }

    public String getName() {
        return team.getTeamName();
    }

    public String getDescription() {
        return team.getDescription();
    }

    public List<ControllableWarAgent> getControllableAgents() {
        return controllableAgents;
    }

    public List<WarProjectile> getProjectiles() {
        return projectiles;
    }

    public List<AliveWarAgent> getBuildings() {
        return buildings;
    }

    public void removeWarAgent(WarAgent agent) {
        WarAgentType type = WarAgentType.valueOf(agent.getClass().getSimpleName());
        nbUnitsLeft.put(type, nbUnitsLeft.get(type) - 1);
        
        allAgents.remove(agent);
        
        if (agent instanceof WarProjectile)
            projectiles.remove(agent);
        
        if (agent instanceof Building)
            buildings.remove(agent);
        
        if (agent instanceof ControllableWarAgent)
            controllableAgents.remove(agent);

        for (TeamListener listener : getListeners())
            listener.onAgentRemoved(agent);
    }

    public void setWarAgentAsDying(WarAgent agent) {
        removeWarAgent(agent);
        dyingAgents.add(agent);
    }

    public List<WarAgent> getAllAgents() {
        List<WarAgent> toReturn = new ArrayList<>(allAgents);
        return toReturn;
    }

    public void removeAllAgents() {
        controllableAgents.clear();
        projectiles.clear();
        buildings.clear();
        dyingAgents.clear();
        for (WarAgentType type : WarAgentType.values())
            nbUnitsLeft.put(type, 0);
    }

    /**
     * Retourne l'agent dont l'id est celui passé en paramètre
     *
     * @param id - id de l'agent à récupérer
     * @return null si aucun agent n'a été trouvé
     */
    public WarAgent getAgentWithID(int id) {
        for (WarAgent a : getAllAgents()) {
            if (a.getID() == id)
                return a;
        }
        return null;
    }

    @Override
    public boolean equals(Object team) {
        if (team instanceof InGameTeam)
            return this.getName().equals(((InGameTeam) team).getName());
        else if (team instanceof String)
            return this.getName().equals(team);
        else
            return false;
    }

    public ArrayList<WarAgent> getAllAgentsInRadiusOf(WarAgent referenceAgent, double radius) {
        ArrayList<WarAgent> toReturn = new ArrayList<>();
        for (WarAgent a : getAllAgents()) {
            if (referenceAgent.getMinDistanceFrom(a) <= radius) {
                toReturn.add(a);
            }
        }
        return toReturn;
    }

    public ArrayList<WarAgent> getAllAgentsInRadius(double posX, double posY, double radius) {
        ArrayList<WarAgent> toReturn = new ArrayList<>();
        for (WarAgent a : getAllAgents()) {
            if ((WarMathTools.getDistanceBetweenTwoPoints(posX, posY, a.getX(), a.getY()) - a.getHitboxMaxRadius()) <= radius) {
                toReturn.add(a);
            }
        }
        return toReturn;
    }

    public ArrayList<AliveWarAgent> getBuildingsInRadiusOf(WarAgent referenceAgent, double radius) {
        ArrayList<AliveWarAgent> toReturn = new ArrayList<>();
        for (AliveWarAgent building : buildings) {
            if (referenceAgent.getMinDistanceFrom(building) <= radius) {
                toReturn.add(building);
            }
        }
        return toReturn;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getNbUnitsLeftOfType(WarAgentType type) {
        return nbUnitsLeft.get(type);
    }

    public Map<WarAgentType, Integer> getAllNbUnitsLeft() {
        return nbUnitsLeft;
    }

    public void killAllAgents() {
        for (WarAgent a : getAllAgents())
            try {
                a.killAgent(a);
            } catch (Exception ignored) {

            }
    }

    public InGameTeam duplicate(String newName) {
        return new InGameTeam(team.duplicate(newName),
                ((this.getColor() == null) ? null : (new Color(this.getColor().getRGB()))),
                new ArrayList<>(this.getAllAgents()),
                new ArrayList<>(this.getControllableAgents()),
                new ArrayList<>(this.getProjectiles()),
                new ArrayList<>(this.getBuildings()),
                new HashMap<>(this.getAllNbUnitsLeft()),
                new ArrayList<>(this.getDyingAgents())
        );
    }

    public void doAfterEachTick() {
        for (int i = 0; i < dyingAgents.size(); i++) {
            WarAgent a = dyingAgents.get(i);
            if (a.getDyingStep() == 0)
                try {
                    a.killAgent(a);
                } catch (Exception e) {

                }
            a.incrementDyingStep();
            if (a.getDyingStep() > MAX_DYING_STEP) {
                dyingAgents.remove(i);
            }
        }
    }

    /**
     * @return une liste d'agents en train de mourir
     */
    public List<WarAgent> getDyingAgents() {
        return new ArrayList<>(dyingAgents);
    }

    /**
     *
     * @return l'instance de partie
     */
    public WarGame getGame() {
        return game;
    }

    public void setGame(WarGame game) {
        this.game = game;
    }

    public ControllableWarAgent instantiateNewControllableWarAgent(String agentName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, UnauthorizedAgentException {
    	WarAgentType agentType = WarAgentType.valueOf(agentName);

    	if (game.authorizedAgent(this, agentType)) {
    		return team.instantiateControllableWarAgent(this, agentType);
    	} else {
    		throw new UnauthorizedAgentException(this.getName(), agentType.name());
    	}    	
    }

    public AliveWarAgent instantiateNewBuilding(String buildingName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException, UnauthorizedAgentException {
    	WarAgentType agentType = WarAgentType.valueOf(buildingName);

    	if (game.authorizedAgent(this, agentType)) {
    		return team.instantiateBuilding(this, agentType);
    	} else {
    		throw new UnauthorizedAgentException(this.getName(), agentType.name());
    	}
    }

    public void createUnit(Creator creatorAgent, WarAgentType agentTypeToCreate) throws UnauthorizedAgentException {
    	if (game.authorizedAgent(this, agentTypeToCreate)) {
    		team.createUnit(this, creatorAgent, agentTypeToCreate);
    	} else {
    		throw new UnauthorizedAgentException(this.getName(), agentTypeToCreate.name());
    	}
    }

    public void build(Builder builderAgent, WarAgentType buildingTypeToBuild) throws UnauthorizedAgentException {
    	if (game.authorizedAgent(this, buildingTypeToBuild)) {
    		team.build(this, builderAgent, buildingTypeToBuild);
    	} else {
    		throw new UnauthorizedAgentException(this.getName(), buildingTypeToBuild.name());
    	}
    }

    public void addTeamListener(TeamListener teamListener) {
        listeners.add(teamListener);
    }

    public void removeTeamListener(TeamListener teamListener) {
        listeners.remove(teamListener);
    }

    private List<TeamListener> getListeners() {
        return listeners;
    }

    public void setHasLost(boolean hasLost) {
        this.hasLost = hasLost;
    }

    public boolean hasLost() {
        return hasLost;
    }

    public void addWarAgent(WarAgent agent) {
        WarAgentType type = WarAgentType.valueOf(agent.getClass().getSimpleName());
        nbUnitsLeft.put(type, nbUnitsLeft.get(type) + 1);
        
        allAgents.add(agent);
        
        if (agent instanceof WarProjectile)
            projectiles.add((WarProjectile) agent);
        
        if (agent instanceof Building)
            buildings.add((AliveWarAgent) agent);
        
        if (agent instanceof ControllableWarAgent)
            controllableAgents.add((ControllableWarAgent) agent);

        agent.getLogger().log(Level.FINEST, agent.toString() + " added to team " + this.getName());
        for (TeamListener listener : getListeners())
            listener.onAgentAdded(agent);
    }

    @Override
    public String toString() {
        return getName();
    }

    public Team getTeam() {
        return team;
    }
}
