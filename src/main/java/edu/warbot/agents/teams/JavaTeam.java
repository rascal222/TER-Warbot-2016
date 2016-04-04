package edu.warbot.agents.teams;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.agents.WarBase;
import edu.warbot.agents.buildings.Wall;
import edu.warbot.agents.enums.WarAgentCategory;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.GhostBrain;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Builder;
import edu.warbot.brains.capacities.Building;
import edu.warbot.brains.capacities.Creator;
import edu.warbot.game.InGameTeam;
import edu.warbot.tools.WarMathTools;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.PolarCoordinates;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by beugnon on 17/06/15.
 *
 * @since 3.2.3
 */
public class JavaTeam extends Team {


    /**
     * @param teamName    Le nom d'une équipe
     * @param description La description d'une équipe
     * @param imageIcon   Le logo d'une équipe
     * @param brains      les classes héritant de WarBrain utilisées pour les différents agents
     */
    public JavaTeam(String teamName, String description, ImageIcon imageIcon, Map<WarAgentType, Class<? extends WarBrain>> brains) {
        super(teamName, description, imageIcon, brains);
    }

    @Override
    public ControllableWarAgent instantiateControllableWarAgent(InGameTeam inGameTeam, WarAgentType agentName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        String agentToCreateClassName = WarBase.class.getPackage().getName() + "." + agentName;
        Class<? extends WarBrain> brainClass = getBrainOf(agentName);

        WarBrain brain;
        if (brainClass == null)
            brain = new GhostBrain("Not defined brain");
        else
            try {
                brain = brainClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                brain = new GhostBrain("Erreur dans le cerveau");
            }
        ControllableWarAgent a = (ControllableWarAgent) Class.forName(agentToCreateClassName)
                .getConstructor(InGameTeam.class, WarBrain.class)
                .newInstance(inGameTeam, brain);
        return a;
    }

    @Override
    public AliveWarAgent instantiateBuilding(InGameTeam inGameTeam, WarAgentType buildingName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        String buildingToCreateClassName = Wall.class.getPackage().getName() + "." + buildingName;
        AliveWarAgent building = (AliveWarAgent) Class.forName(buildingToCreateClassName)
                .getConstructor(InGameTeam.class)
                .newInstance(inGameTeam);
        return building;
    }

    @Override
    public void createUnit(InGameTeam inGameTeam, Creator creatorAgent, WarAgentType agentTypeToCreate) {
        Logger logger = ((AliveWarAgent) creatorAgent).getLogger();

        //L'énumération n'est pas de la catégorie Building
        if (!(agentTypeToCreate.getCategory().equals(WarAgentCategory.Worker)
                || agentTypeToCreate.getCategory().equals(WarAgentCategory.Soldier))) {
            return;
        }
        try {
            logger.log(Level.FINEST, creatorAgent.toString() + " creating " + agentTypeToCreate);
            if (creatorAgent.isAbleToCreate(agentTypeToCreate)) {

                ControllableWarAgent a = instantiateControllableWarAgent(inGameTeam, agentTypeToCreate);

                if (a.getCost() < ((AliveWarAgent) creatorAgent).getHealth()) {
                    associateBrain(a);
                    ((AliveWarAgent) creatorAgent).launchAgent(a);
                    a.setPositionAroundOtherAgent(((AliveWarAgent) creatorAgent));
                    ((AliveWarAgent) creatorAgent).damage(a.getCost());
                    //AddBrainHere
                    logger.log(Level.FINER, creatorAgent.toString() + " create " + agentTypeToCreate);
                } else {
                    logger.log(Level.FINER, creatorAgent.toString() + " can't create " + agentTypeToCreate + " : not enough health !");
                }
            } else {
                logger.log(Level.FINER, creatorAgent.toString() + " isn't able to create " + agentTypeToCreate);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'instanciation du brainController de l'agent " + agentTypeToCreate.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void build(InGameTeam inGameTeam, Builder builderAgent, WarAgentType buildingTypeToBuild) {
        Logger logger = ((AliveWarAgent) builderAgent).getLogger();
        //L'énumération n'est pas de la catégorie Building
        if (!buildingTypeToBuild.getCategory().equals(WarAgentCategory.Building)) {
            return;
        }

        try {
            logger.log(Level.FINEST, builderAgent.toString() + " building " + buildingTypeToBuild);
            if (builderAgent.isAbleToBuild(buildingTypeToBuild)) {
            	AliveWarAgent building;
            	if(buildingTypeToBuild.isControllable())
            		building = instantiateControllableWarAgent(inGameTeam, buildingTypeToBuild);
            	else
            		building = instantiateBuilding(inGameTeam, buildingTypeToBuild);
                if (building.getCost() < ((AliveWarAgent) builderAgent).getHealth()) {
                    ((AliveWarAgent) builderAgent).launchAgent(building);

                    // Position
                    CartesianCoordinates newBuildingPosition = WarMathTools.addTwoPoints(((AliveWarAgent) builderAgent).getPosition(), new PolarCoordinates(Building.MAX_DISTANCE_BUILD, ((AliveWarAgent) builderAgent).getHeading()));
                    for (WarAgent agent : inGameTeam.getAllAgentsInRadiusOf(building, building.getHitboxMaxRadius())) {
                        agent.moveOutOfCollision();
                    }
                    building.setPosition(newBuildingPosition);
                    building.setHeading(((AliveWarAgent) builderAgent).getHeading());

                    // Cost
                    ((AliveWarAgent) builderAgent).damage(building.getCost());
                    logger.log(Level.FINER, builderAgent.toString() + " built " + buildingTypeToBuild);
                } else {
                    logger.log(Level.FINER, builderAgent.toString() + " can't build " + buildingTypeToBuild + " : not enough health !");
                }
            } else {
                logger.log(Level.FINER, builderAgent.toString() + " can't build " + buildingTypeToBuild);
            }
        } catch (Exception e) {
            System.err.println("Erreur lors de l'instanciation du brainController de l'agent " + buildingTypeToBuild.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void associateBrain(ControllableWarAgent agent) {

    }

    @Override
    public Team duplicate(String newName) {
        return new JavaTeam(newName, getDescription(), getLogo(), getBrains());
    }


}
