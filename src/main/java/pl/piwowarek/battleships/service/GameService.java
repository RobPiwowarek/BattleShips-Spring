package pl.piwowarek.battleships.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.piwowarek.battleships.controller.dto.TileDto;
import pl.piwowarek.battleships.domain.Tile;
import pl.piwowarek.battleships.domain.TileRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class GameService {

    private final TileRepository tileRepository;

    @Autowired
    public GameService(TileRepository tileRepository) {
        this.tileRepository = tileRepository;
    }

    public void addTile(Tile tile) {
        tileRepository.save(tile);
    }

    public TileDto getSpecificField(Integer x, Integer y) {
        return new TileDto(tileRepository.findByXAndY(x, y));
    }

    public List<TileDto> getAllOccupiedFields() {
        return tileRepository
                .findByIsOccupied(true)
                .stream()
                .map(TileDto::new)
                .collect(Collectors.toList());
    }

    public void setPostedTile(Integer x, Integer y, String principalName) {
        Tile tile = tileRepository.findByXAndY(x, y);
        tile.setOccupied(true);

        if (principalName.equals("user"))
            tile.setColor("blue");
        else
            tile.setColor("green");

        tileRepository.save(tile);
    }
}
