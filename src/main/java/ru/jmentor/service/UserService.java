package ru.jmentor.service;

import ru.jmentor.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    User edit(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void delete(Long id);
}
