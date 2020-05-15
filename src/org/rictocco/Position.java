package org.rictocco;

public class Position {
    private Player player;
    private int currentPosition;

    public Position(Player player) {
        this.player = player;
        this.currentPosition = 0;
    }

    public Player getPlayer() {
        return player;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public void setCurrentPosition(int currentPosition) {
        this.currentPosition = currentPosition;
    }
}