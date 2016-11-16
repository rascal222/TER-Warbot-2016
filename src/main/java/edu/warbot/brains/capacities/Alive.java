package edu.warbot.brains.capacities;

/**
 * Définition de la capacité Alive donnant la possibilité de gérer une barre de vie
 */
public interface Alive extends CommonCapacities {

    /**
     * @return le niveau de vie courant
     */
    int getHealth();

    /**
     *
     * @return le niveau de vie maximum
     */
    int getMaxHealth();

}
