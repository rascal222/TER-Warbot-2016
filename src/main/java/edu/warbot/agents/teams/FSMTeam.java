package edu.warbot.agents.teams;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.WarFSMBrain;
import edu.warbot.fsm.editor.models.Model;
import edu.warbot.game.InGameTeam;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 *
 */
public final class FSMTeam extends JavaTeam {

    /**
     * Cerveau basique pour agent basé sur un cerveau FSM
     */
    private static Map<WarAgentType, Class<? extends WarBrain>> brains;

    static {
        //Prepare standard classes for agent
        brains = new HashMap<>();
        brains.put(WarAgentType.WarExplorer, WarFSMBrain.class);
        brains.put(WarAgentType.WarRocketLauncher, WarFSMBrain.class);
        brains.put(WarAgentType.WarEngineer, WarFSMBrain.class);
        brains.put(WarAgentType.WarBase, WarFSMBrain.class);
        brains.put(WarAgentType.WarKamikaze, WarFSMBrain.class);
        brains.put(WarAgentType.WarTurret, WarFSMBrain.class);
    }

    @SuppressWarnings("unused")
	private Model fsmModel;

    /**
     * @param teamName    Le nom d'une équipe
     * @param description La description d'une équipe
     * @param imageIcon   Le logo d'une équipe
     */
    public FSMTeam(String teamName, String description, ImageIcon imageIcon, Model model) {
        super(teamName, description, imageIcon, brains);
        this.fsmModel = model;
    }


    @SuppressWarnings("unused")
	@Override
    public ControllableWarAgent instantiateControllableWarAgent(InGameTeam inGameTeam, WarAgentType agentName) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        ControllableWarAgent a = super.instantiateControllableWarAgent(inGameTeam, agentName);
        //TODO Include specificities for WarFSMBrain
        WarFSMBrain brain = (WarFSMBrain) a.getBrain();
        return a;
    }

}