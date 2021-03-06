package edu.warbot.gui.launcher;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import edu.warbot.agents.enums.WarAgentCategory;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.teams.Team;
import edu.warbot.game.WarGameSettings;
import edu.warbot.gui.launcher.mapSelection.MapMiniature;
import edu.warbot.gui.launcher.mapSelection.MapSelectionDialog;
import edu.warbot.launcher.UserPreferences;

@SuppressWarnings("serial")
public abstract class WarGameModePanel extends JPanel
{
	// Eléments de l'interface
    private HashMap<WarAgentType, WarAgentCountSlider> nbAgentsTeamsSliders;
    private ArrayList<TeamSelectionPanel> selectedTeams;
    
    private HashMap<WarAgentType, WarAgentCountSlider> nbAgentsComputersSliders;
    private ArrayList<TeamSelectionPanel> selectedComputers;
    
    private HashMap<WarAgentType, WarAgentCountSlider> resourcesRatesNatureSliders;
    
    // TODO ressources en début de partie
    //private HashMap<WarAgentType, WarAgentCountSlider> resourcesRatesTeamsSliders;
    //private HashMap<WarAgentType, WarAgentCountSlider> resourcesRatesComputersSliders;
    
    private MapMiniature currentDisplayedMapMiniature;
    
    private WarGameSettings _settings;
    private WarLauncherInterface _mainFrame;
    Map<String, Team> _availableTeams;
    
    private String gameModeDescription;
    private int nbTeams;
    // private ArrayList<Team> teams;
    private int nbComputers;
    // private ArrayList<Team> computerss;

    private WarAgentType[] authorizedAgentsTeams;
    private WarAgentType[] authorizedAgentsComputers;
    private WarAgentType[] authorizedResourcesNature;
    
	public WarGameModePanel(WarGameSettings settings, Map<String, Team> availableTeams, WarLauncherInterface mainFrame)
	{
		super(new BorderLayout());
		_settings = settings;
		_mainFrame = mainFrame;
		_availableTeams = availableTeams;

		nbAgentsTeamsSliders = new HashMap<WarAgentType, WarAgentCountSlider>();
		selectedTeams = new ArrayList<TeamSelectionPanel>();
		nbAgentsComputersSliders = new HashMap<WarAgentType, WarAgentCountSlider>();
		selectedComputers = new ArrayList<TeamSelectionPanel>();
		resourcesRatesNatureSliders = new HashMap<WarAgentType, WarAgentCountSlider>();
		
		// Init des param par défaut
		setGameModeDescription("Le constructeur de la classe mère a été appelé, veuillez personnaliser le panel de ce mode de jeu (au minimum cette description).");
		setNbTeams(2);
		setNbComputers(2);
	    setAuthorizedAgentsTeams(WarAgentType.getControllableAgentTypes());
	    setAuthorizedAgentsComputers(WarAgentType.getControllableAgentTypes());
	    setAuthorizedResourcesNature(WarAgentType.getAgentsOfCategories(WarAgentCategory.Resource));
	}
	
