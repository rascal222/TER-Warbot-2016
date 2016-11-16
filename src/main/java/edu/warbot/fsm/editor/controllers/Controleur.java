package edu.warbot.fsm.editor.controllers;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.fsm.editor.FSMModelRebuilder;
import edu.warbot.fsm.editor.models.Model;
import edu.warbot.fsm.editor.models.ModelCondition;
import edu.warbot.fsm.editor.models.ModelState;
import edu.warbot.fsm.editor.models.ModeleBrain;
import edu.warbot.fsm.editor.parsing.xml.FsmXmlParser;
import edu.warbot.fsm.editor.parsing.xml.FsmXmlReader;
import edu.warbot.fsm.editor.parsing.xml.FsmXmlSaver;
import edu.warbot.fsm.editor.settings.GenericConditionSettings;
import edu.warbot.fsm.editor.settings.GenericPlanSettings;
import edu.warbot.fsm.editor.views.View;
import edu.warbot.fsm.editor.views.ViewBrain;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class Controleur {
    public Model model;
    public View view;
    File fileToSave = null;
    private ArrayList<ControleurBrain> controleursBrains = new ArrayList<>();

    public Controleur(Model modele, View view) {
        this.model = modele;
        this.view = view;

        createControleurBrains();

        placeListenerOnMenuBar();

    }

    private void createControleurBrains() {
//		controleursBrains = new ArrayList<>();

        for (ViewBrain viewBrain : this.view.getViewBrains()) {
            this.controleursBrains.add(new ControleurBrain(viewBrain.getModel(), viewBrain));
        }
    }

    public void reloadModel() {
        model.update();

        //On donne le nouveau model à la vu
        view.loadModel(this.model);

        //La vu connait son model (à ce niveau ca ne sert a rien mais ca ne gene rien non plus pour 'linstant on le laisse
        model.setView(view);

        this.update();

    }

    private void update() {
        controleursBrains.clear();
        createControleurBrains();
    }

    public ControleurBrain getControleurBrain(WarAgentType agentType) {
        for (ControleurBrain controleurBrain : controleursBrains) {
            if (controleurBrain.modeleBrain.getAgentType().equals(agentType))
                return controleurBrain;
        }
        System.err.println("Controleur : Impossible to find controleurBrain for agent type " + agentType);
        return null;
    }

    public void createControleursBrains(WarAgentType agentType) {
        ModeleBrain mb = new ModeleBrain(agentType);
        ViewBrain vb = new ViewBrain(mb);
        ControleurBrain cb = new ControleurBrain(mb, vb);

        this.model.addModelBrain(mb);
        this.view.addViewBrain(vb);

        this.controleursBrains.add(cb);
    }

    private void placeListenerOnMenuBar() {
        view.getMenuBarItemSave().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventMenuBarItemSave();
            }
        });
        view.miSaveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventMenuBarItemSaveAs();
            }
        });
        view.getMenuBarItemLoad().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventMenuBarItemOpen();
            }
        });
        view.getMenuBarItemTest().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventMenuBarItemTest();
            }
        });
        view.getMenuBarItemPrint().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventMenuBarItemPrint();
            }
        });
        view.miClean.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eventMenuBarItemClean();
            }
        });
    }

    public void eventMenuBarItemClean() {
        //delete no unique IDs
        for (ModeleBrain modelBrain : this.model.getModelsBrains()) {

            //Check no null pointeur
            ArrayList<ModelState> states = modelBrain.getStates();
            for (ModelState modState : states) {
                if (modState == null)
                    modelBrain.removeState(modState);
                if (modState.getConditionsOut() == null) {
                    modelBrain.removeState(modState);
                }
            }
            //Check no null pointeur
            ArrayList<ModelCondition> condition = modelBrain.getConditions();
            for (ModelCondition modcond : condition) {
                if (modcond == null)
                    modelBrain.removeCondition(modcond);
                if (modcond.getStateDestination() == null) {
                    modelBrain.removeCondition(modcond);
                }
                if (modcond.getStateSource() == null) {
                    modelBrain.removeCondition(modcond);
                }
            }

            //Delete non unique name

            //State
            ArrayList<ModelState> realStates = new ArrayList<>();
            for (ModelState modState : modelBrain.getStates()) {
                realStates.add(modState);
            }

            //Cond
            ArrayList<ModelCondition> realConds = new ArrayList<>();
            for (ModelCondition modCond : modelBrain.getConditions()) {
                realConds.add(modCond);
            }

            for (ModelState modelState : realStates) {
                for (ModelState modelState2 : realStates) {
                    if (!modelState.equals(modelState2) && modelState.getName().equals(modelState2.getName())) {
                        modelBrain.removeState(modelState2);
                    }
                }
            }

            //Cond ID
            ArrayList<ModelCondition> conds = new ArrayList<>();
            for (ModelCondition modCond : modelBrain.getConditions()) {
                conds.add(modCond);
            }

            for (ModelCondition cond1 : conds) {
                for (ModelCondition cond2 : conds) {
                    if (!cond1.equals(cond2) && cond1.getName().equals(cond2.getName())) {
                        modelBrain.removeCondition(cond2);
                    }
                }
            }
        }
        JOptionPane.showMessageDialog(null, "fsm clean sucessfull", "Sucess", JOptionPane.INFORMATION_MESSAGE);
    }

    public void eventMenuBarItemSave() {
//
        FsmXmlSaver fsmSaver = new FsmXmlSaver();

        if (this.fileToSave != null)
            fsmSaver.saveFSM(model, this.fileToSave);
        else
            eventMenuBarItemSaveAs();

        System.out.println("Controleur : Configuration file exported successfull");

//		JOptionPane.showMessageDialog(null, "Save sucessfull", "Sucess", JOptionPane.INFORMATION_MESSAGE);
    }

    public void eventMenuBarItemSaveAs() {

        JFileChooser fc = new JFileChooser(fileToSave);
        fc.setFileFilter(new FileNameExtensionFilter(".xml", "xml"));

        fc.setCurrentDirectory(new File("").getAbsoluteFile());
        fc.setSelectedFile(new File(FsmXmlParser.xmlConfigurationDefaultFilename));

        int result = fc.showSaveDialog(null);


        if (result == JFileChooser.APPROVE_OPTION) {
            this.fileToSave = fc.getSelectedFile();
            this.eventMenuBarItemSave();
        }

    }

    public void eventMenuBarItemTest() {

        boolean isValid = true;

        //For each brains
        for (ModeleBrain modelBrain : this.model.getModelsBrains()) {

            if (modelBrain.getFirstState() == null) {
                JOptionPane.showMessageDialog(this.view,
                        "Warning there is no first state for <" + modelBrain.getAgentTypeName() + "> agent",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE);
                isValid = false;
            }

            //Check no null pointeur states
            for (ModelState modState : modelBrain.getStates()) {
                if (modState.getConditionsOut() == null) {
                    JOptionPane.showMessageDialog(this.view,
                            "Warning state <" + modState.getName() + "> have no conditions !",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
            //Check no null pointeur conditions
            for (ModelCondition modcond : modelBrain.getConditions()) {
                if (modcond.getStateDestination() == null) {
                    JOptionPane.showMessageDialog(this.view,
                            "Error condition <" + modcond.getName() + "> have no destination !",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                if (modcond.getStateSource() == null) {
                    JOptionPane.showMessageDialog(this.view,
                            "Warning condition <" + modcond.getName() + "> have no source !",
                            "Warning",
                            JOptionPane.WARNING_MESSAGE);
                }
            }

            //State ID
            ArrayList<String> stateName = new ArrayList<>();
            for (ModelState modState : modelBrain.getStates()) {
                if (stateName.contains(modState.getName())) {
                    JOptionPane.showMessageDialog(this.view,
                            "State name <" + modState.getName() + "> have to be unique !",
                            "ID error",
                            JOptionPane.ERROR_MESSAGE);
                    isValid = false;
                }
                stateName.add(modState.getName());

            }

            //Cond ID
            ArrayList<String> CondName = new ArrayList<>();
            for (ModelCondition modCond : modelBrain.getConditions()) {
                if (CondName.contains(modCond.getName())) {
                    JOptionPane.showMessageDialog(this.view,
                            "Condition  name <" + modCond.getName() + "> have to be unique !",
                            "ID error",
                            JOptionPane.ERROR_MESSAGE);
                    isValid = false;
                }
                CondName.add(modCond.getName());
            }
        }

//		//Check if declared ID exist
//		for (ModeleBrain modelBrain: this.model.getModelsBrains()) {
//
//			//State ID
//			ArrayList<String> stateName = new ArrayList<>();
//			ArrayList<String> condOutID = new ArrayList<>();
//			for (ModelState modState : modelBrain.getStates()) {
//				stateName.add(modState.getName());
//				for (String string : modState.getConditionsOutID()) {
//					condOutID.add(string);
//				}
//			}
//
//			//Cond ID
//			ArrayList<String> CondName = new ArrayList<>();
//			ArrayList<String> stateOutID = new ArrayList<>();
//			for (ModelCondition modCond : modelBrain.getConditions()) {
//				stateOutID.add(modCond.getStateOutId());
//				CondName.add(modCond.getName());
//			}
//
//			for (String string : stateOutID) {
//				if(! stateName.contains(string)){
//					JOptionPane.showMessageDialog(this.view,
//						    "Condition with state out ID " + string + " have no associated state",
//						    "ID error",
//						    JOptionPane.ERROR_MESSAGE);
//					isValid = false;
//				}
//			}
//
//			for (String string : condOutID) {
//				if(! CondName.contains(string)){
//					JOptionPane.showMessageDialog(this.view,
//						    "State with condition out ID " + string + " have no associated condition",
//						    "ID error",
//						    JOptionPane.ERROR_MESSAGE);
//					isValid = false;
//				}
//			}
//		}

        if (isValid)
            JOptionPane.showMessageDialog(this.view,
                    "Your fsm seen to be corect",
                    "Succes",
                    JOptionPane.INFORMATION_MESSAGE);

        System.out.println("Controleur : Your fsm seen to be valid");

    }

    public void eventMenuBarItemPrint() {
        try {
            printModelInformations(this.model);
            JOptionPane.showMessageDialog(this.view,
                    "Model sucessfull printed in console",
                    "Print sucess",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NullPointerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this.view,
                    "Error during analysing model !",
                    "Print error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void eventMenuBarItemOpen() {
        JFileChooser fc = new JFileChooser(fileToSave);
        fc.setFileFilter(new FileNameExtensionFilter(".xml", "xml"));

        fc.setCurrentDirectory(new File("").getAbsoluteFile());

        if (this.fileToSave != null)
            fc.setSelectedFile(fileToSave);
        else
            fc.setSelectedFile(new File(FsmXmlParser.xmlConfigurationDefaultFilename));

        int result = fc.showSaveDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            this.fileToSave = fc.getSelectedFile();

            FsmXmlReader reader = new FsmXmlReader(fileToSave);
            this.model = reader.getGeneratedFSMModel();

            FSMModelRebuilder rebuilder = new FSMModelRebuilder(this.model);
            this.model = rebuilder.getRebuildModel();

            this.reloadModel();

            System.out.println("Controleur : Configuration file imported successfull");
        }

    }

    private void printModelInformations(Model modeleRead) {
        if (!modeleRead.isRebuild())
            System.out.println("Controleur : WARNING model is not rebuild the print will probabli crash");

        System.out.println("*** Vérification du modele généré dynamiquement pour la fsm ***");
        for (ModeleBrain modBrain : modeleRead.getModelsBrains()) {
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
                Field field[] = planSet.getClass().
                        getDeclaredFields();
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

}
