package edu.warbot.gui.viewer;

import edu.warbot.game.WarGame;
import edu.warbot.gui.GuiIconsLoader;
import edu.warbot.launcher.AbstractWarViewer;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class WarToolBar extends JToolBar implements ActionListener {

    private JButton btnEndGame;
    private JToggleButton btnDisplayInfos;
    private JToggleButton btnDisplayPercepts;
    private JToggleButton btnDisplayHealthBars;
    private JToggleButton btnDisplayDebugMessages;

    private AbstractWarViewer viewer;

    public WarToolBar(AbstractWarViewer viewer) {
        super();
        this.viewer = viewer;
        setFloatable(false);

        btnEndGame = new JButton();
        btnEndGame.setIcon(GuiIconsLoader.getIcon("stop.png"));
        btnEndGame.setToolTipText("Mettre fin au jeu");
        btnEndGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                WarGame.getInstance().setGamePaused();
                int confirmation = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir arrêter le combat ?", "Demande de confirmation", JOptionPane.YES_NO_OPTION);
                if (confirmation == JOptionPane.YES_OPTION) {
                    getViewer().getGame().setGameOver();
                } else {
                	WarGame.getInstance().setGameResumed();
                }
            }
        });
        add(btnEndGame);

        addSeparator();

        btnDisplayInfos = new JToggleButton();
        btnDisplayInfos.setIcon(GuiIconsLoader.getIcon("infos_agent.png"));
        btnDisplayInfos.setToolTipText("Affiche les information sur les agents");
        btnDisplayInfos.addActionListener(this);
        add(btnDisplayInfos);

        btnDisplayPercepts = new JToggleButton();
        btnDisplayPercepts.setIcon(GuiIconsLoader.getIcon("percepts.png"));
        btnDisplayPercepts.setToolTipText("Affiche la perception des agents");
        btnDisplayPercepts.addActionListener(this);
        add(btnDisplayPercepts);

        btnDisplayHealthBars = new JToggleButton();
        btnDisplayHealthBars.setIcon(GuiIconsLoader.getIcon("health.png"));
        btnDisplayHealthBars.setToolTipText("Affiche la santé des agents");
        btnDisplayHealthBars.addActionListener(this);
        add(btnDisplayHealthBars);

        btnDisplayDebugMessages = new JToggleButton();
        btnDisplayDebugMessages.setIcon(GuiIconsLoader.getIcon("infos_messages.png"));
        btnDisplayDebugMessages.setToolTipText("Affiche les messages de debug des agents");
        btnDisplayDebugMessages.addActionListener(this);
        add(btnDisplayDebugMessages);
    }

    public boolean isShowInfos() {
        return btnDisplayInfos.isSelected();
    }

    public boolean isShowPercepts() {
        return btnDisplayPercepts.isSelected();
    }

    public boolean isShowHealthBars() {
        return btnDisplayHealthBars.isSelected();
    }

    public boolean isShowDebugMessages() {
        return btnDisplayDebugMessages.isSelected();
    }

    public AbstractWarViewer getViewer() {
        return viewer;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        getViewer().getFrame().repaint();
    }

}
