package edu.warbot.fsm.editor.models;

import edu.warbot.fsm.editor.settings.GenericConditionSettings;

public class ModelCondition {

    ModelState modeleSource;
    ModelState modeleDest;
    String stateOutId;
    String conditionType;
    private String name;
    private GenericConditionSettings conditionSettings;

    public ModelCondition(String name, String type, GenericConditionSettings conditionSettings) {
        this.name = name;
        this.conditionType = type;
        this.conditionSettings = conditionSettings;
    }

    public void setSource(ModelState modelState) {
        this.modeleSource = modelState;

//		if(modelState.getConditionsOut() == null || !modelState.getConditionsOut().contains(this))
//			modelState.addConditionOut(this);
    }

    public void setDestination(ModelState d) {
        this.modeleDest = d;
//		d.addConditionIn(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return conditionType;
    }

    public ModelState getStateDestination() {
        return this.modeleDest;
    }

    public ModelState getStateSource() {
        return this.modeleSource;
    }

    public String getStateOutId() {
        return stateOutId;
    }

    public void setStateOutId(String stateOutId) {
        this.stateOutId = stateOutId;
    }

    public GenericConditionSettings getConditionSettings() {
        return this.conditionSettings;
    }

    public void setConditionSettings(GenericConditionSettings conditionSettings) {
        this.conditionSettings = conditionSettings;
    }

    public String getConditionType() {
        return this.conditionType;
    }

    public void setConditionType(String conditionType) {
        this.conditionType = conditionType;
    }

}
