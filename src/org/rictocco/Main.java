package org.rictocco;

public class Main {
    public static void main(String[] args) {
        GameConsole console = new GameConsole(new GamePlay());
        console.startGame();
    }
}
