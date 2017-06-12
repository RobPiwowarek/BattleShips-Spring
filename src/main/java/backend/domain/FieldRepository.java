package backend.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FieldRepository extends JpaRepository<Field, Long> {

    List<Field> findByOccupacy(Boolean isOccupied);

    Field findByXAndY(Integer x, Integer y);
}
