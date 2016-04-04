package edu.warbot.gui.launcher;

import edu.warbot.agents.enums.WarAgentCategory;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.teams.Team;
import edu.warbot.game.WarGame;
import edu.warbot.game.WarGameMode;
import edu.warbot.game.WarGameSettings;
import edu.warbot.gui.GuiIconsLoader;
import edu.warbot.gui.launcher.mapSelection.MapMiniature;
import edu.warbot.gui.launcher.mapSelection.MapSelectionListener;
import edu.warbot.launcher.UserPreferences;
import edu.warbot.launcher.WarMain;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.logging.Level;

@SuppressWarnings("serial")
public class WarLauncherInterface extends JFrame {

    public static final String TEXT_ADDED_TO_DUPLICATE_TEAM_NAME = "_bis";
    private final JPanel duelTeamsPanel;

    // Eléments de l'interface
    private HashMap<WarAgentType, WarAgentCountSlider> sliderNbAgents;
    private HashMap<WarAgentType, WarAgentCountSlider> foodCreationRates;

    private TeamSelectionPanel pnlSelectionTeam1;
    private TeamSelectionPanel pnlSelectionTeam2;

    private MapMiniature currentDisplayedMapMiniature;

    private JButton leaveButton;
    private JButton validButton;

    private JComboBox<String> cbLogLevel;

    private JCheckBox cbEnhancedGraphismEnabled;
    
    private JTabbedPane tabbedPaneMillieu;

    private WarMain warMain;
    private WarGameSettings settings;

