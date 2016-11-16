package teams.fsm;

import edu.warbot.brains.brains.WarEngineerBrain;
import edu.warbot.fsm.WarFSM;

public abstract class WarEngineerBrainController extends WarEngineerBrain {

    private WarFSM<WarEngineerBrain> fsm;

    public WarEngineerBrainController() {
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

    protected WarFSM<WarEngineerBrain> initialisation() {
        return new WarFSM<>();
    }
}
