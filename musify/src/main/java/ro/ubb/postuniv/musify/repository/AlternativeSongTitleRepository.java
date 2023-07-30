package ro.ubb.postuniv.musify.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import ro.ubb.postuniv.musify.model.AlternativeSongTitle;

public interface AlternativeSongTitleRepository extends CrudRepository<AlternativeSongTitle, Integer> {

    @Override
    List<AlternativeSongTitle> findAll();
}
