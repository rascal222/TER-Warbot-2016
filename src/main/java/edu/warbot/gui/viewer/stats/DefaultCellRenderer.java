package edu.warbot.gui.viewer.stats;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

@SuppressWarnings("serial")
public class DefaultCellRenderer extends DefaultTableCellRenderer {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        setHorizontalAlignment(JLabel.CENTER);
        setBackground(Color.WHITE);
        setIcon(null);
        if (value instanceof Color) {
            Color color = (Color) value;
            setText("");
            setBackground(color);
        } else if (value instanceof ImageIcon) {
            setText("");
            setIcon((ImageIcon) value);
        } else {
            setText(value.toString());
        }
        return this;
    }
}