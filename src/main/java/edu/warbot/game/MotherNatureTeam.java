package edu.warbot.game;

import edu.warbot.agents.WarAgent;
import edu.warbot.agents.WarResource;
import edu.warbot.agents.enums.WarAgentCategory;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.resources.WarFood;
import edu.warbot.agents.teams.JavaTeam;
import edu.warbot.maps.AbstractWarMap;
import edu.warbot.tools.WarMathTools;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.PolarCoordinates;
import edu.warbot.tools.geometry.WarCircle;
import madkit.kernel.Agent;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MotherNatureTeam extends InGameTeam {

    public static final String NAME = "Mère nature";
    public static final Color COLOR = Color.GREEN;

    private ArrayList<WarResource> resources;

    public MotherNatureTeam(WarGame game) {
        super(new JavaTeam(NAME, "", null, null));
        resources = new ArrayList<>();
        setColor(COLOR);
        setGame(game);
    }

    @Override
    public void addWarAgent(WarAgent agent) {
        if (agent instanceof WarResource)
            resources.add((WarResource) agent);
        else
            super.addWarAgent(agent);
    }

    public ArrayList<WarResource> getResources() {
        return resources;
    }

    @Override
    public void removeWarAgent(WarAgent agent) {
        if (agent instanceof WarResource)
            resources.remove(agent);
        else
            super.removeWarAgent(agent);
    }

    public ArrayList<WarResource> getAccessibleResourcesFor(WarAgent referenceAgent) {
        ArrayList<WarResource> toReturn = new ArrayList<>();
        for (WarResource a : resources) {
            if (referenceAgent.getAverageDistanceFrom(a) <= WarResource.MAX_DISTANCE_TAKE) {
                toReturn.add(a);
            }
        }
        return toReturn;
    }

    @Override
    public List<WarAgent> getAllAgents() {
        List<WarAgent> toReturn = super.getAllAgents();
        toReturn.addAll(resources);
        return toReturn;
    }

    public void createAndLaunchResource(AbstractWarMap map, Agent launcher, WarAgentType resourceType) {
        if (resourceType.getCategory() == WarAgentCategory.Resource) {
            try {
                WarResource resource = instantiateNewWarResource(resourceType.toString());
                launcher.launchAgent(resource);
                List<WarCircle> foodPositions = map.getFoodPositions();

                CartesianCoordinates newPos = WarMathTools.addTwoPoints(
                        foodPositions.get(new Random().nextInt(foodPositions.size())).getCenterPosition(),
                        PolarCoordinates.getRandomInBounds(AbstractWarMap.FOOD_POSITION_RADIUS).toCartesian());

                newPos.normalize(0, map.getWidth(), 0, map.getHeight());
                resource.setPosition(newPos);
                resource.moveOutOfCollision();
            } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                System.err.println("Erreur lors de la création d'une nouvelle ressource : " + resourceType);
                e.printStackTrace();
            }
        } else {
            System.err.println(resourceType + " n'est pas une resource.");
        }
    }

    public WarResource instantiateNewWarResource(String agentName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        String resourceToCreateClassName = WarFood.class.getPackage().getName() + "." + agentName;
        WarResource a = (WarResource) Class.forName(resourceToCreateClassName).getConstructor(InGameTeam.class).newInstance(this);

//		a.setLogLevel(getGame().getSettings().getLogLevel());

        return a;
    }


}
