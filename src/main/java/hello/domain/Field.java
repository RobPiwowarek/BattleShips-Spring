package hello.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@ToString
@Getter
@Setter
public class Field {

    @Id
    @GeneratedValue
    private Long id;

    private Integer x;

    private Integer y;

    private boolean occupacy;

    public Field() {
    }

    public Field(Integer x, Integer y, boolean occupied) {
        this.x = x;
        this.y = y;
        this.occupacy = occupied;
    }
}
