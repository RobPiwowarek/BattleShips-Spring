package backend.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FieldRepositoryTest {

    @Autowired
    FieldRepository repository;

    @Test
    @Transactional
    public void whenSearchingForOccupiedFieldsThenAllFieldsReturnedHaveOccupiedEqualTrue() {
        Field field55 = new Field(5, 5, true);
        Field field77 = new Field(7, 7, true);

        repository.save(field55);
        repository.save(field77);

        ArrayList<Field> fields = (ArrayList<Field>) repository.findByIsOccupied(true);

        assertTrue(field55.getX() == fields.get(0).getX());
        assertTrue(field55.getY() == fields.get(0).getY());
        assertTrue(field77.getX() == fields.get(1).getX());
        assertTrue(field77.getY() == fields.get(1).getY());

        assertTrue(fields.size() == 2);
    }

    @Test
    @Transactional
    public void whenSearchingForFieldOnEmptyRepoThenEmptyFieldIsReturned() {
        repository.deleteAll();

        assertTrue(repository.findByXAndY(0, 0) == null);
    }

    @Test
    public void whenSearchingForOccupiedFieldsIfNoFieldsAreOccupiedThenReturnedCollectionIsEmpty() {
        ArrayList<Field> fields = (ArrayList<Field>) repository.findByIsOccupied(true);

        assertTrue(fields.size() == 0);
    }

    @Test
    public void whenSearchingForFieldWithXAndYReturnThenFieldWithCorrectXAndYIsReturned() {
        System.out.println(repository.findAll().size());

        Field field12 = repository.findByXAndY(1, 2);

        assertTrue(field12.getX() == 1);
        assertTrue(field12.getY() == 2);

        Field field11 = repository.findByXAndY(1, 1);

        assertTrue(field11.getX() == 1);
        assertTrue(field11.getY() == 1);

    }

}