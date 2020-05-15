package org.rictocco;

import java.util.Scanner;

public class GameConsole {

    private GamePlay gameplay;
    public static boolean isThereAWinner;


    public GameConsole(GamePlay gameplay) {
        this.gameplay = gameplay;
        isThereAWinner = false;
    }

    public static void setIsThereAWinner(boolean winner) {
        isThereAWinner = winner;
    }

    public void startGame() {
        Scanner in = new Scanner(System.in);
        String[] gameInstruction;

        System.out.println("Welcome to the Goose Game!");
        printInstructions();
        while (!isThereAWinner) {
            gameInstruction = in.nextLine().split("\\s+");
            instructionHandler(gameInstruction);
        }
    }

    private void instructionHandler(String[] gameInstruction) {
        if (gameInstruction[0].toLowerCase().equals("add") && gameInstruction.length == 3) {
            System.out.println(gameplay.addPlayer(gameInstruction[2]));
        }
        else if (gameInstruction[0].toLowerCase().equals("move") && gameInstruction.length == 2) {
            System.out.println(gameplay.movePlayer(gameInstruction[1]));
        }
        else if (gameInstruction[0].toLowerCase().equals("move") && gameInstruction.length == 4) {

            System.out.println(gameplay.movePlayer(gameInstruction[1], Integer.parseInt(gameInstruction[2].substring(0,1)), Integer.parseInt(gameInstruction[3])));
        }
        else {
            System.out.println("Invalid option");
        }
    }

    private void printInstructions() {
        System.out.println("To add a player type: \"add player Pippo\".");
        System.out.println("To move a player type \"move Pippo\"");
        System.out.println("To move a player specifying the dice: move Pippo 4, 2\". \n");
    }

}
