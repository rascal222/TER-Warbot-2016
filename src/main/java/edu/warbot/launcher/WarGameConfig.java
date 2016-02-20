package edu.warbot.launcher;

import edu.warbot.agents.Hitbox;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.InConePerceptsGetter;
import edu.warbot.agents.percepts.PerceptsGetter;
import edu.warbot.tools.WarMathTools;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.PolarCoordinates;
import org.yaml.snakeyaml.Yaml;

import javax.swing.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("unchecked")
public class WarGameConfig {

    public static final String AGENT_CONFIG_ANGLE_OF_VIEW = "AngleOfView";
    public static final String AGENT_CONFIG_DISTANCE_OF_VIEW = "DistanceOfView";
    public static final String AGENT_CONFIG_COST = "Cost";
    public static final String AGENT_CONFIG_MAX_HEALTH = "MaxHealth";
    public static final String AGENT_CONFIG_BAG_SIZE = "BagSize";
    public static final String AGENT_CONFIG_SPEED = "Speed";
    public static final String AGENT_CONFIG_TICKS_TO_RELOAD = "TicksToReload";
    public static final String AGENT_CONFIG_MAX_REPAIRS_PER_TICK = "MaxRepairsPerTick";

    public static final String PROJECTILE_CONFIG_EXPLOSION_RADIUS = "ExplosionRadius";
    public static final String PROJECTILE_CONFIG_DAMAGE = "Damage";
    public static final String PROJECTILE_CONFIG_AUTONOMY = "Autonomy";

    public static final String RESOURCE_WARFOOD_CONFIG_HEALTH_GIVEN = "HealthGived";

    static private String gameConfigFilePath = "config" + File.separator + "warbot_settings.yml";
    static private Map<String, Object> config = null;

