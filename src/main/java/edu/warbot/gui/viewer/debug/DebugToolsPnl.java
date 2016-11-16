package edu.warbot.gui.viewer.debug;

import edu.warbot.agents.WarAgent;
import edu.warbot.agents.enums.WarAgentCategory;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.InGameTeam;
import edu.warbot.gui.viewer.debug.eventlisteners.AddToolMouseListener;
import edu.warbot.gui.viewer.debug.eventlisteners.DeleteToolMouseListener;
import edu.warbot.gui.viewer.debug.eventlisteners.InfosToolMouseListener;
import edu.warbot.gui.viewer.debug.eventlisteners.MoveToolMouseListener;
import edu.warbot.gui.viewer.debug.infos.WarAgentInformationsPnl;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

@SuppressWarnings("serial")
public class DebugToolsPnl extends JPanel {

    private DebugModePanel _debugToolBar;
    private WarAgent _selectedAgent;

    private WarAgentType _selectedWarAgentTypeToCreate;
    private InGameTeam selectedInGameTeamForNextCreatedAgent;

    private DebugToolOnOffBtn _infoTool;
    private DebugToolOnOffBtn _addTool;
    private DebugToolOnOffBtn _deleteTool;
    private DebugToolOnOffBtn _moveTool;

    private JPanel pnlCenter;
    private JPanel _pnlSelectAgentAndTeam;
    private WarAgentInformationsPnl _agentInfosPnl;

    public DebugToolsPnl(DebugModePanel debugToolBar) {
        super();
        _selectedWarAgentTypeToCreate = null;
        _debugToolBar = debugToolBar;
        setLayout(new BorderLayout());

        JPanel pnlBattlegroundTools = new JPanel();
        ButtonGroup groupBattleGroundTools = new ButtonGroup();

        _infoTool = new DebugToolOnOffBtn("infos.png", "infos_s.png",
                "Obtenir des informations sur un agent",
                'i', _debugToolBar, new InfosToolMouseListener(this));
        pnlBattlegroundTools.add(_infoTool);
        groupBattleGroundTools.add(_infoTool);

        _addTool = new DebugToolOnOffBtn("add.png", "add_s.png",
                "Ajouter un agent sur le champ de bataille",
                'a', _debugToolBar, new AddToolMouseListener(_debugToolBar, this));
        pnlBattlegroundTools.add(_addTool);
        groupBattleGroundTools.add(_addTool);

        _deleteTool = new DebugToolOnOffBtn("delete.png", "delete_s.png",
                "Suprimer un agent du champ de bataille",
                'd', _debugToolBar, new DeleteToolMouseListener(_debugToolBar));
        pnlBattlegroundTools.add(_deleteTool);
        groupBattleGroundTools.add(_deleteTool);

        _moveTool = new DebugToolOnOffBtn("move.png", "move_s.png",
                "Déplacer un agent du champ de bataille",
                'm', _debugToolBar, new MoveToolMouseListener(_debugToolBar));
        pnlBattlegroundTools.add(_moveTool);
        groupBattleGroundTools.add(_moveTool);

        add(pnlBattlegroundTools, BorderLayout.NORTH);


        _pnlSelectAgentAndTeam = new JPanel(new BorderLayout());
        JPanel pnlSelectAgent = new JPanel();
        pnlSelectAgent.setLayout(new FlowLayout());
        pnlSelectAgent.setPreferredSize(new Dimension(300, 300));
        ButtonGroup groupSelectAgentToCreate = new ButtonGroup();
        WarAgentType[] typesWhichCanBeCreate = WarAgentType.getAgentsOfCategories(WarAgentCategory.Building,
                WarAgentCategory.Soldier, WarAgentCategory.Worker, WarAgentCategory.Resource);
        for (WarAgentType type : typesWhichCanBeCreate) {
            WarAgentSelectButton btn = new WarAgentSelectButton(type, _debugToolBar);
            pnlSelectAgent.add(btn);
            groupSelectAgentToCreate.add(btn);
        }
        _pnlSelectAgentAndTeam.add(pnlSelectAgent, BorderLayout.CENTER);
        JPanel pnlSelectTeam = new JPanel();
        pnlSelectTeam.setLayout(new FlowLayout());
        ButtonGroup groupSelectTeam = new ButtonGroup();
        for (InGameTeam inGameTeam : _debugToolBar.getViewer().getGame().getPlayerTeams()) {
            WarTeamSelectButton teamSelectButton = new WarTeamSelectButton(inGameTeam, _debugToolBar);
            pnlSelectTeam.add(teamSelectButton);
            groupSelectTeam.add(teamSelectButton);
        }
        _pnlSelectAgentAndTeam.add(pnlSelectTeam, BorderLayout.NORTH);

        _agentInfosPnl = new WarAgentInformationsPnl(this);

        pnlCenter = new JPanel();
        add(pnlCenter, BorderLayout.CENTER);

        loadEvents();
    }

    private void loadEvents() {
        _addTool.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (_addTool.isSelected()) {
                    pnlCenter.add(_pnlSelectAgentAndTeam, BorderLayout.CENTER);
                } else {
                    pnlCenter.remove(_pnlSelectAgentAndTeam);
                }
                revalidate();
                repaint();
            }
        });
        _infoTool.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (_infoTool.isSelected()) {
                    pnlCenter.add(_agentInfosPnl, BorderLayout.CENTER);
                } else {
                    pnlCenter.remove(_agentInfosPnl);
                }
                revalidate();
                repaint();
            }
        });

    }

    public WarAgentType getSelectedWarAgentTypeToCreate() {
        return _selectedWarAgentTypeToCreate;
    }

    public void setSelectedWarAgentTypeToCreate(WarAgentType agentType) {
        _selectedWarAgentTypeToCreate = agentType;
    }

    public InGameTeam getSelectedInGameTeamForNextCreatedAgent() {
        return selectedInGameTeamForNextCreatedAgent;
    }

    public void setSelectedInGameTeamForNextCreatedAgent(InGameTeam selectedInGameTeamForNextCreatedAgent) {
        this.selectedInGameTeamForNextCreatedAgent = selectedInGameTeamForNextCreatedAgent;
    }

    public DebugToolOnOffBtn getInfoToolBtn() {
        return _infoTool;
    }

    public WarAgentInformationsPnl getAgentInformationsPanel() {
        return _agentInfosPnl;
    }

    public WarAgent getSelectedAgent() {
        return _selectedAgent;
    }

    public void setSelectedAgent(WarAgent agent) {
        if (_selectedAgent != agent) {
            _selectedAgent = agent;
            _agentInfosPnl.update();

            _debugToolBar.getViewer().getFrame().repaint();
        }
    }

    public DebugModePanel getDebugToolBar() {
        return _debugToolBar;
    }
}
