package ro.ubb.postuniv.musify.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.postuniv.musify.model.Playlist;

import java.util.List;

@Repository
public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {

    List<Playlist> findAllByNameContainingIgnoreCase(String searchTerm);
}
