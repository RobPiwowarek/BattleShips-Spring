package backend.controller.dto;

import backend.domain.User;
import lombok.Data;

@Data
public class UserDTO {
    private String nick;

    UserDTO(User user){
        nick = user.getNick();
    }

}
