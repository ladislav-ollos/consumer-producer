package org.producerconsumer.command;

import org.producerconsumer.domain.User;
import org.producerconsumer.repository.Repository;

public class CreateUserCommand implements Command<User> {

    private final User user;
    public CreateUserCommand(User user) {
        this.user=user;
    }
    public void execute(Repository<User> repository) {
        repository.createUser(user);
    }
}
