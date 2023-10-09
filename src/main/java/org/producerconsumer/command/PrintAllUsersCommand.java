package org.producerconsumer.command;

import org.producerconsumer.domain.User;
import org.producerconsumer.repository.Repository;

import java.util.List;

public class PrintAllUsersCommand implements Command<User> {
    public void execute(Repository<User> repository) {
        List<User> users = repository.listUsers();

        System.out.println(users);
    }
}
