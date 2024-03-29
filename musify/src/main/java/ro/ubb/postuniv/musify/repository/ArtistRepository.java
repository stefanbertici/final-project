package ro.ubb.postuniv.musify.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.postuniv.musify.model.Artist;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Integer> {

    @Override
    List<Artist> findAll();
    List<Artist> findAllByStageNameContainingIgnoreCase(String searchTerm);
}
