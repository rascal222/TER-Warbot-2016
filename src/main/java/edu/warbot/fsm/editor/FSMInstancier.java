package edu.warbot.fsm.editor;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.brains.WarBrain;
import edu.warbot.fsm.WarEtat;
import edu.warbot.fsm.WarFSM;
import edu.warbot.fsm.condition.WarCondition;
import edu.warbot.fsm.editor.models.Model;
import edu.warbot.fsm.editor.models.ModelCondition;
import edu.warbot.fsm.editor.models.ModelState;
import edu.warbot.fsm.editor.models.ModeleBrain;
import edu.warbot.fsm.plan.WarPlan;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * Permet de générer et d'instanicer un objet de type fsm grâce à un modele de fsm
 * Prend en parametre un modele de fsm
 * Appeler la méthode getBrainControleurForAgent pour récupérer l'instance du brain d'un agent (instance d'une sous classe de warBrain) (le controleur renvoyé peut etre assimilé à un controleur issue d'une équipe classique)
 *
 * @author Olivier
 */
public class FSMInstancier<BrainType extends WarBrain> {

    WarFSM<BrainType> fsm = new WarFSM<>();

    Model model;

    //HashMap pour assicier les états et condition avec leurs nom
    //Ca pourrait plutot etre des hashmap de ModelState associé au warState
    HashMap<String, WarEtat<BrainType>> hashMapState = new HashMap<>();
    HashMap<String, WarCondition<BrainType>> hashMapCond = new HashMap<>();

    public FSMInstancier(Model modele) {
        if (modele == null)
            System.err.println("FSMInstancier : model is null impossible to build fsm");

        this.model = modele;

        if (!modele.isRebuild()) {
            System.out.println("FSMInstancier : model is not rebuild, asking for rebuilding model...");
            FSMModelRebuilder fsmModeleRebuilder = new FSMModelRebuilder(modele);
            this.model = fsmModeleRebuilder.getRebuildModel();
        }

    }

