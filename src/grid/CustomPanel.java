package grid;

import javax.swing.*;
import java.awt.*;

class CustomPanel extends JPanel {
    Grid grid;

    CustomPanel(Grid grid) {
        this.grid = grid;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        grid.draw(g);
    }
}