    public WarLauncherInterface(WarMain warMain, WarGameSettings settings) {
        super("Warbot 3D");
        this.settings = settings;
        this.warMain = warMain;

		/* *** Fenêtre *** */
        setSize(1000, 700);
        setMinimumSize(new Dimension(600, 400));
        setLocationRelativeTo(null);
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

		/* *** Droite : Options *** */
        JPanel panelOption = new JPanel();
        panelOption.setPreferredSize(new Dimension(300, panelOption.getPreferredSize().height));
        panelOption.setLayout(new BorderLayout());
        panelOption.setBorder(new TitledBorder("Options"));

        JPanel pnlNbUnits = new JPanel();
        pnlNbUnits.setLayout(new BoxLayout(pnlNbUnits, BoxLayout.Y_AXIS));
        sliderNbAgents = new HashMap<>();

        // Controllables
        for (WarAgentType a : WarAgentType.getControllableAgentTypes()) {
            WarAgentCountSlider slider = new WarAgentCountSlider("Nombre de " + a.toString(),
                    0, 30, UserPreferences.getNbAgentsAtStartOfType(a.toString()), 1, 10);
            sliderNbAgents.put(a, slider);
            pnlNbUnits.add(slider);
        }

        // Ressources
        foodCreationRates = new HashMap<>();
        for (WarAgentType a : WarAgentType.getAgentsOfCategories(WarAgentCategory.Resource)) {
            WarAgentCountSlider slider = new WarAgentCountSlider(a.toString() + " tous les x ticks",
                    0, 500, 150, 1, 100);
            foodCreationRates.put(a, slider);
            pnlNbUnits.add(slider);
        }

        panelOption.add(new JScrollPane(pnlNbUnits), BorderLayout.CENTER);

        JPanel panelAdvanced = new JPanel();
        panelAdvanced.setLayout(new BorderLayout());
        panelAdvanced.setBorder(new TitledBorder("Avancé"));
        String comboOption[] = {"ALL", "SEVERE", "WARNING", "INFO", "CONFIG", "FINE", "FINER", "FINEST"};
        this.cbLogLevel = new JComboBox<>(comboOption);
        cbLogLevel.setSelectedItem("WARNING");
        panelAdvanced.add(new JLabel("Niveau de détail des logs"), BorderLayout.NORTH);
        panelAdvanced.add(cbLogLevel, BorderLayout.CENTER);
        // graphisme
        cbEnhancedGraphismEnabled = new JCheckBox("Charger la vue 2D isométrique", false);
        panelAdvanced.add(cbEnhancedGraphismEnabled, BorderLayout.SOUTH);

        panelOption.add(panelAdvanced, BorderLayout.SOUTH);

        JPanel panelMap = new JPanel();
        panelMap.setLayout(new BorderLayout());
        panelMap.setBorder(new TitledBorder("Carte"));
        currentDisplayedMapMiniature = new MapMiniature(this.settings.getSelectedMap(), MapMiniature.SIZE_SMALL);
        panelMap.add(currentDisplayedMapMiniature, BorderLayout.CENTER);
        JButton btnChooseMap = new JButton("Choisir une autre carte");
        btnChooseMap.addActionListener(new MapSelectionListener(this));
        panelMap.add(btnChooseMap, BorderLayout.EAST);
        panelOption.add(panelMap, BorderLayout.NORTH);

        mainPanel.add(panelOption, BorderLayout.EAST);

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

        mainPanel.add(panelBas, BorderLayout.SOUTH);

		/* *** Centre : Choix du mode et sélection des équipes *** */
        tabbedPaneMillieu = new JTabbedPane();
        duelTeamsPanel = createPanelModeClassic();
        /*tabbedPaneMillieu.add(duelTeamsPanel, "Mode Duel");
        tabbedPaneMillieu.add(creerPanelModeMulti(), "Mode Tournoi");
        */
        
        for(WarGameMode wgm : WarGameMode.values())
        {
            tabbedPaneMillieu.add(wgm.toString(), createPanelModeClassic());
        }
        
        mainPanel.add(tabbedPaneMillieu, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Saves settings entered by the user in {@link WarGameSettings} instance
     */
    private void validEnteredSettings() {
        settings.setDefaultLogLevel((Level.parse((String) cbLogLevel.getSelectedItem())));

        for (WarAgentType agent : WarAgentType.values()) {
            WarAgentCountSlider slider = sliderNbAgents.get(agent);
            if (slider != null)
                settings.setNbAgentOfType(agent, slider.getSelectedValue());
        }
        settings.setFoodAppearanceRate(foodCreationRates.get(WarAgentType.WarFood).getSelectedValue());

        settings.setGameMode(WarGameMode.valueOf(tabbedPaneMillieu.getTitleAt(tabbedPaneMillieu.getSelectedIndex()))); // TODO varier selon le choix dans l'interface

        System.out.println(WarGameMode.valueOf(tabbedPaneMillieu.getTitleAt(tabbedPaneMillieu.getSelectedIndex())));
        settings.setEnabledEnhancedGraphism(cbEnhancedGraphismEnabled.isSelected());

        if (settings.getSituationLoader() == null) {
            // On récupère les équipes
            Team inGameTeam1 = pnlSelectionTeam1.getSelectedTeam();
            Team inGameTeam2 = pnlSelectionTeam2.getSelectedTeam();
            // Si c'est les mêmes équipes, on en duplique une en lui donnant un autre nom
//            if (inGameTeam1.equals(inGameTeam2))
//                inGameTeam2 = inGameTeam1.duplicate(inGameTeam1.getTeamName() + TEXT_ADDED_TO_DUPLICATE_TEAM_NAME);
            // On ajoute les équipes au jeu
            settings.addSelectedTeam(inGameTeam1);
            settings.addSelectedTeam(inGameTeam2);
        } else {

        }
    }

    /**
     * Valid entered settings, hide this window and starts the game
     */
    public void startGame() {
        validEnteredSettings();
        warMain.startGame();
        setVisible(false);
    }

    private JPanel creerPanelModeMulti() {
        JPanel toReturn = new JPanel(new GridLayout());

        // TODO

        toReturn.add(new JLabel("not installed"));

        return toReturn;
    }

    private JPanel createPanelModeClassic() {
        JPanel toReturn = new JPanel(new GridLayout(1, 2));

        pnlSelectionTeam1 = new TeamSelectionPanel("Choix de l'équipe 1", warMain.getAvailableTeams());
        toReturn.add(new JScrollPane(pnlSelectionTeam1));
        pnlSelectionTeam2 = new TeamSelectionPanel("Choix de l'équipe 2", warMain.getAvailableTeams());
        toReturn.add(new JScrollPane(pnlSelectionTeam2));

        return toReturn;
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

    public JCheckBox getEnableEnhancedGraphism() {
        return cbEnhancedGraphismEnabled;
    }

    public void displayGameResults(WarGame game) {
        new GameResultsDialog(game);
    }

    public WarGameSettings getGameSettings() {
        return settings;
    }

    public void notifySelectedMapChanged() {
        currentDisplayedMapMiniature.setMap(settings.getSelectedMap());
    }
}
