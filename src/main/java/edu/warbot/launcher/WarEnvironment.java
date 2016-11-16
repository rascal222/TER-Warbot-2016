package edu.warbot.launcher;

import turtlekit.kernel.Patch;
import turtlekit.kernel.TKEnvironment;

public class WarEnvironment extends TKEnvironment {

    private Patch patch;

    public WarEnvironment() {
        super();
        patch = new Patch();
    }

    @Override
    protected void initPatchGrid() {
        // Override initPatchGrid to disabled patchs
    }

    @Override
    protected Patch getPatch(int i, int j) {
        return patch;
    }


}
