package ro.ubb.postuniv.musify.repository;

import org.springframework.data.repository.CrudRepository;
import ro.ubb.postuniv.musify.model.AlternativeSongTitle;

import java.util.List;

public interface AlternativeSongTitleRepository extends CrudRepository<AlternativeSongTitle, Integer> {

    @Override
    List<AlternativeSongTitle> findAll();
}
