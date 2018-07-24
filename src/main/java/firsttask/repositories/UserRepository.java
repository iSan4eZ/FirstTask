package firsttask.repositories;

import firsttask.domain.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface UserRepository extends CrudRepository<User, String> {
    User findUserByUsernameIgnoreCase(String username);
    User findById(int id);
    long deleteById(int id);

    @Query("select u from User u")
    Stream<User> findAllByCustomQueryAndStream();
}
