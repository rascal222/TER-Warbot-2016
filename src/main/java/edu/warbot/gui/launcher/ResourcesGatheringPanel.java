package edu.warbot.gui.launcher;

import java.util.Map;

import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.teams.Team;
import edu.warbot.game.WarGameSettings;

@SuppressWarnings("serial")
public class ResourcesGatheringPanel extends WarGameModePanel
{

	public ResourcesGatheringPanel(WarGameSettings settings,
			Map<String, Team> availableTeams, WarLauncherInterface mainFrame)
	{
		super(settings, availableTeams, mainFrame);
		
		setGameModeDescription("Chercher et ramasser les reliques le plus rapidement possible. Les reliques apparaissent une à une, dès qu'une est ramassée la suivante apparaît de manière aléatoire sur la map. Méfisez-vous des tourelles !");
		setNbTeams(1);
		WarAgentType[] authorizedAgentsTeams = {WarAgentType.WarBase, WarAgentType.WarExplorer};
		setAuthorizedAgentsTeams(authorizedAgentsTeams);
		setNbComputers(1);
		WarAgentType[] authorizedAgentsIas = {WarAgentType.WarTurret};
		setAuthorizedAgentsComputers(authorizedAgentsIas);
		// Cette ligne de code sert à "confirmer" l'absence de onglet nature -> pas paramétrable par les joueurs
		setAuthorizedResourcesNature(null);
		constructPanel();
	}

}
