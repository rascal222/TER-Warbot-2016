package edu.warbot.gui.viewer.debug.infos;

import edu.warbot.agents.MovableWarAgent;
import edu.warbot.gui.viewer.debug.DebugToolsPnl;

import javax.swing.*;

@SuppressWarnings("serial")
public class MovableWarAgentInformationsPnl extends JPanel implements IWarAgentInformationsPnl {

    private DebugToolsPnl debugToolsPnl;

    private InfoLabel _speed;

    public MovableWarAgentInformationsPnl(DebugToolsPnl debugToolsPnl) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.debugToolsPnl = debugToolsPnl;

        add(new JLabel(" "));
        _speed = new InfoLabel("Vitesse");
        add(_speed);
    }

    @Override
    public void update() {
        if (debugToolsPnl.getSelectedAgent() instanceof MovableWarAgent) {
            setVisible(true);
            MovableWarAgent a = (MovableWarAgent) debugToolsPnl.getSelectedAgent();

            _speed.setValue(doubleFormatter.format(a.getSpeed()));
        } else {
            setVisible(false);
        }
    }

    @Override
    public void resetInfos() {
        _speed.setValue("");
    }

}