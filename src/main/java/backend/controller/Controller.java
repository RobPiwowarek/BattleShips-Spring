package backend.controller;

import backend.controller.dto.FieldDTO;
import backend.domain.Field;
import backend.domain.FieldRepository;
import backend.domain.UserRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin
public class Controller {

    @Autowired
    private FieldRepository fieldRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping(value = "/login")
    public void notSureIfNeeded(){}

    @GetMapping(value = "/field")
    public FieldDTO getSpecificField(@RequestParam Integer x, @RequestParam Integer y) {

        return new FieldDTO(fieldRepository.findByXAndY(x, y));
    }

    @GetMapping(value = "/")
    public List<FieldDTO> getAllOccupiedFieldsFromDatabase(Principal principal) {

        System.out.println(principal.getName());

        return fieldRepository
                .findByIsOccupied(true)
                .stream()
                .map(FieldDTO::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/", method = POST)
    public void setOccupiedField(@RequestBody Message data, Principal principal) {

        Field field = fieldRepository.findByXAndY(data.getX(), data.getY());
        field.setOccupied(true);

        if (principal.getName().equals("user"))
            field.setColor("blue");
        else
            field.setColor("green");

        fieldRepository.save(field);

        fieldRepository.findAll().forEach(System.out::println);
    }

}

@Getter
@Setter
@ToString
class Message {
    Integer x;
    Integer y;
}