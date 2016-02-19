package edu.warbot.agents.enums;

import java.util.ArrayList;

/**
 * Définition de la liste des agents Warbot
 */
public enum WarAgentType {
    // Unités non agressives
    WarExplorer(WarAgentCategory.Worker, true),
    WarEngineer(WarAgentCategory.Worker, true),

    // Unités agressives
    WarRocketLauncher(WarAgentCategory.Soldier, true),
    WarKamikaze(WarAgentCategory.Soldier, true),
    WarTurret(WarAgentCategory.Soldier, true),
    WarTest(WarAgentCategory.Soldier, true),

    // Bâtiments
    WarBase(WarAgentCategory.Building, true),
    Wall(WarAgentCategory.Building, false),

    // Projectiles
    WarRocket(WarAgentCategory.Projectile, false),
    WarBomb(WarAgentCategory.Projectile, false),
    WarDeathRocket(WarAgentCategory.Projectile, false),

    // Ressources
    WarFood(WarAgentCategory.Resource, false);

    private final WarAgentCategory _category;
    private final boolean isControllable;

    WarAgentType(WarAgentCategory category, boolean isControllable) {
        _category = category;
        this.isControllable = isControllable;
    }

    public static WarAgentType[] getAgentsOfCategories(WarAgentCategory... agentCategories) {
        ArrayList<WarAgentType> agentTypes = new ArrayList<>();

        for (WarAgentType agentType : WarAgentType.values()) {
            for (WarAgentCategory category : agentCategories) {
                if (agentType.getCategory() == category)
                    agentTypes.add(agentType);
            }
        }

        return agentTypes.toArray(new WarAgentType[]{});
    }

    public static WarAgentType[] getControllableAgentTypes() {
        ArrayList<WarAgentType> agentTypes = new ArrayList<>();

        for (WarAgentType agentType : WarAgentType.values()) {
            if (agentType.isControllable())
                agentTypes.add(agentType);
        }

        return agentTypes.toArray(new WarAgentType[]{});
    }

    public WarAgentCategory getCategory() {
        return _category;
    }

    public boolean isControllable() {
        return isControllable;
    }
}
