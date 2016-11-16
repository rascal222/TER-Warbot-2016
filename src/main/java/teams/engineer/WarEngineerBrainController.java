package teams.engineer;

import edu.warbot.agents.agents.WarEngineer;
import edu.warbot.agents.agents.WarTurret;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.brains.WarEngineerBrain;
import edu.warbot.communications.WarMessage;

import java.util.List;

public abstract class WarEngineerBrainController extends WarEngineerBrain {

    private int _idBase;
    private boolean _aim;
    private boolean _defenseOnDuty;
    private Double _angleDirection;
    private int _state; //0 : normal, 1 : contournement, 2 : faim
    private int _distance;
    private Double _randomHeading;
    private int _nbTurrets;


    public WarEngineerBrainController() {
        super();

        _idBase = 0;
        _aim = false;
        _defenseOnDuty = false;
        _angleDirection = 0.0;
        _state = 0;
        _distance = 0;
        _randomHeading = 0.0;
        _nbTurrets = 0;

    }

    @Override
    public String action() {

        if (_idBase == 0) {
//            broadcastMessageToAll("Give me your ID base", "");
        }


        if (getHealth() < WarEngineer.MAX_HEALTH && !isBagEmpty()) {
            return WarEngineer.ACTION_EAT;
        }

        List<WarMessage> msgs = getMessages();
        for (WarMessage msg : msgs) {
            if (msg.getMessage().equals("I am the base and here is my ID")) {
                String[] content = msg.getContent();
                _idBase = Integer.parseInt(content[0]);
            }
            if (msg.getMessage().equals("Here is your next target")) {
                String[] content = msg.getContent();
                _angleDirection = Double.parseDouble(content[0]);
                setHeading(_angleDirection);
                return WarEngineer.ACTION_MOVE;
            }
            if (msg.getMessage().equals("You are on aim")) {
                //_aim = true;
                if (getHealth() > WarTurret.COST) {
                    setDebugString("Creating tower");
                    broadcastMessageToAll("Don't need food anymore", "");
                    setNextBuildingToBuild(WarAgentType.Wall);
                    _angleDirection = 0.0;
                    _nbTurrets++;
                    if (_nbTurrets == 4) {
                        _aim = true;
                    }
                    return WarEngineer.ACTION_BUILD;
                } else {
                    _state = 2;
                }
            }
            if (msg.getMessage().equals("You are not on aim")) {
                setDebugString("I don't stop");
                return WarEngineer.ACTION_MOVE;
            }
        }

        if (_state == 0) {

            if (!_defenseOnDuty) {
                if (!_aim) {
                    if (_angleDirection == 0.0) {
                        sendMessage(_idBase, "Give me my next target", "");
                        setDebugString("Give me my next target");
                        //System.out.println("Give me my next target");
                        return WarEngineer.ACTION_IDLE;
                    } else {
                        if (isBlocked()) {
                            _state = 1;
                            return WarEngineer.ACTION_IDLE;
                        }
                        setDebugString("Going to put a turret");
                        sendMessage(_idBase, "Am I on aim", "");
                        //System.out.println("I ask if I'm on aim");
                        //return WarEngineer.ACTION_MOVE;
                    }
                }
            }

        }
        if (_state == 1) {
            //setHeading(_angleDirection + 120);
            if (_randomHeading == 0.0) {
                setRandomHeading();
                _randomHeading = getHeading();
            }
            if (_distance < 20) {
                _distance++;
                return WarEngineer.ACTION_MOVE;
            } else {
                _randomHeading = 0.0;
                _angleDirection = 0.0;
                _distance = 0;
                _state = 0;
                return WarEngineer.ACTION_IDLE;
            }
        }
        if (_state == 2) {
            if (getHealth() <= WarTurret.COST) {
                broadcastMessageToAll("Need food", "");
                return WarEngineer.ACTION_IDLE;
            } else {
                setDebugString("Creating tower");
                broadcastMessageToAll("Don't need food anymore", "");
                setNextBuildingToBuild(WarAgentType.Wall);
                _angleDirection = 0.0;
                _nbTurrets++;
                if (_nbTurrets == 4) {
                    _aim = true;
                }
                _state = 0;
                return WarEngineer.ACTION_BUILD;
            }
        }

        return WarEngineer.ACTION_IDLE;
    }
}
