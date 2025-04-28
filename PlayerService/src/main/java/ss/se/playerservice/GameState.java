package ss.se.playerservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class GameState {
    private final Map<String, PlayerMoveMessage> players = new ConcurrentHashMap<>();

    public void updatePlayer(String id, float x, float y) {
        PlayerMoveMessage move = new PlayerMoveMessage();
        move.setId(id);
        move.setX(x);
        move.setY(y);
        players.put(id, move);
    }

    public Map<String, PlayerMoveMessage> getPlayers() {
        return players;
    }


}