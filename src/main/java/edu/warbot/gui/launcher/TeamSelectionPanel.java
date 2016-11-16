package edu.warbot.gui.launcher;

import edu.warbot.agents.teams.Team;
import edu.warbot.gui.GuiIconsLoader;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;
import javax.swing.plaf.metal.MetalComboBoxUI;

import java.awt.*;
import java.util.Map;
import java.util.Vector;

@SuppressWarnings("serial")
public class TeamSelectionPanel extends JPanel
{
	private int maxWidth = 0;
	
    private Map<String, Team> _teams;
    boolean _necessary;
	
	ImageIcon[] logos;
	String[] names;
	String[] descriptions;
	
	WideComboBox teamList;
	JTextArea teamDescription;
	
	public TeamSelectionPanel(Map<String, Team> teams)
    {
		this(teams, true);
    }
	
	public TeamSelectionPanel(Map<String, Team> teams, boolean necessary)
    {
    	super();

        _teams = teams;
        _necessary = necessary;
        reloadTeams();
    }
    
    public Team getSelectedTeam()
    {
    	return _teams.get(names[teamList.getSelectedIndex()]);
    }
    
    @SuppressWarnings("unchecked")
	public void reloadTeams()
    {
    	int saveSelectedIndex = 0;
    	if(teamList != null)
    		saveSelectedIndex = teamList.getSelectedIndex();
    	
    	removeAll();

    	setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	setLayout(new BorderLayout());
    	setPreferredSize(new Dimension(-1, 70));
    	
        //Load the pet images and create an array of indexes.
        int nbTeam = _teams.size();
        if(!_necessary)
        {
        	nbTeam++;
        }
    	logos = new ImageIcon[nbTeam];
    	names = new String[nbTeam];
    	descriptions = new String[nbTeam];
        Integer[] intArray = new Integer[names.length];
        int index = 0;
        if(!_necessary)
        {
        	intArray[index] = new Integer(index);
            logos[index] = new ImageIcon(GuiIconsLoader.getIcon("no_image.png").getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));
            names[index] = "< None >";
            descriptions[index] = "Pas d'équipe sélectionnée";
            if (logos[index] != null)
            	logos[index].setDescription(names[index]);
            index++;
        }
        for (Team t : _teams.values())
        {
            intArray[index] = new Integer(index);
            logos[index] = t.getLogo();
            names[index] = t.getTeamName();
            descriptions[index] = t.getDescription();
            if (logos[index] != null)
            	logos[index].setDescription(names[index]);
            index++;
        }

		// Astuce pour avoir la largeur max de la liste déroulante dès la première ouverture de la liste
		for(int i=0; i<names.length; i++)
		{
			JLabel j = new JLabel(names[i]);
			ImageIcon logo = logos[i];
			if (logo != null)
				j.setIcon(logo);
			if(maxWidth < j.getPreferredSize().width)
			{
				maxWidth = j.getPreferredSize().width;
			}
		}
		if(_teams.size() > 5)
			maxWidth += 20;
		
		//Create the combo box.
        teamList = new WideComboBox(intArray);
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        teamList.setRenderer(renderer);
        teamList.setMaximumRowCount(5);
        teamList.setSize(new Dimension(220, 60));
        teamList.setMaximumSize(new Dimension(220, 60));
        teamList.setMinimumSize(new Dimension(220, 60));
        teamList.setPreferredSize(new Dimension(220, 60));
        teamList.addPopupMenuListener(new PopupMenuListener()
        {
			@Override
			public void popupMenuWillBecomeVisible(PopupMenuEvent e)
			{
				teamList.revalidate();
				teamList.repaint();
			}
			
			@Override
			public void popupMenuWillBecomeInvisible(PopupMenuEvent e)
			{
				teamDescription.setText(descriptions[teamList.getSelectedIndex()]);
				teamList.revalidate();
				teamList.repaint();
			}
			
			@Override
			public void popupMenuCanceled(PopupMenuEvent e)
			{}
		});
        
        add(teamList, BorderLayout.WEST);
        
    	// Add the team description in a JLabel
        JPanel teamDescriptionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gridOfTeamDescription = new GridBagConstraints();
        
