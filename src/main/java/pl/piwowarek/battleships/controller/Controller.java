package pl.piwowarek.battleships.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import pl.piwowarek.battleships.controller.dto.TileDto;
import pl.piwowarek.battleships.service.GameService;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
public class Controller {
    private final static Logger logger = Logger.getLogger(Controller.class);

    private final SimpMessagingTemplate template;

    private final GameService gameService;

    @Autowired
    public Controller(SimpMessagingTemplate template, GameService gameService) {
        this.template = template;
        this.gameService = gameService;
    }

    @SendTo("/topic/greetings")
    private String notifySubscribersToRefreshBoard() throws Exception {
        logger.info("notifying subscribers");

        template.convertAndSend("/topic/greetings", "notify");

        return "{\"refresh\":\"true\"}";
    }

    @GetMapping("/field")
    public TileDto getSpecificField(@RequestParam Integer x, @RequestParam Integer y) {
        return gameService.getSpecificField(x, y);
    }

    @GetMapping("/")
    public List<TileDto> getAllOccupiedFieldsFromDatabase(Principal principal) {
        logger.info(principal.getName() + ":" + " GET /");

        return gameService.getAllOccupiedFields();
    }

    @PostMapping("/")
    public void setOccupiedField(@RequestBody Message data, Principal principal) throws Exception {
        logger.info(principal.getName() + ":" + " POST " + data);

        gameService.setPostedTile(data.getX(), data.getY(), principal.getName());

        notifySubscribersToRefreshBoard();
    }

}

@Getter
@Setter
@ToString
class Message {
    Integer x;
    Integer y;
}