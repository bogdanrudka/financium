package com.financium.menager.repository;

import com.financium.menager.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findUserByIdAndActive(Long id, boolean active);
    Iterable<User> findAllByActive(boolean active);
    int countByLogin(String userName);
}
