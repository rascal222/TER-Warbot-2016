package edu.warbot.scriptcore.interpreter.python;

/**
 * Created by jimmy on 17/03/15.
 */
public enum FileTeamPython {
    WarAgent("WarAgent", ".py"),
    WarBase("WarBase", ".py"),
    WarEngineer("WarEngineer", ".py"),
    WarExplorer("WarExplorer", ".py"),
    WarKamikaze("WarKamikaze", ".py"),
    WarRocketLauncher("WarRocketLauncher", ".py"),
    WarTools("WarTools", ".py"),
    WarTurret("WarTurret", ".py"),
    WarTest("WarTest", ".py");

    private String nameFile = "";
    private String extension = "";

    FileTeamPython(String name, String editor) {
        this.nameFile = name;
        this.extension = editor;
    }

    public String getNameFile() {
        return nameFile;
    }

    public String toString() {
        return nameFile + extension;
    }
}
