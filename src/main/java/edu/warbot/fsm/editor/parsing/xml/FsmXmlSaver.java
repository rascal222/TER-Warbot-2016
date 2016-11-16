package edu.warbot.fsm.editor.parsing.xml;

import edu.warbot.fsm.editor.models.Model;
import edu.warbot.fsm.editor.models.ModelCondition;
import edu.warbot.fsm.editor.models.ModelState;
import edu.warbot.fsm.editor.models.ModeleBrain;
import edu.warbot.fsm.editor.settings.GenericConditionSettings;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;

public class FsmXmlSaver extends FsmXmlParser {

    Document document;
    FileWriter fileWriter;

    public void saveFSM(Model modele, File file) {

        try {
            this.fileWriter = new FileWriter(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element rootBrains = new Element(Brains);

        document = new Document(rootBrains);

        for (ModeleBrain currentBrain : modele.getModelsBrains()) {
            Element brain = new Element("Brain");
            rootBrains.addContent(brain);

            brain.addContent(new Element(AgentType).setText(currentBrain.getAgentTypeName()));

            brain.addContent(getContentStatesForBrain(currentBrain));
            brain.addContent(getContentConditionForBrain(currentBrain));
        }

        XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());

        try {
            sortie.output(document, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveFSM(Model modele, String fileName) {
        saveFSM(modele, new File(fileName));
    }

    private Element getContentStatesForBrain(ModeleBrain brain) {
        Element states = new Element(States);
        states.addContent(getContentForFirstState(brain));


        for (ModelState currentState : brain.getStates()) {
            states.addContent(getContentForState(currentState));
        }
        return states;
    }

    private Element getContentForFirstState(ModeleBrain brain) {
        Element elem = new Element(FirstState);
        if (brain.getFirstState() != null)
            elem.setText(brain.getFirstState().getName());
        return elem;
    }

    private Element getContentConditionForBrain(ModeleBrain brain) {
        Element elemConditions = new Element(Conditions);

        for (ModelCondition condition : brain.getConditions()) {
            elemConditions.addContent(getContentForCondition(condition));
        }
        return elemConditions;
    }

    private Element getContentForState(ModelState state) {
        Element elemState = new Element(State);

        elemState.addContent(new Element("Name").setText(state.getName()));
        elemState.addContent(new Element("Plan").setText(state.getPlanName().toString()));

        elemState.addContent(getContentConditionsOutIDForState(state));
        elemState.addContent(getContentPlanSettings(state));

        return elemState;
    }

    private Element getContentPlanSettings(ModelState state) {
        Element elemPlanSetting = new Element(PlanSettings);

        GenericPlanSettings planSet = state.getPlanSettings();
        if (planSet == null)
            planSet = new GenericPlanSettings();

        Field[] fields = planSet.getClass().getDeclaredFields();

        String fieldValueString = null;
        for (int i = 0; i < fields.length; i++) {
            try {

                if (fields[i].get(planSet) == null)
                    fieldValueString = "";
                else
                    fieldValueString = String.valueOf(fields[i].get(planSet));

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            elemPlanSetting.addContent(
                    new Element(fields[i].getName()).setText(fieldValueString));
        }
        return elemPlanSetting;
    }

    private Element getContentForCondition(ModelCondition cond) {
        Element elemCond = new Element(Condition);

        elemCond.addContent(new Element(Name).setText(cond.getName()));
        elemCond.addContent(new Element(Type).setText(cond.getType().toString()));
        elemCond.addContent(new Element(StateOutID).setText(cond.getStateDestination().getName()));

        elemCond.addContent(getContentConditionSettings(cond));

        return elemCond;
    }

    //TODO fusionner cette méthod avec celle des plans
    private Element getContentConditionSettings(ModelCondition modelCond) {
        Element elemCondSetting = new Element(ConditionSettings);

        GenericConditionSettings planSet = modelCond.getConditionSettings();
        if (planSet == null)
            planSet = new GenericConditionSettings();

        Field[] fields = planSet.getClass().getDeclaredFields();

        String fieldValueString = null;
        for (int i = 0; i < fields.length; i++) {
            try {
                //Pour les tableaux
                if (fields[i].getType().isArray()) {
                    if (fields[i].get(planSet) == null)
                        fieldValueString = "";
                    else {
                        Object[] fieldValues = (Object[]) fields[i].get(planSet);
                        fieldValueString = Arrays.toString(fieldValues);
                    }

                } else { //Pour les valeurs simples
                    if (fields[i].get(planSet) == null)
                        fieldValueString = "";
                    else
                        fieldValueString = String.valueOf(fields[i].get(planSet));
                }

            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            elemCondSetting.addContent(
                    new Element(fields[i].getName()).setText(fieldValueString));
        }
        return elemCondSetting;
    }

    private Element getContentConditionsOutIDForState(ModelState state) {
        Element elemconditions = new Element(ConditionsOutID);

        for (ModelCondition currentCond : state.getConditionsOut()) {
            elemconditions.addContent(new Element(ConditionOutID).setText(currentCond.getName()));
        }

        return elemconditions;
    }

}
