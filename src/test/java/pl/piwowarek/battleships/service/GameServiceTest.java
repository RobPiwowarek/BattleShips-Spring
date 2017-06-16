package pl.piwowarek.battleships.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.transaction.Transactional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class GameServiceTest {

    @Autowired
    private GameService gameService;

    @Test
    @Transactional
    public void givenUser2WhenAddingTileThenColorIsGreen() throws Exception {

        assertEquals(gameService.getSpecificField(1, 1).getColor(), "red");

        gameService.setPostedTile(1, 1, "user2");

        assertEquals(gameService.getSpecificField(1, 1).getColor(), "green");
    }

    @Test
    @Transactional
    public void givenUserWhenAddingTileThenColorIsBlue() throws Exception {
        assertEquals(gameService.getSpecificField(1, 1).getColor(), "red");

        gameService.setPostedTile(1, 1, "user");

        assertEquals(gameService.getSpecificField(1, 1).getColor(), "blue");
    }

    @Test
    @Transactional
    public void whenGettingAllOccupiedTilesThenAllTilesReturnedAreOccupied() throws Exception {
        gameService.setPostedTile(1, 1, "user");
        gameService.setPostedTile(5, 2, "user");
        gameService.setPostedTile(1, 3, "user");
        gameService.setPostedTile(2, 1, "user");

        Assertions.assertThat(gameService.getAllOccupiedFields().contains(gameService.getSpecificField(1, 1)));
        Assertions.assertThat(gameService.getAllOccupiedFields().contains(gameService.getSpecificField(5, 2)));
        Assertions.assertThat(gameService.getAllOccupiedFields().contains(gameService.getSpecificField(1, 3)));
        Assertions.assertThat(gameService.getAllOccupiedFields().contains(gameService.getSpecificField(2, 1)));
        Assertions.assertThat(gameService.getAllOccupiedFields().size() == 4);
    }
}