package ro.ubb.postuniv.musify.repository;

import ro.ubb.postuniv.musify.model.Song;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SongRepository extends CrudRepository<Song, Integer> {

    @Query(value = "SELECT s FROM Song s LEFT JOIN AlternativeSongTitle ast ON ast.song.id=s.id WHERE (s.title LIKE CONCAT('%', :title, '%') OR ast.alternativeTitle LIKE CONCAT('%', :title, '%'))")
    List<Song> findAllByTitleAndAlternativeTitles(
            @Param("title") String title
    );
}
