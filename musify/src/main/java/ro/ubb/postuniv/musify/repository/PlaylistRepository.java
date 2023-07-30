package ro.ubb.postuniv.musify.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.postuniv.musify.model.Playlist;

@Repository
public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {

    @Override
    List<Playlist> findAll();
    List<Playlist> findAllByNameContainingIgnoreCase(String searchTerm);
}
