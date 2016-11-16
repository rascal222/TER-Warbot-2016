package edu.warbot.gui.viewer;

import edu.warbot.launcher.AbstractWarViewer;
import edu.warbot.tools.geometry.CartesianCoordinates;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

public class MapExplorationListener extends MouseAdapter {

    private static final int MAX_CELL_SIZE = 10;
    private static final int MIN_CELL_SIZE = 1;

    private AbstractWarViewer viewer;

    private CartesianCoordinates oldMouseDragPosition;
    private CartesianCoordinates oldViewerPosition;
    private boolean onlyRightClick;

    public MapExplorationListener(AbstractWarViewer viewer) {
        this.viewer = viewer;
        this.onlyRightClick = false;


    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if ((!onlyRightClick) || (onlyRightClick && e.getButton() == MouseEvent.BUTTON2)) {
            CartesianCoordinates movement = new CartesianCoordinates(
                    e.getX() - oldMouseDragPosition.getX(),
                    e.getY() - oldMouseDragPosition.getY());
            viewer.moveMapOffsetTo(movement.getX() + oldViewerPosition.getX(), movement.getY() + oldViewerPosition.getY());
        }
    }


    @Override
    public void mousePressed(MouseEvent e) {
        if ((!onlyRightClick) || (onlyRightClick && e.getButton() == MouseEvent.BUTTON2)) {
            oldMouseDragPosition = new CartesianCoordinates(e.getX(), e.getY());
            oldViewerPosition = new CartesianCoordinates(viewer.getMapOffsetX(), viewer.getMapOffsetY());
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int newCellSize = viewer.getCellSize();
        newCellSize -= e.getWheelRotation();
        newCellSize = Math.max(MIN_CELL_SIZE, newCellSize);
        newCellSize = Math.min(MAX_CELL_SIZE, newCellSize);

        CartesianCoordinates inMapClickPosition = viewer.convertClickPositionToMapPosition(e.getX(), e.getY());

        viewer.setCellSize(newCellSize);

        CartesianCoordinates inMapClickNewPosition = viewer.convertClickPositionToMapPosition(e.getX(), e.getY());
        viewer.moveMapOffsetTo(viewer.getMapOffsetX() - ((inMapClickPosition.getX() - inMapClickNewPosition.getX()) * newCellSize),
                viewer.getMapOffsetY() - ((inMapClickPosition.getY() - inMapClickNewPosition.getY()) * newCellSize));
    }

    public void setOnlyRightClick(boolean onlyRightClick) {
        this.onlyRightClick = onlyRightClick;
    }
}
