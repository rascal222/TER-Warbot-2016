package teams.fsm;

import edu.warbot.brains.brains.WarRocketLauncherBrain;
import edu.warbot.fsm.WarFSM;

public abstract class WarRocketLauncherBrainController extends WarRocketLauncherBrain {

    private WarFSM<WarRocketLauncherBrain> fsm;

    public WarRocketLauncherBrainController() {
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

    protected WarFSM<WarRocketLauncherBrain> initialisation() {
        return new WarFSM<>();
    }

}