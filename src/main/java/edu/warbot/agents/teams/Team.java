package edu.warbot.agents.teams;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Builder;
import edu.warbot.brains.capacities.Creator;
import edu.warbot.game.InGameTeam;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 *
 *
 */
public abstract class Team {

    /**
     * Nom de l'équipe
     */
    private String teamName;

    /**
     * Description de l'équipe
     */
    private String description;

    /**
     * Logo de l'équipe
     */
    private ImageIcon imageIcon;
    private Map<WarAgentType, Class<? extends WarBrain>> brains;
    
    /**
     * IA/Player
     * Tags permettant de considérer la team comme une team IA, ou player. 
     * Sert a concevoir les listes de teams des modes de jeux (Player vs IA, Player vs Player, etc...)
     */
    private boolean isIA;
    private boolean isPlayer;
    

    /**
     * @param teamName    Le nom d'une équipe
     * @param description La description d'une équipe
     * @param imageIcon   Le logo d'une équipe
     * @param brains      les classes héritant de WarBrain utilisées pour les différents agents
     */
    public Team(String teamName, String description, ImageIcon imageIcon, Map<WarAgentType, Class<? extends WarBrain>> brains) {
        super();
        this.teamName = teamName;
        this.description = description;
        this.imageIcon = imageIcon;
        this.brains = brains;
    }

    /**
     * @return le nom de l'équipe
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * @return la description de l'équipe
     */
    public String getDescription() {
        return description;
    }

    /**
     * @return le logo de l'équipe
     */
    public ImageIcon getLogo() {
        return imageIcon;
    }

    public Map<WarAgentType, Class<? extends WarBrain>> getBrains() {
        return brains;
    }

    public Class<? extends WarBrain> getBrainOf(WarAgentType warAgentType) {
        return brains.get(warAgentType);
    }
    
    public void setIA(boolean ia) {
    	this.isIA = ia;
    }
    
    public boolean isIA() {
    	//return this.isIA;
    	return true;
    }
    
    public void setPlayer(boolean player) {
    	this.isPlayer = player;
    }
    
    public boolean isPlayer() {
    	//return this.isPlayer;
    	return true;
    }

    /**
     * Créer une instance d'agent contrôlable
     *
     * @param inGameTeam
     * @param agentName  le nom de l'agent à créer
     * @return une instance d'agent contrôlable
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws ClassNotFoundException
     */
    public abstract ControllableWarAgent instantiateControllableWarAgent(InGameTeam inGameTeam, WarAgentType agentName)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException;

    /**
     * Créer une instance d'agent bâtiment
     *
     * @param inGameTeam
     * @param buildingName le nom de l'agent à construire
     * @return une instance d'agent de classe WarBuilding
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws ClassNotFoundException
     */
    public abstract AliveWarAgent instantiateBuilding(InGameTeam inGameTeam, WarAgentType buildingName)
            throws InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException;

    /**
     * Réalise la création d'une unité si son créateur a les moyens de la produire
     *
     * @param inGameTeam
     * @param creatorAgent      l'agent qui lance la création d'une unité
     * @param agentTypeToCreate le type d'unité souhaité (avec la catégorie différente de Building)
     */
    public abstract void createUnit(InGameTeam inGameTeam, Creator creatorAgent, WarAgentType agentTypeToCreate);

    /**
     * Réalise la création d'un bâtiment si son créateur a les moyens de le produire
     *
     * @param inGameTeam
     * @param builderAgent        l'agent qui lance la création d'un bâtiment
     * @param buildingTypeToBuild le type d'unité souhaité (avec la catégorie Building)
     */
    public abstract void build(InGameTeam inGameTeam, Builder builderAgent, WarAgentType buildingTypeToBuild);

    public abstract void associateBrain(ControllableWarAgent agent) throws IllegalAccessException, InstantiationException;
    /**
     * @param newName nouveau de l'équipe dupliquée
     * @return l'équipe dupliquée
     */
    public abstract Team duplicate(String newName);

}
