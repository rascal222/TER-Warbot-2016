package edu.warbot.gui.viewer.debug.infos;

import javax.swing.*;

@SuppressWarnings("serial")
public class InfoLabel extends JLabel {

    private String _label;
    private String _value;

    public InfoLabel(String label) {
        super();
        _label = label;
        setText(_label + " : ");
    }

    public void setValue(String value) {
        _value = value;
        setText(_label + " : " + _value);
    }
}
