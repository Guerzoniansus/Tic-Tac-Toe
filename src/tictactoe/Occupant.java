package tictactoe;

public enum Occupant {

    PLAYER1, PLAYER2, EMPTY, DRAW;

    public String toString() {
        if (this == Occupant.EMPTY) {
            return " ";
        }

        else if (this == Occupant.PLAYER1) {
            return "X";
        }

        else if (this == Occupant.PLAYER2) {
            return "O";
        }

        return " ";
    }

}
