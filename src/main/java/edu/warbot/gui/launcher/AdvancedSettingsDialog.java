package edu.warbot.gui.launcher;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import edu.warbot.game.WarGameSettings;
import edu.warbot.gui.GuiIconsLoader;

@SuppressWarnings("serial")
public class AdvancedSettingsDialog  extends JDialog
{
	private WarGameSettings _settings;
    private JComboBox<String> cbLogLevel;
    private JCheckBox cbEnhancedGraphismEnabled;
	
	public AdvancedSettingsDialog(WarGameSettings settings, JFrame owner)
	{
		super(owner, "Paramètres avancés", true);
        setLayout(new BorderLayout());
        _settings = settings;
        
        setIconImage(GuiIconsLoader.getLogo("iconLauncher.png").getImage());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        JPanel panelAdvanced = new JPanel();
		panelAdvanced.setLayout(new BorderLayout());
		panelAdvanced.setBorder(new TitledBorder("Avancé"));
		String comboOption[] = {"ALL", "SEVERE", "WARNING", "INFO", "CONFIG", "FINE", "FINER", "FINEST"};
		this.cbLogLevel = new JComboBox<>(comboOption);
		cbLogLevel.setSelectedItem("WARNING");
		panelAdvanced.add(new JLabel("Niveau de détail des logs"), BorderLayout.NORTH);
		panelAdvanced.add(cbLogLevel, BorderLayout.CENTER);
		// graphisme
		cbEnhancedGraphismEnabled = new JCheckBox("Charger la vue 2D isométrique", false);
		panelAdvanced.add(cbEnhancedGraphismEnabled, BorderLayout.SOUTH);
		
		add(panelAdvanced, BorderLayout.CENTER);
		
		JPanel panelBas = new JPanel();

        JButton validButton = new JButton("Valider");
        validButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	validEnteredSettings();
            }
        });
        getRootPane().setDefaultButton(validButton);
        panelBas.add(validButton);

        JButton leaveButton = new JButton("Quitter");
        leaveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelBas.add(leaveButton);

        add(panelBas, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
	}
	
	private void validEnteredSettings()
	{
		_settings.setDefaultLogLevel((Level.parse((String) cbLogLevel.getSelectedItem())));
		_settings.setEnabledEnhancedGraphism(cbEnhancedGraphismEnabled.isSelected());
		dispose();
	}
	
	public void dispose()
	{
		super.dispose();
		
	}
}
