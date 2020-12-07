package tictactoe;

import java.awt.*;
import java.awt.event.MouseEvent;

public class Square extends Canvas {

    Color color;
    Color colorBrighter;

    public static int SIZE;
    int x, y;
    int position;

    Occupant occupant;

    boolean beingHoveredOn;

    Game game;

    public Square(int position, int x, int y) {
        color = Game.BACKGROUND_COLOR;
        colorBrighter = increaseBrightness();

        beingHoveredOn = false;

        SIZE = 150;

        this.position = position;
        this.x = x;
        this.y = y;

        occupant = Occupant.EMPTY;
        setBounds(x, y, SIZE, SIZE);

        game = Game.getGame();
    }

    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, SIZE, SIZE);

        g.setColor(Color.BLACK);

        // Draw position for debugging
        // Game.drawCenteredString(g, "pos: " + position, new Rectangle(x, y, SIZE, 20), new Font(Game.FONT.getName(), Font.PLAIN, 16));

        // Draw player choice
        Game.drawCenteredString(g, occupant.toString(), new Rectangle(x, y, SIZE, SIZE), new Font(Game.FONT.getName(), Font.PLAIN, 50));
    }

    public void onMouseMove(MouseEvent e) {

        if (occupant != Occupant.EMPTY) {
            color = Game.BACKGROUND_COLOR;
            return;
        }

        Rectangle rect = new Rectangle(getBounds());

        // If player hovers the mouse over the square
        if (rect.contains(e.getPoint()) && game.isGameOver() == false) {
            color = colorBrighter;

            if (beingHoveredOn == false) {
                beingHoveredOn = true;
                AudioPlayer.playSound("tik", 0.7f);
            }
        }

        else {
            color = Game.BACKGROUND_COLOR;
            beingHoveredOn = false;
        }

    }

    public void onMouseClick(MouseEvent e) {

        if (occupant != Occupant.EMPTY) {
            return;
        }

        if (game.isGameOver()) {
            return;
        }

        Rectangle rect = new Rectangle(getBounds());

        if (rect.contains(e.getPoint())) {
            occupant = game.getPlayerTurn();
            AudioPlayer.playSound("pling");

            game.doTurn(position);
        }
    }

    public void setOccupant(Occupant occupant) {
        this.occupant = occupant;
    }

    private Color increaseBrightness() {
        float[] hsbVals = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);

        Color highlight = Color.getHSBColor( hsbVals[0], hsbVals[1], 0.4f * ( 1f + hsbVals[2] ));

        return highlight;
    }
}
