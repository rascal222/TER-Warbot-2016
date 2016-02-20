package edu.warbot.fsm.editor.parsing.xml;


import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.fsm.action.WarAction;
import edu.warbot.fsm.editor.models.Model;
import edu.warbot.fsm.editor.models.ModelCondition;
import edu.warbot.fsm.editor.models.ModelState;
import edu.warbot.fsm.editor.models.ModeleBrain;
import edu.warbot.fsm.editor.settings.*;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Permet de lire un fichier de configuration au format de XML et de type fsm
 * Prend en parametre du constructeur le nom du fichier de confuguration de la fsm (par default "XMLConfiguration.xml")
 * Permet de créer un modele de fsm à partir du fichier de configuration
 * Utiliser la méthode getGeneratedFSMModel() pour récupérer le modèle crée
 */
public class FsmXmlReader extends FsmXmlParser {

    String fileName = xmlConfigurationDefaultFilename;
    private Model modeleFSM;

    public FsmXmlReader(File file) {

        Document doc = null;
        try {
            doc = new SAXBuilder().build(file);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("FsmXmlReader : File not found " + fileName);
            e.printStackTrace();
        }

        try {
            readConfigDocument(doc);
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Error while reading fsm configuration file \"" + file.getName() + "\"", "Reading error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public FsmXmlReader(String fileName) {
        this(new File(fileName));
    }

    public FsmXmlReader(InputStream inputStream) {
        Document doc = null;
        try {
            doc = new SAXBuilder().build(inputStream);
        } catch (JDOMException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("InputStream error " + inputStream);
            e.printStackTrace();
        }

        readConfigDocument(doc);
    }

    private void readConfigDocument(Document document) {
        modeleFSM = new Model();
        modeleFSM.setIsRebuild(false);

        Element root = document.getRootElement();

        //Pour chaque brains
        for (Element currentBrain : root.getChildren()) {
            createBrain(currentBrain, modeleFSM);
        }
    }

    private void createBrain(Element brain, Model modele) {
        //Pour chaque conditions et chaque états
        String agentTypeName = brain.getChild(AgentType).getValue();
        WarAgentType agentType = WarAgentType.valueOf(agentTypeName);

        ModeleBrain modelBrain = new ModeleBrain(agentType);
        modele.addModelBrain(modelBrain);

        createStates(brain.getChild(States), modelBrain);
        createConditions(brain.getChild(Conditions), modelBrain);
    }

    private void createStates(Element states, ModeleBrain modeleBrain) {
        String first = states.getChild(FirstState).getValue();

        modeleBrain.setFirstStateID(first);

        for (Element state : states.getChildren(State)) {
            createState(state, modeleBrain);
        }
    }

    private void createConditions(Element elemCond, ModeleBrain modeleBrain) {
        for (Element cond : elemCond.getChildren()) {
            createCondition(cond, modeleBrain);
        }
    }

    private void createState(Element state, ModeleBrain modeleBrain) {

        String name = state.getChild(Name).getValue();
        String plan = state.getChild(Plan).getValue();

        ArrayList<String> condID = getConditionsOutID(state.getChild(ConditionsOutID));

        GenericPlanSettings warPlanSetting =
                (GenericPlanSettings) getWarGenericSettings(GenericPlanSettings.class, state.getChild(PlanSettings));

        ModelState modeleState = new ModelState(name, plan, warPlanSetting);
        modeleState.setConditionsOutID(condID);

        modeleBrain.addState(modeleState);
    }

    private void createCondition(Element cond, ModeleBrain modeleBrain) {
        String name = cond.getChild(Name).getValue();
        String type = cond.getChild(Type).getValue();

        String stateOutID = cond.getChild(StateOutID).getValue();

        GenericConditionSettings warConditionSetting =
                (GenericConditionSettings) getWarGenericSettings(GenericConditionSettings.class, cond.getChild(ConditionSettings));

        ModelCondition modeleCond =
                new ModelCondition(name, type, warConditionSetting);
        modeleCond.setStateOutId(stateOutID);

        modeleBrain.addCondition(modeleCond);
    }

    @SuppressWarnings("rawtypes")
	private AbstractGenericSettings getWarGenericSettings(Class classSetting, Element element) {
        AbstractGenericSettings settings = null;
        try {
            settings = (AbstractGenericSettings) classSetting.newInstance();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        }

        Field[] fields = settings.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            try {

                if (fields[i].getType().equals(Integer.class))
                    fields[i].set(settings, getFieldForInteger(fields[i], element));

                else if (fields[i].getType().equals(Boolean.class))
                    fields[i].set(settings, getFieldForBoolean(fields[i], element));

                else if (fields[i].getType().equals(String.class))
                    fields[i].set(settings, getFieldForString(fields[i], element));

                else if (fields[i].getType().equals(WarAgentType.class))
                    fields[i].set(settings, getFieldForAgentType(fields[i], element));

                else if (fields[i].getType().equals(WarAction.class))
                    fields[i].set(settings, getFieldForAction(fields[i], element));

                else if (fields[i].getType().equals(EnumOperand.class))
                    fields[i].set(settings, getFieldForOperand(fields[i], element));

                else if (fields[i].getType().equals(EnumMethod.class))
                    fields[i].set(settings, getFieldForMethod(fields[i], element));

                else if (fields[i].getType().equals(EnumMessage.class))
                    fields[i].set(settings, getFieldForMessage(fields[i], element));

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Some configuration has changed for field name <" + fields[i].getName() + ">\nCondfiguration file loading may fail.", "Configuration loading warning", JOptionPane.WARNING_MESSAGE);
                System.err.println("FsmXmlReader unknown type for field " + fields[i].getName());
            }
        }

        return settings;
    }

