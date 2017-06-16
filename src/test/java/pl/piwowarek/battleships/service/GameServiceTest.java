package pl.piwowarek.battleships.service;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import pl.piwowarek.battleships.controller.dto.TileDto;
import pl.piwowarek.battleships.domain.Tile;
import pl.piwowarek.battleships.domain.TileRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class GameServiceTest {

    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    private GameService gameService;
    private TileRepository tileRepository;

    @Test
    public void givenUser2WhenAddingTileThenColorIsGreen() throws Exception {
        tileRepository = Mockito.mock(TileRepository.class, Mockito.RETURNS_DEEP_STUBS);
        gameService = new GameService(tileRepository);

        when(tileRepository.findByXAndY(1, 1)).thenReturn(new Tile(1, 1, false));

        assertEquals(gameService.getSpecificField(1, 1).getColor(), "red");

        gameService.setPostedTile(1, 1, "user2");

        assertEquals(gameService.getSpecificField(1, 1).getColor(), "green");
    }

    @Test
    public void givenUserWhenAddingTileThenColorIsBlue() throws Exception {
        tileRepository = Mockito.mock(TileRepository.class, Mockito.RETURNS_DEEP_STUBS);
        gameService = new GameService(tileRepository);

        when(tileRepository.findByXAndY(1, 1)).thenReturn(new Tile(1, 1, false));

        assertEquals(gameService.getSpecificField(1, 1).getColor(), "red");

        gameService.setPostedTile(1, 1, "user");

        assertEquals(gameService.getSpecificField(1, 1).getColor(), "blue");
    }

    @Test
    public void whenGettingAllOccupiedTilesThenAllTilesReturnedAreOccupied() throws Exception {
        tileRepository = Mockito.mock(TileRepository.class, Mockito.RETURNS_DEEP_STUBS);
        gameService = new GameService(tileRepository);

        Tile tile11 = new Tile(1, 1, false);
        Tile tile52 = new Tile(5, 2, false);
        Tile tile13 = new Tile(1, 3, false);
        Tile tile21 = new Tile(2, 1, false);

        // Ten arraylist jest dlatego, ze wystepuje jakis dziwny blad z class cast exception
        // ktory wedlug stacka jest ponoc known-issue Mockito i RETURNS_DEEP_STUBS
        // generalnie psuje sie obiekt wygenerowany przez mockito reprezentujacy tileRepository
        // w polaczeniu z genericami
        // wiec zeby uzyc mockito we wszystkich testach tutaj nie bylo innego sposobu
        // a przynajmniej nie widzialem

        ArrayList<Tile> arrayList = new ArrayList<>();

        arrayList.add(tile11);
        arrayList.add(tile52);
        arrayList.add(tile13);
        arrayList.add(tile21);

        when(tileRepository.findByXAndY(1, 1)).thenReturn(tile11);
        when(tileRepository.findByXAndY(5, 2)).thenReturn(tile52);
        when(tileRepository.findByXAndY(1, 3)).thenReturn(tile13);
        when(tileRepository.findByXAndY(2, 1)).thenReturn(tile21);
        when(tileRepository.findByIsOccupied(true)).thenReturn(arrayList);

        gameService.setPostedTile(1, 1, "user");
        gameService.setPostedTile(5, 2, "user");
        gameService.setPostedTile(1, 3, "user");
        gameService.setPostedTile(2, 1, "user");

        List<TileDto> list = gameService.getAllOccupiedFields();

        Assertions.assertThat(list.contains(gameService.getSpecificField(1, 1)));
        Assertions.assertThat(list.contains(gameService.getSpecificField(5, 2)));
        Assertions.assertThat(list.contains(gameService.getSpecificField(1, 3)));
        Assertions.assertThat(list.contains(gameService.getSpecificField(2, 1)));

        assertTrue(gameService.getSpecificField(1, 1).isOccupied());
        assertTrue(gameService.getSpecificField(5, 2).isOccupied());
        assertTrue(gameService.getSpecificField(1, 3).isOccupied());
        assertTrue(gameService.getSpecificField(2, 1).isOccupied());

        assertEquals(gameService.getAllOccupiedFields().size(), 4);
    }
}