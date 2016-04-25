package teams.presentation_relic;

import edu.warbot.agents.agents.WarExplorer;
import edu.warbot.agents.agents.WarTurret;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.agents.projectiles.WarShell;
import edu.warbot.brains.brains.WarTurretBrain;

import java.util.List;

public abstract class WarTurretBrainController extends WarTurretBrain
{
	private int _sight;
	
    public WarTurretBrainController()
    {
        super();
        _sight = 0;
    }

    @Override
    public String action()
    {
    	this.setDebugString("Activé");
    	//_sight += 90;
        //if (_sight == 360) {
        //    _sight = 0;
        //}
        //setHeading(_sight);
        
        List<WarAgentPercept> percepts = getPercepts();
        for (WarAgentPercept p : percepts) {
        	if (isEnemy(p)) {
        
        		double angleDeTir = UtilTir.angleToShoot(WarExplorer.SPEED, WarShell.SPEED, p.getDistance(), p.getHeading(), p.getAngle());
        		String s = "dist="+p.getDistance()+"/headingCible="+p.getHeading()+"/anglePercept="+p.getAngle();
        		setDebugString(s);
	            setHeading(angleDeTir);
	            if (isReloaded()) {
	                return WarTurret.ACTION_FIRE;
	            } else
	                return WarTurret.ACTION_RELOAD;
	        }
        }
    	
    	return WarTurret.ACTION_IDLE;
    }
}
