package edu.warbot.gui.launcher;

import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameMode;
import edu.warbot.game.WarGameSettings;
import edu.warbot.gui.GuiIconsLoader;
import edu.warbot.launcher.WarMain;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class WarLauncherInterface extends JFrame {

    public static final String TEXT_ADDED_TO_DUPLICATE_TEAM_NAME = "_bis";

    private JButton leaveButton;
    private JButton validButton;
    
    private JTabbedPane tabbedPaneMillieu;

    private WarMain warMain;
    private WarGameSettings settings;

	public WarLauncherInterface(WarMain warMain, final WarGameSettings settings) {
        super("Warbot 3D");
        this.settings = settings;
        this.warMain = warMain;

		/* *** Fenêtre *** */
        setSize(1000, 700);
        setMinimumSize(new Dimension(800, 600));
        setIconImage(GuiIconsLoader.getLogo("iconLauncher.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        // TODO
        setJMenuBar(new InterfaceLauncherMenuBar(this));

		/* *** Haut : Titre *** */
        JPanel panelTitre = new JPanel();
        JLabel imageTitre = new JLabel(GuiIconsLoader.getWarbotLogo());
        panelTitre.add(imageTitre);
        mainPanel.add(panelTitre, BorderLayout.NORTH);

		/* *** Bas : Boutons *** */
        JPanel panelBas = new JPanel();

        validButton = new JButton("Valider");
        validButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        getRootPane().setDefaultButton(validButton);
        panelBas.add(validButton);

        leaveButton = new JButton("Quitter");
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.exit(NORMAL);
            }
        });
        panelBas.add(leaveButton);
        
        JButton advancedSettingsButton = new JButton("Avancés");
        advancedSettingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	displayAdvancedSettingsInterface();
            }
        });
        panelBas.add(advancedSettingsButton);

        mainPanel.add(panelBas, BorderLayout.SOUTH);

		/* *** Centre : Choix du mode et sélection des équipes *** */
        tabbedPaneMillieu = new JTabbedPane();
        for(WarGameMode wgm : WarGameMode.values())
        {
        	tabbedPaneMillieu.add(wgm.toString(), new WarGameModePanel(settings, warMain));
        }
        mainPanel.add(tabbedPaneMillieu, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Saves settings entered by the user in {@link WarGameSettings} instance
     */
    private void validEnteredSettings() {
    	settings.setGameMode(WarGameMode.valueOf(tabbedPaneMillieu.getTitleAt(tabbedPaneMillieu.getSelectedIndex())));
    	((WarGameModePanel)tabbedPaneMillieu.getSelectedComponent()).validEnteredSettings();
    }

    /**
     * Valid entered settings, hide this window and starts the game
     */
    public void startGame() {
        validEnteredSettings();
        warMain.startGame();
        setVisible(false);
    }

    public void reloadTeams(boolean dialog) {
    	/*
        duelTeamsPanel.removeAll();
        duelTeamsPanel.setVisible(true);
        warMain.reloadTeams(dialog);
        pnlSelectionTeam1 = new TeamSelectionPanel("Choix de l'équipe 1", warMain.getAvailableTeams());
        duelTeamsPanel.add(new JScrollPane(pnlSelectionTeam1));
        pnlSelectionTeam2 = new TeamSelectionPanel("Choix de l'équipe 2", warMain.getAvailableTeams());
        duelTeamsPanel.add(new JScrollPane(pnlSelectionTeam2));
        duelTeamsPanel.repaint();
        */
    }

    public void displayGameResults(WarGame game) {
        new GameResultsDialog(game);
    }

    public WarGameSettings getGameSettings() {
        return settings;
    }
    
	private void displayAdvancedSettingsInterface()
	{
	    new AdvancedSettingsInterface(settings);
	}
}
