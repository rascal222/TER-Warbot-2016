package teams.fsm;

import edu.warbot.brains.brains.WarExplorerBrain;
import edu.warbot.fsm.WarFSM;

public abstract class WarExplorerBrainController extends WarExplorerBrain {

    private WarFSM<WarExplorerBrain> fsm;

    public WarExplorerBrainController() {
        super();
    }

    @Override
    public final void activate() {
        fsm = initialisation();
    }

    @Override
    public String action() {
        return fsm.executeFSM();
    }

    protected WarFSM<WarExplorerBrain> initialisation() {
        return new WarFSM<>();
    }
}
