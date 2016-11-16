package edu.warbot.loader;

import edu.warbot.game.WarGame;
import edu.warbot.launcher.WarLauncher;

/**
 * Created by beugnon on 19/06/15.
 * <p/>
 * <p/>
 * SituationLoader. L'implémentation de cette interface permet
 * de fournir le chargement d'une situation à partir de n'importe quel cas de figure.
 *
 * @author BEUGNON Sébastien
 * @since 3.3.3
 */
public interface SituationLoader {


    /**
     * Lance tout les agents dans la configuration du XMLSituationLoader
     *
     * @param warLauncher l'agent Launcher qui permet de lancer les agents
     * @param warGame     L'instance de partie dans laquelle les agents vont être lancés
     */
    void launchAllAgentsFromSituation(WarLauncher warLauncher, WarGame warGame);

}
