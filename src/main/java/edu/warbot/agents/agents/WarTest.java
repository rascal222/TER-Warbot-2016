package edu.warbot.agents.agents;

import edu.warbot.agents.MovableWarAgent;
import edu.warbot.agents.actions.AgressiveActionsMethods;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.projectiles.WarRocket;
import edu.warbot.brains.WarBrain;
import edu.warbot.brains.capacities.Agressive;
import edu.warbot.game.InGameTeam;
import edu.warbot.launcher.WarGameConfig;

import java.util.Map;
import java.util.logging.Level;

public class WarTest extends MovableWarAgent implements AgressiveActionsMethods, Agressive {

    public static final double ANGLE_OF_VIEW;
    public static final double DISTANCE_OF_VIEW;
    public static final int COST;
    public static final int MAX_HEALTH;
    public static final int BAG_SIZE;
    public static final double SPEED;
    public static final int TICKS_TO_RELOAD;

    static {
        Map<String, Object> data = WarGameConfig.getConfigOfWarAgent(WarAgentType.WarTest);
        ANGLE_OF_VIEW = (double) data.get(WarGameConfig.AGENT_CONFIG_ANGLE_OF_VIEW);
        DISTANCE_OF_VIEW = (double) data.get(WarGameConfig.AGENT_CONFIG_DISTANCE_OF_VIEW);
        COST = (int) data.get(WarGameConfig.AGENT_CONFIG_COST);
        MAX_HEALTH = (int) data.get(WarGameConfig.AGENT_CONFIG_MAX_HEALTH);
        BAG_SIZE = (int) data.get(WarGameConfig.AGENT_CONFIG_BAG_SIZE);
        SPEED = (double) data.get(WarGameConfig.AGENT_CONFIG_SPEED);
        TICKS_TO_RELOAD = (int) data.get(WarGameConfig.AGENT_CONFIG_TICKS_TO_RELOAD);
    }

    private boolean _reloaded;
    private boolean _reloading;
    private int _tickLeftBeforeReloaded; // Retient le tick global quand le reload a commencé

    public WarTest(InGameTeam inGameTeam, WarBrain brain) {
        super(ACTION_IDLE, inGameTeam, WarGameConfig.getHitboxOfWarAgent(WarAgentType.WarTest), brain, DISTANCE_OF_VIEW, ANGLE_OF_VIEW, COST, MAX_HEALTH, BAG_SIZE, SPEED);

//		brain.setAgentAdapter(new WarRocketLauncherBrain(this));
        _tickLeftBeforeReloaded = TICKS_TO_RELOAD;
        _reloaded = false;
        _reloading = true;
    }

    @Override
    protected void doBeforeEachTick() {
        _tickLeftBeforeReloaded--;
        if (_tickLeftBeforeReloaded <= 0 && _reloading) {
            _reloaded = true;
            _reloading = false;
        }
        super.doBeforeEachTick();
    }

    @Override
    public String fire() {
        logger.log(Level.FINEST, this.toString() + " firing...");
        if (isReloaded()) {
            logger.log(Level.FINER, this.toString() + " fired.");
            launchAgent(new WarRocket(getTeam(), this));
            _reloaded = false;
        }
        return getBrain().action();
    }

    @Override
    public String beginReloadWeapon() {
        logger.log(Level.FINEST, this.toString() + " begin reload weapon.");
        if (!_reloading) {
            _tickLeftBeforeReloaded = TICKS_TO_RELOAD;
            _reloading = true;
        }
        return getBrain().action();
    }

    @Override
    public boolean isReloaded() {
        return _reloaded;
    }

    @Override
    public boolean isReloading() {
        return _reloading;
    }

    public WarAgentType getType() {
        return WarAgentType.WarTest;
    }
}
