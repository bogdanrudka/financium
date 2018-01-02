package com.financium.menager.service;

import com.financium.menager.dto.request.UserRequest;
import com.financium.menager.dto.response.UserResponse;
import com.financium.menager.validation.FieldValueExists;

import java.util.List;

public interface UserService  extends FieldValueExists{
    List<UserResponse> getAllUsers(boolean skipInactive);

    UserResponse getUser(Long id);

    Long createUser(UserRequest request);

    void updateUser(Long id, UserRequest request);

    void updateActiveState(Long id, Boolean skipInactive);

    Long createNewUser(Long id, UserRequest request);

}
