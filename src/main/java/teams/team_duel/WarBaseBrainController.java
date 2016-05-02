package teams.team_duel;

import edu.warbot.agents.agents.WarBase;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.brains.WarBaseBrain;
import edu.warbot.communications.WarMessage;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.PolarCoordinates;

import java.util.List;

public abstract class WarBaseBrainController extends WarBaseBrain {

    private boolean _alreadyCreated;
    private boolean _inDanger;
    private int etat_assaut = 0;
    private double angleBase;
    private double distanceBase;
    
    public WarBaseBrainController() {
        super();

        _alreadyCreated = false;
        _inDanger = false;
    }


    @Override
    public String action() {

    	List<WarMessage> messages = getMessages();
    	if(etat_assaut == 0){
    		 
    		 if(getPerceptsEnemies().isEmpty()){
    			 broadcastMessageToAgentType(WarAgentType.WarRocketLauncher, "Protect", "bla");
    			 _inDanger=false;
    		 }else{
    		 for(WarAgentPercept wp : getPerceptsEnemies()){
    			 broadcastMessageToAgentType(WarAgentType.WarRocketLauncher, "Defend", "" + wp.getAngle(), "" + wp.getDistance());
    			 _inDanger = true;
    		 }}
    		 for (WarMessage message : messages) {
    	            if (message.getMessage().equals("Trouve")){
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

    	        		distanceBase = vectC.getDistance();
    	        		angleBase = vectC.getAngle();
    	        		
    	            	etat_assaut = 1;
    	            }
    		 }
    	}
    	if(etat_assaut == 1){
    		
    		if(_inDanger){
    		if(getPerceptsEnemies().isEmpty()){
	    			 broadcastMessageToAgentType(WarAgentType.WarRocketLauncher, "Protect", "bla");
	    			 _inDanger=false;
	   		 }else{
	   		 for(WarAgentPercept wp : getPerceptsEnemies()){
	   			 broadcastMessageToAgentType(WarAgentType.WarRocketLauncher, "Defend", "" + wp.getAngle(), "" + wp.getDistance());
	   		 }}
    		}
    		else{
   			 	broadcastMessageToAgentType(WarAgentType.WarRocketLauncher, "Protect", "bla");	
    		}
    		
            broadcastMessageToAgentType(WarAgentType.WarRocketLauncher, "Assaut", "" + angleBase, ""+distanceBase);
            broadcastMessageToAgentType(WarAgentType.WarKamikaze, "Assaut", "" + angleBase, ""+distanceBase);
    	}

        return WarBase.ACTION_IDLE;
    	}
}
