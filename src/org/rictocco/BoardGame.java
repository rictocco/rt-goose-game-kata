package org.rictocco;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class BoardGame {
    private final int boardSize;
    private final Map<Integer, Integer> theBridge;
    private final List<Integer> theGoose;

    public BoardGame() {
        this.boardSize = 63;
        this.theBridge = Collections.singletonMap(6,12);
        this.theGoose = new ArrayList<>(Arrays.asList(5, 9, 14, 18, 23, 27));
    }

    public int getBoardSize() {
        return boardSize;
    }

    public boolean landedOnBridge(int space) {
        return theBridge.containsKey(space);
    }

    public int getBridgeJump(int space) {
        return theBridge.get(space);
    }

    public boolean landedOnGoose(int space) {
        return theGoose.contains(space);
    }

    public boolean victory(int space) {
        return boardSize == space;
    }
}