    private String getField(Field field, Element elemPlanSetting) {
        try {
            return elemPlanSetting.getChild(field.getName()).getValue();
        } catch (NullPointerException e) {
            System.err.println("ERROR : Field " + field.getName() + " do not exist in xmlConfigurationFile but exist in GenericSettings");
            return null;
        } catch (IllegalArgumentException e) {
            System.err.println("ERROR : Field " + field.getName() + elemPlanSetting.getChild(field.getName()).getValue());
            return null;
        }
    }

    //Ici essayer de fusionner avec les autre methodes mais le cast en marche pas de string a integer
    //Et le mathode valueOf est déclaré dans chaque type donc impossible de faire ça de manière generique
    private String getFieldForString(Field field, Element elemPlanSetting) {
        String s = getField(field, elemPlanSetting);
        if (s.isEmpty())
            return null;
        else
            return s;
//		return field.getType().cast(elemPlanSetting.getChild(field.getName()).getValue());
    }

    private Boolean getFieldForBoolean(Field field, Element elemPlanSetting) {
        try {
            String s = getField(field, elemPlanSetting);
            if (s.isEmpty())
                return null;
            else
                return Boolean.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private Integer getFieldForInteger(Field field, Element elemPlanSetting) {
        try {
            return Integer.valueOf(getField(field, elemPlanSetting));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private WarAgentType getFieldForAgentType(Field field, Element elemPlanSetting) {
        try {
            String s = getField(field, elemPlanSetting);
            return WarAgentType.valueOf(s);
        } catch (NumberFormatException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private String getFieldForAction(Field field, Element elemPlanSetting) {
        try {
            return getField(field, elemPlanSetting);
        } catch (NumberFormatException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private EnumMessage getFieldForMessage(Field field, Element elemPlanSetting) {
        try {
            return EnumMessage.valueOf(getField(field, elemPlanSetting));
        } catch (NumberFormatException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private EnumMethod getFieldForMethod(Field field, Element elemPlanSetting) {
        try {
            return EnumMethod.valueOf(getField(field, elemPlanSetting));
        } catch (NumberFormatException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private EnumOperand getFieldForOperand(Field field, Element elemPlanSetting) {
        try {
            return EnumOperand.valueOf(getField(field, elemPlanSetting));
        } catch (NumberFormatException e) {
            return null;
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private ArrayList<String> getConditionsOutID(Element element) {
        ArrayList<String> res = new ArrayList<String>();
        for (Element currentID : element.getChildren(ConditionOutID)) {
            res.add(currentID.getValue());
        }
        return res;
    }

    /**
     * @return Renvoi le modele de fsm générer grace au fichier de configuration
     */
    public Model getGeneratedFSMModel() {
        return this.modeleFSM;
    }


}
