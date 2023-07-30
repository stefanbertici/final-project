package ro.ubb.postuniv.musify.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.postuniv.musify.model.Band;

@Repository
public interface BandRepository extends CrudRepository<Band, Integer> {

    @Override
    List<Band> findAll();
    List<Band> findAllByBandNameContainingIgnoreCase(String searchTerm);
}
