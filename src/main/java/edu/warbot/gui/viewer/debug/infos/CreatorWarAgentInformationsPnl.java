package edu.warbot.gui.viewer.debug.infos;

import edu.warbot.agents.CreatorWarAgent;
import edu.warbot.gui.viewer.debug.DebugToolsPnl;

import javax.swing.*;

@SuppressWarnings("serial")
public class CreatorWarAgentInformationsPnl extends JPanel implements IWarAgentInformationsPnl {

    private DebugToolsPnl debugToolsPnl;

    private InfoLabel _nextAgentToCreate;

    public CreatorWarAgentInformationsPnl(DebugToolsPnl debugToolsPnl) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.debugToolsPnl = debugToolsPnl;

        add(new JLabel(" "));
        _nextAgentToCreate = new InfoLabel("Prochain agent à créer");
        add(_nextAgentToCreate);
    }

    @Override
    public void update() {
        if (debugToolsPnl.getSelectedAgent() instanceof CreatorWarAgent) {
            setVisible(true);
            CreatorWarAgent a = (CreatorWarAgent) debugToolsPnl.getSelectedAgent();

            _nextAgentToCreate.setValue(a.getNextAgentToCreate().toString());
        } else {
            setVisible(false);
        }
    }

    @Override
    public void resetInfos() {
        _nextAgentToCreate.setValue("");
    }

}