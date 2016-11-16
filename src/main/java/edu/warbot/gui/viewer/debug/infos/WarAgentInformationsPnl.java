package edu.warbot.gui.viewer.debug.infos;

import edu.warbot.agents.WarAgent;
import edu.warbot.gui.viewer.debug.DebugToolsPnl;

import javax.swing.*;

@SuppressWarnings("serial")
public class WarAgentInformationsPnl extends JPanel implements IWarAgentInformationsPnl {

    private DebugToolsPnl debugToolsPnl;

    private InfoLabel _id;
    private InfoLabel _type;
    private InfoLabel _position;
    private InfoLabel _team;
    private InfoLabel _heading;

    private AliveWarAgentInformationsPnl _aliveAgent;
    private ControllableWarAgentInformationsPnl _controllableAgent;
    private MovableWarAgentInformationsPnl _movableAgent;
    private CreatorWarAgentInformationsPnl _creatorAgent;
    private WarProjectileInformationsPnl _projectile;

    public WarAgentInformationsPnl(DebugToolsPnl debugToolsPnl) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.debugToolsPnl = debugToolsPnl;

        add(new JLabel("Informations sur l'agent sélectionné : "));

        _id = new InfoLabel("ID");
        add(_id);
        _type = new InfoLabel("Type");
        add(_type);
        _position = new InfoLabel("Position");
        add(_position);
        _team = new InfoLabel("Equipe");
        add(_team);
        _heading = new InfoLabel("Heading");
        add(_heading);

        _aliveAgent = new AliveWarAgentInformationsPnl(this.debugToolsPnl);
        add(_aliveAgent);
        _controllableAgent = new ControllableWarAgentInformationsPnl(this.debugToolsPnl);
        add(_controllableAgent);
        _movableAgent = new MovableWarAgentInformationsPnl(this.debugToolsPnl);
        add(_movableAgent);
        _creatorAgent = new CreatorWarAgentInformationsPnl(this.debugToolsPnl);
        add(_creatorAgent);
        _projectile = new WarProjectileInformationsPnl(this.debugToolsPnl);
        add(_projectile);
    }

    @Override
    public void update() {
        WarAgent a = debugToolsPnl.getSelectedAgent();

        if (a == null) {
            setVisible(false);
        } else {
            setVisible(true);
            _id.setValue(String.valueOf(a.getID()));
            _type.setValue(a.getClass().getSimpleName());
            _position.setValue("(" + doubleFormatter.format(a.getX()) + "; " + doubleFormatter.format(a.getY()) + ")");
            _team.setValue(a.getTeam().getName());
            _heading.setValue(doubleFormatter.format(a.getHeading()));

            _aliveAgent.update();
            _controllableAgent.update();
            _movableAgent.update();
            _creatorAgent.update();
            _projectile.update();
        }
    }

    @Override
    public void resetInfos() {
        _id.setValue("");
        _type.setValue("");
        _position.setValue("");
        _team.setValue("");
        _heading.setValue("");
    }

}
