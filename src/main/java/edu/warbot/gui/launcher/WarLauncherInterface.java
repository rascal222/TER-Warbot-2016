package edu.warbot.gui.launcher;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import com.badlogic.gdx.Gdx;

import edu.warbot.game.WarGameMode;
import edu.warbot.game.WarGameSettings;
import edu.warbot.gui.GuiIconsLoader;
import edu.warbot.launcher.WarMain;

@SuppressWarnings("serial")
public class WarLauncherInterface extends JFrame {

    public static final String TEXT_ADDED_TO_DUPLICATE_TEAM_NAME = "_bis";

    private JButton leaveButton;
    private JButton validButton;
    
    private JTabbedPane tabbedPaneMillieu;

    private WarMain _warMain;
    private WarGameSettings _settings;

	public WarLauncherInterface(WarMain warMain, WarGameSettings settings) {
        super("Warbot 3D");
        _settings = settings;
        _warMain = warMain;
        
		/* *** Fenêtre *** */
        // Dimensionnement de la fenêtre
        int minWidth = Integer.MAX_VALUE, minHeight = Integer.MAX_VALUE;
        for (GraphicsDevice gd : GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices())
        {
            if (gd.getDisplayMode().getWidth() < minWidth)
            	minWidth = gd.getDisplayMode().getWidth();
            if (gd.getDisplayMode().getHeight() < minHeight)
            	minHeight = gd.getDisplayMode().getHeight();
        }
        if(minWidth >= 1000 && minHeight >= 700)
            setMinimumSize(new Dimension(1000, 700));
        else
            setMinimumSize(new Dimension(800, 600));
        
        setIconImage(GuiIconsLoader.getLogo("iconLauncher.png").getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        setContentPane(mainPanel);

        // TODO
        setJMenuBar(new InterfaceLauncherMenuBar(this));

		/* *** Haut : Titre *** */
        JPanel panelTitre = new JPanel();
        JLabel imageTitre = new JLabel(GuiIconsLoader.getWarbotLogo());
        panelTitre.add(imageTitre);
        mainPanel.add(panelTitre, BorderLayout.NORTH);

		/* *** Bas : Boutons *** */
        JPanel panelBas = new JPanel();

        validButton = new JButton("Valider");
        validButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGame();
            }
        });
        getRootPane().setDefaultButton(validButton);
        panelBas.add(validButton);

        leaveButton = new JButton("Quitter");
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	for(Window w : Frame.getWindows())
            		w.dispose();
            	if(Gdx.app != null)
            		Gdx.app.exit();
            	else
            		System.exit(NORMAL);
            }
        });
        panelBas.add(leaveButton);
        
        addWindowListener(new WindowAdapter() {
        	@Override
            public void windowClosing(WindowEvent windowEvent) {
        		for(Window w : Frame.getWindows())
            		w.dispose();
        		if(Gdx.app != null)
	        		Gdx.app.exit();
	        	else
	        		System.exit(NORMAL);
            }
        });
        
        JButton advancedSettingsButton = new JButton("Avancés");
        advancedSettingsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	displayAdvancedSettingsInterface();
            }
        });
        panelBas.add(advancedSettingsButton);

        mainPanel.add(panelBas, BorderLayout.SOUTH);

		/* *** Centre : Choix du mode et sélection des équipes *** */
        tabbedPaneMillieu = new JTabbedPane();
        for(WarGameMode wgm : WarGameMode.values())
        {
        	try
        	{
				tabbedPaneMillieu.add(wgm.toString(), wgm.getGameModePanelClass().getConstructor(WarGameSettings.class, Map.class, WarLauncherInterface.class).newInstance(_settings, _warMain.getAvailableTeams(), this));
			}
        	catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e)
        	{
	            e.printStackTrace();
        	}
        }
        mainPanel.add(tabbedPaneMillieu, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    /**
     * Saves settings entered by the user in {@link WarGameSettings} instance
     */
    private void validEnteredSettings() {
    	_settings.setGameMode(WarGameMode.valueOf(tabbedPaneMillieu.getTitleAt(tabbedPaneMillieu.getSelectedIndex())));
    	((WarGameModePanel)tabbedPaneMillieu.getSelectedComponent()).validEnteredSettings();
    }

    /**
     * Valid entered settings, hide this window and starts the game
     */
    public void startGame() {
        validEnteredSettings();
        setVisible(false);
        _warMain.startGame();
        
    }

    public void reloadTeams(boolean dialog) {
    	tabbedPaneMillieu.removeAll();
        _warMain.reloadTeams(dialog);
        for(WarGameMode wgm : WarGameMode.values())
        {
        	try
        	{
				tabbedPaneMillieu.add(wgm.toString(), wgm.getGameModePanelClass().getConstructor(WarGameSettings.class, Map.class, WarLauncherInterface.class).newInstance(_settings, _warMain.getAvailableTeams(), this));
			}
        	catch (InstantiationException | InvocationTargetException | IllegalAccessException | NoSuchMethodException e)
        	{
	            e.printStackTrace();
        	}
        }
        tabbedPaneMillieu.repaint();
    }

    public WarGameSettings getGameSettings() {
        return _settings;
    }
    
	private void displayAdvancedSettingsInterface()
	{
	    new AdvancedSettingsDialog(_settings, this);
	}
}
