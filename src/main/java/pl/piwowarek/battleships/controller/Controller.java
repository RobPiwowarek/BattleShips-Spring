package pl.piwowarek.battleships.controller;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import pl.piwowarek.battleships.controller.dto.TileDto;
import pl.piwowarek.battleships.domain.Tile;
import pl.piwowarek.battleships.domain.TileRepository;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class Controller {

    private final static Logger logger = Logger.getLogger(Controller.class);

    private final TileRepository tileRepository;

    public Controller(TileRepository tileRepository) {
        this.tileRepository = tileRepository;
    }

    @GetMapping("/field")
    public TileDto getSpecificField(@RequestParam Integer x, @RequestParam Integer y, Principal principal) {
        logger.info(principal.getName() + ":" + " GET /field x: " + x + " y: " + y);

        return new TileDto(tileRepository.findByXAndY(x, y));
    }

    @GetMapping("/")
    public List<TileDto> getAllOccupiedFieldsFromDatabase(Principal principal) {
        logger.info(principal.getName() + ":" + " GET /");

        return tileRepository
                .findByIsOccupied(true)
                .stream()
                .map(TileDto::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/")
    public void setOccupiedField(@RequestBody Message data, Principal principal) {
        logger.info(principal.getName() + ":" + " POST " + data);

        Tile tile = tileRepository.findByXAndY(data.getX(), data.getY());
        tile.setOccupied(true);

        if (principal.getName().equals("user"))
            tile.setColor("blue");
        else
            tile.setColor("green");

        tileRepository.save(tile);
    }

}

@Getter
@Setter
@ToString
class Message {
    Integer x;
    Integer y;
}