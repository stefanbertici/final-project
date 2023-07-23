package ro.ubb.postuniv.musify.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ro.ubb.postuniv.musify.model.Album;

import java.util.List;

@Repository
public interface AlbumRepository extends CrudRepository<Album, Integer> {

    @Override
    List<Album> findAll();
    List<Album> findAllByTitleContainingIgnoreCase(String searchTerm);
}
