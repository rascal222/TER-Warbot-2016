package teams.team_duel;

import edu.warbot.agents.agents.WarExplorer;
import edu.warbot.agents.agents.WarKamikaze;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.brains.WarKamikazeBrain;
import edu.warbot.communications.WarMessage;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.PolarCoordinates;

import java.util.List;

public abstract class WarKamikazeBrainController extends WarKamikazeBrain {

	private int assaut;
	private double angleEn;
	private double distanceEn;
	
    public WarKamikazeBrainController() {
        super();
    }
    
    private void calculAngleAttaque(){
    	List<WarMessage> messages = getMessages();
    	for (WarMessage message : messages) {
			 if (message.getMessage().equals("Assaut")){
				String coord[] = message.getContent();
            	Double angleA = Double.parseDouble(coord[0]);
            	Double distanceA = Double.parseDouble(coord[1]);
            	Double angleB = message.getAngle();
            	Double distanceB = message.getDistance();
            	PolarCoordinates vectA = new PolarCoordinates(distanceA, angleA);
            	PolarCoordinates vectB = new PolarCoordinates(distanceB, angleB);
            	
            	CartesianCoordinates A = vectA.toCartesian();
            	CartesianCoordinates B = vectB.toCartesian();
            	CartesianCoordinates C = A;
            	C.add(B);
            	PolarCoordinates vectC = C.toPolar();

        		distanceEn = vectC.getDistance();
        		angleEn = vectC.getAngle();
        		
			 }
    	}
    }
    
    

    @Override
    public String action() {
        List <WarAgentPercept> percepts = getPercepts();
    	List<WarMessage> messages = getMessages();
    	
    	for (WarMessage message : messages) {
			 if (message.getMessage().equals("Assaut")){
				calculAngleAttaque();
	    		setHeading(angleEn);
	    		setDebugString("Watch out ! ");
        		assaut = 1;
            }
    	}
        
    	if(assaut == 1){
    		calculAngleAttaque();
        	setHeading(angleEn);
        }
        
        for (WarAgentPercept p : percepts) {
            switch (p.getType()) {
                case WarBase:
                    if (isEnemy(p)) {
                    	setHeading(p.getAngle());
                    	String angle = "" + p.getAngle();
                    	String distance = "" + p.getDistance();
                    	broadcastMessageToAgentType(WarAgentType.WarBase, "Trouve", angle, distance);
                    	setDebugString("Trouve angle : " + angle);
                    	if(p.getDistance() < 15){
                            return WarKamikaze.ACTION_FIRE;
                    	}
                    }
                    break;
                default:
                    break;
            }
        }
        
        
        if (isBlocked()){
        	int random = (int )(Math.random() * 25 + 15);
            setHeading(getHeading()+random);
        }
        
        return WarExplorer.ACTION_MOVE;
    }
}
