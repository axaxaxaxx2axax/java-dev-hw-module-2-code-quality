package org.example;
import java.util.Scanner;
import java.util.logging.Logger;

public class TicTacToe {

    private static final Logger logger = Logger.getLogger(TicTacToe.class.getName());
    private static final char PLAYER_X = 'X';
    private static final char PLAYER_O = 'О';
    private static final char[] board = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static byte winner = 0;
    private static final Scanner scanner = new Scanner(System.in);

    private TicTacToe() {

    }

    public static void playGame() {
        displayInstructions();

        while (winner == 0) {
            displayBoard();
            playerMove();
            checkForWinner(PLAYER_X);
            if (winner == 0) {
                computerMove();
                checkForWinner(PLAYER_O);
            }
            checkForDraw();
        }

        displayBoard();
        displayResult();
    }

    private static void displayInstructions() {
        logger.info("Enter box number to select. Enjoy!\n");
    }

    private static void displayBoard() {
        String boardString = "\n\n " + board[0] + " | " + board[1] + " | " + board[2] + " \n" +
                "-----------\n" +
                " " + board[3] + " | " + board[4] + " | " + board[5] + " \n" +
                "-----------\n" +
                " " + board[6] + " | " + board[7] + " | " + board[8] + " \n";
        logger.info(boardString);
    }

    private static void playerMove() {
        while (true) {
            logger.info("Enter your turn (1-9): ");
            byte input = scanner.nextByte();
            if (isValidMove(input)) {
                board[input - 1] = PLAYER_X;
                break;
            } else {
                logger.info("Wrong move. Try again.");
            }
        }
    }

    private static boolean isValidMove(byte input) {
        return input > 0 && input < 10 && board[input - 1] != PLAYER_X && board[input - 1] != PLAYER_O;
    }

    private static void computerMove() {
        byte move;
        do {
            move = (byte) (Math.random() * 9);
        } while (board[move] == PLAYER_X || board[move] == PLAYER_O);
        board[move] = PLAYER_O;
    }

    private static final int[][] WINNING_COMBINATIONS = {
            {0, 1, 2}, // горизонтально 1 рядок
            {3, 4, 5}, // горизонтально 2 рядок
            {6, 7, 8}, // горизонтально 3 рядок
            {0, 3, 6}, // вертикально 1 колонка
            {1, 4, 7}, // вертикально 2 колонка
            {2, 5, 8}, // вертикально 3 колонка
            {0, 4, 8}, // діагональ зліва направо
            {2, 4, 6}  // діагональ справа наліво
    };

    private static void checkForWinner(char player) {
        for (int[] combination : WINNING_COMBINATIONS) {
            if (board[combination[0]] == player &&
            board[combination[1]] == player &&
            board[combination[2]] == player) {
                winner = (byte) ((player == PLAYER_X) ? 1 : 2);
                return;
            }
        }
    }

    private static void checkForDraw() {
        boolean isDraw = true;
        for (char c : board) {
            if (c != PLAYER_X && c != PLAYER_O) {
                isDraw = false;
                break;
            }
        }
        if (isDraw) {
            winner = 3;
        }
    }

    private static void displayResult() {
        switch (winner) {
            case 1 -> logger.info("You won the game!\nCreated by Shreyas Saha. Thanks for playing!");
            case 2 -> logger.info("You lost the game!\nCreated by Shreyas Saha. Thanks for playing!");
            case 3 -> logger.info("It's a draw!\nCreated by Shreyas Saha. Thanks for playing!");
            default -> logger.warning("Unexpected result.\nCreated by Shreyas Saha. Thanks for playing!");
        }
    }
}