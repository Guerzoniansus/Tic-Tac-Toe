package tictactoe;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Game extends Canvas {

    public static int WIDTH = 800;
    public static int HEIGHT = 800;

    public static Color BACKGROUND_COLOR = Color.pink;

    public static Occupant playerTurn;

    public static Font FONT;

    Window window;
    static Game game;
    Field field;

    int player1wins, player2wins;

    boolean gameOver;
    ArrayList<String> options;
    ResetButton resetButton;

    public static void main(String[] args) {

        Game game = new Game();
        game.setGame(game);
        game.init();
        game.setupGame();

        while (true) {
            game.gameLoop();
        }

    }

    public void gameLoop() {

        // Main game loop

        render();
    }

    public void init() {
        FONT = new Font("Arial", Font.PLAIN, 16);

        AudioPlayer.load();

        MyMouseListener mouseListener = new MyMouseListener(game);
        addMouseListener(mouseListener);
        addMouseMotionListener(mouseListener);

        // This is needed to see if you got three in a row
        options = new ArrayList<String>();

        options.add("1+2+3");
        options.add("4+5+6");
        options.add("7+8+9");
        options.add("1+4+7");
        options.add("2+5+8");
        options.add("3+6+9");
        options.add("1+5+9");
        options.add("3+5+7");

        player1wins = 0;
        player2wins = 0;

        window = new Window(WIDTH, HEIGHT, "Tic Tac Toe", game);
    }

    // Used to reset the game too
    public void setupGame() {
        gameOver = false;
        field = new Field(game);
        setField(field);
        playerTurn = Occupant.PLAYER1;
        resetButton = null;

        if (!AudioPlayer.isMusicMuted) {
            AudioPlayer.resumeMusic();
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();

        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = ((BufferStrategy) bs).getDrawGraphics();

        // This will anti-alias text
        Graphics2D g2 = (Graphics2D) g;
        RenderingHints rh = new RenderingHints(RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);

        // Start drawing stuff

        // Background color
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Controls to mute music and tiks
        g.setColor(Color.BLACK);
        g.setFont(new Font(FONT.getName(), Font.PLAIN, 12));
        //g.drawString("Mute tik sounds: middle mouse button", 10, 20);
        //g.drawString("Mute music: right mouse button", 10, 35);

        field.drawField(g);

        // Draw player wins
        g.setColor(Color.BLACK);
        g.setFont(new Font(FONT.getName(), Font.PLAIN, 24));
        g.drawString("Player 1: " + player1wins + " wins", 10, 720);
        g.drawString("Player 2: " + player2wins + " wins", 10, 755);

        if (isGameOver()) {
            resetButton.render(g);
        }

        g.dispose();
        bs.show();
    }

    public void setField(Field field) {
        this.field = field;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public ResetButton getResetButton() {
        return resetButton;
    }

    public Window getWindow() {
        return window;
    }

    public Field getField() {
        return field;
    }

    public Occupant getPlayerTurn() {
        return playerTurn;
    }

    public static Game getGame() { return game; }

    public ArrayList<String> getOptions() {
        return options;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void doTurn(int position) {

        int playerChoice = position;

        field.insert(playerChoice, playerTurn);

        if (playerTurn == Occupant.PLAYER1)
            playerTurn = Occupant.PLAYER2;

        else if (playerTurn == Occupant.PLAYER2)
            playerTurn = Occupant.PLAYER1;

        // Check if someone won

        if (checkFieldForWinner() == Occupant.PLAYER1 || checkFieldForWinner() == Occupant.PLAYER2
        || checkFieldForWinner() == Occupant.DRAW) {
            System.out.println("Game over!");

            if (checkFieldForWinner() == Occupant.PLAYER1) {
                System.out.println("Player 1 won!");
                player1wins++;
            }

            else if (checkFieldForWinner() == Occupant.PLAYER2) {
                System.out.println("Player 2 won!");
                player2wins++;
            }

            gameOver = true;
            resetButton = new ResetButton(this);
            AudioPlayer.playSound("game_over");
            AudioPlayer.pauseMusic();
        }

    }

    public Occupant checkFieldForWinner() {
        // Return player / player2 if they win, return EMPTY if no one has won yet

        /* Loops through every possiblity, then increases playerCount / player2Count for everytime
         a player or player2 is in one of the three spots of a possibility

         For example, one possibility is "4+5+6". It checks if a player has their thing at spot 4, 5 and 6 at the same time.
         It does this for all possibilities in for (String option : getOptions())
         */

        for (String option : getOptions()) {
            int playerCount = 0;
            int player2Count = 0;

            String[] spots = option.split("\\+");

            // Loops through one of the 3 spots of one possibility
            for (String spot : spots) {
                int location = Integer.parseInt(spot);

                if (field.getOccupant(location) == Occupant.PLAYER1)
                    playerCount++;

                if (field.getOccupant(location) == Occupant.PLAYER2)
                    player2Count++;
            }

            if (playerCount == 3)
                return Occupant.PLAYER1;

            if (player2Count == 3)
                return Occupant.PLAYER2;

        }

        if (field.isFull()) return Occupant.DRAW;

        return Occupant.EMPTY;
    }

    // Shamelessly copy pasted from stack overflow

    public static void drawCenteredString(Graphics g, String text, Rectangle rect, Font font) {
        // Get the FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // Determine the X coordinate for the text
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        // Determine the Y coordinate for the text (note we add the ascent, as in java 2d 0 is top of the screen)
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        // Set the font
        g.setFont(font);
        // Draw the String
        g.drawString(text, x, y);
    }

}
