package backend.controller.dto;

import backend.domain.Field;
import lombok.Data;

@Data
public class FieldDTO {
    private final Integer x;
    private final Integer y;
    private final boolean occupied;
    private final String color;

    public FieldDTO(Field field) {
        x = field.getX();
        y = field.getY();
        occupied = field.isOccupied();
        color = field.getColor();
    }
}
