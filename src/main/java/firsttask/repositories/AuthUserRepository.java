package firsttask.repositories;

import firsttask.domain.AuthUser;
import org.springframework.data.repository.CrudRepository;

public interface AuthUserRepository extends CrudRepository<AuthUser, Long> {
    AuthUser findByUsername(String username);
}
