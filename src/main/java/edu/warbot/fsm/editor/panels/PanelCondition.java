package edu.warbot.fsm.editor.panels;

import edu.warbot.fsm.editor.models.ModelCondition;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;
import java.util.Random;

public class PanelCondition extends AbstractPanel {

    private static final long serialVersionUID = 1L;
    public boolean isSelected = false;
    PanelState panelSource;
    PanelState panelDest;
    Point positionDep = new Point(0, 0);
    Point positionArr = new Point(0, 0);
    Dimension dimSrc = new Dimension(0, 0);
    Dimension dimDest = new Dimension(0, 0);
    private ModelCondition modele;
    private Point ctrlPoint = new Point();

    public PanelCondition(ModelCondition m) {
        this.modele = m;

        try {
            panelSource = modele.getStateSource().getViewState();
            panelDest = modele.getStateDestination().getViewState();
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Error while loading fsm conditions"
                    + "\n check your fsm conditions", "Loading error", JOptionPane.ERROR_MESSAGE);
        }

        ctrlPoint = new Point(new Random().nextInt(100), new Random().nextInt(75));

    }

    public void paint(Graphics g1) {
        Graphics2D g = (Graphics2D) g1;

        if (this.isSelected) {
            g.setColor(Color.red);
            g.setStroke(new BasicStroke(2));
        } else {
            g.setColor(Color.black);
            g.setStroke(new BasicStroke(1));
        }

        try {
            this.positionDep = panelSource.position;
            this.positionArr = panelDest.position;
            this.dimSrc = panelSource.size;
            this.dimDest = panelDest.size;
        } catch (NullPointerException e) {
            if (this.panelSource == null) {
                this.positionDep = new Point(0, 0);
                this.dimSrc = new Dimension(0, 0);
            }
            if (this.positionArr == null) {
                this.positionArr = new Point(0, 0);
                this.dimDest = new Dimension(0, 0);
            }
        }

        int depx = (int) (positionDep.x + dimSrc.getWidth() / 2);
        int depy = (int) (positionDep.y + dimSrc.getHeight() / 2);

        int arrx = (int) (positionArr.x + dimDest.getWidth() / 2);
        int arry = (int) (positionArr.y + dimDest.getHeight() / 2);

        g.drawLine(depx, depy, arrx, arry);
//		drawLine(g, Depx, Depy, Arrx, Arry);
//		drawArc(g, Depx, Depy, Arrx, Arry);
//		drawBezier(g, Depx, Depy, Arrx, Arry);

        g.drawString(this.modele.getName(), (positionArr.x - positionDep.x) / 2 + positionDep.x + ctrlPoint.x,
                (positionArr.y - positionDep.y) / 2 + positionDep.y - 1 + ctrlPoint.y);

        //Reset les configues d'écriture
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(1));
    }

    @SuppressWarnings("unused")
	private void drawBezier(Graphics2D g, int x, int y, int arrx, int arry) {
        int c1 = ctrlPoint.x + (arrx - x) + x;
        int c2 = ctrlPoint.y + (arry - y) + y;

        Path2D path = new Path2D.Double();
        path.moveTo(x, y);
        path.curveTo(x, y, c1, c2, arrx, arry);
        g.draw(path);

    }

    @SuppressWarnings("unused")
	private void drawArc(Graphics2D g, int x1, int y1, int x2, int y2) {
        int x0 = x2 - x1 + x1;
        int y0 = y2 - y1 + y1;

        int r = (int) Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
        int x = x0 - r;
        int y = y0 - r;
        int width = 2 * r;
        int height = 2 * r;
        int startAngle = (int) (180 / Math.PI * Math.atan2(y1 - y0, x1 - x0));
        int endAngle = (int) (180 / Math.PI * Math.atan2(y2 - y0, x2 - x0));
        g.drawArc(x, y, width, height, startAngle, endAngle);


    }

    @SuppressWarnings("unused")
	private void drawLine(Graphics2D g, int depx, int depy, int arrx, int arry) {
        int midX = depx + (arrx - depx) * ctrlPoint.x / 100;

        g.drawLine(depx, depy, midX, depy);

        g.drawLine(midX, depy, midX, arry);

        g.drawLine(midX, arry, arrx, arry);
    }

    public ModelCondition getModele() {
        return this.modele;
    }
}
