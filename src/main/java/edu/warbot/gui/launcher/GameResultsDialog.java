package edu.warbot.gui.launcher;

import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;
import edu.warbot.game.listeners.WarGameAdapter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

@SuppressWarnings("serial")
public class GameResultsDialog extends JFrame {

    @SuppressWarnings("unused")
	private WarGame game;

    public GameResultsDialog(final WarGame game) {
        super("Fin du jeu !");
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);


        this.game = game;
        //Game Listener
        game.addWarGameListener(new WarGameAdapter() {
            @Override
            public void onGameStopped() {
                dispose();
            }
        });
        //Window Listener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                super.windowClosed(windowEvent);
                game.stopGame();
            }
        });

        JPanel pnlResult = new JPanel(new BorderLayout());
        JPanel pnlWinners = new JPanel(new FlowLayout());
        for (InGameTeam t : game.getPlayerTeams())
            pnlWinners.add(new JLabel(t.getName(), t.getImage(), JLabel.CENTER));
        pnlResult.add(pnlWinners, BorderLayout.CENTER);
        // Cas où il y n'y a plus qu'une équipe en jeu
        if (game.getPlayerTeams().size() == 1) {
            // On la déclare vainqueur
            pnlResult.add(new JLabel("Victoire de :"), BorderLayout.NORTH);
        } else {
            // Sinon il y a ex-aequo !
            pnlResult.add(new JLabel("Ex-Aequo entre les équipes :"), BorderLayout.NORTH);
        }
        add(pnlResult, BorderLayout.CENTER);

        JButton btnOk = new JButton("Ok");
        add(btnOk, BorderLayout.SOUTH);
        //Button Listener
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

        pack();
        setVisible(true);
    }



}
