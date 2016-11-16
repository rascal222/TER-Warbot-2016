package teams.fsm;

import edu.warbot.brains.brains.WarKamikazeBrain;
import edu.warbot.fsm.WarFSM;

public abstract class WarKamikazeBrainController extends WarKamikazeBrain {

    private WarFSM<WarKamikazeBrain> fsm;

    public WarKamikazeBrainController() {
        super();
    }

    @Override
    public final void activate() {
        fsm = initialisation();
    }

    @Override
    public final String action() {
        return fsm.executeFSM();
    }

    protected WarFSM<WarKamikazeBrain> initialisation() {
        return new WarFSM<>();
    }
}
