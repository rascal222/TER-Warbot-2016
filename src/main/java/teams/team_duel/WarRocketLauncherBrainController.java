package teams.team_duel;

import java.util.List;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.brains.brains.WarRocketLauncherBrain;
import edu.warbot.communications.WarMessage;
import edu.warbot.tools.geometry.CartesianCoordinates;
import edu.warbot.tools.geometry.PolarCoordinates;

public abstract class WarRocketLauncherBrainController extends WarRocketLauncherBrain {

	private int assaut = 0;
	private int defend = 0;
	private int decale = 0;
	private Double angleEn;
	private Double angleDef;
	private Double distanceEn;
	private Double distanceDef;
    public WarRocketLauncherBrainController() {
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
    
    private void calculAngleDefense(){
    	List<WarMessage> messages = getMessages();
    	for (WarMessage message : messages) {
			 if (message.getMessage().equals("Defend")){
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

        		distanceDef = vectC.getDistance();
        		angleDef = vectC.getAngle();
        		
			 }
    	}
    }
    
    private String attaquerBase(){
    	setHeading(angleEn);
    	String retour = ACTION_MOVE;
		for (WarAgentPercept wp : getPerceptsEnemies()) {
			if (wp.getType().equals(WarAgentType.WarBase)) {
				setDebugString("Base reperee");
            	setHeading(wp.getAngle());
            	if (isReloaded())
            		retour = ACTION_FIRE;
            	else if (isReloading())
            		retour = ACTION_IDLE;
            	else
            		retour = ACTION_RELOAD;
            	}
            }
		if (isBlocked()){
            setRandomHeading();
        }
		return retour;
    }
    
    private String attaquerBot(){
    	setHeading(angleDef);
    	String retour = ACTION_MOVE;
		for (WarAgentPercept wp : getPerceptsEnemies()) {
            	setHeading(wp.getAngle());
            	if (isReloaded())
            		retour = ACTION_FIRE;
            	else if (isReloading())
            		retour = ACTION_IDLE;
            	else
            		retour = ACTION_RELOAD;
            	
            }
		if (isBlocked()){
            setRandomHeading();
        }
		return retour;
    }
    
    @Override
    public String action() {

    	List<WarMessage> messages = getMessages();
    	
    	if(assaut == 0 && defend == 0){
    		setDebugString("OKLM");
    		 for (WarMessage message : messages) {
    			 if (message.getMessage().equals("Assaut")){
    				calculAngleAttaque();
    	    		setHeading(angleEn);
    	    		setDebugString("Watch out ! ");
 	        		assaut = 1;
 	            }
    	            else{
    	            		if(message.getMessage().equals("Protect")){
    	            			if(message.getDistance() > 90){
    	            				setHeading(message.getAngle());
    	            			}
    	            		}
    	            		else{
    	            			if(message.getMessage().equals("Defend")){
    	            				calculAngleDefense();
    	            				defend = 1;
    	            			}
    	            		}
    	            }
    		 }
    	setRandomHeading(20);
    	}
    	
    	
    	if(assaut == 1 && decale == 0){
    		setDebugString("Assaut");
        	calculAngleAttaque();
        	if(distanceEn < 15){
        		setHeading(angleEn-180);
        	}
        	else{
        		
    		for(WarAgentPercept wp : getPerceptsAllies()){
    			if(wp.getDistance() < 10){
    				setHeading(wp.getAngle()-180);
            		decale = 5;
    				return ACTION_MOVE;
    			}
    		}
        		return attaquerBase();
        	}
    	}
    	if(assaut == 1 && decale > 0){
    		setHeading(getHeading() - 20);
    		decale --;
    	}
    	
    	if(defend == 1 && decale == 0){
    		setDebugString("Defense");
    		for (WarMessage message : messages) {
    			 if (message.getMessage().equals("Protect")){
    				 defend = 0;
    			 }
    		}
    		calculAngleDefense();
    		if(distanceDef < 15){
        		setHeading(angleDef-180);
        	}
        	else{
	        		
	    		for(WarAgentPercept wp : getPerceptsAllies()){
	    			if(wp.getDistance() < 10){
	    				setHeading(wp.getAngle()-180);
	            		decale = 5;
	    				return ACTION_MOVE;
	    			}
	    		}
	        		return attaquerBot();
        	}
    	}
    	
    	if(defend == 1 && decale > 0){
    		for (WarMessage message : messages) {
    			if (message.getMessage().equals("Protect")){
   				 	defend = 0;
   				 	decale = 0;
   			 	}
    		}
    		
    		setDebugString("Decalage");
    		setHeading(getHeading() - 20);
    		decale --;
    	}
  
    	
        
        if (isBlocked()){
            setRandomHeading();
        }
        

        return ACTION_MOVE;
    	
    }

}
