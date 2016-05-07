package edu.warbot.launcher;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import madkit.simulation.probe.SingleAgentProbe;
import turtlekit.agr.TKOrganization;
import turtlekit.kernel.TKScheduler;
import turtlekit.viewer.AbstractGridViewer;
import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.percepts.WarAgentPercept;
import edu.warbot.game.WarGame;
import edu.warbot.game.listeners.WarGameAdapter;
import edu.warbot.gui.launcher.GameResultsDialog;
import edu.warbot.gui.viewer.MapExplorationListener;
import edu.warbot.gui.viewer.WarToolBar;
import edu.warbot.gui.viewer.debug.DebugModePanel;
import edu.warbot.gui.viewer.stats.GameStatsPanel;
import edu.warbot.tools.geometry.CartesianCoordinates;

@SuppressWarnings("serial")
public abstract class AbstractWarViewer extends AbstractGridViewer {

    protected static final int DEFAULT_CELL_SIZE = 1;

    private WarToolBar warToolBar;
    private DebugModePanel debugModePanel;
    private GameStatsPanel gameStatsPanel;

    private MapExplorationListener mapExplorationMouseListener;

    private ArrayList<Integer> agentsIDsSeenBySelectedAgent;

    private double mapOffsetX, mapOffsetY;

    private WarGame game;

    private boolean isRenderable;
    
    public AbstractWarViewer(WarGame warGame, boolean isRenderable) {
        super();
        this.isRenderable = isRenderable;
        this.game = warGame;
        warToolBar = new WarToolBar(this);
        debugModePanel = new DebugModePanel(this);
        gameStatsPanel = new GameStatsPanel(game);
        agentsIDsSeenBySelectedAgent = new ArrayList<>();
    }

    @Override
    protected void activate() {
        super.activate();
        setSynchronousPainting(false);
    }

    public boolean isRenderable() {
        return isRenderable;
    }

