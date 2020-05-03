package tictactoe;

import java.util.Scanner;

public class Main {
    private static char[][] board = new char[3][3];
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        printState();
        String state = "";
        while (true) {
            enterCoordinates('X');
            state = analyzeState();
            if (!state.equals("Game not finished")) {
                break;
            }
            enterCoordinates('O');
            if (!state.equals("Game not finished")) {
                break;
            }
        }
        System.out.println(state);
    }

    private static void printState() {
        System.out.println("---------");
        for(char[] row : board) {
            System.out.print("| ");
            for(char value : row) {
                System.out.print(value + " ");
            }
            System.out.println("|");
        }
        System.out.println("---------");
    }

    private static void enterCoordinates(char turn) {
        System.out.print("Enter the coordinates: ");
        String str = scanner.nextLine();
        if (!isNumeric(str)) {
            System.out.println("You should enter numbers!");
            enterCoordinates(turn);
            return;
        }
        String numbers[] = str.split(" ");
        int x = Integer.parseInt(numbers[0]), y = Integer.parseInt(numbers[1]);
        if (!isInRange(x, y)) {
            System.out.println("Coordinates should be from 1 to 3!");
            enterCoordinates(turn);
            return;
        }
        int[] transform = transformCoordinates(x, y);
        x = transform[0];
        y = transform[1];
        if (isOccupied(x, y)) {
            System.out.println("This cell is occupied! Choose another one!");
            enterCoordinates(turn);
            return;
        }
        board[x][y] = turn;
        printState();
    }


    private static boolean isNumeric(String str) {
        return str.matches("\\d \\d");
    }

    private static boolean isInRange(int x, int y) {
        return x >= 1 && x <= 3 && y >= 1 && y <= 3;
    }

    private static boolean isOccupied(int x, int y) {
        return board[x][y] != ' ';
    }

    private static int[] transformCoordinates(int x, int y) {
        if (x == 1 && y == 1)
            return new int[] {2, 0};
        else if (x == 1 && y == 2)
            return new int[] {1, 0};
        else if (x == 1 && y == 3)
            return new int[] {0, 0};
        else if (x == 2 && y == 1)
            return new int[] {2, 1};
        else if (x == 2 && y == 2)
            return new int[] {1, 1};
        else if (x == 2 && y == 3)
            return new int[] {0, 1};
        else if (x == 3 && y == 1)
            return new int[] {2, 2};
        else if (x == 3 && y == 2)
            return new int[] {1, 2};
        else
            return new int[] {0, 2};
    }

    private static String checkRow(int row) {
        if (board[row][0] == 'X' && board[row][1] == 'X' && board[row][2] == 'X') {
            return "X wins";
        } else if (board[row][0] == 'O' && board[row][1] == 'O' && board[row][2] == 'O') {
            return "O wins";
        } else {
            return "Fail";
        }
    }

    private static String checkCol(int col) {
        if (board[0][col] == 'X' && board[1][col] == 'X' && board[2][col] == 'X') {
            return "X wins";
        } else if (board[0][col] == 'O' && board[1][col] == 'O' && board[2][col] == 'O') {
            return "O wins";
        } else {
            return "Fail";
        }
    }

    private static String checkDiagonal() {
        if (board[0][0] == 'X' && board[1][1] == 'X' && board[2][2] == 'X' ||
        board[0][2] == 'X' && board[1][1] == 'X' && board[2][0] == 'X') {
            return "X wins";
        } else if (board[0][0] == 'O' && board[1][1] == 'O' && board[2][2] == 'O' ||
                board[0][2] == 'O' && board[1][1] == 'O' && board[2][0] == 'O') {
            return "O wins";
        } else {
            return "Fail";
        }
    }

    private static int countChar(char chr) {
        int count = 0;
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (board[i][j] == chr)
                    ++count;
            }
        }
        return count;
    }

    private static String analyzeState() {
        String result = "", victoryX = "", victoryO = "";
        //Check Rows, Columns, and Diagonals for X or O's victory
        for(int i = 0; i < 3; i++) {
            if (checkRow(i).equals("X wins") || checkCol(i).equals("X wins"))
                victoryX = "X wins";
            else if (checkRow(i).equals("O wins") || checkCol(i).equals("O wins"))
                victoryO = "O wins";
        }
        if (checkDiagonal().equals("X wins"))
            victoryX = "X wins";
        else if (checkDiagonal().equals("O wins"))
            victoryO = "O wins";

        if (victoryO.isEmpty() && victoryX.isEmpty()){
            if (countChar('X') + countChar('O') < 9)
                result =  "Game not finished";
            else
                result = "Draw";
        } else {
            result = victoryO.isEmpty() ? victoryX : victoryO;
        }
        return result;
    }
}
