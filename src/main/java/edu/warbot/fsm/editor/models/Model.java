package edu.warbot.fsm.editor.models;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.fsm.editor.settings.GenericConditionSettings;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;
import edu.warbot.fsm.editor.views.View;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class Model {

    ArrayList<ModeleBrain> modelBrains = new ArrayList<>();

    private boolean isRebuild = false;
    private View view;

    public void update() {
        //TODO ici a prioris pour l'instant ya rien a faire
    }

    public void createModelBrain(WarAgentType agentType) {
        this.modelBrains.add(new ModeleBrain(agentType));
    }

    public ArrayList<ModeleBrain> getModelsBrains() {
        return modelBrains;
    }

    public ModeleBrain getModelBrain(WarAgentType agentType) {
        for (ModeleBrain modeleBrain : modelBrains) {
            if (modeleBrain.getAgentType().equals(agentType))
                return modeleBrain;
        }
        return null;
    }

    public void addModelBrain(ModeleBrain modelBrain) {
        this.modelBrains.add(modelBrain);
    }

    public boolean isRebuild() {
        return this.isRebuild;
    }

    public void setIsRebuild(boolean b) {
        isRebuild = b;
    }

    public void printInformations() {
        if (!this.isRebuild())
            System.out.println("Controleur : WARNING model is not rebuild the print will probabli crash");

        System.out.println("*** Vérification du modele généré dynamiquement pour la fsm ***");
        for (ModeleBrain modBrain : this.getModelsBrains()) {
            System.out.println("* Traitement du modele pour le type d'agent " + modBrain.getAgentTypeName() + " *");

            System.out.println("Liste des états " + modBrain.getStates().size());
            for (ModelState modState : modBrain.getStates()) {
                System.out.println("\tEtat : Name=" + modState.getName() + " plan=" + modState.getPlanName());
                System.out.println("\tConditions de sorties ID : " + modState.getConditionsOutID().size());
                for (String condID : modState.getConditionsOutID()) {
                    System.out.println("\t\t" + condID);
                }
                System.out.println("\tConditions de sorties objet: " + modState.getConditionsOut().size());
                for (ModelCondition condMod : modState.getConditionsOut()) {
                    System.out.println("\t\tName=\"" + condMod.getName() + "\"");
                }

                //Afichage des parametres du plan
                GenericPlanSettings planSet = modState.getPlanSettings();
                Field field[] = planSet.getClass().getDeclaredFields();
                System.out.println("\tPlan settings : ");
                for (int i = 0; i < field.length; i++) {
                    try {
                        String fieldValue = null;
                        if (field[i].getType().isArray()) {
                            Object[] arrayO = (Object[]) field[i].get(planSet);
                            fieldValue = Arrays.toString(arrayO);
                        } else
                            fieldValue = String.valueOf(field[i].get(planSet));

                        System.out.println("\t\t" + field[i].getName() + "=" + fieldValue);

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("Liste des conditions " + modBrain.getConditions().size());
            for (ModelCondition modCond : modBrain.getConditions()) {
                //TODO la ca va plnater car il y aura l'id de l'état destination mais pas le pointeur vers l'objet de l'état
                System.out.println("\tCondition : Name=" + modCond.getName() + " Type=" + modCond.getType());
                System.out.println("\tEtat destination ID : " + modCond.getStateOutId());
                System.out.println("\tEtat destination objet : Name=" + modCond.getStateDestination().getName());

                //Affichage des conditions settings
                GenericConditionSettings condSet = modCond.getConditionSettings();
                Field field[] = condSet.getClass().getDeclaredFields();
                System.out.println("\tCondition settings : ");
                for (int i = 0; i < field.length; i++) {
                    try {
                        String fieldValue = null;
                        if (field[i].getType().isArray()) {
                            Object[] arrayO = (Object[]) field[i].get(condSet);
                            fieldValue = Arrays.toString(arrayO);
                        } else
                            fieldValue = String.valueOf(field[i].get(condSet));

                        System.out.println("\t\t" + field[i].getName() + "=" + fieldValue);

                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public View getView() {
        return this.view;
    }

    /**
     * * Le model connait sa vu **
     */

    public void setView(View view) {
        this.view = view;
    }

}
