package teams.fsm;

import edu.warbot.brains.brains.WarLightBrain;
import edu.warbot.fsm.WarFSM;

public abstract class WarLightBrainController extends WarLightBrain {

    private WarFSM<WarLightBrain> fsm;

    public WarLightBrainController() {
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

    protected WarFSM<WarLightBrain> initialisation() {
        return new WarFSM<>();
    }

}