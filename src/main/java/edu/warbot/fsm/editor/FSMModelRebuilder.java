package edu.warbot.fsm.editor;

import edu.warbot.fsm.editor.models.Model;
import edu.warbot.fsm.editor.models.ModelCondition;
import edu.warbot.fsm.editor.models.ModelState;
import edu.warbot.fsm.editor.models.ModeleBrain;

import java.util.HashMap;

/**
 * Cette classe permet de prendre un Model du FSMEditor qui n'est pas complet
 * Le model contient des ID pour les états destinations des conditions et les conditions des états
 * Cette classe va permettre de reconstuire le modele avec les vrais pointeurs en remplacant les ID
 * par les conditions ou états auquels ils correspondent
 * Passer en parametre du constucteur un modele issue du fichier de configuration
 * Récupérer le modele reconstuit avec getRebuildModel
 *
 * @author Olivier
 */
public class FSMModelRebuilder {

    private Model model;

    /**
     * Appeler le constructeur lancer directement la reconstruction du modele
     *
     * @param model
     */
    public FSMModelRebuilder(Model model) {
        this.model = model;
        rebuildModel();
        this.model.setIsRebuild(true);
    }

    private void rebuildModel() {
        //Rebuild chaque brains
        for (ModeleBrain brain : model.getModelsBrains()) {
            rebuildBrain(brain);
        }
    }

    private void rebuildBrain(ModeleBrain brain) {
        /**
         * Construit une liste d'assotion pour les id (states et conditions) et les objets r�els (pointeurs state et conditions)
         */
        //Construit une liste d'association conditionID - condition pointeur
        //Construit une liste d'association stateID - state pointeur

        HashMap<String, ModelCondition> mapConditionsID = new HashMap<>();
        HashMap<String, ModelState> mapStatesID = new HashMap<>();

        //construit liste états
        for (ModelState state : brain.getStates()) {
            mapStatesID.put(state.getName(), state);
        }

        //Constuit liste condition
        for (ModelCondition condition : brain.getConditions()) {
            mapConditionsID.put(condition.getName(), condition);
        }

        /*** Remplace les ID par des pointeurs ***/
        //Pour chaque état
        brain.setFirstState(mapStatesID.get(brain.getFirstStateID()));
        for (ModelState state : brain.getStates()) {
            rebuildState(state, mapConditionsID);
        }

        //Pour chaque condition
        for (ModelCondition cond : brain.getConditions()) {
            rebuildCondition(cond, mapStatesID);
        }
    }

    private void rebuildState(ModelState state, HashMap<String, ModelCondition> mapConditionsID) {

        //Pour chaque ID conditions de sortie de l'état
        for (String currentConditionID : state.getConditionsOutID()) {
            //On récupère la conditions dans la liste d'association et on l'ajoute au modele
            ModelCondition modCond = mapConditionsID.get(currentConditionID);
            if (modCond == null)
                System.err.println("FSMRebuilder : ERROR no condition found for ID " + currentConditionID);
            state.addConditionOut(modCond);
        }
    }

    private void rebuildCondition(ModelCondition cond, HashMap<String, ModelState> mapStatesID) {
        ModelState state = mapStatesID.get(cond.getStateOutId());
        if (state == null)
            System.err.println("FSMRebuilder : ERROR no state found for ID " + cond.getStateOutId());
        cond.setDestination(state);
    }

    /**
     * @return Le modèle de la fsm reconstuit (correspond au modèle utilisable pour instiancier des FSMBrains)
     */
    public Model getRebuildModel() {
        return this.model;
    }

}
