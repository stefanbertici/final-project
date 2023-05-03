package ro.ubb.postuniv.musify.repository;

import ro.ubb.postuniv.musify.model.Band;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BandRepository extends CrudRepository<Band, Integer> {

    List<Band> findAllByBandNameContainingIgnoreCase(String searchTerm);
}
