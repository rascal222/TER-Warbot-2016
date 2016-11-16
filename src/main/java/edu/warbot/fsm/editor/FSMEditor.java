package edu.warbot.fsm.editor;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.fsm.editor.controllers.Controleur;
import edu.warbot.fsm.editor.models.Model;
import edu.warbot.fsm.editor.models.ModelState;
import edu.warbot.fsm.editor.settings.EditorSettings;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;
import edu.warbot.fsm.editor.views.View;

public class FSMEditor {

    @SuppressWarnings("unused")
	public FSMEditor() {
        Model model = new Model();

        createBasicFSM(model);

        View view = new View(model);

        Controleur controleur = new Controleur(model, view);

    }

    private void createBasicFSM(Model model) {
        model.createModelBrain(WarAgentType.WarBase);
        model.createModelBrain(WarAgentType.WarExplorer);
        model.createModelBrain(WarAgentType.WarRocketLauncher);
        model.createModelBrain(WarAgentType.WarEngineer);
        model.createModelBrain(WarAgentType.WarTurret);
        model.createModelBrain(WarAgentType.WarKamikaze);

        model.getModelBrain(WarAgentType.WarBase).addState(
                new ModelState("State Idle", EditorSettings.WarPlanIdle, new GenericPlanSettings()));
        model.getModelBrain(WarAgentType.WarExplorer).addState(
                new ModelState("State Idle", EditorSettings.WarPlanIdle, new GenericPlanSettings()));
        model.getModelBrain(WarAgentType.WarRocketLauncher).addState(
                new ModelState("State Wiggle", EditorSettings.WarPlanIdle, new GenericPlanSettings()));
        model.getModelBrain(WarAgentType.WarEngineer).addState(
                new ModelState("State Wiggle", EditorSettings.WarPlanIdle, new GenericPlanSettings()));
        model.getModelBrain(WarAgentType.WarTurret).addState(
                new ModelState("State Idle", EditorSettings.WarPlanIdle, new GenericPlanSettings()));
        model.getModelBrain(WarAgentType.WarKamikaze).addState(
                new ModelState("State Wiggle", EditorSettings.WarPlanIdle, new GenericPlanSettings()));
    }
}
