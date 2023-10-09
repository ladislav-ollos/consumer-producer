package org.example.command;

import org.example.repository.Repository;

public interface Command<U> {
    void execute(Repository <U> repository);
}
