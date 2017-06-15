package pl.piwowarek.battleships.controller.dto;

import lombok.Data;
import pl.piwowarek.battleships.domain.Tile;

@Data
public class TileDto {
    private final Integer x;
    private final Integer y;
    private final boolean occupied;
    private final String color;

    public TileDto(Tile tile) {
        x = tile.getX();
        y = tile.getY();
        occupied = tile.isOccupied();
        color = tile.getColor();
    }
}
