package teams.test_team;

import edu.warbot.agents.agents.WarHeavy;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.agents.resources.WarFood;
import edu.warbot.brains.brains.WarHeavyBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarHeavyBrainController extends WarHeavyBrain {

    private boolean _baseFound;
    private boolean _inDanger;
    private int _baseId;
    private Double _basePosition;

    public WarHeavyBrainController() {
        super();

        _baseFound = false;
        _inDanger = false;
        _baseId = 0;
        _basePosition = 0.0;
    }

    @Override
    public String action() {

    	if (_baseId == 0) {
            broadcastMessageToAll("Your ID please", "");
        }

        if (getHealth() <= (WarHeavy.MAX_HEALTH / 5))
            return eat();

        List<WarAgentPercept> percepts = getPercepts();
        for (WarAgentPercept p : percepts) {
            switch (p.getType()) {
                case WarFood:
                    if (p.getDistance() < WarFood.MAX_DISTANCE_TAKE && !isBagFull()) {
                        setHeading(p.getAngle());
                        return take();
                    } else if (!isBagFull()) {
                        setHeading(p.getAngle());
                    }
                    break;
                case WarBase:
                    if (isEnemy(p)) {
                        _baseFound = true;
                        setHeading(p.getAngle());
                        if (isReloaded()) {
                            return fire();
                        } else
                            return beginReloadWeapon();
                    }
                    break;
                default:
                    if (isEnemy(p)) {
                        setHeading(p.getAngle());
                        if (isReloaded()) {
                            return fire();
                        } else
                            return beginReloadWeapon();
                    }
                    break;
            }
        }

        List<WarMessage> msgs = getMessages();
        for (WarMessage msg : msgs) {
            if (msg.getMessage().equals("Enemy base on sight") && !_inDanger) {
                setHeading(msg.getAngle());
            }
            if (msg.getMessage().equals("We are under attack")) {
                _inDanger = true;
                _basePosition = msg.getAngle();
            }
            if (msg.getMessage().equals("Here is my ID")) {
                String[] content = msg.getContent();
                _baseId = Integer.parseInt(content[0]);
            }
            if (msg.getMessage().equals("I am the danger")) {
                _inDanger = false;
                setRandomHeading();
            }
        }

        if (_inDanger && !_baseFound) {
            setHeading(_basePosition);
        }

        if (isBlocked())
            setRandomHeading();
        return move();
    }

}