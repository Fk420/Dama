package com.polishdroughts;

import java.util.*;

public class Board {
    private Pawn[][] board;
    private Scanner scanner;
    private int n;
    private Game game;


    Board() {
        // specify board size
        scanner = new Scanner(System.in);
        System.out.println("Type in the board size (10-20): ");
        String input = scanner.nextLine();
        n = Integer.parseInt(input); //parse input

        // validate input between values
        while (n < 10 || n > 20) {
            System.out.println("Wrong value, try between 10 & 20: ");
            input = scanner.nextLine();
            n = Integer.parseInt(input);
        }

        // set pawns on board
        board = setPawns(new Pawn[n][n], n);
    }



    private Pawn[][] setPawns(Pawn[][] board, int n) {
        // fehér  bábuk
        int whitePieces = n * 2;
        for (int row = board.length - 1; row >= 0; row--) {
            if ((row - 1) % 2 == 0) {
                for (int i = 0; i < board[row].length; i += 2) {
                    if (whitePieces > 0) {
                        board[row][i] = new Pawn("white", row, i);
                        whitePieces--;
                    }
                }
            } else {
                for (int i = 1; i < board[row].length; i += 2) {
                    if (whitePieces > 0) {
                        board[row][i] = new Pawn("white", row, i);
                        whitePieces--;
                    }
                }
            }
        }

        // fekete bábuk
        int blackPieces = n * 2;
        for (int row = 0; row < board.length; row++) {
            if ((row + 1) % 2 == 0) {
                for (int i = 0; i < board[row].length; i += 2) {
                    if (blackPieces > 0) {
                        board[row][i] = new Pawn("black", row, i);
                        blackPieces--;
                    }
                }
            } else {
                for (int i = 1; i < board[row].length; i += 2) {
                    if (blackPieces > 0) {
                        board[row][i] = new Pawn("black", row, i);
                        blackPieces--;
                    }
                }
            }
        }
        return board;
    }

    public Pawn[][] getBoard() {
        return board;
    }

    public static void printBoard(Pawn[][] board) {

        Pawn[][] tmpboard = board;
        // a bábu
        String puck = "¤";

        char[] abc = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        // ebbe tárolja a kirajzolandó táblát
        StringBuilder displayBoard = new StringBuilder("    ");

        // első sorban az olszopot jelölő számok közötti space, 2 ha 1 jegyű a szám
        for (int row = 0; row < tmpboard.length; row++)
        {
            if (row < 9) {
                displayBoard.append(row + 1).append("  ");
            } else {
                displayBoard.append(row + 1).append(" ");
            }
        }



        displayBoard.append("\n");
        // betűk a sor elején + tábla kirajzolás
        for (int row = 0; row < tmpboard.length; row++)
        {
            // sor betűje + 2 space
            displayBoard.append(abc[row]).append("  ");

            // sor kirajzolás
            for (int col = 0; col < tmpboard[row].length; col++) {

                // ha nincs bábu a mezőn
                if (tmpboard[row][col] == null)
                {

                    // ha páros a mező
                    if ((row + col) % 2 == 0)
                    {
                        // cián színű háttér kerül oda
                        displayBoard.append("\u001b[46;1m" + "   " + "\u001b[0m");
                    }
                    else
                    {
                        // amúgy üres mező kerül
                        displayBoard.append("   ");
                    }
                    // ha van bábu a mezőn, kirajzoljuk pöttyként
                } else {

                    // megnézi hogy a tmpboard adott mezőjén lévő bábunak a toString() metódusa milyen értéket ad vissza
                    switch (tmpboard[row][col].getColor()) {
                        // ha "black" a háttér color tulajdonsága, fekete bábut rajzol
                        case "black":
                            // \u001b[31m - piros karakter ANSI kódja
                            // \u001b[0m - reset style, következő bábu v mező ne legyen színes
                            displayBoard.append("\u001b[31m" + " ").append(puck).append(" ").append("\u001b[0m");
                            break;
                        // ha fehéret ad vissza a toString() fehér bábut rajzol
                        case "white":
                            displayBoard.append(" ").append(puck).append(" ");
                            break;
                    }
                }
            }
            // tábla után egy üres sor
            displayBoard.append("\n");
        }
        System.out.println(displayBoard);

    }

    public static void removePawn(Pawn[][] board, int x, int y) {
        board[x][y] = null;
    }

    public void movePawn() {

        int[] selectedPawn = Game.getPawn();
        int fromY = selectedPawn[0];
        int fromX = selectedPawn[1];

        int[] selectedField = Game.getMove();
        int toY = selectedField[0];
        int toX = selectedField[1];

        String pawnColor = board[fromY][fromX].toString();
        if (Game.validateMove(board, pawnColor, fromX, fromY, toX, toY)){
            System.out.println("Valid move!");
        }
        else {
            System.out.println("INVALID MOVE!");
        }

        if ((pawnColor.equals("black") && (toX == (fromX + 1) && toY == (fromY + 1))) ||
                (pawnColor.equals("black") && (toX == (fromX + 1) && toY == (fromY - 1)))) {
            if (Game.canHit(board, fromX, fromY, toX, toY)) {
                Game.hitEnemy(board, fromX, fromY, toX, toY);
            }
            board[toX][toY] = board[fromX][fromY];
            removePawn(board, fromX, fromY);
        }
        else if ((pawnColor.equals("white") && (toX == (fromX - 1) && toY == (fromY + 1))) ||
                (pawnColor.equals("white") && (toX == (fromX -1) && toY == (fromY - 1)))) {
            if (Game.canHit(board, fromX, fromY, toX, toY)) {
                Game.hitEnemy(board, fromX, fromY, toX, toY);
            }
            board[toX][toY] = board[fromX][fromY];
            removePawn(board, fromX, fromY);
        }
    }
}