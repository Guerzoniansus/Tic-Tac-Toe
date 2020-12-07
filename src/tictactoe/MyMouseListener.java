package tictactoe;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MyMouseListener extends MouseInputAdapter {

    Game game;

    public MyMouseListener(Game game) {
        this.game = game;
    }

    @Override
    public void mouseDragged(MouseEvent e) {

        for (Square square : game.getField().getSquares()) {
            square.onMouseMove(e);
        }

        if (game.isGameOver()) {
            game.getResetButton().onMouseMove(e);
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (SwingUtilities.isRightMouseButton(e)) {
            AudioPlayer.toggleMusicMute();
            return;
        }

        else if (SwingUtilities.isMiddleMouseButton(e)) {
            AudioPlayer.toggleTikMute();
            return;
        }

        for (Square square : game.getField().getSquares()) {
            square.onMouseClick(e);
        }

        if (game.isGameOver()) {
            game.getResetButton().onMouseClick(e);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

        for (Square square : game.getField().getSquares()) {
            square.onMouseMove(e);
        }

        if (game.isGameOver()) {
            game.getResetButton().onMouseMove(e);
        }

    }
}