        gridOfTeamDescription.fill = GridBagConstraints.BOTH;
        gridOfTeamDescription.weighty = 0.5;
        gridOfTeamDescription.weightx = 0.5;
        gridOfTeamDescription.gridx = 0;
        gridOfTeamDescription.gridy = 0;
        teamDescriptionPanel.add(new JPanel(), gridOfTeamDescription);
        
        teamDescription = new JTextArea();
        teamDescription.setText(descriptions[teamList.getSelectedIndex()]);
        teamDescription.setWrapStyleWord(true);
        teamDescription.setLineWrap(true);
        teamDescription.setOpaque(false);
        teamDescription.setEditable(false);
        teamDescription.setFocusable(false);
        teamDescription.setBackground(UIManager.getColor("Label.background"));
        teamDescription.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        teamDescription.setFont(teamDescription.getFont().deriveFont(Font.ITALIC));
        gridOfTeamDescription.fill = GridBagConstraints.BOTH;
        gridOfTeamDescription.weighty = 0.5;
        gridOfTeamDescription.weightx = 0.5;
        gridOfTeamDescription.gridx = 0;
        gridOfTeamDescription.gridy = 1;
        teamDescriptionPanel.add(teamDescription, gridOfTeamDescription);
        
        gridOfTeamDescription.fill = GridBagConstraints.BOTH;
        gridOfTeamDescription.weighty = 0.5;
        gridOfTeamDescription.weightx = 0.5;
        gridOfTeamDescription.gridx = 0;
        gridOfTeamDescription.gridy = 2;
        teamDescriptionPanel.add(new JPanel(), gridOfTeamDescription);
        
        add(teamDescriptionPanel, BorderLayout.CENTER);
        
        if(saveSelectedIndex < _teams.size())
        	teamList.setSelectedIndex(saveSelectedIndex);
        else
        	teamList.setSelectedIndex(0);
    }
    
    @SuppressWarnings("rawtypes")
	class ComboBoxRenderer extends JLabel implements ListCellRenderer
    {
		public ComboBoxRenderer()
		{
			setOpaque(true);
			setHorizontalAlignment(SwingConstants.LEFT);
			setVerticalAlignment(SwingConstants.CENTER);
		}
		
		/*
		* This method finds the image and text corresponding
		* to the selected value and returns the label, set up
		* to display the text and image.
		*/
		public Component getListCellRendererComponent(
		                    JList list,
		                    Object value,
		                    int index,
		                    boolean isSelected,
		                    boolean cellHasFocus)
		{
			//Get the selected index. (The index param isn't
			//always valid, so just use the value.)
			int selectedIndex = ((Integer)value).intValue();

			//Set the icon and text.  If icon was null, say so.
			setIcon(logos[selectedIndex]);
			setText(names[selectedIndex]);
			
			if (isSelected)
			{
	            setBackground(list.getSelectionBackground());
	            setForeground(list.getSelectionForeground());
				teamDescription.setText(descriptions[selectedIndex]);
	        }
			else
	        {
	            setBackground(list.getBackground());
	            setForeground(list.getForeground());
	        }
			return this;
		}
	}
    
    @SuppressWarnings("rawtypes")
	public class WideComboBox extends JComboBox
	{
        public WideComboBox()
        {
        	super();
			initUI();
        } 
     
        @SuppressWarnings("unchecked")
		public WideComboBox(final Object items[])
        { 
            super(items);
			initUI();
        } 
     
        @SuppressWarnings("unchecked")
		public WideComboBox(Vector items)
        { 
            super(items);
			initUI();
        } 
     
        @SuppressWarnings("unchecked")
		public WideComboBox(ComboBoxModel aModel)
        { 
            super(aModel);
			initUI();
        }
        
        private void initUI()
        {
			setUI(new MetalComboBoxUI(){
				@Override
				protected ComboPopup createPopup() {
					BasicComboPopup popup = new BasicComboPopup(comboBox) {
						@Override
						protected Rectangle computePopupBounds(int px,int py,int pw,int ph) {
							return super.computePopupBounds(px,py,Math.max(maxWidth,pw),ph);
						}
					};
				popup.getAccessibleContext().setAccessibleParent(comboBox);
				return popup;
				}
			});
        }
    }
}
