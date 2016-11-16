package edu.warbot.brains.capacities;

import edu.warbot.agents.enums.WarAgentType;

/**
 * Définition de la capacité Builder donnant la possibilité de construire des bâtiments
 */
public interface Builder {

    /**
     * @return le type de bâtiment qui a été choisi pour la prochaine construction
     */
    WarAgentType getNextBuildingToBuild();

    /**
     * Change le type de bâtiment à construire
     * @param nextBuildingToBuild le type de bâtiment
     */
    void setNextBuildingToBuild(WarAgentType nextBuildingToBuild);

    /**
     *
     * @param building le type de bâtiment
     * @return Vrai si et seulement si l'agent Builder est autorisé à constuire ce bâtiment
     */
    boolean isAbleToBuild(WarAgentType building);

    /**
     *
     * @return l'identifiant du prochain bâtiment à réparer
     */
    int getIdNextBuildingToRepair();

    /**
     * Change l'identifiant du prochain bâtiment à réparer
     * @param idNextBuildingToRepair l'identifiant du bâtiment
     */
    void setIdNextBuildingToRepair(int idNextBuildingToRepair);

}