    @Override
    public void setupFrame(final JFrame frame) {
        super.setupFrame(frame);
        
        WarGame.addWarGameListener(new WarGameAdapter(){
        	@Override
        	public void onGameOver()
        	{
        		new GameResultsDialog(frame);
        		WarGame.removeWarGameListener(this);
        	}
        });
        
        WindowListener[] wl = (WindowListener[])frame.getListeners(WindowListener.class);
        for (int i = 0; i < wl.length; i++) {
            frame.removeWindowListener(wl[i]);
        }
        
        for (MouseWheelListener listener : getDisplayPane().getMouseWheelListeners())
            getDisplayPane().removeMouseWheelListener(listener);
        
        setDisplayPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                render(g);
            }
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	WarGame.getInstance().setGamePaused();
            	int confirmation = JOptionPane.showConfirmDialog(null, "Êtes-vous sûr de vouloir arrêter le combat ?", "Demande de confirmation", JOptionPane.YES_NO_OPTION);
            	if (confirmation == JOptionPane.YES_OPTION)
            		WarGame.getInstance().setGameOver();
            	else
            		WarGame.getInstance().setGameResumed();
            }
        });
        frame.getContentPane().remove(((BorderLayout) frame.getContentPane().getLayout()).getLayoutComponent(BorderLayout.PAGE_START));
        frame.add(createJToolBar(), BorderLayout.PAGE_START);
        frame.setJMenuBar(createJMenuBar());

        frame.setTitle("Warbot");

        debugModePanel.init(frame);
        gameStatsPanel.init(frame);

        setCellSize(DEFAULT_CELL_SIZE);
        getDisplayPane().setSize(new Dimension(getWidth(), getHeight()));
        getDisplayPane().setBackground(new Color(230, 230, 230));

        mapExplorationMouseListener = new MapExplorationListener(this);
        getDisplayPane().addMouseMotionListener(mapExplorationMouseListener);
        getDisplayPane().addMouseListener(mapExplorationMouseListener);
        getDisplayPane().addMouseWheelListener(mapExplorationMouseListener);

        frame.setExtendedState(Frame.MAXIMIZED_BOTH);
        moveMapOffsetTo((Toolkit.getDefaultToolkit().getScreenSize().width - game.getMap().getWidth()) / 2.,
                (frame.getContentPane().getHeight() - game.getMap().getHeight()) / 2.);
    }

    protected JMenuBar createJMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        menuBar.add(debugModePanel.getDebugMenu());

        return menuBar;
    }

    protected JToolBar createJToolBar() {
        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);

        SingleAgentProbe<TKScheduler, Double> p = new SingleAgentProbe<>(getCommunity(), TKOrganization.ENGINE_GROUP, TKOrganization.SCHEDULER_ROLE, "GVT");
        addProbe(p);
        final TKScheduler tkScheduler = p.getCurrentAgentsList().get(0);

        toolBar.add(warToolBar);
        toolBar.addSeparator();
        toolBar.add(debugModePanel.getDebugModeToolBar());
        toolBar.addSeparator();
        toolBar.add(gameStatsPanel.getStatsToolBar());
        toolBar.addSeparator();
        toolBar.add(getToolBar());
        getToolBar().setFloatable(false);
        toolBar.addSeparator();
        JToolBar schedulerToolBar = tkScheduler.getSchedulerToolBar();
        schedulerToolBar.setFloatable(false);
        
        ((Component) schedulerToolBar.getComponents()[0]).addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				WarGame.getInstance().setGameResumed();
			}
        });
        
        ((Component) schedulerToolBar.getComponents()[1]).addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				WarGame.getInstance().setGamePaused();
			}
        });
        
        toolBar.add(schedulerToolBar);
        toolBar.add(tkScheduler.getGVTLabel());

        return toolBar;
    }

    @Override
    protected void render(Graphics g) {
        if (getDebugModePanel().getDebugTools().getSelectedAgent() != null) {
            // Update de l'affichage des infos sur l'unité sélectionnée
            getDebugModePanel().getDebugTools().getAgentInformationsPanel().update();
            // On récupère la liste des agents vus par l'agent sélectionné
            WarAgent selectedAgent = getDebugModePanel().getDebugTools().getSelectedAgent();
            if (selectedAgent instanceof ControllableWarAgent) {
                for (WarAgentPercept p : ((ControllableWarAgent) selectedAgent).getPercepts())
                    agentsIDsSeenBySelectedAgent.add(p.getID());
            }
        }
    }

    public void moveMapOffsetTo(double newOffsetX, double newOffsetY) {
        this.mapOffsetX = Math.max(Math.min(newOffsetX, getDisplayPane().getWidth() - 200), 200 - getDisplayedMapWidth());
        this.mapOffsetY = Math.max(Math.min(newOffsetY, getDisplayPane().getHeight() - 200), 200 - getDisplayedMapHeight());
        getFrame().repaint();
    }

    public CartesianCoordinates convertClickPositionToMapPosition(double clicX, double clicY) {
        return new CartesianCoordinates((clicX - getMapOffsetX()) / cellSize, (clicY - getMapOffsetY()) / cellSize);
    }

    public CartesianCoordinates convertMapPositionToDisplayPosition(double mapX, double mapY) {
        return new CartesianCoordinates((mapX * cellSize) + getMapOffsetX(), (mapY * cellSize) + getMapOffsetY());
    }

    public double getMapOffsetX() {
        return mapOffsetX;
    }

    public double getMapOffsetY() {
        return mapOffsetY;
    }

    public double getDisplayedMapWidth() {
        return getWidth() * cellSize;
    }

    public double getDisplayedMapHeight() {
        return getHeight() * cellSize;
    }

    public DebugModePanel getDebugModePanel() {
        return debugModePanel;
    }

    public WarGame getGame() {
        return game;
    }

    public WarToolBar getWarToolBar() {
        return warToolBar;
    }

    protected ArrayList<Integer> getAgentsIDsSeenBySelectedAgent() {
        return agentsIDsSeenBySelectedAgent;
    }

    public void setMapExplorationEventsEnabled(boolean bool) {
        mapExplorationMouseListener.setOnlyRightClick(!bool);
    }
}
