package edu.warbot.gui;

import madkit.kernel.AbstractAgent;

import javax.swing.*;

@SuppressWarnings("serial")
public class WarToolBar extends JToolBar {

    public WarToolBar(final AbstractAgent agent) {
        super("Warbot");
//        TurtleKit.addTurleKitActionsTo(this, agent);
//        SwingUtil.scaleAllAbstractButtonIconsOf(this, 22);
    }

}
