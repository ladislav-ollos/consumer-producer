package org.producerconsumer.repository;


import java.util.List;

public interface Repository<T> {
    void createUser(T user);
    List<T> listUsers();
    void deleteAll();
}
