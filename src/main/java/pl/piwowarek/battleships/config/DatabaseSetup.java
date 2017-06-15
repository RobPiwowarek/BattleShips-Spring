package pl.piwowarek.battleships.config;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import pl.piwowarek.battleships.domain.Tile;
import pl.piwowarek.battleships.domain.TileRepository;

@Component
class DatabaseSetup implements CommandLineRunner {

    private final static Logger logger = Logger.getLogger(DatabaseSetup.class);
    private final static int GRID_SIZE = 6;
    private final TileRepository tileRepository;

    @Autowired
    public DatabaseSetup(TileRepository tileRepository) {
        this.tileRepository = tileRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("Database Setup Begins");

        for (int i = 0; i < GRID_SIZE; ++i) {
            for (int j = 0; j < GRID_SIZE; ++j) {
                Tile tile = new Tile(i, j, false);
                tileRepository.save(tile);
                logger.info("Saved new tile: " + tile);
            }
        }

        logger.info("Database Setup Complete");
    }

}
