package ss.se.playerservice.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import ss.se.playerservice.GameState;
import ss.se.playerservice.PlayerMoveMessage;

import java.util.logging.Logger;

@Controller
public class PlayerServiceController {
    private final GameState gameState = new GameState();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/player-move") // Client sendet hierher (=> /app/player-move)
    public void handlePlayerMove(PlayerMoveMessage move) {
        PlayerMoveMessage current = gameState.getPlayers().get(move.getId());

        // wenn neue Position übermittelt wird
        if (current == null || current.getX() != move.getX() || current.getY() != move.getY()) {
            // Position aktualisieren
            gameState.updatePlayer(move.getId(), move.getX(), move.getY());
            Logger.getAnonymousLogger().info("Player: " + move.getId() + " moved at " + move.getX() + " " + move.getY());

            // Allen Clients neue Positionen senden
            messagingTemplate.convertAndSend("/topic/players", gameState.getPlayers().values());
        }
    }
}
