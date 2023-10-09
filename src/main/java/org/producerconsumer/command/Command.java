package org.producerconsumer.command;

import org.producerconsumer.repository.Repository;

public interface Command<U> {
    void execute(Repository <U> repository);
}
