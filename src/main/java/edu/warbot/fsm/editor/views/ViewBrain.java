package edu.warbot.fsm.editor.views;

import edu.warbot.fsm.editor.models.ModelCondition;
import edu.warbot.fsm.editor.models.ModelState;
import edu.warbot.fsm.editor.models.ModeleBrain;
import edu.warbot.fsm.editor.panels.PanelCondition;
import edu.warbot.fsm.editor.panels.PanelEditor;
import edu.warbot.fsm.editor.panels.PanelState;
import org.jfree.ui.tabbedui.VerticalLayout;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ViewBrain extends JPanel {

    private static final long serialVersionUID = 1L;
    /**
     * * Attributs **
     */
    public JList<String> jListCondition;
    public JList<String> jListState;
    private ModeleBrain modeleBrain;
    private PanelEditor panelEditor;
    private DefaultListModel<String> listModeleCond;
    private DefaultListModel<String> listModelState;
    private JButton buttonAddSate;
    private JButton buttonEditState;
    private JButton buttonDelState;
    private JButton buttonAddCond;
    private JButton buttonEditCond;
    private JButton buttonDelCond;

    public ViewBrain(ModeleBrain modeleBrain) {
        this.modeleBrain = modeleBrain;

        createView();
    }

    public void createView() {

        this.setLayout(new BorderLayout());

        // left panel les boutons
        this.add(getPanelLeft(), BorderLayout.WEST);

        // Panel center (l'éditeur)
        panelEditor = new PanelEditor(this.modeleBrain);

        this.add(panelEditor, BorderLayout.CENTER);

        // Panel droite
    }

    private JPanel getPanelLeft() {
        JPanel p = new JPanel(new GridLayout(2, 1));
        p.add(getPanelState());
        p.add(getPanelCondition());
        return p;
    }

    private Component getPanelState() {
        JPanel panel = new JPanel(new VerticalLayout());
        panel.setBorder(new TitledBorder("States"));

        buttonAddSate = new JButton("Add State");
        buttonEditState = new JButton("Edit State");
        buttonDelState = new JButton("Delete State");

        listModelState = new DefaultListModel<>();
        jListState = new JList<>(listModelState);

        jListState.setLayoutOrientation(JList.VERTICAL);
        jListState.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        updateSelectedState();

        panel.add(buttonAddSate);
        panel.add(new JSeparator());
        panel.add(buttonEditState);
        panel.add(new JSeparator());
        panel.add(buttonDelState);
        panel.add(new JSeparator());
        panel.add(new JLabel("First state"));
        panel.add(new JScrollPane(jListState));


        return panel;
    }

    private JPanel getPanelCondition() {
        JPanel panel = new JPanel(new VerticalLayout());
        panel.setBorder(new TitledBorder("Conditions"));
        panel.setPreferredSize(new Dimension(150, -1));

        buttonAddCond = new JButton("Add condition");
        buttonEditCond = new JButton("Edit condition");
        buttonDelCond = new JButton("Delete condition");

        listModeleCond = new DefaultListModel<>();
        jListCondition = new JList<>(listModeleCond);
        jListCondition.setLayoutOrientation(JList.VERTICAL);
        jListCondition.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        updateSelectedCondition();

        panel.add(buttonAddCond);
        panel.add(new JSeparator());
        panel.add(buttonEditCond);
        panel.add(new JSeparator());
        panel.add(buttonDelCond);
        panel.add(new JSeparator());
        panel.add(jListCondition);
        panel.add(new JLabel("Conditions list"));
        panel.add(new JScrollPane(jListCondition));

        return panel;
    }

    public void addState(ModelState state) {
        panelEditor.addState(state);
        listModelState.addElement(state.getName());
    }

    public void addCondition(ModelCondition condition) {
        panelEditor.addCondition(condition);
        listModeleCond.addElement(condition.getName());
    }

    public PanelEditor getViewEditor() {
        return (PanelEditor) this.panelEditor;
    }

    public ModeleBrain getModel() {
        return this.modeleBrain;
    }

    /**
     * Accesseurs **
     */

    public JButton getButtonAddState() {
        return this.buttonAddSate;
    }

    public AbstractButton getButtonDelState() {
        return this.buttonDelState;
    }

    public JButton getButtonEditState() {
        return this.buttonEditState;
    }

    public JButton getButtonAddCond() {
        return this.buttonAddCond;
    }

    public JButton getButtonEditCond() {
        return this.buttonEditCond;
    }

    public JButton getButtonDelCond() {
        return buttonDelCond;
    }

    public void updateSelectedState() {
        listModelState.clear();
        for (ModelState s : modeleBrain.getStates()) {
            listModelState.addElement(s.getName());
        }
    }

    public void updateSelectedCondition() {
        listModeleCond.clear();
        for (ModelCondition c : modeleBrain.getConditions()) {
            listModeleCond.addElement(c.getName());
        }
    }

    public void removeCondition(PanelCondition panel) {
        getViewEditor().removePanelCondition(panel);
        updateSelectedCondition();
        getViewEditor().unselectAllItems();
    }

    public void removeState(PanelState panel) {
        getViewEditor().removePanelState(panel);
        updateSelectedState();
        getViewEditor().unselectAllItems();
    }


}
