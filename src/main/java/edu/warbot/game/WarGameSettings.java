package edu.warbot.game;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.agents.percepts.InRadiusPerceptsGetter;
import edu.warbot.agents.percepts.PerceptsGetter;
import edu.warbot.agents.teams.Team;
import edu.warbot.launcher.UserPreferences;
import edu.warbot.launcher.WarGameConfig;
import edu.warbot.loader.SituationLoader;
import edu.warbot.maps.AbstractWarMap;
import edu.warbot.maps.DefaultWarMap;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

public class WarGameSettings {

    private Map<WarAgentType, Integer> _nbAgentOfEachType;
    private WarGameMode _gameMode;
    private Object[] gameModeArguments;
    private Level _defaultLogLevel;
    private int _foodAppearanceRate;
    private Class<? extends PerceptsGetter> _perceptsGetter;
    private boolean _isEnabledEnhancedGraphism;
    private List<InGameTeam> selectedInGameTeams;
    private SituationLoader situationLoader;
    private AbstractWarMap selectedMap;

    public WarGameSettings() {
        this._nbAgentOfEachType = new HashMap<>();
        this.selectedInGameTeams = new ArrayList<>();

        restartParameters();
    }

    private void restartParameters() {
        for (WarAgentType a : WarAgentType.values()) {
            _nbAgentOfEachType.put(a, UserPreferences.getNbAgentsAtStartOfType(a.toString()));
        }
        _gameMode = WarGameMode.Duel;
        gameModeArguments = new Object[]{};
        _defaultLogLevel = UserPreferences.getLoggerLevel();
        _foodAppearanceRate = UserPreferences.getFoodAppearanceRate();
        _perceptsGetter = WarGameConfig.getDefaultPerception();
        _isEnabledEnhancedGraphism = false; // TODO set 3D as alternative viewer
        this.selectedMap = new DefaultWarMap();
    }

    public void setNbAgentOfType(WarAgentType agent, int number) {
        _nbAgentOfEachType.put(agent, number);
    }

    public int getNbAgentOfType(WarAgentType agent) {
        return _nbAgentOfEachType.get(agent);
    }

    public WarGameMode getGameMode() {
        return _gameMode;
    }

    public void setGameMode(WarGameMode gameMode) {
        _gameMode = gameMode;
    }

    public int getFoodAppearanceRate() {
        return this._foodAppearanceRate;
    }

    public void setFoodAppearanceRate(int rate) {
        _foodAppearanceRate = rate;
    }

    public void setDefaultLogLevel(Level level) {
        _defaultLogLevel = level;
    }

    public Level getLogLevel() {
        return _defaultLogLevel;
    }

    public Class<? extends PerceptsGetter> getPerceptsGetterClass() {
        return _perceptsGetter;
    }

    public void setPerceptsGetterClass(Class<? extends PerceptsGetter> perceptsGetter) {
        _perceptsGetter = perceptsGetter;
    }

    public PerceptsGetter getPerceptsGetterNewInstance(ControllableWarAgent agent, WarGame game) {
        try {
            return _perceptsGetter.getConstructor(ControllableWarAgent.class, WarGame.class).newInstance(agent, game);
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            System.err.println("La classe " + _perceptsGetter.getName() + " ne peut pas être instanciée. InRadiusPerceptsGetter pris à la place.");
            e.printStackTrace();
            return new InRadiusPerceptsGetter(agent, game);
        }
    }

    public boolean isEnabledEnhancedGraphism() {
        return _isEnabledEnhancedGraphism;
    }

    public void setEnabledEnhancedGraphism(boolean bool) {
        _isEnabledEnhancedGraphism = bool;
    }

    public List<InGameTeam> getSelectedInGameTeams() {
        return selectedInGameTeams;
    }

    public void addSelectedTeam(Team team) {
        int i = 0;
        Team t = team;
        while (containsTeamWithName(t.getTeamName() + "." + i))
            i++;
        t = team.duplicate(team.getTeamName() + "." + i);
        selectedInGameTeams.add(new InGameTeam(t));
    }

    public boolean containsTeamWithName(String name) {
        for (InGameTeam igt : selectedInGameTeams) {
            if (igt.getName().equals(name))
                return true;
        }
        return false;
    }

    public void prepareForNewGame() {
        for (InGameTeam t : selectedInGameTeams)
            t.removeAllAgents();
        selectedInGameTeams.clear();
        situationLoader = null;
    }

    public SituationLoader getSituationLoader() {
        return situationLoader;
    }

    public void setSituationLoader(SituationLoader situationLoader) {
        this.situationLoader = situationLoader;
    }

    public AbstractWarMap getSelectedMap() {
        return selectedMap;
    }

    public void setSelectedMap(AbstractWarMap selectedMap) {
        this.selectedMap = selectedMap;
    }

    public Object[] getGameModeArguments() {
        return gameModeArguments;
    }

    public void setGameModeArguments(Object[] gameModeArguments) {
        this.gameModeArguments = gameModeArguments;
    }
}
