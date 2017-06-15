package pl.piwowarek.battleships.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Tile {

    @Id
    @GeneratedValue
    private Long id;

    private Integer x;

    private Integer y;

    private boolean isOccupied;

    private String color;

    public Tile() {
    }

    public Tile(Integer x, Integer y, boolean occupied) {
        this.x = x;
        this.y = y;
        this.isOccupied = occupied;
        color = "red";
    }
}
