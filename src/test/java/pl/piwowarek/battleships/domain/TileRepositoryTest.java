package pl.piwowarek.battleships.domain;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
public class TileRepositoryTest {

    @Autowired
    private TileRepository repository;

    @Test
    @Transactional
    public void whenSearchingForOccupiedFieldsThenAllFieldsReturnedHaveOccupiedEqualTrue() {
        final Tile tile55 = new Tile(5, 5, true);
        final Tile tile77 = new Tile(7, 7, true);

        repository.save(tile55);
        repository.save(tile77);

        List<Tile> tiles = new ArrayList<>(repository.findByIsOccupied(true));

        assertThat(tiles)
                .contains(tile55, tile77)
                .hasSize(2);
    }

    @Test
    @Transactional
    public void whenSearchingForFieldOnEmptyRepoThenEmptyFieldIsReturned() {
        repository.deleteAll();

        assertTrue(repository.findByXAndY(0, 0) == null);
    }

    @Test
    public void whenSearchingForOccupiedFieldsIfNoFieldsAreOccupiedThenReturnedCollectionIsEmpty() {
        List<Tile> tiles = new ArrayList<>(repository
                .findByIsOccupied(true));

        assertThat(tiles)
                .isEmpty();
    }

    @Test
    public void whenSearchingForFieldWithXAndYReturnThenFieldWithCorrectXAndYIsReturned() {
        System.out.println(repository.findAll().size());

        Tile tile12 = repository.findByXAndY(1, 2);

        assertThat(tile12)
                .hasFieldOrPropertyWithValue("x", 1)
                .hasFieldOrPropertyWithValue("y", 2);

        Tile tile11 = repository.findByXAndY(1, 1);

        assertThat(tile11)
                .hasFieldOrPropertyWithValue("x", 1)
                .hasFieldOrPropertyWithValue("y", 1);
    }

}