package edu.warbot.gui.viewer.debug;

import edu.warbot.game.InGameTeam;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class WarTeamSelectButton extends JToggleButton implements ActionListener {

    private InGameTeam inGameTeam;
    private DebugModePanel toolBar;

    // TODO Ajout d'une image des agents
    public WarTeamSelectButton(InGameTeam inGameTeam, DebugModePanel toolBar) {
        super();
        this.inGameTeam = inGameTeam;
        this.toolBar = toolBar;

        setBackground(inGameTeam.getColor());
        setIcon(inGameTeam.getImage());
        setText(inGameTeam.getName());
        setFont(new Font("Arial", Font.PLAIN, 10));
//		setPreferredSize(new Dimension(125, 50));

        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        toolBar.getDebugTools().setSelectedInGameTeamForNextCreatedAgent(inGameTeam);
    }

}
