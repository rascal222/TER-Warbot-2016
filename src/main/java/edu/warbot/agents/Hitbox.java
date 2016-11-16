package edu.warbot.agents;

import java.awt.*;

public class Hitbox {

    private Shape shape;
    private double width, height;

    public Hitbox(Shape shape, double width, double height) {
        this.shape = shape;
        this.width = width;
        this.height = height;
    }

    public Shape getShape() {
        return shape;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }
}
