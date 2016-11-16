package teams.timetoexplode;

import edu.warbot.agents.agents.WarExplorer;
import edu.warbot.agents.agents.WarKamikaze;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.brains.WarKamikazeBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarKamikazeBrainController extends WarKamikazeBrain {

    public WarKamikazeBrainController() {
        super();
    }

    @Override
    public String action() {

        List<WarMessage> msgs = getMessages();

        for (WarMessage msg : msgs) {
            if (msg.getMessage().equals("Enemy base on sight")) {
                setHeading(msg.getAngle());
            }
        }

        List<WarAgentPercept> percepts = getPercepts();

        for (WarAgentPercept p : percepts) {
            switch (p.getType()) {
                case WarBase:
                    if (isEnemy(p)) {
                        return WarKamikaze.ACTION_FIRE;
                    }
                    break;
                default:
                    break;
            }
        }

        if (isBlocked())
            setRandomHeading();
        return WarExplorer.ACTION_MOVE;
    }
}
