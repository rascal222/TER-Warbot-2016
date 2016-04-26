package edu.warbot.gui.launcher;

import java.util.Map;

import edu.warbot.agents.enums.WarAgentCategory;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.teams.Team;
import edu.warbot.game.WarGameSettings;

@SuppressWarnings("serial")
public class DuelPanel extends WarGameModePanel
{

	public DuelPanel(WarGameSettings settings,
			Map<String, Team> availableTeams, WarLauncherInterface mainFrame)
	{
		super(settings, availableTeams, mainFrame);
		
		setGameModeDescription("Détruisez toutes les bases ennemies. Une seule règle : il n'y a pas de règles !");
		setNbTeams(2);
		setAuthorizedAgentsTeams(WarAgentType.getControllableAgentTypes());
		setNbIas(0);
		setAuthorizedAgentsIas(null);
		setAuthorizedResourcesNature(WarAgentType.getAgentsOfCategories(WarAgentCategory.Resource));
		constructPanel();
	}

}
