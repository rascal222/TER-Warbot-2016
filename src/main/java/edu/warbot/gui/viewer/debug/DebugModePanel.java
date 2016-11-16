package edu.warbot.gui.viewer.debug;

import edu.warbot.game.WarGame;
import edu.warbot.gui.GuiIconsLoader;
import edu.warbot.launcher.AbstractWarViewer;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

@SuppressWarnings("serial")
public class DebugModePanel extends JPanel {

    private AbstractWarViewer _viewer;
    private DebugToolsPnl _debugToolsPnl;

    private MouseListener _currentViewMouseListener;

    public DebugModePanel(AbstractWarViewer viewer) {
        super();
        _viewer = viewer;
        _currentViewMouseListener = null;

        setLayout(new BorderLayout());
        setAlignmentY(CENTER_ALIGNMENT);
        setPreferredSize(new Dimension(300, getPreferredSize().height));
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 3, Color.BLACK));

        JLabel title = new JLabel("Mode debug");
        title.setFont(new Font("Arial", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        JPanel pnlCenter = new JPanel();
        pnlCenter.setLayout(new BorderLayout());
        _debugToolsPnl = new DebugToolsPnl(this);
        pnlCenter.add(_debugToolsPnl, BorderLayout.CENTER);
        add(pnlCenter, BorderLayout.CENTER);
    }

    public void init(JFrame frame) {
        frame.add(this, BorderLayout.WEST);
        setVisible(false);
    }

    public DebugToolsPnl getDebugTools() {
        return _debugToolsPnl;
    }

    public JToolBar getDebugModeToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        final JToggleButton btnDebug = new JToggleButton();
        btnDebug.setIcon(GuiIconsLoader.getIcon("debug.png"));
        btnDebug.setToolTipText("Active le mode debug");
        btnDebug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                _viewer.getDebugModePanel().setVisible(btnDebug.isSelected());
                if (btnDebug.isSelected()) {
                	WarGame.getInstance().setGamePaused();
                }
            }
        });
        toolBar.add(btnDebug);

        return toolBar;
    }

    public JMenu getDebugMenu() {
        JMenu menu = new JMenu("Situation");

        JMenuItem saveSituationMenuItem = new JMenuItem(new SaveSituationAction(getViewer(), "Sauvegarder la situation"));
        menu.add(saveSituationMenuItem);

        return menu;
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) { // Si on affiche le panel
            _debugToolsPnl.getAgentInformationsPanel().update();
            if (_currentViewMouseListener == null)
                _debugToolsPnl.getInfoToolBtn().setSelected(true);
            else {
                addCurrentMouseListener();
            }
        } else {
            _debugToolsPnl.setSelectedAgent(null);
            if (_currentViewMouseListener != null) {
                removeCurrentMouseListener();
            }
        }
    }

    public AbstractWarViewer getViewer() {
        return _viewer;
    }

    public void setNewMouseListener(MouseListener newMouseListener) {
        if (_currentViewMouseListener != null)
            removeCurrentMouseListener();
        _currentViewMouseListener = newMouseListener;
        _debugToolsPnl.setSelectedAgent(null);
        addCurrentMouseListener();
    }

    private void addCurrentMouseListener() {
        _viewer.getDisplayPane().addMouseListener(_currentViewMouseListener);
        if (_currentViewMouseListener instanceof MouseMotionListener)
            _viewer.getDisplayPane().addMouseMotionListener((MouseMotionListener) _currentViewMouseListener);
    }

    private void removeCurrentMouseListener() {
        _viewer.getDisplayPane().removeMouseListener(_currentViewMouseListener);
        if (_currentViewMouseListener instanceof MouseMotionListener)
            _viewer.getDisplayPane().removeMouseMotionListener((MouseMotionListener) _currentViewMouseListener);
    }

}
