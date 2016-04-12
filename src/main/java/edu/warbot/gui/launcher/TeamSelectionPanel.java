package edu.warbot.gui.launcher;

import edu.warbot.agents.teams.Team;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

@SuppressWarnings("serial")
public class TeamSelectionPanel extends JPanel
{
    private Map<String, Team> availableTeams;
	
	ImageIcon[] logos;
	String[] names;
	String[] descriptions;
	
	JCheckBox selectedTeam;
	@SuppressWarnings("rawtypes")
	JComboBox teamList;
	JTextArea teamDescrition;
	
	public TeamSelectionPanel(Map<String, Team> availableTeams)
    {
		this(availableTeams, true);
    }
	
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public TeamSelectionPanel(Map<String, Team> availableTeams, boolean necessary)
    {
    	super();
    	setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	setLayout(new GridBagLayout());
    	setPreferredSize(new Dimension(-1, 70));
        GridBagConstraints gridOfTeamElement = new GridBagConstraints();

        this.availableTeams = availableTeams;
        
        //Load the pet images and create an array of indexes.
        int nbTeam = availableTeams.size();
        if(!necessary)
        {
        	nbTeam++;
        }
    	logos = new ImageIcon[nbTeam];
    	names = new String[nbTeam];
    	descriptions = new String[nbTeam];
        Integer[] intArray = new Integer[names.length];
        int index = 0;
        if(!necessary)
        {
        	intArray[index] = new Integer(index);
            logos[index] = null;
            names[index] = "None";
            descriptions[index] = "Pas d'équipe sélectionnée";
            if (logos[index] != null)
            	logos[index].setDescription(names[index]);
            index++;
        }
        for (Team t : availableTeams.values())
        {
            intArray[index] = new Integer(index);
            logos[index] = t.getLogo();
            names[index] = t.getTeamName();
            descriptions[index] = t.getDescription();
            if (logos[index] != null)
            	logos[index].setDescription(names[index]);
            index++;
        }
        
        //Create the combo box.
        teamList = new JComboBox(intArray);
        ComboBoxRenderer renderer = new ComboBoxRenderer();
        teamList.setRenderer(renderer);
        teamList.setMaximumRowCount(5);
        teamList.setSize(new Dimension(300, 60));
        teamList.addActionListener(new ActionListener(){
        	public void actionPerformed(ActionEvent e){
        		teamDescrition.setText(descriptions[teamList.getSelectedIndex()]);
        	}
        });
        
        //Lay out the demo.
        gridOfTeamElement.fill = GridBagConstraints.NONE;
        gridOfTeamElement.weighty = 0.;
        gridOfTeamElement.gridx = 0;
        gridOfTeamElement.gridy = 0;
        add(teamList, gridOfTeamElement);
        
    	// Add the team description in a JLabel
        teamDescrition = new JTextArea();
        teamDescrition.setMinimumSize(new Dimension(200, 60));
        teamDescrition.setText(descriptions[teamList.getSelectedIndex()]);
        
        teamDescrition.setWrapStyleWord(true);
        teamDescrition.setLineWrap(true);
        teamDescrition.setOpaque(false);
        teamDescrition.setEditable(false);
        teamDescrition.setFocusable(false);
        teamDescrition.setBackground(UIManager.getColor("Label.background"));
        //teamDescrition.setBorder(UIManager.getBorder("Label.border"));
        
        teamDescrition.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        teamDescrition.setFont(teamDescrition.getFont().deriveFont(Font.ITALIC));
        gridOfTeamElement.fill = GridBagConstraints.HORIZONTAL;
        gridOfTeamElement.weighty = 0.5;
        gridOfTeamElement.weightx = 0.5;
        gridOfTeamElement.gridx = 1;
        gridOfTeamElement.gridy = 0;
        add(teamDescrition, gridOfTeamElement);
        
        gridOfTeamElement.fill = GridBagConstraints.HORIZONTAL;
        gridOfTeamElement.weighty = 0.5;
        gridOfTeamElement.weightx = 1.0;
        gridOfTeamElement.gridx = 2;
        gridOfTeamElement.gridy = 0;
        add(new JPanel(), gridOfTeamElement);
    }
    
    @SuppressWarnings("rawtypes")
	class ComboBoxRenderer extends JLabel implements ListCellRenderer
    {
		private Font uhOhFont;
		
		public ComboBoxRenderer()
		{
			setOpaque(true);
			setHorizontalAlignment(LEFT);
			setVerticalAlignment(CENTER);
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
			JPanel team = new JPanel();
			team.setLayout(new BoxLayout(team, BoxLayout.X_AXIS));
			JLabel nameAndLogo = new JLabel(names[selectedIndex]);
			ImageIcon logo = logos[selectedIndex];
			if (logo != null)
			{
				nameAndLogo.setIcon(logo);
			}
			else
				nameAndLogo.setText(names[selectedIndex] + " (no image available)");
			team.add(nameAndLogo);

			if (isSelected || cellHasFocus)
			{
				team.setBackground(list.getSelectionBackground());
				team.setForeground(list.getSelectionForeground());
				if(isSelected)
					teamDescrition.setText(descriptions[selectedIndex]);
			}
			else
			{
				team.setBackground(list.getBackground());
				team.setForeground(list.getForeground());
			}
			return team;
		}
		
		//Set the font and text when no image was found.
		protected void setUhOhText(String uhOhText, Font normalFont)
		{
			if (uhOhFont == null) //lazily create this font
				uhOhFont = normalFont.deriveFont(Font.ITALIC);
			setFont(uhOhFont);
			setText(uhOhText);
		}
	}
    
    public Team getSelectedTeam()
    {
    	return availableTeams.get(names[teamList.getSelectedIndex()]);
    }
}
