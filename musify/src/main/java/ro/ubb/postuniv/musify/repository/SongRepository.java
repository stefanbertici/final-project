package ro.ubb.postuniv.musify.repository;

import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.ubb.postuniv.musify.model.Song;

@Repository
public interface SongRepository extends CrudRepository<Song, Integer> {

    @Override
    List<Song> findAll();

    @Query(value = "SELECT s FROM Song s " +
            "LEFT JOIN AlternativeSongTitle ast ON ast.song.id=s.id " +
            "WHERE (s.title LIKE CONCAT('%', :title, '%') OR ast.alternativeTitle LIKE CONCAT('%', :title, '%'))")
    Set<Song> findAllByTitleAndAlternativeTitlesContainingIgnoreCase(@Param("title") String title);

    Set<Song> findAllByAlbumsArtistStageNameContainingIgnoreCase(String title);
}
