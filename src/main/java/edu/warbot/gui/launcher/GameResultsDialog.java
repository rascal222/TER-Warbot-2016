package edu.warbot.gui.launcher;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.warbot.game.InGameTeam;
import edu.warbot.game.WarGame;

@SuppressWarnings("serial")
public class GameResultsDialog extends JDialog {

    public GameResultsDialog(final JFrame owner) {
    	super(owner, "Fin du jeu !", true);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        
        //Window Listener
        addWindowListener(new WindowAdapter() {
        	@Override
            public void windowClosing(WindowEvent windowEvent) {
        		GameResultsDialog.this.setVisible(false);
            	owner.setVisible(false);
                WarGame.getInstance().setGameStopped();
            }
        	
        	@Override
            public void windowClosed(WindowEvent windowEvent) {
            }
        });

        JPanel pnlResult = new JPanel(new BorderLayout());
        JPanel pnlWinners = new JPanel(new FlowLayout());
        for (InGameTeam t : WarGame.getInstance().getPlayerTeams())
            pnlWinners.add(new JLabel(t.getName(), t.getImage(), JLabel.CENTER));
        pnlResult.add(pnlWinners, BorderLayout.CENTER);
        // Cas où il y n'y a plus qu'une équipe en jeu
        if (WarGame.getInstance().getPlayerTeams().size() == 1) {
            // On la déclare vainqueur
            pnlResult.add(new JLabel("Victoire de :"), BorderLayout.NORTH);
        } else {
            // Sinon il y a ex-aequo !
            pnlResult.add(new JLabel("Ex-Aequo entre les équipes :"), BorderLayout.NORTH);
        }
        add(pnlResult, BorderLayout.CENTER);

        JButton btnOk = new JButton("Ok");
        add(btnOk, BorderLayout.SOUTH);
        getRootPane().setDefaultButton(btnOk);
        //Button Listener
        btnOk.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            	GameResultsDialog.this.dispatchEvent(new WindowEvent(GameResultsDialog.this, WindowEvent.WINDOW_CLOSING));
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
