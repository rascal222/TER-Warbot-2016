package edu.warbot.fsm.editor.settings;

import edu.warbot.agents.enums.WarAgentType;

/**
 * Les attributs doivent etre public
 * <p>
 * les attributs peuvent etre de types :
 * Boolean, Integer, String, EnumAction,
 * WarAgentType, EnumMethod, EnumOperand, EnumMessage
 */
public class GenericConditionSettings extends AbstractGenericSettings {

    public EnumMethod Methode;

    public EnumOperand Operateur = EnumOperand.egal;

    public Integer Reference;

    public Boolean Pourcentage;

    public EnumMessage Message;

    public WarAgentType Agent_type = WarAgentType.WarExplorer;

    public Boolean Enemie = false;

    public Boolean Offensif = false;

    public Integer Tik_number = 500;


}
