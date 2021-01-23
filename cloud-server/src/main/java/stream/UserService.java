package stream;

import java.util.Optional;

public class UserService {

    Optional<User> getUserById(int id) {
        return Optional.of(new User(id, "Name"));
    }

}
