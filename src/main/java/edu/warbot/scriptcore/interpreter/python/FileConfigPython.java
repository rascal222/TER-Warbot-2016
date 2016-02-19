package edu.warbot.scriptcore.interpreter.python;

/**
 * Created by jimmy on 12/03/15.
 */
public enum FileConfigPython {
    PyWarAgent("PyWarAgent", ".py"),
    PyWarBase("PyWarBase", ".py"),
    PyWarEngineer("PyWarEngineer", ".py"),
    PyWarExplorer("PyWarExplorer", ".py"),
    PyWarKamikaze("PyWarKamikaze", ".py"),
    PyWarRocketLauncher("PyWarRocketLauncher", ".py"),
    PyWarTools("PyWarTools", ".py"),
    PyWarTurret("PyWarTurret", ".py"),
    PyWarTest("PyWarTest", ".py");

    private String nameFile = "";
    private String extension = "";

    FileConfigPython(String name, String editor) {
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
