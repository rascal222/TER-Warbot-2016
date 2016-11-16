package edu.warbot.gui.viewer.stats;

import edu.warbot.game.WarGame;
import edu.warbot.gui.GuiIconsLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class GameStatsPanel extends JPanel {

    @SuppressWarnings("unused")
	private WarGame game;

    private JToggleButton btnStatsToolBar;
    private TeamsDataTable teamsDataTable;

    public GameStatsPanel(WarGame game) {
        super();
        this.game = game;

        setLayout(new FlowLayout());
        setPreferredSize(new Dimension(400, getPreferredSize().height));
        setBorder(BorderFactory.createMatteBorder(0, 3, 0, 0, Color.BLACK));

        teamsDataTable = new TeamsDataTable(game);
        teamsDataTable.setPreferredSize(new Dimension(getPreferredSize().width - 10, teamsDataTable.getPreferredSize().height));
        add(teamsDataTable);
    }

    public void init(JFrame frame) {
        frame.add(this, BorderLayout.EAST);
        setVisible(false);
    }

    public JToolBar getStatsToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        btnStatsToolBar = new JToggleButton();
        btnStatsToolBar.setIcon(GuiIconsLoader.getIcon("stats.png"));
        btnStatsToolBar.setToolTipText("Voir les statistiques du jeu");
        btnStatsToolBar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameStatsPanel.this.setVisible(btnStatsToolBar.isSelected());
            }
        });
        toolBar.add(btnStatsToolBar);

        return toolBar;
    }

}
