package edu.warbot.fsm.editor.settings;

import edu.warbot.agents.enums.WarAgentType;

/**
 * Les attributs doivent etre public
 * <p>
 * les attributs peuvent etre de types :
 * Boolean, Integer, String, EnumAction,
 * WarAgentType, EnumMethod, EnumOperand, EnumMessage
 */

public class GenericPlanSettings extends AbstractGenericSettings {

    public WarAgentType Agent_type = WarAgentType.WarExplorer;
    ;

    public Integer Number_agent = 1;

    public Integer Min_life = 30;

    public EnumMessage Message;

    public Boolean Pourcentage = true;

    public Boolean Offensif;

    public Integer Tik_number = 500;

}
