package org.rictocco;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GamePlayTest {
    GamePlay gameplay;
    Player player;

    @BeforeEach
    void setUp() {
        gameplay = new GamePlay();
        player = new Player("Pippo");
    }

    @Test
    void addNonExistingPlayer() {
        assertFalse(gameplay.players.contains(player));

        gameplay.addPlayer("Pippo");
        assertTrue(gameplay.players.contains(player));
        assertEquals(1, gameplay.players.size());
    }

    @Test
    void addExistingPlayer() {
        gameplay.addPlayer("Pippo");
        assertTrue(gameplay.players.contains(player));
        assertEquals(1, gameplay.players.size());

        String result = gameplay.addPlayer("Pippo");
        assertTrue(gameplay.players.contains(player));
        assertEquals(1, gameplay.players.size());
        assertEquals("Pippo: already existing player", result);
    }

    @Test
    void movePlayer() {
        gameplay.addPlayer("Pippo");
        Position movement = gameplay.positions.get("Pippo");
        assertEquals(0, movement.getCurrentPosition());
        String result = gameplay.movePlayer("Pippo", 2, 2);
        assertEquals(4, movement.getCurrentPosition());
        assertEquals("Pippo rolls 2, 2. Pippo moves from Start to 4", result);
    }

    @Test
    void movePlayerVictory() {
        gameplay.addPlayer("Pippo");
        Position movement = gameplay.positions.get("Pippo");
        gameplay.movePlayer("Pippo", 59, 1);
        assertEquals(60, movement.getCurrentPosition());
        String result = gameplay.movePlayer("Pippo", 1, 2);
        assertEquals(63, movement.getCurrentPosition());
        assertEquals("Pippo rolls 1, 2. Pippo moves from 60 to 63. Pippo Wins!!", result);
    }

    @Test
    void movePlayerVictoryBounceBack() {
        gameplay.addPlayer("Pippo");
        Position movement = gameplay.positions.get("Pippo");
        gameplay.movePlayer("Pippo", 59, 1);
        assertEquals(60, movement.getCurrentPosition());
        String result = gameplay.movePlayer("Pippo", 3, 2);
        assertEquals(61, movement.getCurrentPosition());
        assertEquals("Pippo rolls 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61", result);
    }

    @Test
    void movePlayerRandom() {
        gameplay.addPlayer("Pippo");
        Position movement = gameplay.positions.get("Pippo");
        assertEquals(0, movement.getCurrentPosition());
        String result = gameplay.movePlayer("Pippo");
        assertNotEquals(0, movement.getCurrentPosition());
    }

    @Test
    void movePlayerLandingOnBridge() {
        gameplay.addPlayer("Pippo");
        Position movement = gameplay.positions.get("Pippo");
        gameplay.movePlayer("Pippo", 2, 2);
        assertEquals(4, movement.getCurrentPosition());
        String result = gameplay.movePlayer("Pippo", 1, 1);
        assertEquals(12, movement.getCurrentPosition());
        assertEquals("Pippo rolls 1, 1. Pippo moves from 4 to The Bridge. Pippo jumps to 12", result);
    }

    @Test
    void movePlayerLandingOnGooseSingleJump() {
        gameplay.addPlayer("Pippo");
        Position movement = gameplay.positions.get("Pippo");
        gameplay.movePlayer("Pippo", 1, 2);
        assertEquals(3, movement.getCurrentPosition());
        String result = gameplay.movePlayer("Pippo", 1, 1);
        assertEquals(7, movement.getCurrentPosition());
        assertEquals("Pippo rolls 1, 1. Pippo moves from 3 to 5, The Goose. Pippo moves again and goes to 7", result);
    }

    @Test
    void movePlayerLandingOnGooseMultipleJump() {
        gameplay.addPlayer("Pippo");
        Position movement = gameplay.positions.get("Pippo");
        gameplay.movePlayer("Pippo", 5, 5);
        assertEquals(10, movement.getCurrentPosition());
        String result = gameplay.movePlayer("Pippo", 2, 2);
        assertEquals(22, movement.getCurrentPosition());
        assertEquals("Pippo rolls 2, 2. Pippo moves from 10 to 14, The Goose. Pippo moves again and goes to 18, The Goose. Pippo moves again and goes to 22", result);
    }

    @Test
    void movePlayerPrank() {
        gameplay.addPlayer("Pippo");
        gameplay.addPlayer("Pluto");

        Position movementPippo = gameplay.positions.get("Pippo");
        Position movementPluto = gameplay.positions.get("Pluto");
        gameplay.movePlayer("Pippo", 10, 5);
        gameplay.movePlayer("Pluto", 10, 7);

        assertEquals(15, movementPippo.getCurrentPosition());
        assertEquals(17, movementPluto.getCurrentPosition());
        String result = gameplay.movePlayer("Pippo", 1, 1);

        assertEquals(17, movementPippo.getCurrentPosition());
        assertEquals(15, movementPluto.getCurrentPosition());
        assertEquals("Pippo rolls 1, 1. Pippo moves from 15 to 17. On 17 there is Pluto, who returns to 15", result);

    }
}