package ro.ubb.postuniv.musify.repository;

import ro.ubb.postuniv.musify.model.Artist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArtistRepository extends CrudRepository<Artist, Integer> {

    List<Artist> findAllByStageNameContainingIgnoreCase(String searchTerm);
}
