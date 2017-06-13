package backend.config;

import backend.domain.Field;
import backend.domain.FieldRepository;
import backend.domain.User;
import backend.domain.UserRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
class DatabaseSetup implements CommandLineRunner {

    final static Logger logger = Logger.getLogger(DatabaseSetup.class);
    private final static int GRID_SIZE = 6;
    private final FieldRepository fieldRepository;
    private final UserRepository userRepository;

    @Autowired
    public DatabaseSetup(FieldRepository fieldRepository, UserRepository userRepository) {
        this.fieldRepository = fieldRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        for (int i = 0; i < GRID_SIZE; ++i) {
            for (int j = 0; j < GRID_SIZE; ++j) {
                fieldRepository.save(new Field(i, j, false));

            }
        }

        userRepository.save(new User("User1", "password1"));
        userRepository.save(new User("User2", "password2"));
        //fieldRepository.findAll().forEach(System.out::println);
    }

}
