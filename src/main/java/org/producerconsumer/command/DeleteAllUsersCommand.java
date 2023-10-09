package org.producerconsumer.command;

import org.producerconsumer.domain.User;
import org.producerconsumer.repository.Repository;

public class DeleteAllUsersCommand implements Command<User> {
    public void execute(Repository<User> repository) {
        repository.deleteAll();
    }
}
