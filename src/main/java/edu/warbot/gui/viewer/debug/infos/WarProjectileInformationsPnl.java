package edu.warbot.gui.viewer.debug.infos;

import edu.warbot.agents.WarProjectile;
import edu.warbot.gui.viewer.debug.DebugToolsPnl;

import javax.swing.*;

@SuppressWarnings("serial")
public class WarProjectileInformationsPnl extends JPanel implements IWarAgentInformationsPnl {

    private DebugToolsPnl debugToolsPnl;

    private InfoLabel _explosionRadius;
    private InfoLabel _damage;
    private InfoLabel _speed;
    private InfoLabel _autonomy;
    private InfoLabel _currentAutonomy;
    private InfoLabel _sender;

    public WarProjectileInformationsPnl(DebugToolsPnl debugToolsPnl) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.debugToolsPnl = debugToolsPnl;

        add(new JLabel(" "));
        _explosionRadius = new InfoLabel("Rayon d'explosion");
        add(_explosionRadius);
        _damage = new InfoLabel("Puissance");
        add(_damage);
        _speed = new InfoLabel("Vitesse");
        add(_speed);
        _autonomy = new InfoLabel("Autonomie");
        add(_autonomy);
        _currentAutonomy = new InfoLabel("Autonomie restante");
        add(_currentAutonomy);
        _sender = new InfoLabel("Tireur");
        add(_sender);
    }

    @Override
    public void update() {
        if (debugToolsPnl.getSelectedAgent() instanceof WarProjectile) {
            setVisible(true);
            WarProjectile a = (WarProjectile) debugToolsPnl.getSelectedAgent();

            _explosionRadius.setValue(doubleFormatter.format(a.getExplosionRadius()));
            _damage.setValue(String.valueOf(a.getDamage()));
            _speed.setValue(doubleFormatter.format(a.getSpeed()));
            _autonomy.setValue(String.valueOf(a.getAutonomy()));
            _currentAutonomy.setValue(String.valueOf(a.getCurrentAutonomy()));
            _sender.setValue(a.toString());
        } else {
            setVisible(false);
        }
    }

    @Override
    public void resetInfos() {
        _explosionRadius.setValue("");
        _damage.setValue("");
        _speed.setValue("");
        _autonomy.setValue("");
        _currentAutonomy.setValue("");
        _sender.setValue("");
    }

}