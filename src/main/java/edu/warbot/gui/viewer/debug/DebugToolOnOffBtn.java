package edu.warbot.gui.viewer.debug;

import edu.warbot.gui.GuiIconsLoader;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseListener;

@SuppressWarnings("serial")
public class DebugToolOnOffBtn extends JToggleButton implements ChangeListener {

    private DebugModePanel _debugToolBar;
    private MouseListener _toolMouseListener;

    public DebugToolOnOffBtn(String iconName, String selectedIconName, String toolTipText, char mnemonic, DebugModePanel debugToolBar, MouseListener toolMouseListener) {
        super();
        _debugToolBar = debugToolBar;
        _toolMouseListener = toolMouseListener;

        setIcon(GuiIconsLoader.getIcon(iconName));
        setSelectedIcon(GuiIconsLoader.getIcon(selectedIconName));
        setToolTipText(toolTipText);
        setMnemonic(mnemonic);
        setPreferredSize(new Dimension(64, 64));
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);

        addChangeListener(this);
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        if (isSelected())
            _debugToolBar.setNewMouseListener(_toolMouseListener);
    }

}
