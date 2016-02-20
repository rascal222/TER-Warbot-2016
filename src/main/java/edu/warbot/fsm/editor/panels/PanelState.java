package edu.warbot.fsm.editor.panels;

import edu.warbot.fsm.editor.models.ModelState;

import java.awt.*;
import java.util.Random;

public class PanelState extends AbstractPanel {

    private static final long serialVersionUID = 1L;
    public Point position = new Point();
    public boolean isSelected = false;
    public boolean isFirstState = false;
    Dimension size = new Dimension(100, 30);
    private ModelState modeleState;

    public PanelState(ModelState m) {
        this.modeleState = m;
        position = new Point(new Random().nextInt(600), new Random().nextInt(400));
    }

    @SuppressWarnings("unused")
	public void paint(Graphics g) {

        String msgName = this.modeleState.getName();
        String msgPlan = this.modeleState.getPlanSimpleName();

        if (this.isSelected)
            g.setColor(Color.red);
        else if (isFirstState)
            g.setColor(Color.GREEN);
        else
            g.setColor(Color.black);

        int padding = 2;

        Font font = new Font("Arial", Font.PLAIN, 10);
//		g.setFont(font);

        FontMetrics metrics = g.getFontMetrics(g.getFont());
        Dimension speechBubbleSizeName = new Dimension(metrics.stringWidth(msgName) + (2 * padding), metrics.getHeight() + (2 * padding));
        Dimension speechBubbleSizePlan = new Dimension(metrics.stringWidth(msgPlan) + (2 * padding), metrics.getHeight() + (2 * padding));

        Dimension speechBubbleSizeMax = new Dimension();
        speechBubbleSizeMax.width = Math.max(speechBubbleSizeName.width, speechBubbleSizePlan.width);
        speechBubbleSizeMax.height = Math.max(speechBubbleSizeName.height, speechBubbleSizePlan.height);

        g.clearRect(position.x, position.y, speechBubbleSizeMax.width, size.height);

        g.drawRect(position.x, position.y, speechBubbleSizeMax.width, size.height);

//        g.drawRect(position.x, position.y, size.width, size.height);

        g.drawLine(position.x, position.y + size.height / 2
                , position.x + speechBubbleSizeMax.width - 1, position.y + size.height / 2);

        g.drawString(this.modeleState.getName(), position.x + 1, position.y + 12);

        g.drawString(this.modeleState.getPlanSimpleName(), position.x + 2, position.y + size.height / 2 + 12);


    }

    public Dimension getSize() {
        return this.size;
    }

    public Point getPosition() {
        return this.position;
    }

    public void setPosition(Point p) {
        this.position = p;
    }

    public ModelState getModelState() {
        return this.modeleState;
    }

    public void setFirstState(boolean b) {
        isFirstState = b;
    }

}
