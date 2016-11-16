package teams.fsm;

import edu.warbot.brains.brains.WarHeavyBrain;
import edu.warbot.fsm.WarFSM;

public abstract class WarHeavyBrainController extends WarHeavyBrain {

    private WarFSM<WarHeavyBrain> fsm;

    public WarHeavyBrainController() {
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

    protected WarFSM<WarHeavyBrain> initialisation() {
        return new WarFSM<>();
    }

}