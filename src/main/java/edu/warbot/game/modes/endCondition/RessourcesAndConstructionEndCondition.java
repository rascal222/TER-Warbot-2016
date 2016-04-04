package edu.warbot.game.modes.endCondition;

import edu.warbot.agents.AliveWarAgent;
import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;

public class RessourcesAndConstructionEndCondition extends AbstractEndCondition
{
	private int pointsNeededToWin;
    private InGameTeam winner;
	
	public RessourcesAndConstructionEndCondition(WarGame game, int pointsNeededToWin)
	{
		super(game);
		this.pointsNeededToWin = pointsNeededToWin;
	}

	@Override
	public void doAfterEachTick()
	{
		for (InGameTeam t : getGame().getPlayerTeams())
		{
            int currentTeamPoints = 0;
            for (AliveWarAgent agent : t.getBuildings())
                	currentTeamPoints += agent.getHealth();
            if (currentTeamPoints >= pointsNeededToWin)
                this.winner = t;
        }
	}

	@Override
	public boolean isGameEnded()
	{
		if (winner != null)
		{
	        for (InGameTeam t : getGame().getPlayerTeams())
	        {
	            if (!t.equals(winner))
	            {
	                getGame().setTeamAsLost(t);
	            }
	        }
	        return true;
		}
		return false;
	}

}
