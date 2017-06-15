package pl.piwowarek.battleships.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TileRepository extends JpaRepository<Tile, Long> {
    List<Tile> findByIsOccupied(Boolean isMarked);

    Tile findByXAndY(Integer x, Integer y);
}
