package org.example.command;

import org.example.domain.User;
import org.example.repository.Repository;

public class CreateUserCommand implements Command<User> {

    private final User user;
    public CreateUserCommand(User user) {
        this.user=user;
    }
    public void execute(Repository<User> repository) {
        repository.createUser(user);
    }
}
