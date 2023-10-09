package org.example.command;

import org.example.domain.User;
import org.example.repository.Repository;

import java.util.List;

public class PrintAllUsersCommand implements Command<User> {
    public void execute(Repository<User> repository) {
        List<User> users = repository.listUsers();

        System.out.println(users);
    }
}
