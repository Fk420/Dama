package com.polishdroughts;

import java.util.Scanner;

public class Game {

    private Board board;
    private boolean gameIsRunning;

    public Game() {
        System.out.println("POLISH DRAUGHTS");
    }

    public void start() {
        int player = 1;
        waitforEnterKey();
        this.board = new Board();
        clearScreen();
        this.gameIsRunning = true;
        while (gameIsRunning) {
            playRound();
            board.movePawn();
            player = player == 1 ? 2 : 1;
        }
    }

    public void waitforEnterKey() {
        System.out.println("Hit \"ENTER\" to Start");
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        clearScreen();
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void playRound() {
        clearScreen();
        System.out.println("White moves first...");
        Board.printBoard(board.getBoard());
    }

    public static boolean adjacentPawnCheck(Pawn[][] board, int fromX, int fromY) {
        return board[fromX + 1][fromY + 1] != null || board[fromX + 1][fromY - 1] != null ||
                board[fromX - 1][fromY + 1] != null || board[fromX - 1][fromY - 1] != null;
    }

    public static int[] getEnemyCoordinates(int fromX, int fromY, int toX, int toY) {
        if (((toX == (fromX + 2) && toY == (fromY + 2))) ||
                ((fromX == (toX - 2) && fromY == (toY - 2)))) {
            return new int[]{fromX + 1, fromY + 1};
        } else if (((toX == (fromX + 2) && toY == (fromY - 2))) ||
                (fromX == (toX - 2) && fromY == (toY + 2))) {
            return new int[]{fromX + 1, fromY - 1};
        } else if (((toX == (fromX - 2) && toY == (fromY - 2))) ||
                ((fromX == (toX + 2) && fromY == (toY + 2)))) {
            return new int[]{fromX - 1, fromY - 1};
        } else if (((toX == (fromX - 2) && toY == (fromY + 2))) ||
                ((fromX == (toX + 2) && fromY == (toY - 2)))) {
            return new int[]{fromX - 1, fromY + 1};
        }
        return null;
    }


    public static boolean canHit(Pawn[][] board, int fromX, int fromY, int toX, int toY) {
        int[] enemyCoordinates = getEnemyCoordinates(fromX, fromY, toX, toY);

        if (enemyCoordinates == null) {
            return false;
        }
        else {
            return !board[enemyCoordinates[0]][enemyCoordinates[1]].toString().equals(board[fromX][fromY].toString());
        }
    }

    public static void hitEnemy(Pawn[][] board, int fromX, int fromY, int toX, int toY) {

        int[] enemyCoordinates = getEnemyCoordinates(fromX, fromY, toX, toY);

        if (adjacentPawnCheck(board, fromX, fromY)) {
            Board.removePawn(board, enemyCoordinates[0], enemyCoordinates[1]);
        }
    }

    public static int[] getPawn() {
        Scanner scanner;
        System.out.println("Enter the coordinates of the pawn!");
        scanner = new Scanner(System.in);

        int XCoordinate = -1;
        int YCoordinate = -1;

        String coordinates = scanner.nextLine();

        XCoordinate = (int) coordinates.toLowerCase().charAt(0) - 97;
        YCoordinate = coordinates.charAt(1) - 49;

        return new int[]{XCoordinate, YCoordinate};
    }

    public static int[] getMove() {
        Scanner scanner;
        System.out.println("Enter the destination coordinates!");
        scanner = new Scanner(System.in);

        int XCoordinate = -1;
        int YCoordinate = -1;

        String coordinates = scanner.nextLine();

        XCoordinate = (int) coordinates.toLowerCase().charAt(0) - 97;
        YCoordinate = coordinates.charAt(1) - 49;

        return new int[]{XCoordinate, YCoordinate};
    }

    public static boolean validateMove(Pawn[][] board, String color, int fromX, int fromY, int toX, int toY) {
        if (board[fromX][fromY] == null) {
            return false;
        }
        //if (board[fromX][fromY].getColor() != color){
          //  return false;
      //  }
        if (board[toX][toY] != null){
            return false;
        }
        if ((toX-fromX != 1) && (toX-fromX != -1)){
            return false;
        }
        if (color.equals("white")&& (fromY-toY == 1)){
            return false;
        }
        else if (fromY-toY == -1){
            return false;
        }
        return true;
    }
}