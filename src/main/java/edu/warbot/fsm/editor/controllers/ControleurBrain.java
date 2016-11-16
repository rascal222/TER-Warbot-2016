package edu.warbot.fsm.editor.controllers;

import edu.warbot.fsm.editor.dialogues.DialogueCondSetting;
import edu.warbot.fsm.editor.dialogues.DialogueStateSetting;
import edu.warbot.fsm.editor.models.ModelCondition;
import edu.warbot.fsm.editor.models.ModelState;
import edu.warbot.fsm.editor.models.ModeleBrain;
import edu.warbot.fsm.editor.panels.PanelCondition;
import edu.warbot.fsm.editor.panels.PanelState;
import edu.warbot.fsm.editor.settings.GenericConditionSettings;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;
import edu.warbot.fsm.editor.views.ViewBrain;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ControleurBrain {

    public ModeleBrain modeleBrain;
    public ViewBrain viewBrain;

    public ControleurBrain(ModeleBrain modele, ViewBrain view) {
        this.modeleBrain = modele;
        this.viewBrain = view;

        placeListenerOnView();
        placeListenerOnPanel();

    }

    private void placeListenerOnPanel() {
        MouseListenerPanelCenter mouseListener = new MouseListenerPanelCenter(this);
        viewBrain.getViewEditor().addMouseListener(mouseListener);
        viewBrain.getViewEditor().addMouseMotionListener(mouseListener);
    }

    private void placeListenerOnView() {

        viewBrain.getButtonAddState().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                eventAddState();
            }
        });

        viewBrain.getButtonEditState().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                eventEditState();
            }
        });

        viewBrain.getButtonAddCond().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                eventAddCond();
            }
        });

        viewBrain.getButtonDelState().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                eventDelState();
            }
        });
        viewBrain.getButtonEditCond().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                eventEditCond();
            }
        });
        viewBrain.getButtonDelCond().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                eventDelCond();
            }
        });
        viewBrain.jListCondition.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                eventListeConditionEdition(e);
            }
        });
        viewBrain.jListState.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                eventListeStateEdition(e);
            }
        });

    }

    private void eventListeStateEdition(ListSelectionEvent e) {
        ModelState firtState = modeleBrain.getFirstState();

        if (firtState != null)
            firtState.getViewState().setFirstState(false);

        String stringCond = viewBrain.jListState.getSelectedValue();
        ModelState modelState = this.getModelStateWithName(stringCond);

        if (modelState != null) {
            modeleBrain.setFirstState(modelState);
            modelState.getViewState().setFirstState(true);
        }

        this.viewBrain.getViewEditor().repaint();
    }

    private void eventListeConditionEdition(ListSelectionEvent e) {

        PanelCondition p = this.viewBrain.getViewEditor().getSelectedCondition();

        if (p != null)
            p.isSelected = false;

        String stringCond = viewBrain.jListCondition.getSelectedValue();
        PanelCondition panelCond = this.getPanelConditionWithName(stringCond);

        viewBrain.getViewEditor().setSelectedCondition(panelCond);

        if (panelCond != null) {
            panelCond.isSelected = true;
        }

        this.viewBrain.getViewEditor().repaint();
    }

    private void eventAddState() {
        GenericPlanSettings planSetting = new GenericPlanSettings();

        DialogueStateSetting d = new DialogueStateSetting(this.viewBrain, planSetting);
        d.createDialog();

        if (d.isValideComponent()) {

            ModelState s = new ModelState(d.getStateName(), d.getPlanName(), d.getPlanSettings());

            this.addState(s);

            viewBrain.getViewEditor().repaint();
        }
    }

    private void eventEditState() {
        ArrayList<PanelState> panelStates = this.viewBrain.getViewEditor().getSelectedStates();

        if (panelStates != null && panelStates.size() == 1) {

            ModelState modelState = panelStates.get(0).getModelState();

            DialogueStateSetting d = new DialogueStateSetting(this.viewBrain, modelState);
            d.createDialog();

            if (d.isValideComponent()) {

                modelState.setName(d.getStateName());
                modelState.setPlanName(d.getPlanName());
                modelState.setPlanSettings(d.getPlanSettings());

                viewBrain.getViewEditor().repaint();
            }
        } else {
            JOptionPane.showMessageDialog(this.viewBrain,
                    "One state must be selected",
                    "Selection error",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void eventDelState() {
        ArrayList<PanelState> panelToDelet = this.viewBrain.getViewEditor().getSelectedStates();

        if (panelToDelet != null && panelToDelet.size() == 1) {

            deleteState(panelToDelet.get(0));

            repaint();

        } else {
            JOptionPane.showMessageDialog(this.viewBrain,
                    "One state must be selected",
                    "Selection error",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void repaint() {
        viewBrain.getViewEditor().repaint();
    }

    private void eventAddCond() {

        if (this.viewBrain.getViewEditor().isTwoStatesSelected()) {

            GenericConditionSettings condSett = new GenericConditionSettings();

            DialogueCondSetting d = new DialogueCondSetting(this.viewBrain, condSett);
            d.createDialog();

            if (d.isValideComponent()) {

                PanelState panelSource;
                PanelState panelDest;
                ModelState modeleStateSource;
                ModelState modeleStateDest;

                panelSource = this.viewBrain.getViewEditor().getFirstSelectedState();
                panelDest = this.viewBrain.getViewEditor().getSecondeSelectedState();
                modeleStateSource = panelSource.getModelState();
                modeleStateDest = panelDest.getModelState();

                //Crée le nouveau modele condition
                ModelCondition mc =
                        new ModelCondition(d.getConditionName(), d.getConditionType(), condSett);
                modeleStateSource.addConditionOut(mc);
                mc.setDestination(modeleStateDest);

                addCondition(mc);
            }

        } else {
            JOptionPane.showMessageDialog(this.viewBrain,
                    "Two conditions must be selected",
                    "Selection error",
                    JOptionPane.INFORMATION_MESSAGE);
        }

        viewBrain.getViewEditor().repaint();
    }

    private void eventEditCond() {

        PanelCondition panelCondition = viewBrain.getViewEditor().getSelectedCondition();

        if (panelCondition == null) {
            JOptionPane.showMessageDialog(this.viewBrain,
                    "One condition must be selected",
                    "Selection error",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {

            ModelCondition modelCond = panelCondition.getModele();

            DialogueCondSetting dialogCondSetting =
                    new DialogueCondSetting(this.viewBrain, modelCond);
            dialogCondSetting.createDialog();

            if (dialogCondSetting.isValideComponent()) {
                modelCond.setName(dialogCondSetting.getConditionName());
                modelCond.setConditionType(dialogCondSetting.getConditionType());
                modelCond.setConditionSettings(dialogCondSetting.getConditionSettings());
            }

            viewBrain.updateSelectedCondition();
        }
    }

    private void eventDelCond() {
        PanelCondition panelCondition = viewBrain.getViewEditor().getSelectedCondition();

        if (panelCondition == null) {
            JOptionPane.showMessageDialog(this.viewBrain,
                    "One condition must be selected",
                    "Selection error",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            deleteCondition(panelCondition);
        }
    }

    private PanelCondition getPanelConditionWithName(String s) {
        for (PanelCondition p : this.viewBrain.getViewEditor().getPanelconditions()) {
            if (p.getModele().getName().equals(s))
                return p;
        }
        return null;
    }

    private ModelState getModelStateWithName(String s) {
        for (ModelState p : this.modeleBrain.getStates()) {
            if (p.getName().equals(s))
                return p;
        }
        return null;
    }


    public void addState(ModelState state) {
        //Dit au model le nouvel état
        this.modeleBrain.addState(state);

        //Dit à la vu le nouvel état
        this.viewBrain.addState(state);
    }


    public void deleteState(PanelState panelState) {
        modeleBrain.removeState(panelState.getModelState());

        viewBrain.removeState(panelState);
    }

    public void deleteCondition(PanelCondition panel) {
        this.modeleBrain.removeCondition(panel.getModele());

        this.viewBrain.removeCondition(panel);
    }

    public void addCondition(ModelCondition condition) {

        //Dit au modele d'ajouter la nouvelle condition
        this.modeleBrain.addCondition(condition);

        //Dit à la vu d'ajouter la nouvelle condition
        this.viewBrain.addCondition(condition);

    }


}
