package org.rictocco;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

public class GamePlay {
    List<Player> players;
    BoardGame boardGame;
    Map<String, Position> positions;

    public GamePlay() {
        this.players = new ArrayList<>();
        this.boardGame = new BoardGame();
        this.positions = new HashMap<>();
    }

    public String addPlayer(String name) {
        Player player = new Player(name);
        if (!checkPlayerExist(player)) {
            players.add(player);
            positions.put(player.getName(), new Position(player));
            return String.format("players: %s", listPlayers());
        }
        return String.format("%s: already existing player", name);
    }

    private boolean checkPlayerExist(Player player) {
        return players.contains(player);
    }

    public String movePlayer(String playerName) {
        int diceOne = rollDice();
        int diceTwo = rollDice();
        return movePlayer(playerName, diceOne, diceTwo);
    }

    public String movePlayer(String playerName, int diceOne, int diceTwo){
        Player player = findPlayer(playerName);
        int moveTo;
        if (player == null) {
            return String.format("%s: player does not exist. Players: %s", playerName, listPlayers());
        }

        Position movement = positions.get(playerName);
        moveTo = movement.getCurrentPosition() + diceOne + diceTwo;

        StringBuilder message = new StringBuilder(String.format("%s rolls %s, %s", playerName, diceOne, diceTwo));

        switch (checkSpace(moveTo)) {
            case "victory":
                message.append(String.format(". %s moves from %s to %s. %s Wins!!",
                        playerName, movement.getCurrentPosition()==0?"Start":movement.getCurrentPosition(), moveTo, playerName));
                GameConsole.setIsThereAWinner(true);
                break;
            case "bridge":
                message.append(String.format(". %s moves from %s to The Bridge. %s jumps to %s",
                        playerName, movement.getCurrentPosition()==0?"Start":movement.getCurrentPosition(), playerName, boardGame.getBridgeJump(moveTo)));
                moveTo = boardGame.getBridgeJump(moveTo);
                break;
            case "goose":
                message.append(String.format(". %s moves from %s to %s",
                        playerName, movement.getCurrentPosition()==0?"Start":movement.getCurrentPosition(), moveTo));
                while(checkSpace(moveTo).equals("goose")) {
                    moveTo += diceOne + diceTwo;
                    message.append(String.format(", The Goose. %s moves again and goes to %s",
                            playerName, moveTo));
                }
                break;
            case "empty":
                if (moveTo > boardGame.getBoardSize()) {
                    moveTo = 2 * boardGame.getBoardSize() - moveTo;
                    message.append(String.format(". %s moves from %s to %s. %s bounces! %s returns to %s",
                            playerName, movement.getCurrentPosition()==0?"Start":movement.getCurrentPosition(), boardGame.getBoardSize(),
                            playerName, playerName, moveTo));
                } else {
                    message.append(String.format(". %s moves from %s to %s",
                            playerName, movement.getCurrentPosition() == 0 ? "Start" : movement.getCurrentPosition(), moveTo));
                }
                break;
        }

        Position prank = checkOtherPlayersInSpace(moveTo);
        if (prank != null) {
            prank.setCurrentPosition(movement.getCurrentPosition());
            message.append(String.format(". On %s there is %s, who returns to %S", moveTo,
                    prank.getPlayer().getName(), prank.getCurrentPosition()==0?"Start":prank.getCurrentPosition()));
        }
        movement.setCurrentPosition(moveTo);
        return message.toString();
    }

    private String checkSpace(int space) {
        if (boardGame.landedOnBridge(space))
            return "bridge";
        else if (boardGame.landedOnGoose(space))
            return "goose";
        if (boardGame.victory(space))
            return "victory";
        return "empty";
    }

    public Position checkOtherPlayersInSpace(int position) {
        Optional<Position> optionalMovement = positions.values().stream().filter(movement -> movement.getCurrentPosition() == position).findFirst();
        return optionalMovement.orElse(null);
    }

    public int rollDice() {
        Random diceRoller = new Random();
        return  diceRoller.nextInt(6) + 1;
    }

    private Player findPlayer(String name) {
        Optional<Player> playerOptional = players.stream().filter(player -> player.getName().equals(name)).findFirst();
        return playerOptional.orElse(null);
    }

    private String listPlayers() {
        return positions.keySet().parallelStream().collect(Collectors.joining(", "));
    }
}
