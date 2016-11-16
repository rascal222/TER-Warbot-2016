package edu.warbot.gui.viewer.debug.infos;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.gui.viewer.debug.DebugToolsPnl;

import javax.swing.*;

@SuppressWarnings("serial")
public class AliveWarAgentInformationsPnl extends JPanel implements IWarAgentInformationsPnl {

    private DebugToolsPnl debugToolsPnl;

    private InfoLabel _health;

    public AliveWarAgentInformationsPnl(DebugToolsPnl debugToolsPnl) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.debugToolsPnl = debugToolsPnl;

        add(new JLabel(" "));
        _health = new InfoLabel("Santé");
        add(_health);
    }

    @Override
    public void update() {
        if (debugToolsPnl.getSelectedAgent() instanceof AliveWarAgent) {
            setVisible(true);
            AliveWarAgent a = (AliveWarAgent) debugToolsPnl.getSelectedAgent();

            _health.setValue(a.getHealth() + " / " + a.getMaxHealth());
        } else {
            setVisible(false);
        }
    }

    @Override
    public void resetInfos() {
        _health.setValue("");
    }

}
