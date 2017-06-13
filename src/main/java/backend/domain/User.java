package backend.domain;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;

@Entity
@ToString
@Getter
@Setter
public class User {
    @GeneratedValue
    private Long id;

    private String nick;

    public User(String nick) {
        this.nick = nick;
    }
}
