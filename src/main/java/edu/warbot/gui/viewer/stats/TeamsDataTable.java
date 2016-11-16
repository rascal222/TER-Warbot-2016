package edu.warbot.gui.viewer.stats;

import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.game.listeners.WarGameListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class TeamsDataTable extends JTable implements WarGameListener {

    private TeamsDataTableModel _model;
    private WarGame game;

    public TeamsDataTable(WarGame game) {
        super();
        this.game = game;

        setDefaultRenderer(Object.class, new DefaultCellRenderer());
        getTableHeader().setVisible(false);
        setRowHeight(20);

        _model = new TeamsDataTableModel(game);
        setModel(_model);
        getColumnModel().getColumn(0).setCellRenderer(new HeaderCellRenderer());

        WarGame.addWarGameListener(this);
    }

    @Override
    public void onNewTeamAdded(InGameTeam newInGameTeam) {
        updateTeamsDataTable();
    }

    @Override
    public void onTeamLost(InGameTeam removedInGameTeam) {
//        updateTeamsDataTable();
    }

    @Override
    public void onGameOver() {
    }

    @Override
    public void onGameStopped() {
    }

    @Override
    public void onGameStarted() {
    }

    private void updateTeamsDataTable() {
        _model = new TeamsDataTableModel(game);
        setModel(_model);
        getColumnModel().getColumn(0).setCellRenderer(new HeaderCellRenderer());
    }

	@Override
	public void onGamePaused() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGameResumed() {
		// TODO Auto-generated method stub
		
	}
}