    static {
        try {
            InputStream input = new FileInputStream(new File(gameConfigFilePath));
            Yaml yaml = new Yaml();
            config = (Map<String, Object>) yaml.load(input);
        } catch (FileNotFoundException e) {
            InputStream input = UserPreferences.class.getClassLoader().getResourceAsStream
                    (gameConfigFilePath.replaceAll("\\\\", "/"));

            if (input != null) {
                //Fichier de configuration par défaut dans le jar
                Yaml yaml = new Yaml();
                config = (Map<String, Object>) yaml.load(input);
                try {
                    input.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null,
                        "Le fichier de configuration de Warbot est introuvable dans le jar.",
                        "Fichier manquant",
                        JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }

	public static Map<String, Object> getConfigOfWarAgent(WarAgentType agentType) {
        Map<String, Object> warAgentsConfigs = (Map<String, Object>) config.get("WarAgents");
        if (warAgentsConfigs.containsKey(agentType.getCategory().toString())) {
            Map<String, Object> typeWarAgentConfigs = (Map<String, Object>) warAgentsConfigs.get(agentType.getCategory().toString());
            if (typeWarAgentConfigs.containsKey(agentType.toString())) {
                return (HashMap<String, Object>) typeWarAgentConfigs.get(agentType.toString());
            }
        }
        return new HashMap<>();
    }

    public static Hitbox getHitboxOfWarAgent(WarAgentType agentType) {
        Map<String, Object> warAgentConfig = getConfigOfWarAgent(agentType);
        if ((!warAgentConfig.isEmpty()) && warAgentConfig.containsKey("Hitbox"))
            return createHitboxFromData((Map<String, Object>) warAgentConfig.get("Hitbox"));
        else
            return new Hitbox(new Ellipse2D.Double(0, 0, 3, 3), 3, 3);
    }

    public static int getMaxDistanceTake() {
        if (config.containsKey("MaxDistanceTake"))
            return (int) config.get("MaxDistanceTake");
        else
            return 5;
    }

    public static int getMaxDistanceGive() {
        if (config.containsKey("MaxDistanceGive"))
            return (int) config.get("MaxDistanceGive");
        else
            return 5;
    }

    public static int getMaxDistanceBuild() {
        if (config.containsKey("MaxDistanceBuild"))
            return (int) config.get("MaxDistanceBuild");
        else
            return 5;
    }

    public static double getRepairsMultiplier() {
        if (config.containsKey("RepairsMultiplier"))
            return (int) config.get("RepairsMultiplier");
        else
            return 5;
    }

    public static Class<? extends PerceptsGetter> getDefaultPerception() {
        if (config.containsKey("Perception")) {
            String className = PerceptsGetter.class.getPackage().getName() + "." + config.get("Perception");
            try {
                Class<? extends PerceptsGetter> perceptGetter = (Class<? extends PerceptsGetter>) Class.forName(className);
                return perceptGetter;
            } catch (IllegalArgumentException | SecurityException | ClassNotFoundException e) {
                System.err.println("Nom de classe invalide pour \"" + className + "\". InRadiusPerceptsGetter pris par défaut.");
            }
        }
        return InConePerceptsGetter.class;
    }

    private static Hitbox createHitboxFromData(Map<String, Object> shapeData) {
        Hitbox hitbox = null;
        double radius;
        CartesianCoordinates position, leftPosition, rightPosition, firstPosition, centerPosition;
        switch ((String) shapeData.get("Shape")) {
            case "Square":
                double sideLength = (double) shapeData.get("SideLength");
                hitbox = new Hitbox(new Rectangle2D.Double(0, 0, sideLength, sideLength), sideLength, sideLength);
                break;
            case "Rectangle":
                double height = (double) shapeData.get("Height");
                double width = (double) shapeData.get("Width");
                hitbox = new Hitbox(new Rectangle2D.Double(0, 0, width, height), width, height);
                break;
            case "Circle":
                radius = (double) shapeData.get("Radius");
                hitbox = new Hitbox(new Ellipse2D.Double(0, 0, radius * 2., radius * 2.), radius * 2., radius * 2.);
                break;
            case "Triangle":
                radius = (double) shapeData.get("Radius");
                Path2D.Double triangle = new Path2D.Double();
                centerPosition = new CartesianCoordinates(radius, radius);
                firstPosition = WarMathTools.addTwoPoints(centerPosition, new PolarCoordinates(radius, 0));
                triangle.moveTo(firstPosition.getX(), firstPosition.getY());
                leftPosition = WarMathTools.addTwoPoints(centerPosition, new PolarCoordinates(radius, 220));
                triangle.lineTo(leftPosition.getX(), leftPosition.getY());
                rightPosition = WarMathTools.addTwoPoints(centerPosition, new PolarCoordinates(radius, 140));
                triangle.lineTo(rightPosition.getX(), rightPosition.getY());
                triangle.lineTo(firstPosition.getX(), firstPosition.getY());
                hitbox = new Hitbox(triangle, rightPosition.getX() - leftPosition.getX(), rightPosition.getY() - firstPosition.getY());
                break;
            case "Diamond":
                radius = (double) shapeData.get("Radius");
                Path2D.Double diamond = new Path2D.Double();
                centerPosition = new CartesianCoordinates(radius, radius);
                firstPosition = WarMathTools.addTwoPoints(centerPosition, new PolarCoordinates(radius, 270));
                diamond.moveTo(firstPosition.getX(), firstPosition.getY());
                position = WarMathTools.addTwoPoints(centerPosition, new PolarCoordinates(radius, 0));
                diamond.lineTo(position.getX(), position.getY());
                position = WarMathTools.addTwoPoints(centerPosition, new PolarCoordinates(radius, 90));
                diamond.lineTo(position.getX(), position.getY());
                position = WarMathTools.addTwoPoints(centerPosition, new PolarCoordinates(radius, 180));
                diamond.lineTo(position.getX(), position.getY());
                diamond.lineTo(firstPosition.getX(), firstPosition.getY());
                hitbox = new Hitbox(diamond, radius * 2., radius * 2.);
                break;
            case "Arrow":
                radius = (double) shapeData.get("Radius");
                Path2D.Double arrow = new Path2D.Double();
                centerPosition = new CartesianCoordinates(radius, radius);
                firstPosition = WarMathTools.addTwoPoints(centerPosition, new PolarCoordinates(radius, 0));
                arrow.moveTo(firstPosition.getX(), firstPosition.getY());
                leftPosition = WarMathTools.addTwoPoints(centerPosition, new PolarCoordinates(radius, 220));
                arrow.lineTo(leftPosition.getX(), leftPosition.getY());
                arrow.lineTo(centerPosition.getX(), centerPosition.getY());
                rightPosition = WarMathTools.addTwoPoints(centerPosition, new PolarCoordinates(radius, 140));
                arrow.lineTo(rightPosition.getX(), rightPosition.getY());
                arrow.lineTo(firstPosition.getX(), firstPosition.getY());
                hitbox = new Hitbox(arrow, rightPosition.getX() - leftPosition.getX(), rightPosition.getY() - firstPosition.getY());
                break;
        }
        return hitbox;
    }
}