    /**
     * @param agentType le type de l'agent que l'on souhaite récupére le brain
     * @param brain     l'adapteur de l'agent dont on souhaite récupérer le BrainContoleur
     * @return une instance de BrainControleur pour une fsm (semble à un brainController du équipe classique) (sous classe de WarBrain)
     */
    @SuppressWarnings("rawtypes")
	public WarFSM getBrainControleurForAgent(WarAgentType agentType, WarBrain brain) {
        System.out.println("FSMInstancier begining instanciation for " + agentType + "...");

        //On recupère le modeleBrain qui correspond à l'agentType
        ModeleBrain modelBrain = this.model.getModelBrain(agentType);

        //On commence par ajouter tous les états à la fsm
        for (ModelState modelState : modelBrain.getStates()) {
            WarEtat<BrainType> warState = getGenerateWarState(modelState, brain);

            //Ajoute l'état à la fsm
            fsm.addEtat(warState);

            //Ajoute l'état dans la hashMap d'état
            hashMapState.put(warState.getName(), warState);
        }
        //Remet l'état de départ
        if (modelBrain.getFirstState() != null)
            fsm.setFirstEtat(hashMapState.get(modelBrain.getFirstState().getName()));

        //On ajoute ensuite les conditions
        for (ModelCondition modelCond : modelBrain.getConditions()) {
            WarCondition<BrainType> warCond = getGeneratedCondition(modelCond, brain);

            //Ajoute l'état de destination de la condition
            WarEtat<BrainType> etat = hashMapState.get(modelCond.getStateDestination().getName());
            warCond.setDestination(etat);

            //Ajoute la condition à la HashMap
            hashMapCond.put(warCond.getName(), warCond);
        }

        //Reparcourir tous les états pour leurs ajouter leurs conditions de sorties
        //(C'était pas possible avant car les conditions n'existaient pas
        for (ModelState modelState : modelBrain.getStates()) {
            WarEtat<BrainType> warEtat = hashMapState.get(modelState.getName());

            for (ModelCondition modelCond : modelState.getConditionsOut()) {
                WarCondition<BrainType> warCond = hashMapCond.get(modelCond.getName());

                warEtat.addCondition(warCond);
            }
        }

        System.out.println("FSMInstancier : lancement de l'initializeFSM de la fsm");
        fsm.initFSM();
        System.out.println("FSMInstancier : initializeFSM terminé avec succes");

        return fsm;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private WarCondition<BrainType> getGeneratedCondition(ModelCondition modelCond,
                                                          WarBrain brain) {

        //Instancie le plan
        WarCondition<BrainType> instanciateCond = null;
        Class typeOfAdapter = null;
        Class c = null;
        try {

            c = Class.forName(modelCond.getConditionType());

            //Récupère le constructeur
            typeOfAdapter = c.getConstructors()[0].getParameterTypes()[1];

            instanciateCond = (WarCondition<BrainType>) c
                    .getConstructor(String.class, typeOfAdapter, modelCond.getConditionSettings().getClass())
                    .newInstance(modelCond.getName(), brain, modelCond.getConditionSettings());

        } catch (NoSuchMethodException | SecurityException
                | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            System.err.println("*** ERROR in dynamic instanciation of generated model check everything:");
            System.err.println("* Check constructor in WarPlan, WarCondition, WarReflexe");
            System.err.println("* Check attribut usage in subclass of previews and in " + modelCond.getConditionType());

            System.err.println("ERROR during instanciate WarCondition with class name " + modelCond.getConditionType() + " check name, constructor, classPath, etc...");
            System.err.println("Objects send Type : Name : " + String.class + ", Adapter : " + typeOfAdapter + ", WarConditionSettings : " + modelCond.getConditionSettings().getClass());
            System.err.println("Objects send Object : Name : " + modelCond.getName() + ", Adapter : " + brain + ", WarConditionSettings : " + modelCond.getConditionSettings());

            try {
                System.err.println("Objects expected Type : Name : " + Class.forName(modelCond.getConditionType()).getConstructors()[0].getParameterTypes()[0] +
                        ", Adapter : " + Class.forName(modelCond.getConditionType()).getConstructors()[0].getParameterTypes()[1] +
                        ", WarPlanSettings : " + Class.forName(modelCond.getConditionType()).getConstructors()[0].getParameterTypes()[2]);
            } catch (SecurityException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }

            if (modelCond.getConditionType() != null)
                JOptionPane.showMessageDialog(null, "Error during dynamic instanciation of condition <" + modelCond.getConditionType() + "> \nPlease check attribut of this condition, attribut in the editor, and generic type of the condition", "Instanciation error", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "Error during dynamic instanciation of condition. Condition have no loader name", "Internal error", JOptionPane.ERROR_MESSAGE);

        }

        return instanciateCond;
    }

    private WarEtat<BrainType> getGenerateWarState(
            ModelState modelState, WarBrain brain) {
        //Récupère le plan
        WarPlan<BrainType> warPlan =
                getGenerateWarPlan(modelState, brain);

        //Crée l'état
        WarEtat<BrainType> warState =
                new WarEtat<>(modelState.getName(), warPlan);

        return warState;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private WarPlan<BrainType> getGenerateWarPlan(
            ModelState modelState, WarBrain brain) {

        //Instancie le plan
        WarPlan<BrainType> instanciatePlan = null;
        try {

            Class c = Class.forName(modelState.getPlanName());

            //Récupère le constructeur
            Class typeOfAdapter = c.getConstructors()[0].getParameterTypes()[0];

            instanciatePlan = (WarPlan<BrainType>) c
                    .getConstructor(typeOfAdapter, modelState.getPlanSettings().getClass())
                    .newInstance(brain, modelState.getPlanSettings());

        } catch (NoSuchMethodException | SecurityException
                | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
            System.err.println("*** ERROR in dynamic instanciation of generated model check everything :");
            System.err.println("* Check constructor in WarPlan");
            System.err.println("* Check attribut usage in subclass of previews and in " + modelState.getPlanName());

            System.err.println("ERROR during instanciate WarPlan with class name " + modelState.getPlanName() + " check name, constructor, classPath, etc...");
            System.err.println("Objects send type : Adapter : " + brain.getClass() + ", WarPlanSettings : " + modelState.getPlanSettings().getClass());
            System.err.println("Objects send instance : Adapter : " + brain + ", WarPlanSettings : " + modelState.getPlanSettings());

            try {
                System.err.println("Objects expected : Adapter : " + Class.forName(modelState.getPlanName()).getConstructors()[0].getParameterTypes()[0]
                        + ", WarPlanSettings : " + Class.forName(modelState.getPlanName()).getConstructors()[0].getParameterTypes()[1]);
            } catch (SecurityException | ClassNotFoundException e1) {
                e1.printStackTrace();
            }

            if (modelState.getPlanName() != null)
                JOptionPane.showMessageDialog(null, "Error during dynamic instanciation of plan <" + modelState.getPlanName() + "> \nPlease check attribut of this plan, attribut in the editor, and generic type of the plan", "Instanciation error", JOptionPane.ERROR_MESSAGE);
            else
                JOptionPane.showMessageDialog(null, "Error during dynamic instanciation of plan. Condition have no loader name", "Internal error", JOptionPane.ERROR_MESSAGE);

        }

        return instanciatePlan;
    }


}
