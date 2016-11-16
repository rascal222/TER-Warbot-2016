package teams.fsm;

import edu.warbot.brains.brains.WarTurretBrain;
import edu.warbot.fsm.WarFSM;

public abstract class WarTurretBrainController extends WarTurretBrain {

    private WarFSM<WarTurretBrain> fsm;


    public WarTurretBrainController() {
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

    protected WarFSM<WarTurretBrain> initialisation() {
        return new WarFSM<>();
    }

}
