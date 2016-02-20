package edu.warbot.gui;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class PaintablePanel extends JPanel {

    private Painter painter;
    private int id;

    public PaintablePanel(Dimension size, int paintingID, Painter painter) {
        super();
        this.painter = painter;
        this.id = paintingID;

        setPreferredSize(size);
        setSize(size);
        setMinimumSize(size);
        setMaximumSize(size);
    }

    public PaintablePanel(Dimension size, int paintingID) {
        this(size, paintingID, null);
    }

    public void setPainter(Painter painter) {
        this.painter = painter;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (painter != null)
            painter.paint((Graphics2D) g, id);
    }

    public interface Painter {
        void paint(Graphics2D g, int paintingID);
    }
}
