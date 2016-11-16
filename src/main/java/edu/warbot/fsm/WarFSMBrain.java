package edu.warbot.fsm;

import edu.warbot.brains.WarBrain;

import javax.swing.*;

public abstract class WarFSMBrain extends WarBrain {

    private WarFSM<WarBrain> fsm;

    @Override
    public String action() {
        try {
            return fsm.executeFSM();
        } catch (NullPointerException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Error fsm use incorrect settings, please check <GenericSettings>, <settings> in the editor, and your settings in the Editor"
                    , "fsm intern error caused by wrong configuration", JOptionPane.INFORMATION_MESSAGE);
            return null;
        }
    }

    @SuppressWarnings("rawtypes")
	public WarFSM getFSM() {
        return fsm;
    }

    public void setFSM(WarFSM<WarBrain> fsm) {
        this.fsm = fsm;
    }

}