	protected void constructPanel()
	{
		/*******************************
		 **** Panel droit : Options ****
		 *******************************/
        JPanel panelOption = new JPanel();
        panelOption.setPreferredSize(new Dimension(330, panelOption.getPreferredSize().height));
        panelOption.setLayout(new BorderLayout());
        panelOption.setBorder(new TitledBorder("Options"));
        
        // Panel contenant le choix de la map
        JPanel mapPanel = new JPanel();
        mapPanel.setLayout(new BorderLayout());
        mapPanel.setBorder(new TitledBorder("Carte"));
        currentDisplayedMapMiniature = new MapMiniature(this._settings.getSelectedMap(), MapMiniature.SIZE_SMALL);
        mapPanel.add(currentDisplayedMapMiniature, BorderLayout.CENTER);
        JButton btnChooseMap = new JButton("Choisir une autre carte");
        btnChooseMap.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	new MapSelectionDialog(currentDisplayedMapMiniature, _settings, _mainFrame);
            }
        });
        mapPanel.add(btnChooseMap, BorderLayout.EAST);
        panelOption.add(mapPanel, BorderLayout.NORTH);
        
        // Panel permettant de modifier le nombre d'agents en début de partie
        if(authorizedAgentsTeams!=null && authorizedAgentsTeams!=null && authorizedAgentsTeams!=null)
        {
	        JPanel nbAgentsPanel = new JPanel(new BorderLayout());
	        nbAgentsPanel.setBorder(new TitledBorder("Agents"));
	        
	        // Onglets contenant les sliders nb agents/food des teams/computers
	        JTabbedPane slidersTabbedPane = new JTabbedPane();
	        
	        // Onglet teams
	        if(authorizedAgentsTeams!=null)
	        {
		        JPanel nbAgentsTeamsPanel = new JPanel();
		        nbAgentsTeamsPanel.setLayout(new BoxLayout(nbAgentsTeamsPanel, BoxLayout.Y_AXIS));
		
		        // Controllables
		        for (WarAgentType a : authorizedAgentsTeams) {
		            WarAgentCountSlider slider = new WarAgentCountSlider("Nombre de " + a.toString(),
		                    0, 30, UserPreferences.getNbAgentsAtStartOfType(a.toString()), 1, 10);
		            nbAgentsTeamsSliders.put(a, slider);
		            nbAgentsTeamsPanel.add(slider);
		        }
		        JScrollPane agentsTeamsScrollPanel = new JScrollPane(nbAgentsTeamsPanel);
		        agentsTeamsScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		        slidersTabbedPane.add("Équipes", agentsTeamsScrollPanel);
        	}
		        
	        // Onglet ordinateurs
	        if(authorizedAgentsComputers!=null)
	        {
		        JPanel nbAgentsComputersPanel = new JPanel();
		        nbAgentsComputersPanel.setLayout(new BoxLayout(nbAgentsComputersPanel, BoxLayout.Y_AXIS));
		
		        // Controllables
		        for (WarAgentType a : authorizedAgentsComputers) {
		            WarAgentCountSlider slider = new WarAgentCountSlider("Nombre de " + a.toString(),
		                    0, 30, UserPreferences.getNbAgentsAtStartOfType(a.toString()), 1, 10);
		            nbAgentsComputersSliders.put(a, slider);
		            nbAgentsComputersPanel.add(slider);
		        }
		        JScrollPane agentsComputersScrollPanel = new JScrollPane(nbAgentsComputersPanel);
		        agentsComputersScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		        slidersTabbedPane.add("Ordinateurs", agentsComputersScrollPanel);
	        }
	        
	        // Onglet nature
	        if(authorizedResourcesNature!=null)
	        {
		        JPanel nbAgentsNaturePanel = new JPanel();
		        nbAgentsNaturePanel.setLayout(new BoxLayout(nbAgentsNaturePanel, BoxLayout.Y_AXIS));
		
		        // Ressources
		        for (WarAgentType a : authorizedResourcesNature) {
		            WarAgentCountSlider slider = new WarAgentCountSlider(a.toString() + " tous les x ticks",
		                    0, 500, 150, 1, 100);
		            resourcesRatesNatureSliders.put(a, slider);
		            nbAgentsNaturePanel.add(slider);
		        }
		        JScrollPane agentsNatureScrollPanel = new JScrollPane(nbAgentsNaturePanel);
		        agentsNatureScrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		        slidersTabbedPane.add("Nature", agentsNatureScrollPanel);
	        }
	        
	        nbAgentsPanel.add(slidersTabbedPane, BorderLayout.CENTER);
	        panelOption.add(nbAgentsPanel, BorderLayout.CENTER);
        }
        
        /***************************************************************
         **** Panel centre : Choix du mode et sélection des équipes ****
         ***************************************************************/
    	// Panel principal de la tab
    	final JPanel selectionPanel = new JPanel();
    	selectionPanel.setLayout(new GridBagLayout());
        GridBagConstraints gridOfSelection = new GridBagConstraints();
    	
        int incr_panelsOfSelection = 0;

        // Panel contenant le(s) objectif(s)
    	final JPanel goalPanel = new JPanel(new BorderLayout());
    	goalPanel.setBorder(new TitledBorder("Objectif de la mission"));
        GridBagConstraints gridOfGoalPanel = new GridBagConstraints();
        
        final JTextArea goal = new JTextArea();
        goal.setText(gameModeDescription);
        goal.setWrapStyleWord(true);
        goal.setLineWrap(true);
        goal.setOpaque(false);
        goal.setEditable(false);
        goal.setFocusable(false);
        goal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        goal.setBackground(UIManager.getColor("Label.background"));
        goalPanel.add(goal, BorderLayout.CENTER);
        
        // Ajout du panel goal au panel selection (panel de la tab)
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
        if(nbTeams!=0)
        {
	    	JPanel teamsPanel = new JPanel();
	    	teamsPanel.setLayout(new GridBagLayout());
	        teamsPanel.setBorder(new TitledBorder("Choix des équipes"));
	        GridBagConstraints gridOfTeams = new GridBagConstraints();
	        
	        for(int i=0; i<nbTeams; i++)
	        {
	        	selectedTeams.add(new TeamSelectionPanel(_availableTeams, true));
	
		        // Ajout des équipes au panel teams
		        gridOfTeams.fill = GridBagConstraints.HORIZONTAL;
		        gridOfTeams.weightx = 0.5;
		        gridOfTeams.gridx = 0;
		        gridOfTeams.gridy = i;
		        teamsPanel.add(selectedTeams.get(i), gridOfTeams);
	        }
	        
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
        }
        
        // Panel proposant difficulté/équipe de l'ordinateur
        if(nbComputers!=0)
        {
	    	JPanel computerPanel = new JPanel();
	    	computerPanel.setLayout(new GridBagLayout());
	    	computerPanel.setBorder(new TitledBorder("Choix des ordinateurs"));
	        GridBagConstraints gridOfComputer = new GridBagConstraints();
	        
	        for(int i=0; i<nbComputers; i++)
	        {
	        	selectedComputers.add(new TeamSelectionPanel(_availableTeams, true));
	
		        // Ajout des équipes au panel ordinateur
		        gridOfComputer.fill = GridBagConstraints.HORIZONTAL;
		        gridOfComputer.weightx = 0.5;
		        gridOfComputer.gridx = 0;
		        gridOfComputer.gridy = i;
		        computerPanel.add(selectedComputers.get(i), gridOfComputer);
	        }
	        
	        // Ajout du panel ordinateur au panel selection (panel de la tab)
	        gridOfComputer.fill = GridBagConstraints.HORIZONTAL;
	        gridOfComputer.weightx = 0.5;
	        gridOfComputer.gridx = 0;
	        gridOfComputer.gridy = incr_panelsOfSelection;
	        incr_panelsOfSelection++;
	        // Ajout de marge
	        JPanel computerPanelMarginExt = new JPanel(new BorderLayout());
	        computerPanelMarginExt.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	        computerPanelMarginExt.add(computerPanel, BorderLayout.CENTER);
	        selectionPanel.add(computerPanelMarginExt, gridOfComputer);
        }
	        
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
					selectionPanel.setPreferredSize(new Dimension(scrollPaneOfSelection.getSize().width, selectionPanel.getPreferredSize().height));
				if(scrollPaneOfSelection.getVerticalScrollBar().getSize().width > 0)
					selectionPanel.setPreferredSize(new Dimension(selectionPanel.getPreferredSize().width-20, selectionPanel.getPreferredSize().height));
			}
			
			@Override
			public void componentMoved(ComponentEvent e) {}
			
			@Override
			public void componentHidden(ComponentEvent e) {}
		});
	}
	
	public void validEnteredSettings()
	{
		for (WarAgentType agent : WarAgentType.values())
		{
			WarAgentCountSlider slider = nbAgentsTeamsSliders.get(agent);
			if (slider != null)
				_settings.setNbAgentOfType(agent, slider.getSelectedValue());
		}
		for (WarAgentType agent : WarAgentType.values())
		{
			WarAgentCountSlider slider = nbAgentsComputersSliders.get(agent);
			if (slider != null)
				_settings.setNbAgentOfType(agent, slider.getSelectedValue());
		}
		if(resourcesRatesNatureSliders.get(WarAgentType.WarFood) != null)
			_settings.setFoodAppearanceRate(resourcesRatesNatureSliders.get(WarAgentType.WarFood).getSelectedValue());
		
		for(TeamSelectionPanel tsp : selectedTeams)
			_settings.addSelectedTeam(tsp.getSelectedTeam());
		
		for(TeamSelectionPanel tsp : selectedComputers)
			_settings.addSelectedTeam(tsp.getSelectedTeam());
	}

	public String getGameModeDescription() {
		return gameModeDescription;
	}

	public void setGameModeDescription(String gameModeDescription) {
		this.gameModeDescription = gameModeDescription;
	}

	public int getNbTeams() {
		return nbTeams;
	}

	public void setNbTeams(int nbTeams) {
		this.nbTeams = nbTeams;
	}

	public int getNbComputers() {
		return nbComputers;
	}

	public void setNbComputers(int nbComputers) {
		this.nbComputers = nbComputers;
	}

	public WarAgentType[] getAuthorizedAgentsTeams() {
		return authorizedAgentsTeams;
	}

	public void setAuthorizedAgentsTeams(WarAgentType[] authorizedAgentsTeams) {
		this.authorizedAgentsTeams = authorizedAgentsTeams;
	}

	public WarAgentType[] getAuthorizedAgentsComputers() {
		return authorizedAgentsComputers;
	}

	public void setAuthorizedAgentsComputers(WarAgentType[] authorizedAgentsComputers) {
		this.authorizedAgentsComputers = authorizedAgentsComputers;
	}

	public WarAgentType[] getAuthorizedResourcesNature() {
		return authorizedResourcesNature;
	}

	public void setAuthorizedResourcesNature(WarAgentType[] authorizedResourcesNature) {
		this.authorizedResourcesNature = authorizedResourcesNature;
	}
	
	public void reloadTeams()
	{
		for(TeamSelectionPanel t : selectedComputers)
			t.reloadTeams();
		
		for(TeamSelectionPanel t : selectedTeams)
			t.reloadTeams();
		
		revalidate();
		repaint();
	}
}
