package edu.warbot.brains.capacities;

/**
 * Définition de la capacité Agressive donnant la possibilité d'attaquer
 */
public interface Agressive {

    /**
     * @return vrai si et seulement si l'agent peut à nouveau attaquer
     */
    boolean isReloaded();

    /**
     *
     * @return faux si et seulement si l'agent peut attaquer ou il a déjà attaqué
     */
    boolean isReloading();

}
