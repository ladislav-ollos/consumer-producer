package org.example.command;

import org.example.domain.User;
import org.example.repository.Repository;

public class DeleteAllUsersCommand implements Command<User> {
    public void execute(Repository<User> repository) {
        repository.deleteAll();
    }
}
