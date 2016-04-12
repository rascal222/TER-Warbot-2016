package edu.warbot.gui.launcher;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import edu.warbot.agents.enums.WarAgentCategory;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.WarGameSettings;
import edu.warbot.gui.launcher.mapSelection.MapMiniature;
import edu.warbot.gui.launcher.mapSelection.MapSelectionListener;
import edu.warbot.launcher.UserPreferences;
import edu.warbot.launcher.WarMain;

@SuppressWarnings("serial")
public class WarGameModePanel extends JPanel
{
	// Eléments de l'interface
    private HashMap<WarAgentType, WarAgentCountSlider> sliderNbAgents;
    private HashMap<WarAgentType, WarAgentCountSlider> foodCreationRates;
    private MapMiniature currentDisplayedMapMiniature;
    private ArrayList<TeamSelectionPanel> selectedTeams;
    private ArrayList<TeamSelectionPanel> selectedIas;
    
    private WarGameSettings settings;
    private WarMain warMain;
	
	public WarGameModePanel(WarGameSettings settings, WarMain warMain)
	{
		super(new BorderLayout());
		this.settings = settings;
		this.warMain = warMain;
		
		/* *** Droite : Options *** */
        JPanel panelOption = new JPanel();
        panelOption.setPreferredSize(new Dimension(320, panelOption.getPreferredSize().height));
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
        JScrollPane agentsScrollPanel = new JScrollPane(pnlNbUnits);
        agentsScrollPanel.setBorder(new TitledBorder("Agents"));
        agentsScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        panelOption.add(agentsScrollPanel, BorderLayout.CENTER);

        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BorderLayout());
        mapPanel.setBorder(new TitledBorder("Carte"));
        currentDisplayedMapMiniature = new MapMiniature(this.settings.getSelectedMap(), MapMiniature.SIZE_SMALL);
        mapPanel.add(currentDisplayedMapMiniature, BorderLayout.CENTER);
        JButton btnChooseMap = new JButton("Choisir une autre carte");
        btnChooseMap.addActionListener(new MapSelectionListener(currentDisplayedMapMiniature, settings));
        mapPanel.add(btnChooseMap, BorderLayout.EAST);
        panelOption.add(mapPanel, BorderLayout.NORTH);

		/* *** Centre : Choix du mode et sélection des équipes *** */
    	// Panel principal de la tab
    	final JPanel selectionPanel = new JPanel();
    	selectionPanel.setLayout(new GridBagLayout());
    	//selectionPanel.setMinimumSize(new Dimension(, ));
        GridBagConstraints gridOfSelection = new GridBagConstraints();
    	
        int incr_panelsOfSelection = 0;

        // Panel contenant le(s) objectif(s)
    	JPanel goalPanel = new JPanel(new BorderLayout());
    	goalPanel.setBorder(new TitledBorder("Objectif de la mission"));
        GridBagConstraints gridOfGoalPanel = new GridBagConstraints();
        
        JTextArea goal = new JTextArea();
        goal.setText("Description et objectifs du mode de jeu choisi");
        goal.setWrapStyleWord(true);
        goal.setLineWrap(true);
        goal.setOpaque(false);
        goal.setEditable(false);
        goal.setFocusable(false);
        goal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        goal.setBackground(UIManager.getColor("Label.background"));
        goalPanel.add(goal, BorderLayout.CENTER);
        
        // Ajout du panel teams au panel selection (panel de la tab)
        gridOfGoalPanel.fill = GridBagConstraints.HORIZONTAL;
        gridOfGoalPanel.weightx = 0.5;
        gridOfGoalPanel.gridx = 0;
        gridOfGoalPanel.gridy = incr_panelsOfSelection;
        incr_panelsOfSelection++;
        // Ajout de marge
        JPanel goalPanelMarginExt = new JPanel(new BorderLayout());
        goalPanelMarginExt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	goalPanelMarginExt.add(goalPanel, BorderLayout.CENTER);
        selectionPanel.add(goalPanelMarginExt, gridOfGoalPanel);        
        
        // Panel proposant les équipes
    	JPanel teamsPanel = new JPanel();
    	teamsPanel.setLayout(new GridBagLayout());
        teamsPanel.setBorder(new TitledBorder("Choix des equipes"));
        GridBagConstraints gridOfTeams = new GridBagConstraints();
        
        selectedTeams = new ArrayList<TeamSelectionPanel>();
        selectedTeams.add(new TeamSelectionPanel("Équipe 1", this.warMain.getAvailableTeams()));
        selectedTeams.add(new TeamSelectionPanel("Équipe 2", this.warMain.getAvailableTeams()));

        // Ajout des équipes au panel teams
        gridOfTeams.fill = GridBagConstraints.HORIZONTAL;
        gridOfTeams.weightx = 0.5;
        gridOfTeams.gridx = 0;
        gridOfTeams.gridy = 0;
        teamsPanel.add(selectedTeams.get(0), gridOfTeams);
        
        gridOfTeams.fill = GridBagConstraints.HORIZONTAL;
        gridOfTeams.weightx = 0.5;
        gridOfTeams.gridx = 0;
        gridOfTeams.gridy = 1;
        teamsPanel.add(selectedTeams.get(1), gridOfTeams);
        
