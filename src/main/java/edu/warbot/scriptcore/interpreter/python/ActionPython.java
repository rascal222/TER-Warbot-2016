package edu.warbot.scriptcore.interpreter.python;

/**
 * Created by jimmy on 18/03/15.
 */
public enum ActionPython {
    actionBase("actionWarBase"),
    actionEngineer("actionWarEngineer"),
    actionExplorer("actionWarExplorer"),
    actionKamikaze("actionWarKamikaze"),
    actionRocketLauncher("actionWarRocketLauncher"),
    actionTurret("actionWarTurret");

    private String nameAction = "";

    ActionPython(String action) {
        this.nameAction = action;
    }

    public String toString() {
        return nameAction;
    }
}
