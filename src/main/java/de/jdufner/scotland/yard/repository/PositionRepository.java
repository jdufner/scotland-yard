package de.jdufner.scotland.yard.repository;

import de.jdufner.scotland.yard.model.position.Position;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Jürgen Dufner
 * @since 1.0
 */
public interface PositionRepository extends CrudRepository<Position, Long> {
}
