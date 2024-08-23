package grid;

import snake.Direction;
import snake.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Random;

public class Grid extends JFrame {
    private Cell[][] gridCells;
    private Snake snake;
    private final CustomPanel panel;
    Timer gameTimer;

    Dimension cellDim;
    int cells;

    public Grid(int dim, int cells) {
        this.cells = cells;
        cellDim = new Dimension(dim / cells, dim / cells);
        Dimension gridDim = new Dimension(dim, dim);

        init(cellDim, cells);
        panel = new CustomPanel(this);
        panel.setPreferredSize(gridDim);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP, KeyEvent.VK_W:
                        if (snake.getCurrentDirection() != Direction.DOWN) {
                            snake.setCurrentDirection(Direction.UP);
                        }
                        break;
                    case KeyEvent.VK_DOWN, KeyEvent.VK_S:
                        if (snake.getCurrentDirection() != Direction.UP) {
                            snake.setCurrentDirection(Direction.DOWN);
                        }
                        break;
                    case KeyEvent.VK_LEFT, KeyEvent.VK_A:
                        if (snake.getCurrentDirection() != Direction.RIGHT) {
                            snake.setCurrentDirection(Direction.LEFT);
                        }
                        break;
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_D:
                        if (snake.getCurrentDirection() != Direction.LEFT) {
                            snake.setCurrentDirection(Direction.RIGHT);
                        }
                        break;
                }
            }
        });

        setSize(gridDim);
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        pack();
    }

    private void init(Dimension cellDim, int cells) {
        this.gridCells = new Cell[cells][cells];

        int y = 0;
        for (int r = 0; r < cells; r++) {
            int x = 0;
            for (int c = 0; c < cells; c++) {
                gridCells[r][c] = new Cell(new Point(x, y), cellDim);
                x += cellDim.width;
            }
            y += cellDim.width;
        }

        int gridMid = gridCells.length / 2;
        Point startPos = new Point(gridMid, gridMid);
        snake = new Snake(startPos);
        gridCells[gridMid][gridMid].setSnake(true);

        gameTimer = new Timer(250, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    move();
                    panel.repaint();
                } catch (ArrayIndexOutOfBoundsException error) {
                    stopGameLoop();
                }

            }
        });

        spawnApple();
    }

    public void draw(Graphics g) {
        for (Cell[] row : gridCells) {
            for (Cell cell : row) {
                if (cell != null) {
                    g.setColor(Color.BLACK);
                    if (cell.isSnake()) {
                        g.fillRect(cell.getSquare().x, cell.getSquare().y, cell.getSquare().width, cell.getSquare().height);
                    } else if (cell.isApple()) {
                        g.setColor(Color.red);
                        g.fillRect(cell.getSquare().x, cell.getSquare().y, cell.getSquare().width, cell.getSquare().height);
                    } else {
                        g.drawRect(cell.getSquare().x, cell.getSquare().y, cell.getSquare().width, cell.getSquare().height);
                    }
                }
            }
        }
    }

    private void move() {
        Direction direction = snake.getCurrentDirection();
        LinkedList<Point> body = snake.getBody();

        Point newHead = new Point(body.getFirst());

        switch (direction) {
            case UP:
                newHead.x--;
                break;
            case DOWN:
                newHead.x++;
                break;
            case LEFT:
                newHead.y--;
                break;
            case RIGHT:
                newHead.y++;
                break;
        }

        checkCollision(newHead);

        body.addFirst(newHead);
        gridCells[newHead.x][newHead.y].setSnake(true);

        Point last = body.removeLast();
        gridCells[last.x][last.y].setSnake(false);

        if (gridCells[body.getFirst().x][body.getFirst().y].isApple()) {
            gridCells[newHead.x][newHead.y].setApple(false);
            body.addLast(new Point(last));
            gridCells[last.x][last.y].setSnake(true);

            spawnApple();
        }

    }


    private void spawnApple() {
        Random rand = new Random();
        Point p = new Point(rand.nextInt(gridCells.length), rand.nextInt(gridCells.length));

        while (gridCells[p.x][p.y].isSnake()) {
            p = new Point(rand.nextInt(gridCells.length), rand.nextInt(gridCells.length));
        }

        gridCells[p.x][p.y].setApple(true);
    }

    public void startGameLoop() {
        gameTimer.setRepeats(true);
        gameTimer.start();

    }

    public void stopGameLoop() {
        gameTimer.stop();
        int input = JOptionPane.showConfirmDialog(this, "Du hast verloren! Neustart?");

        switch (input) {
            case JOptionPane.YES_OPTION:
                restartGameLoop();
                break;
            case JOptionPane.NO_OPTION:
                System.exit(0);
                break;

        }
    }

    public void restartGameLoop() {
        init(cellDim, cells);
        gameTimer.start();
    }

    private void checkCollision(Point newHead) {

        if (gridCells[newHead.x][newHead.y].isSnake() || (newHead.x > 9 || newHead.x < 0 || newHead.y > 9 || newHead.y < 0)) {
            stopGameLoop();
        }
    }

}
