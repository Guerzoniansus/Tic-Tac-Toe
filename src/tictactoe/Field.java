package tictactoe;

import java.awt.*;

public class Field {

    /* Array with occupants, the number in the array + 1 is the position on the field
    / (For example, if (fieldArray[0] == Occupant.PLAYER1), it means top left has player 1's mark
    / By default, it's filled with Occupant.EMPTY for every position
    */

    private Occupant[] fieldArray;

    private Square[] squares;

    private Game game;

    int startingXY;

    public Field(Game game) {

        this.game = game;

        fieldArray = new Occupant[9];
        squares = new Square[9];

        // 175 pixels away from the top and side of the screen so everything is perfectly centered
        // Basically (Game.WIDTH - (Square.SIZE * 3)) / 2; but FOR SOME REASON THIS DOESN'T WORK SO I JUST USE A HARD CODED NUMBER

        startingXY = 175;

        for (int i = 0; i < 9; i++) {
            insert(i + 1, Occupant.EMPTY);
        }

        /*
        startingXY = (Game.WIDTH - (Square.SIZE * 3) / 2);

        So with the window being 800 pixels in size, and the squares being 150 pixel rectangulars, that makes it:
        (800 - (150*3)) / 2 = 175. So 175 pixels at the bottom, left, right and top inbetween the edges of the window, and the field, so everything is perfectly centered

        It uses a for loop to create 3 squares next to each other, using i as the X coord (and adding square size to it everytime to perfectly put them next to each other)
        It does this 3 times for each 3 rows
        The Y coord is startingXY, startingXY + size, startingXY + size*2
         */

        int squareNumber = 0;

        for (int i = startingXY; i < (startingXY + (150*2)) + 1; i = i + Square.SIZE) {
            squares[squareNumber] = new Square(squareNumber + 1, i, startingXY);
            squareNumber++;
        }

        for (int i = startingXY; i < (startingXY + (150*2)) + 1; i = i + Square.SIZE) {
            squares[squareNumber] = new Square(squareNumber + 1, i, startingXY + Square.SIZE);
            squareNumber++;
        }

        for (int i = startingXY; i < (startingXY + (150*2)) + 1; i = i + Square.SIZE) {
            squares[squareNumber] = new Square(squareNumber + 1, i, startingXY + (Square.SIZE * 2));
            squareNumber++;
        }

    }

    public void drawField(Graphics g) {

        // Draw squares
        for (Square square : squares) {
            if (square != null) {
                square.render(g);
            }
        }

        // Draw grid with 6 width
        drawGrid(g);

        // Player turn
        drawTitle(g);
    }

    private void drawGrid(Graphics g) {
        int lineWidth = 6;
        g.setColor(Color.BLACK);

        // Vertical stripes
        g.fillRect(startingXY + Square.SIZE - lineWidth, startingXY, lineWidth, Square.SIZE * 3);
        g.fillRect(startingXY + Square.SIZE - lineWidth + Square.SIZE, startingXY, lineWidth, Square.SIZE * 3);
        // Horizontal stripes
        g.fillRect(startingXY, startingXY + Square.SIZE - lineWidth, Square.SIZE * 3, lineWidth);
        g.fillRect(startingXY, startingXY + Square.SIZE - lineWidth + Square.SIZE, Square.SIZE * 3, lineWidth);
    }

    private void drawTitle(Graphics g) {
        Font font = new Font(Game.FONT.getName(), Font.PLAIN, 36);
        Rectangle titleRect = new Rectangle(0, 0, Game.WIDTH, 150);

        g.setColor(Color.BLACK);

        if (game.isGameOver()) {
            String winner = "error";

            if (game.checkFieldForWinner() == Occupant.PLAYER1) {
                winner = "PLAYER 1";
            }

            else if (game.checkFieldForWinner() == Occupant.PLAYER2) {
                winner = "PLAYER 2";
            }

            if (game.checkFieldForWinner() == Occupant.DRAW) {
                Game.drawCenteredString(g, "GAME OVER! NO ONE WON ):", titleRect, font);
            }

            else {
                Game.drawCenteredString(g, "GAME OVER! " + winner + " WON!", titleRect, font);
            }
        }

        else if (game.getPlayerTurn() == Occupant.PLAYER1){
            Game.drawCenteredString(g, "PLAYER 1'S TURN", titleRect, font);
        }

        else if (game.getPlayerTurn() == Occupant.PLAYER2) {
            Game.drawCenteredString(g, "PLAYER 2'S TURN", titleRect, font);
        }

    }

    public boolean isFull() {
        for (Occupant occupant : fieldArray) {
            if (occupant == Occupant.EMPTY) return false;
        }

        return true;
    }

    public Square[] getSquares() {
        return squares;
    }

    // insert  occupant at location 1-9

    public void insert(int location, Occupant occupant) {
        fieldArray[location - 1] = occupant;
    }

    // get occupant at location 1-9

    public Occupant getOccupant(int location) {
        return fieldArray[location - 1];
    }

}
