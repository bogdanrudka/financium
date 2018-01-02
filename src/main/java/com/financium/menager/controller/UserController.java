package com.financium.menager.controller;

import com.financium.menager.dto.request.UserRequest;
import com.financium.menager.dto.response.UserResponse;
import com.financium.menager.repository.UserRepository;
import com.financium.menager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static java.util.Optional.ofNullable;
import static org.springframework.http.ResponseEntity.created;
import static org.springframework.web.servlet.support.ServletUriComponentsBuilder.fromCurrentRequest;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    //TODO make skipInactive parameter optional
    public List<UserResponse> getAllUsers(@RequestParam(value = "skipDeleted", required = false) Boolean skipDeleted) {
        return userService.getAllUsers(ofNullable(skipDeleted).orElse(true));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> getUser(@PathVariable Long id) {
        return ofNullable(userService.getUser(id)).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest) {
        userService.updateUser(id, userRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        userService.updateActiveState(id, false);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/activate")
    public ResponseEntity<?> activateUser(@PathVariable Long id) {
        userService.updateActiveState(id, true);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<?> createNewUser(@Valid @RequestBody UserRequest request) {
        Long result = userService.createUser(request);
        return created(fromCurrentRequest().path("/{id}").buildAndExpand(result).toUri()).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> partialUpdate(@PathVariable Long id, @Valid @RequestBody UserRequest request) {
        if (userRepository.exists(id)) {
            userService.updateUser(id, request);
        } else {
            //userService.createNewUser(id, request);
            //TODO find a way to implement creation of user with specified id.
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
