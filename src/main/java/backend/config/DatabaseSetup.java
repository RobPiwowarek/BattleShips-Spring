package backend.config;

import backend.domain.Field;
import backend.domain.FieldRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class DatabaseSetup implements CommandLineRunner {

    final static Logger logger = Logger.getLogger(DatabaseSetup.class);
    private final static int GRID_SIZE = 6;
    private final FieldRepository fieldRepository;

    @Autowired
    public DatabaseSetup(FieldRepository fieldRepository) {
        this.fieldRepository = fieldRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        for (int i = 0; i < GRID_SIZE; ++i) {
            for (int j = 0; j < GRID_SIZE; ++j) {
                fieldRepository.save(new Field(i, j, false));

            }
        }


        //fieldRepository.findAll().forEach(System.out::println);
    }

}
