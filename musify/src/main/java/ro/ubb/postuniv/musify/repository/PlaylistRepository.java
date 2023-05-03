package ro.ubb.postuniv.musify.repository;

import ro.ubb.postuniv.musify.model.Playlist;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {
}
