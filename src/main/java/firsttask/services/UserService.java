package firsttask.services;

import firsttask.domain.User;
import firsttask.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        userRepository.findAll().
                forEach(users::add);
        return users;
    }

    public User getUserById(int id){
        return userRepository.findById(id);
    }

    public User getUserByUsername(String username){
        return userRepository.findUserByUsernameIgnoreCase(username);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }


}
