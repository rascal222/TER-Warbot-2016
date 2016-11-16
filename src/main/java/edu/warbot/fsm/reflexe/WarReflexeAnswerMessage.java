package edu.warbot.fsm.reflexe;

import edu.warbot.brains.WarBrain;
import edu.warbot.communications.WarMessage;

public class WarReflexeAnswerMessage<BrainType extends WarBrain> extends WarReflexe<BrainType> {

    public WarReflexeAnswerMessage(BrainType brain, String message, String answer) {
        super(brain, "Reflexe answer message");
    }

    @Override
    public String executeReflexe() {
        for (WarMessage m : getBrain().getMessages()) {
            if (m.getMessage().equals("whereAreYou")) {
                getBrain().sendMessage(m.getSenderID(), "here", "");
            }
        }
        return null;
    }

}
