package edu.warbot.agents.percepts;

import edu.warbot.agents.ControllableWarAgent;
import edu.warbot.agents.WarAgent;
import edu.warbot.agents.enums.WarAgentType;
import edu.warbot.game.WarGame;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PerceptsGetter {

    private ControllableWarAgent _agent;
    private WarGame game;

    private Area thisTickPerceptionArea;
    private boolean perceptsAlreadyGetThisTick;
    private List<WarAgentPercept> allPercepts;
    private List<WarAgentPercept> alliesPercepts;
    private List<WarAgentPercept> enemiesPercepts;
    private List<WarAgentPercept> resourcesPercepts;
    private List<WallPercept> wallsPercepts;

    public PerceptsGetter(ControllableWarAgent agent, WarGame game) {
        _agent = agent;
        this.game = game;
        allPercepts = new ArrayList<>();
        alliesPercepts = new ArrayList<>();
        enemiesPercepts = new ArrayList<>();
        resourcesPercepts = new ArrayList<>();
        wallsPercepts = new ArrayList<>();
    }

    protected ControllableWarAgent getAgent() {
        return _agent;
    }

    protected WarGame getGame() {
        return game;
    }

    public List<WarAgentPercept> getPercepts() {
        if (!perceptsAlreadyGetThisTick) {
            allPercepts = getAgentPercepts();
            for (WarAgentPercept percept : allPercepts) {
                if (getAgent().isEnemy(percept)) {
                    enemiesPercepts.add(percept);
                } else {
                    if (percept.getType().equals(WarAgentType.WarFood))
                        resourcesPercepts.add(percept);
                    else
                        alliesPercepts.add(percept);
                }
            }
            perceptsAlreadyGetThisTick = true;
            Collections.sort(alliesPercepts);
            Collections.sort(enemiesPercepts);
            Collections.sort(resourcesPercepts);
        }
        return allPercepts;
    }

    private List<WarAgentPercept> getAgentPercepts() {
        List<WarAgentPercept> percepts = new ArrayList<WarAgentPercept>();

        Area visibleArea = getPerceptionArea();
        for (WarAgent agentToTestVisible : getGame().getAllAgentsInRadiusOf(getAgent(), getAgent().getDistanceOfView())) {
            if (agentToTestVisible.getID() != getAgent().getID()) {
                if (visibleArea.contains(agentToTestVisible.getX(), agentToTestVisible.getY()))
                    percepts.add(new WarAgentPercept(getAgent(), agentToTestVisible));
            }
        }

        return percepts;
    }

    public Area getPerceptionArea() {
        if (thisTickPerceptionArea == null)
            thisTickPerceptionArea = new Area(getPerceptionAreaShape());
        return thisTickPerceptionArea;
    }

    protected abstract Shape getPerceptionAreaShape();

    public List<WarAgentPercept> getWarAgentsPercepts(boolean ally) {
        if (!perceptsAlreadyGetThisTick)
            getPercepts();

        if (ally)
            return alliesPercepts;
        else
            return enemiesPercepts;
    }

    public List<WarAgentPercept> getResourcesPercepts() {
        if (!perceptsAlreadyGetThisTick)
            getPercepts();

        return resourcesPercepts;
    }

    public List<WarAgentPercept> getPerceptsByType(WarAgentType agentType, boolean ally) {
        if (!perceptsAlreadyGetThisTick)
            getPercepts();

        List<WarAgentPercept> perceptsToReturn = new ArrayList<>();
        List<WarAgentPercept> perceptsToLoop;

        if (ally)
            perceptsToLoop = alliesPercepts;
        else
            perceptsToLoop = enemiesPercepts;

        for (WarAgentPercept warPercept : perceptsToLoop) {
            if (warPercept.getType().equals(agentType)) {
                perceptsToReturn.add(warPercept);
            }
        }

        return perceptsToReturn;
    }

    public void setPerceptsOutdated() {
        thisTickPerceptionArea = null;
        perceptsAlreadyGetThisTick = false;
        allPercepts.clear();
        alliesPercepts.clear();
        enemiesPercepts.clear();
        resourcesPercepts.clear();
        wallsPercepts.clear();
    }

    public void forcePerceptsUpdate() {
        setPerceptsOutdated();
        getPercepts();
    }

    public List<WallPercept> getWallsPercepts() {
        return wallsPercepts;
    }

    @SuppressWarnings("unused")
	private Area removeWallsHidedAreasAndGetWallPercepts(Area initialPerceptionArea) {
        Area finalPerceptionArea;
        ArrayList<Line2D.Double> wallSegmentsInPerception = new ArrayList<>();

//        Area wallsInPerceptionArea = game.getMap().getMapForbidArea();
//        for (WarAgent building : game.getBuildingsInRadiusOf(getAgent(), getAgent().getHitboxMaxRadius() + getAgent().getDistanceOfView())) {
//            wallsInPerceptionArea.add(building.getActualForm());
//        }
//        wallsInPerceptionArea.intersect(initialPerceptionArea);

        finalPerceptionArea = new Area(initialPerceptionArea);
        finalPerceptionArea.intersect(new Area(new Rectangle2D.Double(0, 0, game.getMap().getWidth(), game.getMap().getHeight())));
//        finalPerceptionArea.subtract(wallsInPerceptionArea);

//        Path2D.Double wallsContoursPath = new Path2D.Double();
//        wallsContoursPath.append(wallsInPerceptionArea, false);
//
//         On récupère les segments de murs dans la zone de perception
//         Pour chaque bloque de murs dans la zone de perception
//        List<Point2D.Double> wallPoints = new ArrayList<>();
//        for (Path2D.Double wallInPerceptionPath : dividePluralPathIntoSingularPathsLined(wallsContoursPath)) {
//            List<Point2D.Double> currentWallPoints = GeometryTools.getPointsFromPath(wallInPerceptionPath);
//            if (currentWallPoints.size() > 1) {
//                for (int i = 0; i < (currentWallPoints.size() - 1); i++) {
//                    wallSegmentsInPerception.add(new Line2D.Double(currentWallPoints.get(i), currentWallPoints.get(i + 1)));
//                }
//                wallSegmentsInPerception.add(new Line2D.Double(currentWallPoints.get(currentWallPoints.size() - 1), currentWallPoints.get(0)));
//            }
//            wallPoints.addAll(currentWallPoints);
//        }
//
//         On récupère les points de ces murs vus par l'agent
//        HashMap<Point2D.Double, Boolean> wallPointsSeenByAgent = new HashMap<>();
//        for (Point2D.Double wallPoint : wallPoints) {
//            Line2D.Double lineFromPointToAgent = new Line2D.Double(wallPoint, getAgent().getPosition());
//            boolean pointSeenByAgent = true;
//            for (Line2D.Double comparedWall : wallSegmentsInPerception) {
//                if (!(comparedWall.getP1().equals(wallPoint) || comparedWall.getP2().equals(wallPoint))) { // Si le point n'appartient pas au segment
//                    if (lineFromPointToAgent.intersectsLine(comparedWall)) {
//                        pointSeenByAgent = false;
//                        break;
//                    }
//                }
//            }
//            wallPointsSeenByAgent.put(wallPoint, pointSeenByAgent);
//        }
//         On récupère les murs vus par l'agent (dont les deux extrémités ainsi que le point du milieu du mur sont vus par l'agent
//        List<Line2D.Double> seenWallsSegments = new ArrayList<>();
//        for (Line2D.Double wall : wallSegmentsInPerception) {
//            boolean wallSeenByAgent = true;
//            if (wallPointsSeenByAgent.get(wall.getP1()) && wallPointsSeenByAgent.get(wall.getP2())) {
//                 Si on voit les deux extrémités du mur
//                 On regarde si le milieu du mur est aussi vu
//                Line2D.Double lineFromMiddleToAgent = new Line2D.Double(
//                        new Point2D.Double((wall.getX1() + wall.getX2()) / 2., (wall.getY1() + wall.getY2()) / 2.),
//                        getAgent().getPosition());
//                for (Line2D.Double comparedWall : wallSegmentsInPerception) {
//                    if (!(comparedWall.getP1().equals(wall.getP1()) && comparedWall.getP2().equals(wall.getP2()))) { // Si les deux murs ne sont pas les mêmes
//                        if (lineFromMiddleToAgent.intersectsLine(comparedWall)) {
//                            wallSeenByAgent = false;
//                            break;
//                        }
//                    }
//                }
//            } else if (wallPointsSeenByAgent.get(wall.getP1()) || wallPointsSeenByAgent.get(wall.getP2())) {
//                 Si une seule extrémité du mur est vue
//                 TODO Divide the wall to get only seen part
//                seenWallsSegments.add(wall);
//                wallSeenByAgent = false;
//            } else {
//                wallSeenByAgent = false;
//            }
//            if (wallSeenByAgent) {
//                seenWallsSegments.add(wall);
//                wallsPercepts.add(new WallPercept(getAgent(), wall));
//            }
//        }

        // On supprime de la zone de perception de l'agent l'ombre des murs
//        double shadowPointsDistance = 100;
//        for (Line2D.Double wallSegment : seenWallsSegments) {
//            Path2D.Double currentShadow = new Path2D.Double();
//
//            CartesianCoordinates srcPoint = new CartesianCoordinates(wallSegment.getP1().getX(), wallSegment.getP1().getY());
//            CartesianCoordinates destPoint = new CartesianCoordinates(wallSegment.getP2().getX(), wallSegment.getP2().getY());
//            CartesianCoordinates srcShadowPoint = WarMathTools.addTwoPoints(srcPoint, new PolarCoordinates(shadowPointsDistance, getAgent().getPosition().getAngleToPoint(srcPoint)));
//            CartesianCoordinates destShadowPoint = WarMathTools.addTwoPoints(destPoint, new PolarCoordinates(shadowPointsDistance, getAgent().getPosition().getAngleToPoint(destPoint)));
//
//            currentShadow.moveTo(srcPoint.getX(), srcPoint.getY());
//            currentShadow.lineTo(destPoint.getX(), destPoint.getY());
//            currentShadow.lineTo(destShadowPoint.getX(), destShadowPoint.getY());
//            currentShadow.lineTo(srcShadowPoint.getX(), srcShadowPoint.getY());
//            currentShadow.lineTo(srcPoint.getX(), srcPoint.getY());
//
//            finalPerceptionArea.subtract(new Area(currentShadow));
//        }

//        if(! finalPerceptionArea.isSingular()) {
//            Path2D.Double finalPerceptionPath = new Path2D.Double();
//            finalPerceptionPath.append(finalPerceptionArea, false);
//            List<Path2D.Double> singularVisiblePaths = dividePluralPathIntoSingularPaths(finalPerceptionPath);
//            for(Path2D.Double path : singularVisiblePaths) {
//                Area pathArea = new Area(path);
//                pathArea.intersect(new Area(getAgent().getActualForm()));
//                if(pathArea.isEmpty()) {
//                    finalPerceptionArea.subtract(new Area(path));
//                }
//            }
//        }

//        Area debug = new Area();
//        for(Point2D.Double point : GeometryTools.getPointsFromPath(wallsContoursPath)) {
//            debug.add(new Area(new WarCircle(point.getX(), point.getY(), .5)));
//        }
//        debug.add(new Area(wallsContoursPath));
//        getAgent().setDebugShape(debug);

        return finalPerceptionArea;
    }

    @SuppressWarnings("unused")
	private ArrayList<Path2D.Double> dividePluralPathIntoSingularPathsLined(Path2D.Double path) {
        ArrayList<Path2D.Double> singularPaths = new ArrayList<Path2D.Double>();

        PathIterator it = path.getPathIterator(null);
        double[] coords = new double[6];
        Path2D.Double currentPath = null;
        while (!it.isDone()) {
            int type = it.currentSegment(coords);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    if (currentPath != null)
                        singularPaths.add(new Path2D.Double(currentPath));
                    currentPath = new Path2D.Double();
                    currentPath.moveTo(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_LINETO:
                case PathIterator.SEG_CUBICTO:
                case PathIterator.SEG_QUADTO:
                    currentPath.lineTo(coords[0], coords[1]);
                case PathIterator.SEG_CLOSE:
                    currentPath.closePath();
                    break;
                default:
                    throw new IllegalStateException("unknown PathIterator segment type: " + type);
            }
            it.next();
        }
        if (currentPath != null)
            singularPaths.add(new Path2D.Double(currentPath));

        return singularPaths;
    }

    @SuppressWarnings("unused")
	private ArrayList<Path2D.Double> dividePluralPathIntoSingularPaths(Path2D.Double path) {
        ArrayList<Path2D.Double> singularPaths = new ArrayList<Path2D.Double>();

        PathIterator it = path.getPathIterator(null);
        double[] coords = new double[6];
        Path2D.Double currentPath = null;
        while (!it.isDone()) {
            int type = it.currentSegment(coords);
            switch (type) {
                case PathIterator.SEG_MOVETO:
                    if (currentPath != null)
                        singularPaths.add(new Path2D.Double(currentPath));
                    currentPath = new Path2D.Double();
                    currentPath.moveTo(coords[0], coords[1]);
                    break;
                case PathIterator.SEG_LINETO:
                    currentPath.lineTo(coords[0], coords[1]);
                case PathIterator.SEG_CUBICTO:
                    currentPath.curveTo(coords[0], coords[1], coords[2], coords[3], coords[4], coords[5]);
                case PathIterator.SEG_QUADTO:
                    currentPath.quadTo(coords[0], coords[1], coords[2], coords[3]);
                case PathIterator.SEG_CLOSE:
                    currentPath.closePath();
                    break;
                default:
                    throw new IllegalStateException("unknown PathIterator segment type: " + type);
            }
            it.next();
        }
        if (currentPath != null)
            singularPaths.add(new Path2D.Double(currentPath));

        return singularPaths;
    }
}