package edu.warbot.gui.launcher;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class DisplayOnDisposeAndHideOnOpenWindowListener implements WindowListener {

    private Component _componentToHide;

    public DisplayOnDisposeAndHideOnOpenWindowListener(Component componentToHide) {
        _componentToHide = componentToHide;
    }

    @Override
    public void windowOpened(WindowEvent e) {
        _componentToHide.setVisible(false);
    }

    @Override
    public void windowClosing(WindowEvent e) {
        _componentToHide.setVisible(true);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
