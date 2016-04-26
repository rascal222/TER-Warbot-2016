package edu.warbot.gui.launcher;

import java.util.Map;

import edu.warbot.agents.enums.WarAgentCategory;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.teams.Team;
import edu.warbot.game.WarGameSettings;

@SuppressWarnings("serial")
public class ResourcesAndConstructionPanel extends WarGameModePanel
{

	public ResourcesAndConstructionPanel(WarGameSettings settings,
			Map<String, Team> availableTeams, WarLauncherInterface mainFrame)
	{
		super(settings, availableTeams, mainFrame);
		
		setGameModeDescription("Amasser les ressources et étender votre empire le plus rapidement possible. Le vainqueur sera le meilleur constructeur !");
		setNbTeams(2);
		WarAgentType[] authorizedAgentsTeams = {WarAgentType.WarBase, WarAgentType.WarExplorer, WarAgentType.WarEngineer, WarAgentType.WarTurret, WarAgentType.Wall};
		setAuthorizedAgentsTeams(authorizedAgentsTeams);
		setNbIas(0);
		setAuthorizedAgentsIas(null);
		setAuthorizedResourcesNature(WarAgentType.getAgentsOfCategories(WarAgentCategory.Resource));
		constructPanel();
	}

}
