package grid;

import java.awt.*;

public class Cell {
    private Rectangle square;
    private boolean snake;
    private boolean apple;

    public Cell(Point pos, Dimension dim) {
        square = new Rectangle(pos, dim);
        snake = false;
        apple = false;
    }

    public Rectangle getSquare() {
        return square;
    }

    public void setSquare(Rectangle square) {
        this.square = square;
    }

    public boolean isSnake() {
        return snake;
    }

    public void setSnake(boolean snake) {
        this.snake = snake;
    }

    public boolean isApple() {
        return apple;
    }

    public void setApple(boolean apple) {
        this.apple = apple;
    }
}
