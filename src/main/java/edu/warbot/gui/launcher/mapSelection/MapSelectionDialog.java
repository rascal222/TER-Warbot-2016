package edu.warbot.gui.launcher.mapSelection;

import edu.warbot.gui.GuiIconsLoader;
import edu.warbot.gui.launcher.WarLauncherInterface;
import edu.warbot.maps.AbstractWarMap;
import org.reflections.Reflections;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;
import java.util.Vector;

@SuppressWarnings("serial")
public class MapSelectionDialog extends JFrame implements ActionListener, ListSelectionListener {

    private WarLauncherInterface warLauncherInterface;
    private Vector<MapMiniature> mapMiniaturesList;
    @SuppressWarnings("rawtypes")
	private JList mapMiniaturesJList;
    private MapMiniaturePanel mapMiniaturePanel;

    @SuppressWarnings({ "unchecked", "rawtypes" })
	public MapSelectionDialog(WarLauncherInterface warLauncherInterface) {
        super("Choix de la carte");
        setLayout(new BorderLayout());

        this.warLauncherInterface = warLauncherInterface;

        /* *** Fenêtre *** */
        setSize(1100, 450);
        setMinimumSize(getSize());
        setLocationRelativeTo(null);
        setIconImage(GuiIconsLoader.getLogo("iconLauncher.png").getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Map list
        MapMiniature selectedMapMiniature = null;
        mapMiniaturesList = new Vector<>();
        Reflections reflections = new Reflections(AbstractWarMap.class.getPackage().getName());
        Set<Class<? extends AbstractWarMap>> allClasses =
                reflections.getSubTypesOf(AbstractWarMap.class);
        for (Class<? extends AbstractWarMap> mapClass : allClasses) {

            try {
                MapMiniature currentMapMiniature = new MapMiniature(mapClass.newInstance(), MapMiniature.SIZE_SMALL);
                mapMiniaturesList.add(currentMapMiniature);
                if (currentMapMiniature.getMap().getName().equals(warLauncherInterface.getGameSettings().getSelectedMap().getName()))
                    selectedMapMiniature = currentMapMiniature;
            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }
        mapMiniaturesJList = new JList(mapMiniaturesList);

        if (selectedMapMiniature != null)
            mapMiniaturesJList.setSelectedValue(selectedMapMiniature, true);
        else
            mapMiniaturesJList.setSelectedIndex(0);
        mapMiniaturesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        mapMiniaturesJList.setCellRenderer(new MapMiniatureCellRenderer());
        mapMiniaturesJList.addListSelectionListener(this);
        add(new JScrollPane(mapMiniaturesJList), BorderLayout.WEST);


        // Selected map
        if (!mapMiniaturesJList.isSelectionEmpty()) {
            selectedMapMiniature = new MapMiniature(((MapMiniature) mapMiniaturesJList.getSelectedValue()).getMap(), MapMiniature.SIZE_VERY_LARGE);
            mapMiniaturePanel = new MapMiniaturePanel(selectedMapMiniature);
            add(mapMiniaturePanel, BorderLayout.CENTER);
        }

        // Map key
        JPanel pnlKey = new JPanel(new BorderLayout());
        pnlKey.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.GRAY));
        // Title
        JLabel lblKeyTitle = new JLabel("Légende");
        lblKeyTitle.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 24));
        pnlKey.add(lblKeyTitle, BorderLayout.NORTH);
        // Content
        JPanel pnlContent = new JPanel();
        pnlContent.setLayout(new BoxLayout(pnlContent, BoxLayout.Y_AXIS));
        pnlContent.add(new MapKeyItem(MapKeyItem.ID_KEY_TEAMS_POSITIONS, "Position des équipes", "Une seule position par équipe sera utilisée durant la partie"));
        pnlContent.add(new MapKeyItem(MapKeyItem.ID_KEY_FOOD_POSITIONS, "Position de la nourriture", "La nourriture apparaîtra aléatoirement dans une de ces zones"));
        pnlContent.add(new MapKeyItem(MapKeyItem.ID_KEY_WALLS, "Mur", "Les murs sont infranchissables par les robots"));
        pnlContent.add(new MapKeyItem(MapKeyItem.ID_KEY_MAP_LIMITS, "Limites de la carte", "Si un robot arrive à une limite de la carte, il apparaît de l'autre coté"));
        pnlKey.add(pnlContent, BorderLayout.CENTER);
        add(pnlKey, BorderLayout.EAST);

        JButton btnValid = new JButton("Valider");
        btnValid.addActionListener(this);
        add(btnValid, BorderLayout.SOUTH);
    }

    public MapMiniature getSelectedItem() {
        return (MapMiniature) mapMiniaturesJList.getSelectedValue();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        warLauncherInterface.getGameSettings().setSelectedMap(getSelectedItem().getMap());
        warLauncherInterface.notifySelectedMapChanged();
        this.dispose();
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        mapMiniaturePanel.setMap(((MapMiniature) mapMiniaturesJList.getSelectedValue()).getMap());
    }
}
