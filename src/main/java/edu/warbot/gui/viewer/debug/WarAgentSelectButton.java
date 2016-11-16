package edu.warbot.gui.viewer.debug;

import edu.warbot.agents.enums.WarAgentType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class WarAgentSelectButton extends JToggleButton implements ActionListener {

    private WarAgentType _warAgentTypeToSelect;
    private DebugModePanel _toolBar;

    // TODO Ajout d'une image des agents
    public WarAgentSelectButton(WarAgentType warType, DebugModePanel toolBar) {
        super();

        _warAgentTypeToSelect = warType;
        _toolBar = toolBar;

        setText(_warAgentTypeToSelect.toString());
        setFont(new Font("Arial", Font.PLAIN, 10));
        setPreferredSize(new Dimension(125, 50));

        this.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        _toolBar.getDebugTools().setSelectedWarAgentTypeToCreate(_warAgentTypeToSelect);
    }
}
