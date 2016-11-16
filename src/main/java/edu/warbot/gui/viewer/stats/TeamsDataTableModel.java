package edu.warbot.gui.viewer.stats;

import edu.warbot.agents.WarAgent;
import edu.warbot.agents.enums.WarAgentCategory;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.game.listeners.TeamListener;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Observable;

@SuppressWarnings("serial")
public class TeamsDataTableModel extends AbstractTableModel implements TeamListener {

    private static final String[] COLUMN_NAMES_FOR_TEAM_IDENTIFICATION = {"Couleur", "Logo", "Nom"};
    private static final WarAgentType[] AGENT_TYPES_TO_FOLLOW = WarAgentType.getAgentsOfCategories(WarAgentCategory.Building, WarAgentCategory.Soldier, WarAgentCategory.Worker);
    private final int _nbCols;

    private WarGame game;
    private ArrayList<InGameTeam> playerInGameTeams;

    public TeamsDataTableModel(WarGame game) {
        super();
        this.game = game;
        this.playerInGameTeams = game.getPlayerTeams();
        for (InGameTeam inGameTeam : playerInGameTeams)
            inGameTeam.addTeamListener(this);

        _nbCols = COLUMN_NAMES_FOR_TEAM_IDENTIFICATION.length + AGENT_TYPES_TO_FOLLOW.length;

//		game.addObserver(this);
    }

    public int getNbColumnsForTeamIdentification() {
        return COLUMN_NAMES_FOR_TEAM_IDENTIFICATION.length; // + 1 pour la légende
    }

    @Override
    public int getRowCount() { // Return nb columns because inverted table
        return _nbCols;
    }

    @Override
    public int getColumnCount() { // Return nb rows because inverted table
        return game.getPlayerTeams().size() + 1;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (columnIndex == 0)
            return getRowName(rowIndex);
        else {
            ArrayList<InGameTeam> teams = game.getPlayerTeams();
            int teamPosInListTeams = columnIndex - 1;
            boolean isIndexOutOfBounds = teamPosInListTeams >= teams.size();
            if (isIndexOutOfBounds)
                return new Object();
            InGameTeam inGameTeam = playerInGameTeams.get(columnIndex - 1);
            if (!isIndexOutOfBounds)
                inGameTeam = teams.get(teamPosInListTeams);
            Object toReturn;
            switch (rowIndex) {
                case 0:
                    if (isIndexOutOfBounds)
                        toReturn = Color.WHITE;
                    else
                        toReturn = inGameTeam.getColor();
                    break;
                case 1:
                    if (isIndexOutOfBounds)
                        toReturn = new ImageIcon();
                    else if (inGameTeam.getImage() != null)
                        toReturn = new ImageIcon(inGameTeam.getImage().getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH));
                    else
                        toReturn = "";
                    break;
                case 2:
                    if (isIndexOutOfBounds)
                        toReturn = "";
                    else
                        toReturn = inGameTeam.getName();
                    break;
                default:
                    if (isIndexOutOfBounds)
                        toReturn = 0;
                    else
                        toReturn = inGameTeam.getNbUnitsLeftOfType(AGENT_TYPES_TO_FOLLOW[rowIndex - COLUMN_NAMES_FOR_TEAM_IDENTIFICATION.length]);
                    break;
            }

            return toReturn;
        }
    }

    private String getRowName(int rowIndex) {
        if (rowIndex < COLUMN_NAMES_FOR_TEAM_IDENTIFICATION.length) {
            return COLUMN_NAMES_FOR_TEAM_IDENTIFICATION[rowIndex];
        } else {
            return AGENT_TYPES_TO_FOLLOW[rowIndex - COLUMN_NAMES_FOR_TEAM_IDENTIFICATION.length].toString();
        }
    }


    public void update(Observable o, Object arg) {
        fireTableDataChanged();
    }

    @Override
    public void onAgentAdded(WarAgent newAgent) {
        fireTableDataChanged();
    }

    @Override
    public void onAgentRemoved(WarAgent removedAgent) {
        fireTableDataChanged();
    }
}
