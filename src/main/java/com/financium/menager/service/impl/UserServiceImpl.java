package com.financium.menager.service.impl;

import com.financium.menager.domain.BaseDomain;
import com.financium.menager.domain.User;
import com.financium.menager.dto.request.UserRequest;
import com.financium.menager.dto.response.UserResponse;
import com.financium.menager.exception.UserNotFoundException;
import com.financium.menager.repository.UserRepository;
import com.financium.menager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.stream.StreamSupport.stream;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponse> getAllUsers(boolean skipInactive) {
        Iterable<User> users = skipInactive ? userRepository.findAllByActive(true) : userRepository.findAll();
        return stream(users.spliterator(), false).map(this::mapUserToDto).collect(Collectors.toList());
    }

    @Override
    public UserResponse getUser(Long id) {
        User user = userRepository.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("Cannot find user with id: " + id);
        }
        return mapUserToDto(user);
    }

    private UserResponse mapUserToDto(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setLogin(user.getLogin());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setAccounts(user.getAccounts().stream().map(BaseDomain::getId).collect(Collectors.toList()));
        return response;
    }

    @Override
    public void updateUser(Long id, UserRequest userDto) {
        User user = userRepository.findOne(id);
        setIfPresent(user::setFirstName, userDto.getFirstName());
        setIfPresent(user::setLastName, userDto.getLastName());
        setIfPresent(user::setMiddleName, userDto.getMiddleName());
    }

    /**
     * this method with check if a value is not null. If a value if empty string the it will be set to null in db.
     */
    private <T> void setIfPresent(Consumer<T> set, T value) {
        if (value != null) {
            set.accept(value == "" ? null : value);
        }
    }

    @Override
    public void updateActiveState(Long id, Boolean state) {
        User user = Optional.ofNullable(userRepository.findOne(id))
                .orElseThrow(() -> new UserNotFoundException("Cannot find user with id: " + id));

        user.setActive(state);
        userRepository.save(user);
    }

    @Override
    public Long createNewUser(Long id, UserRequest request) {
        User user = buildUser(request);
        user.setId(id);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Long createUser(UserRequest userDto) {
        User user = buildUser(userDto);
        userRepository.save(user);
        return user.getId();
    }

    private User buildUser(UserRequest userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setLogin(userDto.getLogin());
        user.setMiddleName(userDto.getMiddleName());
        user.setActive(true);
        return user;
    }

    @Override
    public boolean fieldValueExists(Object value, String fieldName) {
        if ("login".equals(fieldName)) {
            throw new UnsupportedOperationException();
        }
        return value != null && userRepository.countByLogin(value.toString()) > 0;
    }
}
