package hello.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface FieldRepository extends JpaRepository<Field, Long> {

    ArrayList<Field> findByOccupacy(Boolean isOccupied);

    Field findByXAndY(Integer x, Integer y);
}
