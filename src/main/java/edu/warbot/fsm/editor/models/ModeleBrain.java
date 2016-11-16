package edu.warbot.fsm.editor.models;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.fsm.editor.views.ViewBrain;

import java.util.ArrayList;

public class ModeleBrain {

    /**
     * * Les modeles brains connaissent leurs vue **
     */
    ViewBrain viewBrain;
    private WarAgentType agentType;
    private ModelState firstState;
    private String firstStateID;
    private ArrayList<ModelState> sates = new ArrayList<>();
    private ArrayList<ModelCondition> conditions = new ArrayList<>();

    public ModeleBrain(WarAgentType agentType) {
        this.agentType = agentType;
    }

    public void addState(ModelState d) {
        this.sates.add(d);
        if (this.firstState == null)
            this.firstState = d;
    }

    public void addCondition(ModelCondition c) {
        this.conditions.add(c);
    }

    public ArrayList<ModelState> getStates() {
        return this.sates;
    }

    public void removeState(ModelState m) {
        for (ModelCondition modelCondition : m.getConditionsOut()) {
            modelCondition.setSource(null);
        }

        for (ModelCondition modelCondition : conditions) {
            if (modelCondition.getStateDestination().equals(m)) {
                modelCondition.setDestination(null);
                modelCondition.setStateOutId(null);
            }

        }

        this.sates.remove(m);
    }

    public void removeCondition(ModelCondition modeleCond) {
        //Supprime la condition en tant que condition de sortie des états
        for (ModelState modelS : sates) {
            if (modelS.getConditionsOut().contains(modeleCond))
                modelS.removeConditionOut(modeleCond);
        }

        //Supprime la condition
        conditions.remove(modeleCond);

    }

    public ArrayList<ModelCondition> getConditions() {
        return this.conditions;
    }

    public String getAgentTypeName() {
        return this.agentType.toString();
    }

    public WarAgentType getAgentType() {
        return this.agentType;
    }

    public ViewBrain getViewBrain() {
        return viewBrain;
    }

    public void setViewBrain(ViewBrain vb) {
        this.viewBrain = vb;
    }

    public ModelState getFirstState() {
        return firstState;
    }

    public void setFirstState(ModelState modelState) {
        this.firstState = modelState;
    }

    public String getFirstStateID() {
        return this.firstStateID;
    }

    public void setFirstStateID(String first) {
        this.firstStateID = first;
    }

}
