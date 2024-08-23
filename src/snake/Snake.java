package snake;

import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    private final LinkedList<Point> body;
    private Direction currentDirection;

    public Snake(Point startPos) {
        body = new LinkedList<>(List.of(startPos));
        currentDirection = Direction.RIGHT;
    }

    public LinkedList<Point> getBody() {
        return body;
    }

    public Point getHead() {
        return body.getFirst();
    }

    public Point getTail() {
        return body.getLast();
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public void setCurrentDirection(Direction currentDirection) {
        this.currentDirection = currentDirection;
    }
}
