package tictactoe;

import java.awt.*;
import java.awt.event.MouseEvent;

public class ResetButton extends Rectangle {

    int x, y, width, height;

    Color color;
    Color drawColor;
    Color colorHighlight;

    boolean beingHoveredOn;

    Game game;

    public ResetButton(Game game) {
        this.game = game;

        width = 300;
        height = 70;
        x = (Game.WIDTH / 2) - (width / 2);
        y = 660;

        color = new Color(255, 183, 247);
        colorHighlight = new Color(252, 156, 242);
        drawColor = color;

        beingHoveredOn = false;

        setBounds(x, y, width, height);
    }

    public void render (Graphics g) {
        g.setColor(drawColor);
        g.fillRect(x, y, width, height);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        g.setColor(Color.BLACK);
        Game.drawCenteredString(g, "RESET", this, new Font(Game.FONT.getName(), Font.PLAIN, 26));
    }

    public void onMouseMove(MouseEvent e) {

        if (this.contains(e.getPoint())) {
            drawColor = colorHighlight;

            if (beingHoveredOn == false) {
                beingHoveredOn = true;
            }
        }
        else {
            drawColor = color;
            beingHoveredOn = false;
        }

    }

    public void onMouseClick(MouseEvent e) {

        if (this.contains(e.getPoint()) && game.isGameOver()) {
            AudioPlayer.playSound("click");

            game.setupGame();
        }
    }

}
