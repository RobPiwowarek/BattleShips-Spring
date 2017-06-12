package hello.controller.dto;

import hello.domain.Field;
import lombok.Data;

@Data
public class FieldDTO {
    private final Integer x;
    private final Integer y;
    private final boolean occupied;

    public FieldDTO(Field field) {
        x = field.getX();
        y = field.getY();
        occupied = field.isOccupacy();
    }
}
