package FirstTask.user;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {
    User findUserByUsernameIgnoreCase(String username);
    User findById(int id);
    long deleteById(int id);
}
