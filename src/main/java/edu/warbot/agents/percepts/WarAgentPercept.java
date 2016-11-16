package edu.warbot.agents.percepts;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.enums.WarAgentType;

public class WarAgentPercept extends WarPercept implements Comparable<WarAgentPercept> {

    // On enregistre les données sur l'agent observé sans conserver l'agent vu pour éviter que ce WarPercept ne soit utilisé pour "continuellement voir" l'agent vu.
    private double _angle;
    private double _distance;
    private int _id;
    private String _teamName;
    private WarAgentType _type;
    private double _heading;
    private int _health;
    private int maxHealth;

    public WarAgentPercept(ControllableWarAgent observer, WarAgent seenAgent) {
        super(observer);
        _angle = observer.getPosition().getAngleToPoint(seenAgent.getPosition());
        this._distance = observer.getAverageDistanceFrom(seenAgent);
        this._teamName = seenAgent.getTeam().getName();
        this._id = seenAgent.getID();
        this._type = WarAgentType.valueOf(seenAgent.getClass().getSimpleName());
        this._heading = seenAgent.getHeading();

        if (seenAgent instanceof AliveWarAgent) {
            _health = ((AliveWarAgent) seenAgent).getHealth();
            maxHealth = ((AliveWarAgent) seenAgent).getMaxHealth();
        } else {
            _health = 0;
        }
    }

    public double getAngle() {
        return _angle;
    }

    public double getDistance() {
        return _distance;
    }

    public int getID() {
        return _id;
    }

    public String getTeamName() {
        return _teamName;
    }

    public WarAgentType getType() {
        return _type;
    }

    public double getHeading() {
        return _heading;
    }

    public int getHealth() {
        return _health;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public String toString() {
        return "AgentPercept : " + _type + " " + _id + " : team = " + _teamName + " : " + _angle + "° " + _distance;
    }

    @Override
    public int compareTo(WarAgentPercept percept) {
        if (getDistance() > percept.getDistance())
            return 1;
        else if (getDistance() < percept.getDistance())
            return -1;
        return 0;
    }
}
