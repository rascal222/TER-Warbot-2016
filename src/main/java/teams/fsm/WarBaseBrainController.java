package teams.fsm;

import edu.warbot.brains.brains.WarBaseBrain;
import edu.warbot.fsm.WarFSM;

public abstract class WarBaseBrainController extends WarBaseBrain {

    private WarFSM<WarBaseBrain> fsm;

    public WarBaseBrainController() {
        super();
    }

    @Override
    public final void activate() {
        fsm = initializeFSM();
    }

    @Override
    public final String action() {
        return fsm.executeFSM();
    }

    protected WarFSM<WarBaseBrain> initializeFSM() {
        return new WarFSM<>();
    }

}
