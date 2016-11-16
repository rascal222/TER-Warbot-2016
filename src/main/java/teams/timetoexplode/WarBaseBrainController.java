package teams.timetoexplode;

import edu.warbot.agents.agents.WarBase;
import edu.warbot.agents.agents.WarEngineer;
import edu.warbot.agents.agents.WarKamikaze;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.brains.WarBaseBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarBaseBrainController extends WarBaseBrain {

    private boolean _inDanger;
    private int _nbKamikaze;

    public WarBaseBrainController() {
        super();

        _inDanger = false;
        _nbKamikaze = 0;
    }

    @Override
    public String action() {
        if ((getHealth() < WarBase.MAX_HEALTH) && (!isBagEmpty())) {
            return WarBase.ACTION_EAT;
        }

        if ((getHealth() > WarKamikaze.COST) && (_nbKamikaze < ((WarBase.MAX_HEALTH / WarKamikaze.COST) - 1))) {
            setNextAgentToCreate(WarAgentType.WarKamikaze);
            _nbKamikaze++;
            return WarBase.ACTION_CREATE;
        }

        List<WarMessage> msgs = getMessages();
        for (WarMessage msg : msgs) {
            if (msg.getMessage().equals("Give me your ID base")) {
                reply(msg, "I am the base and here is my ID", Integer.toString(getID()));
            }
            if (msg.getMessage().equals("Are you full life")) {
                if (getHealth() < WarBase.MAX_HEALTH) {
                    reply(msg, "I am not full life", "");
                } else {
                    reply(msg, "I am full life", "");
                }
            }
        }

        List<WarAgentPercept> percepts = getPercepts();

        for (WarAgentPercept p : percepts) {
            switch (p.getType()) {
                case WarRocketLauncher:
                    if (isEnemy(p)) {
                        if (getHealth() < (WarBase.MAX_HEALTH - WarEngineer.COST)) {
                            broadcastMessageToAll("We are under attack", String.valueOf(p.getAngle()), String.valueOf(p.getDistance()));
                            _inDanger = true;
                        }
                    }
                    break;
                case WarKamikaze:
                    if (isEnemy(p)) {
                        if (getHealth() < (WarBase.MAX_HEALTH - WarEngineer.COST)) {
                            broadcastMessageToAll("We are under attack", String.valueOf(p.getAngle()), String.valueOf(p.getDistance()));
                            _inDanger = true;
                        }
                    }
                    break;
                default:
                    break;
            }
        }

        if (_inDanger) {
            List<WarAgentPercept> enemies = getPerceptsEnemies();
            if (enemies.isEmpty()) {
                _inDanger = false;
                broadcastMessageToAll("I am the danger", "");
            }
        }

        return WarBase.ACTION_IDLE;
    }

}
