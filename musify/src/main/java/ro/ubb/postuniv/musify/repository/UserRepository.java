package ro.ubb.postuniv.musify.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import ro.ubb.postuniv.musify.model.User;

public interface UserRepository extends CrudRepository<User, Integer> {

    @Override
    List<User> findAll();
    Optional<User> findUserByEmail(String email);
}