        // Ajout du panel teams au panel selection (panel de la tab)
        gridOfSelection.fill = GridBagConstraints.HORIZONTAL;
        gridOfSelection.weightx = 0.5;
        gridOfSelection.gridx = 0;
        gridOfSelection.gridy = incr_panelsOfSelection;
        incr_panelsOfSelection++;
        // Ajout de marge
        JPanel teamsPanelMarginExt = new JPanel(new BorderLayout());
        teamsPanelMarginExt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        teamsPanelMarginExt.add(teamsPanel, BorderLayout.CENTER);
        selectionPanel.add(teamsPanelMarginExt, gridOfSelection);
        
        // Panel proposant difficulté/équipe de l'IA
    	JPanel iaPanel = new JPanel();
    	iaPanel.setLayout(new GridBagLayout());
    	iaPanel.setBorder(new TitledBorder("Choix de l'IA"));
        GridBagConstraints gridOfIa = new GridBagConstraints();
        
        selectedIas = new ArrayList<TeamSelectionPanel>();
        selectedIas.add(new TeamSelectionPanel("Équipe 3", this.warMain.getAvailableTeams()));
        selectedIas.add(new TeamSelectionPanel("Équipe 4", this.warMain.getAvailableTeams()));

        // Ajout des équipes au panel ia
        gridOfIa.fill = GridBagConstraints.HORIZONTAL;
        gridOfIa.weightx = 0.5;
        gridOfIa.gridx = 0;
        gridOfIa.gridy = 0;
        iaPanel.add(selectedIas.get(0), gridOfIa);
        
        gridOfIa.fill = GridBagConstraints.HORIZONTAL;
        gridOfIa.weightx = 0.5;
        gridOfIa.gridx = 0;
        gridOfIa.gridy = 1;
        iaPanel.add(selectedIas.get(1), gridOfIa);
        
        // Ajout du panel ia au panel selection (panel de la tab)
        gridOfIa.fill = GridBagConstraints.HORIZONTAL;
        gridOfIa.weightx = 0.5;
        gridOfIa.gridx = 0;
        gridOfIa.gridy = incr_panelsOfSelection;
        incr_panelsOfSelection++;
        // Ajout de marge
        JPanel iaPanelMarginExt = new JPanel(new BorderLayout());
        iaPanelMarginExt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        iaPanelMarginExt.add(iaPanel, BorderLayout.CENTER);
        selectionPanel.add(iaPanelMarginExt, gridOfIa);
        
        // Panel vide (invisible) permettant de monter en haut les autres
        gridOfSelection.fill = GridBagConstraints.HORIZONTAL;
        gridOfSelection.weightx = 0.5;
        // Ligne permettant de remonter les Components du dessus
        gridOfSelection.weighty = 1.0;
        gridOfSelection.gridx = 0;
        gridOfSelection.gridy = incr_panelsOfSelection;
        incr_panelsOfSelection++;
        selectionPanel.add(new JPanel(), gridOfSelection);
        
        
        final JScrollPane scrollPaneOfSelection = new JScrollPane(selectionPanel);
        scrollPaneOfSelection.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
    	   public void run() { 
    		   scrollPaneOfSelection.getVerticalScrollBar().setValue(0);
    	   }
    	});
        
        add(panelOption, BorderLayout.EAST);
        add(scrollPaneOfSelection, BorderLayout.CENTER);
        
        scrollPaneOfSelection.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent e) {}
			
			@Override
			public void componentResized(ComponentEvent e) {
				if(scrollPaneOfSelection.getSize().width < selectionPanel.getSize().width)
					selectionPanel.setPreferredSize(new Dimension(scrollPaneOfSelection.getSize().width-20, selectionPanel.getSize().height));
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {}
			
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
	}
	
	public void validEnteredSettings()
	{
		System.out.println(getSize());
		for (WarAgentType agent : WarAgentType.values())
		{
			WarAgentCountSlider slider = sliderNbAgents.get(agent);
			if (slider != null)
				settings.setNbAgentOfType(agent, slider.getSelectedValue());
		}
		settings.setFoodAppearanceRate(foodCreationRates.get(WarAgentType.WarFood).getSelectedValue());
		
		if (settings.getSituationLoader() == null)
		{
			// On récupère les équipes
			//Team inGameTeam1 = pnlSelectionTeam1.getSelectedTeam();
			//Team inGameTeam2 = pnlSelectionTeam2.getSelectedTeam();
			
			// Si c'est les mêmes équipes, on en duplique une en lui donnant un autre nom
			//            if (inGameTeam1.equals(inGameTeam2))
			//                inGameTeam2 = inGameTeam1.duplicate(inGameTeam1.getTeamName() + TEXT_ADDED_TO_DUPLICATE_TEAM_NAME);
			// On ajoute les équipes au jeu
			//settings.addSelectedTeam(inGameTeam1);
			//settings.addSelectedTeam(inGameTeam2);
			
			for(TeamSelectionPanel tsp : selectedTeams)
				settings.addSelectedTeam(tsp.getSelectedTeam());
		}
		else
		{}
	}
}
