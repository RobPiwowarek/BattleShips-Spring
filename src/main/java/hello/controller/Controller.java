package hello.controller;

import hello.controller.dto.FieldDTO;
import hello.domain.Field;
import hello.domain.FieldRepository;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class Controller {

    @Autowired
    private FieldRepository fieldRepository;

    @GetMapping(value = "/field/")
    public FieldDTO getSpecificField(@RequestParam Integer x, @RequestParam Integer y) {
        return new FieldDTO(fieldRepository.findByXAndY(x, y));
    }

    @GetMapping
    public List<FieldDTO> getAllOccupiedFieldsFromDatabase() {
        return fieldRepository
                .findByOccupacy(true)
                .stream()
                .map(FieldDTO::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/", method = POST)
    public void setOccupiedField(@RequestBody Message data) {

        Field field = fieldRepository.findByXAndY(data.getX(), data.getY());
        field.setOccupacy(true);

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